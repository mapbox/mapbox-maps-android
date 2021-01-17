package com.mapbox.maps.plugin

/**
 * Plugin interface invoked when the size of map changes.
 */
interface MapSizePlugin {

  /**
   * Invoked when MapView's width and height have changed.
   * @param width the width of mapView
   * @param height the height of mapView
   */
  fun onSizeChanged(width: Int, height: Int) {
    // optional
  }
}