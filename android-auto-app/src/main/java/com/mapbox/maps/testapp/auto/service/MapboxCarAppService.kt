package com.mapbox.maps.testapp.auto.service

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import androidx.car.app.CarAppService
import androidx.car.app.R
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
  @SuppressLint("PrivateResource")
  override fun createHostValidator(): HostValidator {
    return if (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
      HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    } else {
      HostValidator.Builder(applicationContext)
        .addAllowedHosts(R.array.hosts_allowlist_sample)
        .build()
    }
  }

  override fun onCreateSession(): Session {
    return MapSession()
  }
}