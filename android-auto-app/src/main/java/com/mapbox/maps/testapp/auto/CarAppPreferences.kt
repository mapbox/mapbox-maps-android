package com.mapbox.maps.testapp.auto

import android.content.Context
import android.content.SharedPreferences

/**
 * Class gives you the ability to modify preferences that can be used by the car and app.
 */
class CarAppPreferences(context: Context) {

  val sharedPreferences: SharedPreferences by lazy {
    context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
  }

  fun enableOnClick(enable: Boolean) {
    sharedPreferences.edit().putBoolean(BOOLEAN_KEY_ENABLE_ON_CLICK, enable).apply()
  }

  fun isOnClickEnabled() = sharedPreferences
    .getBoolean(BOOLEAN_KEY_ENABLE_ON_CLICK, false)

  companion object {
    private const val SHARED_PREFERENCES_KEY = "mapbox_maps_android_auto_app"

    const val BOOLEAN_KEY_ENABLE_ON_CLICK = "enable_onclick"
  }
}