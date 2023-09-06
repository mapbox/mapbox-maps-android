package com.mapbox.maps.renderer

import android.opengl.EGL14
import android.opengl.EGLContext
import android.view.Surface
import com.mapbox.countDownEvery
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.MapboxRenderThread.Companion.RETRY_DELAY_MS
import com.mapbox.maps.renderer.egl.EGLCore
import com.mapbox.maps.renderer.gl.TextureRenderer
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
import com.mapbox.verifyNo
import com.mapbox.verifyOnce
import com.mapbox.waitZeroCounter
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowChoreographer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class MapboxRenderThreadTest {

  private lateinit var mapboxRenderThread: MapboxRenderThread
  private lateinit var mapboxRenderer: MapboxRenderer
  private lateinit var mapboxWidgetRenderer: MapboxWidgetRenderer
  private lateinit var eglCore: EGLCore
  private lateinit var renderHandlerThread: RenderHandlerThread
  private lateinit var textureRenderer: TextureRenderer
  private lateinit var surface: Surface
  private lateinit var fpsManager: FpsManager

  private fun initRenderThread(mapboxRenderer: MapboxRenderer = mockk(relaxUnitFun = true)) {
    this.mapboxRenderer = mapboxRenderer
    mockEglCore()
    mockWidgetRenderer()
    renderHandlerThread = RenderHandlerThread()
    textureRenderer = mockk(relaxed = true)
    fpsManager = mockk(relaxUnitFun = true)
    every { fpsManager.preRender(any()) } returns true
    mapboxRenderThread = MapboxRenderThread(
      mapboxRenderer,
      mapboxWidgetRenderer,
      renderHandlerThread,
      eglCore,
      fpsManager,
      textureRenderer,
    )
    renderHandlerThread.start()
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    every { logW(any(), any()) } just Runs
    every { logI(any(), any()) } just Runs
  }

  private fun mockSurface() {
    surface = mockk()
    every { surface.isValid } returns true
    every { surface.release() } just Runs
  }

  private fun mockWidgetRenderer() {
    mapboxWidgetRenderer = mockk(relaxUnitFun = true)
    every { mapboxWidgetRenderer.getTexture() } returns 0
    every { mapboxWidgetRenderer.hasTexture() } returns false
    every { mapboxWidgetRenderer.needTextureUpdate } returns false
    every { mapboxWidgetRenderer.hasWidgets() } returns false
  }

  private fun mockEglCore() {
    eglCore = mockk(relaxUnitFun = true)
    every { eglCore.eglNoSurface } returns mockk()
    every { eglCore.eglContext } returns mockk()
    every { eglCore.prepareEgl() } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    every { eglCore.makeNothingCurrent() } returns true
    every { eglCore.makeCurrent(any()) } returns true
    every { eglCore.swapBuffers(any()) } returns EGL14.EGL_SUCCESS
  }

  private fun pauseHandler() = Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()

  /**
   * advances system clock by [millis] and executes all the tasks posted by the advanced time
   * by default millis = 0 (e.g. only tasks posted without delay are executed)
   */
  private fun idleHandler(
    millis: Long = 0L
  ) = Shadows.shadowOf(renderHandlerThread.handler?.looper).idleFor(millis, TimeUnit.MILLISECONDS)

  private fun provideValidSurface() {
    mockSurface()
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    idleHandler()
  }

  private fun mockCountdownRunnable(latch: CountDownLatch) =
    mockk<Runnable>(relaxUnitFun = true).also {
      every { it.run() } answers { latch.countDown() }
    }

  @After
  fun cleanup() {
    renderHandlerThread.stop()
    unmockkStatic("com.mapbox.maps.MapboxLogger")
    unmockkAll()
  }

  @Test
  fun onSurfaceCreatedTest() {
    initRenderThread()
    provideValidSurface()
    verifyOnce {
      mapboxRenderer.createRenderer()
      eglCore.makeNothingCurrent()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onInvalidEglSurfaceNotCreateRenderer() {
    initRenderThread()
    every { eglCore.createWindowSurface(any()) } returns eglCore.eglNoSurface
    provideValidSurface()
    verifyNo {
      mapboxRenderer.createRenderer()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onInvalidSurfaceNotInitNativeRenderer() {
    initRenderThread()
    val surface = mockk<Surface>()
    every { surface.isValid } returns false
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    idleHandler()
    verifyNo {
      mapboxRenderer.createRenderer()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onEglCorePrepareFailNotInitNativeRenderer() {
    initRenderThread()
    every { eglCore.prepareEgl() } returns false
    provideValidSurface()
    verifyNo {
      mapboxRenderer.createRenderer()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onMakeCurrentErrorNotInitNativeRenderer() {
    initRenderThread()
    every { eglCore.makeCurrent(any()) } returns false
    provideValidSurface()
    verifyNo {
      mapboxRenderer.createRenderer()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceSizeChangedTest() {
    initRenderThread()
    provideValidSurface()
    pauseHandler()
    mapboxRenderThread.onSurfaceSizeChanged(2, 2)
    idleHandler()
    verifyOnce {
      mapboxRenderer.createRenderer()
      mapboxRenderer.onSurfaceChanged(1, 1)
      mapboxRenderer.onSurfaceChanged(2, 2)
    }
  }

  @Test
  fun onSurfaceSizeChangedSameSizeTest() {
    initRenderThread()
    provideValidSurface()
    pauseHandler()
    mapboxRenderThread.onSurfaceSizeChanged(1, 1)
    idleHandler()
    verifyOnce {
      mapboxRenderer.createRenderer()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceWithActivityDestroyedAfterSurfaceTest() {
    initRenderThread()
    provideValidSurface()
    waitZeroCounter {
      countDownEvery { mapboxRenderer.destroyRenderer() }
      mapboxRenderThread.onSurfaceDestroyed()
      mapboxRenderThread.destroy()
    }
    verifyOnce { eglCore.release() }
    verifyOnce { mapboxRenderer.destroyRenderer() }
    verifyOnce { fpsManager.destroy() }
    assertFalse(renderHandlerThread.isRunning)
  }

  @Test
  fun onSurfaceWithActivityDestroyedBeforeSurfaceTest() {
    initRenderThread()
    provideValidSurface()
    waitZeroCounter {
      countDownEvery { mapboxRenderer.destroyRenderer() }
      mapboxRenderThread.destroy()
      mapboxRenderThread.onSurfaceDestroyed()
    }
    verifyOnce { eglCore.release() }
    verifyOnce { mapboxRenderer.destroyRenderer() }
    verifyOnce { fpsManager.destroy() }
    assertFalse(renderHandlerThread.isRunning)
  }

  @Test
  fun onDrawFrameSeparateRequestRender() {
    initRenderThread()
    provideValidSurface()
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    // one swap buffer for surface creation, two for not squashed render requests
    verify(exactly = 3) {
      eglCore.swapBuffers(any())
    }
    // make EGL context current only once when creating surface
    verifyOnce {
      eglCore.makeNothingCurrent()
    }
  }

  @Test
  fun needViewAnnotationSyncMapSynchronized() {
    initRenderThread()
    provideValidSurface()
    val choreographerCallbackDelayMs = 16L
    // static method so does not matter which thread is called from -
    // we need this interval for our render thread
    ShadowChoreographer.setPostFrameCallbackDelay(choreographerCallbackDelayMs.toInt())
    pauseHandler()
    mapboxRenderThread.viewAnnotationMode = ViewAnnotationUpdateMode.MAP_SYNCHRONIZED
    mapboxRenderThread.needViewAnnotationSync = true
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler(choreographerCallbackDelayMs)
    // buffers are already swapped once when surface was created
    // they should not be swapped this frame N, they need to be swapped next frame N+1
    verifyOnce {
      eglCore.swapBuffers(any())
    }
    // we need another explicit IDLE call as we're performing Choreographer.getInstance().postFrameCallback
    // inside doFrame we're already executing
    idleHandler(choreographerCallbackDelayMs)
    // we swap buffers for frame N+1
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun needViewAnnotationSyncMapFixedDelay() {
    initRenderThread()
    provideValidSurface()
    val choreographerCallbackDelayMs = 16L
    // static method so does not matter which thread is called from -
    // we need this interval for our render thread
    ShadowChoreographer.setPostFrameCallbackDelay(choreographerCallbackDelayMs.toInt())
    pauseHandler()
    mapboxRenderThread.viewAnnotationMode = ViewAnnotationUpdateMode.MAP_FIXED_DELAY
    mapboxRenderThread.needViewAnnotationSync = true
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler(choreographerCallbackDelayMs)
    // buffers are already swapped once when surface was created
    // they should be swapped on this frame N
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
    // we need another explicit IDLE call to make sure we don't have anything scheduled
    idleHandler(choreographerCallbackDelayMs)
    // count should be the same as we already swapped buffers for frame N
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun onDrawFrameSquashedRequestRender() {
    initRenderThread()
    provideValidSurface()
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    // one swap buffer for surface creation, 2 render requests squash in one swap buffers call
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun setUserRefreshRateTest() {
    initRenderThread()
    val userRefreshRate = 30
    mapboxRenderThread.setUserRefreshRate(userRefreshRate)
    idleHandler()
    verifyOnce {
      fpsManager.setUserRefreshRate(userRefreshRate)
    }
  }

  @Test
  fun pauseTest() {
    initRenderThread()
    provideValidSurface()
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    mapboxRenderThread.pause()
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    // one swap buffer for surface creation, one request render after pause is omitted
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun resumeTestWithRequestRenderAtPause() {
    initRenderThread()
    provideValidSurface()
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    mapboxRenderThread.pause()
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    mapboxRenderThread.resume()
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    // render requests after pause do not swap buffer, we do it on resume if needed once
    verify(exactly = 4) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun resumeTestWithoutRequestRenderAtPause() {
    initRenderThread()
    provideValidSurface()
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    mapboxRenderThread.pause()
    idleHandler()
    mapboxRenderThread.resume()
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    // we always do extra render call on resume
    verify(exactly = 4) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun destroyTest() {
    initRenderThread()
    mapboxRenderThread.destroy()
    assertFalse(renderHandlerThread.isRunning)
  }

  @Test
  fun queueRenderEventTest() {
    initRenderThread()
    provideValidSurface()
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        true,
      )
    )
    assertEquals(1, mapboxRenderThread.renderEventQueue.size)
    idleHandler()
    assert(mapboxRenderThread.renderEventQueue.isEmpty())
    verifyOnce { runnable.run() }
    // one swap buffer from surface creation, one for custom event
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun queueNonRenderEventTestNoVsync() {
    initRenderThread()
    provideValidSurface()
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    mapboxRenderThread.awaitingNextVsync = false
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        false,
      )
    )
    // we do not add non-render event to the queue
    assert(mapboxRenderThread.nonRenderEventQueue.isEmpty())
    // do not schedule any render requests explicitly
    idleHandler()
    verifyOnce { runnable.run() }
    // one swap buffer from surface creation only
    verifyOnce {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun queueNonRenderEventTestWithVsync() {
    initRenderThread()
    provideValidSurface()
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    mapboxRenderThread.awaitingNextVsync = true
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        false,
      )
    )
    // we add to the queue
    assert(mapboxRenderThread.nonRenderEventQueue.size == 1)
    // do not schedule any render requests explicitly
    idleHandler()
    // without explicit render event runnable should not be executed
    verifyNo { runnable.run() }
    mapboxRenderThread.awaitingNextVsync = false
    pauseHandler()
    // schedule render request
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    assert(mapboxRenderThread.nonRenderEventQueue.isEmpty())
    // one swap buffer from surface creation + one for render request
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
    verifyOnce { runnable.run() }
  }

  @Test
  fun queueNonRenderEventLoosingSurfaceTest() {
    initRenderThread()
    provideValidSurface()
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        false,
      )
    )
    // simulate render thread is not fully prepared, e.g. EGL context is lost
    mapboxRenderThread.eglContextMadeCurrent = false
    idleHandler()
    verifyNo { runnable.run() }
    pauseHandler()
    // simulate render thread is fully prepared again
    mapboxRenderThread.eglContextMadeCurrent = true
    mapboxRenderThread.processAndroidSurface(surface, 1, 1)
    // taking into account we try to reschedule event with some delay
    idleHandler(RETRY_DELAY_MS)
    // user's runnable is executed when thread is fully prepared again
    verifyOnce { runnable.run() }
  }

  @Test
  fun fpsListenerTest() {
    initRenderThread()
    mapboxRenderThread.fpsChangedListener = mockk()
    provideValidSurface()
    pauseHandler()
    every { fpsManager.preRender(any()) } returns false
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    every { fpsManager.preRender(any()) } returns true
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    // one swap for surface creation and one more when preRender returns true
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun sameFpsListenerTest() {
    initRenderThread()
    val fpsChangedListener = mockk<OnFpsChangedListener>()
    provideValidSurface()
    mapboxRenderThread.fpsChangedListener = fpsChangedListener
    idleHandler()
    mapboxRenderThread.fpsChangedListener = fpsChangedListener
    idleHandler()
    verifyOnce {
      fpsManager.fpsChangedListener = fpsChangedListener
    }
  }

  @Test
  fun queueNonRenderEventAfterOnStop() {
    initRenderThread()
    provideValidSurface()
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    mapboxRenderThread.awaitingNextVsync = false

    // paused state
    mapboxRenderThread.eglContextMadeCurrent = false
    mapboxRenderThread.pause()
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        false,
      )
    )
    idleHandler()
    // event should be added to the queue and not executed until `resume`
    assertEquals(1, mapboxRenderThread.nonRenderEventQueue.size)
    verifyNo { runnable.run() }

    // resumed state
    mapboxRenderThread.eglContextMadeCurrent = true
    mapboxRenderThread.resume()
    idleHandler()
    assertEquals(0, mapboxRenderThread.nonRenderEventQueue.size)
    verifyOnce { runnable.run() }
  }

  @Test
  fun surfaceCreatedCalledBeforeActivityStartTest() {
    initRenderThread()
    mapboxRenderThread.paused = true
    provideValidSurface()
    verifyOnce { mapboxRenderer.createRenderer() }
    // EGL should be fully prepared
    verifyNo { eglCore.releaseSurface(any()) }
  }

  @Test
  fun snapshotsAreTakenAfterDrawAndBeforeSwapBuffers() {
    initRenderThread()
    provideValidSurface()

    lateinit var runnable: Runnable
    lateinit var runnable2: Runnable
    lateinit var runnable3: Runnable
    waitZeroCounter(startCounter = 3) {
      runnable = mockCountdownRunnable(this)
      runnable2 = mockCountdownRunnable(this)
      runnable3 = mockCountdownRunnable(this)

      pauseHandler()

      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable, true))
      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable2, true))
      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable3, true))

      idleHandler()
    }

    verifyOrder {
      mapboxRenderer.render()
      runnable.run()
      runnable2.run()
      runnable3.run()
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun onSurfaceDestroyedWithRenderCallAfterTestSurfaceView() {
    initRenderThread(mockk<MapboxSurfaceRenderer>(relaxUnitFun = true))
    provideValidSurface()
    mapboxRenderThread.onSurfaceDestroyed()
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    verifyNo {
      // we do not destroy native renderer if it's stop and not destroy
      mapboxRenderer.destroyRenderer()
      eglCore.release()
    }
    // we clear only EGLSurface but not all EGL
    verifyOnce { eglCore.releaseSurface(any()) }
    // we notify fps manager
    verifyOnce { fpsManager.onSurfaceDestroyed() }
  }

  @Test
  fun onSurfaceDestroyedWithRenderCallAfterTestTextureView() {
    initRenderThread(mockk<MapboxTextureViewRenderer>(relaxUnitFun = true))
    provideValidSurface()
    mapboxRenderThread.onSurfaceDestroyed()
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()

    verifyOnce {
      // we do destroy native renderer if it's stop (for texture renderer)
      mapboxRenderer.destroyRenderer()
      // we clear all EGL
      eglCore.releaseSurface(any())
      eglCore.release()
      // we notify fps manager
      fpsManager.onSurfaceDestroyed()
    }
  }

  @Test
  fun renderWithMaxFpsSet() {
    initRenderThread()
    provideValidSurface()
    mapboxRenderThread.setUserRefreshRate(15)
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    // 1 swap when creating surface + 1 for request render call
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun onDrawDoesNotRenderWidgets() {
    initRenderThread()
    provideValidSurface()
    every { mapboxWidgetRenderer.needTextureUpdate } returns false
    every { mapboxWidgetRenderer.getTexture() } returns 0
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    verifyNo {
      mapboxWidgetRenderer.updateTexture()
      textureRenderer.render(any())
    }
  }

  @Test
  fun onDrawRendersWidgets() {
    initRenderThread()
    provideValidSurface()
    val textureId = 1
    every { mapboxWidgetRenderer.needTextureUpdate } returns true
    every { mapboxWidgetRenderer.hasWidgets() } returns true
    every { mapboxWidgetRenderer.hasTexture() } returns true
    every { mapboxWidgetRenderer.getTexture() } returns textureId
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    verify(exactly = 2) {
      mapboxWidgetRenderer.updateTexture()
      textureRenderer.render(textureId)
    }
  }

  @Test
  fun onDrawRendersWidgetsBeforeMap() {
    initRenderThread()
    provideValidSurface()
    val textureId = 1
    every { mapboxWidgetRenderer.needTextureUpdate } returns true
    every { mapboxWidgetRenderer.hasWidgets() } returns true
    every { mapboxWidgetRenderer.hasTexture() } returns true
    every { mapboxWidgetRenderer.getTexture() } returns textureId
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    verifyOrder {
      mapboxWidgetRenderer.updateTexture()
      mapboxRenderer.render()
      textureRenderer.render(textureId)
    }
  }

  @Test
  fun onSurfaceCreatedWidgetsInitWidgetRender() {
    initRenderThread()
    val eglContext = mockk<EGLContext>()
    every { eglCore.eglContext } returns eglContext
    every { mapboxWidgetRenderer.hasWidgets() } returns true
    provideValidSurface()
    verifyOnce {
      mapboxWidgetRenderer.setSharedContext(eglContext)
    }
  }

  @Test
  fun onSurfaceCreatedNoWidgetsNotInitWidgetRender() {
    initRenderThread()
    every { mapboxWidgetRenderer.hasWidgets() } returns false
    provideValidSurface()
    verifyNo {
      mapboxWidgetRenderer.setSharedContext(any())
    }
  }

  @Test
  fun onEglCorePrepareFailNotInitWidgetRender() {
    initRenderThread()
    every { eglCore.prepareEgl() } returns false
    provideValidSurface()
    verifyNo {
      mapboxWidgetRenderer.setSharedContext(any())
    }
  }

  @Test
  fun onInvalidSurfaceNotInitWidgetRender() {
    initRenderThread()
    val surface = mockk<Surface>()
    every { surface.isValid } returns false
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    idleHandler()
    verifyNo {
      mapboxWidgetRenderer.setSharedContext(any())
    }
  }

  @Test
  fun onInvalidEglSurfaceNotInitWidgetRender() {
    initRenderThread()
    every { eglCore.createWindowSurface(any()) } returns eglCore.eglNoSurface
    provideValidSurface()
    verifyNo {
      mapboxWidgetRenderer.setSharedContext(any())
    }
  }

  @Test
  fun onMakeCurrentErrorNotInitWidgetRender() {
    initRenderThread()
    every { eglCore.makeCurrent(any()) } returns false
    provideValidSurface()
    verifyNo {
      mapboxWidgetRenderer.setSharedContext(any())
    }
  }

  @Test
  fun onDestroyClearsAllTasks() {
    initRenderThread()
    every { eglCore.makeCurrent(any()) } returns true
    val runnables = listOf<Runnable>(
      mockk(relaxUnitFun = true),
      mockk(relaxUnitFun = true),
      mockk(relaxUnitFun = true),
    )
    // first is render event, second and third are non-render events
    val events = runnables.mapIndexed { i, runnable ->
      RenderEvent(runnable, i == 0)
    }

    // simulate chained DESTROY events being added via scheduleTask on destroyRenderer call
    every { mapboxRenderer.destroyRenderer() } answers {
      events.forEach(mapboxRenderThread::queueRenderEvent)
    }

    provideValidSurface()
    mapboxRenderThread.destroy()

    verifyNo {
      runnables.forEach { it.run() }
    }
  }

  @Test
  fun onAddRenderEventWhenSurfaceIsDestroyed() {
    initRenderThread()
    provideValidSurface()

    lateinit var runnable: Runnable
    lateinit var runnable2: Runnable
    waitZeroCounter(startCounter = 2) {
      runnable = mockCountdownRunnable(this)
      runnable2 = mockCountdownRunnable(this)
      mapboxRenderThread.onSurfaceDestroyed()

      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable, false))
      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable2, false))

      mapboxRenderThread.onSurfaceCreated(surface, 1, 1)

      // non-render events are posted with 50 millis delay when surface is destroyed
      idleHandler(RETRY_DELAY_MS)
    }

    verify { runnable.run() }
    verify { runnable2.run() }
  }

  @Test
  fun contextLostTest() {
    initRenderThread()
    provideValidSurface()
    pauseHandler()
    every { eglCore.swapBuffers(any()) } returns EGL14.EGL_CONTEXT_LOST
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    every { eglCore.swapBuffers(any()) } returns EGL14.EGL_SUCCESS
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    verifyOrder {
      // swap buffers for surface creation
      eglCore.swapBuffers(any())
      // swap buffers for EGL_CONTEXT_LOST
      eglCore.swapBuffers(any())
      // EGL and native renderer are recreated
      mapboxRenderer.destroyRenderer()
      eglCore.releaseSurface(any())
      eglCore.release()
      eglCore.prepareEgl()
      mapboxRenderer.createRenderer()
      // swap buffers for EGL_SUCCESS
      eglCore.swapBuffers(any())
    }
    // however Android surface is not released
    verifyNo {
      surface.release()
    }
  }

  @Test
  fun anotherAndroidSurfaceProvidedTest() {
    initRenderThread()
    provideValidSurface()
    val oldSurface = surface
    // simulate Android providing new surface
    surface = mockk(relaxUnitFun = true)
    provideValidSurface()
    // EGL and native renderer are recreated
    verifyOrder {
      mapboxRenderer.destroyRenderer()
      eglCore.releaseSurface(any())
      eglCore.release()
      // old surface must be released to avoid memory leak
      oldSurface.release()
      eglCore.prepareEgl()
      mapboxRenderer.createRenderer()
    }
  }

  @Test(timeout = 10000) // Added timeout to ensure that if test fails, test does not hang forever.
  fun onSurfaceWithActivityDestroyedBeforeSurfaceWithDestroyTaskInQueueTest() {
    initRenderThread()
    provideValidSurface()
    waitZeroCounter {
      val latch = this
      // `destroyRenderer` should not schedule anything but even if it does - we make sure we do
      // not execute it and clean up resources
      every { mapboxRenderer.destroyRenderer() } answers {
        renderHandlerThread.handler?.post {
          Thread.sleep(500)
          assert(false)
        }
      }
      mapboxRenderThread.destroy()
      mapboxRenderThread.onSurfaceDestroyed()
      latch.countDown()
    }
    verifyOnce { eglCore.release() }
    verifyOnce { mapboxRenderer.destroyRenderer() }
    assertFalse(renderHandlerThread.isRunning)
  }

  @Test(timeout = 10000) // Added timeout to ensure that if test fails, test does not hang forever.
  fun newAndroidSurfaceArriveWhenWaitingVsyncTest() {
    initRenderThread()
    val choreographerCallbackDelayMs = 16L
    ShadowChoreographer.setPostFrameCallbackDelay(choreographerCallbackDelayMs.toInt())
    mockSurface()
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    // make sure we prepareRenderFrame but do not yet swap buffers
    idleHandler(choreographerCallbackDelayMs / 2)
    // current surface becomes invalid
    every { surface.isValid } returns false
    // new valid surface arrives from Android
    val validSurface = mockk<Surface>()
    every { validSurface.isValid } returns true
    mapboxRenderThread.onSurfaceCreated(
      validSurface,
      2,
      2
    )
    // and we have time to process this new valid surface before doFrame is called on VSYNC
    idleHandler(choreographerCallbackDelayMs / 4)
    // finally we get to doFrame
    idleHandler(choreographerCallbackDelayMs)
    verifyOrder {
      // initially we have created the native renderer instance as well as EGL
      eglCore.prepareEgl()
      eglCore.createWindowSurface(surface)
      eglCore.makeCurrent(any())
      mapboxRenderer.createRenderer()
      // as new Android surface != current one - we perform releaseAll()
      mapboxRenderer.destroyRenderer()
      eglCore.release()
      // and then recreate native renderer and EGL before scheduled swap
      eglCore.prepareEgl()
      eglCore.createWindowSurface(validSurface)
      eglCore.makeCurrent(any())
      mapboxRenderer.createRenderer()
      // swap works OK with new EGLSurface already
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun eglErrorTest() {
    initRenderThread()
    provideValidSurface()
    pauseHandler()
    every { eglCore.swapBuffers(any()) } returns EGL14.EGL_BAD_ALLOC
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    every { eglCore.swapBuffers(any()) } returns EGL14.EGL_SUCCESS
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    verifyOrder {
      // swap buffers for surface creation
      eglCore.swapBuffers(any())
      // swap buffers for EGL_BAD_ALLOC
      eglCore.swapBuffers(any())
      // releasing EGL surface
      eglCore.releaseSurface(any())
      // because of creatingSurface=true argument
      eglCore.makeNothingCurrent()
      // create new EGL surface
      eglCore.createWindowSurface(any())
      // re-create the EGL context
      eglCore.makeCurrent(any())
      // swap buffers for EGL_SUCCESS
      eglCore.swapBuffers(any())
    }
    // we do not destroy the native renderer
    verifyNo {
      mapboxRenderer.destroyRenderer()
    }
  }
}