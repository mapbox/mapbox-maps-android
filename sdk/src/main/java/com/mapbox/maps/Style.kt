package com.mapbox.maps

import android.graphics.Bitmap
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import java.lang.ref.WeakReference
import java.nio.ByteBuffer

/**
 * The general class to interact with Styles in the Modular Mapbox Maps SDK for Android.
 * It exposes the entry point for all methods related to the Style.
 * You cannot instantiate [Style] object directly, rather, you must obtain one
 * from the getStyleAsync() method on [MapboxMap].
 * <p>
 * Note: Similar to a View object, a [Style] should only be read and modified
 * from the main thread.
 * </p>
 *
 * @property pixelRatio the scale ratio of the style, default the device pixel ratio
 */
class Style internal constructor(
  styleManager: StyleManagerInterface,
  private val pixelRatio: Float
) : StyleManagerInterface {
  private val styleManagerRef = WeakReference(styleManager)

  /**
   * Subscribes an Observer to a provided list of event types.
   * Observable will hold a strong reference to an Observer instance, therefore,
   * in order to stop receiving notifications, caller must call unsubscribe with an
   * Observer instance used for an initial subscription.
   *
   * @param observer an Observer
   * @param events an array of event types to be subscribed to.
   */
  override fun subscribe(observer: Observer, events: MutableList<String>) {
    styleManagerRef.call { this.subscribe(observer, events) }
  }

  /**
   * Unsubscribes an Observer from a provided list of event types.
   *
   * @param observer an Observer
   * @param events an array of event types to be unsubscribed from.
   */
  override fun unsubscribe(observer: Observer, events: MutableList<String>) {
    styleManagerRef.call { this.unsubscribe(observer, events) }
  }

  /**
   * Unsubscribes an Observer from all events.
   *
   * @param observer an Observer
   */
  override fun unsubscribe(observer: Observer) {
    styleManagerRef.call { this.unsubscribe(observer) }
  }

  /**
   * Returns the map style's default camera, if any, or a default camera otherwise.
   * The map style default camera is defined as follows:
   * - center: https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-center
   * - zoom: https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-zoom
   * - bearing: https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-bearing
   * - pitch: https://docs.mapbox.com/mapbox-gl-js/style-spec/#root-pitch
   *
   * The style default camera is re-evaluated when a new style is loaded.
   *
   * @return Returns the map style default camera.
   */
  override fun getStyleDefaultCamera(): CameraOptions =
    styleManagerRef.call { this.styleDefaultCamera }

  /**
   * Returns the map style's transition options. By default, the style parser will attempt
   * to read the style default transition options, if any, fallbacking to an immediate transition
   * otherwise. Transition options can be overriden via setStyleTransition, but the options are
   * reset once a new style has been loaded.
   *
   * The style transition is re-evaluated when a new style is loaded.
   *
   * @return Returns the map style transition options.
   */
  override fun getStyleTransition(): TransitionOptions =
    styleManagerRef.call { this.styleTransition }

  /**
   * Overrides the map style's transition options with user-provided options.
   *
   * The style transition is re-evaluated when a new style is loaded.
   *
   * @param transitionOptions Map style transition options.
   */
  override fun setStyleTransition(transitionOptions: TransitionOptions) =
    styleManagerRef.call { this.styleTransition = transitionOptions }

  /**
   * Get the URI of the current Mapbox Style in use.
   *
   * @return A string containing a Mapbox style URI.
   */
  override fun getStyleURI() = styleManagerRef.call { this.styleURI }

  /**
   * Get the JSON serialization string of the current Mapbox Style in use.
   *
   * @return A JSON string containing a serialized Mapbox Style.
   */
  override fun getStyleJSON() = styleManagerRef.call { this.styleJSON }

  /**
   * Adds a new style layer.
   *
   * See [https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers)
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * @param parameters A map of style layer parameters.
   * @param position If not empty, the new layer will be added immediately below the layer with this id.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  override fun addStyleLayer(parameters: Value, position: LayerPosition?): Expected<Void, String> =
    styleManagerRef.call { this.addStyleLayer(parameters, position) }

  /**
   * Removes an existing style layer
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * @param layerId Identifier of the style layer to remove.
   *
   * @return A string describing an error if the operation was not successful, or empty otherwise.
   */
  override fun removeStyleLayer(layerId: String): Expected<Void, String> =
    styleManagerRef.call { this.removeStyleLayer(layerId) }

  /**
   * Checks whether a given style layer exists.
   *
   * Runtime style layers are valid until they are either removed or a new style is loaded.
   *
   * @param layerId Style layer identifier.
   *
   * @return True if the given style layer exists, false otherwise.
   */
  override fun styleLayerExists(layerId: String): Boolean =
    styleManagerRef.call { this.styleLayerExists(layerId) }

  /**
   * Load style from provided URI.
   *
   * This is an asynchronious call. In order to get result of this operation please use @see MapObserver#onMapChanged
   * or @see MapObserver#onMapLoadError. In case of successfull style load you should get @see MapChange#DidFinishLoadingStyle.
   * And in case of error @see MapLoadError#StyleLoadError will be generated.
   *
   * \attention This method should be called on the same thread where @see Map object is initialized.
   *
   * @param uri URI where the style should be loaded from.
   */
  override fun setStyleURI(uri: String) {
    styleManagerRef.call {
      this.styleURI = uri
    }
  }

  /**
   * Gets the value of style layer \p property.
   *
   * @param layerId Style layer identified.
   * @param property Style layer property name.
   * @return The value of \p property in the layer with \p layerId.
   */
  override fun getStyleLayerProperty(layerId: String, property: String): StylePropertyValue =
    styleManagerRef.call { this.getStyleLayerProperty(layerId, property) }

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
  ): Expected<Void, String> =
    styleManagerRef.call { this.setStyleLayerProperty(layerId, property, value) }

  /**
   * Adds a new style source.
   *
   * See [https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources)
   *
   * @param sourceId An identifier for the style source.
   * @param properties A map of style source parameters.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun addStyleSource(sourceId: String, properties: Value): Expected<Void, String> =
    styleManagerRef.call {
      this.addStyleSource(sourceId, properties)
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
  ): Expected<Void, String> =
    styleManagerRef.call { this.invalidateStyleCustomGeometrySourceRegion(sourceId, coordinateBounds) }

  /**
   * Check if the style is completely loaded.
   *
   * @return TRUE if and only if the style JSON contents, the style specified sprite and sources are all loaded, otherwise returns FALSE.
   */
  override fun isStyleFullyLoaded(): Boolean {
    return styleManagerRef.call { this.isStyleFullyLoaded }
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
  ): Expected<Void, String> =
    styleManagerRef.call { this.invalidateStyleCustomGeometrySourceTile(sourceId, tileId) }

  /**
   * Updates the image of an image style source.
   *
   * See [https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image)
   *
   * @param sourceId Style source identifier.
   * @param image Pixel data of the image.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun updateStyleImageSourceImage(sourceId: String, image: Image): Expected<Void, String> =
    styleManagerRef.call {
      this.updateStyleImageSourceImage(sourceId, image)
    }

  /**
   * Removes an existing style source.
   *
   * @param sourceId Identifier of the style source to remove.
   */
  override fun removeStyleSource(sourceId: String): Expected<Void, String> = styleManagerRef.call {
    this.removeStyleSource(sourceId)
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
  ): Expected<Void, String> {
    return styleManagerRef.call {
      this.setStyleCustomGeometrySourceTileData(sourceId, tileId, featureCollection)
    }
  }

  /**
   * Checks whether a given style source exists.
   *
   * @param sourceId Style source identifier.
   *
   * @return True if the given source exists, false otherwise.
   */
  override fun styleSourceExists(sourceId: String): Boolean = styleManagerRef.call {
    this.styleSourceExists(sourceId)
  }

  /**
   * Sets the style global light source properties.
   *
   * See [https://docs.mapbox.com/mapbox-gl-js/style-spec/#light](https://docs.mapbox.com/mapbox-gl-js/style-spec/#light)
   *
   * @param parameters A map of style light properties values, with their names as key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun setStyleLight(parameters: Value): Expected<Void, String> = styleManagerRef.call {
    this.setStyleLight(parameters)
  }

  /**
   * Sets a value to the the style light property.
   *
   * @param id Style light property name.
   * @param light Style light property value.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun setStyleLightProperty(id: String, light: Value): Expected<Void, String> =
    styleManagerRef.call {
      this.setStyleLightProperty(id, light)
    }

  /**
   * Sets the style global terrain source properties.
   *
   * @see <a href=https://docs.mapbox.com/mapbox-gl-js/style-spec/#terrain>Mapbox Style Specification: Terrrain</a>
   *
   * @param properties A map of style terrain properties values, with their names as key.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun setStyleTerrain(properties: Value): Expected<Void, String> = styleManagerRef.call {
    this.setStyleTerrain(properties)
  }

  /**
   * Get the value of a style terrain property.
   *
   * @param property Style terrain property name.
   * @return Style terrain property value.
   */
  override fun getStyleTerrainProperty(property: String): StylePropertyValue =
    styleManagerRef.call {
      this.getStyleTerrainProperty(property)
    }

  /**
   * Gets the value of a style terrain property.
   *
   * @param property Style terrain property name.
   * @return Style terrain property value.
   */
  override fun setStyleTerrainProperty(property: String, value: Value): Expected<Void, String> =
    styleManagerRef.call {
      this.setStyleTerrainProperty(property, value)
    }

  /**
   * Gets the value of a style light property.
   *
   * @param property Style light property name.
   * @return Style light property value.
   */
  override fun getStyleLightProperty(property: String): StylePropertyValue = styleManagerRef.call {
    this.getStyleLightProperty(property)
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
  ): Expected<Void, String> =
    styleManagerRef.call {
      this.addStyleImage(
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
  ): Expected<Void, String> = addStyleImage(imageId, pixelRatio, image, sdf, listOf(), listOf(), null)

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
  ): Expected<Void, String> = addImage(imageId, image, false)

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
  ): Expected<Void, String> {
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
  fun addImage(
    imageId: String,
    bitmap: Bitmap
  ): Expected<Void, String> = addImage(imageId, bitmap, false)

  /**
   * Get an image from the style.
   *
   * @param imageId ID of the image.
   *
   * @return Image data associated with the given ID, or empty if no image is
   * associated with that ID.
   */
  override fun getStyleImage(imageId: String): Image? =
    styleManagerRef.call { this.getStyleImage(imageId) }

  /**
   * Removes an image from the style.
   *
   * @param imageId ID of the image to remove.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  override fun removeStyleImage(imageId: String): Expected<Void, String> =
    styleManagerRef.call { this.removeStyleImage(imageId) }

  /**
   * Gets style layer properties.
   *
   * @param layerId Optional filter to specify specific layer type for query.
   * @return Style layer metadata or a string describing an error if the operation was not successful.
   */
  override fun getStyleLayerProperties(layerId: String): Expected<Value, String> {
    return styleManagerRef.call { this.getStyleLayerProperties(layerId) }
  }

  /**
   * Load the style from a provided JSON string.
   *
   * attention This method should be called on the same thread where Map object is initialized.
   *
   * @param json A JSON string containing a serialized Mapbox Style.
   */
  override fun setStyleJSON(json: String) {
    styleManagerRef.call {
      this.styleJSON = json
    }
  }

  /**
   * Sets style layer metadata.
   *
   * @param layerId Optional filter to specify specific layer type for query.
   * @param properties the value wrapper around layer metadata
   * @return a string describing an error if the operation was not successful.
   */
  override fun setStyleLayerProperties(layerId: String, properties: Value): Expected<Void, String> {
    return styleManagerRef.call { this.setStyleLayerProperties(layerId, properties) }
  }

  /**
   * Returns all the leaves of a cluster (given its cluster_id), with pagination support: limit is
   * the number of leaves to return (set to Infinity for all points), and offset is the amount
   * of points to skip (for pagination).
   *
   * @param sourceId GeoJSON style source identifier.
   * @param clusterId from which to retrieve leaves from
   * @param limit is the number of points to return
   * @param offset is the amount of points to skip (for pagination)
   *
   * @return An array of features for the underlying leaves or a string describing
   * an error if the operation was not successful.
   */
  override fun getStyleGeoJSONSourceClusterLeaves(
    sourceId: String,
    clusterId: Int,
    limit: Int,
    offset: Int
  ): Expected<MutableList<Feature>, String> {
    return styleManagerRef.call {
      this.getStyleGeoJSONSourceClusterLeaves(sourceId, clusterId, limit, offset)
    }
  }

  /**
   * Returns the zoom on which the cluster expands into several children
   * (useful for "click to zoom" feature).
   *
   * @param sourceId GeoJSON style source identifier.
   * @param clusterId Cluster from which to retrieve the expansion zoom from
   *
   * @return The zoom on which the cluster expands into several children or a string describing
   * an error if the operation was not successful.
   */
  override fun getStyleGeoJSONSourceClusterExpansionZoom(
    sourceId: String,
    clusterId: Int
  ): Expected<Byte, String> {
    return styleManagerRef.call {
      this.getStyleGeoJSONSourceClusterExpansionZoom(sourceId, clusterId)
    }
  }

  /**
   * Returns the children of a cluster (on the next zoom level).
   *
   * @param sourceId GeoJSON style source identifier.
   * @param clusterId from which to retrieve children from
   *
   * @return An array of features for the underlying children or a string describing
   * an error if the operation was not successful.
   */
  override fun getStyleGeoJSONSourceClusterChildren(
    sourceId: String,
    clusterId: Int
  ): Expected<MutableList<Feature>, String> {
    return styleManagerRef.call {
      this.getStyleGeoJSONSourceClusterChildren(sourceId, clusterId)
    }
  }

  /**
   * Gets the value of style source property.
   *
   * @param sourceId Style source identified.
   * @param property Style source property name.
   * @return The value of \p property in the source with \p sourceId.
   */
  override fun getStyleSourceProperty(sourceId: String, property: String): StylePropertyValue {
    return styleManagerRef.call {
      this.getStyleSourceProperty(sourceId, property)
    }
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
  ): Expected<Void, String> {
    return styleManagerRef.call {
      this.setStyleSourceProperty(sourceId, property, value)
    }
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
  ): Expected<Void, String> {
    return styleManagerRef.call {
      this.addStyleCustomLayer(layerId, layerHost, layerPosition)
    }
  }

  /**
   * Adds a custom geometry to be used in the style. To add the data, implement the fetchTileFunction callback in the options and call setStyleCustomGeometrySourceTileData()
   *
   * @param sourceId Style source identifier
   * @param options Settings for the custom geometry
   */
  override fun addStyleCustomGeometrySource(
    sourceId: String,
    options: CustomGeometrySourceOptions
  ): Expected<Void, String> =
    styleManagerRef.call { this.addStyleCustomGeometrySource(sourceId, options) }

  /**
   * Returns the existing style sources.
   *
   * @return The list containing the ids of the existing style sources.
   */
  override fun getStyleSources(): List<StyleObjectInfo> {
    return styleManagerRef.call { this.styleSources }
  }

  /**
   * Returns the current sources metadata.
   *
   * @return The list of source attributions.
   */
  fun getStyleSourcesAttribution(): List<String> {
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
    return styleManagerRef.call { this.styleLayers }
  }

  /**
   * Gets style source parameters.
   * In order to convert returned value to a json string please take a look at mapbox::common::ValueConverter.
   *
   * @param sourceId Style source identifier.
   *
   * @return Style source parameters or a string describing an error if the operation was not successful.
   */
  override fun getStyleSourceProperties(sourceId: String): Expected<Value, String> {
    return styleManagerRef.call { this.getStyleSourceProperties(sourceId) }
  }

  /**
   * Sets style source parameters.
   * This method can be used to perform batch update for a style source parameters. The structure of a
   * provided `parameters` value must conform to \sa https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/
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
  ): Expected<Void?, String?> {
    return styleManagerRef.call { this.setStyleSourceProperties(sourceId, properties) }
  }

  /**
   * A convenience object to access [the style ID](https://docs.mapbox.com/help/glossary/style-id/) strings of the professionally-designed
   * map styles made by Mapbox.
   */
  companion object {
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
     * available in
     * [these select
 * countries](https://www.mapbox.com/help/how-directions-work/#traffic-data). Using this constant means your map style will always use the latest version and
     * may change as we improve the style.
     */
    const val TRAFFIC_DAY = "mapbox://styles/mapbox/traffic-day-v2"

    /**
     * Traffic Night: Color-coded roads based on live traffic congestion data, designed to maximize
     * legibility in low-light situations. Traffic data is currently available in
     * [these select
 * countries](https://www.mapbox.com/help/how-directions-work/#traffic-data). Using this constant means your map style will always use the latest version and
     * may change as we improve the style.
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