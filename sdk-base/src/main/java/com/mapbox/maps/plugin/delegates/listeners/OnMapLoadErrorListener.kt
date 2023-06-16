package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.model.MapLoadErrorType

/**
 * Definition for listener invoked whenever the map load errors out.
 * See [MapLoadErrorType].
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use MapLoadingErrorCallback instead.",
  replaceWith = ReplaceWith("MapLoadingErrorCallback")
)
interface OnMapLoadErrorListener {
  /**
   * Invoked whenever the map load errors out
   *
   * @param eventData MapLoadingErrorEventData
   */
  fun onMapLoadError(eventData: MapLoadingErrorEventData)
}