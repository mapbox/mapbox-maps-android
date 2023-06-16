package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.extension.observable.eventdata.CameraChangedEventData
import com.mapbox.maps.plugin.animation.CameraAnimationsLifecycleListener
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorChangeListener

/**
 * Definition for listener invoked whenever the camera position changes.
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use CameraChangedCallback instead.",
  replaceWith = ReplaceWith("CameraChangedCallback"),
  level = DeprecationLevel.WARNING
)
fun interface OnCameraChangeListener {

  /**
   * Invoked whenever camera position changes.
   *
   * For more precise information about actual map camera animations
   * please check [CameraAnimationsPlugin], [CameraAnimationsLifecycleListener] and [CameraAnimatorChangeListener]
   *
   * @param eventData CameraChangedEventData
   *
   */
  fun onCameraChanged(eventData: CameraChangedEventData)
}