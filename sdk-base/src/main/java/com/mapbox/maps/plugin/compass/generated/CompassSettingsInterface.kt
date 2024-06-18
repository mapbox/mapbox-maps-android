// This file is generated.

package com.mapbox.maps.plugin.compass.generated

import com.mapbox.maps.ImageHolder

/**
 * Interface that defines the public settings interface for CompassPlugin.
 */
interface CompassSettingsInterface {
  /**
   * Get current compass configuration.
   *
   * @return compass settings
   */
  fun getSettings(): CompassSettings

  /**
   * Update compass configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of CompassSettings
   */
  fun updateSettings(block: CompassSettings.Builder.() -> Unit)

  /**
   * Whether the compass is visible on the map. Default value: true.
   */
  var enabled: Boolean

  /**
   * Defines where the compass is positioned on the map Default value: "top-right".
   */
  var position: Int

  /**
   * Defines the margin to the left that the compass icon honors. Default value: 4.
   */
  var marginLeft: Float

  /**
   * Defines the margin to the top that the compass icon honors. Default value: 4.
   */
  var marginTop: Float

  /**
   * Defines the margin to the right that the compass icon honors. Default value: 4.
   */
  var marginRight: Float

  /**
   * Defines the margin to the bottom that the compass icon honors. Default value: 4.
   */
  var marginBottom: Float

  /**
   * The alpha channel value of the compass image Default value: 1.
   */
  var opacity: Float

  /**
   * The clockwise rotation value in degrees of the compass. Default value: 0.
   */
  var rotation: Float

  /**
   * Whether the compass is displayed. Default value: true.
   */
  var visibility: Boolean

  /**
   * Whether the compass fades out to invisible when facing north direction. Default value: true.
   */
  var fadeWhenFacingNorth: Boolean

  /**
   * Whether the compass can be clicked and click events can be registered. Default value: true.
   */
  var clickable: Boolean

  /**
   * The compass image, the visual representation of the compass.
   */
  var image: ImageHolder?
}

// End of generated file.