package com.mapbox.maps.extension.compose.style.internal

import android.util.Log
import com.mapbox.maps.ColorTheme
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.StyleDataLoaded
import com.mapbox.maps.StyleDataLoadedType
import com.mapbox.maps.TransitionOptions
import com.mapbox.maps.coroutine.styleDataLoadedEvents
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.internal.generated.StyleDefaults
import com.mapbox.maps.extension.compose.style.precipitations.generated.RainState
import com.mapbox.maps.extension.compose.style.precipitations.generated.SnowState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import com.mapbox.maps.logD
import com.mapbox.maps.logW
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(MapboxExperimental::class)
internal class MapStyleNode(
  val style: String,
  val mapboxMap: MapboxMap,
  private val projection: Projection,
  var atmosphereState: AtmosphereState,
  var rainState: RainState,
  var snowState: SnowState,
  var terrainState: TerrainState,
  private val styleDefaults: (String) -> StyleDefaults = { StyleDefaults.fromJson(it) },
  private val defaultsParseDispatcher: CoroutineDispatcher = Dispatchers.Default,
  styleDataLoadedEventsProvider: (MapboxMap) -> Flow<StyleDataLoaded> = {
    // Test seam: @JvmSynthetic extension can't be mocked, so we inject the flow source.
    it.styleDataLoadedEvents
  }
) : MapNode() {

  val coroutineScope =
    CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + CoroutineName("MapStyleNodeScope"))

  // Defaults cache (null = waiting for first STYLE event).
  private var cachedDefaults: StyleDefaults? = null

  // Tracks attached terrain layer for detach-before-attach logic; null on first attach.
  private var attachedTerrainState: TerrainState? = null

  private val styleDataLoadedFlow = styleDataLoadedEventsProvider(mapboxMap)

  internal val styleSourcesLoaded: SharedFlow<StyleDataLoaded> = styleDataLoadedFlow
    .filter { it.type == StyleDataLoadedType.SOURCES }
    .onEach { logD(TAG, "$this - styleSourcesLoaded: $it") }
    .shareIn(
      scope = coroutineScope,
      started = SharingStarted.Eagerly,
      replay = 1
    )
  internal val styleDataLoaded = styleDataLoadedFlow
    .filter { it.type == StyleDataLoadedType.STYLE }
    .onEach { logD(TAG, "$this - styleDataLoaded: $it") }
    .shareIn(
      scope = coroutineScope,
      started = SharingStarted.Eagerly,
      replay = 1
    )
  internal val styleSpritesLoaded = styleDataLoadedFlow
    .filter { it.type == StyleDataLoadedType.SPRITE }
    .onEach { logD(TAG, "$this - styleSpritesLoaded: $it") }
    .shareIn(
      scope = coroutineScope,
      started = SharingStarted.Eagerly,
      replay = 1
    )

  override fun onAttached(parent: MapNode) {
    logD(TAG, "onAttached: parent=$parent")
    updateStyle(style)
    updateProjection(projection)
    // Single consolidated STYLE collector (launched once, not per recomposition).
    // Reparses defaults and attaches all four on each STYLE event.
    coroutineScope.launch {
      styleDataLoaded.collect {
        val styleJson = mapboxMap.styleJSON
        val defaults = withContext(defaultsParseDispatcher) { styleDefaults(styleJson) }
        cachedDefaults = defaults
        attachAtmosphere(defaults)
        attachRain(defaults)
        attachSnow(defaults)
        attachTerrain(defaults)
      }
    }
  }

  override fun onRemoved(parent: MapNode) {
    logD(TAG, "onRemoved: parent=$parent")
    coroutineScope.cancel()
    // remove the current style with loading an empty style json, if this is the only style
    // node in the node tree.
    val styleInstances = parent.children.filterIsInstance<MapStyleNode>()
    if (styleInstances.size == 1) {
      updateStyle("{}")
    } else {
      logW(TAG, "Multiple style node detected in the tree:")
      styleInstances.forEach {
        logW(TAG, "\t${it.style}")
      }
    }

    children.forEach { it.onRemoved(this) }
  }

  override fun onClear() {
    super.onClear()
    atmosphereState.applier.detach()
    rainState.applier.detach()
    snowState.applier.detach()
    terrainState.applier.detach()
    children.forEach { it.onClear() }
  }

  private fun updateStyle(style: String) {
    logD(TAG, "loadStyle $style started")
    mapboxMap.loadStyle(style) {
      logD(TAG, "loadStyle $style finished")
    }
  }

  internal fun updateProjection(projection: Projection) {
    if (projection.notInitial) {
      coroutineScope.launch {
        styleDataLoaded.collect {
          mapboxMap.setStyleProjection(projection.value)
            .onValue {
              Log.d(TAG, "$projection projection applied")
            }.onError {
              Log.e(TAG, "Error $it when applying $projection projection")
            }
        }
      }
    }
  }

  internal fun updateAtmosphere(atmosphereState: AtmosphereState) {
    // we have to detach (in a sense of cancelling property collector jobs) the previous state
    // before attaching the new state; otherwise the jobs will be duplicated
    this.atmosphereState.applier.detach()
    this.atmosphereState = atmosphereState
    // Immediate attach using cached defaults; defers to first STYLE event if null.
    cachedDefaults?.let { attachAtmosphere(it) }
  }

  internal fun updateRain(rainState: RainState) {
    // we have to detach (in a sense of cancelling property collector jobs) the previous state
    // before attaching the new state; otherwise the jobs will be duplicated
    this.rainState.applier.detach()
    this.rainState = rainState
    cachedDefaults?.let { attachRain(it) }
  }

  internal fun updateSnow(snowState: SnowState) {
    // we have to detach (in a sense of cancelling property collector jobs) the previous state
    // before attaching the new state; otherwise the jobs will be duplicated
    this.snowState.applier.detach()
    this.snowState = snowState
    cachedDefaults?.let { attachSnow(it) }
  }

  internal fun updateTerrain(terrainState: TerrainState) {
    // we have to detach (in a sense of cancelling property collector jobs) the previous state
    // before attaching the new state; otherwise the jobs will be duplicated
    this.terrainState.applier.detach()
    this.terrainState = terrainState
    cachedDefaults?.let { attachTerrain(it) }
  }

  /**
   * Applies defaults merged with user properties.
   */
  private fun attachAtmosphere(defaults: StyleDefaults) {
    atmosphereState.applier.attachTo(mapboxMap, defaults.atmosphere)
  }

  /**
   * Applies defaults merged with user properties (or resets/removes if disabled).
   */
  private fun attachRain(defaults: StyleDefaults) {
    rainState.applier.attachTo(mapboxMap, defaults.rain)
  }

  /**
   * Applies defaults merged with user properties; resets/removes if disabled.
   */
  private fun attachSnow(defaults: StyleDefaults) {
    snowState.applier.attachTo(mapboxMap, defaults.snow)
  }

  /**
   * Applies defaults merged with user properties; detaches old layer before attaching new.
   */
  private fun attachTerrain(defaults: StyleDefaults) {
    // Always detach-then-attach terrain (even when state unchanged).
    // Style reload discards native sources; Kotlin bookkeeping must resync or terrain drops silently.
    // Don't optimize by comparing old/new state.
    attachedTerrainState?.applier?.rasterDemSourceState?.let {
      it.detachFromLayer("mapbox-terrain-${it.sourceId}", mapboxMap)
    }
    terrainState.applier.rasterDemSourceState?.let {
      it.attachToLayer("mapbox-terrain-${it.sourceId}", mapboxMap)
    }
    terrainState.applier.attachTo(mapboxMap, defaults.terrain)
    attachedTerrainState = terrainState
  }

  internal fun updateStyleTransition(transition: TransitionOptions) {
    coroutineScope.launch {
      styleDataLoaded.collect {
        mapboxMap.setStyleTransition(transition)
      }
    }
  }

  internal fun updateStyleColorTheme(colorTheme: ColorTheme?, isStyleDefault: Boolean) {
    coroutineScope.launch {
      styleDataLoaded.collect {
        if (isStyleDefault) {
          mapboxMap.setInitialStyleColorTheme()
        } else {
          mapboxMap.setStyleColorTheme(colorTheme)
        }
      }
    }
  }

  override fun toString(): String {
    return "MapStyleNode(style=$style)"
  }

  private companion object {
    private const val TAG = "MapStyleNode"
  }
}