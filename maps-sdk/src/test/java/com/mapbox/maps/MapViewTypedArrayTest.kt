package com.mapbox.maps

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.DisplayMetrics
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.MapboxThread
import com.mapbox.common.MapboxThreadProvider
import com.mapbox.common.mapboxThreadProvider
import com.mapbox.maps.MapView.Companion.DEFAULT_ANTIALIASING_SAMPLE_COUNT
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(Parameterized::class)
class MapViewTypedArrayTest(
  private val inputAntialiasingSampleCount: Int,
  private val inputMapSurfaceMode: Int,
  private val inputMapStyle: String?,
  private val expectedAntialiasingSampleCount: Int,
  private val expectedTextureMode: Boolean,
  private val expectedStyle: String?,
) {

  private lateinit var attrs: AttributeSet
  private lateinit var context: Context
  private lateinit var mapView: MapView

  @OptIn(MapboxExperimental::class)
  @Before
  fun setUp() {
    // This test isn't Robolectric-backed, so MainMapboxThreadProvider's real Looper.getMainLooper()
    // call isn't available here, use a plain mock instead of MapboxThreadProviderTestRule. The
    // dispatcher is stubbed with the Dispatchers.Main proxy backed by an UnconfinedTestDispatcher,
    // since MapView.<init> combines it via CoroutineContext.plus, which a fully relaxed mock can't
    // satisfy.
    Dispatchers.setMain(UnconfinedTestDispatcher())
    val mockMapboxThread = mockk<MapboxThread>(relaxed = true)
    every { mockMapboxThread.dispatcher } returns Dispatchers.Main
    mapboxThreadProvider = mockk<MapboxThreadProvider> {
      every { getOrCreate() } returns mockMapboxThread
    }
    attrs = mockk()
    val mapController = mockk<MapController>(relaxUnitFun = true)
    val mapboxMap = mockk<MapboxMap>(relaxUnitFun = true)
    every { mapController.mapboxMap } returns mapboxMap
    mapView = MapView(
      mockk(relaxed = true),
      mockk(relaxed = true),
      mapController,
    )
    context = mockk()
    val typedArray = mockk<TypedArray>()
    val resources = mockk<Resources>()
    val mapOptions = mockk<MapOptions>()
    val cameraOptions = mockk<CameraOptions>()
    val displayMetrics: DisplayMetrics = mockk()
    mockkObject(MapAttributeParser)
    mockkObject(CameraAttributeParser)
    every { context.resources } returns resources
    every { resources.displayMetrics } returns displayMetrics
    every { MapAttributeParser.parseMapOptions(any(), any()) } returns mapOptions
    every { CameraAttributeParser.parseCameraOptions(any()) } returns cameraOptions

    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapSurface, 0) } returns inputMapSurfaceMode
    every { typedArray.getInteger(R.styleable.mapbox_MapView_mapbox_mapAntialiasingSampleCount, DEFAULT_ANTIALIASING_SAMPLE_COUNT) } returns inputAntialiasingSampleCount
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_mapName) } returns ""
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_styleUri) } returns inputMapStyle
    every { typedArray.recycle() } just Runs
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
  }

  @Test
  fun parseTypedArray() {
    val mapInitOptions = mapView.parseTypedArray(context, attrs)
    assertEquals(expectedTextureMode, mapInitOptions.textureView)
    assertEquals(expectedAntialiasingSampleCount, mapInitOptions.antialiasingSampleCount)
    assertEquals(expectedStyle, mapInitOptions.styleUri)
  }

  @After
  fun shutDown() {
    unmockkAll()
    Dispatchers.resetMain()
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(
        /* inputAntialiasingSampleCount */ 8,
        /* inputMapSurfaceMode */ 0,
        /* inputMapStyle */ null,
        /* expectedAntialiasingSampleCount */ 8,
        /* expectedTextureMode */ false,
        /* expectedStyle */ Style.STANDARD,
      ),
      arrayOf(
        /* inputAntialiasingSampleCount */ DEFAULT_ANTIALIASING_SAMPLE_COUNT,
        /* inputMapSurfaceMode */ 0,
        /* inputMapStyle */ "",
        /* expectedAntialiasingSampleCount */ DEFAULT_ANTIALIASING_SAMPLE_COUNT,
        /* expectedTextureMode */ false,
        /* expectedStyle */ null,
      ),
      arrayOf(
        /* inputAntialiasingSampleCount */ 16,
        /* inputMapSurfaceMode */ 1,
        /* inputMapStyle */ Style.SATELLITE,
        /* expectedAntialiasingSampleCount */ 16,
        /* expectedTextureMode */ true,
        /* expectedStyle */ Style.SATELLITE,
      ),
    )
  }
}