package com.mapbox.maps

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point

internal class NativeMapImpl(val map: MapInterface) :
  MapInterface, StyleManagerInterface, ObservableInterface {

  override fun setSize(size: Size) {
    map.size = size
  }

  override fun getSize(): Size {
    return map.size
  }

  override fun createRenderer() {
    map.createRenderer()
  }

  override fun destroyRenderer() {
    map.destroyRenderer()
  }

  override fun render() {
    map.render()
  }

  override fun setCamera(cameraOptions: CameraOptions) {
    map.setCamera(cameraOptions)
  }

  override fun getCameraState(): CameraState {
    return map.cameraState
  }

  override fun dragStart(point: ScreenCoordinate) {
    map.dragStart(point)
  }

  override fun dragEnd() {
    map.dragEnd()
  }

  override fun getDragCameraOptions(
    fromPoint: ScreenCoordinate,
    toPoint: ScreenCoordinate
  ): CameraOptions {
    return map.getDragCameraOptions(fromPoint, toPoint)
  }

  override fun coordinatesForPixels(pixels: MutableList<ScreenCoordinate>): MutableList<Point> {
    return map.coordinatesForPixels(pixels)
  }

  override fun cameraForCoordinateBounds(
    coordinateBounds: CoordinateBounds,
    edgeInsets: EdgeInsets,
    zoom: Double?,
    pitch: Double?
  ): CameraOptions {
    return map.cameraForCoordinateBounds(coordinateBounds, edgeInsets, zoom, pitch)
  }

  override fun setUserAnimationInProgress(inProgress: Boolean) {
    map.isUserAnimationInProgress = inProgress
  }

  override fun invalidateStyleCustomGeometrySourceRegion(
    sourceId: String,
    coordinateBounds: CoordinateBounds
  ): Expected<String, None> {
    return map.invalidateStyleCustomGeometrySourceRegion(sourceId, coordinateBounds)
  }

  override fun setStyleTerrain(properties: Value): Expected<String, None> {
    return map.setStyleTerrain(properties)
  }

  override fun getStyleTerrainProperty(property: String): StylePropertyValue {
    return map.getStyleTerrainProperty(property)
  }

  override fun setStyleTerrainProperty(property: String, value: Value): Expected<String, None> {
    return map.setStyleTerrainProperty(property, value)
  }

  override fun setStyleProjection(properties: Value): Expected<String, None> {
    return map.setStyleProjection(properties)
  }

  override fun getStyleProjectionProperty(property: String): StylePropertyValue {
    return map.getStyleProjectionProperty(property)
  }

  override fun setStyleProjectionProperty(property: String, value: Value): Expected<String, None> {
    return map.setStyleProjectionProperty(property, value)
  }

  override fun cameraForCoordinates(
    points: List<Point>,
    edgeInsets: EdgeInsets,
    zoom: Double?,
    pitch: Double?
  ): CameraOptions {
    return map.cameraForCoordinates(points, edgeInsets, zoom, pitch)
  }

  override fun cameraForCoordinates(
    points: List<Point>,
    camera: CameraOptions,
    box: ScreenBox
  ): CameraOptions {
    return map.cameraForCoordinates(points, camera, box)
  }

  override fun cameraForGeometry(
    geometry: Geometry,
    edgeInsets: EdgeInsets,
    zoom: Double?,
    pitch: Double?
  ): CameraOptions {
    return map.cameraForGeometry(geometry, edgeInsets, zoom, pitch)
  }

  override fun getElevation(point: Point): Double? {
    return map.getElevation(point)
  }

  override fun setRenderCacheOptions(options: RenderCacheOptions) {
    map.renderCacheOptions = options
  }

  override fun getRenderCacheOptions(): RenderCacheOptions {
    return map.renderCacheOptions
  }

  override fun setViewAnnotationPositionsUpdateListener(listener: ViewAnnotationPositionsUpdateListener?) {
    map.setViewAnnotationPositionsUpdateListener(listener)
  }

  override fun addViewAnnotation(
    identifier: String,
    options: ViewAnnotationOptions
  ): Expected<String, None> {
    return map.addViewAnnotation(identifier, options)
  }

  override fun updateViewAnnotation(
    identifier: String,
    options: ViewAnnotationOptions
  ): Expected<String, None> {
    return map.updateViewAnnotation(identifier, options)
  }

  override fun removeViewAnnotation(identifier: String): Expected<String, None> {
    return map.removeViewAnnotation(identifier)
  }

  override fun getViewAnnotationOptions(identifier: String): Expected<String, ViewAnnotationOptions> {
    return map.getViewAnnotationOptions(identifier)
  }

  override fun tileCover(
    tileCoverOptions: TileCoverOptions,
    cameraOptions: CameraOptions?
  ): MutableList<CanonicalTileID> {
    return map.tileCover(tileCoverOptions, cameraOptions)
  }

  override fun coordinateBoundsForCamera(cameraOptions: CameraOptions): CoordinateBounds {
    return map.coordinateBoundsForCamera(cameraOptions)
  }

  override fun coordinateBoundsForCameraUnwrapped(cameraOptions: CameraOptions): CoordinateBounds {
    return map.coordinateBoundsForCamera(cameraOptions)
  }

  override fun coordinateBoundsZoomForCameraUnwrapped(cameraOptions: CameraOptions): CoordinateBoundsZoom {
    return map.coordinateBoundsZoomForCameraUnwrapped(cameraOptions)
  }

  override fun pixelsForCoordinates(coordinates: MutableList<Point>): MutableList<ScreenCoordinate> {
    return map.pixelsForCoordinates(coordinates)
  }

  override fun setGestureInProgress(gestureInProgress: Boolean) {
    map.isGestureInProgress = gestureInProgress
  }

  override fun isGestureInProgress(): Boolean {
    return map.isGestureInProgress
  }

  override fun setBounds(boundOptions: CameraBoundsOptions): Expected<String, None> {
    return map.setBounds(boundOptions)
  }

  override fun getBounds(): CameraBounds {
    return map.bounds
  }

  override fun setPrefetchZoomDelta(zoomDelta: Byte) {
    map.prefetchZoomDelta = zoomDelta
  }

  override fun getPrefetchZoomDelta(): Byte {
    return map.prefetchZoomDelta
  }

  override fun setNorthOrientation(northOrientation: NorthOrientation) {
    map.setNorthOrientation(northOrientation)
  }

  override fun setConstrainMode(constrainMode: ConstrainMode) {
    map.setConstrainMode(constrainMode)
  }

  override fun setViewportMode(viewportMode: ViewportMode) {
    map.setViewportMode(viewportMode)
  }

  override fun getMapOptions(): MapOptions {
    return map.mapOptions
  }

  override fun triggerRepaint() {
    map.triggerRepaint()
  }

  override fun setStyleLayerProperty(
    layerId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return map.setStyleLayerProperty(layerId, property, value)
  }

  override fun pixelForCoordinate(pixel: Point): ScreenCoordinate {
    return map.pixelForCoordinate((pixel))
  }

  override fun coordinateForPixel(screenCoordinate: ScreenCoordinate): Point {
    return map.coordinateForPixel(screenCoordinate)
  }

  override fun getDebug(): List<MapDebugOptions> {
    return map.debug
  }

  override fun setDebug(list: List<MapDebugOptions>, debugActive: Boolean) {
    map.setDebug(list, debugActive)
  }

  override fun addStyleLayer(
    parameters: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.addStyleLayer(parameters, layerPosition)
  }

  override fun addStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.addStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  override fun addPersistentStyleLayer(
    properties: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.addPersistentStyleLayer(properties, layerPosition)
  }

  override fun addPersistentStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.addPersistentStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  override fun isStyleLayerPersistent(layerId: String): Expected<String, Boolean> {
    return map.isStyleLayerPersistent(layerId)
  }

  override fun moveStyleLayer(
    layerId: String,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return map.moveStyleLayer(layerId, layerPosition)
  }

  override fun getResourceOptions(): ResourceOptions {
    return map.resourceOptions
  }

  override fun setStyleJSON(json: String) {
    map.styleJSON = json
  }

  override fun getStyleJSON(): String {
    return map.styleJSON
  }

  override fun getStyleURI(): String {
    return map.styleURI
  }

  override fun setStyleURI(uri: String) {
    map.styleURI = uri
  }

  override fun getStyleDefaultCamera(): CameraOptions {
    return map.styleDefaultCamera
  }

  override fun getStyleTransition(): TransitionOptions {
    return map.styleTransition
  }

  override fun setStyleTransition(transitionOptions: TransitionOptions) {
    map.styleTransition = transitionOptions
  }

  override fun removeStyleLayer(layerId: String): Expected<String, None> {
    return map.removeStyleLayer(layerId)
  }

  override fun styleLayerExists(layerId: String): Boolean {
    return map.styleLayerExists(layerId)
  }

  override fun getStyleLayerProperty(layerId: String, property: String): StylePropertyValue {
    return map.getStyleLayerProperty(layerId, property)
  }

  override fun setStyleLayerProperties(layerId: String, properties: Value): Expected<String, None> {
    return map.setStyleLayerProperties(layerId, properties)
  }

  override fun addStyleSource(sourceId: String, source: Value): Expected<String, None> {
    return map.addStyleSource(sourceId, source)
  }

  override fun getStyleSourceProperties(sourceId: String): Expected<String, Value> {
    return map.getStyleSourceProperties(sourceId)
  }

  override fun updateStyleImageSourceImage(sourceId: String, image: Image): Expected<String, None> {
    return map.updateStyleImageSourceImage(sourceId, image)
  }

  override fun getStyleLayerProperties(layerId: String): Expected<String, Value> {
    return map.getStyleLayerProperties(layerId)
  }

  override fun removeStyleSource(sourceId: String): Expected<String, None> {
    return map.removeStyleSource(sourceId)
  }

  override fun setStyleCustomGeometrySourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    featureCollection: MutableList<Feature>
  ): Expected<String, None> {
    return map.setStyleCustomGeometrySourceTileData(sourceId, tileId, featureCollection)
  }

  override fun styleSourceExists(sourceId: String): Boolean {
    return map.styleSourceExists(sourceId)
  }

  override fun setStyleLight(light: Value): Expected<String, None> {
    return map.setStyleLight(light)
  }

  override fun setStyleLightProperty(property: String, value: Value): Expected<String, None> {
    return map.setStyleLightProperty(property, value)
  }

  override fun setStyleLightProperty(
    id: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return map.setStyleLightProperty(id, property, value)
  }

  override fun getStyleLights(): MutableList<StyleObjectInfo> {
    return map.styleLights
  }

  override fun setStyleLights(lights: Value): Expected<String, None> {
    return map.setStyleLights(lights)
  }

  override fun setStyleAtmosphere(properties: Value): Expected<String, None> {
    return map.setStyleAtmosphere(properties)
  }

  override fun getStyleAtmosphereProperty(property: String): StylePropertyValue {
    return map.getStyleAtmosphereProperty(property)
  }

  override fun setStyleAtmosphereProperty(property: String, value: Value): Expected<String, None> {
    return map.setStyleAtmosphereProperty(property, value)
  }

  override fun getStyleLightProperty(property: String): StylePropertyValue {
    return map.getStyleLightProperty(property)
  }

  override fun getStyleLightProperty(id: String, property: String): StylePropertyValue {
    return map.getStyleLightProperty(id, property)
  }

  override fun getStyleImage(imageId: String): Image? {
    return map.getStyleImage(imageId)
  }

  override fun removeStyleImage(imageId: String): Expected<String, None> {
    return map.removeStyleImage(imageId)
  }

  override fun hasStyleImage(imageId: String): Boolean {
    return map.hasStyleImage(imageId)
  }

  override fun addStyleModel(modelId: String, modelUri: String): Expected<String, None> {
    return map.addStyleModel(modelId, modelUri)
  }

  override fun removeStyleModel(modelId: String): Expected<String, None> {
    return map.removeStyleModel(modelId)
  }

  override fun hasStyleModel(modelId: String): Boolean {
    return map.hasStyleModel(modelId)
  }

  override fun queryRenderedFeatures(
    shape: MutableList<ScreenCoordinate>,
    options: RenderedQueryOptions,
    callback: QueryFeaturesCallback
  ) {
    map.queryRenderedFeatures(shape, options, callback)
  }

  override fun queryRenderedFeatures(
    screenBox: ScreenBox,
    options: RenderedQueryOptions,
    callback: QueryFeaturesCallback
  ) {
    map.queryRenderedFeatures(screenBox, options, callback)
  }

  override fun queryRenderedFeatures(
    pixel: ScreenCoordinate,
    options: RenderedQueryOptions,
    callback: QueryFeaturesCallback
  ) {
    map.queryRenderedFeatures(pixel, options, callback)
  }

  override fun queryRenderedFeatures(
    geometry: RenderedQueryGeometry,
    options: RenderedQueryOptions,
    callback: QueryFeaturesCallback
  ): Cancelable {
    return map.queryRenderedFeatures(geometry, options, callback)
  }

  override fun querySourceFeatures(
    sourceId: String,
    options: SourceQueryOptions,
    callback: QueryFeaturesCallback
  ) {
    map.querySourceFeatures(sourceId, options, callback)
  }

  override fun queryFeatureExtensions(
    sourceIdentifier: String,
    feature: Feature,
    extension: String,
    extensionField: String,
    args: HashMap<String, Value>?,
    callback: QueryFeatureExtensionCallback
  ) {
    map.queryFeatureExtensions(sourceIdentifier, feature, extension, extensionField, args, callback)
  }

  override fun setFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    state: Value
  ) {
    map.setFeatureState(sourceId, sourceLayerId, featureId, state)
  }

  override fun getFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    callback: QueryFeatureStateCallback
  ) {
    map.getFeatureState(sourceId, sourceLayerId, featureId, callback)
  }

  override fun getFreeCameraOptions(): FreeCameraOptions {
    return map.freeCameraOptions
  }

  override fun setCamera(options: FreeCameraOptions) {
    map.setCamera(options)
  }

  override fun invalidateStyleCustomGeometrySourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    return map.invalidateStyleCustomGeometrySourceTile(sourceId, tileId)
  }

  override fun setStyleSourceProperties(
    sourceId: String,
    properties: Value
  ): Expected<String, None> {
    return map.setStyleSourceProperties(sourceId, properties)
  }

  @Deprecated(
    "This method will be removed in the next major release.",
    replaceWith = ReplaceWith("setStyleGeoJSONSourceData(sourceId, dataId, data)")
  )
  override fun setStyleGeoJSONSourceData(sourceId: String, data: GeoJSONSourceData): Expected<String, None> {
    return map.setStyleGeoJSONSourceData(sourceId, data)
  }

  override fun setStyleGeoJSONSourceData(
    sourceId: String,
    dataId: String,
    data: GeoJSONSourceData
  ): Expected<String, None> {
    return map.setStyleGeoJSONSourceData(sourceId, dataId, data)
  }

  override fun removeFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    stateKey: String?
  ) {
    return map.removeFeatureState(sourceId, sourceLayerId, featureId, stateKey)
  }

  override fun setMemoryBudget(memoryBudget: MapMemoryBudget?) {
    map.setMemoryBudget(memoryBudget)
  }

  override fun addStyleCustomGeometrySource(
    sourceId: String,
    options: CustomGeometrySourceOptions
  ): Expected<String, None> {
    return map.addStyleCustomGeometrySource(sourceId, options)
  }

  override fun addStyleImage(
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

  override fun reduceMemoryUse() {
    map.reduceMemoryUse()
  }

  override fun isUserAnimationInProgress(): Boolean {
    return map.isUserAnimationInProgress
  }

  override fun getStyleSourceProperty(sourceId: String, property: String): StylePropertyValue {
    return map.getStyleSourceProperty(sourceId, property)
  }

  override fun isStyleLoaded(): Boolean {
    return map.isStyleLoaded
  }

  override fun isMapLoaded(): Boolean {
    return map.isMapLoaded
  }

  override fun setStyleSourceProperty(
    sourceId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return map.setStyleSourceProperty(sourceId, property, value)
  }

  override fun coordinateBoundsZoomForCamera(camera: CameraOptions): CoordinateBoundsZoom {
    return map.coordinateBoundsZoomForCamera(camera)
  }

  override fun getStyleLayers(): List<StyleObjectInfo> {
    return map.styleLayers
  }

  override fun getStyleSources(): List<StyleObjectInfo> {
    return map.styleSources
  }

  override fun subscribe(observer: Observer, events: MutableList<String>) {
    map.subscribe(observer, events)
  }

  override fun unsubscribe(observer: Observer, events: MutableList<String>) {
    map.unsubscribe(observer, events)
  }

  override fun unsubscribe(observer: Observer) {
    map.unsubscribe(observer)
  }

  override fun setRenderWorldCopies(renderWorldCopies: Boolean) {
    map.renderWorldCopies = renderWorldCopies
  }

  override fun getRenderWorldCopies(): Boolean {
    return map.renderWorldCopies
  }
}