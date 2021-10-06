package com.mapbox.maps.testapp.examples

import android.content.Context
import android.os.SystemClock
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mapbox.maps.MapEvents
import com.mapbox.maps.MapView
import com.mapbox.maps.Observer
import com.mapbox.maps.testapp.utils.StorageUtils
import kotlin.math.pow
import kotlin.math.sqrt

class PerformanceRecorder(private val mapView: MapView, private val TARGET_FRAME_TIME_MS: Float) {
  var renderFrameFinishCount = 0
    private set
  var overtimeFrameCount = 0
    private set
  var startBenchmarkTime = 0L
    private set
  var lastRenderedFrameTime = 0L
    private set
  var endBenchmarkTime: Long? = null
    private set
  private var started = false

  private val renderFrameIntervalsMs = mutableListOf<Float>()
  fun getRenderFrameIntervalsMs() = mutableListOf<Float>().apply { this.addAll(renderFrameIntervalsMs) }

  private val frameReport = mutableListOf<JsonObject>()
  fun getFrameReport() = mutableListOf<JsonObject>().apply { this.addAll(frameReport) }

  private val renderFrameObserver = Observer { event ->
    if (event.type == MapEvents.RENDER_FRAME_FINISHED) {
      renderFrameFinishCount++
      val now = SystemClock.elapsedRealtimeNanos()
      if (lastRenderedFrameTime != 0L) {
        renderFrameIntervalsMs.add((now - lastRenderedFrameTime) * 1e-6f)
        frameReport.add(
          JsonObject().apply {
            addProperty("time_from_start", (now - startBenchmarkTime) * 1e-6f)
            addProperty("interval_from_last_frame", (now - lastRenderedFrameTime) * 1e-6f)
          }
        )
        if (now - lastRenderedFrameTime > 1000_000L * TARGET_FRAME_TIME_MS) {
          overtimeFrameCount++
        }
      }
      lastRenderedFrameTime = now
    }
  }

  fun startRecording() {
    if (started) {
      return
    } else {
      started = true
    }

    mapView.getMapboxMap().subscribe(
      renderFrameObserver,
      listOf(MapEvents.RENDER_FRAME_FINISHED)
    )
    startBenchmarkTime = SystemClock.elapsedRealtimeNanos()
  }

  fun stopRecording(): String {
    onDestroy()
    endBenchmarkTime = SystemClock.elapsedRealtimeNanos()
    return toString()
  }

  fun writeToFile(context: Context, fileName: String) {
    StorageUtils(context).writeToFile(fileName, Gson().toJson(frameReport))
  }

  override fun toString() = """
              Average FPS: ${(renderFrameFinishCount * 1e9f / (endBenchmarkTime!! - startBenchmarkTime)).format()}
              Over time frames: $overtimeFrameCount
              Over time frames ratio: ${(overtimeFrameCount * 100f / renderFrameFinishCount).format()}%
              Max frame interval: ${renderFrameIntervalsMs.maxOrNull().format()}ms
              Min frame interval: ${renderFrameIntervalsMs.minOrNull().format()}ms
              Frame interval SD: ${renderFrameIntervalsMs.standardDeviation().format()}ms
          """.trimIndent()

  fun onDestroy() {
    mapView.getMapboxMap().unsubscribe(renderFrameObserver)
  }

  private fun Number?.format(): String {
    return "%.2f".format(this)
  }

  private fun List<Float>.standardDeviation(): Double {
    val mean = average()
    return sqrt(
      fold(
        0.0,
        { accumulator, next -> accumulator + (next - mean).pow(2.0) }
      ) / size
    )
  }
}