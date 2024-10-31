package com.mapbox.maps.extension.compose

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraChanged
import com.mapbox.maps.GenericEvent
import com.mapbox.maps.MapIdle
import com.mapbox.maps.MapLoaded
import com.mapbox.maps.MapLoadingError
import com.mapbox.maps.MapOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.QueriedRenderedFeature
import com.mapbox.maps.RenderFrameFinished
import com.mapbox.maps.RenderFrameStarted
import com.mapbox.maps.RenderedQueryGeometry
import com.mapbox.maps.RenderedQueryOptions
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
import com.mapbox.maps.coroutine.cameraChangedEvents
import com.mapbox.maps.coroutine.genericEvents
import com.mapbox.maps.coroutine.mapIdleEvents
import com.mapbox.maps.coroutine.mapLoadedEvents
import com.mapbox.maps.coroutine.mapLoadingErrorEvents
import com.mapbox.maps.coroutine.queryRenderedFeatures
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
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.internal.applySettings
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
  crossinline init: MapState.() -> Unit = {}
): MapState = rememberSaveable(key = key, saver = MapState.Saver) {
  MapState().apply(init)
}

/**
 * The [MapState] that can be hoisted to observe map events, query rendered features and control gestures settings.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Stable
public class MapState internal constructor(initialGesturesSettings: GesturesSettings) {

  /**
   * Constructor for the [MapState].
   */
  public constructor() : this(initialGesturesSettings = GesturesSettings { })

  /**
   * A flow used to indicate the [MapState] is attached to the map.
   */
  private val mapboxMapFlow: MutableStateFlow<MapboxMap?> = MutableStateFlow(null)

  private val gesturesState: MutableState<GesturesSettings> =
    mutableStateOf(initialGesturesSettings)

  /**
   * Gesture configuration allows to control the user touch interaction.
   */
  public var gesturesSettings: GesturesSettings by gesturesState

  @Composable
  private fun UpdateGesturesSettings(mapboxMap: MapboxMap) {
    mapboxMap.gesturesPlugin { applySettings(gesturesSettings) }
  }

  /**
   * [Flow] of [MapLoaded] updates from [MapboxMap.subscribeMapLoaded].
   */
  public val mapLoadedEvents: Flow<MapLoaded> = mapboxMapFlow.flatMapLatest {
    it?.mapLoadedEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [MapLoadingError] updates from [MapboxMap.subscribeMapLoadingError].
   */
  public val mapLoadingErrorEvents: Flow<MapLoadingError> = mapboxMapFlow.flatMapLatest {
    it?.mapLoadingErrorEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [StyleLoaded] updates from [MapboxMap.subscribeStyleLoaded].
   */
  public val styleLoadedEvents: Flow<StyleLoaded> = mapboxMapFlow.flatMapLatest {
    it?.styleLoadedEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [StyleDataLoaded] updates from [MapboxMap.subscribeStyleDataLoaded].
   */
  public val styleDataLoadedEvents: Flow<StyleDataLoaded> = mapboxMapFlow.flatMapLatest {
    it?.styleDataLoadedEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [CameraChanged] updates from [MapboxMap.subscribeCameraChanged].
   */
  public val cameraChangedEvents: Flow<CameraChanged> = mapboxMapFlow.flatMapLatest {
    it?.cameraChangedEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [MapIdle] updates from [MapboxMap.subscribeMapIdle].
   */
  public val mapIdleEvents: Flow<MapIdle> = mapboxMapFlow.flatMapLatest {
    it?.mapIdleEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [SourceAdded] updates from [MapboxMap.subscribeSourceAdded].
   */
  public val sourceAddedEvents: Flow<SourceAdded> = mapboxMapFlow.flatMapLatest {
    it?.sourceAddedEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [SourceRemoved] updates from [MapboxMap.subscribeSourceRemoved].
   */
  public val sourceRemovedEvents: Flow<SourceRemoved> = mapboxMapFlow.flatMapLatest {
    it?.sourceRemovedEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [SourceDataLoaded] updates from [MapboxMap.subscribeSourceDataLoaded].
   */
  public val sourceDataLoadedEvents: Flow<SourceDataLoaded> = mapboxMapFlow.flatMapLatest {
    it?.sourceDataLoadedEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [StyleImageMissing] updates from [MapboxMap.subscribeStyleImageMissing].
   */
  public val styleImageMissingEvents: Flow<StyleImageMissing> = mapboxMapFlow.flatMapLatest {
    it?.styleImageMissingEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [StyleImageRemoveUnused] updates from [MapboxMap.subscribeStyleImageRemoveUnused].
   */
  public val styleImageRemoveUnusedEvents: Flow<StyleImageRemoveUnused> =
    mapboxMapFlow.flatMapLatest {
      it?.styleImageRemoveUnusedEvents ?: emptyFlow()
    }

  /**
   * [Flow] of [RenderFrameStarted] updates from [MapboxMap.subscribeRenderFrameStarted].
   */
  public val renderFrameStartedEvents: Flow<RenderFrameStarted> = mapboxMapFlow.flatMapLatest {
    it?.renderFrameStartedEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [RenderFrameFinished] updates from [MapboxMap.subscribeRenderFrameFinished].
   */
  public val renderFrameFinishedEvents: Flow<RenderFrameFinished> = mapboxMapFlow.flatMapLatest {
    it?.renderFrameFinishedEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [ResourceRequest] updates from [MapboxMap.subscribeResourceRequest].
   */
  public val resourceRequestEvents: Flow<ResourceRequest> = mapboxMapFlow.flatMapLatest {
    it?.resourceRequestEvents ?: emptyFlow()
  }

  /**
   * [Flow] of [GenericEvent] updates from [MapboxMap.subscribeGenericEvent].
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
   * Attach the [MapState] to the [MapboxMap].
   */
  @Composable
  internal fun BindToMap(mapboxMap: MapboxMap) {
    UpdateGesturesSettings(mapboxMap = mapboxMap)
    DisposableEffect(Unit) {
      mapboxMapFlow.value = mapboxMap
      onDispose {
        mapboxMapFlow.value = null
      }
    }
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
  public companion object {
    /**
     * The default saver implementation for [MapViewportState]
     */
    public val Saver: Saver<MapState, Holder> = Saver(
      save = { mapState ->
        Holder(
          mapOf(GESTURES_SETTINGS_KEY to mapState.gesturesSettings)
        ).also { logD(TAG, "save: $it") }
      },
      restore = { holder ->
        MapState(
          (
            holder.savedProperties[GESTURES_SETTINGS_KEY] as? GesturesSettings
              ?: GesturesSettings { }
            ).also { logD(TAG, "restore: $it") }
        )
      }
    )
    private const val TAG = "MapState"
    private const val GESTURES_SETTINGS_KEY = "GesturesSettings"
  }
}