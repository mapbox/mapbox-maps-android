// This file is generated.

package com.mapbox.maps.plugin.scalebar.generated

import android.graphics.Color
import android.view.Gravity
/**
 * Shows the scale bar on the map.
 */
data class ScaleBarSettings(

  /**
   * Whether the scale is visible on the map.
   */
  var enabled: Boolean = true,

  /**
   * Defines where the scale bar is positioned on the map
   */
  var position: Int = Gravity.TOP or Gravity.START,

  /**
   * Defines the margin to the left that the scale bar honors.
   */
  var marginLeft: Float = 4f,

  /**
   * Defines the margin to the top that the scale bar honors.
   */
  var marginTop: Float = 4f,

  /**
   * Defines the margin to the right that the scale bar honors.
   */
  var marginRight: Float = 4f,

  /**
   * Defines the margin to the bottom that the scale bar honors.
   */
  var marginBottom: Float = 4f,

  /**
   * Defines text color of the scale bar.
   */
  var textColor: Int = Color.BLACK,

  /**
   * Defines primary color of the scale bar.
   */
  var primaryColor: Int = Color.BLACK,

  /**
   * Defines secondary color of the scale bar.
   */
  var secondaryColor: Int = Color.WHITE,

  /**
   * Defines width of the border for the scale bar.
   */
  var borderWidth: Float = 2f,

  /**
   * Defines height of the scale bar.
   */
  var height: Float = 2f,

  /**
   * Defines margin of the text bar of the scale bar.
   */
  var textBarMargin: Float = 8f,

  /**
   * Defines text border width of the scale bar.
   */
  var textBorderWidth: Float = 2f,

  /**
   * Defines text size of the scale bar.
   */
  var textSize: Float = 8f,

  /**
   * Whether the scale bar is using metric unit. True if the scale bar is using metric system, false if the scale bar is using imperial units.
   */
  var isMetricUnits: Boolean = true,

  /**
   * Configures minimum refresh interval, in millisecond, default is 15.
   */
  var refreshInterval: Long = 15,

  /**
   * Configures whether to show the text border or not, default is true.
   */
  var showTextBorder: Boolean = true,

  /**
   * configures ratio of scale bar max width compared with MapView width, default is 0.5.
   */
  var ratio: Float = 0.5f,
)

// End of generated file.