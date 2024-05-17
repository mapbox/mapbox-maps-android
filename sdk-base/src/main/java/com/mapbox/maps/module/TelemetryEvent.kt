package com.mapbox.maps.module

import androidx.annotation.RestrictTo
import com.mapbox.common.FeatureTelemetryCounter

/**
 * Feature telemetry event definition.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class TelemetryEvent private constructor(name: String) {
  private val counter: FeatureTelemetryCounter = FeatureTelemetryCounter.create(name)

  /**
   * Static methods.
   */
  companion object {
    fun create(name: String) = TelemetryEvent("maps-mobile/$name")
  }

  /**
   * Increment feature telemetry event counter.
   * @suppress
   */
  fun increment() {
    counter.increment()
  }
}