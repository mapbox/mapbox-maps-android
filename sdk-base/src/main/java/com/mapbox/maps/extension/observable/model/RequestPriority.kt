package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes priority for request object.
 * @param value String value of this enum
 */
enum class RequestPriority(val value: String) {
  /** Regular priority. */
  @SerializedName("regular")
  REGULAR("regular"),

  /** low priority. */
  @SerializedName("low")
  LOW("low")
}