@file:JvmName("IndoorUtils")

package com.mapbox.maps.plugin.indoorselector

import androidx.annotation.RestrictTo
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the indoor selector plugin instance.
 */
@MapboxExperimental
val MapPluginProviderDelegate.indoorSelector: IndoorSelectorPlugin
  @JvmName("getIndoorSelector")
  get() = this.getPlugin(Plugin.MAPBOX_INDOOR_SELECTOR_PLUGIN_ID)!!

/**
 * Static method to create instance of Mapbox indoor selector plugin.
 * @suppress
 */
@MapboxExperimental
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createIndoorSelectorPlugin(): IndoorSelectorPlugin {
  return IndoorSelectorPluginImpl()
}