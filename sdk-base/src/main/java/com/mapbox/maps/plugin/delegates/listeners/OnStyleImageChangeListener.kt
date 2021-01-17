package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked whenever an style image is requested that hasn't been added
 * the style yet or whenever an existing style image can be cleaned up and removed from the style.
 */
interface OnStyleImageChangeListener {
  /**
   * Invoked whenever style image is referenced through the style but isn't yet added.
   *
   * @param id the id of the missing style image
   */
  fun onStyleImageMissing(id: String)

  /**
   * Invoked whenever style image can be removed from the style.
   * Return true to indicate the image has been removed, false otherwise
   *
   * @param id the id of the style image that is no longer used
   * @return true if style image was removed
   */
  fun onCanRemoveUnusedStyleImage(id: String): Boolean
}