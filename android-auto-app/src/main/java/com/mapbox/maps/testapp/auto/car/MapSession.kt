package com.mapbox.maps.testapp.auto.car

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import androidx.car.app.Screen
import androidx.car.app.ScreenManager
import androidx.car.app.Session
import com.mapbox.common.BaseMapboxInitializer
import com.mapbox.maps.ContextMode
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver
import com.mapbox.maps.extension.androidauto.mapboxMapInstaller
import com.mapbox.maps.loader.MapboxMapsInitializerImpl

/**
 * Session class for the Mapbox Map sample app for Android Auto.
 */
class MapSession : Session() {
  private val carMapShowcase = CarMapShowcase()
  override fun onCreateScreen(intent: Intent): Screen {
    // The onCreate is guaranteed to be called before onCreateScreen. You can pass the
    // mapboxCarMap to other screens. Each screen can register and unregister observers.
    // This allows you to scope behaviors to sessions, screens, or events.

    // Also we cover the use-case if native libraries could not yet be loaded and show
    // RetryScreen in that case with UI to retry init on a button click
    val screen = tryInit(carMapShowcase)
      ?: RetryScreen(
        carContext,
        this,
        carMapShowcase,
        "Map not available"
      )

    return if (carContext.checkSelfPermission(ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
      carContext.getCarService(ScreenManager::class.java)
        .push(screen)
      RequestPermissionScreen(carContext)
    } else screen
  }

  override fun onCarConfigurationChanged(newConfiguration: Configuration) {
    carMapShowcase.loadMapStyle(carContext)
  }
}
@OptIn(MapboxExperimental::class)
internal fun MapSession.tryInit(carMapShowcase: MapboxCarMapObserver): MapScreen? = try {
  // if Mapbox was init before successful - this is no-op
  BaseMapboxInitializer.init(MapboxMapsInitializerImpl::class.java)
  val mapboxCarMap = mapboxMapInstaller()
    .onCreated(CarAnimationThreadController(), CarMapWidgets(), carMapShowcase)
    .install { carContext ->
      val mapOptions = MapInitOptions(carContext).mapOptions.toBuilder()
        .contextMode(ContextMode.SHARED)
        .build()
      MapInitOptions(carContext, mapOptions = mapOptions)
    }
  MapScreen(mapboxCarMap)
} catch (e: Throwable) {
  null
}