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
import com.mapbox.maps.testapp.databinding.ActivityStyleVectorSourceBinding

/**
 * Add a vector source to a map using an URL and visualize it with a line layer.
 */
class VectorTileSourceActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityStyleVectorSourceBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.getMapboxMap().loadStyle(
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
}