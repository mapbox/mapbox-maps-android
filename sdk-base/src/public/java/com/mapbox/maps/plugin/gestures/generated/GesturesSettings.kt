// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.ScrollMode
/**
 * Gesture configuration allows to control the user touch interaction.
 */
data class GesturesSettings @JvmOverloads constructor(

  /**
   * Whether the rotate gesture is enabled.
   */
  var rotateEnabled: Boolean = true,

  /**
   * Whether the pinch to zoom gesture is enabled.
   */
  var pinchToZoomEnabled: Boolean = true,

  /**
   * Whether the single-touch scroll gesture is enabled.
   */
  var scrollEnabled: Boolean = true,

  /**
   * Whether rotation is enabled for the pinch to zoom gesture.
   */
  var simultaneousRotateAndPinchToZoomEnabled: Boolean = true,

  /**
   * Whether the pitch gesture is enabled.
   */
  var pitchEnabled: Boolean = true,

  /**
   * Configures the directions in which the map is allowed to move during a scroll gesture.
   */
  var scrollMode: ScrollMode = ScrollMode.HORIZONTAL_AND_VERTICAL,

  /**
   * Whether double tapping the map with one touch results in a zoom-in animation.
   */
  var doubleTapToZoomInEnabled: Boolean = true,

  /**
   * Whether single tapping the map with two touches results in a zoom-out animation.
   */
  var doubleTouchToZoomOutEnabled: Boolean = true,

  /**
   * Whether the quick zoom gesture is enabled.
   */
  var quickZoomEnabled: Boolean = true,

  /**
   * By default, gestures rotate and zoom around the center of the gesture. Set this property to rotate and zoom around a fixed point instead.
   */
  var focalPoint: ScreenCoordinate? = null,

  /**
   * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by default.
   */
  var pinchToZoomDecelerationEnabled: Boolean = true,

  /**
   * Whether a deceleration animation following a rotate gesture is enabled. True by default.
   */
  var rotateDecelerationEnabled: Boolean = true,

  /**
   * Whether a deceleration animation following a scroll gesture is enabled. True by default.
   */
  var scrollDecelerationEnabled: Boolean = true,

  /**
   * Whether rotate threshold increases when pinching to zoom. true by default.
   */
  var increaseRotateThresholdWhenPinchingToZoom: Boolean = true,

  /**
   * Whether pinch to zoom threshold increases when rotating. true by default.
   */
  var increasePinchToZoomThresholdWhenRotating: Boolean = true,

  /**
   * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
   */
  var zoomAnimationAmount: Float = 1f,

  /**
   * Whether pan is enabled for the pinch gesture.
   */
  var pinchScrollEnabled: Boolean = true,
)

// End of generated file.