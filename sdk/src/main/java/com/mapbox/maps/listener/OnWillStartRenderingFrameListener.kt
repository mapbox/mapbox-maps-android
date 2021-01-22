package com.mapbox.maps.listener

/**
 * Definition for listener invoked whenever the map will stant rendering a frame.
 */
interface OnWillStartRenderingFrameListener {

  /**
   * Called when the Map will start rendering a frame.
   */
  fun onWillStartRenderingFrame()
}