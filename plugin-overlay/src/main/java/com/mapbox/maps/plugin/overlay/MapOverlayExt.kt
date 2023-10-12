@file:JvmName("MapOverlayUtils")

package com.mapbox.maps.plugin.overlay

import androidx.annotation.RestrictTo
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the map overlay plugin instance.
 *
 * @return Map overlay plugin instance
 */
val MapPluginProviderDelegate.mapboxOverlay: MapOverlayPlugin
  @JvmName("getOverlay")
  get() = this.getPlugin(Plugin.MAPBOX_MAP_OVERLAY_PLUGIN_ID)!!

/**
 * Static method to create instance of Mapbox overlay plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createOverlayPlugin(): MapOverlayPlugin {
  return MapOverlayPluginImpl()
}