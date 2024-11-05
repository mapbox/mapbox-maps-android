package com.mapbox.maps.extension.compose.animation.viewport

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import androidx.annotation.UiThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.coroutine.cameraChangedEvents
import com.mapbox.maps.dsl.cameraOptions
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
import kotlinx.coroutines.channels.Channel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Create and [rememberSaveable] a [MapViewportState] using [MapViewportState.Saver].
 * [init] will be called when the [MapViewportState] is first created to configure its
 * initial state.
 */
@Composable
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
@Stable
public class MapViewportState(
  initialCameraState: CameraState = INIT_CAMERA_STATE
) {
  private val viewportActionChannel: Channel<(MapView) -> Unit> =
    Channel(capacity = Channel.Factory.UNLIMITED)

  private var mapView: MapView? = null

  private val _cameraState: MutableState<CameraState?> =
    mutableStateOf(if (initialCameraState == INIT_CAMERA_STATE) null else initialCameraState)
  private val _mapViewportStatus: MutableState<ViewportStatus?> = mutableStateOf(null)
  private val _mapViewportStatusChangedReason: MutableState<ViewportStatusChangeReason?> =
    mutableStateOf(null)

  @Composable
  private fun UpdateCameraState(mapView: MapView) {
    LaunchedEffect(Unit) {
      // If the user has provided a specific camera state then we need to enqueue it
      cameraState?.let {
        setCameraOptions(it.toCameraOptions())
      }
      mapView.mapboxMap.cameraChangedEvents.collect {
        _cameraState.value = it.cameraState
      }
    }
  }

  @Composable
  private fun UpdateViewportStatus(mapView: MapView) {
    DisposableEffect(Unit) {
      val viewportStatusObserver = ViewportStatusObserver { _, to, reason ->
        _mapViewportStatus.value = to
        _mapViewportStatusChangedReason.value = reason
      }
      mapView.viewport.addStatusObserver(viewportStatusObserver)
      onDispose {
        mapView.viewport.removeStatusObserver(viewportStatusObserver)
        _mapViewportStatus.value = null
        _mapViewportStatusChangedReason.value = null
      }
    }
  }

  @Composable
  private fun DrainActionQueue(mapView: MapView) {
    LaunchedEffect(Unit) {
      for (action in viewportActionChannel) {
        action(mapView)
      }
    }
  }

  @Composable
  internal fun BindToMap(mapView: MapView) {
    UpdateCameraState(mapView)
    UpdateViewportStatus(mapView)
    DrainActionQueue(mapView)
    DisposableEffect(Unit) {
      this@MapViewportState.mapView = mapView
      onDispose {
        mapView.viewport.idle()
        this@MapViewportState.mapView = null
      }
    }
  }

  /**
   * The current [CameraState] of the map or null if this [MapViewportState] is not yet associated
   * with a map.
   */
  public val cameraState: CameraState? by _cameraState

  /**
   * The reason why the [ViewportStatus] has been changed or null if this [MapViewportState] is not
   * yet associated with a map.
   */
  public val mapViewportStatusChangedReason: ViewportStatusChangeReason? by _mapViewportStatusChangedReason

  /**
   * The current [ViewportStatus] that represents the status of the viewport or null if this
   * [MapViewportState] is not yet associated with a map.
   *
   * It could be either a [ViewportStatus.State], [ViewportStatus.Transition] or
   * [ViewportStatus.Idle].
   */
  public val mapViewportStatus: ViewportStatus? by _mapViewportStatus

  /**
   * Move the camera instantaneously as specified by [cameraOptions]. Any camera animation in progress
   * will be cancelled.
   *
   * This method must be called from the map's UI thread.
   */
  @UiThread
  public fun setCameraOptions(cameraOptions: CameraOptions) {
    viewportActionChannel.trySend { mapView ->
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
  public fun setCameraOptions(block: CameraOptions.Builder.() -> Unit) {
    setCameraOptions(cameraOptions(block))
  }

  /**
   * Convenience method that returns the [CameraOptions] object for given parameters.
   *
   * @param coordinates The `coordinates` representing the bounds of the camera.
   * @param camera The [CameraOptions] which will be applied before calculating the camera for the coordinates. If any of the fields in [CameraOptions] are not provided then the current value from the map for that field will be used.
   * @param coordinatesPadding The amount of padding in pixels to add to the given `coordinates`.
   *                           Note: This padding is not applied to the map but to the coordinates provided. If you want to apply padding to the map use param `camera`.
   * @param maxZoom The maximum zoom level allowed in the returned camera options.
   * @param offset The center of the given bounds relative to map center in pixels.
   *
   * @return The [CameraOptions] object representing the provided parameters.
   */
  @UiThread
  public suspend fun cameraForCoordinates(
    coordinates: List<Point>,
    camera: CameraOptions = cameraOptions { },
    coordinatesPadding: EdgeInsets? = null,
    maxZoom: Double? = null,
    offset: ScreenCoordinate? = null,
  ): CameraOptions = suspendCoroutine { continuation ->
    viewportActionChannel.trySend { mapView ->
      mapView.mapboxMap.cameraForCoordinates(
        coordinates,
        camera,
        coordinatesPadding,
        maxZoom,
        offset,
        continuation::resume
      )
    }
  }

  /**
   * A utility function to get the default [CameraOptions] defined in the style or null if this
   * [MapViewportState] is not yet associated with a map.
   */
  public val styleDefaultCameraOptions: CameraOptions?
    get() = mapView?.mapboxMap?.style?.styleDefaultCamera

  /**
   * Ease the map camera to a given camera options.
   *
   * @param cameraOptions The camera options to ease to
   * @param animationOptions Transition options (animation duration, listeners etc)
   * @param completionListener the optional [CompletionListener] to observe the completion if the transition
   */
  public fun easeTo(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions? = null,
    completionListener: CompletionListener? = null
  ) {
    viewportActionChannel.trySend { mapView ->
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
  public fun flyTo(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions? = null,
    completionListener: CompletionListener? = null
  ) {
    viewportActionChannel.trySend { mapView ->
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
  public fun transitionToOverviewState(
    overviewViewportStateOptions: OverviewViewportStateOptions,
    defaultTransitionOptions: DefaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder()
      .build(),
    completionListener: CompletionListener? = null
  ) {
    viewportActionChannel.trySend { mapView ->
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
  public fun transitionToFollowPuckState(
    followPuckViewportStateOptions: FollowPuckViewportStateOptions = FollowPuckViewportStateOptions.Builder()
      .build(),
    defaultTransitionOptions: DefaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder()
      .build(),
    completionListener: CompletionListener? = null
  ) {
    viewportActionChannel.trySend { mapView ->
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
  public fun idle() {
    viewportActionChannel.trySend { mapView ->
      mapView.viewport.idle()
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