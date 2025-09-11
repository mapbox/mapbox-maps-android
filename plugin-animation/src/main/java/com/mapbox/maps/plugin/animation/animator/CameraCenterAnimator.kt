package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType
import com.mapbox.maps.plugin.animation.CameraTransform
import com.mapbox.maps.plugin.animation.CameraTransform.wrapCoordinate

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

  override fun resolveAnimationObjectValues(startValue: Any) =
    if (useShortestPath) {
      // assemble the original targets by inserting the start point
      val originalTargets: List<Point> = listOf(startValue as Point) + targets
      // Build the reversed target list with wrapped coordinates
      val mutableTargetReversedList = mutableListOf<Point>()
      originalTargets.map { it.wrapCoordinate() }.reversed().forEach {
        if (mutableTargetReversedList.isEmpty()) {
          // insert the raw end point
          mutableTargetReversedList.add(it)
        } else {
          // calculate the previous point
          mutableTargetReversedList.add(
            CameraTransform.unwrapForShortestPath(
              start = it,
              end = mutableTargetReversedList.last()
            )
          )
        }
      }
      mutableTargetReversedList.reversed().toTypedArray()
    } else {
      super.resolveAnimationObjectValues(startValue)
    }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.CENTER
}