package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.gestures.GesturesPlugin

/**
 * Definition of the map plugin extensions delegate.
 * Makes possible to call plugin functions directly from MapboxMap.
 */
interface MapPluginExtensionsDelegate {

  /**
   * Call extension function on [CameraAnimationsPlugin].
   * In most cases should not be called directly.
   */
  fun cameraAnimationsPlugin(function: (CameraAnimationsPlugin.() -> Any?)): Any?

  /**
   * Call extension function on [GesturesPlugin].
   * In most cases should not be called directly.
   */
  fun gesturesPlugin(function: (GesturesPlugin.() -> Any?)): Any?
}