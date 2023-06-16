package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.RenderFrameFinishedEventData

/**
 * Definition for listener invoked whenever the Map finished rendering a frame.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use RenderFrameFinishedCallback instead.",
  replaceWith = ReplaceWith("RenderFrameFinishedCallback")
)
fun interface OnRenderFrameFinishedListener {
  /**
   * Invoked whenever the Map finished rendering a frame.
   * The render-mode value tells whether the Map has all data ("full") required to render the visible viewport.
   * The needs-repaint value provides information about ongoing transitions that trigger Map repaint.
   * The placement-changed value tells if the symbol placement has been changed in the visible viewport.
   *
   * @param eventData RenderFrameFinishedEventData
   */
  fun onRenderFrameFinished(eventData: RenderFrameFinishedEventData)
}