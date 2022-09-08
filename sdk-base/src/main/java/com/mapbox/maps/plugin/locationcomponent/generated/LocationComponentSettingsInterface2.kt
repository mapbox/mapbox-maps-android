// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import com.mapbox.maps.plugin.PuckBearingSource

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

  /**
   * Whether the puck rotates to track the bearing source.
   */
  var puckBearingEnabled: Boolean

  /**
   * The enum controls how the puck is oriented
   */
  var puckBearingSource: PuckBearingSource
}

// End of generated file.