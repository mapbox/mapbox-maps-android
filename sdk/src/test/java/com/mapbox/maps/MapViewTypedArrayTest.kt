package com.mapbox.maps

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.DisplayMetrics
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.renderer.egl.ConfigMSAA
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class MapViewTypedArrayTest(
  private val inputMsaaEnabled: Boolean,
  private val inputMsaaSamples: Int,
  private val inputMapSurfaceMode: Int,
  private val inputMapStyle: String?,
  private val expectedConfigMSAA: ConfigMSAA,
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
    every { typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_mapMsaaEnabled, false) } returns inputMsaaEnabled
    every { typedArray.getInteger(R.styleable.mapbox_MapView_mapbox_mapMsaaSamples, 4) } returns inputMsaaSamples
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_styleUri) } returns inputMapStyle
    every { typedArray.recycle() } just Runs
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
  }

  @Test
  fun parseTypedArray() {
    val mapInitOptions = mapView.parseTypedArray(context, attrs)
    assertEquals(expectedTextureMode, mapInitOptions.textureView)
    assertEquals(expectedConfigMSAA, mapInitOptions.renderConfigMSAA)
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
        /* inputMsaaEnabled */ false,
        /* inputMsaaSamples */ 8,
        /* inputMapSurfaceMode */ 0,
        /* inputMapStyle */ null,
        /* expectedConfigMSAA */ ConfigMSAA.Off,
        /* expectedTextureMode */ false,
        /* expectedStyle */ Style.MAPBOX_STREETS,
      ),
      arrayOf(
        /* inputMsaaEnabled */ true,
        /* inputMsaaSamples */ 8,
        /* inputMapSurfaceMode */ 0,
        /* inputMapStyle */ "",
        /* expectedConfigMSAA */ ConfigMSAA.On(8),
        /* expectedTextureMode */ false,
        /* expectedStyle */ null,
      ),
      arrayOf(
        /* inputMsaaEnabled */ true,
        /* inputMsaaSamples */ 16,
        /* inputMapSurfaceMode */ 1,
        /* inputMapStyle */ Style.SATELLITE,
        /* expectedConfigMSAA */ ConfigMSAA.On(16),
        /* expectedTextureMode */ true,
        /* expectedStyle */ Style.SATELLITE,
      ),
    )
  }
}