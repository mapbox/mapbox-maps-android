package com.mapbox.maps.extension.observable.resourcerequest

import com.google.gson.annotations.SerializedName
import com.mapbox.maps.extension.observable.resourcerequest.eventdata.ResponseErrorType

/**
 *The data class for error in Observer
 */
data class Error(
  /**
   * "reason" property
   */
  @SerializedName("reason") val reason: ResponseErrorType,
  /**
   * "message" property
   */
  @SerializedName("message") val message: String
)