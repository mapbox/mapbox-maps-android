package com.mapbox.maps.testapp.examples.linesandpolygons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
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
    mapView.getMapboxMap().setCamera(
      CameraOptions.Builder().center(
        Point.fromLngLat(
          LATITUDE,
          LONGITUDE
        )
      ).zoom(ZOOM).build()
    )
    mapView.getMapboxMap().loadStyle(
      (
        style(styleUri = Style.MAPBOX_STREETS) {
          +geoJsonSource(GEOJSON_SOURCE_ID) {
            url("asset://from_crema_to_council_crest.geojson")
          }
          +lineLayer("linelayer", GEOJSON_SOURCE_ID) {
            lineCap(LineCap.ROUND)
            lineJoin(LineJoin.ROUND)
            lineOpacity(0.7)
            lineWidth(8.0)
            lineColor("#888")
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
    private const val LATITUDE = -122.486052
    private const val LONGITUDE = 37.830348
    private const val ZOOM = 14.0
  }
}