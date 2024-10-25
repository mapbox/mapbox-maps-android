package com.mapbox.maps

/**
 * A block which can be used to obtain a [SnapshotOverlay] to draw custom content directly over the snapshot image.
 */
fun interface SnapshotOverlayCallback {

  /**
   * Invoked when snapshot overlay has been prepared.
   */
  fun onSnapshotOverlay(overlay: SnapshotOverlay)
}