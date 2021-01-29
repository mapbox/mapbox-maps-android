package com.mapbox.maps.plugin.locationcomponent

/**
 * Listener that gets invoked when indicator bearing changes.
 */
fun interface OnIndicatorBearingChangedListener {
  /**
   * This method is called on each bearing change of the location indicator, including each animation frame.
   *
   * @param bearing indicator's bearing
   */
  fun onIndicatorBearingChanged(bearing: Double)
}