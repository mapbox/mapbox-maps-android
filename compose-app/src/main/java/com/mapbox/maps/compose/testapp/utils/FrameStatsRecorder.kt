package com.mapbox.maps.compose.testapp.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.DisposableMapEffect
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.logI
import com.mapbox.maps.renderer.RenderThreadRecorder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ceil

/**
 * Utility class to record [MapView] frame stats.
 */
public class FrameStatsRecorder {

  private lateinit var recorder: RenderThreadRecorder
  private var writeSummaryJob: Job = Job().apply { cancel() }

  public fun register(mapView: MapView) {
    recorder = RenderThreadRecorder.create(mapView)
    recorder.start()
  }

  private fun getStatisticalModel(): JsonObject {
    val stats = recorder.end()
    val jsonObject = JsonObject()
    // for TTRC SLAs no recorded render calls happen after "CreateMap" command
    // so we return an empty JSON
    if (stats.totalFrames == 0L) {
      return jsonObject
    }
    val frameStats = stats.frameTimeList.sorted()
    jsonObject.addProperty(KEY_TOTAL_FRAMES, stats.totalFrames)
    jsonObject.addProperty(KEY_TOTAL_TIME, stats.totalTime)
    jsonObject.addProperty(KEY_DROPPED, stats.totalDroppedFrames)
    jsonObject.addProperty(
      KEY_DROPPED_PERCENT,
      BigDecimal
        .valueOf(stats.totalDroppedFrames.toDouble() / stats.totalFrames * 100.0)
        .setScale(2, RoundingMode.HALF_UP)
        .toDouble()
    )
    if (frameStats.isNotEmpty()) {
      jsonObject.addProperty(KEY_AVERAGE, frameStats.average())
      jsonObject.addProperty(KEY_PERCENTILE_50, percentileOfSortedList(frameStats, 50.0))
      jsonObject.addProperty(KEY_PERCENTILE_90, percentileOfSortedList(frameStats, 90.0))
      jsonObject.addProperty(KEY_PERCENTILE_95, percentileOfSortedList(frameStats, 95.0))
      jsonObject.addProperty(KEY_PERCENTILE_99, percentileOfSortedList(frameStats, 99.0))
    }
    return jsonObject
  }

  public fun stop(mapView: MapView) {
    writeSummary(mapView.context, getStatisticalModel())
  }

  private fun percentileOfSortedList(stats: List<Number>, percentile: Double): Number {
    val index = ceil(percentile / 100.0 * stats.size).toInt()
    return stats[index - 1]
  }

  @OptIn(DelicateCoroutinesApi::class)
  private fun writeSummary(context: Context, jsonObject: JsonObject) {
    val stats = Gson().toJson(jsonObject)
    writeSummaryJob = GlobalScope.launch(Dispatchers.IO) {
      StorageUtils(context).writeToFile(
        FRAME_LOG_JSON_NAME,
        stats
      )

      logI(TAG, stats)
      withContext(Dispatchers.Main) {
        Toast.makeText(context, stats, Toast.LENGTH_LONG).show()
      }
    }
  }

  private companion object {
    private const val FRAMESTATS = "framestats"
    const val KEY_TOTAL_FRAMES = "${FRAMESTATS}_frames"
    const val KEY_TOTAL_TIME = "${FRAMESTATS}_time"
    const val KEY_AVERAGE = "${FRAMESTATS}_average"
    const val KEY_DROPPED = "${FRAMESTATS}_dropped"
    const val KEY_DROPPED_PERCENT = "${FRAMESTATS}_dropped_percentage"
    const val KEY_PERCENTILE_50 = "${FRAMESTATS}_50_percentile"
    const val KEY_PERCENTILE_90 = "${FRAMESTATS}_90_percentile"
    const val KEY_PERCENTILE_95 = "${FRAMESTATS}_95_percentile"
    const val KEY_PERCENTILE_99 = "${FRAMESTATS}_99_percentile"
    private const val FRAME_LOG_JSON_NAME = "frame_log.json"
    private const val TAG = "FrameStatsRecorder"
  }
}

/**
 * Record the MapboxMap's frame stats to identify potential performance changes.
 *
 * The composable function is a [MapboxMapComposable] which means it should be used within [MapboxMap] composable.
 *
 * When used, the [FrameStatsRecorder] will start logging frame stats once the map enters composition, and the recorder
 * will be stopped when [MapboxMap] leaves composition. Result will be printed in logcat and also saved on disk.
 */
@OptIn(MapboxExperimental::class)
@MapboxMapComposable
@Composable
public fun RecordFrameStats() {
  DisposableMapEffect(Unit) {
    val frameStatsRecorder = FrameStatsRecorder()
    frameStatsRecorder.register(it)
    onDispose {
      frameStatsRecorder.stop(it)
    }
  }
}