package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions

/**
 * The [ViewportState] that shows the overview of a given geometry.
 *
 * Users are responsible to create the viewport states and keep a reference to these states for
 * future operations.
 */
interface OverviewViewportState : ViewportState {
  /**
   * Describes the configuration options of the state.
   */
  var options: OverviewViewportStateOptions
}