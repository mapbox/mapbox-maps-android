package com.mapbox.maps.testapp.examples.style

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.precipitations.generated.rain
import com.mapbox.maps.extension.style.precipitations.generated.removeRain
import com.mapbox.maps.extension.style.precipitations.generated.removeSnow
import com.mapbox.maps.extension.style.precipitations.generated.setRain
import com.mapbox.maps.extension.style.precipitations.generated.setSnow
import com.mapbox.maps.extension.style.precipitations.generated.snow
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.databinding.ActivityPrecipitationsBinding

/**
 * Showcase snow and rain effect.
 */
@OptIn(MapboxExperimental::class)
class PrecipitationActivity : AppCompatActivity() {

  private var isSnowing: Boolean = true
  private var isRaining: Boolean = true
  private val rain = rain {
    intensity(0.6)
    opacity(0.5)
    vignette(0.5)
  }
  private val snow = snow {
    intensity(0.6)
    opacity(0.5)
    vignette(0.5)
  }
  private lateinit var binding: ActivityPrecipitationsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityPrecipitationsBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val mapboxMap = binding.mapView.mapboxMap
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(24.943849, 60.171924))
        .bearing(-17.6)
        .pitch(45.0)
        .zoom(16.0)
        .build()
    )

    mapboxMap.loadStyle(
      style(Style.STANDARD) {
        +snow
        +rain
      }
    )

    // change snow intensity on fab click
    binding.toggleSnow.setOnClickListener {
      isSnowing = !isSnowing
      if (isSnowing) {
        mapboxMap.setSnow(snow)
      } else {
        mapboxMap.removeSnow()
      }
    }

    binding.toggleRain.setOnClickListener {
      isRaining = !isRaining
      if (isRaining) {
        mapboxMap.setRain(rain)
      } else {
        mapboxMap.removeRain()
      }
    }
  }
}