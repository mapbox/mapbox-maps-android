package com.mapbox.maps.testapp.examples.linesandpolygons

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.expressions.dsl.generated.step
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.databinding.ActivityLineGradientBinding
import kotlin.math.pow
import kotlin.math.sqrt

class LineGradientActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityLineGradientBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.getMapboxMap().loadStyle(createStyle()) { style ->
      binding.benchmarkOffsetButton.setOnClickListener {
        startBenchmarkTrimOffset(style)
      }
      binding.benchmarkExpressionButton.setOnClickListener {
        startBenchmarkExpression(style)
      }
    }
  }

  private fun startBenchmarkTrimOffset(style: Style) {
    val intervals = mutableListOf<Long>()
    val lineLayer = style.getLayerAs<LineLayer>(LAYER_ID)!!
    resetInitialLineGradient(lineLayer)
    repeat(100000) {
      val startTime = SystemClock.elapsedRealtimeNanos()
      lineLayer.lineTrimOffset(listOf(0.0, (it % 2) * 0.1))
      intervals.add(SystemClock.elapsedRealtimeNanos() - startTime)
    }
    Toast.makeText(
      this,
      """
              Using line trim offset:
              Average time: ${intervals.average().format()}
              Max time: ${intervals.maxOrNull().format()}
              Min time: ${intervals.minOrNull().format()}
              Time SD: ${intervals.sd().format()}
      """.trimIndent(),
      Toast.LENGTH_LONG
    ).show()
  }

  private fun startBenchmarkExpression(style: Style) {
    val intervals = mutableListOf<Long>()
    val lineLayer = style.getLayerAs<LineLayer>(LAYER_ID)!!
    resetInitialLineGradient(lineLayer)
    repeat(100000) {
      val startTime = SystemClock.elapsedRealtimeNanos()
      lineLayer.lineGradient(if (it % 2 == 0) defaultExpression else trimOffsetExpression)
      intervals.add(SystemClock.elapsedRealtimeNanos() - startTime)
    }
    Toast.makeText(
      this,
      """
              Using line gradient expression:
              Average time: ${intervals.average().format()}
              Max time: ${intervals.maxOrNull().format()}
              Min time: ${intervals.minOrNull().format()}
              Time SD: ${intervals.sd().format()}
      """.trimIndent(),
      Toast.LENGTH_LONG
    ).show()
  }

  private fun resetInitialLineGradient(lineLayer: LineLayer) {
    lineLayer.lineTrimOffset(listOf(0.0, 0.0))
    lineLayer.lineGradient(defaultExpression)
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
      lineGradient(defaultExpression)
    }
  }

  private fun Long?.format(): String {
    return "${this!! * 1e-6f} ms"
  }

  private fun Double.format(): String {
    return "%.2f ms".format(this * 1e-6f)
  }

  private fun List<Long>.sd(): Double {
    val mean = average()
    return sqrt(
      fold(
        0.0
      ) { accumulator, next -> accumulator + (next - mean).pow(2.0) } / size
    )
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
    private val defaultExpression = step {
      lineProgress()
      rgba { literal(255); literal(255); literal(255); literal(0) }
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
    private val trimOffsetExpression = step {
      lineProgress()
      rgba { literal(255); literal(255); literal(255); literal(0) }
      // blue
      stop { literal(0.0); rgba { literal(255); literal(255); literal(255); literal(0) } }
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
  }
}