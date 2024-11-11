package com.mapbox.maps.attribution

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.MapAttributionDelegateImpl
import com.mapbox.maps.plugin.attribution.Attribution
import com.mapbox.maps.plugin.attribution.AttributionParserConfig
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MapAttributionDelegateImplTest {
  private val mapboxMap: MapboxMap = mockk()
  private val mapTelemetry: MapTelemetry = mockk()
  @OptIn(MapboxExperimental::class)
  private val mapGeofencingConsent: MapGeofencingConsent = mockk()
  private val style: Style = mockk()
  private lateinit var mapAttributionDelegate: MapAttributionDelegate
  private lateinit var context: Context

  @OptIn(MapboxExperimental::class)
  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    every { mapGeofencingConsent.shouldShowConsent() } returns true
    mapAttributionDelegate =
      MapAttributionDelegateImpl(mapboxMap, mapTelemetry, mapGeofencingConsent)
    every { mapboxMap.getAttributions() } returns listOf(ATTRIBUTION)
    every { mapboxMap.style } returns style
    every { style.styleURI } returns STYLE_URL
  }

  @Test
  fun telemetry() {
    assertEquals(mapTelemetry, mapAttributionDelegate.telemetry())
  }

  @Test
  fun buildMapBoxFeedbackUrl() {
    val camera: CameraState = mockk()
    every { camera.center } returns Point.fromLngLat(1.0, 2.0)
    every { camera.zoom } returns 0.0
    every { camera.bearing } returns 0.0
    every { camera.pitch } returns 0.0
    every { mapboxMap.cameraState } returns camera
    mockkStatic(MapboxOptions::class)
    every { MapboxOptions.accessToken } returns TOKEN
    val url = mapAttributionDelegate.buildMapBoxFeedbackUrl(context)
    assertEquals(
      "https://apps.mapbox.com/feedback?referrer=com.mapbox.maps.test&access_token=$TOKEN&owner=mapbox&id=light-v10#/1.0/2.0/0.0/0.0/0.0",
      url
    )
  }

  @Test
  fun parseAttributions() {
    var attributions = mapAttributionDelegate.parseAttributions(
      context,
      AttributionParserConfig(
        withImproveMap = false,
        withTelemetryAttribution = false,
        withMapboxAttribution = false,
        withMapboxPrivacyPolicy = false,
        withMapboxGeofencingConsent = false,
      )
    )
    assertEquals(1, attributions.size)
    assertEquals(
      "URL openstreetmap should match", "http://www.openstreetmap.org/about/",
      attributions.first().url
    )
    assertEquals(
      "Title openstreetmap should match", "© OpenStreetMap",
      attributions.first().title
    )

    attributions = mapAttributionDelegate.parseAttributions(
      context,
      AttributionParserConfig(
        withImproveMap = true,
        withTelemetryAttribution = false,
        withMapboxAttribution = false,
        withMapboxPrivacyPolicy = false,
        withMapboxGeofencingConsent = false,
      )
    )
    assertEquals(2, attributions.size)
    assertEquals(
      "URL improve map should match", "https://www.mapbox.com/map-feedback/",
      attributions.last().url
    )
    assertEquals(
      "Title improve map should match", "Improve This Map",
      attributions.last().title
    )

    attributions = mapAttributionDelegate.parseAttributions(
      context,
      AttributionParserConfig(
        withImproveMap = false,
        withTelemetryAttribution = true,
        withMapboxAttribution = false,
        withMapboxPrivacyPolicy = false,
        withMapboxGeofencingConsent = false,
      )
    )

    assertEquals(2, attributions.size)
    assertEquals(
      "Telemetry URL should match", "https://www.mapbox.com/telemetry/",
      attributions.last().url
    )
    assertEquals(
      "Telemetry title should match", "Mapbox Telemetry",
      attributions.last().title
    )

    attributions = mapAttributionDelegate.parseAttributions(
      context,
      AttributionParserConfig(
        withImproveMap = false,
        withTelemetryAttribution = false,
        withMapboxAttribution = false,
        withMapboxPrivacyPolicy = false,
        withMapboxGeofencingConsent = true,
      )
    )

    assertEquals(2, attributions.size)
    assertEquals(
      "Geofencing Consent URL should match", Attribution.GEOFENCING_URL_MARKER,
      attributions.last().url
    )
    assertEquals(
      "Geofencing Consent title should match", Attribution.GEOFENCING,
      attributions.last().title
    )

    attributions = mapAttributionDelegate.parseAttributions(
      context,
      AttributionParserConfig(
        withImproveMap = false,
        withTelemetryAttribution = false,
        withMapboxAttribution = true,
        withMapboxPrivacyPolicy = false,
        withMapboxGeofencingConsent = false,
      )
    )

    assertEquals(2, attributions.size)
    assertEquals(
      "URL mapbox should match", "https://www.mapbox.com/about/maps/",
      attributions.first().url
    )
    assertEquals(
      "Title mapbox should match", "© Mapbox", attributions.first().title
    )

    mapAttributionDelegate.extraAttributions = listOf(Attribution("Custom One", "https://www.custom.com"))
    attributions = mapAttributionDelegate.parseAttributions(
      context,
      AttributionParserConfig(
        withImproveMap = false,
        withTelemetryAttribution = false,
        withMapboxAttribution = false,
        withMapboxPrivacyPolicy = false,
        withMapboxGeofencingConsent = false,
      )
    )

    assertEquals(2, attributions.size)
    assertEquals("https://www.custom.com", attributions.last().url)
    assertEquals("Custom One", attributions.last().title)

    mapAttributionDelegate.extraAttributions = emptyList()
    attributions = mapAttributionDelegate.parseAttributions(context, AttributionParserConfig())
    println(attributions)
    assertEquals(6, attributions.size)
    var counter = 0
    for ((title, url) in attributions) {
      when (counter) {
        0 -> {
          assertEquals(
            "URL mapbox should match", "https://www.mapbox.com/about/maps/",
            url
          )
          assertEquals("Title mapbox should match", "© Mapbox", title)
        }
        1 -> {
          assertEquals(
            "URL openstreetmap should match", "http://www.openstreetmap.org/about/",
            url
          )
          assertEquals(
            "Title openstreetmap should match", "© OpenStreetMap",
            title
          )
        }
        2 -> {
          assertEquals(
            "URL improve map should match", "https://www.mapbox.com/map-feedback/",
            url
          )
          assertEquals(
            "Title improve map should match", "Improve This Map",
            title
          )
        }
        3 -> {
          assertEquals(
            "Telemetry URL should match", "https://www.mapbox.com/telemetry/",
            url
          )
          assertEquals(
            "Telemetry title should match", "Mapbox Telemetry",
            title
          )
        }
        4 -> {
          assertEquals(
            "Geofencing consent URL should match", Attribution.GEOFENCING_URL_MARKER,
            url
          )
          assertEquals(
            "Geofencing consent title should match", Attribution.GEOFENCING,
            title
          )
        }
        5 -> {
          assertEquals(
            "Telemetry URL should match", "https://www.mapbox.com/legal/privacy#product-privacy-policy/",
            url
          )
          assertEquals(
            "Telemetry title should match", "Mapbox Privacy Policy",
            title
          )
        }
      }
      counter++
    }
  }

  companion object {
    private const val TOKEN = "token"
    private const val STYLE_URL = "mapbox://styles/mapbox/light-v10"
    private const val ATTRIBUTION =
      "<a href=\"https://www.mapbox.com/about/maps/\" target=\"_blank\">&copy; Mapbox</a> <a href=\"http://www.openstreetmap.org/about/\" target=\"_blank\">&copy; OpenStreetMap</a> <a class=\"mapbox-improve-map\" href=\"https://www.mapbox.com/map-feedback/\" target=\"_blank\">Improve this map</a>"
  }
}