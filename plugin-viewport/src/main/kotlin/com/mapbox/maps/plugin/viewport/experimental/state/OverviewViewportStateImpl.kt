package com.mapbox.maps.plugin.viewport.experimental.state

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.core.animation.doOnEnd
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.viewport.experimental.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.experimental.transition.MapboxViewportTransitionFactory
import java.util.concurrent.*

/**
 * The actual implementation of [OverviewViewportState] that shows the overview of a given geometry.
 */
class OverviewViewportStateImpl(
  mapDelegateProvider: MapDelegateProvider,
  initialOptions: OverviewViewportStateOptions
) : OverviewViewportState {
  private val cameraPlugin = mapDelegateProvider.mapPluginProviderDelegate.camera
  private val cameraDelegate = mapDelegateProvider.mapCameraManagerDelegate
  private val transitionFactory = MapboxViewportTransitionFactory(mapDelegateProvider)
  private val dataSourceUpdateObservers = CopyOnWriteArrayList<ViewportStateDataObserver>()
  private var runningAnimation: AnimatorSet? = null

  private var isOverviewStateRunning = false

  /**
   * Describes the configuration options of the state.
   */
  override var options: OverviewViewportStateOptions = initialOptions
    set(value) {
      field = value
      handleNewData()
    }

  private fun handleNewData() {
    val cameraOptions = evaluateViewportData()
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

  private fun evaluateViewportData(): CameraOptions {
    return cameraDelegate.cameraForGeometry(
      options.geometry,
      options.padding,
      options.bearing,
      options.pitch
    )
  }

  /**
   * Observer the new camera options produced by the [ViewportState], it can be used to get the
   * latest state [CameraOptions] for [ViewportTransition].
   *
   * @param viewportStateDataObserver observer that observe new viewport data.
   * @return a handle that cancels current observation.
   */
  override fun observeDataSource(viewportStateDataObserver: ViewportStateDataObserver): Cancelable {
    dataSourceUpdateObservers.add(viewportStateDataObserver)
    viewportStateDataObserver.onNewData(evaluateViewportData())

    return Cancelable {
      dataSourceUpdateObservers.remove(viewportStateDataObserver)
    }
  }

  /**
   * Start updating the camera for the current [ViewportState].
   *
   * @return a handle that cancels the camera updates.
   */
  override fun startUpdatingCamera(): Cancelable {
    isOverviewStateRunning = true
    return Cancelable {
      isOverviewStateRunning = false
      cancelAnimation()
    }
  }

  /**
   * Stop updating the camera for the current [ViewportState].
   */
  override fun stopUpdatingCamera() {
    isOverviewStateRunning = false
    cancelAnimation()
  }

  private fun cancelAnimation() {
    runningAnimation?.apply {
      cancel()
      childAnimations.forEach {
        cameraPlugin.unregisterAnimators(it as ValueAnimator)
      }
    }
    runningAnimation = null
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
    animatorSet.start()
    runningAnimation = animatorSet
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
      transitionFactory.transitionLinear(cameraOptions, options.frameTransitionMaxDurationMs)
        .apply { doOnEnd { finishAnimation(this) } },
      instant
    )
  }
}