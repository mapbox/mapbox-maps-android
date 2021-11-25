// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.ScrollMode

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
   * Whether the rotate gesture is enabled.
   */
  var rotateEnabled: Boolean

  /**
   * Whether the pinch to zoom gesture is enabled.
   */
  var pinchToZoomEnabled: Boolean

  /**
   * Whether the single-touch scroll gesture is enabled.
   */
  var scrollEnabled: Boolean

  /**
   * Whether rotation is enabled for the pinch to zoom gesture.
   */
  var simultaneousRotateAndPinchToZoomEnabled: Boolean

  /**
   * Whether the pitch gesture is enabled.
   */
  var pitchEnabled: Boolean

  /**
   * Configures the directions in which the map is allowed to move during a scroll gesture.
   */
  var scrollMode: ScrollMode

  /**
   * Whether double tapping the map with one touch results in a zoom-in animation.
   */
  var doubleTapToZoomInEnabled: Boolean

  /**
   * Whether single tapping the map with two touches results in a zoom-out animation.
   */
  var doubleTouchToZoomOutEnabled: Boolean

  /**
   * Whether the quick zoom gesture is enabled.
   */
  var quickZoomEnabled: Boolean

  /**
   * By default, gestures rotate and zoom around the center of the gesture. Set this property to rotate and zoom around a fixed point instead.
   */
  var focalPoint: ScreenCoordinate?

  /**
   * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by default.
   */
  var pinchToZoomDecelerationEnabled: Boolean

  /**
   * Whether a deceleration animation following a rotate gesture is enabled. True by default.
   */
  var rotateDecelerationEnabled: Boolean

  /**
   * Whether a deceleration animation following a scroll gesture is enabled. True by default.
   */
  var scrollDecelerationEnabled: Boolean

  /**
   * Whether rotate threshold increases when pinching to zoom. true by default.
   */
  var increaseRotateThresholdWhenPinchingToZoom: Boolean

  /**
   * Whether pinch to zoom threshold increases when rotating. true by default.
   */
  var increasePinchToZoomThresholdWhenRotating: Boolean

  /**
   * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
   */
  var zoomAnimationAmount: Float
}

// End of generated file.