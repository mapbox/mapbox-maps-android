package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.eq
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_multiple_geometries.*
import java.net.URISyntaxException

/**
 * Example showing drawing several different geometries for one source.
 */
class MultipleGeometriesActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_multiple_geometries)

    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(
      Style.LIGHT
    ) {
      createGeoJsonSource(it)
      addPolygonLayer(it)
      addLineStringLayer(it)
    }
  }

  private fun createGeoJsonSource(loadedMapStyle: Style) {
    try {
      // Load data from GeoJSON file in the assets folder
      loadedMapStyle.addSource(
        geoJsonSource(GEOJSON_SOURCE_ID) {
          url("asset://multiple_geometry_example.geojson")
        }
      )
    } catch (exception: URISyntaxException) {
      Logger.e(TAG, "Creating geojson source failed ${exception.message}")
    }
  }

  private fun addPolygonLayer(loadedMapStyle: Style) {
    // Create and style a FillLayer that uses the Polygon Feature's coordinates in the GeoJSON data
    loadedMapStyle.addLayer(
      fillLayer("polygon", GEOJSON_SOURCE_ID) {
        fillColor(Color.RED)
        fillOpacity(.4)
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
      lineLayer("line_string", GEOJSON_SOURCE_ID) {
        lineColor(Color.YELLOW)
        lineOpacity(.7)
        filter(
          eq {
            literal("\$type")
            literal("LineString")
          }
        )
      }
    )
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  companion object {
    private val TAG = MultipleGeometriesActivity::class.java.simpleName
    private const val GEOJSON_SOURCE_ID = "geojson_sample"
  }
}