package com.mapbox.maps.testapp.auto.service

import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator
import com.mapbox.maps.testapp.auto.car.MapSession

/**
 * Entry point for the templated app.
 *
 * CarAppService is the main interface between the app and Android Auto.
 *
 * For more details, see the [Android for Cars Library developer guide](https://developer.android.com/training/cars/navigation).
 */
class MapboxCarAppService : CarAppService() {
  override fun createHostValidator(): HostValidator {
    return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
  }

  override fun onCreateSession(): Session {
    return MapSession()
  }
}