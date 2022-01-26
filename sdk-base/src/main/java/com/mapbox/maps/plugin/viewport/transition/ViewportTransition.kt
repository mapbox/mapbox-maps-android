package com.mapbox.maps.plugin.viewport.transition

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.state.ViewportState

/**
 * Defines how to transition to another [ViewportState].
 */
@MapboxExperimental
fun interface ViewportTransition {
  /**
   * Run the [ViewportTransition] from current viewport to the target [ViewportState].
   *
   * The completion block contains a Bool that is true if the transition is cancelled and false
   * if it ran to completion. Implementations must be sure to invoke the completion block with false if
   * the returned Cancelable is invoked prior to completion. the completion block must be invoked
   * on the main queue. Transitions must handle the possibility that the "to" state might fail to
   * provide a target camera in a timely manner or might update the target camera multiple times
   * during the transition (a "moving target").
   *
   * @param to The target [ViewportState]
   * @param completionListener The listener to observe the completion state.
   * @return a handle that can be used to cancel the current [ViewportTransition]
   */
  fun run(
    to: ViewportState,
    completionListener: CompletionListener
  ): Cancelable
}