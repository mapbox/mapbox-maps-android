package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_line_gradient.*

class LineGradientActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_line_gradient)
    mapView.getMapboxMap().loadStyle(createStyle()) {
      Logger.d(TAG, "Style loaded: ${it.styleURI}")
    }
  }

  private fun createStyle() = style(styleUri = Style.TRAFFIC_DAY) {
    +geoJsonSource(id = SOURCE_ID) {
      feature(Feature.fromGeometry(LineString.fromLngLats(POINTS)))
      lineMetrics(true)
    }
    +lineLayer(LAYER_ID, SOURCE_ID) {
      lineCap(LineCap.ROUND)
      lineJoin(LineJoin.ROUND)
      lineWidth(LINE_WIDTH)
      lineGradient(
        interpolate {
          linear()
          lineProgress()
          // blue
          stop { literal(0.0); rgb { literal(6); literal(1); literal(255) } }
          // royal blue
          stop { literal(0.1); rgb { literal(59); literal(118); literal(227) } }
          // cyan
          stop { literal(0.3); rgb { literal(7); literal(238); literal(251) } }
          // lime
          stop { literal(0.5); rgb { literal(0); literal(255); literal(42) } }
          // yellow
          stop { literal(0.7); rgb { literal(255); literal(252); literal(0) } }
          // red
          stop { literal(1.0); rgb { literal(255); literal(30); literal(0) } }
        }
      )
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

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  companion object {
    private const val TAG = "LineGradientActivity"
    private const val LAYER_ID = "layer-id"
    private const val SOURCE_ID = "source-id"
    private const val LINE_WIDTH = 14.0
    private val POINTS = listOf(
      Point.fromLngLat(-77.044211, 38.852924),
      Point.fromLngLat(-77.045659, 38.860158),
      Point.fromLngLat(-77.044232, 38.862326),
      Point.fromLngLat(-77.040879, 38.865454),
      Point.fromLngLat(-77.039936, 38.867698),
      Point.fromLngLat(-77.040338, 38.86943),
      Point.fromLngLat(-77.04264, 38.872528),
      Point.fromLngLat(-77.03696, 38.878424),
      Point.fromLngLat(-77.032309, 38.87937),
      Point.fromLngLat(-77.030056, 38.880945),
      Point.fromLngLat(-77.027645, 38.881779),
      Point.fromLngLat(-77.026946, 38.882645),
      Point.fromLngLat(-77.026942, 38.885502),
      Point.fromLngLat(-77.028054, 38.887449),
      Point.fromLngLat(-77.02806, 38.892088),
      Point.fromLngLat(-77.03364, 38.892108),
      Point.fromLngLat(-77.033643, 38.899926)
    )
  }
}