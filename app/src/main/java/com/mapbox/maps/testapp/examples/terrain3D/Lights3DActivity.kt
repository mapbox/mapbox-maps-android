package com.mapbox.maps.testapp.examples.terrain3D

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.light.generated.ambientLight
import com.mapbox.maps.extension.style.light.generated.directionalLight
import com.mapbox.maps.extension.style.light.setLight
import com.mapbox.maps.testapp.databinding.ActivityFillExtrusionBinding

/**
 * Extrude the building layer in the Mapbox Standard style
 * and set up the light position.
 */
class Lights3DActivity : AppCompatActivity() {

  private var isRedColor: Boolean = false
  private lateinit var binding: ActivityFillExtrusionBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFillExtrusionBinding.inflate(layoutInflater)
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
      Style.STANDARD
    ) { style ->
      setupLights3D(style)
    }
  }

  private fun setupLights3D(style: Style) {
    // setup 3d light
    val ambientLight = ambientLight(AMBIENT_LIGHT_ID) {
      color(Color.WHITE)
      intensity(0.5)
    }
    val directionalLight = directionalLight(DIRECTIONAL_LIGHT_ID) {
      color(Color.YELLOW)
      intensity(0.9)
      castShadows(true)
      direction(listOf(0.0, 15.0))
    }
    style.setLight(
      ambientLight,
      directionalLight,
    )
    // change color on fab click
    binding.fabLightColor.setOnClickListener {
      isRedColor = !isRedColor
      if (isRedColor) {
        ambientLight.color(Color.RED)
      } else {
        ambientLight.color(Color.WHITE)
      }
    }

    binding.fabLightPosition.setOnClickListener {
      directionalLight.direction(listOf(0.0, (directionalLight.direction!![1] + 5.0) % 90.0))
    }
  }

  private companion object {
    private const val AMBIENT_LIGHT_ID = "ambient_id"
    private const val DIRECTIONAL_LIGHT_ID = "directional_id"
  }
}