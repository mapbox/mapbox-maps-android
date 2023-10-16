package com.mapbox.maps.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import java.util.concurrent.CountDownLatch

class TestMapActivity : AppCompatActivity() {
  var startLatch = CountDownLatch(1)
    private set
  var stopLatch = CountDownLatch(0)
    private set

  lateinit var mapView: MapView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mapView = MapView(this)
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

  override fun onStart() {
    super.onStart()
    startLatch.countDown()
    stopLatch = CountDownLatch(1)
  }

  override fun onStop() {
    super.onStop()

    stopLatch.countDown()
    startLatch = CountDownLatch(1)
  }

  companion object {
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}