package com.mapbox.maps.renderer

import android.os.Handler
import androidx.annotation.RestrictTo
import androidx.core.os.postDelayed
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import kotlin.math.pow

@RenderThread
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class FpsManager(
  private val handler: Handler,
  mapName: String,
) {
  private var userRefreshRate = USER_DEFINED_REFRESH_RATE_NOT_SET
  private var userToScreenRefreshRateRatio: Double? = null

  private var screenRefreshRate = SCREEN_METRICS_NOT_DEFINED
  private var screenRefreshPeriodNs = -1L
  private var previousFrameTimeNs = -1L
  private var previousDrawnFrameIndex = 0
  private var frameRenderTimeAccumulatedNs = 0L
  var skippedNow = 0
    private set

  private var preRenderTimeNs = -1L
  private var choreographerTicks = 0
  private var choreographerSkips = 0

  internal var fpsChangedListener: OnFpsChangedListener? = null

  @Suppress("PrivatePropertyName")
  private val TAG = "Mbgl-FpsManager" + if (mapName.isNotBlank()) "\\$mapName" else ""

  fun setScreenRefreshRate(screenRefreshRate: Int) {
    if (this.screenRefreshRate == screenRefreshRate) {
      return
    }
    this.screenRefreshRate = screenRefreshRate
    screenRefreshPeriodNs = ONE_SECOND_NS / screenRefreshRate
    if (userRefreshRate != USER_DEFINED_REFRESH_RATE_NOT_SET) {
      userToScreenRefreshRateRatio = (userRefreshRate.toDouble() / screenRefreshRate).coerceIn(0.0, 1.0)
      logI(TAG, "User defined ratio is $userToScreenRefreshRateRatio")
    }
    if (LOG_STATISTICS) {
      logI(
        TAG,
        "Device screen frequency is $screenRefreshRate," +
          " user defined refresh rate is ${if (userRefreshRate == USER_DEFINED_REFRESH_RATE_NOT_SET) "not set" else userRefreshRate}"
      )
    }
  }

  fun setUserRefreshRate(refreshRate: Int) {
    if (userRefreshRate != refreshRate) {
      userRefreshRate = refreshRate
      logI(TAG, "User set max FPS to $userRefreshRate")
      if (screenRefreshRate != SCREEN_METRICS_NOT_DEFINED) {
        userToScreenRefreshRateRatio = (userRefreshRate.toDouble() / screenRefreshRate).coerceIn(0.0, 1.0)
        logI(TAG, "User defined ratio is $userToScreenRefreshRateRatio")
      }
    }
  }

  /**
   * Return true if rendering should happen this frame and false otherwise.
   *
   * @param frameTimeNs time value from Choreographer in [System.nanoTime] timebase.
   * @param recorderStarted whether [RenderThreadRecorder.start] has been called
   * - in that case we want to [updateFrameStats] even if there is no [OnFpsChangedListener] attached and no frame pacing needed
   */
  fun preRender(frameTimeNs: Long, recorderStarted: Boolean = false): Boolean {
    // no need to perform neither pacing nor FPS calculation when setMaxFps / setOnFpsChangedListener was not called by the user
    if (userToScreenRefreshRateRatio == null && fpsChangedListener == null && !recorderStarted) {
      return true
    }
    // clear any scheduled task as new render call is about to happen
    handler.removeCallbacksAndMessages(fpsManagerToken)
    // update frame stats, count skipped / total VSYNC events
    updateFrameStats(frameTimeNs)
    userToScreenRefreshRateRatio?.let {
      return performPacing(it)
    }
    return true
  }

  fun postRender() {
    val frameRenderTimeNs = System.nanoTime() - preRenderTimeNs
    frameRenderTimeAccumulatedNs += frameRenderTimeNs
    // normally we update FPS counter and reset counters once a second
    if (choreographerTicks >= screenRefreshRate) {
      calculateFpsAndReset()
    } else {
      // however to produce correct values we also update FPS after IDLE_TIMEOUT_MS
      // otherwise when updating the map after it was IDLE first update will report
      // huge delta between new frame and last frame (as we're using dirty rendering)
      handler.postDelayed(
        VSYNC_COUNT_TILL_IDLE * (screenRefreshPeriodNs / ONE_MILLISECOND_NS),
        fpsManagerToken
      ) {
        onRenderingPaused()
      }
    }
    preRenderTimeNs = -1L
  }

  fun onSurfaceDestroyed() {
    onRenderingPaused()
  }

  fun destroy() {
    handler.removeCallbacksAndMessages(fpsManagerToken)
    fpsChangedListener = null
  }

  private fun updateFrameStats(frameTimeNs: Long) {
    preRenderTimeNs = System.nanoTime()
    skippedNow = 0
    // check if we did miss VSYNC deadline meaning too much work was done in previous doFrame
    if (previousFrameTimeNs != -1L && frameTimeNs - previousFrameTimeNs > screenRefreshPeriodNs + ONE_MILLISECOND_NS) {
      skippedNow =
        ((frameTimeNs - previousFrameTimeNs) / (screenRefreshPeriodNs + ONE_MILLISECOND_NS)).toInt()
      choreographerSkips += skippedNow
    }
    previousFrameTimeNs = frameTimeNs
    // we always increase choreographer tick by one + add number of skipped frames for consistent results
    choreographerTicks += skippedNow + 1
    if (skippedNow > 0 && LOG_STATISTICS) {
      logW(
        TAG,
        "Skipped $skippedNow VSYNC pulses since last actual render," +
          " total skipped in measurement period $choreographerSkips / $choreographerTicks"
      )
    }
  }

  private fun onRenderingPaused() {
    handler.removeCallbacksAndMessages(fpsManagerToken)
    calculateFpsAndReset()
    previousFrameTimeNs = -1
  }

  /**
   * Pacing algorithm relies on current choreographer tick
   * for the measuring interval in (0; screenRefreshRate] range (that also means one second time).
   *
   * We actually proceed with drawing only when we change integer part of the index we calculate.
   *
   * E.g. if we wish to have 24 FPS on a 60 FPS screen, the [userToScreenRefreshRateRatio] will be 0.4
   * so that's how we will render them:
   *
   * --------------------------------------------------
   * choreographerTicks | drawnFrameIndex | need render
   * --------------------------------------------------
   *        1           |       0.4       |   false
   *        2           |       0.8       |   false
   *        3           |       1.2       |   true
   *        4           |       1.6       |   false
   *        5           |       2.0       |   true
   *                        ...
   *       60           |       24        |   true
   *                 // reset counters
   *        1           |       0.4       |   false
   *        2           |       0.8       |   false
   */
  private fun performPacing(userToScreenRefreshRateRatio: Double): Boolean {
    val drawnFrameIndex = (choreographerTicks * userToScreenRefreshRateRatio).toInt()
    if (LOG_STATISTICS) {
      logI(
        TAG,
        "Performing pacing, current index=$drawnFrameIndex," +
          " previous drawn=$previousDrawnFrameIndex, proceed with rendering=${drawnFrameIndex > previousDrawnFrameIndex}"
      )
    }
    if (drawnFrameIndex > previousDrawnFrameIndex) {
      previousDrawnFrameIndex = drawnFrameIndex
      return true
    }
    choreographerSkips++
    return false
  }

  private fun calculateFpsAndReset() {
    if (choreographerTicks == 0) {
      return
    }
    fpsChangedListener?.let { listener ->
      val droppedFps = choreographerSkips.toDouble() / choreographerTicks
      val fps = (1.0 - droppedFps) * screenRefreshRate
      listener.onFpsChanged(fps)
      if (choreographerTicks == choreographerSkips) {
        logI(
          TAG,
          "VSYNC based FPS is $fps, missed $choreographerSkips out of $choreographerTicks VSYNC pulses"
        )
      } else {
        val averageRenderTimeNs = frameRenderTimeAccumulatedNs.toDouble() / (choreographerTicks - choreographerSkips)
        logI(
          TAG,
          "VSYNC based FPS is $fps," +
            " average core rendering time is ${averageRenderTimeNs / ONE_MILLISECOND_NS} ms" +
            " (or ${String.format("%.2f", screenRefreshPeriodNs / averageRenderTimeNs * screenRefreshRate)} FPS)," +
            " missed $choreographerSkips out of $choreographerTicks VSYNC pulses"
        )
      }
    }
    previousDrawnFrameIndex = 0
    frameRenderTimeAccumulatedNs = 0L
    choreographerTicks = 0
    choreographerSkips = 0
  }

  internal companion object {
    private const val USER_DEFINED_REFRESH_RATE_NOT_SET = -1
    private const val SCREEN_METRICS_NOT_DEFINED = -1
    private const val LOG_STATISTICS = false

    /**
     * Empiric metric to understand how many VSYNC updates without [preRender] will happen
     * after last [postRender] so that we assume map is IDLE.
     */
    internal const val VSYNC_COUNT_TILL_IDLE = 3
    private val fpsManagerToken = Any()
    private val ONE_SECOND_NS = 10.0.pow(9.0).toLong()
    private val ONE_MILLISECOND_NS = 10.0.pow(6.0).toLong()
  }
}