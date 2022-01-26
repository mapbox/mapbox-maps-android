package com.mapbox.maps.plugin.viewport

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.animation.CameraAnimationsLifecycleListener
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorType
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.animation.MapAnimationOwnerRegistry
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportOptions
import com.mapbox.maps.plugin.viewport.data.ViewportStatusChangeReason
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportStateImpl
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import com.mapbox.maps.plugin.viewport.state.OverviewViewportStateImpl
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.transition.DefaultViewportTransition
import com.mapbox.maps.plugin.viewport.transition.DefaultViewportTransitionImpl
import com.mapbox.maps.plugin.viewport.transition.ImmediateViewportTransition
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Mapbox default implementation for [ViewportPlugin].
 *
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
class ViewportPluginImpl(private val handler: Handler = Handler(Looper.getMainLooper())) :
  ViewportPlugin {
  private val registeredStatusObservers = CopyOnWriteArraySet<ViewportStatusObserver>()
  private var currentCancelable: Cancelable? = null
  private lateinit var delegateProvider: MapDelegateProvider
  private lateinit var cameraPlugin: CameraAnimationsPlugin

  private val cameraAnimationsLifecycleListener = object : CameraAnimationsLifecycleListener {
    override fun onAnimatorStarting(
      type: CameraAnimatorType,
      animator: ValueAnimator,
      owner: String?
    ) {
      when (owner) {
        VIEWPORT_CAMERA_OWNER -> Unit
        MapAnimationOwnerRegistry.GESTURES -> {
          if (options.transitionsToIdleUponUserInteraction) {
            currentCancelable?.cancel()
            currentCancelable = null
            updateStatus(
              ViewportStatus.Idle,
              ViewportStatusChangeReason.USER_INTERACTION
            )
          }
        }
      }
    }

    override fun onAnimatorInterrupting(
      type: CameraAnimatorType,
      runningAnimator: ValueAnimator,
      runningAnimatorOwner: String?,
      newAnimator: ValueAnimator,
      newAnimatorOwner: String?
    ) {
      // no-ops
    }

    override fun onAnimatorEnding(
      type: CameraAnimatorType,
      animator: ValueAnimator,
      owner: String?
    ) {
      // no-ops
    }

    override fun onAnimatorCancelling(
      type: CameraAnimatorType,
      animator: ValueAnimator,
      owner: String?
    ) {
      // no-ops
    }
  }

  /**
   * Returns current [ViewportStatus].
   *
   * @see addStatusObserver
   */
  override var status: ViewportStatus = ViewportStatus.Idle
    private set

  private fun updateStatus(targetStatus: ViewportStatus, reason: ViewportStatusChangeReason) {
    if (targetStatus != status) {
      val previousStatus = status
      status = targetStatus
      notifyStatusChanged(previousStatus, targetStatus, reason)
    }
  }

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
  override fun transitionTo(targetState: ViewportState, transition: ViewportTransition?, completionListener: CompletionListener?) {
    with(status) {
      when (this) {
        is ViewportStatus.State -> {
          if (this.state === targetState) {
            completionListener?.onComplete(true)
            return
          }
        }
        is ViewportStatus.Transition -> {
          if (this.toState === targetState) {
            completionListener?.onComplete(false)
            return
          }
        }
        is ViewportStatus.Idle -> Unit
      }
    }
    currentCancelable?.cancel()
    currentCancelable = null

    // get the transition (or default) for the from and to state
    val viewportTransition = transition ?: defaultTransition

    // run the transition
    var completionBlockInvoked = false
    val transitionCancelable = viewportTransition.run(targetState) { isFinished ->
      completionBlockInvoked = true
      if (isFinished) {
        // transfer camera updating responsibility to targetState
        targetState.startUpdatingCamera()
        currentCancelable = Cancelable { targetState.stopUpdatingCamera() }
        updateStatus(
          ViewportStatus.State(targetState),
          ViewportStatusChangeReason.TRANSITION_SUCCEEDED
        )
      } else {
        currentCancelable = null
        updateStatus(ViewportStatus.Idle, ViewportStatusChangeReason.TRANSITION_FAILED)
      }
      completionListener?.onComplete(isFinished)
    }
    // since it's possible that a transition might invoke its
    // completion block synchronously, we'll only store the
    // transition cancelable if the transition is not complete
    // so that we don't clobber the targetState cancelable.
    if (!completionBlockInvoked) {
      currentCancelable = transitionCancelable
      updateStatus(
        ViewportStatus.Transition(viewportTransition, targetState),
        ViewportStatusChangeReason.TRANSITION_STARTED
      )
    }
  }

  /**
   * Immediately goes to [ViewportStatus.Idle] state canceling all ongoing transitions.
   */
  override fun idle() {
    if (status == ViewportStatus.Idle) return
    currentCancelable?.cancel()
    currentCancelable = null
    updateStatus(ViewportStatus.Idle, ViewportStatusChangeReason.IDLE_REQUESTED)
  }

  /**
   * Options that impact the [ViewportPlugin].
   */
  override var options: ViewportOptions = ViewportOptions.Builder().build()

  private fun notifyStatusChanged(
    previousStatus: ViewportStatus,
    currentStatus: ViewportStatus,
    reason: ViewportStatusChangeReason
  ) {
    registeredStatusObservers.forEach {
      handler.post {
        it.onViewportStatusChanged(
          previousStatus,
          currentStatus,
          reason
        )
      }
    }
  }

  /**
   * DefaultViewportTransition with default options
   *
   * This transition is used unless overridden by one of the registered transitions.
   */
  override lateinit var defaultTransition: ViewportTransition

  /**
   * Adds [ViewportStatusObserver] to observe the status change.
   */
  override fun addStatusObserver(viewportStatusObserver: ViewportStatusObserver) {
    registeredStatusObservers.add(viewportStatusObserver)
  }

  /**
   * Removes [ViewportStatusObserver].
   */
  override fun removeStatusObserver(viewportStatusObserver: ViewportStatusObserver) {
    registeredStatusObservers.remove(viewportStatusObserver)
  }

  // Convenient methods to create the in-stock [ViewportState] and [ViewportTransition].

  /**
   * Create a [FollowPuckViewportState] instance with provided [FollowPuckViewportStateOptions].
   *
   * @param options The desired [FollowPuckViewportStateOptions]
   */
  override fun makeFollowPuckViewportState(options: FollowPuckViewportStateOptions): FollowPuckViewportState {
    return FollowPuckViewportStateImpl(delegateProvider, options)
  }

  /**
   * Create an [OverviewViewportState] instance with provided [OverviewViewportStateOptions].
   *
   * @param options The desired [OverviewViewportStateOptions]
   */
  override fun makeOverviewViewportState(options: OverviewViewportStateOptions): OverviewViewportState {
    return OverviewViewportStateImpl(delegateProvider, options)
  }

  /**
   * Create a default [ViewportTransition] instance with provided [DefaultViewportTransitionOptions].
   *
   * @param options The desired [DefaultViewportTransitionOptions]
   */
  override fun makeDefaultViewportTransition(options: DefaultViewportTransitionOptions): DefaultViewportTransition {
    return DefaultViewportTransitionImpl(delegateProvider, options)
  }

  /**
   * Create a [ViewportTransition] instance that transition to the target [ViewportState] immediately.
   */
  override fun makeImmediateViewportTransition(): ViewportTransition {
    return ImmediateViewportTransition(delegateProvider)
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
    this.cameraPlugin = delegateProvider.mapPluginProviderDelegate.camera
    cameraPlugin.addCameraAnimationsLifecycleListener(cameraAnimationsLifecycleListener)
    this.defaultTransition = DefaultViewportTransitionImpl(
      delegateProvider,
      DefaultViewportTransitionOptions.Builder().build()
    )
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
    cameraPlugin.removeCameraAnimationsLifecycleListener(cameraAnimationsLifecycleListener)
  }

  internal companion object {
    /**
     * Constant used to recognize the owner of transitions initiated by the [ViewportPluginImpl].
     *
     * @see CameraAnimator.owner
     */
    const val VIEWPORT_CAMERA_OWNER = "VIEWPORT_CAMERA_OWNER"
  }
}