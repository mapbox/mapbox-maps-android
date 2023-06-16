package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.RenderFrameStartedEventData

/**
 * Definition for listener invoked whenever the Map started rendering a frame.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use RenderFrameStartedCallback instead.",
  replaceWith = ReplaceWith("RenderFrameStartedCallback")
)
fun interface OnRenderFrameStartedListener {
  /**
   * Invoked whenever the Map started rendering a frame.
   *
   * @param eventData RenderFrameStartedEventData
   */
  fun onRenderFrameStarted(eventData: RenderFrameStartedEventData)
}