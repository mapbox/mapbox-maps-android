package com.mapbox.maps.plugin.viewport.transition

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.state.ViewportState

/**
 * Defines how to transition to another [ViewportState].
 *
 * [ViewportTransition] is used by [ViewportPlugin] to orchestrate transitions to and from different
 * [ViewportState].
 *
 * Mapbox provides implementations of [ViewportTransition] that can be created and configured via methods
 * on [ViewportPlugin]. Applications may also define their own implementations to handle advanced use
 * cases not covered by the provided implementations.
 *
 * @see [ViewportPlugin.makeDefaultViewportTransition]
 * @see [ViewportPlugin.makeImmediateViewportTransition]
 */
@MapboxExperimental
fun interface ViewportTransition {
  /**
   * Run the [ViewportTransition] from current viewport to the target [ViewportState].
   *
   * The completion block must be invoked with true if the transition completes successfully. If the
   * transition fails, invoke the completion block with false.
   *
   * If the returned [Cancelable] is canceled, it's not necessary to invoke the completion block(but
   * safe to do so, as it will just be ignored.
   *
   * Transitions should handle the possibility that [to] state might fail to provide camera in a timely
   * manner or might update the target camera multiple times during the transition(i.e. a 'moving target').
   *
   * @param to The target [ViewportState]
   * @param completionListener The listener to observe the completion state, it must be invoked on the main queue.
   * @return a handle that can be used to cancel the current [ViewportTransition]
   */
  fun run(
    to: ViewportState,
    completionListener: CompletionListener
  ): Cancelable
}