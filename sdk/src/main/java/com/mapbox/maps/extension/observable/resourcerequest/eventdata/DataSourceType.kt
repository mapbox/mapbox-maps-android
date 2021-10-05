package com.mapbox.maps.extension.observable.resourcerequest.eventdata

import com.google.gson.annotations.SerializedName

/**
 * Describes data source of request for resource-request event.
 */
enum class DataSourceType {
  /**
   * data source as resource-loader.
   */
  @SerializedName("resource-loader")
  RESOURCE_LOADER,

  /**
   * data source as network.
   */
  @SerializedName("network")
  NETWORK,

  /**
   * data source as database.
   */
  @SerializedName("database")
  DATABASE,

  /**
   * data source as asset.
   */
  @SerializedName("asset")
  ASSET,

  /**
   * data source as file-system.
   */
  @SerializedName("file-system")
  FILE_SYSTEM,
}