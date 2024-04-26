// Suppress to keep <T> generic type for `callbackFlow<T>` - otherwise kotlin compiler crashes because the onCancel lambda
// see :
// https://youtrack.jetbrains.com/issue/KT-53478/Could-not-load-module-Error-module
// https://youtrack.jetbrains.com/issue/KT-52757/Type-inference-for-builders-fails-if-inferred-from-a-function
@file:Suppress("RemoveExplicitTypeArguments")

package com.mapbox.maps.coroutine

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.util.isEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Get the [Style] of the map.
 *
 * Note: keeping the reference to an invalid [Style] instance introduces significant native memory leak,
 * see [Style.isValid] for more details.
 */
@JvmSynthetic
suspend fun MapboxMap.awaitStyle(): Style = suspendCoroutine { continuation ->
  getStyle(continuation::resume)
}

/**
 * Load a new style from a style extension, suspends until style is loaded.
 *
 * @param styleExtension the style extension to load
 * @param transitionOptions the transition options to use when loading the style
 */
@JvmSynthetic
suspend fun MapboxMap.awaitLoadStyle(
  styleExtension: StyleContract.StyleExtension,
): Style = suspendCoroutine { continuation ->
  loadStyle(
    styleExtension,
    onStyleLoaded = continuation::resume
  )
}

/**
 * Load a new style from a style URI or JSON, suspends until style is loaded.
 *
 * @param styleUri the style URI to load
 * @param transitionOptions the transition options to use when loading the style
 */
@JvmSynthetic
suspend fun MapboxMap.awaitLoadStyle(
  style: String,
): Style = suspendCoroutine { continuation ->
  loadStyle(
    style = style,
    onStyleLoaded = continuation::resume
  )
}

/**
 * Queries the map for rendered features.
 *
 * @param geometry The `screen pixel coordinates` (point, line string or box) to query for rendered features.
 * @param options The `render query options` for querying rendered features.
 *
 * @return a list of [QueriedRenderedFeature] or a string describing an error.
 */
@JvmSynthetic
suspend fun MapboxMap.queryRenderedFeatures(
  geometry: RenderedQueryGeometry,
  options: RenderedQueryOptions,
): Expected<String, List<QueriedRenderedFeature>> =
  suspendMapboxCancellableCoroutine { continuation ->
    queryRenderedFeatures(geometry, options, continuation::resume)
  }

/**
 * Queries the map for source features.
 *
 * @param sourceId The style source identifier used to query for source features.
 * @param options The `source query options` for querying source features.
 *
 * @return a list of [QueriedSourceFeature] or a string describing an error.
 *
 * Note: In order to get expected results, the corresponding source needs to be in use and
 * the query shall be made after the corresponding source data is loaded.
 */
@JvmSynthetic
suspend fun MapboxMap.querySourceFeatures(
  sourceId: String,
  options: SourceQueryOptions,
): Expected<String, List<QueriedSourceFeature>> =
  suspendMapboxCancellableCoroutine { continuation ->
    querySourceFeatures(sourceId, options, continuation::resume)
  }

/**
 * Returns all the leaves (original points) of a cluster (given its cluster_id) from a GeoJsonSource, with pagination support: limit is the number of leaves
 * to return (set to Infinity for all points), and offset is the amount of points to skip (for pagination).
 *
 * Requires configuring the source as a cluster by calling [GeoJsonSource.Builder#cluster(boolean)].
 *
 * @param sourceIdentifier GeoJsonSource identifier.
 * @param cluster Cluster from which to retrieve leaves from.
 * @param limit The number of points to return from the query (must use type [Long], set to maximum for all points). Defaults to 10.
 * @param offset The amount of points to skip (for pagination, must use type [Long]). Defaults to 0.
 *
 * @return a feature collection or a string describing an error.
 */
@JvmSynthetic
suspend fun MapboxMap.getGeoJsonClusterLeaves(
  sourceIdentifier: String,
  cluster: Feature,
  limit: Long,
  offset: Long,
): Expected<String, FeatureExtensionValue> = suspendMapboxCancellableCoroutine { continuation ->
  getGeoJsonClusterLeaves(
    sourceIdentifier = sourceIdentifier,
    cluster = cluster,
    limit = limit,
    offset = offset,
    callback = continuation::resume
  )
}

/**
 * Returns the children (original points or clusters) of a cluster (on the next zoom level)
 * given its id (cluster_id value from feature properties) from a GeoJsonSource.
 *
 * Requires configuring the source as a cluster by calling [GeoJsonSource.Builder#cluster(boolean)].
 *
 * @param sourceIdentifier GeoJsonSource identifier.
 * @param cluster cluster from which to retrieve children from
 *
 * @return a feature collection or a string describing an error.
 */
@JvmSynthetic
suspend fun MapboxMap.getGeoJsonClusterChildren(
  sourceIdentifier: String,
  cluster: Feature,
): Expected<String, FeatureExtensionValue> = suspendMapboxCancellableCoroutine { continuation ->
  getGeoJsonClusterChildren(
    sourceIdentifier = sourceIdentifier,
    cluster = cluster,
    callback = continuation::resume
  )
}

/**
 * Returns the zoom on which the cluster expands into several children (useful for "click to zoom" feature)
 * given the cluster's cluster_id (cluster_id value from feature properties) from a GeoJsonSource.
 *
 * Requires configuring the source as a cluster by calling [GeoJsonSource.Builder#cluster(boolean)].
 *
 * @param sourceIdentifier GeoJsonSource identifier.
 * @param cluster cluster from which to retrieve the expansion zoom from
 *
 * @return a value or a string describing an error.
 */
@JvmSynthetic
suspend fun MapboxMap.getGeoJsonClusterExpansionZoom(
  sourceIdentifier: String,
  cluster: Feature,
): Expected<String, FeatureExtensionValue> = suspendMapboxCancellableCoroutine { continuation ->
  getGeoJsonClusterExpansionZoom(
    sourceIdentifier = sourceIdentifier,
    cluster = cluster,
    callback = continuation::resume
  )
}

/**
 * Get the state map of a feature within a style source.
 *
 * Note that updates to feature state are asynchronous, so changes made by other methods might not be
 * immediately visible.
 *
 * @param sourceId The style source identifier.
 * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
 * @param featureId The feature identifier of the feature whose state should be queried.
 *
 * @return a feature state value or a string describing an error.
 */
@JvmSynthetic
suspend fun MapboxMap.getFeatureState(
  sourceId: String,
  sourceLayerId: String? = null,
  featureId: String,
): Expected<String, Value> = suspendMapboxCancellableCoroutine { continuation ->
  getFeatureState(
    sourceId = sourceId,
    sourceLayerId = sourceLayerId,
    featureId = featureId,
    callback = continuation::resume
  )
}

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
 *
 * @return [None] or a string describing an error.
 */
@JvmSynthetic
suspend fun MapboxMap.removeFeatureState(
  sourceId: String,
  sourceLayerId: String? = null,
  featureId: String,
  stateKey: String? = null,
): Expected<String, None> = suspendMapboxCancellableCoroutine { continuation ->
  removeFeatureState(
    sourceId = sourceId,
    sourceLayerId = sourceLayerId,
    featureId = featureId,
    stateKey = stateKey,
    callback = continuation::resume
  )
}

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
 *
 * @return [None] or a string describing an error.
 */
@JvmSynthetic
suspend fun MapboxMap.resetFeatureStates(
  sourceId: String,
  sourceLayerId: String? = null,
): Expected<String, None> = suspendMapboxCancellableCoroutine { continuation ->
  resetFeatureStates(
    sourceId = sourceId,
    sourceLayerId = sourceLayerId,
    callback = continuation::resume
  )
}

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
 *
 * @return [None] or a string describing an error.
 */
@JvmSynthetic
suspend fun MapboxMap.setFeatureState(
  sourceId: String,
  sourceLayerId: String? = null,
  featureId: String,
  state: Value,
): Expected<String, None> = suspendMapboxCancellableCoroutine { continuation ->
  setFeatureState(
    sourceId = sourceId,
    sourceLayerId = sourceLayerId,
    featureId = featureId,
    state = state,
    callback = continuation::resume
  )
}

/**
 * [Flow] of [MapLoaded] updates from [MapboxMap.subscribeMapLoaded].
 */
val MapboxMap.mapLoadedEvents: Flow<MapLoaded>
  @JvmSynthetic
  get() = callbackFlow<MapLoaded> {
    val cancelable = nativeObserver.subscribeMapLoaded(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [MapLoadingError] updates from [MapboxMap.subscribeMapLoadingError].
 */
val MapboxMap.mapLoadingErrorEvents: Flow<MapLoadingError>
  @JvmSynthetic
  get() = callbackFlow<MapLoadingError> {
    val cancelable = nativeObserver.subscribeMapLoadingError(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [StyleLoaded] updates from [MapboxMap.subscribeStyleLoaded].
 */
val MapboxMap.styleLoadedEvents: Flow<StyleLoaded>
  @JvmSynthetic
  get() = callbackFlow<StyleLoaded> {
    val cancelable = nativeObserver.subscribeStyleLoaded(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [StyleDataLoaded] updates from [MapboxMap.subscribeStyleDataLoaded].
 */
val MapboxMap.styleDataLoadedEvents: Flow<StyleDataLoaded>
  @JvmSynthetic
  get() = callbackFlow<StyleDataLoaded> {
    val cancelable = nativeObserver.subscribeStyleDataLoaded(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [CameraChanged] updates from [MapboxMap.subscribeCameraChanged].
 */
val MapboxMap.cameraChangedEvents: Flow<CameraChanged>
  @JvmSynthetic
  get() = callbackFlow<CameraChanged> {
    val cancelable = nativeObserver.subscribeCameraChanged(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [MapIdle] updates from [MapboxMap.subscribeMapIdle].
 */
val MapboxMap.mapIdleEvents: Flow<MapIdle>
  @JvmSynthetic
  get() = callbackFlow<MapIdle> {
    val cancelable = nativeObserver.subscribeMapIdle(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [SourceAdded] updates from [MapboxMap.subscribeSourceAdded].
 */
val MapboxMap.sourceAddedEvents: Flow<SourceAdded>
  @JvmSynthetic
  get() = callbackFlow<SourceAdded> {
    val cancelable = nativeObserver.subscribeSourceAdded(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [SourceRemoved] updates from [MapboxMap.subscribeSourceRemoved].
 */
val MapboxMap.sourceRemovedEvents: Flow<SourceRemoved>
  @JvmSynthetic
  get() = callbackFlow<SourceRemoved> {
    val cancelable = nativeObserver.subscribeSourceRemoved(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [SourceDataLoaded] updates from [MapboxMap.subscribeSourceDataLoaded].
 */
val MapboxMap.sourceDataLoadedEvents: Flow<SourceDataLoaded>
  @JvmSynthetic
  get() = callbackFlow<SourceDataLoaded> {
    val cancelable = nativeObserver.subscribeSourceDataLoaded(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [StyleImageMissing] updates from [MapboxMap.subscribeStyleImageMissing].
 */
val MapboxMap.styleImageMissingEvents: Flow<StyleImageMissing>
  @JvmSynthetic
  get() = callbackFlow<StyleImageMissing> {
    val cancelable =
      nativeObserver.subscribeStyleImageMissing(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [StyleImageRemoveUnused] updates from [MapboxMap.subscribeStyleImageRemoveUnused].
 */
val MapboxMap.styleImageRemoveUnusedEvents: Flow<StyleImageRemoveUnused>
  @JvmSynthetic
  get() = callbackFlow<StyleImageRemoveUnused> {
    val cancelable =
      nativeObserver.subscribeStyleImageRemoveUnused(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [RenderFrameStarted] updates from [MapboxMap.subscribeRenderFrameStarted].
 */
val MapboxMap.renderFrameStartedEvents: Flow<RenderFrameStarted>
  @JvmSynthetic
  get() = callbackFlow<RenderFrameStarted> {
    val cancelable =
      nativeObserver.subscribeRenderFrameStarted(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [RenderFrameFinished] updates from [MapboxMap.subscribeRenderFrameFinished].
 */
val MapboxMap.renderFrameFinishedEvents: Flow<RenderFrameFinished>
  @JvmSynthetic
  get() = callbackFlow<RenderFrameFinished> {
    val cancelable =
      nativeObserver.subscribeRenderFrameFinished(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [ResourceRequest] updates from [MapboxMap.subscribeResourceRequest].
 */
val MapboxMap.resourceRequestEvents: Flow<ResourceRequest>
  @JvmSynthetic
  get() = callbackFlow<ResourceRequest> {
    val cancelable = nativeObserver.subscribeResourceRequest(::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)

/**
 * [Flow] of [GenericEvent] updates from [MapboxMap.subscribeGenericEvent].
 */
@JvmSynthetic
@MapboxExperimental
fun MapboxMap.genericEvents(eventName: String): Flow<GenericEvent> {
  return callbackFlow<GenericEvent> {
    val cancelable =
      nativeObserver.subscribeGenericEvent(eventName, ::trySendBlocking, onCancel = channel::close)
    awaitClose(cancelable::cancel)
  }.flowOn(Dispatchers.Main.immediate)
}

/**
 * Convenience method that returns the [CameraOptions] object for given parameters.
 *
 * @param coordinates The `coordinates` representing the bounds of the camera.
 * @param camera The [CameraOptions] which will be applied before calculating the camera for the coordinates. If any of the fields in [CameraOptions] are not provided then the current value from the map for that field will be used.
 * @param coordinatesPadding The amount of padding in pixels to add to the given `coordinates`.
 *                           Note: This padding is not applied to the map but to the coordinates provided. If you want to apply padding to the map use param `camera`.
 * @param maxZoom The maximum zoom level allowed in the returned camera options.
 * @param offset The center of the given bounds relative to map center in pixels.
 *
 * @return the [CameraOptions] object representing the provided parameters. Empty [CameraOptions] (see [CameraOptions.isEmpty]) could be returned only if an internal error occurred.
 */
@JvmSynthetic
suspend fun MapboxMap.cameraForCoordinates(
  coordinates: List<Point>,
  camera: CameraOptions,
  coordinatesPadding: EdgeInsets? = null,
  maxZoom: Double? = null,
  offset: ScreenCoordinate? = null,
): CameraOptions = suspendCoroutine { continuation ->
  cameraForCoordinates(
    coordinates = coordinates,
    camera = camera,
    coordinatesPadding = coordinatesPadding,
    maxZoom = maxZoom,
    offset = offset,
    result = continuation::resume
  )
}