package com.mapbox.maps

import android.content.Context
import android.graphics.Bitmap
import android.view.MotionEvent
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapPluginRegistry
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleDataLoadedListener
import com.mapbox.maps.renderer.MapboxRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class MapControllerTest {

  private val mockRenderer: MapboxRenderer = mockk()
  private val mockNativeObserver: NativeObserver = mockk()
  private val mockNativeMap: MapInterface = mockk()
  private val mockMapboxMap: MapboxMap = mockk()
  private val mockPluginRegistry: MapPluginRegistry = mockk()
  private val mockMapInitOptions: MapInitOptions = mockk()
  private val mockCameraState: CameraState = mockk()
  private val mockMotionEvent: MotionEvent = mockk()
  private val mockContext: Context = mockk()
  private val mockMapView: MapView = mockk()
  private val mockOnStyleDataLoadedListener: OnStyleDataLoadedListener = mockk()

  private lateinit var testMapController: MapController

  @Before
  fun setUp() {
    mockkStatic(MapboxMapStaticInitializer::class)
    every { MapboxMapStaticInitializer.loadMapboxMapNativeLib() } just Runs
    every { mockContext.packageName } returns "com.mapbox.maps"

    testMapController = MapController(
      mockRenderer,
      mockNativeObserver,
      mockMapInitOptions,
      mockNativeMap,
      mockMapboxMap,
      mockPluginRegistry,
      mockOnStyleDataLoadedListener
    )
  }

  @After
  fun shutDown() {
    unmockkAll()
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

    testMapController.onStart()

    verifySequence {
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
    every { mockPluginRegistry.cleanup() } just Runs
    every { mockNativeObserver.onDestroy() } just Runs
    every { mockRenderer.onDestroy() } just Runs

    testMapController.onDestroy()

    verifySequence {
      mockMapboxMap.onDestroy()
      mockNativeObserver.onDestroy()
      mockRenderer.onDestroy()
      mockPluginRegistry.cleanup()
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

    testMapController.onSizeChanged(0, 0)

    verify { mockPluginRegistry.onSizeChanged(0, 0) }
  }

  @Test
  fun mapboxMapRequest() {
    assertEquals(mockMapboxMap, testMapController.getMapboxMap())
  }

  @Test
  fun cameraPluginNotified() {
    val onCameraChangeListenerSlot = slot<OnCameraChangeListener>()
    every { mockNativeObserver.addOnCameraChangeListener(capture(onCameraChangeListenerSlot)) } just Runs
    every { mockNativeObserver.addOnStyleDataLoadedListener(any()) } just Runs
    every { mockPluginRegistry.onStart() } just Runs
    every { mockRenderer.onStart() } just Runs
    every { mockPluginRegistry.onCameraMove(mockCameraState) } just Runs
    every { mockMapboxMap.loadStyleUri(Style.MAPBOX_STREETS) } just Runs
    every { mockNativeMap.cameraState } returns mockCameraState
    every { mockMapboxMap.isStyleLoadInitiated } returns false
    every { mockMapInitOptions.styleUri } answers { Style.MAPBOX_STREETS }

    testMapController.onStart()
    val onCameraChangeListener = onCameraChangeListenerSlot.captured
    onCameraChangeListener.onCameraChanged(mockk())

    verifySequence {
      mockPluginRegistry.onStart()
      mockNativeMap.cameraState
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
    every { mockRenderer.queueEvent(event) } just Runs
    every { mockRenderer.queueRenderEvent(event) } just Runs

    testMapController.queueEvent(event, false)
    testMapController.queueEvent(event, true)

    verifySequence {
      mockRenderer.queueEvent(event)
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
    testMapController.setMaximumFps(-1)

    verify(exactly = 0) { mockRenderer.setMaximumFps(any()) }
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
}