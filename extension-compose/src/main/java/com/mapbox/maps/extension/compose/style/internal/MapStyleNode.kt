package com.mapbox.maps.extension.compose.style.internal

import android.util.Log
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.StyleDataLoaded
import com.mapbox.maps.StyleDataLoadedType
import com.mapbox.maps.coroutine.styleDataLoadedEvents
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.projection.Projection
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
  }

  private fun updateStyle(style: String) {
    logD(TAG, "loadStyle $style started")
    mapboxMap.loadStyle(style) {
      logD(TAG, "loadStyle $style finished")
    }
  }

  internal fun updateProjection(projection: Projection) {
    coroutineScope.launch {
      styleDataLoaded.collect {
        mapboxMap.setStyleProjection(projection.value)
          .onValue {
            Log.d(TAG, "$projection projection  applied")
          }.onError {
            Log.e(TAG, "Error $it when applying $projection projection")
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