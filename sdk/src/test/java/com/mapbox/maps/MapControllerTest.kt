package com.mapbox.maps

import android.content.Context
import android.graphics.Bitmap
import android.view.MotionEvent
import com.mapbox.common.EventsService
import com.mapbox.common.TelemetryService
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapPluginRegistry
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.renderer.MapboxRenderThread
import com.mapbox.maps.renderer.MapboxRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.widget.BitmapWidget
import com.mapbox.maps.renderer.widget.Widget
import com.mapbox.maps.shadows.ShadowEventsService
import com.mapbox.maps.shadows.ShadowTelemetryService
import com.mapbox.verifyOnce
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowEventsService::class, ShadowTelemetryService::class])
class MapControllerTest {
  private val mockRenderer: MapboxRenderer = mockk()
  private val mockNativeObserver: NativeObserver = mockk()
  private val mockNativeMap: NativeMapImpl = mockk()
  private val mockMapboxMap: MapboxMap = mockk()
  private val mockPluginRegistry: MapPluginRegistry = mockk()
  private val mockMapInitOptions: MapInitOptions = mockk()
  private val mockCameraState: CameraState = mockk()
  private val mockMotionEvent: MotionEvent = mockk()
  private val mockContext: Context = mockk()
  private val mockMapView: MapView = mockk()
  private val mockOnStyleDataLoadedListener: StyleDataLoadedCallback = mockk()
  private val mockEventsService = mockk<EventsService>()
  private val mockTelemetryService = mockk<TelemetryService>()

  private lateinit var testMapController: MapController

  @Before
  fun setUp() {
    every { mockContext.packageName } returns "com.mapbox.maps"

    testMapController = MapController(
      mockRenderer,
      mockNativeObserver,
      mockMapInitOptions,
      ContextMode.SHARED,
      mockNativeMap,
      mockMapboxMap,
      mockPluginRegistry,
      mockOnStyleDataLoadedListener
    )

    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    val mockResourceOptions = mockk<ResourceOptions>()
    every { mockMapInitOptions.resourceOptions } returns mockResourceOptions
    every { mockResourceOptions.accessToken } returns "access.token"

    mockkStatic(EventsService::class)
    every { EventsService.getOrCreate(any()) } returns mockEventsService
    every { mockEventsService.flush(any()) } just runs

    mockkStatic(TelemetryService::class)
    every { TelemetryService.getOrCreate(any()) } returns mockTelemetryService
    every { mockTelemetryService.flush(any()) } just runs
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun onStart() {
    every { mockPluginRegistry.onStart() } just Runs
    every { mockMapboxMap.loadStyleUri(Style.MAPBOX_STREETS) } just Runs
    every { mockNativeObserver.addOnCameraChangeListener(any()) } just Runs
    every { mockNativeObserver.addOnStyleDataLoadedListener(any()) } just Runs
    every { mockRenderer.onStart() } just Runs
    every { mockMapboxMap.isStyleLoadInitiated } returns false
    every { mockMapInitOptions.styleUri } answers { Style.MAPBOX_STREETS }
    every { mockMapboxMap.getStyle() } returns null

    testMapController.onStart()

    verifySequence {
      mockMapboxMap.getStyle()
      mockNativeObserver.addOnCameraChangeListener(any())
      mockNativeObserver.addOnStyleDataLoadedListener(any())
      mockRenderer.onStart()
      mockMapboxMap.isStyleLoadInitiated
      mockMapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
      mockPluginRegistry.onStart()
    }
  }

  @Test
  fun onStartWithStyleLoaded() {
    every { mockMapboxMap.isStyleLoadInitiated } returns true
    every { mockPluginRegistry.onStart() } just Runs
    every { mockRenderer.onStart() } just Runs
    every { mockNativeObserver.addOnCameraChangeListener(any()) } just Runs
    every { mockNativeObserver.addOnStyleDataLoadedListener(any()) } just Runs
    every { mockMapboxMap.getStyle() } returns mockk()
    every { mockPluginRegistry.onStyleChanged(any()) } just Runs

    testMapController.onStart()

    verify(exactly = 0) { mockMapboxMap.loadStyleUri(Style.MAPBOX_STREETS) }
  }

  @Test
  fun onStop() {
    every { mockPluginRegistry.onStop() } just Runs
    every { mockRenderer.onStop() } just Runs
    every { mockNativeObserver.removeOnCameraChangeListener(any()) } just Runs
    every { mockNativeObserver.removeOnStyleDataLoadedListener(any()) } just Runs

    testMapController.lifecycleState = MapController.LifecycleState.STATE_STARTED
    testMapController.onStop()

    verifySequence {
      mockNativeObserver.removeOnCameraChangeListener(any())
      mockNativeObserver.removeOnStyleDataLoadedListener(any())
      mockRenderer.onStop()
      mockPluginRegistry.onStop()
      mockEventsService.flush(any())
      mockTelemetryService.flush(any())
    }
  }

  @Test
  fun onStartDeliversUpdatedStyle() {
    every { mockPluginRegistry.onStyleChanged(any()) } just Runs
    every { mockPluginRegistry.onStop() } just Runs
    every { mockPluginRegistry.onStart() } just Runs
    every { mockRenderer.onStop() } just Runs
    every { mockRenderer.onStart() } just Runs
    every { mockNativeObserver.addOnCameraChangeListener(any()) } just Runs
    every { mockNativeObserver.addOnStyleDataLoadedListener(any()) } just Runs
    every { mockNativeObserver.removeOnCameraChangeListener(any()) } just Runs
    every { mockNativeObserver.removeOnStyleDataLoadedListener(any()) } just Runs
    every { mockMapboxMap.isStyleLoadInitiated } returns false
    every { mockMapboxMap.loadStyleUri(any()) } just Runs
    every { mockMapInitOptions.styleUri } returns "uri"

    val style1 = mockk<Style>()
    every { mockMapboxMap.getStyle() } returns style1
    testMapController.onStart()

    testMapController.onStop()
    val style2 = mockk<Style>()
    every { mockMapboxMap.getStyle() } returns style2
    testMapController.onStart()

    verifySequence {
      mockPluginRegistry.onStyleChanged(style1)
      mockPluginRegistry.onStart()
      mockPluginRegistry.onStop()
      mockPluginRegistry.onStyleChanged(style2)
      mockPluginRegistry.onStart()
    }
  }

  @Test
  fun onReduceMemoryUse() {
    every { mockMapboxMap.reduceMemoryUse() } just Runs

    testMapController.onLowMemory()

    verify { mockMapboxMap.reduceMemoryUse() }
  }

  @Test
  fun onDestroy() {
    every { mockMapboxMap.onDestroy() } just Runs
    every { mockPluginRegistry.onDestroy() } just Runs
    every { mockNativeObserver.onDestroy() } just Runs
    every { mockRenderer.onDestroy() } just Runs

    testMapController.onDestroy()

    verifySequence {
      mockPluginRegistry.onDestroy()
      mockNativeObserver.onDestroy()
      mockRenderer.onDestroy()
      mockMapboxMap.onDestroy()
    }
  }

  @Test
  fun onTouch() {
    every { mockPluginRegistry.onTouch(mockMotionEvent) } returns true

    testMapController.onTouchEvent(mockMotionEvent)

    verify { mockPluginRegistry.onTouch(mockMotionEvent) }
  }

  @Test
  fun onGenericMotionEvent() {
    val expectedValue = true
    every { mockPluginRegistry.onGenericMotionEvent(mockMotionEvent) } returns expectedValue

    val actualValue = testMapController.onGenericMotionEvent(mockMotionEvent)

    assertEquals(expectedValue, actualValue)
    verify { mockPluginRegistry.onGenericMotionEvent(mockMotionEvent) }
  }

  @Test
  fun onSizeChanged() {
    every { mockPluginRegistry.onSizeChanged(0, 0) } just Runs

    val slotRunnable = slot<Runnable>()
    every { mockRenderer.queueRenderEvent(capture(slotRunnable)) } answers { slotRunnable.captured.run() }
    every { mockRenderer.onSurfaceChanged(any(), any()) } just Runs

    testMapController.onSizeChanged(0, 0)

    verify { mockRenderer.onSurfaceChanged(0, 0) }
    verify { mockPluginRegistry.onSizeChanged(0, 0) }
  }

  @Test
  fun mapboxMapRequest() {
    assertEquals(mockMapboxMap, testMapController.getMapboxMap())
  }

  @Test
  fun cameraPluginNotified() {
    val onCameraChangeListenerSlot = slot<CameraChangedCallback>()
    every { mockNativeObserver.addOnCameraChangeListener(capture(onCameraChangeListenerSlot)) } just Runs
    every { mockNativeObserver.addOnStyleDataLoadedListener(any()) } just Runs
    every { mockPluginRegistry.onStart() } just Runs
    every { mockRenderer.onStart() } just Runs
    every { mockPluginRegistry.onCameraMove(mockCameraState) } just Runs
    every { mockMapboxMap.loadStyleUri(Style.MAPBOX_STREETS) } just Runs
    every { mockNativeMap.getCameraState() } returns mockCameraState
    every { mockMapboxMap.isStyleLoadInitiated } returns false
    every { mockMapInitOptions.styleUri } answers { Style.MAPBOX_STREETS }
    every { mockMapboxMap.getStyle() } returns null

    testMapController.onStart()
    val cameraChangedCallback = onCameraChangeListenerSlot.captured
    cameraChangedCallback.run(mockk())

    verifySequence {
      mockMapboxMap.getStyle()
      mockMapboxMap.isStyleLoadInitiated
      mockMapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
      mockPluginRegistry.onStart()
      mockNativeMap.getCameraState()
      mockPluginRegistry.onCameraMove(mockCameraState)
    }
  }

  @Test
  fun createPlugin() {
    val mockPlugin = mockk<Plugin.Custom>()
    every { mockPluginRegistry.createPlugin(mockMapView, mockMapInitOptions, mockPlugin) } just Runs

    testMapController.createPlugin(mockMapView, mockPlugin)

    verify { mockPluginRegistry.createPlugin(mockMapView, mockMapInitOptions, mockPlugin) }
  }

  @Test
  fun getPlugin() {
    val plugin = mockk<MapPlugin>()
    every { mockPluginRegistry.getPlugin<MapPlugin>("id") } returns plugin

    assertEquals(plugin, testMapController.getPlugin("id"))
    verify { mockPluginRegistry.getPlugin<MapPlugin>("id") }
  }

  @Test
  fun queueEvent() {
    val event = mockk<Runnable>()
    every { mockRenderer.queueNonRenderEvent(event) } just Runs
    every { mockRenderer.queueRenderEvent(event) } just Runs

    testMapController.queueEvent(event, false)
    testMapController.queueEvent(event, true)

    verifySequence {
      mockRenderer.queueNonRenderEvent(event)
      mockRenderer.queueRenderEvent(event)
    }
  }

  @Test
  fun snapshotSync() {
    every { mockRenderer.snapshot() } answers { Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) }

    testMapController.snapshot()

    verify { mockRenderer.snapshot() }
  }

  @Test
  fun snapshotAsync() {
    val listener = mockk<MapView.OnSnapshotReady>()
    every { mockRenderer.snapshot(listener) } just Runs

    testMapController.snapshot(listener)

    verify { mockRenderer.snapshot(listener) }
  }

  @Test
  fun setMaximumFpsValid() {
    every { mockRenderer.setMaximumFps(60) } just Runs

    testMapController.setMaximumFps(60)

    verify { mockRenderer.setMaximumFps(60) }
  }

  @Test
  fun setMaximumFpsInvalid() {
    every { mockRenderer.setMaximumFps(any()) } just Runs

    testMapController.setMaximumFps(-1)

    // range check is performed within MapboxRenderer
    verify { mockRenderer.setMaximumFps(any()) }
  }

  @Test
  fun setOnFpsChangedListener() {
    val listener = mockk<OnFpsChangedListener>()
    every { mockRenderer.setOnFpsChangedListener(listener) } just Runs

    testMapController.setOnFpsChangedListener(listener)

    verifyAll { mockRenderer.setOnFpsChangedListener(listener) }
  }

  @Test
  fun initializePluginsEmpty() {
    every { mockMapInitOptions.plugins } answers { emptyList() }

    testMapController.initializePlugins(mockMapInitOptions)
    verify(exactly = 0) {
      mockPluginRegistry.createPlugin(any(), mockMapInitOptions, any())
      mockMapboxMap.setCameraAnimationPlugin(any())
      mockMapboxMap.setGesturesAnimationPlugin(any())
    }
  }

  @Test
  fun initializePluginsCamera() {
    every { mockMapboxMap.setCameraAnimationPlugin(any()) } just Runs
    every { mockMapInitOptions.plugins } answers { listOf(Plugin.Mapbox(Plugin.MAPBOX_CAMERA_PLUGIN_ID)) }
    val createPluginSlot = slot<Plugin>()
    every {
      mockPluginRegistry.createPlugin(
        mockMapView,
        mockMapInitOptions,
        capture(createPluginSlot)
      )
    } just Runs

    testMapController.initializePlugins(mockMapInitOptions, mockMapView)

    val createdPlugin = createPluginSlot.captured
    assertEquals(Plugin.MAPBOX_CAMERA_PLUGIN_ID, createdPlugin.id)
    verifySequence {
      mockMapInitOptions.plugins
      mockPluginRegistry.createPlugin(mockMapView, mockMapInitOptions, createdPlugin)
      mockMapboxMap.setCameraAnimationPlugin(createdPlugin.instance as CameraAnimationsPlugin)
    }
  }

  @Test
  fun deliversStyleOnStartIfChanged() {
    every { mockMapboxMap.setCameraAnimationPlugin(any()) } just Runs
    every { mockMapInitOptions.plugins } answers { listOf(Plugin.Mapbox(Plugin.MAPBOX_CAMERA_PLUGIN_ID)) }
    val createPluginSlot = slot<Plugin>()
    every {
      mockPluginRegistry.createPlugin(
        mockMapView,
        mockMapInitOptions,
        capture(createPluginSlot)
      )
    } just Runs

    testMapController.initializePlugins(mockMapInitOptions, mockMapView)

    val createdPlugin = createPluginSlot.captured
    assertEquals(Plugin.MAPBOX_CAMERA_PLUGIN_ID, createdPlugin.id)
    verifySequence {
      mockMapInitOptions.plugins
      mockPluginRegistry.createPlugin(mockMapView, mockMapInitOptions, createdPlugin)
      mockMapboxMap.setCameraAnimationPlugin(createdPlugin.instance as CameraAnimationsPlugin)
    }
  }

  @Test
  fun setValidScreenRefreshRate() {
    screenRefreshRateTest(1, 60)
  }

  @Test
  fun setZeroScreenRefreshRate() {
    screenRefreshRateTest(0, 0)
  }

  @Test
  fun setNegativeScreenRefreshRate() {
    screenRefreshRateTest(0, -1)
  }

  @Test
  fun setMaxScreenRefreshRate() {
    screenRefreshRateTest(1, Int.MAX_VALUE)
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun addWidgetTest() {
    val mockWidget = mockk<BitmapWidget>()
    every { mockWidget.setTriggerRepaintAction(any()) } just runs
    val mockRenderThread = mockk<MapboxRenderThread>(relaxed = true)
    every { mockRenderer.renderThread } returns mockRenderThread
    every { mockRenderer.scheduleRepaint() } just runs
    testMapController.addWidget(mockWidget)
    verifyOnce { mockRenderThread.addWidget(mockWidget) }
    verifyOnce { mockWidget.setTriggerRepaintAction(any()) }
    verifyOnce { mockRenderer.scheduleRepaint() }
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun removeWidgetTest() {
    val widget = createTestWidget()
    val mockRenderThread = mockk<MapboxRenderThread>(relaxed = true)
    every { mockRenderer.renderThread } returns mockRenderThread
    every { mockRenderer.scheduleRepaint() } just runs
    every { mockRenderThread.removeWidget(widget) } returns true
    assertTrue(testMapController.removeWidget(widget))
    verifyOnce { mockRenderThread.removeWidget(widget) }
    verifyOnce { mockRenderer.scheduleRepaint() }
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun removeWidgetWithoutAddTest() {
    val widget = createTestWidget()
    val mockRenderThread = mockk<MapboxRenderThread>(relaxed = true)
    every { mockRenderer.renderThread } returns mockRenderThread
    assertFalse(testMapController.removeWidget(widget))
  }

  @OptIn(MapboxExperimental::class)
  @Test(expected = RuntimeException::class)
  fun addWidgetTestUniqueContext() {
    val initOptions = mockk<MapInitOptions>(relaxed = true)
    every { initOptions.mapOptions } returns MapOptions.Builder().contextMode(ContextMode.UNIQUE).build()
    testMapController = MapController(
      mockRenderer,
      mockNativeObserver,
      mockMapInitOptions,
      ContextMode.UNIQUE,
      mockNativeMap,
      mockMapboxMap,
      mockPluginRegistry,
      mockOnStyleDataLoadedListener
    )
    testMapController.addWidget(mockk())
  }

  @Test(expected = RuntimeException::class)
  fun addWidgetTestNullContext() {
    val initOptions = mockk<MapInitOptions>(relaxed = true)
    every { initOptions.mapOptions } returns MapOptions.Builder().build()
    testMapController = MapController(
      mockRenderer,
      mockNativeObserver,
      mockMapInitOptions,
      null,
      mockNativeMap,
      mockMapboxMap,
      mockPluginRegistry,
      mockOnStyleDataLoadedListener
    )
    testMapController.addWidget(mockk())
  }

  @OptIn(MapboxExperimental::class)
  private fun createTestWidget(): Widget {
    val bitmap = mockk<Bitmap>()
    every { bitmap.width } returns 0
    every { bitmap.height } returns 0
    return BitmapWidget(bitmap)
  }

  private fun screenRefreshRateTest(expectedCallCount: Int, actualRefreshRate: Int) {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    val mockRenderThread = mockk<MapboxRenderThread>(relaxed = true)
    every { mockRenderer.renderThread } returns mockRenderThread
    testMapController.setScreenRefreshRate(actualRefreshRate)
    verify(exactly = expectedCallCount) {
      mockRenderThread.setScreenRefreshRate(any())
    }
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }
}