package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.*
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
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.turf.TurfMeasurement

/**
 * Simulate a navigation route with pre-defined route.
 */
class SimulateNavigationRouteActivity : AppCompatActivity() {

  private lateinit var mapboxDirectionsClient: MapboxDirections
  private lateinit var mapView: MapView
  private lateinit var routePoints: LineString
  private val fpsRecords = mutableListOf<Double>()
  private val locationProvider by lazy { FakeLocationProvider(routePoints) }
  private var cameraFollowMode = CameraFollowMode.OVERVIEW
  private val handler = Handler(Looper.getMainLooper())

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

  private val onMapClickListener = OnMapClickListener { point ->
    when (cameraFollowMode) {
      CameraFollowMode.OVERVIEW -> {
        setCameraToFollowPuck()
      }
      CameraFollowMode.FOLLOW -> {
        setCameraToOverview()
      }
      CameraFollowMode.NONE -> {
        setCameraToFollowPuck()
      }
    }
    true
  }

  private fun setCameraToOverview(easeTo: Boolean = true) {
    cameraFollowMode = CameraFollowMode.OVERVIEW
    mapView.getMapboxMap().apply {
      val camera = cameraForCoordinates(
        locationProvider.route.coordinates(),
        bearing = 0.0,
        pitch = 0.0,
        padding = EdgeInsets(100.0, 100.0, 100.0, 100.0)
      )
      if (easeTo) {
        easeTo(
          camera,
          MapAnimationOptions.mapAnimationOptions {
            duration(EASE_TO_PUCK_DURATION)
          }
        )
      } else {
        setCamera(camera)
      }
    }
  }

  private fun easeToPuck(callback: () -> Unit) {
    mapView.camera.apply {
      val zoom = createZoomAnimator(
        cameraAnimatorOptions(CAMERA_TRACKING_ZOOM)
      ) {
        duration = EASE_TO_PUCK_DURATION / 3
        interpolator = AccelerateDecelerateInterpolator()
      }
      val center = createCenterAnimator(
        cameraAnimatorOptions(locationProvider.lastLocation)
      ) {
        duration = EASE_TO_PUCK_DURATION / 3
        interpolator = AccelerateDecelerateInterpolator()
      }
      playAnimatorsSequentially(center, zoom)
      handler.postDelayed({
        easeTo(
          cameraOptions {
            center(locationProvider.lastLocation)
            bearing(locationProvider.lastBearing)
            pitch(CAMERA_TRACKING_PITCH)
          },
          MapAnimationOptions.mapAnimationOptions {
            duration(EASE_TO_PUCK_DURATION / 3)
          }
        )
      }, EASE_TO_PUCK_DURATION * 2 / 3)
    }

    handler.postDelayed(callback, EASE_TO_PUCK_DURATION)
  }

  private fun setCameraToFollowPuck() {
    easeToPuck {
      cameraFollowMode = CameraFollowMode.FOLLOW
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)
    routePoints = LineString.fromPolyline(
      DirectionsResponse.fromJson(
        AnnotationUtils.loadStringFromAssets(
          this,
          NAVIGATION_ROUTE_JSON_NAME
        )
      ).routes()[0].geometry()!!,
      Constants.PRECISION_6
    )
    initFpsCounter()
    initMapboxMap()
  }

  private fun initFpsCounter() {
    mapView.setOnFpsChangedListener {
      fpsRecords.add(it)
    }
  }

  private fun initMapboxMap() {
    mapView.getMapboxMap().loadStyle(
      style(STYLE) {
        +geoJsonSource(GEOJSON_SOURCE_ID) {
          geometry(routePoints)
        }
        +lineLayer(ROUTE_LINE_LAYER_ID, GEOJSON_SOURCE_ID) {
          lineColor(resources.getColor(R.color.mapbox_blue))
          lineWidth(10.0)
          lineCap(LineCap.ROUND)
          lineJoin(LineJoin.ROUND)
        }
      }
    ) {
      initLocationComponent()
      setupGestures()
      setCameraToOverview(false)
      handler.postDelayed(
        {
          setCameraToFollowPuck()
        },
        INITIAL_OVERVIEW_TIME
      )
      handler.postDelayed(
        {
          Toast.makeText(
            this,
            "FPS stats: Average: ${fpsRecords.average()}, Max: ${fpsRecords.maxOrNull()}, Min: ${fpsRecords.minOrNull()} ",
            Toast.LENGTH_LONG
          ).show()
          finish()
        },
        ACTIVITY_RUN_TIME
      )
    }
  }

  private fun setupGestures() {
    // Disable all the gestures
    mapView.gestures.apply {
//      addOnMoveListener(onMoveListener)
//      addOnMapClickListener(onMapClickListener)
      updateSettings {
        scrollEnabled = false
        pinchToZoomEnabled = false
        pitchEnabled = false
        rotateEnabled = false
        doubleTapToZoomInEnabled = false
        doubleTouchToZoomOutEnabled = false
      }
    }
  }

  private fun initLocationComponent() {
    val locationComponentPlugin = mapView.location
    locationComponentPlugin.updateSettings {
      this.enabled = true
      this.locationPuck = LocationPuck2D(
        bearingImage = AppCompatResources.getDrawable(
          this@SimulateNavigationRouteActivity,
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
    Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
    cameraFollowMode = CameraFollowMode.NONE
  }

  override fun onDestroy() {
    super.onDestroy()
    if (this::mapboxDirectionsClient.isInitialized) {
      mapboxDirectionsClient.cancelCall()
    }
    mapView.location
      .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    mapView.location
      .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    mapView.gestures.removeOnMoveListener(onMoveListener)
  }

  private enum class CameraFollowMode {
    FOLLOW,
    OVERVIEW,
    NONE
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
        LOCATION_UPDATE_INTERVAL
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

  companion object {
    private const val STYLE = Style.MAPBOX_STREETS
    private const val INITIAL_OVERVIEW_TIME = 3000L
    private const val EASE_TO_PUCK_DURATION = 2000L
    private const val LOCATION_UPDATE_INTERVAL = 1000L
    private const val ACTIVITY_RUN_TIME = 20000L
    private const val CAMERA_TRACKING_PITCH = 40.0
    private const val CAMERA_TRACKING_ZOOM = 16.5
    private const val GEOJSON_SOURCE_ID = "source_id"
    private const val ROUTE_LINE_LAYER_ID = "route_line_layer_id"
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"
  }
}