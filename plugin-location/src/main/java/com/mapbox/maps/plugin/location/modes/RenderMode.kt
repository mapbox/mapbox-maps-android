package com.mapbox.maps.plugin.location.modes

import com.mapbox.maps.plugin.location.CompassEngine

/**
 * Contains the variety of ways the user location can be rendered on the map.
 */
@Deprecated("Location Plugin is deprecated, use Location Component Plugin instead.")
enum class RenderMode {
  /**
   * Basic tracking is enabled, bearing ignored.
   */
  NORMAL,
  /**
   * Tracking the user location with bearing considered
   * from a [CompassEngine].
   */
  COMPASS,
  /**
   * Tracking the user location with bearing considered from [android.location.Location].
   */
  GPS
}