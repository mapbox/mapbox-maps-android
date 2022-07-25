package com.mapbox.maps.plugin.locationcomponent

/**
 * Callback to receive compass calibration events
 */
fun interface LocationCompassCalibrationListener {
  /**
   * Callback's invoked when compass needs to be calibrated.
   */
  fun onCompassCalibrationNeeded()
}