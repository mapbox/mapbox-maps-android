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
import java.util.LinkedList
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

  internal val renderHandlerThread: RenderHandlerThread
  private val translucentSurface: Boolean
  private val mapboxRenderer: MapboxRenderer
  private val eglCore: EGLCore

  private val lock = ReentrantLock()
  private val createCondition = lock.newCondition()
  private val destroyCondition = lock.newCondition()

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val renderEventQueue = LinkedList<RenderEvent>()
  private val renderEventQueueLock = ReentrantLock()

  private var surface: Surface? = null
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var eglSurface: EGLSurface? = null
  private var width: Int = 0
  private var height: Int = 0

  @Volatile
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var renderTimeNs = 0L
  private var expectedVsyncWakeTimeNs = 0L
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var awaitingNextVsync = false
  private var sizeChanged = false
  @Volatile
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var paused = false
  private val surfaceValid: Boolean
    @Synchronized
    get() = surface?.isValid == true
  private val renderThreadPrepared: Boolean
    @Synchronized
    get() = surfaceValid && nativeRenderCreated
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var eglPrepared = false
  private var nativeRenderCreated = false
  private var nativeRenderNotSupported = false

  internal var fpsChangedListener: OnFpsChangedListener? = null
  private var timeElapsed = 0L

  // TODO needed for workaround until issue is fixed in gl-native
  internal var nativeRenderDestroyCallChain = false

  constructor(
    mapboxRenderer: MapboxRenderer,
    translucentSurface: Boolean,
    antialiasingSampleCount: Int,
  ) {
    this.translucentSurface = translucentSurface
    this.mapboxRenderer = mapboxRenderer
    this.eglCore = EGLCore(translucentSurface, antialiasingSampleCount)
    Logger.e("KIRYLDD", "CTOR")
    renderHandlerThread = RenderHandlerThread().apply { start() }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  constructor(
    mapboxRenderer: MapboxRenderer,
    handlerThread: RenderHandlerThread,
    eglCore: EGLCore
  ) {
    this.translucentSurface = false
    this.mapboxRenderer = mapboxRenderer
    this.renderHandlerThread = handlerThread
    this.eglCore = eglCore
  }

  private fun postPrepareRenderFrame(delayMillis: Long = 0L) {
    renderHandlerThread.postDelayed(
      {
        prepareRenderFrame()
      },
      delayMillis
    )
  }

  private fun isValidRenderingEnvironment(creatingSurface: Boolean): Boolean {
    lock.withLock {
      try {
        val eglConfigOk = checkEglConfig()
        val androidSurfaceOk = checkAndroidSurface()
        if (eglConfigOk && androidSurfaceOk) {
          // on Android SDK <= 23 at least on x86 emulators we need to force set EGL10.EGL_NO_CONTEXT
          // when resuming activity
          if (creatingSurface) {
            eglCore.makeNothingCurrent()
          }
          // it's safe to use !! here as we checked surface above
          val eglSurfaceOk = checkEglSurface(surface!!)
          if (eglSurfaceOk) {
            val eglContextOk = checkEglContextCurrent()
            // finally we can create native renderer if needed or just report OK
            if (eglContextOk) {
              if (!nativeRenderCreated) {
                mapboxRenderer.onSurfaceCreated()
                mapboxRenderer.onSurfaceChanged(
                  width = width,
                  height = height
                )
                nativeRenderCreated = true
              }
              return true
            }
          }
        }
        return false
      } finally {
        createCondition.signal()
      }
    }
  }

  private fun checkEglConfig(): Boolean {
    if (!eglPrepared) {
      val eglOk = eglCore.prepareEgl()
      if (eglOk) {
        eglPrepared = true
      } else {
        Logger.e(TAG, "EGL was not configured, please check logs above.")
        nativeRenderNotSupported = true
        return false
      }
    }
    return true
  }

  private fun checkAndroidSurface(): Boolean {
    return if (surfaceValid) {
      true
    } else {
      Logger.w(TAG, "EGL was configured but Android surface is null or not valid, waiting for a new one...")
      // give system a bit of time and try rendering again hoping surface will be valid now
      postPrepareRenderFrame(delayMillis = RETRY_DELAY_MS)
      false
    }
  }

  private fun checkEglSurface(surface: Surface): Boolean {
    if (eglSurface == null || eglSurface == EGL10.EGL_NO_SURFACE) {
      eglSurface = eglCore.createWindowSurface(surface)
      if (eglSurface == null) {
        // Set EGL Surface as EGL_NO_SURFACE and try recreate it in next iteration.
        eglSurface = EGL10.EGL_NO_SURFACE
        postPrepareRenderFrame(delayMillis = RETRY_DELAY_MS)
        return false
      }
    }
    return true
  }

  private fun checkEglContextCurrent(): Boolean {
    // it's safe to use !! here as we configured EGLSurface before
    val eglContextAttached = eglCore.makeCurrent(eglSurface!!)
    if (!eglContextAttached) {
      Logger.w(TAG, "EGL was configured but context could not be made current. Trying again in a moment...")
      postPrepareRenderFrame(delayMillis = RETRY_DELAY_MS)
      return false
    }
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

  private fun draw() {
    val renderTimeNsCopy = renderTimeNs
    val currentTimeNs = SystemClock.elapsedRealtimeNanos()
    val expectedEndRenderTimeNs = currentTimeNs + renderTimeNsCopy
    if (expectedVsyncWakeTimeNs > currentTimeNs) {
      // when we have FPS limited and desire to skip core render - we must schedule new draw call
      // otherwise map may remain in not fully loaded state
      postPrepareRenderFrame()
      return
    }
    mapboxRenderer.onDrawFrame()
    // assuming render event queue holds user's runnables with OpenGL ES commands
    // it makes sense to execute them after drawing a map but before swapping buffers
    // **note** this queue also holds snapshot tasks
    renderEventQueueLock.withLock {
      renderEventQueue.apply {
        if (isNotEmpty()) {
          forEach {
            it.runnable?.run()
          }
          clear()
        }
      }
    }
    eglSurface?.let {
      when (val swapStatus = eglCore.swapBuffers(it)) {
        EGL10.EGL_SUCCESS -> {}
        EGL11.EGL_CONTEXT_LOST -> {
          Logger.w(TAG, "Context lost. Waiting for re-acquire")
          releaseEgl()
        }
        else -> {
          Logger.w(TAG, "eglSwapBuffer error: $swapStatus. Waiting for new surface")
          releaseEglSurface()
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

  private fun releaseEgl() {
    releaseEglSurface()
    if (eglPrepared) {
      eglCore.release()
    }
    eglPrepared = false
  }

  private fun releaseEglSurface() {
    eglSurface?.let {
      eglCore.releaseSurface(it)
    }
    eglSurface = null
  }

  private fun releaseAll() {
    nativeRenderDestroyCallChain = true
    mapboxRenderer.onSurfaceDestroyed()
    nativeRenderDestroyCallChain = false
    nativeRenderCreated = false
    releaseEgl()
    surface?.release()
  }

  private fun prepareRenderFrame(creatingSurface: Boolean = false) {
    // no need to do anything if we're waiting for next VSYNC already
    if (awaitingNextVsync) {
      Logger.e("KIRYLDD", "prepareRenderFrame skipping as we're waiting for VSYNC already")
      return
    }
    Logger.e("KIRYLDD", "prepareRenderFrame creatingSurface = $creatingSurface, paused = $paused, nativeRenderNotSupported = $nativeRenderNotSupported")
    // Check first if we have to stop rendering at all (even if there was no EGL config) and cleanup EGL.
    // We need to check it ASAP in order not to block thread that is calling `onSurfaceTextureDestroyed`.
    // After that check MapView could be actually rendered on this device (has valid EGL config).
    // After that we check if activity / fragment is paused.
    if (nativeRenderNotSupported || paused) {
      // at least on Android 8 devices we create surface before Activity#onStart
      // so we need to proceed to EGL creation in any case to avoid deadlock
      if (!creatingSurface) {
        return
      }
    }
    if (!isValidRenderingEnvironment(creatingSurface)) {
      return
    }
    checkSurfaceSizeChanged()
    Choreographer.getInstance().postFrameCallback(this)
    awaitingNextVsync = true
  }

  @UiThread
  fun onSurfaceSizeChanged(width: Int, height: Int) {
    if (this.width != width || this.height != height) {
      renderHandlerThread.post {
        this.width = width
        this.height = height
        sizeChanged = true
        prepareRenderFrame()
      }
    }
  }

  @UiThread
  fun onSurfaceDestroyed() {
    Logger.e("KIRYLDD", "onSurfaceDestroyed")
    lock.withLock {
      // in some situations `destroy` is called earlier than onSurfaceDestroyed - in that case no need to clean up
      if (renderHandlerThread.started) {
        renderHandlerThread.post {
          awaitingNextVsync = false
          Choreographer.getInstance().removeFrameCallback(this)
          lock.withLock {
            Logger.e("KIRYLDD", "onSurfaceDestroyed renderHandlerThread inside lock")
            // TODO https://github.com/mapbox/mapbox-maps-android/issues/607
            if (nativeRenderCreated && mapboxRenderer is MapboxTextureViewRenderer) {
              Logger.e("KIRYLDD", "onSurfaceDestroyed releaseAll")
              releaseAll()
              Logger.e("KIRYLDD", "onSurfaceDestroyed releaseAll done")
            } else {
              Logger.e("KIRYLDD", "onSurfaceDestroyed releaseEglSurface")
              releaseEglSurface()
              Logger.e("KIRYLDD", "onSurfaceDestroyed releaseEglSurface done")
            }
            Logger.e("KIRYLDD", "onSurfaceDestroyed renderHandlerThread inside lock signal!")
            destroyCondition.signal()
          }
        }
        Logger.e("KIRYLDD", "onSurfaceDestroyed destroyCondition.await()...")
        destroyCondition.await()
        Logger.e("KIRYLDD", "onSurfaceDestroyed resume main thread!")
      }
    }
  }

  @UiThread
  fun onSurfaceCreated(surface: Surface, width: Int, height: Int) {
    Logger.e("KIRYLDD", "onSurfaceCreated")
    lock.withLock {
      renderHandlerThread.post {
        Logger.e("KIRYLDD", "onSurfaceCreated old: ${this.surface}, new: $surface")
        if (this.surface != surface) {
          releaseEgl()
          this.surface?.release()
          this.surface = surface
        }
        this.width = width
        this.height = height
        // we clean only Mapbox events to avoid outdated runnables associated with previous EGL context
        renderEventQueueLock.withLock {
          // using iterator to avoid concurrent modification exception
          val iterator = renderEventQueue.iterator()
          while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.eventType == EventType.SDK) {
              iterator.remove()
            }
          }
        }
        // we do not want to clear render events scheduled by user
        renderHandlerThread.clearMessageQueue(clearAll = false)
        prepareRenderFrame(creatingSurface = true)
      }
      createCondition.await()
    }
  }

  @AnyThread
  fun setMaximumFps(fps: Int) {
    renderTimeNs = ONE_SECOND_NS / fps
  }

  @WorkerThread
  override fun doFrame(frameTimeNanos: Long) {
    // it makes sense to draw not only when EGL config is prepared but when native renderer is created
    if (nativeRenderCreated && !paused) {
      draw()
      Logger.e("KIRYLDD", "draw + swapbuffers")
    }
    awaitingNextVsync = false
  }

  @AnyThread
  fun queueRenderEvent(renderEvent: RenderEvent) {
    if (renderEvent.needRender) {
      renderEvent.runnable?.let {
        renderEventQueueLock.withLock {
          renderEventQueue.add(renderEvent)
        }
      }
      // in case of native Mapbox events we schedule render event only when we're fully ready for render
      // TODO ideally to be fixed in core
      if (renderEvent.eventType == EventType.SDK) {
        if (renderThreadPrepared) {
          postPrepareRenderFrame()
        }
      } else {
        postPrepareRenderFrame()
      }
    } else {
      // no sense in scheduling non-render SDK tasks if thread is not fully ready for render
      // as render thread SDK tasks queue will be cleared when new surface will arrive
      Logger.e("KIRYLDD", "Non render event, $renderThreadPrepared, $renderEvent")
      if (renderEvent.eventType == EventType.SDK) {
        if (renderThreadPrepared) {
          postNonRenderEvent(renderEvent)
        }
      } else {
        postNonRenderEvent(renderEvent)
      }
    }
  }

  private fun postNonRenderEvent(renderEvent: RenderEvent, delayMillis: Long = 0L) {
    renderHandlerThread.postDelayed(
      {
        Logger.e("KIRYLDD", "postNonRenderEvent, run ${renderEvent.runnable}...")
        if (renderThreadPrepared || renderEvent.eventType == EventType.DESTROY_RENDERER) {
          Logger.e("KIRYLDD", "postNonRenderEvent, run success ${renderEvent.runnable}")
          renderEvent.runnable?.run()
        } else {
          Logger.w(TAG, "Non-render event could not be run, retrying in $RETRY_DELAY_MS ms...")
          postNonRenderEvent(renderEvent, delayMillis = RETRY_DELAY_MS)
        }
      },
      delayMillis,
      renderEvent.eventType
    )
  }

  @AnyThread
  fun queueSnapshot(snapshotTask: Runnable) {
    queueRenderEvent(
      RenderEvent(
        runnable = snapshotTask,
        needRender = true,
        eventType = EventType.SDK
      )
    )
  }

  @UiThread
  fun pause() {
    Logger.e("KIRYLDD", "pause")
    paused = true
    Logger.e("KIRYLDD", "pause = true")
  }

  @UiThread
  fun resume() {
    Logger.e("KIRYLDD", "resume")
    paused = false
    // schedule render if we resume not after first create (e.g. bring map back to front)
    if (renderThreadPrepared) {
      postPrepareRenderFrame()
    }
    Logger.e("KIRYLDD", "pause = false")
  }

  @UiThread
  internal fun destroy() {
    lock.withLock {
      // do nothing if destroy for some reason called more than once to avoid deadlock
      if (renderHandlerThread.started) {
        renderHandlerThread.post {
          lock.withLock {
            if (nativeRenderCreated) {
              releaseAll()
            }
            renderHandlerThread.clearMessageQueue(clearAll = true)
            destroyCondition.signal()
          }
        }
        destroyCondition.await()
      }
    }
    renderHandlerThread.stop()
    mapboxRenderer.map = null
  }

  private companion object {
    const val TAG = "Mbgl-RenderThread"
    const val RETRY_DELAY_MS = 50L
    val ONE_SECOND_NS = 10.0.pow(9.0).toLong()
    val ONE_MILLISECOND_NS = 10.0.pow(6.0).toLong()
  }
}