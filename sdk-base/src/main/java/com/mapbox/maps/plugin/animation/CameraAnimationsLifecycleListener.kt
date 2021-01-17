package com.mapbox.maps.plugin.animation

import android.animation.ValueAnimator

/**
 * Interface responsible to notify about any CameraAnimator lifecycle events registered in [CameraAnimationsPlugin].
 * Those events are:
 *  - animator is about to start
 *  - animator is about to cancel already running animator of same type
 *  - animator is about to end
 *  - animator is about to cancel
 */
interface CameraAnimationsLifecycleListener {

  /**
   * Called when animator is about to start.
   */
  fun onAnimatorStarting(type: CameraAnimatorType, animator: ValueAnimator, owner: String?)

  /**
   * Called when animator is about to cancel already running animator of same [CameraAnimatorType].
   */
  fun onAnimatorInterrupting(
    type: CameraAnimatorType,
    runningAnimator: ValueAnimator,
    runningAnimatorOwner: String?,
    newAnimator: ValueAnimator,
    newAnimatorOwner: String?
  )

  /**
   * Called when animator is about to end.
   */
  fun onAnimatorEnding(type: CameraAnimatorType, animator: ValueAnimator, owner: String?)

  /**
   * Called when [ValueAnimator] is about to cancel.
   */
  fun onAnimatorCancelling(type: CameraAnimatorType, animator: ValueAnimator, owner: String?)
}