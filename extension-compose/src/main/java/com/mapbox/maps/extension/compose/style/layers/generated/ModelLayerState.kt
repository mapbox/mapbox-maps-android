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
public class ModelLayerState private constructor(
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

  /**
   *  Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
   */
  @MapboxExperimental
  public var modelId: ModelIdValue by mutableStateOf(initialModelId)
  /**
   *  Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelAmbientOcclusionIntensity: DoubleValue by mutableStateOf(initialModelAmbientOcclusionIntensity)
  /**
   *  Defines the transition of [modelAmbientOcclusionIntensity].
   */
  @MapboxExperimental
  public var modelAmbientOcclusionIntensityTransition: Transition by mutableStateOf(initialModelAmbientOcclusionIntensityTransition)
  /**
   *  Enable/Disable shadow casting for this layer Default value: true.
   */
  @MapboxExperimental
  public var modelCastShadows: BooleanValue by mutableStateOf(initialModelCastShadows)
  /**
   *  The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Default value: "#ffffff".
   */
  @MapboxExperimental
  public var modelColor: ColorValue by mutableStateOf(initialModelColor)
  /**
   *  Overrides applying of color theme for [modelColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var modelColorUseTheme: StringValue by mutableStateOf(initialModelColorUseTheme)
  /**
   *  Defines the transition of [modelColor].
   */
  @MapboxExperimental
  public var modelColorTransition: Transition by mutableStateOf(initialModelColorTransition)
  /**
   *  Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelColorMixIntensity: DoubleValue by mutableStateOf(initialModelColorMixIntensity)
  /**
   *  Defines the transition of [modelColorMixIntensity].
   */
  @MapboxExperimental
  public var modelColorMixIntensityTransition: Transition by mutableStateOf(initialModelColorMixIntensityTransition)
  /**
   *  This parameter defines the range for the fade-out effect before an automatic content cutoff  on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelCutoffFadeRange: DoubleValue by mutableStateOf(initialModelCutoffFadeRange)
  /**
   *  Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
   */
  @MapboxExperimental
  public var modelElevationReference: ModelElevationReferenceValue by mutableStateOf(initialModelElevationReference)
  /**
   *  Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   */
  @MapboxExperimental
  public var modelEmissiveStrength: DoubleValue by mutableStateOf(initialModelEmissiveStrength)
  /**
   *  Defines the transition of [modelEmissiveStrength].
   */
  @MapboxExperimental
  public var modelEmissiveStrengthTransition: Transition by mutableStateOf(initialModelEmissiveStrengthTransition)
  /**
   *  Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   */
  @MapboxExperimental
  public var modelHeightBasedEmissiveStrengthMultiplier: DoubleListValue by mutableStateOf(initialModelHeightBasedEmissiveStrengthMultiplier)
  /**
   *  Defines the transition of [modelHeightBasedEmissiveStrengthMultiplier].
   */
  @MapboxExperimental
  public var modelHeightBasedEmissiveStrengthMultiplierTransition: Transition by mutableStateOf(initialModelHeightBasedEmissiveStrengthMultiplierTransition)
  /**
   *  The opacity of the model layer. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelOpacity: DoubleValue by mutableStateOf(initialModelOpacity)
  /**
   *  Defines the transition of [modelOpacity].
   */
  @MapboxExperimental
  public var modelOpacityTransition: Transition by mutableStateOf(initialModelOpacityTransition)
  /**
   *  Enable/Disable shadow receiving for this layer Default value: true.
   */
  @MapboxExperimental
  public var modelReceiveShadows: BooleanValue by mutableStateOf(initialModelReceiveShadows)
  /**
   *  The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   */
  @MapboxExperimental
  public var modelRotation: DoubleListValue by mutableStateOf(initialModelRotation)
  /**
   *  Defines the transition of [modelRotation].
   */
  @MapboxExperimental
  public var modelRotationTransition: Transition by mutableStateOf(initialModelRotationTransition)
  /**
   *  Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  public var modelRoughness: DoubleValue by mutableStateOf(initialModelRoughness)
  /**
   *  Defines the transition of [modelRoughness].
   */
  @MapboxExperimental
  public var modelRoughnessTransition: Transition by mutableStateOf(initialModelRoughnessTransition)
  /**
   *  The scale of the model. Default value: [1,1,1].
   */
  @MapboxExperimental
  public var modelScale: DoubleListValue by mutableStateOf(initialModelScale)
  /**
   *  Defines the transition of [modelScale].
   */
  @MapboxExperimental
  public var modelScaleTransition: Transition by mutableStateOf(initialModelScaleTransition)
  /**
   *  Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
   */
  @MapboxExperimental
  public var modelScaleMode: ModelScaleModeValue by mutableStateOf(initialModelScaleMode)
  /**
   *  The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   */
  @MapboxExperimental
  public var modelTranslation: DoubleListValue by mutableStateOf(initialModelTranslation)
  /**
   *  Defines the transition of [modelTranslation].
   */
  @MapboxExperimental
  public var modelTranslationTransition: Transition by mutableStateOf(initialModelTranslationTransition)
  /**
   *  Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
   */
  @MapboxExperimental
  public var modelType: ModelTypeValue by mutableStateOf(initialModelType)
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
  private fun UpdateModelAmbientOcclusionIntensity(layerNode: LayerNode) {
    if (modelAmbientOcclusionIntensity.notInitial) {
      layerNode.setProperty("model-ambient-occlusion-intensity", modelAmbientOcclusionIntensity.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelAmbientOcclusionIntensityTransition(layerNode: LayerNode) {
    if (modelAmbientOcclusionIntensityTransition.notInitial) {
      layerNode.setProperty("model-ambient-occlusion-intensity-transition", modelAmbientOcclusionIntensityTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelCastShadows(layerNode: LayerNode) {
    if (modelCastShadows.notInitial) {
      layerNode.setProperty("model-cast-shadows", modelCastShadows.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelColor(layerNode: LayerNode) {
    if (modelColor.notInitial) {
      layerNode.setProperty("model-color", modelColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelColorUseTheme(layerNode: LayerNode) {
    if (modelColorUseTheme.notInitial) {
      layerNode.setProperty("model-color-use-theme", modelColorUseTheme.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelColorTransition(layerNode: LayerNode) {
    if (modelColorTransition.notInitial) {
      layerNode.setProperty("model-color-transition", modelColorTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelColorMixIntensity(layerNode: LayerNode) {
    if (modelColorMixIntensity.notInitial) {
      layerNode.setProperty("model-color-mix-intensity", modelColorMixIntensity.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelColorMixIntensityTransition(layerNode: LayerNode) {
    if (modelColorMixIntensityTransition.notInitial) {
      layerNode.setProperty("model-color-mix-intensity-transition", modelColorMixIntensityTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelCutoffFadeRange(layerNode: LayerNode) {
    if (modelCutoffFadeRange.notInitial) {
      layerNode.setProperty("model-cutoff-fade-range", modelCutoffFadeRange.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelElevationReference(layerNode: LayerNode) {
    if (modelElevationReference.notInitial) {
      layerNode.setProperty("model-elevation-reference", modelElevationReference.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelEmissiveStrength(layerNode: LayerNode) {
    if (modelEmissiveStrength.notInitial) {
      layerNode.setProperty("model-emissive-strength", modelEmissiveStrength.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelEmissiveStrengthTransition(layerNode: LayerNode) {
    if (modelEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("model-emissive-strength-transition", modelEmissiveStrengthTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelHeightBasedEmissiveStrengthMultiplier(layerNode: LayerNode) {
    if (modelHeightBasedEmissiveStrengthMultiplier.notInitial) {
      layerNode.setProperty("model-height-based-emissive-strength-multiplier", modelHeightBasedEmissiveStrengthMultiplier.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelHeightBasedEmissiveStrengthMultiplierTransition(layerNode: LayerNode) {
    if (modelHeightBasedEmissiveStrengthMultiplierTransition.notInitial) {
      layerNode.setProperty("model-height-based-emissive-strength-multiplier-transition", modelHeightBasedEmissiveStrengthMultiplierTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelOpacity(layerNode: LayerNode) {
    if (modelOpacity.notInitial) {
      layerNode.setProperty("model-opacity", modelOpacity.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelOpacityTransition(layerNode: LayerNode) {
    if (modelOpacityTransition.notInitial) {
      layerNode.setProperty("model-opacity-transition", modelOpacityTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelReceiveShadows(layerNode: LayerNode) {
    if (modelReceiveShadows.notInitial) {
      layerNode.setProperty("model-receive-shadows", modelReceiveShadows.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelRotation(layerNode: LayerNode) {
    if (modelRotation.notInitial) {
      layerNode.setProperty("model-rotation", modelRotation.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelRotationTransition(layerNode: LayerNode) {
    if (modelRotationTransition.notInitial) {
      layerNode.setProperty("model-rotation-transition", modelRotationTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelRoughness(layerNode: LayerNode) {
    if (modelRoughness.notInitial) {
      layerNode.setProperty("model-roughness", modelRoughness.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelRoughnessTransition(layerNode: LayerNode) {
    if (modelRoughnessTransition.notInitial) {
      layerNode.setProperty("model-roughness-transition", modelRoughnessTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelScale(layerNode: LayerNode) {
    if (modelScale.notInitial) {
      layerNode.setProperty("model-scale", modelScale.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelScaleTransition(layerNode: LayerNode) {
    if (modelScaleTransition.notInitial) {
      layerNode.setProperty("model-scale-transition", modelScaleTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelScaleMode(layerNode: LayerNode) {
    if (modelScaleMode.notInitial) {
      layerNode.setProperty("model-scale-mode", modelScaleMode.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelTranslation(layerNode: LayerNode) {
    if (modelTranslation.notInitial) {
      layerNode.setProperty("model-translation", modelTranslation.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelTranslationTransition(layerNode: LayerNode) {
    if (modelTranslationTransition.notInitial) {
      layerNode.setProperty("model-translation-transition", modelTranslationTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateModelType(layerNode: LayerNode) {
    if (modelType.notInitial) {
      layerNode.setProperty("model-type", modelType.value)
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
    UpdateModelId(layerNode)
    UpdateModelAmbientOcclusionIntensity(layerNode)
    UpdateModelAmbientOcclusionIntensityTransition(layerNode)
    UpdateModelCastShadows(layerNode)
    UpdateModelColor(layerNode)
    UpdateModelColorUseTheme(layerNode)
    UpdateModelColorTransition(layerNode)
    UpdateModelColorMixIntensity(layerNode)
    UpdateModelColorMixIntensityTransition(layerNode)
    UpdateModelCutoffFadeRange(layerNode)
    UpdateModelElevationReference(layerNode)
    UpdateModelEmissiveStrength(layerNode)
    UpdateModelEmissiveStrengthTransition(layerNode)
    UpdateModelHeightBasedEmissiveStrengthMultiplier(layerNode)
    UpdateModelHeightBasedEmissiveStrengthMultiplierTransition(layerNode)
    UpdateModelOpacity(layerNode)
    UpdateModelOpacityTransition(layerNode)
    UpdateModelReceiveShadows(layerNode)
    UpdateModelRotation(layerNode)
    UpdateModelRotationTransition(layerNode)
    UpdateModelRoughness(layerNode)
    UpdateModelRoughnessTransition(layerNode)
    UpdateModelScale(layerNode)
    UpdateModelScaleTransition(layerNode)
    UpdateModelScaleMode(layerNode)
    UpdateModelTranslation(layerNode)
    UpdateModelTranslationTransition(layerNode)
    UpdateModelType(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.