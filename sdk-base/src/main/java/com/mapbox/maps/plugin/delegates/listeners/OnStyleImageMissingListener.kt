package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.StyleImageMissingEventData

/**
 * Definition for listener invoked when the style has a missing image.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use StyleImageMissingCallback instead.",
  replaceWith = ReplaceWith("StyleImageMissingCallback")
)
fun interface OnStyleImageMissingListener {

  /**
   * Invoked whenever a style has a missing image. This event is emitted when the Map renders visible tiles and
   * one of the required images is missing in the sprite sheet. Subscriber has to provide the missing image
   * by calling StyleManager#addStyleImage method.
   *
   * @param eventData StyleImageMissingEventData
   */
  fun onStyleImageMissing(eventData: StyleImageMissingEventData)
}