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
  Evaluators.SCREEN_COORDINATE,
  options
) {

  init {
    block?.invoke(this)
  }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.ANCHOR
}