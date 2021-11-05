package com.mapbox.maps.testapp.examples.style

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.common.Logger
import com.mapbox.common.ValueConverter
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.rasterLayer
import com.mapbox.maps.extension.style.sources.TileSet
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.Scheme
import com.mapbox.maps.extension.style.sources.generated.rasterSource
import com.mapbox.maps.testapp.R

/**
 * Activity showcases usage of TileSet to load a TileJSON compatible configuration as a source.
 *
 * This example uses OSM tiles as a raster source and visualises them using a raster layer.
 */
class TileJsonActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_custom_layer)
    val mapView: MapView = findViewById(R.id.mapView)

    val tileSet = TileSet.Builder(TILE_JSON_VERSION, listOf(OSM_RASTER_TILE_URL))
      .name(TILE_JSON_NAME)
      .description(TILE_JSON_DESCRIPTION)
      .attribution(TILE_JSON_ATTRIBUTION)
      .scheme(Scheme.XYZ)
      .minZoom(TILE_JSON_MIN_ZOOM)
      .maxZoom(TILE_JSON_MAX_ZOOM)
      .bounds(MERCATOR_BOUNDS)
      .center(CENTER_MAP_LOCATION)
      .build()

    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleJson("{}") {
      it.addSource(
        rasterSource(SOURCE_ID) {
          tileSet(tileSet)
          tileSize(RASTER_TILE_SIZE_PIXELS)
        }
      )
      it.addLayer(rasterLayer(LAYER_ID, SOURCE_ID) {})
    }

    // Click on button to print out tile set information
    findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
      if (::mapboxMap.isInitialized) {
        mapboxMap.getStyle {
          val properties = it.getStyleSourceProperties(SOURCE_ID).value!!
          val propertiesJson = ValueConverter.toJson(properties)
          Logger.i(TAG, propertiesJson)
          Toast.makeText(this, propertiesJson, Toast.LENGTH_LONG).show()
        }
      }
    }
  }

  private companion object {
    const val SOURCE_ID = "osm"
    const val LAYER_ID = SOURCE_ID
    const val TAG = SOURCE_ID

    const val TILE_JSON_VERSION = "2.0.0"
    const val TILE_JSON_NAME = "OpenStreetMap"
    const val TILE_JSON_DESCRIPTION = "A free editable map of the whole world."
    const val TILE_JSON_ATTRIBUTION = "&copy; OpenStreetMap contributors, CC-BY-SA"
    const val TILE_JSON_MIN_ZOOM = 0
    const val TILE_JSON_MAX_ZOOM = 18

    const val OSM_RASTER_TILE_URL = "http://tile.openstreetmap.org/{z}/{x}/{y}.png"
    const val RASTER_TILE_SIZE_PIXELS = 256L

    val MERCATOR_BOUNDS = listOf(-180.0, -85.0, 180.0, 85.0)
    val CENTER_MAP_LOCATION = listOf(11.9, 57.7, 8.0)
  }
}