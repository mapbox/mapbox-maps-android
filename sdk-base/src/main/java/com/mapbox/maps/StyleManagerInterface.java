package com.mapbox.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mapbox.bindgen.Expected;
import com.mapbox.bindgen.Value;
import com.mapbox.geojson.Feature;
import java.util.List;

/** Interface for managing style of the `map`. */
@MapboxInternal
public interface StyleManagerInterface extends ObservableInterface {
    /**
     * Get the URI of the current style in use.
     *
     * @return A string containing a style URI.
     */
    @NonNull
    String getStyleURI();
    /**
     * Load style from provided URI.
     *
     * This is an asynchronous call. To check the result of this operation the user must register an observer observing
     * `MapLoaded` or `MapLoadingError` events. In case of successful style load, `StyleLoaded` event will be also emitted.
     *
     * @param uri URI where the style should be loaded from.
     */
    void setStyleURI(@NonNull String uri);
    /**
     * Get the JSON serialization string of the current style in use.
     *
     * @return A JSON string containing a serialized style.
     */
    @NonNull
    String getStyleJSON();
    /**
     * Load the style from a provided JSON string.
     *
     * @param json A JSON string containing a serialized style.
     */
    void setStyleJSON(@NonNull String json);
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
     * @return The default `camera options` of the current style in use.
     */
    @NonNull
    CameraOptions getStyleDefaultCamera();
    /**
     * Returns the map style's transition options. By default, the style parser will attempt
     * to read the style default transition options, if any, fallbacking to an immediate transition
     * otherwise. Transition options can be overriden via `setStyleTransition`, but the options are
     * reset once a new style has been loaded.
     *
     * The style transition is re-evaluated when a new style is loaded.
     *
     * @return The `transition options` of the current style in use.
     */
    @NonNull
    TransitionOptions getStyleTransition();
    /**
     * Overrides the map style's transition options with user-provided options.
     *
     * The style transition is re-evaluated when a new style is loaded.
     *
     * @param transitionOptions The `transition options`.
     */
    void setStyleTransition(@NonNull TransitionOptions transitionOptions);
    /**
     * Adds a new [style layer](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers).
     *
     * Runtime style layers are valid until they are either removed or a new style is loaded.
     *
     * @param properties A map of style layer properties.
     * @param layerPosition If not empty, the new layer will be positioned according to `layer position` parameters.
     *
     * @return A string describing an error if the operation was not successful, or empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> addStyleLayer(@NonNull Value properties, @Nullable LayerPosition layerPosition);
    /**
     * Adds a new [style custom layer](https://docs.mapbox.com/mapbox-gl-js/style-spec/#layers).
     *
     * Runtime style layers are valid until they are either removed or a new style is loaded.
     *
     * @param layerId A style layer identifier.
     * @param layerHost The `custom layer host`.
     * @param layerPosition If not empty, the new layer will be positioned according to `layer position` parameters.
     *
     * @return A string describing an error if the operation was not successful, or empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> addStyleCustomLayer(@NonNull String layerId, @NonNull CustomLayerHost layerHost, @Nullable LayerPosition layerPosition);
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
    @NonNull
    Expected<String, com.mapbox.bindgen.None> addPersistentStyleLayer(@NonNull Value properties, @Nullable LayerPosition layerPosition);
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
     * @param layerHost The `custom layer host`.
     * @param layerPosition If not empty, the new layer will be positioned according to `layer position` parameters.
     *
     * @return A string describing an error if the operation was not successful, or empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> addPersistentStyleCustomLayer(@NonNull String layerId, @NonNull CustomLayerHost layerHost, @Nullable LayerPosition layerPosition);
    /**
     * Checks if a style layer is persistent.
     *
     * @param layerId A style layer identifier.
     * @return A string describing an error if the operation was not successful, boolean representing state otherwise.
     */
    @NonNull
    Expected<String, Boolean> isStyleLayerPersistent(@NonNull String layerId);
    /**
     * Removes an existing style layer.
     *
     * @param layerId An identifier of the style layer to remove.
     *
     * @return A string describing an error if the operation was not successful, or empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> removeStyleLayer(@NonNull String layerId);
    /**
     * Moves an existing style layer
     *
     * @param layerId Identifier of the style layer to move.
     * @param layerPosition The layer will be positioned according to the LayerPosition parameters. If an empty LayerPosition
     *                      is provided then the layer is moved to the top of the layerstack.
     *
     * @return A string describing an error if the operation was not successful, or empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> moveStyleLayer(@NonNull String layerId, @Nullable LayerPosition layerPosition);
    /**
     * Checks whether a given style layer exists.
     *
     * @param layerId Style layer identifier.
     *
     * @return A `true` value if the given style layer exists, `false` otherwise.
     */
    boolean styleLayerExists(@NonNull String layerId);
    /**
     * Returns the existing style layers.
     *
     * @return The list containing the information about existing style layer objects.
     */
    @NonNull
    List<StyleObjectInfo> getStyleLayers();
    /**
     * Gets the value of style layer property.
     *
     * @param layerId A style layer identifier.
     * @param property The style layer property name.
     * @return The `style property value`.
     */
    @NonNull
    StylePropertyValue getStyleLayerProperty(@NonNull String layerId, @NonNull String property);
    /**
     * Sets a value to a style layer property.
     *
     * @param layerId A style layer identifier.
     * @param property The style layer property name.
     * @param value The style layer property value.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleLayerProperty(@NonNull String layerId, @NonNull String property, @NonNull Value value);
    /**
     * Gets style layer properties.
     *
     * @return The style layer properties or a string describing an error if the operation was not successful.
     */
    @NonNull
    Expected<String, Value> getStyleLayerProperties(@NonNull String layerId);
    /**
     * Sets style layer properties.
     * This method can be used to perform batch update for a style layer properties. The structure of a
     * provided `properties` value must conform to a format for a corresponding [layer type](https://docs.mapbox.com/mapbox-gl-js/style-spec/layers/).
     * Modification of a layer [id](https://docs.mapbox.com/mapbox-gl-js/style-spec/layers/#id) and/or a [layer type] (https://docs.mapbox.com/mapbox-gl-js/style-spec/layers/#type) is not allowed.
     *
     * @param layerId A style layer identifier.
     * @param properties A map of style layer properties.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleLayerProperties(@NonNull String layerId, @NonNull Value properties);
    /**
     * Adds a new [style source](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources).
     * Note: When adding a `geojson` source, this method does not synchronously parse the GeoJSON data.
     * The events API shall be used to make sure the provided GeoJSON data is valid.
     * In case the GeoJSON is valid, a `map-loaded` event will be propagated. In case of errors, a `map-loading-error` event will be propagated instead.
     *
     * @param sourceId An identifier for the style source.
     * @param properties A map of style source properties.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> addStyleSource(@NonNull String sourceId, @NonNull Value properties);
    /**
     * Gets the value of style source property.
     *
     * @param sourceId A style source identifier.
     * @param property The style source property name.
     * @return The value of a `property` in the source with a `sourceId`.
     */
    @NonNull
    StylePropertyValue getStyleSourceProperty(@NonNull String sourceId, @NonNull String property);
    /**
     * Sets a value to a style source property.
     * Note: When setting the `data` property of a `geojson` source, this method does not synchronously parse the GeoJSON data.
     * The events API shall be used to make sure the provided GeoJSON data is valid.
     * In case the GeoJSON is valid, a `map-loaded` event will be propagated. In case of errors, a `map-loading-error` event will be propagated instead.
     *
     * @param sourceId A style source identifier.
     * @param property The style source property name.
     * @param value The style source property value.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleSourceProperty(@NonNull String sourceId, @NonNull String property, @NonNull Value value);
    /**
     * Gets style source properties.
     *
     * @param sourceId A style source identifier.
     *
     * @return The style source properties or a string describing an error if the operation was not successful.
     */
    @NonNull
    Expected<String, Value> getStyleSourceProperties(@NonNull String sourceId);
    /**
     * Sets style source properties.
     *
     * This method can be used to perform batch update for a style source properties. The structure of a
     * provided `properties` value must conform to a format for a corresponding [source type](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/).
     * Modification of a source [type](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#type) is not allowed.
     *
     * @param sourceId A style source identifier.
     * @param properties A map of Style source properties.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleSourceProperties(@NonNull String sourceId, @NonNull Value properties);
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleGeoJSONSourceData(@NonNull String sourceId, @NonNull GeoJSONSourceData data);
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleGeoJSONSourceData(@NonNull String sourceId, @NonNull String dataId, @NonNull GeoJSONSourceData data);
    /**
     * Updates the image of an [image style source](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image).
     *
     * @param sourceId A style source identifier.
     * @param image An `image`.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> updateStyleImageSourceImage(@NonNull String sourceId, @NonNull Image image);
    /**
     * Removes an existing style source.
     *
     * @param sourceId An identifier of the style source to remove.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> removeStyleSource(@NonNull String sourceId);
    /**
     * Checks whether a given style source exists.
     *
     * @param sourceId A style source identifier.
     *
     * @return `true` if the given source exists, `false` otherwise.
     */
    boolean styleSourceExists(@NonNull String sourceId);
    /**
     * Returns the existing style sources.
     *
     * @return The list containing the information about existing style source objects.
     */
    @NonNull
    List<StyleObjectInfo> getStyleSources();
    /**
     * Sets the style global [light](https://docs.mapbox.com/mapbox-gl-js/style-spec/#light) properties.
     *
     * @param properties A map of style light properties values, with their names as a key.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleLight(@NonNull Value properties);
    /**
     * Gets the value of a style light property.
     *
     * @param property The style light property name.
     * @return The style light property value.
     */
    @NonNull
    StylePropertyValue getStyleLightProperty(@NonNull String property);
    /**
     * Sets a value to the the style light property.
     *
     * @param property The style light property name.
     * @param value The style light property value.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleLightProperty(@NonNull String property, @NonNull Value value);
    @NonNull
    List<StyleObjectInfo> getStyleLights();
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleLights(@NonNull Value lights);
    @NonNull
    StylePropertyValue getStyleLightProperty(@NonNull String id, @NonNull String property);
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleLightProperty(@NonNull String id, @NonNull String property, @NonNull Value value);
    /**
     * Sets the style global [atmosphere](https://docs.mapbox.com/mapbox-gl-js/style-spec/#fog) properties.
     *
     * @param properties A map of style atmosphere properties values, with their names as a key.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleAtmosphere(@NonNull Value properties);
    /**
     * Gets the value of a style atmosphere property.
     *
     * @param property The style atmosphere property name.
     * @return The style atmosphere property value.
     */
    @NonNull
    StylePropertyValue getStyleAtmosphereProperty(@NonNull String property);
    /**
     * Sets a value to the the style atmosphere property.
     *
     * @param property The style atmosphere property name.
     * @param value The style atmosphere property value.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleAtmosphereProperty(@NonNull String property, @NonNull Value value);
    /**
     * Sets the style global [terrain](https://docs.mapbox.com/mapbox-gl-js/style-spec/#terrain) properties.
     *
     * @param properties A map of style terrain properties values, with their names as a key.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleTerrain(@NonNull Value properties);
    /**
     * Gets the value of a style terrain property.
     *
     * @param property The style terrain property name.
     * @return The style terrain property value.
     */
    @NonNull
    StylePropertyValue getStyleTerrainProperty(@NonNull String property);
    /**
     * Sets a value to the the style terrain property.
     *
     * @param property The style terrain property name.
     * @param value The style terrain property value.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleTerrainProperty(@NonNull String property, @NonNull Value value);
    /**
     * Sets the map's [projection](https://docs.mapbox.com/mapbox-gl-js/style-spec/projection/). If called with `null`, the map will reset to Mercator.
     *
     * @param properties A map of style projection values, with their names as a key.
     * Supported projections are:
     *  * Mercator
     *  * Globe
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleProjection(@NonNull Value properties);
    /**
     * Gets the value of a style projection property.
     *
     * @param property The style projection property name.
     * @return The style projection property value.
     */
    @NonNull
    StylePropertyValue getStyleProjectionProperty(@NonNull String property);
    /**
     * Sets a value to the the style projection property.
     *
     * @param property The style projection property name.
     * @param value The style projection property value.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleProjectionProperty(@NonNull String property, @NonNull Value value);
    /**
     * Get an `image` from the style.
     *
     * @param imageId The identifier of the `image`.
     *
     * @return The `image` for the given `imageId`, or empty if no image is associated with the `imageId`.
     */
    @Nullable
    Image getStyleImage(@NonNull String imageId);
    /**
     * Adds an image to be used in the style. This API can also be used for updating
     * an image. If the image for a given `imageId` was already added, it gets replaced by the new image.
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
    @NonNull
    Expected<String, com.mapbox.bindgen.None> addStyleImage(@NonNull String imageId, float scale, @NonNull Image image, boolean sdf, @NonNull List<ImageStretches> stretchX, @NonNull List<ImageStretches> stretchY, @Nullable ImageContent content);
    /**
     * Removes an image from the style.
     *
     * @param imageId The identifier of the image to remove.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> removeStyleImage(@NonNull String imageId);
    /**
     * Checks whether an image exists.
     *
     * @param imageId The identifier of the image.
     *
     * @return True if image exists, false otherwise.
     */
    boolean hasStyleImage(@NonNull String imageId);
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
    @NonNull
    Expected<String, com.mapbox.bindgen.None> addStyleModel(@NonNull String modelId, @NonNull String modelUri);
    /**
     * Removes a model from the style.
     *
     * @param modelId The identifier of the model to remove.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> removeStyleModel(@NonNull String modelId);
    /**
     * Checks whether a model exists.
     *
     * @param modelId The identifier of the model.
     *
     * @return True if model exists, false otherwise.
     */
    boolean hasStyleModel(@NonNull String modelId);
    /**
     * Adds a custom geometry to be used in the style. To add the data, implement the fetchTileFunction callback in the options and call setStyleCustomGeometrySourceTileData()
     *
     * @param sourceId A style source identifier
     * @param options The `custom geometry source options` for the custom geometry.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> addStyleCustomGeometrySource(@NonNull String sourceId, @NonNull CustomGeometrySourceOptions options);
    /**
     * Set tile data of a custom geometry.
     *
     * @param sourceId A style source identifier.
     * @param tileId A `canonical tile id` of the tile.
     * @param featureCollection An array with the features to add.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> setStyleCustomGeometrySourceTileData(@NonNull String sourceId, @NonNull CanonicalTileID tileId, @NonNull List<Feature> featureCollection);
    /**
     * Invalidate tile for provided custom geometry source.
     *
     * @param sourceId A style source identifier,.
     * @param tileId A `canonical tile id` of the tile.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> invalidateStyleCustomGeometrySourceTile(@NonNull String sourceId, @NonNull CanonicalTileID tileId);
    /**
     * Invalidate region for provided custom geometry source.
     *
     * @param sourceId A style source identifier
     * @param bounds A `coordinate bounds` object.
     *
     * @return A string describing an error if the operation was not successful, empty otherwise.
     */
    @NonNull
    Expected<String, com.mapbox.bindgen.None> invalidateStyleCustomGeometrySourceRegion(@NonNull String sourceId, @NonNull CoordinateBounds bounds);
    /**
     * Check if the style is completely loaded.
     *
     * Note: The style specified sprite would be marked as loaded even with sprite loading error (An error will be emitted via `MapLoadingError`).
     * Sprite loading error is not fatal and we don't want it to block the map rendering, thus the function will still return `true` if style and sources are fully loaded.
     *
     * @return `true` if the style JSON contents, the style specified sprite and sources are all loaded, otherwise returns `false`.
     *
     */
    boolean isStyleLoaded();
}
