package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked whenever a source is removed.
 */
fun interface OnSourceRemovedListener {
  /**
   * Invoked whenever the Source has been removed with StyleManager#removeStyleSource runtime API.
   *
   * @param id the id of the source that is removed
   */
  fun onSourceRemoved(id: String)
}