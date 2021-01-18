package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.LocationLayerRenderer

internal class PuckAnimatorManager {

  private var bearingAnimator = BearingPuckAnimator()
  private var pointAnimator = PositionPuckAnimator()
  private var pulsingAnimator = PulsingPuckAnimator()

  fun setLocationLayerRenderer(renderer: LocationLayerRenderer) {
    bearingAnimator.setLocationLayerRenderer(renderer)
    pointAnimator.setLocationLayerRenderer(renderer)
    pulsingAnimator.setLocationLayerRenderer(renderer)
  }

  fun onStart() {
    if (pulsingAnimator.enabled) {
      pulsingAnimator.animateInfinite()
    }
  }

  fun onStop() {
    bearingAnimator.cancelRunning()
    pointAnimator.cancelRunning()
    pulsingAnimator.cancelRunning()
  }

  fun animateBearing(vararg targets: Double) {
    bearingAnimator.animate(*targets.toTypedArray())
  }

  fun animatePoints(vararg targets: Point) {
    pointAnimator.animate(*targets)
  }

  fun enablePulsingAnimation(radius: Float) {
    pulsingAnimator.apply {
      enabled = true
      maxRadius = radius.toDouble()
      animateInfinite()
    }
  }

  fun updateBearingAnimator(block: ValueAnimator.() -> Unit) {
    bearingAnimator.updateOptions(block)
  }

  fun updatePointAnimator(block: ValueAnimator.() -> Unit) {
    pointAnimator.updateOptions(block)
  }
}