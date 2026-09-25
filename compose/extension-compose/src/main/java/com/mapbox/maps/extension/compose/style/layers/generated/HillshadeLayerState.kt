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
 * The state holder for [HillshadeLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#hillshade)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class HillshadeLayerState
@OptIn(MapboxExperimental::class)
private constructor(
  initialHillshadeAccentColor: ColorValue,
  initialHillshadeAccentColorUseTheme: StringValue,
  initialHillshadeAccentColorTransition: Transition,
  initialHillshadeEmissiveStrength: DoubleValue,
  initialHillshadeEmissiveStrengthTransition: Transition,
  initialHillshadeExaggeration: DoubleValue,
  initialHillshadeExaggerationTransition: Transition,
  initialHillshadeHighlightColor: ColorValue,
  initialHillshadeHighlightColorUseTheme: StringValue,
  initialHillshadeHighlightColorTransition: Transition,
  initialHillshadeIlluminationAnchor: HillshadeIlluminationAnchorValue,
  initialHillshadeIlluminationDirection: DoubleValue,
  initialHillshadeShadowColor: ColorValue,
  initialHillshadeShadowColorUseTheme: StringValue,
  initialHillshadeShadowColorTransition: Transition,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [HillshadeLayerState].
   */
  @OptIn(MapboxExperimental::class)
  public constructor() : this(
    initialHillshadeAccentColor = ColorValue.INITIAL,
    initialHillshadeAccentColorUseTheme = StringValue.INITIAL,
    initialHillshadeAccentColorTransition = Transition.INITIAL,
    initialHillshadeEmissiveStrength = DoubleValue.INITIAL,
    initialHillshadeEmissiveStrengthTransition = Transition.INITIAL,
    initialHillshadeExaggeration = DoubleValue.INITIAL,
    initialHillshadeExaggerationTransition = Transition.INITIAL,
    initialHillshadeHighlightColor = ColorValue.INITIAL,
    initialHillshadeHighlightColorUseTheme = StringValue.INITIAL,
    initialHillshadeHighlightColorTransition = Transition.INITIAL,
    initialHillshadeIlluminationAnchor = HillshadeIlluminationAnchorValue.INITIAL,
    initialHillshadeIlluminationDirection = DoubleValue.INITIAL,
    initialHillshadeShadowColor = ColorValue.INITIAL,
    initialHillshadeShadowColorUseTheme = StringValue.INITIAL,
    initialHillshadeShadowColorTransition = Transition.INITIAL,
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

  private val hillshadeAccentColorState: MutableState<ColorValue> = mutableStateOf(initialHillshadeAccentColor)
  /**
   *  The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   */
  public var hillshadeAccentColor: ColorValue by hillshadeAccentColorState

  @MapboxExperimental
  private val hillshadeAccentColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialHillshadeAccentColorUseTheme)
  /**
   *  Overrides applying of color theme for [hillshadeAccentColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var hillshadeAccentColorUseTheme: StringValue by hillshadeAccentColorUseThemeState

  private val hillshadeAccentColorTransitionState: MutableState<Transition> = mutableStateOf(initialHillshadeAccentColorTransition)
  /**
   *  Defines the transition of [hillshadeAccentColor].
   */
  public var hillshadeAccentColorTransition: Transition by hillshadeAccentColorTransitionState

  private val hillshadeEmissiveStrengthState: MutableState<DoubleValue> = mutableStateOf(initialHillshadeEmissiveStrength)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   */
  public var hillshadeEmissiveStrength: DoubleValue by hillshadeEmissiveStrengthState

  private val hillshadeEmissiveStrengthTransitionState: MutableState<Transition> = mutableStateOf(initialHillshadeEmissiveStrengthTransition)
  /**
   *  Defines the transition of [hillshadeEmissiveStrength].
   */
  public var hillshadeEmissiveStrengthTransition: Transition by hillshadeEmissiveStrengthTransitionState

  private val hillshadeExaggerationState: MutableState<DoubleValue> = mutableStateOf(initialHillshadeExaggeration)
  /**
   *  Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   */
  public var hillshadeExaggeration: DoubleValue by hillshadeExaggerationState

  private val hillshadeExaggerationTransitionState: MutableState<Transition> = mutableStateOf(initialHillshadeExaggerationTransition)
  /**
   *  Defines the transition of [hillshadeExaggeration].
   */
  public var hillshadeExaggerationTransition: Transition by hillshadeExaggerationTransitionState

  private val hillshadeHighlightColorState: MutableState<ColorValue> = mutableStateOf(initialHillshadeHighlightColor)
  /**
   *  The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   */
  public var hillshadeHighlightColor: ColorValue by hillshadeHighlightColorState

  @MapboxExperimental
  private val hillshadeHighlightColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialHillshadeHighlightColorUseTheme)
  /**
   *  Overrides applying of color theme for [hillshadeHighlightColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var hillshadeHighlightColorUseTheme: StringValue by hillshadeHighlightColorUseThemeState

  private val hillshadeHighlightColorTransitionState: MutableState<Transition> = mutableStateOf(initialHillshadeHighlightColorTransition)
  /**
   *  Defines the transition of [hillshadeHighlightColor].
   */
  public var hillshadeHighlightColorTransition: Transition by hillshadeHighlightColorTransitionState

  private val hillshadeIlluminationAnchorState: MutableState<HillshadeIlluminationAnchorValue> = mutableStateOf(initialHillshadeIlluminationAnchor)
  /**
   *  Direction of light source when map is rotated. Default value: "viewport".
   */
  public var hillshadeIlluminationAnchor: HillshadeIlluminationAnchorValue by hillshadeIlluminationAnchorState

  private val hillshadeIlluminationDirectionState: MutableState<DoubleValue> = mutableStateOf(initialHillshadeIlluminationDirection)
  /**
   *  The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
   */
  public var hillshadeIlluminationDirection: DoubleValue by hillshadeIlluminationDirectionState

  private val hillshadeShadowColorState: MutableState<ColorValue> = mutableStateOf(initialHillshadeShadowColor)
  /**
   *  The shading color of areas that face away from the light source. Default value: "#000000".
   */
  public var hillshadeShadowColor: ColorValue by hillshadeShadowColorState

  @MapboxExperimental
  private val hillshadeShadowColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialHillshadeShadowColorUseTheme)
  /**
   *  Overrides applying of color theme for [hillshadeShadowColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var hillshadeShadowColorUseTheme: StringValue by hillshadeShadowColorUseThemeState

  private val hillshadeShadowColorTransitionState: MutableState<Transition> = mutableStateOf(initialHillshadeShadowColorTransition)
  /**
   *  Defines the transition of [hillshadeShadowColor].
   */
  public var hillshadeShadowColorTransition: Transition by hillshadeShadowColorTransitionState

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
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeAccentColorState, "hillshade-accent-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeAccentColorUseThemeState, "hillshade-accent-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeAccentColorTransitionState, "hillshade-accent-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeEmissiveStrengthState, "hillshade-emissive-strength")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeEmissiveStrengthTransitionState, "hillshade-emissive-strength-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeExaggerationState, "hillshade-exaggeration")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeExaggerationTransitionState, "hillshade-exaggeration-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeHighlightColorState, "hillshade-highlight-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeHighlightColorUseThemeState, "hillshade-highlight-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeHighlightColorTransitionState, "hillshade-highlight-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeIlluminationAnchorState, "hillshade-illumination-anchor")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeIlluminationDirectionState, "hillshade-illumination-direction")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeShadowColorState, "hillshade-shadow-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeShadowColorUseThemeState, "hillshade-shadow-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, hillshadeShadowColorTransitionState, "hillshade-shadow-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.