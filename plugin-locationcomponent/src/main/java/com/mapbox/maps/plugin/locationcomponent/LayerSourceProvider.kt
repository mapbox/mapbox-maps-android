package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D

internal class LayerSourceProvider(private val locationComponentInitOptions: LocationComponentInitOptions) {

  fun getModelSource(locationModelLayerOptions: LocationPuck3D): ModelSourceWrapper {
    if (locationModelLayerOptions.modelUri.isEmpty()) {
      throw IllegalArgumentException("Model Url must not be empty!")
    }
    return ModelSourceWrapper(
      locationComponentInitOptions.puck3DSourceId,
      locationModelLayerOptions.modelUri,
      locationModelLayerOptions.position.map { it.toDouble() }
    )
  }

  fun getModelLayer(locationModelLayerOptions: LocationPuck3D) =
    ModelLayerWrapper(
      locationComponentInitOptions.puck3DLayerId,
      locationComponentInitOptions.puck3DSourceId,
      locationModelLayerOptions.modelScale.map { it.toDouble() },
      locationModelLayerOptions.modelRotation.map { it.toDouble() },
      locationModelLayerOptions.modelTranslation.map { it.toDouble() },
      locationModelLayerOptions.modelOpacity.toDouble()
    )

  fun getLocationIndicatorLayer() = LocationIndicatorLayerWrapper(locationComponentInitOptions.puck2DLayerId)

  fun getLocationIndicatorLayerRenderer(puckOptions: LocationPuck2D) =
    LocationIndicatorLayerRenderer(locationComponentInitOptions, puckOptions, this)

  fun getModelLayerRenderer(locationModelLayerOptions: LocationPuck3D) =
    ModelLayerRenderer(locationComponentInitOptions, this, locationModelLayerOptions)
}