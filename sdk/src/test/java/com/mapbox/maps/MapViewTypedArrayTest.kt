package com.mapbox.maps

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.DisplayMetrics
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.renderer.egl.AntialiasingConfig
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class MapViewTypedArrayTest(
  private val inputAntialiasingEnabled: Boolean,
  private val inputAntialiasingSampleCount: Int,
  private val inputMapSurfaceMode: Int,
  private val inputMapStyle: String?,
  private val expectedAntialiasingConfig: AntialiasingConfig,
  private val expectedTextureMode: Boolean,
  private val expectedStyle: String?,
) {

  private lateinit var attrs: AttributeSet
  private lateinit var context: Context
  private lateinit var mapView: MapView

  @Before
  fun setUp() {
    mockkStatic(MapboxMapStaticInitializer::class)
    every { MapboxMapStaticInitializer.loadMapboxMapNativeLib() } just Runs
    attrs = mockk()
    val mapController = mockk<MapController>(relaxUnitFun = true)
    val mapboxMap = mockk<MapboxMap>(relaxUnitFun = true)
    every { mapController.getMapboxMap() } returns mapboxMap
    mapView = MapView(
      mockk(relaxed = true),
      mockk(relaxed = true),
      mapController
    )
    context = mockk()
    val typedArray = mockk<TypedArray>()
    val resources = mockk<Resources>()
    val displayMetrics: DisplayMetrics = mockk()
    mockkObject(ResourcesAttributeParser)
    mockkObject(MapAttributeParser)
    mockkObject(CameraAttributeParser)
    every { context.resources } returns resources
    every { resources.displayMetrics } returns displayMetrics
    every { ResourcesAttributeParser.parseResourcesOptions(any(), any()) } returns mockk()
    every { MapAttributeParser.parseMapOptions(any(), any()) } returns mockk()
    every { CameraAttributeParser.parseCameraOptions(any()) } returns mockk()

    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapSurface, 0) } returns inputMapSurfaceMode
    every { typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_mapAntialiasingEnabled, false) } returns inputAntialiasingEnabled
    every { typedArray.getInteger(R.styleable.mapbox_MapView_mapbox_mapAntialiasingSampleCount, 4) } returns inputAntialiasingSampleCount
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_styleUri) } returns inputMapStyle
    every { typedArray.recycle() } just Runs
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
  }

  @Test
  fun parseTypedArray() {
    val mapInitOptions = mapView.parseTypedArray(context, attrs)
    assertEquals(expectedTextureMode, mapInitOptions.textureView)
    assertEquals(expectedAntialiasingConfig, mapInitOptions.antialiasingConfig)
    assertEquals(expectedStyle, mapInitOptions.styleUri)
  }

  @After
  fun shutDown() {
    unmockkAll()
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(
        /* inputAntialiasingEnabled */ false,
        /* inputAntialiasingSampleCount */ 8,
        /* inputMapSurfaceMode */ 0,
        /* inputMapStyle */ null,
        /* expectedConfigMSAA */ AntialiasingConfig.Off,
        /* expectedTextureMode */ false,
        /* expectedStyle */ Style.MAPBOX_STREETS,
      ),
      arrayOf(
        /* inputAntialiasingEnabled */ true,
        /* inputAntialiasingSampleCount */ 8,
        /* inputMapSurfaceMode */ 0,
        /* inputMapStyle */ "",
        /* expectedConfigMSAA */ AntialiasingConfig.On(8),
        /* expectedTextureMode */ false,
        /* expectedStyle */ null,
      ),
      arrayOf(
        /* inputAntialiasingEnabled */ true,
        /* inputAntialiasingSampleCount */ 16,
        /* inputMapSurfaceMode */ 1,
        /* inputMapStyle */ Style.SATELLITE,
        /* expectedConfigMSAA */ AntialiasingConfig.On(16),
        /* expectedTextureMode */ true,
        /* expectedStyle */ Style.SATELLITE,
      ),
    )
  }
}