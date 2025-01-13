package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE
import com.mapbox.maps.plugin.locationcomponent.utils.take
import java.lang.ref.WeakReference

@OptIn(MapboxExperimental::class)
internal object LayerSourceProvider {

  fun getModelSource(locationModelLayerOptions: LocationPuck3D): ModelSourceWrapper {
    if (locationModelLayerOptions.modelUri.isEmpty()) {
      throw IllegalArgumentException("Model Url must not be empty!")
    }
    return ModelSourceWrapper(
      sourceId = MODEL_SOURCE,
      url = locationModelLayerOptions.modelUri,
      position = locationModelLayerOptions.position.map { it.toDouble() },
      materialOverrides = locationModelLayerOptions.materialOverrides,
      nodeOverrides = locationModelLayerOptions.nodeOverrides
    )
  }

  fun getModelLayer(locationModelLayerOptions: LocationPuck3D) =
    ModelLayerWrapper(
      layerId = MODEL_LAYER,
      sourceId = MODEL_SOURCE,
      modelScale = locationModelLayerOptions.modelScale.map { it.toDouble() },
      modelRotation = locationModelLayerOptions.modelRotation.map { it.toDouble() },
      modelRotationExpression = locationModelLayerOptions.modelRotationExpression?.let { Value.fromJson(it).take() },
      modelTranslation = locationModelLayerOptions.modelTranslation.map { it.toDouble() },
      modelCastShadows = locationModelLayerOptions.modelCastShadows,
      modelReceiveShadows = locationModelLayerOptions.modelReceiveShadows,
      modelOpacity = locationModelLayerOptions.modelOpacity.toDouble(),
      modelOpacityExpression = locationModelLayerOptions.modelOpacityExpression?.let { Value.fromJson(it).take() },
      modelScaleMode = locationModelLayerOptions.modelScaleMode,
      modelEmissiveStrength = locationModelLayerOptions.modelEmissiveStrength.toDouble(),
      modelEmissiveStrengthExpression = locationModelLayerOptions.modelEmissiveStrengthExpression?.let { Value.fromJson(it).take() },
      modelColor = locationModelLayerOptions.modelColor,
      modelColorExpression = locationModelLayerOptions.modelColorExpression?.let { Value.fromJson(it).take() },
      modelColorMixIntensity = locationModelLayerOptions.modelColorMixIntensity.toDouble(),
      modelColorMixIntensityExpression = locationModelLayerOptions.modelColorMixIntensityExpression?.let { Value.fromJson(it).take() },
      modelElevationReference = locationModelLayerOptions.modelElevationReference
    )

  fun getLocationIndicatorLayer() = LocationIndicatorLayerWrapper(LOCATION_INDICATOR_LAYER)

  fun getLocationIndicatorLayerRenderer(puckOptions: LocationPuck2D, weakContext: WeakReference<Context>) =
    LocationIndicatorLayerRenderer(puckOptions, weakContext)

  fun getModelLayerRenderer(locationModelLayerOptions: LocationPuck3D) =
    ModelLayerRenderer(this, locationModelLayerOptions)
}