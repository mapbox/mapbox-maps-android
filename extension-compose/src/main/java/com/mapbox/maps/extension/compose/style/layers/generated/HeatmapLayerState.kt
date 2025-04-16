// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.ActionWhenNotInitial
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
public class HeatmapLayerState
@OptIn(MapboxExperimental::class)
private constructor(
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
  @OptIn(MapboxExperimental::class)
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

  private val heatmapColorState: MutableState<ColorValue> = mutableStateOf(initialHeatmapColor)
  /**
   *  Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input. Default value: ["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"].
   */
  public var heatmapColor: ColorValue by heatmapColorState

  @MapboxExperimental
  private val heatmapColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialHeatmapColorUseTheme)
  /**
   *  Overrides applying of color theme for [heatmapColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var heatmapColorUseTheme: StringValue by heatmapColorUseThemeState

  private val heatmapIntensityState: MutableState<DoubleValue> = mutableStateOf(initialHeatmapIntensity)
  /**
   *  Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   */
  public var heatmapIntensity: DoubleValue by heatmapIntensityState

  private val heatmapIntensityTransitionState: MutableState<Transition> = mutableStateOf(initialHeatmapIntensityTransition)
  /**
   *  Defines the transition of [heatmapIntensity].
   */
  public var heatmapIntensityTransition: Transition by heatmapIntensityTransitionState

  private val heatmapOpacityState: MutableState<DoubleValue> = mutableStateOf(initialHeatmapOpacity)
  /**
   *  The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var heatmapOpacity: DoubleValue by heatmapOpacityState

  private val heatmapOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialHeatmapOpacityTransition)
  /**
   *  Defines the transition of [heatmapOpacity].
   */
  public var heatmapOpacityTransition: Transition by heatmapOpacityTransitionState

  private val heatmapRadiusState: MutableState<DoubleValue> = mutableStateOf(initialHeatmapRadius)
  /**
   *  Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   */
  public var heatmapRadius: DoubleValue by heatmapRadiusState

  private val heatmapRadiusTransitionState: MutableState<Transition> = mutableStateOf(initialHeatmapRadiusTransition)
  /**
   *  Defines the transition of [heatmapRadius].
   */
  public var heatmapRadiusTransition: Transition by heatmapRadiusTransitionState

  private val heatmapWeightState: MutableState<DoubleValue> = mutableStateOf(initialHeatmapWeight)
  /**
   *  A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
   */
  public var heatmapWeight: DoubleValue by heatmapWeightState

  private val visibilityState: MutableState<VisibilityValue> = mutableStateOf(initialVisibility)
  /**
   *  Whether this layer is displayed. Default value: "visible".
   */
  public var visibility: VisibilityValue by visibilityState

  private val minZoomState: MutableState<LongValue> = mutableStateOf(initialMinZoom)
  /**
   *  The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var minZoom: LongValue by minZoomState

  private val maxZoomState: MutableState<LongValue> = mutableStateOf(initialMaxZoom)
  /**
   *  The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var maxZoom: LongValue by maxZoomState

  private val sourceLayerState: MutableState<StringValue> = mutableStateOf(initialSourceLayer)
  /**
   *  Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
   */
  public var sourceLayer: StringValue by sourceLayerState

  private val filterState: MutableState<Filter> = mutableStateOf(initialFilter)
  /**
   *  An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
   */
  public var filter: Filter by filterState

  @Composable
  @OptIn(MapboxExperimental::class)
  internal fun UpdateProperties(layerNode: LayerNode) {
    ActionWhenNotInitial(layerNode.setPropertyAction, heatmapColorState, "heatmap-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, heatmapColorUseThemeState, "heatmap-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, heatmapIntensityState, "heatmap-intensity")
    ActionWhenNotInitial(layerNode.setPropertyAction, heatmapIntensityTransitionState, "heatmap-intensity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, heatmapOpacityState, "heatmap-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, heatmapOpacityTransitionState, "heatmap-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, heatmapRadiusState, "heatmap-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, heatmapRadiusTransitionState, "heatmap-radius-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, heatmapWeightState, "heatmap-weight")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.