package com.mapbox.maps.plugin.attribution

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
import org.robolectric.annotation.Config

/**
 * Unit tests for [AttributionSheetView], the sheet body shown in every host.
 *
 * Inflates the real layout through Robolectric and drives the rows the view builds from a payload.
 * Ported from the tests of the removed `AttributionBottomSheetFragment`, which covered this same
 * logic while it still lived in the fragment.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class AttributionSheetViewTest {

  private lateinit var activity: AppCompatActivity

  private val telemetry: MapTelemetry = mockk(relaxed = true)

  @OptIn(MapboxExperimental::class)
  private val geofencingConsent: MapGeofencingConsent = mockk(relaxed = true)
  private val mapAttributionDelegate: MapAttributionDelegate = mockk(relaxed = true)

  @Before
  fun setUp() {
    // The rows use AppCompat widgets, so the host theme has to be one.
    activity = Robolectric.buildActivity(AppCompatActivity::class.java).get().also {
      it.setTheme(R.style.Theme_AppCompat_Dialog_Alert)
    }
  }

  @OptIn(MapboxExperimental::class)
  private fun showSheet(
    attributions: List<Attribution> = listOf(Attribution("© Mapbox", "https://mapbox.com/about/")),
    telemetry: MapTelemetry? = this.telemetry,
    geofencingConsent: MapGeofencingConsent? = this.geofencingConsent,
    onDismissRequest: () -> Unit = {},
  ): AttributionSheetView = AttributionSheetView(activity).apply {
    bind(
      payload = AttributionSheetPayload(
        attributionList = attributions,
        mapAttributionDelegate = mapAttributionDelegate,
        telemetry = telemetry,
        geofencingConsent = geofencingConsent,
      ),
      onDismissRequest = onDismissRequest,
    )
  }

  private fun AttributionSheetView.items(): LinearLayout =
    findViewById(R.id.mapbox_attribution_items_container)

  private fun AttributionSheetView.switches(): List<Switch> {
    val container = items()
    return (0 until container.childCount)
      .map { container.getChildAt(it) }
      .filterIsInstance<LinearLayout>()
      .flatMap { row -> (0 until row.childCount).map { row.getChildAt(it) } }
      .filterIsInstance<Switch>()
  }

  /** The plain dividing views the sheet inserted between its row groups. */
  private fun AttributionSheetView.dividers(): List<android.view.View> {
    val container = items()
    return (0 until container.childCount)
      .map { container.getChildAt(it) }
      .filter { it !is TextView && it !is LinearLayout }
  }

  private fun AttributionSheetView.rowTitles(): List<String> {
    val container = items()
    return (0 until container.childCount)
      .map { container.getChildAt(it) }
      .filterIsInstance<TextView>()
      .map { it.text.toString() }
  }

  @Test
  fun telemetrySwitchCheckedWhenTelemetryParticipating() {
    every { telemetry.userTelemetryRequestState } returns true

    assertTrue(showSheet().switches().first().isChecked)
  }

  @Test
  fun telemetrySwitchUncheckedWhenTelemetryNotParticipating() {
    every { telemetry.userTelemetryRequestState } returns false

    assertFalse(showSheet().switches().first().isChecked)
  }

  @Test
  fun telemetrySwitchToggleWritesStateToMapTelemetry() {
    every { telemetry.userTelemetryRequestState } returns false

    showSheet().switches().first().isChecked = true

    verify { telemetry.userTelemetryRequestState = true }
  }

  @Test
  fun telemetryDescriptionFollowsSwitchState() {
    every { telemetry.userTelemetryRequestState } returns true
    val sheet = showSheet()
    val enabledText = sheet.rowTitles().single { it.contains("anonymized") }

    sheet.switches().first().isChecked = false

    val disabledText = sheet.rowTitles().single { it.contains("OpenStreetMap") && it != enabledText }
    assertEquals(
      activity.getString(R.string.mapbox_attribution_telemetry_message_disabled) + " " +
        activity.getString(R.string.mapbox_attribution_telemetry_learn_more),
      disabledText
    )
  }

  @Test
  fun telemetryRowAbsentWhenTelemetryIsNull() {
    assertTrue(showSheet(telemetry = null).switches().none())
  }

  @Test
  fun geofencingRowHiddenWhenShouldShowConsentReturnsFalse() {
    every { telemetry.userTelemetryRequestState } returns true
    every { geofencingConsent.shouldShowConsent() } returns false

    // Only the telemetry switch remains.
    assertEquals(1, showSheet().switches().size)
  }

  @Test
  fun geofencingRowVisibleWhenShouldShowConsentReturnsTrue() {
    every { telemetry.userTelemetryRequestState } returns true
    every { geofencingConsent.shouldShowConsent() } returns true

    assertEquals(2, showSheet().switches().size)
  }

  @Test
  fun geofencingSwitchReflectsCurrentConsent() {
    every { telemetry.userTelemetryRequestState } returns true
    every { geofencingConsent.shouldShowConsent() } returns true
    every { geofencingConsent.getUserConsent() } returns true

    assertTrue(showSheet().switches()[1].isChecked)
  }

  @Test
  fun geofencingSwitchOnGrantsConsent() {
    every { telemetry.userTelemetryRequestState } returns true
    every { geofencingConsent.shouldShowConsent() } returns true
    every { geofencingConsent.getUserConsent() } returns false

    showSheet().switches()[1].isChecked = true

    verify { geofencingConsent.setUserConsent(true, any()) }
  }

  @Test
  fun geofencingSwitchOffRevokesConsent() {
    every { telemetry.userTelemetryRequestState } returns true
    every { geofencingConsent.shouldShowConsent() } returns true
    every { geofencingConsent.getUserConsent() } returns true

    showSheet().switches()[1].isChecked = false

    verify { geofencingConsent.setUserConsent(false, any()) }
  }

  @Test
  fun bottomGapGrowsByTheNavigationBarInset() {
    val sheet = showSheet()
    val bottomInset = sheet.findViewById<View>(R.id.mapbox_attribution_bottom_inset)
    val minHeight = bottomInset.layoutParams.height

    ViewCompat.dispatchApplyWindowInsets(
      sheet,
      WindowInsetsCompat.Builder()
        .setInsets(WindowInsetsCompat.Type.navigationBars(), Insets.of(10, 0, 20, 48))
        .build(),
    )

    assertEquals(minHeight + 48, bottomInset.layoutParams.height)
    assertEquals(10, sheet.getChildAt(0).paddingLeft)
    assertEquals(20, sheet.getChildAt(0).paddingRight)
  }

  @Test
  fun switchesAreLinkedToTheirLabels() {
    every { geofencingConsent.shouldShowConsent() } returns true

    val sheet = showSheet()
    val labels = sheet.switches().map { switch ->
      val row = switch.parent as LinearLayout
      (0 until row.childCount)
        .map { row.getChildAt(it) }
        .filterIsInstance<TextView>()
        .single { it.labelFor == switch.id }
        .text.toString()
    }

    assertEquals(
      listOf(
        activity.getString(R.string.mapbox_attribution_telemetry_switch_label),
        activity.getString(R.string.mapbox_attribution_geofencing_switch_label),
      ),
      labels,
    )
  }

  @Test
  fun switchRowsAreLabelledLikeIos() {
    every { telemetry.userTelemetryRequestState } returns true
    every { geofencingConsent.shouldShowConsent() } returns true

    val container = showSheet().items()
    val labels = (0 until container.childCount)
      .map { container.getChildAt(it) }
      .filterIsInstance<LinearLayout>()
      .map { (it.getChildAt(0) as TextView).text.toString() }
    assertEquals(listOf("Mapbox Telemetry", "Mapbox Geofencing"), labels)
  }

  @Test
  fun geofencingRowExplainsGeofencing() {
    every { telemetry.userTelemetryRequestState } returns true
    every { geofencingConsent.shouldShowConsent() } returns true

    val sheet = showSheet()
    val message = sheet.context.getString(R.string.mapbox_attributionGeofencingMessage)
    assertTrue(sheet.rowTitles().contains(message))
  }

  @Test
  fun dataAttributionLinksAreRendered() {
    val sheet = showSheet(
      attributions = listOf(
        Attribution("© Mapbox", "https://www.mapbox.com/about/maps/"),
        Attribution("© OpenStreetMap", "https://www.openstreetmap.org/about/"),
      ),
      telemetry = null,
      geofencingConsent = null,
    )

    val titles = sheet.rowTitles()
    assertTrue(titles.contains("© Mapbox"))
    assertTrue(titles.contains("© OpenStreetMap"))
  }

  @Test
  fun telemetryAndGeofencingEntriesAreFilteredFromDataLinks() {
    val sheet = showSheet(
      attributions = listOf(
        Attribution("© Mapbox", "https://www.mapbox.com/about/maps/"),
        Attribution("Telemetry", Attribution.ABOUT_TELEMETRY_URL),
        Attribution(Attribution.GEOFENCING, Attribution.GEOFENCING_URL_MARKER),
      ),
      telemetry = null,
      geofencingConsent = null,
    )

    val titles = sheet.rowTitles()
    assertTrue(titles.contains("© Mapbox"))
    assertFalse(titles.contains("Telemetry"))
    assertFalse(titles.contains(Attribution.GEOFENCING))
  }

  @Test
  fun feedbackRowOpensUrlRebuiltFromTheLiveMap() {
    every { mapAttributionDelegate.buildMapBoxFeedbackUrl(any()) } returns "https://rebuilt/feedback"
    val sheet = showSheet(
      attributions = listOf(Attribution("Improve This Map", "https://apps.mapbox.com/feedback/")),
      telemetry = null,
      geofencingConsent = null,
    )

    val row = sheet.items().let { container ->
      (0 until container.childCount)
        .map { container.getChildAt(it) }
        .filterIsInstance<TextView>()
        .single { it.text == "Improve This Map" }
    }
    row.performClick()

    verify { mapAttributionDelegate.buildMapBoxFeedbackUrl(any()) }
  }

  @Test
  fun linkRowClosesTheSheet() {
    var dismissed = false
    val sheet = showSheet(
      attributions = listOf(Attribution("© OpenStreetMap", "https://www.openstreetmap.org/copyright")),
      telemetry = null,
      geofencingConsent = null,
      onDismissRequest = { dismissed = true },
    )

    sheet.items().getChildAt(0).performClick()

    assertTrue(dismissed)
  }

  @Test
  fun rowWithoutUrlIsNotClickable() {
    val sheet = showSheet(
      attributions = listOf(Attribution("No link", "")),
      telemetry = null,
      geofencingConsent = null,
    )

    val row = sheet.items().getChildAt(0) as TextView
    assertFalse(row.hasOnClickListeners())
  }

  @Test
  fun closeButtonInvokesDismissRequest() {
    var dismissed = false
    val sheet = showSheet(onDismissRequest = { dismissed = true })

    sheet.findViewById<android.view.View>(R.id.mapbox_attribution_close_button).performClick()

    assertTrue(dismissed)
  }

  @Test
  fun sheetConsumesTouchesSoTapsDoNotReachTheScrimBehindIt() {
    assertTrue(showSheet().isClickable)
  }

  @Test
  fun rebindingReplacesRowsInsteadOfAppendingThem() {
    val sheet = showSheet(
      attributions = listOf(Attribution("© Mapbox", "https://mapbox.com/about/")),
      telemetry = null,
      geofencingConsent = null,
    )
    val firstCount = sheet.items().childCount

    sheet.bind(
      payload = AttributionSheetPayload(
        attributionList = listOf(Attribution("© Mapbox", "https://mapbox.com/about/")),
        mapAttributionDelegate = mapAttributionDelegate,
        telemetry = null,
        geofencingConsent = null,
      ),
      onDismissRequest = {},
    )

    assertEquals(firstCount, sheet.items().childCount)
  }

  @Test
  fun bottomSpacerIsPresent() {
    assertNotNull(showSheet().findViewById(R.id.mapbox_attribution_bottom_inset))
  }

  @Test
  fun privacyPolicyRowIsRenderedAfterTheConsentSwitches() {
    every { telemetry.userTelemetryRequestState } returns true
    every { geofencingConsent.shouldShowConsent() } returns false
    val sheet = showSheet(
      attributions = listOf(
        Attribution("© Mapbox", "https://www.mapbox.com/about/maps/"),
        Attribution("Mapbox Privacy Policy", "https://www.mapbox.com/legal/privacy"),
      )
    )

    val titles = sheet.rowTitles()
    val privacyIndex = titles.indexOf("Mapbox Privacy Policy")
    val dataIndex = titles.indexOf("© Mapbox")
    assertTrue(privacyIndex > dataIndex)
    assertNull(titles.firstOrNull { it == "privacy" })
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun `data links alone get no trailing divider`() {
    every { geofencingConsent.shouldShowConsent() } returns false
    val sheet = showSheet(
      attributions = listOf(Attribution("© Mapbox", "https://mapbox.com/about/")),
      telemetry = null,
    )

    assertEquals(listOf("© Mapbox"), sheet.rowTitles())
    assertEquals(emptyList<android.view.View>(), sheet.dividers())
  }

  @Test
  fun `a divider separates the data links from the rows below them`() {
    val sheet = showSheet(
      attributions = listOf(Attribution("© Mapbox", "https://mapbox.com/about/"))
    )

    assertEquals(1, sheet.dividers().size)
  }

  @Test
  fun dividersFollowTheThemeTextColor() {
    // The test theme is dark, so the divider must be light.
    val sheet = showSheet(
      attributions = listOf(Attribution("© Mapbox", "https://mapbox.com/about/"))
    )
    val colors = (sheet.dividers() + sheet.findViewById<View>(R.id.mapbox_attribution_header_divider))
      .map { (it.background as ColorDrawable).color }

    colors.forEach {
      assertEquals(0x1F, Color.alpha(it))
      assertTrue(Color.red(it) > 0x80)
    }
  }
}