package com.mapbox.maps.plugin.location

import android.animation.ValueAnimator
import android.view.animation.Interpolator
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.location.MapboxAnimator.AnimationsValueChangeListener

internal class MapboxAnimatorProvider private constructor() {

  fun latLngAnimator(
    values: Array<Point>,
    updateListener: AnimationsValueChangeListener<*>,
    maxAnimationFps: Int
  ) = MapboxLatLngAnimator(values, updateListener, maxAnimationFps)

  fun floatAnimator(
    values: Array<Float>,
    updateListener: AnimationsValueChangeListener<*>,
    maxAnimationFps: Int
  ) = MapboxFloatAnimator(values, updateListener, maxAnimationFps)

  /**
   * This animator is for the LocationComponent pulsing circle.
   *
   * @param updateListener the listener that is found in the [LocationLayerAnimatorCoordinator]'s
   * listener array.
   * @param maxAnimationFps the max frames per second of the pulsing animation
   * @param pulseSingleDuration the number of milliseconds it takes for the animator to create
   * a single pulse.
   * @param pulseMaxRadius the max radius when the circle is finished with a single pulse.
   * @param pulseInterpolator the type of Android-system interpolator to use for
   * the pulsing animation (linear, accelerate, bounce, etc.)
   * @return a built [PulsingLocationCircleAnimator] object.
   */
  fun pulsingCircleAnimator(
    updateListener: AnimationsValueChangeListener<*>?,
    maxAnimationFps: Int,
    pulseSingleDuration: Float,
    pulseMaxRadius: Float,
    pulseInterpolator: Interpolator?
  ) = PulsingLocationCircleAnimator(updateListener, maxAnimationFps, pulseMaxRadius).apply {
    duration = pulseSingleDuration.toLong()
    repeatMode = ValueAnimator.RESTART
    repeatCount = ValueAnimator.INFINITE
    interpolator = pulseInterpolator
  }

  companion object {
    /**
     * MapboxAnimatorProvider singleton instance.
     */
    var INSTANCE = MapboxAnimatorProvider()
  }
}