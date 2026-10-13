package com.mapbox.maps.extension.compose.style.internal

import com.mapbox.maps.EventTimeInterval
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.StyleDataLoaded
import com.mapbox.maps.StyleDataLoadedType
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.precipitations.generated.RainState
import com.mapbox.maps.extension.compose.style.precipitations.generated.SnowState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.Date

/**
 * Test seam shared by node tests that need a real [MapStyleNode] whose `styleDataLoaded` /
 * `styleSourcesLoaded` flows they can drive by hand.
 *
 * The node is built without calling [MapStyleNode.onAttached], so it performs no style load or
 * default parsing, it only exposes its flows, which are the part other nodes.
 */
@OptIn(MapboxExperimental::class)
internal class TestMapStyleNode(
  val mapboxMap: MapboxMap,
) {
  val styleDataLoadedFlow =
    MutableSharedFlow<StyleDataLoaded>(extraBufferCapacity = 1, onBufferOverflow = DROP_OLDEST)

  val node: MapStyleNode = MapStyleNode(
    style = "{}",
    mapboxMap = mapboxMap,
    projection = Projection.INITIAL,
    atmosphereState = AtmosphereState(),
    rainState = RainState(),
    snowState = SnowState(),
    terrainState = TerrainState.DISABLED,
    styleDataLoadedEventsProvider = { styleDataLoadedFlow },
  )

  fun emitStyleLoaded() {
    styleDataLoadedFlow.tryEmit(
      StyleDataLoaded(StyleDataLoadedType.STYLE, EventTimeInterval(Date(0), Date(0)))
    )
  }
}