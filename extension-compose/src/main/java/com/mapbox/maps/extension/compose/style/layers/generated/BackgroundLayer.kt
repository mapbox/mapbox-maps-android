// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The background color or pattern of the map.
 *
 * This composable function inserts a [BackgroundLayer] to the map. For convenience, if there's
 * no need to hoist the [backgroundLayerState], use `BackgroundLayer(layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#background)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param backgroundLayerState the state holder for [BackgroundLayer]'s properties.
 */
@Composable
@MapboxMapComposable
public fun BackgroundLayer(
  layerId: String = remember {
    generateRandomLayerId("background")
  },
  backgroundLayerState: BackgroundLayerState = remember { BackgroundLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of BackgroundLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "background",
      layerId = layerId,
      coroutineScope = coroutineScope
    )
  }

  ComposeNode<LayerNode, MapApplier>(
    factory = { layerNode },
    update = {
      update(layerId) {
        updateLayerId(layerId)
      }
    }
  ) {
    key(backgroundLayerState) {
      backgroundLayerState.UpdateProperties(layerNode)
    }
  }
}

/**
 * The background color or pattern of the map.
 *
 * This composable function inserts a [BackgroundLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#background)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [BackgroundLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun BackgroundLayer(
  layerId: String = remember {
    generateRandomLayerId("background")
  },
  crossinline init: BackgroundLayerState.() -> Unit
) {
  BackgroundLayer(layerId = layerId, backgroundLayerState = remember { BackgroundLayerState() }.apply(init))
}

// End of generated file.