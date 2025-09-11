package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import java.util.Objects

/**
 * Extension over [TypeEvaluator] to support skip optimizations.
 */
fun interface CameraTypeEvaluator<T> : TypeEvaluator<T> {

  /**
   * Returns true if animator can be skipped and not registered as update listener.
   * It usually means that [evaluate] function does not change on defined values range.
   *
   * @param startValue a start animation value
   * @param values values to animate over
   */
  fun canSkip(cameraCurrentValue: Any, startValue: Any, values: Array<*>): Boolean {
    if (cameraCurrentValue != startValue) return false
    return values.all { Objects.equals(startValue, it) }
  }
}