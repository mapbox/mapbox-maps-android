package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.LocationLayerRenderer
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings

internal class PuckAnimatorManager {

  private var bearingAnimator = PuckBearingAnimator()
  private var pointAnimator = PuckPositionAnimator()
  private var pulsingAnimator = PuckPulsingAnimator()

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

  fun animateBearing(vararg targets: Double, options: (ValueAnimator.() -> Unit)?) {
    bearingAnimator.animate(*targets.toTypedArray(), options = options)
  }

  fun animatePoints(vararg targets: Point, options: (ValueAnimator.() -> Unit)?) {
    pointAnimator.animate(*targets, options = options)
  }

  fun enablePulsingAnimation(settings: LocationComponentSettings) {
    pulsingAnimator.apply {
      enabled = true
      maxRadius = settings.pulsingMaxRadius.toDouble()
      pulsingColor = settings.pulsingColor
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