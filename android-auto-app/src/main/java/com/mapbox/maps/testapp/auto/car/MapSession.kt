package com.mapbox.maps.testapp.auto.car

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import androidx.car.app.Screen
import androidx.car.app.ScreenManager
import androidx.car.app.Session
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.mapboxMapInstaller

/**
 * Session class for the Mapbox Map sample app for Android Auto.
 */
@OptIn(MapboxExperimental::class)
class MapSession : Session() {
  private val carMapShowcase = CarMapShowcase()
  private val mapboxCarMap = mapboxMapInstaller()
    .onCreated(CarMapWidgets(), carMapShowcase)
    .install { carContext ->
      // Callback is triggered when the Session calls onCreate. This allows you to specify
      // custom MapInitOptions.
      MapInitOptions(carContext)
    }

  override fun onCreateScreen(intent: Intent): Screen {
    // The onCreate is guaranteed to be called before onCreateScreen. You can pass the
    // mapboxCarMap to other screens. Each screen can register and unregister observers.
    // This allows you to scope behaviors to sessions, screens, or events.
    val mapScreen = MapScreen(mapboxCarMap)

    return if (carContext.checkSelfPermission(ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
      carContext.getCarService(ScreenManager::class.java)
        .push(mapScreen)
      RequestPermissionScreen(carContext)
    } else mapScreen
  }

  override fun onCarConfigurationChanged(newConfiguration: Configuration) {
    carMapShowcase.loadMapStyle(carContext)
  }
}