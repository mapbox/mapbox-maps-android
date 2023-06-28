@file:JvmName("LogoUtils")

package com.mapbox.maps.plugin.logo

import androidx.annotation.RestrictTo
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the Logo View plugin instance.
 */
val MapPluginProviderDelegate.logo: LogoPlugin
  @JvmName("getLogo")
  get() = this.getPlugin(Plugin.MAPBOX_LOGO_PLUGIN_ID)!!

/**
 * Static method to create instance of Mapbox logo plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createLogoPlugin(): LogoPlugin {
  return LogoViewPlugin()
}