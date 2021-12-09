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
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
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
    renderThread = mockk(relaxed = true)
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
    verify { renderThread.requestRender() }
  }

  @Test
  fun scheduleTaskTest() {
    val task = mockk<Task>(relaxUnitFun = true)
    mapboxRenderer.scheduleTask(task)
    verify { renderThread.queueEvent(any()) }
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
    verify { renderThread.queueEvent(event) }
  }

  @Test
  fun queueRenderEventTest() {
    val event = mockk<Runnable>(relaxUnitFun = true)
    mapboxRenderer.queueRenderEvent(event)
    verify { renderThread.queueRenderEvent(event) }
  }

  @Test
  fun onSurfaceCreatedTest() {
    val map = mockk<MapInterface>(relaxUnitFun = true)
    mapboxRenderer.map = map
    mapboxRenderer.onSurfaceCreated()
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
    mapboxRenderer.onSurfaceDestroyed()
    verify { map.destroyRenderer() }
  }

  @Test
  fun onDrawFrameTest() {
    val map = mockk<MapInterface>(relaxUnitFun = true)
    mapboxRenderer.map = map
    mapboxRenderer.onDrawFrame()
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
    val runnable = slot<Runnable>()
    every { renderThread.queueSnapshot(capture(runnable)) } answers {
      handler.post(runnable.captured)
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
    val runnable = slot<Runnable>()
    every { renderThread.queueRenderEvent(capture(runnable)) } answers {
      handler.post(runnable.captured)
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
    val runnable = slot<Runnable>()
    every { renderThread.queueRenderEvent(capture(runnable)) } answers {
      handler.post(runnable.captured)
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
    val runnable = slot<Runnable>()
    every { renderThread.queueRenderEvent(capture(runnable)) } answers {
      handler.post(runnable.captured)
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