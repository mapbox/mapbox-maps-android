package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator

/**
 * Defines the interface for LocationConsumer.
 */
interface LocationConsumer2 : LocationConsumer {

  /**
   * Called whenever the accuracy radius is updated.
   * @param radius - supports multiple radius value to create more complex animations with intermediate points.
   *  Last [radius] value will always be the animator target for next animation.
   * @param options - if specified explicitly will apply current animator option to radius animation.
   *  Otherwise default animator options will be used.
   */
  fun onAccuracyRadiusUpdated(
    vararg radius: Double,
    options: (ValueAnimator.() -> Unit)? = null
  )

  /**
   * Update [ValueAnimator] options that will be used to animate between accuracy radius [Double] updates by default.
   * This will apply to all upcoming updates.
   */
  fun onPuckAccuracyRadiusAnimatorDefaultOptionsUpdated(options: ValueAnimator.() -> Unit)
}