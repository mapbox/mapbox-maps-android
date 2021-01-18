package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import com.mapbox.geojson.Point

/**
 * Defines the interface for LocationConsumer.
 */
interface LocationConsumer {

  /**
   * Called whenever the location is updated.
   */
  fun onLocationUpdated(vararg location: Point)

  /**
   * Called whenever the bearing is updated.
   */
  fun onBearingUpdated(vararg bearing: Double)

  /**
   * Update [ValueAnimator] options that will be used to animate between [Point] updates.
   */
  fun onPuckLocationAnimatorOptionsUpdated(options: ValueAnimator.() -> Unit)

  /**
   * Update [ValueAnimator] options that will be used to animate between bearing [Double] updates.
   */
  fun onPuckBearingAnimatorOptionsUpdated(options: ValueAnimator.() -> Unit)
}