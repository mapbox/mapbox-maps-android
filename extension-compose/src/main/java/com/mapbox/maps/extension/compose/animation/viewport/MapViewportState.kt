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
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportStatusChangeReason
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportStateDataObserver
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.toCameraOptions

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
 * @param cameraState the initial camera position
 */
@MapboxExperimental
public class MapViewportState(
  cameraState: CameraState = INIT_CAMERA_STATE
) {
  private var controller: MapView? = null

  // cache the last pending operation when the operation is ran before map is attached.
  private var lastPendingOperation: ((MapView) -> Unit)? = null

  internal fun setMap(mapView: MapView?) {
    this.controller = mapView
    if (cameraState !== INIT_CAMERA_STATE) {
      setCameraOptions(cameraState.toCameraOptions())
    }
    // invoke the last pending operation if map is valid
    // or clear the last pending operation if the map is null
    mapView?.let {
      lastPendingOperation?.invoke(it)
    } ?: kotlin.run {
      lastPendingOperation = null
    }
  }

  /**
   * The current [CameraState] of the map.
   */
  @MapboxExperimental
  public var cameraState: CameraState by mutableStateOf(cameraState)
    internal set

  /**
   * The reason why the [ViewportStatus] has been changed.
   */
  @MapboxExperimental
  public var mapViewportStatusChangedReason: ViewportStatusChangeReason by mutableStateOf(
    ViewportStatusChangeReason.IDLE_REQUESTED
  )
    internal set

  /**
   * The current [ViewportStatus] that represents the status of the viewport.
   *
   * It could be either a [ViewportStatus.State], [ViewportStatus.Transition] or [ViewportStatus.Idle].
   */
  @MapboxExperimental
  public var mapViewportStatus: ViewportStatus by mutableStateOf(ViewportStatus.Idle)
    internal set

  /**
   * Move the camera instantaneously as specified by [cameraOptions]. Any camera animation in progress
   * will be cancelled.
   *
   * This method must be called from the map's UI thread.
   */
  @UiThread
  @MapboxExperimental
  public fun setCameraOptions(cameraOptions: CameraOptions) {
    tryInvokingOperation { mapView ->
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
   * A utility function to get the default [CameraOptions] defined in the style.
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
    tryInvokingOperation { mapView ->
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
    tryInvokingOperation { mapView ->
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
    tryInvokingOperation { mapView ->
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
    followPuckViewportStateOptions: FollowPuckViewportStateOptions = FollowPuckViewportStateOptions.Builder().build(),
    defaultTransitionOptions: DefaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder()
      .build(),
    completionListener: CompletionListener? = null
  ) {
    tryInvokingOperation { mapView ->
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
    tryInvokingOperation { mapView ->
      mapView.viewport.idle()
    }
  }

  private fun tryInvokingOperation(operation: (MapView) -> Unit) {
    with(controller) {
      if (this == null) {
        logW(
          TAG,
          "tryInvokingOperation invoked when Map is not set, added the operation as pending operation."
        )
        lastPendingOperation = operation
      } else {
        operation.invoke(this)
      }
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