package com.mapbox.maps.plugin.delegates.listeners.eventdata

import com.google.gson.annotations.SerializedName

/** Describes an error while loading the map.  */
enum class MapLoadError {
  /** An error while interpreting the contents of the style.  */
  @SerializedName("style-parse")
  STYLE_PARSE_ERROR,

  /** An I/O error while attempting to load the style contents.  */
  @SerializedName("style-load")
  STYLE_LOAD_ERROR,

  /** The style has not been found or incompatible.  */
  @SerializedName("not-found")
  NOT_FOUND_ERROR,

  /** An unspecified error.  */
  @SerializedName("unknown")
  UNKNOWN_ERROR
}