package com.mapbox.maps.plugin.locationcomponent

/**
 * Contains all the constants being used for the [LocationComponentPlugin].
 */
object LocationComponentConstants {
  /**
   * Controls the compass update rate in milliseconds
   */
  const val COMPASS_UPDATE_RATE_MS: Long = 500

  /**
   * Sets the transition animation duration when switching camera modes.
   */
  const val TRANSITION_ANIMATION_DURATION_MS: Long = 750

  /**
   * Sets the max allowed time for the location icon animation from one LatLng to another.
   */
  const val MAX_ANIMATION_DURATION_MS: Long = 2000

  /**
   * Sets the duration of change of accuracy radius when a different value is provided.
   */
  const val ACCURACY_RADIUS_ANIMATION_DURATION: Long = 250

  /**
   * Default animation duration for zooming while tracking.
   */
  const val DEFAULT_TRACKING_ZOOM_ANIM_DURATION: Long = 750

  /**
   * Default animation duration for updating padding while tracking.
   */
  const val DEFAULT_TRACKING_PADDING_ANIM_DURATION: Long = 750

  /**
   * Default animation duration for tilting while tracking.
   */
  const val DEFAULT_TRACKING_TILT_ANIM_DURATION: Long = 1250

  /**
   * Threshold value to perform immediate camera/layer position update.
   */
  const val INSTANT_LOCATION_TRANSITION_THRESHOLD = 50000.0

  /**
   * Default interval between location updates
   */
  const val DEFAULT_INTERVAL_MILLIS: Long = 1000

  /**
   * Default fastest acceptable interval between location updates
   */
  const val DEFAULT_FASTEST_INTERVAL_MILLIS: Long = 1000
  // Layers
  /**
   * Layer ID of the location indicator layer.
   */
  const val LOCATION_INDICATOR_LAYER = "mapbox-location-indicator-layer"

  /**
   * Source ID of the background source.
   */
  const val MODEL_SOURCE = "mapbox-location-model-source"

  /**
   * Layer ID of the location background layer.
   */
  const val MODEL_LAYER = "mapbox-location-model-layer"

  // Icons
  /**
   * Image ID for the top icon of the location indicator layer.
   */
  const val TOP_ICON = "mapbox-location-top-icon"

  /**
   * Image ID for the shadow icon of the location indicator layer.
   */
  const val SHADOW_ICON = "mapbox-location-shadow-icon"

  /**
   * Image ID for the bearing icon of the location indicator layer.
   */
  const val BEARING_ICON = "mapbox-location-bearing-icon"

  /**
   * Control maximum radius for pulsing puck to follow location accuracy’s radius.
   */
  const val PULSING_MAX_RADIUS_FOLLOW_ACCURACY = -1f
}