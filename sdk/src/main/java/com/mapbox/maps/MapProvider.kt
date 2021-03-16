package com.mapbox.maps

import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.MapDelegateProviderImpl
import com.mapbox.maps.plugin.MapPluginRegistry

internal object MapProvider {

  fun getNativeMap(
    mapboxMapOptions: MapboxMapOptions,
    mapClient: MapClient,
  ): MapInterface = NativeMapImpl(
    Map(
      mapClient,
      mapboxMapOptions.mapOptions,
      mapboxMapOptions.resourceOptions
    )
  )

  fun getMapboxMap(
    nativeMap: MapInterface,
    nativeObserver: NativeObserver,
    pixelRatio: Float
  ) =
    MapboxMap(nativeMap, nativeObserver, pixelRatio)

  fun getMapPluginRegistry(
    mapboxMap: MapboxMap,
    mapController: MapController,
    telemetry: MapTelemetry
  ) = MapPluginRegistry(MapDelegateProviderImpl(mapboxMap, mapController, telemetry))
}