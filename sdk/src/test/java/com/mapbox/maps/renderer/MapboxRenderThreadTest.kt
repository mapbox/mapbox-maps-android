package com.mapbox.maps.renderer

import android.view.Surface
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.renderer.egl.EGLCore
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
@LooperMode(LooperMode.Mode.PAUSED)
class MapboxRenderThreadTest {

  private lateinit var mapboxRenderThread: MapboxRenderThread
  private lateinit var mapboxRenderer: MapboxRenderer
  private lateinit var eglCore: EGLCore
  private lateinit var workerThread: WorkerHandlerThread

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
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    verify {
      mapboxRenderer.onSurfaceCreated()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceCreatedNotNativeSupportedTest() {
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns false
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    verify(exactly = 0) {
      mapboxRenderer.onSurfaceCreated()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceSizeChangedIndeedTest() {
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.onSurfaceSizeChanged(2, 2)
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    verify {
      mapboxRenderer.onSurfaceCreated()
      mapboxRenderer.onSurfaceChanged(1, 1)
      mapboxRenderer.onSurfaceChanged(2, 2)
    }
  }

  @Test
  fun onSurfaceSizeChangedSameSizeTest() {
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.onSurfaceSizeChanged(1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    verify {
      mapboxRenderer.onSurfaceCreated()
      mapboxRenderer.onSurfaceChanged(1, 1)
    }
  }

  @Test
  fun onSurfaceDestroyedTest() {
    val surface = mockk<Surface>(relaxUnitFun = true)
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    mapboxRenderThread.onSurfaceDestroyed()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    verify {
      mapboxRenderer.onSurfaceDestroyed()
    }
  }

  @Test
  fun onSurfaceDestroyedWithRenderCallAfterTest() {
    val surface = mockk<Surface>(relaxUnitFun = true)
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    mapboxRenderThread.onSurfaceDestroyed()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    verify(exactly = 1) {
      mapboxRenderer.onSurfaceDestroyed()
    }
    // we should not even start preparing EGL again until new surface will not arrive
    assert(!mapboxRenderThread.eglPrepared)
  }

  @Test
  fun onDrawFrameSeparateRequestRender() {
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
    // one swap buffer for surface creation, two for not squashed render requests
    verify(exactly = 3) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun onDrawFrameSquashedRequestRender() {
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.requestRender()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
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
    // one swap buffer for surface creation, one request render after pause is omitted
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun resumeTestWithRequestRenderAtPause() {
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
    // render requests after pause do not swap buffer, we do it on resume if needed once
    verify(exactly = 4) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun resumeTestWithoutRequestRenderAtPause() {
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
  fun queueEventTest() {
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.queueEvent { }
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    assert(mapboxRenderThread.eventQueue.isEmpty())
    // one swap buffer from surface creation, one for custom event
    verify(exactly = 2) {
      eglCore.swapBuffers(any())
    }
  }

  @Test
  fun requestRenderTest() {
    mapboxRenderThread.requestRender()
    assert(mapboxRenderThread.requestRender.get())
  }

  @Test
  fun fpsListenerTest() {
    val listener = mockk<OnFpsChangedListener>(relaxUnitFun = true)
    val surface = mockk<Surface>()
    every { surface.isValid } returns true
    every { eglCore.eglStatusSuccess } returns true
    every { eglCore.createWindowSurface(any()) } returns mockk(relaxed = true)
    mapboxRenderThread.onSurfaceCreated(surface, 1, 1)
    mapboxRenderThread.fpsChangedListener = listener
    Shadows.shadowOf(workerThread.handler?.looper).pause()
    mapboxRenderThread.requestRender()
    mapboxRenderThread.requestRender()
    Shadows.shadowOf(workerThread.handler?.looper).idle()
    verify(exactly = 2) {
      listener.onFpsChanged(any())
    }
  }
}