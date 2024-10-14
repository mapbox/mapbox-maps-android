package com.mapbox.maps.plugin.attribution

import com.mapbox.maps.MapboxExperimental

/**
 * Config class for Attribution
 */
class AttributionParserConfig @JvmOverloads constructor(
  /**
   * Whether contains improve map attribution
   */
  var withImproveMap: Boolean = true,
  /**
   * Whether contains copyright sign
   */
  var withCopyrightSign: Boolean = true,
  /**
   * Whether contains telemetry
   */
  var withTelemetryAttribution: Boolean = true,
  /**
   * Whether contains mapbox attribution
   */
  var withMapboxAttribution: Boolean = true,
  /**
   * Whether contains mapbox privacy policy
   */
  var withMapboxPrivacyPolicy: Boolean = true,
  /**
   * Whether to show Geofencing entry.
   * Note that the entry will be shown only if the Geofencing is currently active or the user has
   * previously opted out.
   */
  @MapboxExperimental
  var withMapboxGeofencingConsent: Boolean = true,
)