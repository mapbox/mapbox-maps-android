package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.testapp.databinding.ActivityStyleCrossFadeBinding
import com.mapbox.maps.testapp.utils.StyleSwitcher

/**
 * Example of cross fading animation when changing between styles.
 */
class StyleCrossFadeAnimationActivity : AppCompatActivity() {

  private lateinit var styleSwitcher: StyleSwitcher
  private var currentStyleURI = Style.MAPBOX_STREETS
  private lateinit var binding: ActivityStyleCrossFadeBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityStyleCrossFadeBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.switchStyleButton.visibility = View.GONE
    binding.mapView.getMapboxMap().setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
        .zoom(ZOOM)
        .pitch(PITCH)
        .build()
    )
    styleSwitcher = StyleSwitcher(binding.mapView.getMapboxMap())
    binding.mapView.getMapboxMap().loadStyleUri(currentStyleURI) {
      binding.switchStyleButton.visibility = View.VISIBLE
      binding.switchStyleButton.setOnClickListener {
        crossFadeSwitchStyle()
      }
    }
  }

  private fun crossFadeSwitchStyle() {
    binding.switchStyleButton.isEnabled = false
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
        binding.switchStyleButton.isEnabled = true
      }
    }
  }

  companion object {
    private const val LATITUDE = 48.1351
    private const val LONGITUDE = 11.582
    private const val ZOOM = 13.0
    private const val PITCH = 60.0
    private const val STYLE_TRANSITION_DELAY_MS = 400L
  }
}