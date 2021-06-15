package com.mapbox.maps.renderer

import android.view.Surface
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.renderer.egl.EGLCore
import io.mockk.*
import org.junit.After
import org.junit.Assert
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

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
@LooperMode(LooperMode.Mode.PAUSED)
class MapboxRenderThreadTest {

  private lateinit var mapboxRenderThread: MapboxRenderThread
  private lateinit var mapboxRenderer: MapboxRenderer
  private lateinit var eglCore: EGLCore
  private lateinit var workerThread: WorkerHandlerThread
  private val waitTime = 100L

  @Before
  fun setUp() {
    mapboxRenderer = mockk(relaxUnitFun = true)
    eglCore = mockk(relaxed = true)
    workerThread = WorkerHandlerThread()
    mapboxRenderThread = MapboxRenderThread(
      mapboxRenderer,
      workerThread,
      eglCore
    ).apply {
      workerThread.start()
    }
  }

  @After
  fun cleanup() {
    unmockkAll()
    workerThread.stop()
  }

  @Test
  fun onSurfaceCreatedTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.onSurfaceCreated() } answers { latch.countDown() }
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify {
      mapboxRenderer.onSurfaceCreated()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceCreatedNotNativeSupportedTest() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns false
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    verify(exactly = 0) {
      mapboxRenderer.onSurfaceCreated()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceSizeChangedIndeedTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.onSurfaceCreated() } answers { latch.countDown() }
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.onSurfaceSizeChanged(2, 2)
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify {
      mapboxRenderer.onSurfaceCreated()
      mapboxRenderer.onSurfaceChanged(1, 1)
      mapboxRenderer.onSurfaceChanged(2, 2)
    }
  }

  @Test
  fun onSurfaceSizeChangedSameSizeTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.onSurfaceCreated() } answers { latch.countDown() }
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.onSurfaceSizeChanged(1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify {
      mapboxRenderer.onSurfaceCreated()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceOnlyDestroyedTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.onSurfaceDestroyed() } answers { latch.countDown() }
    every { mapboxRenderer.needDestroy } returns false
    val surface = mockk<Surface>(relaxUnitFun = true)
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    mapboxRenderThread.onSurfaceDestroyed()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify { mapboxRenderer.onSurfaceDestroyed() }
    assert(workerThread.handlerThread.isAlive)
  }

  @Test
  fun onSurfaceWithActivityDestroyedTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.onSurfaceDestroyed() } answers { latch.countDown() }
    every { mapboxRenderer.needDestroy } returns true
    val surface = mockk<Surface>(relaxUnitFun = true)
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    mapboxRenderThread.onSurfaceDestroyed()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify { mapboxRenderer.onSurfaceDestroyed() }
    assert(!workerThread.handlerThread.isAlive)
  }

  @Test
  fun onSurfaceDestroyedWithRenderCallAfterTest() {
    val latch = CountDownLatch(1)
    every { mapboxRenderer.onSurfaceDestroyed() } answers { latch.countDown() }
    every { mapboxRenderer.needDestroy } returns false
    val surface = mockk<Surface>(relaxUnitFun = true)
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    mapboxRenderThread.onSurfaceDestroyed()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify(exactly = 1) {
      mapboxRenderer.onSurfaceDestroyed()
    }
    // we should not even start preparing EGL again until new surface will not arrive
    assert(!mapboxRenderThread.eglPrepared)
  }

  @Test
  fun onDrawFrameSeparateRequestRender() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // one swap buffer for surface creation, two for not squashed render requests
    verify(exactly = 3) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun onDrawFrameSquashedRequestRender() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.requestRender()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // one swap buffer for surface creation, 2 render requests squash in one swap buffers call
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun setMaximumFpsTest() {
    mapboxRenderThread.setMaximumFps(30)
    assert(mapboxRenderThread.renderTimeNs.get() == 33333333L)
  }

  @Test
  fun pauseTest() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.pause()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // one swap buffer for surface creation, one request render after pause is omitted
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun resumeTestWithRequestRenderAtPause() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.pause()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.resume()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // render requests after pause do not swap buffer, we do it on resume if needed once
    verify(exactly = 4) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun resumeTestWithoutRequestRenderAtPause() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.pause()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.resume()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    // no actual swap buffer after resume as no calls were done after pause
    verify(exactly = 3) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun destroyTest() {
    mapboxRenderThread.destroy()
    assert(!workerThread.handlerThread.isAlive)
  }

  @Test
  fun queueRenderEventTest() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    every { runnable.run() } answers { latch.countDown() }
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.queueRenderEvent(runnable)
    Shadows.shadowOf(workerThread.handler?.looper).idle()
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
  fun queueEventTestWithAwaitVsync() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    mapboxRenderThread.awaitingNextVsync.set(true)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.queueEvent(runnable)
    Assert.assertEquals(1, mapboxRenderThread.eventQueue.size)
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    latch.await(waitTime, TimeUnit.MILLISECONDS)
    assert(mapboxRenderThread.eventQueue.isEmpty())
    verify { runnable.run() }
    // one swap buffer from surface creation, one for custom event
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun queueEventTestWithoutAwaitVsync() {
    val latch = CountDownLatch(1)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    val runnable = mockk<Runnable>(relaxUnitFun = true)
    every { runnable.run() } answers { latch.countDown() }
    mapboxRenderThread.awaitingNextVsync.set(false)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.queueEvent(runnable)
    Assert.assertEquals(0, mapboxRenderThread.eventQueue.size)
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assert(mapboxRenderThread.eventQueue.isEmpty())
    verify { runnable.run() }
    // one swap buffer from surface creation and no from custom event
    verify(exactly = 1) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun fpsListenerTest() {
    val latch = CountDownLatch(2)
    val listener = mockk<OnFpsChangedListener>(relaxUnitFun = true)
    val surface = mockk<Surface>()
    every { listener.onFpsChanged(any()) } answers { latch.countDown() }
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    mapboxRenderThread.fpsChangedListener = listener
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.requestRender()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    if (!latch.await(waitTime, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    verify(exactly = 2) {
      listener.onFpsChanged(any())
    }
  }
}