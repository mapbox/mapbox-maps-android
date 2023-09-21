package com.mapbox.maps

import androidx.annotation.AnyThread
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import java.util.HashMap

/**
 * Wrapper class for [StyleManager] allowing to expose style related methods for [MapboxMap].
 */
open class MapboxStyleManager internal constructor(
  private val styleManager: StyleManager
) {

  /**
   * Returns the map style's default camera, if any, or a default camera otherwise.
   * The map style's default camera is defined as follows:
   * - [center](https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-center)
   * - [zoom](https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-zoom)
   * - [bearing](https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-bearing)
   * - [pitch](https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-pitch)
   *
   * The style default camera is re-evaluated when a new style is loaded.
   *
   * @return The default [CameraOptions] of the current style in use.
   */
  val styleDefaultCamera: CameraOptions
    get() = styleManager.styleDefaultCamera

  /**
   * Returns the map style's transition options. By default, the style parser will attempt
   * to read the style default transition options, if any, fallback-ing to an immediate transition
   * otherwise. Transition options can be overridden via [setStyleTransition], but the options are
   * reset once a new style has been loaded.
   *
   * The style transition is re-evaluated when a new style is loaded.
   *
   * @return The [TransitionOptions] of the current style in use.
   */
  fun getStyleTransition(): TransitionOptions {
    return styleManager.styleTransition
  }

  /**
   * Overrides the map style's transition options with user-provided options.
   *
   * The style transition is re-evaluated when a new style is loaded.
   *
   * @param transitionOptions The [TransitionOptions].
   */
  fun setStyleTransition(transitionOptions: TransitionOptions) {
    styleManager.styleTransition = transitionOptions
  }

  /**
   * Returns the existing style imports.
   *
   * @return The list containing the information about existing style import objects.
   */
  @MapboxExperimental
  fun getStyleImports(): List<StyleObjectInfo> {
    return styleManager.styleImports
  }

  /**
   * Removes an existing style import.
   *
   * @param importId An identifier of the style import to remove.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @MapboxExperimental
  fun removeStyleImport(importId: String): Expected<String, None> {
    return styleManager.removeStyleImport(importId)
  }

  /**
   * Gets style import schema.
   *
   * @param importId A style import identifier.
   *
   * @return The style import schema or a string describing an error if the operation was not successful.
   */
  @MapboxExperimental
  fun getStyleImportSchema(importId: String): Expected<String, Value> {
    return styleManager.getStyleImportSchema(importId)
  }

  /**
   * Gets style import config.
   *
   * @return The style import configuration or a string describing an error if the operation was not successful.
   */
  @MapboxExperimental
  fun getStyleImportConfigProperties(importId: String): Expected<String, HashMap<String, StylePropertyValue>> {
    return styleManager.getStyleImportConfigProperties(importId)
  }

  /**
   * Gets the value of style import config.
   *
   * @param importId A style import identifier.
   * @param config The style import config name.
   * @return The style import config value.
   */
  @MapboxExperimental
  fun getStyleImportConfigProperty(
    importId: String,
    config: String
  ): Expected<String, StylePropertyValue> {
    return styleManager.getStyleImportConfigProperty(importId, config)
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
  @MapboxExperimental
  fun setStyleImportConfigProperties(
    importId: String,
    configs: HashMap<String, Value>
  ): Expected<String, None> {
    return styleManager.setStyleImportConfigProperties(importId, configs)
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
  @MapboxExperimental
  fun setStyleImportConfigProperty(
    importId: String,
    config: String,
    value: Value
  ): Expected<String, None> {
    return styleManager.setStyleImportConfigProperty(importId, config, value)
  }

  /**
   * Adds a new [style layer](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers).
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * @param properties A map of style layer properties.
   * @param layerPosition If not empty, the new layer will be positioned according to [LayerPosition] parameters.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  fun addStyleLayer(
    properties: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return styleManager.addStyleLayer(properties, layerPosition)
  }

  /**
   * Adds a new [style custom layer](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers).
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * @param layerId A style layer identifier.
   * @param layerHost The [CustomLayerHost].
   * @param layerPosition If not empty, the new layer will be positioned according to [LayerPosition] parameters.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  fun addStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return styleManager.addStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  /**
   * Adds a new [style layer](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers).
   *
   * Whenever a new style is being parsed and currently used style has persistent layers,
   * an engine will try to do following:
   *   - keep the persistent layer at its relative position
   *   - keep the source used by a persistent layer
   *   - keep images added through [addStyleImage] method
   *
   * In cases when a new style has the same layer, source or image resource, style's resources would be
   * used instead and [MapLoadingError] event will be emitted.
   *
   * @param properties A map of style layer properties.
   * @param layerPosition If not empty, the new layer will be positioned according to [LayerPosition] parameters.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  fun addPersistentStyleLayer(
    properties: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return styleManager.addPersistentStyleLayer(properties, layerPosition)
  }

  /**
   * Adds a new [style custom layer](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers).
   *
   * Whenever a new style is being parsed and currently used style has persistent layers,
   * an engine will try to do following:
   *   - keep the persistent layer at its relative position
   *   - keep the source used by a persistent layer
   *   - keep images added through [addStyleImage] method
   *
   * In cases when a new style has the same layer, source or image resource, style's resources would be
   * used instead and [MapLoadingError] event will be emitted.
   *
   * @param layerId A style layer identifier.
   * @param layerHost The [CustomLayerHost].
   * @param layerPosition If not empty, the new layer will be positioned according to [LayerPosition] parameters.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  fun addPersistentStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return styleManager.addPersistentStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  /**
   * Checks if a style layer is persistent.
   *
   * @param layerId A style layer identifier.
   * @return A string describing an error if the operation was not successful, boolean representing state otherwise.
   */
  fun isStyleLayerPersistent(layerId: String): Expected<String, Boolean> {
    return styleManager.isStyleLayerPersistent(layerId)
  }

  /**
   * Removes an existing style layer.
   *
   * @param layerId An identifier of the style layer to remove.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  fun removeStyleLayer(layerId: String): Expected<String, None> {
    return styleManager.removeStyleLayer(layerId)
  }

  /**
   * Moves an existing style layer
   *
   * @param layerId Identifier of the style layer to move.
   * @param layerPosition The layer will be positioned according to the LayerPosition parameters. If an empty LayerPosition
   *                      is provided then the layer is moved to the top of the layer stack.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  fun moveStyleLayer(
    layerId: String,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    return styleManager.moveStyleLayer(layerId, layerPosition)
  }

  /**
   * Checks whether a given style layer exists.
   *
   * @param layerId Style layer identifier.
   *
   * @return A True value if the given style layer exists, False otherwise.
   */
  fun styleLayerExists(layerId: String): Boolean {
    return styleManager.styleLayerExists(layerId)
  }

  /**
   * Returns the existing style layers.
   *
   * @return The list containing the information about existing style layer objects.
   */
  fun getStyleLayers(): List<StyleObjectInfo> {
    return styleManager.styleLayers
  }

  /**
   * Gets the value of style layer property.
   *
   * @param layerId A style layer identifier.
   * @param property The style layer property name.
   * @return The [StylePropertyValue].
   */
  fun getStyleLayerProperty(layerId: String, property: String): StylePropertyValue {
    return styleManager.getStyleLayerProperty(layerId, property)
  }

  /**
   * Sets a value to a style layer property.
   *
   * @param layerId A style layer identifier.
   * @param property The style layer property name.
   * @param value The style layer property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun setStyleLayerProperty(
    layerId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return styleManager.setStyleLayerProperty(layerId, property, value)
  }

  /**
   * Gets style layer properties.
   *
   * @return The style layer properties or a string describing an error if the operation was not successful.
   */
  fun getStyleLayerProperties(layerId: String): Expected<String, Value> {
    return styleManager.getStyleLayerProperties(layerId)
  }

  /**
   * Sets style layer properties.
   * This method can be used to perform batch update for a style layer properties. The structure of a
   * provided [properties] value must conform to a format for a corresponding [layer type](https://docs.mapbox.com/mapbox-gl-js/style-spec/layers/).
   * Modification of a layer [id](https://docs.mapbox.com/mapbox-gl-js/style-spec/layers/#id) and/or a [layer type] (https://docs.mapbox.com/mapbox-gl-js/style-spec/layers/#type) is not allowed.
   *
   * @param layerId A style layer identifier.
   * @param properties A map of style layer properties.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun setStyleLayerProperties(layerId: String, properties: Value): Expected<String, None> {
    return styleManager.setStyleLayerProperties(layerId, properties)
  }

  /**
   * Adds a new [style source](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources).
   * Note: When adding a geojson source, this method does not synchronously parse the GeoJSON data.
   * The events API shall be used to make sure the provided GeoJSON data is valid.
   * In case the GeoJSON is valid, a [MapLoaded] event will be propagated. In case of errors, a [MapLoadingError] event will be propagated instead.
   *
   * @param sourceId An identifier for the style source.
   * @param properties A map of style source properties.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun addStyleSource(sourceId: String, properties: Value): Expected<String, None> {
    return styleManager.addStyleSource(sourceId, properties)
  }

  /**
   * Gets the value of style source property.
   *
   * @param sourceId A style source identifier.
   * @param property The style source property name.
   * @return The [StylePropertyValue] object.
   */
  fun getStyleSourceProperty(sourceId: String, property: String): StylePropertyValue {
    return styleManager.getStyleSourceProperty(sourceId, property)
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
  fun setStyleSourceProperty(
    sourceId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    return styleManager.setStyleSourceProperty(sourceId, property, value)
  }

  /**
   * Gets style source properties.
   *
   * @param sourceId A style source identifier.
   *
   * @return The style source properties or a string describing an error if the operation was not successful.
   */
  fun getStyleSourceProperties(sourceId: String): Expected<String, Value> {
    return styleManager.getStyleSourceProperties(sourceId)
  }

  /**
   * Sets style source properties.
   *
   * This method can be used to perform batch update for a style source properties. The structure of a
   * provided [properties] value must conform to a format for a corresponding [source type](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/).
   * Modification of a source [type](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#type) is not allowed.
   *
   * @param sourceId A style source identifier.
   * @param properties A map of Style source properties.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun setStyleSourceProperties(
    sourceId: String,
    properties: Value
  ): Expected<String, None> {
    return styleManager.setStyleSourceProperties(sourceId, properties)
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
  fun addGeoJSONSourceFeatures(
    sourceId: String,
    dataId: String,
    features: MutableList<Feature>
  ): Expected<String, None> {
    return styleManager.addGeoJSONSourceFeatures(sourceId, dataId, features)
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
  fun updateGeoJSONSourceFeatures(
    sourceId: String,
    dataId: String,
    features: MutableList<Feature>
  ): Expected<String, None> {
    return styleManager.updateGeoJSONSourceFeatures(sourceId, dataId, features)
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
  fun removeGeoJSONSourceFeatures(
    sourceId: String,
    dataId: String,
    featureIds: MutableList<String>
  ): Expected<String, None> {
    return styleManager.removeGeoJSONSourceFeatures(sourceId, dataId, featureIds)
  }

  /**
   * Updates the image of an [image style source](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image).
   *
   * @param sourceId A style source identifier.
   * @param image An [Image].
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun updateStyleImageSourceImage(sourceId: String, image: Image): Expected<String, None> {
    return styleManager.updateStyleImageSourceImage(sourceId, image)
  }

  /**
   * Removes an existing style source.
   *
   * @param sourceId An identifier of the style source to remove.
   */
  fun removeStyleSource(sourceId: String): Expected<String, None> {
    return styleManager.removeStyleSource(sourceId)
  }

  /**
   * Checks whether a given style source exists.
   *
   * @param sourceId A style source identifier.
   *
   * @return True if the given source exists, False otherwise.
   */
  fun styleSourceExists(sourceId: String): Boolean {
    return styleManager.styleSourceExists(sourceId)
  }

  /**
   * Returns the existing style sources.
   *
   * @return The list containing the information about existing style source objects.
   */
  fun getStyleSources(): List<StyleObjectInfo> {
    return styleManager.styleSources
  }

  /**
   * Sets the style global [atmosphere](https://docs.mapbox.com/mapbox-gl-js/style-spec/#fog) properties.
   *
   * @param properties A map of style atmosphere properties values, with their names as a key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun setStyleAtmosphere(properties: Value): Expected<String, None> {
    return styleManager.setStyleAtmosphere(properties)
  }

  /**
   * Gets the value of a style atmosphere property.
   *
   * @param property The style atmosphere property name.
   * @return The style atmosphere [StylePropertyValue].
   */
  fun getStyleAtmosphereProperty(property: String): StylePropertyValue {
    return styleManager.getStyleAtmosphereProperty(property)
  }

  /**
   * Sets a value to the the style atmosphere property.
   *
   * @param property The style atmosphere property name.
   * @param value The style atmosphere property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun setStyleAtmosphereProperty(property: String, value: Value): Expected<String, None> {
    return styleManager.setStyleAtmosphereProperty(property, value)
  }

  /**
   * Sets the style global [terrain](https://docs.mapbox.com/mapbox-gl-js/style-spec/#terrain) properties.
   *
   * @param properties A map of style terrain properties values, with their names as a key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun setStyleTerrain(properties: Value): Expected<String, None> {
    return styleManager.setStyleTerrain(properties)
  }

  /**
   * Gets the value of a style terrain property.
   *
   * @param property The style terrain property name.
   * @return The style terrain property value.
   */
  fun getStyleTerrainProperty(property: String): StylePropertyValue {
    return styleManager.getStyleTerrainProperty(property)
  }

  /**
   * Sets a value to the the style terrain property.
   *
   * @param property The style terrain property name.
   * @param value The style terrain property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun setStyleTerrainProperty(property: String, value: Value): Expected<String, None> {
    return styleManager.setStyleTerrainProperty(property, value)
  }

  /**
   * Sets the map's [projection](https://docs.mapbox.com/mapbox-gl-js/style-spec/projection/). If called with NULL, the map will reset to Mercator.
   *
   * @param properties A map of style projection values, with their names as a key.
   * Supported projections are:
   *  * Mercator
   *  * Globe
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun setStyleProjection(properties: Value): Expected<String, None> {
    return styleManager.setStyleProjection(properties)
  }

  /**
   * Gets the value of a style projection property.
   *
   * @param property The style projection property name.
   * @return The style projection property value.
   */
  fun getStyleProjectionProperty(property: String): StylePropertyValue {
    return styleManager.getStyleProjectionProperty(property)
  }

  /**
   * Sets a value to the the style projection property.
   *
   * @param property The style projection property name.
   * @param value The style projection property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun setStyleProjectionProperty(property: String, value: Value): Expected<String, None> {
    return styleManager.setStyleProjectionProperty(property, value)
  }

  /**
   * Get an image from the style.
   *
   * @param imageId The identifier of the image.
   *
   * @return The image for the given [imageId], or empty if no image is associated with the [imageId].
   */
  fun getStyleImage(imageId: String): Image? {
    return styleManager.getStyleImage(imageId)
  }

  /**
   * Adds an image to be used in the style. This API can also be used for updating
   * an image. If the image for a given [imageId] was already added, it gets replaced by the new image.
   *
   * The image can be used in [`icon-image`](https://www.mapbox.com/mapbox-gl-js/style-spec/#layout-symbol-icon-image),
   * [`fill-pattern`](https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-fill-fill-pattern),
   * [`line-pattern`](https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-line-line-pattern) and
   * [`text-field`](https://www.mapbox.com/mapbox-gl-js/style-spec/#layout-symbol-text-field) properties.
   *
   * @param imageId An identifier of the image.
   * @param scale A scale factor for the image.
   * @param image A pixel data of the image.
   * @param sdf An option to treat whether image is SDF(signed distance field) or not.
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
  fun addStyleImage(
    imageId: String,
    scale: Float,
    image: Image,
    sdf: Boolean,
    stretchX: MutableList<ImageStretches>,
    stretchY: MutableList<ImageStretches>,
    content: ImageContent?
  ): Expected<String, None> {
    return styleManager.addStyleImage(imageId, scale, image, sdf, stretchX, stretchY, content)
  }

  /**
   * Removes an image from the style.
   *
   * @param imageId The identifier of the image to remove.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun removeStyleImage(imageId: String): Expected<String, None> {
    return styleManager.removeStyleImage(imageId)
  }

  /**
   * Checks whether an image exists.
   *
   * @param imageId The identifier of the image.
   *
   * @return True if image exists, false otherwise.
   */
  fun hasStyleImage(imageId: String): Boolean {
    return styleManager.hasStyleImage(imageId)
  }

  /**
   * Adds a custom geometry to be used in the style. To add the data, implement the [CustomGeometrySourceOptions.Builder.fetchTileFunction] callback in the options and call [setStyleCustomGeometrySourceTileData]
   *
   * @param sourceId A style source identifier
   * @param options The [CustomGeometrySourceOptions] for the custom geometry.
   */
  fun addStyleCustomGeometrySource(
    sourceId: String,
    options: CustomGeometrySourceOptions
  ): Expected<String, None> {
    return styleManager.addStyleCustomGeometrySource(sourceId, options)
  }

  /**
   * Set tile data of a custom geometry.
   *
   * @param sourceId A style source identifier.
   * @param tileId A [CanonicalTileID] of the tile.
   * @param featureCollection An array with the features to add.
   */
  fun setStyleCustomGeometrySourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    featureCollection: MutableList<Feature>
  ): Expected<String, None> {
    return styleManager.setStyleCustomGeometrySourceTileData(sourceId, tileId, featureCollection)
  }

  /**
   * Invalidate tile for provided custom geometry source.
   *
   * @param sourceId A style source identifier,.
   * @param tileId A [CanonicalTileID] of the tile.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun invalidateStyleCustomGeometrySourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    return styleManager.invalidateStyleCustomGeometrySourceTile(sourceId, tileId)
  }

  /**
   * Invalidate region for provided custom geometry source.
   *
   * @param sourceId A style source identifier
   * @param bounds A [CoordinateBounds] object.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun invalidateStyleCustomGeometrySourceRegion(
    sourceId: String,
    bounds: CoordinateBounds
  ): Expected<String, None> {
    return styleManager.invalidateStyleCustomGeometrySourceRegion(sourceId, bounds)
  }

  /**
   * Check if the style is completely loaded.
   *
   * Note: The style specified sprite would be marked as loaded even with sprite loading error (An error will be emitted via [MapLoadingError]).
   * Sprite loading error is not fatal and we don't want it to block the map rendering, thus the function will still return True if style and sources are fully loaded.
   *
   * @return True if the style JSON contents, the style specified sprite and sources are all loaded, otherwise returns False.
   *
   */
  fun isStyleLoaded(): Boolean {
    return styleManager.isStyleLoaded
  }

  /**
   * Note! This is an experimental feature. It can be changed or removed in future versions.
   *
   * Adds a custom raster source to be used in the style. To add the data, implement the fetchTileFunction callback in the options and call setStyleCustomRasterSourceTileData()
   *
   * @param sourceId A style source identifier
   * @param options The `custom raster source options` for the custom raster source.
   */
  @MapboxExperimental
  fun addStyleCustomRasterSource(
    sourceId: String,
    options: CustomRasterSourceOptions
  ): Expected<String, None> {
    return styleManager.addStyleCustomRasterSource(sourceId, options)
  }

  /**
   * Note! This is an experimental feature. It can be changed or removed in future versions.
   *
   * Set tile data for a raster tile.
   *
   * @param sourceId A style source identifier.
   * @param tileId A `canonical tile id` of the tile.
   * @param square `Image` content of the tile.
   */
  @MapboxExperimental
  fun setStyleCustomRasterSourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    square: Image
  ): Expected<String, None> {
    return styleManager.setStyleCustomRasterSourceTileData(sourceId, tileId, square)
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
  fun invalidateStyleCustomRasterSourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    return styleManager.invalidateStyleCustomRasterSourceTile(sourceId, tileId)
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
  fun invalidateStyleCustomRasterSourceRegion(
    sourceId: String,
    bounds: CoordinateBounds
  ): Expected<String, None> {
    return styleManager.invalidateStyleCustomRasterSourceRegion(sourceId, bounds)
  }
}