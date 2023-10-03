package com.mapbox.maps.plugin.viewport.state

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportTransitionFactory
import com.mapbox.maps.threading.AnimationThreadController
import java.util.concurrent.*

/**
 * The actual implementation of [FollowPuckViewportState] that follows the location component's puck position.
 *
 * Note: [LocationComponentPlugin] should be enabled to use this viewport state.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class FollowPuckViewportStateImpl(
  mapDelegateProvider: MapDelegateProvider,
  initialOptions: FollowPuckViewportStateOptions,
  private val transitionFactory: MapboxViewportTransitionFactory = MapboxViewportTransitionFactory(
    mapDelegateProvider
  )
) : FollowPuckViewportState {
  private val cleanUpAnimatorListener: Animator.AnimatorListener =
    object : Animator.AnimatorListener {
      override fun onAnimationStart(animation: Animator) {}

      override fun onAnimationEnd(animation: Animator) {
        unregisterRunningAnimationAnimators()
      }

      override fun onAnimationCancel(animation: Animator) {}

      override fun onAnimationRepeat(animation: Animator) {}
    }

  private val cameraPlugin = mapDelegateProvider.mapPluginProviderDelegate.camera
  private val locationComponent = mapDelegateProvider.mapPluginProviderDelegate.location
  private val dataSourceUpdateObservers = CopyOnWriteArraySet<ViewportStateDataObserver>()

  private var lastLocation: Point? = null
  private var lastBearing: Double? = null

  private var runningAnimation: AnimatorSet? = null

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var isFollowingStateRunning = false
  private var isObservingLocationUpdates = false

  private val indicatorPositionChangedListener = OnIndicatorPositionChangedListener { point ->
    lastLocation = point
    notifyLatestViewportData()
  }

  private val indicatorBearingChangedListener = OnIndicatorBearingChangedListener { bearing ->
    if (options.bearing == FollowPuckViewportStateBearing.SyncWithLocationPuck) {
      lastBearing = bearing
      notifyLatestViewportData()
    }
  }

  private fun notifyLatestViewportData() {
    if (shouldNotifyLatestViewportData()) {
      val viewportData = evaluateViewportData()
      if (isFollowingStateRunning) {
        updateFrame(viewportData)
      }
      dataSourceUpdateObservers.forEach {
        notifyViewportStateDataObserver(it, viewportData)
      }
    }
  }

  private fun shouldNotifyLatestViewportData() = lastLocation != null

  private fun notifyViewportStateDataObserver(
    observer: ViewportStateDataObserver,
    cameraOptions: CameraOptions
  ) {
    if (!observer.onNewData(cameraOptions)) {
      dataSourceUpdateObservers.remove(observer)
    }
  }

  private fun evaluateViewportData(): CameraOptions = with(CameraOptions.Builder()) {
    center(lastLocation)
    when (val bearingOptions = options.bearing) {
      is FollowPuckViewportStateBearing.Constant -> bearing(bearingOptions.bearing)
      FollowPuckViewportStateBearing.SyncWithLocationPuck -> lastBearing?.let { bearing(it) }
      else -> {} // don't touch camera bearing
    }
    zoom(options.zoom)
    pitch(options.pitch)
    padding(options.padding)
  }.build()

  private fun addIndicatorListenerIfNeeded() {
    if (!isObservingLocationUpdates) {
      locationComponent.addOnIndicatorPositionChangedListener(indicatorPositionChangedListener)
      locationComponent.addOnIndicatorBearingChangedListener(indicatorBearingChangedListener)
      isObservingLocationUpdates = true
    }
  }

  private fun removeIndicatorListenerIfNeeded() {
    if (isObservingLocationUpdates && dataSourceUpdateObservers.isEmpty() && !isFollowingStateRunning) {
      locationComponent.removeOnIndicatorPositionChangedListener(indicatorPositionChangedListener)
      locationComponent.removeOnIndicatorBearingChangedListener(indicatorBearingChangedListener)
      isObservingLocationUpdates = false
      // when unsubscribed from the location updates, we don't want to keep an outdated location, so
      // when user transition to the FollowPuckViewportState, there wouldn't be any unintentional jump.
      lastBearing = null
      lastLocation = null
    }
  }

  /**
   * Describes the configuration options of the state.
   */
  override var options: FollowPuckViewportStateOptions = initialOptions
    set(value) {
      field = value
      notifyLatestViewportData()
    }

  /**
   * Observer the new camera options produced by the [ViewportState], it can be used to get the
   * latest state [CameraOptions] for [com.mapbox.maps.plugin.viewport.transition.ViewportTransition].
   *
   * @param viewportStateDataObserver observer that observe new viewport data.
   * @return a handle that cancels current observation.
   */
  override fun observeDataSource(viewportStateDataObserver: ViewportStateDataObserver): Cancelable {
    checkLocationComponentEnablement()
    addIndicatorListenerIfNeeded()
    dataSourceUpdateObservers.add(viewportStateDataObserver)
    if (shouldNotifyLatestViewportData()) {
      notifyViewportStateDataObserver(viewportStateDataObserver, evaluateViewportData())
    }
    return Cancelable {
      dataSourceUpdateObservers.remove(viewportStateDataObserver)
      removeIndicatorListenerIfNeeded()
    }
  }

  private fun checkLocationComponentEnablement() {
    if (!locationComponent.enabled) {
      logW(
        TAG,
        "Location component is required to be enabled to use FollowPuckViewportState, otherwise there would be no FollowPuckViewportState updates or ViewportTransition updates towards the FollowPuckViewportState."
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

  @OptIn(MapboxExperimental::class)
  private fun cancelAnimation() {
    AnimationThreadController.postOnAnimatorThread {
      runningAnimation?.apply {
        // cancel will take care of calling unregisterRunningAnimationAnimators through
        // the cleanUpAnimatorListener
        cancel()
      }
    }
  }

  @OptIn(MapboxExperimental::class)
  private fun startAnimation(animatorSet: AnimatorSet) {
    cancelAnimation()
    animatorSet.childAnimations.forEach {
      cameraPlugin.registerAnimators(it as ValueAnimator)
    }
    animatorSet.duration = 0
    AnimationThreadController.postOnAnimatorThread {
      animatorSet.start()
      runningAnimation = animatorSet
    }
  }

  private fun unregisterRunningAnimationAnimators() {
    runningAnimation?.childAnimations?.forEach {
      cameraPlugin.unregisterAnimators(it as ValueAnimator)
    }
    runningAnimation = null
  }

  private fun updateFrame(cameraOptions: CameraOptions) {
    startAnimation(
      // Duration is set to 0 because we want instant animation
      transitionFactory.transitionLinear(cameraOptions, 0)
        .apply { addListener(cleanUpAnimatorListener) }
    )
  }

  private companion object {
    const val TAG = "FollowPuckViewportStateImpl"
  }
}