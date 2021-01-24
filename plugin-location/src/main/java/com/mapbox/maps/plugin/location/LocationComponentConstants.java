package com.mapbox.maps.plugin.location;

/**
 * Contains all the constants being used for the {@link LocationPluginImpl}.
 *
 * @deprecated Location Plugin is deprecated, use Location Component Plugin instead.
 */
@Deprecated
public final class LocationComponentConstants {

  // Controls the compass update rate in milliseconds
  static final long COMPASS_UPDATE_RATE_MS = 500;

  // Sets the transition animation duration when switching camera modes.
  static final long TRANSITION_ANIMATION_DURATION_MS = 750;

  // Sets the max allowed time for the location icon animation from one LatLng to another.
  static final long MAX_ANIMATION_DURATION_MS = 2000;

  // Sets the duration of change of accuracy radius when a different value is provided.
  static final long ACCURACY_RADIUS_ANIMATION_DURATION = 250;

  // Default animation duration for zooming while tracking.
  static final long DEFAULT_TRACKING_ZOOM_ANIM_DURATION = 750;

  // Default animation duration for updating padding while tracking.
  static final long DEFAULT_TRACKING_PADDING_ANIM_DURATION = 750;

  // Default animation duration for tilting while tracking.
  static final long DEFAULT_TRACKING_TILT_ANIM_DURATION = 1250;

  // Threshold value to perform immediate camera/layer position update.
  static final double INSTANT_LOCATION_TRANSITION_THRESHOLD = 50_000;

  // Default interval between location updates
  static final long DEFAULT_INTERVAL_MILLIS = 1000;

  // Default fastest acceptable interval between location updates
  static final long DEFAULT_FASTEST_INTERVAL_MILLIS = 1000;

  // Layers

  /**
   * Layer ID of the location foreground icon.
   */
  public static final String FOREGROUND_LAYER = "mapbox-location-foreground-layer";
  /**
   * Source ID of the background source.
   */
  public static final String MODEL_SCOURCE = "mapbox-location-model-source";
  /**
   * Layer ID of the location background layer.
   */
  public static final String MODEL_LAYER = "mapbox-location-model-layer";

  // Icons
  static final String FOREGROUND_ICON = "mapbox-location-icon";
  static final String BACKGROUND_ICON = "mapbox-location-stroke-icon";
  static final String FOREGROUND_STALE_ICON = "mapbox-location-stale-icon";
  static final String BACKGROUND_STALE_ICON = "mapbox-location-background-stale-icon";
  static final String SHADOW_ICON = "mapbox-location-shadow-icon";
  static final String BEARING_ICON = "mapbox-location-bearing-icon";
  static final String BEARING_STALE_ICON = "mapbox-location-bearing-stale-icon";

  private LocationComponentConstants() {
    // Class should not be initialized
  }
}