package com.mapbox.maps

import android.content.Context
import android.util.DisplayMetrics
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.plugin.*
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class MapInitOptionsTest {

  private val context: Context = mockk(relaxUnitFun = true)
  private val displayMetrics: DisplayMetrics = mockk(relaxUnitFun = true)

  @Before
  fun setUp() {
    every { context.getString(-1) } returns "token"
    every {
      context.resources.getIdentifier(
        "mapbox_access_token",
        "string",
        "com.mapbox.maps"
      )
    } returns -1
    every { context.packageName } returns "com.mapbox.maps"
    every { context.filesDir } returns File("foobar")
    every { context.resources.displayMetrics } returns displayMetrics
    displayMetrics.density = 1f
  }

  @After
  fun cleanUp() {
    ResourceOptionsManager.destroyDefault()
  }

  @Test
  fun surfaceView() {
    assertEquals(false, MapInitOptions(context).textureView)
    assertEquals(true, MapInitOptions(context, textureView = true).textureView)
  }

  @Test
  fun setInvalidToken() {
    ResourceOptionsManager.getDefault(context).update { accessToken("") }
    val mapboxMapOptions = MapInitOptions(context)
    assertEquals("", mapboxMapOptions.resourceOptions.accessToken)
  }

  @Test
  fun setValidToken() {
    ResourceOptionsManager.getDefault(context).update { accessToken("token") }
    val mapboxMapOptions = MapInitOptions(context)
    assertEquals("token", mapboxMapOptions.resourceOptions.accessToken)
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
    assertEquals(Style.MAPBOX_STREETS, mapboxMapOptions.styleUri)
  }

  @Test
  fun defaultResourceOptions() {
    val mapboxMapOptions = MapInitOptions(context)
    assertEquals("token", mapboxMapOptions.resourceOptions.accessToken)
    assertNull(mapboxMapOptions.resourceOptions.dataPath)
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