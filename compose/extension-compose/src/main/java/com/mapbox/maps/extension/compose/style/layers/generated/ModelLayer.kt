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
 * A 3D model
 *
 * This composable function inserts a [ModelLayer] to the map. For convenience, if there's
 * no need to hoist the [modelLayerState], use `ModelLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#model)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param modelLayerState the state holder for [ModelLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@OptIn(MapboxExperimental::class)
public fun ModelLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("model")
  },
  modelLayerState: ModelLayerState = remember { ModelLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of ModelLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "model",
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
    key(modelLayerState) {
      modelLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(modelLayerState.interactionsState) {
    modelLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * A 3D model
 *
 * This composable function inserts a [ModelLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#model)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [ModelLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun ModelLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("model")
  },
  crossinline init: ModelLayerState.() -> Unit
) {
  ModelLayer(sourceState = sourceState, layerId = layerId, modelLayerState = remember { ModelLayerState() }.apply(init))
}

// End of generated file.