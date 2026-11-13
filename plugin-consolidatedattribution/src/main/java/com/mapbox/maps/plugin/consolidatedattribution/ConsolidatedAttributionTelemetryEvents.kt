package com.mapbox.maps.plugin.consolidatedattribution

import com.mapbox.maps.module.TelemetryEvent

internal object ConsolidatedAttributionTelemetryEvents {
  val enabled = TelemetryEvent.create("consolidated-attribution/enabled")
}