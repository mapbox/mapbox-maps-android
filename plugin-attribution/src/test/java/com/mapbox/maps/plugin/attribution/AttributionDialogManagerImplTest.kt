package com.mapbox.maps.plugin.attribution

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
class AttributionDialogManagerImplTest {

  private lateinit var attributionDialogManagerImpl: AttributionDialogManagerImpl
  private val telemetry: MapTelemetry = mockk()
  @OptIn(MapboxExperimental::class)
  private val geofencingConsent: MapGeofencingConsent = mockk()
  private val mapAttributionDelegate: MapAttributionDelegate = mockk()

  @OptIn(MapboxExperimental::class)
  @Before
  fun setUp() {
    every { mapAttributionDelegate.telemetry() } returns telemetry
    every { mapAttributionDelegate.geofencingConsent() } returns geofencingConsent
    every { geofencingConsent.shouldShowConsent() } returns true
    every {
      mapAttributionDelegate.parseAttributions(
        any(),
        any()
      )
    } returns listOf(
      Attribution("feadback", "feadback_url"),
      Attribution("Telemetry", Attribution.ABOUT_TELEMETRY_URL),
      Attribution(Attribution.GEOFENCING, Attribution.GEOFENCING_URL_MARKER),
    )
    attributionDialogManagerImpl = AttributionDialogManagerImpl(
      Robolectric.buildActivity(AppCompatActivity::class.java).get().also {
        it.setTheme(R.style.Theme_AppCompat_Dialog_Alert)
      }
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

  @OptIn(MapboxExperimental::class)
  @Test
  fun onClickGeofencingConsentWithConsentGiven() {
    assertNull(attributionDialogManagerImpl.geofencingDialog)
    every { geofencingConsent.getUserConsent() } returns true
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    attributionDialogManagerImpl.onClick(mockk(), 2)
    assertNotNull(attributionDialogManagerImpl.geofencingDialog)
    val geofencingDialog = attributionDialogManagerImpl.geofencingDialog!!
    assertTrue(geofencingDialog.isShowing)
    attributionDialogManagerImpl.onStop()
    assertFalse(geofencingDialog.isShowing)
    val expectedPositive = geofencingDialog.context.getString(R.string.mapbox_attributionGeofencingConsentedPositive)
    assertEquals(expectedPositive, geofencingDialog.getButton(DialogInterface.BUTTON_POSITIVE).text)
    val expectedNegative = geofencingDialog.context.getString(R.string.mapbox_attributionGeofencingConsentedNegative)
    assertEquals(expectedNegative, geofencingDialog.getButton(DialogInterface.BUTTON_NEGATIVE).text)
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun onClickGeofencingConsentWithConsentRevoked() {
    assertNull(attributionDialogManagerImpl.geofencingDialog)
    every { geofencingConsent.getUserConsent() } returns false
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    attributionDialogManagerImpl.onClick(mockk(), 2)
    assertNotNull(attributionDialogManagerImpl.geofencingDialog)
    val geofencingDialog = attributionDialogManagerImpl.geofencingDialog!!
    assertTrue(geofencingDialog.isShowing)
    attributionDialogManagerImpl.onStop()
    assertFalse(geofencingDialog.isShowing)
    val expectedPositive = geofencingDialog.context.getString(R.string.mapbox_attributionGeofencingRevokedPositive)
    assertEquals(expectedPositive, geofencingDialog.getButton(DialogInterface.BUTTON_POSITIVE).text)
    val expectedNegative = geofencingDialog.context.getString(R.string.mapbox_attributionGeofencingRevokedNegative)
    assertEquals(expectedNegative, geofencingDialog.getButton(DialogInterface.BUTTON_NEGATIVE).text)
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun onClickGeofencingConsentPositive() {
    val slot = slot<Boolean>()
    every { geofencingConsent.setUserConsent(capture(slot), any()) } just Runs
    assertNull(attributionDialogManagerImpl.geofencingDialog)
    every { geofencingConsent.getUserConsent() } returns true
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    attributionDialogManagerImpl.onClick(mockk(), 2)
    val geofencingDialog = attributionDialogManagerImpl.geofencingDialog!!
    assertTrue(geofencingDialog.isShowing)
    geofencingDialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()
    ShadowLooper.runUiThreadTasks()
    assertFalse(geofencingDialog.isShowing)
    assertTrue(slot.captured)
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun onClickGeofencingConsentNegative() {
    val slot = slot<Boolean>()
    every { geofencingConsent.setUserConsent(capture(slot), any()) } just Runs
    assertNull(attributionDialogManagerImpl.geofencingDialog)
    every { geofencingConsent.getUserConsent() } returns true
    attributionDialogManagerImpl.showAttribution(mapAttributionDelegate)
    attributionDialogManagerImpl.onClick(mockk(), 2)
    val geofencingDialog = attributionDialogManagerImpl.geofencingDialog!!
    assertTrue(geofencingDialog.isShowing)
    geofencingDialog.getButton(DialogInterface.BUTTON_NEGATIVE).performClick()
    ShadowLooper.runUiThreadTasks()
    assertFalse(geofencingDialog.isShowing)
    assertFalse(slot.captured)
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
    ShadowLooper.runUiThreadTasks()
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
    ShadowLooper.runUiThreadTasks()
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
    ShadowLooper.runUiThreadTasks()
    assertFalse(attributionDialogManagerImpl.telemetryDialog!!.isShowing)
  }
}