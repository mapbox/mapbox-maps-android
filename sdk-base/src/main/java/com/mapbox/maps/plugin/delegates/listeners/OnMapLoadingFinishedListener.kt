package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked when the map loading finishes.
 */
fun interface OnMapLoadingFinishedListener {

  /**
   * Invoked when the Map's style has been fully loaded, and the Map has rendered all visible tiles.
   */
  fun onMapLoadingFinished()
}