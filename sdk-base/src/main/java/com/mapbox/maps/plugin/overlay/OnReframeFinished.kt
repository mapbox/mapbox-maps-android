package com.mapbox.maps.plugin.overlay

import com.mapbox.maps.CameraOptions

/**
 * Interface invoked after reframe calculations are finished which provides valid [CameraOptions] object
 */
fun interface OnReframeFinished {
  /**
   * Callback method to return the [CameraOptions] object.
   * @param cameraOptions the [CameraOptions] that refame operation will apply.
   */
  fun onReframeFinished(cameraOptions: CameraOptions?)
}