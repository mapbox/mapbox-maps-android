package com.mapbox.maps.plugin.location.modes

import android.location.Location

/**
 * Contains the variety of camera modes which determine how the camera will track
 * the user location.
 */
@Deprecated("Location Plugin is deprecated, use Location Component Plugin instead.")
enum class CameraMode {
  /**
   * No camera tracking.
   */
  NONE,
  /**
   * Camera does not track location, but does track compass bearing.
   */
  NONE_COMPASS,
  /**
   * Camera does not track location, but does track GPS [Location] bearing.
   */
  NONE_GPS,
  /**
   * Camera tracks the user location.
   */
  TRACKING,
  /**
   * Camera tracks the user location, with bearing
   * provided by a compass.
   */
  TRACKING_COMPASS,
  /**
   * Camera tracks the user location, with bearing
   * provided by a normalized [Location.getBearing].
   */
  TRACKING_GPS,
  /**
   * Camera tracks the user location, with bearing
   * always set to north (0).
   */
  TRACKING_GPS_NORTH
}