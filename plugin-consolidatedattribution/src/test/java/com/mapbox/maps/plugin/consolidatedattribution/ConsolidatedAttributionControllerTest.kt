package com.mapbox.maps.plugin.consolidatedattribution

import android.content.Context
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StyleAttributionsChanged
import com.mapbox.maps.StyleAttributionsChangedCallback
import com.mapbox.maps.plugin.attribution.Attribution
import com.mapbox.maps.plugin.consolidatedattribution.generated.ConsolidatedAttributionSettings
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapListenerDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMoveListener
import io.mockk.every
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
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Unit tests for the map subscriptions [ConsolidatedAttributionController] holds. Collapsing is
 * all the gesture listeners do, so the controller is only expected to hold them while the pill
 * is expanded.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ConsolidatedAttributionControllerTest {

  private lateinit var context: Context
  private lateinit var view: ConsolidatedAttributionView
  private lateinit var controller: ConsolidatedAttributionController
  private val gesturesPlugin = mockk<GesturesPlugin>(relaxed = true)
  private val attributionDelegate = mockk<MapAttributionDelegate>(relaxed = true)
  private val styleManager = mockk<MapboxStyleManager>(relaxed = true)
  private val attributionsChanged = slot<StyleAttributionsChangedCallback>()

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    ConsolidatedAttributionView.introFlagStore = InMemoryIntroFlagStore()
    ConsolidatedAttributionView.hasShownExpandedInProcess = false
    view = ConsolidatedAttributionView(context)
    controller = ConsolidatedAttributionController(view)
    every { attributionDelegate.parseAttributions(any(), any()) } returns
      listOf(Attribution("OpenStreetMap", "https://www.openstreetmap.org/about/"))
    val pluginProvider = mockk<MapPluginProviderDelegate>(relaxed = true)
    every { pluginProvider.getPlugin<GesturesPlugin>(any()) } returns gesturesPlugin
    mockkStatic(STYLE_ATTRIBUTIONS_CHANGED_EXT)
    every { styleManager.subscribeStyleAttributionsChanged(capture(attributionsChanged)) } returns
      mockk(relaxed = true)
    controller.onDelegateProvider(
      mockk<MapDelegateProvider>(relaxed = true).also {
        every { it.mapAttributionDelegate } returns attributionDelegate
        every { it.mapListenerDelegate } returns mockk<MapListenerDelegate>(relaxed = true)
        every { it.mapPluginProviderDelegate } returns pluginProvider
        every { it.mapStyleManagerDelegate } returns styleManager
      }
    )
    controller.initialize()
  }

  @After
  fun tearDown() {
    unmockkStatic(STYLE_ATTRIBUTIONS_CHANGED_EXT)
  }

  @Test
  fun `no gesture listeners are held before the pill expands`() {
    verify(exactly = 0) { gesturesPlugin.addOnMoveListener(any()) }
  }

  @Test
  fun `gesture listeners are held while the pill is expanded`() {
    expand()

    assertTrue(view.isExpanded)
    verify(exactly = 1) { gesturesPlugin.addOnMapClickListener(any()) }
    verify(exactly = 1) { gesturesPlugin.addOnMapLongClickListener(any()) }
    verify(exactly = 1) { gesturesPlugin.addOnMoveListener(any()) }
    verify(exactly = 1) { gesturesPlugin.addOnScaleListener(any()) }
    verify(exactly = 1) { gesturesPlugin.addOnRotateListener(any()) }
    verify(exactly = 1) { gesturesPlugin.addOnFlingListener(any()) }
  }

  @Test
  fun `gesture listeners are dropped once the pill collapses`() {
    expand()
    view.collapseImmediately()

    verify(exactly = 1) { gesturesPlugin.removeOnMapClickListener(any()) }
    verify(exactly = 1) { gesturesPlugin.removeOnMapLongClickListener(any()) }
    verify(exactly = 1) { gesturesPlugin.removeOnMoveListener(any()) }
    verify(exactly = 1) { gesturesPlugin.removeOnScaleListener(any()) }
    verify(exactly = 1) { gesturesPlugin.removeOnRotateListener(any()) }
    verify(exactly = 1) { gesturesPlugin.removeOnFlingListener(any()) }
  }

  @Test
  fun `the same listener instance is removed as was added`() {
    val added = slot<OnMoveListener>()
    val removed = slot<OnMoveListener>()
    every { gesturesPlugin.addOnMoveListener(capture(added)) } returns Unit
    every { gesturesPlugin.removeOnMoveListener(capture(removed)) } returns Unit

    expand()
    view.collapseImmediately()

    assertTrue(added.captured === removed.captured)
  }

  @Test
  fun `cleanup drops the gesture listeners of an expanded pill`() {
    expand()
    controller.cleanup()

    verify(exactly = 1) { gesturesPlugin.removeOnMoveListener(any()) }
    // The pill collapsing after cleanup must not reach the plugin the controller let go of.
    view.collapseImmediately()
    verify(exactly = 1) { gesturesPlugin.removeOnMoveListener(any()) }
  }

  @Test
  fun `the label follows the style's attributions`() {
    expand()
    every { attributionDelegate.parseAttributions(any(), any()) } returns listOf(
      Attribution("OpenStreetMap", "https://www.openstreetmap.org/about/"),
      Attribution("Vantor", "https://vantor.com"),
    )

    attributionsChanged.captured.run(mockk<StyleAttributionsChanged>(relaxed = true))

    assertEquals("© OpenStreetMap © Vantor", (view.getChildAt(2) as TextView).text.toString())
  }

  /** Play the intro expansion the way a style load does: enable, then hand over the text. */
  private fun expand() {
    controller.applyAppearance(ConsolidatedAttributionSettings { enabled = true })
  }
}

internal const val STYLE_ATTRIBUTIONS_CHANGED_EXT =
  "com.mapbox.maps.plugin.consolidatedattribution.ConsolidatedAttributionControllerKt"