package com.mapbox.maps.testapp.examples

import android.animation.ValueAnimator
import com.mapbox.maps.plugin.animation.CameraAnimationsLifecycleListener
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorType

private const val NAVIGATION_CAMERA_OWNER = "NAVIGATION_CAMERA_OWNER"
/**
 * Provides a basic support in reacting to map gesture interaction
 * and other animations scheduled outside of the [NavigationCamera] context.
 *
 * Whenever any gesture input is registered or any other transition is started,
 * the [NavigationCamera] will immediately fallback to [NavigationCameraState.IDLE] state
 * to avoid running competing animations.
 *
 * To initialize the handler, register it via
 * [CameraAnimationsPlugin.addCameraAnimationsLifecycleListener]. To deinitialize, call
 * [CameraAnimationsPlugin.removeCameraAnimationsLifecycleListener].
 */
class NavigationBasicGesturesHandler(
  private val listener: () -> Unit
) : CameraAnimationsLifecycleListener {

    /**
     * Called when animator is about to start.
     */
    override fun onAnimatorCancelling(
        type: CameraAnimatorType,
        animator: ValueAnimator,
        owner: String?
    ) {
        // no impl
    }

    /**
     * Called when animator is about to cancel already running animator of same [CameraAnimatorType].
     */
    override fun onAnimatorEnding(
        type: CameraAnimatorType,
        animator: ValueAnimator,
        owner: String?
    ) {
        // no impl
    }

    /**
     * Called when animator is about to end.
     */
    override fun onAnimatorInterrupting(
        type: CameraAnimatorType,
        runningAnimator: ValueAnimator,
        runningAnimatorOwner: String?,
        newAnimator: ValueAnimator,
        newAnimatorOwner: String?
    ) {
        // no impl
    }

    /**
     * Called when [ValueAnimator] is about to cancel.
     */
    override fun onAnimatorStarting(
        type: CameraAnimatorType,
        animator: ValueAnimator,
        owner: String?
    ) {
        if (owner != NAVIGATION_CAMERA_OWNER) {
          listener()
        }
    }
}
