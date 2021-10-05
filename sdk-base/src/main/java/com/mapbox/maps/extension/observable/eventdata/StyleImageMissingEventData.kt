package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName

/**
 * The data class for style-image-missing event data in Observer
 */
data class StyleImageMissingEventData(
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
   * The ID of the missing image.
   */
  @SerializedName("id") val id: String
)