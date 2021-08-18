package com.mapbox.maps.plugin.attribution

import android.content.DialogInterface
import androidx.test.core.app.ApplicationProvider
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AttributionDialogManagerImplTest {

  private lateinit var attributionDialogManagerImpl: AttributionDialogManagerImpl
  private val telemetry: MapTelemetry = mockk()
  private val mapAttributionDelegate: MapAttributionDelegate = mockk()

  @Before
  fun setUp() {
    every { mapAttributionDelegate.telemetry() } returns telemetry
    every {
      mapAttributionDelegate.parseAttributions(
        any(),
        any()
      )
    } returns listOf(
      Attribution("feadback", "feadback_url"),
      Attribution("Telemetry", "telemetry_url")
    )
    attributionDialogManagerImpl = AttributionDialogManagerImpl(
      ApplicationProvider.getApplicationContext()
    )
  }

  @Test
  fun showAttribution() {
    assertNull(attributionDialogManagerImpl.dialog)
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    assertNotNull(attributionDialogManagerImpl.dialog)
    assertTrue(attributionDialogManagerImpl.dialog!!.isShowing)
    attributionDialogManagerImpl.onStop()
    assertFalse(attributionDialogManagerImpl.dialog!!.isShowing)
  }

  @Test
  fun onClick() {
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    attributionDialogManagerImpl.onClick(mockk(), 0)
  }

  @Test
  fun onClickTelemetry() {
    assertNull(attributionDialogManagerImpl.telemetryDialog)
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    attributionDialogManagerImpl.onClick(mockk(), 1)
    assertNotNull(attributionDialogManagerImpl.telemetryDialog)
    assertTrue(attributionDialogManagerImpl.telemetryDialog!!.isShowing)
    attributionDialogManagerImpl.onStop()
    assertFalse(attributionDialogManagerImpl.telemetryDialog!!.isShowing)
  }

  @Test
  fun onClickTelemetryPositive() {
    val slot = slot<Boolean>()
    every { telemetry.setUserTelemetryRequestState(capture(slot)) } just Runs
    assertNull(attributionDialogManagerImpl.telemetryDialog)
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    attributionDialogManagerImpl.onClick(mockk(), 1)
    assertNotNull(attributionDialogManagerImpl.telemetryDialog)
    assertTrue(attributionDialogManagerImpl.telemetryDialog!!.isShowing)
    val button =
      attributionDialogManagerImpl.telemetryDialog!!.getButton(DialogInterface.BUTTON_POSITIVE)
    assertNotNull(button)
    button.performClick()
    assertFalse(attributionDialogManagerImpl.telemetryDialog!!.isShowing)
    assertTrue(slot.captured)
  }

  @Test
  fun onClickTelemetryNegativeButton() {
    val slot = slot<Boolean>()
    every { telemetry.setUserTelemetryRequestState(capture(slot)) } just Runs
    assertNull(attributionDialogManagerImpl.telemetryDialog)
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    attributionDialogManagerImpl.onClick(mockk(), 1)
    assertNotNull(attributionDialogManagerImpl.telemetryDialog)
    assertTrue(attributionDialogManagerImpl.telemetryDialog!!.isShowing)
    val button =
      attributionDialogManagerImpl.telemetryDialog!!.getButton(DialogInterface.BUTTON_NEGATIVE)
    assertNotNull(button)
    button.performClick()
    assertFalse(attributionDialogManagerImpl.telemetryDialog!!.isShowing)
    assertFalse(slot.captured)
  }

  @Test
  fun onClickTelemetryNeutralButton() {
    assertNull(attributionDialogManagerImpl.telemetryDialog)
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    attributionDialogManagerImpl.onClick(mockk(), 1)
    assertNotNull(attributionDialogManagerImpl.telemetryDialog)
    assertTrue(attributionDialogManagerImpl.telemetryDialog!!.isShowing)
    val button =
      attributionDialogManagerImpl.telemetryDialog!!.getButton(DialogInterface.BUTTON_NEUTRAL)
    assertNotNull(button)
    button.performClick()
    assertFalse(attributionDialogManagerImpl.telemetryDialog!!.isShowing)
  }
}