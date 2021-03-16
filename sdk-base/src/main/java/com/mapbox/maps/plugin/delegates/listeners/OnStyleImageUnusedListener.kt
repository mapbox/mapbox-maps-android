package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked when an image added to the Style is no longer needed.
 */
fun interface OnStyleImageUnusedListener {

  /**
   * Invoked whenever an image added to the Style is no longer needed and can be removed using StyleManager#removeStyleImage method.
   *
   * @param id the id of the missing style image
   */
  fun onStyleImageUnused(id: String)
}