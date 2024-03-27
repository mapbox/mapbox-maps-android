// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * A layer to render 3D Models.
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 * @param modelId Model to render.
 * @param modelAmbientOcclusionIntensity Intensity of the ambient occlusion if present in the 3D model.
 * @param modelCastShadows Enable/Disable shadow casting for this layer
 * @param modelColor The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
 * @param modelColorMixIntensity Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
 * @param modelCutoffFadeRange This parameter defines the range for the fade-out effect before an automatic content cutoff  on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled.
 * @param modelEmissiveStrength Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
 * @param modelHeightBasedEmissiveStrengthMultiplier Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
 * @param modelOpacity The opacity of the model layer.
 * @param modelReceiveShadows Enable/Disable shadow receiving for this layer
 * @param modelRotation The rotation of the model in euler angles [lon, lat, z].
 * @param modelRoughness Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
 * @param modelScale The scale of the model.
 * @param modelScaleMode Defines scaling mode. Only applies to location-indicator type layers.
 * @param modelTranslation The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
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
  layerId: String,
  sourceId: String,
  modelId: ModelId = ModelId.default,
  modelAmbientOcclusionIntensity: ModelAmbientOcclusionIntensity = ModelAmbientOcclusionIntensity.default,
  modelAmbientOcclusionIntensityTransition: Transition = Transition.default,
  modelCastShadows: ModelCastShadows = ModelCastShadows.default,
  modelColor: ModelColor = ModelColor.default,
  modelColorTransition: Transition = Transition.default,
  modelColorMixIntensity: ModelColorMixIntensity = ModelColorMixIntensity.default,
  modelColorMixIntensityTransition: Transition = Transition.default,
  modelCutoffFadeRange: ModelCutoffFadeRange = ModelCutoffFadeRange.default,
  modelEmissiveStrength: ModelEmissiveStrength = ModelEmissiveStrength.default,
  modelEmissiveStrengthTransition: Transition = Transition.default,
  modelHeightBasedEmissiveStrengthMultiplier: ModelHeightBasedEmissiveStrengthMultiplier = ModelHeightBasedEmissiveStrengthMultiplier.default,
  modelHeightBasedEmissiveStrengthMultiplierTransition: Transition = Transition.default,
  modelOpacity: ModelOpacity = ModelOpacity.default,
  modelOpacityTransition: Transition = Transition.default,
  modelReceiveShadows: ModelReceiveShadows = ModelReceiveShadows.default,
  modelRotation: ModelRotation = ModelRotation.default,
  modelRotationTransition: Transition = Transition.default,
  modelRoughness: ModelRoughness = ModelRoughness.default,
  modelRoughnessTransition: Transition = Transition.default,
  modelScale: ModelScale = ModelScale.default,
  modelScaleTransition: Transition = Transition.default,
  modelScaleMode: ModelScaleMode = ModelScaleMode.default,
  modelTranslation: ModelTranslation = ModelTranslation.default,
  modelTranslationTransition: Transition = Transition.default,
  modelType: ModelType = ModelType.default,
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
        layerType = "model",
        layerId = layerId,
        sourceId = sourceId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (modelId != ModelId.default) {
          setProperty(ModelId.NAME, modelId.value)
        }
        if (modelAmbientOcclusionIntensity != ModelAmbientOcclusionIntensity.default) {
          setProperty(ModelAmbientOcclusionIntensity.NAME, modelAmbientOcclusionIntensity.value)
        }
        if (modelAmbientOcclusionIntensityTransition != Transition.default) {
          setProperty(ModelAmbientOcclusionIntensity.TRANSITION_NAME, modelAmbientOcclusionIntensityTransition.value)
        }
        if (modelCastShadows != ModelCastShadows.default) {
          setProperty(ModelCastShadows.NAME, modelCastShadows.value)
        }
        if (modelColor != ModelColor.default) {
          setProperty(ModelColor.NAME, modelColor.value)
        }
        if (modelColorTransition != Transition.default) {
          setProperty(ModelColor.TRANSITION_NAME, modelColorTransition.value)
        }
        if (modelColorMixIntensity != ModelColorMixIntensity.default) {
          setProperty(ModelColorMixIntensity.NAME, modelColorMixIntensity.value)
        }
        if (modelColorMixIntensityTransition != Transition.default) {
          setProperty(ModelColorMixIntensity.TRANSITION_NAME, modelColorMixIntensityTransition.value)
        }
        if (modelCutoffFadeRange != ModelCutoffFadeRange.default) {
          setProperty(ModelCutoffFadeRange.NAME, modelCutoffFadeRange.value)
        }
        if (modelEmissiveStrength != ModelEmissiveStrength.default) {
          setProperty(ModelEmissiveStrength.NAME, modelEmissiveStrength.value)
        }
        if (modelEmissiveStrengthTransition != Transition.default) {
          setProperty(ModelEmissiveStrength.TRANSITION_NAME, modelEmissiveStrengthTransition.value)
        }
        if (modelHeightBasedEmissiveStrengthMultiplier != ModelHeightBasedEmissiveStrengthMultiplier.default) {
          setProperty(ModelHeightBasedEmissiveStrengthMultiplier.NAME, modelHeightBasedEmissiveStrengthMultiplier.value)
        }
        if (modelHeightBasedEmissiveStrengthMultiplierTransition != Transition.default) {
          setProperty(ModelHeightBasedEmissiveStrengthMultiplier.TRANSITION_NAME, modelHeightBasedEmissiveStrengthMultiplierTransition.value)
        }
        if (modelOpacity != ModelOpacity.default) {
          setProperty(ModelOpacity.NAME, modelOpacity.value)
        }
        if (modelOpacityTransition != Transition.default) {
          setProperty(ModelOpacity.TRANSITION_NAME, modelOpacityTransition.value)
        }
        if (modelReceiveShadows != ModelReceiveShadows.default) {
          setProperty(ModelReceiveShadows.NAME, modelReceiveShadows.value)
        }
        if (modelRotation != ModelRotation.default) {
          setProperty(ModelRotation.NAME, modelRotation.value)
        }
        if (modelRotationTransition != Transition.default) {
          setProperty(ModelRotation.TRANSITION_NAME, modelRotationTransition.value)
        }
        if (modelRoughness != ModelRoughness.default) {
          setProperty(ModelRoughness.NAME, modelRoughness.value)
        }
        if (modelRoughnessTransition != Transition.default) {
          setProperty(ModelRoughness.TRANSITION_NAME, modelRoughnessTransition.value)
        }
        if (modelScale != ModelScale.default) {
          setProperty(ModelScale.NAME, modelScale.value)
        }
        if (modelScaleTransition != Transition.default) {
          setProperty(ModelScale.TRANSITION_NAME, modelScaleTransition.value)
        }
        if (modelScaleMode != ModelScaleMode.default) {
          setProperty(ModelScaleMode.NAME, modelScaleMode.value)
        }
        if (modelTranslation != ModelTranslation.default) {
          setProperty(ModelTranslation.NAME, modelTranslation.value)
        }
        if (modelTranslationTransition != Transition.default) {
          setProperty(ModelTranslation.TRANSITION_NAME, modelTranslationTransition.value)
        }
        if (modelType != ModelType.default) {
          setProperty(ModelType.NAME, modelType.value)
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
      update(layerId) {
        setConstructorProperty("id", Value(layerId))
      }
      update(sourceId) {
        setConstructorProperty("source", Value(sourceId))
      }
      update(modelId) {
        setProperty(ModelId.NAME, modelId.value)
      }
      update(modelAmbientOcclusionIntensity) {
        setProperty(ModelAmbientOcclusionIntensity.NAME, modelAmbientOcclusionIntensity.value)
      }
      update(modelAmbientOcclusionIntensityTransition) {
        setProperty(ModelAmbientOcclusionIntensity.TRANSITION_NAME, modelAmbientOcclusionIntensityTransition.value)
      }
      update(modelCastShadows) {
        setProperty(ModelCastShadows.NAME, modelCastShadows.value)
      }
      update(modelColor) {
        setProperty(ModelColor.NAME, modelColor.value)
      }
      update(modelColorTransition) {
        setProperty(ModelColor.TRANSITION_NAME, modelColorTransition.value)
      }
      update(modelColorMixIntensity) {
        setProperty(ModelColorMixIntensity.NAME, modelColorMixIntensity.value)
      }
      update(modelColorMixIntensityTransition) {
        setProperty(ModelColorMixIntensity.TRANSITION_NAME, modelColorMixIntensityTransition.value)
      }
      update(modelCutoffFadeRange) {
        setProperty(ModelCutoffFadeRange.NAME, modelCutoffFadeRange.value)
      }
      update(modelEmissiveStrength) {
        setProperty(ModelEmissiveStrength.NAME, modelEmissiveStrength.value)
      }
      update(modelEmissiveStrengthTransition) {
        setProperty(ModelEmissiveStrength.TRANSITION_NAME, modelEmissiveStrengthTransition.value)
      }
      update(modelHeightBasedEmissiveStrengthMultiplier) {
        setProperty(ModelHeightBasedEmissiveStrengthMultiplier.NAME, modelHeightBasedEmissiveStrengthMultiplier.value)
      }
      update(modelHeightBasedEmissiveStrengthMultiplierTransition) {
        setProperty(ModelHeightBasedEmissiveStrengthMultiplier.TRANSITION_NAME, modelHeightBasedEmissiveStrengthMultiplierTransition.value)
      }
      update(modelOpacity) {
        setProperty(ModelOpacity.NAME, modelOpacity.value)
      }
      update(modelOpacityTransition) {
        setProperty(ModelOpacity.TRANSITION_NAME, modelOpacityTransition.value)
      }
      update(modelReceiveShadows) {
        setProperty(ModelReceiveShadows.NAME, modelReceiveShadows.value)
      }
      update(modelRotation) {
        setProperty(ModelRotation.NAME, modelRotation.value)
      }
      update(modelRotationTransition) {
        setProperty(ModelRotation.TRANSITION_NAME, modelRotationTransition.value)
      }
      update(modelRoughness) {
        setProperty(ModelRoughness.NAME, modelRoughness.value)
      }
      update(modelRoughnessTransition) {
        setProperty(ModelRoughness.TRANSITION_NAME, modelRoughnessTransition.value)
      }
      update(modelScale) {
        setProperty(ModelScale.NAME, modelScale.value)
      }
      update(modelScaleTransition) {
        setProperty(ModelScale.TRANSITION_NAME, modelScaleTransition.value)
      }
      update(modelScaleMode) {
        setProperty(ModelScaleMode.NAME, modelScaleMode.value)
      }
      update(modelTranslation) {
        setProperty(ModelTranslation.NAME, modelTranslation.value)
      }
      update(modelTranslationTransition) {
        setProperty(ModelTranslation.TRANSITION_NAME, modelTranslationTransition.value)
      }
      update(modelType) {
        setProperty(ModelType.NAME, modelType.value)
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