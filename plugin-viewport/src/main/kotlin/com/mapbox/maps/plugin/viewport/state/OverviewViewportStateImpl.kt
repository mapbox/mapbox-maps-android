package com.mapbox.maps.plugin.viewport.state

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import androidx.core.animation.doOnEnd
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.GeometryCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.MultiLineString
import com.mapbox.geojson.MultiPoint
import com.mapbox.geojson.MultiPolygon
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMapException
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.logI
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportTransitionFactory
import com.mapbox.maps.threading.AnimationThreadController
import java.util.concurrent.*

/**
 * The actual implementation of [OverviewViewportState] that shows the overview of a given geometry.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class OverviewViewportStateImpl(
  mapDelegateProvider: MapDelegateProvider,
  initialOptions: OverviewViewportStateOptions,
  private val transitionFactory: MapboxViewportTransitionFactory = MapboxViewportTransitionFactory(
    mapDelegateProvider
  )
) : OverviewViewportState {
  private val cameraPlugin = mapDelegateProvider.mapPluginProviderDelegate.camera
  private val cameraDelegate = mapDelegateProvider.mapCameraManagerDelegate
  private val dataSourceUpdateObservers = CopyOnWriteArraySet<ViewportStateDataObserver>()
  private var runningAnimation: AnimatorSet? = null

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var isOverviewStateRunning = false

  init {
    var cancelable = Cancelable { }
    // notify the camera options to any subscribers when the MapView's width and height is ready
    cancelable = mapDelegateProvider.mapListenerDelegate.subscribeRenderFrameStarted {
      handleNewData()
      cancelable.cancel()
    }
  }

  /**
   * Describes the configuration options of the state.
   */
  override var options: OverviewViewportStateOptions = initialOptions
    set(value) {
      field = value
      handleNewData()
    }

  private fun handleNewData() {
    val cameraOptions = evaluateViewportData() ?: return
    if (isOverviewStateRunning) {
      updateFrame(cameraOptions, false)
    }
    dataSourceUpdateObservers.forEach {
      if (!it.onNewData(cameraOptions)
      ) {
        dataSourceUpdateObservers.remove(it)
      }
    }
  }

  private fun evaluateViewportData(): CameraOptions? {
    return try {
      cameraDelegate.cameraForCoordinates(
        coordinates = options.geometry.extractCoordinates(),
        camera = cameraOptions {
          padding(options.padding)
          bearing(options.bearing)
          pitch(options.pitch)
        },
        coordinatesPadding = options.geometryPadding,
        maxZoom = options.maxZoom,
        offset = options.offset
      )
    } catch (exception: MapboxMapException) {
      // Catch the exception here as there's a corner case where calculation is done before the
      // mapView is inflated/properly rendered, and following exception will occur:
      // `Unable to calculate cameraForCoordinates, either padding exceeds screensize or screensize is not set`
      // In this case, we will silently ignore the exception and log it.
      logI(TAG, "Failed to evaluate viewport data. ${exception.message}")
      null
    }
  }

  /**
   * Observer the new camera options produced by the [ViewportState], it can be used to get the
   * latest state [CameraOptions] for [com.mapbox.maps.plugin.viewport.transition.ViewportTransition].
   *
   * @param viewportStateDataObserver observer that observe new viewport data.
   * @return a handle that cancels current observation.
   */
  override fun observeDataSource(viewportStateDataObserver: ViewportStateDataObserver): Cancelable {
    var keepObserving = true
    evaluateViewportData()?.let { cameraOptions ->
      keepObserving = viewportStateDataObserver.onNewData(cameraOptions)
    }
    if (keepObserving) {
      dataSourceUpdateObservers.add(viewportStateDataObserver)
    }

    return Cancelable {
      dataSourceUpdateObservers.remove(viewportStateDataObserver)
    }
  }

  /**
   * Start updating the camera for the current [ViewportState].
   */
  override fun startUpdatingCamera() {
    isOverviewStateRunning = true
  }

  /**
   * Stop updating the camera for the current [ViewportState].
   */
  override fun stopUpdatingCamera() {
    isOverviewStateRunning = false
    cancelAnimation()
  }

  @OptIn(MapboxExperimental::class)
  private fun cancelAnimation() {
    runningAnimation?.apply {
      AnimationThreadController.postOnAnimatorThread {
        cancel()
        childAnimations.forEach {
          cameraPlugin.unregisterAnimators(it as ValueAnimator)
        }
      }
      runningAnimation = null
    }
  }

  @OptIn(MapboxExperimental::class)
  private fun startAnimation(
    animatorSet: AnimatorSet,
    instant: Boolean,
  ) {
    cancelAnimation()
    animatorSet.childAnimations.forEach {
      cameraPlugin.registerAnimators(it as ValueAnimator)
    }
    if (instant) {
      animatorSet.duration = 0
    }
    AnimationThreadController.postOnAnimatorThread {
      animatorSet.start()
      runningAnimation = animatorSet
    }
  }

  private fun finishAnimation(animatorSet: AnimatorSet?) {
    animatorSet?.childAnimations?.forEach {
      cameraPlugin.unregisterAnimators(it as ValueAnimator)
    }
    if (runningAnimation == animatorSet) {
      runningAnimation = null
    }
  }

  private fun updateFrame(cameraOptions: CameraOptions, instant: Boolean = false) {
    startAnimation(
      transitionFactory.transitionLinear(cameraOptions, options.animationDurationMs)
        .apply { doOnEnd { finishAnimation(this) } },
      instant
    )
  }

  /**
   * Extract the flattened coordinates from the [Geometry].
   */
  private fun Geometry.extractCoordinates(): List<Point> {
    return when (this) {
      is Point -> listOf(this)
      is LineString -> this.coordinates()
      is Polygon -> this.coordinates().flatten()
      is MultiPoint -> this.coordinates()
      is MultiLineString -> this.coordinates().flatten()
      is MultiPolygon -> this.coordinates().flatten().flatten()
      is GeometryCollection -> this.geometries().flatMap { it.extractCoordinates() }
      else -> emptyList()
    }
  }

  private companion object {
    private const val TAG = "OverviewViewportStateImpl"
  }
}