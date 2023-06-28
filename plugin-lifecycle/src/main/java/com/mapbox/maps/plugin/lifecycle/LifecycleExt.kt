@file:JvmName("LifecycleUtils")

package com.mapbox.maps.plugin.lifecycle

import androidx.annotation.RestrictTo
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension function for MapView to get the lifecycle plugin instance.
 *
 * @return Lifecycle plugin instance
 */
val MapPluginProviderDelegate.lifecycle: MapboxLifecyclePlugin
  @JvmName("getLifecycle")
  get() = this.getPlugin(Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID)!!

/**
 * Static method to create instance of Mapbox lifecycle plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createLifecyclePlugin(): MapboxLifecyclePlugin {
  return MapboxLifecyclePluginImpl()
}