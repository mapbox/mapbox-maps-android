package com.mapbox.maps.extension.androidauto

import androidx.car.app.CarContext
import androidx.car.app.SurfaceContainer
import com.mapbox.maps.MapSurface

/**
 * This contains the Android Auto head unit map information.
 * @see MapboxCarMap.registerObserver
 *
 * @since 1.0.0
 *
 * @property carContext reference to the context provided to the [MapboxCarMap]
 * @property mapSurface Mapbox controllable interface
 * @property surfaceContainer A container for the Surface created by the car.
 */
class MapboxCarMapSurface internal constructor(
  val carContext: CarContext,
  val mapSurface: MapSurface,
  val surfaceContainer: SurfaceContainer,
) {
  /**
   * Get a string representation of the map surface.
   *
   * @return the string representation
   */
  override fun toString(): String {
    return "MapboxCarMapSurface(carContext=$carContext," +
      " mapSurface=$mapSurface," +
      " surfaceContainer=$surfaceContainer" +
      ")"
  }
}