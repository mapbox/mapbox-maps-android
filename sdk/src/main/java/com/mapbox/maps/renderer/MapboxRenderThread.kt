package com.mapbox.maps.renderer

import android.os.SystemClock
import android.view.Choreographer
import android.view.Surface
import androidx.annotation.AnyThread
import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import com.mapbox.common.Logger
import com.mapbox.maps.renderer.egl.EGLCore
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.ReentrantLock
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGL11
import javax.microedition.khronos.egl.EGLSurface
import kotlin.concurrent.withLock
import kotlin.math.pow

/**
 * The render thread is responsible for the communication between any thread and the render thread it creates.
 * It is also responsible for EGL set up, managing context, window surfaces etc.
 */
internal class MapboxRenderThread : Choreographer.FrameCallback {

  internal val handlerThread: WorkerHandlerThread
  private val translucentSurface: Boolean
  private val mapboxRenderer: MapboxRenderer
  private val eglCore: EGLCore

  private val lock = ReentrantLock()
  private val destroyCondition = lock.newCondition()
  private val createCondition = lock.newCondition()

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val renderEventQueue = CopyOnWriteArrayList<Runnable>()
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val eventQueue = CopyOnWriteArrayList<Runnable>()

  private var surface: Surface? = null
  private var eglSurface: EGLSurface? = EGL10.EGL_NO_SURFACE
  private var width: Int = 0
  private var height: Int = 0

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val renderTimeNs = AtomicLong(0)
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  private var needRenderOnResume = false
  private var expectedVsyncWakeTimeNs = 0L
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var awaitingNextVsync = AtomicBoolean(false)
  private var sizeChanged = false
  private var paused = false
  private var shouldExit = false
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var eglPrepared = false
  private var nativeRenderCreated = false
  private var nativeRenderNotSupported = false

  internal var fpsChangedListener: OnFpsChangedListener? = null
  private var timeElapsed = 0L

  constructor(mapboxRenderer: MapboxRenderer, translucentSurface: Boolean) {
    this.translucentSurface = translucentSurface
    this.mapboxRenderer = mapboxRenderer
    this.eglCore = EGLCore(translucentSurface)
    handlerThread = WorkerHandlerThread().apply { start() }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  constructor(
    mapboxRenderer: MapboxRenderer,
    handlerThread: WorkerHandlerThread,
    eglCore: EGLCore
  ) {
    this.translucentSurface = false
    this.mapboxRenderer = mapboxRenderer
    this.handlerThread = handlerThread
    this.eglCore = eglCore
    this.eglSurface = null
  }

  private fun postPrepareRenderFrame() {
    handlerThread.post { prepareRenderFrame() }
  }

  private fun checkSurfaceReady(): Boolean {
    lock.withLock {
      try {
        surface?.let {
          if (eglSurface == null || eglSurface == EGL10.EGL_NO_SURFACE) {
            return prepareEglSurface(it)
          }
        } ?: return false
        return true
      } finally {
        createCondition.signal()
      }
    }
  }

  private fun prepareEglSurface(surface: Surface): Boolean {
    if (!eglPrepared) {
      eglCore.prepareEgl()
      if (eglCore.eglStatusSuccess) {
        eglPrepared = true
      } else {
        Logger.e(TAG, "EGL was not configured, please check logs above.")
        nativeRenderNotSupported = true
        return false
      }
    }
    if (!surface.isValid) {
      Logger.w(TAG, "EGL was configured but surface is not valid.")
      postPrepareRenderFrame()
      return false
    }
    eglSurface = eglCore.createWindowSurface(surface)
    if (!eglCore.eglStatusSuccess) {
      // Set EGL Surface as EGL_NO_SURFACE and try recreate it in next iteration.
      eglSurface = EGL10.EGL_NO_SURFACE
      postPrepareRenderFrame()
      return false
    }
    eglSurface?.let {
      eglCore.makeCurrent(it)
    }
    if (!nativeRenderCreated) {
      mapboxRenderer.onSurfaceCreated()
      nativeRenderCreated = true
    }
    mapboxRenderer.onSurfaceChanged(
      width = width,
      height = height
    )
    return true
  }

  private fun checkSurfaceSizeChanged() {
    if (sizeChanged) {
      mapboxRenderer.onSurfaceChanged(
        width = width,
        height = height
      )
      sizeChanged = false
    }
  }

  private fun drainQueue(queue: CopyOnWriteArrayList<Runnable>) {
    queue.apply {
      if (isNotEmpty()) {
        forEach {
          it.run()
        }
        clear()
      }
    }
  }

  private fun draw() {
    val renderTimeNsCopy = renderTimeNs.get()
    val currentTimeNs = SystemClock.elapsedRealtimeNanos()
    val expectedEndRenderTimeNs = currentTimeNs + renderTimeNsCopy
    if (expectedVsyncWakeTimeNs > currentTimeNs) {
      return
    }
    mapboxRenderer.onDrawFrame()
    eglSurface?.let {
      when (val swapStatus = eglCore.swapBuffers(it)) {
        EGL10.EGL_SUCCESS -> {}
        EGL11.EGL_CONTEXT_LOST -> {
          Logger.w(TAG, "Context lost. Waiting for re-acquire")
          eglPrepared = false
          eglCore.releaseSurface(it)
          eglSurface = EGL10.EGL_NO_SURFACE
          eglCore.release()
        }
        else -> {
          Logger.w(TAG, "eglSwapBuffer error: $swapStatus. Waiting or new surface")
          eglCore.releaseSurface(it)
          eglSurface = EGL10.EGL_NO_SURFACE
        }
      }
    }
    val actualEndRenderTimeNs = SystemClock.elapsedRealtimeNanos()
    if (renderTimeNsCopy != 0L && actualEndRenderTimeNs < expectedEndRenderTimeNs) {
      // we need to stop swap buffers for less than time requested in order to have some time to render upcoming frame
      // before next vsync so it will be drawn, otherwise we will drop it
      expectedVsyncWakeTimeNs = expectedEndRenderTimeNs - ONE_MILLISECOND_NS
    }
    fpsChangedListener?.let {
      val fps = 1E9 / (actualEndRenderTimeNs - timeElapsed)
      if (timeElapsed != 0L) {
        it.onFpsChanged(fps)
      }
      timeElapsed = actualEndRenderTimeNs
    }
  }

  private fun prepareRenderFrame() {
    // Check first if we have to stop rendering at all (even if there was no EGL config) and cleanup EGL.
    // We need to check it ASAP in order not to block thread that is calling `onSurfaceTextureDestroyed`.
    // After that check MapView could be actually rendered on this device (has valid EGL config).
    // After that we check if activity / fragment is paused.
    if (shouldExit || nativeRenderNotSupported || paused) {
      if (paused) {
        needRenderOnResume = true
      }
      return
    }
    if (!checkSurfaceReady()) {
      return
    }
    checkSurfaceSizeChanged()
    drainQueue(renderEventQueue)
    // listen to next VSYNC event if not listening already
    if (awaitingNextVsync.compareAndSet(false, true)) {
      Choreographer.getInstance().postFrameCallback(this)
    }
  }

  @UiThread
  fun onSurfaceSizeChanged(width: Int, height: Int) {
    if (this.width != width || this.height != height) {
      handlerThread.post {
        this.width = width
        this.height = height
        sizeChanged = true
        prepareRenderFrame()
      }
    }
  }

  @UiThread
  fun onSurfaceDestroyed() {
    lock.withLock {
      handlerThread.post {
        awaitingNextVsync.set(false)
        Choreographer.getInstance().removeFrameCallback(this)
        shouldExit = true
        lock.withLock {
          mapboxRenderer.onSurfaceDestroyed()
          if (eglPrepared) {
            eglSurface?.let {
              eglCore.releaseSurface(it)
            }
            eglCore.release()
            eglPrepared = false
          }
          eglSurface = EGL10.EGL_NO_SURFACE
          handlerThread.clearMessageQueue()
          surface?.release()
          destroyCondition.signal()
        }
      }
      destroyCondition.await()
      if (mapboxRenderer.needDestroy) {
        destroy()
      }
    }
  }

  @UiThread
  fun onSurfaceCreated(surface: Surface, width: Int, height: Int) {
    lock.withLock {
      handlerThread.post {
        // Surface could be re-used effectively by Android under the hood -
        // in that case do not release it.
        if (this.surface != surface) {
          this.surface?.release()
          this.surface = surface
        }
        this.width = width
        this.height = height
        shouldExit = false
        eventQueue.clear()
        renderEventQueue.clear()
        prepareRenderFrame()
      }
      createCondition.await()
    }
  }

  @AnyThread
  fun setMaximumFps(fps: Int) {
    renderTimeNs.set(ONE_SECOND_NS / fps)
  }

  @WorkerThread
  override fun doFrame(frameTimeNanos: Long) {
    try {
      awaitingNextVsync.set(false)
      if (eglPrepared && !paused && !shouldExit) {
        draw()
      }
    } finally {
      // regardless if we did drawing or not execute all non gl relative events
      drainQueue(eventQueue)
    }
  }

  // MapRenderer delegate methods

  @AnyThread
  fun requestRender() {
    postPrepareRenderFrame()
  }

  @AnyThread
  fun queueRenderEvent(runnable: Runnable) {
    renderEventQueue.add(runnable)
    postPrepareRenderFrame()
  }

  @AnyThread
  fun queueEvent(runnable: Runnable) {
    // if we already waiting listening for next VSYNC then add runnable to queue to execute
    // after actual drawing otherwise execute asap on render thread
    if (awaitingNextVsync.get()) {
      eventQueue.add(runnable)
    } else {
      handlerThread.post {
        // at the time we start executing surface may be already destroyed
        if (!shouldExit) {
          runnable.run()
        }
      }
    }
  }

  @UiThread
  fun pause() {
    handlerThread.post {
      paused = true
    }
  }

  @UiThread
  fun resume() {
    handlerThread.post {
      paused = false
      if (needRenderOnResume) {
        prepareRenderFrame()
        needRenderOnResume = false
      }
    }
  }

  @UiThread
  internal fun destroy() {
    handlerThread.apply {
      clearMessageQueue()
      stop()
    }
  }

  companion object {
    private const val TAG = "Mbgl-RenderThread"
    private val ONE_SECOND_NS = 10.0.pow(9.0).toLong()
    private val ONE_MILLISECOND_NS = 10.0.pow(6.0).toLong()
  }
}