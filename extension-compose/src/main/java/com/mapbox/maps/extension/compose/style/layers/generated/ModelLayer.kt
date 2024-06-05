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
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.ModelIdValue
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * A layer to render 3D Models.
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param modelId Model to render.
 * @param modelAmbientOcclusionIntensity Intensity of the ambient occlusion if present in the 3D model.
 * @param modelAmbientOcclusionIntensityTransition Defines the transition of [modelAmbientOcclusionIntensity].
 * @param modelCastShadows Enable/Disable shadow casting for this layer
 * @param modelColor The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
 * @param modelColorTransition Defines the transition of [modelColor].
 * @param modelColorMixIntensity Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
 * @param modelColorMixIntensityTransition Defines the transition of [modelColorMixIntensity].
 * @param modelCutoffFadeRange This parameter defines the range for the fade-out effect before an automatic content cutoff  on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled.
 * @param modelEmissiveStrength Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
 * @param modelEmissiveStrengthTransition Defines the transition of [modelEmissiveStrength].
 * @param modelHeightBasedEmissiveStrengthMultiplier Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
 * @param modelHeightBasedEmissiveStrengthMultiplierTransition Defines the transition of [modelHeightBasedEmissiveStrengthMultiplier].
 * @param modelOpacity The opacity of the model layer.
 * @param modelOpacityTransition Defines the transition of [modelOpacity].
 * @param modelReceiveShadows Enable/Disable shadow receiving for this layer
 * @param modelRotation The rotation of the model in euler angles [lon, lat, z].
 * @param modelRotationTransition Defines the transition of [modelRotation].
 * @param modelRoughness Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
 * @param modelRoughnessTransition Defines the transition of [modelRoughness].
 * @param modelScale The scale of the model.
 * @param modelScaleTransition Defines the transition of [modelScale].
 * @param modelScaleMode Defines scaling mode. Only applies to location-indicator type layers.
 * @param modelTranslation The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
 * @param modelTranslationTransition Defines the transition of [modelTranslation].
 * @param modelType Defines rendering behavior of model in respect to other 3D scene objects.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun ModelLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("model")
  },
  modelId: ModelIdValue = ModelIdValue.INITIAL,
  modelAmbientOcclusionIntensity: DoubleValue = DoubleValue.INITIAL,
  modelAmbientOcclusionIntensityTransition: Transition = Transition.INITIAL,
  modelCastShadows: BooleanValue = BooleanValue.INITIAL,
  modelColor: ColorValue = ColorValue.INITIAL,
  modelColorTransition: Transition = Transition.INITIAL,
  modelColorMixIntensity: DoubleValue = DoubleValue.INITIAL,
  modelColorMixIntensityTransition: Transition = Transition.INITIAL,
  modelCutoffFadeRange: DoubleValue = DoubleValue.INITIAL,
  modelEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  modelEmissiveStrengthTransition: Transition = Transition.INITIAL,
  modelHeightBasedEmissiveStrengthMultiplier: DoubleListValue = DoubleListValue.INITIAL,
  modelHeightBasedEmissiveStrengthMultiplierTransition: Transition = Transition.INITIAL,
  modelOpacity: DoubleValue = DoubleValue.INITIAL,
  modelOpacityTransition: Transition = Transition.INITIAL,
  modelReceiveShadows: BooleanValue = BooleanValue.INITIAL,
  modelRotation: DoubleListValue = DoubleListValue.INITIAL,
  modelRotationTransition: Transition = Transition.INITIAL,
  modelRoughness: DoubleValue = DoubleValue.INITIAL,
  modelRoughnessTransition: Transition = Transition.INITIAL,
  modelScale: DoubleListValue = DoubleListValue.INITIAL,
  modelScaleTransition: Transition = Transition.INITIAL,
  modelScaleMode: ModelScaleModeValue = ModelScaleModeValue.INITIAL,
  modelTranslation: DoubleListValue = DoubleListValue.INITIAL,
  modelTranslationTransition: Transition = Transition.INITIAL,
  modelType: ModelTypeValue = ModelTypeValue.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of ModelLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "model",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (modelId.notInitial) {
          modelId.modelInfo?.let {
            addModel(it)
          }
          setProperty("model-id", modelId.value)
        }
        if (modelAmbientOcclusionIntensity.notInitial) {
          setProperty("model-ambient-occlusion-intensity", modelAmbientOcclusionIntensity.value)
        }
        if (modelAmbientOcclusionIntensityTransition.notInitial) {
          setProperty("model-ambient-occlusion-intensity-transition", modelAmbientOcclusionIntensityTransition.value)
        }
        if (modelCastShadows.notInitial) {
          setProperty("model-cast-shadows", modelCastShadows.value)
        }
        if (modelColor.notInitial) {
          setProperty("model-color", modelColor.value)
        }
        if (modelColorTransition.notInitial) {
          setProperty("model-color-transition", modelColorTransition.value)
        }
        if (modelColorMixIntensity.notInitial) {
          setProperty("model-color-mix-intensity", modelColorMixIntensity.value)
        }
        if (modelColorMixIntensityTransition.notInitial) {
          setProperty("model-color-mix-intensity-transition", modelColorMixIntensityTransition.value)
        }
        if (modelCutoffFadeRange.notInitial) {
          setProperty("model-cutoff-fade-range", modelCutoffFadeRange.value)
        }
        if (modelEmissiveStrength.notInitial) {
          setProperty("model-emissive-strength", modelEmissiveStrength.value)
        }
        if (modelEmissiveStrengthTransition.notInitial) {
          setProperty("model-emissive-strength-transition", modelEmissiveStrengthTransition.value)
        }
        if (modelHeightBasedEmissiveStrengthMultiplier.notInitial) {
          setProperty("model-height-based-emissive-strength-multiplier", modelHeightBasedEmissiveStrengthMultiplier.value)
        }
        if (modelHeightBasedEmissiveStrengthMultiplierTransition.notInitial) {
          setProperty("model-height-based-emissive-strength-multiplier-transition", modelHeightBasedEmissiveStrengthMultiplierTransition.value)
        }
        if (modelOpacity.notInitial) {
          setProperty("model-opacity", modelOpacity.value)
        }
        if (modelOpacityTransition.notInitial) {
          setProperty("model-opacity-transition", modelOpacityTransition.value)
        }
        if (modelReceiveShadows.notInitial) {
          setProperty("model-receive-shadows", modelReceiveShadows.value)
        }
        if (modelRotation.notInitial) {
          setProperty("model-rotation", modelRotation.value)
        }
        if (modelRotationTransition.notInitial) {
          setProperty("model-rotation-transition", modelRotationTransition.value)
        }
        if (modelRoughness.notInitial) {
          setProperty("model-roughness", modelRoughness.value)
        }
        if (modelRoughnessTransition.notInitial) {
          setProperty("model-roughness-transition", modelRoughnessTransition.value)
        }
        if (modelScale.notInitial) {
          setProperty("model-scale", modelScale.value)
        }
        if (modelScaleTransition.notInitial) {
          setProperty("model-scale-transition", modelScaleTransition.value)
        }
        if (modelScaleMode.notInitial) {
          setProperty("model-scale-mode", modelScaleMode.value)
        }
        if (modelTranslation.notInitial) {
          setProperty("model-translation", modelTranslation.value)
        }
        if (modelTranslationTransition.notInitial) {
          setProperty("model-translation-transition", modelTranslationTransition.value)
        }
        if (modelType.notInitial) {
          setProperty("model-type", modelType.value)
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
      update(modelId) {
        modelId.modelInfo?.let {
          addModel(it)
        }
        setProperty("model-id", modelId.value)
      }
      update(modelAmbientOcclusionIntensity) {
        setProperty("model-ambient-occlusion-intensity", modelAmbientOcclusionIntensity.value)
      }
      update(modelAmbientOcclusionIntensityTransition) {
        setProperty("model-ambient-occlusion-intensity-transition", modelAmbientOcclusionIntensityTransition.value)
      }
      update(modelCastShadows) {
        setProperty("model-cast-shadows", modelCastShadows.value)
      }
      update(modelColor) {
        setProperty("model-color", modelColor.value)
      }
      update(modelColorTransition) {
        setProperty("model-color-transition", modelColorTransition.value)
      }
      update(modelColorMixIntensity) {
        setProperty("model-color-mix-intensity", modelColorMixIntensity.value)
      }
      update(modelColorMixIntensityTransition) {
        setProperty("model-color-mix-intensity-transition", modelColorMixIntensityTransition.value)
      }
      update(modelCutoffFadeRange) {
        setProperty("model-cutoff-fade-range", modelCutoffFadeRange.value)
      }
      update(modelEmissiveStrength) {
        setProperty("model-emissive-strength", modelEmissiveStrength.value)
      }
      update(modelEmissiveStrengthTransition) {
        setProperty("model-emissive-strength-transition", modelEmissiveStrengthTransition.value)
      }
      update(modelHeightBasedEmissiveStrengthMultiplier) {
        setProperty("model-height-based-emissive-strength-multiplier", modelHeightBasedEmissiveStrengthMultiplier.value)
      }
      update(modelHeightBasedEmissiveStrengthMultiplierTransition) {
        setProperty("model-height-based-emissive-strength-multiplier-transition", modelHeightBasedEmissiveStrengthMultiplierTransition.value)
      }
      update(modelOpacity) {
        setProperty("model-opacity", modelOpacity.value)
      }
      update(modelOpacityTransition) {
        setProperty("model-opacity-transition", modelOpacityTransition.value)
      }
      update(modelReceiveShadows) {
        setProperty("model-receive-shadows", modelReceiveShadows.value)
      }
      update(modelRotation) {
        setProperty("model-rotation", modelRotation.value)
      }
      update(modelRotationTransition) {
        setProperty("model-rotation-transition", modelRotationTransition.value)
      }
      update(modelRoughness) {
        setProperty("model-roughness", modelRoughness.value)
      }
      update(modelRoughnessTransition) {
        setProperty("model-roughness-transition", modelRoughnessTransition.value)
      }
      update(modelScale) {
        setProperty("model-scale", modelScale.value)
      }
      update(modelScaleTransition) {
        setProperty("model-scale-transition", modelScaleTransition.value)
      }
      update(modelScaleMode) {
        setProperty("model-scale-mode", modelScaleMode.value)
      }
      update(modelTranslation) {
        setProperty("model-translation", modelTranslation.value)
      }
      update(modelTranslationTransition) {
        setProperty("model-translation-transition", modelTranslationTransition.value)
      }
      update(modelType) {
        setProperty("model-type", modelType.value)
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