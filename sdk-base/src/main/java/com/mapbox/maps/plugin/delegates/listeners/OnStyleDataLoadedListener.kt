package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.plugin.delegates.listeners.eventdata.StyleDataType

/**
 * Definition for listener invoked when the requested style data has been loaded.
 */
fun interface OnStyleDataLoadedListener {

  /**
   * Invoked when the requested style data has been loaded.
   *
   * @param type defines what kind of style data has been loaded.
   */
  fun onStyleDataLoaded(type: StyleDataType)
}