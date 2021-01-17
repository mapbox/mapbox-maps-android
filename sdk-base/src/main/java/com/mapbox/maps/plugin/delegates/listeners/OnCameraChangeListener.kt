package com.mapbox.maps.plugin.delegates.listeners

import com.mapbox.maps.plugin.animation.CameraAnimationsLifecycleListener
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorChangeListener

/**
 * Definition for listener invoked whenever the camera position changes.
 */
fun interface OnCameraChangeListener {

  /**
   * Invoked whenever camera position changes.
   *
   * For more precise information about actual map camera animations
   * please check [CameraAnimationsPlugin], [CameraAnimationsLifecycleListener] and [CameraAnimatorChangeListener]
   */
  fun onCameraChanged()
}