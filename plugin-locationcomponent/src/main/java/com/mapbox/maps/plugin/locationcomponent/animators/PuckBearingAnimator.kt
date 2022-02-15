package com.mapbox.maps.plugin.locationcomponent.animators

import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener

internal class PuckBearingAnimator(private val indicatorBearingChangedListener: OnIndicatorBearingChangedListener) : PuckAnimator<Double>(Evaluators.DOUBLE) {

  override var enabled = true

  override fun updateLayer(fraction: Float, value: Double) {
    if (enabled) {
      locationRenderer?.setBearing(value)
      indicatorBearingChangedListener.onIndicatorBearingChanged(value)
    }
  }
}