package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Animator class used to animate [CameraOptions.center] property.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class CameraCenterAnimator internal constructor(
  evaluator: TypeEvaluator<Point> = Evaluators.POINT,
  options: CameraAnimatorOptions<Point>,
  val useShortestPath: Boolean,
  block: (ValueAnimator.() -> Unit)? = null
) : CameraAnimator<Point>(
  evaluator, options
) {
  init {
    block?.invoke(this)
  }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.CENTER
}