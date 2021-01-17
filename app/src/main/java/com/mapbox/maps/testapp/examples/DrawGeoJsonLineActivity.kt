package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.item_map.*

/**
 * Draw a polyline by parsing a GeoJSON file with the Mapbox Android SDK.
 */
class DrawGeoJsonLineActivity : AppCompatActivity() {

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dds_draw_geojson_line)
    mapView.getMapboxMap().loadStyle(
      (
        style(styleUri = Style.MAPBOX_STREETS) {
          +geoJsonSource(GEOJSON_SOURCE_ID) {
            url("asset://from_crema_to_council_crest.geojson")
          }
          +lineLayer("linelayer", GEOJSON_SOURCE_ID) {
            lineCap(LineCap.SQUARE)
            lineJoin(LineJoin.MITER)
            lineOpacity(0.7)
            lineWidth(7.0)
            lineColor("#3bb2d0")
          }
        }
        )
    )
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  companion object {
    private const val GEOJSON_SOURCE_ID = "line"
  }
}