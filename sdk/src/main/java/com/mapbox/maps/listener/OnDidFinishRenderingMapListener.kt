package com.mapbox.maps.listener

import com.mapbox.maps.RenderMode

/**
 * Definition for listener invoked whenever the map has finished rendering.
 * See [RenderMode].
 */
fun interface OnDidFinishRenderingMapListener {
  /**
   * Invoked whenever the map has finished rendering.
   *
   * @param mode the [RenderMode]
   */
  fun onDidFinishRenderingMap(mode: RenderMode)
}