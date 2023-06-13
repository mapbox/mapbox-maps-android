package com.mapbox.maps.plugin.viewport.state

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.annotation.VisibleForTesting
import androidx.core.animation.doOnEnd
import com.mapbox.common.Cancelable
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportTransitionFactory
import com.mapbox.maps.threading.AnimationThreadController
import java.util.concurrent.*

/**
 * The actual implementation of [OverviewViewportState] that shows the overview of a given geometry.
 */
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
   * latest state [CameraOptions] for [com.mapbox.maps.plugin.viewport.transition.ViewportTransition].
   *
   * @param viewportStateDataObserver observer that observe new viewport data.
   * @return a handle that cancels current observation.
   */
  override fun observeDataSource(viewportStateDataObserver: ViewportStateDataObserver): Cancelable {
    if (viewportStateDataObserver.onNewData(evaluateViewportData())) {
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
}