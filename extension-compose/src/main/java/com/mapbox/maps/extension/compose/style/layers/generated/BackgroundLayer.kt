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
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The background color or pattern of the map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#background)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param backgroundColor The color with which the background will be drawn.
 * @param backgroundColorTransition Defines the transition of [backgroundColor].
 * @param backgroundEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param backgroundEmissiveStrengthTransition Defines the transition of [backgroundEmissiveStrength].
 * @param backgroundOpacity The opacity at which the background will be drawn.
 * @param backgroundOpacityTransition Defines the transition of [backgroundOpacity].
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
  backgroundColor: ColorValue = ColorValue.INITIAL,
  backgroundColorTransition: Transition = Transition.INITIAL,
  backgroundEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  backgroundEmissiveStrengthTransition: Transition = Transition.INITIAL,
  backgroundOpacity: DoubleValue = DoubleValue.INITIAL,
  backgroundOpacityTransition: Transition = Transition.INITIAL,
  backgroundPattern: ImageValue = ImageValue.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of BackgroundLayer inside unsupported composable function")

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
        if (backgroundColor.notInitial) {
          setProperty("background-color", backgroundColor.value)
        }
        if (backgroundColorTransition.notInitial) {
          setProperty("background-color-transition", backgroundColorTransition.value)
        }
        if (backgroundEmissiveStrength.notInitial) {
          setProperty("background-emissive-strength", backgroundEmissiveStrength.value)
        }
        if (backgroundEmissiveStrengthTransition.notInitial) {
          setProperty("background-emissive-strength-transition", backgroundEmissiveStrengthTransition.value)
        }
        if (backgroundOpacity.notInitial) {
          setProperty("background-opacity", backgroundOpacity.value)
        }
        if (backgroundOpacityTransition.notInitial) {
          setProperty("background-opacity-transition", backgroundOpacityTransition.value)
        }
        if (backgroundPattern.notInitial) {
          backgroundPattern.styleImage?.let {
            addImage(it)
          }
          setProperty("background-pattern", backgroundPattern.value)
        }
        if (visibility.notInitial) {
          setProperty("visibility", visibility.value)
        }
        if (minZoom.notInitial) {
          setProperty("min-zoom", minZoom.value)
        }
        if (maxZoom.notInitial) {
          setProperty("max-zoom", maxZoom.value)
        }
      }
      update(layerId) {
        updateLayerId(layerId)
      }
      update(backgroundColor) {
        setProperty("background-color", backgroundColor.value)
      }
      update(backgroundColorTransition) {
        setProperty("background-color-transition", backgroundColorTransition.value)
      }
      update(backgroundEmissiveStrength) {
        setProperty("background-emissive-strength", backgroundEmissiveStrength.value)
      }
      update(backgroundEmissiveStrengthTransition) {
        setProperty("background-emissive-strength-transition", backgroundEmissiveStrengthTransition.value)
      }
      update(backgroundOpacity) {
        setProperty("background-opacity", backgroundOpacity.value)
      }
      update(backgroundOpacityTransition) {
        setProperty("background-opacity-transition", backgroundOpacityTransition.value)
      }
      update(backgroundPattern) {
        backgroundPattern.styleImage?.let {
          addImage(it)
        }
        setProperty("background-pattern", backgroundPattern.value)
      }
      update(visibility) {
        setProperty("visibility", visibility.value)
      }
      update(minZoom) {
        setProperty("min-zoom", minZoom.value)
      }
      update(maxZoom) {
        setProperty("max-zoom", maxZoom.value)
      }
    }
  )
}
// End of generated file.