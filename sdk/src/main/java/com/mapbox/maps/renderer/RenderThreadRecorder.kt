package com.mapbox.maps.renderer

import android.os.SystemClock
import androidx.annotation.RestrictTo
import com.mapbox.maps.MapView

/**
 * Recorder for tracking [MapboxRenderThread] frame stats.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class RenderThreadRecorder private constructor() {

  private var startTime = 0L
  private var totalDroppedFrames = 0L
  private val frameTimeList = mutableListOf<Long>()

  /**
   * Is recording in progress.
   */
  val recording: Boolean
    get() = startTime != 0L

  /**
   * Start recording.
   */
  fun start() {
    if (startTime != 0L) {
      throw RuntimeException("RendererStatRecorder: end() was not called after previous start()!")
    }
    startTime = SystemClock.elapsedRealtime()
  }

  internal fun addFrameStats(frameTime: Long, droppedFrames: Int) {
    totalDroppedFrames += droppedFrames
    frameTimeList.add(frameTime)
  }

  /**
   * End recording.
   */
  fun end(): RenderThreadStats {
    if (startTime == 0L) {
      throw RuntimeException("RendererStatRecorder: start() was not called!")
    }
    return RenderThreadStats(
      totalTime = SystemClock.elapsedRealtime() - startTime,
      totalFrames = frameTimeList.size + totalDroppedFrames,
      totalDroppedFrames = totalDroppedFrames,
      frameTimeList = frameTimeList.toList(),
    ).also {
      startTime = 0L
      totalDroppedFrames = 0L
      frameTimeList.clear()
    }
  }

  /**
   * Static methods.
   * @suppress
   */
  companion object {
    /**
     * Create [RenderThreadRecorder] for given [mapView].
     * @suppress
     */
    fun create(mapView: MapView): RenderThreadRecorder {
      return RenderThreadRecorder().apply {
        mapView.mapController.renderer.renderThread.renderThreadRecorder = this
      }
    }
  }
}

/**
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
data class RenderThreadStats(
  val totalTime: Long,
  val totalFrames: Long,
  val totalDroppedFrames: Long,
  val frameTimeList: List<Long>,
)