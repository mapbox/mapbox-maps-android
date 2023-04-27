package com.mapbox.maps

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature

@OptIn(MapboxInternal::class)
internal class MapSnapshotterWrapper(
  private val mapSnapshotter: MapSnapshotter,
) : StyleManagerInterface {

  override fun subscribe(observer: Observer, events: MutableList<String>) {
    mapSnapshotter.subscribe(observer, events)
  }

  override fun unsubscribe(observer: Observer, events: MutableList<String>) {
    mapSnapshotter.unsubscribe(observer, events)
  }

  override fun unsubscribe(observer: Observer) {
    mapSnapshotter.unsubscribe(observer)
  }

  override fun getStyleURI(): String {
    return mapSnapshotter.styleURI
  }

  override fun setStyleURI(uri: String) {
    mapSnapshotter.styleURI = uri
  }

  override fun getStyleJSON(): String {
    return mapSnapshotter.styleJSON
  }

  override fun setStyleJSON(json: String) {
    mapSnapshotter.styleJSON = json
  }

  override fun getStyleDefaultCamera(): CameraOptions {
    return mapSnapshotter.styleDefaultCamera
  }

  override fun getStyleTransition(): TransitionOptions {
    return mapSnapshotter.styleTransition
  }

  override fun setStyleTransition(transitionOptions: TransitionOptions) {
    mapSnapshotter.styleTransition = transitionOptions
  }

  override fun addStyleLayer(
    properties: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return mapSnapshotter.addStyleLayer(properties, layerPosition)
  }

  override fun addStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return mapSnapshotter.addStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  override fun addPersistentStyleLayer(
    properties: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return mapSnapshotter.addPersistentStyleLayer(properties, layerPosition)
  }

  override fun addPersistentStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return mapSnapshotter.addPersistentStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  override fun isStyleLayerPersistent(layerId: String): Expected<String, Boolean> {
    return mapSnapshotter.isStyleLayerPersistent(layerId)
  }

  override fun removeStyleLayer(layerId: String): Expected<String, None> {
    return mapSnapshotter.removeStyleLayer(layerId)
  }

  override fun moveStyleLayer(
    layerId: String,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return mapSnapshotter.moveStyleLayer(layerId, layerPosition)
  }

  override fun styleLayerExists(layerId: String): Boolean {
    return mapSnapshotter.styleLayerExists(layerId)
  }

  override fun getStyleLayers(): MutableList<StyleObjectInfo> {
    return mapSnapshotter.styleLayers
  }

  override fun getStyleLayerProperty(layerId: String, property: String): StylePropertyValue {
    return mapSnapshotter.getStyleLayerProperty(layerId, property)
  }

  override fun setStyleLayerProperty(
    layerId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return mapSnapshotter.setStyleLayerProperty(layerId, property, value)
  }

  override fun getStyleLayerProperties(layerId: String): Expected<String, Value> {
    return mapSnapshotter.getStyleLayerProperties(layerId)
  }

  override fun setStyleLayerProperties(layerId: String, properties: Value): Expected<String, None> {
    return mapSnapshotter.setStyleLayerProperties(layerId, properties)
  }

  override fun addStyleSource(sourceId: String, properties: Value): Expected<String, None> {
    return mapSnapshotter.addStyleSource(sourceId, properties)
  }

  override fun getStyleSourceProperty(sourceId: String, property: String): StylePropertyValue {
    return mapSnapshotter.getStyleSourceProperty(sourceId, property)
  }

  override fun setStyleSourceProperty(
    sourceId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return mapSnapshotter.setStyleSourceProperty(sourceId, property, value)
  }

  override fun getStyleSourceProperties(sourceId: String): Expected<String, Value> {
    return mapSnapshotter.getStyleSourceProperties(sourceId)
  }

  override fun setStyleSourceProperties(
    sourceId: String,
    properties: Value
  ): Expected<String, None> {
    return mapSnapshotter.setStyleSourceProperties(sourceId, properties)
  }

  override fun setStyleGeoJSONSourceData(
    sourceId: String,
    data: GeoJSONSourceData
  ): Expected<String, None> {
    return mapSnapshotter.setStyleGeoJSONSourceData(sourceId, data)
  }

  override fun setStyleGeoJSONSourceData(
    sourceId: String,
    dataId: String,
    data: GeoJSONSourceData
  ): Expected<String, None> {
    return mapSnapshotter.setStyleGeoJSONSourceData(sourceId, dataId, data)
  }

  override fun updateStyleImageSourceImage(sourceId: String, image: Image): Expected<String, None> {
    return mapSnapshotter.updateStyleImageSourceImage(sourceId, image)
  }

  override fun removeStyleSource(sourceId: String): Expected<String, None> {
    return mapSnapshotter.removeStyleSource(sourceId)
  }

  override fun styleSourceExists(sourceId: String): Boolean {
    return mapSnapshotter.styleSourceExists(sourceId)
  }

  override fun getStyleSources(): MutableList<StyleObjectInfo> {
    return mapSnapshotter.styleSources
  }

  override fun setStyleLight(properties: Value): Expected<String, None> {
    return mapSnapshotter.setStyleLight(properties)
  }

  override fun getStyleLightProperty(property: String): StylePropertyValue {
    return mapSnapshotter.getStyleLightProperty(property)
  }

  override fun getStyleLightProperty(id: String, property: String): StylePropertyValue {
    return mapSnapshotter.getStyleLightProperty(id, property)
  }

  override fun setStyleLightProperty(property: String, value: Value): Expected<String, None> {
    return mapSnapshotter.setStyleLightProperty(property, value)
  }

  override fun setStyleLightProperty(
    id: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return mapSnapshotter.setStyleLightProperty(id, property, value)
  }

  override fun getStyleLights(): MutableList<StyleObjectInfo> {
    return mapSnapshotter.styleLights
  }

  override fun setStyleLights(lights: Value): Expected<String, None> {
    return mapSnapshotter.setStyleLights(lights)
  }

  override fun setStyleAtmosphere(properties: Value): Expected<String, None> {
    return mapSnapshotter.setStyleAtmosphere(properties)
  }

  override fun getStyleAtmosphereProperty(property: String): StylePropertyValue {
    return mapSnapshotter.getStyleAtmosphereProperty(property)
  }

  override fun setStyleAtmosphereProperty(property: String, value: Value): Expected<String, None> {
    return mapSnapshotter.setStyleAtmosphereProperty(property, value)
  }

  override fun setStyleTerrain(properties: Value): Expected<String, None> {
    return mapSnapshotter.setStyleTerrain(properties)
  }

  override fun getStyleTerrainProperty(property: String): StylePropertyValue {
    return mapSnapshotter.getStyleTerrainProperty(property)
  }

  override fun setStyleTerrainProperty(property: String, value: Value): Expected<String, None> {
    return mapSnapshotter.setStyleTerrainProperty(property, value)
  }

  override fun setStyleProjection(properties: Value): Expected<String, None> {
    return mapSnapshotter.setStyleProjection(properties)
  }

  override fun getStyleProjectionProperty(property: String): StylePropertyValue {
    return mapSnapshotter.getStyleProjectionProperty(property)
  }

  override fun setStyleProjectionProperty(property: String, value: Value): Expected<String, None> {
    return mapSnapshotter.setStyleProjectionProperty(property, value)
  }

  override fun getStyleImage(imageId: String): Image? {
    return mapSnapshotter.getStyleImage(imageId)
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
    return mapSnapshotter.addStyleImage(imageId, scale, image, sdf, stretchX, stretchY, content)
  }

  override fun removeStyleImage(imageId: String): Expected<String, None> {
    return mapSnapshotter.removeStyleImage(imageId)
  }

  override fun hasStyleImage(imageId: String): Boolean {
    return mapSnapshotter.hasStyleImage(imageId)
  }

  override fun addStyleModel(modelId: String, modelUri: String): Expected<String, None> {
    return mapSnapshotter.addStyleModel(modelId, modelUri)
  }

  override fun removeStyleModel(modelId: String): Expected<String, None> {
    return mapSnapshotter.removeStyleModel(modelId)
  }

  override fun hasStyleModel(modelId: String): Boolean {
    return mapSnapshotter.hasStyleModel(modelId)
  }

  override fun addStyleCustomGeometrySource(
    sourceId: String,
    options: CustomGeometrySourceOptions
  ): Expected<String, None> {
    return mapSnapshotter.addStyleCustomGeometrySource(sourceId, options)
  }

  override fun setStyleCustomGeometrySourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    featureCollection: MutableList<Feature>
  ): Expected<String, None> {
    return mapSnapshotter.setStyleCustomGeometrySourceTileData(sourceId, tileId, featureCollection)
  }

  override fun invalidateStyleCustomGeometrySourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    return mapSnapshotter.invalidateStyleCustomGeometrySourceTile(sourceId, tileId)
  }

  override fun invalidateStyleCustomGeometrySourceRegion(
    sourceId: String,
    bounds: CoordinateBounds
  ): Expected<String, None> {
    return mapSnapshotter.invalidateStyleCustomGeometrySourceRegion(sourceId, bounds)
  }

  override fun isStyleLoaded(): Boolean {
    return mapSnapshotter.isStyleLoaded
  }
}