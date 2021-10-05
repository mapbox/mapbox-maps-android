package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.model.MapLoadErrorType

/**
 * Definition for listener invoked whenever the map load errors out.
 * See [MapLoadErrorType].
 */
interface OnMapLoadErrorListener {
  /**
   * Invoked whenever the map load errors out
   *
   * @param eventData MapLoadingErrorEventData
   */
  fun onMapLoadError(eventData: MapLoadingErrorEventData)
}