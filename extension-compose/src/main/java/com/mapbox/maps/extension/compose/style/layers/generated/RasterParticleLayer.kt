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
 * Particle animation driven by textures such as wind maps.
 *
 * This composable function inserts a [RasterParticleLayer] to the map. For convenience, if there's
 * no need to hoist the [rasterParticleLayerState], use `RasterParticleLayer(sourceState, layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#raster-particle)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param rasterParticleLayerState the state holder for [RasterParticleLayer]'s properties.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun RasterParticleLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("raster-particle")
  },
  rasterParticleLayerState: RasterParticleLayerState = remember { RasterParticleLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of RasterParticleLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "raster-particle",
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
    key(rasterParticleLayerState) {
      rasterParticleLayerState.UpdateProperties(layerNode)
    }
  }
  sourceState.UpdateProperties()
  key(rasterParticleLayerState.interactionsState) {
    rasterParticleLayerState.interactionsState.BindTo(
      mapboxMap = mapApplier.mapView.mapboxMap,
      layerId = layerId
    )
  }
}

/**
 * Particle animation driven by textures such as wind maps.
 *
 * This composable function inserts a [RasterParticleLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#raster-particle)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [RasterParticleLayerState].
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public inline fun RasterParticleLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("raster-particle")
  },
  crossinline init: RasterParticleLayerState.() -> Unit
) {
  RasterParticleLayer(sourceState = sourceState, layerId = layerId, rasterParticleLayerState = remember { RasterParticleLayerState() }.apply(init))
}

// End of generated file.