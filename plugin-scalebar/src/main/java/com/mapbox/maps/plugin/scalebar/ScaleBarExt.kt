@file:JvmName("ScaleBarUtils")

package com.mapbox.maps.plugin.scalebar

import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the ScaleBar plugin instance.
 */
val MapPluginProviderDelegate.scalebar: ScaleBarPlugin
  @JvmName("getScaleBar")
  get() = this.getPlugin(Plugin.MAPBOX_SCALEBAR_PLUGIN_ID)!!