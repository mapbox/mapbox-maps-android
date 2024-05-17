package com.mapbox.maps.plugin.viewport.util

import com.mapbox.maps.module.TelemetryEvent

internal object ViewportTelemetryEvents {
  val stateFollowPuck = TelemetryEvent.create("viewport/state/follow-puck")
  val stateOverview = TelemetryEvent.create("viewport/state/overview")
  val stateTransition = TelemetryEvent.create("viewport/state/transition")
}