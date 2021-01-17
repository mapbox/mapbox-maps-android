package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.CameraChange
import com.mapbox.maps.CameraChangeMode

/**
 * Definition for listener invoked whenever the camera position changes.
 * See [CameraChange] and [CameraChangeMode].
 */
fun interface OnCameraChangeListener {

  /**
   * Invoked whenever camera position changes.
   *
   * @param changeEvent the [CameraChange] event
   * @param mode the [CameraChangeMode] mode
   */
  fun onCameraChange(changeEvent: CameraChange, mode: CameraChangeMode)
}