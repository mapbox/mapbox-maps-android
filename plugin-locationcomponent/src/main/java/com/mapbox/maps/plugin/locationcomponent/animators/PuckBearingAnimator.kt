package com.mapbox.maps.plugin.locationcomponent.animators

import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.threading.AnimationSynchronizer

internal class PuckBearingAnimator(private val indicatorBearingChangedListener: OnIndicatorBearingChangedListener) : PuckAnimator<Double>(Evaluators.DOUBLE) {

  override var enabled = true

  override fun updateLayer(fraction: Float, value: Double) {
    if (enabled) {
      locationRenderer?.let { layerRenderer ->
        AnimationSynchronizer.get(locationRenderer)?.let {
          it.sendPuckBearingUpdate(choreographerFrameTimeNanos, value)
          super.updateLayer(fraction, value)
        } ?: run {
          layerRenderer.setBearing(value)
        }
      }
      indicatorBearingChangedListener.onIndicatorBearingChanged(value)
    }
  }
}