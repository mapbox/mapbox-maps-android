package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes type for request object.
 * @param value String value of this enum
 */
enum class RequestType(val value: String) {
  /**
   * Request type unknown.
   */
  @SerializedName("unknown")
  UNKNOWN("unknown"),

  /**
   * Request type style.
   */
  @SerializedName("style")
  STYLE("style"),

  /**
   * Request type source.
   */
  @SerializedName("source")
  SOURCE("source"),

  /**
   * Request type tile.
   */
  @SerializedName("tile")
  TILE("tile"),

  /**
   * Request type glyphs.
   */
  @SerializedName("glyphs")
  GLYPHS("glyphs"),

  /**
   * Request type sprite-image.
   */
  @SerializedName("sprite-image")
  SPRITE_IMAGE("sprite-image"),

  /**
   * Request type sprite-json.
   */
  @SerializedName("sprite-json")
  SPRITE_JSON("sprite-json"),

  /**
   * Request type image.
   */
  @SerializedName("image")
  IMAGE("image"),
}