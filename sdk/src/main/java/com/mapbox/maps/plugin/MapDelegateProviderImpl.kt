package com.mapbox.maps.plugin

import androidx.annotation.RestrictTo
import com.mapbox.maps.*
import com.mapbox.maps.MapController
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class MapDelegateProviderImpl(
  val mapboxMap: MapboxMap,
  mapController: MapController,
  telemetry: MapTelemetry
) : MapDelegateProvider {
  override val mapCameraManagerDelegate: MapCameraManagerDelegate by lazy { mapboxMap }
  override val mapProjectionDelegate: MapProjectionDelegate by lazy { mapboxMap }
  override val mapTransformDelegate: MapTransformDelegate by lazy { mapboxMap }
  override val mapAttributionDelegate: MapAttributionDelegate by lazy {
    MapAttributionDelegateImpl(
      mapboxMap,
      telemetry
    )
  }
  override val mapFeatureQueryDelegate: MapFeatureQueryDelegate by lazy { mapboxMap }
  override val mapPluginProviderDelegate: MapPluginProviderDelegate by lazy { mapController }
  override val mapListenerDelegate: MapListenerDelegate by lazy { mapboxMap }

  override fun getStyle(callback: (MapboxStyleManager) -> Unit) {
    mapboxMap.getStyle { style -> callback(style) }
  }
}