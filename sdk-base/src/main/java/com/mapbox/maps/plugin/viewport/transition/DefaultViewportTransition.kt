package com.mapbox.maps.plugin.viewport.transition

import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions

/**
 * The default [ViewportTransition] that transitions Viewport to the target [ViewportState].
 */
interface DefaultViewportTransition : ViewportTransition {
  /**
   * Describes the configuration options for the [DefaultViewportTransition].
   */
  var options: DefaultViewportTransitionOptions
}