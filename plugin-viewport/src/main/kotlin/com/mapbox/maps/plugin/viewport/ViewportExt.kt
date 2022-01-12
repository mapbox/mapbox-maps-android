@file:JvmName("ViewportUtils")

package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the [ViewportPlugin] instance.
 */
@MapboxExperimental
val MapPluginProviderDelegate.viewport: ViewportPlugin
  @JvmName("getViewport")
  get() = this.getPlugin(Plugin.MAPBOX_VIEWPORT_PLUGIN_ID)!!