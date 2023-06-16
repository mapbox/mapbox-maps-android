package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.SourceDataLoadedEventData

/**
 * Definition for listener invoked when the requested source data has been loaded.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use SourceDataLoadedCallback instead.",
  replaceWith = ReplaceWith("SourceDataLoadedCallback")
)
fun interface OnSourceDataLoadedListener {

  /**
   * Invoked when the requested source data has been loaded.
   *
   * @param eventData SourceDataLoadedEventData
   */
  fun onSourceDataLoaded(eventData: SourceDataLoadedEventData)
}