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
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.ModelIdValue
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [ModelLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#model)
 */
@Stable
@MapboxExperimental
public class ModelLayerState
@OptIn(MapboxExperimental::class)
private constructor(
  initialModelId: ModelIdValue,
  initialModelAmbientOcclusionIntensity: DoubleValue,
  initialModelAmbientOcclusionIntensityTransition: Transition,
  initialModelCastShadows: BooleanValue,
  initialModelColor: ColorValue,
  initialModelColorUseTheme: StringValue,
  initialModelColorTransition: Transition,
  initialModelColorMixIntensity: DoubleValue,
  initialModelColorMixIntensityTransition: Transition,
  initialModelCutoffFadeRange: DoubleValue,
  initialModelElevationReference: ModelElevationReferenceValue,
  initialModelEmissiveStrength: DoubleValue,
  initialModelEmissiveStrengthTransition: Transition,
  initialModelHeightBasedEmissiveStrengthMultiplier: DoubleListValue,
  initialModelHeightBasedEmissiveStrengthMultiplierTransition: Transition,
  initialModelOpacity: DoubleValue,
  initialModelOpacityTransition: Transition,
  initialModelReceiveShadows: BooleanValue,
  initialModelRotation: DoubleListValue,
  initialModelRotationTransition: Transition,
  initialModelRoughness: DoubleValue,
  initialModelRoughnessTransition: Transition,
  initialModelScale: DoubleListValue,
  initialModelScaleTransition: Transition,
  initialModelScaleMode: ModelScaleModeValue,
  initialModelTranslation: DoubleListValue,
  initialModelTranslationTransition: Transition,
  initialModelType: ModelTypeValue,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [ModelLayerState].
   */
  @OptIn(MapboxExperimental::class)
  public constructor() : this(
    initialModelId = ModelIdValue.INITIAL,
    initialModelAmbientOcclusionIntensity = DoubleValue.INITIAL,
    initialModelAmbientOcclusionIntensityTransition = Transition.INITIAL,
    initialModelCastShadows = BooleanValue.INITIAL,
    initialModelColor = ColorValue.INITIAL,
    initialModelColorUseTheme = StringValue.INITIAL,
    initialModelColorTransition = Transition.INITIAL,
    initialModelColorMixIntensity = DoubleValue.INITIAL,
    initialModelColorMixIntensityTransition = Transition.INITIAL,
    initialModelCutoffFadeRange = DoubleValue.INITIAL,
    initialModelElevationReference = ModelElevationReferenceValue.INITIAL,
    initialModelEmissiveStrength = DoubleValue.INITIAL,
    initialModelEmissiveStrengthTransition = Transition.INITIAL,
    initialModelHeightBasedEmissiveStrengthMultiplier = DoubleListValue.INITIAL,
    initialModelHeightBasedEmissiveStrengthMultiplierTransition = Transition.INITIAL,
    initialModelOpacity = DoubleValue.INITIAL,
    initialModelOpacityTransition = Transition.INITIAL,
    initialModelReceiveShadows = BooleanValue.INITIAL,
    initialModelRotation = DoubleListValue.INITIAL,
    initialModelRotationTransition = Transition.INITIAL,
    initialModelRoughness = DoubleValue.INITIAL,
    initialModelRoughnessTransition = Transition.INITIAL,
    initialModelScale = DoubleListValue.INITIAL,
    initialModelScaleTransition = Transition.INITIAL,
    initialModelScaleMode = ModelScaleModeValue.INITIAL,
    initialModelTranslation = DoubleListValue.INITIAL,
    initialModelTranslationTransition = Transition.INITIAL,
    initialModelType = ModelTypeValue.INITIAL,
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
  private val modelIdState: MutableState<ModelIdValue> = mutableStateOf(initialModelId)
  /**
   *  Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
   */
  @MapboxExperimental
  public var modelId: ModelIdValue by modelIdState

  @MapboxExperimental
  private val modelAmbientOcclusionIntensityState: MutableState<DoubleValue> = mutableStateOf(initialModelAmbientOcclusionIntensity)
  /**
   *  Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelAmbientOcclusionIntensity: DoubleValue by modelAmbientOcclusionIntensityState

  @MapboxExperimental
  private val modelAmbientOcclusionIntensityTransitionState: MutableState<Transition> = mutableStateOf(initialModelAmbientOcclusionIntensityTransition)
  /**
   *  Defines the transition of [modelAmbientOcclusionIntensity].
   */
  @MapboxExperimental
  public var modelAmbientOcclusionIntensityTransition: Transition by modelAmbientOcclusionIntensityTransitionState

  @MapboxExperimental
  private val modelCastShadowsState: MutableState<BooleanValue> = mutableStateOf(initialModelCastShadows)
  /**
   *  Enable/Disable shadow casting for this layer Default value: true.
   */
  @MapboxExperimental
  public var modelCastShadows: BooleanValue by modelCastShadowsState

  @MapboxExperimental
  private val modelColorState: MutableState<ColorValue> = mutableStateOf(initialModelColor)
  /**
   *  The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Default value: "#ffffff".
   */
  @MapboxExperimental
  public var modelColor: ColorValue by modelColorState

  @MapboxExperimental
  private val modelColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialModelColorUseTheme)
  /**
   *  Overrides applying of color theme for [modelColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var modelColorUseTheme: StringValue by modelColorUseThemeState

  @MapboxExperimental
  private val modelColorTransitionState: MutableState<Transition> = mutableStateOf(initialModelColorTransition)
  /**
   *  Defines the transition of [modelColor].
   */
  @MapboxExperimental
  public var modelColorTransition: Transition by modelColorTransitionState

  @MapboxExperimental
  private val modelColorMixIntensityState: MutableState<DoubleValue> = mutableStateOf(initialModelColorMixIntensity)
  /**
   *  Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelColorMixIntensity: DoubleValue by modelColorMixIntensityState

  @MapboxExperimental
  private val modelColorMixIntensityTransitionState: MutableState<Transition> = mutableStateOf(initialModelColorMixIntensityTransition)
  /**
   *  Defines the transition of [modelColorMixIntensity].
   */
  @MapboxExperimental
  public var modelColorMixIntensityTransition: Transition by modelColorMixIntensityTransitionState

  @MapboxExperimental
  private val modelCutoffFadeRangeState: MutableState<DoubleValue> = mutableStateOf(initialModelCutoffFadeRange)
  /**
   *  This parameter defines the range for the fade-out effect before an automatic content cutoff  on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelCutoffFadeRange: DoubleValue by modelCutoffFadeRangeState

  @MapboxExperimental
  private val modelElevationReferenceState: MutableState<ModelElevationReferenceValue> = mutableStateOf(initialModelElevationReference)
  /**
   *  Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
   */
  @MapboxExperimental
  public var modelElevationReference: ModelElevationReferenceValue by modelElevationReferenceState

  @MapboxExperimental
  private val modelEmissiveStrengthState: MutableState<DoubleValue> = mutableStateOf(initialModelEmissiveStrength)
  /**
   *  Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   */
  @MapboxExperimental
  public var modelEmissiveStrength: DoubleValue by modelEmissiveStrengthState

  @MapboxExperimental
  private val modelEmissiveStrengthTransitionState: MutableState<Transition> = mutableStateOf(initialModelEmissiveStrengthTransition)
  /**
   *  Defines the transition of [modelEmissiveStrength].
   */
  @MapboxExperimental
  public var modelEmissiveStrengthTransition: Transition by modelEmissiveStrengthTransitionState

  @MapboxExperimental
  private val modelHeightBasedEmissiveStrengthMultiplierState: MutableState<DoubleListValue> = mutableStateOf(initialModelHeightBasedEmissiveStrengthMultiplier)
  /**
   *  Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   */
  @MapboxExperimental
  public var modelHeightBasedEmissiveStrengthMultiplier: DoubleListValue by modelHeightBasedEmissiveStrengthMultiplierState

  @MapboxExperimental
  private val modelHeightBasedEmissiveStrengthMultiplierTransitionState: MutableState<Transition> = mutableStateOf(initialModelHeightBasedEmissiveStrengthMultiplierTransition)
  /**
   *  Defines the transition of [modelHeightBasedEmissiveStrengthMultiplier].
   */
  @MapboxExperimental
  public var modelHeightBasedEmissiveStrengthMultiplierTransition: Transition by modelHeightBasedEmissiveStrengthMultiplierTransitionState

  @MapboxExperimental
  private val modelOpacityState: MutableState<DoubleValue> = mutableStateOf(initialModelOpacity)
  /**
   *  The opacity of the model layer. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelOpacity: DoubleValue by modelOpacityState

  @MapboxExperimental
  private val modelOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialModelOpacityTransition)
  /**
   *  Defines the transition of [modelOpacity].
   */
  @MapboxExperimental
  public var modelOpacityTransition: Transition by modelOpacityTransitionState

  @MapboxExperimental
  private val modelReceiveShadowsState: MutableState<BooleanValue> = mutableStateOf(initialModelReceiveShadows)
  /**
   *  Enable/Disable shadow receiving for this layer Default value: true.
   */
  @MapboxExperimental
  public var modelReceiveShadows: BooleanValue by modelReceiveShadowsState

  @MapboxExperimental
  private val modelRotationState: MutableState<DoubleListValue> = mutableStateOf(initialModelRotation)
  /**
   *  The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   */
  @MapboxExperimental
  public var modelRotation: DoubleListValue by modelRotationState

  @MapboxExperimental
  private val modelRotationTransitionState: MutableState<Transition> = mutableStateOf(initialModelRotationTransition)
  /**
   *  Defines the transition of [modelRotation].
   */
  @MapboxExperimental
  public var modelRotationTransition: Transition by modelRotationTransitionState

  @MapboxExperimental
  private val modelRoughnessState: MutableState<DoubleValue> = mutableStateOf(initialModelRoughness)
  /**
   *  Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelRoughness: DoubleValue by modelRoughnessState

  @MapboxExperimental
  private val modelRoughnessTransitionState: MutableState<Transition> = mutableStateOf(initialModelRoughnessTransition)
  /**
   *  Defines the transition of [modelRoughness].
   */
  @MapboxExperimental
  public var modelRoughnessTransition: Transition by modelRoughnessTransitionState

  @MapboxExperimental
  private val modelScaleState: MutableState<DoubleListValue> = mutableStateOf(initialModelScale)
  /**
   *  The scale of the model. Default value: [1,1,1].
   */
  @MapboxExperimental
  public var modelScale: DoubleListValue by modelScaleState

  @MapboxExperimental
  private val modelScaleTransitionState: MutableState<Transition> = mutableStateOf(initialModelScaleTransition)
  /**
   *  Defines the transition of [modelScale].
   */
  @MapboxExperimental
  public var modelScaleTransition: Transition by modelScaleTransitionState

  @MapboxExperimental
  private val modelScaleModeState: MutableState<ModelScaleModeValue> = mutableStateOf(initialModelScaleMode)
  /**
   *  Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
   */
  @MapboxExperimental
  public var modelScaleMode: ModelScaleModeValue by modelScaleModeState

  @MapboxExperimental
  private val modelTranslationState: MutableState<DoubleListValue> = mutableStateOf(initialModelTranslation)
  /**
   *  The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   */
  @MapboxExperimental
  public var modelTranslation: DoubleListValue by modelTranslationState

  @MapboxExperimental
  private val modelTranslationTransitionState: MutableState<Transition> = mutableStateOf(initialModelTranslationTransition)
  /**
   *  Defines the transition of [modelTranslation].
   */
  @MapboxExperimental
  public var modelTranslationTransition: Transition by modelTranslationTransitionState

  @MapboxExperimental
  private val modelTypeState: MutableState<ModelTypeValue> = mutableStateOf(initialModelType)
  /**
   *  Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
   */
  @MapboxExperimental
  public var modelType: ModelTypeValue by modelTypeState

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
  private fun UpdateModelId(layerNode: LayerNode) {
    if (modelId.notInitial) {
      modelId.modelInfo?.let {
        layerNode.addModel(it)
      }
      layerNode.setProperty("model-id", modelId.value)
    }
  }

  @Composable
  @OptIn(MapboxExperimental::class)
  internal fun UpdateProperties(layerNode: LayerNode) {
    UpdateModelId(layerNode)
    ActionWhenNotInitial(layerNode.setPropertyAction, modelAmbientOcclusionIntensityState, "model-ambient-occlusion-intensity")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelAmbientOcclusionIntensityTransitionState, "model-ambient-occlusion-intensity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelCastShadowsState, "model-cast-shadows")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelColorState, "model-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelColorUseThemeState, "model-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelColorTransitionState, "model-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelColorMixIntensityState, "model-color-mix-intensity")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelColorMixIntensityTransitionState, "model-color-mix-intensity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelCutoffFadeRangeState, "model-cutoff-fade-range")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelElevationReferenceState, "model-elevation-reference")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelEmissiveStrengthState, "model-emissive-strength")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelEmissiveStrengthTransitionState, "model-emissive-strength-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelHeightBasedEmissiveStrengthMultiplierState, "model-height-based-emissive-strength-multiplier")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelHeightBasedEmissiveStrengthMultiplierTransitionState, "model-height-based-emissive-strength-multiplier-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelOpacityState, "model-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelOpacityTransitionState, "model-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelReceiveShadowsState, "model-receive-shadows")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelRotationState, "model-rotation")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelRotationTransitionState, "model-rotation-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelRoughnessState, "model-roughness")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelRoughnessTransitionState, "model-roughness-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelScaleState, "model-scale")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelScaleTransitionState, "model-scale-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelScaleModeState, "model-scale-mode")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelTranslationState, "model-translation")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelTranslationTransitionState, "model-translation-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, modelTypeState, "model-type")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.