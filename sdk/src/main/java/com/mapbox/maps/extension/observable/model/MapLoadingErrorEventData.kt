package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.plugin.delegates.listeners.eventdata.MapLoadErrorType

/**
 *The data class for Map Loading Error event data in Observer
 */
data class MapLoadingErrorEventData(
  /**
   * Defines what resource could not be loaded.
   */
  @SerializedName("type") val type: MapLoadErrorType,
  /**
   * The descriptive error message of the error.
   */
  @SerializedName("message") val message: String
)