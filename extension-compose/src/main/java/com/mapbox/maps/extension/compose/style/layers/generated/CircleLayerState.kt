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
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [CircleLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#circle)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class CircleLayerState
@OptIn(MapboxExperimental::class)
private constructor(
  initialCircleSortKey: DoubleValue,
  initialCircleBlur: DoubleValue,
  initialCircleBlurTransition: Transition,
  initialCircleColor: ColorValue,
  initialCircleColorUseTheme: StringValue,
  initialCircleColorTransition: Transition,
  initialCircleEmissiveStrength: DoubleValue,
  initialCircleEmissiveStrengthTransition: Transition,
  initialCircleOpacity: DoubleValue,
  initialCircleOpacityTransition: Transition,
  initialCirclePitchAlignment: CirclePitchAlignmentValue,
  initialCirclePitchScale: CirclePitchScaleValue,
  initialCircleRadius: DoubleValue,
  initialCircleRadiusTransition: Transition,
  initialCircleStrokeColor: ColorValue,
  initialCircleStrokeColorUseTheme: StringValue,
  initialCircleStrokeColorTransition: Transition,
  initialCircleStrokeOpacity: DoubleValue,
  initialCircleStrokeOpacityTransition: Transition,
  initialCircleStrokeWidth: DoubleValue,
  initialCircleStrokeWidthTransition: Transition,
  initialCircleTranslate: DoubleListValue,
  initialCircleTranslateTransition: Transition,
  initialCircleTranslateAnchor: CircleTranslateAnchorValue,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [CircleLayerState].
   */
  @OptIn(MapboxExperimental::class)
  public constructor() : this(
    initialCircleSortKey = DoubleValue.INITIAL,
    initialCircleBlur = DoubleValue.INITIAL,
    initialCircleBlurTransition = Transition.INITIAL,
    initialCircleColor = ColorValue.INITIAL,
    initialCircleColorUseTheme = StringValue.INITIAL,
    initialCircleColorTransition = Transition.INITIAL,
    initialCircleEmissiveStrength = DoubleValue.INITIAL,
    initialCircleEmissiveStrengthTransition = Transition.INITIAL,
    initialCircleOpacity = DoubleValue.INITIAL,
    initialCircleOpacityTransition = Transition.INITIAL,
    initialCirclePitchAlignment = CirclePitchAlignmentValue.INITIAL,
    initialCirclePitchScale = CirclePitchScaleValue.INITIAL,
    initialCircleRadius = DoubleValue.INITIAL,
    initialCircleRadiusTransition = Transition.INITIAL,
    initialCircleStrokeColor = ColorValue.INITIAL,
    initialCircleStrokeColorUseTheme = StringValue.INITIAL,
    initialCircleStrokeColorTransition = Transition.INITIAL,
    initialCircleStrokeOpacity = DoubleValue.INITIAL,
    initialCircleStrokeOpacityTransition = Transition.INITIAL,
    initialCircleStrokeWidth = DoubleValue.INITIAL,
    initialCircleStrokeWidthTransition = Transition.INITIAL,
    initialCircleTranslate = DoubleListValue.INITIAL,
    initialCircleTranslateTransition = Transition.INITIAL,
    initialCircleTranslateAnchor = CircleTranslateAnchorValue.INITIAL,
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

  private val circleSortKeyState: MutableState<DoubleValue> = mutableStateOf(initialCircleSortKey)
  /**
   *  Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  public var circleSortKey: DoubleValue by circleSortKeyState

  private val circleBlurState: MutableState<DoubleValue> = mutableStateOf(initialCircleBlur)
  /**
   *  Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Setting a negative value renders the blur as an inner glow effect. Default value: 0.
   */
  public var circleBlur: DoubleValue by circleBlurState

  private val circleBlurTransitionState: MutableState<Transition> = mutableStateOf(initialCircleBlurTransition)
  /**
   *  Defines the transition of [circleBlur].
   */
  public var circleBlurTransition: Transition by circleBlurTransitionState

  private val circleColorState: MutableState<ColorValue> = mutableStateOf(initialCircleColor)
  /**
   *  The fill color of the circle. Default value: "#000000".
   */
  public var circleColor: ColorValue by circleColorState

  @MapboxExperimental
  private val circleColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialCircleColorUseTheme)
  /**
   *  Overrides applying of color theme for [circleColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var circleColorUseTheme: StringValue by circleColorUseThemeState

  private val circleColorTransitionState: MutableState<Transition> = mutableStateOf(initialCircleColorTransition)
  /**
   *  Defines the transition of [circleColor].
   */
  public var circleColorTransition: Transition by circleColorTransitionState

  private val circleEmissiveStrengthState: MutableState<DoubleValue> = mutableStateOf(initialCircleEmissiveStrength)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of circleEmissiveStrength is in intensity.
   */
  public var circleEmissiveStrength: DoubleValue by circleEmissiveStrengthState

  private val circleEmissiveStrengthTransitionState: MutableState<Transition> = mutableStateOf(initialCircleEmissiveStrengthTransition)
  /**
   *  Defines the transition of [circleEmissiveStrength].
   */
  public var circleEmissiveStrengthTransition: Transition by circleEmissiveStrengthTransitionState

  private val circleOpacityState: MutableState<DoubleValue> = mutableStateOf(initialCircleOpacity)
  /**
   *  The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var circleOpacity: DoubleValue by circleOpacityState

  private val circleOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialCircleOpacityTransition)
  /**
   *  Defines the transition of [circleOpacity].
   */
  public var circleOpacityTransition: Transition by circleOpacityTransitionState

  private val circlePitchAlignmentState: MutableState<CirclePitchAlignmentValue> = mutableStateOf(initialCirclePitchAlignment)
  /**
   *  Orientation of circle when map is pitched. Default value: "viewport".
   */
  public var circlePitchAlignment: CirclePitchAlignmentValue by circlePitchAlignmentState

  private val circlePitchScaleState: MutableState<CirclePitchScaleValue> = mutableStateOf(initialCirclePitchScale)
  /**
   *  Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   */
  public var circlePitchScale: CirclePitchScaleValue by circlePitchScaleState

  private val circleRadiusState: MutableState<DoubleValue> = mutableStateOf(initialCircleRadius)
  /**
   *  Circle radius. Default value: 5. Minimum value: 0. The unit of circleRadius is in pixels.
   */
  public var circleRadius: DoubleValue by circleRadiusState

  private val circleRadiusTransitionState: MutableState<Transition> = mutableStateOf(initialCircleRadiusTransition)
  /**
   *  Defines the transition of [circleRadius].
   */
  public var circleRadiusTransition: Transition by circleRadiusTransitionState

  private val circleStrokeColorState: MutableState<ColorValue> = mutableStateOf(initialCircleStrokeColor)
  /**
   *  The stroke color of the circle. Default value: "#000000".
   */
  public var circleStrokeColor: ColorValue by circleStrokeColorState

  @MapboxExperimental
  private val circleStrokeColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialCircleStrokeColorUseTheme)
  /**
   *  Overrides applying of color theme for [circleStrokeColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var circleStrokeColorUseTheme: StringValue by circleStrokeColorUseThemeState

  private val circleStrokeColorTransitionState: MutableState<Transition> = mutableStateOf(initialCircleStrokeColorTransition)
  /**
   *  Defines the transition of [circleStrokeColor].
   */
  public var circleStrokeColorTransition: Transition by circleStrokeColorTransitionState

  private val circleStrokeOpacityState: MutableState<DoubleValue> = mutableStateOf(initialCircleStrokeOpacity)
  /**
   *  The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   */
  public var circleStrokeOpacity: DoubleValue by circleStrokeOpacityState

  private val circleStrokeOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialCircleStrokeOpacityTransition)
  /**
   *  Defines the transition of [circleStrokeOpacity].
   */
  public var circleStrokeOpacityTransition: Transition by circleStrokeOpacityTransitionState

  private val circleStrokeWidthState: MutableState<DoubleValue> = mutableStateOf(initialCircleStrokeWidth)
  /**
   *  The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0. The unit of circleStrokeWidth is in pixels.
   */
  public var circleStrokeWidth: DoubleValue by circleStrokeWidthState

  private val circleStrokeWidthTransitionState: MutableState<Transition> = mutableStateOf(initialCircleStrokeWidthTransition)
  /**
   *  Defines the transition of [circleStrokeWidth].
   */
  public var circleStrokeWidthTransition: Transition by circleStrokeWidthTransitionState

  private val circleTranslateState: MutableState<DoubleListValue> = mutableStateOf(initialCircleTranslate)
  /**
   *  The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of circleTranslate is in pixels.
   */
  public var circleTranslate: DoubleListValue by circleTranslateState

  private val circleTranslateTransitionState: MutableState<Transition> = mutableStateOf(initialCircleTranslateTransition)
  /**
   *  Defines the transition of [circleTranslate].
   */
  public var circleTranslateTransition: Transition by circleTranslateTransitionState

  private val circleTranslateAnchorState: MutableState<CircleTranslateAnchorValue> = mutableStateOf(initialCircleTranslateAnchor)
  /**
   *  Controls the frame of reference for `circle-translate`. Default value: "map".
   */
  public var circleTranslateAnchor: CircleTranslateAnchorValue by circleTranslateAnchorState

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
    ActionWhenNotInitial(layerNode.setPropertyAction, circleSortKeyState, "circle-sort-key")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleBlurState, "circle-blur")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleBlurTransitionState, "circle-blur-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleColorState, "circle-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleColorUseThemeState, "circle-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleColorTransitionState, "circle-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleEmissiveStrengthState, "circle-emissive-strength")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleEmissiveStrengthTransitionState, "circle-emissive-strength-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleOpacityState, "circle-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleOpacityTransitionState, "circle-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, circlePitchAlignmentState, "circle-pitch-alignment")
    ActionWhenNotInitial(layerNode.setPropertyAction, circlePitchScaleState, "circle-pitch-scale")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleRadiusState, "circle-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleRadiusTransitionState, "circle-radius-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleStrokeColorState, "circle-stroke-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleStrokeColorUseThemeState, "circle-stroke-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleStrokeColorTransitionState, "circle-stroke-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleStrokeOpacityState, "circle-stroke-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleStrokeOpacityTransitionState, "circle-stroke-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleStrokeWidthState, "circle-stroke-width")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleStrokeWidthTransitionState, "circle-stroke-width-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleTranslateState, "circle-translate")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleTranslateTransitionState, "circle-translate-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, circleTranslateAnchorState, "circle-translate-anchor")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.