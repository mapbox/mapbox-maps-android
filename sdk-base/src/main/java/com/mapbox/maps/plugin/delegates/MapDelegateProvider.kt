package com.mapbox.maps.plugin.delegates

import androidx.annotation.RestrictTo
import com.mapbox.common.Cancelable
import com.mapbox.maps.IndoorManager
import com.mapbox.maps.MapboxExperimental
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
   * Execute the provided [callback] when the style becomes fully loaded.
   * Will be executed synchronously if the style is loaded at the moment of invocation.
   *
   * If no style is loaded yet, [callback] stays referenced until a style loads or the map is
   * destroyed. Prefer [whenStyleReady] when the caller can be destroyed earlier than the map.
   *
   * Passing the same [callback] instance again while it is still pending does not add a
   * second request, and the callback is invoked once. If the map is destroyed before the style
   * loads, [callback] is never invoked.
   */
  fun getStyle(callback: (MapboxStyleManager) -> Unit)

  /**
   * Execute the provided [callback] when the style becomes fully loaded.
   * Will be executed synchronously if the style is loaded at the moment of invocation.
   *
   * Passing the same [callback] instance again while it is still pending does not add a
   * second request: the callback is invoked once, both calls return the same [Cancelable], and
   * cancelling it removes the request for both. Use separate instances to cancel them independently.
   * If the map is destroyed before the style loads, [callback] is never invoked.
   *
   * @return a [Cancelable] that can be used to cancel this registration if the style
   * never becomes ready. Cancelling after [callback] was invoked has no effect.
   */
  fun whenStyleReady(callback: (MapboxStyleManager) -> Unit): Cancelable

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

  /**
   * Delegate used to use feature state APIs.
   */
  val mapFeatureStateDelegate: MapFeatureStateDelegate

  /**
   * Delegate used to manage interactions (click gestures for example).
   */
  @MapboxExperimental
  val mapInteractionDelegate: MapInteractionDelegate

  /**
   * IndoorManager for controlling indoor map floor display.
   */
  @MapboxExperimental
  @OptIn(com.mapbox.annotation.MapboxExperimental::class)
  val indoorManager: IndoorManager

  /**
   * Current user-set maximum FPS cap, or null if not set. Plugins may read this each frame to
   * throttle gesture-driven animations to match the render loop's frame rate.
   */
  val maximumFps: Int? get() = null
}