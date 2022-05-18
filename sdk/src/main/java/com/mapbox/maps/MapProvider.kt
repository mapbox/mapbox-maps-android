package com.mapbox.maps

import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.MapDelegateProviderImpl
import com.mapbox.maps.plugin.MapPluginRegistry

internal object MapProvider {

  fun getNativeMapWrapper(
    mapInitOptions: MapInitOptions,
    mapClient: MapClient,
  ): MapInterface = NativeMapImpl(
    Map(
      mapClient,
      mapInitOptions.mapOptions,
      mapInitOptions.resourceOptions
    )
  )

  fun getNativeMapCore(mapView: MapView): MapInterface {
    return (mapView.getController().getNativeMap() as NativeMapImpl).map
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