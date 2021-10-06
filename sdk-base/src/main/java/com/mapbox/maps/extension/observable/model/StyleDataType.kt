package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Defines what kind of style data has been loaded in a style-data-loaded event.
 */
enum class StyleDataType {
  /**
   * The style data loaded event is associated with style.
   */
  @SerializedName("style")
  STYLE,

  /**
   * The style data loaded event is associated with sprite.
   */
  @SerializedName("sprite")
  SPRITE,

  /**
   * The style data loaded event is associated with sources.
   */
  @SerializedName("sources")
  SOURCES
}