package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.MapLoadedEventData

/**
 * Definition for listener invoked when the map loading finishes.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use MapLoadedCallback instead.",
  replaceWith = ReplaceWith("MapLoadedCallback")
)
fun interface OnMapLoadedListener {

  /**
   * Invoked when the Map's style has been fully loaded, and the Map has rendered all visible tiles.
   *
   * @param eventData MapLoadedEventData
   */
  fun onMapLoaded(eventData: MapLoadedEventData)
}