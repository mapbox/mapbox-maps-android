package com.mapbox.maps.plugin.viewport.transition

import android.animation.AnimatorSet
import com.mapbox.maps.CameraOptions

/**
 * Helper interface to provide navigation camera state transitions.
 */
interface ViewportCameraStateTransition {
  /**
   * Transition the camera to following state.
   */
  fun transitionToFollowing(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet

  /**
   * Transition the camera to overview state.
   */
  fun transitionToOverview(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet

  /**
   * Transition that keeps following.
   */
  fun updateFrameForFollowing(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet

  /**
   * Transition that keeps showing overview.
   */
  fun updateFrameForOverview(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet
}
