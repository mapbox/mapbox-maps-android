package com.mapbox.maps.plugin.location.listeneres;

import com.mapbox.maps.plugin.location.modes.CameraMode;

/**
 * Listener that gets invoked when camera tracking state changes.
 *
 * @deprecated Location Plugin is deprecated, use Location Component Plugin instead.
 */
@Deprecated
public interface OnCameraTrackingChangedListener {
  /**
   * Invoked whenever camera tracking is broken.
   * This callback gets invoked just after {@link #onCameraTrackingChanged(CameraMode)}, if needed.
   */
  void onCameraTrackingDismissed();

  /**
   * Invoked on every {@link CameraMode} change.
   *
   * @param currentMode current active {@link CameraMode}.
   */
  void onCameraTrackingChanged(CameraMode currentMode);
}
