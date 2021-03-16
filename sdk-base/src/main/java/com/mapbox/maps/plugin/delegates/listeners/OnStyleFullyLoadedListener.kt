package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked when the style is fully loaded.
 */
fun interface OnStyleFullyLoadedListener {

  /**
   * Invoked when the requested style has been fully loaded, including the style specified sprite and sources.
   */
  fun onStyleFullyLoaded()
}