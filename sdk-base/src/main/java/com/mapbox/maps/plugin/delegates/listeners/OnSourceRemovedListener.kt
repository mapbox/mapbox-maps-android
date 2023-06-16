package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.SourceRemovedEventData

/**
 * Definition for listener invoked whenever a source is removed.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use SourceRemovedCallback instead.",
  replaceWith = ReplaceWith("SourceRemovedCallback")
)
fun interface OnSourceRemovedListener {
  /**
   * Invoked whenever the Source has been removed with StyleManager#removeStyleSource runtime API.
   *
   * @param eventData SourceRemovedEventData
   */
  fun onSourceRemoved(eventData: SourceRemovedEventData)
}