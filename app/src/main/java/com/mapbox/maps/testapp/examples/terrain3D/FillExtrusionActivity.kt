package com.mapbox.maps.testapp.examples.terrain3D

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.eq
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.literal
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.FillExtrusionLayer
import com.mapbox.maps.extension.style.light.generated.getLight
import com.mapbox.maps.testapp.databinding.ActivityFillExtrusionBinding

/**
 * Extrude the building layer in the Mapbox Light style using FillExtrusionLayer
 * and set up the light position.
 */
class FillExtrusionActivity : AppCompatActivity() {

  private var isRedColor: Boolean = false
  private var isInitPosition: Boolean = false
  private lateinit var binding: ActivityFillExtrusionBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFillExtrusionBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val mapboxMap = binding.mapView.getMapboxMap()

    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(-74.0066, 40.7135))
        .pitch(45.0)
        .zoom(15.5)
        .bearing(-17.6)
        .build()
    )

    mapboxMap.loadStyleUri(
      Style.LIGHT
    ) { style ->
      setupBuildings(style)
      setupLight(style)
    }
  }

  private fun setupBuildings(style: Style) {
    val fillExtrusionLayer = FillExtrusionLayer("3d-buildings", "composite")
    fillExtrusionLayer.sourceLayer("building")
    fillExtrusionLayer.filter(eq(get("extrude"), literal("true")))
    fillExtrusionLayer.minZoom(15.0)
    fillExtrusionLayer.fillExtrusionColor(Color.parseColor("#aaaaaa"))
    fillExtrusionLayer.fillExtrusionHeight(get("height"))
    fillExtrusionLayer.fillExtrusionBase(get("min_height"))
    fillExtrusionLayer.fillExtrusionOpacity(0.6)
    fillExtrusionLayer.fillExtrusionAmbientOcclusionIntensity(0.3)
    fillExtrusionLayer.fillExtrusionAmbientOcclusionRadius(3.0)
    style.addLayer(fillExtrusionLayer)
  }

  private fun setupLight(style: Style) {
    val light = style.getLight()
    binding.fabLightPosition.setOnClickListener {
      isInitPosition = !isInitPosition
      if (isInitPosition) {
        light.position(1.5, 90.0, 80.0)
      } else {
        light.position(1.15, 210.0, 30.0)
      }
    }

    binding.fabLightColor.setOnClickListener {
      isRedColor = !isRedColor
      if (isRedColor) {
        light.color(Color.RED)
      } else {
        light.color(Color.BLUE)
      }
    }
  }
}