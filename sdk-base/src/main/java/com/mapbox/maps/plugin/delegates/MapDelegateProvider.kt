package com.mapbox.maps.plugin.delegates

import androidx.annotation.RestrictTo
import com.mapbox.maps.MapboxStyleManager

/**
 * Definition of map delegate transporter. Provides hooks to all map delegate instances.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
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
   * Delegate used to interact with map's style.
   */
  fun getStyle(callback: (MapboxStyleManager) -> Unit)

  /**
   * Delegate used to interact with map's style manager without waiting for style loading events.
   */
  val mapStyleManagerDelegate: MapboxStyleManager

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