package com.mapbox.maps.renderer

import android.view.Surface
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.renderer.MapboxRenderThread.Companion.RETRY_DELAY_MS
import com.mapbox.maps.renderer.egl.EGLCore
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.microedition.khronos.egl.EGL10

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
@LooperMode(LooperMode.Mode.PAUSED)
class MapboxRenderThreadTest {

  private lateinit var mapboxRenderThread: MapboxRenderThread
  private lateinit var mapboxRenderer: MapboxRenderer
  private lateinit var eglCore: EGLCore
  private lateinit var renderHandlerThread: RenderHandlerThread
  private val waitTime = 200L

  private fun mockValidSurface(): Surface {
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { surface.release() } just Runs
    every { eglCore.prepareEgl() } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    every { eglCore.makeNothingCurrent() } returns true
    every { eglCore.makeCurrent(any()) } returns true
    every { eglCore.swapBuffers(any()) } returns EGL10.EGL_SUCCESS
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    return surface
  }

  private fun mockCountdownRunnable(latch: CountDownLatch) = mockk<Runnable>(relaxUnitFun = true).also {
    every { it.run() } answers { latch.countDown() }
  }

  @Before
  fun setUp() {
    mapboxRenderer = mockk(relaxUnitFun = true)
    eglCore = mockk(relaxUnitFun = true)
    every { eglCore.eglNoSurface } returns mockk()
    renderHandlerThread = RenderHandlerThread()
    mapboxRenderThread = MapboxRenderThread(
      mapboxRenderer,
      renderHandlerThread,
      eglCore
    ).apply {
      renderHandlerThread.start()
    }
  }

  @After
  fun cleanup() {
    clearAllMocks()
    renderHandlerThread.stop()
  }

  @Test
  fun onSurfaceCreatedTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.createRenderer() } answers { latch.countDown() }
    mockValidSurface()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify {
      mapboxRenderer.createRenderer()
      eglCore.makeNothingCurrent()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceCreatedNotNativeSupportedTest() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
//    every { eglCore.eglStatusSuccess } returns false
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    verify(exactly = 0) {
      mapboxRenderer.createRenderer()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceSizeChangedIndeedTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.createRenderer() } answers { latch.countDown() }
    mockValidSurface()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.onSurfaceSizeChanged(2, 2)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify {
      mapboxRenderer.createRenderer()
      mapboxRenderer.onSurfaceChanged(1, 1)
      mapboxRenderer.onSurfaceChanged(2, 2)
    }
  }

  @Test
  fun onSurfaceSizeChangedSameSizeTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.createRenderer() } answers { latch.countDown() }
    mockValidSurface()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.onSurfaceSizeChanged(1, 1)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify {
      mapboxRenderer.createRenderer()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceWithActivityDestroyedAfterSurfaceTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.destroyRenderer() } answers { latch.countDown() }
    mockValidSurface()
    mapboxRenderThread.onSurfaceDestroyed()
    mapboxRenderThread.destroy()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify(exactly = 1) { eglCore.release() }
    verify { mapboxRenderer.destroyRenderer() }
    assert(!renderHandlerThread.started)
  }

  @Test
  fun onSurfaceWithActivityDestroyedBeforeSurfaceTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.destroyRenderer() } answers { latch.countDown() }
    mockValidSurface()
    mapboxRenderThread.destroy()
    mapboxRenderThread.onSurfaceDestroyed()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify(exactly = 1) { eglCore.release() }
    verify(exactly = 1) { mapboxRenderer.destroyRenderer() }
    assert(!renderHandlerThread.started)
  }

  @Test
  fun onDrawFrameSeparateRequestRender() {
    val latch = CountDownLatch(1)
    mockValidSurface()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // one swap buffer for surface creation, two for not squashed render requests
    verify(exactly = 3) {
      eglCore.swapBuffers(any())
    }
    // make EGL context current only once when creating surface
    verify(exactly = 1) {
      eglCore.makeNothingCurrent()
    }
  }

  @Test
  fun onDrawFrameSquashedRequestRender() {
    val latch = CountDownLatch(1)
    mockValidSurface()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // one swap buffer for surface creation, 2 render requests squash in one swap buffers call
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun setMaximumFpsTest() {
    mapboxRenderThread.setMaximumFps(30)
    assert(mapboxRenderThread.renderTimeNs == 33333333L)
  }

  @Test
  fun pauseTest() {
    val latch = CountDownLatch(1)
    mockValidSurface()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.pause()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // one swap buffer for surface creation, one request render after pause is omitted
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun resumeTestWithRequestRenderAtPause() {
    val latch = CountDownLatch(1)
    mockValidSurface()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.pause()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.resume()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // render requests after pause do not swap buffer, we do it on resume if needed once
    verify(exactly = 4) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun resumeTestWithoutRequestRenderAtPause() {
    val latch = CountDownLatch(1)
    mockValidSurface()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.pause()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.resume()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // we always do extra render call on resume
    verify(exactly = 4) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun destroyTest() {
    mapboxRenderThread.destroy()
    assert(!renderHandlerThread.started)
  }

  @Test
  fun queueRenderEventTest() {
    val latch = CountDownLatch(1)
    mockValidSurface()
    val runnable = mockCountdownRunnable(latch)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        true,
        EventType.OTHER
      )
    )
    assertEquals(1, mapboxRenderThread.renderEventQueue.size)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assert(mapboxRenderThread.renderEventQueue.isEmpty())
    verify { runnable.run() }
    // one swap buffer from surface creation, one for custom event
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun queueSdkNonRenderEventTestNoVsync() {
    mockValidSurface()
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    mapboxRenderThread.awaitingNextVsync = false
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        false,
        EventType.SDK
      )
    )
    // we do not add non-render event to the queue
    assert(mapboxRenderThread.nonRenderEventQueue.isEmpty())
    // do not schedule any render requests explicitly
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    verify { runnable.run() }
    // one swap buffer from surface creation only
    verify(exactly = 1) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun queueSdkNonRenderEventTestWithVsync() {
    mockValidSurface()
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    mapboxRenderThread.awaitingNextVsync = true
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        false,
        EventType.SDK
      )
    )
    // we add to the queue
    assert(mapboxRenderThread.nonRenderEventQueue.size == 1)
    // do not schedule any render requests explicitly
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    // without explicit render event runnable should not be executed
    verify(exactly = 0) { runnable.run() }
    mapboxRenderThread.awaitingNextVsync = false
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    // schedule render request
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    assert(mapboxRenderThread.nonRenderEventQueue.isEmpty())
    // one swap buffer from surface creation + one for render request
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
    verify(exactly = 1) { runnable.run() }
  }

  @Test
  fun queueUserNonRenderEventLoosingSurfaceTest() {
    val surface = mockValidSurface()
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        false,
        EventType.OTHER
      )
    )
    // simulate render thread is not fully prepared, e.g. EGL context is lost
    mapboxRenderThread.eglContextCreated = false
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    verify(exactly = 0) { runnable.run() }
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    // simulate render thread is fully prepared again
    mapboxRenderThread.eglContextCreated = true
    mapboxRenderThread.processAndroidSurface(surface, 1, 1)
    // taking into account we try to reschedule event with some delay
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idleFor(RETRY_DELAY_MS, TimeUnit.MILLISECONDS)
    // user's runnable is executed when thread is fully prepared again
    verify(exactly = 1) { runnable.run() }
  }

  @Test
  fun queueSdkNonRenderEventLoosingSurfaceTest() {
    val surface = mockValidSurface()
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(
      RenderEvent(
        runnable,
        false,
        EventType.SDK
      )
    )
    // simulate render thread is not fully prepared, e.g. EGL context is lost
    mapboxRenderThread.eglContextCreated = false
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    verify(exactly = 0) { runnable.run() }
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    // simulate render thread is fully prepared again
    mapboxRenderThread.eglContextCreated = true
    mapboxRenderThread.processAndroidSurface(surface, 1, 1)
    // taking into account we try to reschedule event with some delay
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idleFor(RETRY_DELAY_MS, TimeUnit.MILLISECONDS)
    // SDK's task is not executed with new surface
    verify(exactly = 0) { runnable.run() }
  }

  @Test
  fun fpsListenerTest() {
    val latch = CountDownLatch(2)
    val listener = mockk<OnFpsChangedListener>(relaxUnitFun = true)
    every { listener.onFpsChanged(any()) } answers { latch.countDown() }
    mapboxRenderThread.fpsChangedListener = listener
    mockValidSurface()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify(exactly = 2) {
      listener.onFpsChanged(any())
    }
  }

  @Test
  fun surfaceCreatedCalledBeforeActivityStartTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.createRenderer() } answers { latch.countDown() }
    mapboxRenderThread.paused = true
    mockValidSurface()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify(exactly = 1) { mapboxRenderer.createRenderer() }
    // EGL should be fully prepared
    verify(exactly = 0) { eglCore.releaseSurface(any()) }
  }

  @Test
  fun snapshotsAreTakenAfterDrawAndBeforeSwapBuffers() {
    val latch = CountDownLatch(3)

    mockValidSurface()

    val runnable = mockCountdownRunnable(latch)
    val runnable2 = mockCountdownRunnable(latch)
    val runnable3 = mockCountdownRunnable(latch)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()

    mapboxRenderThread.queueRenderEvent(RenderEvent(runnable, true, EventType.SDK))
    mapboxRenderThread.queueRenderEvent(RenderEvent(runnable2, true, EventType.SDK))
    mapboxRenderThread.queueRenderEvent(RenderEvent(runnable3, true, EventType.SDK))

    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
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
    mapboxRenderer = mockk<MapboxSurfaceRenderer>(relaxUnitFun = true)
    mapboxRenderThread = MapboxRenderThread(
      mapboxRenderer,
      renderHandlerThread,
      eglCore
    ).apply {
      renderHandlerThread.start()
    }
    mockValidSurface()
    mapboxRenderThread.onSurfaceDestroyed()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    // we do not destroy native renderer if it's stop and not destroy
    verify(exactly = 0) { mapboxRenderer.destroyRenderer() }
    // we clear only EGLSurface but not all EGL
    verify(exactly = 1) { eglCore.releaseSurface(any()) }
    verify(exactly = 0) { eglCore.release() }
  }

  @Test
  fun onSurfaceDestroyedWithRenderCallAfterTestTextureView() {
    mapboxRenderer = mockk<MapboxTextureViewRenderer>(relaxUnitFun = true)
    mapboxRenderThread = MapboxRenderThread(
      mapboxRenderer,
      renderHandlerThread,
      eglCore
    ).apply {
      renderHandlerThread.start()
    }
    val latch = CountDownLatch(1)
    every { mapboxRenderer.destroyRenderer() } answers { latch.countDown() }
    mockValidSurface()
    mapboxRenderThread.onSurfaceDestroyed()
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    // we do destroy native renderer if it's stop (for texture renderer)
    verify(exactly = 1) { mapboxRenderer.destroyRenderer() }
    // we clear all EGL
    verify(exactly = 1) { eglCore.releaseSurface(any()) }
    verify(exactly = 1) { eglCore.release() }
  }

  @Test
  fun renderWithMaxFpsSet() {
    mockValidSurface()
    mapboxRenderThread.setMaximumFps(15)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(MapboxRenderer.renderEventSdk)
    Shadows.shadowOf(renderHandlerThread.handler?.looper).idle()
    // 1 swap when creating surface + 1 for request render call
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }
}