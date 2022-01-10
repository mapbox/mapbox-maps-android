package com.mapbox.maps.plugin.viewport.experimental.state

import com.mapbox.maps.plugin.viewport.experimental.data.OverviewViewportStateOptions

/**
 * The [ViewportState] that shows the overview of a given geometry.
 */
interface OverviewViewportState : ViewportState {
  /**
   * Describes the configuration options of the state.
   */
  var options: OverviewViewportStateOptions
}