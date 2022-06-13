package com.mapbox.maps.renderer

import android.view.Surface
import com.mapbox.countDownEvery
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.MapboxRenderThread.Companion.RETRY_DELAY_MS
import com.mapbox.maps.renderer.egl.EGLCore
import com.mapbox.maps.renderer.gl.TextureRenderer
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
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLContext

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

  private fun initRenderThread(mapboxRenderer: MapboxRenderer = mockk(relaxUnitFun = true)) {
    this.mapboxRenderer = mapboxRenderer
    mockEglCore()
    mockWidgetRenderer()
    renderHandlerThread = RenderHandlerThread()
    textureRenderer = mockk(relaxed = true)
    mapboxRenderThread = MapboxRenderThread(
      mapboxRenderer,
      mapboxWidgetRenderer,
      renderHandlerThread,
      eglCore,
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
    every { eglCore.swapBuffers(any()) } returns EGL10.EGL_SUCCESS
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

  private fun mockCountdownRunnable(latch: CountDownLatch) = mockk<Runnable>(relaxUnitFun = true).also {
    every { it.run() } answers { latch.countDown() }
  }

  @After
  fun cleanup() {
    renderHandlerThread.stop()
    unmockkStatic("com.mapbox.maps.MapboxLogger")
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
    assertFalse(renderHandlerThread.started)
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
    assertFalse(renderHandlerThread.started)
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
  fun setMaximumFpsTest() {
    initRenderThread()
    mapboxRenderThread.setMaximumFps(30)
    assert(mapboxRenderThread.renderTimeNs == 33333333L)
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
    assertFalse(renderHandlerThread.started)
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
        EventType.DEFAULT
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
        EventType.DEFAULT
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
        EventType.DEFAULT
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
        EventType.DEFAULT
      )
    )
    // simulate render thread is not fully prepared, e.g. EGL context is lost
    mapboxRenderThread.eglContextCreated = false
    idleHandler()
    verifyNo { runnable.run() }
    pauseHandler()
    // simulate render thread is fully prepared again
    mapboxRenderThread.eglContextCreated = true
    mapboxRenderThread.processAndroidSurface(surface, 1, 1)
    // taking into account we try to reschedule event with some delay
    idleHandler(RETRY_DELAY_MS)
    // user's runnable is executed when thread is fully prepared again
    verifyOnce { runnable.run() }
  }

  @Test
  fun fpsListenerTest() {
    initRenderThread()
    val listener = mockk<OnFpsChangedListener>(relaxUnitFun = true)
    mapboxRenderThread.fpsChangedListener = listener
    provideValidSurface()
    pauseHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.repaintRenderEvent)
    idleHandler()
    verify(exactly = 2) {
      listener.onFpsChanged(any())
    }
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

      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable, true, EventType.DEFAULT))
      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable2, true, EventType.DEFAULT))
      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable3, true, EventType.DEFAULT))

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
    }
  }

  @Test
  fun renderWithMaxFpsSet() {
    initRenderThread()
    provideValidSurface()
    mapboxRenderThread.setMaximumFps(15)
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
  fun onDestroyExecutesAllDestroyTasks() {
    initRenderThread()
    every { eglCore.makeCurrent(any()) } returns true
    val runnables = listOf<Runnable>(
      mockk(relaxUnitFun = true),
      mockk(relaxUnitFun = true),
      mockk(relaxUnitFun = true),
    )
    val events = runnables.map { RenderEvent(it, false, EventType.DESTROY_RENDERER) }

    // simulate chained DESTROY events being added via scheduleTask on destroyRenderer call
    every { mapboxRenderer.destroyRenderer() } answers {
      events.forEach(mapboxRenderThread::queueRenderEvent)
    }

    provideValidSurface()
    mapboxRenderThread.destroy()

    verifyOrder {
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

      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable, false, EventType.DEFAULT))
      mapboxRenderThread.queueRenderEvent(RenderEvent(runnable2, false, EventType.DEFAULT))

      mapboxRenderThread.onSurfaceCreated(surface, 1, 1)

      // non-render events are posted with 50 millis delay when surface is destroyed
      idleHandler(RETRY_DELAY_MS)
    }

    verify { runnable.run() }
    verify { runnable2.run() }
  }
}