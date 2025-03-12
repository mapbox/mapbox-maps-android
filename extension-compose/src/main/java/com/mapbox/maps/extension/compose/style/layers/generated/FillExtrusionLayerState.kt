// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
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
public class FillExtrusionLayerState private constructor(
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

  /**
   *  Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  public var fillExtrusionEdgeRadius: DoubleValue by mutableStateOf(initialFillExtrusionEdgeRadius)
  /**
   *  Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionGroundAttenuation: DoubleValue by mutableStateOf(initialFillExtrusionAmbientOcclusionGroundAttenuation)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionGroundAttenuation].
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionGroundAttenuationTransition: Transition by mutableStateOf(initialFillExtrusionAmbientOcclusionGroundAttenuationTransition)
  /**
   *  The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionGroundRadius: DoubleValue by mutableStateOf(initialFillExtrusionAmbientOcclusionGroundRadius)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionGroundRadius].
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionGroundRadiusTransition: Transition by mutableStateOf(initialFillExtrusionAmbientOcclusionGroundRadiusTransition)
  /**
   *  Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
   */
  public var fillExtrusionAmbientOcclusionIntensity: DoubleValue by mutableStateOf(initialFillExtrusionAmbientOcclusionIntensity)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionIntensity].
   */
  public var fillExtrusionAmbientOcclusionIntensityTransition: Transition by mutableStateOf(initialFillExtrusionAmbientOcclusionIntensityTransition)
  /**
   *  Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
   */
  public var fillExtrusionAmbientOcclusionRadius: DoubleValue by mutableStateOf(initialFillExtrusionAmbientOcclusionRadius)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionRadius].
   */
  public var fillExtrusionAmbientOcclusionRadiusTransition: Transition by mutableStateOf(initialFillExtrusionAmbientOcclusionRadiusTransition)
  /**
   *  Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionWallRadius: DoubleValue by mutableStateOf(initialFillExtrusionAmbientOcclusionWallRadius)
  /**
   *  Defines the transition of [fillExtrusionAmbientOcclusionWallRadius].
   */
  @MapboxExperimental
  public var fillExtrusionAmbientOcclusionWallRadiusTransition: Transition by mutableStateOf(initialFillExtrusionAmbientOcclusionWallRadiusTransition)
  /**
   *  The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
   */
  public var fillExtrusionBase: DoubleValue by mutableStateOf(initialFillExtrusionBase)
  /**
   *  Defines the transition of [fillExtrusionBase].
   */
  public var fillExtrusionBaseTransition: Transition by mutableStateOf(initialFillExtrusionBaseTransition)
  /**
   *  Controls the behavior of fill extrusion base over terrain Default value: "terrain".
   */
  @MapboxExperimental
  public var fillExtrusionBaseAlignment: FillExtrusionBaseAlignmentValue by mutableStateOf(initialFillExtrusionBaseAlignment)
  /**
   *  The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   */
  public var fillExtrusionColor: ColorValue by mutableStateOf(initialFillExtrusionColor)
  /**
   *  Overrides applying of color theme for [fillExtrusionColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var fillExtrusionColorUseTheme: StringValue by mutableStateOf(initialFillExtrusionColorUseTheme)
  /**
   *  Defines the transition of [fillExtrusionColor].
   */
  public var fillExtrusionColorTransition: Transition by mutableStateOf(initialFillExtrusionColorTransition)
  /**
   *  This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   */
  public var fillExtrusionCutoffFadeRange: DoubleValue by mutableStateOf(initialFillExtrusionCutoffFadeRange)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   */
  public var fillExtrusionEmissiveStrength: DoubleValue by mutableStateOf(initialFillExtrusionEmissiveStrength)
  /**
   *  Defines the transition of [fillExtrusionEmissiveStrength].
   */
  public var fillExtrusionEmissiveStrengthTransition: Transition by mutableStateOf(initialFillExtrusionEmissiveStrengthTransition)
  /**
   *  The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightColor: ColorValue by mutableStateOf(initialFillExtrusionFloodLightColor)
  /**
   *  Overrides applying of color theme for [fillExtrusionFloodLightColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightColorUseTheme: StringValue by mutableStateOf(initialFillExtrusionFloodLightColorUseTheme)
  /**
   *  Defines the transition of [fillExtrusionFloodLightColor].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightColorTransition: Transition by mutableStateOf(initialFillExtrusionFloodLightColorTransition)
  /**
   *  Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightGroundAttenuation: DoubleValue by mutableStateOf(initialFillExtrusionFloodLightGroundAttenuation)
  /**
   *  Defines the transition of [fillExtrusionFloodLightGroundAttenuation].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightGroundAttenuationTransition: Transition by mutableStateOf(initialFillExtrusionFloodLightGroundAttenuationTransition)
  /**
   *  The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightGroundRadius: DoubleValue by mutableStateOf(initialFillExtrusionFloodLightGroundRadius)
  /**
   *  Defines the transition of [fillExtrusionFloodLightGroundRadius].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightGroundRadiusTransition: Transition by mutableStateOf(initialFillExtrusionFloodLightGroundRadiusTransition)
  /**
   *  The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightIntensity: DoubleValue by mutableStateOf(initialFillExtrusionFloodLightIntensity)
  /**
   *  Defines the transition of [fillExtrusionFloodLightIntensity].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightIntensityTransition: Transition by mutableStateOf(initialFillExtrusionFloodLightIntensityTransition)
  /**
   *  The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightWallRadius: DoubleValue by mutableStateOf(initialFillExtrusionFloodLightWallRadius)
  /**
   *  Defines the transition of [fillExtrusionFloodLightWallRadius].
   */
  @MapboxExperimental
  public var fillExtrusionFloodLightWallRadiusTransition: Transition by mutableStateOf(initialFillExtrusionFloodLightWallRadiusTransition)
  /**
   *  The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
   */
  public var fillExtrusionHeight: DoubleValue by mutableStateOf(initialFillExtrusionHeight)
  /**
   *  Defines the transition of [fillExtrusionHeight].
   */
  public var fillExtrusionHeightTransition: Transition by mutableStateOf(initialFillExtrusionHeightTransition)
  /**
   *  Controls the behavior of fill extrusion height over terrain Default value: "flat".
   */
  @MapboxExperimental
  public var fillExtrusionHeightAlignment: FillExtrusionHeightAlignmentValue by mutableStateOf(initialFillExtrusionHeightAlignment)
  /**
   *  If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   */
  @MapboxExperimental
  public var fillExtrusionLineWidth: DoubleValue by mutableStateOf(initialFillExtrusionLineWidth)
  /**
   *  Defines the transition of [fillExtrusionLineWidth].
   */
  @MapboxExperimental
  public var fillExtrusionLineWidthTransition: Transition by mutableStateOf(initialFillExtrusionLineWidthTransition)
  /**
   *  The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
   */
  public var fillExtrusionOpacity: DoubleValue by mutableStateOf(initialFillExtrusionOpacity)
  /**
   *  Defines the transition of [fillExtrusionOpacity].
   */
  public var fillExtrusionOpacityTransition: Transition by mutableStateOf(initialFillExtrusionOpacityTransition)
  /**
   *  Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var fillExtrusionPattern: ImageValue by mutableStateOf(initialFillExtrusionPattern)
  /**
   *  Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
   */
  @MapboxExperimental
  public var fillExtrusionRoundedRoof: BooleanValue by mutableStateOf(initialFillExtrusionRoundedRoof)
  /**
   *  The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
   */
  public var fillExtrusionTranslate: DoubleListValue by mutableStateOf(initialFillExtrusionTranslate)
  /**
   *  Defines the transition of [fillExtrusionTranslate].
   */
  public var fillExtrusionTranslateTransition: Transition by mutableStateOf(initialFillExtrusionTranslateTransition)
  /**
   *  Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
   */
  public var fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchorValue by mutableStateOf(initialFillExtrusionTranslateAnchor)
  /**
   *  Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
   */
  public var fillExtrusionVerticalGradient: BooleanValue by mutableStateOf(initialFillExtrusionVerticalGradient)
  /**
   *  A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   */
  @MapboxExperimental
  public var fillExtrusionVerticalScale: DoubleValue by mutableStateOf(initialFillExtrusionVerticalScale)
  /**
   *  Defines the transition of [fillExtrusionVerticalScale].
   */
  @MapboxExperimental
  public var fillExtrusionVerticalScaleTransition: Transition by mutableStateOf(initialFillExtrusionVerticalScaleTransition)
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
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionEdgeRadius(layerNode: LayerNode) {
    if (fillExtrusionEdgeRadius.notInitial) {
      layerNode.setProperty("fill-extrusion-edge-radius", fillExtrusionEdgeRadius.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionAmbientOcclusionGroundAttenuation(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionGroundAttenuation.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-ground-attenuation", fillExtrusionAmbientOcclusionGroundAttenuation.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionAmbientOcclusionGroundAttenuationTransition(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionGroundAttenuationTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-ground-attenuation-transition", fillExtrusionAmbientOcclusionGroundAttenuationTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionAmbientOcclusionGroundRadius(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionGroundRadius.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-ground-radius", fillExtrusionAmbientOcclusionGroundRadius.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionAmbientOcclusionGroundRadiusTransition(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionGroundRadiusTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-ground-radius-transition", fillExtrusionAmbientOcclusionGroundRadiusTransition.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionAmbientOcclusionIntensity(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionIntensity.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-intensity", fillExtrusionAmbientOcclusionIntensity.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionAmbientOcclusionIntensityTransition(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionIntensityTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-intensity-transition", fillExtrusionAmbientOcclusionIntensityTransition.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionAmbientOcclusionRadius(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionRadius.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-radius", fillExtrusionAmbientOcclusionRadius.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionAmbientOcclusionRadiusTransition(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionRadiusTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-radius-transition", fillExtrusionAmbientOcclusionRadiusTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionAmbientOcclusionWallRadius(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionWallRadius.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-wall-radius", fillExtrusionAmbientOcclusionWallRadius.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionAmbientOcclusionWallRadiusTransition(layerNode: LayerNode) {
    if (fillExtrusionAmbientOcclusionWallRadiusTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-ambient-occlusion-wall-radius-transition", fillExtrusionAmbientOcclusionWallRadiusTransition.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionBase(layerNode: LayerNode) {
    if (fillExtrusionBase.notInitial) {
      layerNode.setProperty("fill-extrusion-base", fillExtrusionBase.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionBaseTransition(layerNode: LayerNode) {
    if (fillExtrusionBaseTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-base-transition", fillExtrusionBaseTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionBaseAlignment(layerNode: LayerNode) {
    if (fillExtrusionBaseAlignment.notInitial) {
      layerNode.setProperty("fill-extrusion-base-alignment", fillExtrusionBaseAlignment.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionColor(layerNode: LayerNode) {
    if (fillExtrusionColor.notInitial) {
      layerNode.setProperty("fill-extrusion-color", fillExtrusionColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionColorUseTheme(layerNode: LayerNode) {
    if (fillExtrusionColorUseTheme.notInitial) {
      layerNode.setProperty("fill-extrusion-color-use-theme", fillExtrusionColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionColorTransition(layerNode: LayerNode) {
    if (fillExtrusionColorTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-color-transition", fillExtrusionColorTransition.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionCutoffFadeRange(layerNode: LayerNode) {
    if (fillExtrusionCutoffFadeRange.notInitial) {
      layerNode.setProperty("fill-extrusion-cutoff-fade-range", fillExtrusionCutoffFadeRange.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionEmissiveStrength(layerNode: LayerNode) {
    if (fillExtrusionEmissiveStrength.notInitial) {
      layerNode.setProperty("fill-extrusion-emissive-strength", fillExtrusionEmissiveStrength.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionEmissiveStrengthTransition(layerNode: LayerNode) {
    if (fillExtrusionEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-emissive-strength-transition", fillExtrusionEmissiveStrengthTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightColor(layerNode: LayerNode) {
    if (fillExtrusionFloodLightColor.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-color", fillExtrusionFloodLightColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightColorUseTheme(layerNode: LayerNode) {
    if (fillExtrusionFloodLightColorUseTheme.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-color-use-theme", fillExtrusionFloodLightColorUseTheme.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightColorTransition(layerNode: LayerNode) {
    if (fillExtrusionFloodLightColorTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-color-transition", fillExtrusionFloodLightColorTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightGroundAttenuation(layerNode: LayerNode) {
    if (fillExtrusionFloodLightGroundAttenuation.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-ground-attenuation", fillExtrusionFloodLightGroundAttenuation.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightGroundAttenuationTransition(layerNode: LayerNode) {
    if (fillExtrusionFloodLightGroundAttenuationTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-ground-attenuation-transition", fillExtrusionFloodLightGroundAttenuationTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightGroundRadius(layerNode: LayerNode) {
    if (fillExtrusionFloodLightGroundRadius.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-ground-radius", fillExtrusionFloodLightGroundRadius.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightGroundRadiusTransition(layerNode: LayerNode) {
    if (fillExtrusionFloodLightGroundRadiusTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-ground-radius-transition", fillExtrusionFloodLightGroundRadiusTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightIntensity(layerNode: LayerNode) {
    if (fillExtrusionFloodLightIntensity.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-intensity", fillExtrusionFloodLightIntensity.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightIntensityTransition(layerNode: LayerNode) {
    if (fillExtrusionFloodLightIntensityTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-intensity-transition", fillExtrusionFloodLightIntensityTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightWallRadius(layerNode: LayerNode) {
    if (fillExtrusionFloodLightWallRadius.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-wall-radius", fillExtrusionFloodLightWallRadius.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionFloodLightWallRadiusTransition(layerNode: LayerNode) {
    if (fillExtrusionFloodLightWallRadiusTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-flood-light-wall-radius-transition", fillExtrusionFloodLightWallRadiusTransition.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionHeight(layerNode: LayerNode) {
    if (fillExtrusionHeight.notInitial) {
      layerNode.setProperty("fill-extrusion-height", fillExtrusionHeight.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionHeightTransition(layerNode: LayerNode) {
    if (fillExtrusionHeightTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-height-transition", fillExtrusionHeightTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionHeightAlignment(layerNode: LayerNode) {
    if (fillExtrusionHeightAlignment.notInitial) {
      layerNode.setProperty("fill-extrusion-height-alignment", fillExtrusionHeightAlignment.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionLineWidth(layerNode: LayerNode) {
    if (fillExtrusionLineWidth.notInitial) {
      layerNode.setProperty("fill-extrusion-line-width", fillExtrusionLineWidth.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionLineWidthTransition(layerNode: LayerNode) {
    if (fillExtrusionLineWidthTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-line-width-transition", fillExtrusionLineWidthTransition.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionOpacity(layerNode: LayerNode) {
    if (fillExtrusionOpacity.notInitial) {
      layerNode.setProperty("fill-extrusion-opacity", fillExtrusionOpacity.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionOpacityTransition(layerNode: LayerNode) {
    if (fillExtrusionOpacityTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-opacity-transition", fillExtrusionOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionPattern(layerNode: LayerNode) {
    if (fillExtrusionPattern.notInitial) {
      fillExtrusionPattern.styleImage?.let {
        layerNode.addImage(it)
      }
      layerNode.setProperty("fill-extrusion-pattern", fillExtrusionPattern.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionRoundedRoof(layerNode: LayerNode) {
    if (fillExtrusionRoundedRoof.notInitial) {
      layerNode.setProperty("fill-extrusion-rounded-roof", fillExtrusionRoundedRoof.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionTranslate(layerNode: LayerNode) {
    if (fillExtrusionTranslate.notInitial) {
      layerNode.setProperty("fill-extrusion-translate", fillExtrusionTranslate.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionTranslateTransition(layerNode: LayerNode) {
    if (fillExtrusionTranslateTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-translate-transition", fillExtrusionTranslateTransition.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionTranslateAnchor(layerNode: LayerNode) {
    if (fillExtrusionTranslateAnchor.notInitial) {
      layerNode.setProperty("fill-extrusion-translate-anchor", fillExtrusionTranslateAnchor.value)
    }
  }
  @Composable
  private fun UpdateFillExtrusionVerticalGradient(layerNode: LayerNode) {
    if (fillExtrusionVerticalGradient.notInitial) {
      layerNode.setProperty("fill-extrusion-vertical-gradient", fillExtrusionVerticalGradient.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionVerticalScale(layerNode: LayerNode) {
    if (fillExtrusionVerticalScale.notInitial) {
      layerNode.setProperty("fill-extrusion-vertical-scale", fillExtrusionVerticalScale.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillExtrusionVerticalScaleTransition(layerNode: LayerNode) {
    if (fillExtrusionVerticalScaleTransition.notInitial) {
      layerNode.setProperty("fill-extrusion-vertical-scale-transition", fillExtrusionVerticalScaleTransition.value)
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
    UpdateFillExtrusionEdgeRadius(layerNode)
    UpdateFillExtrusionAmbientOcclusionGroundAttenuation(layerNode)
    UpdateFillExtrusionAmbientOcclusionGroundAttenuationTransition(layerNode)
    UpdateFillExtrusionAmbientOcclusionGroundRadius(layerNode)
    UpdateFillExtrusionAmbientOcclusionGroundRadiusTransition(layerNode)
    UpdateFillExtrusionAmbientOcclusionIntensity(layerNode)
    UpdateFillExtrusionAmbientOcclusionIntensityTransition(layerNode)
    UpdateFillExtrusionAmbientOcclusionRadius(layerNode)
    UpdateFillExtrusionAmbientOcclusionRadiusTransition(layerNode)
    UpdateFillExtrusionAmbientOcclusionWallRadius(layerNode)
    UpdateFillExtrusionAmbientOcclusionWallRadiusTransition(layerNode)
    UpdateFillExtrusionBase(layerNode)
    UpdateFillExtrusionBaseTransition(layerNode)
    UpdateFillExtrusionBaseAlignment(layerNode)
    UpdateFillExtrusionColor(layerNode)
    UpdateFillExtrusionColorUseTheme(layerNode)
    UpdateFillExtrusionColorTransition(layerNode)
    UpdateFillExtrusionCutoffFadeRange(layerNode)
    UpdateFillExtrusionEmissiveStrength(layerNode)
    UpdateFillExtrusionEmissiveStrengthTransition(layerNode)
    UpdateFillExtrusionFloodLightColor(layerNode)
    UpdateFillExtrusionFloodLightColorUseTheme(layerNode)
    UpdateFillExtrusionFloodLightColorTransition(layerNode)
    UpdateFillExtrusionFloodLightGroundAttenuation(layerNode)
    UpdateFillExtrusionFloodLightGroundAttenuationTransition(layerNode)
    UpdateFillExtrusionFloodLightGroundRadius(layerNode)
    UpdateFillExtrusionFloodLightGroundRadiusTransition(layerNode)
    UpdateFillExtrusionFloodLightIntensity(layerNode)
    UpdateFillExtrusionFloodLightIntensityTransition(layerNode)
    UpdateFillExtrusionFloodLightWallRadius(layerNode)
    UpdateFillExtrusionFloodLightWallRadiusTransition(layerNode)
    UpdateFillExtrusionHeight(layerNode)
    UpdateFillExtrusionHeightTransition(layerNode)
    UpdateFillExtrusionHeightAlignment(layerNode)
    UpdateFillExtrusionLineWidth(layerNode)
    UpdateFillExtrusionLineWidthTransition(layerNode)
    UpdateFillExtrusionOpacity(layerNode)
    UpdateFillExtrusionOpacityTransition(layerNode)
    UpdateFillExtrusionPattern(layerNode)
    UpdateFillExtrusionRoundedRoof(layerNode)
    UpdateFillExtrusionTranslate(layerNode)
    UpdateFillExtrusionTranslateTransition(layerNode)
    UpdateFillExtrusionTranslateAnchor(layerNode)
    UpdateFillExtrusionVerticalGradient(layerNode)
    UpdateFillExtrusionVerticalScale(layerNode)
    UpdateFillExtrusionVerticalScaleTransition(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.