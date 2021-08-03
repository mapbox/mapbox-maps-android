@file:JvmName("LocationComponentUtils")

package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val to get the LocationComponentPlugin instance.
 */
val MapPluginProviderDelegate.location: LocationComponentPlugin
  @JvmName("getLocationComponent")
  get() = this.getPlugin(Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID)!!