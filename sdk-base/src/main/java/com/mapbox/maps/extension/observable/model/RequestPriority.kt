package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes priority for request object.
 * @param value String value of this enum
 */
@Deprecated(
  message = "This enum class is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("RequestPriorityType"),
  level = DeprecationLevel.WARNING
)
enum class RequestPriority(val value: String) {
  /** Regular priority. */
  @SerializedName("regular")
  REGULAR("regular"),

  /** low priority. */
  @SerializedName("low")
  LOW("low")
}