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
 * A filled circle.
 *
 * This composable function inserts a [CircleLayer] to the map. For convenience, if there's
 * no need to hoist the [circleLayerState], use `CircleLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#circle)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param circleLayerState the state holder for [CircleLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@OptIn(MapboxExperimental::class)
public fun CircleLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("circle")
  },
  circleLayerState: CircleLayerState = remember { CircleLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of CircleLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "circle",
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
    key(circleLayerState) {
      circleLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(circleLayerState.interactionsState) {
    circleLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * A filled circle.
 *
 * This composable function inserts a [CircleLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#circle)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [CircleLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun CircleLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("circle")
  },
  crossinline init: CircleLayerState.() -> Unit
) {
  CircleLayer(sourceState = sourceState, layerId = layerId, circleLayerState = remember { CircleLayerState() }.apply(init))
}

// End of generated file.