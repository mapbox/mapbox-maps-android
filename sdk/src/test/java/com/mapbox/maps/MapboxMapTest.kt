package com.mapbox.maps

import android.graphics.RectF
import android.os.Looper
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.listeners.*
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.verifyNo
import io.mockk.*
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

private val ZERO_EDGE_INSETS = EdgeInsets(0.0, 0.0, 0.0, 0.0)

@OptIn(MapboxExperimental::class)
@Suppress("DEPRECATION")
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(shadows = [ShadowMap::class])
class MapboxMapTest {

  private val nativeMap: NativeMapImpl = mockk(relaxed = true)
  private val nativeObserver: NativeObserver = mockk(relaxed = true)

  private lateinit var styleObserver: StyleObserver
  private lateinit var mapboxMap: MapboxMap

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    styleObserver = mockk(relaxUnitFun = true)
    mapboxMap = MapboxMap(nativeMap, nativeObserver, styleObserver)
  }

  @Test
  fun isValid() {
    assertTrue(mapboxMap.isValid())
    mapboxMap.onDestroy()
    assertFalse(mapboxMap.isValid())
  }

  @Test
  fun onDestroyWithPlugins() {
    mapboxMap.cameraAnimationsPlugin = mockk()
    mapboxMap.gesturesPlugin = mockk()
    mapboxMap.onDestroy()
    assertTrue(mapboxMap.cameraAnimationsPlugin == null)
    assertTrue(mapboxMap.gesturesPlugin == null)
  }

  @Test
  fun onDestroyWhenPerformanceStatisticsStarted() {
    mapboxMap.startPerformanceStatisticsCollection(mockk(), mockk())
    mapboxMap.onDestroy()
    verify(exactly = 1) { nativeMap.stopPerformanceStatisticsCollection() }
  }

  @Test
  fun onDestroyWhenPerformanceStatisticsNotStarted() {
    mapboxMap.onDestroy()
    verify(exactly = 0) { nativeMap.stopPerformanceStatisticsCollection() }
  }

  @Test
  fun loadStyleMapboxUri() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle("mapbox://foo")
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.setStyleURI("mapbox://foo") }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleMapboxFile() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle("file://foo")
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.setStyleURI("file://foo") }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleInvalidUri() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle("foo")
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.setStyleJSON("foo") }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleUriLambda() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle("foo") {}
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleJSON() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle("foo")
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.setStyleJSON("foo") }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleJSONLambda() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle("foo") {}
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.setStyleJSON("foo") }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadEmptyStyle() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle(style("") {})
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.setStyleJSON("{}") }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyle() {
    val styleExtension = mockk<StyleContract.StyleExtension>()
    every { styleExtension.style } returns "asset://foobar"
    val onMapLoadError = mockk<OnMapLoadErrorListener>()
    val onStyleLoadError = mockk<Style.OnStyleLoaded>()
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle(styleExtension, onStyleLoadError, onMapLoadError)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.setStyleURI("asset://foobar") }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleLambda() {
    val styleExtension = mockk<StyleContract.StyleExtension>()
    every { styleExtension.style } returns "asset://foobar"
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle(styleExtension) {}
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.setStyleURI("asset://foobar") }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun bindsStyleExtensionComponentsInCorrectOrderAfterStyleDataLoadEvents() {
    val style = mockk<Style>()
    val styleExtension = mockk<StyleContract.StyleExtension>(relaxed = true)

    val source = mockk<StyleContract.StyleSourceExtension>(relaxed = true)
    every { styleExtension.sources } returns listOf(source)

    val image = mockk<StyleContract.StyleImageExtension>(relaxed = true)
    every { styleExtension.images } returns listOf(image)

    val layer = mockk<StyleContract.StyleLayerExtension>(relaxed = true)
    val layerPosition = LayerPosition(null, null, 0)
    every { styleExtension.layers } returns listOf(Pair(layer, layerPosition))

    val flatLight = mockk<StyleContract.StyleLightExtension>(relaxed = true)
    every { styleExtension.flatLight } returns flatLight

    val dynamicLight = mockk<StyleContract.StyleLightExtension>(relaxed = true)
    every { styleExtension.dynamicLight } returns dynamicLight

    val terrain = mockk<StyleContract.StyleTerrainExtension>(relaxed = true)
    every { styleExtension.terrain } returns terrain

    val atmosphere = mockk<StyleContract.StyleAtmosphereExtension>(relaxed = true)
    every { styleExtension.atmosphere } returns atmosphere

    val projection = mockk<StyleContract.StyleProjectionExtension>(relaxed = true)
    every { styleExtension.projection } returns projection

    val model = mockk<StyleContract.StyleModelExtension>(relaxed = true)
    every { styleExtension.models } returns listOf(model)

    val transition = mockk<TransitionOptions>(relaxed = true)
    every { styleExtension.transition } returns transition
    every { style.setStyleTransition(any()) } just Runs

    val styleLoadCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    val userCallbackStyleSlots = mutableListOf<Style.OnStyleLoaded?>()
    val callbackStyleSlots = mutableListOf<Style.OnStyleLoaded>()
    val callbackStyleSpritesSlots = mutableListOf<Style.OnStyleLoaded?>()
    val callbackStyleSourcesSlots = mutableListOf<Style.OnStyleLoaded?>()

    mapboxMap.loadStyle(styleExtension, styleLoadCallback)

    verify {
      styleObserver.setLoadStyleListener(
        captureNullable(userCallbackStyleSlots),
        capture(callbackStyleSlots),
        captureNullable(callbackStyleSpritesSlots),
        captureNullable(callbackStyleSourcesSlots),
      )
    }

    verifyNo { source.bindTo(style) }
    verifyNo { image.bindTo(style) }
    verifyNo { layer.bindTo(style, layerPosition) }
    verifyNo { flatLight.bindTo(style) }
    verifyNo { dynamicLight.bindTo(style) }
    verifyNo { terrain.bindTo(style) }
    verifyNo { projection.bindTo(style) }
    verifyNo { atmosphere.bindTo(style) }
    verifyNo { style.setStyleTransition(any()) }
    verifyNo { styleLoadCallback.onStyleLoaded(style) }
    verifyNo { model.bindTo(style) }

    callbackStyleSlots.first().onStyleLoaded(style)

    verify { flatLight.bindTo(style) }
    verify { dynamicLight.bindTo(style) }
    verify { terrain.bindTo(style) }
    verify { projection.bindTo(style) }
    verify { atmosphere.bindTo(style) }
    verify { style.setStyleTransition(any()) }
    verifyNo { source.bindTo(style) }
    verifyNo { image.bindTo(style) }
    verifyNo { layer.bindTo(style, layerPosition) }
    verifyNo { styleLoadCallback.onStyleLoaded(style) }
    verifyNo { model.bindTo(style) }

    callbackStyleSpritesSlots.first()!!.onStyleLoaded(style)

    verify { image.bindTo(style) }
    verify { model.bindTo(style) }
    verifyNo { source.bindTo(style) }
    verifyNo { layer.bindTo(style, layerPosition) }
    verifyNo { styleLoadCallback.onStyleLoaded(style) }

    callbackStyleSourcesSlots.first()!!.onStyleLoaded(style)

    verify { source.bindTo(style) }
    verify { layer.bindTo(style, layerPosition) }

    userCallbackStyleSlots.first()!!.onStyleLoaded(style)
    verify { image.bindTo(style) }
    verify { source.bindTo(style) }
    verify { layer.bindTo(style, layerPosition) }
    verify { styleLoadCallback.onStyleLoaded(style) }
  }

  @Test
  fun getStyleLoadedCallback() {
    val style = mockk<Style>()
    mapboxMap.style = style
    val styleLoadCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    mapboxMap.getStyle(styleLoadCallback)
    verify { styleLoadCallback.onStyleLoaded(style) }
  }

  @Test
  fun getStyleSynchronously() {
    val style = mockk<Style>()
    mapboxMap.style = style
    assertNotNull(mapboxMap.style)
  }

  @Test
  fun getStyleSynchronouslyNotReady() {
    assertNull(mapboxMap.style)
  }

  @Test
  fun addOnCameraChangeListener() {
    val listener = mockk<OnCameraChangeListener>()
    mapboxMap.addOnCameraChangeListener(listener)
    verify { nativeObserver.addOnCameraChangeListener(listener) }
  }

  @Test
  fun removeOnCameraChangeListener() {
    val listener = mockk<OnCameraChangeListener>()
    mapboxMap.removeOnCameraChangeListener(listener)
    verify { nativeObserver.removeOnCameraChangeListener(listener) }
  }

  // Map events
  @Test
  fun addOnMapIdleListener() {
    val listener = mockk<OnMapIdleListener>()
    mapboxMap.addOnMapIdleListener(listener)
    verify { nativeObserver.addOnMapIdleListener(listener) }
  }

  @Test
  fun removeOnMapIdleListener() {
    val listener = mockk<OnMapIdleListener>()
    mapboxMap.removeOnMapIdleListener(listener)
    verify { nativeObserver.removeOnMapIdleListener(listener) }
  }

  @Test
  fun addOnMapLoadErrorListener() {
    val listener = mockk<OnMapLoadErrorListener>()
    mapboxMap.addOnMapLoadErrorListener(listener)
    verify { nativeObserver.addOnMapLoadErrorListener(listener) }
  }

  @Test
  fun removeOnMapLoadErrorListener() {
    val listener = mockk<OnMapLoadErrorListener>()
    mapboxMap.removeOnMapLoadErrorListener(listener)
    verify { nativeObserver.removeOnMapLoadErrorListener(listener) }
  }

  @Test
  fun addOnMapLoadedListener() {
    val listener = mockk<OnMapLoadedListener>()
    mapboxMap.addOnMapLoadedListener(listener)
    verify { nativeObserver.addOnMapLoadedListener(listener) }
  }

  @Test
  fun removeOnMapLoadedListener() {
    val listener = mockk<OnMapLoadedListener>()
    mapboxMap.removeOnMapLoadedListener(listener)
    verify { nativeObserver.removeOnMapLoadedListener(listener) }
  }

  // Render frame events
  @Test
  fun addOnRenderFrameFinishedListener() {
    val listener = mockk<OnRenderFrameFinishedListener>()
    mapboxMap.addOnRenderFrameFinishedListener(listener)
    verify { nativeObserver.addOnRenderFrameFinishedListener(listener) }
  }

  @Test
  fun removeOnRenderFrameFinishedListener() {
    val listener = mockk<OnRenderFrameFinishedListener>()
    mapboxMap.removeOnRenderFrameFinishedListener(listener)
    verify { nativeObserver.removeOnRenderFrameFinishedListener(listener) }
  }

  @Test
  fun addOnRenderFrameStartedListener() {
    val listener = mockk<OnRenderFrameStartedListener>()
    mapboxMap.addOnRenderFrameStartedListener(listener)
    verify { nativeObserver.addOnRenderFrameStartedListener(listener) }
  }

  @Test
  fun removeOnRenderFrameStartedListener() {
    val listener = mockk<OnRenderFrameStartedListener>()
    mapboxMap.removeOnRenderFrameStartedListener(listener)
    verify { nativeObserver.removeOnRenderFrameStartedListener(listener) }
  }

  // Source events
  @Test
  fun addOnSourceAddedListener() {
    val listener = mockk<OnSourceAddedListener>()
    mapboxMap.addOnSourceAddedListener(listener)
    verify { nativeObserver.addOnSourceAddedListener(listener) }
  }

  @Test
  fun removeOnSourceAddedListener() {
    val listener = mockk<OnSourceAddedListener>()
    mapboxMap.removeOnSourceAddedListener(listener)
    verify { nativeObserver.removeOnSourceAddedListener(listener) }
  }

  @Test
  fun addOnSourceDataLoadedListener() {
    val listener = mockk<OnSourceDataLoadedListener>()
    mapboxMap.addOnSourceDataLoadedListener(listener)
    verify { nativeObserver.addOnSourceDataLoadedListener(listener) }
  }

  @Test
  fun removeOnSourceDataLoadedListener() {
    val listener = mockk<OnSourceDataLoadedListener>()
    mapboxMap.removeOnSourceDataLoadedListener(listener)
    verify { nativeObserver.removeOnSourceDataLoadedListener(listener) }
  }

  @Test
  fun addOnSourceRemovedListener() {
    val listener = mockk<OnSourceRemovedListener>()
    mapboxMap.addOnSourceRemovedListener(listener)
    verify { nativeObserver.addOnSourceRemovedListener(listener) }
  }

  @Test
  fun removeOnSourceRemovedListener() {
    val listener = mockk<OnSourceRemovedListener>()
    mapboxMap.removeOnSourceRemovedListener(listener)
    verify { nativeObserver.removeOnSourceRemovedListener(listener) }
  }

  // Style events
  @Test
  fun addOnStyleLoadedListener() {
    val listener = mockk<OnStyleLoadedListener>()
    mapboxMap.addOnStyleLoadedListener(listener)
    verify { nativeObserver.addOnStyleLoadedListener(listener) }
  }

  @Test
  fun removeOnStyleLoadedListener() {
    val listener = mockk<OnStyleLoadedListener>()
    mapboxMap.removeOnStyleLoadedListener(listener)
    verify { nativeObserver.removeOnStyleLoadedListener(listener) }
  }

  @Test
  fun addOnStyleImageMissingListener() {
    val listener = mockk<OnStyleImageMissingListener>()
    mapboxMap.addOnStyleImageMissingListener(listener)
    verify { nativeObserver.addOnStyleImageMissingListener(listener) }
  }

  @Test
  fun removeOnStyleImageMissingListener() {
    val listener = mockk<OnStyleImageMissingListener>()
    mapboxMap.removeOnStyleImageMissingListener(listener)
    verify { nativeObserver.removeOnStyleImageMissingListener(listener) }
  }

  @Test
  fun addOnStyleImageUnusedListener() {
    val listener = mockk<OnStyleImageUnusedListener>()
    mapboxMap.addOnStyleImageUnusedListener(listener)
    verify { nativeObserver.addOnStyleImageUnusedListener(listener) }
  }

  @Test
  fun removeOnStyleImageUnusedListener() {
    val listener = mockk<OnStyleImageUnusedListener>()
    mapboxMap.removeOnStyleImageUnusedListener(listener)
    verify { nativeObserver.removeOnStyleImageUnusedListener(listener) }
  }

  @Test
  fun addOnStyleDataLoadedListener() {
    val listener = mockk<OnStyleDataLoadedListener>()
    mapboxMap.addOnStyleDataLoadedListener(listener)
    verify { nativeObserver.addOnStyleDataLoadedListener(listener) }
  }

  @Test
  fun removeOnStyleDataLoadedListener() {
    val listener = mockk<OnStyleDataLoadedListener>()
    mapboxMap.removeOnStyleDataLoadedListener(listener)
    verify { nativeObserver.removeOnStyleDataLoadedListener(listener) }
  }

  @Test
  fun setPrefetchZoomDelta() {
    mapboxMap.setPrefetchZoomDelta(3)
    verify { nativeMap.setPrefetchZoomDelta(3) }
  }

  @Test
  fun getPrefetchZoomDelta() {
    mapboxMap.getPrefetchZoomDelta()
    verify { nativeMap.getPrefetchZoomDelta() }
  }

  @Test
  fun setCamera() {
    val cameraOptions = CameraOptions.Builder().build()
    mapboxMap.setCamera(cameraOptions)
    verify { nativeMap.setCamera(cameraOptions) }
  }

  @Test
  fun cameraForCoordinateBounds() {
    val bounds = mockk<CoordinateBounds>()
    val edgeInsets = mockk<EdgeInsets>()
    val screenCoordinate = mockk<ScreenCoordinate>()
    mapboxMap.cameraForCoordinateBounds(
      bounds = bounds,
      boundsPadding = edgeInsets,
      bearing = 0.0,
      pitch = 1.0,
      maxZoom = 2.0,
      offset = screenCoordinate
    )
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = edgeInsets,
        bearing = 0.0,
        pitch = 1.0,
        maxZoom = 2.0,
        offset = screenCoordinate
      )
    }
  }

  @Test
  fun cameraForCoordinateBoundsOverload() {
    val bounds = mockk<CoordinateBounds>()
    mapboxMap.cameraForCoordinateBounds(bounds = bounds)
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = null,
        bearing = null,
        pitch = null,
        maxZoom = null,
        offset = null
      )
    }
  }

  @Test
  fun cameraForCoordinateBoundsOverloadPadding() {
    val bounds = mockk<CoordinateBounds>()
    val padding = EdgeInsets(1.1, 1.3, 1.4, 1.2)
    mapboxMap.cameraForCoordinateBounds(bounds = bounds, boundsPadding = padding)
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = padding,
        bearing = null,
        pitch = null,
        maxZoom = null,
        offset = null
      )
    }
  }

  @Test
  fun cameraForCoordinateBoundsWithoutPadding() {
    val bounds = mockk<CoordinateBounds>()
    val offset = mockk<ScreenCoordinate>()
    mapboxMap.cameraForCoordinateBounds(
      bounds = bounds,
      bearing = 1.0,
      pitch = 2.0,
      maxZoom = 12.0,
      offset = offset
    )
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = null,
        bearing = 1.0,
        pitch = 2.0,
        maxZoom = 12.0,
        offset = offset
      )
    }
  }

  @Test
  fun cameraForCoordinateBoundsOverloadBearing() {
    val bounds = mockk<CoordinateBounds>()
    mapboxMap.cameraForCoordinateBounds(bounds = bounds, bearing = 2.0)
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = null,
        bearing = 2.0,
        pitch = null,
        maxZoom = null,
        offset = null
      )
    }
  }

  @Test
  fun cameraForCoordinateBoundsOverloadPitch() {
    val bounds = mockk<CoordinateBounds>()
    mapboxMap.cameraForCoordinateBounds(bounds = bounds, pitch = 2.0)
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = null,
        bearing = null,
        pitch = 2.0,
        maxZoom = null,
        offset = null
      )
    }
  }

  @Test
  fun cameraForCoordinateBoundsOverloadMaxZoom() {
    val bounds = mockk<CoordinateBounds>()
    mapboxMap.cameraForCoordinateBounds(bounds = bounds, maxZoom = 10.0)
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = null,
        bearing = null,
        pitch = null,
        maxZoom = 10.0,
        offset = null
      )
    }
  }

  @Test
  fun cameraForCoordinateBoundsOverloadOffset() {
    val bounds = mockk<CoordinateBounds>()
    val screenCoordinate = mockk<ScreenCoordinate>()
    mapboxMap.cameraForCoordinateBounds(bounds, offset = screenCoordinate)
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = null,
        bearing = null,
        pitch = null,
        maxZoom = null,
        offset = screenCoordinate
      )
    }
  }

  @Test
  fun coordinateBoundsForCamera() {
    val cameraOptions = mockk<CameraOptions>()
    mapboxMap.coordinateBoundsForCamera(cameraOptions)
    verify { nativeMap.coordinateBoundsForCamera(cameraOptions) }
  }

  @Test
  fun coordinateBoundsForCameraUnwrapped() {
    val cameraOptions = mockk<CameraOptions>()
    mapboxMap.coordinateBoundsForCameraUnwrapped(cameraOptions)
    verify { nativeMap.coordinateBoundsForCameraUnwrapped(cameraOptions) }
  }

  @Test
  fun coordinateBoundsZoomForCamera() {
    val cameraOptions = mockk<CameraOptions>()
    mapboxMap.coordinateBoundsZoomForCamera(cameraOptions)
    verify { nativeMap.coordinateBoundsZoomForCamera(cameraOptions) }
  }

  @Test
  fun coordinateBoundsZoomForCameraUnwrapped() {
    val cameraOptions = mockk<CameraOptions>()
    mapboxMap.coordinateBoundsZoomForCameraUnwrapped(cameraOptions)
    verify { nativeMap.coordinateBoundsZoomForCameraUnwrapped(cameraOptions) }
  }

  @Test
  fun cameraForCoordinatesOne() {
    val points = mockk<List<Point>>()
    val camera = mockk<CameraOptions>()
    val box = mockk<ScreenBox>()
    mapboxMap.cameraForCoordinates(points, camera, box)
    verify { nativeMap.cameraForCoordinates(points, camera, box) }
  }

  @Test
  fun cameraForCoordinatesTwo() {
    val points = mockk<List<Point>>()
    val edgeInsets = mockk<EdgeInsets>()
    mapboxMap.cameraForCoordinates(points, edgeInsets, 0.0, 1.0)
    verify { nativeMap.cameraForCoordinates(points, edgeInsets, 0.0, 1.0) }
  }

  @Test
  fun cameraForCoordinatesOverload() {
    val points = mockk<List<Point>>()
    mapboxMap.cameraForCoordinates(points)
    verify { nativeMap.cameraForCoordinates(points, null, null, null) }
  }

  @Test
  fun cameraForCoordinatesWithoutPadding() {
    val points = mockk<List<Point>>()
    mapboxMap.cameraForCoordinates(points)
    verify { nativeMap.cameraForCoordinates(points, null, null, null) }
  }

  @Test
  fun cameraForCoordinatesOverloadBearing() {
    val points = mockk<List<Point>>()
    mapboxMap.cameraForCoordinates(points, bearing = 2.0)
    verify { nativeMap.cameraForCoordinates(points, null, 2.0, null) }
  }

  @Test
  fun cameraForCoordinatesOverloadPitch() {
    val points = mockk<List<Point>>()
    mapboxMap.cameraForCoordinates(points, pitch = 2.0)
    verify { nativeMap.cameraForCoordinates(points, null, null, 2.0) }
  }

  @Test
  fun cameraForCoordinatesWithOffset() {
    val points = mockk<List<Point>>()
    val camera = mockk<CameraOptions>()
    val offset = mockk<ScreenCoordinate>()
    every { nativeMap.sizeSet } returns true
    every { nativeMap.cameraForCoordinates(any(), any(), any(), any(), any()) } returns
      ExpectedFactory.createValue(mockk())

    mapboxMap.cameraForCoordinates(points, camera, ZERO_EDGE_INSETS, 1.0, offset)
    verify { nativeMap.cameraForCoordinates(points, camera, ZERO_EDGE_INSETS, 1.0, offset) }
  }

  @Test
  fun cameraForGeometry() {
    val point = mockk<Point>()
    mapboxMap.cameraForGeometry(point, ZERO_EDGE_INSETS, 0.0, 1.0)
    verify { nativeMap.cameraForGeometry(point, ZERO_EDGE_INSETS, 0.0, 1.0) }
  }

  @Test
  fun cameraForGeometryOverload() {
    val point = mockk<Point>()
    mapboxMap.cameraForGeometry(point)
    verify { nativeMap.cameraForGeometry(point, null, null, null) }
  }

  @Test
  fun cameraForGeometryWithoutPadding() {
    val point = mockk<Point>()
    mapboxMap.cameraForGeometry(point)
    verify { nativeMap.cameraForGeometry(point, null, null, null) }
  }

  @Test
  fun cameraForGeometryOverloadBearing() {
    val point = mockk<Point>()
    mapboxMap.cameraForGeometry(point, bearing = 2.0)
    verify { nativeMap.cameraForGeometry(point, null, 2.0, null) }
  }

  @Test
  fun cameraForGeometryOverloadPitch() {
    val point = mockk<Point>()
    mapboxMap.cameraForGeometry(point, pitch = 2.0)
    verify { nativeMap.cameraForGeometry(point, null, null, 2.0) }
  }

  @Test
  fun coordinateForPixel() {
    val point = mockk<ScreenCoordinate>()
    mapboxMap.coordinateForPixel(point)
    verify { nativeMap.coordinateForPixel(point) }
  }

  @Test
  fun pixelsForCoordinates() {
    val points: List<Point> = emptyList()
    mapboxMap.pixelsForCoordinates(points)
    verify { nativeMap.pixelsForCoordinates(points.toMutableList()) }
  }

  @Test
  fun coordinatesForPixels() {
    val points: List<ScreenCoordinate> = emptyList()
    mapboxMap.coordinatesForPixels(points)
    verify { nativeMap.coordinatesForPixels(points.toMutableList()) }
  }

  @Test(expected = IllegalArgumentException::class)
  fun coordinatesForRectWithEmptyRect() {
    val rect = RectF()
    mapboxMap.coordinateBoundsForRect(rect)
  }

  @Test
  fun coordinatesForRectWithValidRect() {
    val rect = RectF(1f, 2f, 3f, 4f)
    val screenCoordinates = listOf(
      ScreenCoordinate(/* bottom */ 4.0, /* left */ 1.0),
      ScreenCoordinate(/* top */ 2.0, /* right */ 3.0),
    )
    every { nativeMap.coordinatesForPixels(any()) } returns
      mutableListOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))
    mapboxMap.coordinateBoundsForRect(rect)
    verify { nativeMap.coordinatesForPixels(screenCoordinates.toMutableList()) }
  }

  @Test
  fun coordinateInfoForPixel() {
    val pixel = mockk<ScreenCoordinate>()
    mapboxMap.coordinateInfoForPixel(pixel)
    verify { nativeMap.coordinateInfoForPixel(pixel) }
  }

  @Test
  fun coordinateInfoForPixels() {
    val pixels = mockk<List<ScreenCoordinate>>()
    mapboxMap.coordinatesInfoForPixels(pixels)
    verify { nativeMap.coordinatesInfoForPixels(pixels) }
  }

  @Test
  fun getBounds() {
    mapboxMap.getBounds()
    verify { nativeMap.getBounds() }
  }

  @Test
  fun setBounds() {
    val bounds = mockk<CameraBoundsOptions>()
    mapboxMap.setBounds(bounds)
    verify { nativeMap.setBounds(bounds) }
  }

  @Test
  fun setGestureInProgress() {
    mapboxMap.setGestureInProgress(true)
    verify { nativeMap.setGestureInProgress(true) }
  }

  @Test
  fun isGestureInProgress() {
    mapboxMap.isGestureInProgress()
    verify { nativeMap.isGestureInProgress() }
  }

  @Test
  fun setNorthOrientation() {
    mapboxMap.setNorthOrientation(NorthOrientation.DOWNWARDS)
    verify { nativeMap.setNorthOrientation(NorthOrientation.DOWNWARDS) }
  }

  @Test
  fun setConstrainMode() {
    mapboxMap.setConstrainMode(ConstrainMode.HEIGHT_ONLY)
    verify { nativeMap.setConstrainMode(ConstrainMode.HEIGHT_ONLY) }
  }

  @Test
  fun setViewportMode() {
    mapboxMap.setViewportMode(ViewportMode.FLIPPED_Y)
    verify { nativeMap.setViewportMode(ViewportMode.FLIPPED_Y) }
  }

  @Test
  fun setFeatureState() {
    val value = mockk<Value>()
    val callback = mockk<FeatureStateOperationCallback>()
    mapboxMap.setFeatureState("id", "source-layer", "feature", value, callback)
    verify { nativeMap.setFeatureState("id", "source-layer", "feature", value, callback) }
  }

  @Test
  fun getFeatureState() {
    val value = mockk<QueryFeatureStateCallback>()
    mapboxMap.getFeatureState("id", "source-layer", "feature", value)
    verify { nativeMap.getFeatureState("id", "source-layer", "feature", value) }
  }

  @Test
  fun removeFeatureState() {
    val callback = mockk<FeatureStateOperationCallback>()
    mapboxMap.removeFeatureState("id", "source-layer", "feature", "state", callback)
    verify { nativeMap.removeFeatureState("id", "source-layer", "feature", "state", callback) }
  }

  @Test
  fun getClusterLeavesDefaultParam() {
    val feature = mockk<Feature>()
    val mapSlot = slot<HashMap<String, Value>>()
    val callback = mockk<QueryFeatureExtensionCallback>()
    mapboxMap.getGeoJsonClusterLeaves("id", feature, callback = callback)
    verify {
      nativeMap.queryFeatureExtensions(
        "id",
        feature,
        MapboxMap.QFE_SUPER_CLUSTER,
        MapboxMap.QFE_LEAVES,
        capture(mapSlot),
        callback
      )
    }
    checkCapturedMap(
      mapSlot,
      MapboxMap.QFE_DEFAULT_LIMIT.toString(),
      MapboxMap.QFE_DEFAULT_OFFSET.toString()
    )
  }

  private fun checkCapturedMap(
    mapSlot: CapturingSlot<HashMap<String, Value>>,
    expectedLimit: String,
    expectedOffset: String
  ) {
    val captureMap = mapSlot.captured
    assertEquals(2, captureMap.size)
    assertEquals(expectedLimit, captureMap["limit"]!!.contents.toString())
    assertEquals(expectedOffset, captureMap["offset"]!!.contents.toString())
  }

  @Test
  fun getClusterLeaves() {
    val feature = mockk<Feature>()
    val mapSlot = slot<HashMap<String, Value>>()
    val callback = mockk<QueryFeatureExtensionCallback>()
    mapboxMap.getGeoJsonClusterLeaves("id", feature, 1, 2, callback = callback)
    verify {
      nativeMap.queryFeatureExtensions(
        "id",
        feature,
        MapboxMap.QFE_SUPER_CLUSTER,
        MapboxMap.QFE_LEAVES,
        capture(mapSlot),
        callback
      )
    }
    checkCapturedMap(mapSlot, "1", "2")
  }

  @Test
  fun getClusterChildren() {
    val feature = mockk<Feature>()
    val callback = mockk<QueryFeatureExtensionCallback>()
    mapboxMap.getGeoJsonClusterChildren("id", feature, callback = callback)
    verify {
      nativeMap.queryFeatureExtensions(
        "id",
        feature,
        MapboxMap.QFE_SUPER_CLUSTER,
        MapboxMap.QFE_CHILDREN,
        null,
        callback
      )
    }
  }

  @Test
  fun getClusterExpansionZoom() {
    val feature = mockk<Feature>()
    val callback = mockk<QueryFeatureExtensionCallback>()
    mapboxMap.getGeoJsonClusterExpansionZoom("id", feature, callback = callback)
    verify {
      nativeMap.queryFeatureExtensions(
        "id",
        feature,
        MapboxMap.QFE_SUPER_CLUSTER,
        MapboxMap.QFE_EXPANSION_ZOOM,
        null,
        callback
      )
    }
  }

  @Test
  fun getCameraState() {
    mapboxMap.cameraState
    verify { nativeMap.getCameraState() }
  }

  @Test
  fun setDebug() {
    val debugOptions = mockk<List<MapDebugOptions>>()
    mapboxMap.setDebug(debugOptions, true)
    verify { nativeMap.setDebug(debugOptions, true) }
  }

  @Test
  fun getDebug() {
    mapboxMap.getDebug()
    verify { nativeMap.getDebug() }
  }

  @Test
  fun getMapOptions() {
    mapboxMap.getMapOptions()
    verify { mapboxMap.getMapOptions() }
  }

  @Test
  fun setUserAnimationInProgress() {
    mapboxMap.setUserAnimationInProgress(true)
    verify { nativeMap.setUserAnimationInProgress(true) }
  }

  @Test
  fun isUserAnimationInProgress() {
    mapboxMap.isUserAnimationInProgress()
    verify { nativeMap.isUserAnimationInProgress() }
  }

  @Test
  fun reduceMemoryUse() {
    mapboxMap.reduceMemoryUse()
    verify { mapboxMap.reduceMemoryUse() }
  }

  @Test
  fun querySourceFeatures() {
    val querySourceCallback = mockk<QuerySourceFeaturesCallback>()
    val querySourceOptions = mockk<SourceQueryOptions>()
    mapboxMap.querySourceFeatures("id", querySourceOptions, querySourceCallback)
    verify { nativeMap.querySourceFeatures("id", querySourceOptions, querySourceCallback) }
  }

  @Test
  fun queryRenderedFeaturesGeometry() {
    val queryCallback = mockk<QueryRenderedFeaturesCallback>()
    val geometry = mockk<RenderedQueryGeometry>()
    val renderedQueryOptions = mockk<RenderedQueryOptions>()
    mapboxMap.queryRenderedFeatures(geometry, renderedQueryOptions, queryCallback)
    verify { nativeMap.queryRenderedFeatures(geometry, renderedQueryOptions, queryCallback) }
  }

  @Test
  fun queryRenderedFeaturesRenderedQueryGeometry() {
    val queryCallback = mockk<QueryRenderedFeaturesCallback>()
    val geometry = mockk<RenderedQueryGeometry>()
    val renderedQueryOptions = mockk<RenderedQueryOptions>()
    mapboxMap.queryRenderedFeatures(geometry, renderedQueryOptions, queryCallback)
    verify { nativeMap.queryRenderedFeatures(geometry, renderedQueryOptions, queryCallback) }
  }

  @Test
  fun getSize() {
    mapboxMap.getSize()
    verify { nativeMap.getSize() }
  }

  @Test
  fun getFreeCameraOptions() {
    mapboxMap.getFreeCameraOptions()
    verify { nativeMap.getFreeCameraOptions() }
  }

  @Test
  fun setFreeCameraOptions() {
    val options = mockk<FreeCameraOptions>()
    mapboxMap.setCamera(options)
    verify { nativeMap.setCamera(options) }
  }

  @Test
  fun getElevation() {
    val point = mockk<Point>()
    mapboxMap.getElevation(point)
    verify { nativeMap.getElevation(point) }
  }

  @Test
  fun cameraForDrag() {
    val fromPoint = mockk<ScreenCoordinate>()
    val toPoint = mockk<ScreenCoordinate>()
    mapboxMap.cameraForDrag(fromPoint, toPoint)
    verify { nativeMap.cameraForDrag(fromPoint, toPoint) }
  }

  @Test
  fun setCenterAltitudeMode() {
    val altitude = MapCenterAltitudeMode.SEA
    mapboxMap.setCenterAltitudeMode(altitude)
    verify { nativeMap.setCenterAltitudeMode(altitude) }
  }

  @Test
  fun startPerformanceStatisticsCollection() {
    val options = mockk<PerformanceStatisticsOptions>()
    val callback = mockk<PerformanceStatisticsCallback>()
    mapboxMap.startPerformanceStatisticsCollection(options, callback)
    verify { nativeMap.startPerformanceStatisticsCollection(options, callback) }
  }

  @Test
  fun stopPerformanceStatisticsCollection() {
    mapboxMap.stopPerformanceStatisticsCollection()
    verify { nativeMap.stopPerformanceStatisticsCollection() }
  }

  @Test
  fun cameraAnimationsPluginValid() {
    val animations = mockk<CameraAnimationsPlugin>(relaxed = true)
    mapboxMap.cameraAnimationsPlugin = animations
    val options = CameraOptions.Builder().build()
    mapboxMap.cameraAnimationsPlugin { easeTo(options) }
    verify {
      animations.easeTo(options, null)
    }
  }

  @Test
  fun cameraAnimationsPluginInvalid() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    mapboxMap.cameraAnimationsPlugin = null
    val options = CameraOptions.Builder().build()
    assertNull(mapboxMap.cameraAnimationsPlugin { easeTo(options) })
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun gesturesPluginValid() {
    val gestures = mockk<GesturesPlugin>(relaxed = true)
    mapboxMap.gesturesPlugin = gestures
    val moveListener = mockk<OnMoveListener>(relaxed = true)
    mapboxMap.gesturesPlugin { addOnMoveListener(moveListener) }
    verify {
      gestures.addOnMoveListener(moveListener)
    }
  }

  @Test
  fun gesturesPluginInvalid() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    mapboxMap.gesturesPlugin = null
    val moveListener = mockk<OnMoveListener>(relaxed = true)
    assertNull(mapboxMap.gesturesPlugin { addOnMoveListener(moveListener) })
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun cameraState() {
    mapboxMap.cameraState
    verify { nativeMap.getCameraState() }
  }

  @Test
  fun setTileCacheBudget() {
    val tileCacheBudget = mockk<TileCacheBudget>()
    mapboxMap.setTileCacheBudget(tileCacheBudget)
    verify { nativeMap.setTileCacheBudget(tileCacheBudget) }
  }

  @Test
  fun setsStyleTransitionAfterOnStyleDataEvent() {
    val options = mockk<TransitionOptions>(relaxed = true)
    val style = mockk<Style>(relaxUnitFun = true)
    mapboxMap.loadStyle(
      style(Style.STANDARD) {
        +options
      },
    )

    val userCallbackStyleSlot = CapturingSlot<Style.OnStyleLoaded>()

    verify {
      styleObserver.setLoadStyleListener(any(), capture(userCallbackStyleSlot), any(), any())
    }

    userCallbackStyleSlot.captured.onStyleLoaded(style)

    verify { style.setStyleTransition(options) }
  }

  @Test
  fun tileCover() {
    val tileCoverOptions = TileCoverOptions.Builder().build()
    val cameraOptions = CameraOptions.Builder().build()
    mapboxMap.tileCover(tileCoverOptions, cameraOptions)
    verify { nativeMap.tileCover(tileCoverOptions, cameraOptions) }
  }

  @Test
  fun getAttributions() {
    mapboxMap.getAttributions()
    verify { nativeMap.getAttributions() }
  }

  @Test
  fun subscribeCameraChange() {
    val listener = mockk<CameraChangedCallback>()
    mapboxMap.subscribeCameraChanged(listener)
    verify { nativeObserver.subscribeCameraChanged(listener) }
  }

  @Test
  fun subscribeMapIdleListener() {
    val listener = mockk<MapIdleCallback>()
    mapboxMap.subscribeMapIdle(listener)
    verify { nativeObserver.subscribeMapIdle(listener) }
  }

  @Test
  fun subscribeMapLoadErrorListener() {
    val listener = mockk<MapLoadingErrorCallback>()
    mapboxMap.subscribeMapLoadingError(listener)
    verify { nativeObserver.subscribeMapLoadingError(listener) }
  }

  @Test
  fun subscribeMapLoadedListener() {
    val listener = mockk<MapLoadedCallback>()
    mapboxMap.subscribeMapLoaded(listener)
    verify { nativeObserver.subscribeMapLoaded(listener) }
  }

  // Render frame events
  @Test
  fun subscribeRenderFrameFinishedListener() {
    val listener = mockk<RenderFrameFinishedCallback>()
    mapboxMap.subscribeRenderFrameFinished(listener)
    verify { nativeObserver.subscribeRenderFrameFinished(listener) }
  }

  @Test
  fun subscribeRenderFrameStartedListener() {
    val listener = mockk<RenderFrameStartedCallback>()
    mapboxMap.subscribeRenderFrameStarted(listener)
    verify { nativeObserver.subscribeRenderFrameStarted(listener) }
  }

  // Source events
  @Test
  fun subscribeSourceAddedListener() {
    val listener = mockk<SourceAddedCallback>()
    mapboxMap.subscribeSourceAdded(listener)
    verify { nativeObserver.subscribeSourceAdded(listener) }
  }

  @Test
  fun subscribeSourceDataLoadedListener() {
    val listener = mockk<SourceDataLoadedCallback>()
    mapboxMap.subscribeSourceDataLoaded(listener)
    verify { nativeObserver.subscribeSourceDataLoaded(listener) }
  }

  @Test
  fun subscribeSourceRemovedListener() {
    val listener = mockk<SourceRemovedCallback>()
    mapboxMap.subscribeSourceRemoved(listener)
    verify { nativeObserver.subscribeSourceRemoved(listener) }
  }

  // Style events
  @Test
  fun subscribeStyleLoadedListener() {
    val listener = mockk<StyleLoadedCallback>()
    mapboxMap.subscribeStyleLoaded(listener)
    verify { nativeObserver.subscribeStyleLoaded(listener) }
  }

  @Test
  fun subscribeStyleImageMissingListener() {
    val listener = mockk<StyleImageMissingCallback>()
    mapboxMap.subscribeStyleImageMissing(listener)
    verify { nativeObserver.subscribeStyleImageMissing(listener) }
  }

  @Test
  fun subscribeStyleImageRemoveUnusedListener() {
    val listener = mockk<StyleImageRemoveUnusedCallback>()
    mapboxMap.subscribeStyleImageRemoveUnused(listener)
    verify { nativeObserver.subscribeStyleImageRemoveUnused(listener) }
  }

  @Test
  fun subscribeStyleDataLoaded() {
    val listener = mockk<StyleDataLoadedCallback>()
    mapboxMap.subscribeStyleDataLoaded(listener)
    verify { nativeObserver.subscribeStyleDataLoaded(listener) }
  }

  @Test
  fun subscribeResourceRequest() {
    val listener = mockk<ResourceRequestCallback>()
    mapboxMap.subscribeResourceRequest(listener)
    verify { nativeObserver.subscribeResourceRequest(listener) }
  }

  @Test
  fun subscribeGenericEvent() {
    val listener = mockk<GenericEventCallback>()
    mapboxMap.subscribeGenericEvent("event1", listener)
    verify { nativeObserver.subscribeGenericEvent("event1", listener) }
  }

  @Test
  fun setRenderWorldCopies() {
    mapboxMap.setRenderWorldCopies(true)
    verify { nativeMap.setRenderWorldCopies(true) }
  }

  @Test
  fun getRenderWorldCopies() {
    mapboxMap.getRenderWorldCopies()
    verify { nativeMap.getRenderWorldCopies() }
  }
}

@RunWith(ParameterizedRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class PixelForCoordinatesTest(
  private val input: List<ScreenCoordinate>,
  private val expected: List<ScreenCoordinate>,
) {
  private val nativeMap: NativeMapImpl = mockk(relaxed = true)
  private val nativeObserver: NativeObserver = mockk(relaxed = true)

  private lateinit var styleObserver: StyleObserver
  private lateinit var mapboxMap: MapboxMap

  @Before
  fun setUp() {
    mockkStatic(kotlin.collections.Map::class)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    styleObserver = mockk(relaxUnitFun = true)
    mapboxMap = MapboxMap(nativeMap, nativeObserver, styleObserver)
  }

  @Test
  fun pixelForCoordinate() {
    val point = mockk<Point>()
    every { nativeMap.getSize() } returns Size(100f, 100f)
    every { nativeMap.pixelForCoordinate(point) } returns input[0]
    val screenCoordinate = mapboxMap.pixelForCoordinate(point)
    verifySequence {
      nativeMap.map
      nativeMap.pixelForCoordinate(point)
      nativeMap.getSize()
    }
    assertEquals(expected[0], screenCoordinate)
  }

  @Test
  fun pixelsForCoordinates() {
    val points = mockk<List<Point>>(relaxed = true)
    every { nativeMap.getSize() } returns Size(100f, 100f)
    every { nativeMap.pixelsForCoordinates(points.toMutableList()) } returns input.toMutableList()
    val screenCoordinates = mapboxMap.pixelsForCoordinates(points)
    assertEquals(expected, screenCoordinates)
  }

  @After
  fun cleanUp() {
    unmockkStatic(Map::class)
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  companion object {
    @JvmStatic
    @ParameterizedRobolectricTestRunner.Parameters(name = "Input ScreenCoordinate({0}, {1}) should be mapped to ScreenCoordinate({2}, {3})")
    @Suppress("unused")
    fun data() = listOf(
      arrayOf(
        listOf(ScreenCoordinate(150.0, 150.0)),
        listOf(ScreenCoordinate(-1.0, -1.0)),
      ),
      arrayOf(
        listOf(ScreenCoordinate(50.0, 50.0)),
        listOf(ScreenCoordinate(50.0, 50.0)),
      ),
      arrayOf(
        listOf(ScreenCoordinate(0.0, 0.0)),
        listOf(ScreenCoordinate(0.0, 0.0)),
      ),
      arrayOf(
        listOf(ScreenCoordinate(100.0, 100.0)),
        listOf(ScreenCoordinate(100.0, 100.0))
      ),
      arrayOf(
        listOf(ScreenCoordinate(100.0000000345, 100.0)),
        listOf(ScreenCoordinate(100.0, 100.0))
      ),
      arrayOf(
        listOf(ScreenCoordinate(0.0000000345, 100.0)),
        listOf(ScreenCoordinate(0.0000000345, 100.0))
      ),
      arrayOf(
        listOf(ScreenCoordinate(0.1000000345, 100.0)),
        listOf(ScreenCoordinate(0.1000000345, 100.0))
      ),
      arrayOf(
        listOf(ScreenCoordinate(100.45, 100.0)),
        listOf(ScreenCoordinate(100.0, 100.0))
      ),
      arrayOf(
        listOf(ScreenCoordinate(100.500001, 100.0)),
        listOf(ScreenCoordinate(-1.0, -1.0))
      ),
      arrayOf(
        listOf(ScreenCoordinate(100.0, -0.0000001)),
        listOf(ScreenCoordinate(100.0, 0.0)),
      ),
      arrayOf(
        listOf(
          ScreenCoordinate(100.0, -0.0000001),
          ScreenCoordinate(0.0, 0.0),
          ScreenCoordinate(100.0, 100.0),
          ScreenCoordinate(100.1233445, 100.0000434)
        ),
        listOf(
          ScreenCoordinate(100.0, 0.0),
          ScreenCoordinate(0.0, 0.0),
          ScreenCoordinate(100.0, 100.0),
          ScreenCoordinate(100.0, 100.0)
        ),
      ),
      arrayOf(
        listOf(
          ScreenCoordinate(100.56666, 110.0000001),
          ScreenCoordinate(-10.0, -10.0),
          ScreenCoordinate(0.023456, -0.023456),
          ScreenCoordinate(100.1233445, 50.0)
        ),
        listOf(
          ScreenCoordinate(-1.0, -1.0),
          ScreenCoordinate(-1.0, -1.0),
          ScreenCoordinate(0.023456, 0.0),
          ScreenCoordinate(100.0, 50.0)
        ),
      ),
    )
  }
}