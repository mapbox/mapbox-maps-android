package com.mapbox.maps.plugin.scalebar

import java.util.*

/**
 * Util object to determine if the user is in a country using the metric system.
 */
object LocaleUnitResolver {
  /**
   * @return true if user country is using metric, false if imperial
   */
  val isMetricSystem: Boolean
    get() {
      return when (Locale.getDefault().country.toUpperCase(Locale.getDefault())) {
        ImperialCountryCode.US, ImperialCountryCode.LIBERIA, ImperialCountryCode.MYANMAR -> false
        else -> true
      }
    }

  /**
   * Data class containing uppercase country codes for countries using the imperial system.
   */
  internal object ImperialCountryCode {
    const val US = "US"
    const val MYANMAR = "MM"
    const val LIBERIA = "LR"
  }
}