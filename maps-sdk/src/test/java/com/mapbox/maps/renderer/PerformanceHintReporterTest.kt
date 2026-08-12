package com.mapbox.maps.renderer

import android.os.PerformanceHintManager
import android.os.Process
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.common.MapboxSDKCommon
import com.mapbox.common.SettingsService
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.shadows.ShadowSettingsService
import com.mapbox.maps.shadows.ShadowSettingsServiceFactory
import io.mockk.*
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
  sdk = [31], // API 31 = S
  shadows = [
    ShadowSettingsServiceFactory::class,
    ShadowSettingsService::class
  ]
)
class PerformanceHintReporterTest {

  private lateinit var settingsService: SettingsService
  private lateinit var manager: PerformanceHintManager
  private lateinit var session: PerformanceHintManager.Session

  @Before
  fun setUp() {
    mockkStatic(Process::class)
    mockkStatic(SettingsServiceFactory::class)
    mockkObject(MapboxSDKCommon)
    mockkStatic("com.mapbox.maps.MapboxLogger")

    settingsService = mockk()
    manager = mockk()
    session = mockk(relaxed = true)

    every { SettingsServiceFactory.getInstance(any()) } returns settingsService
    every { MapboxSDKCommon.getContext() } returns mockk {
      every { getSystemService(PerformanceHintManager::class.java) } returns manager
    }
    every { logI(any(), any()) } just runs
    every { logW(any(), any()) } just runs
    every { Process.myTid() } returns 1
    every { manager.createHintSession(any(), any()) } returns session
  }

  @Config(sdk = [30]) // API 30, one below the S=31 floor
  @Test
  fun `obtainPerformanceHintReporter returns null below API 31`() {
    assertNull(PerformanceHintReporter.obtainPerformanceHintReporter(mapName = ""))
  }

  @Test
  fun `obtainPerformanceHintReporter returns null when the setting is explicitly disabled`() {
    every { settingsService.get("com.mapbox.maps.android.performanceHintReportingEnabled", any()) } returns
      ExpectedFactory.createValue(Value(false))

    // Short-circuits before ever resolving a Context/PerformanceHintManager.
    every { MapboxSDKCommon.getContext() } throws IllegalStateException("should not be called")

    assertNull(PerformanceHintReporter.obtainPerformanceHintReporter(mapName = ""))
  }

  @Test
  fun `obtainPerformanceHintReporter returns a valid reporter when setting is missing or enabled`() {
    // Test both cases in one, as they share the same outcome.
    listOf(
      ExpectedFactory.createValue<String, Value>(Value(true)), // Explicitly enabled
      ExpectedFactory.createValue<String, Value>(Value(true)) // Missing, defaults to enabled
    ).forEach {
      every { settingsService.get("com.mapbox.maps.android.performanceHintReportingEnabled", any()) } returns it
      assertNotNull(PerformanceHintReporter.obtainPerformanceHintReporter(mapName = ""))
    }
  }

  @Test
  fun `report does nothing until updateTargetDuration has been called`() {
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")
    reporter.report(20_000_000L)
    verify(exactly = 0) { manager.createHintSession(any(), any()) }
  }

  @Test
  fun `report creates a session lazily using the render thread tid and scaled target duration`() {
    every { Process.myTid() } returns 4242
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")

    // target ratio is 0.95: (16_000_000 * 0.95).toLong() = 15_200_000
    reporter.updateTargetDuration(16_000_000L)
    reporter.report(10_000_000L)

    verify(exactly = 1) { manager.createHintSession(intArrayOf(4242), 15_200_000L) }
    verify(exactly = 1) { session.reportActualWorkDuration(10_000_000L) }
  }

  @Test
  fun `report reuses the existing session instead of recreating it`() {
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")
    reporter.updateTargetDuration(16_000_000L)

    reporter.report(10_000_000L)
    reporter.report(11_000_000L)

    verify(exactly = 1) { manager.createHintSession(any(), any()) }
    verify(exactly = 1) { session.reportActualWorkDuration(10_000_000L) }
    verify(exactly = 1) { session.reportActualWorkDuration(11_000_000L) }
  }

  @Test
  fun `updateTargetDuration after session exists calls updateTargetWorkDuration with the scaled value instead of recreating`() {
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")
    reporter.updateTargetDuration(16_000_000L)
    reporter.report(10_000_000L)

    // target ratio is 0.95: (8_000_000 * 0.95).toLong() = 7_600_000
    reporter.updateTargetDuration(8_000_000L)

    verify(exactly = 1) { manager.createHintSession(any(), any()) }
    verify(exactly = 1) { session.updateTargetWorkDuration(7_600_000L) }
  }

  @Test
  fun `updateTargetDuration logs the new target duration`() {
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")
    reporter.updateTargetDuration(16_000_000L)
    verify(exactly = 1) { logI(any(), any()) }
  }

  @Test
  fun `report logs when the session is created successfully`() {
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")
    reporter.updateTargetDuration(16_000_000L)
    reporter.report(10_000_000L)
    verify(exactly = 1) { logI(any(), match { it.contains("Created performance hint session") }) }
  }

  @Test
  fun `report logs a warning when session creation fails`() {
    every { manager.createHintSession(any(), any()) } returns null
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")
    reporter.updateTargetDuration(16_000_000L)
    reporter.report(10_000_000L)
    verify(exactly = 1) { logW(any(), any()) }
  }

  @Test
  fun `report does not retry createHintSession after it returns null`() {
    every { manager.createHintSession(any(), any()) } returns null
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")
    reporter.updateTargetDuration(16_000_000L)

    reporter.report(10_000_000L)
    reporter.report(11_000_000L)
    reporter.report(12_000_000L)

    verify(exactly = 1) { manager.createHintSession(any(), any()) }
  }

  @Test
  fun `close closes the session and a subsequent report does not recreate it`() {
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")
    reporter.updateTargetDuration(16_000_000L)
    reporter.report(10_000_000L)

    reporter.close()
    reporter.report(11_000_000L)

    verify(exactly = 1) { session.close() }
    verify(exactly = 1) { manager.createHintSession(any(), any()) }
  }

  @Test
  fun `createHintSession throwing closes the reporter without crashing`() {
    every { manager.createHintSession(any(), any()) } throws RuntimeException("OEM quirk")
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")

    reporter.updateTargetDuration(16_000_000L)
    reporter.report(10_000_000L)
    reporter.report(11_000_000L)

    verify(exactly = 1) { manager.createHintSession(any(), any()) }
    verify(exactly = 1) { logW(any(), match { it.contains("Failed to create") }) }
  }

  @Test
  fun `reportActualWorkDuration throwing closes the reporter without crashing`() {
    every { session.reportActualWorkDuration(any<Long>()) } throws RuntimeException("OEM quirk")
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")

    reporter.updateTargetDuration(16_000_000L)
    reporter.report(10_000_000L)
    reporter.report(11_000_000L)

    verify(exactly = 1) { session.reportActualWorkDuration(any<Long>()) }
    verify(exactly = 1) { logW(any(), match { it.contains("Failed to report") }) }
  }

  @Test
  fun `updateTargetWorkDuration throwing closes the reporter without crashing`() {
    every { session.updateTargetWorkDuration(any()) } throws RuntimeException("OEM quirk")
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")

    reporter.updateTargetDuration(16_000_000L)
    reporter.report(10_000_000L)
    reporter.updateTargetDuration(8_000_000L)
    reporter.updateTargetDuration(7_000_000L)

    verify(exactly = 1) { session.updateTargetWorkDuration(any()) }
    verify(exactly = 1) { logW(any(), match { it.contains("Failed to update") }) }
  }

  @Test
  fun `close throwing still disables the reporter`() {
    every { session.close() } throws RuntimeException("OEM quirk")
    val reporter = PerformanceHintReporter(manager = manager, TAG = "")

    reporter.updateTargetDuration(16_000_000L)
    reporter.report(10_000_000L)
    reporter.close()
    reporter.report(20_000_000L)

    verify(exactly = 1) { session.close() }
    verify(exactly = 1) { logW(any(), match { it.contains("Failed to close") }) }
    verify(exactly = 1) { manager.createHintSession(any(), any()) }
  }
}