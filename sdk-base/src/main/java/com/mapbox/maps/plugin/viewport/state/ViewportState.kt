package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.viewport.ViewportPlugin

/**
 * Definition of [ViewportPlugin] states.
 *
 * [ViewportState] is used to observe the data source associated with the state and query the camera
 * to keep updating according to current viewport data.
 *
 * Users are responsible to create the viewport states and keep a reference to these states for
 * future operations.
 */
@MapboxExperimental
interface ViewportState {
  /**
   * Observe the new camera options produced by the [ViewportState], it can be used to get the
   * latest state [CameraOptions] for [ViewportTransition].
   *
   * @param viewportStateDataObserver observer that observe new viewport data.
   * @return a handle that cancels current observation.
   */
  fun observeDataSource(viewportStateDataObserver: ViewportStateDataObserver): Cancelable

  /**
   * Start updating the camera for the current [ViewportState].
   */
  fun startUpdatingCamera()

  /**
   * Stop updating the camera for the current [ViewportState].
   */
  fun stopUpdatingCamera()
}