package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Defines what kind of source data has been loaded in a source-data-loaded event.
 */
enum class SourceDataType {
  /**
   * The source data loaded event is associated with source metadata.
   */
  @SerializedName("metadata")
  METADATA,

  /**
   * The source data loaded event is associated with source tile.
   */
  @SerializedName("tile")
  TILE
}