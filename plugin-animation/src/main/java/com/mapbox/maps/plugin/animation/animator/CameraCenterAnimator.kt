package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Animator class used to animate [CameraOptions.center] property.
 */
internal class CameraCenterAnimator : CameraAnimator<Point> {

  constructor(
    options: CameraAnimatorOptions<Point>,
    block: (ValueAnimator.() -> Unit)? = null
  ) : super(
    Evaluators.POINT,
    options
  ) {
    block?.invoke(this)
  }

  constructor(
    evaluator: TypeEvaluator<Point>,
    options: CameraAnimatorOptions<Point>,
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
  override val type = CameraAnimatorType.CENTER
}