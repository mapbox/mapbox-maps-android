package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked when the style has a missing image.
 */
fun interface OnStyleImageMissingListener {

  /**
   * Invoked whenever a style has a missing image. This event is emitted when the Map renders visible tiles and
   * one of the required images is missing in the sprite sheet. Subscriber has to provide the missing image
   * by calling StyleManager#addStyleImage method.
   *
   * @param id the id of the missing style image
   */
  fun onStyleImageMissing(id: String)
}