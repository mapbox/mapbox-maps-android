package com.mapbox.maps.extension.androidauto

import androidx.car.app.CarContext
import androidx.car.app.SurfaceContainer
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental

/**
 * This contains the Android Auto head unit map information.
 * @see MapboxCarMap.registerObserver
 *
 * @property carContext reference to the context provided to the [MapboxCarMap]
 * @property mapView the map view when the [MapboxCarMap] uses [MapboxCarMapType.MAP_VIEW]
 * @property mapSurface Mapbox controllable interface. In [MapboxCarMapType.MAP_VIEW] mode this
 * shares the map, renderer, and plugins of [mapView]; the view owns its lifecycle and surface.
 * @property surfaceContainer A container for the Surface created by the car.
 */
class MapboxCarMapSurface internal constructor(
  val carContext: CarContext,
  @property:MapboxExperimental val mapView: MapView?,
  val mapSurface: MapSurface,
  val surfaceContainer: SurfaceContainer,
) {

  internal constructor(
    carContext: CarContext,
    mapSurface: MapSurface,
    surfaceContainer: SurfaceContainer,
  ) : this(carContext, null, mapSurface, surfaceContainer)

  /**
   * Get a string representation of the map surface.
   *
   * @return the string representation
   */
  @OptIn(MapboxExperimental::class)
  override fun toString(): String {
    return "MapboxCarMapSurface(carContext=$carContext," +
      " mapView=$mapView," +
      " mapSurface=$mapSurface," +
      " surfaceContainer=$surfaceContainer" +
      ")"
  }
}