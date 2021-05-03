package com.mapbox.maps.plugin

/**
 * Definition for map camera plugins. The map will constantly push current camera position values.
 */
fun interface MapCameraPlugin : MapPlugin {

  /**
   * Called whenever camera position changes.
   */
  fun onCameraMove(
    lat: Double,
    lon: Double,
    zoom: Double,
    pitch: Double,
    bearing: Double,
    padding: Array<Double>
  )
}