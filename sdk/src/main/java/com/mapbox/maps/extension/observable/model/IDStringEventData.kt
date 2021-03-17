package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 *The data class for the object that only contains an ID.
 */
data class IDStringEventData(
  /**
   * The ID of the returned event.
   */
  @SerializedName("id") val id: String
)