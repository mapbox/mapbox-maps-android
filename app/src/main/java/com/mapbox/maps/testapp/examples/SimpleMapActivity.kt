package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_simple_map.*

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  var count = 0
  var sum = 0.0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
      mapView.setOnFpsChangedListener {
        count++
        sum += it
        Logger.e("Mbx-Benchmark", "fps: $it, count = $count")
        if (count == 500) {
          Logger.e("Mbx-Benchmark", "Avg fps: ${sum / count}")
        }
      }
      mapView.postDelayed({
        mapView.getMapboxMap().flyTo(
          CameraOptions.Builder()
            .center(Point.fromLngLat(-77.0369, 38.9072))
            .zoom(16.0)
            .pitch(85.0)
            .build(),
          MapAnimationOptions.mapAnimationOptions {
            duration = 10_000L
          }
        )
      }, 1000L)
    }
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }
}