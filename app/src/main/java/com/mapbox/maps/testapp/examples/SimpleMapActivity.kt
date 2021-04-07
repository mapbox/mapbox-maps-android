package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.common.Logger
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.LineString
import com.mapbox.geojson.MultiPoint
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.distance
import com.mapbox.maps.extension.style.expressions.dsl.generated.gt
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import kotlinx.android.synthetic.main.activity_simple_map.*

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder().center(Point.fromLngLat(LONGITUDE, LATITUDE)).zoom(9.0).build()
        )
      }
      .loadStyle(
        styleExtension = style(Style.MAPBOX_STREETS) {
          // prepare blue marker from resources
          +image("marker") {
            bitmap(BitmapFactory.decodeResource(resources, R.drawable.blue_marker_view))
          }
          +geoJsonSource("source") {
            geometry(Point.fromLngLat(0.0, 0.0))
          }
          +symbolLayer("road-incident", "source") {
            iconImage("marker")
            iconAnchor(IconAnchor.BOTTOM)
          }
        }
      ) {
        updateFilter(it)
      }
  }

  private fun updateFilter(style: Style) {
    Logger.e("KIRYLDD", "1")
    val route = DirectionsResponse.fromJson(AnnotationUtils.loadStringFromAssets(
      this,
      "Rindermarkt-CalledeAlcalá.json"
    )).routes()[0]
    val incidentLayer = style.getLayer("road-incident") as? SymbolLayer
    Logger.e("KIRYLDD", "2 ${route.geometry()}")
    val filterExpression = gt {
      distance(
        // route.value.geometry() is checked for null above
        LineString.fromPolyline(route.geometry()!!, Constants.PRECISION_6)
        // that works
//        LineString.fromLngLats(MultiPoint.fromLngLats(listOf(
//          Point.fromLngLat(11.57502, 48.13636),
//          Point.fromLngLat(11.57467, 48.1362)
//        )))
      )
      literal(20L)
    }
    Logger.e("KIRYLDD", "3 ${filterExpression.toString().length}")

    // Set expression as filter on the incident layer
    incidentLayer?.filter(filterExpression)
    Logger.e("KIRYLDD", "4")
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
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}