// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import com.mapbox.maps.plugin.LocationPuck

/**
 * Interface that defines the public settings interface for LocationComponentPlugin.
 */
interface LocationComponentSettingsInterface {
  /**
   * Get current locationcomponent configuration.
   *
   * @return locationcomponent settings
   */
  fun getSettings(): LocationComponentSettings

  /**
   * Update locationcomponent configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of LocationComponentSettings
   */
  fun updateSettings(block: LocationComponentSettings.() -> Unit)

  /**
   * Whether the user location is visible on the map.
   */
  var enabled: Boolean

  /**
   * Whether the location puck is pulsing on the map. Works for 2D location puck only.
   */
  var pulsingEnabled: Boolean

  /**
   * The color of the pulsing circle. Works for 2D location puck only.
   */
  var pulsingColor: Int

  /**
   * The maximum radius of the pulsing circle. Works for 2D location puck only.
   */
  var pulsingMaxRadius: Float

  /**
   * Sets the id of the layer that's added above to when placing the component on the map.
   */
  var layerAbove: String?

  /**
   * Sets the id of the layer that's added below to when placing the component on the map.
   */
  var layerBelow: String?

  /**
   * Defines what the customised look of the location puck.
   */
  var locationPuck: LocationPuck
}

// End of generated file.