package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.StyleDataLoadedEventData

/**
 * Definition for listener invoked when the requested style data has been loaded.
 */
fun interface OnStyleDataLoadedListener {
  /**
   * Invoked when the requested style data has been loaded.
   *
   * @param eventData StyleDataLoadedEventData
   */
  fun onStyleDataLoaded(eventData: StyleDataLoadedEventData)
}