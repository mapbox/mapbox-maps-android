package com.mapbox.maps.plugin.scalebar

import com.mapbox.maps.plugin.DistanceUnits
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
      return when (Locale.getDefault().country.uppercase(Locale.getDefault())) {
        ImperialCountryCode.US, ImperialCountryCode.LIBERIA, ImperialCountryCode.MYANMAR -> false
        else -> true
      }
    }

  /**
   * @return distance units for user country
   */
  val distanceUnits: DistanceUnits
    get() {
      return if (isMetricSystem) DistanceUnits.METRIC else DistanceUnits.IMPERIAL
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