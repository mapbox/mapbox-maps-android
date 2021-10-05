package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName

/**
 * The data class for source-added event data in Observer
 */
data class SourceAddedEventData(
  /**
   * The ID of the added source.
   */
  @SerializedName("id") val id: String
)