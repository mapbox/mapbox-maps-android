package com.mapbox.maps.testapp.examples.datajoin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.match
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.rgb
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.rgba
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.maps.extension.style.style
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Example of joining JSON data with vector tiles and coloring them based on data values.
 */
class DataJoinActivity : AppCompatActivity() {
  private var countries: List<Country> = emptyList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    lifecycleScope.launch {
      countries = withContext(Dispatchers.IO) {
        CountriesData.loadCountriesFromJSON(this@DataJoinActivity)
      }

      setupMap(mapView)
    }
  }

  private fun setupMap(mapView: MapView) {
    mapView.mapboxMap.apply {
      setCamera(
        cameraOptions {
          center(Point.fromLngLat(12.0, 50.0))
          zoom(1.6)
          pitch(0.0)
          bearing(0.0)
        }
      )

      loadStyle(
        style(Style.STANDARD) {
          +vectorSource("countries") { url("mapbox://mapbox.country-boundaries-v1") }
          +fillLayer("countries", "countries") {
            sourceLayer("country_boundaries")
            fillColor(createColorExpression(countries))
          }
        }
      ) { style ->
        // Set map to monochrome theme after style loads
        style.setStyleImportConfigProperty("basemap", "theme", Value.valueOf("monochrome"))
      }
    }
  }

  private fun createColorExpression(countries: List<Country>) = match(
    input = Expression.get("iso_3166_1_alpha_3"),
    stops = countries.map { country ->
      literal(country.code) to rgb(0.0, (country.hdi * 255), 0.0)
    }.toTypedArray(),
    fallback = rgba(0.0, 0.0, 0.0, 0.0)
  )
}