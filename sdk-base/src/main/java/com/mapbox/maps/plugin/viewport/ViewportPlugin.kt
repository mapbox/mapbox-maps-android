package com.mapbox.maps.plugin.viewport

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
 * The [ViewportPlugin] provides a structured approach to organizing camera management logic into states
 * and transitions between them.
 *
 * At any given time, the viewport is either:
 *  - idle (not updating the camera)
 *  - in a state (camera is being managed by a [ViewportState])
 *  - transitioning (camera is being managed by a [ViewportTransition])
 */
interface ViewportPlugin : MapPlugin {

  /**
   * Returns current [ViewportStatus].
   *
   * [ViewportStatus] can not be set directly, use [ViewportPlugin.transitionTo] and [ViewportPlugin.idle]
   * to transition to a state or to idle.
   *
   * Defaults to [ViewportStatus.Idle]
   *
   * @see addStatusObserver
   * @see removeStatusObserver
   */
  val status: ViewportStatus

  /**
   * Executes a transition to requested state.
   *
   * When called, the [status] goes to [ViewportTransition] and to the final [ViewportState] when ended.
   *
   * Transitioning to state 'x' when the current [status] is 'ViewportStatus.State(x)' invokes [completionListener]
   * synchronously with true and does not modify [ViewportPlugin.status].
   *
   * Transitioning to state 'x' when the current [status] is 'ViewportStatus.Transition(_, x)' invokes
   * [completionListener] synchronously with false and does not modify [ViewportPlugin.status].
   *
   * If transition is failed or canceled, [status] goes to [ViewportStatus.Idle].
   *
   * @param targetState The target [ViewportState] to transition to.
   * @param transition The [ViewportTransition] that's used to transition to target state, if not specified, the [ViewportPlugin.defaultTransition] will be used. Defaults to null.
   * @param completionListener The listener that's invoked when the transition ends, defaults to null.
   */
  fun transitionTo(targetState: ViewportState, transition: ViewportTransition? = null, completionListener: CompletionListener? = null)

  /**
   * Sets [ViewportPlugin.status] to [ViewportStatus.Idle] synchronously.
   *
   * This cancels any active [ViewportState] or [ViewportTransition].
   */
  fun idle()

  /**
   * Configuration options that impact the [ViewportPlugin].
   */
  var options: ViewportOptions

  // Transitions

  /**
   * [ViewportPlugin.transitionTo] uses this transition unless some non-null value is passed to its
   * transition argument.
   *
   * Defaults to [DefaultViewportTransition] with default options.
   */
  var defaultTransition: ViewportTransition

  // Observers

  /**
   * Subscribes [ViewportStatusObserver] to observe the [ViewportStatus] change.
   *
   * [ViewportPlugin] keeps a strong reference to registered observers. Adding the same observer again
   * while it is already subscribed has no effect.
   *
   * Note: Observers are notified of status changes asynchronously on the main queue. This means that
   * by the time the notification is delivered, the status may have already changed again. This behaviour
   * is necessary to allow observers to trigger further transitions while avoiding out-of-order delivery
   * of status changed notifications.
   *
   * @param viewportStatusObserver the observer that will be notified when the [ViewportPlugin.status] changes.
   * @see removeStatusObserver
   */
  fun addStatusObserver(
    viewportStatusObserver: ViewportStatusObserver
  )

  /**
   * Unsubscribes [ViewportStatusObserver] from [ViewportPlugin.status] changes.
   *
   * This causes [ViewportPlugin] to release its strong reference to the observer. Removing an observer
   * that is not subscribed has no effect.
   *
   * @param viewportStatusObserver the observer that should no longer be notified when the [ViewportPlugin.status] changes.
   * @see addStatusObserver
   */
  fun removeStatusObserver(
    viewportStatusObserver: ViewportStatusObserver
  )

  // Convenient methods to create the in-stock [ViewportState] and [ViewportTransition].

  /**
   * Create a new [FollowPuckViewportState] instance with provided [FollowPuckViewportStateOptions].
   *
   * @param options The desired [FollowPuckViewportStateOptions], defaults to [FollowPuckViewportStateOptions] that's initialised with default parameters.
   * @return The newly-created [FollowPuckViewportState] instance.
   */
  fun makeFollowPuckViewportState(
    options: FollowPuckViewportStateOptions = FollowPuckViewportStateOptions.Builder().build()
  ): FollowPuckViewportState

  /**
   * Create a new [OverviewViewportState] instance with provided [OverviewViewportStateOptions].
   *
   * @param options The desired [OverviewViewportStateOptions]
   * @return The newly-created [OverviewViewportState] instance.
   */
  fun makeOverviewViewportState(options: OverviewViewportStateOptions): OverviewViewportState

  /**
   * Create a new [DefaultViewportTransition] instance with provided [DefaultViewportTransitionOptions].
   *
   * @param options The desired [DefaultViewportTransitionOptions], defaults to [DefaultViewportTransitionOptions] that's initialised with default parameters.
   * @return The newly-created [DefaultViewportTransition] instance.
   */
  fun makeDefaultViewportTransition(
    options: DefaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder().build()
  ): DefaultViewportTransition

  /**
   * Create a new [ViewportTransition] instance that transition to the target [ViewportState] immediately.
   *
   * @return The newly-created ImmediateViewportTransition instance.
   */
  fun makeImmediateViewportTransition(): ViewportTransition
}