package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.plugin.delegates.listeners.eventdata.MapLoadErrorType

/**
 * Definition for listener invoked whenever the map load errors out.
 * See [MapLoadErrorType].
 */
interface OnMapLoadErrorListener {
  /**
   * Invoked whenever the map load errors out
   *
   * @param mapLoadErrorType the [MapLoadErrorType]
   * @param message the error message string
   */
  fun onMapLoadError(mapLoadErrorType: MapLoadErrorType, message: String)
}