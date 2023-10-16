package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

/**
 * Example showcasing raw source/layer json conversion support through the Value API.
 *
 * Source:
 * ```
 * {
 *    "type": "geojson",
 *    "data": {
 *        "type": "Feature",
 *        "geometry": {
 *        "type": "Point",
 *          "coordinates": [-77.032667, 38.913175]
 *        },
 *        "properties": {
 *          "title": "Mapbox DC",
 *          "marker-symbol": "monument"
 *        }
 *    }
 * }
 * ```
 *
 * Layer:
 * ```
 * {
 *    "id": "custom",
 *    "type": "circle",
 *    "source": "source",
 *    "source-layer": "",
 *    "layout": {},
 *    "paint": {
 *        "circle-radius": 20,
 *        "circle-color": "#FF3300",
 *        "circle-pitch-alignment": "map"
 *    }
 * }
 * ```
 */
class RawSourceLayerActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapView.mapboxMap.apply {
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(-77.032667, 38.913175))
          .zoom(16.0)
          .build()
      )
      loadStyle(Style.STANDARD) { addGeoJsonSource(it) }
    }
  }

  private fun addGeoJsonSource(style: Style) {
    val source = Value.fromJson(
      """
        {
          "type": "geojson",
          "data": {
            "type": "Feature",
            "geometry": {
              "type": "Point",
              "coordinates": [-77.032667, 38.913175]
            },
            "properties": {
              "title": "Mapbox Garage",
              "marker-symbol": "monument"
            }
          }
        }
      """.trimIndent()
    )

    if (source.isError) {
      throw RuntimeException("Invalid GeoJson:" + source.error)
    }

    val expected = style.addStyleSource("source", source.value!!)
    if (expected.isError) {
      throw RuntimeException("Invalid GeoJson:" + expected.error)
    }

    val layer = Value.fromJson(
      """
        {
            "id": "custom",
            "type": "circle",
            "source": "source",
            "source-layer": "",
            "layout": {},
            "paint": {
                "circle-radius": 20,
                "circle-color": "#FF3300",
                "circle-pitch-alignment": "map"
            }
        }
      """.trimIndent()
    )

    if (layer.isError) {
      throw RuntimeException("Invalid GeoJson:" + layer.error)
    }

    val expectedLayer = style.addStyleLayer(layer.value!!, null)

    if (expectedLayer.isError) {
      throw RuntimeException("Invalid GeoJson:" + expectedLayer.error)
    }
  }
}