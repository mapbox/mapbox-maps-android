package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.plugin.viewport.data.ViewportStatusChangeReason

/**
 * Observer that gets notified whenever [ViewportStatus] changes.
 *
 * @see [ViewportPlugin.addStatusObserver]
 * @see [ViewportPlugin.removeStatusObserver]
 */
fun interface ViewportStatusObserver {
  /**
   * Called whenever [ViewportStatus] changes.
   *
   * @param from The previous [ViewportStatus]
   * @param to The current [ViewportStatus].
   * @param reason The reason that the [ViewportStatus] has been changed.
   */
  fun onViewportStatusChanged(from: ViewportStatus, to: ViewportStatus, reason: ViewportStatusChangeReason)
}