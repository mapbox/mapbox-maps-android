package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Defines the tile id in a source-data-loaded event.
 */
data class TileID(
  /**
   * The zoom level.
   */
  @SerializedName("z")
  val zoom: Long,
  /**
   * The x coordinate of the tile
   */
  @SerializedName("x")
  val x: Long,
  /**
   * The y coordinate of the tile
   */
  @SerializedName("y")
  val y: Long
)