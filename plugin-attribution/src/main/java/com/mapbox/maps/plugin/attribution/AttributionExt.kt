@file:JvmName("AttributionUtils")

package com.mapbox.maps.plugin.attribution

import androidx.annotation.RestrictTo
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the Compass View plugin instance.
 */
val MapPluginProviderDelegate.attribution: AttributionPlugin
  @JvmName("getAttribution")
  get() = this.getPlugin(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID)!!

/**
 * Static method to create instance of Mapbox attribution plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createAttributionPlugin(): AttributionPlugin {
  return AttributionPluginImpl()
}