package com.mapbox.maps.plugin

import androidx.annotation.RestrictTo
import com.mapbox.maps.MapController
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapFeatureQueryDelegate
import com.mapbox.maps.plugin.delegates.MapListenerDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate

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

  override val mapStyleManagerDelegate: MapboxStyleManager by lazy { mapboxMap }
}