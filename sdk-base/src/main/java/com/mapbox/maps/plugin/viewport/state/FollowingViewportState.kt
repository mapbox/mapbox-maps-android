package com.mapbox.maps.plugin.viewport.state

import com.mapbox.maps.plugin.viewport.data.FollowingViewportStateOptions

/**
 * The [ViewportState] that follows user's location.
 */
interface FollowingViewportState : ViewportState {
  /**
   * Describes the configuration options of the state.
   */
  var options: FollowingViewportStateOptions
}