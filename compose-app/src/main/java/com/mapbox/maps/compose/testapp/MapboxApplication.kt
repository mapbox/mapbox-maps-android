package com.mapbox.maps.compose.testapp

import android.os.StrictMode
import androidx.multidex.MultiDexApplication

/**
 * Application class of the test application.
 **/
public class MapboxApplication : MultiDexApplication() {

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