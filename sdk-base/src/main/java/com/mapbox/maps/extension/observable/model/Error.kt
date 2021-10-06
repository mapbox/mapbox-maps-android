package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 *The data class for error in Observer
 */
data class Error(
  /**
   * "reason" property
   */
  @SerializedName("reason") val reason: ResponseErrorReason,
  /**
   * "message" property
   */
  @SerializedName("message") val message: String
)