package com.mapbox.maps

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class NativeMapTest {

  private val map = mockk<MapInterface>(relaxed = true)

  @Test
  fun subscribe() {
    val nativeMap = NativeMapImpl(map)
    val list = mutableListOf("foobar")
    nativeMap.subscribe(
      object : Observer() {
        override fun notify(event: Event) {}
      },
      list
    )
    verify { map.subscribe(any(), list) }
  }

  @Test
  fun unsubscribe() {
    val nativeMap = NativeMapImpl(map)
    val list = mutableListOf("foobar")
    nativeMap.unsubscribe(
      object : Observer() {
        override fun notify(event: Event) {}
      },
      list
    )
    verify { map.unsubscribe(any(), list) }
  }

  @Test
  fun unsubscribeSingle() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.unsubscribe(object : Observer() {
      override fun notify(event: Event) {}
    })
    verify { map.unsubscribe(any()) }
  }

  @Test
  fun getStyleURI() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleURI
    verify { map.styleURI }
  }

  @Test
  fun setStyleURI() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleURI = "foobar"
    verify { map.styleURI = "foobar" }
  }

  @Test
  fun getStyleJSON() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleJSON
    verify { map.styleJSON }
  }

  @Test
  fun setStyleJSON() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleJSON = "foobar"
    verify { map.styleJSON = "foobar" }
  }

  @Test
  fun getStyleDefaultCamera() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleDefaultCamera
    verify { map.styleDefaultCamera }
  }

  @Test
  fun getStyleTransition() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleTransition
    verify { map.styleTransition }
  }

  @Test
  fun setStyleTransition() {
    val transitionOptions = TransitionOptions.Builder().build()
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleTransition = transitionOptions
    verify { map.styleTransition = transitionOptions }
  }

  @Test
  fun addStyleLayer() {
    val value = mockk<Value>()
    val layerPosition = LayerPosition(null, null, null)
    val nativeMap = NativeMapImpl(map)
    nativeMap.addStyleLayer(value, layerPosition)
    verify { map.addStyleLayer(value, layerPosition) }
  }

  @Test
  fun addStyleCustomLayer() {
    val value = mockk<CustomLayerHost>()
    val layerPosition = LayerPosition(null, null, null)
    val nativeMap = NativeMapImpl(map)
    nativeMap.addStyleCustomLayer("foobar", value, layerPosition)
    verify { map.addStyleCustomLayer("foobar", value, layerPosition) }
  }

  @Test
  fun removeStyleLayer() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.removeStyleLayer("foobar")
    verify { map.removeStyleLayer("foobar") }
  }

  @Test
  fun styleLayerExists() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleLayerExists("foobar")
    verify { map.styleLayerExists("foobar") }
  }

  @Test
  fun getStyleLayers() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleLayers
    verify { map.styleLayers }
  }

  @Test
  fun getStyleLayerProperty() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleLayerProperty("foo", "bar")
    verify { map.getStyleLayerProperty("foo", "bar") }
  }

  @Test
  fun setStyleLayerProperty() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setStyleLayerProperty("foo", "bar", value)
    verify { map.setStyleLayerProperty("foo", "bar", value) }
  }

  @Test
  fun getStyleLayerProperties() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleLayerProperties("foo")
    verify { map.getStyleLayerProperties("foo") }
  }

  @Test
  fun setStyleLayerProperties() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setStyleLayerProperties("foo", value)
    verify { map.setStyleLayerProperties("foo", value) }
  }

  @Test
  fun addStyleSource() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.addStyleSource("foo", value)
    verify { map.addStyleSource("foo", value) }
  }

  @Test
  fun getStyleSourceProperty() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleSourceProperty("foo", "bar")
    verify { map.getStyleSourceProperty("foo", "bar") }
  }

  @Test
  fun setStyleSourceProperty() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setStyleSourceProperty("foo", "bar", value)
    verify { map.setStyleSourceProperty("foo", "bar", value) }
  }

  @Test
  fun getStyleSourceProperties() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleSourceProperties("foo")
    verify { map.getStyleSourceProperties("foo") }
  }

  @Test
  fun setStyleSourceProperties() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setStyleSourceProperties("foo", value)
    verify { map.setStyleSourceProperties("foo", value) }
  }

  @Test
  fun getStyleGeoJSONSourceClusterExpansionZoom() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleGeoJSONSourceClusterExpansionZoom("foo", 1)
    verify { map.getStyleGeoJSONSourceClusterExpansionZoom("foo", 1) }
  }

  @Test
  fun getStyleGeoJSONSourceClusterChildren() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleGeoJSONSourceClusterChildren("foo", 1)
    verify { map.getStyleGeoJSONSourceClusterChildren("foo", 1) }
  }

  @Test
  fun getStyleGeoJSONSourceClusterLeaves() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleGeoJSONSourceClusterLeaves("foo", 1, 2, 3)
    verify { map.getStyleGeoJSONSourceClusterLeaves("foo", 1, 2, 3) }
  }

  @Test
  fun updateStyleImageSourceImage() {
    val value = mockk<Image>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.updateStyleImageSourceImage("foo", value)
    verify { map.updateStyleImageSourceImage("foo", value) }
  }

  @Test
  fun removeStyleSource() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.removeStyleSource("foo")
    verify { map.removeStyleSource("foo") }
  }

  @Test
  fun styleSourceExists() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleSourceExists("foo")
    verify { map.styleSourceExists("foo") }
  }

  @Test
  fun getStyleSources() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.styleSources
    verify { map.styleSources }
  }

  @Test
  fun setStyleLight() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setStyleLight(value)
    verify { map.setStyleLight(value) }
  }

  @Test
  fun getStyleLightProperty() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleLightProperty("foo")
    verify { map.getStyleLightProperty("foo") }
  }

  @Test
  fun setStyleLightProperty() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setStyleLightProperty("foo", value)
    verify { map.setStyleLightProperty("foo", value) }
  }

  @Test
  fun setStyleTerrain() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setStyleTerrain(value)
    verify { map.setStyleTerrain(value) }
  }

  @Test
  fun getStyleTerrainProperty() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleTerrainProperty("foo")
    verify { map.getStyleTerrainProperty("foo") }
  }

  @Test
  fun setStyleTerrainProperty() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setStyleTerrainProperty("foo", value)
    verify { map.setStyleTerrainProperty("foo", value) }
  }

  @Test
  fun getStyleImage() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.getStyleImage("foo")
    verify { map.getStyleImage("foo") }
  }

  @Test
  fun addStyleImage() {
    val value = mockk<Image>()
    val imageContent = mockk<ImageContent>()
    val stretches = mutableListOf<ImageStretches>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.addStyleImage("foo", 1.0f, value, true, stretches, stretches, imageContent)
    verify { map.addStyleImage("foo", 1.0f, value, true, stretches, stretches, imageContent) }
  }

  @Test
  fun removeStyleImage() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.removeStyleImage("foo")
    verify { map.removeStyleImage("foo") }
  }

  @Test
  fun addStyleCustomGeometrySource() {
    val value = mockk<CustomGeometrySourceOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.addStyleCustomGeometrySource("foo", value)
    verify { map.addStyleCustomGeometrySource("foo", value) }
  }

  @Test
  fun setStyleCustomGeometrySourceTileData() {
    val id = mockk<CanonicalTileID>()
    val featureCollection = mockk<MutableList<Feature>>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setStyleCustomGeometrySourceTileData("foo", id, featureCollection)
    verify { map.setStyleCustomGeometrySourceTileData("foo", id, featureCollection) }
  }

  @Test
  fun invalidateStyleCustomGeometrySourceTile() {
    val id = mockk<CanonicalTileID>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.invalidateStyleCustomGeometrySourceTile("foo", id)
    verify { map.invalidateStyleCustomGeometrySourceTile("foo", id) }
  }

  @Test
  fun invalidateStyleCustomGeometrySourceRegion() {
    val bounds = mockk<CoordinateBounds>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.invalidateStyleCustomGeometrySourceRegion("foo", bounds)
    verify { map.invalidateStyleCustomGeometrySourceRegion("foo", bounds) }
  }

  @Test
  fun createRenderer() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.createRenderer()
    verify { map.createRenderer() }
  }

  @Test
  fun destroyRenderer() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.destroyRenderer()
    verify { map.destroyRenderer() }
  }

  @Test
  fun render() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.render()
    verify { map.render() }
  }

  @Test
  fun setSize() {
    val size = Size(1.0f, 2.0f)
    val nativeMap = NativeMapImpl(map)
    nativeMap.size = size
    verify { map.size = size }
  }

  @Test
  fun getSize() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.size
    verify { map.size }
  }

  @Test
  fun setDefaultFramebufferObject() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.setDefaultFramebufferObject(1)
    verify { map.setDefaultFramebufferObject(1) }
  }

  @Test
  fun renderStill() {
    val value = mockk<StillImageCallback>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.renderStill(value)
    verify { map.renderStill(value) }
  }

  @Test
  fun renderStillOptions() {
    val cameraOptions = mockk<CameraOptions>()
    val debugOptions = mockk<MutableList<MapDebugOptions>>()
    val value = mockk<StillImageCallback>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.renderStill(cameraOptions, debugOptions, value)
    verify { map.renderStill(cameraOptions, debugOptions, value) }
  }

  @Test
  fun triggerRepaint() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.triggerRepaint()
    verify { map.triggerRepaint() }
  }

  @Test
  fun jumpTo() {
    val value = mockk<CameraOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.jumpTo(value)
    verify { map.jumpTo(value) }
  }

  @Test
  fun getCameraOptions() {
    val value = mockk<EdgeInsets>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.getCameraOptions(value)
    verify { map.getCameraOptions(value) }
  }

  @Test
  fun easeTo() {
    val cameraOptions = mockk<CameraOptions>()
    val animationOptions = mockk<AnimationOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.easeTo(cameraOptions, animationOptions)
    verify { map.easeTo(cameraOptions, animationOptions) }
  }

  @Test
  fun flyTo() {
    val cameraOptions = mockk<CameraOptions>()
    val animationOptions = mockk<AnimationOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.flyTo(cameraOptions, animationOptions)
    verify { map.flyTo(cameraOptions, animationOptions) }
  }

  @Test
  fun moveBy() {
    val screenCoordinate = mockk<ScreenCoordinate>()
    val animationOptions = mockk<AnimationOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.moveBy(screenCoordinate, animationOptions)
    verify { map.moveBy(screenCoordinate, animationOptions) }
  }

  @Test
  fun scaleBy() {
    val screenCoordinate = mockk<ScreenCoordinate>()
    val animationOptions = mockk<AnimationOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.scaleBy(1.0, screenCoordinate, animationOptions)
    verify { map.scaleBy(1.0, screenCoordinate, animationOptions) }
  }

  @Test
  fun pitchBy() {
    val animationOptions = mockk<AnimationOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.pitchBy(1.0, animationOptions)
    verify { map.pitchBy(1.0, animationOptions) }
  }

  @Test
  fun rotateBy() {
    val screenCoordinateOne = mockk<ScreenCoordinate>()
    val screenCoordinateTwo = mockk<ScreenCoordinate>()
    val animationOptions = mockk<AnimationOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.rotateBy(screenCoordinateOne, screenCoordinateTwo, animationOptions)
    verify { map.rotateBy(screenCoordinateOne, screenCoordinateTwo, animationOptions) }
  }

  @Test
  fun cameraForCoordinateBounds() {
    val bounds = mockk<CoordinateBounds>()
    val edgeInsets = mockk<EdgeInsets>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.cameraForCoordinateBounds(bounds, edgeInsets, 1.0, 2.0)
    verify { map.cameraForCoordinateBounds(bounds, edgeInsets, 1.0, 2.0) }
  }

  @Test
  fun cameraForCoordinates() {
    val points = mockk<MutableList<Point>>()
    val edgeInsets = mockk<EdgeInsets>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.cameraForCoordinates(points, edgeInsets, 1.0, 2.0)
    verify { map.cameraForCoordinates(points, edgeInsets, 1.0, 2.0) }
  }

  @Test
  fun cameraForGeometry() {
    val geometry = mockk<Geometry>()
    val edgeInsets = mockk<EdgeInsets>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.cameraForGeometry(geometry, edgeInsets, 1.0, 2.0)
    verify { map.cameraForGeometry(geometry, edgeInsets, 1.0, 2.0) }
  }

  @Test
  fun coordinateBoundsForCamera() {
    val value = mockk<CameraOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.coordinateBoundsForCamera(value)
    verify { map.coordinateBoundsForCamera(value) }
  }

  @Test
  fun coordinateBoundsZoomForCamera() {
    val value = mockk<CameraOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.coordinateBoundsZoomForCamera(value)
    verify { map.coordinateBoundsZoomForCamera(value) }
  }

  @Test
  fun coordinateBoundsZoomForCameraUnwrapped() {
    val value = mockk<CameraOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.coordinateBoundsZoomForCameraUnwrapped(value)
    verify { map.coordinateBoundsZoomForCameraUnwrapped(value) }
  }

  @Test
  fun cancelTransitions() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.cancelTransitions()
    verify { map.cancelTransitions() }
  }

  @Test
  fun setGestureInProgress() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.isGestureInProgress = true
    verify { map.isGestureInProgress = true }
  }

  @Test
  fun isGestureInProgress() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.isGestureInProgress
    verify { map.isGestureInProgress }
  }

  @Test
  fun setUserAnimationInProgress() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.isUserAnimationInProgress = true
    verify { map.isUserAnimationInProgress = true }
  }

  @Test
  fun isUserAnimationInProgress() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.isUserAnimationInProgress
    verify { map.isUserAnimationInProgress }
  }

  @Test
  fun isRotating() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.isRotating
    verify { map.isRotating }
  }

  @Test
  fun isScaling() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.isScaling
    verify { map.isScaling }
  }

  @Test
  fun isPanning() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.isPanning
    verify { map.isPanning }
  }

  @Test
  fun setBounds() {
    val value = mockk<BoundOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.bounds = value
    verify { map.bounds = value }
  }

  @Test
  fun getBounds() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.bounds
    verify { map.bounds }
  }

  @Test
  fun setPrefetchZoomDelta() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.prefetchZoomDelta = 2
    verify { map.prefetchZoomDelta = 2 }
  }

  @Test
  fun getPrefetchZoomDelta() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.prefetchZoomDelta
    verify { map.prefetchZoomDelta }
  }

  @Test
  fun setNorthOrientation() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.setNorthOrientation(NorthOrientation.DOWNWARDS)
    verify { map.setNorthOrientation(NorthOrientation.DOWNWARDS) }
  }

  @Test
  fun setConstrainMode() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.setConstrainMode(ConstrainMode.HEIGHT_ONLY)
    verify { map.setConstrainMode(ConstrainMode.HEIGHT_ONLY) }
  }

  @Test
  fun setViewportMode() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.setViewportMode(ViewportMode.FLIPPED_Y)
    verify { map.setViewportMode(ViewportMode.FLIPPED_Y) }
  }

  @Test
  fun getMapOptions() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.mapOptions
    verify { map.mapOptions }
  }

  @Test
  fun pixelForCoordinate() {
    val value = mockk<Point>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.pixelForCoordinate(value)
    verify { map.pixelForCoordinate(value) }
  }

  @Test
  fun coordinateForPixel() {
    val value = mockk<ScreenCoordinate>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.coordinateForPixel(value)
    verify { map.coordinateForPixel(value) }
  }

  @Test
  fun pixelsForCoordinates() {
    val value = mockk<MutableList<Point>>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.pixelsForCoordinates(value)
    verify { map.pixelsForCoordinates(value) }
  }

  @Test
  fun coordinatesForPixels() {
    val value = mockk<MutableList<ScreenCoordinate>>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.coordinatesForPixels(value)
    verify { map.coordinatesForPixels(value) }
  }

  @Test
  fun getDebug() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.debug
    verify { map.debug }
  }

  @Test
  fun setDebug() {
    val value = mockk<List<MapDebugOptions>>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setDebug(value, true)
    verify { map.setDebug(value, true) }
  }

  @Test
  fun isMapFullyLoaded() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.isMapFullyLoaded
    verify { map.isMapFullyLoaded }
  }

  @Test
  fun isStyleFullyLoaded() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.isStyleFullyLoaded
    verify { map.isStyleFullyLoaded }
  }

  @Test
  fun dumpDebugLogs() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.dumpDebugLogs()
    verify { map.dumpDebugLogs() }
  }

  @Test
  fun queryRenderedFeaturesGeometry() {
    val callback = mockk<QueryFeaturesCallback>()
    val value = mockk<MutableList<ScreenCoordinate>>()
    val queryOptions = mockk<RenderedQueryOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.queryRenderedFeatures(value, queryOptions, callback)
    verify { map.queryRenderedFeatures(value, queryOptions, callback) }
  }

  @Test
  fun queryRenderedFeaturesBox() {
    val callback = mockk<QueryFeaturesCallback>()
    val value = mockk<ScreenBox>()
    val queryOptions = mockk<RenderedQueryOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.queryRenderedFeatures(value, queryOptions, callback)
    verify { map.queryRenderedFeatures(value, queryOptions, callback) }
  }

  @Test
  fun queryRenderedFeaturesCoordinate() {
    val callback = mockk<QueryFeaturesCallback>()
    val value = mockk<ScreenCoordinate>()
    val queryOptions = mockk<RenderedQueryOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.queryRenderedFeatures(value, queryOptions, callback)
    verify { map.queryRenderedFeatures(value, queryOptions, callback) }
  }

  @Test
  fun querySourceFeatures() {
    val callback = mockk<QueryFeaturesCallback>()
    val queryOptions = mockk<SourceQueryOptions>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.querySourceFeatures("foo", queryOptions, callback)
    verify { map.querySourceFeatures("foo", queryOptions, callback) }
  }

  @Test
  fun queryFeatureExtensions() {
    val callback = mockk<QueryFeatureExtensionCallback>()
    val value = mockk<Feature>()
    val hashMap = mockk<HashMap<String, Value>>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.queryFeatureExtensions("id", value, "extension", "extensionField", hashMap, callback)
    verify { map.queryFeatureExtensions("id", value, "extension", "extensionField", hashMap, callback) }
  }

  @Test
  fun setFeatureState() {
    val value = mockk<Value>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.setFeatureState("foo", "bar", "id", value)
    verify { map.setFeatureState("foo", "bar", "id", value) }
  }

  @Test
  fun getFeatureState() {
    val callback = mockk<QueryFeatureStateCallback>()
    val nativeMap = NativeMapImpl(map)
    nativeMap.getFeatureState("foo", "bar", "id", callback)
    verify { map.getFeatureState("foo", "bar", "id", callback) }
  }

  @Test
  fun removeFeatureState() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.removeFeatureState("foo", "bar", "id", "key")
    verify { map.removeFeatureState("foo", "bar", "id", "key") }
  }

  @Test
  fun reduceMemoryUse() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.reduceMemoryUse()
    verify { map.reduceMemoryUse() }
  }

  @Test
  fun getResourceOptions() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.resourceOptions
    verify { map.resourceOptions }
  }

  @Test
  fun getMinZoom() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.minZoom
    verify { map.minZoom }
  }

  @Test
  fun getMaxZoom() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.maxZoom
    verify { map.maxZoom }
  }

  @Test
  fun getScale() {
    val nativeMap = NativeMapImpl(map)
    nativeMap.scale
    verify { map.scale }
  }
}