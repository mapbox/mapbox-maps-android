package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName

/**
 * The data class for source-removed event data in Observer
 */
data class SourceRemovedEventData(
  /**
   * The ID of the removed source.
   */
  @SerializedName("id") val id: String
)