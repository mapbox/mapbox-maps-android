package com.mapbox.maps.extension.observable

import com.google.gson.annotations.SerializedName

/**
 *The data class for event data in Observer
 */
data class EventData(
  /**
   * "data-source" property
   */
  @SerializedName("data-source") val dataSource: String,
  /**
   * "request" property
   */
  @SerializedName("request") val request: Request,
  /**
   * "response" property
   */
  @SerializedName("response") val response: Response,
  /**
   * "cancelled" property
   */
  @SerializedName("cancelled") val cancelled: Boolean
)