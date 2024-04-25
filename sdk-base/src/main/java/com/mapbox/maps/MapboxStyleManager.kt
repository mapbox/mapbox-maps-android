package com.mapbox.maps

import android.graphics.Bitmap
import androidx.annotation.AnyThread
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature

/**
 * Wrapper class for [StyleManager] allowing to expose style related methods for MapboxMap.
 */
open class MapboxStyleManager @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX) constructor(
  /**
   * Native style manager instance.
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  val styleManager: StyleManager,
  /**
   * Current pixel ratio.
   */
  val pixelRatio: Float,
  /**
   * For internal usage.
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  val mapLoadingErrorDelegate: MapLoadingErrorDelegate,
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
  open val styleDefaultCamera: CameraOptions
    @CallSuper
    @MainThread
    get() {
      ThreadChecker.throwIfNotMainThread()
      return styleManager.styleDefaultCamera
    }

  /**
   * Get the URI of the current style in use.
   *
   * @return A string containing a style URI.
   */
  open val styleURI: String
    @CallSuper
    @MainThread
    get() {
      ThreadChecker.throwIfNotMainThread()
      return styleManager.styleURI
    }

  /**
   * Get the JSON serialization string of the current style in use.
   *
   * @return A JSON string containing a serialized style.
   */
  open val styleJSON: String
    @CallSuper
    @MainThread
    get() {
      ThreadChecker.throwIfNotMainThread()
      return styleManager.styleJSON
    }

  /**
   * Returns the existing style layers.
   *
   * @return The list containing the information about existing style layer objects.
   */
  open val styleLayers: List<StyleObjectInfo>
    @CallSuper
    @MainThread
    get() {
      ThreadChecker.throwIfNotMainThread()
      return styleManager.styleLayers
    }

  /**
   * Returns the existing style sources.
   *
   * @return The list containing the information about existing style source objects.
   */
  open val styleSources: List<StyleObjectInfo>
    @CallSuper
    @MainThread
    get() {
      ThreadChecker.throwIfNotMainThread()
      return styleManager.styleSources
    }

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
  @CallSuper
  @MainThread
  open fun getStyleTransition(): TransitionOptions {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.styleTransition
  }

  /**
   * Overrides the map style's transition options with user-provided options.
   *
   * The style transition is re-evaluated when a new style is loaded.
   *
   * @param transitionOptions The [TransitionOptions].
   */
  @CallSuper
  @MainThread
  open fun setStyleTransition(transitionOptions: TransitionOptions) {
    ThreadChecker.throwIfNotMainThread()
    styleManager.styleTransition = transitionOptions
  }

  /**
   * Returns the existing style imports.
   *
   * @return The list containing the information about existing style import objects.
   */
  @CallSuper
  @MainThread
  open fun getStyleImports(): List<StyleObjectInfo> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.styleImports
  }

  /**
   * Removes an existing style import.
   *
   * @param importId An identifier of the style import to remove.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun removeStyleImport(importId: String): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.removeStyleImport(importId)
  }

  /**
   * Gets style import schema.
   *
   * @param importId A style import identifier.
   *
   * @return The style import schema or a string describing an error if the operation was not successful.
   */
  @CallSuper
  @MainThread
  open fun getStyleImportSchema(importId: String): Expected<String, Value> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.getStyleImportSchema(importId)
  }

  /**
   * Gets style import config.
   *
   * @return The style import configuration or a string describing an error if the operation was not successful.
   */
  @CallSuper
  @MainThread
  open fun getStyleImportConfigProperties(importId: String): Expected<String, HashMap<String, StylePropertyValue>> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.getStyleImportConfigProperties(importId)
  }

  /**
   * Gets the value of style import config.
   *
   * @param importId A style import identifier.
   * @param config The style import config name.
   * @return The style import config value.
   */
  @CallSuper
  @MainThread
  open fun getStyleImportConfigProperty(
    importId: String,
    config: String
  ): Expected<String, StylePropertyValue> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleImportConfigProperties(
    importId: String,
    configs: HashMap<String, Value>
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleImportConfigProperty(
    importId: String,
    config: String,
    value: Value
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleImportConfigProperty(importId, config, value)
  }

  /**
   * Adds new import to current style, loaded from a JSON string.
   *
   * @param importId Identifier of import to update.
   * @param json The JSON string to be loaded directly as the import.
   * @param config A map containing the configuration options of the import.
   * @param importPosition The import will be positioned according to the ImportPosition parameters. If not specified, then the import is moved to the top of the import stack.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun addStyleImportFromJSON(
    importId: String,
    json: String,
    config: HashMap<String, Value>?,
    importPosition: ImportPosition?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.addStyleImportFromJSON(importId, json, config, importPosition)
  }

  /**
   * Adds new import to current style, loaded from an URI.
   *
   * @param importId Identifier of import to update.
   * @param uri URI of the import.
   * @param config A map containing the configuration options of the import.
   * @param importPosition The import will be positioned according to the ImportPosition parameters. If not specified, then the import is moved to the top of the import stack.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun addStyleImportFromURI(
    importId: String,
    uri: String,
    config: HashMap<String, Value>?,
    importPosition: ImportPosition?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.addStyleImportFromURI(importId, uri, config, importPosition)
  }

  /**
   * Updates an existing import in the style.
   * The function replaces the content of the import, with the content loaded from the provided data value.
   * The configuration values of the import are merged with the configuration provided in the update.
   *
   * @param importId Identifier of import to update.
   * @param json The JSON string to be loaded directly as the import.
   * @param config A map containing the configuration options of the import.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun updateStyleImportWithJSON(
    importId: String,
    json: String,
    config: HashMap<String, Value>?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.updateStyleImportWithJSON(
      importId, json, config
    )
  }

  /**
   * Updates an existing import in the style.
   * The function replaces the content of the import, with the content loaded from the provided URI.
   * The configuration values of the import are merged with the configuration provided in the update.
   *
   * @param importId Identifier of import to update.
   * @param uri URI of the import.
   * @param config A map containing the configuration options of the import.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun updateStyleImportWithURI(
    importId: String,
    uri: String,
    config: HashMap<String, Value>?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.updateStyleImportWithURI(importId, uri, config)
  }

  /**
   * Moves import to position before another import, specified with `beforeId`. Order of imported styles corresponds to order of their layers.
   *
   * @param importId Identifier of import to move.
   * @param importPosition The import will be positioned according to the ImportPosition parameters. If not specified, then the import is moved to the top of the import stack.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun moveStyleImport(
    importId: String,
    importPosition: ImportPosition?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.moveStyleImport(importId, importPosition)
  }

  /**
   * Adds a new [style layer](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers).
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * @param parameters A map of style layer properties.
   * @param position If not empty, the new layer will be positioned according to [LayerPosition] parameters.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun addStyleLayer(
    parameters: Value,
    position: LayerPosition?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.addStyleLayer(parameters, position)
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
  @CallSuper
  @MainThread
  open fun addStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun addPersistentStyleLayer(
    properties: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun addPersistentStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.addPersistentStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  /**
   * Checks if a style layer is persistent.
   *
   * @param layerId A style layer identifier.
   * @return A string describing an error if the operation was not successful, boolean representing state otherwise.
   */
  @CallSuper
  @MainThread
  open fun isStyleLayerPersistent(layerId: String): Expected<String, Boolean> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.isStyleLayerPersistent(layerId)
  }

  /**
   * Removes an existing style layer.
   *
   * @param layerId An identifier of the style layer to remove.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun removeStyleLayer(layerId: String): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun moveStyleLayer(
    layerId: String,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.moveStyleLayer(layerId, layerPosition)
  }

  /**
   * Checks whether a given style layer exists.
   *
   * @param layerId Style layer identifier.
   *
   * @return A True value if the given style layer exists, False otherwise.
   */
  @CallSuper
  @MainThread
  open fun styleLayerExists(layerId: String): Boolean {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.styleLayerExists(layerId)
  }

  /**
   * Gets the value of style layer property.
   *
   * @param layerId A style layer identifier.
   * @param property The style layer property name.
   * @return The [StylePropertyValue].
   */
  @CallSuper
  @MainThread
  open fun getStyleLayerProperty(layerId: String, property: String): StylePropertyValue {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleLayerProperty(
    layerId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleLayerProperty(layerId, property, value)
  }

  /**
   * Gets style layer properties.
   *
   * @return The style layer properties or a string describing an error if the operation was not successful.
   */
  @CallSuper
  @MainThread
  open fun getStyleLayerProperties(layerId: String): Expected<String, Value> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleLayerProperties(layerId: String, properties: Value): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun addStyleSource(sourceId: String, properties: Value): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.addStyleSource(sourceId, properties)
  }

  /**
   * Gets the value of style source property.
   *
   * @param sourceId A style source identifier.
   * @param property The style source property name.
   * @return The [StylePropertyValue] object.
   */
  @CallSuper
  @MainThread
  open fun getStyleSourceProperty(sourceId: String, property: String): StylePropertyValue {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleSourceProperty(
    sourceId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleSourceProperty(sourceId, property, value)
  }

  /**
   * Gets style source properties.
   *
   * @param sourceId A style source identifier.
   *
   * @return The style source properties or a string describing an error if the operation was not successful.
   */
  @CallSuper
  @MainThread
  open fun getStyleSourceProperties(sourceId: String): Expected<String, Value> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleSourceProperties(
    sourceId: String,
    properties: Value
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @AnyThread
  open fun addGeoJSONSourceFeatures(
    sourceId: String,
    dataId: String,
    features: List<Feature>
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
  @CallSuper
  @AnyThread
  open fun updateGeoJSONSourceFeatures(
    sourceId: String,
    dataId: String,
    features: List<Feature>
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
  @CallSuper
  @AnyThread
  open fun removeGeoJSONSourceFeatures(
    sourceId: String,
    dataId: String,
    featureIds: List<String>
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
  @CallSuper
  @MainThread
  open fun updateStyleImageSourceImage(sourceId: String, image: Image): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.updateStyleImageSourceImage(sourceId, image)
  }

  /**
   * Removes an existing style source.
   *
   * @param sourceId An identifier of the style source to remove.
   */
  @CallSuper
  @MainThread
  open fun removeStyleSource(sourceId: String): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.removeStyleSource(sourceId)
  }

  /**
   * Removes an existing style source without checking if it's being used by any other component.
   *
   * @param sourceId An identifier of the style source to remove.
   */
  @CallSuper
  @MainThread
  @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  open fun removeStyleSourceUnchecked(sourceId: String): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.removeStyleSourceUnchecked(sourceId)
  }

  /**
   * Checks whether a given style source exists.
   *
   * @param sourceId A style source identifier.
   *
   * @return True if the given source exists, False otherwise.
   */
  @CallSuper
  @MainThread
  open fun styleSourceExists(sourceId: String): Boolean {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.styleSourceExists(sourceId)
  }

  /**
   * Sets the style global [atmosphere](https://docs.mapbox.com/mapbox-gl-js/style-spec/#fog) properties.
   *
   * @param properties A map of style atmosphere properties values, with their names as a key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun setStyleAtmosphere(properties: Value): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleAtmosphere(properties)
  }

  /**
   * Gets the value of a style atmosphere property.
   *
   * @param property The style atmosphere property name.
   * @return The style atmosphere [StylePropertyValue].
   */
  @CallSuper
  @MainThread
  open fun getStyleAtmosphereProperty(property: String): StylePropertyValue {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleAtmosphereProperty(property: String, value: Value): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleAtmosphereProperty(property, value)
  }

  /**
   * Sets the style global [terrain](https://docs.mapbox.com/mapbox-gl-js/style-spec/#terrain) properties.
   *
   * @param properties A map of style terrain properties values, with their names as a key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun setStyleTerrain(properties: Value): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleTerrain(properties)
  }

  /**
   * Gets the value of a style terrain property.
   *
   * @param property The style terrain property name.
   * @return The style terrain property value.
   */
  @CallSuper
  @MainThread
  open fun getStyleTerrainProperty(property: String): StylePropertyValue {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleTerrainProperty(property: String, value: Value): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleProjection(properties: Value): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleProjection(properties)
  }

  /**
   * Gets the value of a style projection property.
   *
   * @param property The style projection property name.
   * @return The style projection property value.
   */
  @CallSuper
  @MainThread
  open fun getStyleProjectionProperty(property: String): StylePropertyValue {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun setStyleProjectionProperty(property: String, value: Value): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleProjectionProperty(property, value)
  }

  /**
   * Get an image from the style.
   *
   * @param imageId The identifier of the image.
   *
   * @return The image for the given [imageId], or empty if no image is associated with the [imageId].
   */
  @CallSuper
  @MainThread
  open fun getStyleImage(imageId: String): Image? {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun addStyleImage(
    imageId: String,
    scale: Float,
    image: Image,
    sdf: Boolean,
    stretchX: List<ImageStretches>,
    stretchY: List<ImageStretches>,
    content: ImageContent?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.addStyleImage(imageId, scale, image, sdf, stretchX, stretchY, content)
  }

  /**
   * Removes an image from the style.
   *
   * @param imageId The identifier of the image to remove.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun removeStyleImage(imageId: String): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.removeStyleImage(imageId)
  }

  /**
   * Checks whether an image exists.
   *
   * @param imageId The identifier of the image.
   *
   * @return True if image exists, false otherwise.
   */
  @CallSuper
  @MainThread
  open fun hasStyleImage(imageId: String): Boolean {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.hasStyleImage(imageId)
  }

  /**
   * Adds a custom geometry to be used in the style. To add the data, implement the [CustomGeometrySourceOptions.Builder.fetchTile@CallSuper
  @MainThread
  open function] callback in the options and call [setStyleCustomGeometrySourceTileData]
   *
   * @param sourceId A style source identifier
   * @param options The [CustomGeometrySourceOptions] for the custom geometry.
   */
  @CallSuper
  @MainThread
  open fun addStyleCustomGeometrySource(
    sourceId: String,
    options: CustomGeometrySourceOptions
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.addStyleCustomGeometrySource(sourceId, options)
  }

  /**
   * Set tile data of a custom geometry.
   *
   * @param sourceId A style source identifier.
   * @param tileId A [CanonicalTileID] of the tile.
   * @param featureCollection An array with the features to add.
   */
  @CallSuper
  @MainThread
  open fun setStyleCustomGeometrySourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    featureCollection: MutableList<Feature>
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun invalidateStyleCustomGeometrySourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.invalidateStyleCustomGeometrySourceTile(sourceId, tileId)
  }

  /**
   * Invalidate region for provided custom geometry source.
   *
   * @param sourceId A style source identifier
   * @param coordinateBounds A [CoordinateBounds] object.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun invalidateStyleCustomGeometrySourceRegion(
    sourceId: String,
    coordinateBounds: CoordinateBounds
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.invalidateStyleCustomGeometrySourceRegion(sourceId, coordinateBounds)
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
  @CallSuper
  @MainThread
  open fun isStyleLoaded(): Boolean {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun addStyleCustomRasterSource(
    sourceId: String,
    options: CustomRasterSourceOptions
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.addStyleCustomRasterSource(sourceId, options)
  }

  /**
   * Note! This is an experimental feature. It can be changed or removed in future versions.
   *
   * Set tile data for a raster tile.
   *
   * By default, the provided data is not cached, and the implementation will call the fetch callback each time the tile reappears.
   * Use the MapboxMap.setTileCacheBudget API to establish an internal cache for the source.
   *
   * @param sourceId A style source identifier.
   * @param tileId A `canonical tile id` of the tile.
   * @param image `Image` content of the tile. If an empty image is provided then the tile gets removed from the map.
   */
  @MapboxExperimental
  @CallSuper
  @MainThread
  open fun setStyleCustomRasterSourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    image: Image?
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleCustomRasterSourceTileData(sourceId, tileId, image)
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
  @CallSuper
  @MainThread
  open fun invalidateStyleCustomRasterSourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
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
  @CallSuper
  @MainThread
  open fun invalidateStyleCustomRasterSourceRegion(
    sourceId: String,
    bounds: CoordinateBounds
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.invalidateStyleCustomRasterSourceRegion(sourceId, bounds)
  }

  /**
   * Gets the value of the style light property.
   *
   * @param id light id.
   * @param property light property name.
   *
   * @return The value of property in the light.
   */
  @CallSuper
  @MainThread
  open fun getStyleLightProperty(id: String, property: String): StylePropertyValue {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.getStyleLightProperty(id, property)
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
  @CallSuper
  @MainThread
  open fun setStyleLightProperty(
    id: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleLightProperty(id, property, value)
  }

  /**
   * Gets added lights to the style.
   *
   * @return list of [StyleObjectInfo].
   */
  @CallSuper
  @MainThread
  open fun getStyleLights(): MutableList<StyleObjectInfo> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.styleLights
  }

  /**
   * Sets lights to the style.
   *
   * @param lights light value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @CallSuper
  @MainThread
  open fun setStyleLights(lights: Value): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.setStyleLights(lights)
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
  @CallSuper
  @MainThread
  open fun addStyleModel(modelId: String, modelUri: String): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.addStyleModel(modelId, modelUri)
  }

  /**
   * Removes a model from the style.
   *
   * @param modelId The identifier of the model to remove.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @MapboxExperimental
  @CallSuper
  @MainThread
  open fun removeStyleModel(modelId: String): Expected<String, None> {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.removeStyleModel(modelId)
  }

  /**
   * Checks whether a model exists.
   *
   * @param modelId The identifier of the model.
   *
   * @return True if model exists, false otherwise.
   */
  @MapboxExperimental
  @CallSuper
  @MainThread
  open fun hasStyleModel(modelId: String): Boolean {
    ThreadChecker.throwIfNotMainThread()
    return styleManager.hasStyleModel(modelId)
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
  @CallSuper
  @AnyThread
  open fun setStyleGeoJSONSourceData(
    sourceId: String,
    dataId: String,
    data: GeoJSONSourceData
  ): Expected<String, None> {
    return styleManager.setStyleGeoJSONSourceData(sourceId, dataId, data)
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
   * @param image Pixel data of the image.
   * @param sdf Option to treat whether image is SDF(signed distance field) or not.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @CallSuper
  @MainThread
  fun addImage(
    imageId: String,
    image: Image,
    sdf: Boolean
  ): Expected<String, None> =
    addStyleImage(imageId, pixelRatio, image, sdf, listOf(), listOf(), null)

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
   * @param image Pixel data of the image.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @CallSuper
  @MainThread
  fun addImage(
    imageId: String,
    image: Image
  ): Expected<String, None> = addImage(imageId, image, false)

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
   * @param bitmap The bitmap image.
   * @param sdf Option to treat whether image is SDF(signed distance field) or not.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @CallSuper
  @MainThread
  fun addImage(
    imageId: String,
    bitmap: Bitmap,
    sdf: Boolean
  ): Expected<String, None> {
    return addImage(
      imageId,
      bitmap.toMapboxImage(),
      sdf
    )
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
   * @param bitmap The bitmap image.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  @CallSuper
  @MainThread
  fun addImage(
    imageId: String,
    bitmap: Bitmap
  ): Expected<String, None> = addImage(imageId, bitmap, false)
}