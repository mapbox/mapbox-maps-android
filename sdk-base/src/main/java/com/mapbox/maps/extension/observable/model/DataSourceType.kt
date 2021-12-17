package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/**
 * Describes data source of request for resource-request event.
 * @param value String value of this enum
 */
enum class DataSourceType(val value: String) {
  /**
   * data source as resource-loader.
   */
  @SerializedName("resource-loader")
  RESOURCE_LOADER("resource-loader"),

  /**
   * data source as network.
   */
  @SerializedName("network")
  NETWORK("network"),

  /**
   * data source as database.
   */
  @SerializedName("database")
  DATABASE("database"),

  /**
   * data source as asset.
   */
  @SerializedName("asset")
  ASSET("asset"),

  /**
   * data source as file-system.
   */
  @SerializedName("file-system")
  FILE_SYSTEM("file-system"),
}