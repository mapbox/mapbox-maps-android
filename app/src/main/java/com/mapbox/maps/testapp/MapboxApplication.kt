package com.mapbox.maps.testapp

import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.geofencing.GeofencingError
import com.mapbox.common.geofencing.GeofencingEvent
import com.mapbox.common.geofencing.GeofencingFactory
import com.mapbox.common.geofencing.GeofencingObserver
import com.mapbox.maps.logD
import com.mapbox.maps.logW
import com.mapbox.maps.testapp.examples.geofence.ExtendedGeofencingActivity

/**
 * Application class of the test application.
 **/
class MapboxApplication : MultiDexApplication() {

  @MapboxExperimental
  private val geofencingObserver: GeofencingObserver = object : GeofencingObserver {

    override fun onEntry(event: GeofencingEvent) {
      ExtendedGeofencingActivity.showNotification(
        this@MapboxApplication,
        "Entry into feature id = ${event.feature.id()} at ${event.timestamp}",
        event.feature.id(),
        ExtendedGeofencingActivity.NOTIFICATION_FEATURE_ENTRY
      )
    }

    override fun onExit(event: GeofencingEvent) {
      ExtendedGeofencingActivity.showNotification(
        this@MapboxApplication,
        "Exit from feature id = ${event.feature.id()} at ${event.timestamp}",
        event.feature.id(),
        ExtendedGeofencingActivity.NOTIFICATION_FEATURE_EXIT
      )
    }

    override fun onDwell(event: GeofencingEvent) {
      ExtendedGeofencingActivity.showNotification(
        this@MapboxApplication,
        "Dwell into feature id = ${event.feature.id()} at ${event.timestamp}",
        event.feature.id(),
        ExtendedGeofencingActivity.NOTIFICATION_FEATURE_DWELL
      )
    }

    override fun onError(error: GeofencingError) {
      logD("MapboxApplication", "onError() called with: error = $error")
    }

    override fun onUserConsentChanged(isConsentGiven: Boolean) {
      logW("MapboxApplication", "onUserConsentChanged() called with: isConsentGiven = $isConsentGiven")
    }
  }

  // TODO: temporary workaround to avoid double adding of listener if we don't
  // have location permissions on the start of the app
  private var isObserverAdded: Boolean = false

  override fun onCreate() {
    super.onCreate()
    initializeStrictMode()
    if (ENABLE_BACKGROUND_GEOFENCING) {
      registerGeofencingObserver()
    }
  }

  private fun initializeStrictMode() {
    StrictMode.setThreadPolicy(
      StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .build()
    )
    StrictMode.setVmPolicy(
      StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .penaltyLog()
        .penaltyDeath()
        .build()
    )
  }

  @MapboxExperimental
  fun registerGeofencingObserver() {
    if (PermissionsManager.areLocationPermissionsGranted(this) && !isObserverAdded) {
      val geofencing = GeofencingFactory.getOrCreate()
      geofencing.addObserver(geofencingObserver) { it ->
        it.error?.let {
          logW("MapboxApplication", "Failed to registerGeofencingObserver: ${it.message}")
        }
      }
      isObserverAdded = true
    }
  }

  companion object {

    /**
     * Flag to showcase background behavior of the geofence engine. When enabled, notifications will
     * be created for the different geofencing events.
     */
    const val ENABLE_BACKGROUND_GEOFENCING = true
  }
}