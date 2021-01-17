// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import com.mapbox.maps.ScreenCoordinate

/**
 * Interface that defines the public settings interface for GesturesPlugin.
 */
interface GesturesSettingsInterface {
  /**
   * Get current gestures configuration.
   *
   * @return gestures settings
   */
  fun getSettings(): GesturesSettings

  /**
   * Update gestures configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of GesturesSettings
   */
  fun updateSettings(block: GesturesSettings.() -> Unit)

  /**
   * Whether rotation gestures are enabled.
   */
  var rotateEnabled: Boolean

  /**
   * Whether zoom gestures are enabled.
   */
  var zoomEnabled: Boolean

  /**
   * Whether scroll gestures are enabled.
   */
  var scrollEnabled: Boolean

  /**
   * Whether pitch gestures are enabled.
   */
  var pitchEnabled: Boolean

  /**
   * Whether double tapping the map results in a zoom gesture.
   */
  var doubleTapToZoomEnabled: Boolean

  /**
   * Whether quick zoom gesture is enabled.
   */
  var quickZoomEnabled: Boolean

  /**
   * Whether a gesture executes around a fixed focal point or the center of the gesture.
   */
  var focalPoint: ScreenCoordinate?

  /**
   * Whether scale velocity animations are enabled, true by default.
   */
  var scaleVelocityAnimationEnabled: Boolean

  /**
   * Whether rotate velocity animations are enabled, true by default.
   */
  var rotateVelocityAnimationEnabled: Boolean

  /**
   * Whether fling velocity animations are enabled, true by default.
   */
  var flingVelocityAnimationEnabled: Boolean

  /**
   * Whether rotate threshold increases when scaling. true by default.
   */
  var increaseRotateThresholdWhenScaling: Boolean

  /**
   * Whether rotate is disabled when scaling. true by default.
   */
  var disableRotateWhenScaling: Boolean

  /**
   * Whether scale threshold increases when rotating. true by default.
   */
  var increaseScaleThresholdWhenRotating: Boolean

  /**
   * The rate at which the zoom level increases. 1.0 by default
   */
  var zoomRate: Float

  /**
   * The pixel ratio of the device that the gestures will take in account.
   */
  var pixelRatio: Float
}

// End of generated file.