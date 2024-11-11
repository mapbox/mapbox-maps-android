package com.mapbox.maps.renderer

import com.mapbox.maps.MapView

/**
 * Interface definition for a callback to be invoked when a frame is rendered to the map view.
 * Important note: [onFpsChanged] is called on non-UI thread.
 *
 * @see [MapView.setOnFpsChangedListener]
 */
fun interface OnFpsChangedListener {
  /**
   * Called on non-UI thread for every frame rendered to the map view.
   *
   * @param fps The average number of frames rendered over the last second.
   */
  @RenderThread
  fun onFpsChanged(fps: Double)
}