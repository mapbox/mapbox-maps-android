package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes the reason of Error for response object.
 * @param value String value of this enum
 */
enum class ResponseErrorReason(val value: String) {
  /**
   * Error type success.
   */
  @SerializedName("success")
  SUCCESS("success"),

  /**
   * Error type not-found.
   */
  @SerializedName("not-found")
  NOT_FOUND("not-found"),

  /**
   * Error type server.
   */
  @SerializedName("server")
  SERVER("server"),

  /**
   * Error type connection.
   */
  @SerializedName("connection")
  CONNECTION("connection"),

  /**
   * Error type rate-limit.
   */
  @SerializedName("rate-limit")
  RATE_LIMIT("rate-limit"),

  /**
   * Error type in-offline-mode.
   */
  @SerializedName("in-offline-mode")
  IN_OFFLINE_MODE("in-offline-mode"),

  /**
   * Error type other.
   */
  @SerializedName("other")
  OTHER("other")
}