package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.LocationLayerRenderer
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.utils.MathUtils

internal class PuckAnimatorManager(
  indicatorPositionChangedListener: OnIndicatorPositionChangedListener,
  indicatorBearingChangedListener: OnIndicatorBearingChangedListener
) {

  private var bearingAnimator = PuckBearingAnimator(indicatorBearingChangedListener)
  private var pointAnimator = PuckPositionAnimator(indicatorPositionChangedListener)
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
    val optimized = DoubleArray(targets.size)
    targets.toTypedArray().apply {
      for (i in 0 until size) {
        optimized[i] = if (i == 0)
          MathUtils.normalize(get(i))
        else
          MathUtils.shortestRotation(MathUtils.normalize(get(i)), optimized[i - 1])
      }
    }
    bearingAnimator.animate(*optimized.toTypedArray(), options = options)
  }

  fun animatePoints(vararg targets: Point, options: (ValueAnimator.() -> Unit)?) {
    pointAnimator.animate(*targets, options = options)
  }

  fun applyPulsingAnimationSettings(settings: LocationComponentSettings) {
    pulsingAnimator.apply {
      enabled = settings.pulsingEnabled
      maxRadius = settings.pulsingMaxRadius.toDouble()
      pulsingColor = settings.pulsingColor
      if (enabled) {
        animateInfinite()
      } else {
        cancelRunning()
      }
    }
  }

  fun updateBearingAnimator(block: ValueAnimator.() -> Unit) {
    bearingAnimator.updateOptions(block)
  }

  fun updatePointAnimator(block: ValueAnimator.() -> Unit) {
    pointAnimator.updateOptions(block)
  }
}