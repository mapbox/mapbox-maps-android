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
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportTransitionFactory
import com.mapbox.maps.threading.AnimationThreadController
import java.util.concurrent.CopyOnWriteArraySet

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

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val dataSourceUpdateObservers = CopyOnWriteArraySet<ViewportStateDataObserver>()
  private var runningAnimation: AnimatorSet? = null

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var isOverviewStateRunning = false

  private var evaluateViewportDataCancelable: Cancelable? = null

  private var latestViewportData: CameraOptions? = null

  /**
   * Describes the configuration options of the state.
   */
  override var options: OverviewViewportStateOptions = initialOptions
    set(value) {
      field = value
      handleNewData()
    }

  init {
    handleNewData()
  }

  private fun handleNewData() {
    latestViewportData = null
    evaluateViewportDataCancelable?.cancel()
    evaluateViewportDataCancelable = evaluateViewportData { cameraOptions ->
      latestViewportData = cameraOptions
      if (isOverviewStateRunning) {
        updateFrame(cameraOptions, false)
      }
      dataSourceUpdateObservers.forEach {
        if (!it.onNewData(cameraOptions)) {
          dataSourceUpdateObservers.remove(it)
        }
      }
    }
  }

  private fun evaluateViewportData(callback: (CameraOptions) -> Unit): Cancelable {
    var cancelled = false
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
    ) {
      if (!cancelled) {
        callback.invoke(it)
      }
    }
    return Cancelable {
      cancelled = true
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
    latestViewportData?.let {
      if (viewportStateDataObserver.onNewData(it)) {
        dataSourceUpdateObservers.add(viewportStateDataObserver)
      }
    } ?: dataSourceUpdateObservers.add(viewportStateDataObserver)

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
}