package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.MapChange

/**
 * Definition for listener invoked whenever the map state changes.
 * See [MapChange].
 */
fun interface OnMapChangedListener {
  /**
   * Invoked whenever the map state changes.
   *
   * @param mapChange the [MapChange] change
   */
  fun onMapChange(mapChange: MapChange)
}