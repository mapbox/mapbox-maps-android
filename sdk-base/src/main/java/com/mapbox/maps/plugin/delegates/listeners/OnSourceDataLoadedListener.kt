package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.SourceDataLoadedEventData

/**
 * Definition for listener invoked when the requested source data has been loaded.
 */
fun interface OnSourceDataLoadedListener {

  /**
   * Invoked when the requested source data has been loaded.
   *
   * @param eventData SourceDataLoadedEventData
   */
  fun onSourceDataLoaded(eventData: SourceDataLoadedEventData)
}