package com.mapbox.maps.testapp.examples.terrain3D

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.all
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.eq
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.gt
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.lte
import com.mapbox.maps.extension.style.layers.generated.fillExtrusionLayer
import com.mapbox.maps.extension.style.light.generated.ambientLight
import com.mapbox.maps.extension.style.light.generated.directionalLight
import com.mapbox.maps.extension.style.light.setLight
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.databinding.ActivityFillExtrusionBinding

/**
 * Extrude the building layer in the Mapbox Light style using FillExtrusionLayer
 * and set up the light position.
 */
class FillExtrusionActivity : AppCompatActivity() {

  private var isRedColor: Boolean = false
  private lateinit var binding: ActivityFillExtrusionBinding

  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFillExtrusionBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val mapboxMap = binding.mapView.mapboxMap
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(-74.0066, 40.7135))
        .pitch(45.0)
        .zoom(15.5)
        .bearing(-17.6)
        .build()
    )

    // buildings lower than 20 meters will be rendered in wall-only mode
    val wallOnlyThreshold = 20.0
    val extrudeFilter = eq(get("extrude"), literal("true"))

    mapboxMap.loadStyle(
      style(style = Style.LIGHT) {
        +fillExtrusionLayer("3d-buildings", "composite") {
          sourceLayer("building")
          filter(
            all(
              extrudeFilter,
              gt(get("height"), literal(wallOnlyThreshold))
            )
          )
          minZoom(15.0)
          fillExtrusionColor(Color.parseColor("#aaaaaa"))
          fillExtrusionHeight(get("height"))
          fillExtrusionBase(get("min_height"))
          fillExtrusionOpacity(0.6)
          fillExtrusionAmbientOcclusionIntensity(0.3)
          fillExtrusionAmbientOcclusionRadius(3.0)
          fillExtrusionEdgeRadius(0.6)
        }

        +fillExtrusionLayer("3d-buildings-wall", "composite") {
          sourceLayer("building")
          filter(
            all(
              extrudeFilter,
              lte(get("height"), literal(wallOnlyThreshold))
            )
          )
          fillExtrusionLineWidth(2.0)
          minZoom(15.0)
          fillExtrusionColor(Color.parseColor("#aaaaaa"))
          fillExtrusionHeight(get("height"))
          fillExtrusionBase(get("min_height"))
          fillExtrusionOpacity(0.6)
        }
      }
    ) {
      setupLights3D(it)
    }
  }

  private fun setupLights3D(style: Style) {
    // setup 3d light
    val ambientLight = ambientLight {
      color(Color.BLUE)
      intensity(0.9)
    }
    val directionalLight = directionalLight {
      color(Color.YELLOW)
      intensity(0.9)
      castShadows(true)
      direction(listOf(0.0, 15.0))
    }
    style.setLight(ambientLight, directionalLight)
    // change color on fab click
    binding.fabLightColor.setOnClickListener {
      isRedColor = !isRedColor
      if (isRedColor) {
        ambientLight.color(Color.RED)
      } else {
        ambientLight.color(Color.BLUE)
      }
    }

    binding.fabLightPosition.setOnClickListener {
      directionalLight.direction(listOf(0.0, (directionalLight.direction!![1] + 5.0) % 90.0))
    }
  }
}