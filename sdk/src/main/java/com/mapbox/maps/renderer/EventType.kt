package com.mapbox.maps.renderer

/**
 * Render event type.
 */
internal enum class EventType {
  /**
   * Those events are scheduled by the Maps SDK or by user.
   * Such events do not get cleared when Android provides new surface or texture.
   * If render thread is not fully prepared - such messages will keep being rescheduled until
   * thread is prepared or killed.
   */
  DEFAULT,

  /**
   * Special event type to handle when renderer is being destroyed
   * TODO remove when gl-native fix will land
   */
  DESTROY_RENDERER,
}