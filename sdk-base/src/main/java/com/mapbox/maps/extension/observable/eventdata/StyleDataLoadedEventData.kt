package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.extension.observable.model.StyleDataType

/**
 * The data class for style-data-loaded event data in Observer
 */
data class StyleDataLoadedEventData(
  /**
   * The 'type' property defines what kind of style data has been loaded.
   */
  @SerializedName("type") val type: StyleDataType
)