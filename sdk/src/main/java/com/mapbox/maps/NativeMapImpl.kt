package com.mapbox.maps

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point

internal class NativeMapImpl(val map: Map) {

  fun setSize(size: Size) {
    map.size = size
  }

  fun getSize(): Size {
    return map.size
  }

  fun createRenderer() {
    map.createRenderer()
  }

  fun destroyRenderer() {
    map.destroyRenderer()
  }

  fun render() {
    map.render()
  }

  fun setCamera(cameraOptions: CameraOptions) {
    map.setCamera(cameraOptions)
  }

  fun getCameraState(): CameraState {
    return map.cameraState
  }

  fun coordinatesForPixels(pixels: MutableList<ScreenCoordinate>): MutableList<Point> {
    return map.coordinatesForPixels(pixels)
  }

  fun cameraForCoordinateBounds(
    coordinateBounds: CoordinateBounds,
    padding: EdgeInsets?,
    bearing: Double?,
    pitch: Double?,
    maxZoom: Double?,
    offset: ScreenCoordinate?
  ): CameraOptions {
    return map.cameraForCoordinateBounds(coordinateBounds, padding, bearing, pitch, maxZoom, offset)
  }

  fun coordinateInfoForPixel(pixel: ScreenCoordinate): CoordinateInfo {
    return map.coordinateInfoForPixel(pixel)
  }

  fun coordinatesInfoForPixels(pixels: List<ScreenCoordinate>): List<CoordinateInfo> {
    return map.coordinatesInfoForPixels(pixels)
  }

  fun setUserAnimationInProgress(inProgress: Boolean) {
    map.isUserAnimationInProgress = inProgress
  }

  fun invalidateStyleCustomGeometrySourceRegion(
    sourceId: String,
    coordinateBounds: CoordinateBounds
  ): Expected<String, None> {
    return map.invalidateStyleCustomGeometrySourceRegion(sourceId, coordinateBounds)
  }

  fun setStyleTerrain(properties: Value): Expected<String, None> {
    return map.setStyleTerrain(properties)
  }

  fun getStyleTerrainProperty(property: String): StylePropertyValue {
    return map.getStyleTerrainProperty(property)
  }

  fun setStyleTerrainProperty(property: String, value: Value): Expected<String, None> {
    return map.setStyleTerrainProperty(property, value)
  }

  fun setStyleProjection(properties: Value): Expected<String, None> {
    return map.setStyleProjection(properties)
  }

  fun getStyleProjectionProperty(property: String): StylePropertyValue {
    return map.getStyleProjectionProperty(property)
  }

  fun setStyleProjectionProperty(property: String, value: Value): Expected<String, None> {
    return map.setStyleProjectionProperty(property, value)
  }

  fun cameraForCoordinates(
    points: List<Point>,
    padding: EdgeInsets?,
    bearing: Double?,
    pitch: Double?
  ): CameraOptions {
    return map.cameraForCoordinates(points, padding, bearing, pitch)
  }

  fun cameraForCoordinates(
    points: List<Point>,
    camera: CameraOptions,
    box: ScreenBox
  ): CameraOptions {
    return map.cameraForCoordinates(points, camera, box)
  }

  fun cameraForGeometry(
    geometry: Geometry,
    padding: EdgeInsets?,
    bearing: Double?,
    pitch: Double?
  ): CameraOptions {
    return map.cameraForGeometry(geometry, padding, bearing, pitch)
  }

  fun getElevation(point: Point): Double? {
    return map.getElevation(point)
  }

  fun setViewAnnotationPositionsUpdateListener(listener: ViewAnnotationPositionsUpdateListener?) {
    map.setViewAnnotationPositionsUpdateListener(listener)
  }

  fun addViewAnnotation(
    identifier: String,
    options: ViewAnnotationOptions
  ): Expected<String, None> {
    return map.addViewAnnotation(identifier, options)
  }

  fun updateViewAnnotation(
    identifier: String,
    options: ViewAnnotationOptions
  ): Expected<String, None> {
    return map.updateViewAnnotation(identifier, options)
  }

  fun removeViewAnnotation(identifier: String): Expected<String, None> {
    return map.removeViewAnnotation(identifier)
  }

  fun getViewAnnotationOptions(identifier: String): Expected<String, ViewAnnotationOptions> {
    return map.getViewAnnotationOptions(identifier)
  }

  fun tileCover(
    tileCoverOptions: TileCoverOptions,
    cameraOptions: CameraOptions?
  ): MutableList<CanonicalTileID> {
    return map.tileCover(tileCoverOptions, cameraOptions)
  }

  fun coordinateBoundsForCamera(cameraOptions: CameraOptions): CoordinateBounds {
    return map.coordinateBoundsForCamera(cameraOptions)
  }

  fun coordinateBoundsForCameraUnwrapped(cameraOptions: CameraOptions): CoordinateBounds {
    return map.coordinateBoundsForCamera(cameraOptions)
  }

  fun coordinateBoundsZoomForCameraUnwrapped(cameraOptions: CameraOptions): CoordinateBoundsZoom {
    return map.coordinateBoundsZoomForCameraUnwrapped(cameraOptions)
  }

  fun pixelsForCoordinates(coordinates: MutableList<Point>): MutableList<ScreenCoordinate> {
    return map.pixelsForCoordinates(coordinates)
  }

  fun setGestureInProgress(gestureInProgress: Boolean) {
    map.isGestureInProgress = gestureInProgress
  }

  fun isGestureInProgress(): Boolean {
    return map.isGestureInProgress
  }

  fun setBounds(boundOptions: CameraBoundsOptions): Expected<String, None> {
    return map.setBounds(boundOptions)
  }

  fun getBounds(): CameraBounds {
    return map.bounds
  }

  fun setPrefetchZoomDelta(zoomDelta: Byte) {
    map.prefetchZoomDelta = zoomDelta
  }

  fun getPrefetchZoomDelta(): Byte {
    return map.prefetchZoomDelta
  }

  fun setNorthOrientation(northOrientation: NorthOrientation) {
    map.setNorthOrientation(northOrientation)
  }

  fun setConstrainMode(constrainMode: ConstrainMode) {
    map.setConstrainMode(constrainMode)
  }

  fun setViewportMode(viewportMode: ViewportMode) {
    map.setViewportMode(viewportMode)
  }

  fun getMapOptions(): MapOptions {
    return map.mapOptions
  }

  fun triggerRepaint() {
    map.triggerRepaint()
  }

  fun setStyleLayerProperty(
    layerId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return map.setStyleLayerProperty(layerId, property, value)
  }

  fun pixelForCoordinate(pixel: Point): ScreenCoordinate {
    return map.pixelForCoordinate((pixel))
  }

  fun coordinateForPixel(screenCoordinate: ScreenCoordinate): Point {
    return map.coordinateForPixel(screenCoordinate)
  }

  fun getDebug(): List<MapDebugOptions> {
    return map.debug
  }

  fun setDebug(list: List<MapDebugOptions>, debugActive: Boolean) {
    map.setDebug(list, debugActive)
  }

  fun addStyleLayer(
    parameters: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.addStyleLayer(parameters, layerPosition)
  }

  fun addStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.addStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  fun addPersistentStyleLayer(
    properties: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.addPersistentStyleLayer(properties, layerPosition)
  }

  fun addPersistentStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.addPersistentStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  fun isStyleLayerPersistent(layerId: String): Expected<String, Boolean> {
    return map.isStyleLayerPersistent(layerId)
  }

  fun moveStyleLayer(
    layerId: String,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.moveStyleLayer(layerId, layerPosition)
  }

  fun setStyleJSON(json: String) {
    map.styleJSON = json
  }

  fun getStyleJSON(): String {
    return map.styleJSON
  }

  fun getStyleURI(): String {
    return map.styleURI
  }

  fun setStyleURI(uri: String) {
    map.styleURI = uri
  }

  fun getStyleDefaultCamera(): CameraOptions {
    return map.styleDefaultCamera
  }

  fun getStyleTransition(): TransitionOptions {
    return map.styleTransition
  }

  fun setStyleTransition(transitionOptions: TransitionOptions) {
    map.styleTransition = transitionOptions
  }

  fun removeStyleLayer(layerId: String): Expected<String, None> {
    return map.removeStyleLayer(layerId)
  }

  fun styleLayerExists(layerId: String): Boolean {
    return map.styleLayerExists(layerId)
  }

  fun getStyleLayerProperty(layerId: String, property: String): StylePropertyValue {
    return map.getStyleLayerProperty(layerId, property)
  }

  fun setStyleLayerProperties(layerId: String, properties: Value): Expected<String, None> {
    return map.setStyleLayerProperties(layerId, properties)
  }

  fun addStyleSource(sourceId: String, source: Value): Expected<String, None> {
    return map.addStyleSource(sourceId, source)
  }

  fun getStyleSourceProperties(sourceId: String): Expected<String, Value> {
    return map.getStyleSourceProperties(sourceId)
  }

  fun updateStyleImageSourceImage(sourceId: String, image: Image): Expected<String, None> {
    return map.updateStyleImageSourceImage(sourceId, image)
  }

  fun getStyleLayerProperties(layerId: String): Expected<String, Value> {
    return map.getStyleLayerProperties(layerId)
  }

  fun removeStyleSource(sourceId: String): Expected<String, None> {
    return map.removeStyleSource(sourceId)
  }

  fun setStyleCustomGeometrySourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    featureCollection: MutableList<Feature>
  ): Expected<String, None> {
    return map.setStyleCustomGeometrySourceTileData(sourceId, tileId, featureCollection)
  }

  fun styleSourceExists(sourceId: String): Boolean {
    return map.styleSourceExists(sourceId)
  }

  fun setStyleLightProperty(
    id: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return map.setStyleLightProperty(id, property, value)
  }

  fun getStyleLights(): MutableList<StyleObjectInfo> {
    return map.styleLights
  }

  fun setStyleLights(lights: Value): Expected<String, None> {
    return map.setStyleLights(lights)
  }

  fun setStyleAtmosphere(properties: Value): Expected<String, None> {
    return map.setStyleAtmosphere(properties)
  }

  fun getStyleAtmosphereProperty(property: String): StylePropertyValue {
    return map.getStyleAtmosphereProperty(property)
  }

  fun setStyleAtmosphereProperty(property: String, value: Value): Expected<String, None> {
    return map.setStyleAtmosphereProperty(property, value)
  }

  fun getStyleLightProperty(id: String, property: String): StylePropertyValue {
    return map.getStyleLightProperty(id, property)
  }

  fun getStyleImage(imageId: String): Image? {
    return map.getStyleImage(imageId)
  }

  fun removeStyleImage(imageId: String): Expected<String, None> {
    return map.removeStyleImage(imageId)
  }

  fun hasStyleImage(imageId: String): Boolean {
    return map.hasStyleImage(imageId)
  }

  fun addStyleModel(modelId: String, modelUri: String): Expected<String, None> {
    return map.addStyleModel(modelId, modelUri)
  }

  fun removeStyleModel(modelId: String): Expected<String, None> {
    return map.removeStyleModel(modelId)
  }

  fun hasStyleModel(modelId: String): Boolean {
    return map.hasStyleModel(modelId)
  }

  fun queryRenderedFeatures(
    geometry: RenderedQueryGeometry,
    options: RenderedQueryOptions,
    callback: QueryRenderedFeaturesCallback
  ): Cancelable = map.queryRenderedFeatures(geometry, options, callback)

  fun querySourceFeatures(
    sourceId: String,
    options: SourceQueryOptions,
    callback: QuerySourceFeaturesCallback
  ): Cancelable = map.querySourceFeatures(sourceId, options, callback)

  fun queryFeatureExtensions(
    sourceIdentifier: String,
    feature: Feature,
    extension: String,
    extensionField: String,
    args: HashMap<String, Value>?,
    callback: QueryFeatureExtensionCallback
  ): Cancelable = map.queryFeatureExtensions(sourceIdentifier, feature, extension, extensionField, args, callback)

  fun setFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    state: Value,
    callback: FeatureStateOperationCallback,
  ): Cancelable = map.setFeatureState(sourceId, sourceLayerId, featureId, state, callback)

  fun getFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    callback: QueryFeatureStateCallback
  ): Cancelable = map.getFeatureState(sourceId, sourceLayerId, featureId, callback)

  fun getFreeCameraOptions(): FreeCameraOptions {
    return map.freeCameraOptions
  }

  fun setCamera(freeCameraOptions: FreeCameraOptions) {
    map.setCamera(freeCameraOptions)
  }

  fun invalidateStyleCustomGeometrySourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    return map.invalidateStyleCustomGeometrySourceTile(sourceId, tileId)
  }

  fun setStyleSourceProperties(
    sourceId: String,
    properties: Value
  ): Expected<String, None> {
    return map.setStyleSourceProperties(sourceId, properties)
  }

  fun setStyleGeoJSONSourceData(
    sourceId: String,
    dataId: String,
    data: GeoJSONSourceData
  ): Expected<String, None> {
    return map.setStyleGeoJSONSourceData(sourceId, dataId, data)
  }

  fun removeFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    stateKey: String?,
    callback: FeatureStateOperationCallback,
  ): Cancelable {
    return map.removeFeatureState(sourceId, sourceLayerId, featureId, stateKey, callback)
  }

  fun resetFeatureStates(
    sourceId: String,
    sourceLayerId: String?,
    callback: FeatureStateOperationCallback
  ): Cancelable {
    return map.resetFeatureStates(sourceId, sourceLayerId, callback)
  }

  fun setTileCacheBudget(tileCacheBudget: TileCacheBudget?) {
    map.setTileCacheBudget(tileCacheBudget)
  }

  fun addStyleCustomGeometrySource(
    sourceId: String,
    options: CustomGeometrySourceOptions
  ): Expected<String, None> {
    return map.addStyleCustomGeometrySource(sourceId, options)
  }

  fun addStyleImage(
    imageId: String,
    scale: Float,
    image: Image,
    sdf: Boolean,
    stretchX: MutableList<ImageStretches>,
    stretchY: MutableList<ImageStretches>,
    content: ImageContent?
  ): Expected<String, None> {
    return map.addStyleImage(imageId, scale, image, sdf, stretchX, stretchY, content)
  }

  fun reduceMemoryUse() {
    map.reduceMemoryUse()
  }

  fun isUserAnimationInProgress(): Boolean {
    return map.isUserAnimationInProgress
  }

  fun getStyleSourceProperty(sourceId: String, property: String): StylePropertyValue {
    return map.getStyleSourceProperty(sourceId, property)
  }

  fun isStyleLoaded(): Boolean {
    return map.isStyleLoaded
  }

  fun setStyleSourceProperty(
    sourceId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return map.setStyleSourceProperty(sourceId, property, value)
  }

  fun coordinateBoundsZoomForCamera(camera: CameraOptions): CoordinateBoundsZoom {
    return map.coordinateBoundsZoomForCamera(camera)
  }

  fun getStyleLayers(): List<StyleObjectInfo> {
    return map.styleLayers
  }

  fun getStyleSources(): List<StyleObjectInfo> {
    return map.styleSources
  }

  fun setRenderWorldCopies(renderWorldCopies: Boolean) {
    map.renderWorldCopies = renderWorldCopies
  }

  fun getRenderWorldCopies(): Boolean {
    return map.renderWorldCopies
  }

  fun subscribe(mapLoadedCallback: MapLoadedCallback): Cancelable {
    return map.subscribe(mapLoadedCallback)
  }

  fun subscribe(onMapIdleListener: MapIdleCallback): Cancelable {
    return map.subscribe(onMapIdleListener)
  }

  fun subscribe(mapLoadingErrorCallback: MapLoadingErrorCallback): Cancelable {
    return map.subscribe(mapLoadingErrorCallback)
  }

  fun subscribe(styleLoadedCallback: StyleLoadedCallback): Cancelable {
    return map.subscribe(styleLoadedCallback)
  }

  fun subscribe(styleDataLoadedCallback: StyleDataLoadedCallback): Cancelable {
    return map.subscribe(styleDataLoadedCallback)
  }

  fun subscribe(sourceDataLoadedCallback: SourceDataLoadedCallback): Cancelable {
    return map.subscribe(sourceDataLoadedCallback)
  }

  fun subscribe(sourceAddedCallback: SourceAddedCallback): Cancelable {
    return map.subscribe(sourceAddedCallback)
  }

  fun subscribe(sourceRemovedCallback: SourceRemovedCallback): Cancelable {
    return map.subscribe(sourceRemovedCallback)
  }

  fun subscribe(styleImageMissingCallback: StyleImageMissingCallback): Cancelable {
    return map.subscribe(styleImageMissingCallback)
  }

  fun subscribe(styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback): Cancelable {
    return map.subscribe(styleImageRemoveUnusedCallback)
  }

  fun subscribe(cameraChangedCallback: CameraChangedCallback): Cancelable {
    return map.subscribe(cameraChangedCallback)
  }

  fun subscribe(renderFrameStartedCallback: RenderFrameStartedCallback): Cancelable {
    return map.subscribe(renderFrameStartedCallback)
  }

  fun subscribe(renderFrameFinishedCallback: RenderFrameFinishedCallback): Cancelable {
    return map.subscribe(renderFrameFinishedCallback)
  }

  fun subscribe(resourceRequestCallback: ResourceRequestCallback): Cancelable {
    return map.subscribe(resourceRequestCallback)
  }

  @MapboxExperimental
  fun subscribe(eventName: String, onGenericEventsListener: GenericEventCallback): Cancelable {
    return map.subscribe(eventName, onGenericEventsListener)
  }

  fun getAttributions(): List<String> {
    return map.attributions
  }

  fun cameraForDrag(fromPoint: ScreenCoordinate, toPoint: ScreenCoordinate): CameraOptions {
    return map.cameraForDrag(fromPoint, toPoint)
  }

  fun setCenterAltitudeMode(mode: MapCenterAltitudeMode) {
    map.setCenterAltitudeMode(mode)
  }
}