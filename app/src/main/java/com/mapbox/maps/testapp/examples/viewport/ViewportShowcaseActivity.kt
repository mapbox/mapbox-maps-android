package com.mapbox.maps.testapp.examples.viewport

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityViewportAnimationBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.SimulateRouteLocationProvider


/**
 * Showcase the use age of viewport plugin.
 *
 * Use button to toggle the following and overview mode.
 *
 * @see [User location guide](https://docs.mapbox.com/android/maps/guides/user-location/#location-tracking)
 */
class ViewportShowcaseActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  private lateinit var mapView: MapView
  private lateinit var viewportButton: Button
  private lateinit var viewport: ViewportPlugin
  private lateinit var followPuckViewportState: FollowPuckViewportState
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
    followPuckViewportState =
      viewport.makeFollowPuckViewportState(FollowPuckViewportStateOptions.Builder().build())
    overviewViewportState =
      viewport.makeOverviewViewportState(
        OverviewViewportStateOptions.Builder().geometry(simulateRouteLocationProvider.route).build()
      )
    viewport.addStatusObserver { from, to, reason ->
      logI(
        TAG,
        """
        ViewportStatus changed:
            from:         $from,
            to:           $to,
            with reason:         $reason
        """.trimIndent()
      )
      followPuckViewportState.resetDefaultOptions()
    }
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      followPuckViewportState.apply {
        options = options.toBuilder().padding(landscapeFollowingPadding).build()
      }
      overviewViewportState.apply {
        options = options.toBuilder().padding(landscapeOverviewPadding).build()
      }
    } else {
      followPuckViewportState.apply {
        options = options.toBuilder().padding(followingPadding).build()
      }
      overviewViewportState.apply {
        options = options.toBuilder().padding(overviewPadding).build()
      }
    }
    viewportButton.text = STATES[CURRENT_STATE_INDEX]
    viewportButton.setOnClickListener {
      when (STATES[CURRENT_STATE_INDEX]) {
        OVERVIEW -> viewport.transitionTo(overviewViewportState)
        FOLLOW -> viewport.transitionTo(followPuckViewportState)
        FOLLOW_WITH_INCREASED_ZOOM -> animateZoomAndPitchSeparately(
          followPuckViewportState,
          zoom = 18.0,
          pitch = 10.0,
          durationMs = 1000
        )
        FOLLOW_WITH_DECREASED_ZOOM -> animateZoomAndPitchSeparately(
          followPuckViewportState,
          zoom = 15.0,
          pitch = 60.0,
          durationMs = 1000
        )
      }
      CURRENT_STATE_INDEX = (++CURRENT_STATE_INDEX) % 4
      viewportButton.text = STATES[CURRENT_STATE_INDEX]
    }
  }

  private fun animateZoomAndPitchSeparately(
    followPuckViewportState: FollowPuckViewportState,
    zoom: Double,
    pitch: Double,
    durationMs: Long
  ) {
    // configure the FollowPuckViewportState to not update zoom level
    followPuckViewportState.disableZoomUpdate()
    followPuckViewportState.disablePitchUpdate()

    // Do the animation for zoom update
    mapView.camera.easeTo(
      CameraOptions.Builder()
        .zoom(zoom)
        .pitch(pitch)
        .build(),
      MapAnimationOptions.Builder()
        .duration(durationMs)
        .animatorListener(
          object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
              // no-ops
            }

            override fun onAnimationEnd(animation: Animator?) {
              followPuckViewportState.resumeZoomUpdateWithCurrentZoomLevel()
              followPuckViewportState.resumeZoomUpdateWithCurrentPitchLevel()
            }

            override fun onAnimationCancel(animation: Animator?) {
              // no-ops
            }

            override fun onAnimationRepeat(animation: Animator?) {
              // no-ops
            }

          }
        ).build()
    )
  }

  private fun FollowPuckViewportState.disableZoomUpdate() {
    // configure the FollowPuckViewportState to not update zoom level
    options = options.toBuilder().zoom(null).build()
  }

  private fun FollowPuckViewportState.resumeZoomUpdateWithCurrentZoomLevel() {
    // configure the FollowPuckViewportState to use the current zoom level
    options = options.toBuilder().zoom(mapView.getMapboxMap().cameraState.zoom).build()
  }

  private fun FollowPuckViewportState.disablePitchUpdate() {
    // configure the FollowPuckViewportState to not update camera pitch.
    options = options.toBuilder().pitch(null).build()
  }

  private fun FollowPuckViewportState.resumeZoomUpdateWithCurrentPitchLevel() {
    // configure the FollowPuckViewportState to use the current pitch level
    options = options.toBuilder().pitch(mapView.getMapboxMap().cameraState.pitch).build()
  }

  private fun FollowPuckViewportState.resetDefaultOptions() {
    // reset to the default FollowPuckViewportStateOptions
    options = FollowPuckViewportStateOptions.Builder().build()
  }

  companion object {
    private const val TAG = "ViewportShowcase"
    private const val FOLLOW = "Follow with default zoom"
    private const val OVERVIEW = "Overview"
    private const val FOLLOW_WITH_INCREASED_ZOOM = "Follow with increased zoom"
    private const val FOLLOW_WITH_DECREASED_ZOOM = "Follow with decreased zoom"
    private const val POINT_LAT = 34.052235
    private const val POINT_LNG = -118.243683
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"

    private val STATES = arrayOf(
      OVERVIEW,
      FOLLOW,
      FOLLOW_WITH_INCREASED_ZOOM,
      FOLLOW_WITH_DECREASED_ZOOM,
    )
    private var CURRENT_STATE_INDEX = 0
  }
}
