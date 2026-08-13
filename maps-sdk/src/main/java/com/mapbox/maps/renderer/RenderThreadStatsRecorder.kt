package com.mapbox.maps.renderer

import android.os.SystemClock
import com.mapbox.maps.MapboxExperimental
import java.util.Objects
import kotlin.math.ceil

/**
 * Recorder for tracking [MapboxRenderThread] frame stats.
 *
 * Note that [RenderThreadStatsRecorder] results are relevant when the map is continuously
 * rendering (for example, during an animation).
 */
@MapboxExperimental
class RenderThreadStatsRecorder {

  private var startTime = 0L
  private var totalSkippedVsync = 0L
  private var pacedSkippedVsync = 0L
  private var missedMapRenderFrames = 0L
  private val frameTimeList = mutableListOf<Double>()

  /**
   * @return true if recording is in progress.
   */
  val isRecording: Boolean
    get() = startTime != 0L

  /**
   * Start recording frame stats.
   *
   * @throws RuntimeException if the recorder was already started.
   */
  fun start() {
    if (isRecording) {
      throw RuntimeException("RendererStatRecorder: end() was not called after previous start()!")
    }
    startTime = SystemClock.elapsedRealtime()
  }

  internal fun addFrameStats(frameTime: Double, droppedFrames: Int, pacingSkips: Int, missedMapFrames: Int) {
    totalSkippedVsync += droppedFrames
    pacedSkippedVsync += pacingSkips
    missedMapRenderFrames += missedMapFrames
    frameTimeList.add(frameTime)
  }

  private fun percentileOfSortedList(sortedValues: List<Double>, percentile: Double): Double? {
    val index = ceil((percentile / 100.0) * sortedValues.size).toInt()
    return sortedValues.elementAtOrNull(index - 1)
  }

  /**
   * End recording and calculate final results.
   *
   * @return [RenderThreadStats] with calculated stats.
   *
   * @throws RuntimeException if [start] was not called before.
   */
  fun end(): RenderThreadStats {
    if (!isRecording) {
      throw RuntimeException("RendererStatRecorder: start() was not called!")
    }
    val frameTimeListCopy = frameTimeList.toList()
    val sortedFrameTimeList = frameTimeList.sorted()

    return RenderThreadStats.Builder()
      .setTotalTime(SystemClock.elapsedRealtime() - startTime)
      .setTotalFrames(frameTimeList.size + totalSkippedVsync)
      .setTotalDroppedFrames(totalSkippedVsync)
      .setTotalSkippedVsync(totalSkippedVsync)
      .setPacedSkippedVsync(pacedSkippedVsync)
      .setMissedMapRenderFrames(missedMapRenderFrames)
      .setFrameTimeList(frameTimeListCopy)
      .setPercentile50(percentileOfSortedList(sortedFrameTimeList, 50.0))
      .setPercentile90(percentileOfSortedList(sortedFrameTimeList, 90.0))
      .setPercentile95(percentileOfSortedList(sortedFrameTimeList, 95.0))
      .setPercentile99(percentileOfSortedList(sortedFrameTimeList, 99.0))
      .build()
      .also {
        startTime = 0L
        totalSkippedVsync = 0L
        pacedSkippedVsync = 0L
        missedMapRenderFrames = 0L
        frameTimeList.clear()
      }
  }
}

/**
 * Data class for holding frame stats.
 */
@MapboxExperimental
class RenderThreadStats private constructor(
  /**
   * Total time in milliseconds that the recorder was running.
   */
  val totalTime: Long,
  /**
   * Total number of frames (rendered + dropped).
   */
  val totalFrames: Long,
  /**
   * Number of VSYNC pulses skipped because a render missed its deadline (took too long).
   *
   * @deprecated Renamed to [totalSkippedVsync]. This field never included intentional pacing
   * skips from [setMaximumFps][com.mapbox.maps.MapboxMap.setMaximumFps] - see [pacedSkippedVsync]
   * for those, tracked separately.
   */
  @Deprecated(
    message = "Renamed to totalSkippedVsync. This field only counts missed-render-deadline VSYNCs; see pacedSkippedVsync for intentional pacing skips.",
    replaceWith = ReplaceWith("totalSkippedVsync")
  )
  val totalDroppedFrames: Long,
  /**
   * Number of VSYNC pulses skipped because a render missed its deadline (took too long).
   * Does not include intentional pacing skips from [setMaximumFps][com.mapbox.maps.MapboxMap.setMaximumFps] -
   * see [pacedSkippedVsync] for those. Replacement for the deprecated [totalDroppedFrames].
   */
  val totalSkippedVsync: Long,
  /**
   * Number of VSYNC pulses intentionally skipped due to frame pacing (i.e., [setMaximumFps][com.mapbox.maps.MapboxMap.setMaximumFps] is active).
   * These are not missed render deadlines — they are expected skips.
   */
  val pacedSkippedVsync: Long,
  /**
   * Number of map render frames missed because the render took longer than the target frame time.
   * This counts misses relative to the map render frame rate (which may differ from the screen refresh rate when [setMaximumFps][com.mapbox.maps.MapboxMap.setMaximumFps] is set).
   */
  val missedMapRenderFrames: Long,
  /**
   * List of rendered frames times in milliseconds.
   */
  val frameTimeList: List<Double>,
  /**
   * 50th percentile of frame times in milliseconds.
   */
  val percentile50: Double?,
  /**
   * 90th percentile of frame times in milliseconds.
   */
  val percentile90: Double?,
  /**
   * 95th percentile of frame times in milliseconds.
   */
  val percentile95: Double?,
  /**
   * 99th percentile of frame times in milliseconds.
   */
  val percentile99: Double?,
) {
  /**
   * @return Returns a string representation of the object.
   */
  override fun toString() =
    "RenderThreadStats(totalTime=$totalTime, totalFrames=$totalFrames, totalDroppedFrames=$totalDroppedFrames, totalSkippedVsync=$totalSkippedVsync, pacedSkippedVsync=$pacedSkippedVsync, missedMapRenderFrames=$missedMapRenderFrames, frameTimeList=$frameTimeList, percentile50=$percentile50, percentile90=$percentile90, percentile95=$percentile95, percentile99=$percentile99)"

  /**
   * @return Returns true if the object is equal to the other object.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RenderThreadStats

    if (totalTime != other.totalTime) return false
    if (totalFrames != other.totalFrames) return false
    if (totalDroppedFrames != other.totalDroppedFrames) return false
    if (totalSkippedVsync != other.totalSkippedVsync) return false
    if (pacedSkippedVsync != other.pacedSkippedVsync) return false
    if (missedMapRenderFrames != other.missedMapRenderFrames) return false
    if (percentile50 != other.percentile50) return false
    if (percentile90 != other.percentile90) return false
    if (percentile95 != other.percentile95) return false
    if (percentile99 != other.percentile99) return false
    if (frameTimeList != other.frameTimeList) return false

    return true
  }

  /**
   * @return Returns the hash code of the object.
   */
  override fun hashCode(): Int = Objects.hash(
    totalTime,
    totalFrames,
    totalDroppedFrames,
    totalSkippedVsync,
    pacedSkippedVsync,
    missedMapRenderFrames,
    percentile50,
    percentile90,
    percentile95,
    percentile99
  )

  internal class Builder {
    private var totalTime: Long = 0
    private var totalFrames: Long = 0
    private var totalDroppedFrames: Long = 0
    private var totalSkippedVsync: Long = 0
    private var pacedSkippedVsync: Long = 0
    private var missedMapRenderFrames: Long = 0
    private var frameTimeList: List<Double> = emptyList()
    private var percentile50: Double? = null
    private var percentile90: Double? = null
    private var percentile95: Double? = null
    private var percentile99: Double? = null

    fun setTotalTime(totalTime: Long) = apply { this.totalTime = totalTime }
    fun setTotalFrames(totalFrames: Long) = apply { this.totalFrames = totalFrames }
    fun setTotalDroppedFrames(totalDroppedFrames: Long) =
      apply { this.totalDroppedFrames = totalDroppedFrames }

    fun setTotalSkippedVsync(totalSkippedVsync: Long) = apply { this.totalSkippedVsync = totalSkippedVsync }
    fun setPacedSkippedVsync(pacedSkippedVsync: Long) = apply { this.pacedSkippedVsync = pacedSkippedVsync }
    fun setMissedMapRenderFrames(missedMapRenderFrames: Long) = apply { this.missedMapRenderFrames = missedMapRenderFrames }

    fun setFrameTimeList(frameTimeList: List<Double>) = apply { this.frameTimeList = frameTimeList }
    fun setPercentile50(percentile50: Double?) = apply { this.percentile50 = percentile50 }
    fun setPercentile90(percentile90: Double?) = apply { this.percentile90 = percentile90 }
    fun setPercentile95(percentile95: Double?) = apply { this.percentile95 = percentile95 }
    fun setPercentile99(percentile99: Double?) = apply { this.percentile99 = percentile99 }

    fun build(): RenderThreadStats {
      return RenderThreadStats(
        totalTime,
        totalFrames,
        totalDroppedFrames,
        totalSkippedVsync,
        pacedSkippedVsync,
        missedMapRenderFrames,
        frameTimeList,
        percentile50,
        percentile90,
        percentile95,
        percentile99
      )
    }
  }
}