package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
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
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(23.735390592527207, 61.48924433618418))
          .zoom(13.954639815373357)
          .build()
      )
      addOnCameraChangeListener {
        println("Camera\n\tzoom: ${cameraState.zoom}\n\tcenter: ${cameraState.center}")
      }
    }
  }
}