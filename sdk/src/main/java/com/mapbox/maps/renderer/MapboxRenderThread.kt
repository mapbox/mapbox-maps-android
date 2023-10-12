package com.mapbox.maps.renderer

import android.opengl.EGL14
import android.opengl.EGLSurface
import android.opengl.GLES20
import android.os.SystemClock
import android.os.Trace
import android.view.Choreographer
import android.view.Surface
import androidx.annotation.AnyThread
import androidx.annotation.RestrictTo
import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxTracing
import com.mapbox.maps.MapboxTracing.MAPBOX_TRACE_ID
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.egl.EGLCore
import com.mapbox.maps.renderer.gl.TextureRenderer
import com.mapbox.maps.renderer.widget.Widget
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.properties.Delegates

/**
 * The render thread is responsible for the communication between any thread and the render thread it creates.
 * It is also responsible for EGL set up, managing context, window surfaces etc.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class MapboxRenderThread : Choreographer.FrameCallback {

  internal val renderHandlerThread: RenderHandlerThread
  private val translucentSurface: Boolean
  private val mapboxRenderer: MapboxRenderer
  internal val eglCore: EGLCore

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
  internal var awaitingNextVsync = false
  private var sizeChanged = false
  @Volatile
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var paused = false

  internal var renderThreadRecorder: RenderThreadRecorder? = null

  /**
   * We track moment when native renderer is prepared.
   */
  private var nativeRenderCreated = false
    // no need for synchronized getter, getting value for this var happens on render thread only
    set(value) = renderThreadPreparedLock.withLock {
      field = value
    }

  /**
   * We track moment when EGL context is created and associated with current Android surface.
   */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var eglContextMadeCurrent = false
    // no need for synchronized getter, getting value for this var happens on render thread only
    set(value) = renderThreadPreparedLock.withLock {
      field = value
    }

  private val renderThreadPreparedLock = ReentrantLock()
  /**
   * Render thread should be treated as valid (prepared to render a map) when both flags are true.
   * Getter is thread-safe as this flag could be accessed from any thread.
   */
  private val renderThreadPrepared get() = renderThreadPreparedLock.withLock {
    eglContextMadeCurrent && nativeRenderCreated
  }
  private var eglContextCreated = false
  private var renderNotSupported = false

  // could not be volatile as setter method is synchronized
  internal var fpsChangedListener by Delegates.observable<OnFpsChangedListener?>(null) { _, old, new ->
    if (old != new) {
      /**
       * Consider setting [OnFpsChangedListener] as non-render event to make sure that
       * it does not get dropped if user has set it right after MapView creation before render thread
       * is actually fully prepared for rendering.
       */
      postNonRenderEvent(
        RenderEvent(
          { fpsManager.fpsChangedListener = new },
          false,
        )
      )
    }
  }

  private val fpsManager: FpsManager

  /**
   * Modified from render thread only, needed to understand when exactly to swap buffers
   * to achieve better synchronization with view annotation updates.
   */
  internal var needViewAnnotationSync = false
  @Volatile
  internal var viewAnnotationMode = ViewAnnotationManager.DEFAULT_UPDATE_MODE

  private inline fun trace(sectionName: String, section: (() -> Unit)) {
    if (MapboxTracing.platformTracingEnabled) {
      Trace.beginSection("$MAPBOX_TRACE_ID: $sectionName")
      section.invoke()
      Trace.endSection()
    } else {
      section.invoke()
    }
  }

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
    renderHandlerThread = RenderHandlerThread()
    val handler = renderHandlerThread.start()
    fpsManager = FpsManager(handler)
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  constructor(
    mapboxRenderer: MapboxRenderer,
    mapboxWidgetRenderer: MapboxWidgetRenderer,
    handlerThread: RenderHandlerThread,
    eglCore: EGLCore,
    fpsManager: FpsManager,
    widgetTextureRenderer: TextureRenderer,
  ) {
    this.translucentSurface = false
    this.mapboxRenderer = mapboxRenderer
    this.widgetRenderer = mapboxWidgetRenderer
    this.renderHandlerThread = handlerThread
    this.eglCore = eglCore
    this.fpsManager = fpsManager
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
        logI(
          TAG,
          "Setting up render thread, flags:" +
            " creatingSurface=$creatingSurface," +
            " nativeRenderCreated=$nativeRenderCreated," +
            " eglContextMadeCurrent=$eglContextMadeCurrent," +
            " eglContextCreated=$eglContextCreated," +
            " paused=$paused"
        )
        val eglConfigOk = checkEglConfig()
        val androidSurfaceOk = checkAndroidSurface()
        if (eglConfigOk && androidSurfaceOk) {
          // on Android SDK <= 23 at least on x86 emulators we need to force set EGL14.EGL_NO_CONTEXT
          // when resuming activity
          if (creatingSurface) {
            eglCore.makeNothingCurrent()
          }
          // it's safe to use !! here as we checked surface above
          val eglSurfaceOk = checkEglSurface(surface!!)
          if (eglSurfaceOk) {
            eglContextMadeCurrent = checkEglContextCurrent()
            // finally we can create native renderer if needed or just report OK
            if (eglContextMadeCurrent) {
              if (!nativeRenderCreated) {
                // we set `nativeRenderCreated` as `true` before creating native render as core could potentially
                // schedule task in the same callchain and we need to make sure that `renderThreadPrepared` is already `true`
                // so that we do not drop this task
                nativeRenderCreated = true
                mapboxRenderer.createRenderer()
                logI(TAG, "Native renderer created.")
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
    if (!eglContextCreated) {
      val eglOk = eglCore.prepareEgl()
      if (eglOk) {
        eglContextCreated = true
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
      logW(TAG, "EGL was configured but Android surface.isValid=${surface?.isValid}, waiting for a new one...")
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
        logW(
          TAG,
          "Could not create EGL surface although Android surface was valid," +
            " retrying in $RETRY_DELAY_MS ms..."
        )
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
    if (eglContextCreated && !widgetRenderCreated && widgetRenderer.hasWidgets()) {
      widgetRenderer.setSharedContext(eglCore.eglContext)
      widgetRenderCreated = true
    }
  }

  private fun draw(frameTimeNanos: Long) {
    if (!fpsManager.preRender(frameTimeNanos, renderThreadRecorder?.recording == true)) {
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

      // TODO remove when rendering engine will handle this for us;
      //  for now we have to clean context a bit so that our texture(s) is (are) drawn on top of the map
      GLES20.glDisable(GLES20.GL_STENCIL_TEST)
      GLES20.glDisable(GLES20.GL_DEPTH_TEST)
      GLES20.glUseProgram(0)
      GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
      GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)

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
    fpsManager.postRender()
    if (needViewAnnotationSync && viewAnnotationMode == ViewAnnotationUpdateMode.MAP_SYNCHRONIZED) {
      // when we're syncing view annotations with the map -
      // we swap buffers the next frame to achieve better synchronization with view annotations update
      // that always happens 1 frame later
      Choreographer.getInstance().postFrameCallback {
        swapBuffers()
      }
      // explicit flush as we will not be doing any drawing until buffer swap for the next frame -
      // we send commands to GPU this frame as we should have some free time and perform buffer swap asap on the next frame
      // note that this doesn't block the calling thread, it merely signals the driver that we might not be sending any additional commands.
      // ref https://stackoverflow.com/a/38297697
      GLES20.glFlush()
    } else {
      // perform swap immediately if no view annotations are visible or mode is not MAP_SYNCHRONIZED
      swapBuffers()
    }
    // always reset the flag
    needViewAnnotationSync = false
  }

  private fun swapBuffers() {
    when (val swapStatus = eglCore.swapBuffers(eglSurface)) {
      EGL14.EGL_SUCCESS -> { }
      EGL14.EGL_CONTEXT_LOST -> {
        logW(TAG, "Context lost. Waiting for re-acquire")
        // release all resources but not release Android surface
        // as it still potentially may be valid - then it could be re-used to recreate EGL;
        // if it's not valid - system should shortly send us brand new surface and
        // we will recreate EGL and native renderer anyway
        releaseAll(tryRecreate = true)
      }
      else -> {
        logW(TAG, "eglSwapBuffer error: $swapStatus. Waiting for new surface")
        releaseEglSurface()
      }
    }
  }

  private fun releaseEglSurface() {
    trace("release-egl-surface") {
      widgetTextureRenderer.release()
      eglCore.releaseSurface(eglSurface)
      eglContextMadeCurrent = false
      eglSurface = eglCore.eglNoSurface
      widgetRenderCreated = false
      widgetRenderer.release()
    }
  }

  private fun releaseAll(tryRecreate: Boolean = false) {
    trace("release-egl-all") {
      mapboxRenderer.destroyRenderer()
      logI(TAG, "Native renderer destroyed.")
      renderEventQueue.clear()
      nonRenderEventQueue.clear()
      nativeRenderCreated = false
      releaseEglSurface()
      if (eglContextCreated) {
        eglCore.release()
      }
      eglContextCreated = false
      if (tryRecreate) {
        setUpRenderThread(creatingSurface = true)
      } else {
        surface?.release()
      }
    }
  }

  private fun prepareRenderFrame(creatingSurface: Boolean = false) {
    // no need to do anything if we're waiting for next VSYNC already;
    // however if Android has sent us new surface - we must proceed up to `setUpRenderThread` or
    // otherwise main thread will end up having deadlock
    if (awaitingNextVsync && !creatingSurface) {
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
        logI(
          TAG,
          "Skip render frame - NOT creating surface although " +
            "renderNotSupported ($renderNotSupported) || paused ($paused)"
        )
        return
      }
    }
    // check for creatingSurface flag to make sure we don't hit deadlock
    if (creatingSurface || !renderThreadPrepared) {
      trace("set-up-render-thread") {
        val renderThreadPreparedOk = setUpRenderThread(creatingSurface)
        if (!renderThreadPreparedOk) {
          logI(
            TAG,
            "Skip render frame - render thread NOT prepared although " +
              "creatingSurface ($creatingSurface) || !renderThreadPrepared (${!renderThreadPrepared})"
          )
          return@trace
        }
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
    trace("surface-destroyed") {
      logI(TAG, "onSurfaceDestroyed")
      lock.withLock {
        // in some situations `destroy` is called earlier than onSurfaceDestroyed - in that case no need to clean up
        if (renderHandlerThread.isRunning) {
          renderHandlerThread.post {
            awaitingNextVsync = false
            Choreographer.getInstance().removeFrameCallback(this)
            lock.withLock {
              // TODO https://github.com/mapbox/mapbox-maps-android/issues/607
              if (nativeRenderCreated && mapboxRenderer is MapboxTextureViewRenderer) {
                releaseAll()
                renderHandlerThread.clearRenderEventQueue()
              } else {
                releaseEglSurface()
              }
              fpsManager.onSurfaceDestroyed()
              destroyCondition.signal()
            }
          }
          logI(TAG, "onSurfaceDestroyed: waiting until EGL will be cleaned up...")
          destroyCondition.await()
          logI(TAG, "onSurfaceDestroyed: EGL resources were cleaned up.")
        }
      }
    }
  }

  @OptIn(MapboxExperimental::class)
  fun addWidget(widget: Widget) {
    widgetRenderer.addWidget(widget)
  }

  @OptIn(MapboxExperimental::class)
  fun removeWidget(widget: Widget) = widgetRenderer.removeWidget(widget)

  @WorkerThread
  internal fun processAndroidSurface(surface: Surface, width: Int, height: Int) {
    if (this.surface != surface) {
      if (this.surface != null) {
        logI(
          TAG,
          "Processing new android surface while current is not null, " +
            "releasing current EGL and recreating native renderer."
        )
        releaseAll()
      }
      this.surface = surface
    }
    this.width = width
    this.height = height
    widgetRenderer.onSurfaceChanged(width = width, height = height)
    prepareRenderFrame(creatingSurface = true)
  }

  @UiThread
  fun setScreenRefreshRate(refreshRate: Int) {
    renderHandlerThread.post {
      fpsManager.setScreenRefreshRate(refreshRate)
    }
  }

  @UiThread
  fun onSurfaceCreated(surface: Surface, width: Int, height: Int) {
    trace("surface-created") {
      logI(TAG, "onSurfaceCreated")
      lock.withLock {
        if (renderHandlerThread.isRunning) {
          renderHandlerThread.post {
            processAndroidSurface(surface, width, height)
          }
          logI(TAG, "onSurfaceCreated: waiting Android surface to be processed...")
          createCondition.await()
          logI(TAG, "onSurfaceCreated: Android surface was processed.")
        }
      }
    }
  }

  @AnyThread
  fun setUserRefreshRate(fps: Int) {
    renderHandlerThread.post {
      fpsManager.setUserRefreshRate(fps)
    }
  }

  @WorkerThread
  override fun doFrame(frameTimeNanos: Long) {
    trace("do-frame") {
      val startTime = if (renderThreadRecorder?.recording == true) {
        SystemClock.elapsedRealtimeNanos()
      } else {
        0L
      }
      // it makes sense to draw not only when EGL config is prepared but when native renderer is created
      if (renderThreadPrepared && !paused) {
        draw(frameTimeNanos)
      }
      awaitingNextVsync = false
      // It's critical to drain queue after setting `awaitingNextVsync` to false as some tasks may recursively schedule other tasks when executed.
      // With `awaitingNextVsync = false` we will always schedule recursive tasks for later execution
      // via `renderHandlerThread.postDelayed` instead of updating queue concurrently that is being drained (which may lead to deadlock in core).
      drainQueue(nonRenderEventQueue)
      val endTime = if (renderThreadRecorder?.recording == true) {
        SystemClock.elapsedRealtimeNanos()
      } else {
        0L
      }
      if (startTime != 0L && endTime != 0L) {
        renderThreadRecorder?.addFrameStats(
          (endTime - startTime) / 1e6,
          fpsManager.skippedNow
        )
      }
    }
  }

  @AnyThread
  fun queueRenderEvent(renderEvent: RenderEvent) {
    if (renderEvent.needRender) {
      renderEvent.runnable?.let {
        renderEventQueue.add(renderEvent)
      }
      renderPreparedGuardedRun {
        postPrepareRenderFrame()
      }
    } else {
      postNonRenderEvent(renderEvent)
    }
  }

  private inline fun renderPreparedGuardedRun(crossinline block: () -> Unit) {
    if (renderThreadPrepared) {
      block.invoke()
      return
    }
    // it may happen that Android surface is valid but renderThreadPrepared=false
    // due to eglContextMadeCurrent=false when we do `releaseEglSurface()` after some egl error;
    // in this case we try to re-setup render thread; if Android surface is invalid - we can't do anything
    // until Android system sends us the new one
    if (surface?.isValid == true) {
      logI(TAG, "renderThreadPrepared=false but Android surface is valid, trying to recreate EGL...")
      renderHandlerThread.post {
        if (setUpRenderThread(creatingSurface = true)) {
          block.invoke()
          logI(TAG, "Setting up render thread was OK, map should render again!")
        } else {
          logI(TAG, "Setting up render thread failed, check logs above.")
        }
      }
    } else {
      logI(TAG, "renderThreadPrepared=false and Android surface is not valid (isValid=${surface?.isValid}). Waiting for new one.")
    }
  }

  private fun postNonRenderEvent(renderEvent: RenderEvent, delayMillis: Long = 0L) {
    // if we already waiting listening for next VSYNC then add runnable to queue to execute
    // after actual drawing otherwise execute asap on render thread
    if (awaitingNextVsync) {
      nonRenderEventQueue.add(renderEvent)
    } else {
      renderHandlerThread.postDelayed(
        {
          when {
            renderThreadPrepared -> {
              renderEvent.runnable?.run()
            }
            paused -> {
              nonRenderEventQueue.add(renderEvent)
            }
            else -> {
              logW(TAG, "Non-render event could not be run, retrying in $RETRY_DELAY_MS ms...")
              postNonRenderEvent(renderEvent, delayMillis = RETRY_DELAY_MS)
            }
          }
        },
        delayMillis,
      )
    }
  }

  @UiThread
  fun pause() {
    paused = true
    logI(TAG, "Renderer paused")
  }

  @UiThread
  fun resume() {
    paused = false
    logI(TAG, "Renderer resumed, renderThreadPrepared=$renderThreadPrepared, surface.isValid=${surface?.isValid}")
    // schedule render if we resume not after first create (e.g. bring map back to front)
    renderPreparedGuardedRun {
      postPrepareRenderFrame()
    }
  }

  @UiThread
  internal fun destroy() {
    trace("destroy") {
      logI(TAG, "destroy")
      lock.withLock {
        // do nothing if destroy for some reason called more than once to avoid deadlock
        if (renderHandlerThread.isRunning) {
          renderHandlerThread.post {
            lock.withLock {
              if (nativeRenderCreated) {
                releaseAll()
              }
              renderHandlerThread.clearRenderEventQueue()
              fpsManager.destroy()
              eglCore.clearRendererStateListeners()
              destroyCondition.signal()
            }
          }
          logI(TAG, "destroy: waiting until all resources will be cleaned up...")
          destroyCondition.await()
          logI(TAG, "destroy: all resources were cleaned up.")
        }
      }
      renderHandlerThread.stop()
      mapboxRenderer.map = null
    }
  }

  private fun drainQueue(originalQueue: ConcurrentLinkedQueue<RenderEvent>) {
    var event = originalQueue.poll()
    while (event != null) {
      event.runnable?.run()
      event = originalQueue.poll()
    }
  }

  internal companion object {
    private const val TAG = "Mbgl-RenderThread"
    /**
     * If we hit some issue caused by invalid state (most likely caused by GPU driver) we start
     * rescheduling configuration with that delay in order not to overflood handler thread message queue.
     */
    internal const val RETRY_DELAY_MS = 50L
  }
}