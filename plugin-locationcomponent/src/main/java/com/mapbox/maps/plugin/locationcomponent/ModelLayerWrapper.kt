package com.mapbox.maps.plugin.locationcomponent

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.plugin.ModelElevationReference
import com.mapbox.maps.plugin.ModelScaleMode

@OptIn(MapboxExperimental::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class ModelLayerWrapper(
  layerId: String,
  sourceId: String,
  modelScale: List<Double>,
  modelRotation: List<Double>,
  modelRotationExpression: Value?,
  modelTranslation: List<Double>,
  modelCastShadows: Boolean,
  modelReceiveShadows: Boolean,
  modelOpacity: Double,
  modelOpacityExpression: Value?,
  modelScaleMode: ModelScaleMode,
  modelEmissiveStrength: Double,
  modelEmissiveStrengthExpression: Value?,
  modelColor: Int,
  modelColorExpression: Value?,
  modelColorMixIntensity: Double,
  modelColorMixIntensityExpression: Value?,
  modelElevationReference: ModelElevationReference,
) : LocationLayerWrapper(layerId) {
  init {
    layerProperties["id"] = Value(layerId)
    layerProperties["type"] = Value("model")
    layerProperties["source"] = Value(sourceId)
    layerProperties["model-type"] = Value("location-indicator")
    layerProperties["model-scale"] = Value(modelScale.map(::Value))
    layerProperties["model-rotation"] = modelRotationExpression ?: Value(modelRotation.map(::Value))
    layerProperties["model-translation"] = Value(modelTranslation.map(::Value))
    layerProperties["model-cast-shadows"] = Value(modelCastShadows)
    layerProperties["model-receive-shadows"] = Value(modelReceiveShadows)
    layerProperties["model-opacity"] = modelOpacityExpression ?: Value(modelOpacity)
    layerProperties["model-scale-mode"] = Value(modelScaleMode.value)
    layerProperties["model-scale-transition"] = buildTransition(delay = 0, duration = 0)
    layerProperties["model-rotation-transition"] = buildTransition(delay = 0, duration = 0)
    layerProperties["model-emissive-strength"] = modelEmissiveStrengthExpression ?: Value(modelEmissiveStrength)
    layerProperties["model-color"] = modelColorExpression ?: colorIntToRgbaExpression(modelColor)
    layerProperties["model-color-mix-intensity"] = modelColorMixIntensityExpression ?: Value(modelColorMixIntensity)
    layerProperties["model-elevation-reference"] = Value(modelElevationReference.value)
  }

  private fun buildTransition(delay: Long, duration: Long): Value {
    val transition = HashMap<String, Value>()
    transition["delay"] = Value(delay)
    transition["duration"] = Value(duration)
    return Value(transition)
  }

  fun modelScale(scale: List<Double>) = updateProperty("model-scale", Value(scale.map(::Value)))

  fun modelScaleExpression(scaleExpression: Value) = updateProperty("model-scale", scaleExpression)

  fun modelRotation(rotation: List<Double>) = updateProperty("model-rotation", Value(rotation.map(::Value)))

  fun modelTranslation(translation: List<Double>) = updateProperty("model-translation", Value(translation.map(::Value)))

  fun modelOpacity(opacity: Double) = updateProperty("model-opacity", Value(opacity))

  fun modelScaleMode(modelScaleMode: ModelScaleMode) = updateProperty("model-scale-mode", Value(modelScaleMode.value))

  fun modelElevationReference(modelElevationReference: ModelElevationReference) = updateProperty("model-elevation-reference", Value(modelElevationReference.value))

  fun slot(slot: String?) = updateProperty("slot", Value(slot ?: ""))
}