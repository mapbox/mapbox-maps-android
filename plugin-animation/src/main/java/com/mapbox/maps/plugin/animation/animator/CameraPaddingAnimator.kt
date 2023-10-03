package com.mapbox.maps.plugin.animation.animator

import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Animator class used to animate [CameraOptions.padding] property.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class CameraPaddingAnimator(
  options: CameraAnimatorOptions<EdgeInsets>,
  block: (ValueAnimator.() -> Unit)? = null
) : CameraAnimator<EdgeInsets>(
  Evaluators.EDGE_INSET,
  options
) {

  init {
    block?.invoke(this)
  }

  /**
   * Animator type.
   */
  override val type = CameraAnimatorType.PADDING
}