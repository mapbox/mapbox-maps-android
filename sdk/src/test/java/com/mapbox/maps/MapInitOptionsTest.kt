package com.mapbox.maps

import android.content.Context
import android.util.DisplayMetrics
import com.mapbox.maps.MapView.Companion.DEFAULT_ANTIALIASING_SAMPLE_COUNT
import com.mapbox.maps.plugin.*
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MapInitOptionsTest {

  private val context: Context = mockk(relaxUnitFun = true)
  private val displayMetrics: DisplayMetrics = mockk(relaxUnitFun = true)

  @Before
  fun setUp() {
    every { context.resources.displayMetrics } returns displayMetrics
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    displayMetrics.density = 1f
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun surfaceView() {
    assertEquals(false, MapInitOptions(context).textureView)
    assertEquals(true, MapInitOptions(context, textureView = true).textureView)
  }

  @Test
  fun antialiasingSampleCount() {
    assertEquals(DEFAULT_ANTIALIASING_SAMPLE_COUNT, MapInitOptions(context).antialiasingSampleCount)
    assertEquals(0, MapInitOptions(context, antialiasingSampleCount = 0).antialiasingSampleCount)
    assertEquals(8, MapInitOptions(context, antialiasingSampleCount = 8).antialiasingSampleCount)
  }

  @Test
  fun defaultMapOptions() {
    val mapboxMapOptions = MapInitOptions(context)
    assertEquals(
      GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY,
      mapboxMapOptions.mapOptions.glyphsRasterizationOptions!!.rasterizationMode
    )
    assertEquals(
      FontUtils.extractValidFont(null),
      mapboxMapOptions.mapOptions.glyphsRasterizationOptions!!.fontFamily
    )
    assertEquals(1f, mapboxMapOptions.mapOptions.pixelRatio)
    assertEquals(
      ConstrainMode.HEIGHT_ONLY,
      mapboxMapOptions.mapOptions.constrainMode
    )
    assertEquals(ContextMode.UNIQUE, mapboxMapOptions.mapOptions.contextMode)
    assertEquals(NorthOrientation.UPWARDS, mapboxMapOptions.mapOptions.orientation)
    assertEquals(ViewportMode.DEFAULT, mapboxMapOptions.mapOptions.viewportMode)
    assertEquals(true, mapboxMapOptions.mapOptions.crossSourceCollisions)
    assertEquals(true, mapboxMapOptions.mapOptions.optimizeForTerrain)
    assertEquals(Style.STANDARD, mapboxMapOptions.styleUri)
  }

  @Test
  fun defaultPlugins() {
    val mapboxMapOptions = MapInitOptions(context)
    val plugins = mapboxMapOptions.plugins
    assertEquals(
      MapInitOptions.defaultPluginList,
      plugins
    )
  }

  @Test
  fun emptyPlugins() {
    val mapboxMapOptions = MapInitOptions(context, plugins = listOf())
    val plugins = mapboxMapOptions.plugins
    assertTrue(plugins.isEmpty())
  }

  @Test
  fun customPlugins() {
    val customPluginDummy = object : MapPlugin {}
    val mapboxMapOptions = MapInitOptions(
      context,
      plugins = listOf(
        Plugin.Custom("customId", customPluginDummy)
      )
    )
    val plugins = mapboxMapOptions.plugins
    assertTrue(plugins.size == 1)
    assertEquals("customId", plugins[0].id)
    assertEquals(customPluginDummy, plugins[0].instance)
  }
}