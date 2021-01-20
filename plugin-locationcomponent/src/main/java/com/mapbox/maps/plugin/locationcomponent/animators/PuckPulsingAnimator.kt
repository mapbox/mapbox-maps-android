package com.mapbox.maps.plugin.locationcomponent.animators

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.view.animation.PathInterpolatorCompat

internal class PuckPulsingAnimator : PuckAnimator<Double>(Evaluators.DOUBLE) {

  var enabled = false
  var maxRadius: Double = 10.0
  @ColorInt
  var pulsingColor: Int = Color.BLUE
  var pulseFadeEnabled = true

  init {
    duration = PULSING_DEFAULT_DURATION
    repeatMode = RESTART
    repeatCount = INFINITE
    interpolator = PULSING_DEFAULT_INTERPOLATOR
  }

  fun animateInfinite() {
    animate(0.0, maxRadius)
  }

  override fun updateLayer(fraction: Float, value: Double) {
    var opacity = 1.0f
    if (pulseFadeEnabled) {
      opacity = 1.0f - (value / maxRadius).toFloat()
    }
    locationRenderer?.updatePulsingUi(pulsingColor, value.toFloat(), if (fraction <= 0.1f) 0f else opacity)
  }

  companion object {
    private const val PULSING_DEFAULT_DURATION = 3_000L
    private val PULSING_DEFAULT_INTERPOLATOR = PathInterpolatorCompat.create(
      0.0f,
      0.0f,
      0.25f,
      1.0f
    )
  }
}