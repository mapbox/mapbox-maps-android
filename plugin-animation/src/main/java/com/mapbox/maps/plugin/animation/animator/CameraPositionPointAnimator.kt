package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraPositionPoint
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Animator class used to animate [CameraOptions.positionPoint] property.
 */
@OptIn(MapboxExperimental::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class CameraPositionPointAnimator : CameraAnimator<CameraPositionPoint> {
  internal constructor(
    options: CameraAnimatorOptions<CameraPositionPoint>,
    block: (ValueAnimator.() -> Unit)? = null
  ) : super(POSITION_POINT_EVALUATOR, options) {
    block?.invoke(this)
  }

  internal constructor(
    evaluator: TypeEvaluator<CameraPositionPoint>,
    options: CameraAnimatorOptions<CameraPositionPoint>,
    block: (ValueAnimator.() -> Unit)? = null
  ) : super(evaluator, options) {
    block?.invoke(this)
  }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.POSITION_POINT

  internal companion object {
    // Easing interpolators can overshoot the [0, 1] fraction, while the core rejects points outside [-1, 1].
    internal val POSITION_POINT_EVALUATOR: TypeEvaluator<CameraPositionPoint> =
      CameraTypeEvaluator { fraction, startValue, endValue ->
        if (fraction == 1F && endValue.isInRange()) {
          endValue
        } else if (fraction == 0F && startValue.isInRange()) {
          startValue
        } else {
          CameraPositionPoint(
            (startValue.x + fraction * (endValue.x - startValue.x)).coerceIn(-1.0, 1.0),
            (startValue.y + fraction * (endValue.y - startValue.y)).coerceIn(-1.0, 1.0)
          )
        }
      }

    private fun CameraPositionPoint.isInRange() = x in -1.0..1.0 && y in -1.0..1.0
  }
}