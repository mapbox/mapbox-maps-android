package com.mapbox.maps.plugin

/**
 * Definition for map camera plugins. The map will constantly push current camera position values.
 */
fun interface MapCameraPlugin : MapPlugin {

  /**
   * Called whenever camera position changes.
   *
   * @param lat latitude
   * @param lon longitude
   * @param zoom zoom
   * @param pitch pitch in degrees
   * @param bearing bearing in degrees
   * @param padding padding ordered as [left, top, right, bottom]
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