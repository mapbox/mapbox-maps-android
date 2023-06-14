package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Value
import com.mapbox.maps.plugin.ModelScaleMode

internal class ModelLayerWrapper(
  layerId: String,
  sourceId: String,
  modelScale: List<Double>,
  modelRotation: List<Double>,
  modelTranslation: List<Double>,
  modelOpacity: Double,
  modelScaleMode: ModelScaleMode,
) : LocationLayerWrapper(layerId) {
  init {
    layerProperties["id"] = Value(layerId)
    layerProperties["type"] = Value("model")
    layerProperties["source"] = Value(sourceId)
    layerProperties["model-type"] = Value("location-indicator")
    layerProperties["model-scale"] = Value(modelScale.map(::Value))
    layerProperties["model-rotation"] = Value(modelRotation.map(::Value))
    layerProperties["model-translation"] = Value(modelTranslation.map(::Value))
    layerProperties["model-opacity"] = Value(modelOpacity)
    layerProperties["model-scale-transition"] = buildTransition(delay = 0, duration = 0)
    layerProperties["model-rotation-transition"] = buildTransition(delay = 0, duration = 0)
    layerProperties["model-scale-mode"] = Value(modelScaleMode.value)
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
}