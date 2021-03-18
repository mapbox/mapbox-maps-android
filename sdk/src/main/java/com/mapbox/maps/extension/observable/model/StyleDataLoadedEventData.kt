package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.plugin.delegates.listeners.eventdata.StyleDataType

/**
 *The data class for style data loaded event data in Observer
 */
data class StyleDataLoadedEventData(
  /**
   * The 'type' property defines what kind of style data has been loaded.
   */
  @SerializedName("type") val styleDataType: StyleDataType
)