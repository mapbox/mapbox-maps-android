package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.viewport.data.ViewportDataSource
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportState.Following
import com.mapbox.maps.plugin.viewport.state.ViewportState.Idle
import com.mapbox.maps.plugin.viewport.state.ViewportState.Overview
import com.mapbox.maps.plugin.viewport.state.ViewportState.TransitionToFollowing
import com.mapbox.maps.plugin.viewport.state.ViewportState.TransitionToOverview
import com.mapbox.maps.plugin.viewport.state.ViewportStateChangedObserver
import com.mapbox.maps.plugin.viewport.transition.TransitionEndListener
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import com.mapbox.maps.plugin.viewport.transition.ViewportTransitionOptions

/**
 * The Viewport plugin allows to track objects on a map.
 *
 * Most common use-case is the ability to track the user location and animate the location updates
 * to create a navigation experience. Next to tracking a single object, the API allows tracking
 * multiple objects. The viewport needs to automatically optimize the camera position to take in
 * account the user provided data.
 */
interface ViewportPlugin : MapPlugin {

  /**
   * Returns current [ViewportTransition].
   * @see registerViewportStateChangedObserver
   */
  val state: ViewportState

  /**
   * Describes an object that provides desired camera positions to [ViewportPlugin].
   */
  var dataSource: ViewportDataSource

  /**
   * Executes a transition to [Following] state. When started, goes to [TransitionToFollowing]
   * and to the final [Following] when ended. If transition is canceled, state goes to [Idle].
   *
   * The target camera position is obtained with [ViewportDataSource.getViewportData].
   *
   * @param stateTransitionOptionsBlock options that impact the transition animation from
   * the current state to the requested state.
   * Defaults to [ViewportTransitionOptions.maxDurationMs] equal to [DEFAULT_STATE_TRANSITION_MAX_DURATION_MS] millis.
   * @param frameTransitionOptionsBlock options that impact the transition animations between
   * viewport frames in the selected state.
   * This refers to camera transition on each [ViewportDataSource] update when [Following] is engaged.
   * Defaults to [ViewportTransitionOptions.maxDurationMs] equal to [DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS] millis.
   * @param transitionEndListener invoked when transition ends.
   */
  fun requestCameraToFollowing(
    stateTransitionOptionsBlock: ((ViewportTransitionOptions.Builder).() -> Unit),
    frameTransitionOptionsBlock: ((ViewportTransitionOptions.Builder).() -> Unit),
    transitionEndListener: TransitionEndListener? = null,
  )

  /**
   * Executes a transition to [Following] state. When started, goes to [TransitionToFollowing]
   * and to the final [Following] when ended. If transition is canceled, state goes to [Idle].
   *
   * The target camera position is obtained with [ViewportDataSource.getViewportData].
   *
   * @param stateTransitionOptions options that impact the transition animation from the current state to the requested state.
   * Defaults to [ViewportTransitionOptions.maxDurationMs] equal to [DEFAULT_STATE_TRANSITION_MAX_DURATION_MS] millis.
   * @param frameTransitionOptions options that impact the transition animations between viewport frames in the selected state.
   * This refers to camera transition on each [ViewportDataSource] update when [Following] is engaged.
   * Defaults to [ViewportTransitionOptions.maxDurationMs] equal to [DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS] millis.
   * @param transitionEndListener invoked when transition ends.
   */
  fun requestCameraToFollowing(
    stateTransitionOptions: ViewportTransitionOptions = DEFAULT_STATE_TRANSITION_OPT,
    frameTransitionOptions: ViewportTransitionOptions = DEFAULT_FRAME_TRANSITION_OPT,
    transitionEndListener: TransitionEndListener? = null,
  )

  /**
   * Executes a transition to [Overview] state. When started, goes to [TransitionToOverview]
   * and to the final [Overview] when ended. If transition is canceled, state goes to [Idle].
   *
   * The target camera position is obtained with [ViewportDataSource.getViewportData].
   *
   * @param stateTransitionOptionsBlock options that impact the transition animation from
   * the current state to the requested state.
   * Defaults to [ViewportTransitionOptions.maxDurationMs] equal to [DEFAULT_STATE_TRANSITION_MAX_DURATION_MS] millis.
   * @param frameTransitionOptionsBlock options that impact the transition animations between
   * viewport frames in the selected state.
   * This refers to camera transition on each [ViewportDataSource] update when [Overview] is engaged.
   * Defaults to [ViewportTransitionOptions.maxDurationMs] equal to [DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS] millis.
   * @param transitionEndListener invoked when transition ends.
   */
  fun requestCameraToOverview(
    stateTransitionOptionsBlock: ((ViewportTransitionOptions.Builder).() -> Unit),
    frameTransitionOptionsBlock: ((ViewportTransitionOptions.Builder).() -> Unit),
    transitionEndListener: TransitionEndListener? = null,
  )

  /**
   * Executes a transition to [Overview] state. When started, goes to [TransitionToOverview]
   * and to the final [Overview] when ended. If transition is canceled, state goes to [Idle].
   *
   * The target camera position is obtained with [ViewportDataSource.getViewportData].
   *
   * @param stateTransitionOptions options that impact the transition animation from the current state to the requested state.
   * Defaults to [ViewportTransitionOptions.maxDurationMs] equal to [DEFAULT_STATE_TRANSITION_MAX_DURATION_MS] millis.
   * @param frameTransitionOptions options that impact the transition animations between viewport frames in the selected state.
   * This refers to camera transition on each [ViewportDataSource] update when [Overview] is engaged.
   * Defaults to [ViewportTransitionOptions.maxDurationMs] equal to [DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS] millis.
   * @param transitionEndListener invoked when transition ends.
   */
  fun requestCameraToOverview(
    stateTransitionOptions: ViewportTransitionOptions = DEFAULT_STATE_TRANSITION_OPT,
    frameTransitionOptions: ViewportTransitionOptions = DEFAULT_FRAME_TRANSITION_OPT,
    transitionEndListener: TransitionEndListener? = null,
  )

  /**
   * If the [state] is [Following] or [Overview],
   * performs an immediate camera transition (a jump, with animation duration equal to `0`)
   * based on the latest data obtained with [ViewportDataSource.getViewportData].
   */
  fun resetFrame()

  /**
   * Immediately goes to [Idle] state canceling all ongoing transitions.
   */
  fun requestCameraToIdle()

  /**
   * Registers [ViewportStateChangedObserver].
   */
  fun registerViewportStateChangedObserver(
    viewportStateChangedObserver: ViewportStateChangedObserver
  )

  /**
   * Unregisters [ViewportStateChangedObserver].
   */
  fun unregisterViewportStateChangedObserver(
    viewportStateChangedObserver: ViewportStateChangedObserver
  )

  /**
   * Companion object of ViewportPlugin interface.
   */
  companion object {
    /**
     * The default state transition option.
     */
    val DEFAULT_STATE_TRANSITION_OPT =
      ViewportTransitionOptions.build { maxDuration(DEFAULT_STATE_TRANSITION_MAX_DURATION_MS) }

    /**
     * The default frame transition option.
     */
    val DEFAULT_FRAME_TRANSITION_OPT =
      ViewportTransitionOptions.build { maxDuration(DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS) }
  }
}