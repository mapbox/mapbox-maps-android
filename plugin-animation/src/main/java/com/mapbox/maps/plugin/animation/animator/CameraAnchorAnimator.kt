package com.mapbox.maps.plugin.animation.animator

import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Animator class used to animate [CameraOptions.anchor] property.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class CameraAnchorAnimator(
  options: CameraAnimatorOptions<ScreenCoordinate>,
  block: (ValueAnimator.() -> Unit)? = null
) : CameraAnimator<ScreenCoordinate>(
  anchorEvaluator,
  options
) {

  init {
    block?.invoke(this)
  }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.ANCHOR

  private companion object {
    private val anchorEvaluator = object : CameraTypeEvaluator<ScreenCoordinate> {
      override fun canSkip(cameraCurrentValue: Any, startValue: Any, values: Array<*>) = false
      override fun evaluate(
        fraction: Float,
        startValue: ScreenCoordinate?,
        endValue: ScreenCoordinate?,
      ) = Evaluators.SCREEN_COORDINATE.evaluate(fraction, startValue, endValue)
    }
  }
}