package com.mapbox.maps.plugin.location

import com.mapbox.bindgen.Value

internal class ModelLocationLayerWrapper(
  layerId: String,
  sourceId: String,
  modelScale: List<Double>,
  modelRotation: List<Double>
) : LocationLayerWrapper(layerId) {
  init {
    layerProperties["id"] = Value(layerId)
    layerProperties["type"] = Value("model")
    layerProperties["source"] = Value(sourceId)
    layerProperties["model-scale"] = Value(modelScale.map { Value(it) })
    layerProperties["model-rotation"] = Value(modelRotation.map { Value(it) })
  }

  fun modelScale(scale: List<Double>) = updateProperty("model-scale", Value(scale.map { Value(it) }))

  fun modelRotation(rotation: List<Double>) = updateProperty("model-rotation", Value(rotation.map { Value(it) }))
}