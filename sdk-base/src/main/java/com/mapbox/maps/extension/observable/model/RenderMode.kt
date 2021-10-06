package com.mapbox.maps.extension.observable.model

import com.google.gson.annotations.SerializedName

/** Describes whether a map or frame has been fully rendered or not.  */
enum class RenderMode {
  /**
   * The map is partially rendered. Partially rendered map means
   * that not all data needed to render the map has been arrived
   * from the network or being parsed.
   */
  @SerializedName("partial")
  PARTIAL,

  /** The map is fully rendered.  */
  @SerializedName("full")
  FULL
}