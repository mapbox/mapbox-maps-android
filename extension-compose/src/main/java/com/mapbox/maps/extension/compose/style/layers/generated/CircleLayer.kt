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
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
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
 * @param circleBlurTransition Defines the transition of [circleBlur].
 * @param circleColor The fill color of the circle.
 * @param circleColorTransition Defines the transition of [circleColor].
 * @param circleEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param circleEmissiveStrengthTransition Defines the transition of [circleEmissiveStrength].
 * @param circleOpacity The opacity at which the circle will be drawn.
 * @param circleOpacityTransition Defines the transition of [circleOpacity].
 * @param circlePitchAlignment Orientation of circle when map is pitched.
 * @param circlePitchScale Controls the scaling behavior of the circle when the map is pitched.
 * @param circleRadius Circle radius.
 * @param circleRadiusTransition Defines the transition of [circleRadius].
 * @param circleStrokeColor The stroke color of the circle.
 * @param circleStrokeColorTransition Defines the transition of [circleStrokeColor].
 * @param circleStrokeOpacity The opacity of the circle's stroke.
 * @param circleStrokeOpacityTransition Defines the transition of [circleStrokeOpacity].
 * @param circleStrokeWidth The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
 * @param circleStrokeWidthTransition Defines the transition of [circleStrokeWidth].
 * @param circleTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
 * @param circleTranslateTransition Defines the transition of [circleTranslate].
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
  circleSortKey: DoubleValue = DoubleValue.INITIAL,
  circleBlur: DoubleValue = DoubleValue.INITIAL,
  circleBlurTransition: Transition = Transition.INITIAL,
  circleColor: ColorValue = ColorValue.INITIAL,
  circleColorTransition: Transition = Transition.INITIAL,
  circleEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  circleEmissiveStrengthTransition: Transition = Transition.INITIAL,
  circleOpacity: DoubleValue = DoubleValue.INITIAL,
  circleOpacityTransition: Transition = Transition.INITIAL,
  circlePitchAlignment: CirclePitchAlignmentValue = CirclePitchAlignmentValue.INITIAL,
  circlePitchScale: CirclePitchScaleValue = CirclePitchScaleValue.INITIAL,
  circleRadius: DoubleValue = DoubleValue.INITIAL,
  circleRadiusTransition: Transition = Transition.INITIAL,
  circleStrokeColor: ColorValue = ColorValue.INITIAL,
  circleStrokeColorTransition: Transition = Transition.INITIAL,
  circleStrokeOpacity: DoubleValue = DoubleValue.INITIAL,
  circleStrokeOpacityTransition: Transition = Transition.INITIAL,
  circleStrokeWidth: DoubleValue = DoubleValue.INITIAL,
  circleStrokeWidthTransition: Transition = Transition.INITIAL,
  circleTranslate: DoubleListValue = DoubleListValue.INITIAL,
  circleTranslateTransition: Transition = Transition.INITIAL,
  circleTranslateAnchor: CircleTranslateAnchorValue = CircleTranslateAnchorValue.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of CircleLayer inside unsupported composable function")

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
        if (circleSortKey.notInitial) {
          setProperty("circle-sort-key", circleSortKey.value)
        }
        if (circleBlur.notInitial) {
          setProperty("circle-blur", circleBlur.value)
        }
        if (circleBlurTransition.notInitial) {
          setProperty("circle-blur-transition", circleBlurTransition.value)
        }
        if (circleColor.notInitial) {
          setProperty("circle-color", circleColor.value)
        }
        if (circleColorTransition.notInitial) {
          setProperty("circle-color-transition", circleColorTransition.value)
        }
        if (circleEmissiveStrength.notInitial) {
          setProperty("circle-emissive-strength", circleEmissiveStrength.value)
        }
        if (circleEmissiveStrengthTransition.notInitial) {
          setProperty("circle-emissive-strength-transition", circleEmissiveStrengthTransition.value)
        }
        if (circleOpacity.notInitial) {
          setProperty("circle-opacity", circleOpacity.value)
        }
        if (circleOpacityTransition.notInitial) {
          setProperty("circle-opacity-transition", circleOpacityTransition.value)
        }
        if (circlePitchAlignment.notInitial) {
          setProperty("circle-pitch-alignment", circlePitchAlignment.value)
        }
        if (circlePitchScale.notInitial) {
          setProperty("circle-pitch-scale", circlePitchScale.value)
        }
        if (circleRadius.notInitial) {
          setProperty("circle-radius", circleRadius.value)
        }
        if (circleRadiusTransition.notInitial) {
          setProperty("circle-radius-transition", circleRadiusTransition.value)
        }
        if (circleStrokeColor.notInitial) {
          setProperty("circle-stroke-color", circleStrokeColor.value)
        }
        if (circleStrokeColorTransition.notInitial) {
          setProperty("circle-stroke-color-transition", circleStrokeColorTransition.value)
        }
        if (circleStrokeOpacity.notInitial) {
          setProperty("circle-stroke-opacity", circleStrokeOpacity.value)
        }
        if (circleStrokeOpacityTransition.notInitial) {
          setProperty("circle-stroke-opacity-transition", circleStrokeOpacityTransition.value)
        }
        if (circleStrokeWidth.notInitial) {
          setProperty("circle-stroke-width", circleStrokeWidth.value)
        }
        if (circleStrokeWidthTransition.notInitial) {
          setProperty("circle-stroke-width-transition", circleStrokeWidthTransition.value)
        }
        if (circleTranslate.notInitial) {
          setProperty("circle-translate", circleTranslate.value)
        }
        if (circleTranslateTransition.notInitial) {
          setProperty("circle-translate-transition", circleTranslateTransition.value)
        }
        if (circleTranslateAnchor.notInitial) {
          setProperty("circle-translate-anchor", circleTranslateAnchor.value)
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
        if (sourceLayer.notInitial) {
          setProperty("source-layer", sourceLayer.value)
        }
        if (filter.notInitial) {
          setProperty("filter", filter.value)
        }
      }
      update(sourceState) {
        updateSource(sourceState)
      }
      update(layerId) {
        updateLayerId(layerId)
      }
      update(circleSortKey) {
        setProperty("circle-sort-key", circleSortKey.value)
      }
      update(circleBlur) {
        setProperty("circle-blur", circleBlur.value)
      }
      update(circleBlurTransition) {
        setProperty("circle-blur-transition", circleBlurTransition.value)
      }
      update(circleColor) {
        setProperty("circle-color", circleColor.value)
      }
      update(circleColorTransition) {
        setProperty("circle-color-transition", circleColorTransition.value)
      }
      update(circleEmissiveStrength) {
        setProperty("circle-emissive-strength", circleEmissiveStrength.value)
      }
      update(circleEmissiveStrengthTransition) {
        setProperty("circle-emissive-strength-transition", circleEmissiveStrengthTransition.value)
      }
      update(circleOpacity) {
        setProperty("circle-opacity", circleOpacity.value)
      }
      update(circleOpacityTransition) {
        setProperty("circle-opacity-transition", circleOpacityTransition.value)
      }
      update(circlePitchAlignment) {
        setProperty("circle-pitch-alignment", circlePitchAlignment.value)
      }
      update(circlePitchScale) {
        setProperty("circle-pitch-scale", circlePitchScale.value)
      }
      update(circleRadius) {
        setProperty("circle-radius", circleRadius.value)
      }
      update(circleRadiusTransition) {
        setProperty("circle-radius-transition", circleRadiusTransition.value)
      }
      update(circleStrokeColor) {
        setProperty("circle-stroke-color", circleStrokeColor.value)
      }
      update(circleStrokeColorTransition) {
        setProperty("circle-stroke-color-transition", circleStrokeColorTransition.value)
      }
      update(circleStrokeOpacity) {
        setProperty("circle-stroke-opacity", circleStrokeOpacity.value)
      }
      update(circleStrokeOpacityTransition) {
        setProperty("circle-stroke-opacity-transition", circleStrokeOpacityTransition.value)
      }
      update(circleStrokeWidth) {
        setProperty("circle-stroke-width", circleStrokeWidth.value)
      }
      update(circleStrokeWidthTransition) {
        setProperty("circle-stroke-width-transition", circleStrokeWidthTransition.value)
      }
      update(circleTranslate) {
        setProperty("circle-translate", circleTranslate.value)
      }
      update(circleTranslateTransition) {
        setProperty("circle-translate-transition", circleTranslateTransition.value)
      }
      update(circleTranslateAnchor) {
        setProperty("circle-translate-anchor", circleTranslateAnchor.value)
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
      update(sourceLayer) {
        setProperty("source-layer", sourceLayer.value)
      }
      update(filter) {
        setProperty("filter", filter.value)
      }
    }
  )
}
// End of generated file.