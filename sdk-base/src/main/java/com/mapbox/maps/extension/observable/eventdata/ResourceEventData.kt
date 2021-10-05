package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.extension.observable.model.Request
import com.mapbox.maps.extension.observable.model.Response
import com.mapbox.maps.extension.observable.model.DataSourceType

/**
 *The data class for event data in Observer
 */
data class ResourceEventData(
  /**
   * "data-source" property
   */
  @SerializedName("data-source") val dataSource: DataSourceType,
  /**
   * "request" property
   */
  @SerializedName("request") val request: Request,
  /**
   * "response" property
   */
  @SerializedName("response") val response: Response?,
  /**
   * "cancelled" property
   */
  @SerializedName("cancelled") val cancelled: Boolean
)