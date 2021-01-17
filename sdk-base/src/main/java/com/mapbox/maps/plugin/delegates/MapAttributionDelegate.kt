package com.mapbox.maps.plugin.delegates

import android.content.Context
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.attribution.Attribution
import com.mapbox.maps.plugin.attribution.AttributionParserConfig

/**
 * Map attribution delegates the request to get attribution data from the underlying map style.
 */
interface MapAttributionDelegate {

  /**
   * Called to request an instance of map telemetry.
   *
   * @return map telemetry instance
   */
  fun telemetry(): MapTelemetry

  /**
   * Parse attributions with the given config
   *
   * @param context the context
   * @param config configuration for parings
   *
   * @return the parsed attributions
   */
  fun parseAttributions(context: Context, config: AttributionParserConfig): List<Attribution>

  /**
   * Build the feedback url
   *
   * @param context the context
   */
  fun buildMapBoxFeedbackUrl(context: Context): String
}