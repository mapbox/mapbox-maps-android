// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.graphics.Color
import com.mapbox.maps.plugin.LocationPuck
/**
 * Shows a location puck on the map.
 */
data class LocationComponentSettings(

  /**
   * Whether the user location is visible on the map.
   */
  var enabled: Boolean = false,

  /**
   * Whether the location puck is pulsing on the map. Currently only work for 2D location puck and presets.
   */
  var pulsingEnabled: Boolean = false,

  /**
   * The color of the pulsing circle. Currently only work for 2D location puck and presets.
   */
  var pulsingColor: Int = Color.parseColor("#4A90E2"),

  /**
   * The maximum radius of the pulsing circle. Currently only work for 2D location puck and presets.
   */
  var pulsingMaxRadius: Float = 10f,

  /**
   * Sets the id of the layer that's added above to when placing the component on the map.
   */
  var layerAbove: String? = null,

  /**
   * Sets the id of the layer that's added below to when placing the component on the map.
   */
  var layerBelow: String? = null,

  /**
   * Defines what the customised look of the location puck. Defining the puck will overwrite the puck style preset.
   */
  var locationPuck: LocationPuck,
)

// End of generated file.