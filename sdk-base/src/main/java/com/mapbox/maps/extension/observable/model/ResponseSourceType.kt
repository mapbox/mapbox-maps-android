package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes source data type for response in resource-request event.
 * @param value String value of this enum
 */
enum class ResponseSourceType(val value: String) {
  /**
   * source type as network.
   */
  @SerializedName("network")
  NETWORK("network"),

  /**
   * source type as cache.
   */
  @SerializedName("cache")
  CACHE("cache"),

  /**
   * source type as tile-store.
   */
  @SerializedName("tile-store")
  TILE_STORE("tile-store"),

  /**
   * source type as local-file.
   */
  @SerializedName("local-file")
  LOCAL_FILE("local-file"),
}