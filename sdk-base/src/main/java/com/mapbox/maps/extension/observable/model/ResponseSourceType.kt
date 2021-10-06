package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes source data type for response in resource-request event.
 */
enum class ResponseSourceType {
  /**
   * source type as network.
   */
  @SerializedName("network")
  NETWORK,

  /**
   * source type as cache.
   */
  @SerializedName("cache")
  CACHE,

  /**
   * source type as tile-store.
   */
  @SerializedName("tile-store")
  TILE_STORE,

  /**
   * source type as local-file.
   */
  @SerializedName("local-file")
  LOCAL_FILE,
}