package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.gestures.RotateGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.gestures.OnRotateListener
import com.mapbox.maps.plugin.gestures.addOnRotateListener
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar

/**
 * Test activity to validate correct margin displacement of ornaments when the map rotates.
 */
class OrnamentMarginActivity : AppCompatActivity(), OnRotateListener {

  private lateinit var mapView: MapView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)
    with(mapView.getMapboxMap()) {
      mapView.attribution.position = Gravity.END or Gravity.BOTTOM
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(23.760833, 61.498056))
          .zoom(14.0)
          .build()
      )
      addOnRotateListener(this@OrnamentMarginActivity)
    }
  }

  override fun onRotate(detector: RotateGestureDetector) {
    val bearing = mapView.getMapboxMap().cameraState.bearing.toFloat()
    val margin = 2f * if (bearing <= 180f) bearing else 180f - (bearing % 180f)
    with(mapView.logo) {
      marginLeft = margin
      marginBottom = margin
      marginRight = margin
      marginTop = margin
    }
    with(mapView.attribution) {
      marginLeft = margin
      marginBottom = margin
      marginRight = margin
      marginTop = margin
    }
    with(mapView.scalebar) {
      marginLeft = margin
      marginBottom = margin
      marginRight = margin
      marginTop = margin
    }
    with(mapView.compass) {
      marginLeft = margin
      marginBottom = margin
      marginRight = margin
      marginTop = margin
    }
  }

  override fun onRotateEnd(detector: RotateGestureDetector) {
  }

  override fun onRotateBegin(detector: RotateGestureDetector) {
  }
}