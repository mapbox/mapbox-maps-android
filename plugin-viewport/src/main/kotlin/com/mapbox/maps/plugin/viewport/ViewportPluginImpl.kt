package com.mapbox.maps.plugin.viewport

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.core.animation.doOnEnd
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.viewport.ViewportPlugin.Companion.DEFAULT_FRAME_TRANSITION_OPT
import com.mapbox.maps.plugin.viewport.data.MapboxViewportDataSource
import com.mapbox.maps.plugin.viewport.data.ViewportData
import com.mapbox.maps.plugin.viewport.data.ViewportDataSource
import com.mapbox.maps.plugin.viewport.data.ViewportDataSourceUpdateObserver
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportState.Following
import com.mapbox.maps.plugin.viewport.state.ViewportState.Idle
import com.mapbox.maps.plugin.viewport.state.ViewportState.Overview
import com.mapbox.maps.plugin.viewport.state.ViewportState.TransitionToFollowing
import com.mapbox.maps.plugin.viewport.state.ViewportState.TransitionToOverview
import com.mapbox.maps.plugin.viewport.state.ViewportStateChangedObserver
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportStateTransition
import com.mapbox.maps.plugin.viewport.transition.TransitionEndListener
import com.mapbox.maps.plugin.viewport.transition.ViewportStateTransition
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import com.mapbox.maps.plugin.viewport.transition.ViewportTransitionOptions
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Mapbox default implementation for [ViewportPlugin].
 */
class ViewportPluginImpl : ViewportPlugin {
  private var frameTransitionOptions = DEFAULT_FRAME_TRANSITION_OPT
  private var runningAnimation: AnimatorSet? = null
  private val transitionEndListeners = CopyOnWriteArraySet<TransitionEndListener>()
  private val viewportCameraStateChangedObservers =
    CopyOnWriteArraySet<ViewportStateChangedObserver>()
  private lateinit var cameraPlugin: CameraAnimationsPlugin
  private lateinit var locationComponentPlugin: LocationComponentPlugin
  private lateinit var mapDelegateProvider: MapDelegateProvider
  private lateinit var stateTransition: ViewportStateTransition

  /**
   * Describes an object that provides desired camera positions to [ViewportPlugin].
   */
  override lateinit var dataSource: ViewportDataSource

  /**
   * Returns current [ViewportTransition].
   * @see registerViewportCameraStateChangedObserver
   */
  override var state: ViewportState = Idle
    private set(value) {
      if (value != field) {
        field = value
        viewportCameraStateChangedObservers.forEach {
          it.onViewportCameraStateChanged(value)
        }
      }
    }

  private val sourceUpdateObserver = ViewportDataSourceUpdateObserver(::updateFrame)

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
  override fun requestCameraToFollowing(
    stateTransitionOptionsBlock: ((ViewportTransitionOptions.Builder).() -> Unit),
    frameTransitionOptionsBlock: ((ViewportTransitionOptions.Builder).() -> Unit),
    transitionEndListener: TransitionEndListener?,
  ) {
    requestCameraToFollowing(
      ViewportTransitionOptions.Builder()
        .maxDuration(DEFAULT_STATE_TRANSITION_MAX_DURATION_MS).apply(stateTransitionOptionsBlock)
        .build(),
      ViewportTransitionOptions.Builder()
        .maxDuration(DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS).apply(frameTransitionOptionsBlock)
        .build(),
      transitionEndListener,
    )
  }

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
  override fun requestCameraToFollowing(
    stateTransitionOptions: ViewportTransitionOptions,
    frameTransitionOptions: ViewportTransitionOptions,
    transitionEndListener: TransitionEndListener?,
  ) {
    when (state) {
      TransitionToFollowing -> transitionEndListener?.let { listener ->
        transitionEndListeners.add(listener)
      }
      Following -> transitionEndListener?.onTransitionEnd(isCanceled = false)
      Idle, TransitionToOverview, Overview -> {
        val data = dataSource.getViewportData()
        startAnimation(
          stateTransition.transitionToFollowing(data.cameraForFollowing, stateTransitionOptions)
            .apply {
              addListener( // todo
                createTransitionListener(TransitionToFollowing, Following, frameTransitionOptions)
              )
            },
          instant = false,
          transitionEndListener,
        )
      }
    }
  }

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
  override fun requestCameraToOverview(
    stateTransitionOptionsBlock: ((ViewportTransitionOptions.Builder).() -> Unit),
    frameTransitionOptionsBlock: ((ViewportTransitionOptions.Builder).() -> Unit),
    transitionEndListener: TransitionEndListener?,
  ) {
    requestCameraToOverview(
      ViewportTransitionOptions.Builder()
        .maxDuration(DEFAULT_STATE_TRANSITION_MAX_DURATION_MS).apply(stateTransitionOptionsBlock)
        .build(),
      ViewportTransitionOptions.Builder()
        .maxDuration(DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS).apply(frameTransitionOptionsBlock)
        .build(),
      transitionEndListener,
    )
  }

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
  override fun requestCameraToOverview(
    stateTransitionOptions: ViewportTransitionOptions,
    frameTransitionOptions: ViewportTransitionOptions,
    transitionEndListener: TransitionEndListener?,
  ) {
    when (state) {
      TransitionToOverview -> transitionEndListener?.let { listener ->
        transitionEndListeners.add(listener)
      }
      Overview -> transitionEndListener?.onTransitionEnd(isCanceled = false)
      Idle, TransitionToFollowing, Following -> {
        val data = dataSource.getViewportData()
        startAnimation(
          stateTransition.transitionToOverview(data.cameraForOverview, stateTransitionOptions)
            .apply {
              addListener( // todo
                createTransitionListener(TransitionToOverview, Overview, frameTransitionOptions)
              )
            },
          instant = false,
          transitionEndListener,
        )
      }
    }
  }

  /**
   * If the [state] is [Following] or [Overview],
   * performs an immediate camera transition (a jump, with animation duration equal to `0`)
   * based on the latest data obtained with [ViewportDataSource.getViewportData].
   */
  override fun resetFrame() {
    val viewportData = dataSource.getViewportData()
    updateFrame(viewportData, instant = true)
  }

  private fun updateFrame(viewportData: ViewportData, instant: Boolean = false) {
    when (state) {
      Following -> {
        startAnimation(
          stateTransition.updateFrameForFollowing(
            viewportData.cameraForFollowing,
            frameTransitionOptions
          ).apply { doOnEnd { finishAnimation(this) } },
          instant
        )
      }
      Overview -> {
        startAnimation(
          stateTransition.updateFrameForOverview(
            viewportData.cameraForOverview,
            frameTransitionOptions
          ).apply { doOnEnd { finishAnimation(this) } },
          instant
        )
      }
      Idle, TransitionToFollowing, TransitionToOverview -> {
        // no impl
      }
    }
  }

  /**
   * Immediately goes to [Idle] state canceling all ongoing transitions.
   */
  override fun requestCameraToIdle() {
    if (state != Idle) {
      cancelAnimation()
      setIdleProperties()
    }
  }

  /**
   * Registers [ViewportStateChangedObserver].
   */
  override fun registerViewportCameraStateChangedObserver(
    viewportStateChangedObserver: ViewportStateChangedObserver
  ) {
    viewportCameraStateChangedObservers.add(viewportStateChangedObserver)
    viewportStateChangedObserver.onViewportCameraStateChanged(state)
  }

  /**
   * Unregisters [ViewportStateChangedObserver].
   */
  override fun unregisterViewportCameraStateChangedObserver(
    viewportStateChangedObserver: ViewportStateChangedObserver
  ) {
    viewportCameraStateChangedObservers.remove(viewportStateChangedObserver)
  }

  private fun setIdleProperties() {
    this.frameTransitionOptions = DEFAULT_FRAME_TRANSITION_OPT
    state = Idle
  }

  private fun cancelAnimation() {
    runningAnimation?.apply {
      cancel()
      childAnimations.forEach {
        cameraPlugin.unregisterAnimators(it as ValueAnimator)
      }
    }
    runningAnimation = null
  }

  private fun startAnimation(
    animatorSet: AnimatorSet,
    instant: Boolean,
    transitionEndListener: TransitionEndListener? = null,
  ) {
    cancelAnimation()
    transitionEndListener?.let {
      transitionEndListeners.add(it)
    }
    animatorSet.childAnimations.forEach {
      cameraPlugin.registerAnimators(it as ValueAnimator)
    }
    if (instant) {
      animatorSet.duration = 0
    }
    animatorSet.start()
    runningAnimation = animatorSet
  }

  private fun finishAnimation(animatorSet: AnimatorSet?) {
    animatorSet?.childAnimations?.forEach {
      cameraPlugin.unregisterAnimators(it as ValueAnimator)
    }
    if (runningAnimation == animatorSet) {
      runningAnimation = null
    }
  }

  private fun createTransitionListener(
    progressState: ViewportState,
    finalState: ViewportState,
    frameTransitionOptions: ViewportTransitionOptions,
  ) = object : Animator.AnimatorListener {

    private var isCanceled = false

    override fun onAnimationStart(animation: Animator?) {
      this@ViewportPluginImpl.frameTransitionOptions = DEFAULT_FRAME_TRANSITION_OPT
      state = progressState
    }

    override fun onAnimationEnd(animation: Animator?) {
      if (isCanceled) {
        setIdleProperties()
      } else {
        this@ViewportPluginImpl.frameTransitionOptions = frameTransitionOptions
        state = finalState
      }

      finishAnimation(animation as AnimatorSet)
      transitionEndListeners.forEach { it.onTransitionEnd(isCanceled) }
      transitionEndListeners.clear()
      updateFrame(dataSource.getViewportData())
    }

    override fun onAnimationCancel(animation: Animator?) {
      isCanceled = true
    }

    override fun onAnimationRepeat(animation: Animator?) {
    }
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.mapDelegateProvider = delegateProvider
    this.cameraPlugin = delegateProvider.mapPluginProviderDelegate.camera
    this.stateTransition =
      MapboxViewportStateTransition(delegateProvider.mapCameraManagerDelegate, cameraPlugin)

    if (!::dataSource.isInitialized) {
      this.dataSource = MapboxViewportDataSource(
        mapDelegateProvider.mapCameraManagerDelegate,
        mapDelegateProvider.mapTransformDelegate
      )
    }
    @Suppress("UNCHECKED_CAST")
    this.locationComponentPlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(
      Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID
    ) ?: throw InvalidPluginConfigurationException(
      "Can't look up an instance of location component plugin, " +
        "is it available on the clazz path and loaded through the map?"
    )
  }

  /**
   * Called when the plugin is first added to the map.
   */
  override fun initialize() {
    dataSource.registerUpdateObserver(sourceUpdateObserver)
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
    runningAnimation?.cancel()
    transitionEndListeners.clear()
    viewportCameraStateChangedObservers.clear()
  }

  internal companion object {
    /**
     * Constant used to recognize the owner of transitions initiated by the [ViewportPluginImpl].
     *
     * @see CameraAnimator.owner
     */
    internal const val VIEWPORT_CAMERA_OWNER = "VIEWPORT_CAMERA_OWNER"
  }
}