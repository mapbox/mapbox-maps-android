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
import com.mapbox.maps.extension.compose.style.AddImageWhenNotInitial
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [FillExtrusionLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#fill-extrusion)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class FillExtrusionLayerState
@OptIn(MapboxExperimental::class)
private constructor(
  initialFillExtrusionEdgeRadius: DoubleValue,
  initialFillExtrusionAmbientOcclusionGroundAttenuation: DoubleValue,
  initialFillExtrusionAmbientOcclusionGroundAttenuationTransition: Transition,
  initialFillExtrusionAmbientOcclusionGroundRadius: DoubleValue,
  initialFillExtrusionAmbientOcclusionGroundRadiusTransition: Transition,
  initialFillExtrusionAmbientOcclusionIntensity: DoubleValue,
  initialFillExtrusionAmbientOcclusionIntensityTransition: Transition,
  initialFillExtrusionAmbientOcclusionRadius: DoubleValue,
  initialFillExtrusionAmbientOcclusionRadiusTransition: Transition,
  initialFillExtrusionAmbientOcclusionWallRadius: DoubleValue,
  initialFillExtrusionAmbientOcclusionWallRadiusTransition: Transition,
  initialFillExtrusionBase: DoubleValue,
  initialFillExtrusionBaseTransition: Transition,
  initialFillExtrusionBaseAlignment: FillExtrusionBaseAlignmentValue,
  initialFillExtrusionColor: ColorValue,
  initialFillExtrusionColorUseTheme: StringValue,
  initialFillExtrusionColorTransition: Transition,
  initialFillExtrusionCutoffFadeRange: DoubleValue,
  initialFillExtrusionEmissiveStrength: DoubleValue,
  initialFillExtrusionEmissiveStrengthTransition: Transition,
  initialFillExtrusionFloodLightColor: ColorValue,
  initialFillExtrusionFloodLightColorUseTheme: StringValue,
  initialFillExtrusionFloodLightColorTransition: Transition,
  initialFillExtrusionFloodLightGroundAttenuation: DoubleValue,
  initialFillExtrusionFloodLightGroundAttenuationTransition: Transition,
  initialFillExtrusionFloodLightGroundRadius: DoubleValue,
  initialFillExtrusionFloodLightGroundRadiusTransition: Transition,
  initialFillExtrusionFloodLightIntensity: DoubleValue,
  initialFillExtrusionFloodLightIntensityTransition: Transition,
  initialFillExtrusionFloodLightWallRadius: DoubleValue,
  initialFillExtrusionFloodLightWallRadiusTransition: Transition,
  initialFillExtrusionHeight: DoubleValue,
  initialFillExtrusionHeightTransition: Transition,
  initialFillExtrusionHeightAlignment: FillExtrusionHeightAlignmentValue,
  initialFillExtrusionLineWidth: DoubleValue,
  initialFillExtrusionLineWidthTransition: Transition,
  initialFillExtrusionOpacity: DoubleValue,
  initialFillExtrusionOpacityTransition: Transition,
  initialFillExtrusionPattern: ImageValue,
  initialFillExtrusionPatternCrossFade: DoubleValue,
  initialFillExtrusionRoundedRoof: BooleanValue,
  initialFillExtrusionTranslate: DoubleListValue,
  initialFillExtrusionTranslateTransition: Transition,
  initialFillExtrusionTranslateAnchor: FillExtrusionTranslateAnchorValue,
  initialFillExtrusionVerticalGradient: BooleanValue,
  initialFillExtrusionVerticalScale: DoubleValue,
  initialFillExtrusionVerticalScaleTransition: Transition,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [FillExtrusionLayerState].
   */
  @OptIn(MapboxExperimental::class)
  public constructor() : this(
    initialFillExtrusionEdgeRadius = DoubleValue.INITIAL,
    initialFillExtrusionAmbientOcclusionGroundAttenuation = DoubleValue.INITIAL,
    initialFillExtrusionAmbientOcclusionGroundAttenuationTransition = Transition.INITIAL,
    initialFillExtrusionAmbientOcclusionGroundRadius = DoubleValue.INITIAL,
    initialFillExtrusionAmbientOcclusionGroundRadiusTransition = Transition.INITIAL,
    initialFillExtrusionAmbientOcclusionIntensity = DoubleValue.INITIAL,
    initialFillExtrusionAmbientOcclusionIntensityTransition = Transition.INITIAL,
    initialFillExtrusionAmbientOcclusionRadius = DoubleValue.INITIAL,
    initialFillExtrusionAmbientOcclusionRadiusTransition = Transition.INITIAL,
    initialFillExtrusionAmbientOcclusionWallRadius = DoubleValue.INITIAL,
    initialFillExtrusionAmbientOcclusionWallRadiusTransition = Transition.INITIAL,
    initialFillExtrusionBase = DoubleValue.INITIAL,
    initialFillExtrusionBaseTransition = Transition.INITIAL,
    initialFillExtrusionBaseAlignment = FillExtrusionBaseAlignmentValue.INITIAL,
    initialFillExtrusionColor = ColorValue.INITIAL,
    initialFillExtrusionColorUseTheme = StringValue.INITIAL,
    initialFillExtrusionColorTransition = Transition.INITIAL,
    initialFillExtrusionCutoffFadeRange = DoubleValue.INITIAL,
    initialFillExtrusionEmissiveStrength = DoubleValue.INITIAL,
    initialFillExtrusionEmissiveStrengthTransition = Transition.INITIAL,
    initialFillExtrusionFloodLightColor = ColorValue.INITIAL,
    initialFillExtrusionFloodLightColorUseTheme = StringValue.INITIAL,
    initialFillExtrusionFloodLightColorTransition = Transition.INITIAL,
    initialFillExtrusionFloodLightGroundAttenuation = DoubleValue.INITIAL,
    initialFillExtrusionFloodLightGroundAttenuationTransition = Transition.INITIAL,
    initialFillExtrusionFloodLightGroundRadius = DoubleValue.INITIAL,
    initialFillExtrusionFloodLightGroundRadiusTransition = Transition.INITIAL,
    initialFillExtrusionFloodLightIntensity = DoubleValue.INITIAL,
    initialFillExtrusionFloodLightIntensityTransition = Transition.INITIAL,
    initialFillExtrusionFloodLightWallRadius = DoubleValue.INITIAL,
    initialFillExtrusionFloodLightWallRadiusTransition = Transition.INITIAL,
    initialFillExtrusionHeight = DoubleValue.INITIAL,
    initialFillExtrusionHeightTransition = Transition.INITIAL,
    initialFillExtrusionHeightAlignment = FillExtrusionHeightAlignmentValue.INITIAL,
    initialFillExtrusionLineWidth = DoubleValue.INITIAL,
    initialFillExtrusionLineWidthTransition = Transition.INITIAL,
    initialFillExtrusionOpacity = DoubleValue.INITIAL,
    initialFillExtrusionOpacityTransition = Transition.INITIAL,
    initialFillExtrusionPattern = ImageValue.INITIAL,
    initialFillExtrusionPatternCrossFade = DoubleValue.INITIAL,
    initialFillExtrusionRoundedRoof = BooleanValue.INITIAL,
    initialFillExtrusionTranslate = DoubleListValue.INITIAL,
    initialFillExtrusionTranslateTransition = Transition.INITIAL,
    initialFillExtrusionTranslateAnchor = FillExtrusionTranslateAnchorValue.INITIAL,
    initialFillExtrusionVerticalGradient = BooleanValue.INITIAL,
    initialFillExtrusionVerticalScale = DoubleValue.INITIAL,
    initialFillExtrusionVerticalScaleTransition = Transition.INITIAL,
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

  @MapboxExperimental
  private val fillExtrusionEdgeRadiusState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionEdgeRadius)
  /**
   *  Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  public var fillExtrusionEdgeRadius: DoubleValue by fillExtrusionEdgeRadiusState

  @MapboxExperimental
  private val fillExtrusionAmbientOcclusionGroundAttenuationState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionAmbientOcclusionGroundAttenuation)
  /**
   *  Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionGroundAttenuation: DoubleValue by fillExtrusionAmbientOcclusionGroundAttenuationState

  @MapboxExperimental
  private val fillExtrusionAmbientOcclusionGroundAttenuationTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionAmbientOcclusionGroundAttenuationTransition)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionGroundAttenuation].
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionGroundAttenuationTransition: Transition by fillExtrusionAmbientOcclusionGroundAttenuationTransitionState

  @MapboxExperimental
  private val fillExtrusionAmbientOcclusionGroundRadiusState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionAmbientOcclusionGroundRadius)
  /**
   *  The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionGroundRadius: DoubleValue by fillExtrusionAmbientOcclusionGroundRadiusState

  @MapboxExperimental
  private val fillExtrusionAmbientOcclusionGroundRadiusTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionAmbientOcclusionGroundRadiusTransition)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionGroundRadius].
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionGroundRadiusTransition: Transition by fillExtrusionAmbientOcclusionGroundRadiusTransitionState

  private val fillExtrusionAmbientOcclusionIntensityState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionAmbientOcclusionIntensity)
  /**
   *  Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
   */
  public var fillExtrusionAmbientOcclusionIntensity: DoubleValue by fillExtrusionAmbientOcclusionIntensityState

  private val fillExtrusionAmbientOcclusionIntensityTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionAmbientOcclusionIntensityTransition)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionIntensity].
   */
  public var fillExtrusionAmbientOcclusionIntensityTransition: Transition by fillExtrusionAmbientOcclusionIntensityTransitionState

  private val fillExtrusionAmbientOcclusionRadiusState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionAmbientOcclusionRadius)
  /**
   *  Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
   */
  public var fillExtrusionAmbientOcclusionRadius: DoubleValue by fillExtrusionAmbientOcclusionRadiusState

  private val fillExtrusionAmbientOcclusionRadiusTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionAmbientOcclusionRadiusTransition)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionRadius].
   */
  public var fillExtrusionAmbientOcclusionRadiusTransition: Transition by fillExtrusionAmbientOcclusionRadiusTransitionState

  @MapboxExperimental
  private val fillExtrusionAmbientOcclusionWallRadiusState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionAmbientOcclusionWallRadius)
  /**
   *  Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionWallRadius: DoubleValue by fillExtrusionAmbientOcclusionWallRadiusState

  @MapboxExperimental
  private val fillExtrusionAmbientOcclusionWallRadiusTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionAmbientOcclusionWallRadiusTransition)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionWallRadius].
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionWallRadiusTransition: Transition by fillExtrusionAmbientOcclusionWallRadiusTransitionState

  private val fillExtrusionBaseState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionBase)
  /**
   *  The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
   */
  public var fillExtrusionBase: DoubleValue by fillExtrusionBaseState

  private val fillExtrusionBaseTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionBaseTransition)
  /**
   *  Defines the transition of [fillExtrusionBase].
   */
  public var fillExtrusionBaseTransition: Transition by fillExtrusionBaseTransitionState

  @MapboxExperimental
  private val fillExtrusionBaseAlignmentState: MutableState<FillExtrusionBaseAlignmentValue> = mutableStateOf(initialFillExtrusionBaseAlignment)
  /**
   *  Controls the behavior of fill extrusion base over terrain Default value: "terrain".
   */
  @MapboxExperimental
  public var fillExtrusionBaseAlignment: FillExtrusionBaseAlignmentValue by fillExtrusionBaseAlignmentState

  private val fillExtrusionColorState: MutableState<ColorValue> = mutableStateOf(initialFillExtrusionColor)
  /**
   *  The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   */
  public var fillExtrusionColor: ColorValue by fillExtrusionColorState

  @MapboxExperimental
  private val fillExtrusionColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialFillExtrusionColorUseTheme)
  /**
   *  Overrides applying of color theme for [fillExtrusionColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var fillExtrusionColorUseTheme: StringValue by fillExtrusionColorUseThemeState

  private val fillExtrusionColorTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionColorTransition)
  /**
   *  Defines the transition of [fillExtrusionColor].
   */
  public var fillExtrusionColorTransition: Transition by fillExtrusionColorTransitionState

  private val fillExtrusionCutoffFadeRangeState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionCutoffFadeRange)
  /**
   *  This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   */
  public var fillExtrusionCutoffFadeRange: DoubleValue by fillExtrusionCutoffFadeRangeState

  private val fillExtrusionEmissiveStrengthState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionEmissiveStrength)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   */
  public var fillExtrusionEmissiveStrength: DoubleValue by fillExtrusionEmissiveStrengthState

  private val fillExtrusionEmissiveStrengthTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionEmissiveStrengthTransition)
  /**
   *  Defines the transition of [fillExtrusionEmissiveStrength].
   */
  public var fillExtrusionEmissiveStrengthTransition: Transition by fillExtrusionEmissiveStrengthTransitionState

  @MapboxExperimental
  private val fillExtrusionFloodLightColorState: MutableState<ColorValue> = mutableStateOf(initialFillExtrusionFloodLightColor)
  /**
   *  The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightColor: ColorValue by fillExtrusionFloodLightColorState

  @MapboxExperimental
  private val fillExtrusionFloodLightColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialFillExtrusionFloodLightColorUseTheme)
  /**
   *  Overrides applying of color theme for [fillExtrusionFloodLightColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightColorUseTheme: StringValue by fillExtrusionFloodLightColorUseThemeState

  @MapboxExperimental
  private val fillExtrusionFloodLightColorTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionFloodLightColorTransition)
  /**
   *  Defines the transition of [fillExtrusionFloodLightColor].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightColorTransition: Transition by fillExtrusionFloodLightColorTransitionState

  @MapboxExperimental
  private val fillExtrusionFloodLightGroundAttenuationState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionFloodLightGroundAttenuation)
  /**
   *  Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightGroundAttenuation: DoubleValue by fillExtrusionFloodLightGroundAttenuationState

  @MapboxExperimental
  private val fillExtrusionFloodLightGroundAttenuationTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionFloodLightGroundAttenuationTransition)
  /**
   *  Defines the transition of [fillExtrusionFloodLightGroundAttenuation].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightGroundAttenuationTransition: Transition by fillExtrusionFloodLightGroundAttenuationTransitionState

  @MapboxExperimental
  private val fillExtrusionFloodLightGroundRadiusState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionFloodLightGroundRadius)
  /**
   *  The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightGroundRadius: DoubleValue by fillExtrusionFloodLightGroundRadiusState

  @MapboxExperimental
  private val fillExtrusionFloodLightGroundRadiusTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionFloodLightGroundRadiusTransition)
  /**
   *  Defines the transition of [fillExtrusionFloodLightGroundRadius].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightGroundRadiusTransition: Transition by fillExtrusionFloodLightGroundRadiusTransitionState

  @MapboxExperimental
  private val fillExtrusionFloodLightIntensityState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionFloodLightIntensity)
  /**
   *  The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightIntensity: DoubleValue by fillExtrusionFloodLightIntensityState

  @MapboxExperimental
  private val fillExtrusionFloodLightIntensityTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionFloodLightIntensityTransition)
  /**
   *  Defines the transition of [fillExtrusionFloodLightIntensity].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightIntensityTransition: Transition by fillExtrusionFloodLightIntensityTransitionState

  @MapboxExperimental
  private val fillExtrusionFloodLightWallRadiusState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionFloodLightWallRadius)
  /**
   *  The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightWallRadius: DoubleValue by fillExtrusionFloodLightWallRadiusState

  @MapboxExperimental
  private val fillExtrusionFloodLightWallRadiusTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionFloodLightWallRadiusTransition)
  /**
   *  Defines the transition of [fillExtrusionFloodLightWallRadius].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightWallRadiusTransition: Transition by fillExtrusionFloodLightWallRadiusTransitionState

  private val fillExtrusionHeightState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionHeight)
  /**
   *  The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
   */
  public var fillExtrusionHeight: DoubleValue by fillExtrusionHeightState

  private val fillExtrusionHeightTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionHeightTransition)
  /**
   *  Defines the transition of [fillExtrusionHeight].
   */
  public var fillExtrusionHeightTransition: Transition by fillExtrusionHeightTransitionState

  @MapboxExperimental
  private val fillExtrusionHeightAlignmentState: MutableState<FillExtrusionHeightAlignmentValue> = mutableStateOf(initialFillExtrusionHeightAlignment)
  /**
   *  Controls the behavior of fill extrusion height over terrain Default value: "flat".
   */
  @MapboxExperimental
  public var fillExtrusionHeightAlignment: FillExtrusionHeightAlignmentValue by fillExtrusionHeightAlignmentState

  @MapboxExperimental
  private val fillExtrusionLineWidthState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionLineWidth)
  /**
   *  If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   */
  @MapboxExperimental
  public var fillExtrusionLineWidth: DoubleValue by fillExtrusionLineWidthState

  @MapboxExperimental
  private val fillExtrusionLineWidthTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionLineWidthTransition)
  /**
   *  Defines the transition of [fillExtrusionLineWidth].
   */
  @MapboxExperimental
  public var fillExtrusionLineWidthTransition: Transition by fillExtrusionLineWidthTransitionState

  private val fillExtrusionOpacityState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionOpacity)
  /**
   *  The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
   */
  public var fillExtrusionOpacity: DoubleValue by fillExtrusionOpacityState

  private val fillExtrusionOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionOpacityTransition)
  /**
   *  Defines the transition of [fillExtrusionOpacity].
   */
  public var fillExtrusionOpacityTransition: Transition by fillExtrusionOpacityTransitionState

  private val fillExtrusionPatternState: MutableState<ImageValue> = mutableStateOf(initialFillExtrusionPattern)
  /**
   *  Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var fillExtrusionPattern: ImageValue by fillExtrusionPatternState

  private val fillExtrusionPatternCrossFadeState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionPatternCrossFade)
  /**
   *  Controls the transition progress between the image variants of fill-extrusion-pattern. Zero means the first variant is used, one is the second, and in between they are blended together. Both images should be the same size and have the same type (either raster or vector). Default value: 0. Value range: [0, 1]
   */
  public var fillExtrusionPatternCrossFade: DoubleValue by fillExtrusionPatternCrossFadeState

  @MapboxExperimental
  private val fillExtrusionRoundedRoofState: MutableState<BooleanValue> = mutableStateOf(initialFillExtrusionRoundedRoof)
  /**
   *  Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
   */
  @MapboxExperimental
  public var fillExtrusionRoundedRoof: BooleanValue by fillExtrusionRoundedRoofState

  private val fillExtrusionTranslateState: MutableState<DoubleListValue> = mutableStateOf(initialFillExtrusionTranslate)
  /**
   *  The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
   */
  public var fillExtrusionTranslate: DoubleListValue by fillExtrusionTranslateState

  private val fillExtrusionTranslateTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionTranslateTransition)
  /**
   *  Defines the transition of [fillExtrusionTranslate].
   */
  public var fillExtrusionTranslateTransition: Transition by fillExtrusionTranslateTransitionState

  private val fillExtrusionTranslateAnchorState: MutableState<FillExtrusionTranslateAnchorValue> = mutableStateOf(initialFillExtrusionTranslateAnchor)
  /**
   *  Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
   */
  public var fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchorValue by fillExtrusionTranslateAnchorState

  private val fillExtrusionVerticalGradientState: MutableState<BooleanValue> = mutableStateOf(initialFillExtrusionVerticalGradient)
  /**
   *  Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
   */
  public var fillExtrusionVerticalGradient: BooleanValue by fillExtrusionVerticalGradientState

  @MapboxExperimental
  private val fillExtrusionVerticalScaleState: MutableState<DoubleValue> = mutableStateOf(initialFillExtrusionVerticalScale)
  /**
   *  A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   */
  @MapboxExperimental
  public var fillExtrusionVerticalScale: DoubleValue by fillExtrusionVerticalScaleState

  @MapboxExperimental
  private val fillExtrusionVerticalScaleTransitionState: MutableState<Transition> = mutableStateOf(initialFillExtrusionVerticalScaleTransition)
  /**
   *  Defines the transition of [fillExtrusionVerticalScale].
   */
  @MapboxExperimental
  public var fillExtrusionVerticalScaleTransition: Transition by fillExtrusionVerticalScaleTransitionState

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
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionEdgeRadiusState, "fill-extrusion-edge-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionGroundAttenuationState, "fill-extrusion-ambient-occlusion-ground-attenuation")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionGroundAttenuationTransitionState, "fill-extrusion-ambient-occlusion-ground-attenuation-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionGroundRadiusState, "fill-extrusion-ambient-occlusion-ground-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionGroundRadiusTransitionState, "fill-extrusion-ambient-occlusion-ground-radius-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionIntensityState, "fill-extrusion-ambient-occlusion-intensity")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionIntensityTransitionState, "fill-extrusion-ambient-occlusion-intensity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionRadiusState, "fill-extrusion-ambient-occlusion-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionRadiusTransitionState, "fill-extrusion-ambient-occlusion-radius-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionWallRadiusState, "fill-extrusion-ambient-occlusion-wall-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionAmbientOcclusionWallRadiusTransitionState, "fill-extrusion-ambient-occlusion-wall-radius-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionBaseState, "fill-extrusion-base")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionBaseTransitionState, "fill-extrusion-base-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionBaseAlignmentState, "fill-extrusion-base-alignment")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionColorState, "fill-extrusion-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionColorUseThemeState, "fill-extrusion-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionColorTransitionState, "fill-extrusion-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionCutoffFadeRangeState, "fill-extrusion-cutoff-fade-range")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionEmissiveStrengthState, "fill-extrusion-emissive-strength")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionEmissiveStrengthTransitionState, "fill-extrusion-emissive-strength-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightColorState, "fill-extrusion-flood-light-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightColorUseThemeState, "fill-extrusion-flood-light-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightColorTransitionState, "fill-extrusion-flood-light-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightGroundAttenuationState, "fill-extrusion-flood-light-ground-attenuation")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightGroundAttenuationTransitionState, "fill-extrusion-flood-light-ground-attenuation-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightGroundRadiusState, "fill-extrusion-flood-light-ground-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightGroundRadiusTransitionState, "fill-extrusion-flood-light-ground-radius-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightIntensityState, "fill-extrusion-flood-light-intensity")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightIntensityTransitionState, "fill-extrusion-flood-light-intensity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightWallRadiusState, "fill-extrusion-flood-light-wall-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionFloodLightWallRadiusTransitionState, "fill-extrusion-flood-light-wall-radius-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionHeightState, "fill-extrusion-height")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionHeightTransitionState, "fill-extrusion-height-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionHeightAlignmentState, "fill-extrusion-height-alignment")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionLineWidthState, "fill-extrusion-line-width")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionLineWidthTransitionState, "fill-extrusion-line-width-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionOpacityState, "fill-extrusion-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionOpacityTransitionState, "fill-extrusion-opacity-transition")
    AddImageWhenNotInitial(layerNode, fillExtrusionPatternState, "fill-extrusion-pattern")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionPatternCrossFadeState, "fill-extrusion-pattern-cross-fade")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionRoundedRoofState, "fill-extrusion-rounded-roof")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionTranslateState, "fill-extrusion-translate")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionTranslateTransitionState, "fill-extrusion-translate-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionTranslateAnchorState, "fill-extrusion-translate-anchor")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionVerticalGradientState, "fill-extrusion-vertical-gradient")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionVerticalScaleState, "fill-extrusion-vertical-scale")
    ActionWhenNotInitial(layerNode.setPropertyAction, fillExtrusionVerticalScaleTransitionState, "fill-extrusion-vertical-scale-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.