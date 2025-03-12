package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.NavigationSimulator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Simulate a navigation route with pre-defined route (from LA to San Francisco) with location puck,
 * route line and camera tracking. The activity has disabled gestures and will run for 20 seconds.
 * At the end of the activity, there will be a toast showing the average fps, overtime frames
 */
class SimulateNavigationRouteActivity : AppCompatActivity() {

  private lateinit var navigationSimulator: NavigationSimulator

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
      delay(SIMULATION_DURATION)
      finish()
      // Uncomment below to play the default navigation script in loop.
      // navigationSimulator.playDefaultNavigationScriptsInLoop()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    if (this::navigationSimulator.isInitialized) {
      navigationSimulator.onDestroy()
    }
  }

  companion object {
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"
    private const val INITIAL_OVERVIEW_DELAY_MS = 3000L
    private const val FIRST_FOLLOW_MODE_DELAY_MS = 8000L
    private const val SECOND_OVERVIEW_MODE_DELAY_MS = 4000L
    private const val SIMULATION_DURATION = 20_000L
  }
}