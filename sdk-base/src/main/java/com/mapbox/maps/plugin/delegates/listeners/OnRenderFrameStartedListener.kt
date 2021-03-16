package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked whenever the Map started rendering a frame.
 */
fun interface OnRenderFrameStartedListener {
  /**
   * Invoked whenever the Map started rendering a frame.
   */
  fun onRenderFrameStarted()
}