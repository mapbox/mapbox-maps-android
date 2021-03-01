package com.mapbox.maps

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import java.util.*

internal class NativeMapImpl(private val map: MapInterface) :
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

  override fun jumpTo(cameraOptions: CameraOptions) {
    map.jumpTo(cameraOptions)
  }

  override fun getCameraOptions(edgeInsets: EdgeInsets?): CameraOptions {
    return map.getCameraOptions(edgeInsets)
  }

  override fun easeTo(cameraOptions: CameraOptions, animationOptions: AnimationOptions) {
    map.easeTo(cameraOptions, animationOptions)
  }

  override fun flyTo(cameraOptions: CameraOptions, animationOptions: AnimationOptions) {
    map.flyTo(cameraOptions, animationOptions)
  }

  override fun dragStart(point: ScreenCoordinate) {
    map.dragStart(point)
  }

  override fun dragEnd() {
    map.dragEnd()
  }

  override fun drag(
    fromPoint: ScreenCoordinate,
    toPoint: ScreenCoordinate,
    animation: AnimationOptions?
  ) {
    map.drag(fromPoint, toPoint, animation)
  }

  override fun moveBy(screenCoordinate: ScreenCoordinate, animationOptions: AnimationOptions?) {
    map.moveBy(screenCoordinate, animationOptions)
  }

  override fun scaleBy(
    amount: Double,
    screenCoordinate: ScreenCoordinate?,
    animationOptions: AnimationOptions?
  ) {
    map.scaleBy(amount, screenCoordinate, animationOptions)
  }

  override fun coordinatesForPixels(pixels: MutableList<ScreenCoordinate>): MutableList<Point> {
    return map.coordinatesForPixels(pixels)
  }

  override fun pitchBy(pitchBy: Double, animationOptions: AnimationOptions?) {
    map.pitchBy(pitchBy, animationOptions)
  }

  override fun rotateBy(
    first: ScreenCoordinate,
    second: ScreenCoordinate,
    animationOptions: AnimationOptions?
  ) {
    map.rotateBy(first, second, animationOptions)
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
  ): Expected<Void, String> {
    return map.invalidateStyleCustomGeometrySourceRegion(sourceId, coordinateBounds)
  }

  override fun setStyleTerrain(properties: Value): Expected<Void, String> {
    return map.setStyleTerrain(properties)
  }

  override fun getStyleTerrainProperty(property: String): StylePropertyValue {
    return map.getStyleTerrainProperty(property)
  }

  override fun setStyleTerrainProperty(property: String, value: Value): Expected<Void, String> {
    return map.setStyleTerrainProperty(property, value)
  }

  override fun cameraForCoordinates(
    points: List<Point>,
    edgeInsets: EdgeInsets,
    zoom: Double?,
    pitch: Double?
  ): CameraOptions {
    return map.cameraForCoordinates(points, edgeInsets, zoom, pitch)
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

  override fun coordinateBoundsForCamera(cameraOptions: CameraOptions): CoordinateBounds {
    return map.coordinateBoundsForCamera(cameraOptions)
  }

  override fun coordinateBoundsZoomForCameraUnwrapped(cameraOptions: CameraOptions): CoordinateBoundsZoom {
    return map.coordinateBoundsZoomForCameraUnwrapped(cameraOptions)
  }

  override fun cancelTransitions() {
    map.cancelTransitions()
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

  override fun isRotating(): Boolean {
    return map.isRotating
  }

  override fun isScaling(): Boolean {
    return map.isScaling
  }

  override fun isPanning(): Boolean {
    return map.isPanning
  }

  override fun setBounds(boundOptions: BoundOptions) {
    map.bounds = boundOptions
  }

  override fun getBounds(): BoundOptions {
    return map.bounds
  }

  override fun setPrefetchZoomDelta(zoomDelta: Byte) {
    map.prefetchZoomDelta = zoomDelta
  }

  override fun getPrefetchZoomDelta(): Byte {
    return map.prefetchZoomDelta
  }

  override fun setDefaultFramebufferObject(defaultFramebufferObject: Int) {
    map.setDefaultFramebufferObject(defaultFramebufferObject)
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
  ): Expected<Void, String> {
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

  override fun isMapFullyLoaded(): Boolean {
    return map.isMapFullyLoaded
  }

  override fun isStyleFullyLoaded(): Boolean {
    return map.isStyleFullyLoaded
  }

  override fun dumpDebugLogs() {
    map.dumpDebugLogs()
  }

  override fun addStyleLayer(
    parameters: Value,
    layerPosition: LayerPosition?
  ): Expected<Void, String> {
    return map.addStyleLayer(parameters, layerPosition)
  }

  override fun addStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<Void, String> {
    return map.addStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  override fun getResourceOptions(): ResourceOptions {
    return map.resourceOptions
  }

  override fun getMinZoom(): Double {
    return map.minZoom
  }

  override fun getMaxZoom(): Double {
    return map.maxZoom
  }

  override fun getScale(): Double {
    return map.scale
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

  override fun removeStyleLayer(layerId: String): Expected<Void, String> {
    return map.removeStyleLayer(layerId)
  }

  override fun styleLayerExists(layerId: String): Boolean {
    return map.styleLayerExists(layerId)
  }

  override fun getStyleLayerProperty(layerId: String, property: String): StylePropertyValue {
    return map.getStyleLayerProperty(layerId, property)
  }

  override fun setStyleLayerProperties(layerId: String, properties: Value): Expected<Void, String> {
    return map.setStyleLayerProperties(layerId, properties)
  }

  override fun addStyleSource(sourceId: String, source: Value): Expected<Void, String> {
    return map.addStyleSource(sourceId, source)
  }

  override fun getStyleSourceProperties(sourceId: String): Expected<Value, String> {
    return map.getStyleSourceProperties(sourceId)
  }

  override fun updateStyleImageSourceImage(sourceId: String, image: Image): Expected<Void, String> {
    return map.updateStyleImageSourceImage(sourceId, image)
  }

  override fun getStyleLayerProperties(layerId: String): Expected<Value, String> {
    return map.getStyleLayerProperties(layerId)
  }

  override fun removeStyleSource(sourceId: String): Expected<Void, String> {
    return map.removeStyleSource(sourceId)
  }

  override fun setStyleCustomGeometrySourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    featureCollection: MutableList<Feature>
  ): Expected<Void, String> {
    return map.setStyleCustomGeometrySourceTileData(sourceId, tileId, featureCollection)
  }

  override fun styleSourceExists(sourceId: String): Boolean {
    return map.styleSourceExists(sourceId)
  }

  override fun setStyleLight(light: Value): Expected<Void, String> {
    return map.setStyleLight(light)
  }

  override fun setStyleLightProperty(property: String, value: Value): Expected<Void, String> {
    return map.setStyleLightProperty(property, value)
  }

  override fun getStyleLightProperty(property: String): StylePropertyValue {
    return map.getStyleLightProperty(property)
  }

  override fun getStyleImage(imageId: String): Image? {
    return map.getStyleImage(imageId)
  }

  override fun removeStyleImage(imageId: String): Expected<Void, String> {
    return map.removeStyleImage(imageId)
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
    return map.getFreeCameraOptions()
  }

  override fun setFreeCameraOptions(
    options: FreeCameraOptions
  ) {
    map.setFreeCameraOptions(options)
  }

  override fun invalidateStyleCustomGeometrySourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<Void, String> {
    return map.invalidateStyleCustomGeometrySourceTile(sourceId, tileId)
  }

  override fun setStyleSourceProperties(
    sourceId: String,
    properties: Value
  ): Expected<Void, String> {
    return map.setStyleSourceProperties(sourceId, properties)
  }

  override fun removeFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    stateKey: String?
  ) {
    return map.removeFeatureState(sourceId, sourceLayerId, featureId, stateKey)
  }

  override fun addStyleCustomGeometrySource(
    sourceId: String,
    options: CustomGeometrySourceOptions
  ): Expected<Void, String> {
    return map.addStyleCustomGeometrySource(sourceId, options)
  }

  override fun addStyleImage(
    imageId: String,
    scale: Float,
    float: Image,
    sdf: Boolean,
    stretchX: MutableList<ImageStretches>,
    stretchY: MutableList<ImageStretches>,
    content: ImageContent?
  ): Expected<Void, String> {
    return map.addStyleImage(imageId, scale, float, sdf, stretchX, stretchY, content)
  }

  override fun reduceMemoryUse() {
    map.reduceMemoryUse()
  }

  override fun isUserAnimationInProgress(): Boolean {
    return map.isUserAnimationInProgress
  }

  override fun getStyleGeoJSONSourceClusterLeaves(
    sourceId: String,
    clusterId: Int,
    limit: Int,
    offset: Int
  ): Expected<MutableList<Feature>, String> {
    return map.getStyleGeoJSONSourceClusterLeaves(sourceId, clusterId, limit, offset)
  }

  override fun getStyleGeoJSONSourceClusterExpansionZoom(
    sourceId: String,
    clusterId: Int
  ): Expected<Byte, String> {
    return map.getStyleGeoJSONSourceClusterExpansionZoom(sourceId, clusterId)
  }

  override fun getStyleGeoJSONSourceClusterChildren(
    sourceId: String,
    clusterId: Int
  ): Expected<MutableList<Feature>, String> {
    return map.getStyleGeoJSONSourceClusterChildren(sourceId, clusterId)
  }

  override fun getStyleSourceProperty(sourceId: String, property: String): StylePropertyValue {
    return map.getStyleSourceProperty(sourceId, property)
  }

  override fun setStyleSourceProperty(
    sourceId: String,
    property: String,
    value: Value
  ): Expected<Void, String> {
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
}