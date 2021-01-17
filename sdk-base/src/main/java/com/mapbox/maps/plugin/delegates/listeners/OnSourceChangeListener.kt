package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked whenever a source definition has changed.
 */
fun interface OnSourceChangeListener {
  /**
   * Invoked whenever the source definition has changed.
   *
   * @param id the id of the source that has changed
   */
  fun onSourceChanged(id: String)
}