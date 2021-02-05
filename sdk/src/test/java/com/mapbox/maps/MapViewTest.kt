package com.mapbox.maps

import android.util.AttributeSet
import android.view.MotionEvent
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.plugin.PLUGIN_LOGO_CLASS_NAME
import com.mapbox.maps.plugin.logo.LogoPlugin
import com.mapbox.maps.renderer.OnFpsChangedListener
import io.mockk.*
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class MapViewTest {

  private lateinit var attrs: AttributeSet
  private lateinit var mapController: MapController
  private lateinit var mapboxMap: MapboxMap
  private lateinit var mapView: MapView

  @Before
  fun setUp() {
    mockkStatic(MapboxMapStaticInitializer::class)
    every { MapboxMapStaticInitializer.loadMapboxMapNativeLib() } just Runs
    attrs = mockk()
    mapController = mockk(relaxUnitFun = true)
    mapboxMap = mockk(relaxUnitFun = true)
    every { mapController.getMapboxMap() } returns mapboxMap
    mapView = MapView(
      mockk(relaxed = true),
      mockk(relaxed = true),
      mapController
    )
  }

  @Test
  fun start() {
    mapView.onStart()
    verify { mapController.onStart() }
  }

  @Test
  fun stop() {
    mapView.onStop()
    verify { mapController.onStop() }
  }

  @Test
  fun onDestroy() {
    mapView.onDestroy()
    verify { mapController.onDestroy() }
  }

  @Test
  fun onLowMemory() {
    mapView.onLowMemory()
    verify { mapController.reduceMemoryUse() }
  }

  @Test
  fun getMapboxMap() {
    mapView.getMapboxMap()
    verify { mapController.getMapboxMap() }
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
    every { mapController.createPlugin<LogoPlugin>(any(), any(), any()) } returns mockk()
    val clazz = Class.forName(PLUGIN_LOGO_CLASS_NAME) as Class<LogoPlugin>
    val args = mockk<Pair<Class<*>, Any>>()
    mapView.createPlugin(clazz, args)
    verify { mapController.createPlugin(mapView, clazz, args) }
  }

  @Test
  fun getPluginByClazz() {
    val clazz = Class.forName(PLUGIN_LOGO_CLASS_NAME) as Class<LogoPlugin>
    every { mapController.getPlugin(clazz) } returns mockk()
    mapView.getPlugin<LogoPlugin>(PLUGIN_LOGO_CLASS_NAME)
    verify { mapController.getPlugin(clazz) }
  }

  @Test
  fun getPluginByName() {
    val clazz = Class.forName(PLUGIN_LOGO_CLASS_NAME) as Class<LogoPlugin>
    every { mapController.getPlugin(clazz) } returns mockk()
    mapView.getPlugin(clazz)
    verify { mapController.getPlugin(clazz) }
  }

  @Test
  fun setOnFpsChangedListener() {
    val listener = mockk<OnFpsChangedListener>()
    mapView.setOnFpsChangedListener(listener)
    verify { mapController.setOnFpsChangedListener(listener) }
  }
}