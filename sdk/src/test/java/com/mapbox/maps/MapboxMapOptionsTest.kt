package com.mapbox.maps

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mapbox.common.ShadowLogger
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class MapboxMapOptionsTest {

  private val context: Context = mockk(relaxUnitFun = true)
  private val attrs: AttributeSet = mockk(relaxUnitFun = true)
  private val typedArray: TypedArray = mockk(relaxed = true)

  @Before
  fun setUp() {
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { context.packageName } returns "com.mapbox.maps"
    every { context.filesDir } returns File("foobar")
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getFloat(any(), any()) } returns 0.0f

    mockkObject(MapAttributeParser)
    every { MapAttributeParser.parseMapOptions(any(), any()) } returns mockk()

    mockkObject(ResourcesAttributeParser)
    every {
      ResourcesAttributeParser.parseResourcesOptions(
        any(),
        any()
      )
    } returns mockk()

    mockkObject(CameraAttributeParser)
    every {
      CameraAttributeParser.parseCameraOptions(
        any(),
        any()
      )
    } returns mockk()
  }

  @Test
  fun surfaceView() {
    assertEquals(false, MapboxMapOptions(context, 1.0f, attrs).textureView)
  }

  @Test
  fun textureView() {
    every {
      typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapSurface, 0)
    } returns 1
    assertEquals(true, MapboxMapOptions(context, 1.0f, attrs).textureView)
  }

  @Test(expected = MapboxConfigurationException::class)
  fun setInvalidToken() {
    val mapboxMapOptions = MapboxMapOptions(context, 1.0f, attrs)
    mapboxMapOptions.resourceOptions = MapboxOptions.createResourceOptions(context, "")

    assertEquals("", mapboxMapOptions.resourceOptions.accessToken)
  }

  @Test
  fun setValidToken() {
    val mapboxMapOptions = MapboxMapOptions(context, 1.0f, attrs)
    mapboxMapOptions.resourceOptions = MapboxOptions.createResourceOptions(context, "token")

    assertEquals("token", mapboxMapOptions.resourceOptions.accessToken)
  }

  @Test
  fun cacheResourceOptions() {
    val resourceOptions = MapboxOptions.createResourceOptions(context, "token")
    MapboxOptions.setDefaultResourceOptions(resourceOptions)
    val mapboxMapOptions = MapboxMapOptions(context, 1.0f, attrs)
    assertEquals(resourceOptions, mapboxMapOptions.resourceOptions)
  }
}