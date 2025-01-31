// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [HeatmapLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#heatmap)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class HeatmapLayerState private constructor(
  initialHeatmapColor: ColorValue,
  initialHeatmapColorUseTheme: StringValue,
  initialHeatmapIntensity: DoubleValue,
  initialHeatmapIntensityTransition: Transition,
  initialHeatmapOpacity: DoubleValue,
  initialHeatmapOpacityTransition: Transition,
  initialHeatmapRadius: DoubleValue,
  initialHeatmapRadiusTransition: Transition,
  initialHeatmapWeight: DoubleValue,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [HeatmapLayerState].
   */
  public constructor() : this(
    initialHeatmapColor = ColorValue.INITIAL,
    initialHeatmapColorUseTheme = StringValue.INITIAL,
    initialHeatmapIntensity = DoubleValue.INITIAL,
    initialHeatmapIntensityTransition = Transition.INITIAL,
    initialHeatmapOpacity = DoubleValue.INITIAL,
    initialHeatmapOpacityTransition = Transition.INITIAL,
    initialHeatmapRadius = DoubleValue.INITIAL,
    initialHeatmapRadiusTransition = Transition.INITIAL,
    initialHeatmapWeight = DoubleValue.INITIAL,
    initialVisibility = VisibilityValue.INITIAL,
    initialMinZoom = LongValue.INITIAL,
    initialMaxZoom = LongValue.INITIAL,
    initialSourceLayer = StringValue.INITIAL,
    initialFilter = Filter.INITIAL,
    initialInteractionsState = LayerInteractionsState(),
  )

  /**
   * The interactions associated with this layer.
   */
  @MapboxExperimental
  public var interactionsState: LayerInteractionsState by mutableStateOf(initialInteractionsState)

  /**
   *  Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input. Default value: ["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"].
   */
  public var heatmapColor: ColorValue by mutableStateOf(initialHeatmapColor)
  /**
   *  Overrides applying of color theme for [heatmapColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var heatmapColorUseTheme: StringValue by mutableStateOf(initialHeatmapColorUseTheme)
  /**
   *  Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   */
  public var heatmapIntensity: DoubleValue by mutableStateOf(initialHeatmapIntensity)
  /**
   *  Defines the transition of [heatmapIntensity].
   */
  public var heatmapIntensityTransition: Transition by mutableStateOf(initialHeatmapIntensityTransition)
  /**
   *  The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var heatmapOpacity: DoubleValue by mutableStateOf(initialHeatmapOpacity)
  /**
   *  Defines the transition of [heatmapOpacity].
   */
  public var heatmapOpacityTransition: Transition by mutableStateOf(initialHeatmapOpacityTransition)
  /**
   *  Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   */
  public var heatmapRadius: DoubleValue by mutableStateOf(initialHeatmapRadius)
  /**
   *  Defines the transition of [heatmapRadius].
   */
  public var heatmapRadiusTransition: Transition by mutableStateOf(initialHeatmapRadiusTransition)
  /**
   *  A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
   */
  public var heatmapWeight: DoubleValue by mutableStateOf(initialHeatmapWeight)
  /**
   *  Whether this layer is displayed. Default value: "visible".
   */
  public var visibility: VisibilityValue by mutableStateOf(initialVisibility)
  /**
   *  The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var minZoom: LongValue by mutableStateOf(initialMinZoom)
  /**
   *  The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var maxZoom: LongValue by mutableStateOf(initialMaxZoom)
  /**
   *  Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
   */
  public var sourceLayer: StringValue by mutableStateOf(initialSourceLayer)
  /**
   *  An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
   */
  public var filter: Filter by mutableStateOf(initialFilter)

  @Composable
  private fun UpdateHeatmapColor(layerNode: LayerNode) {
    if (heatmapColor.notInitial) {
      layerNode.setProperty("heatmap-color", heatmapColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateHeatmapColorUseTheme(layerNode: LayerNode) {
    if (heatmapColorUseTheme.notInitial) {
      layerNode.setProperty("heatmap-color-use-theme", heatmapColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateHeatmapIntensity(layerNode: LayerNode) {
    if (heatmapIntensity.notInitial) {
      layerNode.setProperty("heatmap-intensity", heatmapIntensity.value)
    }
  }
  @Composable
  private fun UpdateHeatmapIntensityTransition(layerNode: LayerNode) {
    if (heatmapIntensityTransition.notInitial) {
      layerNode.setProperty("heatmap-intensity-transition", heatmapIntensityTransition.value)
    }
  }
  @Composable
  private fun UpdateHeatmapOpacity(layerNode: LayerNode) {
    if (heatmapOpacity.notInitial) {
      layerNode.setProperty("heatmap-opacity", heatmapOpacity.value)
    }
  }
  @Composable
  private fun UpdateHeatmapOpacityTransition(layerNode: LayerNode) {
    if (heatmapOpacityTransition.notInitial) {
      layerNode.setProperty("heatmap-opacity-transition", heatmapOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateHeatmapRadius(layerNode: LayerNode) {
    if (heatmapRadius.notInitial) {
      layerNode.setProperty("heatmap-radius", heatmapRadius.value)
    }
  }
  @Composable
  private fun UpdateHeatmapRadiusTransition(layerNode: LayerNode) {
    if (heatmapRadiusTransition.notInitial) {
      layerNode.setProperty("heatmap-radius-transition", heatmapRadiusTransition.value)
    }
  }
  @Composable
  private fun UpdateHeatmapWeight(layerNode: LayerNode) {
    if (heatmapWeight.notInitial) {
      layerNode.setProperty("heatmap-weight", heatmapWeight.value)
    }
  }
  @Composable
  private fun UpdateVisibility(layerNode: LayerNode) {
    if (visibility.notInitial) {
      layerNode.setProperty("visibility", visibility.value)
    }
  }
  @Composable
  private fun UpdateMinZoom(layerNode: LayerNode) {
    if (minZoom.notInitial) {
      layerNode.setProperty("minzoom", minZoom.value)
    }
  }
  @Composable
  private fun UpdateMaxZoom(layerNode: LayerNode) {
    if (maxZoom.notInitial) {
      layerNode.setProperty("maxzoom", maxZoom.value)
    }
  }
  @Composable
  private fun UpdateSourceLayer(layerNode: LayerNode) {
    if (sourceLayer.notInitial) {
      layerNode.setProperty("source-layer", sourceLayer.value)
    }
  }
  @Composable
  private fun UpdateFilter(layerNode: LayerNode) {
    if (filter.notInitial) {
      layerNode.setProperty("filter", filter.value)
    }
  }

  @Composable
  internal fun UpdateProperties(layerNode: LayerNode) {
    UpdateHeatmapColor(layerNode)
    UpdateHeatmapColorUseTheme(layerNode)
    UpdateHeatmapIntensity(layerNode)
    UpdateHeatmapIntensityTransition(layerNode)
    UpdateHeatmapOpacity(layerNode)
    UpdateHeatmapOpacityTransition(layerNode)
    UpdateHeatmapRadius(layerNode)
    UpdateHeatmapRadiusTransition(layerNode)
    UpdateHeatmapWeight(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.