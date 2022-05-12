package com.mapbox.maps.renderer

import android.os.SystemClock
import android.view.Choreographer
import android.view.Surface
import androidx.annotation.AnyThread
import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.egl.EGLCore
import com.mapbox.maps.renderer.gl.TextureRenderer
import com.mapbox.maps.renderer.widget.Widget
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
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
  internal val renderEventQueue = ConcurrentLinkedQueue<RenderEvent>()

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val nonRenderEventQueue = ConcurrentLinkedQueue<RenderEvent>()

  private var surface: Surface? = null
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var eglSurface: EGLSurface
  private var width: Int = 0
  private var height: Int = 0

  private val widgetRenderer: MapboxWidgetRenderer
  private var widgetRenderCreated = false
  private val widgetTextureRenderer: TextureRenderer

  @Volatile
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var renderTimeNs = 0L
  private var expectedVsyncWakeTimeNs = 0L

  @Volatile
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var awaitingNextVsync = false
  private var sizeChanged = false
  @Volatile
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var paused = false

  /**
   * We track moment when native renderer is prepared.
   */
  private var renderCreated = false

  /**
   * We track moment when EGL context is created and associated with current Android surface.
   */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var eglContextCreated = false

  /**
   * Render thread should be treated as valid (prepared to render a map) when both flags are true.
   */
  private val renderThreadPrepared get() = eglContextCreated && renderCreated
  private var eglPrepared = false
  private var renderNotSupported = false

  internal var fpsChangedListener: OnFpsChangedListener? = null
  private var timeElapsed = 0L

  // TODO needed for workaround until issue is fixed in gl-native
  internal var renderDestroyCallChain = false

  constructor(
    mapboxRenderer: MapboxRenderer,
    mapboxWidgetRenderer: MapboxWidgetRenderer,
    translucentSurface: Boolean,
    antialiasingSampleCount: Int,
  ) {
    this.translucentSurface = translucentSurface
    this.mapboxRenderer = mapboxRenderer
    this.widgetRenderer = mapboxWidgetRenderer
    this.eglCore = EGLCore(translucentSurface, antialiasingSampleCount)
    this.eglSurface = eglCore.eglNoSurface
    this.widgetTextureRenderer = TextureRenderer()
    renderHandlerThread = RenderHandlerThread().apply { start() }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  constructor(
    mapboxRenderer: MapboxRenderer,
    mapboxWidgetRenderer: MapboxWidgetRenderer,
    handlerThread: RenderHandlerThread,
    eglCore: EGLCore,
    widgetTextureRenderer: TextureRenderer,
  ) {
    this.translucentSurface = false
    this.mapboxRenderer = mapboxRenderer
    this.widgetRenderer = mapboxWidgetRenderer
    this.renderHandlerThread = handlerThread
    this.eglCore = eglCore
    this.widgetTextureRenderer = widgetTextureRenderer
    this.eglSurface = eglCore.eglNoSurface
  }

  private fun postPrepareRenderFrame(delayMillis: Long = 0L) {
    renderHandlerThread.postDelayed(
      {
        prepareRenderFrame()
      },
      delayMillis
    )
  }

  private fun setUpRenderThread(creatingSurface: Boolean): Boolean {
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
            eglContextCreated = checkEglContextCurrent()
            // finally we can create native renderer if needed or just report OK
            if (eglContextCreated) {
              if (!renderCreated) {
                // we set `renderCreated` as `true` before creating native render as core could potentially
                // schedule task in the same callchain and we need to make sure that `renderThreadPrepared` is already `true`
                // so that we do not drop this task
                renderCreated = true
                mapboxRenderer.createRenderer()
                mapboxRenderer.onSurfaceChanged(
                  width = width,
                  height = height
                )
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
        logE(TAG, "EGL was not configured, please check logs above.")
        renderNotSupported = true
        return false
      }
    }
    return true
  }

  private fun checkAndroidSurface(): Boolean {
    return if (surface?.isValid == true) {
      true
    } else {
      logW(TAG, "EGL was configured but Android surface is null or not valid, waiting for a new one...")
      // give system a bit of time and try rendering again hoping surface will be valid now
      postPrepareRenderFrame(delayMillis = RETRY_DELAY_MS)
      false
    }
  }

  private fun checkEglSurface(surface: Surface): Boolean {
    if (eglSurface == eglCore.eglNoSurface) {
      eglSurface = eglCore.createWindowSurface(surface)
      if (eglSurface == eglCore.eglNoSurface) {
        // try recreate it in next iteration.
        postPrepareRenderFrame(delayMillis = RETRY_DELAY_MS)
        return false
      }
    }
    return true
  }

  private fun checkEglContextCurrent(): Boolean {
    val eglContextAttached = eglCore.makeCurrent(eglSurface)
    if (!eglContextAttached) {
      logW(TAG, "EGL was configured but context could not be made current. Trying again in a moment...")
      postPrepareRenderFrame(delayMillis = RETRY_DELAY_MS)
      return false
    }
    return true
  }

  private fun checkSurfaceSizeChanged() {
    if (sizeChanged) {
      mapboxRenderer.onSurfaceChanged(width = width, height = height)
      widgetRenderer.onSurfaceChanged(width = width, height = height)
      sizeChanged = false
    }
  }

  private fun checkWidgetRender() {
    if (eglPrepared && !widgetRenderCreated && widgetRenderer.hasWidgets()) {
      widgetRenderer.setSharedContext(eglCore.eglContext)
      widgetRenderCreated = true
    }
  }

  private fun draw() {
    if (needSwapBuffersWithCachedMap.getAndSet(false)) {
      when (val swapStatus = eglCore.swapBuffers(eglSurface)) {
        EGL10.EGL_SUCCESS -> {
          logE("KIRYLDD", "Swap with cached map")
        }
        EGL11.EGL_CONTEXT_LOST -> {
          logW(TAG, "Context lost. Waiting for re-acquire")
          releaseEgl()
        }
        else -> {
          logW(TAG, "eglSwapBuffer error: $swapStatus. Waiting for new surface")
          releaseEglSurface()
        }
      }
      logE("KIRYLDD", "Render cached start")
      mapboxRenderer.render()
      logE("KIRYLDD", "Render cached end")
//      waitUntilViewAnnotationPositioned.set(true)
      return
    }
    // render but do not swap buffers yet
    if (waitUntilViewAnnotationPositioned.get()) return
    val renderTimeNsCopy = renderTimeNs
    val currentTimeNs = SystemClock.elapsedRealtimeNanos()
    val expectedEndRenderTimeNs = currentTimeNs + renderTimeNsCopy
    if (expectedVsyncWakeTimeNs > currentTimeNs) {
      // when we have FPS limited and desire to skip core render - we must schedule new draw call
      // otherwise map may remain in not fully loaded state
      postPrepareRenderFrame()
      return
    }

    if (widgetRenderer.hasWidgets()) {
      if (widgetRenderer.needTextureUpdate) {
        widgetRenderer.updateTexture()
        eglCore.makeCurrent(eglSurface)
      }

      mapboxRenderer.render()

      if (widgetRenderer.hasTexture()) {
        widgetTextureRenderer.render(widgetRenderer.getTexture())
      }
    } else {
      mapboxRenderer.render()
    }

    // assuming render event queue holds user's runnables with OpenGL ES commands
    // it makes sense to execute them after drawing a map but before swapping buffers
    // **note** this queue also holds snapshot tasks
    drainQueue(renderEventQueue)
    when (val swapStatus = eglCore.swapBuffers(eglSurface)) {
      EGL10.EGL_SUCCESS -> {
        logE("KIRYLDD", "Swap with latest map")
      }
      EGL11.EGL_CONTEXT_LOST -> {
        logW(TAG, "Context lost. Waiting for re-acquire")
        releaseEgl()
      }
      else -> {
        logW(TAG, "eglSwapBuffer error: $swapStatus. Waiting for new surface")
        releaseEglSurface()
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
    widgetTextureRenderer.release()
    eglCore.releaseSurface(eglSurface)
    eglContextCreated = false
    eglSurface = eglCore.eglNoSurface
    widgetRenderCreated = false
    widgetRenderer.release()
  }

  private fun releaseAll() {
    renderDestroyCallChain = true
    mapboxRenderer.destroyRenderer()
    renderDestroyCallChain = false
    renderCreated = false
    releaseEgl()
    surface?.release()
  }

  private fun prepareRenderFrame(creatingSurface: Boolean = false) {
    // no need to do anything if we're waiting for next VSYNC already
    if (awaitingNextVsync) {
      return
    }
    // Check first if we have to stop rendering at all (even if there was no EGL config) and cleanup EGL.
    // We need to check it ASAP in order not to block thread that is calling `onSurfaceTextureDestroyed`.
    // After that check MapView could be actually rendered on this device (has valid EGL config).
    // After that we check if activity / fragment is paused.
    if (renderNotSupported || paused) {
      // at least on Android 8 devices we create surface before Activity#onStart
      // so we need to proceed to EGL creation in any case to avoid deadlock
      if (!creatingSurface) {
        return
      }
    }
    // check for creatingSurface flag to make sure we don't hit deadlock
    if (creatingSurface || !renderThreadPrepared) {
      val renderThreadPreparedOk = setUpRenderThread(creatingSurface)
      if (!renderThreadPreparedOk) {
        return
      }
    }
    checkWidgetRender()
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
    lock.withLock {
      // in some situations `destroy` is called earlier than onSurfaceDestroyed - in that case no need to clean up
      if (renderHandlerThread.started) {
        renderHandlerThread.post {
          awaitingNextVsync = false
          Choreographer.getInstance().removeFrameCallback(this)
          lock.withLock {
            // TODO https://github.com/mapbox/mapbox-maps-android/issues/607
            if (renderCreated && mapboxRenderer is MapboxTextureViewRenderer) {
              releaseAll()
            } else {
              releaseEglSurface()
            }
            destroyCondition.signal()
          }
        }
        destroyCondition.await()
      }
    }
  }

  fun addWidget(widget: Widget) {
    widgetRenderer.addWidget(widget)
  }

  fun removeWidget(widget: Widget) = widgetRenderer.removeWidget(widget)

  @WorkerThread
  internal fun processAndroidSurface(surface: Surface, width: Int, height: Int) {
    if (this.surface != surface) {
      if (this.surface != null) {
        releaseEgl()
        this.surface?.release()
      }
      this.surface = surface
    }
    this.width = width
    this.height = height
    widgetRenderer.onSurfaceChanged(width = width, height = height)
    renderEventQueue.removeAll { it.eventType == EventType.SDK }
    nonRenderEventQueue.removeAll { it.eventType == EventType.SDK }
    // we do not want to clear render events scheduled by user
    renderHandlerThread.clearMessageQueue(clearAll = false)
    prepareRenderFrame(creatingSurface = true)
  }

  @UiThread
  fun onSurfaceCreated(surface: Surface, width: Int, height: Int) {
    lock.withLock {
      renderHandlerThread.post {
        processAndroidSurface(surface, width, height)
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
    if (renderThreadPrepared && !paused) {
      draw()
    }
    awaitingNextVsync = false
    // It's critical to drain queue after setting `awaitingNextVsync` to false as some tasks may recursively schedule other tasks when executed.
    // With `awaitingNextVsync = false` we will always schedule recursive tasks for later execution
    // via `renderHandlerThread.postDelayed` instead of updating queue concurrently that is being drained (which may lead to deadlock in core).
    drainQueue(nonRenderEventQueue)
  }

  @AnyThread
  fun queueRenderEvent(renderEvent: RenderEvent, priority: Int) {
    if (renderEvent.needRender) {
      renderEvent.runnable?.let {
        renderEventQueue.add(renderEvent)
      }
      // in case of native Mapbox events we schedule render event only when we're fully ready for render
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
      if (renderEvent.eventType == EventType.SDK) {
        if (renderThreadPrepared) {
          postNonRenderEvent(renderEvent, 0, priority)
        }
      } else {
        postNonRenderEvent(renderEvent)
      }
    }
  }

  private val needSwapBuffersWithCachedMap = AtomicBoolean(false)
  private val waitUntilViewAnnotationPositioned = AtomicBoolean(false)

  internal fun viewAnnotationsDraw() {
//    waitUntilViewAnnotationPositioned.set(false)
    needSwapBuffersWithCachedMap.set(true)
    if (!awaitingNextVsync) {
      postPrepareRenderFrame()
    }
  }

  internal fun viewAnnotationPositionArrived() {
    waitUntilViewAnnotationPositioned.set(true)
  }

  private fun postNonRenderEvent(renderEvent: RenderEvent, delayMillis: Long = 0L, priority: Int = 0) {
    // if we already waiting listening for next VSYNC then add runnable to queue to execute
    // after actual drawing otherwise execute asap on render thread
    if (awaitingNextVsync && priority == 0) {
      nonRenderEventQueue.add(renderEvent)
    } else if (priority > 0) {
      renderHandlerThread.postImmediate {
        if (renderThreadPrepared) {
          renderEvent.runnable?.run()
        }
      }
    } else {
      renderHandlerThread.postDelayed(
        {
          if (renderThreadPrepared || renderEvent.eventType == EventType.DESTROY_RENDERER) {
            renderEvent.runnable?.run()
          } else {
            logW(TAG, "Non-render event could not be run, retrying in $RETRY_DELAY_MS ms...")
            postNonRenderEvent(renderEvent, delayMillis = RETRY_DELAY_MS)
          }
        },
        delayMillis,
        renderEvent.eventType
      )
    }
  }

  @UiThread
  fun pause() {
    paused = true
  }

  @UiThread
  fun resume() {
    paused = false
    // schedule render if we resume not after first create (e.g. bring map back to front)
    if (renderThreadPrepared) {
      postPrepareRenderFrame()
    }
  }

  @UiThread
  internal fun destroy() {
    lock.withLock {
      // do nothing if destroy for some reason called more than once to avoid deadlock
      if (renderHandlerThread.started) {
        renderHandlerThread.post {
          lock.withLock {
            if (renderCreated) {
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

  private fun drainQueue(originalQueue: ConcurrentLinkedQueue<RenderEvent>) {
    var event = originalQueue.poll()
    while (event != null) {
      event.runnable?.run()
      event = originalQueue.poll()
    }
  }

  companion object {
    private const val TAG = "Mbgl-RenderThread"

    private val ONE_SECOND_NS = 10.0.pow(9.0).toLong()
    private val ONE_MILLISECOND_NS = 10.0.pow(6.0).toLong()

    /**
     * If we hit some issue caused by invalid state (most likely caused by GPU driver) we start
     * rescheduling configuration with that delay in order not to overflood handler thread message queue.
     */
    internal const val RETRY_DELAY_MS = 50L
  }
}