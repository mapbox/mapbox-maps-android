package com.mapbox.maps.listener

/**
 * Definition for listener invoked whenever the map will starting rendering a map.
 */
interface OnWillStartRenderingMapListener {

  /**
   * Called when the map will start rendering a map.
   */
  fun onWillStartRenderingMap()
}