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
 * Raster map textures such as satellite imagery.
 *
 * This composable function inserts a [RasterLayer] to the map. For convenience, if there's
 * no need to hoist the [rasterLayerState], use `RasterLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#raster)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param rasterLayerState the state holder for [RasterLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@OptIn(MapboxExperimental::class)
public fun RasterLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("raster")
  },
  rasterLayerState: RasterLayerState = remember { RasterLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of RasterLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "raster",
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
    key(rasterLayerState) {
      rasterLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(rasterLayerState.interactionsState) {
    rasterLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * Raster map textures such as satellite imagery.
 *
 * This composable function inserts a [RasterLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#raster)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [RasterLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun RasterLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("raster")
  },
  crossinline init: RasterLayerState.() -> Unit
) {
  RasterLayer(sourceState = sourceState, layerId = layerId, rasterLayerState = remember { RasterLayerState() }.apply(init))
}

// End of generated file.