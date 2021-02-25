package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_add_marker_symbol.*

/**
 * Example showing how to add a marker on map with symbol layer
 */
class AddOneMarkerSymbolActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_marker_symbol)

    mapView.getMapboxMap().also {
      it.jumpTo(
        CameraOptions.Builder()
          .center(Point.fromLngLat(12.550343, 55.665957))
          .zoom(8.0)
          .build()
      )
    }.loadStyle(
      styleExtension = style(Style.MAPBOX_STREETS) {
        // prepare blue marker from resources
        +image(BLUE_ICON_ID) {
          bitmap(BitmapFactory.decodeResource(resources, R.drawable.blue_marker_view))
        }
        +geoJsonSource(SOURCE_ID) {
          geometry(Point.fromLngLat(12.550343, 55.665957))
        }

        +symbolLayer(LAYER_ID, SOURCE_ID) {
          iconImage(BLUE_ICON_ID)
          iconAnchor(IconAnchor.BOTTOM)
        }
      }
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

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  companion object {
    private const val BLUE_ICON_ID = "blue"
    private const val SOURCE_ID = "source_id"
    private const val LAYER_ID = "layer_id"
  }
}