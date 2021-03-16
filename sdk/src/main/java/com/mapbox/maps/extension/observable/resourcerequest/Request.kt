package com.mapbox.maps.extension.observable.resourcerequest

import com.google.gson.annotations.SerializedName

/**
 * The request data class that included in EventData
 */
data class Request(
  /**
   * "loading-method" property
   */
  @SerializedName("loading-method") val loadingMethod: List<String>,
  /**
   * "url" property
   */
  @SerializedName("url") val url: String,
  /**
   * "kind" property
   */
  @SerializedName("kind") val kind: String,
  /**
   * "priority" property
   */
  @SerializedName("priority") val priority: String
)