// This file is generated.
// This class is annotated with `DataCompat`.
// Therefore, it is used to auto-generate `GesturesSettings`.

package com.mapbox.maps.plugin.gestures.generated

import android.os.Parcelable
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.ScrollMode
import com.tobrun.datacompat.annotation.DataCompat
import com.tobrun.datacompat.annotation.Default
import kotlinx.parcelize.Parcelize

/**
 * Gesture configuration allows to control the user touch interaction.
 */
@Parcelize
@DataCompat
private data class GesturesSettingsData(

  /**
   * Whether the rotate gesture is enabled.
   */
  @Default("true")
  var rotateEnabled: Boolean,

  /**
   * Whether the pinch to zoom gesture is enabled.
   */
  @Default("true")
  var pinchToZoomEnabled: Boolean,

  /**
   * Whether the single-touch scroll gesture is enabled.
   */
  @Default("true")
  var scrollEnabled: Boolean,

  /**
   * Whether rotation is enabled for the pinch to zoom gesture.
   */
  @Default("true")
  var simultaneousRotateAndPinchToZoomEnabled: Boolean,

  /**
   * Whether the pitch gesture is enabled.
   */
  @Default("true")
  var pitchEnabled: Boolean,

  /**
   * Configures the directions in which the map is allowed to move during a scroll gesture.
   */
  @Default("ScrollMode.HORIZONTAL_AND_VERTICAL")
  var scrollMode: ScrollMode,

  /**
   * Whether double tapping the map with one touch results in a zoom-in animation.
   */
  @Default("true")
  var doubleTapToZoomInEnabled: Boolean,

  /**
   * Whether single tapping the map with two touches results in a zoom-out animation.
   */
  @Default("true")
  var doubleTouchToZoomOutEnabled: Boolean,

  /**
   * Whether the quick zoom gesture is enabled.
   */
  @Default("true")
  var quickZoomEnabled: Boolean,

  /**
   * By default, gestures rotate and zoom around the center of the gesture. Set this property to rotate and zoom around a fixed point instead.
   */
  var focalPoint: ScreenCoordinate? = null,

  /**
   * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by default.
   */
  @Default("true")
  var pinchToZoomDecelerationEnabled: Boolean,

  /**
   * Whether a deceleration animation following a rotate gesture is enabled. True by default.
   */
  @Default("true")
  var rotateDecelerationEnabled: Boolean,

  /**
   * Whether a deceleration animation following a scroll gesture is enabled. True by default.
   */
  @Default("true")
  var scrollDecelerationEnabled: Boolean,

  /**
   * Whether rotate threshold increases when pinching to zoom. true by default.
   */
  @Default("true")
  var increaseRotateThresholdWhenPinchingToZoom: Boolean,

  /**
   * Whether pinch to zoom threshold increases when rotating. true by default.
   */
  @Default("true")
  var increasePinchToZoomThresholdWhenRotating: Boolean,

  /**
   * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
   */
  @Default("1f")
  var zoomAnimationAmount: Float,

  /**
   * Whether pan is enabled for the pinch gesture.
   */
  @Default("true")
  var pinchScrollEnabled: Boolean,
) : Parcelable

// End of generated file.