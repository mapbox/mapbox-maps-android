package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Animator class used to animate [CameraOptions.verticalFov] property.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class CameraVerticalFovAnimator : CameraAnimator<Double> {
  internal constructor(
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)? = null
  ) : super(Evaluators.DOUBLE, options) {
    block?.invoke(this)
  }

  internal constructor(
    evaluator: TypeEvaluator<Double>,
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)? = null
  ) : super(evaluator, options) {
    block?.invoke(this)
  }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.VERTICAL_FOV
}