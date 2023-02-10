// This file is generated.

package com.mapbox.maps.plugin.logo.generated

/**
 * Interface that defines the public settings interface for LogoPlugin.
 */
interface LogoSettingsInterface {
  /**
   * Get current logo configuration.
   *
   * @return logo settings
   */
  fun getSettings(): LogoSettings

  /**
   * Update logo configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of LogoSettings
   */
  fun updateSettings(block: LogoSettings.() -> Unit)

  /**
   * Whether the logo is visible on the map.
   */
  var enabled: Boolean

  /**
   * Defines where the logo is positioned on the map
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
}

// End of generated file.