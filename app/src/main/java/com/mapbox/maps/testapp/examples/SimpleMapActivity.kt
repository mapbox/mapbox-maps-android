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
  lateinit var mapView: MapView
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)
    mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(LONGITUDE, LATITUDE))
            .zoom(9.0)
            .build()
        )
      }
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }
  companion object {
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}