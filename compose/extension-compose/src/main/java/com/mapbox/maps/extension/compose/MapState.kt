package com.mapbox.maps.extension.compose

import android.os.Parcelable
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraChanged
import com.mapbox.maps.CameraChangedCoalesced
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CoordinateInfo
import com.mapbox.maps.GenericEvent
import com.mapbox.maps.MapIdle
import com.mapbox.maps.MapLoaded
import com.mapbox.maps.MapLoadingError
import com.mapbox.maps.MapOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.MapsResourceOptions
import com.mapbox.maps.OverscaledTileID
import com.mapbox.maps.QueriedRasterValues
import com.mapbox.maps.QueriedRenderedFeature
import com.mapbox.maps.RenderFrameFinished
import com.mapbox.maps.RenderFrameStarted
import com.mapbox.maps.RenderedQueryGeometry
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.RenderedRasterQueryOptions
import com.mapbox.maps.ResourceRequest
import com.mapbox.maps.ScreenBox
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.SourceAdded
import com.mapbox.maps.SourceDataLoaded
import com.mapbox.maps.SourceRemoved
import com.mapbox.maps.StyleDataLoaded
import com.mapbox.maps.StyleImageMissing
import com.mapbox.maps.StyleImageRemoveUnused
import com.mapbox.maps.StyleLoaded
import com.mapbox.maps.TileCoverOptions
import com.mapbox.maps.coroutine.cameraChangedCoalescedEvents
import com.mapbox.maps.coroutine.cameraChangedEvents
import com.mapbox.maps.coroutine.genericEvents
import com.mapbox.maps.coroutine.mapIdleEvents
import com.mapbox.maps.coroutine.mapLoadedEvents
import com.mapbox.maps.coroutine.mapLoadingErrorEvents
import com.mapbox.maps.coroutine.queryRenderedFeatures
import com.mapbox.maps.coroutine.queryRenderedRasterValues
import com.mapbox.maps.coroutine.renderFrameFinishedEvents
import com.mapbox.maps.coroutine.renderFrameStartedEvents
import com.mapbox.maps.coroutine.resourceRequestEvents
import com.mapbox.maps.coroutine.sourceAddedEvents
import com.mapbox.maps.coroutine.sourceDataLoadedEvents
import com.mapbox.maps.coroutine.sourceRemovedEvents
import com.mapbox.maps.coroutine.styleDataLoadedEvents
import com.mapbox.maps.coroutine.styleImageMissingEvents
import com.mapbox.maps.coroutine.styleImageRemoveUnusedEvents
import com.mapbox.maps.coroutine.styleLoadedEvents
import com.mapbox.maps.extension.compose.gestures.GesturesState
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeatureStateKey
import com.mapbox.maps.interactions.FeaturesetFeature
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor
import com.mapbox.maps.logD
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.parcelize.Parcelize
import kotlin.coroutines.resume

/**
 * Create and [rememberSaveable] a [MapState] using [MapState.Saver].
 * [init] will be called when the [MapState] is first created to configure its
 * initial state.
 */
@Composable
public inline fun rememberMapState(
  key: String? = null,
  crossinline init: MapState.() -> Unit = {},
): MapState = rememberSaveable(key = key, saver = MapState.Saver) {
  MapState().apply(init)
}

/**
 * The [MapState] that can be hoisted to observe map events, query rendered features and control gestures settings.
 */
@OptIn(ExperimentalCoroutinesApi::class, MapboxExperimental::class)
@Stable
public class MapState internal constructor(initialGesturesState: GesturesState) {

  /**
   * Constructor for the [MapState].
   */
  public constructor() : this(initialGesturesState = GesturesState())

  /**
   * A flow used to indicate the [MapState] is attached to the map.
   */
  private val mapboxMapFlow: MutableStateFlow<MapboxMap?> = MutableStateFlow(null)

  /**
   * State holder for gesture configuration and per-gesture event observation.
   *
   * Use [GesturesState.gesturesSettings] to configure which gestures are enabled, and
   * [GesturesState.GestureInput][com.mapbox.maps.extension.compose.gestures.GestureInput]
   * to observe individual gesture events.
   */
  @MapboxExperimental
  public var gesturesState: GesturesState by mutableStateOf(initialGesturesState)

  /**
   * Gesture configuration allows to control the user touch interaction.
   */
  @Deprecated(
    message = "Use gesturesState.gesturesSettings instead.",
    replaceWith = ReplaceWith("gesturesState.gesturesSettings"),
  )
  public var gesturesSettings: GesturesSettings
    get() = gesturesState.gesturesSettings
    set(value) {
      gesturesState.gesturesSettings = value
    }

  /**
   * Conflated [Flow] of [MapLoaded] updates from [MapboxMap.subscribeMapLoaded].
   */
  public val mapLoadedEvents: Flow<MapLoaded> = mapboxMapFlow.flatMapLatest {
    it?.mapLoadedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [MapLoadingError] updates from [MapboxMap.subscribeMapLoadingError].
   */
  public val mapLoadingErrorEvents: Flow<MapLoadingError> = mapboxMapFlow.flatMapLatest {
    it?.mapLoadingErrorEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [StyleLoaded] updates from [MapboxMap.subscribeStyleLoaded].
   */
  public val styleLoadedEvents: Flow<StyleLoaded> = mapboxMapFlow.flatMapLatest {
    it?.styleLoadedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [StyleDataLoaded] updates from [MapboxMap.subscribeStyleDataLoaded].
   */
  public val styleDataLoadedEvents: Flow<StyleDataLoaded> = mapboxMapFlow.flatMapLatest {
    it?.styleDataLoadedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [CameraChanged] updates from [MapboxMap.subscribeCameraChanged].
   */
  public val cameraChangedEvents: Flow<CameraChanged> = mapboxMapFlow.flatMapLatest {
    it?.cameraChangedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [CameraChangedCoalesced] updates from [MapboxMap.subscribeCameraChangedCoalesced].
   */
  @com.mapbox.annotation.MapboxExperimental
  public val cameraChangedCoalescedEvents: Flow<CameraChangedCoalesced> = mapboxMapFlow.flatMapLatest {
    it?.cameraChangedCoalescedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [MapIdle] updates from [MapboxMap.subscribeMapIdle].
   */
  public val mapIdleEvents: Flow<MapIdle> = mapboxMapFlow.flatMapLatest {
    it?.mapIdleEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [SourceAdded] updates from [MapboxMap.subscribeSourceAdded].
   */
  public val sourceAddedEvents: Flow<SourceAdded> = mapboxMapFlow.flatMapLatest {
    it?.sourceAddedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [SourceRemoved] updates from [MapboxMap.subscribeSourceRemoved].
   */
  public val sourceRemovedEvents: Flow<SourceRemoved> = mapboxMapFlow.flatMapLatest {
    it?.sourceRemovedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [SourceDataLoaded] updates from [MapboxMap.subscribeSourceDataLoaded].
   */
  public val sourceDataLoadedEvents: Flow<SourceDataLoaded> = mapboxMapFlow.flatMapLatest {
    it?.sourceDataLoadedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [StyleImageMissing] updates from [MapboxMap.subscribeStyleImageMissing].
   */
  public val styleImageMissingEvents: Flow<StyleImageMissing> = mapboxMapFlow.flatMapLatest {
    it?.styleImageMissingEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [StyleImageRemoveUnused] updates from [MapboxMap.subscribeStyleImageRemoveUnused].
   */
  public val styleImageRemoveUnusedEvents: Flow<StyleImageRemoveUnused> =
    mapboxMapFlow.flatMapLatest {
      it?.styleImageRemoveUnusedEvents ?: emptyFlow()
    }

  /**
   * Conflated [Flow] of [RenderFrameStarted] updates from [MapboxMap.subscribeRenderFrameStarted].
   */
  public val renderFrameStartedEvents: Flow<RenderFrameStarted> = mapboxMapFlow.flatMapLatest {
    it?.renderFrameStartedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [RenderFrameFinished] updates from [MapboxMap.subscribeRenderFrameFinished].
   */
  public val renderFrameFinishedEvents: Flow<RenderFrameFinished> = mapboxMapFlow.flatMapLatest {
    it?.renderFrameFinishedEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [ResourceRequest] updates from [MapboxMap.subscribeResourceRequest].
   */
  public val resourceRequestEvents: Flow<ResourceRequest> = mapboxMapFlow.flatMapLatest {
    it?.resourceRequestEvents ?: emptyFlow()
  }

  /**
   * Conflated [Flow] of [GenericEvent] updates from [MapboxMap.subscribeGenericEvent].
   */
  @MapboxExperimental
  public fun genericEvents(eventName: String): Flow<GenericEvent> = mapboxMapFlow.flatMapLatest {
    it?.genericEvents(eventName) ?: emptyFlow()
  }

  /**
   * Queries the map for rendered features.
   *
   * It will suspend until current [MapState] is set to the [MapboxMap] composable function.
   *
   * @param geometry The `screen pixel coordinates` (point, line string or box) to query for rendered features.
   * @param options The `render query options` for querying rendered features.
   *
   * @return a list of [QueriedRenderedFeature] or a string describing an error.
   */
  public suspend fun queryRenderedFeatures(
    geometry: RenderedQueryGeometry,
    options: RenderedQueryOptions
  ): Expected<String, List<QueriedRenderedFeature>> =
    mapboxMapFlow.filterNotNull().first().queryRenderedFeatures(geometry, options)

  /**
   * Queries the map for given [descriptor] and returns typed [FeaturesetFeature] list of rendered features.
   *
   * @param geometry The optional geometry ([ScreenCoordinate], [ScreenBox] or list of [ScreenCoordinate]s) to query for rendered features.
   *  Passing NULL is equivalent to passing a bounding box encompassing the entire map viewport.
   * @param descriptor [TypedFeaturesetDescriptor] object representing either a featureset or a single layer.
   * @param filter optional global filter.
   *
   * @return A typed instance list of the [FeaturesetFeature].
   */
  @MapboxExperimental
  @JvmOverloads
  public suspend fun <FF : FeaturesetFeature<*>> queryRenderedFeatures(
    descriptor: TypedFeaturesetDescriptor<*, FF>,
    geometry: RenderedQueryGeometry? = null,
    filter: Expression? = null,
  ): List<FF> {
    mapboxMapFlow.filterNotNull().first().apply {
      return suspendCancellableCoroutine { continuation ->
        val cancelable = queryRenderedFeatures(
          geometry = geometry,
          descriptor = descriptor,
          filter = filter,
          callback = continuation::resume
        )
        continuation.invokeOnCancellation {
          cancelable.cancel()
        }
      }
    }
  }

  /**
   * Queries the map for rendered raster values at a specific coordinate.
   *
   * @param coordinate The position on the screen to query.
   * @param options The options for configuring the rendered raster value query.
   *
   * @return [QueriedRasterValues] containing raster values or a string describing an error.
   */
  @MapboxExperimental
  public suspend fun queryRenderedRasterValues(
    coordinate: ScreenCoordinate,
    options: RenderedRasterQueryOptions
  ): Expected<String, QueriedRasterValues> =
    mapboxMapFlow.filterNotNull().first().queryRenderedRasterValues(coordinate, options)

  /**
   * Gets the state map of a feature from a featureset asynchronously.
   *
   * @param featuresetFeature the featureset feature coming from an interaction callback or [queryRenderedFeatures].
   *
   * @return A concrete instance of [FeatureState].
   */
  @MapboxExperimental
  public suspend fun <FS : FeatureState> getFeatureState(
    featuresetFeature: FeaturesetFeature<FS>,
  ): FS {
    mapboxMapFlow.filterNotNull().first().apply {
      return suspendCancellableCoroutine { continuation ->
        val cancelable = getFeatureState(
          featuresetFeature = featuresetFeature,
          callback = continuation::resume
        )
        continuation.invokeOnCancellation {
          cancelable.cancel()
        }
      }
    }
  }

  /**
   * Sets the state map for given [featuresetFeature] coming from an interaction callback asynchronously.
   *
   * @param featuresetFeature the featureset feature coming from an interaction callback or [queryRenderedFeatures].
   * @param state describes the new state of the map for given [featuresetFeature].
   *
   * @return the optional error wrapped in [Expected].
   */
  @MapboxExperimental
  public suspend fun <FS : FeatureState> setFeatureState(
    featuresetFeature: FeaturesetFeature<FS>,
    state: FS,
  ): Expected<String, com.mapbox.bindgen.None> {
    mapboxMapFlow.filterNotNull().first().apply {
      return suspendCancellableCoroutine { continuation ->
        val cancelable = setFeatureState(
          featuresetFeature = featuresetFeature,
          state = state,
          callback = continuation::resume
        )
        continuation.invokeOnCancellation {
          cancelable.cancel()
        }
      }
    }
  }

  /**
   * Removes entries from a feature state based on [featuresetFeature] coming from an interaction callback.
   *
   * Removes a specified property or all property from a feature's state object, depending on the value of
   * [stateKey].
   *
   * Note that updates to feature state are asynchronous, so changes made by this method might not be
   * immediately visible using [getFeatureState].
   *
   * @param featuresetFeature The featureset feature coming from an interaction callback.
   * @param stateKey The generic key of the property to remove. If `null`, all feature's state object properties are removed.
   *
   * @return the optional error wrapped in [Expected].
   */
  @MapboxExperimental
  @JvmOverloads
  public suspend fun <FS, FSK> removeFeatureState(
    featuresetFeature: FeaturesetFeature<FS>,
    stateKey: FSK? = null,
  ): Expected<String, com.mapbox.bindgen.None> where FS : FeatureState, FSK : FeatureStateKey<FS> {
    mapboxMapFlow.filterNotNull().first().apply {
      return suspendCancellableCoroutine { continuation ->
        val cancelable = removeFeatureState(
          featuresetFeature = featuresetFeature,
          stateKey = stateKey,
          callback = continuation::resume
        )
        continuation.invokeOnCancellation {
          cancelable.cancel()
        }
      }
    }
  }

  /**
   * Reset all the feature states within a style source.
   *
   * Remove all feature state entries from the specified style source or source layer.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method might not be
   * immediately visible using [getFeatureState].
   *
   * @param descriptor [TypedFeaturesetDescriptor] object representing either a featureset or a single layer.
   *
   * @return the optional error wrapped in [Expected].
   */
  @MapboxExperimental
  public suspend fun resetFeatureStates(
    descriptor: TypedFeaturesetDescriptor<*, *>,
  ): Expected<String, com.mapbox.bindgen.None> {
    mapboxMapFlow.filterNotNull().first().apply {
      return suspendCancellableCoroutine { continuation ->
        val cancelable = resetFeatureStates(
          descriptor = descriptor,
          callback = continuation::resume
        )
        continuation.invokeOnCancellation {
          cancelable.cancel()
        }
      }
    }
  }

  /**
   * Calculate a screen coordinate that corresponds to a geographical coordinate
   * (i.e., longitude-latitude pair).
   *
   * The screen coordinate is in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * Map must be fully loaded for getting an altitude-compliant result if using 3D terrain.
   *
   * If the screen coordinate is outside of the bounds of [MapView] the returned screen coordinate
   * contains -1 for both coordinates.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching center of
   * the screen.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * It will suspend until current [MapState] is set to the [MapboxMap] composable function.
   *
   * @param coordinate A geographical coordinate on the map to convert to a screen coordinate.
   *
   * @return Returns a screen coordinate on the screen in [MapOptions.size] platform pixels. If the screen coordinate is outside of the bounds of [MapView] the returned screen coordinate contains -1 for both coordinates.
   */
  public suspend fun pixelForCoordinate(coordinate: Point): ScreenCoordinate =
    mapboxMapFlow.filterNotNull().first().pixelForCoordinate(coordinate)

  /**
   * Calculate a geographical coordinate (i.e., longitude-latitude pair) that corresponds
   * to a screen coordinate.
   *
   * The screen coordinate is in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * Map must be fully loaded for getting an altitude-compliant result if using 3D terrain.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the center
   * of the screen.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * It will suspend until current [MapState] is set to the [MapboxMap] composable function.
   *
   * @param pixel A screen coordinate represented by x y coordinates.
   *
   * @return Returns a geographical coordinate corresponding to the x y coordinates on the screen.
   */
  public suspend fun coordinateForPixel(pixel: ScreenCoordinate): Point =
    mapboxMapFlow.filterNotNull().first().coordinateForPixel(pixel)

  /**
   * Calculate screen coordinates that corresponds to geographical coordinates
   * (i.e., longitude-latitude pair).
   *
   * The screen coordinates are in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * Map must be fully loaded for getting an altitude-compliant result if using 3D terrain.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the center
   * of the screen.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * It will suspend until current [MapState] is set to the [MapboxMap] composable function.
   *
   * @param coordinates A batch of geographical coordinates on the map to convert to screen coordinates.
   *
   * @return Returns a batch of screen coordinates on the screen in [MapOptions.size] platform pixels.
   */
  public suspend fun pixelsForCoordinates(coordinates: List<Point>): List<ScreenCoordinate> =
    mapboxMapFlow.filterNotNull().first().pixelsForCoordinates(coordinates)

  /**
   * Calculate geographical coordinates (i.e., longitude-latitude pair) that corresponds
   * to screen coordinates.
   *
   * The screen coordinates are in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * Map must be fully loaded for getting an altitude-compliant result if using 3D terrain.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the center
   * of the screen.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * It will suspend until current [MapState] is set to the [MapboxMap] composable function.
   *
   * @param pixels A batch of screen coordinates on the screen in [MapOptions.size] platform pixels.
   *
   * @return Returns a batch of geographical coordinates corresponding to the screen coordinates on the screen.
   */
  public suspend fun coordinatesForPixels(pixels: List<ScreenCoordinate>): List<Point> =
    mapboxMapFlow.filterNotNull().first().coordinatesForPixels(pixels)

  /**
   * Calculates the geographical coordinate information that corresponds to a given screen coordinate.
   *
   * The screen coordinate is in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * The returned coordinate will be the closest position projected onto the map surface,
   * in case the screen coordinate does not intersect with the map surface.
   *
   * It will suspend until current [MapState] is set to the [MapboxMap] composable function.
   *
   * @param pixel The screen coordinate on the map, in platform pixels.
   *
   * @return A [CoordinateInfo] record containing the geographical coordinate and whether it is on the map surface.
   */
  public suspend fun coordinateInfoForPixel(pixel: ScreenCoordinate): CoordinateInfo =
    mapboxMapFlow.filterNotNull().first().coordinateInfoForPixel(pixel)

  /**
   * Calculates the geographical coordinates information that corresponds to the given screen coordinates.
   *
   * The screen coordinates are in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * The returned coordinate will be the closest position projected onto the map surface,
   * in case the screen coordinate does not intersect with the map surface.
   *
   * It will suspend until current [MapState] is set to the [MapboxMap] composable function.
   *
   * @param pixels The list of screen coordinates on the map, in platform pixels.
   *
   * @return [CoordinateInfo] records containing geographical coordinates and whether each is on the map surface.
   */
  public suspend fun coordinatesInfoForPixels(pixels: List<ScreenCoordinate>): List<CoordinateInfo> =
    mapboxMapFlow.filterNotNull().first().coordinatesInfoForPixels(pixels)

  /**
   * Returns tileIDs that cover current map camera.
   *
   * Note! This is an experimental API and behavior might change in future.
   * As of v11.30, this returns [OverscaledTileID] (previously `CanonicalTileID`, which is
   * still preserved as the `canonical` field) so that callers can distinguish overscaled
   * tiles from their canonical zoom level, and distinguish tiles that repeat across the
   * antimeridian via their `wrap` offset. Overscaling only happens when
   * [TileCoverOptions.maxZoom] is set and the camera's zoom exceeds it: the tile is
   * clamped to `maxZoom`, and `canonical` holds the coordinate at that clamped zoom level.
   *
   * It will suspend until current [MapState] is set to the [MapboxMap] composable function.
   *
   * @param tileCoverOptions Options for the tile cover method.
   * @param cameraOptions This is an extra parameter for future use. Has no effect for now.
   *
   * @return Returns a list of [OverscaledTileID] that cover the current map camera.
   */
  @MapboxExperimental
  @JvmOverloads
  public suspend fun tileCover(
    tileCoverOptions: TileCoverOptions,
    cameraOptions: CameraOptions? = null,
  ): List<OverscaledTileID> =
    mapboxMapFlow.filterNotNull().first().tileCover(tileCoverOptions, cameraOptions)

  /**
   * Attach the [MapState] to the [MapboxMap].
   */
  @Composable
  internal fun BindToMap(mapboxMap: MapboxMap) {
    key(gesturesState) {
      gesturesState.BindToMap(mapboxMap = mapboxMap)
    }
    DisposableEffect(Unit) {
      updateMap(mapboxMap)
      onDispose {
        updateMap(null)
      }
    }
  }

  @VisibleForTesting
  internal fun updateMap(mapboxMap: MapboxMap?) {
    mapboxMapFlow.value = mapboxMap
  }

  /**
   * [MapState] Holder class to be used within [Saver].
   *
   * @param savedProperties properties to be saved
   */
  @Parcelize
  public data class Holder(
    val savedProperties: Map<String, Parcelable>
  ) : Parcelable

  /**
   * Public companion object of [MapState].
   */
  @OptIn(MapboxExperimental::class)
  public companion object {

    /**
     * Clears temporary map data.
     *
     * Clears temporary map data from the data path defined in the current options.
     * Useful to reduce the disk usage or in case the disk cache contains invalid data.
     *
     * Note that calling this API will affect all maps that use the same data path and does not
     * affect persistent map data like offline style packages.
     *
     * @return An [Expected] with [None] on success, or a [String] error message on failure.
     */
    public suspend fun clearData(): Expected<String, com.mapbox.bindgen.None> =
      suspendCancellableCoroutine { continuation ->
        MapsResourceOptions.clearData(continuation::resume)
      }

    /**
     * The default [Saver] implementation for [MapState].
     */
    public val Saver: Saver<MapState, Holder> = Saver(
      save = { mapState ->
        Holder(
          mapOf(GESTURES_SETTINGS_KEY to with(GesturesState.Saver) { save(mapState.gesturesState) }!!)
        ).also { logD(TAG, "save: $it") }
      },
      restore = { holder ->
        val gesturesSettings = holder.savedProperties[GESTURES_SETTINGS_KEY] as? GesturesSettings
          ?: GesturesSettings { }
        MapState(
          GesturesState.Saver.restore(gesturesSettings.also { logD(TAG, "restore: $it") })!!
        )
      }
    )
    private const val TAG = "MapState"
    private const val GESTURES_SETTINGS_KEY = "GesturesSettings"
  }
}