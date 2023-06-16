package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Defines what kind of source data has been loaded in a source-data-loaded event.
 * @param value String value of this enum
 */
@Deprecated(
  message = "This enum class is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("SourceDataLoadedType"),
  level = DeprecationLevel.WARNING
)
enum class SourceDataType(val value: String) {
  /**
   * The source data loaded event is associated with source metadata.
   */
  @SerializedName("metadata")
  METADATA("metadata"),

  /**
   * The source data loaded event is associated with source tile.
   */
  @SerializedName("tile")
  TILE("tile")
}