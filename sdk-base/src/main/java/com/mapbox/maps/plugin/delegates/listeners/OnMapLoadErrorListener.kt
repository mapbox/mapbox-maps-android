package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.plugin.delegates.listeners.eventdata.MapLoadError

/**
 * Definition for listener invoked whenever the map load errors out.
 * See [MapLoadError].
 */
interface OnMapLoadErrorListener {
  /**
   * Invoked whenever the map load errors out
   *
   * @param mapLoadError the [MapLoadError]
   * @param description the error message string
   */
  fun onMapLoadError(mapLoadError: MapLoadError, description: String)
}