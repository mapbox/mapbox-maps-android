package com.mapbox.maps.testapp.auto.car

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver
import com.mapbox.maps.extension.androidauto.MapboxCarMapSurface
import com.mapbox.maps.extension.androidauto.widgets.CompassWidget
import com.mapbox.maps.extension.androidauto.widgets.LogoWidget
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener

@OptIn(MapboxExperimental::class)
class CarMapWidgets : MapboxCarMapObserver {
  private lateinit var logoWidget: LogoWidget
  private lateinit var compassWidget: CompassWidget
  private lateinit var onCameraChangeListener: OnCameraChangeListener
  override fun onAttached(mapboxCarMapSurface: MapboxCarMapSurface) {
    super.onAttached(mapboxCarMapSurface)
    with(mapboxCarMapSurface) {
      logoWidget = LogoWidget(carContext)
      compassWidget = CompassWidget(
        carContext,
        marginX = 26f,
        marginY = 120f,
      )
      onCameraChangeListener = OnCameraChangeListener { compassWidget.setRotation(-mapSurface.getMapboxMap().cameraState.bearing.toFloat()) }
      mapSurface.addWidget(logoWidget)
      mapSurface.addWidget(compassWidget)
      mapSurface.getMapboxMap().addOnCameraChangeListener(onCameraChangeListener)
    }
  }

  override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
    super.onDetached(mapboxCarMapSurface)
    with(mapboxCarMapSurface) {
      mapSurface.getMapboxMap().removeOnCameraChangeListener(onCameraChangeListener)
    }
  }
}