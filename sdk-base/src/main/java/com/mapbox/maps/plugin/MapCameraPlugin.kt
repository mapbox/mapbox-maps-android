package com.mapbox.maps.plugin

import androidx.annotation.AnyThread

/**
 * Definition for map camera plugins. The map will constantly push current camera position values.
 */
fun interface MapCameraPlugin : MapPlugin {

  /**
   * Called whenever camera position changes.
   * Could be invoked from any thread when map starts rendering.
   */
  @AnyThread
  fun onCameraMove(
    lat: Double,
    lon: Double,
    zoom: Double,
    pitch: Double,
    bearing: Double,
    padding: Array<Double>
  )
}