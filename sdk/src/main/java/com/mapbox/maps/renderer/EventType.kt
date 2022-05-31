package com.mapbox.maps.renderer

/**
 * Render event type.
 */
internal enum class EventType {
  /**
   * Those events are scheduled not by the Maps SDK (most likely by the user using MapView#queueEvent)
   * Such events do not get cleared when Android provides new surface or texture.
   * In case render thread is not fully prepared - such messages will keep being rescheduled until
   * thread is prepared or killed.
   */
  OTHER,

  /**
   * TODO remove when gl-native fix will land
   */
  DESTROY_RENDERER,
}