package com.mapbox.maps

import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.MapDelegateProviderImpl
import com.mapbox.maps.plugin.MapPluginRegistry
import java.lang.ref.WeakReference

internal object MapProvider {

  fun getNativeMap(
    mapInitOptions: MapInitOptions,
    mapClient: MapClient,
  ): MapInterface = NativeMapImpl(
    Map(
      mapClient,
      mapInitOptions.mapOptions,
      mapInitOptions.resourceOptions
    )
  )

  fun getMapboxMap(
    nativeMapWeakRef: WeakReference<MapInterface>,
    nativeObserver: NativeObserver,
    pixelRatio: Float
  ) =
    MapboxMap(nativeMapWeakRef, nativeObserver, pixelRatio)

  fun getMapPluginRegistry(
    mapboxMap: MapboxMap,
    mapController: MapController,
    telemetry: MapTelemetry
  ) = MapPluginRegistry(MapDelegateProviderImpl(mapboxMap, mapController, telemetry))
}