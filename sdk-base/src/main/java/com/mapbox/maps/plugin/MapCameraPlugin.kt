package com.mapbox.maps.plugin

import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets

/**
 * Definition for map camera plugins. The map will constantly push current camera position values.
 */
fun interface MapCameraPlugin : MapPlugin {

  /**
   * Called whenever camera position changes.
   *
   * @param center camera center
   * @param zoom zoom
   * @param pitch pitch in degrees
   * @param bearing bearing in degrees
   * @param padding camera padding
   */
  fun onCameraMove(
    center: Point,
    zoom: Double,
    pitch: Double,
    bearing: Double,
    padding: EdgeInsets
  )
}