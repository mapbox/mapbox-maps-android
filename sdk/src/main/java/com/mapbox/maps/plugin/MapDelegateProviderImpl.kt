package com.mapbox.maps.plugin

import com.mapbox.maps.*
import com.mapbox.maps.MapController
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate

internal class MapDelegateProviderImpl constructor(val mapboxMap: MapboxMap, mapController: MapController, telemetry: MapTelemetry) : MapDelegateProvider {
  override val mapCameraManagerDelegate: MapCameraManagerDelegate by lazy { mapboxMap }
  override val mapProjectionDelegate: MapProjectionDelegate by lazy { mapboxMap }
  override val mapTransformDelegate: MapTransformDelegate by lazy { mapboxMap }
  override val mapAttributionDelegate: MapAttributionDelegate by lazy { MapAttributionDelegateImpl(mapboxMap, telemetry) }
  override val mapFeatureQueryDelegate: MapFeatureQueryDelegate by lazy { mapboxMap }
  override val mapPluginProviderDelegate: MapPluginProviderDelegate by lazy { mapController }
  override val mapListenerDelegate: MapListenerDelegate by lazy { mapboxMap }

  override fun getStyle(callback: (Style) -> Unit) {
    mapboxMap.getStyle { style -> callback(style) }
  }
}