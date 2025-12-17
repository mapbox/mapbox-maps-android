// This file is generated.

package com.mapbox.maps.plugin.indoorselector.generated

import com.mapbox.maps.MapboxExperimental

/**
 * Interface that defines the public settings interface for IndoorSelectorPlugin.
 */
@MapboxExperimental
interface IndoorSelectorSettingsInterface {
  /**
   * Get current indoorselector configuration.
   *
   * @return indoorselector settings
   */
  fun getSettings(): IndoorSelectorSettings

  /**
   * Update indoorselector configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of IndoorSelectorSettings
   */
  fun updateSettings(block: IndoorSelectorSettings.Builder.() -> Unit)

  /**
   * Whether the indoor selector is visible on the map. Default value: true.
   */
  var enabled: Boolean

  /**
   * Defines where the indoor selector is positioned on the map. Default value: "top-right".
   */
  var position: Int

  /**
   * Defines the margin to the left that the indoor selector honors. Default value: 8.
   */
  var marginLeft: Float

  /**
   * Defines the margin to the top that the indoor selector honors. Default value: 60.
   */
  var marginTop: Float

  /**
   * Defines the margin to the right that the indoor selector honors. Default value: 8.
   */
  var marginRight: Float

  /**
   * Defines the margin to the bottom that the indoor selector honors. Default value: 8.
   */
  var marginBottom: Float
}

// End of generated file.