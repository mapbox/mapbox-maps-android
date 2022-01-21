package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportOptions
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.transition.DefaultViewportTransition
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition

/**
 * The Viewport plugin allows to track objects on a map.
 *
 * It provides a structured approach to organizing camera management logic into states and transitions between them.
 *
 * at any given time, the viewport is either:
 *  - idle (not updating the camera)
 *  - in a state (camera is being managed by a ViewportState)
 *  - transitioning (camera is being managed by a ViewportTransition)
 */
@MapboxExperimental
interface ViewportPlugin : MapPlugin {

  /**
   * Returns current [ViewportStatus].
   *
   * @see addStatusObserver
   */
  val status: ViewportStatus

  /**
   * Executes a transition to requested state.
   *
   * When started, goes to [ViewportTransition]
   * and to the final [ViewportState] when ended.
   *
   * If transition is canceled, state goes to IDLE.
   *
   * @param targetState The target [ViewportState] to transition to.
   * @param transition The [ViewportTransition] that's used to transition to target state, if not specified, the [ViewportPlugin.defaultTransition] will be used.
   * @param completionListener The listener to observe the completion state.
   */
  fun transitionTo(targetState: ViewportState, transition: ViewportTransition? = null, completionListener: CompletionListener? = null)

  /**
   * Immediately goes to IDLE state canceling all ongoing transitions.
   */
  fun idle()

  /**
   * Options that impact the [ViewportPlugin].
   */
  var options: ViewportOptions

  // Transitions

  /**
   * DefaultViewportTransition with default options.
   *
   * This transition is used unless overridden by one of the registered transitions.
   */
  var defaultTransition: ViewportTransition

  // Observers

  /**
   * Adds [ViewportStatusObserver] to observe the status change.
   */
  fun addStatusObserver(
    viewportStatusObserver: ViewportStatusObserver
  )

  /**
   * Removes [ViewportStatusObserver].
   */
  fun removeStatusObserver(
    viewportStatusObserver: ViewportStatusObserver
  )

  // Convenient methods to create the in-stock [ViewportState] and [ViewportTransition].

  /**
   * Create a [FollowPuckViewportState] instance with provided [FollowPuckViewportStateOptions].
   *
   * @param options The desired [FollowPuckViewportStateOptions]
   */
  fun makeFollowPuckViewportState(
    options: FollowPuckViewportStateOptions = FollowPuckViewportStateOptions.Builder().build()
  ): FollowPuckViewportState

  /**
   * Create an [OverviewViewportState] instance with provided [OverviewViewportStateOptions].
   *
   * @param options The desired [OverviewViewportStateOptions]
   */
  fun makeOverviewViewportState(options: OverviewViewportStateOptions): OverviewViewportState

  /**
   * Create a default [ViewportTransition] instance with provided [DefaultViewportTransitionOptions].
   *
   * @param options The desired [DefaultViewportTransitionOptions]
   */
  fun makeDefaultViewportTransition(
    options: DefaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder().build()
  ): DefaultViewportTransition

  /**
   * Create a [ViewportTransition] instance that transition to the target [ViewportState] immediately.
   */
  fun makeImmediateViewportTransition(): ViewportTransition
}