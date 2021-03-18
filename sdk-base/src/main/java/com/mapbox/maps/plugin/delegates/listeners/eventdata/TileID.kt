package com.mapbox.maps.plugin.delegates.listeners.eventdata

import com.google.gson.annotations.SerializedName

/**
 * Defines the tile id in a source-data-loaded event.
 */
data class TileID(
  /**
   * The zoom level.
   */
  @SerializedName("z")
  val zoom: Double,
  /**
   * The x coordinate of the tile
   */
  @SerializedName("x")
  val x: Double,
  /**
   * The y coordinate of the tile
   */
  @SerializedName("y")
  val y: Double
)