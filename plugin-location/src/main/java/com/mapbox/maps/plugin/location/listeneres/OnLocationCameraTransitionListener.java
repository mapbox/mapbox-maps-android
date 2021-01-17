package com.mapbox.maps.plugin.location.listeneres;

import com.mapbox.maps.plugin.location.modes.CameraMode;

/**
 * Callback for {@link CameraMode } transition state.
 *
 * @deprecated Location Plugin is deprecated, use Location Component Plugin instead.
 */
@Deprecated
public interface OnLocationCameraTransitionListener {
  /**
   * Invoked when the camera mode transition animation has been finished.
   *
   * @param cameraMode camera mode change that initiated the transition
   */
  void onLocationCameraTransitionFinished(CameraMode cameraMode);

  /**
   * Invoked when the camera mode transition animation has been canceled.
   * <p>
   * The camera mode is set regardless of the cancellation of the transition animation.
   *
   * @param cameraMode camera mode change that initiated the transition
   */
  void onLocationCameraTransitionCanceled(CameraMode cameraMode);
}
