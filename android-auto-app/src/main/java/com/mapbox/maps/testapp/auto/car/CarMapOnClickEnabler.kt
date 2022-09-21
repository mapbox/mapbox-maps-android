package com.mapbox.maps.testapp.auto.car

import android.content.SharedPreferences
import androidx.car.app.AppManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.MapboxCarMap
import com.mapbox.maps.extension.androidauto.MapboxCarMapInitializer
import com.mapbox.maps.logI
import com.mapbox.maps.testapp.auto.CarAppPreferences
import com.mapbox.maps.testapp.auto.custom.SurfaceCallbackInterceptor

/**
 * In order to enable onClick, you need to override the mapboxCarMap.prepareSurfaceCallback
 *
 * This class allows you to toggle the preference from the app, to show that you can change this
 * setting at runtime. In real scenarios, it may be initialize the map with onClick enabled.
 */
@OptIn(MapboxExperimental::class)
class CarMapOnClickEnabler(
  private val mapboxCarMap: MapboxCarMap,
  private val initializer: MapboxCarMapInitializer
) : DefaultLifecycleObserver {

  private var carAppPreferences: CarAppPreferences? = null

  private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
    if (key == CarAppPreferences.BOOLEAN_KEY_ENABLE_ON_CLICK) {
      val customCallbackEnabled = sharedPreferences.getBoolean(key, false)
      onEnableOnClickPreferenceChanged(customCallbackEnabled)
    }
  }

  override fun onResume(owner: LifecycleOwner) {
    super.onResume(owner)
    carAppPreferences = CarAppPreferences(mapboxCarMap.carContext).apply {
      sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
      onEnableOnClickPreferenceChanged(isOnClickEnabled())
    }
  }

  override fun onPause(owner: LifecycleOwner) {
    super.onPause(owner)
    carAppPreferences?.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(listener)
    carAppPreferences = null
  }

  private fun onEnableOnClickPreferenceChanged(customCallbackEnabled: Boolean) {
    val carContext = mapboxCarMap.carContext
    if (customCallbackEnabled) {
      val surfaceCallback = mapboxCarMap.prepareSurfaceCallback(
        carContext, initializer.onCreate(carContext)
      )
      carContext.getCarService(AppManager::class.java)
        .setSurfaceCallback(object : SurfaceCallbackInterceptor(surfaceCallback) {
          override fun onClick(x: Float, y: Float) {
            super.onClick(x, y)
            onMapSurfaceClick(x, y)
          }
        })
    } else {
      mapboxCarMap.setup(carContext, initializer.onCreate(carContext))
    }
  }

  private fun onMapSurfaceClick(x: Float, y: Float) {
    logI("CarMapOnClickEnabler", "onMapSurfaceClick $x $y")
  }
}