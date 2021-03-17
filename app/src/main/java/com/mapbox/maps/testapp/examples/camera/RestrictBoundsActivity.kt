package com.mapbox.maps.testapp.examples.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.BoundOptions
import com.mapbox.maps.CoordinateBounds
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_restrict_bounds.*

/**
 * Test activity showcasing restricting user gestures to a bounds.
 */
class RestrictBoundsActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_restrict_bounds)
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
      mapboxMap.setBounds(BOUNDS)
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

  companion object {
    private val BOUNDS: BoundOptions = BoundOptions.Builder()
      .bounds(
        CoordinateBounds(
          Point.fromLngLat(-74.04728500751165, 40.68392799015035),
          Point.fromLngLat(-73.91058699000139, 40.87764500765852)
        )
      )
      .minZoom(13.0)
      .build()
  }
}