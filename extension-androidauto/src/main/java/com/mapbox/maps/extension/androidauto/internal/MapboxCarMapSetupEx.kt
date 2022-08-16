package com.mapbox.maps.extension.androidauto.internal

import androidx.car.app.CarContext
import com.mapbox.maps.MapInitOptions

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