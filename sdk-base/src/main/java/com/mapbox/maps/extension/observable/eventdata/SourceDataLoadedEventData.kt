package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.extension.observable.model.SourceDataType
import com.mapbox.maps.extension.observable.model.TileID

/**
 * The data class for source-data-loaded event data in Observer
 */
data class SourceDataLoadedEventData(
  /**
   * Representing timestamp taken at the time of an event creation, in microseconds, since the epoch.
   */
  @SerializedName("begin") val begin: Long,
  /**
   * For an interval events, an optional `end` property will be present that represents timestamp taken at the time
   * of an event completion.
   */
  @SerializedName("end") val end: Long?,
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