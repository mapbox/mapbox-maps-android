package com.mapbox.maps.plugin.locationcomponent.animators

import androidx.annotation.RestrictTo
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class PuckBearingAnimator(private val indicatorBearingChangedListener: OnIndicatorBearingChangedListener) : PuckAnimator<Double>(Evaluators.DOUBLE) {

  override var enabled = true

  override fun updateLayer(fraction: Float, value: Double) {
    if (enabled) {
      locationRenderer?.setBearing(value)
      indicatorBearingChangedListener.onIndicatorBearingChanged(value)
    }
  }
}