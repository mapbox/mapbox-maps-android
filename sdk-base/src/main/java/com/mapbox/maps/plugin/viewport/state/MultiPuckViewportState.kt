package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.MultiPuckViewportStateOptions

/**
 * The [ViewportState] that tracks the location puck's position.
 *
 * Use [ViewportPlugin.makeMultiPuckViewportState] to create instances of [MultiPuckViewportState].
 *
 * Note: [LocationComponentPlugin] should be enabled to use this viewport state, and Users are
 * responsible to create the viewport states and keep a reference to these states for
 * future operations.
 */
interface MultiPuckViewportState : ViewportState {
  /**
   * Describes the configuration options of the state.
   */
  var options: MultiPuckViewportStateOptions
}