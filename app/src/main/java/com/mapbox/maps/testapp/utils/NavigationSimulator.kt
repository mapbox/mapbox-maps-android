package com.mapbox.maps.testapp.utils

import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.testapp.R
import com.mapbox.turf.TurfMeasurement

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
  private val locationProvider by lazy { FakeLocationProvider(routePoints) }
  private var cameraFollowMode = CameraFollowMode.OVERVIEW
  private val handler = Handler(Looper.getMainLooper())
  private var gesturesEnabled = true

  init {
    initMapboxMap()
  }

  private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
    if (cameraFollowMode == CameraFollowMode.FOLLOW) {
      mapView.getMapboxMap()
        .setCamera(CameraOptions.Builder().pitch(CAMERA_TRACKING_PITCH).bearing(it).build())
    }
  }

  private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
    if (cameraFollowMode == CameraFollowMode.FOLLOW) {
      mapView.getMapboxMap()
        .setCamera(CameraOptions.Builder().center(it).pitch(CAMERA_TRACKING_PITCH).build())
      mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }
  }

  private val onMoveListener = object : OnMoveListener {
    override fun onMoveBegin(detector: MoveGestureDetector) {
      if (cameraFollowMode != CameraFollowMode.NONE) {
        onCameraTrackingDismissed()
      }
    }

    override fun onMove(detector: MoveGestureDetector): Boolean {
      return false
    }

    override fun onMoveEnd(detector: MoveGestureDetector) {}
  }

  private val onMapClickListener = OnMapClickListener {
    when (cameraFollowMode) {
      CameraFollowMode.OVERVIEW -> {
        setCameraToFollowPuck()
      }
      CameraFollowMode.FOLLOW -> {
        setCameraToOverview(true)
      }
      CameraFollowMode.NONE -> {
        setCameraToFollowPuck()
      }
    }
    true
  }

  private fun setCameraToOverview(easeTo: Boolean = true) {
    cameraFollowMode = CameraFollowMode.OVERVIEW
    val camera = mapView.getMapboxMap().cameraForCoordinates(
      locationProvider.route.coordinates(),
      bearing = 0.0,
      pitch = 0.0,
      padding = EdgeInsets(100.0, 100.0, 100.0, 100.0)
    )
    if (easeTo) {
      mapView.camera.apply {
        easeTo(
          cameraOptions {
            pitch(camera.pitch!!)
            bearing(camera.bearing!!)
          },
          MapAnimationOptions.mapAnimationOptions {
            duration(EASE_TO_PUCK_DURATION_MS / 3)
          }
        )
        val zoom = createZoomAnimator(
          CameraAnimatorOptions.cameraAnimatorOptions(camera.zoom!!)
        ) {
          duration = EASE_TO_PUCK_DURATION_MS / 3
          interpolator = AccelerateDecelerateInterpolator()
        }
        handler.postDelayed(
          {
            playAnimatorsSequentially(zoom)
          },
          EASE_TO_PUCK_DURATION_MS / 3
        )
        handler.postDelayed(
          {
            easeTo(
              camera,
              MapAnimationOptions.mapAnimationOptions {
                duration(EASE_TO_PUCK_DURATION_MS / 3)
              }
            )
          },
          EASE_TO_PUCK_DURATION_MS * 2 / 3
        )
      }
    } else {
      mapView.getMapboxMap().setCamera(camera)
    }
  }

  private fun easeToPuck(callback: () -> Unit) {
    mapView.camera.apply {
      val zoom = createZoomAnimator(
        CameraAnimatorOptions.cameraAnimatorOptions(CAMERA_TRACKING_ZOOM)
      ) {
        duration = EASE_TO_PUCK_DURATION_MS / 3
        interpolator = AccelerateDecelerateInterpolator()
      }
      val center = createCenterAnimator(
        CameraAnimatorOptions.cameraAnimatorOptions(locationProvider.lastLocation)
      ) {
        duration = EASE_TO_PUCK_DURATION_MS / 3
        interpolator = AccelerateDecelerateInterpolator()
      }
      playAnimatorsSequentially(center, zoom)
      handler.postDelayed(
        {
          easeTo(
            cameraOptions {
              center(locationProvider.lastLocation)
              bearing(locationProvider.lastBearing)
              pitch(CAMERA_TRACKING_PITCH)
            },
            MapAnimationOptions.mapAnimationOptions {
              duration(EASE_TO_PUCK_DURATION_MS / 3)
            }
          )
        },
        EASE_TO_PUCK_DURATION_MS * 2 / 3
      )
    }

    handler.postDelayed(callback, EASE_TO_PUCK_DURATION_MS)
  }

  private fun setCameraToFollowPuck() {
    easeToPuck {
      cameraFollowMode = CameraFollowMode.FOLLOW
    }
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
      setCameraToOverview(false)
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
      addOnMoveListener(onMoveListener)
      addOnMapClickListener(onMapClickListener)
    }
    gesturesEnabled = true
  }

  override fun setCameraTrackingMode(cameraMode: CameraFollowMode) {
    when (cameraMode) {
      CameraFollowMode.FOLLOW -> setCameraToFollowPuck()
      CameraFollowMode.OVERVIEW -> setCameraToOverview(true)
      CameraFollowMode.NONE -> stopCameraTracking()
    }
  }

  /**
   * Disable gestures in order to get stable benchmark result.
   */
  override fun disableGestures() {
    if (gesturesEnabled) {
      mapView.gestures.apply {
        removeOnMoveListener(onMoveListener)
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
    locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
  }

  private fun onCameraTrackingDismissed() {
    Toast.makeText(mapView.context, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
    stopCameraTracking()
  }

  private fun stopCameraTracking() {
    cameraFollowMode = CameraFollowMode.NONE
  }

  fun onDestroy() {
    handler.removeCallbacksAndMessages(null)
    mapView.location.apply {
      removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
      removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    }
    mapView.gestures.apply {
      removeOnMoveListener(onMoveListener)
      removeOnMapClickListener(onMapClickListener)
    }
  }

  private inner class FakeLocationProvider(val route: LineString) : LocationProvider {
    private var locationConsumer: LocationConsumer? = null
    private val pointList = route.coordinates().toMutableList()
    private val iterator = pointList.iterator()
    var lastLocation: Point = iterator.next()
    var lastBearing = 0.0

    private fun emitFakeLocations() {
      handler.postDelayed(
        {
          if (iterator.hasNext()) {
            val point = iterator.next()
            val bearing = TurfMeasurement.bearing(lastLocation, point)
            lastLocation = point
            lastBearing = bearing
            iterator.remove()

            locationConsumer?.onLocationUpdated(point)
            locationConsumer?.onBearingUpdated(bearing)
          }
          emitFakeLocations()
        },
        LOCATION_UPDATE_INTERVAL_MS
      )
    }

    override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
      this.locationConsumer = locationConsumer
      emitFakeLocations()
    }

    override fun unRegisterLocationConsumer(locationConsumer: LocationConsumer) {
      this.locationConsumer = null
      handler.removeCallbacksAndMessages(null)
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
    private const val EASE_TO_PUCK_DURATION_MS = 2000L
    private const val LOCATION_UPDATE_INTERVAL_MS = 1000L
    private const val CAMERA_TRACKING_PITCH = 40.0
    private const val CAMERA_TRACKING_ZOOM = 16.5
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