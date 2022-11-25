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