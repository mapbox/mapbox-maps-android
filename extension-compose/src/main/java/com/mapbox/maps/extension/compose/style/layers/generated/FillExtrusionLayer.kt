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
 * An extruded (3D) polygon.
 *
 * This composable function inserts a [FillExtrusionLayer] to the map. For convenience, if there's
 * no need to hoist the [fillExtrusionLayerState], use `FillExtrusionLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#fill-extrusion)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param fillExtrusionLayerState the state holder for [FillExtrusionLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@OptIn(MapboxExperimental::class)
public fun FillExtrusionLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("fill-extrusion")
  },
  fillExtrusionLayerState: FillExtrusionLayerState = remember { FillExtrusionLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of FillExtrusionLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "fill-extrusion",
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
    key(fillExtrusionLayerState) {
      fillExtrusionLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(fillExtrusionLayerState.interactionsState) {
    fillExtrusionLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * An extruded (3D) polygon.
 *
 * This composable function inserts a [FillExtrusionLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#fill-extrusion)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [FillExtrusionLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun FillExtrusionLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("fill-extrusion")
  },
  crossinline init: FillExtrusionLayerState.() -> Unit
) {
  FillExtrusionLayer(sourceState = sourceState, layerId = layerId, fillExtrusionLayerState = remember { FillExtrusionLayerState() }.apply(init))
}

// End of generated file.