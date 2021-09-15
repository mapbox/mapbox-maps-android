package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked whenever the Map has entered the idle state.
 */
fun interface OnMapIdleListener {

  /**
   * Invoked when the Map has entered the idle state. The Map is in the idle state when there are no ongoing transitions
   * and the Map has rendered all available tiles.
   */
  fun onMapIdle()
}