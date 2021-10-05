package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName

/**
 * The data class for style-image-missing event data in Observer
 */
data class StyleImageMissingEventData(
  /**
   * The ID of the missing image.
   */
  @SerializedName("id") val id: String
)