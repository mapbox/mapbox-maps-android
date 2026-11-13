package com.mapbox.maps.plugin.consolidatedattribution

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mapbox.maps.module.TelemetryEvent
import com.mapbox.maps.plugin.consolidatedattribution.generated.ConsolidatedAttributionSettings
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * The opt-in counter behind "how many apps enable the consolidated attribution control": it is
 * reported once per control that is enabled, and not at all for one left at its default.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ConsolidatedAttributionTelemetryTest {

  private lateinit var context: Context
  private lateinit var controller: ConsolidatedAttributionController
  private val counter = mockk<TelemetryEvent>(relaxed = true)

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    ConsolidatedAttributionView.introFlagStore = InMemoryIntroFlagStore()
    ConsolidatedAttributionView.hasShownExpandedInProcess = false
    controller = ConsolidatedAttributionController(ConsolidatedAttributionView(context))
    mockkObject(ConsolidatedAttributionTelemetryEvents)
    every { ConsolidatedAttributionTelemetryEvents.enabled } returns counter
  }

  @After
  fun tearDown() {
    unmockkObject(ConsolidatedAttributionTelemetryEvents)
  }

  @Test
  fun `a control left disabled reports nothing`() {
    controller.applyAppearance(ConsolidatedAttributionSettings { })

    verify(exactly = 0) { counter.increment() }
  }

  @Test
  fun `enabling the control reports once, whatever else changes afterwards`() {
    controller.applyAppearance(ConsolidatedAttributionSettings { enabled = true })
    controller.applyAppearance(
      ConsolidatedAttributionSettings {
        enabled = true
        marginLeft = 24f
      }
    )
    controller.applyAppearance(ConsolidatedAttributionSettings { enabled = false })
    controller.applyAppearance(ConsolidatedAttributionSettings { enabled = true })

    verify(exactly = 1) { counter.increment() }
  }
}