@file:JvmName("LogoUtils")

package com.mapbox.maps.plugin.logo

import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the Logo View plugin instance.
 */
val MapPluginProviderDelegate.logo: LogoPlugin
  @JvmName("getLogo")
  get() = this.getPlugin(Plugin.MAPBOX_LOGO_PLUGIN_ID)!!