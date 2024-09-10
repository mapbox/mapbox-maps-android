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
 * A filled polygon with an optional stroked border.
 *
 * This composable function inserts a [FillLayer] to the map. For convenience, if there's
 * no need to hoist the [fillLayerState], use `FillLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#fill)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param fillLayerState the state holder for [FillLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@OptIn(MapboxExperimental::class)
public fun FillLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("fill")
  },
  fillLayerState: FillLayerState = remember { FillLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of FillLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "fill",
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
    key(fillLayerState) {
      fillLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(fillLayerState.interactionsState) {
    fillLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * A filled polygon with an optional stroked border.
 *
 * This composable function inserts a [FillLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#fill)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [FillLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun FillLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("fill")
  },
  crossinline init: FillLayerState.() -> Unit
) {
  FillLayer(sourceState = sourceState, layerId = layerId, fillLayerState = remember { FillLayerState() }.apply(init))
}

// End of generated file.