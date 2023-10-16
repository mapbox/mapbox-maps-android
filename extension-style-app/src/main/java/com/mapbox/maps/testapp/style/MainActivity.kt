package com.mapbox.maps.testapp.style

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.style

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapView.mapboxMap.loadStyle(
      styleExtension = style(Style.LIGHT) {
        +lineLayer("line-layer", "line-source") {
          lineColor(
            interpolate {
              linear()
              heatmapDensity()
              stop {
                literal(0)
                rgba(33.0, 102.0, 172.0, 0.0)
              }
              stop {
                literal(0.2)
                rgb(103.0, 169.0, 207.0)
              }
              stop {
                literal(0.4)
                rgb(209.0, 229.0, 240.0)
              }
              stop {
                literal(0.6)
                rgb(253.0, 219.0, 240.0)
              }
              stop {
                literal(0.8)
                rgb(239.0, 138.0, 98.0)
              }
              stop {
                literal(1)
                rgb(178.0, 24.0, 43.0)
              }
            }
          )
        }
      }
    )
  }
}