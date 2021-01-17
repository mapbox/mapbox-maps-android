// This file is generated.

package com.mapbox.maps.plugin.compass.generated

import android.graphics.drawable.Drawable

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
  fun updateSettings(block: CompassSettings.() -> Unit)

  /**
   * Whether the compass is visible on the map.
   */
  var enabled: Boolean

  /**
   * Defines where the compass is positioned on the map
   */
  var position: Int

  /**
   * Defines the margin to the left that the compass icon honors.
   */
  var marginLeft: Float

  /**
   * Defines the margin to the top that the compass icon honors.
   */
  var marginTop: Float

  /**
   * Defines the margin to the right that the compass icon honors.
   */
  var marginRight: Float

  /**
   * Defines the margin to the bottom that the compass icon honors.
   */
  var marginBottom: Float

  /**
   * The alpha channel value of the compass image
   */
  var opacity: Float

  /**
   * The clockwise rotation value in degrees of the compass.
   */
  var rotation: Float

  /**
   * Whether the compass is displayed.
   */
  var visibility: Boolean

  /**
   * Whether the compass fades out to invisible when facing north direction.
   */
  var fadeWhenFacingNorth: Boolean

  /**
   * Whether the compass can be clicked and click events can be registered.
   */
  var clickable: Boolean

  /**
   * The compass image, the visual representation of the compass.
   */
  var image: Drawable?
}

// End of generated file.