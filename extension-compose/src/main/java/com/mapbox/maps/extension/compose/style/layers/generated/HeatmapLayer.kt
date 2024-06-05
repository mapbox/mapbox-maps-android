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
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * A heatmap.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#heatmap)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param heatmapColor Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input.
 * @param heatmapIntensity Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
 * @param heatmapIntensityTransition Defines the transition of [heatmapIntensity].
 * @param heatmapOpacity The global opacity at which the heatmap layer will be drawn.
 * @param heatmapOpacityTransition Defines the transition of [heatmapOpacity].
 * @param heatmapRadius Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
 * @param heatmapRadiusTransition Defines the transition of [heatmapRadius].
 * @param heatmapWeight A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun HeatmapLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("heatmap")
  },
  heatmapColor: ColorValue = ColorValue.INITIAL,
  heatmapIntensity: DoubleValue = DoubleValue.INITIAL,
  heatmapIntensityTransition: Transition = Transition.INITIAL,
  heatmapOpacity: DoubleValue = DoubleValue.INITIAL,
  heatmapOpacityTransition: Transition = Transition.INITIAL,
  heatmapRadius: DoubleValue = DoubleValue.INITIAL,
  heatmapRadiusTransition: Transition = Transition.INITIAL,
  heatmapWeight: DoubleValue = DoubleValue.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of HeatmapLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "heatmap",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (heatmapColor.notInitial) {
          setProperty("heatmap-color", heatmapColor.value)
        }
        if (heatmapIntensity.notInitial) {
          setProperty("heatmap-intensity", heatmapIntensity.value)
        }
        if (heatmapIntensityTransition.notInitial) {
          setProperty("heatmap-intensity-transition", heatmapIntensityTransition.value)
        }
        if (heatmapOpacity.notInitial) {
          setProperty("heatmap-opacity", heatmapOpacity.value)
        }
        if (heatmapOpacityTransition.notInitial) {
          setProperty("heatmap-opacity-transition", heatmapOpacityTransition.value)
        }
        if (heatmapRadius.notInitial) {
          setProperty("heatmap-radius", heatmapRadius.value)
        }
        if (heatmapRadiusTransition.notInitial) {
          setProperty("heatmap-radius-transition", heatmapRadiusTransition.value)
        }
        if (heatmapWeight.notInitial) {
          setProperty("heatmap-weight", heatmapWeight.value)
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
      update(heatmapColor) {
        setProperty("heatmap-color", heatmapColor.value)
      }
      update(heatmapIntensity) {
        setProperty("heatmap-intensity", heatmapIntensity.value)
      }
      update(heatmapIntensityTransition) {
        setProperty("heatmap-intensity-transition", heatmapIntensityTransition.value)
      }
      update(heatmapOpacity) {
        setProperty("heatmap-opacity", heatmapOpacity.value)
      }
      update(heatmapOpacityTransition) {
        setProperty("heatmap-opacity-transition", heatmapOpacityTransition.value)
      }
      update(heatmapRadius) {
        setProperty("heatmap-radius", heatmapRadius.value)
      }
      update(heatmapRadiusTransition) {
        setProperty("heatmap-radius-transition", heatmapRadiusTransition.value)
      }
      update(heatmapWeight) {
        setProperty("heatmap-weight", heatmapWeight.value)
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