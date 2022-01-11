package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.viewport.data.FollowingViewportStateOptions

/**
 * The [ViewportState] that follows user's location.
 *
 * Note: [LocationComponentPlugin] should be enabled to use this viewport state.
 */
interface FollowingViewportState : ViewportState {
  /**
   * Describes the configuration options of the state.
   */
  var options: FollowingViewportStateOptions
}