package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.plugin.MapPlugin

/**
 * Definition of a map plugin provider delegate.
 */
interface MapPluginProviderDelegate {
  /**
   * Get the plugin instance.
   *
   * @param clazz the same class type that was used when instantiating the plugin
   * @return created plugin instance
   */
  fun getPlugin(id: String): MapPlugin?
}