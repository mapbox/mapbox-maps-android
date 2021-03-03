package com.mapbox.maps.extension.observable.map

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.plugin.delegates.listeners.eventdata.MapLoadError

/**
 *The data class for Map Loading Error event data in Observer
 */
data class MapLoadingErrorEventData(
  /**
   * Describes the category of the error that has occurred while loading the Map.
   */
  @SerializedName("error") val error: MapLoadError,
  /**
   * The description of the error.
   */
  @SerializedName("description") val description: String
)