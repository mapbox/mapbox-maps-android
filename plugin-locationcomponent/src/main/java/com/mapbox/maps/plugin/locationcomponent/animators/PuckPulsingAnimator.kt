package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.RestrictTo
import androidx.core.view.animation.PathInterpolatorCompat

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class PuckPulsingAnimator(private val pixelRatio: Float = 1.0f) :
  PuckAnimator<Double>(Evaluators.DOUBLE) {
  var maxRadius: Double = DEFAULT_RADIUS_DP * pixelRatio

  @ColorInt
  var pulsingColor: Int = Color.BLUE
  private var pulseFadeEnabled = true

  init {
    duration = PULSING_DEFAULT_DURATION
    repeatMode = RESTART
    repeatCount = INFINITE
    interpolator = PULSING_DEFAULT_INTERPOLATOR
  }

  fun animateInfinite() {
    if (maxRadius <= 0.0) {
      maxRadius = DEFAULT_RADIUS_DP * pixelRatio
    }
    if (!isRunning) {
      animate(0.0, maxRadius)
    }
    addListener(object : AnimatorListenerAdapter() {
      override fun onAnimationRepeat(animation: Animator) {
        super.onAnimationRepeat(animation)
        setObjectValues(0.0, maxRadius)
      }
    })
  }

  override fun updateLayer(fraction: Float, value: Double) {
    var opacity = 1.0f
    if (pulseFadeEnabled) {
      opacity = (1.0f - (value / maxRadius).toFloat()).coerceIn(0.0f, 1.0f)
    }
    locationRenderer?.updatePulsingUi(
      pulsingColor,
      value.toFloat(),
      if (fraction <= 0.1f) 0f else opacity
    )
  }

  internal companion object {
    const val PULSING_DEFAULT_DURATION = 3_000L
    const val DEFAULT_RADIUS_DP = 10.0
    private val PULSING_DEFAULT_INTERPOLATOR = PathInterpolatorCompat.create(
      0.0f,
      0.0f,
      0.25f,
      1.0f
    )
  }
}