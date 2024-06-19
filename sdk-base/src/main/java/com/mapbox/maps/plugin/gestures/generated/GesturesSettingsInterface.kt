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
  fun updateSettings(block: GesturesSettings.Builder.() -> Unit)

  /**
   * Whether the rotate gesture is enabled. Default value: true.
   */
  var rotateEnabled: Boolean

  /**
   * Whether the pinch to zoom gesture is enabled. Default value: true.
   */
  var pinchToZoomEnabled: Boolean

  /**
   * Whether the single-touch scroll gesture is enabled. Default value: true.
   */
  var scrollEnabled: Boolean

  /**
   * Whether rotation is enabled for the pinch to zoom gesture. Default value: true.
   */
  var simultaneousRotateAndPinchToZoomEnabled: Boolean

  /**
   * Whether the pitch gesture is enabled. Default value: true.
   */
  var pitchEnabled: Boolean

  /**
   * Configures the directions in which the map is allowed to move during a scroll gesture. Default value: "horizontal-and-vertical".
   */
  var scrollMode: ScrollMode

  /**
   * Whether double tapping the map with one touch results in a zoom-in animation. Default value: true.
   */
  var doubleTapToZoomInEnabled: Boolean

  /**
   * Whether single tapping the map with two touches results in a zoom-out animation. Default value: true.
   */
  var doubleTouchToZoomOutEnabled: Boolean

  /**
   * Whether the quick zoom gesture is enabled. Default value: true.
   */
  var quickZoomEnabled: Boolean

  /**
   * By default, gestures rotate and zoom around the center of the gesture. Set this property to rotate and zoom around a fixed point instead.
   */
  var focalPoint: ScreenCoordinate?

  /**
   * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by default. Default value: true.
   */
  var pinchToZoomDecelerationEnabled: Boolean

  /**
   * Whether a deceleration animation following a rotate gesture is enabled. True by default. Default value: true.
   */
  var rotateDecelerationEnabled: Boolean

  /**
   * Whether a deceleration animation following a scroll gesture is enabled. True by default. Default value: true.
   */
  var scrollDecelerationEnabled: Boolean

  /**
   * Whether rotate threshold increases when pinching to zoom. true by default. Default value: true.
   */
  @Deprecated(message = "This property has no effect")
  var increaseRotateThresholdWhenPinchingToZoom: Boolean

  /**
   * Whether pinch to zoom threshold increases when rotating. true by default. Default value: true.
   */
  var increasePinchToZoomThresholdWhenRotating: Boolean

  /**
   * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or double-touch-to-zoom-out gesture. 1.0 by default. Must be positive. Default value: 1.
   */
  var zoomAnimationAmount: Float

  /**
   * Whether pan is enabled for the pinch gesture. Default value: true.
   */
  var pinchScrollEnabled: Boolean
}

// End of generated file.