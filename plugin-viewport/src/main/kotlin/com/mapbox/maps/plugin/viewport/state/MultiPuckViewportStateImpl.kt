package com.mapbox.maps.plugin.viewport.state

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.annotation.VisibleForTesting
import com.mapbox.geojson.MultiPoint
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.plugin.viewport.DEFAULT_STATE_ANIMATION_DURATION_MS
import com.mapbox.maps.plugin.viewport.data.MultiPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.MultiPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportTransitionFactory
import com.mapbox.maps.threading.AnimationThreadController
import java.util.concurrent.*
import kotlin.math.abs

/**
 * The actual implementation of [FollowPuckViewportState] that follows the location component's puck position.
 *
 * Note: [LocationComponentPlugin] should be enabled to use this viewport state.
 */
internal class MultiPuckViewportStateImpl(
  mapDelegateProvider: MapDelegateProvider,
  initialOptions: MultiPuckViewportStateOptions,
  private val locationComponents: List<LocationComponentPlugin2>,
  private val transitionFactory: MapboxViewportTransitionFactory = MapboxViewportTransitionFactory(
    mapDelegateProvider
  )
) : MultiPuckViewportState {
  private val cameraPlugin = mapDelegateProvider.mapPluginProviderDelegate.camera
  private val cameraDelegate = mapDelegateProvider.mapCameraManagerDelegate
  private val dataSourceUpdateObservers = CopyOnWriteArraySet<ViewportStateDataObserver>()

  private var runningAnimation: AnimatorSet? = null

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var isFollowingStateRunning = false
  private var isObservingLocationUpdates = false
  private var lastZoom: Double? = null

  private val lastLocations: MutableMap<LocationComponentPlugin2, Point?> =
    locationComponents.associateBy(
      { it },
      { null }
    ).toMutableMap()

  private val lastBearings: MutableMap<LocationComponentPlugin2, Double?> =
    locationComponents.associateBy(
      { it },
      { null }
    ).toMutableMap()
  private val locationComponentIndicatorPositionChangedListeners: Map<LocationComponentPlugin2, OnIndicatorPositionChangedListener> =
    locationComponents.associateBy(
      { it },
      {
        OnIndicatorPositionChangedListener { point ->
          lastLocations[it] = point
          notifyLatestViewportData()
        }
      }
    )
  private val locationComponentIndicatorBearingChangedListeners: Map<LocationComponentPlugin2, OnIndicatorBearingChangedListener> =
    locationComponents.associateBy(
      { it },
      {
        OnIndicatorBearingChangedListener { bearing ->
          lastBearings[it] = bearing
          notifyLatestViewportData()
        }
      }
    )

  private fun notifyLatestViewportData() {
    if (lastLocations.values.filterNotNull().isNotEmpty() &&
      (
        options.bearing is MultiPuckViewportStateBearing.Constant ||
          lastBearings.values.filterNotNull().isNotEmpty()
        )
    ) {
      val viewportData = evaluateViewportData()
      if (isFollowingStateRunning) {
        // Use instant update here since the location updates are already interpolated by the location component plugin
        updateFrame(viewportData, true)
      }
      dataSourceUpdateObservers.forEach {
        if (!it.onNewData(viewportData)) {
          dataSourceUpdateObservers.remove(it)
        }
      }
    }
  }

  private fun evaluateViewportData(): CameraOptions {
    val cameraOptions = cameraDelegate.cameraForGeometry(
      MultiPoint.fromLngLats(lastLocations.values.filterNotNull()),
      options.padding ?: EdgeInsets(0.0, 0.0, 0.0, 0.0),
      with(options.bearing) {
        when (this) {
          is MultiPuckViewportStateBearing.Constant -> bearing
          is MultiPuckViewportStateBearing.SyncWithLocationPuck -> lastBearings[locationComponent]
            ?: 0.0
          else -> 0.0
        }
      },
      options.pitch
    )
    val targetZoom = cameraOptions.zoom!!
    val nextZoom = lastZoom?.let {
      if (abs(targetZoom - it) > ZOOM_UPDATE_STEP) {
        if (targetZoom > it) {
          it + ZOOM_UPDATE_STEP
        } else {
          it - ZOOM_UPDATE_STEP
        }
      } else {
        targetZoom
      }
    } ?: targetZoom
    lastZoom = nextZoom

    return cameraOptions.toBuilder().zoom(nextZoom).build()
  }

  private fun addIndicatorListenerIfNeeded() {
    if (!isObservingLocationUpdates) {
      locationComponentIndicatorPositionChangedListeners.entries.forEach {
        it.key.addOnIndicatorPositionChangedListener(it.value)
      }
      locationComponentIndicatorBearingChangedListeners.entries.forEach {
        it.key.addOnIndicatorBearingChangedListener(it.value)
      }
      isObservingLocationUpdates = true
    }
  }

  private fun removeIndicatorListenerIfNeeded() {
    if (isObservingLocationUpdates && dataSourceUpdateObservers.isEmpty() && !isFollowingStateRunning) {
      locationComponentIndicatorPositionChangedListeners.entries.forEach {
        it.key.removeOnIndicatorPositionChangedListener(it.value)
      }
      locationComponentIndicatorBearingChangedListeners.entries.forEach {
        it.key.removeOnIndicatorBearingChangedListener(it.value)
      }
      isObservingLocationUpdates = false
    }
  }

  /**
   * Describes the configuration options of the state.
   */
  override var options: MultiPuckViewportStateOptions = initialOptions
    set(value) {
      field = value
      notifyLatestViewportData()
    }

  /**
   * Observer the new camera options produced by the [ViewportState], it can be used to get the
   * latest state [CameraOptions] for [ViewportTransition].
   *
   * @param viewportStateDataObserver observer that observe new viewport data.
   * @return a handle that cancels current observation.
   */
  override fun observeDataSource(viewportStateDataObserver: ViewportStateDataObserver): Cancelable {
    checkLocationComponentEnablement()
    addIndicatorListenerIfNeeded()
    dataSourceUpdateObservers.add(viewportStateDataObserver)
    return Cancelable {
      dataSourceUpdateObservers.remove(viewportStateDataObserver)
      removeIndicatorListenerIfNeeded()
    }
  }

  private fun checkLocationComponentEnablement() {
    if (locationComponents.none { it.enabled }) {
      logW(
        TAG,
        "At least one Location component is required to be enabled to use MultiPuckViewportState, otherwise there would be no MultiPuckViewportState updates or ViewportTransition updates towards the MultiPuckViewportState."
      )
    }
  }

  /**
   * Start updating the camera for the current [ViewportState].
   */
  override fun startUpdatingCamera() {
    checkLocationComponentEnablement()
    addIndicatorListenerIfNeeded()
    isFollowingStateRunning = true
  }

  /**
   * Stop updating the camera for the current [ViewportState].
   */
  override fun stopUpdatingCamera() {
    isFollowingStateRunning = false
    cancelAnimation()
    removeIndicatorListenerIfNeeded()
  }

  private fun cancelAnimation() {
    AnimationThreadController.postOnAnimatorThread {
      runningAnimation?.apply {
        cancel()
        childAnimations.forEach {
          cameraPlugin.unregisterAnimators(it as ValueAnimator)
        }
      }
      runningAnimation = null
    }
  }

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

  private fun updateFrame(
    cameraOptions: CameraOptions,
    instant: Boolean = false,
    onComplete: ((isFinished: Boolean) -> Unit)? = null
  ) {
    startAnimation(
      transitionFactory.transitionLinear(cameraOptions, DEFAULT_STATE_ANIMATION_DURATION_MS)
        .apply {
          addListener(
            object : Animator.AnimatorListener {
              private var isCanceled = false
              override fun onAnimationStart(animation: Animator) {
                // no-ops
              }

              override fun onAnimationEnd(animation: Animator) {
                onComplete?.invoke(!isCanceled)
                finishAnimation(this@apply)
              }

              override fun onAnimationCancel(animation: Animator) {
                isCanceled = true
              }

              override fun onAnimationRepeat(animation: Animator) {
                // no-ops
              }
            }
          )
        },
      instant
    )
  }

  private companion object {
    const val TAG = "MultiPuckViewportStateImpl"
    const val ZOOM_UPDATE_STEP = 0.001
  }
}