package com.mapbox.maps.plugin.viewport

/**
 * Observer that gets notified whenever [ViewportStatus] changes.
 */
fun interface ViewportStatusObserver {
  /**
   * Called whenever [ViewportStatus] changes.
   *
   * @param from The previous [ViewportStatus], null if the previous [ViewportStatus] is IDLE.
   * @param to The current [ViewportStatus], null if the current [ViewportStatus] is IDLE.
   * @param reason The reason that the state has been changed.
   */
  fun onViewportStatusChanged(from: ViewportStatus, to: ViewportStatus, reason: String)
}