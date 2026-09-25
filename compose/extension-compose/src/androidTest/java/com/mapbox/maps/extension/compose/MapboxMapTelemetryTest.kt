package com.mapbox.maps.extension.compose

import android.os.Bundle
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.module.telemetry.MapTelemetryMetadata
import com.mapbox.maps.module.telemetry.UiFramework
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * End-to-end instrumented test: composing [MapboxMap] sets [UiFramework.JETPACK_COMPOSE]
 * via [com.mapbox.maps.MapInitOptions.uiFramework], and the turnstile event metadata carries
 * the correct value.
 */
public class MapboxMapTelemetryTest {

  @get:Rule
  public val composeTestRule: ComposeContentTestRule = createComposeRule()

  private val capturedMetadata = mutableListOf<MapTelemetryMetadata?>()

  private val fakeTelemetry: MapTelemetry = object : MapTelemetry {
    override fun onAppUserTurnstileEvent() {}
    override fun onAppUserTurnstileEvent(metadata: MapTelemetryMetadata?) {
      capturedMetadata.add(metadata)
    }
    override fun setUserTelemetryRequestState(enabled: Boolean) {}
    override fun disableTelemetrySession() {}
    override fun onPerformanceEvent(data: Bundle?) {}
  }

  @Before
  public fun setUp() {
    // MapProvider caches mapTelemetry as a singleton lateinit var. Reset it so the
    // MapboxModuleProvider.createModule mock below can inject fakeTelemetry.
    val cls = Class.forName("com.mapbox.maps.MapProvider")
    val instance = cls.getDeclaredField("INSTANCE").apply { isAccessible = true }.get(null)
    cls.getDeclaredField("mapTelemetry").apply { isAccessible = true }.set(instance, null)
  }

  @After
  public fun tearDown() {
    unmockkObject(MapboxModuleProvider)
    capturedMetadata.clear()
  }

  @Test
  public fun mapboxMap_composable_sends_JETPACK_COMPOSE_in_turnstile_metadata() {
    mockkObject(MapboxModuleProvider)
    every { MapboxModuleProvider.createModule<Any>(any(), any()) } returns fakeTelemetry

    composeTestRule.setContent {
      MapboxMap()
    }
    composeTestRule.waitForIdle()

    assertTrue(
      "Expected onAppUserTurnstileEvent to be called at least once",
      capturedMetadata.isNotEmpty()
    )
    assertEquals(
      UiFramework.JETPACK_COMPOSE,
      capturedMetadata.first()?.uiFramework
    )
  }
}