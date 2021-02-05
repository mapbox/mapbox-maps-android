package com.mapbox.maps.renderer

import com.mapbox.maps.MapView

/**
 * Interface definition for a callback to be invoked when a frame is rendered to the map view.
 *
 * @see [MapView.setOnFpsChangedListener]
 */
fun interface OnFpsChangedListener {
  /**
   * Called for every frame rendered to the map view.
   *
   * @param fps The average number of frames rendered over the last second.
   */
  fun onFpsChanged(fps: Double)
}