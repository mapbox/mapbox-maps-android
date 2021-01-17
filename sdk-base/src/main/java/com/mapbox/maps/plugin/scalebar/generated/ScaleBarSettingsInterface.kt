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
  fun updateSettings(block: ScaleBarSettings.() -> Unit)

  /**
   * Whether the scale is visible on the map.
   */
  var enabled: Boolean

  /**
   * Defines where the scale bar is positioned on the map
   */
  var position: Int

  /**
   * Defines the margin to the left that the scale bar honors.
   */
  var marginLeft: Float

  /**
   * Defines the margin to the top that the scale bar honors.
   */
  var marginTop: Float

  /**
   * Defines the margin to the right that the scale bar honors.
   */
  var marginRight: Float

  /**
   * Defines the margin to the bottom that the scale bar honors.
   */
  var marginBottom: Float

  /**
   * Defines text color of the scale bar.
   */
  var textColor: Int

  /**
   * Defines primary color of the scale bar.
   */
  var primaryColor: Int

  /**
   * Defines secondary color of the scale bar.
   */
  var secondaryColor: Int

  /**
   * Defines width of the border for the scale bar.
   */
  var borderWidth: Float

  /**
   * Defines height of the scale bar.
   */
  var height: Float

  /**
   * Defines margin of the text bar of the scale bar.
   */
  var textBarMargin: Float

  /**
   * Defines text border width of the scale bar.
   */
  var textBorderWidth: Float

  /**
   * Defines text size of the scale bar.
   */
  var textSize: Float

  /**
   * Whether the scale bar is using metric unit. True if the scale bar is using metric system, false if the scale bar is using imperial units.
   */
  var isMetricUnits: Boolean

  /**
   * Configures minimum refresh interval, in millisecond, default is 15.
   */
  var refreshInterval: Long

  /**
   * Configures whether to show the text border or not, default is true.
   */
  var showTextBorder: Boolean

  /**
   * configures ratio of scale bar max width compared with MapView width, default is 0.5.
   */
  var ratio: Float
}

// End of generated file.