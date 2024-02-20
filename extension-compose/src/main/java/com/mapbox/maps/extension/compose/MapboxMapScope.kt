package com.mapbox.maps.extension.compose

import androidx.compose.runtime.Immutable
import com.mapbox.maps.MapboxExperimental

/**
 * A MapboxMapScope provides a scope for the children of MapboxMap.
 */
@MapboxMapScopeMarker
@Immutable
@MapboxExperimental
public object MapboxMapScope {
  // TODO add needed extensions here
//  public fun Modifier.queryRenderedFeature(
//    key1: List<String>?,
//    block: suspend PointerInputScope.() -> Unit
//  ): Modifier = composed(
//    inspectorInfo = debugInspectorInfo {
//      name = "pointerInput"
//      properties["key1"] = key1
//      properties["block"] = block
//    }
//  ) {
//    val density = LocalDensity.current
//    val viewConfiguration = LocalViewConfiguration.current
//    remember(density) { SuspendingPointerInputFilter(viewConfiguration, density) }.also { filter ->
//      LaunchedEffect(filter, key1) {
//        filter.coroutineScope = this
//        filter.block()
//      }
//    }
//  }
}