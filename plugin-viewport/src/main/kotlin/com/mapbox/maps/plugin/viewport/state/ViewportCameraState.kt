package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.plugin.viewport.data.ViewportDataSource

/**
 * Set of possible [ViewportCamera] states.
 */
sealed class ViewportCameraState {
  /**
   * Describes state when `ViewportCamera` does not execute any transitions.
   *
   * Set after invoking [ViewportCamera.requestViewportCameraToIdle]
   */
  object Idle: ViewportCameraState()

  /**
   * Describes state when `ViewportCamera` transitions to the [Following] state.
   *
   * Set after invoking [ViewportCamera.requestViewportCameraToFollowing].
   */
  object TransitionToFollowing: ViewportCameraState()

  /**
   * Describes state when `ViewportCamera` finished transition to the following state.
   *
   * Preceded by [TransitionToFollowing].
   *
   * Set after invoking [ViewportCamera.requestViewportCameraToFollowing].
   *
   * When in this state, each update to the [ViewportDataSource]
   * will automatically trigger another transition.
   */
  object Following: ViewportCameraState()

  /**
   * Describes state when `ViewportCamera` transitions to the [Overview] state.
   *
   * Set after invoking [ViewportCamera.requestViewportCameraToOverview].
   */
  object TransitionToOverview: ViewportCameraState()

  /**
   * Describes state when `ViewportCamera` finished transition to the overview state.
   *
   * Preceded by [TransitionToOverview].
   *
   * Set after invoking [ViewportCamera.requestViewportCameraToOverview].
   *
   * When in this state, each update to the [ViewportDataSource]
   * will automatically trigger another transition.
   */
  object Overview: ViewportCameraState()
}
