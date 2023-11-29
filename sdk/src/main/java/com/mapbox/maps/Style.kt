package com.mapbox.maps

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature

/**
 * The general class to interact with Styles in the Modular Mapbox Maps SDK for Android.
 * It exposes the entry point for all methods related to the Style.
 * You cannot instantiate [Style] object directly, rather, you must obtain one
 * from the [getStyle(onStyleLoaded)][MapboxMap.getStyle] method on [MapboxMap].
 *
 * Note: Similar to a View object, a [Style] should only be read and modified
 * from the main thread.
 */
class Style internal constructor(
  styleManager: StyleManager,
  pixelRatio: Float
) : MapboxStyleManager(styleManager, pixelRatio) {

  @Volatile
  private var isStyleValid = true

  /**
   * Mark style invalid.
   */
  internal fun markInvalid() {
    isStyleValid = false
  }

  /**
   * Whether the Style instance is valid.
   *
   * Style becomes invalid after MapView.onDestroy() is invoked or if new style was loaded,
   * calling any method then could result in undefined behaviour and will print an error log.
   *
   * Keeping the reference to an invalid Style instance introduces significant native memory leak.
   *
   * @return True if Style is valid and false otherwise.
   */
  fun isValid(): Boolean {
    return isStyleValid
  }

  /**
   * Returns the map style's default camera, if any, or a default camera otherwise.
   * The map style default camera is defined as follows:
   * - [center](https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-center)
   * - [zoom](https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-zoom)
   * - [bearing](https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-bearing)
   * - [pitch](https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-pitch)
   *
   * The style default camera is re-evaluated when a new style is loaded.
   *
   * @return Returns the map style default [camera][CameraOptions].
   */
  override val styleDefaultCamera: CameraOptions
    @MainThread
    get() {
      checkNativeStyle("getStyleDefaultCamera")
      return super.styleDefaultCamera
    }

  /**
   * Gets the value of style source property.
   *
   * @param sourceId A style source identifier.
   * @param property The style source property name.
   * @return The [StylePropertyValue] object.
   */
  @MainThread
  override fun getStyleSourceProperty(sourceId: String, property: String): StylePropertyValue {
    checkNativeStyle("getStyleSourceProperty")
    return super.getStyleSourceProperty(sourceId, property)
  }

  /**
   * Sets a value to a style source property.
   * Note: When setting the "data" property of a geojson source, this method does not synchronously parse the GeoJSON data.
   * The events API shall be used to make sure the provided GeoJSON data is valid.
   * In case the GeoJSON is valid, a [MapLoaded] event will be propagated. In case of errors, a [MapLoadingError] event will be propagated instead.
   *
   * @param sourceId A style source identifier.
   * @param property The style source property name.
   * @param value The style source property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleSourceProperty(
    sourceId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    checkNativeStyle("setStyleSourceProperty")
    return super.setStyleSourceProperty(sourceId, property, value)
  }

  /**
   * Returns the map style's transition options. By default, the style parser will attempt
   * to read the style default transition options, if any, fallbacking to an immediate transition
   * otherwise. Transition options can be overridden via [setStyleTransition], but the options are
   * reset once a new style has been loaded.
   *
   * The style transition is re-evaluated when a new style is loaded.
   *
   * @return Returns the map style [transition options][TransitionOptions].
   */
  @MainThread
  override fun getStyleTransition(): TransitionOptions {
    checkNativeStyle("getStyleTransition")
    return super.getStyleTransition()
  }

  /**
   * Overrides the map style's transition options with user-provided options.
   *
   * The style transition is re-evaluated when a new style is loaded.
   *
   * @param transitionOptions Map style [transition options][TransitionOptions].
   */
  @MainThread
  override fun setStyleTransition(transitionOptions: TransitionOptions) {
    checkNativeStyle("setStyleTransition")
    super.setStyleTransition(transitionOptions)
  }

  /**
   * Returns the existing style imports.
   *
   * @return The list containing the information about existing style import objects.
   */
  @MainThread
  override fun getStyleImports(): List<StyleObjectInfo> {
    checkNativeStyle("getStyleImports")
    return super.getStyleImports()
  }

  /**
   * Removes an existing style import.
   *
   * @param importId An identifier of the style import to remove.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @MainThread
  override fun removeStyleImport(importId: String): Expected<String, None> {
    checkNativeStyle("removeStyleImport")
    return super.removeStyleImport(importId)
  }

  /**
   * Gets style import schema.
   *
   * @param importId A style import identifier.
   *
   * @return The style import schema or a string describing an error if the operation was not successful.
   */
  @MainThread
  override fun getStyleImportSchema(importId: String): Expected<String, Value> {
    checkNativeStyle("getStyleImportSchema")
    return super.getStyleImportSchema(importId)
  }

  /**
   * Gets style import config.
   *
   * @return The style import configuration or a string describing an error if the operation was not successful.
   */
  @MainThread
  override fun getStyleImportConfigProperties(importId: String): Expected<String, HashMap<String, StylePropertyValue>> {
    checkNativeStyle("getStyleImportConfigProperties")
    return super.getStyleImportConfigProperties(importId)
  }

  /**
   * Gets the value of style import config.
   *
   * @param importId A style import identifier.
   * @param config The style import config name.
   * @return The style import config value.
   */
  @MainThread
  override fun getStyleImportConfigProperty(
    importId: String,
    config: String
  ): Expected<String, StylePropertyValue> {
    checkNativeStyle("getStyleImportConfigProperty")
    return super.getStyleImportConfigProperty(importId, config)
  }

  /**
   * Sets style import config.
   * This method can be used to perform batch update for a style import configurations.
   *
   * @param importId A style import identifier.
   * @param configs A map of style import configurations.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleImportConfigProperties(
    importId: String,
    configs: HashMap<String, Value>
  ): Expected<String, None> {
    checkNativeStyle("setStyleImportConfigProperties")
    return super.setStyleImportConfigProperties(importId, configs)
  }

  /**
   * Sets a value to a style import config.
   *
   * @param importId A style import identifier.
   * @param config The style import config name.
   * @param value The style import config value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleImportConfigProperty(
    importId: String,
    config: String,
    value: Value
  ): Expected<String, None> {
    checkNativeStyle("setStyleImportConfigProperty")
    return super.setStyleImportConfigProperty(importId, config, value)
  }

  /**
   * Get the styleURI of the current Mapbox Style in use.
   */
  override val styleURI: String
    @MainThread
    get() {
      checkNativeStyle("getStyleURI")
      return super.styleURI
    }

  /**
   * Get the JSON serialization string of the current Mapbox Style in use.
   *
   * @return A JSON string containing a serialized Mapbox Style.
   */
  override val styleJSON: String
    @MainThread
    get() {
      checkNativeStyle("getStyleJSON")
      return super.styleJSON
    }

  /**
   * Adds a new style layer.
   *
   * See [Style Specification - Layers](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers)
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * @param parameters A [map][Value] of style layer parameters.
   * @param position If not empty, the new layer will be added immediately below the layer with this id.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @MainThread
  override fun addStyleLayer(parameters: Value, position: LayerPosition?): Expected<String, None> {
    checkNativeStyle("addStyleLayer")
    return super.addStyleLayer(parameters, position)
  }

  /**
   * Removes an existing style layer
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * @param layerId Identifier of the style layer to remove.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @MainThread
  override fun removeStyleLayer(layerId: String): Expected<String, None> {
    checkNativeStyle("removeStyleLayer")
    return super.removeStyleLayer(layerId)
  }

  /**
   * Moves an existing style layer.
   *
   * @param layerId – Identifier of the style layer to move.
   * @param layerPosition – The layer will be positioned according to the [LayerPosition] parameters. If an empty LayerPosition is provided then the layer is moved to the top of the layerstack.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @MainThread
  override fun moveStyleLayer(
    layerId: String,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    checkNativeStyle("moveStyleLayer")
    return super.moveStyleLayer(layerId, layerPosition)
  }

  /**
   * Checks whether a given style layer exists.
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * @param layerId Style layer identifier.
   *
   * @return True if the given style layer exists, false otherwise.
   */
  @MainThread
  override fun styleLayerExists(layerId: String): Boolean {
    checkNativeStyle("styleLayerExists")
    return super.styleLayerExists(layerId)
  }

  /**
   * Gets the value of [style layer property][StylePropertyValue].
   *
   * @param layerId Style layer identifier.
   * @param property Style layer property name.
   * @return The property value in the layer with layerId.
   */
  @MainThread
  override fun getStyleLayerProperty(layerId: String, property: String): StylePropertyValue {
    checkNativeStyle("getStyleLayerProperty")
    return super.getStyleLayerProperty(layerId, property)
  }

  /**
   * Sets a value to a style layer property.
   *
   * @param layerId Style layer identifier.
   * @param property Style layer property name.
   * @param value Style layer property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleLayerProperty(
    layerId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    checkNativeStyle("setStyleLayerProperty")
    return super.setStyleLayerProperty(layerId, property, value)
  }

  /**
   * Adds a new style source.
   *
   * See [Style Specification - Sources](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources)
   *
   * @param sourceId An identifier for the style source.
   * @param properties A map of style source parameters.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun addStyleSource(sourceId: String, properties: Value): Expected<String, None> {
    checkNativeStyle("addStyleSource")
    return super.addStyleSource(sourceId, properties)
  }

  /**
   * Invalidate region for provided custom geometry source.
   *
   * @param sourceId Style source identifier
   * @param coordinateBounds Coordinate bounds.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun invalidateStyleCustomGeometrySourceRegion(
    sourceId: String,
    coordinateBounds: CoordinateBounds
  ): Expected<String, None> {
    checkNativeStyle("invalidateStyleCustomGeometrySourceRegion")
    return super.invalidateStyleCustomGeometrySourceRegion(sourceId, coordinateBounds)
  }

  /**
   * Invalidate tile for provided custom geometry source.
   *
   * @param sourceId Style source identifier
   * @param tileId Identifier of the tile
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun invalidateStyleCustomGeometrySourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    checkNativeStyle("invalidateStyleCustomGeometrySourceTile")
    return super.invalidateStyleCustomGeometrySourceTile(sourceId, tileId)
  }

  /**
   * Updates the image of an image style source.
   *
   * See [Style Specification - Sources Image](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image)
   *
   * @param sourceId Style source identifier.
   * @param image Pixel data of the image.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun updateStyleImageSourceImage(sourceId: String, image: Image): Expected<String, None> {
    checkNativeStyle("updateStyleImageSourceImage")
    return super.updateStyleImageSourceImage(sourceId, image)
  }

  /**
   * Removes an existing style source.
   *
   * @param sourceId Identifier of the style source to remove.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun removeStyleSource(sourceId: String): Expected<String, None> {
    checkNativeStyle("removeStyleSource")
    return super.removeStyleSource(sourceId)
  }

  /**
   * Set tile data of a custom geometry.
   *
   * @param sourceId Style source identifier
   * @param tileId Identifier of the tile
   * @param featureCollection An array with the features to add
   */
  @MainThread
  override fun setStyleCustomGeometrySourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    featureCollection: MutableList<Feature>
  ): Expected<String, None> {
    checkNativeStyle("setStyleCustomGeometrySourceTileData")
    return super.setStyleCustomGeometrySourceTileData(sourceId, tileId, featureCollection)
  }

  /**
   * Checks whether a given style source exists.
   *
   * @param sourceId Style source identifier.
   *
   * @return True if the given source exists, false otherwise.
   */
  @MainThread
  override fun styleSourceExists(sourceId: String): Boolean {
    checkNativeStyle("styleSourceExists")
    return super.styleSourceExists(sourceId)
  }

  /**
   * Sets a value to the light property.
   *
   * @param id light id.
   * @param property light property name.
   * @param value light property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleLightProperty(
    id: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    checkNativeStyle("setStyleLightProperty")
    return super.setStyleLightProperty(id, property, value)
  }

  /**
   * Gets added lights to the style.
   *
   * @return list of [StyleObjectInfo].
   */
  @MainThread
  override fun getStyleLights(): MutableList<StyleObjectInfo> {
    checkNativeStyle("getStyleLights")
    return super.getStyleLights()
  }

  /**
   * Sets lights to the style.
   *
   * @param lights light value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleLights(lights: Value): Expected<String, None> {
    checkNativeStyle("setStyleLights")
    return super.setStyleLights(lights)
  }

  /**
   * Sets the style global [atmosphere](https://docs.mapbox.com/mapbox-gl-js/style-spec/fog/) properties.
   *
   * @param properties A map of style atmosphere properties values, with their names as a key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleAtmosphere(properties: Value): Expected<String, None> {
    checkNativeStyle("setStyleAtmosphere")
    return super.setStyleAtmosphere(properties)
  }

  /**
   * Gets the value of a style atmosphere property.
   *
   * @param property The style atmosphere property name.
   * @return The style atmosphere property value.
   */
  @MainThread
  override fun getStyleAtmosphereProperty(property: String): StylePropertyValue {
    checkNativeStyle("getStyleAtmosphereProperty")
    return super.getStyleAtmosphereProperty(property)
  }

  /**
   * Sets a value to the the style atmosphere property.
   *
   * @param property The style atmosphere property name.
   * @param value The style atmosphere property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleAtmosphereProperty(property: String, value: Value): Expected<String, None> {
    checkNativeStyle("setStyleAtmosphereProperty")
    return super.setStyleAtmosphereProperty(property, value)
  }

  /**
   * Sets the style global terrain source properties.
   *
   * See [Mapbox Style Specification: Terrain](https://docs.mapbox.com/mapbox-gl-js/style-spec/terrain/).
   *
   * @param properties A map of style terrain properties values, with their names as key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleTerrain(properties: Value): Expected<String, None> {
    checkNativeStyle("setStyleTerrain")
    return super.setStyleTerrain(properties)
  }

  /**
   * Get the value of a style terrain property.
   *
   * @param property Style terrain property name.
   * @return Style terrain property value.
   */
  @MainThread
  override fun getStyleTerrainProperty(property: String): StylePropertyValue {
    checkNativeStyle("getStyleTerrainProperty")
    return super.getStyleTerrainProperty(property)
  }

  /**
   * Sets the value of a style terrain property.
   *
   * @param property Style terrain property name.
   * @param value Style terrain property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleTerrainProperty(property: String, value: Value): Expected<String, None> {
    checkNativeStyle("setStyleTerrainProperty")
    return super.setStyleTerrainProperty(property, value)
  }

  /**
   * Sets the map's [projection](https://docs.mapbox.com/mapbox-gl-js/style-spec/projection/).
   * If called with `null`, the map will reset to Mercator.
   *
   * @param properties A map of style projection values, with their names as a key.
   * Supported projections are:
   *  * Mercator
   *  * Globe
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleProjection(properties: Value): Expected<String, None> {
    checkNativeStyle("setStyleProjection")
    return super.setStyleProjection(properties)
  }

  /**
   * Gets the value of a style projection property.
   *
   * @param property The style projection property name.
   * @return The style projection property value.
   */
  @MainThread
  override fun getStyleProjectionProperty(property: String): StylePropertyValue {
    checkNativeStyle("getStyleProjectionProperty")
    return super.getStyleProjectionProperty(property)
  }

  /**
   * Sets a value to the the style projection property.
   *
   * @param property The style projection property name.
   * @param value The style projection property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleProjectionProperty(property: String, value: Value): Expected<String, None> {
    checkNativeStyle("setStyleProjectionProperty")
    return super.setStyleProjectionProperty(property, value)
  }

  /**
   * Gets the value of the style light property.
   *
   * @param id light id.
   * @param property light property name.
   *
   * @return The value of property in the light.
   */
  @MainThread
  override fun getStyleLightProperty(id: String, property: String): StylePropertyValue {
    checkNativeStyle("getStyleLightProperty")
    return super.getStyleLightProperty(id, property)
  }

  /**
   * Adds an image to be used in the style. This API can also be used for updating
   * an image. If the image id was already added, it gets replaced by the new image.
   *
   * The image can be used in `icon-image`, `fill-pattern`, and `line-pattern`.
   *
   * See [https://www.mapbox.com/mapbox-gl-js/style-spec/#layout-symbol-icon-image](https://www.mapbox.com/mapbox-gl-js/style-spec/#layout-symbol-icon-image)
   * See [https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-line-line-pattern](https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-line-line-pattern)
   * See [https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-fill-fill-pattern](https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-fill-fill-pattern)
   *
   * @param imageId ID of the image.
   * @param scale Scale factor for the image.
   * @param image Pixel data of the image.
   * @param sdf Option to treat whether image is SDF(signed distance field) or not.
   * @param stretchX An array of two-element arrays, consisting of two numbers that represent
   * the from position and the to position of areas that can be stretched horizontally.
   * @param stretchY An array of two-element arrays, consisting of two numbers that represent
   * the from position and the to position of areas that can be stretched vertically.
   * @param content An array of four numbers, with the first two specifying the left, top
   * corner, and the last two specifying the right, bottom corner. If present, and if the
   * icon uses icon-text-fit, the symbol's text will be fit inside the content box.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun addStyleImage(
    imageId: String,
    scale: Float,
    image: Image,
    sdf: Boolean,
    stretchX: List<ImageStretches>,
    stretchY: List<ImageStretches>,
    content: ImageContent?
  ): Expected<String, None> {
    checkNativeStyle("addStyleImage")
    return super.addStyleImage(
      imageId,
      scale,
      image,
      sdf,
      stretchX,
      stretchY,
      content
    )
  }

  /**
   * Get an image from the style.
   *
   * @param imageId ID of the image.
   *
   * @return Image data associated with the given ID, or empty if no image is
   * associated with that ID.
   */
  @MainThread
  override fun getStyleImage(imageId: String): Image? {
    checkNativeStyle("getStyleImage")
    return super.getStyleImage(imageId)
  }

  /**
   * Removes an image from the style.
   *
   * @param imageId ID of the image to remove.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun removeStyleImage(imageId: String): Expected<String, None> {
    checkNativeStyle("removeStyleImage")
    return super.removeStyleImage(imageId)
  }

  /**
   * Checks whether an image exists.
   *
   * @param imageId The identifier of the image.
   *
   * @return True if image exists, false otherwise.
   */
  @MainThread
  override fun hasStyleImage(imageId: String): Boolean {
    checkNativeStyle("hasStyleImage")
    return super.hasStyleImage(imageId)
  }

  /**
   * Adds a model to be used in the style. This API can also be used for updating
   * a model. If the model for a given `modelId` was already added, it gets replaced by the new model.
   *
   * The model can be used in `model-id` property in model layer.
   *
   * @param modelId An identifier of the model.
   * @param modelUri A URI for the model.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MapboxExperimental
  @MainThread
  override fun addStyleModel(modelId: String, modelUri: String): Expected<String, None> {
    checkNativeStyle("addStyleModel")
    return super.addStyleModel(modelId, modelUri)
  }

  /**
   * Removes a model from the style.
   *
   * @param modelId The identifier of the model to remove.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MapboxExperimental
  @MainThread
  override fun removeStyleModel(modelId: String): Expected<String, None> {
    checkNativeStyle("removeStyleModel")
    return super.removeStyleModel(modelId)
  }

  /**
   * Checks whether a model exists.
   *
   * @param modelId The identifier of the model.
   *
   * @return True if model exists, false otherwise.
   */
  @MapboxExperimental
  @MainThread
  override fun hasStyleModel(modelId: String): Boolean {
    checkNativeStyle("hasStyleModel")
    return super.hasStyleModel(modelId)
  }

  /**
   * Gets style layer properties.
   *
   * @param layerId A style layer identifier.
   * @return Style layer metadata or a string describing an error if the operation was not successful.
   */
  @MainThread
  override fun getStyleLayerProperties(layerId: String): Expected<String, Value> {
    checkNativeStyle("getStyleLayerProperties")
    return super.getStyleLayerProperties(layerId)
  }

  /**
   * Sets style layer metadata.
   *
   * @param layerId A style layer identifier.
   * @param properties the value wrapper around layer metadata
   * @return a string describing an error if the operation was not successful.
   */
  @MainThread
  override fun setStyleLayerProperties(layerId: String, properties: Value): Expected<String, None> {
    checkNativeStyle("setStyleLayerProperties")
    return super.setStyleLayerProperties(layerId, properties)
  }

  /**
   * Adds a new style custom layer.
   *
   * [style-spec#layers](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers)
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * Note: Custom layers are only valid on OpenGL rendering backend. Attempting to add a custom layer on Metal
   * rendering backend results in undefined behavior.
   *
   * @param layerId Style layer id.
   * @param layerHost Style custom layer host.
   * @param layerPosition The position of the layer
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @MainThread
  override fun addStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    checkNativeStyle("addStyleCustomLayer")
    return super.addStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  /**
   * Adds a new [style layer](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers).
   *
   * Whenever a new style is being parsed and currently used style has persistent layers,
   * an engine will try to do following:
   *   - keep the persistent layer at its relative position
   *   - keep the source used by a persistent layer
   *   - keep images added through `addStyleImage` method
   *
   * In cases when a new style has the same layer, source or image resource, style's resources would be
   * used instead and `MapLoadingError` event will be emitted.
   *
   * @param properties A map of style layer properties.
   * @param layerPosition If not empty, the new layer will be positioned according to `layer position` parameters.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @MainThread
  override fun addPersistentStyleLayer(
    properties: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    checkNativeStyle("addPersistentStyleLayer")
    return super.addPersistentStyleLayer(properties, layerPosition)
  }

  /**
   * Adds a new [style custom layer](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers).
   *
   * Whenever a new style is being parsed and currently used style has persistent layers,
   * an engine will try to do following:
   *   - keep the persistent layer at its relative position
   *   - keep the source used by a persistent layer
   *   - keep images added through `addStyleImage` method
   *
   * In cases when a new style has the same layer, source or image resource, style's resources would be
   * used instead and `MapLoadingError` event will be emitted.
   *
   * @param layerId A style layer identifier.
   * @param layerHost The custom layer host.
   * @param layerPosition If not empty, the new layer will be positioned according to `layer position` parameters.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @MainThread
  override fun addPersistentStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    checkNativeStyle("addPersistentStyleCustomLayer")
    return super.addPersistentStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  /**
   * Checks if a style layer is persistent.
   *
   * @param layerId A style layer identifier.
   * @return A string describing an error if the operation was not successful, boolean representing state otherwise.
   */
  @MainThread
  override fun isStyleLayerPersistent(layerId: String): Expected<String, Boolean> {
    checkNativeStyle("isStyleLayerPersistent")
    return super.isStyleLayerPersistent(layerId)
  }

  /**
   * Adds a custom geometry to be used in the style. To add the data, implement the [CustomGeometrySourceOptions.fetchTile@MainThread
  override function]
   * callback in the options and call [setStyleCustomGeometrySourceTileData]
   *
   * @param sourceId Style source identifier
   * @param options Settings for the custom geometry
   */
  @MainThread
  override fun addStyleCustomGeometrySource(
    sourceId: String,
    options: CustomGeometrySourceOptions
  ): Expected<String, None> {
    checkNativeStyle("addStyleCustomGeometrySource")
    return super.addStyleCustomGeometrySource(sourceId, options)
  }

  /**
   * Returns the existing style sources.
   *
   * @return The list containing the ids of the existing style sources.
   */
  override val styleSources: List<StyleObjectInfo>
    @MainThread
    get() {
      checkNativeStyle("getStyleSources")
      return super.styleSources
    }

  /**
   * Returns the existing style layers.
   *
   * @return The list containing the ids of the existing style layers.
   */
  override val styleLayers: List<StyleObjectInfo>
    @MainThread
    get() {
      checkNativeStyle("getStyleLayers")
      return super.styleLayers
    }

  /**
   * Gets style source parameters.
   * In order to convert returned value to a json string please take a look at [Value.toJson].
   *
   * @param sourceId Style source identifier.
   *
   * @return Style source parameters or a string describing an error if the operation was not successful.
   */
  @MainThread
  override fun getStyleSourceProperties(sourceId: String): Expected<String, Value> {
    checkNativeStyle("getStyleSourceProperties")
    return super.getStyleSourceProperties(sourceId)
  }

  /**
   * Sets style source parameters.
   * This method can be used to perform batch update for a style source parameters. The structure of a
   * provided `parameters` value must conform to [Style Specification - Sources](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/)
   * format for a corresponding source type. Modification of a source type
   * https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#type is not allowed.
   *
   * @param sourceId Style source identifier.
   * @param properties A map of Style source parameters.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MainThread
  override fun setStyleSourceProperties(
    sourceId: String,
    properties: Value
  ): Expected<String, None> {
    checkNativeStyle("setStyleSourceProperties")
    return super.setStyleSourceProperties(sourceId, properties)
  }

  /**
   * Set geojson source data.
   * Call may take significant time for parsing and marshalling depending on the data size.
   *
   * Direct method use is not recommended, consider using `GeoJsonSource.geometry`, `GeoJsonSource.feature`, `GeoJsonSource.featureCollection`, `GeoJsonSource.data` instead.
   *
   * @param sourceId the id of the GeoJSON source
   * @param dataId an arbitrary string used to track the given GeoJSON data, empty string means null ID
   * @param data the GeoJson source data
   *
   */
  @AnyThread
  override fun setStyleGeoJSONSourceData(
    sourceId: String,
    dataId: String,
    data: GeoJSONSourceData
  ): Expected<String, None> {
    if (!isStyleValid) {
      logW(
        TAG,
        "Style object (accessing setStyleGeoJSONSourceData) should not be stored and used after MapView is destroyed or new style has been loaded."
      )
    }
    return super.setStyleGeoJSONSourceData(sourceId, dataId, data)
  }

  /**
   * Add additional features to a GeoJSON style source.
   *
   * This method is thread-safe.
   *
   * Note that when calling this method from a thread other than the main thread, the return value does
   * not contain the actual operation status. To ensure the success of the operation, use the events API,
   * which will propagate a [MapLoaded] event upon success or a [MapLoadingError] event upon failure.
   *
   * Partially updating a GeoJSON source is not compatible with using shared cache and generated IDs.
   * It is important to ensure that every feature in the GeoJSON style source, as well as the newly added
   * feature, has a unique ID (or a unique promote ID if in use). Failure to provide unique IDs will result
   * in a [MapLoadingError].
   *
   * The method allows the user to provide a data ID, which will be returned as the dataId parameter in the
   * [SourceDataLoaded] event. However, it's important to note that multiple partial updates can be queued
   * for the same GeoJSON source when ongoing source parsing is taking place. In these cases, the partial
   * updates will be applied to the source in batches. Only the data ID provided in the most recent call within
   * each batch will be included in the [SourceDataLoaded] event. If no data ID is provided in the most recent
   * call, the data ID in the [SourceDataLoaded]event will be null.
   *
   * @param sourceId The identifier of the style source.
   * @param dataId An arbitrary string used to track the given GeoJSON data, empty string means null ID.
   * @param features An array of GeoJSON features to be added to the source.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @AnyThread
  override fun addGeoJSONSourceFeatures(
    sourceId: String,
    dataId: String,
    features: List<Feature>
  ): Expected<String, None> {
    if (!isStyleValid) {
      logW(
        TAG,
        "Style object (accessing addGeoJSONSourceFeatures) should not be stored and used after MapView is destroyed or new style has been loaded."
      )
    }
    return super.addGeoJSONSourceFeatures(sourceId, dataId, features)
  }

  /**
   * Update existing features in a GeoJSON style source.
   *
   * This method is thread safe.
   *
   * Note that when calling this method from a thread other than the main thread, the return value does
   * not contain the actual operation status. To ensure the success of the operation, use the events API,
   * which will propagate a [MapLoaded] event upon success or a [MapLoadingError] event upon failure.
   *
   * Partially updating a GeoJSON source is not compatible with using shared cache and generated IDs.
   * It is important to ensure that every feature in the GeoJSON style source, as well as the newly added
   * feature, has a unique ID (or a unique promote ID if in use). Failure to provide unique IDs will result
   * in a [MapLoadingError].
   *
   * The method allows the user to provide a data ID, which will be returned as the dataId parameter in the
   * [SourceDataLoaded] event. However, it's important to note that multiple partial updates can be queued
   * for the same GeoJSON source when ongoing source parsing is taking place. In these cases, the partial
   * updates will be applied to the source in batches. Only the data ID provided in the most recent call within
   * each batch will be included in the [SourceDataLoaded] event. If no data ID is provided in the most recent
   * call, the data ID in the [SourceDataLoaded]event will be null.
   *
   * @param sourceId A style source identifier.
   * @param dataId An arbitrary string used to track the given GeoJSON data, empty string means null ID.
   * @param features the GeoJSON features to be updated in the source.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @AnyThread
  override fun updateGeoJSONSourceFeatures(
    sourceId: String,
    dataId: String,
    features: List<Feature>
  ): Expected<String, None> {
    if (!isStyleValid) {
      logW(
        TAG,
        "Style object (accessing updateGeoJSONSourceFeatures) should not be stored and used after MapView is destroyed or new style has been loaded."
      )
    }
    return super.updateGeoJSONSourceFeatures(sourceId, dataId, features)
  }

  /**
   * Remove features from a GeoJSON style source.
   *
   * Note that when calling this method from a thread other than the main thread, the return value does
   * not contain the actual operation status. To ensure the success of the operation, use the events API,
   * which will propagate a [MapLoaded] event upon success or a [MapLoadingError] event upon failure.
   *
   * Partially updating a GeoJSON source is not compatible with using shared cache and generated IDs.
   * It is important to ensure that every feature in the GeoJSON style source, as well as the newly added
   * feature, has a unique ID (or a unique promote ID if in use). Failure to provide unique IDs will result
   * in a [MapLoadingError].
   *
   * The method allows the user to provide a data ID, which will be returned as the dataId parameter in the
   * [SourceDataLoaded] event. However, it's important to note that multiple partial updates can be queued
   * for the same GeoJSON source when ongoing source parsing is taking place. In these cases, the partial
   * updates will be applied to the source in batches. Only the data ID provided in the most recent call within
   * each batch will be included in the [SourceDataLoaded] event. If no data ID is provided in the most recent
   * call, the data ID in the [SourceDataLoaded]event will be null.
   *
   * @param sourceId A style source identifier.
   * @param dataId An arbitrary string used to track the given GeoJSON data, empty string means null ID.
   * @param featureIds the Ids of the features that need to be removed from the source.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @AnyThread
  override fun removeGeoJSONSourceFeatures(
    sourceId: String,
    dataId: String,
    featureIds: List<String>
  ): Expected<String, None> {
    if (!isStyleValid) {
      logW(
        TAG,
        "Style object (accessing removeGeoJSONSourceFeatures) should not be stored and used after MapView is destroyed or new style has been loaded."
      )
    }
    return super.removeGeoJSONSourceFeatures(sourceId, dataId, featureIds)
  }

  /**
   * Check if the style is completely loaded.
   *
   * @return TRUE if and only if the style JSON contents, the style specified sprite and sources are all loaded, otherwise returns FALSE.
   */
  @MainThread
  override fun isStyleLoaded(): Boolean {
    checkNativeStyle("isStyleLoaded")
    return super.isStyleLoaded()
  }

  /**
   * Note! This is an experimental feature. It can be changed or removed in future versions.
   *
   * Adds a custom raster source to be used in the style. To add the data, implement the fetchTile@MainThread
  override function callback in the options and call setStyleCustomRasterSourceTileData()
   *
   * @param sourceId A style source identifier
   * @param options The `custom raster source options` for the custom raster source.
   */
  @MapboxExperimental
  @MainThread
  override fun addStyleCustomRasterSource(
    sourceId: String,
    options: CustomRasterSourceOptions
  ): Expected<String, None> {
    checkNativeStyle("addStyleCustomRasterSource")
    return super.addStyleCustomRasterSource(sourceId, options)
  }

  /**
   * Note! This is an experimental feature. It can be changed or removed in future versions.
   *
   * Set tile data for a raster tile.
   *
   * By default, the provided data is not cached, and the implementation will call the fetch callback each time the tile reappears.
   * Use the [MapboxMap.setTileCacheBudget] API to establish an internal cache for the source.
   *
   * @param sourceId A style source identifier.
   * @param tileId A `canonical tile id` of the tile.
   * @param image `Image` content of the tile. If an empty image is provided then the tile gets removed from the map.
   */
  @MapboxExperimental
  @MainThread
  override fun setStyleCustomRasterSourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    image: Image?
  ): Expected<String, None> {
    checkNativeStyle("setStyleCustomRasterSourceTileData")
    return super.setStyleCustomRasterSourceTileData(sourceId, tileId, image)
  }

  /**
   * Note! This is an experimental feature. It can be changed or removed in future versions.
   *
   * Invalidate tile for provided custom raster source.
   *
   * @param sourceId A style source identifier,.
   * @param tileId A `canonical tile id` of the tile.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MapboxExperimental
  @MainThread
  override fun invalidateStyleCustomRasterSourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    checkNativeStyle("invalidateStyleCustomRasterSourceTile")
    return super.invalidateStyleCustomRasterSourceTile(sourceId, tileId)
  }

  /**
   * Note! This is an experimental feature. It can be changed or removed in future versions.
   *
   * Invalidate region for provided custom raster source.
   *
   * @param sourceId A style source identifier
   * @param bounds A `coordinate bounds` object.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MapboxExperimental
  @MainThread
  override fun invalidateStyleCustomRasterSourceRegion(
    sourceId: String,
    bounds: CoordinateBounds
  ): Expected<String, None> {
    checkNativeStyle("invalidateStyleCustomRasterSourceRegion")
    return super.invalidateStyleCustomRasterSourceRegion(sourceId, bounds)
  }

  private fun checkNativeStyle(methodName: String) {
    if (!isStyleValid) {
      logW(
        TAG,
        "Style object (accessing $methodName) should not be stored and used after MapView is destroyed or new style has been loaded."
      )
    }
  }

  /**
   * A convenience object to access [the style ID](https://docs.mapbox.com/help/glossary/style-id/) strings of the professionally-designed
   * map styles made by Mapbox.
   */
  companion object {
    private const val TAG = "Mbgl-Style"

    /**
     * Mapbox Standard: A dynamic and performant 3D style that is the default for Mapbox maps.
     */
    const val STANDARD = "mapbox://styles/mapbox/standard"

    /**
     * Mapbox Streets: A complete base map, perfect for incorporating your own data. Using this
     * constant means your map style will always use the latest version and may change as we
     * improve the style.
     */
    const val MAPBOX_STREETS = "mapbox://styles/mapbox/streets-v12"

    /**
     * Outdoors: A general-purpose style tailored to outdoor activities. Using this constant means
     * your map style will always use the latest version and may change as we improve the style.
     */
    const val OUTDOORS = "mapbox://styles/mapbox/outdoors-v12"

    /**
     * Light: Subtle light backdrop for data visualizations. Using this constant means your map
     * style will always use the latest version and may change as we improve the style.
     */
    const val LIGHT = "mapbox://styles/mapbox/light-v11"

    /**
     * Dark: Subtle dark backdrop for data visualizations. Using this constant means your map style
     * will always use the latest version and may change as we improve the style.
     */
    const val DARK = "mapbox://styles/mapbox/dark-v11"

    /**
     * Satellite: A beautiful global satellite and aerial imagery layer. Using this constant means
     * your map style will always use the latest version and may change as we improve the style.
     */
    const val SATELLITE = "mapbox://styles/mapbox/satellite-v9"

    /**
     * Satellite Streets: Global satellite and aerial imagery with unobtrusive labels. Using this
     * constant means your map style will always use the latest version and may change as we
     * improve the style.
     */
    const val SATELLITE_STREETS = "mapbox://styles/mapbox/satellite-streets-v12"

    /**
     * Traffic Day: Color-coded roads based on live traffic congestion data. Traffic data is currently
     * available in [these select countries](https://www.mapbox.com/help/how-directions-work/#traffic-data).
     * Using this constant means your map style will always use the latest version and may change as we improve the style.
     */
    const val TRAFFIC_DAY = "mapbox://styles/mapbox/traffic-day-v2"

    /**
     * Traffic Night: Color-coded roads based on live traffic congestion data, designed to maximize
     * legibility in low-light situations. Traffic data is currently available in
     * [these select countries](https://www.mapbox.com/help/how-directions-work/#traffic-data).
     * Using this constant means your map style will always use the latest version and may change as we improve the style.
     */
    const val TRAFFIC_NIGHT = "mapbox://styles/mapbox/traffic-night-v2"
  }

  /**
   * Callback to be invoked when a style has finished loading.
   */
  fun interface OnStyleLoaded {
    /**
     * Invoked when a style has finished loading.
     *
     * @param style the style that has finished loading
     */
    fun onStyleLoaded(style: Style)
  }
}