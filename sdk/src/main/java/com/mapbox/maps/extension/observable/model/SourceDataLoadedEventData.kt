package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.plugin.delegates.listeners.eventdata.SourceDataType
import com.mapbox.maps.plugin.delegates.listeners.eventdata.TileID

/**
 *The data class for style data loaded event data in Observer
 */
data class SourceDataLoadedEventData(
  /**
   * The 'id' property defines the source id.
   */
  @SerializedName("id") val id: String,

  /**
   * The 'type' property defines if source's metadata (e.g., TileJSON) or tile has been loaded.
   */
  @SerializedName("type") val type: SourceDataType,

  /**
   * The 'loaded' property will be set to 'true' if all source's data required for Map's visible viewport, are loaded.
   */
  @SerializedName("loaded") val loaded: Boolean?,

  /**
   * The 'tile-id' property defines the tile id if the 'type' field equals 'tile'.
   */
  @SerializedName("tile-id") val tileID: TileID?
)