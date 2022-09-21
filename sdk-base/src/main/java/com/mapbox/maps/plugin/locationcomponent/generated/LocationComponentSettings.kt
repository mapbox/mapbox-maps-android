// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.graphics.Color
import com.mapbox.maps.plugin.LocationPuck
/**
 * Shows a location puck on the map.
 */
data class LocationComponentSettings @JvmOverloads constructor(

  /**
   * Whether the user location is visible on the map.
   */
  var enabled: Boolean = false,

  /**
   * Whether the location puck is pulsing on the map. Works for 2D location puck only.
   */
  var pulsingEnabled: Boolean = false,

  /**
   * The color of the pulsing circle. Works for 2D location puck only.
   */
  var pulsingColor: Int = Color.parseColor("#4A90E2"),

  /**
   * The maximum radius of the pulsing circle. Works for 2D location puck only. Note: Setting [pulsingMaxRadius] to LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the pulsing circle's maximum radius to follow location accuracy circle. This property is specified in pixels.
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
   * Defines what the customised look of the location puck. Note that direct changes to the puck variables won't have any effect, a new puck needs to be set every time.
   */
  var locationPuck: LocationPuck,
)

// End of generated file.