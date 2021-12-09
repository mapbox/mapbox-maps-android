package com.mapbox.maps.testapp.examples.viewport

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.LocationConsumer
import com.mapbox.maps.plugin.locationcomponent.LocationProvider
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.data.MapboxViewportDataSource
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityViewportAnimationBinding

/**
 * Showcase the use age of viewport plugin.
 *
 * Use button to toggle the following and overview mode.
 */
class ViewportShowcaseActivity : AppCompatActivity() {
  private var emitCount = 0
  private var delta = 0f
  private val handler = Handler(Looper.getMainLooper())
  private lateinit var mapboxMap: MapboxMap
  private lateinit var mapView: MapView
  private lateinit var viewportButton: Button
  private lateinit var latestLocation: Location
  private lateinit var viewport: ViewportPlugin
  private lateinit var viewportDataSource: MapboxViewportDataSource
  private val pixelDensity = Resources.getSystem().displayMetrics.density

  private val paddedFollowingEdgeInsets = EdgeInsets(
    0.0,
    0.0,
    120.0 * pixelDensity,
    0.0
  )

  private val notPaddedEdgeInsets: EdgeInsets by lazy {
    EdgeInsets(
      0.0,
      0.0,
      0.0,
      0.0
    )
  }
  private var followingEdgeInsets = paddedFollowingEdgeInsets
    set(value) {
      field = value
      viewportDataSource.followingPadding = value
      viewportDataSource.evaluate()
    }
  private val overviewEdgeInsets: EdgeInsets by lazy {
    EdgeInsets(
      40.0 * pixelDensity,
      40.0 * pixelDensity,
      40.0 * pixelDensity,
      40.0 * pixelDensity
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
          val provider = FakeLocationProvider()
          latestLocation = Location(provider.javaClass.simpleName)
          setLocationProvider(provider)
          updateSettings {
            locationPuck = LocationPuck2D(
              bearingImage = AppCompatResources.getDrawable(
                this@ViewportShowcaseActivity,
                R.drawable.mapbox_mylocation_icon_bearing,
              )
            )
          }
          addOnIndicatorPositionChangedListener {
            latestLocation.latitude = it.latitude()
            latestLocation.longitude = it.longitude()
            viewportDataSource.onLocationChanged(latestLocation)
            viewport.resetFrame()
          }
          addOnIndicatorBearingChangedListener {
            latestLocation.bearing = it.toFloat()
            viewportDataSource.onLocationChanged(latestLocation)
            viewport.resetFrame()
          }
        }
      }
    }
    setupViewportCamera()
  }

  @SuppressLint("SetTextI18n")
  private fun setupViewportCamera() {
    viewport = mapView.viewport
    viewportDataSource = MapboxViewportDataSource(mapboxMap, mapboxMap)
    viewport.dataSource = viewportDataSource
    viewport.registerViewportCameraStateChangedObserver { cameraState ->
      // change title of viewport button depending on the camera state
      when (cameraState) {
        ViewportState.TransitionToFollowing,
        ViewportState.Following -> viewportButton.text = OVERVIEW

        ViewportState.TransitionToOverview,
        ViewportState.Overview,
        ViewportState.Idle -> viewportButton.text = FOLLOW
      }
    }
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      viewportDataSource.overviewPadding = landscapeOverviewPadding
      viewportDataSource.followingPadding = landscapeFollowingPadding
    } else {
      viewportDataSource.overviewPadding = overviewPadding
      viewportDataSource.followingPadding = followingPadding
    }
    viewportButton.setOnClickListener {
      when (viewportButton.text) {
        FOLLOW -> {
          followingEdgeInsets = paddedFollowingEdgeInsets
          viewportDataSource.options.followingFrameOptions.zoomUpdatesAllowed = true
          viewportDataSource.followingPadding = followingEdgeInsets
          viewportDataSource.evaluate()
          viewport.requestCameraToFollowing()
        }
        OVERVIEW -> {
          viewportDataSource.overviewPadding = overviewEdgeInsets
          viewportDataSource.evaluate()
          viewport.requestCameraToOverview()
        }
      }
    }
  }

  private inner class FakeLocationProvider : LocationProvider {

    private var locationConsumer: LocationConsumer? = null

    private fun emitFakeLocations() {
      // after several first emits we update puck animator options
      if (emitCount == 5) {
        locationConsumer?.run {
          onPuckLocationAnimatorDefaultOptionsUpdated {
            // set same duration as our location emit frequency - it will make puck position change smooth
            duration = 2000
            interpolator = FastOutSlowInInterpolator()
          }
          onPuckBearingAnimatorDefaultOptionsUpdated {
            // set duration bigger than our location emit frequency -
            // this will result in cancelling ongoing animation and starting new one with a visible non-smooth `jump`
            duration = 5000
          }
        }
      }
      handler.postDelayed(
        {
          locationConsumer?.onLocationUpdated(Point.fromLngLat(POINT_LNG + delta, POINT_LAT + delta))
          locationConsumer?.onBearingUpdated(BEARING + delta * 10000.0 * 5)
          delta += 0.001f
          emitCount++
          emitFakeLocations()
        },
        2000
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
    private const val FOLLOW = "Follow"
    private const val OVERVIEW = "Overview"
    private const val POINT_LAT = 60.1699
    private const val POINT_LNG = 24.9384
    private const val BEARING = 60.0
  }
}