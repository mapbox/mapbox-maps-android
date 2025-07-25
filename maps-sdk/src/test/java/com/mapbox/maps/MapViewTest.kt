package com.mapbox.maps

import android.view.MotionEvent
import com.mapbox.maps.debugoptions.DebugOptionsController
import com.mapbox.maps.debugoptions.MapViewDebugOptions
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.RendererSetupErrorListener
import com.mapbox.verifyNo
import io.mockk.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class MapViewTest {

  private lateinit var mapController: MapController
  private lateinit var mapboxMap: MapboxMap
  private lateinit var mapView: MapView

  private val testScheduler = TestCoroutineScheduler()
  private val testDispatcher = UnconfinedTestDispatcher(testScheduler)
  private val mainTestDispatcher = UnconfinedTestDispatcher(testScheduler, "MainTestDispatcher")
  private val testScope = CoroutineScope(testDispatcher)

  @Before
  fun setUp() {
    Dispatchers.setMain(mainTestDispatcher)
    mockkConstructor(DebugOptionsController::class)
    every { anyConstructed<DebugOptionsController>().options = any() } just Runs
    mapController = mockk(relaxUnitFun = true)
    mapboxMap = mockk(relaxUnitFun = true)
    every { mapController.mapboxMap } returns mapboxMap
    // Use test scope for lifecycleScope
    every { mapController.lifecycleScope } returns testScope

    mapView = MapView(
      mockk(relaxed = true),
      mockk(relaxed = true),
      mapController,
    )
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    every { logW(any(), any()) } just Runs
    every { logE(any(), any()) } just Runs
  }

  @After
  fun cleanUp() {
    Dispatchers.resetMain()
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun start() {
    mapView.onStart()

    verify { mapController.onStart() }
    verify { mapController.setScreenRefreshRate(MapView.DEFAULT_FPS) }
  }

  @Test
  fun stop() {
    mapView.onStop()
    verify { mapController.onStop() }
  }

  @Test
  fun resume() {
    mapView.onResume()
    verify { mapController.onResume() }
  }

  @Test
  fun onDestroy() {
    mapView.onDestroy()
    verify { mapController.onDestroy() }
  }

  @Test
  fun onLowMemory() {
    mapView.onLowMemory()
    verify { mapController.onLowMemory() }
  }

  @Test
  fun getMapboxMap() {
    mapView.mapboxMap
    verify { mapController.mapboxMap }
  }

  @Test
  fun getDebugOptions() {
    mapView.debugOptions
    verify { anyConstructed<DebugOptionsController>().options }
  }

  @Test
  fun setDebugOption() {
    val debugOptions: Set<MapViewDebugOptions> = mockk()

    mapView.debugOptions = debugOptions

    verify { anyConstructed<DebugOptionsController>().options = debugOptions }
  }

  @Test
  fun debugOptionsControllerNotInitializedByLifecycle() {
    mapView.onStart()
    mapView.onStop()

    verifyNo { anyConstructed<DebugOptionsController>().started = any() }
    verifyNo { anyConstructed<DebugOptionsController>().started = any() }
  }

  @Test
  fun debugOptionsControllerInitializedByOptions() {
    mapView.debugOptions = setOf()

    mapView.onStart()
    mapView.onStop()

    verify { anyConstructed<DebugOptionsController>().started = true }
    verify { anyConstructed<DebugOptionsController>().started = false }
  }

  @Test
  fun onSizeChangedCalled() {
    mapView.onSizeChanged(10, 15, 5, 5)
    verify { mapController.onSizeChanged(10, 15) }
  }

  @Test
  fun onSizeChangedNotCalled() {
    mapView.onSizeChanged(1, 1, 1, 1)
    verify(exactly = 0) { mapController.onSizeChanged(any(), any()) }
  }

  @Test
  fun onGenericMotionEventController() {
    val event = mockk<MotionEvent>()
    every { mapController.onGenericMotionEvent(any()) } returns true
    assertTrue(mapView.onGenericMotionEvent(event))
    verify { mapController.onGenericMotionEvent(event) }
  }

  @Test
  fun onGenericMotionEventSuper() {
    val event = mockk<MotionEvent>()
    every { mapController.onGenericMotionEvent(any()) } returns false
    assertFalse(mapView.onGenericMotionEvent(event))
    verify { mapController.onGenericMotionEvent(event) }
  }

  @Test
  fun onTouchEventController() {
    val event = mockk<MotionEvent>()
    every { mapController.onTouchEvent(any()) } returns true
    assertTrue(mapView.onTouchEvent(event))
    verify { mapController.onTouchEvent(event) }
  }

  @Test
  fun onTouchEventSuper() {
    val event = mockk<MotionEvent>()
    every { mapController.onTouchEvent(any()) } returns false
    assertFalse(mapView.onTouchEvent(event))
    verify { mapController.onTouchEvent(event) }
  }

  @Test
  fun setMaximumFps() {
    mapView.setMaximumFps(60)
    verify { mapController.setMaximumFps(60) }
  }

  @Test
  fun queueEvent() {
    val runnable = mockk<Runnable>()
    mapView.queueEvent(runnable)
    verify { mapController.queueEvent(runnable) }
  }

  @Test
  fun snapshotSync() {
    every { mapController.snapshot() } returns mockk()
    mapView.snapshot()
    verify { mapController.snapshot() }
  }

  @Test
  fun snapshotCallback() {
    val callback = mockk<MapView.OnSnapshotReady>()
    mapView.snapshot(callback)
    verify { mapController.snapshot(callback) }
  }

  @Test
  fun createPlugin() {
    every { mapController.createPlugin(any(), any()) } returns mockk()
    val plugin = Plugin.Custom("id", mockk())
    mapView.createPlugin(plugin)
    verify { mapController.createPlugin(mapView, plugin) }
  }

  @Test
  fun getPlugin() {
    every { mapController.getPlugin<MapPlugin>(any()) } returns mockk()
    mapView.getPlugin<MapPlugin>("id")
    verify { mapController.getPlugin<MapPlugin>("id") }
  }

  @Test
  fun setOnFpsChangedListener() {
    val listener = mockk<OnFpsChangedListener>()
    mapView.setOnFpsChangedListener(listener)
    verify { mapController.setOnFpsChangedListener(listener) }
  }

  @Test
  fun addRendererSetupErrorListener() {
    val listener = mockk<RendererSetupErrorListener>()
    mapView.addRendererSetupErrorListener(listener)
    verify { mapController.addRendererSetupErrorListener(listener) }
  }

  @Test
  fun removeRendererSetupErrorListener() {
    val listener = mockk<RendererSetupErrorListener>()
    mapView.removeRendererSetupErrorListener(listener)
    verify { mapController.removeRendererSetupErrorListener(listener) }
  }

  @OptIn(MapboxExperimental::class, MapboxDelicateApi::class)
  @Test
  fun scheduleThreadServiceTypeReset() {
    val renderThread = mockk<com.mapbox.maps.renderer.MapboxRenderThread>(relaxUnitFun = true)
    val renderer = mockk<com.mapbox.maps.renderer.MapboxRenderer>(relaxed = true)
    every { mapController.renderer } returns renderer
    every { renderer.renderThread } returns renderThread
    mapView.scheduleThreadServiceTypeReset()
    verify { renderThread.scheduleThreadServiceTypeReset() }
  }
}