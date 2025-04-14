package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.logI
import com.mapbox.maps.renderer.RenderThreadStats
import com.mapbox.maps.renderer.RenderThreadStatsRecorder
import com.mapbox.maps.testapp.examples.SimulateNavigationRouteActivity.Companion.SIMULATION_DURATION
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.NavigationSimulator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Simulate a navigation route with pre-defined route (from LA to San Francisco) with location puck,
 * route line and camera tracking. Gestures are disabled.
 * The simulation will run [repetitions] times, and the network stack will be disabled after the 5th.
 * On each iteration, the simulation will run for [SIMULATION_DURATION] milliseconds and some frame
 * stats will be logged.
 */
@OptIn(MapboxExperimental::class)
class SimulateNavigationRouteActivity : AppCompatActivity() {

  private val repetitions = 3

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapView.mapboxMap.setCamera(
      cameraOptions {
        center(Point.fromLngLat(-118.289795, 34.03084))
        bearing(0.0)
        pitch(0.0)
        zoom(9.0)
      }
    )
    // Prevent screen from sleeping while running the simulation
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    lifecycleScope.launch {
      val routePoints = withContext(Dispatchers.Default) {
        LineString.fromPolyline(
          DirectionsResponse.fromJson(
            AnnotationUtils.loadStringFromAssets(
              this@SimulateNavigationRouteActivity,
              NAVIGATION_ROUTE_JSON_NAME
            )
          ).routes()[0].geometry()!!,
          Constants.PRECISION_6
        )
      }
      repeat(repetitions) {
        val renderThreadStatsRecorder = RenderThreadStatsRecorder()
        mapView.setRenderThreadStatsRecorder(renderThreadStatsRecorder)
        val navigationSimulator = NavigationSimulator(mapView, routePoints)
        navigationSimulator.disableGestures()
        renderThreadStatsRecorder.start()
        navigationSimulator.playCustomNavigationScripts(
          NavigationSimulator.NavigationStep(INITIAL_OVERVIEW_DELAY_MS) {
            setCameraTrackingMode(NavigationSimulator.CameraFollowMode.FOLLOW)
          },
          NavigationSimulator.NavigationStep(FIRST_FOLLOW_MODE_DELAY_MS) {
            setCameraTrackingMode(NavigationSimulator.CameraFollowMode.OVERVIEW)
          },
          NavigationSimulator.NavigationStep(SECOND_OVERVIEW_MODE_DELAY_MS) {
            setCameraTrackingMode(NavigationSimulator.CameraFollowMode.FOLLOW)
          }
        )
        delay(SIMULATION_DURATION)
        logStats(renderThreadStatsRecorder.end())
        navigationSimulator.onDestroy()
      }
      finish()
    }
  }

  private fun logStats(renderThreadStats: RenderThreadStats) = with(renderThreadStats) {
    logI(TAG, "RenderThread Stats:")
    logI(TAG, "\ttotal time: $totalTime ms")
    logI(TAG, "\ttotal frames (rendered + skipped): $totalFrames")
    logI(TAG, "\taverage FPS: ${(totalFrames / (totalTime / 1000F)).format()}")
    logI(TAG, "\tskipped frames: $totalDroppedFrames")
    logI(TAG, "\t50 percentile: ${percentile50.format()} ms")
    logI(TAG, "\t90 percentile: ${percentile90.format()} ms")
    logI(TAG, "\t95 percentile: ${percentile95.format()} ms")
    logI(TAG, "\t99 percentile: ${percentile99.format()} ms")
  }

  private fun Number?.format() = "%.3f".format(this)

  companion object {
    private const val TAG = "SimulateNavigationRoute"
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"
    private const val INITIAL_OVERVIEW_DELAY_MS = 3000L
    private const val FIRST_FOLLOW_MODE_DELAY_MS = 8000L
    private const val SECOND_OVERVIEW_MODE_DELAY_MS = 4000L
    private const val SIMULATION_DURATION = 20_000L
  }
}