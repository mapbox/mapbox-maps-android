package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.MapLoadedEventData

/**
 * Definition for listener invoked when the map loading finishes.
 */
fun interface OnMapLoadedListener {

  /**
   * Invoked when the Map's style has been fully loaded, and the Map has rendered all visible tiles.
   *
   * @param eventData MapLoadedEventData
   */
  fun onMapLoaded(eventData: MapLoadedEventData)
}