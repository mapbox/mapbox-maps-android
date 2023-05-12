package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.Style

/**
 * Definition of map delegate transporter. Provides hooks to all map delegate instances.
 */
interface MapDelegateProvider {

  /**
   * Delegate used to interact with map's camera.
   */
  val mapCameraManagerDelegate: MapCameraManagerDelegate

  /**
   * Delegate used to interact with map's projection methods.
   */
  val mapProjectionDelegate: MapProjectionDelegate

  /**
   * Delegate used to interact with map's transform methods.
   */
  val mapTransformDelegate: MapTransformDelegate

  /**
   * Delegate used to interact with map's attribution.
   */
  val mapAttributionDelegate: MapAttributionDelegate

  /**
   * Delegate used to interact with map's plugins.
   */
  fun getStyle(callback: (Style) -> Unit)

  /**
   * Delegate used to interact with map's plugins.
   */
  val mapPluginProviderDelegate: MapPluginProviderDelegate

  /**
   * Delegate used to feature queries.
   */
  val mapFeatureQueryDelegate: MapFeatureQueryDelegate

  /**
   * Delegate used to manager listeners.
   */
  val mapListenerDelegate: MapListenerDelegate
}