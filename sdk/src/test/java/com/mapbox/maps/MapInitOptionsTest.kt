package com.mapbox.maps

import android.content.Context
import android.util.DisplayMetrics
import com.mapbox.common.ShadowLogger
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
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
  fun defaultMapOption() {
    val mapboxMapOptions = MapInitOptions(context)
    assertEquals(
      GlyphsRasterizationMode.ALL_GLYPHS_RASTERIZED_LOCALLY,
      mapboxMapOptions.mapOptions.glyphsRasterizationOptions!!.rasterizationMode
    )

    assertEquals(1f, mapboxMapOptions.mapOptions.pixelRatio)
  }
}