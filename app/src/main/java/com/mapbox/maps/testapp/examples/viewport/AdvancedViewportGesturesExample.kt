package com.mapbox.maps.testapp.examples.viewport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.android.gestures.ShoveGestureDetector
import com.mapbox.android.gestures.StandardScaleGestureDetector
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnScaleListener
import com.mapbox.maps.plugin.gestures.OnShoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.data.FollowingViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowingViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportOptions
import com.mapbox.maps.plugin.viewport.state.FollowingViewportState
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.SimulateRouteLocationProvider
import kotlin.math.PI
import kotlin.math.ln

/**
 * Showcase the use age of viewport plugin with advanced gestures customisation.
 *
 * Touch the map to toggle the following and overview mode.
 */
@MapboxExperimental
class AdvancedViewportGesturesExample : AppCompatActivity() {
  private lateinit var mapView: MapView
  private lateinit var routePoints: LineString
  private val followingViewportState: FollowingViewportState by lazy {
    mapView.viewport.makeFollowingViewportState(
      FollowingViewportStateOptions.Builder()
        .bearing(FollowingViewportStateBearing.Constant(0.0))
        .frameAnimationDurationMs(500)
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
    mapView.getMapboxMap().loadStyle(
      // Show the route line on the map
      style(Style.TRAFFIC_DAY) {
        +geoJsonSource(GEOJSON_SOURCE_ID) {
          geometry(routePoints)
        }
        +lineLayer(
          ROUTE_LINE_LAYER_ID,
          GEOJSON_SOURCE_ID
        ) {
          lineColor(mapView.context.resources.getColor(R.color.mapbox_blue))
          lineWidth(10.0)
          lineCap(LineCap.ROUND)
          lineJoin(LineJoin.ROUND)
        }
      }
    ) {
      setupLocationComponent(routePoints)
      setupAdvancedGestures()
      mapView.viewport.transitionTo(overviewViewportState)
    }
  }

  private fun setupAdvancedGestures() {
    // Disable viewport's transitionsToIdleUponUserInteraction so that we can customise the gestures
    // within the state.
    mapView.viewport.options =
      ViewportOptions.Builder().transitionsToIdleUponUserInteraction(false).build()

    // Advanced gestures handling

    // Disable scroll gesture
    mapView.gestures.scrollEnabled = false

    // Switch ViewportStates by single tapping on the map.
    mapView.gestures.addOnMapClickListener {
      mapView.viewport.transitionTo(
        when (mapView.viewport.status.getCurrentOrNextState()) {
          followingViewportState -> overviewViewportState
          else -> followingViewportState
        }
      )
      false
    }

    // Let shove gesture to overwrite the followingViewportState's default pitch value.
    mapView.gestures.addOnShoveListener(
      object : OnShoveListener {
        override fun onShoveBegin(detector: ShoveGestureDetector) {
          // no-ops
        }

        override fun onShove(detector: ShoveGestureDetector) {
          followingViewportState.options = followingViewportState.options.toBuilder()
            .pitch(followingViewportState.options.pitch - PITCH_FACTOR * detector.deltaPixelSinceLast)
            .build()
        }

        override fun onShoveEnd(detector: ShoveGestureDetector) {
          // no-ops
        }
      })

    // Let scale gesture to overwrite the followingViewportState's default zoom value.
    mapView.gestures.addOnScaleListener(object : OnScaleListener {
      override fun onScaleBegin(detector: StandardScaleGestureDetector) {
        // no-ops
      }

      override fun onScale(detector: StandardScaleGestureDetector) {
        // This is an example of calculating the correct zoom delta from the StandardScaleGestureDetector
        // logic is ported from the gestures plugin implementation
        val zoomBy = ln(detector.scaleFactor.toDouble()) / ln(PI / 2) * SCALE_FACTOR
        followingViewportState.options = followingViewportState.options.toBuilder()
          .zoom(followingViewportState.options.zoom + zoomBy).build()
      }

      override fun onScaleEnd(detector: StandardScaleGestureDetector) {
        // no-ops
      }
    })
  }

  private fun setupLocationComponent(routePoints: LineString) {
    // setup the location component
    mapView.location.apply {
      enabled = true
      locationPuck = LocationPuck2D(
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
      setLocationProvider(SimulateRouteLocationProvider(routePoints))
    }
  }

  companion object {
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"
    private const val GEOJSON_SOURCE_ID = "source_id"
    private const val ROUTE_LINE_LAYER_ID = "route_line_layer_id"
    private const val SCALE_FACTOR = 0.65
    private const val PITCH_FACTOR = 0.1
  }
}

private fun ViewportStatus.getCurrentOrNextState(): ViewportState? =
  when (this) {
    is ViewportStatus.State -> state
    is ViewportStatus.Transition -> toState
  }