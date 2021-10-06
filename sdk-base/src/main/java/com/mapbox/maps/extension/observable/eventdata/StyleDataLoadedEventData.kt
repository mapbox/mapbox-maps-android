package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.extension.observable.model.StyleDataType

/**
 * The data class for style-data-loaded event data in Observer
 */
data class StyleDataLoadedEventData(
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
   * The 'type' property defines what kind of style data has been loaded.
   */
  @SerializedName("type") val type: StyleDataType
)