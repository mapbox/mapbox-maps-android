package com.mapbox.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mapbox.bindgen.Expected;
import com.mapbox.bindgen.Value;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import java.util.HashMap;
import java.util.List;

/**
 * Map class provides map rendering functionality.
 *
 */
@MapboxInternal
public interface MapInterface extends CameraManagerInterface {
    /**
     * Creates the infrastructure needed for rendering the map.
     * It should be called before any call to `render` method. Must be called on the render thread.
     */
    void createRenderer();
    /**
     * Destroys the infrastructure needed for rendering the map, releasing resources.
     * Must be called on the render thread.
     */
    void destroyRenderer();
    /** Renders the map. */
    void render();
    /**
     * Sets the size of the map.
     * @param size The new `size` of the map in `platform pixels`.
     */
    void setSize(@NonNull Size size);
    /**
     * Gets the size of the map.
     *
     * @return The `size` of the map in `platform pixels`.
     */
    @NonNull
    Size getSize();
    /** Triggers a repaint of the map. */
    void triggerRepaint();
    /**
     * Tells the map rendering engine that there is currently a gesture in progress. This
     * affects how the map renders labels, as it will use different texture filters if a gesture
     * is ongoing.
     *
     * @param inProgress The `boolean` value representing if a gesture is in progress.
     */
    void setGestureInProgress(boolean inProgress);
    /**
     * Returns `true` if a gesture is currently in progress.
     *
     * @return `true` if a gesture is currently in progress, `false` otherwise.
     */
    boolean isGestureInProgress();
    /**
     * Tells the map rendering engine that the animation is currently performed by the
     * user (e.g. with a `setCamera` calls series). It adjusts the engine for the animation use case.
     * In particular, it brings more stability to symbol placement and rendering.
     *
     * @param inProgress The `boolean` value representing if user animation is in progress
     */
    void setUserAnimationInProgress(boolean inProgress);
    /**
     * Returns `true` if user animation is currently in progress.
     *
     * @return `true` if a user animation is currently in progress, `false` otherwise.
     */
    boolean isUserAnimationInProgress();
    /**
     * When loading a map, if prefetch zoom `delta` is set to any number greater than 0,
     * the map will first request a tile at zoom level lower than `zoom - delta`, with requested
     * zoom level a multiple of `delta`, in an attempt to display a full map at lower resolution as quick as possible.
     *
     * @param delta The new prefetch zoom delta.
     */
    void setPrefetchZoomDelta(byte delta);
    /**
     * Returns the map's prefetch zoom delta.
     *
     * @return The map's prefetch zoom `delta`.
     */
    byte getPrefetchZoomDelta();
    /** Sets the north `orientation mode`. */
    void setNorthOrientation(@NonNull NorthOrientation orientation);
    /** Sets the map `constrain mode`. */
    void setConstrainMode(@NonNull ConstrainMode mode);
    /** Sets the `viewport mode`. */
    void setViewportMode(@NonNull ViewportMode mode);
    /**
     * Returns the `map options`.
     *
     * @return The map's `map options`.
     */
    @NonNull
    MapOptions getMapOptions();
    /**
     * Returns the `map debug options`.
     *
     * @return An array of `map debug options` flags currently set to the map.
     */
    @NonNull
    List<MapDebugOptions> getDebug();
    /**
     * Sets the `map debug options` and enables debug mode based on the passed value.
     *
     * @param debugOptions An array of `map debug options` to be set.
     * @param value A `boolean` value representing the state for a given `map debug options`.
     *
     */
    void setDebug(@NonNull List<MapDebugOptions> debugOptions, boolean value);
    /**
     * Queries the map for rendered features.
     *
     * @param geometry The `screen pixel coordinates` (point, line string or box) to query for rendered features.
     * @param options The `render query options` for querying rendered features.
     * @param callback The `query features callback` called when the query completes.
     * @return A `cancelable` object that could be used to cancel the pending query.
     */
    @NonNull
    com.mapbox.common.Cancelable queryRenderedFeatures(@NonNull RenderedQueryGeometry geometry, @NonNull RenderedQueryOptions options, @NonNull QueryRenderedFeaturesCallback callback);
    /**
     * Queries the map for source features.
     *
     * @param sourceId The style source identifier used to query for source features.
     * @param options The `source query options` for querying source features.
     * @param callback The `query features callback` called when the query completes.
     * @return A `cancelable` object that could be used to cancel the pending query.
     *
     * Note: In order to get expected results, the corresponding source needs to be in use and the query shall be made after the corresponding source data is loaded.
     */
    @NonNull
    com.mapbox.common.Cancelable querySourceFeatures(@NonNull String sourceId, @NonNull SourceQueryOptions options, @NonNull QuerySourceFeaturesCallback callback);
    /**
     * Queries for feature extension values in a GeoJSON source.
     *
     * @param sourceIdentifier The `source identifier` of the source to query.
     * @param feature The `feature` to look for in the query.
     * @param extension The `extension`, now only support keyword 'supercluster'.
     * @param extensionField The `extension field`, now only support following extensions:
     *        'children': returns the children of a cluster (on the next zoom level).
     *        'leaves': returns all the leaves of a cluster (given its cluster_id)
     *        'expansion-zoom': returns the zoom on which the cluster expands into several children (useful for "click to zoom" feature).
     * @param args The `args` parameter used for further query specification when using 'leaves' extensionField. Now only support following args:
     *        'limit': the number of points to return from the query (must use type 'uint64_t', set to maximum for all points)
     *        'offset': the amount of points to skip (for pagination, must use type 'uint64_t')
     * @return A `cancelable` object that could be used to cancel the pending query.
     *
     * The result will be returned through the `query featureExtension callback`.
     * The result could be a feature extension value containing either a value (expansion-zoom) or a feature collection (children or leaves).
     * Or a string describing an error if the operation was not successful.
     */
    @NonNull
    com.mapbox.common.Cancelable queryFeatureExtensions(@NonNull String sourceIdentifier, @NonNull Feature feature, @NonNull String extension, @NonNull String extensionField, @Nullable HashMap<String, Value> args, @NonNull QueryFeatureExtensionCallback callback);
    /**
     * Updates the state object of a feature within a style source.
     *
     * Update entries in the `state` object of a given feature within a style source. Only properties of the
     * `state` object will be updated. A property in the feature `state` object that is not listed in `state` will
     * retain its previous value. The properties must be paint properties, layout properties are not supported.
     *
     * Note that updates to feature `state` are asynchronous, so changes made by this method might not be
     * immediately visible using `getStateFeature`. And the corresponding source needs to be in use to ensure the
     * feature data it contains can be successfully updated.
     *
     * @param sourceId The style source identifier.
     * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
     * @param featureId The feature identifier of the feature whose state should be updated.
     * @param state The `state` object with properties to update with their respective new values.
     * @param callback The `feature state operation callback` called when the operation completes or ends.
     * @return A `cancelable` object that could be used to cancel the pending operation.
     *
     */
    @NonNull
    com.mapbox.common.Cancelable setFeatureState(@NonNull String sourceId, @Nullable String sourceLayerId, @NonNull String featureId, @NonNull Value state, @NonNull FeatureStateOperationCallback callback);
    /**
     * Gets the state map of a feature within a style source.
     *
     * Note that updates to feature state are asynchronous, so changes made by other methods might not be
     * immediately visible.
     *
     * @param sourceId The style source identifier.
     * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
     * @param featureId The feature identifier of the feature whose state should be queried.
     * @param callback The `query feature state callback` called when the query completes.
     * @return A `cancelable` object that could be used to cancel the pending query.
     *
     */
    @NonNull
    com.mapbox.common.Cancelable getFeatureState(@NonNull String sourceId, @Nullable String sourceLayerId, @NonNull String featureId, @NonNull QueryFeatureStateCallback callback);
    /**
     * Removes entries from a feature state object.
     *
     * Remove a specified property or all property from a feature's state object, depending on the value of
     * `stateKey`.
     *
     * Note that updates to feature state are asynchronous, so changes made by this method might not be
     * immediately visible using `getStateFeature`.
     *
     * @param sourceId The style source identifier.
     * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
     * @param featureId The feature identifier of the feature whose state should be removed.
     * @param stateKey The key of the property to remove. If `null`, all feature's state object properties are removed.
     * @param callback The `feature state operation callback` called when the operation completes or ends.
     * @return A `cancelable` object that could be used to cancel the pending operation.
     */
    @NonNull
    com.mapbox.common.Cancelable removeFeatureState(@NonNull String sourceId, @Nullable String sourceLayerId, @NonNull String featureId, @Nullable String stateKey, @NonNull FeatureStateOperationCallback callback);
    /**
     * Reset all the feature states within a style source.
     *
     * Remove all feature state entries from the specified style source or source layer.
     *
     * Note that updates to feature state are asynchronous, so changes made by this method might not be
     * immediately visible using `getStateFeature`.
     *
     * @param sourceId The style source identifier.
     * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
     * @param callback The `feature state operation callback` called when the operation completes or ends.
     * @return A `cancelable` object that could be used to cancel the pending operation.
     */
    @NonNull
    com.mapbox.common.Cancelable resetFeatureStates(@NonNull String sourceId, @Nullable String sourceLayerId, @NonNull FeatureStateOperationCallback callback);
    void setMemoryBudget(@Nullable MapMemoryBudget memoryBudget);
    /** Reduces memory use. Useful to call when the application gets paused or sent to background. */
    void reduceMemoryUse();
    /**
     * Gets the resource options for the map.
     *
     * All optional fields of the retuned object are initialized with the actual values.
     *
     * Note that result of this method is different from the `resource options` that were provided to the map's constructor.
     *
     * @return The `resource options` for the map.
     */
    @NonNull
    ResourceOptions getResourceOptions();
    /**
     * Gets elevation for the given coordinate.
     * Note: Elevation is only available for the visible region on the screen.
     *
     * @param coordinate The `coordinate` defined as longitude-latitude pair.
     * @return The elevation (in meters) multiplied by current terrain exaggeration, or empty if elevation for the coordinate is not available.
     */
    @Nullable
    Double getElevation(@NonNull Point coordinate);
    void setViewAnnotationPositionsUpdateListener(@Nullable ViewAnnotationPositionsUpdateListener listener);
    @NonNull
    Expected<String, com.mapbox.bindgen.None> addViewAnnotation(@NonNull String identifier, @NonNull ViewAnnotationOptions options);
    @NonNull
    Expected<String, com.mapbox.bindgen.None> updateViewAnnotation(@NonNull String identifier, @NonNull ViewAnnotationOptions options);
    @NonNull
    Expected<String, com.mapbox.bindgen.None> removeViewAnnotation(@NonNull String identifier);
    @NonNull
    Expected<String, ViewAnnotationOptions> getViewAnnotationOptions(@NonNull String identifier);
    @NonNull
    List<CanonicalTileID> tileCover(@NonNull TileCoverOptions tileCoverOptions, @Nullable CameraOptions cameraOptions);
}
