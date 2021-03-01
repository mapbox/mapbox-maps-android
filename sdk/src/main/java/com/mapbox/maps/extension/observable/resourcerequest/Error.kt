package com.mapbox.maps.extension.observable.resourcerequest

import com.google.gson.annotations.SerializedName

/**
 *The data class for error in Observer
 */
data class Error(
  /**
   * "reason" property
   */
  @SerializedName("reason") val reason: String,
  /**
   * "message" property
   */
  @SerializedName("message") val message: String
)