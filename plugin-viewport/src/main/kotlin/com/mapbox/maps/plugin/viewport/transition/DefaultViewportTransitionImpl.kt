package com.mapbox.maps.plugin.viewport.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.state.ViewportState

/**
 * The implementation of [DefaultViewportTransition] that transitions Viewport from one [ViewportState] to another.
 */
internal class DefaultViewportTransitionImpl(
  delegateProvider: MapDelegateProvider,
  /**
   * Describes the configuration options for the [DefaultViewportTransition].
   */
  override var options: DefaultViewportTransitionOptions,
  private val transitionFactory: MapboxViewportTransitionFactory = MapboxViewportTransitionFactory(
    delegateProvider
  )
) : DefaultViewportTransition {
  private val cameraPlugin = delegateProvider.mapPluginProviderDelegate.camera
  private val cameraDelegate = delegateProvider.mapCameraManagerDelegate
  private var runningAnimation: AnimatorSet? = null

  /**
   * Run the [ViewportTransition] from previous [ViewportState] to the target [ViewportState].
   *
   * The completion block contains a Bool that is true if the transition is cancelled and false
   * if it ran to completion. Implementations must be sure to invoke the completion block with false if
   * the returned Cancelable is invoked prior to completion. the completion block must be invoked
   * on the main queue. Transitions must handle the possibility that the "to" state might fail to
   * provide a target camera in a timely manner or might update the target camera multiple times
   * during the transition (a "moving target").
   *
   * @param to The target [ViewportState]
   * @param completionListener The listener to observe the completion state.
   * @return a handle that can be used to cancel the current [ViewportTransition]
   */
  override fun run(
    to: ViewportState,
    completionListener: CompletionListener
  ): Cancelable {
    var isCancelableCalled = false
    val cancelable = to.observeDataSource { cameraOptions ->
      startAnimation(
        createAnimatorSet(cameraOptions, options.maxDurationMs)
          .apply {
            addListener(
              object : Animator.AnimatorListener {
                private var isCanceled = false
                override fun onAnimationStart(animation: Animator?) {
                  // no-ops
                }

                override fun onAnimationEnd(animation: Animator?) {
                  if (!isCancelableCalled) {
                    completionListener.onComplete(!isCanceled)
                  }
                  finishAnimation(this@apply)
                }

                override fun onAnimationCancel(animation: Animator?) {
                  isCanceled = true
                }

                override fun onAnimationRepeat(animation: Animator?) {
                  // no-ops
                }
              }
            )
          },
        instant = false
      )
      false
    }
    return Cancelable {
      isCancelableCalled = true
      cancelAnimation()
      cancelable.cancel()
    }
  }

  private fun createAnimatorSet(cameraOptions: CameraOptions, maxDurationMs: Long): AnimatorSet {
    val currentZoom = cameraDelegate.cameraState.zoom
    return with(transitionFactory) {
      if (currentZoom < cameraOptions.zoom ?: currentZoom) {
        transitionFromLowZoomToHighZoom(cameraOptions, maxDurationMs)
      } else {
        transitionFromHighZoomToLowZoom(cameraOptions, maxDurationMs)
      }
    }
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
}