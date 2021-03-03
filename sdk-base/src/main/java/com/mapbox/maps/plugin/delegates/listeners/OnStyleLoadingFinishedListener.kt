package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked when the style loading finishes.
 */
fun interface OnStyleLoadingFinishedListener {

  /**
   * Invoked when the requested style has been loaded, not including the style specified sprite sheet and sources'
   * descriptions. This event may be useful when application needs to modify style layers and add or remove sources
   * before style is fully loaded.
   */
  fun onStyleLoadingFinished()
}