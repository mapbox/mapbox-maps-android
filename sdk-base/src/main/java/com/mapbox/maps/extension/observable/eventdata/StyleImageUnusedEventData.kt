package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName

/**
 * The data class for style-image-remove-unused event data in Observer
 */
data class StyleImageUnusedEventData(
  /**
   * The ID of the unused image.
   */
  @SerializedName("id") val id: String
)