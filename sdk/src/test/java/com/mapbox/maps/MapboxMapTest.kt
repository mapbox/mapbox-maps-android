package com.mapbox.maps

import android.os.Looper
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.listeners.*
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMoveListener
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class MapboxMapTest {

  private val nativeMap: CustomMapInterface = mockk(relaxed = true)
  private val nativeObserver: NativeObserver = mockk(relaxed = true)

  private lateinit var mapboxMap: MapboxMap

  @Before
  fun setUp() {
    mapboxMap = MapboxMap(nativeMap, nativeObserver, 1.0f)
  }

  @Test
  fun loadStyleUri() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    mapboxMap.loadStyleUri("foo")
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeObserver.addOnStyleDataLoadedListener(any()) }
    verify { nativeMap.styleURI = "foo" }
  }

  @Test
  fun loadStyleUriLambda() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    mapboxMap.loadStyleUri("foo") {}
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeObserver.addOnStyleDataLoadedListener(any()) }
    verify { nativeMap.styleURI = "foo" }
  }

  @Test
  fun loadStyleJSON() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    mapboxMap.loadStyleJSON("foo")
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeObserver.addOnStyleDataLoadedListener(any()) }
    verify { nativeMap.styleJSON = "foo" }
  }

  @Test
  fun loadStyleJSONLambda() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    mapboxMap.loadStyleJSON("foo") {}
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeObserver.addOnStyleDataLoadedListener(any()) }
    verify { nativeMap.styleJSON = "foo" }
  }

  @Test
  fun loadStyle() {
    val styleExtension = mockk<StyleContract.StyleExtension>()
    every { styleExtension.styleUri } returns "foobar"
    val onMapLoadError = mockk<OnMapLoadErrorListener>()
    val onStyleLoadError = mockk<Style.OnStyleLoaded>()
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    mapboxMap.loadStyle(styleExtension, onStyleLoadError, onMapLoadError)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.styleURI = "foobar" }
  }

  @Test
  fun loadStyleLambda() {
    val styleExtension = mockk<StyleContract.StyleExtension>()
    every { styleExtension.styleUri } returns "foobar"
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    mapboxMap.loadStyle(styleExtension) {}
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { nativeMap.styleURI = "foobar" }
  }

  @Test
  fun finishLoadingStyle() {
    val styleLoadCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val mapLoadError = mockk<OnMapLoadErrorListener>()
    mapboxMap.onFinishLoadingStyle(styleLoadCallback, mapLoadError)
    verify { styleLoadCallback.onStyleLoaded(any()) }
    verify { nativeObserver.awaitingStyleGetters.clear() }
    verify { mapboxMap.removeOnMapLoadErrorListener(mapLoadError) }
  }

  @Test
  fun finishLoadingStyleExtension() {
    val style = mockk<Style>()
    val styleExtension = mockk<StyleContract.StyleExtension>()

    val source = mockk<StyleContract.StyleSourceExtension>(relaxed = true)
    every { styleExtension.sources } returns listOf(source)

    val image = mockk<StyleContract.StyleImageExtension>(relaxed = true)
    every { styleExtension.images } returns listOf(image)

    val layer = mockk<StyleContract.StyleLayerExtension>(relaxed = true)
    val layerPosition = LayerPosition(null, null, 0)
    every { styleExtension.layers } returns listOf(
      Pair(layer, layerPosition)
    )

    val light = mockk<StyleContract.StyleLightExtension>(relaxed = true)
    every { styleExtension.light } returns light

    val terrain = mockk<StyleContract.StyleTerrainExtension>(relaxed = true)
    every { styleExtension.terrain } returns terrain

    val styleLoadCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    mapboxMap.onFinishLoadingStyleExtension(style, styleExtension, styleLoadCallback)

    assertEquals(mapboxMap.style, style)
    verify { source.bindTo(style) }
    verify { image.bindTo(style) }
    verify { layer.bindTo(style, layerPosition) }
    verify { light.bindTo(style) }
    verify { terrain.bindTo(style) }
    verify { styleLoadCallback.onStyleLoaded(style) }
  }

  @Test
  fun getStyleWaitCallback() {
    val styleLoadCallback = mockk<Style.OnStyleLoaded>()
    mapboxMap.getStyle(styleLoadCallback)
    verify { nativeObserver.awaitingStyleGetters.add(styleLoadCallback) }
  }

  @Test
  fun getStyleLoadedCallback() {
    val style = mockk<Style>()
    mapboxMap.style = style
    every { style.fullyLoaded } returns true
    val styleLoadCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    mapboxMap.getStyle(styleLoadCallback)
    verify { styleLoadCallback.onStyleLoaded(style) }
  }

  @Test
  fun getStyleLoadedWaitCallback() {
    val style = mockk<Style>()
    mapboxMap.style = style
    every { style.fullyLoaded } returns false
    val styleLoadCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    mapboxMap.getStyle(styleLoadCallback)
    verify { nativeObserver.awaitingStyleGetters.add(styleLoadCallback) }
  }

  @Test
  fun getStyleSynchronously() {
    val style = mockk<Style>()
    mapboxMap.style = style
    every { style.fullyLoaded } returns true
    assertNotNull(mapboxMap.getStyle())
  }

  @Test
  fun getStyleSynchronouslyNull() {
    val style = mockk<Style>()
    mapboxMap.style = style
    every { style.fullyLoaded } returns false
    assertNull(mapboxMap.getStyle())
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
    mapboxMap.unsubscribe(observer)
    verify { (nativeMap as ObservableInterface).unsubscribe(observer) }
  }

  @Test
  fun removeObserverList() {
    val observer = mockk<Observer>()
    val debugList = arrayListOf<String>()
    mapboxMap.unsubscribe(observer, debugList)
    verify { (nativeMap as ObservableInterface).unsubscribe(observer, debugList) }
  }

  @Test
  fun dumpDebugLogs() {
    mapboxMap.dumpDebugLogs()
    verify { nativeMap.dumpDebugLogs() }
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
  fun coordinateBoundsForCamera() {
    val cameraOptions = mockk<CameraOptions>()
    mapboxMap.coordinateBoundsForCamera(cameraOptions)
    verify { nativeMap.coordinateBoundsForCamera(cameraOptions) }
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
  fun cameraForCoordinates() {
    val points = mockk<List<Point>>()
    val edgeInsets = mockk<EdgeInsets>()
    mapboxMap.cameraForCoordinates(points, edgeInsets, 0.0, 1.0)
    verify { nativeMap.cameraForCoordinates(points, edgeInsets, 0.0, 1.0) }
  }

  @Test
  fun cameraForGeometry() {
    val point = mockk<Point>()
    val edgeInsets = mockk<EdgeInsets>()
    mapboxMap.cameraForGeometry(point, edgeInsets, 0.0, 1.0)
    verify { nativeMap.cameraForGeometry(point, edgeInsets, 0.0, 1.0) }
  }

  @Test
  fun pixelForCoordinate() {
    val point = mockk<Point>()
    mapboxMap.pixelForCoordinate(point)
    verify { nativeMap.pixelForCoordinate(point) }
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
    val bounds = mockk<BoundOptions>()
    mapboxMap.setBounds(bounds)
    verify { nativeMap.bounds = bounds }
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
  fun setDefaultFramebufferObject() {
    val id = 1
    mapboxMap.setDefaultFramebufferObject(id)
    verify { nativeMap.setDefaultFramebufferObject(id) }
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
  fun getCameraOptions() {
    val edgeInsets = mockk<EdgeInsets>()
    mapboxMap.getCameraOptions(edgeInsets)
    verify { nativeMap.getCameraOptions(edgeInsets) }
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
  fun queryRenderedFeaturesGeometry() {
    val queryCallback = mockk<QueryFeaturesCallback>()
    val point = mockk<ScreenCoordinate>()
    val renderedQueryOptions = mockk<RenderedQueryOptions>()
    mapboxMap.queryRenderedFeatures(point, renderedQueryOptions, queryCallback)
    verify { nativeMap.queryRenderedFeatures(point, renderedQueryOptions, queryCallback) }
  }

  @Test
  fun getScale() {
    mapboxMap.getScale()
    verify { nativeMap.scale }
  }

  @Test
  fun getMinZoom() {
    mapboxMap.getMinZoom()
    verify { nativeMap.minZoom }
  }

  @Test
  fun getMaxZoom() {
    mapboxMap.getMaxZoom()
    verify { nativeMap.maxZoom }
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
    every { style.isFullyLoaded() } returns true
    assertTrue(mapboxMap.isFullyLoaded())
    verify { style.isFullyLoaded() }
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
  fun drag() {
    val fromPoint = mockk<ScreenCoordinate>()
    val toPoint = mockk<ScreenCoordinate>()
    mapboxMap.drag(fromPoint, toPoint, null)
    verify { nativeMap.drag(fromPoint, toPoint, null) }
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
    mapboxMap.cameraAnimationsPlugin { easeTo(options) }
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
    mapboxMap.gesturesPlugin { addOnMoveListener(moveListener) }
    verify(exactly = 0) {
      gestures.addOnMoveListener(moveListener)
    }
  }
}