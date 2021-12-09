package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.plugin.viewport.data.ViewportDataSource

/**
 * Set of possible [ViewportCamera] states.
 */
sealed class ViewportState {
  /**
   * Describes state when `ViewportCamera` does not execute any transitions.
   *
   * Set after invoking [ViewportCamera.requestViewportCameraToIdle]
   */
  object Idle : ViewportState()

  /**
   * Describes state when `ViewportCamera` transitions to the [Following] state.
   *
   * Set after invoking [ViewportCamera.requestViewportCameraToFollowing].
   */
  object TransitionToFollowing : ViewportState()

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
  object Following : ViewportState()

  /**
   * Describes state when `ViewportCamera` transitions to the [Overview] state.
   *
   * Set after invoking [ViewportCamera.requestViewportCameraToOverview].
   */
  object TransitionToOverview : ViewportState()

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
  object Overview : ViewportState()
}