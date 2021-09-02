package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

/**
 * Example showcasing raw source/layer json conversion support through the Value API.
 *
 * Source:
 * ```
 * {
 * "type": "geojson",
 *    "data": {
 *    "type": "Feature",
 *        "geometry": {
 *            "type": "Point",
 *            "coordinates": [-77.0323, 38.9131]
 *        },
 *        "properties": {
 *            "title": "Mapbox DC",
 *            "marker-symbol": "monument"
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
 *    "paint": {}
 * }
 * ```
 */
class RawSourceLayerActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapView.getMapboxMap().loadStyleUri(
      Style.MAPBOX_STREETS
    ) { addGeoJsonSource(it) }
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
              "coordinates": [-77.0323, 38.9131]
            },
            "properties": {
              "title": "Mapbox DC",
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
            "paint": {}
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