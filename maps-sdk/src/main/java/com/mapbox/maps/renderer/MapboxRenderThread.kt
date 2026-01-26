package com.mapbox.maps.renderer

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.os.Trace
import android.view.Choreographer
import android.view.Surface
import androidx.annotation.AnyThread
import androidx.annotation.RestrictTo
import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import com.mapbox.common.MapboxTracing
import com.mapbox.common.MapboxTracing.MAPBOX_TRACE_ID
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.widget.Widget
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.properties.Delegates

/**
 * Manages the map rendering lifecycle on a dedicated thread.
 *
 * This abstract class is the core of the rendering engine, acting as a bridge between the Android
 * view system (specifically the [Surface]) and the underlying native Mapbox renderer. It runs on a
 * dedicated [RenderHandlerThread] to avoid blocking the main UI thread with potentially
 * long-running rendering tasks.
 *
 * ### Key Responsibilities:
 *
 * 1.  **Thread and Lifecycle Management:**
 *     - Initializes and manages a dedicated render thread ([RenderHandlerThread]).
 *     - Synchronizes with the main UI thread during critical lifecycle events like surface creation,
 *       resizing, and destruction using locks ([ReentrantLock]) and conditions ([Condition]) to
 *       prevent race conditions and ensure a consistent state.
 *     - Handles pausing and resuming of the renderer, stopping frame production when the map is not
 *       visible and resuming it when it comes back to the foreground.
 *
 * 2.  **Surface and Renderer Orchestration:**
 *     - Receives a [Surface] from the view system (e.g., `TextureView` or `SurfaceView`).
 *     - Prepares the graphics backend (e.g., OpenGL ES context) via the abstract [prepareRenderer]
 *       method.
 *     - Creates and manages the native [MapboxRenderer] instance, which communicates with the
 *       Mapbox Core engine.
 *     - Attaches the [Surface] to the renderer and handles size changes, notifying the graphics
 *       backend, the Mapbox Core engine, and any associated widget renderers.
 *
 * 3.  **The Rendering Loop:**
 *     - Implements [Choreographer.FrameCallback] to hook into the display's VSYNC signal.
 *     - The [doFrame] method is the heart of the render loop. On each VSYNC pulse, it orchestrates
 *       the sequence of operations required to render a single frame.
 *     - It calls [renderWithoutWidgets] or [renderWithWidgets] to execute native drawing
 *       commands ([MapboxRenderer].
 *     - After drawing, it calls [presentFrame] to swap the graphics buffers and display the newly
 *       rendered image on the screen.
 *
 * 4.  **Event and Task Queuing:**
 *     - Manages two concurrent queues for tasks: [renderEventQueue] and [nonRenderEventQueue].
 *     - [renderEventQueue]: Holds tasks that must be executed synchronously within the rendering
 *       cycle, such as OpenGL commands or snapshot requests. These are processed after the map is
 *       drawn but before the frame is presented.
 *     - [nonRenderEventQueue]: Holds tasks that can be executed asynchronously on the render thread
 *       but outside the immediate drawing sequence.
 *     - Provides the [queueRenderEvent] method to safely post runnables from any thread to be
 *       executed on the render thread.
 *
 * 5.  **Performance and Frame Pacing:**
 *     - Manages frame pacing through an [FpsManager], allowing for throttling the frame rate at a
 *       specific target FPS.
 *     - Can skip rendering frames if the desired FPS is lower than the display's refresh rate.
 *
 * Subclasses, such as [GLMapboxRenderThread], implement the abstract methods to provide the
 * specific graphics backend setup (e.g., EGL context management) required for rendering.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal abstract class MapboxRenderThread : Choreographer.FrameCallback {

  internal val renderHandlerThread: RenderHandlerThread
  protected val mapboxRenderer: MapboxRenderer
  protected val widgetRenderer: MapboxWidgetRenderer

  /**
   * [ReentrantLock] to guarantee consistent behaviour of surface create / destroy events.
   */
  private val surfaceProcessingLock: ReentrantLock

  /**
   * Condition used to lock the Android main thread until underlying renderer is set up.
   */
  private val createCondition: Condition

  /**
   * Condition used to lock the Android main thread until either the renderer is destroyed
   * or we understand renderer was already destroyed before (checking [isRendererReady] flag from render thread).
   */
  private val destroyCondition: Condition

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val renderEventQueue = ConcurrentLinkedQueue<RenderEvent>()

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val nonRenderEventQueue = ConcurrentLinkedQueue<RenderEvent>()

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var surface: Surface? = null

  @RenderThread
  private var width: Int = 0
  @RenderThread
  private var height: Int = 0

  /**
   * Flag that signals that we're waiting for the next [doFrame] (VSYNC) to run.
   */
  @Volatile
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var awaitingNextVsync = false

  @Volatile
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var paused = false

  @OptIn(MapboxExperimental::class)
  internal var renderThreadStatsRecorder: RenderThreadStatsRecorder? = null

  /**
   * Handler for posting tasks to the main thread.
   */
  private val mainHandler by lazy {
    Handler(Looper.getMainLooper())
  }

  private val renderThreadPreparedLock = ReentrantLock()

  /**
   * We track when native Map renderer is prepared ([MapboxRenderer.createRenderer]).
   */
  @get:RenderThread
  @set:AnyThread
  protected var nativeMapRenderCreated = false
    // no need for synchronized getter, getting value for this var happens on render thread only
    set(value) = renderThreadPreparedLock.withLock {
      field = value
    }

  /**
   * We track when renderer (e.g. OpenGL ES context) is created and associated with current
   * Android surface.
   */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  @get:RenderThread
  @set:AnyThread
  internal var isRendererReady = false
    set(value) = renderThreadPreparedLock.withLock {
      field = value
    }

  /**
   * Render thread should be treated as valid (prepared to render a map) when both flags are true.
   * Getter is thread-safe as this flag could be accessed from any thread.
   */
  protected val renderThreadPrepared: Boolean
    get() = renderThreadPreparedLock.withLock {
      isRendererReady && nativeMapRenderCreated
    }

  /**
   * Flag that signals that renderer is not supported.
   */
  private var rendererNotSupported = false

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
   * Modified from render thread only, needed to understand when exactly to present frame
   * to achieve better synchronization with view annotation updates.
   */
  @RenderThread
  internal var needViewAnnotationSync = false
  @Volatile
  internal var viewAnnotationMode = ViewAnnotationManager.DEFAULT_UPDATE_MODE

  @Suppress("PropertyName")
  protected val TAG: String

  constructor(
    mapboxRenderer: MapboxRenderer,
    widgetRenderer: MapboxWidgetRenderer,
    mapName: String,
    rendererName: String
  ) {
    this.mapboxRenderer = mapboxRenderer
    this.widgetRenderer = widgetRenderer
    this.TAG = "${rendererName}RenderThread" + if (mapName.isNotBlank()) "\\$mapName" else ""
    renderHandlerThread = RenderHandlerThread(mapName)
    val handler = renderHandlerThread.start()
    fpsManager = FpsManager(handler, mapName)
    surfaceProcessingLock = ReentrantLock()
    createCondition = surfaceProcessingLock.newCondition()
    destroyCondition = surfaceProcessingLock.newCondition()
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  constructor(
    mapboxRenderer: MapboxRenderer,
    mapboxWidgetRenderer: MapboxWidgetRenderer,
    handlerThread: RenderHandlerThread,
    fpsManager: FpsManager,
    surfaceProcessingLock: ReentrantLock,
    createCondition: Condition,
    destroyCondition: Condition,
  ) {
    this.TAG = ""
    this.widgetRenderer = mapboxWidgetRenderer
    this.mapboxRenderer = mapboxRenderer
    this.renderHandlerThread = handlerThread
    this.fpsManager = fpsManager
    this.surfaceProcessingLock = surfaceProcessingLock
    this.createCondition = createCondition
    this.destroyCondition = destroyCondition
  }

  protected abstract fun prepareRenderer(): Boolean
  protected abstract fun prepareWidgetRender()
  protected abstract fun attachSurfaceToRenderer(surface: Surface): Boolean
  protected abstract fun detachSurfaceFromRenderer(creatingSurface: Boolean)
  protected abstract fun presentFrame()
  protected abstract fun preRenderWithSharedContext()
  protected abstract fun renderWithWidgets()
  protected abstract fun renderWithoutWidgets()
  protected abstract fun releaseResources()
  protected abstract fun releaseRenderSurface()
  protected abstract fun clearRendererStateListeners()
  protected abstract fun flushCommands()
  abstract fun addRendererStateListener(listener: RendererSetupErrorListener)
  abstract fun removeRendererStateListener(listener: RendererSetupErrorListener)
  abstract fun resize(width: Int, height: Int)

  protected inline fun trace(sectionName: String, section: (() -> Unit)) {
    if (MapboxTracing.platformTracingEnabled) {
      Trace.beginSection("$MAPBOX_TRACE_ID: $sectionName")
      try {
        section.invoke()
      } finally {
        Trace.endSection()
      }
    } else {
      section.invoke()
    }
  }

  /**
   * Keep a runnable to [prepareRenderFrame] to avoid creating a new one on every frame.
   */
  private val prepareRenderFrameRunnable: Runnable = Runnable {
      prepareRenderFrame(width = null, height = null, creatingSurface = false)
  }
  protected fun postPrepareRenderFrame(delayMillis: Long = 0L) {
    renderHandlerThread.postDelayed(
      prepareRenderFrameRunnable,
      delayMillis
    )
  }

  /**
   * Setting up the render thread means that:
   * - the Mapbox Map renderer is created ([MapboxRenderer.createRenderer])
   * - The graphics renderer backend is prepared ([prepareRenderer]) and ready to render ([isRendererReady])
   */
  private fun setUpRenderThread(creatingSurface: Boolean): Boolean {
    surfaceProcessingLock.withLock {
      try {
        logI(
          TAG,
          "Setting up render thread, flags:" +
            " creatingSurface=$creatingSurface," +
            " isRendererReady=$isRendererReady," +
            " nativeMapRenderCreated=$nativeMapRenderCreated," +
            " paused=$paused"
        )
        val rendererPrepared = prepareRenderer()
        logI(TAG, "Renderer prepared: $rendererPrepared")
        rendererNotSupported = !rendererPrepared
        val androidSurfaceReady = checkAndroidSurface()
        if (rendererPrepared && androidSurfaceReady) {
          detachSurfaceFromRenderer(creatingSurface)
          // it's safe to use !! here as we checked surface above
          isRendererReady = attachSurfaceToRenderer(surface!!)
          if (isRendererReady) {
            if (!nativeMapRenderCreated) {
              // we set `nativeMapRenderCreated` as `true` before creating native render as core could potentially
              // schedule task in the same callchain and we need to make sure that `renderThreadPrepared` is already `true`
              // so that we do not drop this task
              nativeMapRenderCreated = true
              mapboxRenderer.createRenderer()
              logI(TAG, "Native renderer created.")
            }
            return true
          }
        }
        return false
      } finally {
        createCondition.signal()
      }
    }
  }

  private fun checkAndroidSurface(): Boolean {
    return if (surface?.isValid == true) {
      true
    } else {
      logW(TAG, "Android surface.isValid=${surface?.isValid}, waiting ${RETRY_DELAY_MS}ms for a new one...")
      // give system a bit of time and try rendering again hoping surface will be valid now
      postPrepareRenderFrame(delayMillis = RETRY_DELAY_MS)
      false
    }
  }

  @RenderThread
  private fun notifyRenderersSizeChanged(width: Int, height: Int) {
    mapboxRenderer.onSurfaceChanged(width = width, height = height)
    widgetRenderer.onSurfaceChanged(width = width, height = height)
  }

  private val presentFrameFunc = Choreographer.FrameCallback { presentFrame() }

  @OptIn(MapboxExperimental::class)
  private fun draw(frameTimeNanos: Long) {
    if (!fpsManager.preRender(frameTimeNanos, renderThreadStatsRecorder?.isRecording == true)) {
      // when we have FPS limited and desire to skip core render - we must schedule new draw call
      // otherwise map may remain in not fully loaded state
      postPrepareRenderFrame()
      return
    }
    preRenderWithSharedContext()
    if (widgetRenderer.hasWidgets() == true) {
      renderWithWidgets()
    } else {
      renderWithoutWidgets()
    }

    // assuming render event queue holds user's runnables with renderer commands (e.g. OpenGL
    // commands) it makes sense to execute them after drawing a map but before swapping buffers
    // **note** this queue also holds snapshot tasks
    drainQueue(renderEventQueue)
    fpsManager.postRender()
    if (needViewAnnotationSync && viewAnnotationMode == ViewAnnotationUpdateMode.MAP_SYNCHRONIZED) {
      // when we're syncing view annotations with the map -
      // we present frame the next frame to achieve better synchronization with view annotations update
      // that always happens 1 frame later
      Choreographer.getInstance().postFrameCallback(presentFrameFunc)
      flushCommands()
    } else {
      // present frame immediately if no view annotations are visible or mode is not MAP_SYNCHRONIZED
     presentFrame()
    }
    // always reset the flag
    needViewAnnotationSync = false
  }

  protected fun releaseAll(tryRecreate: Boolean = false) {
    trace("release-all") {
      mapboxRenderer.destroyRenderer()
      logI(TAG, "Native renderer destroyed.")
      renderEventQueue.clear()
      nonRenderEventQueue.clear()
      nativeMapRenderCreated = false
      releaseResources()
      if (tryRecreate) {
        if (setUpRenderThread(creatingSurface = true)) {
          notifyRenderersSizeChanged(width, height)
        }
      } else {
        surface?.release()
      }
    }
  }

  /**
   * Responsible to prepare for rendering and schedule a new frame render (synchronized with the
   * display's refresh rate using [Choreographer]).
   * There are 3 main reasons to request a new frame:
   * 1. A new surface is available ([creatingSurface] flag is true and [width] and [height] are given).
   * 2. The surface was resized (new [width] and [height] are given).
   * 3. Normal render pass ([creatingSurface] flag is false and no sizes are given).
   */
  @RenderThread
  private fun prepareRenderFrame(
    width: Int?,
    height: Int?,
    creatingSurface: Boolean
  ) {
    // no need to do anything if we're already waiting for next VSYNC (`doFrame`);
    // however if Android has sent us new surface - we must proceed up to `setUpRenderThread` or
    // otherwise main thread will end up having deadlock
    // We also should go through if we've new sizes to set
    if (awaitingNextVsync && !creatingSurface && width == null && height == null) {
      return
    }
    // We need to check ASAP if we have to stop rendering in order not to block thread that is calling `onSurfaceTextureDestroyed`:
    // - Check MapView could be actually rendered on this device (renderer configuration is not supported).
    // - Check if activity / fragment is paused.
    if (rendererNotSupported || paused) {
      // at least on Android 8 devices we create surface before Activity#onStart (i.e. still paused)
      // so we need to proceed to EGL creation in any case to avoid deadlock
      if (!creatingSurface) {
        logI(
          TAG,
          "Skip render frame - NOT creating surface although " +
            "rendererNotSupported ($rendererNotSupported) || paused ($paused)"
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
    prepareWidgetRender()
    if (width != null && height != null && renderThreadPrepared) {
      notifyRenderersSizeChanged(width, height)
    }
    // Finally schedule next doFrame call
    Choreographer.getInstance().postFrameCallback(this)
    awaitingNextVsync = true
  }

  @UiThread
  fun onSurfaceSizeChanged(width: Int, height: Int) {
    renderHandlerThread.post {
      if (this.width != width || this.height != height) {
        this@MapboxRenderThread.width = width
        this@MapboxRenderThread.height = height
        // Schedule a new frame to draw map with the new size
        prepareRenderFrame(width = width, height = height, creatingSurface = false)
      }
    }
  }

  @UiThread
  fun onSurfaceDestroyed() {
    trace("surface-destroyed") {
      logI(TAG, "onSurfaceDestroyed")
      surfaceProcessingLock.withLock {
        if (renderHandlerThread.isRunning) {
          renderHandlerThread.post {
            awaitingNextVsync = false
            Choreographer.getInstance().removeFrameCallback(this@MapboxRenderThread)
            surfaceProcessingLock.withLock {
              // TODO https://github.com/mapbox/mapbox-maps-android/issues/607
              if (nativeMapRenderCreated && mapboxRenderer is MapboxTextureViewRenderer) {
                releaseAll()
                renderHandlerThread.clearRenderEventQueue()
              } else {
                releaseRenderSurface()
              }
              fpsManager.onSurfaceDestroyed()
              destroyCondition.signal()
            }
          }
          logI(TAG, "onSurfaceDestroyed: waiting until EGL will be cleaned up...")
          destroyCondition.await()
          logI(TAG, "onSurfaceDestroyed: EGL resources were cleaned up.")
        } else {
          logI(TAG, "onSurfaceDestroyed: render thread is not running.")
        }
      }
    }
  }

  @OptIn(MapboxExperimental::class)
  fun addWidget(widget: Widget) {
    widgetRenderer.addWidget(widget)
  }

  @OptIn(MapboxExperimental::class)
  fun removeWidget(widget: Widget): Boolean = widgetRenderer.removeWidget(widget)

  @RenderThread
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
    // Make sure we initialize renderer and schedule next frame to draw map.
    prepareRenderFrame(width, height, creatingSurface = true)
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
      surfaceProcessingLock.withLock {
        if (renderHandlerThread.isRunning) {
          renderHandlerThread.post {
            this.width = width
            this.height = height
            processAndroidSurface(surface, width, height)
          }
          logI(TAG, "onSurfaceCreated: waiting Android surface to be processed...")
          createCondition.await()
          logI(TAG, "onSurfaceCreated: Android surface was processed.")
        } else {
          logI(TAG, "onSurfaceCreated: render thread is not running.")
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

  @OptIn(MapboxExperimental::class)
  @RenderThread
  final override fun doFrame(frameTimeNanos: Long) {
    trace("do-frame") {
      val startTime = if (renderThreadStatsRecorder?.isRecording == true) {
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
      val endTime = if (renderThreadStatsRecorder?.isRecording == true) {
        SystemClock.elapsedRealtimeNanos()
      } else {
        0L
      }
      if (startTime != 0L && endTime != 0L) {
        renderThreadStatsRecorder?.addFrameStats(
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
      logI(TAG, "renderThreadPrepared=false but Android surface is valid, trying to setup render thread again...")
      renderHandlerThread.post {
        if (setUpRenderThread(creatingSurface = true)) {
          block.invoke()
          logI(TAG, "Setting up render thread was OK, map should render again!")
        } else {
          logI(TAG, "Setting up render thread failed, check logs above.")
        }
      }
    } else if (!paused) {
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

  /**
   * Schedules a resetThreadServiceType on the main thread after a specified delay.
   * This method is called to ensure CPU affinity is properly set
   * when coming back from background, addressing timing issues where CPU affinity
   * might not be immediately available.
   */
  @OptIn(MapboxExperimental::class)
  fun scheduleThreadServiceTypeReset() {
    logI(TAG, "Scheduling thread service type reset with delay")
    mainHandler.postDelayed({
      // log current thread name to help debug
      logI(TAG, "Executing thread service type reset from ${Thread.currentThread().name} thread")
      mapboxRenderer.resetThreadServiceType()
    }, RESET_THREAD_SERVICE_TYPE_DELAY_MS)
  }

  @UiThread
  fun pause() {
    paused = true
    logI(TAG, "Renderer paused")
  }

  @UiThread
  fun resume() {
    paused = false
    logI(
      TAG,
      "Renderer resumed, renderThreadPrepared=$renderThreadPrepared, surface.isValid=${surface?.isValid}"
    )
    // schedule render if we resume not after first create (e.g. bring map back to front)
    renderPreparedGuardedRun(::postPrepareRenderFrame)
  }

  @UiThread
  internal fun destroy() {
    trace("destroy") {
      logI(TAG, "destroy")
      surfaceProcessingLock.withLock {
        if (renderHandlerThread.isRunning) {
          renderHandlerThread.post {
            var synchronousCleanUpNeeded = true
            surfaceProcessingLock.lock()
            try {
              // For OpenGL renderer:
              // It is safe to release main thread immediately (destroyCondition.signal()) if
              // renderer is not ready (i.e. EGLSurface was destroyed and EGLContext was cleared
              // beforehand in onSurfaceDestroyed)
              if (!isRendererReady) {
                destroyCondition.signal()
                surfaceProcessingLock.unlock()
                synchronousCleanUpNeeded = false
              }
              if (nativeMapRenderCreated) {
                releaseAll()
              }
              renderHandlerThread.clearRenderEventQueue()
              fpsManager.destroy()
              clearRendererStateListeners()
              mapboxRenderer.map = null
              renderHandlerThread.stop()
            } finally {
              if (synchronousCleanUpNeeded) {
                destroyCondition.signal()
                surfaceProcessingLock.unlock()
              } else {
                // if the system somehow sent us surfaceCreated / surfaceDestroyed from main thread
                // during async destroy - signal to release the lock on main thread to avoid ANR
                surfaceProcessingLock.withLock {
                  createCondition.signal()
                }
                surfaceProcessingLock.withLock {
                  destroyCondition.signal()
                }
              }
            }
          }
          logI(TAG, "destroy: waiting until all resources will be cleaned up...")
          destroyCondition.await()
          logI(TAG, "destroy: all resources were cleaned up.")
        } else {
          logI(TAG, "destroy: render thread is not running.")
        }
      }
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
    /**
     * If we hit some issue caused by invalid state (most likely caused by GPU driver) we start
     * rescheduling configuration with that delay in order not to overflood handler thread message queue.
     */
    internal const val RETRY_DELAY_MS = 50L
    /**
     * Delay before calling resetThreadServiceType() on resume to ensure CPU affinity is properly set.
     * This delay helps address timing issues where CPU affinity might not be set immediately
     * when coming back from background.
     */
    internal const val RESET_THREAD_SERVICE_TYPE_DELAY_MS = 300L
  }
}