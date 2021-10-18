package com.mapbox.maps

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.DisplayMetrics
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.renderer.egl.ConfigMSAA
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class MapViewTypedArrayTest(
  private val inputMsaaEnabled: Boolean,
  private val inputMsaaSamples: Int,
  private val inputMapSurfaceMode: Int,
  private val expectedConfigMSAA: ConfigMSAA,
  private val expectedTextureMode: Boolean
) {

  private lateinit var attrs: AttributeSet
  private lateinit var mapView: MapView

  companion object {
    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(
        /* inputMsaaEnabled */ false,
        /* inputMsaaSamples */ 8,
        /* inputMapSurfaceMode */ 0,
        /* expectedConfigMSAA */ ConfigMSAA.Off,
        /* expectedTextureMode */ false
      ),
      arrayOf(
        /* inputMsaaEnabled */ true,
        /* inputMsaaSamples */ 8,
        /* inputMapSurfaceMode */ 0,
        /* expectedConfigMSAA */ ConfigMSAA.On(8),
        /* expectedTextureMode */ false
      ),
      arrayOf(
        /* inputMsaaEnabled */ true,
        /* inputMsaaSamples */ 16,
        /* inputMapSurfaceMode */ 1,
        /* expectedConfigMSAA */ ConfigMSAA.On(16),
        /* expectedTextureMode */ true
      ),
    )
  }

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
  }

  @Test
  fun parseTypedArray() {
    val context: Context = mockk()
    val typedArray: TypedArray = mockk()
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
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_styleUri) } returns Style.SATELLITE
    every { typedArray.recycle() } just Runs
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    var mapInitOptions = mapView.parseTypedArray(context, attrs)
    Assert.assertEquals(expectedTextureMode, mapInitOptions.textureView)
    Assert.assertEquals(expectedConfigMSAA, mapInitOptions.renderConfigMSAA)
    Assert.assertEquals(Style.SATELLITE, mapInitOptions.styleUri)

    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_styleUri) } returns null
    mapInitOptions = mapView.parseTypedArray(context, attrs)
    Assert.assertEquals(Style.MAPBOX_STREETS, mapInitOptions.styleUri)

    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_styleUri) } returns ""
    mapInitOptions = mapView.parseTypedArray(context, attrs)
    Assert.assertEquals(null, mapInitOptions.styleUri)
  }
}