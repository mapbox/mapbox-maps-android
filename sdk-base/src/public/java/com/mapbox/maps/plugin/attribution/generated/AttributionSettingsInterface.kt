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
  fun updateSettings(block: AttributionSettings.() -> Unit)

  /**
   * Whether the attribution icon is visible on the map.
   */
  var enabled: Boolean

  /**
   * Defines text color of the attribution icon.
   */
  var iconColor: Int

  /**
   * Defines where the attribution icon is positioned on the map
   */
  var position: Int

  /**
   * Defines the margin to the left that the attribution icon honors.
   */
  var marginLeft: Float

  /**
   * Defines the margin to the top that the attribution icon honors.
   */
  var marginTop: Float

  /**
   * Defines the margin to the right that the attribution icon honors.
   */
  var marginRight: Float

  /**
   * Defines the margin to the bottom that the attribution icon honors.
   */
  var marginBottom: Float

  /**
   * Whether the attribution can be clicked and click events can be registered.
   */
  var clickable: Boolean
}

// End of generated file.