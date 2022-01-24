package com.mapbox.maps.testapp.utils

import android.os.Handler
import android.os.Looper
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.geojson.LineString
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportOptions
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.R

/**
 * Simulate a navigation route with pre-defined route as LineString.
 *
 * The simulator provides 3 camera tracking modes: OVERVIEW, TRACKING and NONE.
 * APIs are exposed so that user can play the navigation route scripts by switching the camera modes.
 */
class NavigationSimulator(
  private val mapView: MapView,
  routePoints: LineString,
  private val style: String = DEFAULT_STYLE
) :
  NavigationSimulatorCameraController {
  private val locationProvider by lazy { SimulateRouteLocationProvider(routePoints) }
  private val handler = Handler(Looper.getMainLooper())
  private val viewportPlugin = mapView.viewport
  private val followPuckViewportState =
    viewportPlugin.makeFollowPuckViewportState(FollowPuckViewportStateOptions.Builder().build())
  private val overviewViewportState = viewportPlugin.makeOverviewViewportState(
    OverviewViewportStateOptions.Builder().geometry(routePoints)
      .padding(EdgeInsets(100.0, 100.0, 100.0, 100.0)).build()
  )
  private var gesturesEnabled = true

  init {
    initMapboxMap()
    viewportPlugin.defaultTransition = viewportPlugin.makeDefaultViewportTransition(
      DefaultViewportTransitionOptions.Builder()
        .maxDurationMs(DEFAULT_VIEWPORT_TRANSITION_MAX_DURATION).build()
    )
  }

  private val onMapClickListener = OnMapClickListener {
    with(viewportPlugin.status) {
      if (this is ViewportStatus.State) {
        when (state) {
          followPuckViewportState -> viewportPlugin.transitionTo(overviewViewportState)
          else -> viewportPlugin.transitionTo(followPuckViewportState)
        }
      }
    }
    true
  }

  private fun initMapboxMap() {
    mapView.getMapboxMap().loadStyle(
      style(style) {
        +geoJsonSource(GEOJSON_SOURCE_ID) {
          geometry(locationProvider.route)
        }
        +lineLayer(ROUTE_LINE_LAYER_ID, GEOJSON_SOURCE_ID) {
          lineColor(mapView.context.resources.getColor(R.color.mapbox_blue))
          lineWidth(10.0)
          lineCap(LineCap.ROUND)
          lineJoin(LineJoin.ROUND)
        }
      }
    ) {
      mapView.recordFrameStats()
      initLocationComponent()
      viewportPlugin.transitionTo(overviewViewportState)
      enableGestures()
    }
  }

  /**
   * Play a pre-defined 20 seconds script to simulate a navigation route with switching between follow and overview mode.
   */
  fun playDefaultNavigationScripts(finishCallback: () -> Unit) {
    disableGestures()
    playCustomNavigationScripts(
      NavigationStep(DEFAULT_CAMERA_MODE_SWITCH_INTERVAL_MS) {
        setCameraTrackingMode(CameraFollowMode.FOLLOW)
      },
      NavigationStep(DEFAULT_CAMERA_MODE_SWITCH_INTERVAL_MS) {
        setCameraTrackingMode(CameraFollowMode.OVERVIEW)
      },
      NavigationStep(DEFAULT_CAMERA_MODE_SWITCH_INTERVAL_MS) {
        setCameraTrackingMode(CameraFollowMode.FOLLOW)
      },
      NavigationStep(DEFAULT_CAMERA_MODE_SWITCH_INTERVAL_MS) {
        setCameraTrackingMode(CameraFollowMode.OVERVIEW)
      }
    )
    // Invoke callback when the simulation finishes.
    handler.postDelayed(
      {
        finishCallback.invoke()
      },
      DEFAULT_SCRIPT_DURATION_MS
    )
  }

  /**
   * Play a repeated script to simulate a navigation route with switching between follow and overview mode.
   */
  fun playDefaultNavigationScriptsInLoop() {
    playDefaultNavigationScripts {
      playDefaultNavigationScriptsInLoop()
    }
  }

  /**
   * Play a customised script to simulate a navigation route with provided NavigationSteps.
   */
  fun playCustomNavigationScripts(
    vararg navigationStep: NavigationStep
  ) {
    var playOffset = 0L
    navigationStep.forEach {
      handler.postDelayed(
        {
          this.apply(it.action)
        },
        it.delay + playOffset
      )
      playOffset += it.delay
    }
  }

  override fun enableGestures() {
    mapView.gestures.apply {
      updateSettings {
        scrollEnabled = true
        pinchToZoomEnabled = true
        pitchEnabled = true
        rotateEnabled = true
        doubleTapToZoomInEnabled = true
        doubleTouchToZoomOutEnabled = true
      }
      viewportPlugin.options =
        ViewportOptions.Builder().transitionsToIdleUponUserInteraction(true).build()
      addOnMapClickListener(onMapClickListener)
    }
    gesturesEnabled = true
  }

  override fun setCameraTrackingMode(cameraMode: CameraFollowMode) {
    when (cameraMode) {
      CameraFollowMode.FOLLOW -> viewportPlugin.transitionTo(followPuckViewportState)
      CameraFollowMode.OVERVIEW -> viewportPlugin.transitionTo(overviewViewportState)
      CameraFollowMode.NONE -> viewportPlugin.idle()
    }
  }

  /**
   * Disable gestures in order to get stable benchmark result.
   */
  override fun disableGestures() {
    if (gesturesEnabled) {
      mapView.gestures.apply {
        viewportPlugin.options =
          ViewportOptions.Builder().transitionsToIdleUponUserInteraction(false).build()
        removeOnMapClickListener(onMapClickListener)
      }
      mapView.gestures.updateSettings {
        scrollEnabled = false
        pinchToZoomEnabled = false
        pitchEnabled = false
        rotateEnabled = false
        doubleTapToZoomInEnabled = false
        doubleTouchToZoomOutEnabled = false
      }
    }
    gesturesEnabled = false
  }

  private fun initLocationComponent() {
    val locationComponentPlugin = mapView.location
    locationComponentPlugin.updateSettings {
      this.enabled = true
      this.locationPuck = LocationPuck2D(
        bearingImage = AppCompatResources.getDrawable(
          mapView.context,
          R.drawable.mapbox_user_puck_icon,
        ),
        scaleExpression = interpolate {
          linear()
          zoom()
          stop {
            literal(0.0)
            literal(0.6)
          }
          stop {
            literal(20.0)
            literal(1.0)
          }
        }.toJson()
      )
    }
    locationComponentPlugin.setLocationProvider(locationProvider)
  }

  fun onDestroy() {
    handler.removeCallbacksAndMessages(null)
    mapView.gestures.apply {
      removeOnMapClickListener(onMapClickListener)
    }
  }

  /**
   * Defines camera follow modes.
   */
  enum class CameraFollowMode {
    FOLLOW,
    OVERVIEW,
    NONE
  }

  /**
   * Defines a navigation simulation step.
   */
  data class NavigationStep(
    /**
     * Delay(in ms) before the simulation action takes place.
     */
    val delay: Long = DEFAULT_CAMERA_MODE_SWITCH_INTERVAL_MS,
    /**
     * The action to be executed by the simulator.
     */
    val action: NavigationSimulatorCameraController.() -> Unit
  )

  companion object {
    private const val TAG = "NavigationSimulator"
    private const val DEFAULT_STYLE = Style.MAPBOX_STREETS
    private const val DEFAULT_CAMERA_MODE_SWITCH_INTERVAL_MS = 5000L
    private const val DEFAULT_SCRIPT_DURATION_MS = 20000L
    private const val DEFAULT_VIEWPORT_TRANSITION_MAX_DURATION = 2000L
    private const val GEOJSON_SOURCE_ID = "source_id"
    private const val ROUTE_LINE_LAYER_ID = "route_line_layer_id"
  }
}

/**
 * Camera Controller interface for navigation simulator.
 */
interface NavigationSimulatorCameraController {
  /**
   * Disable all gestures for the simulation.
   */
  fun disableGestures()

  /**
   * Enable gestures control for the simulation(enabled by default).
   *
   * When enabled, user interaction will set the camera tracking mode to NONE.
   * Single touch on the screen will toggle the camera tracking mode between TRACKING and OVERVIEW.
   */
  fun enableGestures()

  /**
   * Set the camera tracking mode to either TRACKING, OVERVIEW or NONE.
   */
  fun setCameraTrackingMode(cameraMode: NavigationSimulator.CameraFollowMode)
}