package com.mapbox.maps.testapp.utils

import android.os.Handler
import android.os.Looper
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportOptions
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.R
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import com.mapbox.turf.TurfMisc

/**
 * Simulate a navigation route with pre-defined route as LineString.
 *
 * The simulator provides 3 camera tracking modes: OVERVIEW, TRACKING and NONE.
 * APIs are exposed so that user can play the navigation route scripts by switching the camera modes.
 */
class NavigationSimulator(
  private val mapView: MapView,
  private val routePoints: LineString,
  private val style: String = DEFAULT_STYLE
) :
  NavigationSimulatorCameraController, OnIndicatorPositionChangedListener {
  private val locationProvider by lazy {
    SimulateRouteLocationProvider(route = routePoints)
  }
  private lateinit var routeLayer: LineLayer
  private lateinit var casingLayer: LineLayer
  private val handler = Handler(Looper.getMainLooper())
  private val totalRouteLength = TurfMeasurement.length(routePoints, TurfConstants.UNIT_CENTIMETERS)
  private val routeStartPoint = routePoints.coordinates().first()
  private val viewportPlugin = mapView.viewport
  private val followPuckViewportState =
    viewportPlugin.makeFollowPuckViewportState(FollowPuckViewportStateOptions.Builder().build())
  private val overviewViewportState = viewportPlugin.makeOverviewViewportState(
    OverviewViewportStateOptions.Builder().geometry(routePoints)
      .padding(EdgeInsets(100.0, 100.0, 100.0, 100.0)).build()
  )
  private var gesturesEnabled = true

  init {
    viewportPlugin.defaultTransition = viewportPlugin.makeDefaultViewportTransition(
      DefaultViewportTransitionOptions.Builder()
        .maxDurationMs(DEFAULT_VIEWPORT_TRANSITION_MAX_DURATION).build()
    )
    initMapboxMap()
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
    routeLayer = lineLayer(ROUTE_LINE_LAYER_ID, GEOJSON_SOURCE_ID) {
      lineWidth(
        interpolate {
          exponential {
            literal(1.5)
          }
          zoom()
          stop {
            literal(4.0)
            product(3.0, 1.0)
          }
          stop {
            literal(10.0)
            product(4.0, 1.0)
          }
          stop {
            literal(13.0)
            product(6.0, 1.0)
          }
          stop {
            literal(16.0)
            product(10.0, 1.0)
          }
          stop {
            literal(19.0)
            product(14.0, 1.0)
          }
          stop {
            literal(22.0)
            product(18.0, 1.0)
          }
        }
      )
      lineCap(LineCap.ROUND)
      lineJoin(LineJoin.ROUND)
      lineGradient(
        interpolate {
          linear()
          lineProgress()
          stop {
            literal(0)
            rgba(6.0, 1.0, 255.0, 1.0)
          }
          stop {
            literal(0.1)
            rgba(59.0, 118.0, 227.0, 1.0)
          }
          stop {
            literal(0.3)
            rgba(7.0, 238.0, 251.0, 1.0)
          }
          stop {
            literal(0.5)
            rgba(0.0, 255.0, 42.0, 1.0)
          }
          stop {
            literal(0.7)
            rgba(255.0, 252.0, 0.0, 1.0)
          }
          stop {
            literal(1.0)
            rgba(255.0, 30.0, 0.0, 1.0)
          }
        }
      )
    }
    casingLayer = lineLayer(ROUTE_CASING_LAYER_ID, GEOJSON_SOURCE_ID) {
      lineWidth(
        interpolate {
          exponential {
            literal(1.5)
          }
          zoom()
          stop {
            literal(10)
            product(7.0, 1.0)
          }
          stop {
            literal(14.0)
            product(10.5, 1.0)
          }
          stop {
            literal(16.5)
            product(15.5, 1.0)
          }
          stop {
            literal(19.0)
            product(24.0, 1.0)
          }
          stop {
            literal(22.0)
            product(29.0, 1.0)
          }
        }
      )
      lineCap(LineCap.ROUND)
      lineJoin(LineJoin.ROUND)
      lineGradient(
        interpolate {
          linear()
          lineProgress()
          stop {
            literal(0)
            rgba(47.0, 122.0, 198.0, 1.0)
          }
          stop {
            literal(1.0)
            rgba(47.0, 122.0, 198.0, 1.0)
          }
        }
      )
    }
    mapView.getMapboxMap().loadStyle(
      style(style) {
        +geoJsonSource(GEOJSON_SOURCE_ID) {
          geometry(locationProvider.route)
          lineMetrics(true)
        }
        +casingLayer
        +routeLayer
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
    locationComponentPlugin.addOnIndicatorPositionChangedListener(this)
  }

  override fun onIndicatorPositionChanged(point: Point) {
    val progress = TurfMeasurement.length(
      TurfMisc.lineSlice(routeStartPoint, point, routePoints),
      TurfConstants.UNIT_CENTIMETERS
    ) / totalRouteLength
    routeLayer.lineTrimOffset(listOf(0.0, progress))
    casingLayer.lineTrimOffset(listOf(0.0, progress))
  }

  fun onDestroy() {
    handler.removeCallbacksAndMessages(null)
    mapView.location.removeOnIndicatorPositionChangedListener(this)
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
    private const val ROUTE_CASING_LAYER_ID = "route_casing_layer_id"
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