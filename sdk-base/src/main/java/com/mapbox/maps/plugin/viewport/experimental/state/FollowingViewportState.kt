package com.mapbox.maps.plugin.viewport.experimental.state

import com.mapbox.maps.plugin.viewport.experimental.data.FollowingViewportStateOptions

/**
 * The [ViewportState] that follows user's location.
 */
interface FollowingViewportState : ViewportState {
  /**
   * Describes the configuration options of the state.
   */
  var options: FollowingViewportStateOptions
}