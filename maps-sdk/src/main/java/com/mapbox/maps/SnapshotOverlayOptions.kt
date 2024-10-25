package com.mapbox.maps

/**
 * Data class to config the overlays on the snapshotter
 *
 * @param showLogo whether show Mapbox logo on the taken snapshot
 * @param showAttributes whether show attribution on the taken snapshot
 */

data class SnapshotOverlayOptions @JvmOverloads constructor(
  val showLogo: Boolean = true,
  val showAttributes: Boolean = true
)