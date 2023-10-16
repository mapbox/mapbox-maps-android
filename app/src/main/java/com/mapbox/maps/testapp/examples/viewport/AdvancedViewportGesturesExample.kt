package com.mapbox.maps.testapp.examples.viewport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.gestures.RotateGestureDetector
import com.mapbox.android.gestures.ShoveGestureDetector
import com.mapbox.android.gestures.StandardScaleGestureDetector
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnRotateListener
import com.mapbox.maps.plugin.gestures.OnScaleListener
import com.mapbox.maps.plugin.gestures.OnShoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.ViewportStatusObserver
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportOptions
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.SimulateRouteLocationProvider

/**
 * Showcase the use age of viewport plugin with advanced gestures customisation.
 *
 * Touch the map to toggle the following and overview mode.
 *
 * @see [User location guide](https://docs.mapbox.com/android/maps/guides/user-location/#location-tracking)
 */
class AdvancedViewportGesturesExample : AppCompatActivity() {
  private lateinit var mapView: MapView
  private lateinit var routePoints: LineString
  private val followPuckViewportState: FollowPuckViewportState by lazy {
    mapView.viewport.makeFollowPuckViewportState(
      FollowPuckViewportStateOptions.Builder()
        .bearing(FollowPuckViewportStateBearing.Constant(0.0))
        .padding(EdgeInsets(200.0 * resources.displayMetrics.density, 0.0, 0.0, 0.0))
        .build()
    )
  }
  private val overviewViewportState: OverviewViewportState by lazy {
    mapView.viewport.makeOverviewViewportState(
      OverviewViewportStateOptions.Builder()
        .geometry(routePoints)
        .padding(EdgeInsets(100.0, 100.0, 100.0, 100.0))
        .build()
    )
  }
  private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
    mapView.gestures.focalPoint = mapView.mapboxMap.pixelForCoordinate(it)
  }

  private val viewportStatusObserver = ViewportStatusObserver { from, to, _ ->
    // Clean up the gestures settings when current viewport is moving away from followPuckViewportState
    if (from == ViewportStatus.State(followPuckViewportState)) {
      clearAdvancedGesturesForFollowPuckViewportState()
    }
    // Set up the gestures settings when current viewport has entered the followPuckViewportState
    if (to == ViewportStatus.State(followPuckViewportState)) {
      setupAdvancedGesturesForFollowPuckViewportState()
    }
  }

  private val onScaleListener = object : OnScaleListener {
    override fun onScaleBegin(detector: StandardScaleGestureDetector) {
      // set the default zoom that will be generated for camera following frames to null,
      // thus allows gestures to adjust the camera zoom using zoom gestures.
      followPuckViewportState.apply {
        options = options.toBuilder().zoom(null).build()
      }
    }

    override fun onScale(detector: StandardScaleGestureDetector) {
      // no-ops
    }

    override fun onScaleEnd(detector: StandardScaleGestureDetector) {
      // set the default zoom to current value on scale gesture end
      followPuckViewportState.apply {
        options = options.toBuilder().zoom(mapView.mapboxMap.cameraState.zoom).build()
      }
    }
  }

  private val onRotateListener = object : OnRotateListener {
    override fun onRotateBegin(detector: RotateGestureDetector) {
      // set the default bearing that will be generated for camera following frames to null,
      // thus allows gestures to adjust the camera bearing using rotate gestures.
      followPuckViewportState.apply {
        options = options.toBuilder().bearing(null).build()
      }
    }

    override fun onRotate(detector: RotateGestureDetector) {
      // no-ops
    }

    override fun onRotateEnd(detector: RotateGestureDetector) {
      // set the default bearing to current value on rotate gesture end
      followPuckViewportState.apply {
        options = options.toBuilder()
          .bearing(FollowPuckViewportStateBearing.Constant(mapView.mapboxMap.cameraState.bearing))
          .build()
      }
    }
  }

  private val onShoveListener = object : OnShoveListener {
    override fun onShoveBegin(detector: ShoveGestureDetector) {
      // set the default pitch that will be generated for camera following frames to null,
      // thus allows gestures to adjust the camera pitch using shove gestures.
      followPuckViewportState.apply {
        options = options.toBuilder()
          .pitch(null)
          .build()
      }
    }

    override fun onShove(detector: ShoveGestureDetector) {
      // no-ops
    }

    override fun onShoveEnd(detector: ShoveGestureDetector) {
      // set the default pitch to current value on shove gesture end
      followPuckViewportState.apply {
        options = options.toBuilder()
          .pitch(mapView.mapboxMap.cameraState.pitch)
          .build()
      }
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
    mapView.mapboxMap.loadStyle(
      // Show the route line on the map
      style(Style.TRAFFIC_DAY) {
        +geoJsonSource(GEOJSON_SOURCE_ID) {
          geometry(routePoints)
        }
        +lineLayer(
          ROUTE_LINE_LAYER_ID,
          GEOJSON_SOURCE_ID
        ) {
          lineColor(mapView.context.getColor(R.color.mapbox_blue))
          lineWidth(10.0)
          lineCap(LineCap.ROUND)
          lineJoin(LineJoin.ROUND)
        }
      }
    ) {
      // Prepare the location component with puck styling and a simulated route.
      setupLocationComponent(routePoints)

      // Observe the viewport status to setup/clean up the gestures settings
      // specifically for followPuckViewportState
      mapView.viewport.addStatusObserver(viewportStatusObserver)

      // Switch ViewportStates by single tapping on the map.
      mapView.gestures.addOnMapClickListener {
        mapView.viewport.transitionTo(
          when (mapView.viewport.status.getCurrentOrNextState()) {
            followPuckViewportState -> overviewViewportState
            else -> followPuckViewportState
          }
        )
        false
      }
      mapView.viewport.transitionTo(overviewViewportState)
    }
  }

  private fun setupAdvancedGesturesForFollowPuckViewportState() {
    // Disable viewport's transitionsToIdleUponUserInteraction so that we can customise the gestures
    // within the state.
    mapView.viewport.options =
      ViewportOptions.Builder().transitionsToIdleUponUserInteraction(false).build()

    // Advanced gestures handling

    // Disable scroll gesture
    mapView.gestures.scrollEnabled = false
    // Set the focal point of the gestures to the puck's location
    mapView.location.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)

    // Add hooks to the gesture to adjust the followPuckViewportState's options.
    mapView.gestures.addOnScaleListener(onScaleListener)
    mapView.gestures.addOnRotateListener(onRotateListener)
    mapView.gestures.addOnShoveListener(onShoveListener)
  }

  private fun clearAdvancedGesturesForFollowPuckViewportState() {
    // Re-enable the default viewport behaviour, which will transition to idle upon user interactions.
    mapView.viewport.options =
      ViewportOptions.Builder().transitionsToIdleUponUserInteraction(true).build()
    // Stop updating the focal point to the puck's position, set the focal point to null(reset to the default behaviour)
    mapView.location.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    mapView.gestures.focalPoint = null
    // Re-enable scroll gesture.
    mapView.gestures.scrollEnabled = true

    // Remove the hooks to the gesture that updates the followPuckViewportState's options.
    mapView.gestures.removeOnScaleListener(onScaleListener)
    mapView.gestures.removeOnRotateListener(onRotateListener)
    mapView.gestures.removeOnShoveListener(onShoveListener)
  }

  private fun setupLocationComponent(routePoints: LineString) {
    // setup the location component
    mapView.location.apply {
      enabled = true
      locationPuck = LocationPuck2D(
        bearingImage = ImageHolder.from(R.drawable.mapbox_user_puck_icon),
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
      setLocationProvider(SimulateRouteLocationProvider(routePoints))
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.location.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    mapView.viewport.removeStatusObserver(viewportStatusObserver)
    mapView.gestures.removeOnScaleListener(onScaleListener)
    mapView.gestures.removeOnRotateListener(onRotateListener)
    mapView.gestures.removeOnShoveListener(onShoveListener)
  }

  companion object {
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"
    private const val GEOJSON_SOURCE_ID = "source_id"
    private const val ROUTE_LINE_LAYER_ID = "route_line_layer_id"
  }
}

private fun ViewportStatus.getCurrentOrNextState(): ViewportState? =
  when (this) {
    is ViewportStatus.State -> state
    is ViewportStatus.Transition -> toState
    ViewportStatus.Idle -> null
  }