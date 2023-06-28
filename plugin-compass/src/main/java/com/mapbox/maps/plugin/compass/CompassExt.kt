@file:JvmName("CompassUtils")

package com.mapbox.maps.plugin.compass

import androidx.annotation.RestrictTo
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the Compass View plugin instance.
 */
val MapPluginProviderDelegate.compass: CompassPlugin
  @JvmName("getCompass")
  get() = this.getPlugin(Plugin.MAPBOX_COMPASS_PLUGIN_ID)!!

/**
 * Static method to create instance of Mapbox compass plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createCompassPlugin(): CompassPlugin {
  return CompassViewPlugin()
}