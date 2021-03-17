package com.mapbox.maps.plugin.delegates.listeners.eventdata

import com.google.gson.annotations.SerializedName

/**
 * Describes an error type while loading the map.
 * Defines what resource could not be loaded.
 */
enum class MapLoadErrorType {
  /**
   * An error related to style.
   */
  @SerializedName("style")
  STYLE,

  /**
   * An error related to sprite.
   */
  @SerializedName("sprite")
  SPRITE,

  /**
   * An error related to source.
   */
  @SerializedName("source")
  SOURCE,

  /**
   * An error related to tile.
   */
  @SerializedName("tile")
  TILE,

  /**
   * An error related to glyphs.
   */
  @SerializedName("glyphs")
  GLYPHS
}