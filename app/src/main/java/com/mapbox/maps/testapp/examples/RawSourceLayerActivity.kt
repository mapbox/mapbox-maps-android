package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.ValueConverter
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_simple_map.*

/**
 * Example showcasing raw source/layer json conversion support through the ValueConverter API.
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
    setContentView(R.layout.activity_simple_map)
    mapView.getMapboxMap().loadStyleUri(
      Style.MAPBOX_STREETS
    ) { addGeoJsonSource(it) }
  }

  private fun addGeoJsonSource(style: Style) {
    val source = ValueConverter.fromJson(
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

    val layer = ValueConverter.fromJson(
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
}