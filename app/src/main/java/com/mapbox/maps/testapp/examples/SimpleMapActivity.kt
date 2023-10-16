package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapView.mapboxMap
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(LONGITUDE, LATITUDE))
            .zoom(9.0)
            .build()
        )
      }
  }

  companion object {
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}