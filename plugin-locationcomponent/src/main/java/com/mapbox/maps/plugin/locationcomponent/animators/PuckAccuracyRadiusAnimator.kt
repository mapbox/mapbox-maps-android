package com.mapbox.maps.plugin.locationcomponent.animators

import android.graphics.Color
import androidx.annotation.ColorInt
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorAccuracyRadiusChangedListener

internal class PuckAccuracyRadiusAnimator(private val accuracyRadiusChangedListener: OnIndicatorAccuracyRadiusChangedListener) :
  PuckAnimator<Double>(Evaluators.DOUBLE) {

  @ColorInt
  internal var accuracyCircleColor = Color.BLUE
  @ColorInt
  internal var accuracyCircleBorderColor = Color.BLUE

  override fun updateLayer(fraction: Float, value: Double) {
    if (enabled) {
      val resolvedValue = 0f.coerceAtLeast(value.toFloat())
      locationRenderer?.setAccuracyRadius(resolvedValue)
      locationRenderer?.styleAccuracy(accuracyCircleColor, accuracyCircleBorderColor)
      accuracyRadiusChangedListener.onIndicatorAccuracyRadiusChanged(value)
    } else {
      locationRenderer?.setAccuracyRadius(0f)
    }
  }
}