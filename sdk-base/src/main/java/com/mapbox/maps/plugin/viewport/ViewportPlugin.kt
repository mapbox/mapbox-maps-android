package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowingViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportOptions
import com.mapbox.maps.plugin.viewport.state.FollowingViewportState
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
   * If current status is IDLE, returns ViewportStatus.State(null).
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
   * @param completionListener The listener to observe the completion state.
   */
  fun transitionTo(targetState: ViewportState, completionListener: CompletionListener? = null)

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

  /**
   * Set the [ViewportTransition] for the transition from given [ViewportState] to target [ViewportState]
   *
   * @param transition The transition to be set.
   * @param from The state before the transition.
   * @param to The state after the transition.
   */
  fun setTransition(transition: ViewportTransition, from: ViewportState?, to: ViewportState)

  /**
   * Get the [ViewportTransition] from given [ViewportState] to target [ViewportState].
   *
   * @param from The state before the transition.
   * @param to The state after the transition.
   */
  fun getTransition(from: ViewportState?, to: ViewportState): ViewportTransition?

  /**
   * Remove the [ViewportTransition] from given [ViewportState] to target [ViewportState].
   *
   * @param from The state before the transition.
   * @param to The state after the transition.
   */
  fun removeTransition(from: ViewportState?, to: ViewportState)

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
   * Create a [FollowingViewportState] instance with provided [FollowingViewportStateOptions].
   *
   * @param options The desired [FollowingViewportStateOptions]
   */
  fun makeFollowingViewportState(
    options: FollowingViewportStateOptions = FollowingViewportStateOptions.Builder().build()
  ): FollowingViewportState

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