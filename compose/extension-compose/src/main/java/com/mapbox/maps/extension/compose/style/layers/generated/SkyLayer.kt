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
 * A spherical dome around the map that is always rendered behind all other layers.
 *
 * This composable function inserts a [SkyLayer] to the map. For convenience, if there's
 * no need to hoist the [skyLayerState], use `SkyLayer(layerId, init)` with trailing lambda instead.
 *
 * **Warning**: As of v10.6.0, [Atmosphere] is the preferred method for atmospheric styling.
 * Sky layer is not supported by the globe projection, and will be phased out in future major release.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#sky)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param skyLayerState the state holder for [SkyLayer]'s properties.
 */
@Composable
@MapboxMapComposable
public fun SkyLayer(
  layerId: String = remember {
    generateRandomLayerId("sky")
  },
  skyLayerState: SkyLayerState = remember { SkyLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of SkyLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "sky",
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
    key(skyLayerState) {
      skyLayerState.UpdateProperties(layerNode)
    }
  }
}

/**
 * A spherical dome around the map that is always rendered behind all other layers.
 *
 * This composable function inserts a [SkyLayer] to the map.
 *
 * **Warning**: As of v10.6.0, [Atmosphere] is the preferred method for atmospheric styling.
 * Sky layer is not supported by the globe projection, and will be phased out in future major release.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#sky)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [SkyLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun SkyLayer(
  layerId: String = remember {
    generateRandomLayerId("sky")
  },
  crossinline init: SkyLayerState.() -> Unit
) {
  SkyLayer(layerId = layerId, skyLayerState = remember { SkyLayerState() }.apply(init))
}

// End of generated file.