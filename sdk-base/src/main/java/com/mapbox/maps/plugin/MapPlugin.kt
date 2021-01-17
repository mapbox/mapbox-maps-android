package com.mapbox.maps.plugin

import com.mapbox.maps.plugin.delegates.MapDelegateProvider

/**
 * Parent definition of all Map plugins.
 */
interface MapPlugin {
  /**
   * Called when the plugin is first added to the map.
   */
  fun initialize() {
    // optional
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  fun cleanup() {
    // optional
  }

  /**
   * Provides all map delegate instances.
   */
  fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    // optional
  }
}