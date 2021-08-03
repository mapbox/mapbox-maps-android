@file:JvmName("MapOverlayUtils")

package com.mapbox.maps.plugin.overlay

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the map overlay plugin instance.
 *
 * @return Map overlay plugin instance
 */
@MapboxExperimental
val MapPluginProviderDelegate.mapboxOverlay: MapOverlayPlugin
  @JvmName("getOverlay")
  get() = this.getPlugin(Plugin.MAPBOX_MAP_OVERLAY_PLUGIN_ID)!!