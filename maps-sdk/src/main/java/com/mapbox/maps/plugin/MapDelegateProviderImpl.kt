package com.mapbox.maps.plugin

import androidx.annotation.RestrictTo
import com.mapbox.maps.MapController
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapFeatureQueryDelegate
import com.mapbox.maps.plugin.delegates.MapFeatureStateDelegate
import com.mapbox.maps.plugin.delegates.MapInteractionDelegate
import com.mapbox.maps.plugin.delegates.MapListenerDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate

@OptIn(MapboxExperimental::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class MapDelegateProviderImpl(
  val mapboxMap: MapboxMap,
  mapController: MapController,
  telemetry: MapTelemetry,
  mapGeofencingConsent: MapGeofencingConsent,
) : MapDelegateProvider {
  override val mapCameraManagerDelegate: MapCameraManagerDelegate = mapboxMap
  override val mapProjectionDelegate: MapProjectionDelegate = mapboxMap
  override val mapTransformDelegate: MapTransformDelegate = mapboxMap
  override val mapAttributionDelegate: MapAttributionDelegate by lazy {
    MapAttributionDelegateImpl(mapboxMap, telemetry, mapGeofencingConsent)
  }
  override val mapFeatureQueryDelegate: MapFeatureQueryDelegate = mapboxMap
  override val mapPluginProviderDelegate: MapPluginProviderDelegate = mapController
  override val mapListenerDelegate: MapListenerDelegate = mapboxMap
  override val mapFeatureStateDelegate: MapFeatureStateDelegate = mapboxMap

  override fun getStyle(callback: (MapboxStyleManager) -> Unit) {
    mapboxMap.getStyle { style -> callback(style) }
  }

  override val mapStyleManagerDelegate: MapboxStyleManager = mapboxMap
  override val mapInteractionDelegate: MapInteractionDelegate = mapboxMap
}