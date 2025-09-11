package com.mapbox.maps.plugin.animation.animator

import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType
import com.mapbox.maps.util.MathUtils

/**
 * Animator class used to animate [CameraOptions.bearing] property.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class CameraBearingAnimator(
  options: CameraAnimatorOptions<Double>,
  val useShortestPath: Boolean,
  block: (ValueAnimator.() -> Unit)? = null
) : CameraAnimator<Double>(
  Evaluators.DOUBLE,
  options
) {

  init {
    block?.invoke(this)
  }

  override fun resolveAnimationObjectValues(startValue: Any) =
    if (useShortestPath) {
      MathUtils.prepareOptimalBearingPath(
        DoubleArray(targets.size + 1) { index ->
          if (index == 0) {
            startValue as Double
          } else {
            targets[index - 1]
          }
        }
      ).toTypedArray()
    } else {
      super.resolveAnimationObjectValues(startValue)
    }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.BEARING
}