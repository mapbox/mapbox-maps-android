package com.mapbox.maps.plugin.consolidatedattribution

import android.content.Context
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.attribution.Attribution
import com.mapbox.maps.plugin.attribution.AttributionDialogManager
import com.mapbox.maps.plugin.attribution.AttributionParserConfig
import com.mapbox.maps.plugin.attribution.AttributionPlugin
import com.mapbox.maps.plugin.attribution.setHiddenByConsolidatedAttribution
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapListenerDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.logo.LogoPlugin
import com.mapbox.maps.plugin.logo.setHiddenByConsolidatedAttribution
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowDialog

/**
 * Unit tests for the pill label [ConsolidatedAttributionPluginImpl] derives from the style's
 * attributions. Mirrors the iOS `consolidatedAttributionText(from:)` expectations: every
 * copyright row the sheet renders reaches the label, not a curated provider list.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ConsolidatedAttributionPluginImplTest {

  private lateinit var context: Context
  private lateinit var view: ConsolidatedAttributionView
  private val attributionDelegate = mockk<MapAttributionDelegate>(relaxed = true)

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    ConsolidatedAttributionView.introFlagStore = InMemoryIntroFlagStore()
    ConsolidatedAttributionView.hasShownExpandedInProcess = false
    view = ConsolidatedAttributionView(context)
    mockkStatic(STYLE_ATTRIBUTIONS_CHANGED_EXT)
    every { any<MapboxStyleManager>().subscribeStyleAttributionsChanged(any()) } returns
      mockk(relaxed = true)
  }

  @After
  fun tearDown() {
    unmockkStatic(STYLE_ATTRIBUTIONS_CHANGED_EXT)
  }

  @Test
  fun `label renders every copyright row, not just known providers`() {
    assertEquals(
      "© OpenStreetMap © TomTom © Airbus",
      attributionText(
        Attribution("OpenStreetMap", "https://www.openstreetmap.org/about/"),
        Attribution("TomTom", "https://tomtom.com/"),
        Attribution("Airbus", ""),
      )
    )
  }

  @Test
  fun `label drops the mapbox row, which the wordmark already carries`() {
    assertEquals(
      "© OpenStreetMap",
      attributionText(
        Attribution("Mapbox", Attribution.ABOUT_MAPS_URL),
        Attribution("OpenStreetMap", "https://www.openstreetmap.org/about/"),
      )
    )
  }

  @Test
  fun `label drops feedback rows and blank titles`() {
    assertEquals(
      "© Zenrin",
      attributionText(
        Attribution("Improve this map", "https://www.mapbox.com/feedback/"),
        Attribution("Contribute", "https://www.mapbox.com/contribute/"),
        Attribution("  ", ""),
        Attribution("Zenrin", ""),
      )
    )
  }

  @Test
  fun `label is empty when the style declares no data providers`() {
    assertEquals("", attributionText())
  }

  @Test
  fun `label requests copyright-free titles and no sheet-only rows`() {
    val config = slot<AttributionParserConfig>()
    every { attributionDelegate.parseAttributions(any(), capture(config)) } returns emptyList()

    bindPlugin()

    with(config.captured) {
      assertEquals(false, withCopyrightSign)
      assertEquals(false, withImproveMap)
      assertEquals(false, withMapboxAttribution)
      assertEquals(false, withTelemetryAttribution)
      assertEquals(false, withMapboxPrivacyPolicy)
      assertEquals(false, withMapboxGeofencingConsent)
    }
  }

  private fun attributionText(vararg attributions: Attribution): String {
    every { attributionDelegate.parseAttributions(any(), any()) } returns attributions.toList()
    bindPlugin()
    // attributionLabel is the third child (index 2) of the pill's LinearLayout.
    return (view.getChildAt(2) as TextView).text.toString()
  }

  private fun bindPlugin(
    logo: LogoPlugin? = null,
    attribution: AttributionPlugin? = null,
  ): ConsolidatedAttributionPluginImpl {
    val plugin = ConsolidatedAttributionPluginImpl { view }
    val mapView = FrameLayout(context)
    plugin.bind(mapView, null, 1f)
    mapView.addView(view)
    plugin.onPluginView(view)
    // The gestures plugin is absent here: only collapse-on-interaction needs it, and a relaxed
    // mock would hand back a MapPlugin that cannot be cast to GesturesPlugin.
    val pluginProvider = mockk<MapPluginProviderDelegate>(relaxed = true)
    every { pluginProvider.getPlugin<GesturesPlugin>(any()) } returns null
    every { pluginProvider.getPlugin<LogoPlugin>(Plugin.MAPBOX_LOGO_PLUGIN_ID) } returns logo
    every {
      pluginProvider.getPlugin<AttributionPlugin>(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID)
    } returns attribution
    plugin.onDelegateProvider(
      mockk<MapDelegateProvider>(relaxed = true).also {
        every { it.mapAttributionDelegate } returns attributionDelegate
        every { it.mapListenerDelegate } returns mockk<MapListenerDelegate>(relaxed = true)
        every { it.mapPluginProviderDelegate } returns pluginProvider
      }
    )
    plugin.initialize()
    // The control is off by default; the label is only derived while it is enabled.
    plugin.updateSettings { enabled = true }
    return plugin
  }

  @Test
  fun `the control hides the logo and the attribution button while enabled`() {
    mockkStatic(LOGO_EXT, ATTRIBUTION_EXT)
    val logo = mockk<LogoPlugin>()
    val attribution = mockk<AttributionPlugin>()
    every { logo.setHiddenByConsolidatedAttribution(any()) } just Runs
    every { attribution.setHiddenByConsolidatedAttribution(any()) } just Runs

    val plugin = bindPlugin(logo, attribution)
    verify { logo.setHiddenByConsolidatedAttribution(true) }
    verify { attribution.setHiddenByConsolidatedAttribution(true) }

    plugin.updateSettings { enabled = false }
    verify { logo.setHiddenByConsolidatedAttribution(false) }
    verify { attribution.setHiddenByConsolidatedAttribution(false) }
    unmockkStatic(LOGO_EXT, ATTRIBUTION_EXT)
  }

  @Test
  fun `re-applying the default dialog manager keeps the open sheet`() {
    val activity = Robolectric.buildActivity(AppCompatActivity::class.java).get().also {
      it.setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight)
    }
    every { attributionDelegate.telemetry() } returns mockk(relaxed = true)
    every { attributionDelegate.parseAttributions(any(), any()) } returns
      listOf(Attribution("© Mapbox", "https://mapbox.com/about/"))
    val pill = ConsolidatedAttributionView(activity)
    val controller = ConsolidatedAttributionController(pill)
    controller.onDelegateProvider(
      mockk<MapDelegateProvider>(relaxed = true).also {
        every { it.mapAttributionDelegate } returns attributionDelegate
        every { it.mapPluginProviderDelegate } returns
          mockk<MapPluginProviderDelegate>(relaxed = true).also { provider ->
            every { provider.getPlugin<GesturesPlugin>(any()) } returns null
          }
      }
    )
    controller.attachTapHandling(activity)
    pill.performClick()
    val sheet = ShadowDialog.getLatestDialog()
    assertTrue(sheet.isShowing)

    // Null is the "use the SDK's own sheet" default, and a Compose host re-applies it on every
    // recomposition - which must not dismiss the sheet the user is reading.
    controller.setCustomAttributionDialogManager(null)

    assertTrue(sheet.isShowing)
  }

  @Test
  fun `withdrawing a custom dialog manager stops it`() {
    val controller = ConsolidatedAttributionController(view)
    controller.attachTapHandling(context)
    val custom = mockk<AttributionDialogManager>(relaxed = true)
    controller.setCustomAttributionDialogManager(custom)

    controller.setCustomAttributionDialogManager(null)

    verify(exactly = 1) { custom.onStop() }
  }
}

private const val LOGO_EXT = "com.mapbox.maps.plugin.logo.LogoUtils"
private const val ATTRIBUTION_EXT = "com.mapbox.maps.plugin.attribution.AttributionUtils"