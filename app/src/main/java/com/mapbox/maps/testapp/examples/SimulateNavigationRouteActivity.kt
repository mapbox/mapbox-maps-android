package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.maps.MapView
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.NavigationSimulator

/**
 * Simulate a navigation route with pre-defined route (from LA to San Francisco) with location puck,
 * route line and camera tracking. The activity has disabled gestures and will run for 20 seconds.
 * At the end of the activity, there will be a toast showing the average fps, overtime frames
 */
class SimulateNavigationRouteActivity : AppCompatActivity() {

  private lateinit var navigationSimulator: NavigationSimulator
  private val handler = Handler(Looper.getMainLooper())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    val routePoints = LineString.fromPolyline(
      DirectionsResponse.fromJson(
        AnnotationUtils.loadStringFromAssets(
          this,
          NAVIGATION_ROUTE_JSON_NAME
        )
      ).routes()[0].geometry()!!,
      Constants.PRECISION_6
    )
    navigationSimulator = NavigationSimulator(mapView, routePoints)
    navigationSimulator.apply {
      disableGestures()
      playCustomNavigationScripts(
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
    }
    handler.postDelayed(
      {
        finish()
      },
      SIMULATION_DURATION
    )
    // Uncomment below to play the default navigation script in loop.
    // navigationSimulator.playDefaultNavigationScriptsInLoop()
  }

  override fun onDestroy() {
    super.onDestroy()
    if (this::navigationSimulator.isInitialized) {
      navigationSimulator.onDestroy()
    }
  }

  companion object {
    private const val TAG = "NavigationRouteActivity"
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"
    private const val INITIAL_OVERVIEW_DELAY_MS = 3000L
    private const val FIRST_FOLLOW_MODE_DELAY_MS = 8000L
    private const val SECOND_OVERVIEW_MODE_DELAY_MS = 4000L
    private const val SIMULATION_DURATION = 20_000L
  }
}