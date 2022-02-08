// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

/**
 * Interface that defines the public settings interface for LocationComponentPlugin.
 */
interface LocationComponentSettingsInterface2 : LocationComponentSettingsInterface {
  /**
   * Get current locationcomponent configuration.
   *
   * @return locationcomponent settings
   */
  fun getSettings2(): LocationComponentSettings2

  /**
   * Update locationcomponent configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of LocationComponentSettings
   */
  fun updateSettings2(block: LocationComponentSettings2.() -> Unit)

  /**
   * Whether show accuracy ring with location puck. Works for 2D location puck only.
   */
  var showAccuracyRing: Boolean

  /**
   * The color of the accuracy ring. Works for 2D location puck only.
   */
  var accuracyRingColor: Int

  /**
   * The color of the accuracy ring border. Works for 2D location puck only.
   */
  var accuracyRingBorderColor: Int
}

// End of generated file.