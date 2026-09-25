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
 * Location Indicator layer.
 *
 * This composable function inserts a [LocationIndicatorLayer] to the map. For convenience, if there's
 * no need to hoist the [locationIndicatorLayerState], use `LocationIndicatorLayer(layerId, init)` with trailing lambda instead.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#location-indicator)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param locationIndicatorLayerState the state holder for [LocationIndicatorLayer]'s properties.
 */
@Composable
@MapboxMapComposable
public fun LocationIndicatorLayer(
  layerId: String = remember {
    generateRandomLayerId("location-indicator")
  },
  locationIndicatorLayerState: LocationIndicatorLayerState = remember { LocationIndicatorLayerState() }
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of LocationIndicatorLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  val layerNode = remember {
    LayerNode(
      map = mapApplier.mapView.mapboxMap,
      layerType = "location-indicator",
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
    key(locationIndicatorLayerState) {
      locationIndicatorLayerState.UpdateProperties(layerNode)
    }
  }
}

/**
 * Location Indicator layer.
 *
 * This composable function inserts a [LocationIndicatorLayer] to the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#location-indicator)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param init the lambda that will be applied to the remembered [LocationIndicatorLayerState].
 */
@Composable
@MapboxMapComposable
public inline fun LocationIndicatorLayer(
  layerId: String = remember {
    generateRandomLayerId("location-indicator")
  },
  crossinline init: LocationIndicatorLayerState.() -> Unit
) {
  LocationIndicatorLayer(layerId = layerId, locationIndicatorLayerState = remember { LocationIndicatorLayerState() }.apply(init))
}

// End of generated file.