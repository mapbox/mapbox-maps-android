package com.mapbox.maps.extension.observable.resourcerequest.eventdata

import com.google.gson.annotations.SerializedName

/**
 * Describes type of Error for response object.
 */
enum class ResponseErrorType {
  /**
   * Error type server.
   */
  @SerializedName("server")
  SERVER,

  /**
   * Error type connection.
   */
  @SerializedName("connection")
  CONNECTION,

  /**
   * Error type rate-limit.
   */
  @SerializedName("rate-limit")
  RATE_LIMIT,

  /**
   * Error type not-found.
   */
  @SerializedName("not-found")
  NOT_FOUND,

  /**
   * Error type other.
   */
  @SerializedName("other")
  OTHER,

  /**
   * Error type success.
   */
  @SerializedName("success")
  SUCCESS,
}