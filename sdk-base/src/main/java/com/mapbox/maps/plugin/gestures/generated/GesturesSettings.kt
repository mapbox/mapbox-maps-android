// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.PanScrollMode
/**
 * Gesture configuration allows to control the user touch interaction.
 */
data class GesturesSettings @JvmOverloads constructor(

  /**
   * Whether rotation gestures are enabled.
   */
  var rotateEnabled: Boolean = true,

  /**
   * Whether zoom gestures are enabled.
   */
  var zoomEnabled: Boolean = true,

  /**
   * Whether scroll gestures are enabled.
   */
  var scrollEnabled: Boolean = true,

  /**
   * Whether pitch gestures are enabled.
   */
  var pitchEnabled: Boolean = true,

  /**
   * Whether the user is restricted in which direction the map is scrolled.
   */
  var panScrollMode: PanScrollMode = PanScrollMode.HORIZONTAL_AND_VERTICAL,

  /**
   * Whether double tapping the map results in a zoom gesture.
   */
  var doubleTapToZoomEnabled: Boolean = true,

  /**
   * Whether quick zoom gesture is enabled.
   */
  var quickZoomEnabled: Boolean = true,

  /**
   * Whether a gesture executes around a fixed focal point or the center of the gesture.
   */
  var focalPoint: ScreenCoordinate? = null,

  /**
   * Whether scale velocity animations are enabled, true by default.
   */
  var scaleVelocityAnimationEnabled: Boolean = true,

  /**
   * Whether rotate velocity animations are enabled, true by default.
   */
  var rotateVelocityAnimationEnabled: Boolean = true,

  /**
   * Whether fling velocity animations are enabled, true by default.
   */
  var flingVelocityAnimationEnabled: Boolean = true,

  /**
   * Whether rotate threshold increases when scaling. true by default.
   */
  var increaseRotateThresholdWhenScaling: Boolean = true,

  /**
   * Whether rotate is disabled when scaling. true by default.
   */
  var disableRotateWhenScaling: Boolean = true,

  /**
   * Whether scale threshold increases when rotating. true by default.
   */
  var increaseScaleThresholdWhenRotating: Boolean = true,

  /**
   * The rate at which the zoom level increases. 1.0 by default
   */
  var zoomRate: Float = 1f,

  /**
   * The pixel ratio of the device that the gestures will take in account.
   */
  var pixelRatio: Float = 1f,
)

// End of generated file.