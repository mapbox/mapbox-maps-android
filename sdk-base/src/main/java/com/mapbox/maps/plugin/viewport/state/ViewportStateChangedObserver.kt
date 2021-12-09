package com.mapbox.maps.plugin.viewport.state

/**
 * Observer that gets notified whenever [ViewportState] changes.
 */
fun interface ViewportStateChangedObserver {
  /**
   * Called whenever [ViewportState] changes.
   * @param viewportState current states
   */
  fun onViewportCameraStateChanged(viewportState: ViewportState)
}