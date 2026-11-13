package com.mapbox.maps.plugin.attribution

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

/**
 * Unit tests for [AttributionDialogManagerImpl], which shows and dismisses the attribution sheet.
 *
 * The sheet's own contents are covered by [AttributionSheetViewTest]; this covers what the manager
 * decides around it - whether a sheet is shown at all, and that it does not outlive the map.
 */
@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class AttributionDialogManagerImplTest {

  private lateinit var controller: ActivityController<AppCompatActivity>
  private lateinit var activity: AppCompatActivity

  private val telemetry: MapTelemetry = mockk(relaxed = true)
  private val geofencingConsent: MapGeofencingConsent = mockk(relaxed = true)
  private val mapAttributionDelegate: MapAttributionDelegate = mockk(relaxed = true)

  @Before
  fun setUp() {
    controller = Robolectric.buildActivity(AppCompatActivity::class.java)
    activity = controller.get().also { it.setTheme(R.style.Theme_AppCompat_Dialog_Alert) }
    every { mapAttributionDelegate.telemetry() } returns telemetry
    every { mapAttributionDelegate.geofencingConsent() } returns geofencingConsent
    every { mapAttributionDelegate.parseAttributions(any(), any()) } returns
      listOf(Attribution("© Mapbox", "https://mapbox.com/about/"))
  }

  @Test
  fun `showAttribution shows the sheet`() {
    val manager = AttributionDialogManagerImpl(activity)

    manager.showAttribution(mapAttributionDelegate)

    assertNotNull(manager.sheetDialog)
    assertTrue(manager.sheetDialog!!.isShowing)
  }

  @Test
  fun `showAttribution keeps the sheet it already has`() {
    val manager = AttributionDialogManagerImpl(activity)

    manager.showAttribution(mapAttributionDelegate)
    val first = manager.sheetDialog
    manager.showAttribution(mapAttributionDelegate)

    assertTrue(first === manager.sheetDialog)
  }

  @Test
  fun `onStop dismisses the sheet, so it cannot outlive the map`() {
    val manager = AttributionDialogManagerImpl(activity)
    manager.showAttribution(mapAttributionDelegate)
    val dialog = manager.sheetDialog!!

    manager.onStop()

    assertFalse(dialog.isShowing)
    assertNull(manager.sheetDialog)
  }

  @Test
  fun `a finishing activity gets no sheet`() {
    val manager = AttributionDialogManagerImpl(activity)
    activity.finish()

    manager.showAttribution(mapAttributionDelegate)

    assertNull(manager.sheetDialog)
  }

  @Test
  fun `a finishing activity behind a context wrapper gets no sheet`() {
    // What a MapView is actually handed once anything in the hierarchy carries android:theme.
    val manager = AttributionDialogManagerImpl(
      ContextThemeWrapper(activity, R.style.Theme_AppCompat_Dialog_Alert)
    )
    activity.finish()

    manager.showAttribution(mapAttributionDelegate)

    assertNull(manager.sheetDialog)
  }
}