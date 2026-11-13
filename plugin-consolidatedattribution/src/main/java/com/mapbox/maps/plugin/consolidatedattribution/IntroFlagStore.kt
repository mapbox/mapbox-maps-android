package com.mapbox.maps.plugin.consolidatedattribution

import com.mapbox.bindgen.Value
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.common.SettingsServiceStorageType
import com.mapbox.maps.logW

/** Saves whether the intro expansion has played, across launches. */
internal interface IntroFlagStore {
  var isSet: Boolean
}

internal object SettingsServiceIntroFlagStore : IntroFlagStore {
  internal const val KEY = "com.mapbox.maps.consolidatedAttribution.introShown"

  private val settings
    get() = SettingsServiceFactory.getInstance(SettingsServiceStorageType.PERSISTENT)

  override var isSet: Boolean
    get() {
      val settings = settings
      val has = settings.has(KEY)
      has.error?.let { logW(TAG, "Failed to read intro flag: $it") }
      if (has.value != true) return false
      val result = settings.get(KEY)
      result.error?.let { logW(TAG, "Failed to read intro flag: $it") }
      return result.value?.contents == true
    }
    set(value) {
      val result = if (value) settings.set(KEY, Value(true)) else settings.erase(KEY)
      result.error?.let { logW(TAG, "Failed to save intro flag: $it") }
    }

  private const val TAG = "MbxConsolidatedAttribution"
}