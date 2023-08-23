package com.mapbox.maps.plugin

import android.content.Context
import android.net.Uri
import com.mapbox.common.MapboxOptions
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.attribution.AttributionParser
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.attribution.Attribution
import com.mapbox.maps.plugin.attribution.AttributionParserConfig
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import java.util.regex.Matcher
import java.util.regex.Pattern

internal class MapAttributionDelegateImpl constructor(
  private val mapboxMap: MapboxMap,
  private val mapTelemetry: MapTelemetry
) : MapAttributionDelegate {

  /**
   * Called to request an instance of map telemetry.
   *
   * @return map telemetry instance
   */
  override fun telemetry(): MapTelemetry {
    return mapTelemetry
  }

  /**
   * Parse attributions with the given config
   *
   * @param context the context
   * @param config configuration for parings
   *
   * @return the parsed attributions
   */
  override fun parseAttributions(
    context: Context,
    config: AttributionParserConfig
  ): List<Attribution> {
    val attributionsArray = mapboxMap.getAttributions().toTypedArray()
    return AttributionParser.Options(context)
      .withCopyrightSign(config.withCopyrightSign)
      .withImproveMap(config.withImproveMap)
      .withTelemetryAttribution(config.withTelemetryAttribution)
      .withMapboxAttribution(config.withMapboxAttribution)
      .withMapboxPrivacyPolicy(config.withMapboxPrivacyPolicy)
      .withAttributionData(*attributionsArray)
      .build().getAttributions().toList()
  }

  /**
   * Build the feedback url
   *
   * @param context the context
   */
  override fun buildMapBoxFeedbackUrl(context: Context): String {
    val builder = Uri.parse(MAP_FEEDBACK_URL).buildUpon()
    val cameraPosition = mapboxMap.cameraState
    cameraPosition.center.let {
      builder.encodedFragment(
        "/${it.longitude()}/${it.latitude()}/${cameraPosition.zoom}/${cameraPosition.bearing}/${cameraPosition.pitch}"
      )
    }
    val packageName = context.applicationContext.packageName
    if (packageName != null) {
      builder.appendQueryParameter("referrer", packageName)
    }
    builder.appendQueryParameter("access_token", MapboxOptions.accessToken)

    mapboxMap.getStyle()?.let {
      val pattern: Pattern = Pattern.compile(MAP_FEEDBACK_STYLE_URI_REGEX)
      val matcher: Matcher = pattern.matcher(it.styleURI)
      if (matcher.find()) {
        val styleOwner: String? = matcher.group(2)
        val styleId: String? = matcher.group(3)
        builder.appendQueryParameter("owner", styleOwner)
          .appendQueryParameter("id", styleId)
      }
    }
    return builder.build().toString()
  }

  companion object {
    private const val MAP_FEEDBACK_URL = "https://apps.mapbox.com/feedback"
    private const val MAP_FEEDBACK_STYLE_URI_REGEX = "^(.*://[^:^/]*)/(.*)/(.*)"
  }
}