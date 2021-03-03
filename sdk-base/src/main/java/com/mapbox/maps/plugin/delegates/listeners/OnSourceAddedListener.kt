package com.mapbox.maps.plugin.delegates.listeners

/**
 * Definition for listener invoked whenever a source is added.
 */
fun interface OnSourceAddedListener {
  /**
   * Invoked whenever the Source has been added with StyleManager#addStyleSource runtime API.
   *
   * @param id the id of the source that is added
   */
  fun onSourceAdded(id: String)
}