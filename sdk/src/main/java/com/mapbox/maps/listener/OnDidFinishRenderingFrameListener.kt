package com.mapbox.maps.listener

import com.mapbox.maps.RenderFrameStatus

/**
 * Definition for listener invoked whenever the map finish rendering a frame.
 * See [RenderFrameStatus].
 */
fun interface OnDidFinishRenderingFrameListener {
  /**
   * Invoked whenever the map finish rendering a frame
   *
   * @param status the [RenderFrameStatus]
   */
  fun onDidFinishRenderingFrame(status: RenderFrameStatus)
}