// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * An icon or a text label.
 *
 * This composable function inserts a [SymbolLayer] to the map. For convenience, if there's
 * no need to hoist the [symbolLayerState], use `SymbolLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#symbol)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param symbolLayerState the state holder for [SymbolLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@OptIn(MapboxExperimental::class)
public fun SymbolLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("symbol")
  },
  symbolLayerState: SymbolLayerState = remember { SymbolLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of SymbolLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "symbol",
      sourceState = sourceState,
      layerId = layerId,
      coroutineScope = coroutineScope
    )
  }

  ComposeNode<LayerNode, MapApplier>(
    factory = { layerNode },
    update = {
      update(sourceState) {
        updateSource(sourceState)
      }
      update(layerId) {
        updateLayerId(layerId)
      }
    }
  ) {
    key(symbolLayerState) {
      symbolLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(symbolLayerState.interactionsState) {
    symbolLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * An icon or a text label.
 *
 * This composable function inserts a [SymbolLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#symbol)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [SymbolLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun SymbolLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("symbol")
  },
  crossinline init: SymbolLayerState.() -> Unit
) {
  SymbolLayer(sourceState = sourceState, layerId = layerId, symbolLayerState = remember { SymbolLayerState() }.apply(init))
}

// End of generated file.