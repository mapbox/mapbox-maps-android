package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.StyleSwitcher
import kotlinx.android.synthetic.main.activity_style_cross_fade.*

/**
 * Example of cross fading animation when changing between styles.
 */
class StyleCrossFadeAnimationActivity : AppCompatActivity() {

  private lateinit var styleSwitcher: StyleSwitcher
  private var currentStyleURI = Style.MAPBOX_STREETS

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_style_cross_fade)
    switchStyleButton.visibility = View.GONE
    mapView.getMapboxMap().setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
        .zoom(ZOOM)
        .pitch(PITCH)
        .build()
    )
    styleSwitcher = StyleSwitcher(mapView.getMapboxMap())
    mapView.getMapboxMap().loadStyleUri(currentStyleURI) {
      switchStyleButton.visibility = View.VISIBLE
      switchStyleButton.setOnClickListener {
        crossFadeSwitchStyle()
      }
    }
  }

  private fun crossFadeSwitchStyle() {
    switchStyleButton.isEnabled = false
    val newStyleUri = if (currentStyleURI == Style.MAPBOX_STREETS) {
      Style.DARK
    } else {
      Style.MAPBOX_STREETS
    }
    styleSwitcher.switchStyle(
      newStyleUri,
      STYLE_TRANSITION_DELAY_MS,
      listOf("road-", "landuse", "bridge-", "tunnel-")
    ) {
      currentStyleURI = it.styleURI
      runOnUiThread {
        switchStyleButton.isEnabled = true
      }
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
    private const val LATITUDE = 48.1351
    private const val LONGITUDE = 11.582
    private const val ZOOM = 13.0
    private const val PITCH = 60.0
    private const val STYLE_TRANSITION_DELAY_MS = 400L
  }
}