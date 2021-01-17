package com.mapbox.maps.plugin.location

import android.text.TextUtils
import com.mapbox.maps.plugin.location.LocationComponentConstants.*

internal class LayerSourceProvider {

  fun getModelSource(locationModelLayerOptions: LocationModelLayerOptions): ModelLocationSourceWrapper {
    if (TextUtils.isEmpty(locationModelLayerOptions.modelUrl)) {
      throw RuntimeException("Model Url must not be empty!")
    }
    return ModelLocationSourceWrapper(
      MODEL_SCOURCE,
      locationModelLayerOptions.modelUrl,
      locationModelLayerOptions.position
    )
  }

  fun getModelLayer(locationModelLayerOptions: LocationModelLayerOptions) =
    ModelLocationLayerWrapper(
      MODEL_LAYER,
      MODEL_SCOURCE,
      locationModelLayerOptions.modelScale,
      locationModelLayerOptions.modelRotation
    )

  fun getIndicatorLocationLayer() = IndicatorLocationLayerWrapper(FOREGROUND_LAYER)

  fun getIndicatorLocationLayerRenderer() = IndicatorLocationLayerRenderer(this)

  fun getModelLocationLayerRenderer(locationModelLayerOptions: LocationModelLayerOptions) =
    ModelLocationLayerRenderer(this, locationModelLayerOptions)
}