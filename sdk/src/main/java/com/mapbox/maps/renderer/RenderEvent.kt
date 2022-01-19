package com.mapbox.maps.renderer

internal data class RenderEvent(
  val runnable: Runnable?,
  val needRender: Boolean,
  val eventType: EventType
)
