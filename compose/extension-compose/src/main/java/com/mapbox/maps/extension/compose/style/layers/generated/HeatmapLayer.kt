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
 * A heatmap.
 *
 * This composable function inserts a [HeatmapLayer] to the map. For convenience, if there's
 * no need to hoist the [heatmapLayerState], use `HeatmapLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#heatmap)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param heatmapLayerState the state holder for [HeatmapLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@OptIn(MapboxExperimental::class)
public fun HeatmapLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("heatmap")
  },
  heatmapLayerState: HeatmapLayerState = remember { HeatmapLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of HeatmapLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "heatmap",
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
    key(heatmapLayerState) {
      heatmapLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(heatmapLayerState.interactionsState) {
    heatmapLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * A heatmap.
 *
 * This composable function inserts a [HeatmapLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#heatmap)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [HeatmapLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun HeatmapLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("heatmap")
  },
  crossinline init: HeatmapLayerState.() -> Unit
) {
  HeatmapLayer(sourceState = sourceState, layerId = layerId, heatmapLayerState = remember { HeatmapLayerState() }.apply(init))
}

// End of generated file.