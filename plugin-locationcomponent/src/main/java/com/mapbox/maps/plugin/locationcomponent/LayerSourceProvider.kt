package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE

internal class LayerSourceProvider {

  fun getModelSource(locationModelLayerOptions: LocationPuck3D): ModelSourceWrapper {
    if (locationModelLayerOptions.modelUri.isEmpty()) {
      throw IllegalArgumentException("Model Url must not be empty!")
    }
    return ModelSourceWrapper(
      MODEL_SOURCE,
      locationModelLayerOptions.modelUri,
      locationModelLayerOptions.position.map { it.toDouble() }
    )
  }

  fun getModelLayer(locationModelLayerOptions: LocationPuck3D) =
    ModelLayerWrapper(
      MODEL_LAYER,
      MODEL_SOURCE,
      locationModelLayerOptions.modelScale.map { it.toDouble() },
      locationModelLayerOptions.modelRotation.map { it.toDouble() },
      locationModelLayerOptions.modelTranslation.map { it.toDouble() },
      locationModelLayerOptions.modelOpacity.toDouble()
    )

  fun getLocationIndicatorLayer() = LocationIndicatorLayerWrapper(LOCATION_INDICATOR_LAYER)

  fun getLocationIndicatorLayerRenderer(puckOptions: LocationPuck2D) =
    LocationIndicatorLayerRenderer(puckOptions, this)

  fun getModelLayerRenderer(locationModelLayerOptions: LocationPuck3D) =
    ModelLayerRenderer(this, locationModelLayerOptions)
}