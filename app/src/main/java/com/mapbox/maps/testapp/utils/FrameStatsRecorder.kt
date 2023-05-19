package com.mapbox.maps.testapp.utils

import android.os.SystemClock
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mapbox.common.Cancelable
import com.mapbox.maps.*
import com.mapbox.maps.plugin.lifecycle.lifecycle
import kotlin.math.pow
import kotlin.math.sqrt

class FrameStatsRecorder {
  private var renderFrameFinishCount = 0
  private var overtimeFrameCount = 0
  private val renderFrameIntervalsMs = mutableListOf<Float>()
  private val frameReport = mutableListOf<JsonObject>()
  private var startBenchmarkTime = 0L
  private var lastRenderedFrameTime = 0L
  private val renderFrameFinishedCancelable: Cancelable? = null
  private val renderFrameFinishedCallback = RenderFrameFinishedCallback {
    renderFrameFinishCount++
    val now = SystemClock.elapsedRealtimeNanos()
    if (lastRenderedFrameTime != 0L) {
      renderFrameIntervalsMs.add((now - lastRenderedFrameTime) * 1e-6f)
      frameReport.add(
        JsonObject().apply {
          addProperty(TIME_FROM_START, (now - startBenchmarkTime) * 1e-6f)
          addProperty(INTERVAL_FROM_LAST_FRAME, (now - lastRenderedFrameTime) * 1e-6f)
        }
      )
      if (now - lastRenderedFrameTime > 1000_000L * TARGET_FRAME_TIME_MS) {
        overtimeFrameCount++
      }
    }
    lastRenderedFrameTime = now
  }

  /**
   * Start benchmarking the MapView's frame stats.
   */
  fun startBenchmarking(mapView: MapView) {
    mapView.getMapboxMap().subscribeRenderFrameFinished(renderFrameFinishedCallback)
    startBenchmarkTime = SystemClock.elapsedRealtimeNanos()
  }

  /**
   * Stop benchmarking the MapView's frame stats, save stats to a file and toast the frame stats info.
   */
  fun stopBenchmarking(mapView: MapView) {
    val endBenchmarkTime = SystemClock.elapsedRealtimeNanos()
    StorageUtils(mapView.context).writeToFile(
      FRAME_LOG_JSON_NAME,
      Gson().toJson(frameReport)
    )
    Toast.makeText(
      mapView.context,
      """
              Average FPS: ${(renderFrameFinishCount * 1e9f / (endBenchmarkTime - startBenchmarkTime)).format()}
              Over time frames: $overtimeFrameCount
              Over time frames ratio: ${(overtimeFrameCount * 100f / renderFrameFinishCount).format()}%
              Max frame interval: ${renderFrameIntervalsMs.maxOrNull().format()}ms
              Min frame interval: ${renderFrameIntervalsMs.minOrNull().format()}ms
              Frame interval SD: ${renderFrameIntervalsMs.sd().format()}ms
              Logs have been saved to ${mapView.context.filesDir}/${StorageUtils.LOG_DIR}/$FRAME_LOG_JSON_NAME.
      """.trimIndent(),
      Toast.LENGTH_LONG
    ).show()
    renderFrameFinishedCancelable?.cancel()
  }

  private fun Number?.format(): String {
    return "%.2f".format(this)
  }

  private fun List<Float>.sd(): Double {
    val mean = average()
    return sqrt(
      fold(
        0.0,
        { accumulator, next -> accumulator + (next - mean).pow(2.0) }
      ) / size
    )
  }

  companion object {
    private const val TARGET_FRAME_TIME_MS = 1000f / 30f
    private const val FRAME_LOG_JSON_NAME = "frame_log.json"
    private const val TIME_FROM_START = "time_from_start"
    private const val INTERVAL_FROM_LAST_FRAME = "interval_from_last_frame"
  }
}

/**
 * Start recording Mapview's frame stats util the destroy of the MapView.
 */
fun MapView.recordFrameStats() {
  val frameStatsRecorder = FrameStatsRecorder()
  frameStatsRecorder.startBenchmarking(this)
  this.lifecycle.registerLifecycleObserver(
    this,
    object : MapboxLifecycleObserver {
      override fun onStart() {}

      override fun onStop() {}

      override fun onDestroy() {
        frameStatsRecorder.stopBenchmarking(this@recordFrameStats)
      }

      override fun onLowMemory() {}
    }
  )
}