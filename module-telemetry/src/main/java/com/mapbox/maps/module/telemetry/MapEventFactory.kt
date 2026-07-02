package com.mapbox.maps.module.telemetry

import android.os.Bundle
import com.mapbox.common.TelemetrySystemUtils

internal object MapEventFactory {

  fun buildMapLoadEvent(phoneState: PhoneState): MapLoadEvent {
    return buildMapLoadEvent(phoneState, metadata = null)
  }

  fun buildMapLoadEvent(phoneState: PhoneState, metadata: MapTelemetryMetadata?): MapLoadEvent {
    val userId = TelemetrySystemUtils.obtainUniversalUniqueIdentifier()
    val uiFramework = metadata?.uiFramework?.value
    return MapLoadEvent(userId, uiFramework, phoneState)
  }

  fun buildPerformanceEvent(
    phoneState: PhoneState,
    sessionId: String,
    data: Bundle
  ): PerformanceEvent {
    return PerformanceEvent(phoneState, sessionId, data)
  }
}