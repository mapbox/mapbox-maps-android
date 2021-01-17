package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_style_vector_source.*

/**
 * Add a vector source to a map using an URL and visualize it with a line layer.
 */
class VectorTileSourceActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_style_vector_source)
    mapView.getMapboxMap().loadStyle(
      style(Style.LIGHT) {
        +vectorSource("terrain-data") {
          url("mapbox://mapbox.mapbox-terrain-v2")
        }
        +layerAtPosition(
          lineLayer("terrain-data", "terrain-data") {
            sourceLayer("contour")
            lineJoin(LineJoin.ROUND)
            lineCap(LineCap.ROUND)
            lineColor(Color.parseColor("#ff69b4"))
            lineWidth(1.9)
          },
          below = "road-label"
        )
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
}