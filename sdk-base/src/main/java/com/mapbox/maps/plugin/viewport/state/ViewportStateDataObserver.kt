package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental

/**
 * Observer that gets notified whenever new data is available.
 */
@MapboxExperimental
fun interface ViewportStateDataObserver {
  /**
   * Notifies that new data is available.
   *
   * @param cameraOptions The latest [CameraOptions] from the [ViewportState].
   * @return true if new data is needed. returning false will unsubscribe from further data updates.
   */
  fun onNewData(cameraOptions: CameraOptions): Boolean
}