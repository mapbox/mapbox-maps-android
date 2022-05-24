package com.mapbox.maps

import android.os.Looper
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.MapProjection
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.listeners.*
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.verifyNo
import io.mockk.*
import junit.framework.Assert.*
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(shadows = [ShadowMap::class])
class MapboxMapTest {

  private val nativeMap: MapInterface = mockk(relaxed = true)
  private val nativeObserver: NativeObserver = mockk(relaxed = true)
  private val resourceOptions = mockk<ResourceOptions>(relaxed = true)

  private lateinit var styleObserver: StyleObserver
  private lateinit var mapboxMap: MapboxMap

  @Before
  fun setUp() {
    mockkStatic(Map::class)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    every { Map.clearData(any(), any()) } just runs
    every { nativeMap.resourceOptions } returns resourceOptions
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
  fun loadStyleUri() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyleUri("foo")
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.styleURI = "foo" }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleUriLambda() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyleUri("foo") {}
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleJSON() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyleJson("foo")
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.styleJSON = "foo" }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleJSONLambda() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyleJson("foo") {}
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.styleJSON = "foo" }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadEmptyStyle() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle(style("") {})
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.styleJSON = "{}" }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyle() {
    val styleExtension = mockk<StyleContract.StyleExtension>()
    every { styleExtension.styleUri } returns "foobar"
    val onMapLoadError = mockk<OnMapLoadErrorListener>()
    val onStyleLoadError = mockk<Style.OnStyleLoaded>()
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle(styleExtension, onStyleLoadError, onMapLoadError)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.styleURI = "foobar" }
    assertTrue(mapboxMap.isStyleLoadInitiated)
  }

  @Test
  fun loadStyleLambda() {
    val styleExtension = mockk<StyleContract.StyleExtension>()
    every { styleExtension.styleUri } returns "foobar"
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    assertFalse(mapboxMap.isStyleLoadInitiated)
    mapboxMap.loadStyle(styleExtension) {}
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.styleURI = "foobar" }
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

    val light = mockk<StyleContract.StyleLightExtension>(relaxed = true)
    every { styleExtension.light } returns light

    val terrain = mockk<StyleContract.StyleTerrainExtension>(relaxed = true)
    every { styleExtension.terrain } returns terrain

    val atmosphere = mockk<StyleContract.StyleAtmosphereExtension>(relaxed = true)
    every { styleExtension.atmosphere } returns atmosphere

    val projection = mockk<StyleContract.StyleProjectionExtension>(relaxed = true)
    every { styleExtension.projection } returns projection

    val model = mockk<StyleContract.StyleModelExtension>(relaxed = true)
    every { styleExtension.models } returns listOf(model)

    val styleLoadCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    val userCallbackStyleSlots = mutableListOf<Style.OnStyleLoaded?>()
    val callbackStyleSlots = mutableListOf<Style.OnStyleLoaded?>()
    val callbackStyleSpritesSlots = mutableListOf<Style.OnStyleLoaded?>()
    val callbackStyleSourcesSlots = mutableListOf<Style.OnStyleLoaded?>()

    mapboxMap.loadStyle(styleExtension, styleLoadCallback)

    verify {
      styleObserver.setLoadStyleListener(
        captureNullable(userCallbackStyleSlots),
        captureNullable(callbackStyleSlots),
        captureNullable(callbackStyleSpritesSlots),
        captureNullable(callbackStyleSourcesSlots),
        any(),
      )
    }

    verifyNo { source.bindTo(style) }
    verifyNo { image.bindTo(style) }
    verifyNo { layer.bindTo(style, layerPosition) }
    verifyNo { light.bindTo(style) }
    verifyNo { terrain.bindTo(style) }
    verifyNo { projection.bindTo(style) }
    verifyNo { atmosphere.bindTo(style) }
    verifyNo { styleLoadCallback.onStyleLoaded(style) }
    verifyNo { model.bindTo(style) }

    callbackStyleSlots.first()!!.onStyleLoaded(style)

    verify { light.bindTo(style) }
    verify { terrain.bindTo(style) }
    verify { projection.bindTo(style) }
    verify { atmosphere.bindTo(style) }
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
    assertNotNull(mapboxMap.getStyle())
  }

  @Test
  fun getStyleSynchronouslyNotReady() {
    assertNull(mapboxMap.getStyle())
  }

  @Test
  fun getResourceOptions() {
    mapboxMap.getResourceOptions()
    verify { nativeMap.resourceOptions }
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
  fun addObserver() {
    val observer = mockk<Observer>()
    val debugList = arrayListOf<String>()
    mapboxMap.subscribe(observer, debugList)
    verify { (nativeMap as ObservableInterface).subscribe(observer, debugList) }
  }

  @Test
  fun removeObserver() {
    val observer = mockk<Observer>()
    val debugList = arrayListOf<String>()
    mapboxMap.subscribe(observer, debugList)
    mapboxMap.unsubscribe(observer)
    verify { (nativeMap as ObservableInterface).unsubscribe(observer) }
  }

  @Test
  fun removeObserverList() {
    val observer = mockk<Observer>()
    val debugList = arrayListOf<String>()
    mapboxMap.subscribe(observer, debugList)
    mapboxMap.unsubscribe(observer, debugList)
    verify { (nativeMap as ObservableInterface).unsubscribe(observer, debugList) }
  }

  @Test
  fun setPrefetchZoomDelta() {
    mapboxMap.setPrefetchZoomDelta(3)
    verify { nativeMap.prefetchZoomDelta = 3 }
  }

  @Test
  fun getPrefetchZoomDelta() {
    mapboxMap.getPrefetchZoomDelta()
    verify { nativeMap.prefetchZoomDelta }
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
    mapboxMap.cameraForCoordinateBounds(bounds, edgeInsets, 0.0, 1.0)
    verify { nativeMap.cameraForCoordinateBounds(bounds, edgeInsets, 0.0, 1.0) }
  }

  @Test
  fun cameraForCoordinateBoundsOverload() {
    val bounds = mockk<CoordinateBounds>()
    mapboxMap.cameraForCoordinateBounds(bounds)
    verify {
      nativeMap.cameraForCoordinateBounds(
        bounds,
        EdgeInsets(0.0, 0.0, 0.0, 0.0),
        null,
        null
      )
    }
  }

  @Test
  fun cameraForCoordinateBoundsOverloadPadding() {
    val bounds = mockk<CoordinateBounds>()
    val padding = EdgeInsets(1.1, 1.3, 1.4, 1.2)
    mapboxMap.cameraForCoordinateBounds(bounds, padding)
    verify { nativeMap.cameraForCoordinateBounds(bounds, padding, null, null) }
  }

  @Test
  fun cameraForCoordinateBoundsOverloadBearing() {
    val bounds = mockk<CoordinateBounds>()
    mapboxMap.cameraForCoordinateBounds(bounds, bearing = 2.0)
    verify {
      nativeMap.cameraForCoordinateBounds(
        bounds,
        EdgeInsets(0.0, 0.0, 0.0, 0.0),
        2.0,
        null
      )
    }
  }

  @Test
  fun cameraForCoordinateBoundsOverloadPitch() {
    val bounds = mockk<CoordinateBounds>()
    mapboxMap.cameraForCoordinateBounds(bounds, pitch = 2.0)
    verify {
      nativeMap.cameraForCoordinateBounds(
        bounds,
        EdgeInsets(0.0, 0.0, 0.0, 0.0),
        null,
        2.0
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
    verify { nativeMap.cameraForCoordinates(points, EdgeInsets(0.0, 0.0, 0.0, 0.0), null, null) }
  }

  @Test
  fun cameraForCoordinatesOverloadBearing() {
    val points = mockk<List<Point>>()
    mapboxMap.cameraForCoordinates(points, bearing = 2.0)
    verify { nativeMap.cameraForCoordinates(points, EdgeInsets(0.0, 0.0, 0.0, 0.0), 2.0, null) }
  }

  @Test
  fun cameraForCoordinatesOverloadPitch() {
    val points = mockk<List<Point>>()
    mapboxMap.cameraForCoordinates(points, pitch = 2.0)
    verify { nativeMap.cameraForCoordinates(points, EdgeInsets(0.0, 0.0, 0.0, 0.0), null, 2.0) }
  }

  @Test
  fun cameraForGeometry() {
    val point = mockk<Point>()
    val edgeInsets = mockk<EdgeInsets>()
    mapboxMap.cameraForGeometry(point, edgeInsets, 0.0, 1.0)
    verify { nativeMap.cameraForGeometry(point, edgeInsets, 0.0, 1.0) }
  }

  @Test
  fun cameraForGeometryOverload() {
    val point = mockk<Point>()
    mapboxMap.cameraForGeometry(point)
    verify { nativeMap.cameraForGeometry(point, EdgeInsets(0.0, 0.0, 0.0, 0.0), null, null) }
  }

  @Test
  fun cameraForGeometryOverloadBearing() {
    val point = mockk<Point>()
    mapboxMap.cameraForGeometry(point, bearing = 2.0)
    verify { nativeMap.cameraForGeometry(point, EdgeInsets(0.0, 0.0, 0.0, 0.0), 2.0, null) }
  }

  @Test
  fun cameraForGeometryOverloadPitch() {
    val point = mockk<Point>()
    mapboxMap.cameraForGeometry(point, pitch = 2.0)
    verify { nativeMap.cameraForGeometry(point, EdgeInsets(0.0, 0.0, 0.0, 0.0), null, 2.0) }
  }

  @Test
  fun coordinateForPixel() {
    val point = mockk<ScreenCoordinate>()
    mapboxMap.coordinateForPixel(point)
    verify { nativeMap.coordinateForPixel(point) }
  }

  @Test
  fun pixelsForCoordinates() {
    val points = mockk<List<Point>>()
    mapboxMap.pixelsForCoordinates(points)
    verify { nativeMap.pixelsForCoordinates(points) }
  }

  @Test
  fun coordinatesForPixels() {
    val points = mockk<List<ScreenCoordinate>>()
    mapboxMap.coordinatesForPixels(points)
    verify { nativeMap.coordinatesForPixels(points) }
  }

  @Test
  fun getBounds() {
    mapboxMap.getBounds()
    verify { nativeMap.bounds }
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
    verify { nativeMap.isGestureInProgress = true }
  }

  @Test
  fun isGestureInProgress() {
    mapboxMap.isGestureInProgress()
    verify { nativeMap.isGestureInProgress }
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
    mapboxMap.setFeatureState("id", "source-layer", "feature", value)
    verify { nativeMap.setFeatureState("id", "source-layer", "feature", value) }
  }

  @Test
  fun getFeatureState() {
    val value = mockk<QueryFeatureStateCallback>()
    mapboxMap.getFeatureState("id", "source-layer", "feature", value)
    verify { nativeMap.getFeatureState("id", "source-layer", "feature", value) }
  }

  @Test
  fun removeFeatureState() {
    mapboxMap.removeFeatureState("id", "source-layer", "feature", "state")
    verify { nativeMap.removeFeatureState("id", "source-layer", "feature", "state") }
  }

  @Test
  fun queryFeatureExtensions() {
    val feature = mockk<Feature>()
    val map = mockk<HashMap<String, Value>>()
    val callback = mockk<QueryFeatureExtensionCallback>()
    mapboxMap.queryFeatureExtensions("id", feature, "extension", "extensionField", map, callback)
    verify {
      nativeMap.queryFeatureExtensions(
        "id",
        feature,
        "extension",
        "extensionField",
        map,
        callback
      )
    }
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
    verify { nativeMap.cameraState }
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
    verify { nativeMap.debug }
  }

  @Test
  fun getMapOptions() {
    mapboxMap.getMapOptions()
    verify { mapboxMap.getMapOptions() }
  }

  @Test
  fun setUserAnimationInProgress() {
    mapboxMap.setUserAnimationInProgress(true)
    verify { nativeMap.isUserAnimationInProgress = true }
  }

  @Test
  fun isUserAnimationInProgress() {
    mapboxMap.isUserAnimationInProgress()
    verify { nativeMap.isUserAnimationInProgress }
  }

  @Test
  fun reduceMemoryUse() {
    mapboxMap.reduceMemoryUse()
    verify { mapboxMap.reduceMemoryUse() }
  }

  @Test
  fun querySourceFeatures() {
    val querySourceCallback = mockk<QueryFeaturesCallback>()
    val querySourceOptions = mockk<SourceQueryOptions>()
    mapboxMap.querySourceFeatures("id", querySourceOptions, querySourceCallback)
    verify { nativeMap.querySourceFeatures("id", querySourceOptions, querySourceCallback) }
  }

  @Test
  fun queryRenderedFeaturesScreenBox() {
    val queryCallback = mockk<QueryFeaturesCallback>()
    val box = mockk<ScreenBox>()
    val renderedQueryOptions = mockk<RenderedQueryOptions>()
    mapboxMap.queryRenderedFeatures(box, renderedQueryOptions, queryCallback)
    verify { nativeMap.queryRenderedFeatures(box, renderedQueryOptions, queryCallback) }
  }

  @Test
  fun queryRenderedFeaturesScreenCoordinate() {
    val queryCallback = mockk<QueryFeaturesCallback>()
    val coordinate = mockk<ScreenCoordinate>()
    val renderedQueryOptions = mockk<RenderedQueryOptions>()
    mapboxMap.queryRenderedFeatures(coordinate, renderedQueryOptions, queryCallback)
    verify { nativeMap.queryRenderedFeatures(coordinate, renderedQueryOptions, queryCallback) }
  }

  @Test
  fun queryRenderedFeaturesShape() {
    val queryCallback = mockk<QueryFeaturesCallback>()
    val shape = mockk<List<ScreenCoordinate>>()
    val renderedQueryOptions = mockk<RenderedQueryOptions>()
    mapboxMap.queryRenderedFeatures(shape, renderedQueryOptions, queryCallback)
    verify { nativeMap.queryRenderedFeatures(shape, renderedQueryOptions, queryCallback) }
  }

  @Test
  fun queryRenderedFeaturesGeometry() {
    val queryCallback = mockk<QueryFeaturesCallback>()
    val geometry = mockk<RenderedQueryGeometry>()
    val renderedQueryOptions = mockk<RenderedQueryOptions>()
    mapboxMap.queryRenderedFeatures(geometry, renderedQueryOptions, queryCallback)
    verify { nativeMap.queryRenderedFeatures(geometry, renderedQueryOptions, queryCallback) }
  }

  @Test
  fun queryRenderedFeaturesRenderedQueryGeometry() {
    val queryCallback = mockk<QueryFeaturesCallback>()
    val geometry = mockk<RenderedQueryGeometry>()
    val renderedQueryOptions = mockk<RenderedQueryOptions>()
    mapboxMap.queryRenderedFeatures(geometry, renderedQueryOptions, queryCallback)
    verify { nativeMap.queryRenderedFeatures(geometry, renderedQueryOptions, queryCallback) }
  }

  @Test
  fun getSize() {
    mapboxMap.getSize()
    verify { nativeMap.size }
  }

  @Test
  fun getFreeCameraOptions() {
    mapboxMap.getFreeCameraOptions()
    verify { nativeMap.freeCameraOptions }
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
  fun isFullyLoaded() {
    val style = mockk<Style>(relaxed = true)
    mapboxMap.style = style
    every { style.isStyleLoaded } returns true
    assertTrue(mapboxMap.isFullyLoaded())
    verify { style.isStyleLoaded }
  }

  @Test
  fun dragStart() {
    val screenCoordinate = mockk<ScreenCoordinate>()
    mapboxMap.dragStart(screenCoordinate)
    verify { nativeMap.dragStart(screenCoordinate) }
  }

  @Test
  fun dragEnd() {
    mapboxMap.dragEnd()
    verify { nativeMap.dragEnd() }
  }

  @Test
  fun getDragCameraOptions() {
    val fromPoint = mockk<ScreenCoordinate>()
    val toPoint = mockk<ScreenCoordinate>()
    mapboxMap.getDragCameraOptions(fromPoint, toPoint)
    verify { nativeMap.getDragCameraOptions(fromPoint, toPoint) }
  }

  @Test
  fun setCameraAnimationsPluginInvalid() {
    mapboxMap.setCameraAnimationPlugin(mockk())
    assertNull(mapboxMap.cameraAnimationsPlugin)
  }

  @Test
  fun setGesturesPluginInvalid() {
    mapboxMap.setGesturesAnimationPlugin(mockk())
    assertNull(mapboxMap.gesturesPlugin)
  }

  @Test
  fun cameraAnimationsPluginValid() {
    val animations = mockk<CameraAnimationsPlugin>(relaxed = true)
    mapboxMap.cameraAnimationsPlugin = WeakReference(animations)
    val options = CameraOptions.Builder().build()
    mapboxMap.cameraAnimationsPlugin { easeTo(options) }
    verify {
      animations.easeTo(options, null)
    }
  }

  @Test
  fun cameraAnimationsPluginInvalid() {
    val animations = mockk<CameraAnimationsPlugin>(relaxed = true)
    mapboxMap.cameraAnimationsPlugin = WeakReference(animations)
    val options = CameraOptions.Builder().build()
    mapboxMap.cameraAnimationsPlugin?.clear()
    val exception = assertThrows(IllegalStateException::class.java) {
      mapboxMap.cameraAnimationsPlugin { easeTo(options) }
    }
    assertEquals(
      "Camera plugin is not added as the part of MapInitOptions for given MapView.",
      exception.message
    )
    verify(exactly = 0) {
      animations.easeTo(options, null)
    }
  }

  @Test
  fun gesturesPluginValid() {
    val gestures = mockk<GesturesPlugin>(relaxed = true)
    mapboxMap.gesturesPlugin = WeakReference(gestures)
    val moveListener = mockk<OnMoveListener>(relaxed = true)
    mapboxMap.gesturesPlugin { addOnMoveListener(moveListener) }
    verify {
      gestures.addOnMoveListener(moveListener)
    }
  }

  @Test
  fun gesturesPluginInvalid() {
    val gestures = mockk<GesturesPlugin>(relaxed = true)
    mapboxMap.gesturesPlugin = WeakReference(gestures)
    val moveListener = mockk<OnMoveListener>(relaxed = true)
    mapboxMap.gesturesPlugin?.clear()
    val exception = assertThrows(IllegalStateException::class.java) {
      mapboxMap.gesturesPlugin { addOnMoveListener(moveListener) }
    }
    assertEquals(
      "Gesture plugin is not added as the part of MapInitOptions for given MapView.",
      exception.message
    )
    verify(exactly = 0) {
      gestures.addOnMoveListener(moveListener)
    }
  }

  @Test
  fun cameraState() {
    mapboxMap.cameraState
    verify { nativeMap.cameraState }
  }

  @Test
  fun clearData() {
    val callback = mockk<AsyncOperationResultCallback>(relaxed = true)
    mapboxMap.clearData(callback)
    verify { Map.clearData(resourceOptions, callback) }
  }

  @Test
  fun setMapProjectionMercator() {
    verifySetMapProjection(MapProjection.Mercator, "mercator")
  }

  @Test
  fun setMapProjectionGlobe() {
    verifySetMapProjection(MapProjection.Globe, "globe")
  }

  private fun verifySetMapProjection(givenProjection: MapProjection, expectedValue: String) {
    mapboxMap.setMapProjection(givenProjection)
    verify {
      nativeMap.setStyleProjection(
        Value.valueOf(
          hashMapOf(
            "name" to Value(expectedValue)
          )
        )
      )
    }
  }

  @Test
  fun getMapProjectionMercator() {
    every { nativeMap.getStyleProjectionProperty("name") } returns StylePropertyValue(
      Value.valueOf("mercator"),
      StylePropertyValueKind.CONSTANT
    )
    assertEquals(MapProjection.Mercator, mapboxMap.getMapProjection())
  }

  @Test
  fun getMapProjectionGlobe() {
    every { nativeMap.getStyleProjectionProperty("name") } returns StylePropertyValue(
      Value.valueOf("globe"),
      StylePropertyValueKind.CONSTANT
    )
    assertEquals(MapProjection.Globe, mapboxMap.getMapProjection())
  }

  @Test
  fun getMapProjectionDefault() {
    every { nativeMap.getStyleProjectionProperty("name") } returns StylePropertyValue(
      Value.valueOf("globe"),
      StylePropertyValueKind.UNDEFINED
    )
    assertEquals(MapProjection.Mercator, mapboxMap.getMapProjection())
  }

  @Test
  fun setMemoryBudget() {
    val memoryBudget = mockk<MapMemoryBudget>()
    mapboxMap.setMemoryBudget(memoryBudget)
    verify { nativeMap.setMemoryBudget(memoryBudget) }
  }

  @Test
  fun setsStyleTransitionAfterOnStyleDataEvent() {
    val options = mockk<TransitionOptions>(relaxed = true)
    val style = mockk<Style>(relaxUnitFun = true)
    mapboxMap.loadStyleUri(
      "style",
      options,
      {}
    )

    val userCallbackStyleSlot = CapturingSlot<Style.OnStyleLoaded>()

    verify {
      styleObserver.setLoadStyleListener(any(), capture(userCallbackStyleSlot), any(), any(), any())
    }

    userCallbackStyleSlot.captured.onStyleLoaded(style)

    verify { style.styleTransition = options }
  }

  @Test
  fun addStyleModel() {
    val modelId = "modelId"
    val modelUri = "modelUri"
    mapboxMap.addStyleModel(modelId, modelUri)
    verify { nativeMap.addStyleModel(modelId, modelUri) }
  }

  @Test
  fun removeStyleModel() {
    val modelId = "modelId"
    mapboxMap.removeStyleModel(modelId)
    verify { nativeMap.removeStyleModel(modelId) }
  }

  @Test
  fun hasStyleModel() {
    val modelId = "modelId"
    mapboxMap.hasStyleModel(modelId)
    verify { nativeMap.hasStyleModel(modelId) }
  }
}

@RunWith(ParameterizedRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(shadows = [ShadowMap::class])
class PixelForCoordinatesTest(
  private val inputX: Double,
  private val inputY: Double,
  private val expectedX: Double,
  private val expectedY: Double
) {
  private val nativeMap: MapInterface = mockk(relaxed = true)
  private val nativeObserver: NativeObserver = mockk(relaxed = true)
  private val resourceOptions = mockk<ResourceOptions>(relaxed = true)

  private lateinit var styleObserver: StyleObserver
  private lateinit var mapboxMap: MapboxMap

  @Before
  fun setUp() {
    mockkStatic(kotlin.collections.Map::class)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    every { nativeMap.resourceOptions } returns resourceOptions
    styleObserver = mockk(relaxUnitFun = true)
    mapboxMap = MapboxMap(nativeMap, nativeObserver, styleObserver)
  }

  @Test
  fun pixelForCoordinate() {
    val point = mockk<Point>()
    val convertedScreenCoordinate = ScreenCoordinate(inputX, inputY)
    every { nativeMap.size } returns Size(100f, 100f)
    every { nativeMap.pixelForCoordinate(point) } returns convertedScreenCoordinate
    val screenCoordinate = mapboxMap.pixelForCoordinate(point)
    verifySequence {
      nativeMap.pixelForCoordinate(point)
      nativeMap.size
    }
    assertEquals(ScreenCoordinate(expectedX, expectedY), screenCoordinate)
  }

  @After
  fun cleanUp() {
    unmockkStatic(Map::class)
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  companion object {
    @JvmStatic
    @ParameterizedRobolectricTestRunner.Parameters(name = "Input ScreenCoordinate({0}, {1}) should be mapped to ScreenCoordinate({2}, {3})")
    fun data() = listOf(
      arrayOf(
        150.0,
        150.0,
        -1.0,
        -1.0
      ),
      arrayOf(
        50.0,
        50.0,
        50.0,
        50.0
      ),
      arrayOf(
        0.0,
        0.0,
        0.0,
        0.0
      ),
      arrayOf(
        100.0,
        100.0,
        100.0,
        100.0
      )
    )
  }
}