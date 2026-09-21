package com.mapbox.maps.renderer

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.common.SettingsService
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.maps.shadows.ShadowSettingsService
import com.mapbox.maps.shadows.ShadowSettingsServiceFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
  shadows = [
    ShadowSettingsServiceFactory::class,
    ShadowSettingsService::class
  ]
)
class MapboxRenderThreadTest {

  private lateinit var settingsService: SettingsService

  @Before
  fun setUp() {
    mockkStatic(SettingsServiceFactory::class)
    settingsService = mockk()
    every { SettingsServiceFactory.getInstance(any()) } returns settingsService
  }

  @After
  fun tearDown() {
    unmockkStatic(SettingsServiceFactory::class)
  }

  @Test
  fun `resolveLogThrottleIntervalMs returns the settings override when present`() {
    every {
      settingsService.get("com.mapbox.maps.android.renderThreadLogThrottleIntervalMs", any())
    } returns ExpectedFactory.createValue(Value(5_000L))

    assertEquals(5_000L, MapboxRenderThread.resolveLogThrottleIntervalMs())
  }

  @Test
  fun `resolveLogThrottleIntervalMs falls back to the default when the key is missing`() {
    every {
      settingsService.get("com.mapbox.maps.android.renderThreadLogThrottleIntervalMs", any())
    } returns ExpectedFactory.createError("not set")

    assertEquals(MapboxRenderThread.LOG_THROTTLE_INTERVAL_MS, MapboxRenderThread.resolveLogThrottleIntervalMs())
  }

  @Test
  fun `resolveLogThrottleIntervalMs falls back to the default when the value is the wrong type`() {
    every {
      settingsService.get("com.mapbox.maps.android.renderThreadLogThrottleIntervalMs", any())
    } returns ExpectedFactory.createValue(Value(true))

    assertEquals(MapboxRenderThread.LOG_THROTTLE_INTERVAL_MS, MapboxRenderThread.resolveLogThrottleIntervalMs())
  }

  @Test
  fun `resolveLogThrottleIntervalMs is not cached across calls`() {
    every {
      settingsService.get("com.mapbox.maps.android.renderThreadLogThrottleIntervalMs", any())
    } returns ExpectedFactory.createValue(Value(1_000L))
    val first = MapboxRenderThread.resolveLogThrottleIntervalMs()

    every {
      settingsService.get("com.mapbox.maps.android.renderThreadLogThrottleIntervalMs", any())
    } returns ExpectedFactory.createValue(Value(2_000L))
    val second = MapboxRenderThread.resolveLogThrottleIntervalMs()

    assertEquals(1_000L, first)
    assertEquals(2_000L, second)
  }
}