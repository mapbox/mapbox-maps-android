// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.graphics.Color
import com.mapbox.maps.plugin.LocationPuck
import com.mapbox.maps.plugin.PresetPuckStyle
/**
 * Shows a location puck on the map.
 */
data class LocationComponentSettings(

  /**
   * Whether the user location is visible on the map.
   */
  var enabled: Boolean = false,

  /**
   * The stale state indicates to the user that the location being displayed on the map hasn't been updated in a specific amount of time. Currently only work for 2D location puck and presets.
   */
  var staleStateEnabled: Boolean = true,

  /**
   * Set the delay before the location puck becomes stale. The timer begins approximately when a new location update comes in and using this defined time, if an update hasn't occured by the end, the location is considered stale. Currently only work for 2D location puck and presets.
   */
  var staleStateTimeout: Long = 30000,

  /**
   * The scale factor of the location icon when the map is zoomed out. Scaling is linear. Currently only work for 2D location puck and presets.
   */
  var minZoomIconScale: Float = 0.6f,

  /**
   * The scale factor of the location icon when the map is zoomed in. Scaling is linear. Currently only work for 2D location puck and presets.
   */
  var maxZoomIconScale: Float = 1f,

  /**
   * Whether the location puck is pulsing on the map. Currently only work for 2D location puck and presets.
   */
  var pulsingEnabled: Boolean = false,

  /**
   * The color of the pulsing circle. Currently only work for 2D location puck and presets.
   */
  var pulsingColor: Int = Color.BLUE,

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
  var locationPuck: LocationPuck? = null,

  /**
   * Defines the preset puck styles bundled with the plugin.
   */
  var presetPuckStyle: PresetPuckStyle = PresetPuckStyle.PRECISE,
)

// End of generated file.