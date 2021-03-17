package com.mapbox.maps.testapp.examples.camera

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_restrict_bounds.*

/**
 * Test activity showcasing fitting camera to a geometry..
 */
class FitCameraToGeometryActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    mapboxMap = mapView.getMapboxMap()
    val polygon = Polygon.fromLngLats(POINTS)
    mapboxMap.loadStyle(
      style(Style.MAPBOX_STREETS) {
        +geoJsonSource(SOURCE_ID) {
          feature(Feature.fromGeometry(polygon))
        }
        +fillLayer(LAYER_ID, SOURCE_ID) {
          fillOpacity(0.5)
          fillColor(Color.GRAY)
        }
      }
    ) {
      val cameraOption =
        mapboxMap.cameraForGeometry(polygon, EdgeInsets(0.0, 0.0, 0.0, 0.0), null, null)
      mapboxMap.easeTo(cameraOption)
    }
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  companion object {
    private const val SOURCE_ID = "source_id"
    private const val LAYER_ID = "layer_id"
    private val POINTS = listOf(
      listOf<Point>(
        Point.fromLngLat(-2.938070297241211, 43.274580742195845),
        Point.fromLngLat(-2.9680252075195312, 43.258768377941465),
        Point.fromLngLat(-2.912750244140625, 43.24063848114794),
        Point.fromLngLat(-2.938070297241211, 43.274580742195845)
      )
    )
  }
}