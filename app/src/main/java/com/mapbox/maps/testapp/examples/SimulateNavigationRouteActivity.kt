package com.mapbox.maps.testapp.examples

import android.os.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.*
import com.mapbox.maps.*
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
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.StorageUtils
import com.mapbox.turf.TurfMeasurement

/**
 * Simulate a navigation route with pre-defined route (from LA to San Francisco) with location puck,
 * route line and camera tracking. The activity has disabled gestures and will run for 20 seconds.
 * At the end of the activity, there will be a toast showing the average fps, overtime frames
 */
class SimulateNavigationRouteActivity : AppCompatActivity() {

  private lateinit var mapView: MapView
  private lateinit var routePoints: LineString
  private var renderFrameFinishCount = 0
  private var overtimeFrameCount = 0
  private val renderFrameIntervalsMs = mutableListOf<Float>()
  private val frameReport = mutableListOf<JsonObject>()
  private val locationProvider by lazy { FakeLocationProvider(routePoints) }
  private var cameraFollowMode = CameraFollowMode.OVERVIEW
  private val handler = Handler(Looper.getMainLooper())
  private var startBenchmarkTime = 0L
  private var lastRenderedFrameTime = 0L

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
    val camera = mapView.getMapboxMap().cameraForCoordinates(
      locationProvider.route.coordinates(),
      bearing = 0.0,
      pitch = 0.0,
      padding = EdgeInsets(100.0, 100.0, 100.0, 100.0)
    )
    if (easeTo) {
      mapView.camera.apply {
        val zoom = createZoomAnimator(
          cameraAnimatorOptions(camera.zoom!!)
        ) {
          duration = EASE_TO_PUCK_DURATION / 3
          interpolator = AccelerateDecelerateInterpolator()
        }
        val bearing = createBearingAnimator(
          cameraAnimatorOptions(camera.bearing!!)
        ) {
          duration = EASE_TO_PUCK_DURATION / 3
          interpolator = AccelerateDecelerateInterpolator()
        }
        playAnimatorsSequentially(bearing, zoom)
        handler.postDelayed(
          {
            easeTo(
              camera,
              MapAnimationOptions.mapAnimationOptions {
                duration(EASE_TO_PUCK_DURATION / 3)
              }
            )
          },
          EASE_TO_PUCK_DURATION * 2 / 3
        )
      }
    } else {
      mapView.getMapboxMap().setCamera(camera)
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
      handler.postDelayed(
        {
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
        },
        EASE_TO_PUCK_DURATION * 2 / 3
      )
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
    initMapboxMap()
  }

  private val renderFrameObserver = Observer { event ->
    if (event.type == MapEvents.RENDER_FRAME_FINISHED) {
      renderFrameFinishCount++
      val now = SystemClock.elapsedRealtimeNanos()
      if (lastRenderedFrameTime != 0L) {
        renderFrameIntervalsMs.add((now - lastRenderedFrameTime) * 1e-6f)
        frameReport.add(
          JsonObject().apply {
            addProperty("time_from_start", (now - startBenchmarkTime) * 1e-6f)
            addProperty("interval_from_last_frame", (now - lastRenderedFrameTime)  * 1e-6f)
          }
        )
        if (now - lastRenderedFrameTime > 1000_000L * TARGET_FRAME_TIME_MS) {
          overtimeFrameCount++
        }
      }
      lastRenderedFrameTime = now
    }
  }

  private fun initFpsCounter() {
    mapView.getMapboxMap().subscribe(
      renderFrameObserver,
      listOf(MapEvents.RENDER_FRAME_FINISHED)
    )
    startBenchmarkTime = SystemClock.elapsedRealtimeNanos()
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
      initFpsCounter()
      initLocationComponent()
      setupGestures()
      setCameraToOverview(false)
      playScripts()
      handler.postDelayed(
        {
          val endBenchmarkTime = SystemClock.elapsedRealtimeNanos()
          Toast.makeText(
            this,
            """
              Average FPS: ${(renderFrameFinishCount * 1e9f / (endBenchmarkTime - startBenchmarkTime)).format()}
              Over time frames: $overtimeFrameCount
              Over time frames ratio: ${(overtimeFrameCount * 100f / renderFrameFinishCount).format()}%
              Max frame interval: ${renderFrameIntervalsMs.maxOrNull().format()}ms
              Min frame interval: ${renderFrameIntervalsMs.minOrNull().format()}ms
              Logs have been saved to $filesDir/logs/frame_log.json.
            """.trimIndent(),
            Toast.LENGTH_LONG
          ).show()
          StorageUtils(this).writeToFile("frame_log.json", Gson().toJson(frameReport))
          finish()
        },
        ACTIVITY_RUN_TIME
      )
    }
  }

  private fun playScripts() {
    handler.postDelayed(
      {
        setCameraToFollowPuck()
      },
      INITIAL_OVERVIEW_TIME
    )
    handler.postDelayed(
      {
        setCameraToOverview(true)
      },
      INITIAL_OVERVIEW_TIME + CAMERA_ACTION_INTERVAL * 2
    )
    handler.postDelayed(
      {
        setCameraToFollowPuck()
      },
      INITIAL_OVERVIEW_TIME + CAMERA_ACTION_INTERVAL * 3
    )
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
    mapView.location
      .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    mapView.location
      .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    mapView.gestures.removeOnMoveListener(onMoveListener)
    mapView.getMapboxMap().unsubscribe(renderFrameObserver)
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

  private fun Float?.format(): String {
    return "%.2f".format(this)
  }

  companion object {
    private const val TAG = "NavigationRouteActivity"
    private const val STYLE = Style.MAPBOX_STREETS
    private const val TARGET_FRAME_TIME_MS = 1000f / 30f
    private const val INITIAL_OVERVIEW_TIME = 3000L
    private const val CAMERA_ACTION_INTERVAL = 4000L
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