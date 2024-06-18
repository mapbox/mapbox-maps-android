// This file is generated.

package com.mapbox.maps.plugin.scalebar.generated

/**
 * Interface that defines the public settings interface for ScaleBarPlugin.
 */
interface ScaleBarSettingsInterface {
  /**
   * Get current scalebar configuration.
   *
   * @return scalebar settings
   */
  fun getSettings(): ScaleBarSettings

  /**
   * Update scalebar configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of ScaleBarSettings
   */
  fun updateSettings(block: ScaleBarSettings.Builder.() -> Unit)

  /**
   * Whether the scale is visible on the map. Default value: true.
   */
  var enabled: Boolean

  /**
   * Defines where the scale bar is positioned on the map Default value: "top-left".
   */
  var position: Int

  /**
   * Defines the margin to the left that the scale bar honors. Default value: 4.
   */
  var marginLeft: Float

  /**
   * Defines the margin to the top that the scale bar honors. Default value: 4.
   */
  var marginTop: Float

  /**
   * Defines the margin to the right that the scale bar honors. Default value: 4.
   */
  var marginRight: Float

  /**
   * Defines the margin to the bottom that the scale bar honors. Default value: 4.
   */
  var marginBottom: Float

  /**
   * Defines text color of the scale bar. Default value: "black".
   */
  var textColor: Int

  /**
   * Defines primary color of the scale bar. Default value: "black".
   */
  var primaryColor: Int

  /**
   * Defines secondary color of the scale bar. Default value: "white".
   */
  var secondaryColor: Int

  /**
   * Defines width of the border for the scale bar. Default value: 2.
   */
  var borderWidth: Float

  /**
   * Defines height of the scale bar. Default value: 2.
   */
  var height: Float

  /**
   * Defines margin of the text bar of the scale bar. Default value: 8.
   */
  var textBarMargin: Float

  /**
   * Defines text border width of the scale bar. Default value: 2.
   */
  var textBorderWidth: Float

  /**
   * Defines text size of the scale bar. Default value: 8.
   */
  var textSize: Float

  /**
   * Whether the scale bar is using metric unit. True if the scale bar is using metric system, false if the scale bar is using imperial units. Default value: true.
   */
  var isMetricUnits: Boolean

  /**
   * Configures minimum refresh interval, in millisecond, default is 15. Default value: 15.
   */
  var refreshInterval: Long

  /**
   * Configures whether to show the text border or not, default is true. Default value: true.
   */
  var showTextBorder: Boolean

  /**
   * configures ratio of scale bar max width compared with MapView width, default is 0.5. Default value: 0.5.
   */
  var ratio: Float

  /**
   * If set to True scale bar will be triggering onDraw depending on [ScaleBarSettings.refreshInterval] even if actual data did not change. If set to False scale bar will redraw only on demand. Defaults to False and should not be changed explicitly in most cases. Could be set to True to produce correct GPU frame metrics when running gfxinfo command. Default value: false.
   */
  var useContinuousRendering: Boolean
}

// End of generated file.