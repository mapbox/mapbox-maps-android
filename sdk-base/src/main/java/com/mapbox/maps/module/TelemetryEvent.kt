package com.mapbox.maps.module

import androidx.annotation.RestrictTo
import com.mapbox.common.FeatureTelemetryCounter

/**
 * Feature telemetry event definition.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class TelemetryEvent private constructor(private val name: String) {
  private val counter: FeatureTelemetryCounter? = try {
    FeatureTelemetryCounter.create(name)
  } catch (e: Throwable) {
    null
  }

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
    counter?.increment()
  }
}