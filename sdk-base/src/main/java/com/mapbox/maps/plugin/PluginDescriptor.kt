package com.mapbox.maps.plugin

data class PluginDescriptor(
  // unique id
  val pluginId: String,
  // instance is needed for custom plugins, for Mapbox ones null is passed
  val pluginInstance: MapPlugin? = null
)