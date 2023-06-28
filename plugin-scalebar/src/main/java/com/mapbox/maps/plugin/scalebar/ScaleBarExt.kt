@file:JvmName("ScaleBarUtils")

package com.mapbox.maps.plugin.scalebar

import androidx.annotation.RestrictTo
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the ScaleBar plugin instance.
 */
val MapPluginProviderDelegate.scalebar: ScaleBarPlugin
  @JvmName("getScaleBar")
  get() = this.getPlugin(Plugin.MAPBOX_SCALEBAR_PLUGIN_ID)!!

/**
 * Static method to create instance of Mapbox scalebar plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createScaleBarPlugin(): ScaleBarPlugin {
  return ScaleBarPluginImpl()
}