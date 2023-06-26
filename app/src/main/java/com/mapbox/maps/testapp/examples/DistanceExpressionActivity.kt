package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.lt
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfTransformation

/**
 * An Activity that showcases the within expression to filter features outside a geometry
 */
class DistanceExpressionActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    val center = Point.fromLngLat(LON, LAT)
    val circle = TurfTransformation.circle(center, RADIUS, TurfConstants.UNIT_METERS)

    // Setup camera position above Georgetown
    mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(center).zoom(16.0).build())

    mapView.getMapboxMap().loadStyle(
      style(Style.STANDARD) {
        +geoJsonSource(POINT_ID) {
          geometry(center)
        }
        +geoJsonSource(CIRCLE_ID) {
          geometry(circle)
        }
        +layerAtPosition(
          fillLayer(CIRCLE_ID, CIRCLE_ID) {
            fillOpacity(0.5)
            fillColor("#3bb2d0")
          },
          below = POI_LABEL
        )
      }
    ) {
      val symbolLayer = it.getLayer("poi-label") as SymbolLayer
      symbolLayer.filter(
        lt {
          distance(Point.fromLngLat(LON, LAT))
          literal(150)
        }
      )
      // Hide other types of labels to highlight POI labels
      (it.getLayer(ROAD_LABEL) as SymbolLayer).visibility(Visibility.NONE)
      (it.getLayer(TRANSIT_LABEL) as SymbolLayer).visibility(Visibility.NONE)
      (it.getLayer(ROAD_NUMBER_SHIELD) as SymbolLayer).visibility(Visibility.NONE)
    }
  }

  companion object {
    const val POINT_ID = "point"
    const val CIRCLE_ID = "circle"
    const val LAT = 37.78794572301525
    const val LON = -122.40752220153807
    const val RADIUS = 150.0
    const val POI_LABEL = "poi-label"
    const val ROAD_LABEL = "road-label"
    const val TRANSIT_LABEL = "transit-label"
    const val ROAD_NUMBER_SHIELD = "road-number-shield"
  }
}