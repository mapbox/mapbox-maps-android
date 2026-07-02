package com.mapbox.maps.plugin

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
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
  @Deprecated(
    "Use onCameraMove(cameraState) overload to get the latest functionality.",
    replaceWith = ReplaceWith("onCameraMove(cameraState)")
  )
  fun onCameraMove(
    center: Point,
    zoom: Double,
    pitch: Double,
    bearing: Double,
    padding: EdgeInsets
  )

  /**
   * Called whenever camera position changes.
   *
   * @param cameraState camera state containing position information.
   */
  @Suppress("DEPRECATION")
  fun onCameraMove(cameraState: CameraState) = onCameraMove(
    cameraState.center,
    cameraState.zoom,
    cameraState.pitch,
    cameraState.bearing,
    cameraState.padding
  )
}