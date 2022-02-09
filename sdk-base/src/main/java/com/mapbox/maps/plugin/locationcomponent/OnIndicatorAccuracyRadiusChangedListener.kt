package com.mapbox.maps.plugin.locationcomponent

/**
 * Listener that gets invoked when indicator accuracy radius changes.
 */
fun interface OnIndicatorAccuracyRadiusChangedListener {
  /**
   * This method is called on each accuracy radius change of the location indicator, including each animation frame.
   *
   * @param radius indicator's accuracy radius
   */
  fun onIndicatorAccuracyRadiusChanged(radius: Double)
}