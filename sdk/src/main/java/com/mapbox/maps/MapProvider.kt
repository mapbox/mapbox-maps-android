package com.mapbox.maps

import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.MapDelegateProviderImpl
import com.mapbox.maps.plugin.MapPluginRegistry

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

  fun getNativeMap(mapView: MapView): MapInterface {
    return mapView.getController().getNativeMap()
  }

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

  private fun MapView.getController(): MapController = this.mapController
}