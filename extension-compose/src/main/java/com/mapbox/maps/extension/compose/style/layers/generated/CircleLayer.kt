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
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * A filled circle.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#circle)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param circleSortKey Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
 * @param circleBlur Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
 * @param circleColor The fill color of the circle.
 * @param circleEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param circleOpacity The opacity at which the circle will be drawn.
 * @param circlePitchAlignment Orientation of circle when map is pitched.
 * @param circlePitchScale Controls the scaling behavior of the circle when the map is pitched.
 * @param circleRadius Circle radius.
 * @param circleStrokeColor The stroke color of the circle.
 * @param circleStrokeOpacity The opacity of the circle's stroke.
 * @param circleStrokeWidth The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
 * @param circleTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
 * @param circleTranslateAnchor Controls the frame of reference for `circle-translate`.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun CircleLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("circle")
  },
  circleSortKey: CircleSortKey = CircleSortKey.default,
  circleBlur: CircleBlur = CircleBlur.default,
  circleBlurTransition: Transition = Transition.default,
  circleColor: CircleColor = CircleColor.default,
  circleColorTransition: Transition = Transition.default,
  circleEmissiveStrength: CircleEmissiveStrength = CircleEmissiveStrength.default,
  circleEmissiveStrengthTransition: Transition = Transition.default,
  circleOpacity: CircleOpacity = CircleOpacity.default,
  circleOpacityTransition: Transition = Transition.default,
  circlePitchAlignment: CirclePitchAlignment = CirclePitchAlignment.default,
  circlePitchScale: CirclePitchScale = CirclePitchScale.default,
  circleRadius: CircleRadius = CircleRadius.default,
  circleRadiusTransition: Transition = Transition.default,
  circleStrokeColor: CircleStrokeColor = CircleStrokeColor.default,
  circleStrokeColorTransition: Transition = Transition.default,
  circleStrokeOpacity: CircleStrokeOpacity = CircleStrokeOpacity.default,
  circleStrokeOpacityTransition: Transition = Transition.default,
  circleStrokeWidth: CircleStrokeWidth = CircleStrokeWidth.default,
  circleStrokeWidthTransition: Transition = Transition.default,
  circleTranslate: CircleTranslate = CircleTranslate.default,
  circleTranslateTransition: Transition = Transition.default,
  circleTranslateAnchor: CircleTranslateAnchor = CircleTranslateAnchor.default,
  visibility: Visibility = Visibility.default,
  minZoom: MinZoom = MinZoom.default,
  maxZoom: MaxZoom = MaxZoom.default,
  sourceLayer: SourceLayer = SourceLayer.default,
  filter: Filter = Filter.default,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of SymbolLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "circle",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (circleSortKey != CircleSortKey.default) {
          setProperty(CircleSortKey.NAME, circleSortKey.value)
        }
        if (circleBlur != CircleBlur.default) {
          setProperty(CircleBlur.NAME, circleBlur.value)
        }
        if (circleBlurTransition != Transition.default) {
          setProperty(CircleBlur.TRANSITION_NAME, circleBlurTransition.value)
        }
        if (circleColor != CircleColor.default) {
          setProperty(CircleColor.NAME, circleColor.value)
        }
        if (circleColorTransition != Transition.default) {
          setProperty(CircleColor.TRANSITION_NAME, circleColorTransition.value)
        }
        if (circleEmissiveStrength != CircleEmissiveStrength.default) {
          setProperty(CircleEmissiveStrength.NAME, circleEmissiveStrength.value)
        }
        if (circleEmissiveStrengthTransition != Transition.default) {
          setProperty(CircleEmissiveStrength.TRANSITION_NAME, circleEmissiveStrengthTransition.value)
        }
        if (circleOpacity != CircleOpacity.default) {
          setProperty(CircleOpacity.NAME, circleOpacity.value)
        }
        if (circleOpacityTransition != Transition.default) {
          setProperty(CircleOpacity.TRANSITION_NAME, circleOpacityTransition.value)
        }
        if (circlePitchAlignment != CirclePitchAlignment.default) {
          setProperty(CirclePitchAlignment.NAME, circlePitchAlignment.value)
        }
        if (circlePitchScale != CirclePitchScale.default) {
          setProperty(CirclePitchScale.NAME, circlePitchScale.value)
        }
        if (circleRadius != CircleRadius.default) {
          setProperty(CircleRadius.NAME, circleRadius.value)
        }
        if (circleRadiusTransition != Transition.default) {
          setProperty(CircleRadius.TRANSITION_NAME, circleRadiusTransition.value)
        }
        if (circleStrokeColor != CircleStrokeColor.default) {
          setProperty(CircleStrokeColor.NAME, circleStrokeColor.value)
        }
        if (circleStrokeColorTransition != Transition.default) {
          setProperty(CircleStrokeColor.TRANSITION_NAME, circleStrokeColorTransition.value)
        }
        if (circleStrokeOpacity != CircleStrokeOpacity.default) {
          setProperty(CircleStrokeOpacity.NAME, circleStrokeOpacity.value)
        }
        if (circleStrokeOpacityTransition != Transition.default) {
          setProperty(CircleStrokeOpacity.TRANSITION_NAME, circleStrokeOpacityTransition.value)
        }
        if (circleStrokeWidth != CircleStrokeWidth.default) {
          setProperty(CircleStrokeWidth.NAME, circleStrokeWidth.value)
        }
        if (circleStrokeWidthTransition != Transition.default) {
          setProperty(CircleStrokeWidth.TRANSITION_NAME, circleStrokeWidthTransition.value)
        }
        if (circleTranslate != CircleTranslate.default) {
          setProperty(CircleTranslate.NAME, circleTranslate.value)
        }
        if (circleTranslateTransition != Transition.default) {
          setProperty(CircleTranslate.TRANSITION_NAME, circleTranslateTransition.value)
        }
        if (circleTranslateAnchor != CircleTranslateAnchor.default) {
          setProperty(CircleTranslateAnchor.NAME, circleTranslateAnchor.value)
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
        if (sourceLayer != SourceLayer.default) {
          setProperty(SourceLayer.NAME, sourceLayer.value)
        }
        if (filter != Filter.default) {
          setProperty(Filter.NAME, filter.value)
        }
      }
      update(sourceState) {
        updateSource(sourceState)
      }
      update(layerId) {
        updateLayerId(layerId)
      }
      update(circleSortKey) {
        setProperty(CircleSortKey.NAME, circleSortKey.value)
      }
      update(circleBlur) {
        setProperty(CircleBlur.NAME, circleBlur.value)
      }
      update(circleBlurTransition) {
        setProperty(CircleBlur.TRANSITION_NAME, circleBlurTransition.value)
      }
      update(circleColor) {
        setProperty(CircleColor.NAME, circleColor.value)
      }
      update(circleColorTransition) {
        setProperty(CircleColor.TRANSITION_NAME, circleColorTransition.value)
      }
      update(circleEmissiveStrength) {
        setProperty(CircleEmissiveStrength.NAME, circleEmissiveStrength.value)
      }
      update(circleEmissiveStrengthTransition) {
        setProperty(CircleEmissiveStrength.TRANSITION_NAME, circleEmissiveStrengthTransition.value)
      }
      update(circleOpacity) {
        setProperty(CircleOpacity.NAME, circleOpacity.value)
      }
      update(circleOpacityTransition) {
        setProperty(CircleOpacity.TRANSITION_NAME, circleOpacityTransition.value)
      }
      update(circlePitchAlignment) {
        setProperty(CirclePitchAlignment.NAME, circlePitchAlignment.value)
      }
      update(circlePitchScale) {
        setProperty(CirclePitchScale.NAME, circlePitchScale.value)
      }
      update(circleRadius) {
        setProperty(CircleRadius.NAME, circleRadius.value)
      }
      update(circleRadiusTransition) {
        setProperty(CircleRadius.TRANSITION_NAME, circleRadiusTransition.value)
      }
      update(circleStrokeColor) {
        setProperty(CircleStrokeColor.NAME, circleStrokeColor.value)
      }
      update(circleStrokeColorTransition) {
        setProperty(CircleStrokeColor.TRANSITION_NAME, circleStrokeColorTransition.value)
      }
      update(circleStrokeOpacity) {
        setProperty(CircleStrokeOpacity.NAME, circleStrokeOpacity.value)
      }
      update(circleStrokeOpacityTransition) {
        setProperty(CircleStrokeOpacity.TRANSITION_NAME, circleStrokeOpacityTransition.value)
      }
      update(circleStrokeWidth) {
        setProperty(CircleStrokeWidth.NAME, circleStrokeWidth.value)
      }
      update(circleStrokeWidthTransition) {
        setProperty(CircleStrokeWidth.TRANSITION_NAME, circleStrokeWidthTransition.value)
      }
      update(circleTranslate) {
        setProperty(CircleTranslate.NAME, circleTranslate.value)
      }
      update(circleTranslateTransition) {
        setProperty(CircleTranslate.TRANSITION_NAME, circleTranslateTransition.value)
      }
      update(circleTranslateAnchor) {
        setProperty(CircleTranslateAnchor.NAME, circleTranslateAnchor.value)
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
      update(sourceLayer) {
        setProperty(SourceLayer.NAME, sourceLayer.value)
      }
      update(filter) {
        setProperty(Filter.NAME, filter.value)
      }
    }
  )
}
// End of generated file.