package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.LocationConsumer
import com.mapbox.maps.plugin.locationcomponent.LocationProvider
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityLocationComponentAnimationBinding

/**
 * Example that demonstrates using custom [LocationProvider] and sending custom location updates
 * with [LocationConsumer] with using custom puck transition options.
 */
class LocationComponentAnimationActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  private var emitCount = 0
  private var delta = 0f
  private val handler = Handler(Looper.getMainLooper())

  private inner class FakeLocationProvider : LocationProvider {

    private var locationConsumer: LocationConsumer? = null

    private fun emitFakeLocations() {
      // after several first emits we update puck animator options
      if (emitCount == 5) {
        locationConsumer?.let {
          it.onPuckLocationAnimatorDefaultOptionsUpdated {
            // set same duration as our location emit frequency - it will make puck position change smooth
            duration = 2000
            interpolator = FastOutSlowInInterpolator()
          }
          it.onPuckBearingAnimatorDefaultOptionsUpdated {
            // set duration bigger than our location emit frequency -
            // this will result in cancelling ongoing animation and starting new one with a visible non-smooth `jump`
            duration = 5000
          }
        }
      }
      handler.postDelayed(
        {
          when (emitCount) {
            // set duration > location emit frequency -> location animator will interrupt running one
            // and start from last interpolated point. Because of constant change of target point
            // it will result in increasing puck speed.
            in 1..5 -> {
              locationConsumer?.onLocationUpdated(
                Point.fromLngLat(
                  POINT_LNG + delta,
                  POINT_LAT + delta
                )
              ) {
                duration = 4000
              }
            }
            // set duration = emit frequency -> location animators do not interrupt each other
            else -> {
              locationConsumer?.onLocationUpdated(
                Point.fromLngLat(
                  POINT_LNG + delta,
                  POINT_LAT + delta
                )
              )
            }
          }
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityLocationComponentAnimationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.getMapboxMap().apply {
      loadStyle(
        Style.STANDARD
      ) {
        setCamera(
          CameraOptions.Builder()
            .zoom(14.0)
            .center(Point.fromLngLat(POINT_LNG, POINT_LAT))
            .build()
        )
        binding.mapView.location.apply {
          setLocationProvider(FakeLocationProvider())
          updateSettings {
            locationPuck = LocationPuck2D(
              bearingImage = ImageHolder.from(R.drawable.mapbox_mylocation_icon_bearing),
            )
          }
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    handler.removeCallbacksAndMessages(null)
  }

  companion object {
    private const val POINT_LAT = 60.1699
    private const val POINT_LNG = 24.9384
    private const val BEARING = 60.0
  }
}