// This file is generated.
// This class is annotated with `DataCompat`.
// Therefore, it is used to auto-generate `ScaleBarSettings`.

package com.mapbox.maps.plugin.scalebar.generated

import android.os.Parcelable
import com.tobrun.datacompat.annotation.DataCompat
import com.tobrun.datacompat.annotation.Default
import kotlinx.parcelize.Parcelize

/**
 * Shows the scale bar on the map.
 */
@Parcelize
@DataCompat(
    importsForDefaults = [
        "android.graphics.Color",
        "android.view.Gravity",
    ]
)
private data class ScaleBarSettingsData(

  /**
   * Whether the scale is visible on the map.
   */
  @Default("true")
  var enabled: Boolean,

  /**
   * Defines where the scale bar is positioned on the map
   */
  @Default("Gravity.TOP or Gravity.START")
  var position: Int,

  /**
   * Defines the margin to the left that the scale bar honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginLeft: Float,

  /**
   * Defines the margin to the top that the scale bar honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginTop: Float,

  /**
   * Defines the margin to the right that the scale bar honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginRight: Float,

  /**
   * Defines the margin to the bottom that the scale bar honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginBottom: Float,

  /**
   * Defines text color of the scale bar.
   */
  @Default("Color.BLACK")
  var textColor: Int,

  /**
   * Defines primary color of the scale bar.
   */
  @Default("Color.BLACK")
  var primaryColor: Int,

  /**
   * Defines secondary color of the scale bar.
   */
  @Default("Color.WHITE")
  var secondaryColor: Int,

  /**
   * Defines width of the border for the scale bar. This property is specified in pixels.
   */
  @Default("2f")
  var borderWidth: Float,

  /**
   * Defines height of the scale bar. This property is specified in pixels.
   */
  @Default("2f")
  var height: Float,

  /**
   * Defines margin of the text bar of the scale bar. This property is specified in pixels.
   */
  @Default("8f")
  var textBarMargin: Float,

  /**
   * Defines text border width of the scale bar. This property is specified in pixels.
   */
  @Default("2f")
  var textBorderWidth: Float,

  /**
   * Defines text size of the scale bar. This property is specified in pixels.
   */
  @Default("8f")
  var textSize: Float,

  /**
   * Whether the scale bar is using metric unit. True if the scale bar is using metric system, false if the scale bar is using imperial units.
   */
  @Default("true")
  var isMetricUnits: Boolean,

  /**
   * Configures minimum refresh interval, in millisecond, default is 15.
   */
  @Default("15")
  var refreshInterval: Long,

  /**
   * Configures whether to show the text border or not, default is true.
   */
  @Default("true")
  var showTextBorder: Boolean,

  /**
   * configures ratio of scale bar max width compared with MapView width, default is 0.5.
   */
  @Default("0.5f")
  var ratio: Float,

  /**
   * If set to True scale bar will be triggering onDraw depending on [ScaleBarSettings.refreshInterval] even if actual data did not change. If set to False scale bar will redraw only on demand. Defaults to False and should not be changed explicitly in most cases. Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
   */
  @Default("false")
  var useContinuousRendering: Boolean,
) : Parcelable

// End of generated file.