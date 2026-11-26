package com.mapbox.maps

import com.mapbox.annotation.MapboxExperimental
import com.mapbox.bindgen.Value
import com.mapbox.common.MainMapboxThreadProvider
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.shadows.ShadowObservable
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
  shadows = [
    ShadowMap::class,
    ShadowMapSnapshotter::class,
    ShadowObservable::class,
    ShadowCameraManager::class,
    ShadowStyleManager::class,
  ]
)
class NativeMapTest {

  private val map = mockk<Map>(relaxed = true)
  private val mapboxThread = MainMapboxThreadProvider().getOrCreate()

  @Test
  fun subscribeCameraChangeEvent() {
    subscribeEvent<CameraChangedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun subscribeCameraChangeCoalescedEvent() {
    subscribeEvent<CameraChangedCoalescedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeMapIdleEvent() {
    subscribeEvent<MapIdleCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }
  @Test
  fun subscribeStyleDataLoadedEvent() {
    subscribeEvent<StyleDataLoadedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeStyleLoadedEvent() {
    subscribeEvent<StyleLoadedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeImageMissingEvent() {
    subscribeEvent<StyleImageMissingCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeImageRemovedEvent() {
    subscribeEvent<StyleImageRemoveUnusedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeRenderFrameFinishedEvent() {
    subscribeEvent<RenderFrameFinishedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeRenderFrameStartedEvent() {
    subscribeEvent<RenderFrameStartedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeSourceAddedEvent() {
    subscribeEvent<SourceAddedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeSourceDataLoadedEvent() {
    subscribeEvent<SourceDataLoadedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeSourceRemovedEvent() {
    subscribeEvent<SourceRemovedCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  @Test
  fun subscribeResourceRequestEvent() {
    subscribeEvent<ResourceRequestCallback>(NativeMapImpl::subscribe, Map::subscribe)
  }

  private inline fun <reified C> subscribeEvent(
    nativeSubscribe: NativeMapImpl.(C) -> Unit,
    crossinline mapSubscribe: Map.(C) -> Unit
  ) {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val callback: C = mockk()
    nativeMap.nativeSubscribe(callback)
    verify { map.mapSubscribe(callback) }
  }

  @Test
  fun subscribeUntypedEvent() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val callback = mockk<GenericEventCallback>()
    nativeMap.subscribe("event", callback)
    verify { map.subscribe("event", callback) }
  }

  @Test
  fun getStyleURI() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleURI()
    verify { map.styleURI }
  }

  @Test
  fun setStyleURI() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleURI("foobar")
    verify { map.styleURI = "foobar" }
  }

  @Test
  fun getStyleJSON() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleJSON()
    verify { map.styleJSON }
  }

  @Test
  fun setStyleJSON() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleJSON("foobar")
    verify { map.styleJSON = "foobar" }
  }

  @Test
  fun getStyleDefaultCamera() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleDefaultCamera()
    verify { map.styleDefaultCamera }
  }

  @Test
  fun getStyleTransition() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleTransition()
    verify { map.styleTransition }
  }

  @Test
  fun setStyleTransition() {
    val transitionOptions = TransitionOptions.Builder().build()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleTransition(transitionOptions)
    verify { map.styleTransition = transitionOptions }
  }

  @Test
  fun addStyleLayer() {
    val value = mockk<Value>()
    val layerPosition = LayerPosition(null, null, null)
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.addStyleLayer(value, layerPosition)
    verify { map.addStyleLayer(value, layerPosition) }
  }

  @Test
  fun addStyleCustomLayer() {
    val value = mockk<CustomLayerHost>()
    val layerPosition = LayerPosition(null, null, null)
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.addStyleCustomLayer("foobar", value, layerPosition)
    verify { map.addStyleCustomLayer("foobar", value, layerPosition) }
  }

  @Test
  fun addPersistentStyleLayer() {
    val value = mockk<Value>()
    val layerPosition = LayerPosition(null, null, null)
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.addPersistentStyleLayer(value, layerPosition)
    verify { map.addPersistentStyleLayer(value, layerPosition) }
  }

  @Test
  fun addPersistentStyleCustomLayer() {
    val value = mockk<CustomLayerHost>()
    val layerPosition = LayerPosition(null, null, null)
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.addPersistentStyleCustomLayer("foobar", value, layerPosition)
    verify { map.addPersistentStyleCustomLayer("foobar", value, layerPosition) }
  }

  @Test
  fun isStyleLayerPersistent() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.isStyleLayerPersistent("foobar")
    verify { map.isStyleLayerPersistent("foobar") }
  }

  @Test
  fun removeStyleLayer() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.removeStyleLayer("foobar")
    verify { map.removeStyleLayer("foobar") }
  }

  @Test
  fun styleLayerExists() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.styleLayerExists("foobar")
    verify { map.styleLayerExists("foobar") }
  }

  @Test
  fun getStyleLayers() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleLayers()
    verify { map.styleLayers }
  }

  @Test
  fun getStyleLayerProperty() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleLayerProperty("foo", "bar")
    verify { map.getStyleLayerProperty("foo", "bar") }
  }

  @Test
  fun setStyleLayerProperty() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleLayerProperty("foo", "bar", value)
    verify { map.setStyleLayerProperty("foo", "bar", value) }
  }

  @Test
  fun getStyleLayerProperties() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleLayerProperties("foo")
    verify { map.getStyleLayerProperties("foo") }
  }

  @Test
  fun setStyleLayerProperties() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleLayerProperties("foo", value)
    verify { map.setStyleLayerProperties("foo", value) }
  }

  @Test
  fun addStyleSource() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.addStyleSource("foo", value)
    verify { map.addStyleSource("foo", value) }
  }

  @Test
  fun getStyleSourceProperty() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleSourceProperty("foo", "bar")
    verify { map.getStyleSourceProperty("foo", "bar") }
  }

  @Test
  fun setStyleSourceProperty() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleSourceProperty("foo", "bar", value)
    verify { map.setStyleSourceProperty("foo", "bar", value) }
  }

  @Test
  fun setStyleSourcePropertyWithImportId() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleSourceProperty("importId", "foo", "bar", value)
    verify { map.setStyleSourceProperty("importId", "foo", "bar", value) }
  }

  @Test
  fun getStyleSourceProperties() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleSourceProperties("foo")
    verify { map.getStyleSourceProperties("foo") }
  }

  @Test
  fun setStyleSourceProperties() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleSourceProperties("foo", value)
    verify { map.setStyleSourceProperties("foo", value) }
  }

  @Test
  fun updateStyleImageSourceImage() {
    val value = mockk<Image>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.updateStyleImageSourceImage("foo", value)
    verify { map.updateStyleImageSourceImage("foo", value) }
  }

  @Test
  fun removeStyleSource() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.removeStyleSource("foo")
    verify { map.removeStyleSource("foo") }
  }

  @Test
  fun styleSourceExists() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.styleSourceExists("foo")
    verify { map.styleSourceExists("foo") }
  }

  @Test
  fun getStyleSources() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleSources()
    verify { map.styleSources }
  }

  @Test
  fun setStyleTerrain() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleTerrain(value)
    verify { map.setStyleTerrain(value) }
  }

  @Test
  fun getStyleTerrainProperty() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleTerrainProperty("foo")
    verify { map.getStyleTerrainProperty("foo") }
  }

  @Test
  fun setStyleTerrainProperty() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleTerrainProperty("foo", value)
    verify { map.setStyleTerrainProperty("foo", value) }
  }

  @Test
  fun getStyleImage() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleImage("foo")
    verify { map.getStyleImage("foo") }
  }

  @Test
  fun addStyleImage() {
    val value = mockk<Image>()
    val imageContent = mockk<ImageContent>()
    val stretches = mutableListOf<ImageStretches>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.addStyleImage("foo", 1.0f, value, true, stretches, stretches, imageContent)
    verify { map.addStyleImage("foo", 1.0f, value, true, stretches, stretches, imageContent) }
  }

  @Test
  fun removeStyleImage() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.removeStyleImage("foo")
    verify { map.removeStyleImage("foo") }
  }

  @Test
  fun hasStyleImage() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.hasStyleImage("foo")
    verify { map.hasStyleImage("foo") }
  }

  @Test
  fun addStyleCustomGeometrySource() {
    val value = mockk<CustomGeometrySourceOptions>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.addStyleCustomGeometrySource("foo", value)
    verify { map.addStyleCustomGeometrySource("foo", value) }
  }

  @Test
  fun setStyleCustomGeometrySourceTileData() {
    val id = mockk<CanonicalTileID>()
    val featureCollection = mockk<MutableList<Feature>>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleCustomGeometrySourceTileData("foo", id, featureCollection)
    verify { map.setStyleCustomGeometrySourceTileData("foo", id, featureCollection) }
  }

  @Test
  fun invalidateStyleCustomGeometrySourceTile() {
    val id = mockk<CanonicalTileID>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.invalidateStyleCustomGeometrySourceTile("foo", id)
    verify { map.invalidateStyleCustomGeometrySourceTile("foo", id) }
  }

  @Test
  fun invalidateStyleCustomGeometrySourceRegion() {
    val bounds = mockk<CoordinateBounds>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.invalidateStyleCustomGeometrySourceRegion("foo", bounds)
    verify { map.invalidateStyleCustomGeometrySourceRegion("foo", bounds) }
  }

  @Test
  fun createRenderer() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.createRenderer()
    verify { map.createRenderer() }
  }

  @Test
  fun destroyRenderer() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.destroyRenderer()
    verify { map.destroyRenderer() }
  }

  @Test
  fun render() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.render()
    verify { map.render() }
  }

  @Test
  fun setSize() {
    val size = Size(1.0f, 2.0f)
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setSize(size)
    verify { map.size = size }
  }
  @Test
  fun initialSizeSetCallbacksTestBeforeSetSize() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    var callbackTriggered = false
    var anotherCallbackTriggered = false
    val callback: () -> Unit = { callbackTriggered = true }
    val anotherCallback: () -> Unit = { anotherCallbackTriggered = true }
    nativeMap.whenMapSizeReady(callback)
    nativeMap.whenMapSizeReady(anotherCallback)
    assert(!callbackTriggered)
    assert(!anotherCallbackTriggered)
    nativeMap.setSize(Size(1.0f, 2.0f))
    assert(callbackTriggered)
    assert(anotherCallbackTriggered)
  }

  @Test
  fun whenMapSizeReadyCancelPreventsCallbackFromFiring() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    var callbackTriggered = false
    var otherCallbackTriggered = false
    val callback: () -> Unit = { callbackTriggered = true }
    val otherCallback: () -> Unit = { otherCallbackTriggered = true }
    val cancelable = nativeMap.whenMapSizeReady(callback)
    nativeMap.whenMapSizeReady(otherCallback)
    cancelable.cancel()
    nativeMap.setSize(Size(1.0f, 2.0f))
    assert(!callbackTriggered)
    assert(otherCallbackTriggered)
  }

  @Test
  fun initialSizeSetCallbackTestAfterSetSize() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    var callbackTriggered = false
    val callback: () -> Unit = { callbackTriggered = true }
    nativeMap.setSize(Size(1.0f, 2.0f))
    nativeMap.whenMapSizeReady(callback)
    assert(callbackTriggered)
  }

  @Test
  fun getSize() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getSize()
    verify { map.size }
  }

  @Test
  fun triggerRepaint() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.triggerRepaint()
    verify { map.triggerRepaint() }
  }

  @Test
  fun setCameraOptions() {
    val value = mockk<CameraOptions>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setCamera(value)
    verify { map.setCamera(value) }
  }

  @Test
  fun getCameraState() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getCameraState()
    verify { map.cameraState }
  }

  @Test
  fun cameraForCoordinateBounds() {
    val bounds = mockk<CoordinateBounds>()
    val edgeInsets = mockk<EdgeInsets>()
    val offset = mockk<ScreenCoordinate>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.cameraForCoordinateBounds(
      coordinateBounds = bounds,
      padding = edgeInsets,
      bearing = 1.0,
      pitch = 2.0,
      maxZoom = 2.0,
      offset = offset,
    )
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = edgeInsets,
        bearing = 1.0,
        pitch = 2.0,
        maxZoom = 2.0,
        offset = offset
      )
    }
  }

  @Test
  fun cameraForCoordinates() {
    val points = mockk<MutableList<Point>>()
    val edgeInsets = mockk<EdgeInsets>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.cameraForCoordinates(points, edgeInsets, 1.0, 2.0)
    verify { map.cameraForCoordinates(points, edgeInsets, 1.0, 2.0) }
  }

  @Test
  fun cameraForCoordinatesWithOffset() {
    val points = mockk<MutableList<Point>>()
    val camera = mockk<CameraOptions>()
    val edgeInsets = mockk<EdgeInsets>()
    val offset = mockk<ScreenCoordinate>()
    val nativeMap = NativeMapImpl(map, mapboxThread)

    nativeMap.cameraForCoordinates(points, camera, edgeInsets, 1.0, offset)
    verify { map.cameraForCoordinates(points, camera, edgeInsets, 1.0, offset) }
  }

  @Test
  fun cameraForGeometry() {
    val geometry = mockk<Geometry>()
    val edgeInsets = mockk<EdgeInsets>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.cameraForGeometry(geometry, edgeInsets, 1.0, 2.0)
    verify { map.cameraForGeometry(geometry, edgeInsets, 1.0, 2.0) }
  }

  @Test
  fun coordinateBoundsForCamera() {
    val value = mockk<CameraOptions>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.coordinateBoundsForCamera(value)
    verify { map.coordinateBoundsForCamera(value) }
  }

  @Test
  fun coordinateBoundsZoomForCamera() {
    val value = mockk<CameraOptions>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.coordinateBoundsZoomForCamera(value)
    verify { map.coordinateBoundsZoomForCamera(value) }
  }

  @Test
  fun coordinateBoundsForCameraUnwrapped() {
    val value = mockk<CameraOptions>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.coordinateBoundsForCameraUnwrapped(value)
    verify { map.coordinateBoundsForCameraUnwrapped(value) }
  }

  @Test
  fun coordinateBoundsZoomForCameraUnwrapped() {
    val value = mockk<CameraOptions>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.coordinateBoundsZoomForCameraUnwrapped(value)
    verify { map.coordinateBoundsZoomForCameraUnwrapped(value) }
  }

  @Test
  fun setGestureInProgress() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setGestureInProgress(true)
    verify { map.isGestureInProgress = true }
  }

  @Test
  fun isGestureInProgress() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.isGestureInProgress()
    verify { map.isGestureInProgress }
  }

  @Test
  fun setUserAnimationInProgress() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setUserAnimationInProgress(true)
    verify { map.isUserAnimationInProgress = true }
  }

  @Test
  fun isUserAnimationInProgress() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.isUserAnimationInProgress()
    verify { map.isUserAnimationInProgress }
  }

  @Test
  fun setBounds() {
    val value = mockk<CameraBoundsOptions>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setBounds(value)
    verify { map.setBounds(value) }
  }

  @Test
  fun getBounds() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getBounds()
    verify { map.bounds }
  }

  @Test
  fun setPrefetchZoomDelta() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setPrefetchZoomDelta(2)
    verify { map.prefetchZoomDelta = 2 }
  }

  @Test
  fun getPrefetchZoomDelta() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getPrefetchZoomDelta()
    verify { map.prefetchZoomDelta }
  }

  @Test
  fun setNorthOrientation() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setNorthOrientation(NorthOrientation.DOWNWARDS)
    verify { map.setNorthOrientation(NorthOrientation.DOWNWARDS) }
  }

  @Test
  fun setConstrainMode() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setConstrainMode(ConstrainMode.HEIGHT_ONLY)
    verify { map.setConstrainMode(ConstrainMode.HEIGHT_ONLY) }
  }

  @Test
  fun setViewportMode() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setViewportMode(ViewportMode.FLIPPED_Y)
    verify { map.setViewportMode(ViewportMode.FLIPPED_Y) }
  }

  @Test
  fun getMapOptions() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getMapOptions()
    verify { map.mapOptions }
  }

  @Test
  fun pixelForCoordinate() {
    val value = mockk<Point>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.pixelForCoordinate(value)
    verify { map.pixelForCoordinate(value) }
  }

  @Test
  fun coordinateForPixel() {
    val value = mockk<ScreenCoordinate>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.coordinateForPixel(value)
    verify { map.coordinateForPixel(value) }
  }

  @Test
  fun pixelsForCoordinates() {
    val value = mockk<MutableList<Point>>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.pixelsForCoordinates(value)
    verify { map.pixelsForCoordinates(value) }
  }

  @Test
  fun coordinatesForPixels() {
    val value = mockk<MutableList<ScreenCoordinate>>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.coordinatesForPixels(value)
    verify { map.coordinatesForPixels(value) }
  }

  @Test
  fun getDebug() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getDebug()
    verify { map.debug }
  }

  @Test
  fun setDebug() {
    val value = mockk<List<MapDebugOptions>>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setDebug(value, true)
    verify { map.setDebug(value, true) }
  }

  @Test
  fun isStyleFullyLoaded() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.isStyleLoaded()
    verify { map.isStyleLoaded }
  }

  @Test
  fun queryRenderedFeatures() {
    val callback = mockk<QueryRenderedFeaturesCallback>()
    val geometry = mockk<RenderedQueryGeometry>()
    val queryOptions = mockk<RenderedQueryOptions>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.queryRenderedFeatures(geometry, queryOptions, callback)
    verify { map.queryRenderedFeatures(geometry, queryOptions, callback) }
  }

  @Test
  fun querySourceFeatures() {
    val callback = mockk<QuerySourceFeaturesCallback>()
    val queryOptions = mockk<SourceQueryOptions>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.querySourceFeatures("foo", queryOptions, callback)
    verify { map.querySourceFeatures("foo", queryOptions, callback) }
  }

  @Test
  fun queryFeatureExtensions() {
    val callback = mockk<QueryFeatureExtensionCallback>()
    val value = mockk<Feature>()
    val hashMap = mockk<HashMap<String, Value>>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.queryFeatureExtensions("id", value, "extension", "extensionField", hashMap, callback)
    verify {
      map.queryFeatureExtensions(
        "id",
        value,
        "extension",
        "extensionField",
        hashMap,
        callback
      )
    }
  }

  @Test
  fun setFeatureState() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val callback = mockk<FeatureStateOperationCallback>()
    nativeMap.setFeatureState("foo", "bar", "id", value, callback)
    verify { map.setFeatureState("foo", "bar", "id", any(), callback) }
  }

  @Test
  fun setFeatureStateFestureset() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val featuresetDescriptor = mockk<FeaturesetDescriptor>()
    val featuresetFeatureId = mockk<FeaturesetFeatureId>()
    val callback = mockk<FeatureStateOperationCallback>()
    nativeMap.setFeatureState(featuresetDescriptor, featuresetFeatureId, value, callback)
    verify { map.setFeatureState(featuresetDescriptor, featuresetFeatureId, value, callback) }
  }

  @Test
  fun getFeatureState() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val callback = mockk<QueryFeatureStateCallback>()
    nativeMap.getFeatureState("foo", "bar", "id", callback)
    verify { map.getFeatureState("foo", "bar", "id", callback) }
  }

  @Test
  fun getFeatureStateFestureset() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val featuresetDescriptor = mockk<FeaturesetDescriptor>()
    val featuresetFeatureId = mockk<FeaturesetFeatureId>()
    val callback = mockk<QueryFeatureStateCallback>()
    nativeMap.getFeatureState(featuresetDescriptor, featuresetFeatureId, callback)
    verify { map.getFeatureState(featuresetDescriptor, featuresetFeatureId, callback) }
  }

  @Test
  fun removeFeatureState() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val callback = mockk<FeatureStateOperationCallback>()
    nativeMap.removeFeatureState("foo", "bar", "id", "key", callback)
    verify { map.removeFeatureState("foo", "bar", "id", "key", callback) }
  }

  @Test
  fun removeFeatureStateFeatureset() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val callback = mockk<FeatureStateOperationCallback>()
    val featuresetDescriptor = mockk<FeaturesetDescriptor>()
    val featuresetFeatureId = mockk<FeaturesetFeatureId>()
    nativeMap.removeFeatureState(featuresetDescriptor, featuresetFeatureId, "key", callback)
    verify { map.removeFeatureState(featuresetDescriptor, featuresetFeatureId, "key", callback) }
  }

  @Test
  fun resetFeatureStates() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val callback = mockk<FeatureStateOperationCallback>()
    nativeMap.resetFeatureStates("foo", "bar", callback)
    verify { map.resetFeatureStates("foo", "bar", callback) }
  }

  @Test
  fun resetFeatureStatesFeatureset() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val callback = mockk<FeatureStateOperationCallback>()
    val featuresetDescriptor = mockk<FeaturesetDescriptor>()
    nativeMap.resetFeatureStates(featuresetDescriptor, callback)
    verify { map.resetFeatureStates(featuresetDescriptor, callback) }
  }

  @Test
  @OptIn(com.mapbox.maps.MapboxExperimental::class, MapboxExperimental::class)
  fun setFeatureStateExpression() {
    val expression = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)

    val featuresetDescriptor = mockk<FeaturesetDescriptor>()
    val state = mockk<Value>()
    val callback = mockk<FeatureStateOperationCallback>()
    nativeMap.setFeatureStateExpression(3, featuresetDescriptor, state, expression, callback)
    verify { map.setFeatureStateExpression(3, featuresetDescriptor, state, expression, callback) }
  }

  @Test
  @OptIn(com.mapbox.maps.MapboxExperimental::class, MapboxExperimental::class)
  fun removeFeatureStateExpression() {
    val nativeMap = NativeMapImpl(map, mapboxThread)

    val callback = mockk<FeatureStateOperationCallback>()
    nativeMap.removeFeatureStateExpression(3, callback)
    verify { map.removeFeatureStateExpression(3, callback) }
  }

  @Test
  @OptIn(com.mapbox.maps.MapboxExperimental::class, MapboxExperimental::class)
  fun resetFeatureStateExpressions() {
    val nativeMap = NativeMapImpl(map, mapboxThread)

    val callback = mockk<FeatureStateOperationCallback>()
    nativeMap.resetFeatureStateExpressions(callback)
    verify { map.resetFeatureStateExpressions(callback) }
  }

  @Test
  fun reduceMemoryUse() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.reduceMemoryUse()
    verify { map.reduceMemoryUse() }
  }

  @Test
  fun moveStyleLayer() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.moveStyleLayer("layerId", LayerPosition("above", "below", 0))
    verify { map.moveStyleLayer("layerId", LayerPosition("above", "below", 0)) }
  }

  @Test
  fun setStyleProjection() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleProjection(value)
    verify { map.setStyleProjection(value) }
  }

  @Test
  fun getStyleProjectionProperty() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleProjectionProperty("foo")
    verify { map.getStyleProjectionProperty("foo") }
  }

  @Test
  fun setStyleProjectionProperty() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleProjectionProperty("foo", value)
    verify { map.setStyleProjectionProperty("foo", value) }
  }

  @Test
  fun addStyleModel() {
    val modelId = "modelId"
    val modelUri = "modelUri"
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.addStyleModel(modelId, modelUri)
    verify { map.addStyleModel(modelId, modelUri) }
  }

  @Test
  fun removeStyleModel() {
    val modelId = "modelId"
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.removeStyleModel(modelId)
    verify { map.removeStyleModel(modelId) }
  }

  @Test
  fun hasStyleModel() {
    val modelId = "modelId"
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.hasStyleModel(modelId)
    verify { map.hasStyleModel(modelId) }
  }

  @Test
  fun setStyleAtmosphere() {
    val atmosphereValue = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleAtmosphere(atmosphereValue)
    verify { map.setStyleAtmosphere(atmosphereValue) }
  }

  @Test
  fun setStyleAtmosphereProperty() {
    val property = "property"
    val atmosphereValueProperty = mockk<Value>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setStyleAtmosphereProperty(property, atmosphereValueProperty)
    verify { map.setStyleAtmosphereProperty(property, atmosphereValueProperty) }
  }

  @Test
  fun getStyleAtmosphereProperty() {
    val property = "property"
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getStyleAtmosphereProperty(property)
    verify { map.getStyleAtmosphereProperty(property) }
  }

  @Test
  fun setRenderWorldCopiesProperty() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setRenderWorldCopies(true)
    verify { map.renderWorldCopies = true }
  }

  @Test
  fun getRenderWorldCopiesProperty() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getRenderWorldCopies()
    verify { map.renderWorldCopies }
  }

  @Test
  fun cameraForCoordinateBoundsWithoutPadding() {
    val bounds = mockk<CoordinateBounds>()
    val offset = mockk<ScreenCoordinate>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.cameraForCoordinateBounds(
      coordinateBounds = bounds,
      padding = null,
      bearing = 1.0,
      pitch = 2.0,
      maxZoom = 2.0,
      offset = offset,
    )
    verify {
      nativeMap.cameraForCoordinateBounds(
        coordinateBounds = bounds,
        padding = null,
        bearing = 1.0,
        pitch = 2.0,
        maxZoom = 2.0,
        offset = offset
      )
    }
  }

  @Test
  fun cameraForCoordinatesWithoutPadding() {
    val points = mockk<MutableList<Point>>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.cameraForCoordinates(points, null, 1.0, 2.0)
    verify { map.cameraForCoordinates(points, null, 1.0, 2.0) }
  }

  @Test
  fun cameraForGeometryWithoutPadding() {
    val geometry = mockk<Geometry>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.cameraForGeometry(geometry, null, 1.0, 2.0)
    verify { map.cameraForGeometry(geometry, null, 1.0, 2.0) }
  }

  @Test
  fun coordinateInfoForPixel() {
    val pixel = mockk<ScreenCoordinate>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.coordinateInfoForPixel(pixel)
    verify { map.coordinateInfoForPixel(pixel) }
  }

  @Test
  fun coordinateInfoForPixels() {
    val pixels = mockk<List<ScreenCoordinate>>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.coordinatesInfoForPixels(pixels)
    verify { map.coordinatesInfoForPixels(pixels) }
  }

  @Test
  fun getAttributions() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getAttributions()
    verify { map.attributions }
  }

  @Test
  fun cameraForDrag() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val start = ScreenCoordinate(0.0, 0.0)
    val end = ScreenCoordinate(1.0, 1.0)
    nativeMap.cameraForDrag(start, end)
    verify { map.cameraForDrag(start, end) }
  }

  @Test
  fun setCenterAltitudeMode() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setCenterAltitudeMode(MapCenterAltitudeMode.TERRAIN)
    verify { map.centerAltitudeMode = MapCenterAltitudeMode.TERRAIN }
  }

  @Test
  fun startPerformanceStatisticsCollection() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    val options = mockk<PerformanceStatisticsOptions>()
    val callback = mockk<PerformanceStatisticsCallback>()
    nativeMap.startPerformanceStatisticsCollection(options, callback)
    verify { map.startPerformanceStatisticsCollection(options, callback) }
  }

  @Test
  fun stopPerformanceStatisticsCollection() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.stopPerformanceStatisticsCollection()
    verify { map.stopPerformanceStatisticsCollection() }
  }

  @Test
  fun getScreenCullingShape() {
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.getScreenCullingShape()
    verify { map.screenCullingShape }
  }

  @Test
  fun setCustomScreenShape() {
    val shape = mockk<List<Vec2>>()
    val nativeMap = NativeMapImpl(map, mapboxThread)
    nativeMap.setScreenCullingShape(shape)
    verify { map.screenCullingShape = shape }
  }
}