package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Animator class used to animate [CameraOptions.zoom] property.
 */
internal class CameraZoomAnimator : CameraAnimator<Double> {

  constructor(
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)? = null
  ) : super(
    Evaluators.DOUBLE,
    options
  ) {
    block?.invoke(this)
  }

  constructor(
    evaluator: TypeEvaluator<Double>,
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)? = null
  ) : super(
    evaluator,
    options
  ) {
    block?.invoke(this)
  }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.ZOOM
}