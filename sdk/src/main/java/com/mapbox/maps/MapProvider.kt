package com.mapbox.maps

import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.MapDelegateProviderImpl
import com.mapbox.maps.plugin.MapPluginRegistry

internal object MapProvider {

  fun getNativeMap(
    mapboxMapOptions: MapboxMapOptions,
    mapClient: MapClient,
    mapObserver: MapObserver
  ): MapInterface = NativeMapImpl(
    Map(
      mapClient,
      mapObserver,
      mapboxMapOptions.mapOptions,
      mapboxMapOptions.resourceOptions
    )
  )

  fun getMapboxMap(
    nativeMap: MapInterface,
    mapObserver: NativeMapObserver,
    pixelRatio: Float
  ) =
    MapboxMap(nativeMap, mapObserver, pixelRatio)

  fun getMapPluginRegistry(
    mapboxMap: MapboxMap,
    mapController: MapController,
    telemetry: MapTelemetry
  ) = MapPluginRegistry(MapDelegateProviderImpl(mapboxMap, mapController, telemetry))
}