package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets

/**
 * Definition of a camera delegate. Any invocation will interact with the map's actual camera.
 */
interface MapCameraDelegate {

  /**
   * Get current latitude.
   * @return latitude
   */
  fun getLat(): Double

  /**
   * Get current longitude.
   * @return longitude
   */
  fun getLon(): Double

  /**
   * Get current zoom.
   * @return zoom
   */
  fun getZoom(): Double

  /**
   * Get current pitch.
   * @return pitch
   */
  fun getPitch(): Double

  /**
   * Get current bearing.
   * @return bearing
   */
  fun getBearing(): Double

  /**
   * Get current padding.
   * @return padding
   */
  fun getPadding(): Array<Double>

  /**
   * Set camera's bearing.
   */
  fun setBearing(bearing: Double)

  /**
   * Get camera state.
   * @return camera state
   */
  fun getCameraState(): CameraState
}