package com.mapbox.maps.plugin.locationcomponent.animators

import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener

internal class PuckBearingAnimator(private val indicatorBearingChangedListener: OnIndicatorBearingChangedListener) : PuckAnimator<Double>(Evaluators.DOUBLE) {

  override fun updateLayer(fraction: Float, value: Double) {
    locationRenderer?.setBearing(value)
    indicatorBearingChangedListener.onIndicatorBearingChanged(value)
  }
}