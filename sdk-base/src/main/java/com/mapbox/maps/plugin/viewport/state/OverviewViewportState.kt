package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions

/**
 * The [ViewportState] that shows an overview of the geometry specified by its [OverviewViewportStateOptions.geometry].
 *
 * Use [ViewportPlugin.makeOverviewViewportState] to create instances of [OverviewViewportState].
 *
 * Note: Users are responsible to create the viewport states and keep a reference to these states for
 * future operations.
 */
interface OverviewViewportState : ViewportState {
  /**
   * Describes the configuration options of the state.
   *
   * When set, the viewport reframes the geometry using the new options and update its camera with a
   * linear camera animation and with duration specified by the new value's [OverviewViewportStateOptions.animationDurationMs].
   */
  var options: OverviewViewportStateOptions
}