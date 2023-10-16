package com.mapbox.maps.testapp.utils

import android.content.Context
import android.os.SystemClock
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mapbox.common.Cancelable
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxLifecycleObserver
import com.mapbox.maps.RenderFrameFinishedCallback
import com.mapbox.maps.logI
import com.mapbox.maps.plugin.lifecycle.lifecycle
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.math.pow
import kotlin.math.sqrt

class FrameStatsRecorder {
  private var writeSummaryJob: Job = Job().apply { cancel() }
  private var renderFrameFinishCount = 0
  private var overtimeFrameCount = 0
  private val renderFrameIntervalsMs = mutableListOf<Float>()
  private val frameReport = mutableListOf<JsonObject>()
  private var startBenchmarkTime = 0L
  private var lastRenderedFrameTime = 0L
  private var renderFrameFinishedCancelable = Cancelable {}
  private val renderFrameFinishedCallback = RenderFrameFinishedCallback {
    renderFrameFinishCount++
    val now = SystemClock.elapsedRealtimeNanos()
    if (lastRenderedFrameTime != 0L) {
      val renderFrameIntervalMs = (now - lastRenderedFrameTime) * 1e-6f
      renderFrameIntervalsMs.add(renderFrameIntervalMs)
      frameReport.add(
        JsonObject().apply {
          addProperty(TIME_FROM_START, (now - startBenchmarkTime) * 1e-6f)
          addProperty(INTERVAL_FROM_LAST_FRAME, renderFrameIntervalMs)
        }
      )
      if (renderFrameIntervalMs > TARGET_FRAME_TIME_MS) {
        overtimeFrameCount++
      }
    }
    lastRenderedFrameTime = now
  }

  /**
   * Start or resume benchmarking the MapView's frame stats.
   */
  fun startResumeBenchmarking(mapView: MapView) {
    // Make sure we've finished writing any previous stats
    cancelAndWaitWrite()
    renderFrameFinishedCancelable = mapView.mapboxMap.subscribeRenderFrameFinished(renderFrameFinishedCallback)
    startBenchmarkTime = SystemClock.elapsedRealtimeNanos()
    lastRenderedFrameTime = 0L
  }

  private fun cancelAndWaitWrite() {
    renderFrameFinishedCancelable.cancel()
    runBlocking {
      writeSummaryJob.join()
    }
  }

  /**
   * Stop benchmarking the MapView's frame stats, save stats to a file and toast the frame stats info.
   */
  fun stopBenchmarking(mapView: MapView) {
    val endBenchmarkTime = SystemClock.elapsedRealtimeNanos()
    renderFrameFinishedCancelable.cancel()
    writeSummary(mapView.context.applicationContext, endBenchmarkTime)
  }

  @OptIn(DelicateCoroutinesApi::class)
  private fun writeSummary(context: Context, endBenchmarkTime: Long) {
    writeSummaryJob = GlobalScope.launch(Dispatchers.IO) {
      StorageUtils(context).writeToFile(
        FRAME_LOG_JSON_NAME,
        Gson().toJson(frameReport)
      )
      val stats = """
                Average FPS: ${(renderFrameFinishCount * 1e9f / (endBenchmarkTime - startBenchmarkTime)).format()}
                Over time frames: $overtimeFrameCount
                Over time frames ratio: ${(overtimeFrameCount * 100f / renderFrameFinishCount).format()}%
                Max frame interval: ${renderFrameIntervalsMs.maxOrNull().format()}ms
                Min frame interval: ${renderFrameIntervalsMs.minOrNull().format()}ms
                Frame interval SD: ${renderFrameIntervalsMs.sd().format()}ms
                Logs have been saved to ${context.filesDir}/${StorageUtils.LOG_DIR}/$FRAME_LOG_JSON_NAME.
        """.trimIndent()
      logI(TAG, stats)
      withContext(Dispatchers.Main) {
        Toast.makeText(context, stats, Toast.LENGTH_LONG).show()
      }
    }
  }

  private fun Number?.format(): String {
    return "%.2f".format(this)
  }

  private fun List<Float>.sd(): Double {
    val mean = average()
    val variance = fold(0.0) { accumulator, next ->
      accumulator + (next - mean).pow(2.0)
    } / size
    return sqrt(variance)
  }

  companion object {
    private const val TARGET_FRAME_TIME_MS = 1000f / 30f
    private const val FRAME_LOG_JSON_NAME = "frame_log.json"
    private const val TIME_FROM_START = "time_from_start"
    private const val INTERVAL_FROM_LAST_FRAME = "interval_from_last_frame"
    private const val TAG = "FrameStatsRecorder"
  }
}

/**
 * Start recording Mapview's frame stats util the destroy of the MapView.
 */
fun MapView.recordFrameStats() {
  val frameStatsRecorder = FrameStatsRecorder()
  frameStatsRecorder.startResumeBenchmarking(this)
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