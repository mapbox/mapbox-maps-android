package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.extension.observable.model.MapLoadErrorType
import com.mapbox.maps.extension.observable.model.TileID

/**
 * The data class for map-loading-error event data in Observer
 */
data class MapLoadingErrorEventData(
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
   * Defines what resource could not be loaded.
   */
  @SerializedName("type") val type: MapLoadErrorType,
  /**
   * The descriptive error message of the error.
   */
  @SerializedName("message") val message: String,
  /**
   * In case of `source` or `tile` loading errors, `source-id` will contain the id of the source failing.
   */
  @SerializedName("source-id") val sourceId: String?,
  /**
   * In case of `tile` loading errors, `tile-id` will contain the id of the tile.
   */
  @SerializedName("tile-id") val tileId: TileID?,
)