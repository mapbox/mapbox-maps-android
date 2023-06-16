package com.mapbox.maps.extension.observable.eventdata

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.extension.observable.model.DataSourceType
import com.mapbox.maps.extension.observable.model.Request
import com.mapbox.maps.extension.observable.model.Response

/**
 *The data class for event data in Observer
 */
@Deprecated(
  message = "This data class is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("ResourceRequest"),
  level = DeprecationLevel.WARNING
)
data class ResourceEventData(
  /**
   * Representing timestamp taken at the time of an event creation, in microseconds, since the epoch.
   */
  @SerializedName("begin") val begin: Long,
  /**
   * For an interval events, an optional `end` property will be present that represents timestamp taken at the time
   * of an event completion.
   */
  @SerializedName("end") val end: Long?,
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