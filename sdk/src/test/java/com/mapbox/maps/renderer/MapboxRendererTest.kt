package com.mapbox.maps.renderer

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.MapInterface
import com.mapbox.maps.MapView
import com.mapbox.maps.Size
import com.mapbox.maps.Task
import com.mapbox.maps.renderer.gl.PixelReader
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
@LooperMode(LooperMode.Mode.PAUSED)
internal abstract class MapboxRendererTest {

  protected lateinit var renderThread: MapboxRenderThread
  protected lateinit var mapboxRenderer: MapboxRenderer

  @Before
  open fun setUp() {
    renderThread = mockk(relaxUnitFun = true)
  }

  @Test
  fun setMapTest() {
    val map = mockk<MapInterface>(relaxUnitFun = true)
    mapboxRenderer.setMap(map)
    assert(mapboxRenderer.map == map)
  }

  @Test
  fun scheduleRepaintTest() {
    mapboxRenderer.scheduleRepaint()
    verify {
      renderThread.queueRenderEvent(
        RenderEvent(
          null,
          true,
          EventType.SDK
        )
      )
    }
  }

  @Test
  fun scheduleTaskTest() {
    val task = mockk<Task>(relaxUnitFun = true)
    every { renderThread.renderDestroyCallChain } returns false
    val event = slot<RenderEvent>()
    mapboxRenderer.scheduleTask(task)
    verify {
      renderThread.queueRenderEvent(capture(event))
    }
    assert(!event.captured.needRender)
    assert(event.captured.eventType == EventType.SDK)
  }

  @Test
  fun scheduleDestroyTaskTest() {
    val task = mockk<Task>(relaxUnitFun = true)
    every { renderThread.renderDestroyCallChain } returns true
    val event = slot<RenderEvent>()
    mapboxRenderer.scheduleTask(task)
    verify {
      renderThread.queueRenderEvent(capture(event))
    }
    assert(!event.captured.needRender)
    assert(event.captured.eventType == EventType.DESTROY_RENDERER)
  }

  @Test
  fun onStartTest() {
    mapboxRenderer.onStart()
    verify { renderThread.resume() }
  }

  @Test
  fun onStopTest() {
    mapboxRenderer.onStop()
    verify { renderThread.pause() }
  }

  @Test
  fun setMaximumFpsTest() {
    mapboxRenderer.setMaximumFps(10)
    verify { renderThread.setMaximumFps(10) }
  }

  @Test
  fun queueEventTest() {
    val event = mockk<Runnable>(relaxUnitFun = true)
    mapboxRenderer.queueEvent(event)
    verify {
      renderThread.queueRenderEvent(
        RenderEvent(
          event,
          false,
          EventType.OTHER
        )
      )
    }
  }

  @Test
  fun queueRenderEventTest() {
    val event = mockk<Runnable>(relaxUnitFun = true)
    mapboxRenderer.queueRenderEvent(event)
    verify {
      renderThread.queueRenderEvent(
        RenderEvent(
          event,
          true,
          EventType.OTHER
        )
      )
    }
  }

  @Test
  fun onSurfaceCreatedTest() {
    val map = mockk<MapInterface>(relaxUnitFun = true)
    mapboxRenderer.map = map
    mapboxRenderer.createRenderer()
    verify { map.createRenderer() }
  }

  @Test
  fun onSurfaceChangedTest() {
    val map = mockk<MapInterface>(relaxUnitFun = true)
    mapboxRenderer.map = map
    mapboxRenderer.onSurfaceChanged(1, 1)
    verify { map.size = Size(1f, 1f) }
  }

  @Test
  fun onSurfaceDestroyedTest() {
    val map = mockk<MapInterface>(relaxUnitFun = true)
    mapboxRenderer.map = map
    mapboxRenderer.destroyRenderer()
    verify { map.destroyRenderer() }
  }

  @Test
  fun onDrawFrameTest() {
    val map = mockk<MapInterface>(relaxUnitFun = true)
    mapboxRenderer.map = map
    mapboxRenderer.render()
    verify { map.render() }
  }

  @Test
  fun onSnapshotSuccessSync() {
    val pixelReader = mockk<PixelReader>(relaxed = true)
    every { pixelReader.width } returns 1
    every { pixelReader.height } returns 1
    every { pixelReader.readPixels() } returns ByteBuffer.allocateDirect(1 * 1 * 4)
    var handler: Handler
    val handlerThread = HandlerThread("thread").apply {
      start()
      handler = Handler(this.looper)
    }
    val event = slot<RenderEvent>()
    every { renderThread.queueRenderEvent(capture(event)) } answers {
      handler.post(event.captured.runnable!!)
    }
    mapboxRenderer.pixelReader = pixelReader
    mapboxRenderer.readyForSnapshot = AtomicBoolean(true)
    mapboxRenderer.onSurfaceChanged(1, 1)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    mapboxRenderer.snapshot()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { pixelReader.readPixels() }
    handlerThread.quit()
  }

  @Test
  fun onSnapshotFailureSync() {
    val pixelReader = mockk<PixelReader>(relaxed = true)
    every { pixelReader.width } returns 1
    every { pixelReader.height } returns 1
    every { pixelReader.readPixels() } returns ByteBuffer.allocateDirect(1 * 1 * 4)
    var handler: Handler
    val handlerThread = HandlerThread("thread").apply {
      start()
      handler = Handler(this.looper)
    }
    val event = slot<RenderEvent>()
    every { renderThread.queueRenderEvent(capture(event)) } answers {
      handler.post(event.captured.runnable!!)
    }
    mapboxRenderer.pixelReader = pixelReader
    mapboxRenderer.readyForSnapshot = AtomicBoolean(false)
    mapboxRenderer.onSurfaceChanged(1, 1)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    mapboxRenderer.snapshot()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { pixelReader.readPixels() }
    handlerThread.quit()
  }

  @Test
  fun onSnapshotSuccessAsync() {
    val pixelReader = mockk<PixelReader>(relaxed = true)
    every { pixelReader.width } returns 1
    every { pixelReader.height } returns 1
    every { pixelReader.readPixels() } returns ByteBuffer.allocateDirect(1 * 1 * 4)
    var handler: Handler
    val handlerThread = HandlerThread("thread").apply {
      start()
      handler = Handler(this.looper)
    }
    val event = slot<RenderEvent>()
    every { renderThread.queueRenderEvent(capture(event)) } answers {
      handler.post(event.captured.runnable!!)
    }
    mapboxRenderer.pixelReader = pixelReader
    mapboxRenderer.readyForSnapshot = AtomicBoolean(true)
    mapboxRenderer.onSurfaceChanged(1, 1)
    val callback = MapView.OnSnapshotReady {
      verify { pixelReader.readPixels() }
      handlerThread.quit()
    }
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(handlerThread.looper).pause()
    mapboxRenderer.snapshot(callback)
    Shadows.shadowOf(handlerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
  }

  @Test
  fun onSnapshotFailureAsync() {
    val pixelReader = mockk<PixelReader>(relaxed = true)
    every { pixelReader.width } returns 1
    every { pixelReader.height } returns 1
    every { pixelReader.readPixels() } returns ByteBuffer.allocateDirect(1 * 1 * 4)
    var handler: Handler
    val handlerThread = HandlerThread("thread").apply {
      start()
      handler = Handler(this.looper)
    }
    val event = slot<RenderEvent>()
    every { renderThread.queueRenderEvent(capture(event)) } answers {
      handler.post(event.captured.runnable!!)
    }
    mapboxRenderer.pixelReader = pixelReader
    mapboxRenderer.readyForSnapshot = AtomicBoolean(false)
    mapboxRenderer.onSurfaceChanged(1, 1)
    val callback = MapView.OnSnapshotReady {
      Assert.assertNull(it)
      handlerThread.quit()
    }
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    mapboxRenderer.snapshot(callback)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
  }

  @Test
  fun onDestroyTest() {
    val listener = OnFpsChangedListener { }
    every { renderThread.fpsChangedListener } returns listener
    mapboxRenderer.setOnFpsChangedListener(listener)
    mapboxRenderer.onDestroy()
    verify { renderThread.destroy() }
    verify { renderThread.fpsChangedListener = null }
  }
}