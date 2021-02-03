package com.mapbox.maps.plugin.delegates.extensions

import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.gestures.GesturesPlugin

/**
 * Definition of the map plugin extensions delegate.
 * Makes possible to call plugin functions directly from MapboxMap.
 */
interface MapPluginExtensionsDelegate {

  /**
   * Get an instance of [CameraAnimationsPlugin].
   * In most cases must not be called directly.
   */
  fun getCameraAnimationPlugin(): CameraAnimationsPlugin?

  /**
   * Get an instance of [GesturesPlugin].
   * In most cases must not be called directly.
   */
  fun getGesturesPlugin(): GesturesPlugin?
}