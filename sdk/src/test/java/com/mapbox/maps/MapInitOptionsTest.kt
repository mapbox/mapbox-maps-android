package com.mapbox.maps

import android.content.Context
import android.util.DisplayMetrics
import com.mapbox.common.ShadowLogger
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
    CredentialsManager.default.setAccessToken("token")
    every { context.packageName } returns "com.mapbox.maps"
    every { context.filesDir } returns File("foobar")
    every { context.resources.displayMetrics } returns displayMetrics
    displayMetrics.density = 1f
  }

  @Test
  fun surfaceView() {
    assertEquals(false, MapInitOptions(context).textureView)
    assertEquals(true, MapInitOptions(context, textureView = true).textureView)
  }

  fun setInvalidToken() {
    CredentialsManager.default.setAccessToken("")

    val mapboxMapOptions = MapInitOptions(context)
    assertEquals("", mapboxMapOptions.resourceOptions.accessToken)
  }

  @Test
  fun setValidToken() {
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
    assertEquals(Style.MAPBOX_STREETS, mapboxMapOptions.styleUri)
  }

  @Test
  fun defaultResourceOptions() {
    val mapboxMapOptions = MapInitOptions(context)
    assertEquals("token", mapboxMapOptions.resourceOptions.accessToken)
    assertTrue(mapboxMapOptions.resourceOptions.cachePath!!.endsWith("foobar/mbx.db"))
    assertTrue(mapboxMapOptions.resourceOptions.assetPath!!.endsWith("foobar"))
    assertEquals(MapInitOptions.DEFAULT_CACHE_SIZE, mapboxMapOptions.resourceOptions.cacheSize)
  }
}