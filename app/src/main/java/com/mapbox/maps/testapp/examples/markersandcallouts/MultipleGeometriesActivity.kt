//package com.mapbox.maps.testapp.examples.markersandcallouts
//
//import android.graphics.Color
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.mapbox.common.Logger
//import com.mapbox.maps.MapboxMap
//import com.mapbox.maps.Style
//import com.mapbox.maps.extension.style.expressions.dsl.generated.eq
//import com.mapbox.maps.extension.style.layers.addLayer
//import com.mapbox.maps.extension.style.layers.generated.*
//import com.mapbox.maps.extension.style.sources.addSource
//import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
//import com.mapbox.maps.testapp.databinding.ActivityMultipleGeometriesBinding
//import java.net.URISyntaxException
//
///**
// * Example showing drawing several different geometries for one source.
// */
//class MultipleGeometriesActivity : AppCompatActivity() {
//
//  private lateinit var mapboxMap: MapboxMap
//
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    val binding = ActivityMultipleGeometriesBinding.inflate(layoutInflater)
//    setContentView(binding.root)
//
//    mapboxMap = binding.mapView.getMapboxMap()
//    mapboxMap.loadStyleUri(
//      Style.LIGHT
//    ) {
//      createGeoJsonSource(it)
//      addPolygonLayer(it)
//      addLineStringLayer(it)
//      addPointLayer(it)
//    }
//  }
//
//  private fun createGeoJsonSource(loadedMapStyle: Style) {
//    try {
//      // Load data from GeoJSON file in the assets folder
//      loadedMapStyle.addSource(
//        geoJsonSource(GEOJSON_SOURCE_ID) {
//          url(GEOJSON_SOURCE_URL)
//        }
//      )
//    } catch (exception: URISyntaxException) {
//      Logger.e(TAG, "Creating geojson source failed ${exception.message}")
//    }
//  }
//
//  private fun addPolygonLayer(loadedMapStyle: Style) {
//    // Create and style a FillLayer that uses the Polygon Feature's coordinates in the GeoJSON data
//    loadedMapStyle.addLayer(
//      fillLayer(POLYGON_LAYER_ID, GEOJSON_SOURCE_ID) {
//        fillColor(Color.parseColor("#4469f7"))
//        fillOpacity(POLYGON_OPACITY)
//        filter(
//          eq {
//            literal("\$type")
//            literal("Polygon")
//          }
//        )
//      }
//    )
//  }
//
//  private fun addLineStringLayer(loadedMapStyle: Style) {
//    // Create and style a LineLayer that uses the Line String Feature's coordinates in the GeoJSON data
//    loadedMapStyle.addLayer(
//      lineLayer(LINE_LAYER_ID, GEOJSON_SOURCE_ID) {
//        lineColor(Color.RED)
//        lineWidth(LINE_WIDTH)
//        filter(
//          eq {
//            literal("\$type")
//            literal("LineString")
//          }
//        )
//      }
//    )
//  }
//
//  private fun addPointLayer(loadedMapStyle: Style) {
//    // Create and style a Circle layer that uses the Point Feature's coordinates in the GeoJSON data
//    loadedMapStyle.addLayer(
//      circleLayer(CIRCLE_LAYER_ID, GEOJSON_SOURCE_ID) {
//        filter(
//          eq {
//            literal("\$type")
//            literal("Point")
//          }
//        )
//        circleColor(Color.RED)
//        circleRadius(CIRCLE_RADIUS)
//        circleStrokeWidth(CIRCLE_STROKE_WIDTH)
//        circleStrokeColor(Color.BLACK)
//      }
//    )
//  }
//
//  companion object {
//    private val TAG = MultipleGeometriesActivity::class.java.simpleName
//    private const val GEOJSON_SOURCE_ID = "geojson_sample"
//    private const val CIRCLE_LAYER_ID = "circle-layer"
//    private const val LINE_LAYER_ID = "line_string"
//    private const val POLYGON_LAYER_ID = "polygon"
//    private const val GEOJSON_SOURCE_URL = "asset://multiple_geometry_example.geojson"
//    private const val LINE_WIDTH = 2.0
//    private const val CIRCLE_RADIUS = 6.0
//    private const val CIRCLE_STROKE_WIDTH = 2.0
//    private const val POLYGON_OPACITY = 0.3
//  }
//}

package com.mapbox.maps.testapp.examples.markersandcallouts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.CustomGeometrySource
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.testapp.databinding.ActivityMultipleGeometriesBinding
import com.mapbox.maps.testapp.examples.waitForLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Example showing drawing several different geometries for one source.
 */
class MultipleGeometriesActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap

  private var customGeometrySource: CustomGeometrySource? = null
  private var points = mutableListOf<Point>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityMultipleGeometriesBinding.inflate(layoutInflater)
    setContentView(binding.root)
    Logger.d("KIRYLDD"," on create multiple")
    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.loadStyleUri(
      Style.LIGHT
    ) {
      setupCustomGeometrySource(it)
    }

    binding.mapView.waitForLayout {
      CoroutineScope(Dispatchers.IO).launch {
        var i = 0
        var lat = START_LATITUDE
        var lon = START_LONGITUDE
        var direction = 1
        while (true) {

          if (i % 10 == 0) {
            lat += .002
            direction *= -1
          } else {
            lon += .002 * direction
          }
          points.add(Point.fromLngLat(lon, lat))
          customGeometrySource?.invalidRegion(CoordinateBounds.world())
          delay(1000)
          i++
        }
      }
    }

    binding.fab.setOnClickListener {
      updateLayer()
    }
  }

  private fun setupCustomGeometrySource(loadedMapStyle: Style) {
    val options = CustomGeometrySourceOptions.Builder()
      .cancelTileFunction {}
      .fetchTileFunction { tileId ->
        fetchTiles(tileId)
      }
      .tileOptions(
        TileOptions.Builder()
          .buffer(4.toShort())
          .clip(true)
          .tolerance(1.0)
          .build()
      )
      .build()
    val source = CustomGeometrySource(CUSTOM_SOURCE_ID, options)
    val layer = LineLayer(CUSTOM_LAYER_ID, CUSTOM_SOURCE_ID).apply {
      lineCap(LineCap.ROUND)
      lineColor("#F242F2")
      lineJoin(LineJoin.ROUND)
      lineWidth(literal(3.0))
    }

    customGeometrySource = source
    loadedMapStyle.addSource(source)
    loadedMapStyle.addLayer(layer)
  }

  private fun fetchTiles(tileId: CanonicalTileID) {
    if (points.isEmpty()) return

    val feature = Feature.fromGeometry(LineString.fromLngLats(points))

    customGeometrySource?.apply {
      try {
        setTileData(tileId, mutableListOf(feature))
      } catch (e: Exception) {
        Log.e(TAG, "Exception when setting tile data.", e)
      }
    }
  }

  private fun updateLayer() {
    mapboxMap.triggerRepaint()
  }

  companion object {
    private val TAG = MultipleGeometriesActivity::class.java.simpleName

    private const val START_LATITUDE = 38.897435
    private const val START_LONGITUDE = -77.039679
    private const val CUSTOM_SOURCE_ID = "zigzag-source"
    private const val CUSTOM_LAYER_ID = "zigzag-layer"
  }
}