package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.*
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.within
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.getCameraAnimationsPlugin
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_within_expression.*

/**
 * An Activity that showcases the within expression to filter features outside a geometry
 */
class WithinExpressionActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_within_expression)

    val center = Point.fromLngLat(LON, LAT)

    // Setup camera position above Georgetown
    mapView.getMapboxMap().jumpTo(CameraOptions.Builder().center(center).zoom(15.5).build())

    // Assume the route is represented by an array of coordinates.
    val coordinates = listOf<Point>(
      Point.fromLngLat(
        -77.06866264343262,
        38.90506061276737
      ),
      Point.fromLngLat(
        -77.06283688545227,
        38.905194197410545
      ),
      Point.fromLngLat(
        -77.06285834312439,
        38.906429843444094
      ),
      Point.fromLngLat(
        -77.0630407333374,
        38.90680554236621
      )
    )

    // Create buffer around linestring
    val bufferedRouteGeometry = bufferLineStringGeometry()

    mapView.getMapboxMap().loadStyle(
      style(Style.MAPBOX_STREETS) {
        +geoJsonSource(POINT_ID) {
          geometry(LineString.fromLngLats(coordinates))
        }
        +geoJsonSource(FILL_ID) {
          featureCollection(FeatureCollection.fromFeature(Feature.fromGeometry(bufferedRouteGeometry)))
          buffer(0)
          tolerance(0.0)
        }
        +layerAtPosition(
          lineLayer(LINE_ID, POINT_ID) {
            lineWidth(7.5)
            lineColor(Color.LTGRAY)
          },
          below = POI_LABEL
        )
      }
    ) { // Add fill layer to represent buffered LineString
      it.addLayerBelow(
        fillLayer(FILL_ID, FILL_ID) {
          fillOpacity(0.12)
          fillColor(Color.YELLOW)
        },
        LINE_ID
      )

      // Move to a new camera position
      mapView.getCameraAnimationsPlugin().easeTo(
        CameraOptions.Builder()
          .zoom(16.0)
          .center(Point.fromLngLat(-77.06535338052844, 38.905156245642814))
          .bearing(80.68015859462369)
          .pitch(55.0)
          .build(),
        mapAnimationOptions { duration = 1750 }
      )

      // Show only POI labels inside geometry using within expression
      val symbolLayer = it.getLayer(POI_LABEL) as SymbolLayer
      symbolLayer.filter(within(bufferedRouteGeometry))

      // Hide other types of labels to highlight POI labels
      (it.getLayer(ROAD_LABEL) as SymbolLayer).visibility(
        Visibility.NONE
      )
      (it.getLayer(TRANSIT_LABEL) as SymbolLayer).visibility(
        Visibility.NONE
      )
      (it.getLayer(ROAD_NUMBER_SHIELD) as SymbolLayer).visibility(
        Visibility.NONE
      )
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

  private fun bufferLineStringGeometry(): Polygon {
    // TODO replace static data by Turf#Buffer: mapbox-java/issues/987
    return FeatureCollection.fromJson(
      """
    {
      "type": "FeatureCollection",
      "features": [
        {
          "type": "Feature",
          "properties": {},
          "geometry": {
            "type": "Polygon",
            "coordinates": [
              [
                [
                  -77.06867337226866,
                  38.90467655551809
                ],
                [
                  -77.06233263015747,
                  38.90479344272695
                ],
                [
                  -77.06234335899353,
                  38.906463238984344
                ],
                [
                  -77.06290125846863,
                  38.907206285691615
                ],
                [
                  -77.06364154815674,
                  38.90684728656818
                ],
                [
                  -77.06326603889465,
                  38.90637140121084
                ],
                [
                  -77.06321239471436,
                  38.905561553883246
                ],
                [
                  -77.0691454410553,
                  38.905436318935635
                ],
                [
                  -77.06912398338318,
                  38.90466820642439
                ],
                [
                  -77.06867337226866,
                  38.90467655551809
                ]
              ]
            ]
          }
        }
      ]
    }
      """.trimIndent()
    ).features()!![0].geometry() as Polygon
  }

  companion object {
    const val POINT_ID = "point"
    const val FILL_ID = "fill"
    const val LINE_ID = "line"
    const val LAT = 38.90628988399711
    const val LON = -77.06574689337494
    const val POI_LABEL = "poi-label"
    const val ROAD_LABEL = "road-label"
    const val TRANSIT_LABEL = "transit-label"
    const val ROAD_NUMBER_SHIELD = "road-number-shield"
  }
}