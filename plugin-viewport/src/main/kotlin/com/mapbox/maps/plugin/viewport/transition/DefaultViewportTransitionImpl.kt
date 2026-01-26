package com.mapbox.maps.plugin.viewport.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.common.Cancelable
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.threading.AnimationThreadController
import com.mapbox.maps.util.MathUtils

/**
 * The implementation of [DefaultViewportTransition] that transitions Viewport from one [ViewportState] to another.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
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
  private var cachedAnchor: ScreenCoordinate? = null

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
    var animatorSet: AnimatorSet? = null
    val startCamera = cameraDelegate.cameraState
    val completedChildAnimators = mutableSetOf<CameraAnimatorType>()
    var keepObserving = true
    val cancelable = to.observeDataSource { cameraOptions ->
      animatorSet?.updateTargetCameraOptions(
        completedChildAnimators,
        startCamera,
        cameraOptions
      ) ?: kotlin.run {
        val initialAnimatorSet = createAnimatorSet(cameraOptions, options.maxDurationMs)
          .apply {
            addListener(
              object : Animator.AnimatorListener {
                private var isCanceled = false
                override fun onAnimationStart(animation: Animator) {
                  // no-ops
                }

                override fun onAnimationEnd(animation: Animator) {
                  keepObserving = false
                  if (!isCancelableCalled) {
                    completionListener.onComplete(!isCanceled)
                  }
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
          }
        initialAnimatorSet.childAnimations.forEach {
          val cameraAnimator = it as CameraAnimator<*>
          cameraAnimator.addListener(
            object : Animator.AnimatorListener {
              private var isCanceled = false
              override fun onAnimationStart(p0: Animator) {
                // no-ops
              }

              override fun onAnimationEnd(p0: Animator) {
                if (!isCanceled) {
                  completedChildAnimators.add(cameraAnimator.type)
                }
              }

              override fun onAnimationCancel(p0: Animator) {
                isCanceled = true
              }

              override fun onAnimationRepeat(p0: Animator) {
                // no-ops
              }
            }
          )
        }
        startAnimation(initialAnimatorSet, instant = false)
        animatorSet = initialAnimatorSet
      }
      keepObserving
    }
    return Cancelable {
      isCancelableCalled = true
      keepObserving = false
      cancelAnimation()
      cancelable.cancel()
    }
  }

  private fun createAnimatorSet(cameraOptions: CameraOptions, maxDurationMs: Long): AnimatorSet {
    val currentZoom = cameraDelegate.cameraState.zoom
    return with(transitionFactory) {
      if (currentZoom < (cameraOptions.zoom ?: currentZoom)) {
        transitionFromLowZoomToHighZoom(cameraOptions, maxDurationMs)
      } else {
        transitionFromHighZoomToLowZoom(cameraOptions, maxDurationMs)
      }
    }
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
    // cache the camera plugin's last anchor point, and reset it once transition is ended
    cachedAnchor = cameraPlugin.anchor
    // For the viewport transition, the anchor should be set to null to avoid unexpected center shift
    // caused by the zoom/bearing animators.
    cameraPlugin.anchor = null
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
    cameraPlugin.anchor = cachedAnchor
  }

  private fun AnimatorSet.updateTargetCameraOptions(
    completedChildAnimators: Set<CameraAnimatorType>,
    startCamera: CameraState,
    targetCamera: CameraOptions
  ) {
    childAnimations.forEach {
      val cameraAnimator = it as CameraAnimator<*>
      when (cameraAnimator.type) {
        CameraAnimatorType.ANCHOR -> return@forEach
        CameraAnimatorType.BEARING ->
          if (completedChildAnimators.contains(CameraAnimatorType.BEARING)) {
            cameraDelegate.setCamera(cameraOptions { bearing(targetCamera.bearing) })
          } else {
            // calculate shortest rotation for bearing
            targetCamera.bearing?.let { targetBearing ->
              val targets = MathUtils.prepareOptimalBearingPath(
                doubleArrayOf(startCamera.bearing, targetBearing)
              ).toTypedArray()
              cameraAnimator.setObjectValues(*targets)
            }
          }
        CameraAnimatorType.CENTER ->
          if (completedChildAnimators.contains(CameraAnimatorType.CENTER)) {
            cameraDelegate.setCamera(cameraOptions { center(targetCamera.center) })
          } else {
            cameraAnimator.setObjectValues(startCamera.center, targetCamera.center)
          }
        CameraAnimatorType.ZOOM ->
          if (completedChildAnimators.contains(CameraAnimatorType.ZOOM)) {
            cameraDelegate.setCamera(cameraOptions { zoom(targetCamera.zoom) })
          } else {
            cameraAnimator.setObjectValues(startCamera.zoom, targetCamera.zoom)
          }
        CameraAnimatorType.PITCH ->
          if (completedChildAnimators.contains(CameraAnimatorType.PITCH)) {
            cameraDelegate.setCamera(cameraOptions { pitch(targetCamera.pitch) })
          } else {
            cameraAnimator.setObjectValues(startCamera.pitch, targetCamera.pitch)
          }
        CameraAnimatorType.PADDING ->
          if (completedChildAnimators.contains(CameraAnimatorType.PADDING)) {
            cameraDelegate.setCamera(cameraOptions { padding(targetCamera.padding) })
          } else {
            cameraAnimator.setObjectValues(startCamera.padding, targetCamera.padding)
          }
      }
    }
  }
}