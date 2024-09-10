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
 * Client-side hillshading visualization based on DEM data. Currently, the implementation only supports Mapbox Terrain RGB and Mapzen Terrarium tiles.
 *
 * This composable function inserts a [HillshadeLayer] to the map. For convenience, if there's
 * no need to hoist the [hillshadeLayerState], use `HillshadeLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#hillshade)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param hillshadeLayerState the state holder for [HillshadeLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@OptIn(MapboxExperimental::class)
public fun HillshadeLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("hillshade")
  },
  hillshadeLayerState: HillshadeLayerState = remember { HillshadeLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of HillshadeLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "hillshade",
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
    key(hillshadeLayerState) {
      hillshadeLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(hillshadeLayerState.interactionsState) {
    hillshadeLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * Client-side hillshading visualization based on DEM data. Currently, the implementation only supports Mapbox Terrain RGB and Mapzen Terrarium tiles.
 *
 * This composable function inserts a [HillshadeLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#hillshade)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [HillshadeLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun HillshadeLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("hillshade")
  },
  crossinline init: HillshadeLayerState.() -> Unit
) {
  HillshadeLayer(sourceState = sourceState, layerId = layerId, hillshadeLayerState = remember { HillshadeLayerState() }.apply(init))
}

// End of generated file.