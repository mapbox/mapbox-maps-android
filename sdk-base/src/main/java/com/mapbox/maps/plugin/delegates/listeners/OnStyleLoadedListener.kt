package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.StyleLoadedEventData

/**
 * Definition for listener invoked when the style is fully loaded.
 */
fun interface OnStyleLoadedListener {

  /**
   * Invoked when the requested style has been fully loaded, including the style, specified sprite and sources' metadata.
   *
   * @param eventData StyleLoadedEventData
   */
  fun onStyleLoaded(eventData: StyleLoadedEventData)
}