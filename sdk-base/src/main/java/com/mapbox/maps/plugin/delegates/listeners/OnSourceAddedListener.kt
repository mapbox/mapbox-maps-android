package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.SourceAddedEventData

/**
 * Definition for listener invoked whenever a source is added.
 */
fun interface OnSourceAddedListener {
  /**
   * Invoked whenever the Source has been added with StyleManager#addStyleSource runtime API.
   *
   * @param eventData SourceAddedEventData
   */
  fun onSourceAdded(eventData: SourceAddedEventData)
}