package com.mapbox.maps.testapp.auto.car

import android.os.Build
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver
import com.mapbox.maps.extension.androidauto.MapboxCarMapSurface
import com.mapbox.maps.threading.AnimationThreadController

/**
 * This class is needed for Xiaomi devices. When the mobile app has been backgrounded and you are
 * using android auto, Xiaomi MIUI will stop animations that are called from the main thread.
 *
 * https://github.com/mapbox/mapbox-maps-android/issues/1413
 */
@OptIn(MapboxExperimental::class)
class CarAnimationThreadController : MapboxCarMapObserver {
  override fun onAttached(mapboxCarMapSurface: MapboxCarMapSurface) {
    if (deviceRequiresBackgroundThreadAnimations()) {
      AnimationThreadController.useBackgroundThread()
    }
  }

  override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
    AnimationThreadController.useMainThread()
  }

  private fun deviceRequiresBackgroundThreadAnimations(): Boolean {
    return impactedManufacturer.contains(Build.MANUFACTURER)
  }

  private companion object {
    private val impactedManufacturer = setOf("Xiaomi")
  }
}