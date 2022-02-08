package com.mapbox.maps.plugin.delegates

/**
 * Delegate that allows to check if the style has fully loaded.
 */
fun interface MapStyleStateDelegate {

  /**
   * Returns if the style has fully loaded.
   */
  @Deprecated(
    "Use getStyle()?.isStyleLoaded instead.",
    replaceWith = ReplaceWith(
      "getStyle()?.isStyleLoaded",
    )
  )
  fun isFullyLoaded(): Boolean
}