package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.plugin.delegates.listeners.eventdata.SourceDataType
import com.mapbox.maps.plugin.delegates.listeners.eventdata.TileID

/**
 * Definition for listener invoked when the requested style data has been loaded.
 */
fun interface OnSourceDataLoadedListener {

  /**
   * Invoked when the requested source data has been loaded.
   *
   * @param id defines the source id.
   * @param type defines if source's metadata (e.g., TileJSON) or tile has been loaded.
   * @param loaded will be set to 'true' if all source's data required for Map's visible viewport, are loaded.
   * @param tileID defines the tile id if the 'type' field equals 'tile'.
   */
  fun onSourceDataLoaded(id: String, type: SourceDataType, loaded: Boolean?, tileID: TileID?)
}