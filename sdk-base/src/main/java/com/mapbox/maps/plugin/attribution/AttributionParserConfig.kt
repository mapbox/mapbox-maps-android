package com.mapbox.maps.plugin.attribution

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
)