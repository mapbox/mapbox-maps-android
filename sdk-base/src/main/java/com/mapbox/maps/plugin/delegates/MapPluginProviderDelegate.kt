package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.plugin.MapPlugin

/**
 * Definition of a map plugin provider delegate.
 */
interface MapPluginProviderDelegate {
  /**
   * Get the plugin instance.
   *
   * @param id plugin id
   * @return created plugin instance or null if no plugin is found for given id.
   */
  fun <T : MapPlugin> getPlugin(id: String): T?
}