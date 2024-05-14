package com.mapbox.maps.extension.compose.animation.viewport

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import androidx.annotation.UiThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraChangedCallback
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.internal.StateDispatcher
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.ViewportStatusObserver
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportStatusChangeReason
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportStateDataObserver
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.toCameraOptions
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Create and [rememberSaveable] a [MapViewportState] using [MapViewportState.Saver].
 * [init] will be called when the [MapViewportState] is first created to configure its
 * initial state.
 */
@Composable
@MapboxExperimental
public inline fun rememberMapViewportState(
  key: String? = null,
  crossinline init: MapViewportState.() -> Unit = {}
): MapViewportState = rememberSaveable(key = key, saver = MapViewportState.Saver) {
  MapViewportState().apply(init)
}

/**
 * A state object that can be hoisted to control and observe the map's camera state.
 * A [MapViewportState] may only be used by a single [MapboxMap] composable at a time
 * as it reflects instance state for a single view of a map.
 *
 * @param initialCameraState the initial camera position
 */
@MapboxExperimental
public class MapViewportState(
  initialCameraState: CameraState = INIT_CAMERA_STATE
) {
  private var controller: MapView? = null

  private val coroutineScope =
    CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + CoroutineName("MapViewportStateScope"))
  private var cameraChangedCancelable: Cancelable? = null

  private val cameraChangedCallback = CameraChangedCallback {
    cameraState = it.cameraState
  }

  private val viewportStatusObserver = ViewportStatusObserver { _, to, reason ->
    mapViewportStatus = to
    mapViewportStatusChangedReason = reason
  }

  private var stateDispatcher = StateDispatcher()

  /**
   * The current [CameraState] of the map or null if this [MapViewportState] is not yet associated
   * with a map.
   */
  @MapboxExperimental
  public var cameraState: CameraState? by mutableStateOf(null)
    internal set

  /**
   * The reason why the [ViewportStatus] has been changed or null if this [MapViewportState] is not
   * yet associated with a map.
   */
  @MapboxExperimental
  public var mapViewportStatusChangedReason: ViewportStatusChangeReason? by mutableStateOf(null)
    internal set

  /**
   * The current [ViewportStatus] that represents the status of the viewport or null if this
   * [MapViewportState] is not yet associated with a map.
   *
   * It could be either a [ViewportStatus.State], [ViewportStatus.Transition] or
   * [ViewportStatus.Idle].
   */
  @MapboxExperimental
  public var mapViewportStatus: ViewportStatus? by mutableStateOf(null)
    internal set

  init {
    // If the user has provided a specific camera state then we need to enqueue it
    if (initialCameraState != INIT_CAMERA_STATE) {
      setCameraOptions(initialCameraState.toCameraOptions())
    }
  }

  internal fun setMap(mapView: MapView?) {
    // If the MapViewportState is set to another map with different reference, we log a warning message that we don't support multiple map instances.
    if (controller != null && mapView != null && mapView !== this.controller) {
      logW(
        TAG,
        "The map viewport state should not be used across multiple map instances! The previous map instance will lose updates."
      )
    }

    // Setting same map instance will be a no-op
    if (mapView === this.controller) {
      return
    }

    // clear up previous observers
    controller?.viewport?.removeStatusObserver(viewportStatusObserver)
    cameraChangedCancelable?.cancel()

    // update controller
    this.controller = mapView

    // Resume or pause the dispatching based on current validness of mapview
    if (mapView != null) {
      // Manually retrieve the current camera state, so we don't need to wait for the first callback.
      cameraState = mapView.mapboxMap.cameraState
      // start observing the camera updates from the new mapview
      cameraChangedCancelable = mapView.mapboxMap.subscribeCameraChanged(cameraChangedCallback)

      // Manually retrieve the current viewport state, so we don't need to wait for the first callback.
      val viewport = mapView.viewport
      mapViewportStatus = viewport.status
      // TODO: we don't expose the reason as public API in viewport plugin so for now we reset to null
      mapViewportStatusChangedReason = null
      viewport.addStatusObserver(viewportStatusObserver)

      stateDispatcher.resumeDispatching()
    } else {
      // We lost the mapview so let's reset the current state to unknown
      cameraState = null
      mapViewportStatus = null
      mapViewportStatusChangedReason = null
      stateDispatcher.pauseDispatching()
    }
  }

  /**
   * Move the camera instantaneously as specified by [cameraOptions]. Any camera animation in progress
   * will be cancelled.
   *
   * This method must be called from the map's UI thread.
   */
  @UiThread
  @MapboxExperimental
  public fun setCameraOptions(cameraOptions: CameraOptions) {
    dispatchMapViewOperation { mapView ->
      mapView.apply {
        viewport.transitionTo(
          CameraViewportState(cameraOptions) {
            camera.easeToImmediately(it)
          },
          viewport.makeImmediateViewportTransition()
        )
      }
    }
  }

  /**
   * Move the camera instantaneously as specified by [block] camera options.
   * Any camera animation in progress will be cancelled.
   *
   * This method must be called from the map's UI thread.
   */
  @UiThread
  @MapboxExperimental
  public fun setCameraOptions(block: CameraOptions.Builder.() -> Unit) {
    setCameraOptions(cameraOptions(block))
  }

  /**
   * A utility function to get the default [CameraOptions] defined in the style or null if this
   * [MapViewportState] is not yet associated with a map.
   */
  @MapboxExperimental
  public val styleDefaultCameraOptions: CameraOptions?
    get() = controller?.mapboxMap?.style?.styleDefaultCamera

  /**
   * Ease the map camera to a given camera options.
   *
   * @param cameraOptions The camera options to ease to
   * @param animationOptions Transition options (animation duration, listeners etc)
   * @param completionListener the optional [CompletionListener] to observe the completion if the transition
   */
  @MapboxExperimental
  public fun easeTo(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions? = null,
    completionListener: CompletionListener? = null
  ) {
    dispatchMapViewOperation { mapView ->
      mapView.apply {
        viewport.transitionTo(
          targetState = CameraViewportState(cameraOptions) {
            camera.easeToImmediately(it)
          },
          transition = GenericViewportTransition { cameraOptions, transitionCompletionListener ->
            camera.easeTo(
              cameraOptions,
              animationOptions,
              OnAnimationCompletedListener(listOf(completionListener, transitionCompletionListener))
            )
          }
        )
      }
    }
  }

  /**
   * Fly the map camera to a given camera options.
   *
   * This method implements an “optimal path” animation, as detailed in:

   * Van Wijk, Jarke J.; Nuij, Wim A. A. “Smooth and efficient zooming and
   * panning.” INFOVIS ’03. pp. 15–22.
   * [The online documentation](https://www.win.tue.nl/~vanwijk/zoompan.pdf#page=5).

   * Where applicable, local variable documentation begins with the associated
   * variable or function in van Wijk (2003).
   *
   * @param cameraOptions The camera options to fly to
   * @param animationOptions Transition options (animation duration, listeners etc)
   * @param completionListener the optional [CompletionListener] to observe the completion if the transition
   */
  @MapboxExperimental
  public fun flyTo(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions? = null,
    completionListener: CompletionListener? = null
  ) {
    dispatchMapViewOperation { mapView ->
      mapView.apply {
        viewport.transitionTo(
          targetState = CameraViewportState(cameraOptions) {
            camera.easeToImmediately(it)
          },
          transition = GenericViewportTransition { cameraOptions, transitionCompletionListener ->
            camera.flyTo(
              cameraOptions,
              animationOptions,
              OnAnimationCompletedListener(listOf(completionListener, transitionCompletionListener))
            )
          }
        )
      }
    }
  }

  /**
   * Transition to a new [OverviewViewportState].
   * @param overviewViewportStateOptions the target [OverviewViewportStateOptions] to transition to
   * @param defaultTransitionOptions the defaultTransitionOptions for the transition
   * @param completionListener the optional [CompletionListener] to observe the completion if the transition
   */
  @MapboxExperimental
  public fun transitionToOverviewState(
    overviewViewportStateOptions: OverviewViewportStateOptions,
    defaultTransitionOptions: DefaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder()
      .build(),
    completionListener: CompletionListener? = null
  ) {
    dispatchMapViewOperation { mapView ->
      mapView.viewport.apply {
        transitionTo(
          targetState = makeOverviewViewportState(overviewViewportStateOptions),
          transition = makeDefaultViewportTransition(defaultTransitionOptions),
          completionListener
        )
      }
    }
  }

  /**
   * Transition to a new [FollowPuckViewportState].
   *
   * @param followPuckViewportStateOptions the target [FollowPuckViewportStateOptions] to transition to
   * @param defaultTransitionOptions the defaultTransitionOptions for the transition
   * @param completionListener the optional [CompletionListener] to observe the completion if the transition
   */
  @MapboxExperimental
  public fun transitionToFollowPuckState(
    followPuckViewportStateOptions: FollowPuckViewportStateOptions = FollowPuckViewportStateOptions.Builder()
      .build(),
    defaultTransitionOptions: DefaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder()
      .build(),
    completionListener: CompletionListener? = null
  ) {
    dispatchMapViewOperation { mapView ->
      mapView.viewport.apply {
        transitionTo(
          targetState = makeFollowPuckViewportState(followPuckViewportStateOptions),
          transition = makeDefaultViewportTransition(defaultTransitionOptions),
          completionListener
        )
      }
    }
  }

  /**
   * Sets [ViewportPlugin.status] to [ViewportStatus.Idle] synchronously.
   *
   * This cancels any active [ViewportState] or [ViewportTransition].
   */
  @MapboxExperimental
  public fun idle() {
    dispatchMapViewOperation { mapView ->
      mapView.viewport.idle()
    }
  }

  private fun dispatchMapViewOperation(operation: (MapView) -> Unit) {
    coroutineScope.launch {
      val block: suspend CoroutineScope.() -> Unit = {
        controller!!.apply(operation)
      }
      stateDispatcher.dispatch(block = block)
    }
  }

  /**
   * Companion object of [MapViewportState].
   */
  public companion object {
    /**
     * The default saver implementation for [MapViewportState]
     */
    public val Saver: Saver<MapViewportState, CameraState> = Saver(
      save = { it.cameraState },
      restore = { MapViewportState(it) }
    )
    private const val TAG = "MapViewportState"
    private val INIT_CAMERA_STATE = CameraState(
      /* center = */ Point.fromLngLat(0.0, 0.0),
      /* padding = */ EdgeInsets(0.0, 0.0, 0.0, 0.0),
      /* zoom = */ 0.0,
      /* bearing = */ 0.0,
      /* pitch = */ 0.0,
    )
  }
}

/**
 * A viewport state that contains one CameraOptions, this is introduced to replace the simple camera animations
 * using [MapViewportState] API.
 */
private class CameraViewportState(
  private val cameraOptions: CameraOptions,
  private val doSetCamera: (CameraOptions) -> Unit
) : ViewportState {
  override fun observeDataSource(viewportStateDataObserver: ViewportStateDataObserver): Cancelable {
    viewportStateDataObserver.onNewData(cameraOptions = cameraOptions)
    return Cancelable {
      // Dummy cancelable, as this state contains only one camera update
    }
  }

  override fun startUpdatingCamera() {
    // set the camera options immediately
    doSetCamera.invoke(cameraOptions)
  }

  override fun stopUpdatingCamera() {
    // no-op
  }

  override fun toString(): String {
    return "CameraViewportState(targetCamera=$cameraOptions)"
  }
}

/**
 * A generic viewport transition that can be wrapped with basic camera animation types, e.g. easeTo, flyTo
 */
private class GenericViewportTransition(private val doTransition: (CameraOptions, CompletionListener) -> Cancelable) :
  ViewportTransition {
  override fun run(to: ViewportState, completionListener: CompletionListener): Cancelable {
    lateinit var cancelable: Cancelable
    to.observeDataSource {
      cancelable = doTransition.invoke(it, completionListener)
      false
    }
    return cancelable
  }
}

/**
 * Utility function to perform an immediately setCameraOptions call through the animations plugin, so
 * it can interrupt other ongoing animations.
 */
private fun CameraAnimationsPlugin.easeToImmediately(cameraOptions: CameraOptions) {
  easeTo(
    cameraOptions,
    MapAnimationOptions.mapAnimationOptions { duration(0) }
  )
}

private class OnAnimationCompletedListener(val completionListener: List<CompletionListener?>) :
  AnimatorListener {
  private var isCancelled = false
  override fun onAnimationStart(animation: Animator) {
    // no-ops
  }

  override fun onAnimationEnd(animation: Animator) {
    // no-ops
    completionListener.forEach {
      it?.onComplete(!isCancelled)
    }
  }

  override fun onAnimationCancel(animation: Animator) {
    isCancelled = true
  }

  override fun onAnimationRepeat(animation: Animator) {
    // no-ops
  }
}