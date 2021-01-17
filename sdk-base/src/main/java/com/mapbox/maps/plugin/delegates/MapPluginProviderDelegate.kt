package com.mapbox.maps.plugin.delegates

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
  fun <T> getPlugin(clazz: Class<T>): T?

  /**
   * Get the plugin instance from name.
   *
   * @param className
   * @return created plugin instance
   */
  fun <T> getPlugin(className: String): T?
}