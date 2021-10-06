package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes the reason of Error for response object.
 */
enum class ResponseErrorReason {
  /**
   * Error type success.
   */
  @SerializedName("success")
  SUCCESS,

  /**
   * Error type not-found.
   */
  @SerializedName("not-found")
  NOT_FOUND,

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
   * Error type in-offline-mode.
   */
  @SerializedName("in-offline-mode")
  IN_OFFLINE_MODE,

  /**
   * Error type other.
   */
  @SerializedName("other")
  OTHER
}