package com.mapbox.maps

import android.graphics.Bitmap
import android.view.MotionEvent
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.plugin.MapPluginRegistry
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

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class MapControllerTest {

  private val renderer: MapboxRenderer = mockk(relaxUnitFun = true)

  private val mapObserver: NativeObserver = mockk(relaxUnitFun = true)

  private val nativeMap: CustomMapInterface = mockk(relaxUnitFun = true)

  private lateinit var mapView: MapView

  private val mapboxMap: MapboxMap = mockk(relaxUnitFun = true)

  private val pluginRegistry: MapPluginRegistry = mockk(relaxed = true)

  private val mapboxMapOptions: MapboxMapOptions = mockk(relaxed = true)

  private val cameraOptions: CameraOptions = mockk(relaxed = true)

  private val motionEvent: MotionEvent = mockk(relaxed = true)

  private val pixelRatio = 1f

  private val cachePath = "foo/bar"

  private val resourcesPath = "foo/bar/biz"

  private lateinit var mapController: MapController

  @Before
  fun setUp() {
    mockkStatic(MapboxMapStaticInitializer::class)
    every { MapboxMapStaticInitializer.loadMapboxMapNativeLib() } just Runs
    mapView = mockk(relaxUnitFun = true)
    val token = "pk.123"
    val resourceOptions = mockk<ResourceOptions>()
    mockkObject(MapProvider)
    every { mapboxMapOptions.resourceOptions } answers { resourceOptions }
    every { resourceOptions.accessToken } answers { token }
    every {
      MapProvider.getNativeMap(
        mapboxMapOptions,
        renderer
      )
    } answers { nativeMap }
    every { nativeMap.getCameraOptions(any()) } returns cameraOptions

    every { MapProvider.getMapboxMap(nativeMap, mapObserver, 1.0f) } answers { mapboxMap }
    every { MapProvider.getMapPluginRegistry(any(), any(), any()) } returns pluginRegistry

    mapController =
      MapController(
        renderer,
        mapObserver,
        mapboxMapOptions,
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
  }

  @Test
  fun onStop() {
    mapController.onStop()
    verify { renderer.onStop() }
    verify { pluginRegistry.onStop() }
  }

  @Test
  fun onReduceMemoryUse() {
    mapController.reduceMemoryUse()
    verify { mapboxMap.reduceMemoryUse() }
  }

  @Test
  fun onDestroy() {
    mapController.onDestroy()
    verify { mapObserver.clearListeners() }
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
    every { mapObserver.addOnCameraChangeListener(capture(onCameraChangeListenerSlot)) } answers {}
    mapController.onStart()
    val onCameraChangeListener = onCameraChangeListenerSlot.captured

    onCameraChangeListener.onCameraChanged()
    verify { nativeMap.getCameraOptions(null) }
    verify { pluginRegistry.onCameraMove(cameraOptions) }
  }

  @Test
  fun createPlugin() {
    val mapView = mockk<MapView>()
    val clazz = mockkClass(Any::class)::class.java
    mapController.createPlugin(mapView, clazz)
    verify { pluginRegistry.createPlugin(mapView, mapboxMapOptions, clazz) }
  }

  @Test
  fun createPlugin_constructor_args() {
    val mapView = mockk<MapView>()
    val clazz = mockkClass(Any::class)::class.java
    val pair1 = Any::class.java to Any()
    val pair2 = Any::class.java to Any()
    mapController.createPlugin(mapView, clazz, pair1, pair2)
    verify { pluginRegistry.createPlugin(mapView, mapboxMapOptions, clazz, pair1, pair2) }
  }

  @Test
  fun getPlugin() {
    val plugin = mockk<Any>()
    val clazz = mockkClass(Any::class)::class.java
    every { pluginRegistry.getPlugin(clazz) } returns plugin
    Assert.assertEquals(plugin, mapController.getPlugin(clazz))
    verify { pluginRegistry.getPlugin(clazz) }
  }

  @Test
  fun queueEvent() {
    val event = mockk<Runnable>()
    mapController.queueEvent(event)
    verify { renderer.queueEvent(event) }
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
}