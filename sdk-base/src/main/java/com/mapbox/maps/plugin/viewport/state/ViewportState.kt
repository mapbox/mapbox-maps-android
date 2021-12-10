package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.data.ViewportDataSource

/**
 * Set of possible [ViewportPlugin] states.
 */
sealed class ViewportState {
  /**
   * Describes state when [ViewportPlugin] does not execute any transitions.
   *
   * Set after invoking [ViewportPlugin.requestCameraToIdle]
   */
  object Idle : ViewportState()

  /**
   * Describes state when [ViewportPlugin] transitions to the [Following] state.
   *
   * Set after invoking [ViewportPlugin.requestCameraToFollowing].
   */
  object TransitionToFollowing : ViewportState()

  /**
   * Describes state when [ViewportPlugin] finished transition to the following state.
   *
   * Preceded by [TransitionToFollowing].
   *
   * Set after invoking [ViewportPlugin.requestCameraToFollowing].
   *
   * When in this state, each update to the [ViewportDataSource]
   * will automatically trigger another transition.
   */
  object Following : ViewportState()

  /**
   * Describes state when [ViewportPlugin] transitions to the [Overview] state.
   *
   * Set after invoking [ViewportPlugin.requestCameraToOverview].
   */
  object TransitionToOverview : ViewportState()

  /**
   * Describes state when [ViewportPlugin] finished transition to the overview state.
   *
   * Preceded by [TransitionToOverview].
   *
   * Set after invoking [ViewportPlugin.requestCameraToOverview].
   *
   * When in this state, each update to the [ViewportDataSource]
   * will automatically trigger another transition.
   */
  object Overview : ViewportState()
}