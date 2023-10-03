package com.mapbox.maps.plugin.animation.animator

import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Animator class used to animate [CameraOptions.pitch] property.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class CameraPitchAnimator(
  options: CameraAnimatorOptions<Double>,
  block: (ValueAnimator.() -> Unit)? = null
) : CameraAnimator<Double>(
  Evaluators.DOUBLE,
  options
) {

  init {
    block?.invoke(this)
  }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.PITCH
}