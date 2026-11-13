@file:JvmName("ConsolidatedAttributionUtils")

package com.mapbox.maps.plugin.consolidatedattribution

import androidx.annotation.RestrictTo
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the consolidated attribution plugin instance.
 */
@MapboxExperimental
val MapPluginProviderDelegate.consolidatedAttribution: ConsolidatedAttributionPlugin
  @JvmName("getConsolidatedAttribution")
  get() = this.getPlugin(Plugin.MAPBOX_CONSOLIDATED_ATTRIBUTION_PLUGIN_ID)!!

/**
 * Static method to create instance of the Mapbox consolidated attribution plugin.
 * @suppress
 */
@MapboxExperimental
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createConsolidatedAttributionPlugin(): ConsolidatedAttributionPlugin {
  return ConsolidatedAttributionPluginImpl()
}