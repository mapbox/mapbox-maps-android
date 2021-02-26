package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_simple_map.*

/**
 * Example of displaying a custom Mapbox-hosted style.
 */
class MapboxStudioStyleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_style_mapbox_studio)
    mapView.getMapboxMap().loadStyleUri(STUDIO_STYLE_URL)
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
    private const val STUDIO_STYLE_URL = "mapbox://styles/mapbox/cj3kbeqzo00022smj7akz3o1e"
  }
}