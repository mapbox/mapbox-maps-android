package com.mapbox.maps.module.telemetry

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager

/**
 * Do not use this class outside of activity!!!
 */
class TelemetryEnabler internal constructor(private val isFromPreferences: Boolean) {
  enum class State(val isEnabled: Boolean) {
    ENABLED(true),
    DISABLED(false)
  }

  private var currentTelemetryState = State.ENABLED

  fun obtainTelemetryState(context: Context): State {
    return if (isFromPreferences) {
      retrieveTelemetryStateFromPreferences(context)
    } else currentTelemetryState
  }

  fun updatePreferences(context: Context, telemetryState: State): State {
    if (isFromPreferences) {
      return updateTelemetryState(context, telemetryState)
    }
    currentTelemetryState = telemetryState
    return currentTelemetryState
  }

  companion object {
    private const val MAPBOX_SHARED_PREFERENCE_KEY_TELEMETRY_STATE = "mapboxTelemetryState"
    private const val KEY_META_DATA_ENABLED = "com.mapbox.EnableEvents"
    /**
     * Mapbox shared preferences file name
     */
    private const val MAPBOX_SHARED_PREFERENCES = "MapboxSharedPreferences"

    fun retrieveTelemetryStateFromPreferences(context: Context): State {
      val sharedPreferences: SharedPreferences =
        obtainSharedPreferences(context)
      val isTelemetryEnabled = sharedPreferences.getBoolean(
        MAPBOX_SHARED_PREFERENCE_KEY_TELEMETRY_STATE,
        true
      )
      return if (isTelemetryEnabled) State.ENABLED else State.DISABLED
    }

    fun updateTelemetryState(context: Context, telemetryState: State): State {
      val sharedPreferences: SharedPreferences =
        obtainSharedPreferences(context)
      val editor = sharedPreferences.edit()
      editor.putBoolean(MAPBOX_SHARED_PREFERENCE_KEY_TELEMETRY_STATE, telemetryState.isEnabled)
      editor.apply()
      return telemetryState
    }

    fun isEventsEnabled(context: Context): Boolean {
      try {
        val appInformation = context.packageManager.getApplicationInfo(
          context.packageName, PackageManager.GET_META_DATA
        )
        if (appInformation?.metaData != null) {
          return appInformation.metaData.getBoolean(KEY_META_DATA_ENABLED, true)
        }
      } catch (exception: PackageManager.NameNotFoundException) {
        exception.printStackTrace()
      }
      return true
    }

    fun obtainSharedPreferences(context: Context): SharedPreferences {
      return context.getSharedPreferences(MAPBOX_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
  }
}