package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.eq
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.logE
import com.mapbox.maps.testapp.databinding.ActivityMultipleGeometriesBinding
import java.net.URISyntaxException

/**
 * Example showing drawing several different geometries for one source.
 */
class MultipleGeometriesActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityMultipleGeometriesBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.mapboxMap
    mapboxMap.loadStyle(
      Style.LIGHT
    ) {
      createGeoJsonSource(it)
      addPolygonLayer(it)
      addLineStringLayer(it)
      addPointLayer(it)
    }
  }

  private fun createGeoJsonSource(loadedMapStyle: Style) {
    try {
      // Load data from GeoJSON file in the assets folder
      loadedMapStyle.addSource(
        geoJsonSource(GEOJSON_SOURCE_ID) {
          data(GEOJSON_SOURCE_URL)
        }
      )
    } catch (exception: URISyntaxException) {
      logE(TAG, "Creating geojson source failed ${exception.message}")
    }
  }

  private fun addPolygonLayer(loadedMapStyle: Style) {
    // Create and style a FillLayer that uses the Polygon Feature's coordinates in the GeoJSON data
    loadedMapStyle.addLayer(
      fillLayer(POLYGON_LAYER_ID, GEOJSON_SOURCE_ID) {
        fillColor(Color.parseColor("#4469f7"))
        fillOpacity(POLYGON_OPACITY)
        filter(
          eq {
            literal("\$type")
            literal("Polygon")
          }
        )
      }
    )
  }

  private fun addLineStringLayer(loadedMapStyle: Style) {
    // Create and style a LineLayer that uses the Line String Feature's coordinates in the GeoJSON data
    loadedMapStyle.addLayer(
      lineLayer(LINE_LAYER_ID, GEOJSON_SOURCE_ID) {
        lineColor(Color.RED)
        lineWidth(LINE_WIDTH)
        filter(
          eq {
            literal("\$type")
            literal("LineString")
          }
        )
      }
    )
  }

  private fun addPointLayer(loadedMapStyle: Style) {
    // Create and style a Circle layer that uses the Point Feature's coordinates in the GeoJSON data
    loadedMapStyle.addLayer(
      circleLayer(CIRCLE_LAYER_ID, GEOJSON_SOURCE_ID) {
        filter(
          eq {
            literal("\$type")
            literal("Point")
          }
        )
        circleColor(Color.RED)
        circleRadius(CIRCLE_RADIUS)
        circleStrokeWidth(CIRCLE_STROKE_WIDTH)
        circleStrokeColor(Color.BLACK)
      }
    )
  }

  companion object {
    private val TAG = MultipleGeometriesActivity::class.java.simpleName
    private const val GEOJSON_SOURCE_ID = "geojson_sample"
    private const val CIRCLE_LAYER_ID = "circle-layer"
    private const val LINE_LAYER_ID = "line_string"
    private const val POLYGON_LAYER_ID = "polygon"
    private const val GEOJSON_SOURCE_URL = "asset://multiple_geometry_example.geojson"
    private const val LINE_WIDTH = 2.0
    private const val CIRCLE_RADIUS = 6.0
    private const val CIRCLE_STROKE_WIDTH = 2.0
    private const val POLYGON_OPACITY = 0.3
  }
}