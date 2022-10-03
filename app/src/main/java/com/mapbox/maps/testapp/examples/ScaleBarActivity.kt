package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.testapp.R

/**
 * Test activity to validate correct margin displacement of ornaments when the map rotates.
 */
class ScaleBarActivity : AppCompatActivity() {

  private lateinit var mapView: MapView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scale_bar)
    mapView = findViewById(R.id.mapView)
    with(mapView.getMapboxMap()) {
      //mapView.attribution.position = Gravity.END or Gravity.BOTTOM
      //setCamera(CUT_RIGHT)
      addOnCameraChangeListener {
        println("Camera\n\tzoom: ${cameraState.zoom}\n\tcenter: ${cameraState.center}")
      }
    }
  }
}

private val OVERLAPPED = cameraOptions {
  zoom(12.70239810098114)
  center(Point.fromLngLat(23.743161580668357, 61.48698499984082))
}
private val CUT_RIGHT = cameraOptions {
  zoom(14.940602577615023)
  center(Point.fromLngLat(23.735379149896005, 61.489557808394814))
}
private val CUT_RIGHT_OVERLAPPED = cameraOptions {
  zoom(0.411417980720449)
  center(Point.fromLngLat(25.14249889895251, 53.1200907283227))
}