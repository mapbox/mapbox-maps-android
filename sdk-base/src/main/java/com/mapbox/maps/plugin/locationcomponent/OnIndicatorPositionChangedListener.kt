package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.geojson.Point

/**
 * Listener that gets invoked when indicator position changes.
 */
fun interface OnIndicatorPositionChangedListener {
  /**
   * This method is called on each position change of the location indicator, including each animation frame.
   *
   * @param point indicator's position
   */
  fun onIndicatorPositionChanged(point: Point)
}