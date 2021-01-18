package com.mapbox.maps.plugin.locationcomponent

import android.text.TextUtils
import com.mapbox.maps.plugin.ThreeDLocationPuck
import com.mapbox.maps.plugin.TwoDLocationPuck
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE

internal class LayerSourceProvider {

  fun getModelSource(locationModelLayerOptions: ThreeDLocationPuck): ModelSourceWrapper {
    if (TextUtils.isEmpty(locationModelLayerOptions.modelUri)) {
      throw RuntimeException("Model Url must not be empty!")
    }
    return ModelSourceWrapper(
      MODEL_SOURCE,
      locationModelLayerOptions.modelUri,
      locationModelLayerOptions.position.map { it.toDouble() }
    )
  }

  fun getModelLayer(locationModelLayerOptions: ThreeDLocationPuck) =
    ModelLayerWrapper(
      MODEL_LAYER,
      MODEL_SOURCE,
      locationModelLayerOptions.modelScale.map { it.toDouble() },
      locationModelLayerOptions.modelRotation.map { it.toDouble() }
    )

  fun getLocationIndicatorLayer() = LocationIndicatorLayerWrapper(LOCATION_INDICATOR_LAYER)

  fun getLocationIndicatorLayerRenderer(puckOptions: TwoDLocationPuck) =
    LocationIndicatorLayerRenderer(puckOptions, this)

  fun getModelLayerRenderer(locationModelLayerOptions: ThreeDLocationPuck) =
    ModelLayerRenderer(this, locationModelLayerOptions)
}