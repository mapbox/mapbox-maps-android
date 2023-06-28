@file:JvmName("ViewportUtils")

package com.mapbox.maps.plugin.viewport

import androidx.annotation.RestrictTo
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the [ViewportPlugin] instance.
 *
 * [ViewportPlugin] is a high-level and extensible API for driving the map camera. It
 * provides built-in states for following the location puck and showing an overview of
 * a GeoJSON geometry. Transitions between states can be animated with a built-in
 * default transition and via custom transitions.
 */
val MapPluginProviderDelegate.viewport: ViewportPlugin
  @JvmName("getViewport")
  get() = this.getPlugin(Plugin.MAPBOX_VIEWPORT_PLUGIN_ID)!!

/**
 * Static method to create instance of Mapbox viewport plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createViewportPlugin(): ViewportPlugin {
  return ViewportPluginImpl()
}