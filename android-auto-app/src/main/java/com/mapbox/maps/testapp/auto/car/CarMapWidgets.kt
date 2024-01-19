package com.mapbox.maps.testapp.auto.car

import com.mapbox.common.Cancelable
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver
import com.mapbox.maps.extension.androidauto.MapboxCarMapSurface
import com.mapbox.maps.extension.androidauto.widgets.CompassWidget
import com.mapbox.maps.extension.androidauto.widgets.LogoWidget

/**
 * Note that the Widgets are only available when using android auto extension together with the
 * Android Maps SDK v10.4.0 and above, otherwise you will get compilation errors.
 */
@OptIn(MapboxExperimental::class)
class CarMapWidgets : MapboxCarMapObserver {
  private lateinit var logoWidget: LogoWidget
  private lateinit var compassWidget: CompassWidget
  private var cancellable: Cancelable? = null
  override fun onAttached(mapboxCarMapSurface: MapboxCarMapSurface) {
    super.onAttached(mapboxCarMapSurface)
    with(mapboxCarMapSurface) {
      logoWidget = LogoWidget(carContext)
      compassWidget = CompassWidget(
        carContext,
        marginX = 26f,
        marginY = 120f,
      )
      mapSurface.addWidget(logoWidget)
      mapSurface.addWidget(compassWidget)
      cancellable = mapSurface.mapboxMap.subscribeCameraChanged {
        compassWidget.setRotation(-mapSurface.mapboxMap.cameraState.bearing.toFloat())
      }
    }
  }

  override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
    super.onDetached(mapboxCarMapSurface)
    with(mapboxCarMapSurface) {
      cancellable?.cancel()
      cancellable = null
      mapSurface.removeWidget(logoWidget)
      mapSurface.removeWidget(compassWidget)
    }
  }
}