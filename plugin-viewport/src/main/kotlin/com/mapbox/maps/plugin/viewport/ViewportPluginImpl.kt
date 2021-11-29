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
import com.mapbox.maps.plugin.viewport.state.ViewportCameraState
import com.mapbox.maps.plugin.viewport.state.ViewportCameraState.Following
import com.mapbox.maps.plugin.viewport.state.ViewportCameraState.Idle
import com.mapbox.maps.plugin.viewport.state.ViewportCameraState.Overview
import com.mapbox.maps.plugin.viewport.state.ViewportCameraState.TransitionToFollowing
import com.mapbox.maps.plugin.viewport.state.ViewportCameraState.TransitionToOverview
import com.mapbox.maps.plugin.viewport.state.ViewportCameraStateChangedObserver
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportCameraStateTransition
import com.mapbox.maps.plugin.viewport.transition.TransitionEndListener
import com.mapbox.maps.plugin.viewport.transition.ViewportCameraStateTransition
import com.mapbox.maps.plugin.viewport.transition.ViewportCameraTransition
import com.mapbox.maps.plugin.viewport.transition.ViewportCameraTransitionOptions
import java.util.concurrent.CopyOnWriteArraySet

class ViewportPluginImpl : ViewportPlugin {
  private var frameTransitionOptions = DEFAULT_FRAME_TRANSITION_OPT
  private var runningAnimation: AnimatorSet? = null
  private val transitionEndListeners = CopyOnWriteArraySet<TransitionEndListener>()
  private val viewportCameraStateChangedObservers =
    CopyOnWriteArraySet<ViewportCameraStateChangedObserver>()
  private lateinit var cameraPlugin: CameraAnimationsPlugin
  private lateinit var locationComponentPlugin: LocationComponentPlugin
  private lateinit var mapDelegateProvider: MapDelegateProvider
  override lateinit var viewportDataSource: ViewportDataSource
  private lateinit var stateTransition: ViewportCameraStateTransition

  /**
   * Returns current [ViewportCameraTransition].
   * @see registerViewportCameraStateChangedObserver
   */
  override var state: ViewportCameraState = Idle
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
   * Defaults to [ViewportCameraTransitionOptions.maxDurationMs] equal to 3500 millis.
   * @param frameTransitionOptionsBlock options that impact the transition animations between
   * viewport frames in the selected state.
   * This refers to camera transition on each [ViewportDataSource] update when [Following] is engaged.
   * Defaults to [ViewportCameraTransitionOptions.maxDurationMs] equal to 1000 millis.
   * @param transitionEndListener invoked when transition ends.
   */
  @JvmOverloads
  override fun requestNavigationCameraToFollowing(
    stateTransitionOptionsBlock: ((ViewportCameraTransitionOptions.Builder).() -> Unit),
    frameTransitionOptionsBlock: ((ViewportCameraTransitionOptions.Builder).() -> Unit),
    transitionEndListener: TransitionEndListener?,
  ) {
    requestNavigationCameraToFollowing(
      ViewportCameraTransitionOptions.Builder().apply(stateTransitionOptionsBlock).build(),
      ViewportCameraTransitionOptions.Builder().apply(frameTransitionOptionsBlock).build(),
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
   * Defaults to [ViewportCameraTransitionOptions.maxDurationMs] equal to 3500 millis.
   * @param frameTransitionOptions options that impact the transition animations between viewport frames in the selected state.
   * This refers to camera transition on each [ViewportDataSource] update when [Following] is engaged.
   * Defaults to [ViewportCameraTransitionOptions.maxDurationMs] equal to 1000 millis.
   * @param transitionEndListener invoked when transition ends.
   */
  @JvmOverloads
  override fun requestNavigationCameraToFollowing(
    stateTransitionOptions: ViewportCameraTransitionOptions,
    frameTransitionOptions: ViewportCameraTransitionOptions,
    transitionEndListener: TransitionEndListener?,
  ) {
    when (state) {
      TransitionToFollowing -> transitionEndListener?.let { listener ->
        transitionEndListeners.add(listener)
      }
      Following -> transitionEndListener?.onTransitionEnd(isCanceled = false)
      Idle, TransitionToOverview, Overview -> {
        val data = viewportDataSource.getViewportData()
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
   * Defaults to [ViewportCameraTransitionOptions.maxDurationMs] equal to 3500 millis.
   * @param frameTransitionOptionsBlock options that impact the transition animations between
   * viewport frames in the selected state.
   * This refers to camera transition on each [ViewportDataSource] update when [Overview] is engaged.
   * Defaults to [ViewportCameraTransitionOptions.maxDurationMs] equal to 1000 millis.
   * @param transitionEndListener invoked when transition ends.
   */
  @JvmOverloads
  override fun requestNavigationCameraToOverview(
    stateTransitionOptionsBlock: ((ViewportCameraTransitionOptions.Builder).() -> Unit),
    frameTransitionOptionsBlock: ((ViewportCameraTransitionOptions.Builder).() -> Unit),
    transitionEndListener: TransitionEndListener?,
  ) {
    requestNavigationCameraToOverview(
      ViewportCameraTransitionOptions.Builder().apply(stateTransitionOptionsBlock).build(),
      ViewportCameraTransitionOptions.Builder().apply(frameTransitionOptionsBlock).build(),
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
   * Defaults to [ViewportCameraTransitionOptions.maxDurationMs] equal to 3500 millis.
   * @param frameTransitionOptions options that impact the transition animations between viewport frames in the selected state.
   * This refers to camera transition on each [ViewportDataSource] update when [Overview] is engaged.
   * Defaults to [ViewportCameraTransitionOptions.maxDurationMs] equal to 1000 millis.
   * @param transitionEndListener invoked when transition ends.
   */
  @JvmOverloads
  override fun requestNavigationCameraToOverview(
    stateTransitionOptions: ViewportCameraTransitionOptions,
    frameTransitionOptions: ViewportCameraTransitionOptions,
    transitionEndListener: TransitionEndListener?,
  ) {
    when (state) {
      TransitionToOverview -> transitionEndListener?.let { listener ->
        transitionEndListeners.add(listener)
      }
      Overview -> transitionEndListener?.onTransitionEnd(isCanceled = false)
      Idle, TransitionToFollowing, Following -> {
        val data = viewportDataSource.getViewportData()
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
    val viewportData = viewportDataSource.getViewportData()
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
  override fun requestNavigationCameraToIdle() {
    if (state != Idle) {
      cancelAnimation()
      setIdleProperties()
    }
  }

  /**
   * Registers [ViewportCameraStateChangedObserver].
   */
  override fun registerViewportCameraStateChangedObserver(
    viewportCameraStateChangedObserver: ViewportCameraStateChangedObserver
  ) {
    viewportCameraStateChangedObservers.add(viewportCameraStateChangedObserver)
    viewportCameraStateChangedObserver.onViewportCameraStateChanged(state)
  }

  /**
   * Unregisters [ViewportCameraStateChangedObserver].
   */
  override fun unregisterViewportCameraStateChangedObserver(
    viewportCameraStateChangedObserver: ViewportCameraStateChangedObserver
  ) {
    viewportCameraStateChangedObservers.remove(viewportCameraStateChangedObserver)
  }

  private fun setIdleProperties() {
    this.frameTransitionOptions = DEFAULT_FRAME_TRANSITION_OPT
    state = Idle
  }

  private fun cancelAnimation() {
    runningAnimation?.let { set ->
      set.cancel()
      set.childAnimations.forEach {
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
    if (transitionEndListener != null) {
      transitionEndListeners.add(transitionEndListener)
    }
    animatorSet.childAnimations.forEach {
      cameraPlugin.registerAnimators(it as ValueAnimator)
    }
    if (instant) {
      animatorSet.duration = 0
    }

    // workaround for https://github.com/mapbox/mapbox-maps-android/issues/277
    cameraPlugin.anchor = null

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
    progressState: ViewportCameraState,
    finalState: ViewportCameraState,
    frameTransitionOptions: ViewportCameraTransitionOptions,
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
      updateFrame(viewportDataSource.getViewportData())
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
      MapboxViewportCameraStateTransition(delegateProvider.mapCameraManagerDelegate, cameraPlugin)

    if (!::viewportDataSource.isInitialized) {
      this.viewportDataSource = MapboxViewportDataSource(
        mapDelegateProvider.mapCameraManagerDelegate,
        mapDelegateProvider.mapTransformDelegate
      )
    }
    @Suppress("UNCHECKED_CAST")
    this.locationComponentPlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(
      Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID
    ) ?: throw InvalidPluginConfigurationException(
      "Can't look up an instance of plugin, " +
        "is it available on the clazz path and loaded through the map?"
    )
  }

  override fun initialize() {
    viewportDataSource.registerUpdateObserver(sourceUpdateObserver)
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