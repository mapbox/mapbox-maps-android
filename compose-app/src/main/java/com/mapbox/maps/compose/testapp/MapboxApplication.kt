package com.mapbox.maps.compose.testapp

import android.app.Application
import android.os.StrictMode

/**
 * Application class of the test application.
 **/
public class MapboxApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    initializeStrictMode()
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
}