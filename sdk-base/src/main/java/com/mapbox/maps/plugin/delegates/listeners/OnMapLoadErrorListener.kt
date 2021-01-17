package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.MapLoadError

/**
 * Definition for listener invoked whenever the map load errors out.
 * See [MapLoadError].
 */
interface OnMapLoadErrorListener {
  /**
   * Invoked whenever the map load errors out
   *
   * @param mapViewLoadError the [MapLoadError]
   * @param msg the error message string
   */
  fun onMapLoadError(mapViewLoadError: MapLoadError, msg: String)
}