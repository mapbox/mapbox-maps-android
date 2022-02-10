package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions

/**
 * The [ViewportState] that tracks the location puck's position.
 *
 * Use [ViewportPlugin.makeFollowPuckViewportState] to create instances of [FollowPuckViewportState].
 *
 * Note: [LocationComponentPlugin] should be enabled to use this viewport state, and Users are
 * responsible to create the viewport states and keep a reference to these states for
 * future operations.
 */
@MapboxExperimental
interface FollowPuckViewportState : ViewportState {
  /**
   * Describes the configuration options of the state.
   */
  var options: FollowPuckViewportStateOptions
}