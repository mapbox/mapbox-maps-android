@file:kotlin.jvm.JvmName("MapboxCarMapEx")

package com.mapbox.maps.extension.androidauto

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.Session
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental

/**
 * Shorthand extension for installing the map onto a [Session].
 */
@OptIn(MapboxExperimental::class)
fun Session.mapboxMapInstaller() = MapboxCarMapSessionInstaller(this)

/**
 * Shorthand extension for installing an experience onto a [Screen].
 */
@OptIn(MapboxExperimental::class)
fun Screen.mapboxMapInstaller(mapboxCarMap: MapboxCarMap) =
  MapboxCarMapScreenInstaller(this, mapboxCarMap)

internal fun CarContext.defaultMapInitOptions(): MapInitOptions {
  val mapInitOptions = MapInitOptions(context = this)
  checkNotNull(mapInitOptions.resourceOptions.accessToken) {
    "MapboxCarMap cannot be used without an access token"
  }
  return mapInitOptions
}

internal fun MapInitOptions.verifyCarContext() {
  check(context is CarContext) {
    "You must construct a MapboxCarMap with the CarContext"
  }
}