package com.mapbox.maps.testapp.utils

import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.locationcomponent.LocationComponentInitOptions
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin2
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPluginImpl

public fun MapView.createLocationComponent(locationComponentInitOptions: LocationComponentInitOptions): LocationComponentPlugin2 {
  val locationComponent = LocationComponentPluginImpl(locationComponentInitOptions)
  val locationComponentPlugin = Plugin.Custom(
    locationComponentInitOptions.hashCode().toString(),
    locationComponent
  )
  this.createPlugin(locationComponentPlugin)
  return locationComponent
}

object LocationComponentUtils {
  private var custom2DLayerIdCount = 0
  private var custom3DLayerIdCount = 0
  private var custom3DSourceIdCount = 0

  fun getNextLocationComponentOptions() = LocationComponentInitOptions {
    puck2DLayerId = "custom_location_component_2d_layer_$custom2DLayerIdCount"
    puck3DLayerId = "custom_location_component_3d_layer_$custom3DLayerIdCount"
    puck3DSourceId = "custom_location_component_3d_source_$custom3DSourceIdCount"
    custom2DLayerIdCount++
    custom3DLayerIdCount++
    custom3DSourceIdCount++
  }
}