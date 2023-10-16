package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer

/**
 * Example showcasing raw geojson conversion support through the Value API.
 * This converts the following geojson to a value object:
 * ```
 * {
 *  "type": "FeatureCollection",
 *  "features": [
 *    {
 *      "type": "Feature",
 *      "properties": {},
 *      "geometry": {
 *        "type": "Point",
 *        "coordinates": [
 *          6.0033416748046875,
 *          43.70908256335716
 *        ]
 *      }
 *    }
 *   ]
 * }
 * ```
 */
class RawGeoJsonActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.mapboxMap.apply {
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(6.0033416748046875, 43.70908256335716))
          .zoom(16.0)
          .build()
      )
      loadStyle(Style.STANDARD) { addGeoJsonSource(it) }
    }
  }

  private fun addGeoJsonSource(style: Style) {
    val geojson = Value.fromJson(
      """
      {
        "type": "FeatureCollection",
        "features": [
          {
            "type": "Feature",
            "properties": {},
            "geometry": {
              "type": "Point",
              "coordinates": [
                6.0033416748046875,
                43.70908256335716
              ]
            }
          }
        ]
      }
      """.trimIndent()
    )

    if (geojson.isError) {
      throw RuntimeException("Invalid GeoJson:" + geojson.error)
    }

    val sourceParams = HashMap<String, Value>()
    sourceParams["type"] = Value("geojson")
    sourceParams["data"] = geojson.value!!
    val expected = style.addStyleSource("source", Value(sourceParams))

    if (expected.isError) {
      throw RuntimeException("Invalid GeoJson:" + expected.error)
    }

    style.addLayer(
      circleLayer("circle", "source") {
        circleColor(Color.BLACK)
        circleRadius(10.0)
      }
    )
  }
}