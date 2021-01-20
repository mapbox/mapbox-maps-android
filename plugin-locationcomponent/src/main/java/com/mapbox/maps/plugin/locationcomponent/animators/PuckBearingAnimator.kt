package com.mapbox.maps.plugin.locationcomponent.animators

internal class PuckBearingAnimator : PuckAnimator<Double>(Evaluators.DOUBLE) {

  override fun updateLayer(fraction: Float, value: Double) {
    locationRenderer?.setBearing(value)
  }
}