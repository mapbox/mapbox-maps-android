package com.mapbox.maps.module.telemetry

import android.os.Bundle
import com.mapbox.common.TelemetrySystemUtils

internal object MapEventFactory {
  fun buildMapLoadEvent(phoneState: PhoneState): MapLoadEvent {
    val userId = TelemetrySystemUtils.obtainUniversalUniqueIdentifier()
    return MapLoadEvent(userId, phoneState)
  }

  fun buildPerformanceEvent(
    phoneState: PhoneState,
    sessionId: String,
    data: Bundle
  ): PerformanceEvent {
    return PerformanceEvent(phoneState, sessionId, data)
  }
}