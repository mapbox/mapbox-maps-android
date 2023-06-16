package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.StyleDataLoadedEventData

/**
 * Definition for listener invoked when the requested style data has been loaded.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use StyleDataLoadedCallback instead.",
  replaceWith = ReplaceWith("StyleDataLoadedCallback")
)
fun interface OnStyleDataLoadedListener {
  /**
   * Invoked when the requested style data has been loaded.
   *
   * @param eventData StyleDataLoadedEventData
   */
  fun onStyleDataLoaded(eventData: StyleDataLoadedEventData)
}