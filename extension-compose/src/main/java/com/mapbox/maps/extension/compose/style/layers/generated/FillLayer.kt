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
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * A filled polygon with an optional stroked border.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#fill)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param fillSortKey Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
 * @param fillAntialias Whether or not the fill should be antialiased.
 * @param fillColor The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
 * @param fillColorTransition Defines the transition of [fillColor].
 * @param fillEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param fillEmissiveStrengthTransition Defines the transition of [fillEmissiveStrength].
 * @param fillOpacity The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
 * @param fillOpacityTransition Defines the transition of [fillOpacity].
 * @param fillOutlineColor The outline color of the fill. Matches the value of `fill-color` if unspecified.
 * @param fillOutlineColorTransition Defines the transition of [fillOutlineColor].
 * @param fillPattern Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 * @param fillTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
 * @param fillTranslateTransition Defines the transition of [fillTranslate].
 * @param fillTranslateAnchor Controls the frame of reference for `fill-translate`.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun FillLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("fill")
  },
  fillSortKey: DoubleValue = DoubleValue.INITIAL,
  fillAntialias: BooleanValue = BooleanValue.INITIAL,
  fillColor: ColorValue = ColorValue.INITIAL,
  fillColorTransition: Transition = Transition.INITIAL,
  fillEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  fillEmissiveStrengthTransition: Transition = Transition.INITIAL,
  fillOpacity: DoubleValue = DoubleValue.INITIAL,
  fillOpacityTransition: Transition = Transition.INITIAL,
  fillOutlineColor: ColorValue = ColorValue.INITIAL,
  fillOutlineColorTransition: Transition = Transition.INITIAL,
  fillPattern: ImageValue = ImageValue.INITIAL,
  fillTranslate: DoubleListValue = DoubleListValue.INITIAL,
  fillTranslateTransition: Transition = Transition.INITIAL,
  fillTranslateAnchor: FillTranslateAnchorValue = FillTranslateAnchorValue.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of FillLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "fill",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (fillSortKey.notInitial) {
          setProperty("fill-sort-key", fillSortKey.value)
        }
        if (fillAntialias.notInitial) {
          setProperty("fill-antialias", fillAntialias.value)
        }
        if (fillColor.notInitial) {
          setProperty("fill-color", fillColor.value)
        }
        if (fillColorTransition.notInitial) {
          setProperty("fill-color-transition", fillColorTransition.value)
        }
        if (fillEmissiveStrength.notInitial) {
          setProperty("fill-emissive-strength", fillEmissiveStrength.value)
        }
        if (fillEmissiveStrengthTransition.notInitial) {
          setProperty("fill-emissive-strength-transition", fillEmissiveStrengthTransition.value)
        }
        if (fillOpacity.notInitial) {
          setProperty("fill-opacity", fillOpacity.value)
        }
        if (fillOpacityTransition.notInitial) {
          setProperty("fill-opacity-transition", fillOpacityTransition.value)
        }
        if (fillOutlineColor.notInitial) {
          setProperty("fill-outline-color", fillOutlineColor.value)
        }
        if (fillOutlineColorTransition.notInitial) {
          setProperty("fill-outline-color-transition", fillOutlineColorTransition.value)
        }
        if (fillPattern.notInitial) {
          fillPattern.styleImage?.let {
            addImage(it)
          }
          setProperty("fill-pattern", fillPattern.value)
        }
        if (fillTranslate.notInitial) {
          setProperty("fill-translate", fillTranslate.value)
        }
        if (fillTranslateTransition.notInitial) {
          setProperty("fill-translate-transition", fillTranslateTransition.value)
        }
        if (fillTranslateAnchor.notInitial) {
          setProperty("fill-translate-anchor", fillTranslateAnchor.value)
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
      update(fillSortKey) {
        setProperty("fill-sort-key", fillSortKey.value)
      }
      update(fillAntialias) {
        setProperty("fill-antialias", fillAntialias.value)
      }
      update(fillColor) {
        setProperty("fill-color", fillColor.value)
      }
      update(fillColorTransition) {
        setProperty("fill-color-transition", fillColorTransition.value)
      }
      update(fillEmissiveStrength) {
        setProperty("fill-emissive-strength", fillEmissiveStrength.value)
      }
      update(fillEmissiveStrengthTransition) {
        setProperty("fill-emissive-strength-transition", fillEmissiveStrengthTransition.value)
      }
      update(fillOpacity) {
        setProperty("fill-opacity", fillOpacity.value)
      }
      update(fillOpacityTransition) {
        setProperty("fill-opacity-transition", fillOpacityTransition.value)
      }
      update(fillOutlineColor) {
        setProperty("fill-outline-color", fillOutlineColor.value)
      }
      update(fillOutlineColorTransition) {
        setProperty("fill-outline-color-transition", fillOutlineColorTransition.value)
      }
      update(fillPattern) {
        fillPattern.styleImage?.let {
          addImage(it)
        }
        setProperty("fill-pattern", fillPattern.value)
      }
      update(fillTranslate) {
        setProperty("fill-translate", fillTranslate.value)
      }
      update(fillTranslateTransition) {
        setProperty("fill-translate-transition", fillTranslateTransition.value)
      }
      update(fillTranslateAnchor) {
        setProperty("fill-translate-anchor", fillTranslateAnchor.value)
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