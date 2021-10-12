@file:JvmName("ViewportUtils")

package com.mapbox.maps.plugin.viewport.experimental

import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the [ViewportPlugin] instance.
 */
val MapPluginProviderDelegate.experimentalViewport: ViewportPlugin
  @JvmName("getViewport")
  get() = this.getPlugin(Plugin.MAPBOX_VIEWPORT_PLUGIN_ID)!!