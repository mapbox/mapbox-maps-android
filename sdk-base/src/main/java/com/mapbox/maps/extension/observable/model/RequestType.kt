package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes type for request object.
 */
enum class RequestType {
  /**
   * Request type unknown.
   */
  @SerializedName("unknown")
  UNKNOWN,

  /**
   * Request type style.
   */
  @SerializedName("style")
  STYLE,

  /**
   * Request type source.
   */
  @SerializedName("source")
  SOURCE,

  /**
   * Request type tile.
   */
  @SerializedName("tile")
  TILE,

  /**
   * Request type glyphs.
   */
  @SerializedName("glyphs")
  GLYPHS,

  /**
   * Request type sprite-image.
   */
  @SerializedName("sprite-image")
  SPRITE_IMAGE,

  /**
   * Request type sprite-json.
   */
  @SerializedName("sprite-json")
  SPRITE_JSON,

  /**
   * Request type image.
   */
  @SerializedName("image")
  IMAGE,
}