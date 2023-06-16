package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 *The data class for error in Observer
 */
@Deprecated(
  message = "This data class is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("ResourceRequestError"),
  level = DeprecationLevel.WARNING
)
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