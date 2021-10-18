package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.viewport.transition.ViewportCameraTransitionOptions

class ViewportCamera {

  companion object {
    /**
     * Constant used to recognize the owner of transitions initiated by the [ViewportCamera].
     *
     * @see CameraAnimator.owner
     */
    internal const val VIEWPORT_CAMERA_OWNER = "VIEWPORT_CAMERA_OWNER"

    internal val DEFAULT_STATE_TRANSITION_OPT =
      ViewportCameraTransitionOptions.build { maxDuration(3500L) }
    internal val DEFAULT_FRAME_TRANSITION_OPT =
      ViewportCameraTransitionOptions.build { maxDuration(1000L) }
  }
}