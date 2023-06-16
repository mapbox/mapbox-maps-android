package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.SourceAddedEventData

/**
 * Definition for listener invoked whenever a source is added.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use SourceAddedCallback instead.",
  replaceWith = ReplaceWith("SourceAddedCallback")
)
fun interface OnSourceAddedListener {
  /**
   * Invoked whenever the Source has been added with StyleManager#addStyleSource runtime API.
   *
   * @param eventData SourceAddedEventData
   */
  fun onSourceAdded(eventData: SourceAddedEventData)
}