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
import com.mapbox.maps.extension.compose.style.precipitations.generated.RainState
import com.mapbox.maps.extension.compose.style.precipitations.generated.SnowState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import com.mapbox.maps.logD
import com.mapbox.maps.logW
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

@OptIn(MapboxExperimental::class)
internal class MapStyleNode(
  val style: String,
  val mapboxMap: MapboxMap,
  private val projection: Projection,
  var atmosphereState: AtmosphereState,
  var rainState: RainState,
  var snowState: SnowState,
  var terrainState: TerrainState
) : MapNode() {

  val coroutineScope =
    CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + CoroutineName("MapStyleNodeScope"))

  private val styleDataLoadedFlow = mapboxMap.styleDataLoadedEvents

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
    updateAtmosphere(atmosphereState)
    updateRain(rainState)
    updateSnow(snowState)
    updateTerrain(terrainState)
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

      // TODO: create an AtmosphereState with the gl-native default prefilled then read the
      //       properties from the style (gl-native could be optimized to only return only the
      //       mutated ones from their default values, for now we need to read them individually) and
      //       merge them as the `styleAtmosphereState`.
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
    // TODO: merge the atmosphere state from the `styleAtmosphereState` (it was captured above in
    //       updateStyle) and the new one and then set all the properties. So we avoid possible
    //       flickering if we would reset the AtmosphereState and then apply the new one
    this.atmosphereState = atmosphereState
    coroutineScope.launch {
      styleDataLoaded.collect {
        atmosphereState.applier.attachTo(mapboxMap)
      }
    }
  }

  internal fun updateRain(rainState: RainState) {
    // we have to detach (in a sense of cancelling property collector jobs) the previous state
    // before attaching the new state; otherwise the jobs will be duplicated
    this.rainState.applier.detach()
    this.rainState = rainState
    coroutineScope.launch {
      styleDataLoaded.collect {
        rainState.applier.attachTo(mapboxMap)
      }
    }
  }

  internal fun updateSnow(snowState: SnowState) {
    // we have to detach (in a sense of cancelling property collector jobs) the previous state
    // before attaching the new state; otherwise the jobs will be duplicated
    this.snowState.applier.detach()
    this.snowState = snowState
    coroutineScope.launch {
      styleDataLoaded.collect {
        snowState.applier.attachTo(mapboxMap)
      }
    }
  }

  internal fun updateTerrain(terrainState: TerrainState) {
    val previousTerrainState = this.terrainState
    this.terrainState = terrainState
    // we have to detach (in a sense of cancelling property collector jobs) the previous state
    // before attaching the new state; otherwise the jobs will be duplicated
    previousTerrainState.applier.detach()
    coroutineScope.launch {
      styleDataLoaded.collect {
        // we have to treat terrain as some sort of persistent layer and attach / detach map accordingly
        previousTerrainState.applier.rasterDemSourceState?.let {
          it.detachFromLayer("mapbox-terrain-${it.sourceId}", mapboxMap)
        }
        terrainState.applier.rasterDemSourceState?.let {
          it.attachToLayer("mapbox-terrain-${it.sourceId}", mapboxMap)
        }
        terrainState.applier.attachTo(mapboxMap)
      }
    }
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