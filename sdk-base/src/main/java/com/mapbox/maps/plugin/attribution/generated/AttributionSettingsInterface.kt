// This file is generated.

package com.mapbox.maps.plugin.attribution.generated

/**
 * Interface that defines the public settings interface for AttributionPlugin.
 */
interface AttributionSettingsInterface {
  /**
   * Get current attribution configuration.
   *
   * @return attribution settings
   */
  fun getSettings(): AttributionSettings

  /**
   * Update attribution configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of AttributionSettings
   */
  fun updateSettings(block: AttributionSettings.Builder.() -> Unit)

  /**
   * Whether the attribution icon is visible on the map. Default value: true.
   */
  var enabled: Boolean

  /**
   * Defines text color of the attribution icon. Default value: "#FF1E8CAB".
   */
  var iconColor: Int

  /**
   * Defines where the attribution icon is positioned on the map Default value: "bottom-left".
   */
  var position: Int

  /**
   * Defines the margin to the left that the attribution icon honors. Default value: 92.
   */
  var marginLeft: Float

  /**
   * Defines the margin to the top that the attribution icon honors. Default value: 4.
   */
  var marginTop: Float

  /**
   * Defines the margin to the right that the attribution icon honors. Default value: 4.
   */
  var marginRight: Float

  /**
   * Defines the margin to the bottom that the attribution icon honors. Default value: 4.
   */
  var marginBottom: Float

  /**
   * Whether the attribution can be clicked and click events can be registered. Default value: true.
   */
  var clickable: Boolean
}

// End of generated file.