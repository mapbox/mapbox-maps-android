package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.module.TelemetryEvent

internal object ComposeTelemetryEvents {
  val map = TelemetryEvent.create("compose/map")
}