package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes an error type while loading the map.
 * Defines what resource could not be loaded.
 * @param value String value of this enum
 */
enum class MapLoadErrorType(val value: String) {
  /**
   * An error related to style.
   */
  @SerializedName("style")
  STYLE("style"),

  /**
   * An error related to sprite.
   */
  @SerializedName("sprite")
  SPRITE("sprite"),

  /**
   * An error related to source.
   */
  @SerializedName("source")
  SOURCE("source"),

  /**
   * An error related to tile.
   */
  @SerializedName("tile")
  TILE("tile"),

  /**
   * An error related to glyphs.
   */
  @SerializedName("glyphs")
  GLYPHS("glyphs")
}