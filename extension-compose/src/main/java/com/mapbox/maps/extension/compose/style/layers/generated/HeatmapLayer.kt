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
 * A heatmap.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#heatmap)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param heatmapColor Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input.
 * @param heatmapIntensity Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
 * @param heatmapOpacity The global opacity at which the heatmap layer will be drawn.
 * @param heatmapRadius Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
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
  heatmapColor: HeatmapColor = HeatmapColor.default,
  heatmapIntensity: HeatmapIntensity = HeatmapIntensity.default,
  heatmapIntensityTransition: Transition = Transition.default,
  heatmapOpacity: HeatmapOpacity = HeatmapOpacity.default,
  heatmapOpacityTransition: Transition = Transition.default,
  heatmapRadius: HeatmapRadius = HeatmapRadius.default,
  heatmapRadiusTransition: Transition = Transition.default,
  heatmapWeight: HeatmapWeight = HeatmapWeight.default,
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
        layerType = "heatmap",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (heatmapColor != HeatmapColor.default) {
          setProperty(HeatmapColor.NAME, heatmapColor.value)
        }
        if (heatmapIntensity != HeatmapIntensity.default) {
          setProperty(HeatmapIntensity.NAME, heatmapIntensity.value)
        }
        if (heatmapIntensityTransition != Transition.default) {
          setProperty(HeatmapIntensity.TRANSITION_NAME, heatmapIntensityTransition.value)
        }
        if (heatmapOpacity != HeatmapOpacity.default) {
          setProperty(HeatmapOpacity.NAME, heatmapOpacity.value)
        }
        if (heatmapOpacityTransition != Transition.default) {
          setProperty(HeatmapOpacity.TRANSITION_NAME, heatmapOpacityTransition.value)
        }
        if (heatmapRadius != HeatmapRadius.default) {
          setProperty(HeatmapRadius.NAME, heatmapRadius.value)
        }
        if (heatmapRadiusTransition != Transition.default) {
          setProperty(HeatmapRadius.TRANSITION_NAME, heatmapRadiusTransition.value)
        }
        if (heatmapWeight != HeatmapWeight.default) {
          setProperty(HeatmapWeight.NAME, heatmapWeight.value)
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
      update(heatmapColor) {
        setProperty(HeatmapColor.NAME, heatmapColor.value)
      }
      update(heatmapIntensity) {
        setProperty(HeatmapIntensity.NAME, heatmapIntensity.value)
      }
      update(heatmapIntensityTransition) {
        setProperty(HeatmapIntensity.TRANSITION_NAME, heatmapIntensityTransition.value)
      }
      update(heatmapOpacity) {
        setProperty(HeatmapOpacity.NAME, heatmapOpacity.value)
      }
      update(heatmapOpacityTransition) {
        setProperty(HeatmapOpacity.TRANSITION_NAME, heatmapOpacityTransition.value)
      }
      update(heatmapRadius) {
        setProperty(HeatmapRadius.NAME, heatmapRadius.value)
      }
      update(heatmapRadiusTransition) {
        setProperty(HeatmapRadius.TRANSITION_NAME, heatmapRadiusTransition.value)
      }
      update(heatmapWeight) {
        setProperty(HeatmapWeight.NAME, heatmapWeight.value)
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