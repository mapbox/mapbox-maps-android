package com.mapbox.maps.threading

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Choreographer
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.threading.internal.PuckAnimatorDataApplier

/**
 * Used to synchronize map camera and puck animators and provide single endpoint to send data to core.
 */
@MapboxExperimental
class AnimationSynchronizer private constructor(
  private val mapboxMap: MapCameraManagerDelegate,
) {
  private var enabled = false
  private var enableLogs = false
  private var lastAppliedFrameTimeNanos = 0L
  private val animationEventsMap = HashMap<Long, Triple<CameraOptions?, Double?, Point?>>()
  private val mainHandler = Handler(Looper.getMainLooper())

  /**
   * Interface for updating puck animator values.
   * For internal usage.
   */
  var puckAnimatorDataApplier: PuckAnimatorDataApplier? = null

  /**
   * Whether map camera animator update is expected to apply to rendering engine as part of common state.
   */
  var expectingCamera = false
    set(value) {
      field = value
      if (enableLogs) {
        logI(
          TAG,
          "expectingCamera=$value"
        )
      }
    }
  /**
   * Whether puck bearing animator update is expected to apply to rendering engine as part of common state.
   */
  var expectingPuckBearing = false
    set(value) {
      field = value
      if (enableLogs) {
        logI(
          TAG,
          "expectingPuckBearing=$value"
        )
      }
    }
  /**
   * Whether puck position animator update is expected to apply to rendering engine as part of common state.
   */
  var expectingPuckPosition = false
    set(value) {
      field = value
      if (enableLogs) {
        logI(
          TAG,
          "expectingPuckPosition=$value"
        )
      }
    }

  private fun applySynchronizedState(
    frameTimeNanos: Long
  ) {
    animationEventsMap[frameTimeNanos]?.let { synchronizedState ->
      synchronizedState.first?.let {
        mapboxMap.setCamera(it)
      }
      synchronizedState.second?.let {
        puckAnimatorDataApplier?.setBearing(it)
      }
      synchronizedState.third?.let {
        puckAnimatorDataApplier?.setLatLng(it)
      }
      if (enableLogs) {
        logI(
          TAG,
          "frameTimeNanos=$frameTimeNanos, synchronizedState=$synchronizedState applied"
        )
      }
      lastAppliedFrameTimeNanos = frameTimeNanos
      animationEventsMap.remove(frameTimeNanos)
      clearOutdatedEvents()
    }
  }

  /**
   * Send map camera update.
   */
  fun sendCameraUpdate(frameTimeNanos: Long, cameraOptions: CameraOptions) {
    if (enableLogs) {
      logI(
        TAG,
        "frameTimeNanos=$frameTimeNanos, sendCameraUpdate=$cameraOptions"
      )
    }
    processUpdate(frameTimeNanos) {
      val data = animationEventsMap[frameTimeNanos]
      val mergedCameraOptions = data?.first?.let {
        mergeCameraUpdate(it, cameraOptions)
      } ?: cameraOptions
      val updatedData = Triple(mergedCameraOptions, data?.second, data?.third)
      animationEventsMap[frameTimeNanos] = updatedData
    }
  }

  /**
   * Send puck bearing update.
   */
  fun sendPuckBearingUpdate(frameTimeNanos: Long, bearing: Double) {
    if (enableLogs) {
      logI(
        TAG,
        "frameTimeNanos=$frameTimeNanos, sendPuckBearingUpdate"
      )
    }
    processUpdate(frameTimeNanos) {
      val data = animationEventsMap[frameTimeNanos]
      val updatedData = Triple(data?.first, bearing, data?.third)
      animationEventsMap[frameTimeNanos] = updatedData
    }
  }

  /**
   * Send puck position update.
   */
  fun sendPuckPositionUpdate(frameTimeNanos: Long, position: Point) {
    if (enableLogs) {
      logI(
        TAG,
        "frameTimeNanos=$frameTimeNanos, sendPuckPositionUpdate"
      )
    }
    processUpdate(frameTimeNanos) {
      val data = animationEventsMap[frameTimeNanos]
      val updatedData = Triple(data?.first, data?.second, position)
      animationEventsMap[frameTimeNanos] = updatedData
    }
  }

  private fun mergeCameraUpdate(currentCamera: CameraOptions, updatedCamera: CameraOptions): CameraOptions {
    return cameraOptions {
      center(updatedCamera.center ?: currentCamera.center)
      zoom(updatedCamera.zoom ?: currentCamera.zoom)
      padding(updatedCamera.padding ?: currentCamera.padding)
      bearing(updatedCamera.bearing ?: currentCamera.bearing)
      anchor(updatedCamera.anchor ?: currentCamera.anchor)
      pitch(updatedCamera.pitch ?: currentCamera.pitch)
    }
  }

  private fun processUpdate(frameTimeNanos: Long, putToMap: (() -> Unit)) {
    if (frameTimeNanos <= lastAppliedFrameTimeNanos) {
      if (enableLogs) {
        logI(
          TAG,
          "Skipping update for frameTimeNanos=$frameTimeNanos as lastAppliedFrameTimeNanos=$lastAppliedFrameTimeNanos"
        )
      }
      return
    }
    putToMap.invoke()
    // return if applying synchronized state has already been scheduled
    if (mainHandler.hasMessages(frameTimeNanos.toInt())) {
      return
    }
    animationEventsMap[frameTimeNanos]?.let { currentState ->
      // each type event is considered to be ready if data for given frameTimeNanos is
      // already present or if we are not expecting that data at all
      val cameraReady = currentState.first != null || !expectingCamera
      val puckBearingReady = currentState.second != null || !expectingPuckBearing
      val puckPositionReady = currentState.third != null || !expectingPuckPosition
      if (cameraReady && puckBearingReady && puckPositionReady) {
        // applying immediately may not work correctly for gestures where camera may arrive
        // later than puck animation due to complex implementation of 0-duration animators
        val message = Message.obtain(mainHandler) {
          applySynchronizedState(frameTimeNanos)
        }
        message.what = frameTimeNanos.toInt()
        mainHandler.sendMessage(message)
      }
    }
  }

  private fun clearOutdatedEvents() {
    val iterator = animationEventsMap.entries.iterator()
    while (iterator.hasNext()) {
      val next = iterator.next()
      if (next.key < lastAppliedFrameTimeNanos) {
        if (enableLogs) {
          logI(
            TAG,
            "Clearing outdated event frameTimeNanos=${next.key}," +
              " lastAppliedFrameTimeNanos=$lastAppliedFrameTimeNanos," +
              " data=${next.value}"
          )
        }
        iterator.remove()
      }
    }
  }

  /**
   * Static methods and variables.
   */
  companion object {
    private const val TAG = "AnimationSynchronizer"
    private val animationSynchronizerSet = mutableSetOf<AnimationSynchronizer>()

    /**
     * Get synchronizer instance for given map.
     * For internal usage.
     *
     * @return [AnimationSynchronizer] if this was enabled by [AnimationSynchronizer.enableFor] or
     *  NULL otherwise.
     */
    fun get(mapboxMap: MapCameraManagerDelegate) =
      animationSynchronizerSet.firstOrNull { it.mapboxMap == mapboxMap && it.enabled }

    /**
     * Get synchronizer instance for given [PuckAnimatorDataApplier].
     * For internal usage.
     *
     * @return [AnimationSynchronizer] if this was enabled by [AnimationSynchronizer.enableFor] or
     *  NULL otherwise.
     */
    fun get(applier: PuckAnimatorDataApplier?): AnimationSynchronizer? {
      if (applier == null) {
        return null
      }
      return animationSynchronizerSet.firstOrNull { it.puckAnimatorDataApplier == applier && it.enabled }
    }

    /**
     * Remove animation synchronizer for given map to avoid memory leak.
     * For internal usage.
     */
    fun remove(mapboxMap: MapCameraManagerDelegate) {
      disableFor(mapboxMap)
      animationSynchronizerSet.removeAll { it.mapboxMap == mapboxMap }
    }

    /**
     * Disable synchronizing puck and map camera updates for given map.
     */
    fun disableFor(mapboxMap: MapCameraManagerDelegate): Boolean {
      animationSynchronizerSet.forEach {
        if (it.mapboxMap == mapboxMap) {
          it.enabled = false
          it.mainHandler.removeCallbacksAndMessages(null)
          return true
        }
      }
      return false
    }

    /**
     * Enable synchronizing puck and map camera updates for given map.
     *
     * In practice this means that puck and map camera animators will be sending data at once to
     * rendering engine based on [Choreographer.FrameCallback.doFrame] `frameTimeNanos` coming from
     * the Android system. This might help to synchronize map camera and puck in certain scenarios e.g.
     * when system is highly overloaded: either synchronized puck + camera data will be applied or
     * the whole frame will be skipped or delayed.
     *
     * @param mapboxMap instance of MapboxMap
     * @param enableLogs whether logs will be printed. Defaults to False.
     */
    @JvmOverloads
    fun enableFor(mapboxMap: MapCameraManagerDelegate, enableLogs: Boolean = false) {
      animationSynchronizerSet.forEach {
        if (it.mapboxMap == mapboxMap) {
          it.enabled = true
          return
        }
      }
      animationSynchronizerSet.add(
        AnimationSynchronizer(mapboxMap).apply {
          this.enabled = true
          this.enableLogs = enableLogs
        }
      )
    }
  }
}