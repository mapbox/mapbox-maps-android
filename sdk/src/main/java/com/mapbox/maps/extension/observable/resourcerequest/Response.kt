package com.mapbox.maps.extension.observable.resourcerequest

import com.google.gson.annotations.SerializedName

/**
 * The response data class that included in EventData
 */
data class Response(
  /**
   * "etag" property
   */
  @SerializedName("etag") val eTag: String,
  /**
   * "must-revalidate" property
   */
  @SerializedName("must-revalidate") val mustRevalidate: Boolean,
  /**
   * "no-content" property
   */
  @SerializedName("no-content") val noContent: Boolean,
  /**
   * "modified" property
   */
  @SerializedName("modified") val modified: String,
  /**
   * "offline-data" property
   */
  @SerializedName("offline-data") val offlineData: Boolean,
  /**
   * "not-modified" property
   */
  @SerializedName("not-modified") val notModified: Boolean,
  /**
   * "expires" property
   */
  @SerializedName("expires") val expires: String,
  /**
   * "size" property
   */
  @SerializedName("size") val size: Int,
  /**
   * "error" property
   */
  @SerializedName("error") val error: Error?
)