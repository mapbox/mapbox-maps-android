package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgb
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.databinding.ActivityStandardStyleBinding

/**
 * Example of working with style imports and the Standard style.
 */
@OptIn(MapboxExperimental::class)
class StandardStyleActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  private val line = LineString.fromLngLats(LINE_COORDINATES)
  private var lightSetting = LightPresets.DUSK
  private var labelsSetting = true
  private lateinit var binding: ActivityStandardStyleBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityStandardStyleBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.getMapboxMap()
    setContentView(binding.root)

    // Set the camera options to center on New York City
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
        .zoom(11.0)
        .pitch(45.0)
        .build()
    )

    // Load a style which imports Mapbox Standard as a basemap
    mapboxMap.loadStyle(
      style(style = STYLE_URL) {
        // Create a new GeoJSON data source of the line's coordinates
        +geoJsonSource(id = "line-layer") {
          geometry(line)
        }
        // Create and apply basic styling to the fill layer, assign the layer to the "middle" slot
        +lineLayer(layerId = "line-layer", sourceId = "line-layer") {
          lineColor(rgb(255.0, 165.0, 0.0))
          lineWidth(8.0)
          slot("middle")
        }
      }
    ) { style ->
      addOnClickListeners(style)
    }
  }

  private fun addOnClickListeners(style: Style) {
    // When a user clicks the light setting button change the `lightPreset` config property on the Standard style import
    binding.fabLightSetting.setOnClickListener {
      lightSetting = when (lightSetting) {
          LightPresets.DAY -> {
            LightPresets.DAWN
          }
          LightPresets.DAWN -> {
            LightPresets.DUSK
          }
          LightPresets.DUSK -> {
            LightPresets.NIGHT
          }
          LightPresets.NIGHT -> {
            LightPresets.DAY
          }
      }
      style.setStyleImportConfigProperty("standard", "lightPreset", Value.valueOf(lightSetting.toString().lowercase()))
    }

    // When a user clicks the labels setting button change the label config properties on the Standard style import to show/hide them
    // To identify which configuration properties are available on an imported style you can use `style.getStyleImportSchema()`
    binding.fabLabelsSetting.setOnClickListener {
      labelsSetting = !labelsSetting
      style.setStyleImportConfigProperty("standard", "showPlaceLabels", Value.valueOf(labelsSetting))
      style.setStyleImportConfigProperty("standard", "showRoadLabels", Value.valueOf(labelsSetting))
      style.setStyleImportConfigProperty("standard", "showPointInterestLabels", Value.valueOf(labelsSetting))
      style.setStyleImportConfigProperty("standard", "showTransitLabels", Value.valueOf(labelsSetting))
    }
  }

  private enum class LightPresets {
    DAY,
    DAWN,
    DUSK,
    NIGHT
  }

  companion object {
    private const val LATITUDE = 40.72
    private const val LONGITUDE = -73.99
    private const val STYLE_URL = "asset://fragment-realestate-NY.json"
    private val LINE_COORDINATES = listOf(
      Point.fromLngLat(-73.91912400100642, 40.913503418907936),
      Point.fromLngLat(-73.9615887363045, 40.82943110786286),
      Point.fromLngLat(-74.01409059085539, 40.75461056309348),
      Point.fromLngLat(-74.02798814058939, 40.69522028220487),
      Point.fromLngLat(-74.05655532615407, 40.65188756398558),
      Point.fromLngLat(-74.13916853846217, 40.64339339389301)
    )
  }
}