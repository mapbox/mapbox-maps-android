package com.mapbox.maps

import android.content.Context
import android.graphics.Bitmap
import android.view.MotionEvent
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapPluginRegistry
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.renderer.MapboxRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class MapControllerTest {

  private val renderer: MapboxRenderer = mockk(relaxUnitFun = true)

  private val nativeObserver: NativeObserver = mockk(relaxUnitFun = true)

  private val nativeMap: MapInterface = mockk(relaxUnitFun = true)

  private lateinit var mapView: MapView

  private val mapboxMap: MapboxMap = mockk(relaxUnitFun = true)

  private val pluginRegistry: MapPluginRegistry = mockk(relaxed = true)

  private val mapInitOptions: MapInitOptions = mockk(relaxed = true)

  private val cameraState: CameraState = mockk(relaxed = true)

  private val motionEvent: MotionEvent = mockk(relaxed = true)

  private lateinit var mapController: MapController

  private val context: Context = mockk(relaxUnitFun = true)

  @Before
  fun setUp() {
    mockkStatic(MapboxMapStaticInitializer::class)
    every { MapboxMapStaticInitializer.loadMapboxMapNativeLib() } just Runs
    every { context.getString(-1) } returns "token"
    every {
      context.resources.getIdentifier(
        "mapbox_access_token",
        "string",
        "com.mapbox.maps"
      )
    } returns -1
    every { context.packageName } returns "com.mapbox.maps"
    every { context.filesDir } returns File("foobar")
    ResourceOptionsManager.getDefault(context).update { accessToken("foobar") }
    mapView = mockk(relaxUnitFun = true)
    val token = "pk.123"
    val resourceOptions = mockk<ResourceOptions>()
    mockkObject(MapProvider)
    every { mapInitOptions.resourceOptions } answers { resourceOptions }
    every { mapInitOptions.styleUri } answers { Style.MAPBOX_STREETS }
    every { resourceOptions.accessToken } answers { token }
    every {
      MapProvider.getNativeMap(
        mapInitOptions,
        renderer
      )
    } answers { nativeMap }
    every { nativeMap.cameraState } returns cameraState

    every { MapProvider.getMapboxMap(nativeMap, nativeObserver, 1.0f) } answers { mapboxMap }
    every { MapProvider.getMapPluginRegistry(any(), any(), any()) } returns pluginRegistry
    every { mapboxMap.isStyleLoadInitiated } returns false
    every { renderer.renderThread.handlerThread.handler } returns mockk()
    mapController =
      MapController(
        renderer,
        nativeObserver,
        mapInitOptions,
        nativeMap,
        mapboxMap,
        pluginRegistry,
        mockk()
      )
  }

  @Test
  fun onStart() {
    mapController.onStart()
    verify { renderer.onStart() }
    verify { pluginRegistry.onStart() }
    verify { mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) }
  }

  @Test
  fun onStartWithStyleLoaded() {
    every { mapboxMap.isStyleLoadInitiated } returns true
    mapController.onStart()
    verify(exactly = 0) { mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) }
  }

  @Test
  fun onStop() {
    mapController.onStart()
    mapController.onStop()
    verify { renderer.onStop() }
    verify { pluginRegistry.onStop() }
  }

  @Test
  fun onReduceMemoryUse() {
    mapController.onLowMemory()
    verify { mapboxMap.reduceMemoryUse() }
  }

  @Test
  fun onDestroy() {
    mapController.onDestroy()
    verify { nativeObserver.onDestroy() }
  }

  @Test
  fun onTouch() {
    mapController.onTouchEvent(motionEvent)
    verify { pluginRegistry.onTouch(motionEvent) }
  }

  @Test
  fun onGenericMotionEvent() {
    mapController.onGenericMotionEvent(motionEvent)
    verify { pluginRegistry.onGenericMotionEvent(motionEvent) }
  }

  @Test
  fun onSizeChanged() {
    mapController.onSizeChanged(0, 0)
    verify { pluginRegistry.onSizeChanged(0, 0) }
  }

  @Test
  fun mapboxMapRequest() {
    Assert.assertEquals(mapboxMap, mapController.getMapboxMap())
  }

  @Test
  fun cameraPluginNotified() {
    val onCameraChangeListenerSlot = slot<OnCameraChangeListener>()
    every { nativeObserver.addOnCameraChangeListener(capture(onCameraChangeListenerSlot)) } answers {}
    mapController.onStart()
    val onCameraChangeListener = onCameraChangeListenerSlot.captured

    onCameraChangeListener.onCameraChanged()
    verify { nativeMap.cameraState }
    verify { pluginRegistry.onCameraMove(cameraState) }
  }

  @Test
  fun createPlugin() {
    val plugin = Plugin.Custom("id", mockk())
    mapController.createPlugin(mapView, plugin)
    verify { pluginRegistry.createPlugin(mapView, mapInitOptions, plugin) }
  }

  @Test
  fun getPlugin() {
    val plugin = mockk<MapPlugin>()
    every { pluginRegistry.getPlugin<MapPlugin>("id") } returns plugin
    Assert.assertEquals(plugin, mapController.getPlugin("id"))
    verify { pluginRegistry.getPlugin<MapPlugin>("id") }
  }

  @Test
  fun queueEvent() {
    val event = mockk<Runnable>()
    mapController.queueEvent(event, false)
    verify { renderer.queueEvent(event) }
    mapController.queueEvent(event, true)
    verify { renderer.queueRenderEvent(event) }
  }

  @Test
  fun snapshotSync() {
    every { renderer.snapshot() } answers { Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) }
    mapController.snapshot()
    verify { renderer.snapshot() }
  }

  @Test
  fun snapshotAsync() {
    val listener = mockk<MapView.OnSnapshotReady>()
    mapController.snapshot(listener)
    verify { renderer.snapshot(listener) }
  }

  @Test
  fun setMaximumFpsValid() {
    mapController.setMaximumFps(60)
    verify { renderer.setMaximumFps(60) }
  }

  @Test
  fun setMaximumFpsInvalid() {
    mapController.setMaximumFps(-1)
    verify(exactly = 0) { renderer.setMaximumFps(any()) }
  }

  @Test
  fun setOnFpsChangedListener() {
    val listener = mockk<OnFpsChangedListener>()
    mapController.setOnFpsChangedListener(listener)
    verify { renderer.setOnFpsChangedListener(listener) }
  }

  @Test
  fun initializePluginsEmpty() {
    val mapController =
      MapController(
        renderer,
        nativeObserver,
        mapInitOptions,
        nativeMap,
        mapboxMap,
        MapPluginRegistry(mockk()),
        mockk()
      )
    val mapInitOptions = MapInitOptions(mockk(relaxed = true))
    mapInitOptions.plugins = listOf()
    mapController.initializePlugins(mapInitOptions)
  }

  @Test
  fun initializePluginsCamera() {
    val mapController =
      MapController(
        renderer,
        nativeObserver,
        mapInitOptions,
        nativeMap,
        mapboxMap,
        MapPluginRegistry(mockk(relaxed = true)),
        mockk()
      )
    val mapInitOptions = MapInitOptions(mockk(relaxed = true))
    mapInitOptions.plugins = listOf(Plugin.Mapbox(Plugin.MAPBOX_CAMERA_PLUGIN_ID))
    mapController.initializePlugins(mapInitOptions)
    verify { mapboxMap.setCameraAnimationPlugin(any()) }
  }
}