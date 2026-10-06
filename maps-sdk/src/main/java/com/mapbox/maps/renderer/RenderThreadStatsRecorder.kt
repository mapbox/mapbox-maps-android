package com.mapbox.maps.renderer

import android.os.SystemClock
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.MapboxExperimental
import java.util.Objects
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.math.ceil

/**
 * Recorder for tracking [MapboxRenderThread] frame stats.
 *
 * Each map owns one recorder, available from `MapView.renderThreadStatsRecorder` or
 * `MapSurface.renderThreadStatsRecorder`.
 *
 * Note that [RenderThreadStatsRecorder] results are relevant when the map is continuously
 * rendering (for example, during an animation).
 *
 * Recording never makes the render thread wait. A frame rendered while [start] or [stop] is in
 * progress on another thread may therefore be left out of the stats.
 *
 * It is recommended to keep recording sessions short, calling [start] and [stop] every 1 to 2
 * seconds, and to aggregate the returned [RenderThreadStats] yourself if you need longer
 * measurements. The recorder keeps every frame time of the current session in memory, so memory
 * grows with the session length (about 0.5 KB per second at 60 FPS), and [stop] sorts all of them
 * on the calling thread to compute percentiles. Short sessions keep both costs small.
 */
@MapboxExperimental
class RenderThreadStatsRecorder internal constructor() {

  // start() and stop() are called from the user's thread while addFrameStats() is called from the
  // render thread. The lock is held only for a few field updates (and an O(1) array swap in stop()).
  // The render thread only ever calls tryLock(), so it never waits for the user's thread.
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val lock = ReentrantLock()

  // Volatile because isRecording is read without the lock on every frame by MapboxRenderThread
  // (and by users). All writes happen under the lock.
  @Volatile
  private var startTime = 0L

  // Guarded by lock.
  private var totalSkippedVsync = 0L
  private var pacedSkippedVsync = 0L
  private var missedMapRenderFrames = 0L

  // Frame times are stored as primitives in a growable array, so recording a frame does not box a
  // Double or allocate on the render thread (apart from occasional growth). Guarded by lock.
  private var frameTimes = EMPTY_FRAME_TIMES
  private var frameCount = 0

  // Written by stop() and read by start() to pre-size the next session's array, so the render thread
  // does not regrow it from scratch in every session. Read outside the lock, which is fine because
  // a stale value only affects the initial capacity.
  private var lastSessionFrameCount = 0

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
    // Allocate here rather than in stop(), so an idle recorder does not hold a large empty array.
    // Allocation happens outside the lock to keep the critical section short. The capacity is capped
    // so a very long previous session does not make a short one allocate a large array up front.
    val newFrameTimes = DoubleArray(minOf(lastSessionFrameCount, MAX_PRESIZED_FRAME_COUNT))
    lock.withLock {
      if (isRecording) {
        throw RuntimeException("RendererStatRecorder: stop() was not called after previous start()!")
      }
      frameTimes = newFrameTimes
      frameCount = 0
      startTime = SystemClock.elapsedRealtime()
    }
  }

  internal fun addFrameStats(frameTime: Double, droppedFrames: Int, pacingSkips: Int, missedMapFrames: Int) {
    // Never block the render thread. If start() or stop() holds the lock right now, drop this
    // frame's stats instead of waiting: it is at the very edge of a session anyway.
    // The dropped frame is not counted in totalFrames even though it falls within totalTime. That
    // is accepted, because the critical sections in start() and stop() are only a few field writes.
    if (!lock.tryLock()) {
      return
    }
    try {
      // The MapboxRenderThread isRecording check happens outside the lock, so stop() may have run
      // since then.
      // Without this check a late frame would leak into the next session.
      if (!isRecording) {
        return
      }
      totalSkippedVsync += droppedFrames
      pacedSkippedVsync += pacingSkips
      missedMapRenderFrames += missedMapFrames
      if (frameCount == frameTimes.size) {
        frameTimes = frameTimes.copyOf(maxOf(MIN_GROWN_FRAME_CAPACITY, frameTimes.size * 2))
      }
      frameTimes[frameCount++] = frameTime
    } finally {
      lock.unlock()
    }
  }

  private fun percentileOfSorted(sortedValues: DoubleArray, percentile: Double): Double? {
    val index = ceil((percentile / 100.0) * sortedValues.size).toInt()
    return sortedValues.getOrNull(index - 1)
  }

  /**
   * Stop recording and calculate final results.
   *
   * The cost of this call grows with the number of frames recorded since [start], so prefer short
   * sessions (see [RenderThreadStatsRecorder]).
   *
   * @return [RenderThreadStats] with calculated stats.
   *
   * @throws RuntimeException if [start] was not called before.
   */
  fun stop(): RenderThreadStats {
    val totalTime: Long
    val skippedVsync: Long
    val pacedVsync: Long
    val missedFrames: Long
    val recordedFrameTimes: DoubleArray
    val recordedFrameCount: Int
    lock.withLock {
      if (!isRecording) {
        throw RuntimeException("RendererStatRecorder: start() was not called!")
      }
      // Read the clock under the lock so totalTime covers exactly the frames in this snapshot.
      totalTime = SystemClock.elapsedRealtime() - startTime
      skippedVsync = totalSkippedVsync
      pacedVsync = pacedSkippedVsync
      missedFrames = missedMapRenderFrames
      // Swap the array instead of copying it so the lock is held for O(1) time. The render thread
      // never touches the swapped out array again, and the recorder holds no frame storage between
      // sessions.
      recordedFrameTimes = frameTimes
      recordedFrameCount = frameCount
      frameTimes = EMPTY_FRAME_TIMES
      frameCount = 0
      lastSessionFrameCount = recordedFrameCount
      startTime = 0L
      totalSkippedVsync = 0L
      pacedSkippedVsync = 0L
      missedMapRenderFrames = 0L
    }
    // Everything below happens outside the lock so it never blocks the render thread.
    val sessionFrameTimes = if (recordedFrameCount == recordedFrameTimes.size) {
      recordedFrameTimes
    } else {
      recordedFrameTimes.copyOf(recordedFrameCount)
    }
    // Sorting primitives avoids boxing every frame time.
    val sortedFrameTimes = sessionFrameTimes.copyOf().apply { sort() }

    return RenderThreadStats.Builder()
      .setTotalTime(totalTime)
      .setTotalFrames(sessionFrameTimes.size + skippedVsync)
      .setTotalSkippedVsync(skippedVsync)
      .setPacedSkippedVsync(pacedVsync)
      .setMissedMapRenderFrames(missedFrames)
      // A read-only view that boxes values only when they are read, on the caller's thread.
      .setFrameTimeList(sessionFrameTimes.asList())
      .setPercentile50(percentileOfSorted(sortedFrameTimes, 50.0))
      .setPercentile90(percentileOfSorted(sortedFrameTimes, 90.0))
      .setPercentile95(percentileOfSorted(sortedFrameTimes, 95.0))
      .setPercentile99(percentileOfSorted(sortedFrameTimes, 99.0))
      .build()
  }
}

// The constants below are top level rather than in a companion object, where a const would become
// public API.

// 5 seconds of frames at 60 FPS, about 2.4 KB. Longer sessions grow the array as needed.
private const val MAX_PRESIZED_FRAME_COUNT = 60 * 5

// Smallest capacity when the array has to grow, so a session that starts without pre-sizing does
// not grow one frame at a time.
private const val MIN_GROWN_FRAME_CAPACITY = 64

private val EMPTY_FRAME_TIMES = DoubleArray(0)

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
   * Does not include intentional pacing skips from [setMaximumFps][com.mapbox.maps.MapboxMap.setMaximumFps] -
   * see [pacedSkippedVsync] for those.
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
    "RenderThreadStats(totalTime=$totalTime, totalFrames=$totalFrames, totalSkippedVsync=$totalSkippedVsync, pacedSkippedVsync=$pacedSkippedVsync, missedMapRenderFrames=$missedMapRenderFrames, frameTimeList=$frameTimeList, percentile50=$percentile50, percentile90=$percentile90, percentile95=$percentile95, percentile99=$percentile99)"

  /**
   * @return Returns true if the object is equal to the other object.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RenderThreadStats

    if (totalTime != other.totalTime) return false
    if (totalFrames != other.totalFrames) return false
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