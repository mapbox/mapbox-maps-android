package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import com.mapbox.common.location.LocationError
import com.mapbox.geojson.Point

/**
 * Defines the interface for LocationConsumer.
 */
interface LocationConsumer {

  /**
   * Called whenever the location is updated.
   * @param location supports multiple points to create more complex animations with intermediate points.
   *  Last [location] value will always be the animator target for next animation.
   * @param options if specified explicitly will apply current animator option to single location update animation.
   *  Otherwise default animator options will be used.
   */
  fun onLocationUpdated(vararg location: Point, options: (ValueAnimator.() -> Unit)? = null)

  /**
   * Called whenever the bearing is updated.
   * @param bearing supports multiple bearing values to create more complex animations with intermediate points.
   *  Last [bearing] value will always be the animator target for next animation.
   * @param options if specified explicitly will apply current animator option to single location bearing animation.
   *  Otherwise default animator options will be used.
   */
  fun onBearingUpdated(vararg bearing: Double, options: (ValueAnimator.() -> Unit)? = null)

  /**
   * Update [ValueAnimator] options that will be used to animate between [Point] updates by default.
   * This will apply to all upcoming updates.
   */
  fun onPuckLocationAnimatorDefaultOptionsUpdated(options: ValueAnimator.() -> Unit)

  /**
   * Update [ValueAnimator] options that will be used to animate between bearing [Double] updates by default.
   * This will apply to all upcoming updates.
   */
  fun onPuckBearingAnimatorDefaultOptionsUpdated(options: ValueAnimator.() -> Unit)

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

  /**
   * Called whenever there is an error.
   * @param error The actual error
   */
  fun onError(error: LocationError)
}