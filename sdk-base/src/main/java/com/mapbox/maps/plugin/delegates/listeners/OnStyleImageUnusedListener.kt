package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.StyleImageUnusedEventData

/**
 * Definition for listener invoked when an image added to the Style is no longer needed.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use StyleImageRemoveUnusedCallback instead.",
  replaceWith = ReplaceWith("StyleImageRemoveUnusedCallback")
)
fun interface OnStyleImageUnusedListener {

  /**
   * Invoked whenever an image added to the Style is no longer needed and can be removed using StyleManager#removeStyleImage method.
   *
   * @param eventData StyleImageUnusedEventData
   */
  fun onStyleImageUnused(eventData: StyleImageUnusedEventData)
}