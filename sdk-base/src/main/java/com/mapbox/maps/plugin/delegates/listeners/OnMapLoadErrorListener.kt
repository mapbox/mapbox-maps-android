package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.plugin.delegates.listeners.eventdata.MapLoadErrorType
import com.mapbox.maps.plugin.delegates.listeners.eventdata.TileID

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
   * @param sourceId optional, in case of `source` or `tile` loading errors, `source-id` will contain the id of the source failing.
   * @param tileId optional, in case of `tile` loading errors, `tile-id` will contain the id of the tile
   */
  fun onMapLoadError(
    mapLoadErrorType: MapLoadErrorType,
    message: String,
    sourceId: String?,
    tileId: TileID?
  )
}