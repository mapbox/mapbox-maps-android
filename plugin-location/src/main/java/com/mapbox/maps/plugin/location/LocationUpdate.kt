package com.mapbox.maps.plugin.location

import android.location.Location

/**
 * A class that contains the location update configuration.
 */
@Deprecated("Location Plugin is deprecated, use Location Component Plugin instead.")
data class LocationUpdate(
  /**
   * @return target location of the transition
   */
  val location: Location,
  /**
   * @return list of locations that are on the path to the target location for animation interpolation
   */
  val intermediatePoints: List<Location>? = null,
  /**
   * @return If set, all of the transitions to this update (puck's and possibly camera's if tracking mode is engaged)
   * will have the provided duration. If null, the duration will be calculated internally.
   *
   *
   * **[LocationComponentOptions.Builder.trackingAnimationDurationMultiplier]
   * is ignored if this value is provided.**
   */
  val animationDuration: Long? = null
)