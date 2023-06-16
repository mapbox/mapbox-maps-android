package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.MapIdleEventData

/**
 * Definition for listener invoked whenever the Map has entered the idle state.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use MapIdleCallback instead.",
  replaceWith = ReplaceWith("MapIdleCallback"),
  level = DeprecationLevel.WARNING
)
fun interface OnMapIdleListener {

  /**
   * Invoked when the Map has entered the idle state. The Map is in the idle state when there are no ongoing transitions
   * and the Map has rendered all available tiles.
   *
   * @param eventData MapIdleEventData
   */
  fun onMapIdle(eventData: MapIdleEventData)
}