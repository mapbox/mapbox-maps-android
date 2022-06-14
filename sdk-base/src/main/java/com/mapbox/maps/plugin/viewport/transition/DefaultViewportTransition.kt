package com.mapbox.maps.plugin.viewport.transition

import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.state.ViewportState

/**
 * The default [ViewportTransition] that transitions viewport to the target [ViewportState].
 *
 * Use [ViewportPlugin.makeDefaultViewportTransition] to create instances of [DefaultViewportTransition].
 *
 * Note: Users are responsible to create the viewport transitions and keep a reference to these transitions for
 * future operations.
 */
interface DefaultViewportTransition : ViewportTransition {
  /**
   * Describes the configuration options for the [DefaultViewportTransition].
   *
   * New values will take effect the next time [ViewportTransition.run] is invoked.
   */
  var options: DefaultViewportTransitionOptions
}