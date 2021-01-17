package com.mapbox.maps.plugin.location

import com.mapbox.geojson.Point

/**
 * Listener that gets invoked when indicator position changes.
 */
@Deprecated("Location Plugin is deprecated, use Location Component Plugin instead.")
fun interface OnIndicatorPositionChangedListener {
  /**
   * This method is called on each position change of the location indicator, including each animation frame.
   *
   * @param point indicator's position
   */
  fun onIndicatorPositionChanged(point: Point)
}