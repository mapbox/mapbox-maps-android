package com.mapbox.maps.listener

/**
 * Definition for listener invoked whenever the map has become idle.
 */
interface OnDidBecomeIdleListener {
  /**
   * Called when the map is in an idle state.
   */
  fun onIdle()
}