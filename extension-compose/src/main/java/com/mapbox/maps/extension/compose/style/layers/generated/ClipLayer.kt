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
 * Layer that removes 3D content from map.
 *
 * This composable function inserts a [ClipLayer] to the map. For convenience, if there's
 * no need to hoist the [clipLayerState], use `ClipLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#clip)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param clipLayerState the state holder for [ClipLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@OptIn(MapboxExperimental::class)
public fun ClipLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("clip")
  },
  clipLayerState: ClipLayerState = remember { ClipLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of ClipLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "clip",
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
    key(clipLayerState) {
      clipLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(clipLayerState.interactionsState) {
    clipLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * Layer that removes 3D content from map.
 *
 * This composable function inserts a [ClipLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#clip)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [ClipLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun ClipLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("clip")
  },
  crossinline init: ClipLayerState.() -> Unit
) {
  ClipLayer(sourceState = sourceState, layerId = layerId, clipLayerState = remember { ClipLayerState() }.apply(init))
}

// End of generated file.