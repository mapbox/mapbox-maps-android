package com.mapbox.maps

import android.graphics.Bitmap
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.extension.style.StyleInterface
import java.nio.ByteBuffer

/**
 * The general class to interact with Styles in the Modular Mapbox Maps SDK for Android.
 * It exposes the entry point for all methods related to the Style.
 * You cannot instantiate [Style] object directly, rather, you must obtain one
 * from the [getStyle(onStyleLoaded)][MapboxMap.getStyle] method on [MapboxMap].
 *
 * Note: Similar to a View object, a [Style] should only be read and modified
 * from the main thread.
 *
 * @property pixelRatio the scale ratio of the style, default the device pixel ratio
 */
class Style internal constructor(
  private var styleManager: StyleManagerInterface,
  override val pixelRatio: Float
) : StyleInterface {

  private var isStyleValid = true

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
  override fun isValid(): Boolean {
    return isStyleValid
  }

  /**
   * Subscribes an [Observer] to a provided list of event types.
   * Observable will hold a strong reference to an Observer instance, therefore,
   * in order to stop receiving notifications, caller must call unsubscribe with an
   * Observer instance used for an initial subscription.
   *
   * @param observer an Observer
   * @param events an array of event types to be subscribed to.
   */
  override fun subscribe(observer: Observer, events: MutableList<String>) {
    checkNativeStyle("subscribe")
    styleManager.subscribe(observer, events)
  }

  /**
   * Unsubscribes an [Observer] from a provided list of event types.
   *
   * @param observer an Observer
   * @param events an array of event types to be unsubscribed from.
   */
  override fun unsubscribe(observer: Observer, events: MutableList<String>) {
    checkNativeStyle("unsubscribe")
    styleManager.unsubscribe(observer, events)
  }

  /**
   * Unsubscribes an [Observer] from all events.
   *
   * @param observer an Observer
   */
  override fun unsubscribe(observer: Observer) {
    checkNativeStyle("unsubscribe")
    styleManager.unsubscribe(observer)
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
  override fun getStyleDefaultCamera(): CameraOptions {
    checkNativeStyle("getStyleDefaultCamera")
    return styleManager.styleDefaultCamera
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
  override fun getStyleTransition(): TransitionOptions {
    checkNativeStyle("getStyleTransition")
    return styleManager.styleTransition
  }

  /**
   * Overrides the map style's transition options with user-provided options.
   *
   * The style transition is re-evaluated when a new style is loaded.
   *
   * @param transitionOptions Map style [transition options][TransitionOptions].
   */
  override fun setStyleTransition(transitionOptions: TransitionOptions) {
    checkNativeStyle("setStyleTransition")
    styleManager.styleTransition = transitionOptions
  }

  /**
   * Get the URI of the current Mapbox Style in use.
   *
   * @return A string containing a Mapbox style URI.
   */
  override fun getStyleURI(): String {
    checkNativeStyle("getStyleURI")
    return styleManager.styleURI
  }

  /**
   * Get the JSON serialization string of the current Mapbox Style in use.
   *
   * @return A JSON string containing a serialized Mapbox Style.
   */
  override fun getStyleJSON(): String {
    checkNativeStyle("getStyleJSON")
    return styleManager.styleJSON
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
  override fun addStyleLayer(parameters: Value, position: LayerPosition?): Expected<String, None> {
    checkNativeStyle("addStyleLayer")
    return styleManager.addStyleLayer(parameters, position)
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
  override fun removeStyleLayer(layerId: String): Expected<String, None> {
    checkNativeStyle("removeStyleLayer")
    return styleManager.removeStyleLayer(layerId)
  }

  /**
   * Moves an existing style layer.
   *
   * @param layerId – Identifier of the style layer to move.
   * @param layerPosition – The layer will be positioned according to the [LayerPosition] parameters. If an empty LayerPosition is provided then the layer is moved to the top of the layerstack.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  override fun moveStyleLayer(layerId: String, layerPosition: LayerPosition?): Expected<String, None> {
    checkNativeStyle("moveStyleLayer")
    return styleManager.moveStyleLayer(layerId, layerPosition)
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
  override fun styleLayerExists(layerId: String): Boolean {
    checkNativeStyle("styleLayerExists")
    return styleManager.styleLayerExists(layerId)
  }

  /**
   * Load style from provided URI.
   *
   * This is an asynchronous call. In order to get result of this operation please use
   * [MapboxMap.addOnStyleLoadedListener], [MapboxMap.addOnStyleDataLoadedListener] or
   * [MapboxMap.addOnMapLoadErrorListener]. In case of successful style load you should get notified
   * by [MapboxMap.addOnStyleLoadedListener].
   *
   * And in case of error @see MapLoadError#StyleLoadError will be generated.
   *
   * \attention This method should be called on the same thread where @see Map object is initialized.
   *
   * @param uri URI where the style should be loaded from.
   */
  override fun setStyleURI(uri: String) {
    checkNativeStyle("setStyleURI")
    styleManager.styleURI = uri
  }

  /**
   * Gets the value of [style layer property][StylePropertyValue].
   *
   * @param layerId Style layer identifier.
   * @param property Style layer property name.
   * @return The property value in the layer with layerId.
   */
  override fun getStyleLayerProperty(layerId: String, property: String): StylePropertyValue {
    checkNativeStyle("getStyleLayerProperty")
    return styleManager.getStyleLayerProperty(layerId, property)
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
  override fun setStyleLayerProperty(
    layerId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    checkNativeStyle("setStyleLayerProperty")
    return styleManager.setStyleLayerProperty(layerId, property, value)
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
  override fun addStyleSource(sourceId: String, properties: Value): Expected<String, None> {
    checkNativeStyle("addStyleSource")
    return styleManager.addStyleSource(sourceId, properties)
  }

  /**
   * Invalidate region for provided custom geometry source.
   *
   * @param sourceId Style source identifier
   * @param coordinateBounds Coordinate bounds.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun invalidateStyleCustomGeometrySourceRegion(
    sourceId: String,
    coordinateBounds: CoordinateBounds
  ): Expected<String, None> {
    checkNativeStyle("invalidateStyleCustomGeometrySourceRegion")
    return styleManager.invalidateStyleCustomGeometrySourceRegion(sourceId, coordinateBounds)
  }

  /**
   * Invalidate tile for provided custom geometry source.
   *
   * @param sourceId Style source identifier
   * @param tileId Identifier of the tile
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun invalidateStyleCustomGeometrySourceTile(
    sourceId: String,
    tileId: CanonicalTileID
  ): Expected<String, None> {
    checkNativeStyle("invalidateStyleCustomGeometrySourceTile")
    return styleManager.invalidateStyleCustomGeometrySourceTile(sourceId, tileId)
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
  override fun updateStyleImageSourceImage(sourceId: String, image: Image): Expected<String, None> {
    checkNativeStyle("updateStyleImageSourceImage")
    return styleManager.updateStyleImageSourceImage(sourceId, image)
  }

  /**
   * Removes an existing style source.
   *
   * @param sourceId Identifier of the style source to remove.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun removeStyleSource(sourceId: String): Expected<String, None> {
    checkNativeStyle("removeStyleSource")
    return styleManager.removeStyleSource(sourceId)
  }

  /**
   * Set tile data of a custom geometry.
   *
   * @param sourceId Style source identifier
   * @param tileId Identifier of the tile
   * @param featureCollection An array with the features to add
   */
  override fun setStyleCustomGeometrySourceTileData(
    sourceId: String,
    tileId: CanonicalTileID,
    featureCollection: MutableList<Feature>
  ): Expected<String, None> {
    checkNativeStyle("setStyleCustomGeometrySourceTileData")
    return styleManager.setStyleCustomGeometrySourceTileData(sourceId, tileId, featureCollection)
  }

  /**
   * Checks whether a given style source exists.
   *
   * @param sourceId Style source identifier.
   *
   * @return True if the given source exists, false otherwise.
   */
  override fun styleSourceExists(sourceId: String): Boolean {
    checkNativeStyle("styleSourceExists")
    return styleManager.styleSourceExists(sourceId)
  }

  /**
   * Sets the style global light source properties.
   *
   * See [Style Specification - Light](https://docs.mapbox.com/mapbox-gl-js/style-spec/#light)
   *
   * @param parameters A map of style light properties values, with their names as key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun setStyleLight(parameters: Value): Expected<String, None> {
    checkNativeStyle("setStyleLight")
    return styleManager.setStyleLight(parameters)
  }

  /**
   * Sets a value to the the style light property.
   *
   * @param id Style light property name.
   * @param light Style light property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun setStyleLightProperty(id: String, light: Value): Expected<String, None> {
    checkNativeStyle("setStyleLightProperty")
    return styleManager.setStyleLightProperty(id, light)
  }

  /**
   * Sets the style global [atmosphere](https://docs.mapbox.com/mapbox-gl-js/style-spec/fog/) properties.
   *
   * @param properties A map of style atmosphere properties values, with their names as a key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun setStyleAtmosphere(properties: Value): Expected<String, None> {
    checkNativeStyle("setStyleAtmosphere")
    return styleManager.setStyleAtmosphere(properties)
  }

  /**
   * Gets the value of a style atmosphere property.
   *
   * @param property The style atmosphere property name.
   * @return The style atmosphere property value.
   */
  override fun getStyleAtmosphereProperty(property: String): StylePropertyValue {
    checkNativeStyle("getStyleAtmosphereProperty")
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
  override fun setStyleAtmosphereProperty(property: String, value: Value): Expected<String, None> {
    checkNativeStyle("setStyleAtmosphereProperty")
    return styleManager.setStyleAtmosphereProperty(property, value)
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
  override fun setStyleTerrain(properties: Value): Expected<String, None> {
    checkNativeStyle("setStyleTerrain")
    return styleManager.setStyleTerrain(properties)
  }

  /**
   * Get the value of a style terrain property.
   *
   * @param property Style terrain property name.
   * @return Style terrain property value.
   */
  override fun getStyleTerrainProperty(property: String): StylePropertyValue {
    checkNativeStyle("getStyleTerrainProperty")
    return styleManager.getStyleTerrainProperty(property)
  }

  /**
   * Sets the value of a style terrain property.
   *
   * @param property Style terrain property name.
   * @param value Style terrain property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun setStyleTerrainProperty(property: String, value: Value): Expected<String, None> {
    checkNativeStyle("setStyleTerrainProperty")
    return styleManager.setStyleTerrainProperty(property, value)
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
  override fun setStyleProjection(properties: Value): Expected<String, None> {
    checkNativeStyle("setStyleProjection")
    return styleManager.setStyleProjection(properties)
  }

  /**
   * Gets the value of a style projection property.
   *
   * @param property The style projection property name.
   * @return The style projection property value.
   */
  override fun getStyleProjectionProperty(property: String): StylePropertyValue {
    checkNativeStyle("getStyleProjectionProperty")
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
  override fun setStyleProjectionProperty(property: String, value: Value): Expected<String, None> {
    checkNativeStyle("setStyleProjectionProperty")
    return styleManager.setStyleProjectionProperty(property, value)
  }

  /**
   * Gets the value of a style light property.
   *
   * @param property Style light property name.
   * @return Style light property value.
   */
  override fun getStyleLightProperty(property: String): StylePropertyValue {
    checkNativeStyle("getStyleLightProperty")
    return styleManager.getStyleLightProperty(property)
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
    return styleManager.addStyleImage(
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
  fun addImage(
    imageId: String,
    image: Image,
    sdf: Boolean
  ): Expected<String, None> = addStyleImage(imageId, pixelRatio, image, sdf, listOf(), listOf(), null)

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
  fun addImage(
    imageId: String,
    bitmap: Bitmap,
    sdf: Boolean
  ): Expected<String, None> {
    val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
    bitmap.copyPixelsToBuffer(byteBuffer)
    return addImage(
      imageId,
      Image(bitmap.width, bitmap.height, byteBuffer.array()),
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
  override fun addImage(
    imageId: String,
    bitmap: Bitmap
  ): Expected<String, None> = addImage(imageId, bitmap, false)

  /**
   * Get an image from the style.
   *
   * @param imageId ID of the image.
   *
   * @return Image data associated with the given ID, or empty if no image is
   * associated with that ID.
   */
  override fun getStyleImage(imageId: String): Image? {
    checkNativeStyle("getStyleImage")
    return styleManager.getStyleImage(imageId)
  }

  /**
   * Removes an image from the style.
   *
   * @param imageId ID of the image to remove.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun removeStyleImage(imageId: String): Expected<String, None> {
    checkNativeStyle("removeStyleImage")
    return styleManager.removeStyleImage(imageId)
  }

  /**
   * Checks whether an image exists.
   *
   * @param imageId The identifier of the image.
   *
   * @return True if image exists, false otherwise.
   */
  override fun hasStyleImage(imageId: String): Boolean {
    checkNativeStyle("hasStyleImage")
    return styleManager.hasStyleImage(imageId)
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
  override fun addStyleModel(modelId: String, modelUri: String): Expected<String, None> {
    checkNativeStyle("addStyleModel")
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
  override fun removeStyleModel(modelId: String): Expected<String, None> {
    checkNativeStyle("removeStyleModel")
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
  override fun hasStyleModel(modelId: String): Boolean {
    checkNativeStyle("hasStyleModel")
    return styleManager.hasStyleModel(modelId)
  }

  /**
   * Gets style layer properties.
   *
   * @param layerId A style layer identifier.
   * @return Style layer metadata or a string describing an error if the operation was not successful.
   */
  override fun getStyleLayerProperties(layerId: String): Expected<String, Value> {
    checkNativeStyle("getStyleLayerProperties")
    return styleManager.getStyleLayerProperties(layerId)
  }

  /**
   * Load the style from a provided JSON string.
   *
   * attention This method should be called on the same thread where Map object is initialized.
   *
   * @param json A JSON string containing a serialized Mapbox Style.
   */
  override fun setStyleJSON(json: String) {
    checkNativeStyle("setStyleJSON")
    styleManager.styleJSON = json
  }

  /**
   * Sets style layer metadata.
   *
   * @param layerId A style layer identifier.
   * @param properties the value wrapper around layer metadata
   * @return a string describing an error if the operation was not successful.
   */
  override fun setStyleLayerProperties(layerId: String, properties: Value): Expected<String, None> {
    checkNativeStyle("setStyleLayerProperties")
    return styleManager.setStyleLayerProperties(layerId, properties)
  }

  /**
   * Gets the value of style source property.
   *
   * @param sourceId Style source identifier.
   * @param property Style source property name.
   * @return The value of property in the source with sourceId.
   */
  override fun getStyleSourceProperty(sourceId: String, property: String): StylePropertyValue {
    // TODO see #1105 issue in internal repo
//    checkNativeStyle("getStyleSourceProperty")
    return styleManager.getStyleSourceProperty(sourceId, property)
  }

  /**
   * Sets a value to a style source property.
   *
   * @param sourceId Style source identifier.
   * @param property Style source property name.
   * @param value Style source property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun setStyleSourceProperty(
    sourceId: String,
    property: String,
    value: Value
  ): Expected<String, None> {
    // TODO see #1105 issue in internal repo
//    checkNativeStyle("setStyleSourceProperty")
    return styleManager.setStyleSourceProperty(sourceId, property, value)
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
  override fun addStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    checkNativeStyle("addStyleCustomLayer")
    return styleManager.addStyleCustomLayer(layerId, layerHost, layerPosition)
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
  override fun addPersistentStyleLayer(
    properties: Value,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    checkNativeStyle("addPersistentStyleLayer")
    return styleManager.addPersistentStyleLayer(properties, layerPosition)
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
  override fun addPersistentStyleCustomLayer(
    layerId: String,
    layerHost: CustomLayerHost,
    layerPosition: LayerPosition?
  ): Expected<String, None> {
    checkNativeStyle("addPersistentStyleCustomLayer")
    return styleManager.addPersistentStyleCustomLayer(layerId, layerHost, layerPosition)
  }

  /**
   * Checks if a style layer is persistent.
   *
   * @param layerId A style layer identifier.
   * @return A string describing an error if the operation was not successful, boolean representing state otherwise.
   */
  override fun isStyleLayerPersistent(layerId: String): Expected<String, Boolean> {
    checkNativeStyle("isStyleLayerPersistent")
    return styleManager.isStyleLayerPersistent(layerId)
  }

  /**
   * Adds a custom geometry to be used in the style. To add the data, implement the [CustomGeometrySourceOptions.fetchTileFunction]
   * callback in the options and call [setStyleCustomGeometrySourceTileData]
   *
   * @param sourceId Style source identifier
   * @param options Settings for the custom geometry
   */
  override fun addStyleCustomGeometrySource(
    sourceId: String,
    options: CustomGeometrySourceOptions
  ): Expected<String, None> {
    checkNativeStyle("addStyleCustomGeometrySource")
    return styleManager.addStyleCustomGeometrySource(sourceId, options)
  }

  /**
   * Returns the existing style sources.
   *
   * @return The list containing the ids of the existing style sources.
   */
  override fun getStyleSources(): List<StyleObjectInfo> {
    checkNativeStyle("getStyleSources")
    return styleManager.styleSources
  }

  /**
   * Returns the current sources metadata.
   *
   * @return The list of source attributions.
   */
  fun getStyleSourcesAttribution(): List<String> {
    checkNativeStyle("getStyleSourcesAttribution")
    val sources = styleSources
    val sourceAttributions = mutableListOf<String>()
    for (sourceId in sources) {
      val sourceParameters = getStyleSourceProperties(sourceId.id)
      if (sourceParameters.isValue) {
        val parameterMap = sourceParameters.value?.contents as HashMap<String, Value>
        sourceAttributions.add(parameterMap["attribution"].toString())
      }
    }
    return sourceAttributions
  }

  /**
   * Returns the existing style layers.
   *
   * @return The list containing the ids of the existing style layers.
   */
  override fun getStyleLayers(): List<StyleObjectInfo> {
    checkNativeStyle("getStyleLayers")
    return styleManager.styleLayers
  }

  /**
   * Gets style source parameters.
   * In order to convert returned value to a json string please take a look at [Value.toJson].
   *
   * @param sourceId Style source identifier.
   *
   * @return Style source parameters or a string describing an error if the operation was not successful.
   */
  override fun getStyleSourceProperties(sourceId: String): Expected<String, Value> {
    checkNativeStyle("getStyleSourceProperties")
    return styleManager.getStyleSourceProperties(sourceId)
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
  override fun setStyleSourceProperties(
    sourceId: String,
    properties: Value
  ): Expected<String, None> {
    checkNativeStyle("setStyleSourceProperties")
    return styleManager.setStyleSourceProperties(sourceId, properties)
  }

  /**
   * Check if the style is completely loaded.
   *
   * @return TRUE if and only if the style JSON contents, the style specified sprite and sources are all loaded, otherwise returns FALSE.
   */
  override fun isStyleLoaded(): Boolean {
    checkNativeStyle("isStyleLoaded")
    return styleManager.isStyleLoaded
  }

  private fun checkNativeStyle(methodName: String) {
    ThreadChecker.throwIfNotMainThread()
    if (!isStyleValid) {
      logW(TAG, "Style object (accessing $methodName) should not be stored and used after MapView is destroyed or new style has been loaded.")
    }
  }

  /**
   * A convenience object to access [the style ID](https://docs.mapbox.com/help/glossary/style-id/) strings of the professionally-designed
   * map styles made by Mapbox.
   */
  companion object {
    private const val TAG = "Mbgl-Style"

    /**
     * Mapbox Streets: A complete base map, perfect for incorporating your own data. Using this
     * constant means your map style will always use the latest version and may change as we
     * improve the style.
     */
    const val MAPBOX_STREETS = "mapbox://styles/mapbox/streets-v11"

    /**
     * Outdoors: A general-purpose style tailored to outdoor activities. Using this constant means
     * your map style will always use the latest version and may change as we improve the style.
     */
    const val OUTDOORS = "mapbox://styles/mapbox/outdoors-v11"

    /**
     * Light: Subtle light backdrop for data visualizations. Using this constant means your map
     * style will always use the latest version and may change as we improve the style.
     */
    const val LIGHT = "mapbox://styles/mapbox/light-v10"

    /**
     * Dark: Subtle dark backdrop for data visualizations. Using this constant means your map style
     * will always use the latest version and may change as we improve the style.
     */
    const val DARK = "mapbox://styles/mapbox/dark-v10"

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
    const val SATELLITE_STREETS = "mapbox://styles/mapbox/satellite-streets-v11"

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