package com.mapbox.maps.plugin.animation

/**
 * Interface describing cancelable animation set for high-level functions
 * like flyTo, easeTo etc.
 */
fun interface Cancelable {

  /**
   * Cancel running animation set.
   */
  fun cancel()
}