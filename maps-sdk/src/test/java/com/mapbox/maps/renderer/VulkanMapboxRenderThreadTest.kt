package com.mapbox.maps.renderer

import android.util.Log
import android.view.Surface
import com.mapbox.maps.IVulkanManager
import com.mapbox.maps.NativeMapImpl
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.shadows.ShadowLogThrottler
import com.mapbox.verifyNo
import com.mapbox.verifyOnce
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.unmockkStatic
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowLog
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

@RunWith(RobolectricTestRunner::class)
@Config(
  shadows = [
    ShadowLogThrottler::class,
    ShadowSurfaceWrapper::class
  ]
)
@LooperMode(LooperMode.Mode.PAUSED)
class VulkanMapboxRenderThreadTest {

  private lateinit var mapboxRenderer: MapboxRenderer
  private lateinit var vulkanManager: IVulkanManager
  private lateinit var nativeMap: NativeMapImpl
  private lateinit var renderThread: VulkanMapboxRenderThread
  private lateinit var renderHandlerThread: RenderHandlerThread
  private lateinit var fpsManager: FpsManager
  private lateinit var surface: Surface

  private fun initRenderThread() {
    mapboxRenderer = mockk(relaxUnitFun = true)
    vulkanManager = mockk(relaxUnitFun = true)
    nativeMap = mockk(relaxUnitFun = true)

    renderHandlerThread = RenderHandlerThread(mapName = "")
    renderHandlerThread.start()

    fpsManager = mockk(relaxUnitFun = true)
    every { fpsManager.preRender(any()) } returns true

    val surfaceProcessingLock = ReentrantLock()

    renderThread = VulkanMapboxRenderThread(
      mapboxRenderer = mapboxRenderer,
      antialiasingSampleCount = 1,
      handlerThread = renderHandlerThread,
      fpsManager = fpsManager,
      surfaceProcessingLock = surfaceProcessingLock,
      createCondition = surfaceProcessingLock.newCondition(),
      destroyCondition = surfaceProcessingLock.newCondition(),
    )

    mockkStatic("com.mapbox.maps.MapboxLogger")
    ShadowLog.stream = System.out
    every { logI(any(), any()) } answers { Log.i(firstArg(), secondArg()) }
    every { logI(any(), any(), any()) } answers { Log.i(firstArg(), secondArg()) }
    every { logW(any(), any()) } answers { Log.w(firstArg<String>(), secondArg<String>()) }
  }

  private fun setupVulkanManagerAvailable() {
    every { mapboxRenderer.map } returns nativeMap
    every { nativeMap.getVulkanManager() } returns vulkanManager
  }

  private fun setupVulkanManagerUnavailable() {
    every { mapboxRenderer.map } returns nativeMap
    every { nativeMap.getVulkanManager() } returns null
  }

  private fun mockSurface() {
    surface = mockk(relaxUnitFun = true)
    every { surface.isValid } returns true
    every { vulkanManager.init(any()) } returns true
  }

  private fun idleHandler(
    millis: Long = 0L
  ) = Shadows.shadowOf(renderHandlerThread.handler?.looper).idleFor(millis, TimeUnit.MILLISECONDS)

  private fun provideValidSurface() {
    mockSurface()
    renderThread.onSurfaceCreated(surface, 100, 100)
    idleHandler()
  }

  @After
  fun cleanup() {
    if (::renderHandlerThread.isInitialized) {
      renderHandlerThread.stop()
    }
    unmockkStatic("com.mapbox.maps.MapboxLogger")
    unmockkAll()
  }

  @Test
  fun resizeForwardsToVulkanManagerWhenAvailable() {
    initRenderThread()
    setupVulkanManagerAvailable()
    assertTrue(renderThread.prepareRendererForTest())

    renderThread.resize(800, 600)

    verifyOnce { vulkanManager.resize(800, 600) }
  }

  @Test
  fun resizeCachesDimensionsWhenVulkanManagerNull() {
    initRenderThread()
    setupVulkanManagerUnavailable()
    assertFalse(renderThread.prepareRendererForTest())

    renderThread.resize(800, 600)

    verifyNo { vulkanManager.resize(any(), any()) }
  }

  @Test
  fun cachedResizeAppliedWhenVulkanManagerBecomesAvailable() {
    initRenderThread()
    setupVulkanManagerUnavailable()
    renderThread.prepareRendererForTest()
    renderThread.resize(800, 600)

    setupVulkanManagerAvailable()
    renderThread.prepareRendererForTest()

    verifyOnce { vulkanManager.resize(800, 600) }
  }

  @Test
  fun lastResizeWinsWhenMultipleCached() {
    initRenderThread()
    setupVulkanManagerUnavailable()
    renderThread.prepareRendererForTest()

    renderThread.resize(800, 600)
    renderThread.resize(1024, 768)
    renderThread.resize(1920, 1080)

    setupVulkanManagerAvailable()
    renderThread.prepareRendererForTest()

    verifyOnce { vulkanManager.resize(1920, 1080) }
    verifyNo { vulkanManager.resize(800, 600) }
    verifyNo { vulkanManager.resize(1024, 768) }
  }

  @Test
  fun cachedResizeClearedAfterApplying() {
    initRenderThread()
    setupVulkanManagerUnavailable()
    renderThread.prepareRendererForTest()
    renderThread.resize(800, 600)

    setupVulkanManagerAvailable()
    renderThread.prepareRendererForTest()
    verifyOnce { vulkanManager.resize(800, 600) }

    renderThread.prepareRendererForTest()
    verify(exactly = 1) { vulkanManager.resize(800, 600) }
  }

  @Test
  fun noCachedResizeWhenNeverResizedBeforeVulkanManager() {
    initRenderThread()
    setupVulkanManagerAvailable()
    renderThread.prepareRendererForTest()

    verifyNo { vulkanManager.resize(any(), any()) }
  }

  @Test
  fun resizeAfterVulkanManagerAvailableGoesDirectly() {
    initRenderThread()
    setupVulkanManagerAvailable()
    renderThread.prepareRendererForTest()

    renderThread.resize(800, 600)
    renderThread.resize(1024, 768)

    verifyOrder {
      vulkanManager.resize(800, 600)
      vulkanManager.resize(1024, 768)
    }
  }

  @Test
  fun surfaceArrivesBeforeSetMap_renderingDeferred() {
    initRenderThread()
    every { mapboxRenderer.map } returns null
    provideValidSurface()
    verifyNo { mapboxRenderer.createRenderer() }
    assertTrue(renderThread.surface?.isValid == true)
  }

  @Test
  fun setMapTriggersRenderSetup_afterSurfaceCached() {
    initRenderThread()
    every { mapboxRenderer.map } returns null
    provideValidSurface()
    verifyNo { mapboxRenderer.createRenderer() }

    setupVulkanManagerAvailable()
    renderThread.onMapSet()
    idleHandler()

    verifyOnce { mapboxRenderer.createRenderer() }
    verifyOnce { mapboxRenderer.onSurfaceChanged(100, 100) }
  }

  @Test
  fun setMapBeforeSurface_normalFlow() {
    initRenderThread()
    setupVulkanManagerAvailable()
    provideValidSurface()

    verifyOnce { mapboxRenderer.createRenderer() }
    verifyOnce { mapboxRenderer.onSurfaceChanged(100, 100) }
  }

  @Test
  fun vulkanNotSupported_rendererNotCreated() {
    initRenderThread()
    setupVulkanManagerUnavailable()
    provideValidSurface()

    verifyNo { mapboxRenderer.createRenderer() }
  }

  @Test
  fun onMapSetNoOp_whenSurfaceNotAvailable() {
    initRenderThread()
    every { mapboxRenderer.map } returns null

    setupVulkanManagerAvailable()
    renderThread.onMapSet()
    idleHandler()

    verifyNo { mapboxRenderer.createRenderer() }
  }

  @Test
  fun onMapSetNoOp_whenRenderThreadAlreadyPrepared() {
    initRenderThread()
    setupVulkanManagerAvailable()
    provideValidSurface()
    verifyOnce { mapboxRenderer.createRenderer() }

    renderThread.onMapSet()
    idleHandler()

    verifyOnce { mapboxRenderer.createRenderer() }
  }

  @Test
  fun surfaceInvalidatedBeforeSetMap_onMapSetNoOp() {
    initRenderThread()
    every { mapboxRenderer.map } returns null
    provideValidSurface()

    every { surface.isValid } returns false

    setupVulkanManagerAvailable()
    renderThread.onMapSet()
    idleHandler()

    verifyNo { mapboxRenderer.createRenderer() }
  }
}