package com.mapbox.maps.plugin.overlay

import com.mapbox.geojson.Point

/**
 * Interface that should be implemented by users to provide all the coordinates that will shown
 * on MapView without covered by overlays.
 */
fun interface MapOverlayCoordinatesProvider {
  /**
   * Get all the coordinates that will shown on MapView
   * @return return the shown coordinates
   */
  fun getShownCoordinates(): List<Point>
}