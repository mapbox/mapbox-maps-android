// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The background color or pattern of the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#background)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param backgroundColor The color with which the background will be drawn.
 * @param backgroundEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param backgroundOpacity The opacity at which the background will be drawn.
 * @param backgroundPattern Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun BackgroundLayer(
  layerId: String = remember {
    generateRandomLayerId("background")
  },
  backgroundColor: BackgroundColor = BackgroundColor.default,
  backgroundColorTransition: Transition = Transition.default,
  backgroundEmissiveStrength: BackgroundEmissiveStrength = BackgroundEmissiveStrength.default,
  backgroundEmissiveStrengthTransition: Transition = Transition.default,
  backgroundOpacity: BackgroundOpacity = BackgroundOpacity.default,
  backgroundOpacityTransition: Transition = Transition.default,
  backgroundPattern: BackgroundPattern = BackgroundPattern.default,
  visibility: Visibility = Visibility.default,
  minZoom: MinZoom = MinZoom.default,
  maxZoom: MaxZoom = MaxZoom.default,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of SymbolLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "background",
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (backgroundColor != BackgroundColor.default) {
          setProperty(BackgroundColor.NAME, backgroundColor.value)
        }
        if (backgroundColorTransition != Transition.default) {
          setProperty(BackgroundColor.TRANSITION_NAME, backgroundColorTransition.value)
        }
        if (backgroundEmissiveStrength != BackgroundEmissiveStrength.default) {
          setProperty(BackgroundEmissiveStrength.NAME, backgroundEmissiveStrength.value)
        }
        if (backgroundEmissiveStrengthTransition != Transition.default) {
          setProperty(BackgroundEmissiveStrength.TRANSITION_NAME, backgroundEmissiveStrengthTransition.value)
        }
        if (backgroundOpacity != BackgroundOpacity.default) {
          setProperty(BackgroundOpacity.NAME, backgroundOpacity.value)
        }
        if (backgroundOpacityTransition != Transition.default) {
          setProperty(BackgroundOpacity.TRANSITION_NAME, backgroundOpacityTransition.value)
        }
        if (backgroundPattern != BackgroundPattern.default) {
          setProperty(BackgroundPattern.NAME, backgroundPattern.value)
        }
        if (visibility != Visibility.default) {
          setProperty(Visibility.NAME, visibility.value)
        }
        if (minZoom != MinZoom.default) {
          setProperty(MinZoom.NAME, minZoom.value)
        }
        if (maxZoom != MaxZoom.default) {
          setProperty(MaxZoom.NAME, maxZoom.value)
        }
      }
      update(layerId) {
        updateLayerId(layerId)
      }
      update(backgroundColor) {
        setProperty(BackgroundColor.NAME, backgroundColor.value)
      }
      update(backgroundColorTransition) {
        setProperty(BackgroundColor.TRANSITION_NAME, backgroundColorTransition.value)
      }
      update(backgroundEmissiveStrength) {
        setProperty(BackgroundEmissiveStrength.NAME, backgroundEmissiveStrength.value)
      }
      update(backgroundEmissiveStrengthTransition) {
        setProperty(BackgroundEmissiveStrength.TRANSITION_NAME, backgroundEmissiveStrengthTransition.value)
      }
      update(backgroundOpacity) {
        setProperty(BackgroundOpacity.NAME, backgroundOpacity.value)
      }
      update(backgroundOpacityTransition) {
        setProperty(BackgroundOpacity.TRANSITION_NAME, backgroundOpacityTransition.value)
      }
      update(backgroundPattern) {
        setProperty(BackgroundPattern.NAME, backgroundPattern.value)
      }
      update(visibility) {
        setProperty(Visibility.NAME, visibility.value)
      }
      update(minZoom) {
        setProperty(MinZoom.NAME, minZoom.value)
      }
      update(maxZoom) {
        setProperty(MaxZoom.NAME, maxZoom.value)
      }
    }
  )
}
// End of generated file.