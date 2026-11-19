package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraViewPoint
import com.mapbox.maps.CameraViewPointState
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Null-safe equality by kind and coordinates.
 */
@OptIn(MapboxExperimental::class)
internal fun CameraViewPointState.isSameAs(other: CameraViewPointState?): Boolean = when {
  other == null -> false
  isCameraViewPointFollowsPositionPoint -> other.isCameraViewPointFollowsPositionPoint
  else -> other.isCameraViewPoint && cameraViewPoint == other.cameraViewPoint
}

/**
 * Animator class used to animate [CameraOptions.viewPoint] property.
 *
 * @param followingPoint the view point that a "follows position point" state is interpolated from or to.
 */
@OptIn(MapboxExperimental::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class CameraViewPointAnimator : CameraAnimator<CameraViewPointState> {
  internal constructor(
    options: CameraAnimatorOptions<CameraViewPointState>,
    followingPoint: CameraViewPoint = CameraViewPoint(0.0, 0.0),
    block: (ValueAnimator.() -> Unit)? = null
  ) : this(viewPointEvaluator(followingPoint), options, block)

  internal constructor(
    evaluator: TypeEvaluator<CameraViewPointState>,
    options: CameraAnimatorOptions<CameraViewPointState>,
    block: (ValueAnimator.() -> Unit)? = null
  ) : super(evaluator, options) {
    block?.invoke(this)
  }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.VIEW_POINT

  /**
   * True if the animation hands the view point back to the position point at its end.
   */
  internal val releasesToFollow: Boolean
    get() = targets.lastOrNull()?.isCameraViewPointFollowsPositionPoint == true

  internal companion object {
    // Pinned values are always written on the last frame, so the camera never keeps an interpolated
    // value that differs from the requested one.
    internal fun viewPointEvaluator(followingPoint: CameraViewPoint): TypeEvaluator<CameraViewPointState> =
      object : CameraTypeEvaluator<CameraViewPointState> {
        override fun evaluate(
          fraction: Float,
          startValue: CameraViewPointState,
          endValue: CameraViewPointState
        ): CameraViewPointState {
          if (fraction == 1F && endValue.isValid()) {
            return endValue
          }
          if (fraction == 0F && startValue.isValid()) {
            return startValue
          }
          val start = startValue.resolve(followingPoint)
          val end = endValue.resolve(followingPoint)
          // Easing interpolators can overshoot the [0, 1] fraction, while the core rejects points outside [-1, 1].
          return CameraViewPointState.valueOf(
            CameraViewPoint(
              (start.x + fraction * (end.x - start.x)).coerceIn(-1.0, 1.0),
              (start.y + fraction * (end.y - start.y)).coerceIn(-1.0, 1.0)
            )
          )
        }

        // Writing the end value is required even when it equals the start value.
        override fun canSkip(cameraCurrentValue: Any, startValue: Any, values: Array<*>) = false
      }

    private fun CameraViewPointState.resolve(followingPoint: CameraViewPoint) =
      if (isCameraViewPoint) cameraViewPoint else followingPoint

    private fun CameraViewPointState.isValid() =
      isCameraViewPointFollowsPositionPoint || cameraViewPoint.let { it.x in -1.0..1.0 && it.y in -1.0..1.0 }
  }
}