package com.mapbox.maps.plugin.animation.animator

import android.animation.ValueAnimator
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Animator class used to animate [CameraOptions.bearing] property.
 */
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

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.BEARING
}