package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Defines what kind of style data has been loaded in a style-data-loaded event.
 * @param value String value of this enum
 */
@Deprecated(
  message = "This enum class is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("StyleDataLoadedType"),
  level = DeprecationLevel.WARNING
)
enum class StyleDataType(val value: String) {
  /**
   * The style data loaded event is associated with style.
   */
  @SerializedName("style")
  STYLE("style"),

  /**
   * The style data loaded event is associated with sprite.
   */
  @SerializedName("sprite")
  SPRITE("sprite"),

  /**
   * The style data loaded event is associated with sources.
   */
  @SerializedName("sources")
  SOURCES("sources")
}