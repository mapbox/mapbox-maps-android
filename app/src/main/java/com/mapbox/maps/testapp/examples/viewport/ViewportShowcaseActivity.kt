package com.mapbox.maps.testapp.examples.viewport

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.common.Logger
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.data.FollowingViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.state.FollowingViewportState
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityViewportAnimationBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.SimulateRouteLocationProvider

/**
 * Showcase the use age of viewport plugin.
 *
 * Use button to toggle the following and overview mode.
 */
@MapboxExperimental
class ViewportShowcaseActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  private lateinit var mapView: MapView
  private lateinit var viewportButton: Button
  private lateinit var viewport: ViewportPlugin
  private lateinit var followingViewportState: FollowingViewportState
  private lateinit var overviewViewportState: OverviewViewportState
  private val pixelDensity = Resources.getSystem().displayMetrics.density
  private val simulateRouteLocationProvider by lazy {
    SimulateRouteLocationProvider(
      LineString.fromPolyline(
        DirectionsResponse.fromJson(
          AnnotationUtils.loadStringFromAssets(
            this,
            NAVIGATION_ROUTE_JSON_NAME
          )
        ).routes()[0].geometry()!!,
        Constants.PRECISION_6
      )
    )
  }

  private val overviewPadding: EdgeInsets by lazy {
    EdgeInsets(
      140.0 * pixelDensity,
      40.0 * pixelDensity,
      120.0 * pixelDensity,
      40.0 * pixelDensity
    )
  }
  private val landscapeOverviewPadding: EdgeInsets by lazy {
    EdgeInsets(
      30.0 * pixelDensity,
      380.0 * pixelDensity,
      20.0 * pixelDensity,
      20.0 * pixelDensity
    )
  }
  private val followingPadding: EdgeInsets by lazy {
    EdgeInsets(
      180.0 * pixelDensity,
      40.0 * pixelDensity,
      150.0 * pixelDensity,
      40.0 * pixelDensity
    )
  }
  private val landscapeFollowingPadding: EdgeInsets by lazy {
    EdgeInsets(
      30.0 * pixelDensity,
      380.0 * pixelDensity,
      110.0 * pixelDensity,
      40.0 * pixelDensity
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityViewportAnimationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    viewportButton = binding.switchButton
    mapView = binding.mapView
    mapboxMap = binding.mapView.getMapboxMap().apply {
      addOnStyleLoadedListener {
        setCamera(
          CameraOptions.Builder()
            .zoom(14.0)
            .center(Point.fromLngLat(POINT_LNG, POINT_LAT))
            .build()
        )
        binding.mapView.location.apply {
          setLocationProvider(simulateRouteLocationProvider)
          updateSettings {
            locationPuck = LocationPuck2D(
              bearingImage = AppCompatResources.getDrawable(
                this@ViewportShowcaseActivity,
                R.drawable.mapbox_mylocation_icon_bearing,
              )
            )
          }
        }
      }
    }
    setupViewportPlugin()
  }

  @SuppressLint("SetTextI18n")
  private fun setupViewportPlugin() {
    viewport = mapView.viewport
    followingViewportState =
      viewport.makeFollowingViewportState(FollowingViewportStateOptions.Builder().build())
    overviewViewportState =
      viewport.makeOverviewViewportState(
        OverviewViewportStateOptions.Builder().geometry(simulateRouteLocationProvider.route).build()
      )
    viewport.addStatusObserver { from, to, reason ->
      Logger.i(
        TAG,
        """
        ViewportStatus changed:
            from:         $from,
            to:           $to,
            with reason:         $reason
        """.trimIndent()
      )
      when (to.getCurrentOrNextState()) {
        is FollowingViewportState -> viewportButton.text = OVERVIEW
        else -> viewportButton.text = FOLLOW
      }
    }
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      followingViewportState.apply {
        options = options.toBuilder().padding(landscapeFollowingPadding).build()
      }
      overviewViewportState.apply {
        options = options.toBuilder().padding(landscapeOverviewPadding).build()
      }
    } else {
      followingViewportState.apply {
        options = options.toBuilder().padding(followingPadding).build()
      }
      overviewViewportState.apply {
        options = options.toBuilder().padding(overviewPadding).build()
      }
    }
    viewportButton.setOnClickListener {
      when (viewportButton.text) {
        FOLLOW -> viewport.transitionTo(followingViewportState)
        OVERVIEW -> viewport.transitionTo(overviewViewportState)
      }
    }
  }

  companion object {
    private const val TAG = "ViewportShowcase"
    private const val FOLLOW = "Follow"
    private const val OVERVIEW = "Overview"
    private const val POINT_LAT = 34.052235
    private const val POINT_LNG = -118.243683
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"
  }
}

private fun ViewportStatus.getCurrentOrNextState(): ViewportState? =
  when (this) {
    is ViewportStatus.State -> state
    is ViewportStatus.Transition -> toState
  }