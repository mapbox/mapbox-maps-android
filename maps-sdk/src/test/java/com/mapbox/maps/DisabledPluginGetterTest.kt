package com.mapbox.maps

import com.mapbox.maps.plugin.DisabledPlugin
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapPluginRegistry
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.PluginDisabledException
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.indoorselector.indoorSelector
import com.mapbox.maps.plugin.lifecycle.lifecycle
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.renderer.MapboxRenderer
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Verifies the generic maps-sdk behavior: when a plugin is registered as a [DisabledPlugin]
 * placeholder, accessing its getter throws a [PluginDisabledException] carrying the placeholder's
 * message.
 */
@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class DisabledPluginGetterTest {

  private val mockPluginRegistry: MapPluginRegistry = mockk()
  private lateinit var mapController: MapController

  @Before
  fun setUp() {
    mapController = MapController(
      mockk<MapboxRenderer>(),
      mockk<NativeObserver>(),
      mockk<MapInitOptions>(),
      ContextMode.SHARED,
      mockk<NativeMapImpl>(),
      mockk<MapboxMap>(),
      mockPluginRegistry,
      mockk<StyleDataLoadedCallback>(),
    )
  }

  private fun stubDisabled(id: String, message: String) {
    every { mockPluginRegistry.getPlugin<MapPlugin>(id) } returns DisabledPlugin(message)
  }

  @Test
  fun `compass getter throws with the placeholder message`() {
    stubDisabled(Plugin.MAPBOX_COMPASS_PLUGIN_ID, "compass disabled")
    val exception = assertThrows(PluginDisabledException::class.java) { mapController.compass }
    assertEquals("compass disabled", exception.message)
  }

  @Test
  fun `logo getter throws with the placeholder message`() {
    stubDisabled(Plugin.MAPBOX_LOGO_PLUGIN_ID, "logo disabled")
    val exception = assertThrows(PluginDisabledException::class.java) { mapController.logo }
    assertEquals("logo disabled", exception.message)
  }

  @Test
  fun `attribution getter throws with the placeholder message`() {
    stubDisabled(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID, "attribution disabled")
    val exception = assertThrows(PluginDisabledException::class.java) { mapController.attribution }
    assertEquals("attribution disabled", exception.message)
  }

  @Test
  fun `scalebar getter throws with the placeholder message`() {
    stubDisabled(Plugin.MAPBOX_SCALEBAR_PLUGIN_ID, "scalebar disabled")
    val exception = assertThrows(PluginDisabledException::class.java) { mapController.scalebar }
    assertEquals("scalebar disabled", exception.message)
  }

  @Test
  fun `lifecycle getter throws with the placeholder message`() {
    stubDisabled(Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID, "lifecycle disabled")
    val exception = assertThrows(PluginDisabledException::class.java) { mapController.lifecycle }
    assertEquals("lifecycle disabled", exception.message)
  }

  @Test
  fun `indoorSelector getter throws with the placeholder message`() {
    stubDisabled(Plugin.MAPBOX_INDOOR_SELECTOR_PLUGIN_ID, "indoor disabled")
    val exception = assertThrows(PluginDisabledException::class.java) { mapController.indoorSelector }
    assertEquals("indoor disabled", exception.message)
  }

  @Test
  fun `getPlugin returns the real plugin when it is not disabled`() {
    val realPlugin = mockk<MapPlugin>()
    every { mockPluginRegistry.getPlugin<MapPlugin>("id") } returns realPlugin
    assertSame(realPlugin, mapController.getPlugin<MapPlugin>("id"))
  }

  @Test
  fun `getPlugin returns null when the plugin is absent`() {
    every { mockPluginRegistry.getPlugin<MapPlugin>("absent") } returns null
    assertNull(mapController.getPlugin<MapPlugin>("absent"))
  }
}