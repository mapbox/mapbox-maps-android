package com.mapbox.maps

import android.content.Context
import android.graphics.Bitmap
import android.view.Display
import android.view.MotionEvent
import android.view.Surface
import android.view.WindowManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.renderer.MapboxSurfaceRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.RendererSetupErrorListener
import com.mapbox.maps.renderer.widget.BitmapWidget
import com.mapbox.verifyOnce
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapSurfaceTest {

  private lateinit var context: Context
  private lateinit var mapController: MapController
  private lateinit var mapSurface: MapSurface
  private lateinit var mapInitOptions: MapInitOptions
  private lateinit var mapboxSurfaceRenderer: MapboxSurfaceRenderer
  private lateinit var surface: Surface

  @Before
  fun setUp() {
    context = mockk(relaxUnitFun = true)
    mapInitOptions = mockk(relaxUnitFun = true)
    mapController = mockk(relaxUnitFun = true)
    mapboxSurfaceRenderer = mockk(relaxUnitFun = true)
    surface = mockk(relaxed = true)

    mapSurface = MapSurface(
      context,
      surface,
      mapInitOptions,
      mapboxSurfaceRenderer,
      mapController
    )
  }

  @Test
  fun testGetSurface() {
    assertEquals(surface, mapSurface.surface)
  }

  @Test
  fun testSurfaceCreated() {
    val display: Display = mockk()
    val windowManager: WindowManager = mockk()
    val refreshRate = 100f
    every { windowManager.defaultDisplay } returns display
    every { display.refreshRate } returns refreshRate
    every { context.getSystemService(Context.WINDOW_SERVICE) } returns windowManager
    mapSurface.surfaceCreated()
    verifyOnce { mapboxSurfaceRenderer.surfaceCreated() }
    verifyOnce { mapController.setScreenRefreshRate(refreshRate.toInt()) }
  }

  @Test
  fun testSurfaceCreatedWithNoWindowManager() {
    every { context.getSystemService(Context.WINDOW_SERVICE) } returns null
    mapSurface.surfaceCreated()
    verifyOnce { mapboxSurfaceRenderer.surfaceCreated() }
    verifyOnce { mapController.setScreenRefreshRate(MapView.DEFAULT_FPS) }
  }

  @Test
  fun checkSurfaceChanged() {
    val w = 100
    val h = 200
    mapSurface.surfaceChanged(w, h)
    verifyOnce { mapboxSurfaceRenderer.surfaceChanged(surface, w, h) }
    verifyOnce { mapSurface.onSizeChanged(w, h) }
  }

  @Test
  fun testSurfaceDestroyed() {
    mapSurface.surfaceDestroyed()
    verifyOnce { mapboxSurfaceRenderer.surfaceDestroyed() }
  }

  @Test
  fun testGetMapboxMap() {
    val nativeMap: MapInterface = mockk(relaxed = true)
    val nativeObserver: NativeObserver = mockk(relaxed = true)
    val styleObserver: StyleObserver = mockk(relaxUnitFun = true)
    val mapboxMap = MapboxMap(nativeMap, nativeObserver, styleObserver)
    every { mapController.getMapboxMap() } returns mapboxMap
    val result = mapSurface.getMapboxMap()
    verifyOnce { mapController.getMapboxMap() }
    assertEquals(mapboxMap, result)
  }

  @Test
  fun testOnTouchEvent() {
    val event = MotionEvent.obtain(0, 0L, MotionEvent.ACTION_MOVE, 0f, 0f, 0)
    every { mapController.onTouchEvent(any()) } returns true
    mapSurface.onTouchEvent(event)
    verifyOnce { mapController.onTouchEvent(event) }
  }

  @Test
  fun testOnGenericMotionEvent() {
    val event = MotionEvent.obtain(0, 0L, MotionEvent.ACTION_MOVE, 0f, 0f, 0)
    every { mapController.onGenericMotionEvent(any()) } returns true
    mapSurface.onGenericMotionEvent(event)
    verifyOnce { mapController.onGenericMotionEvent(event) }
  }

  @Test
  fun testQueueEvent() {
    val event = Runnable {}
    mapSurface.queueEvent(event)
    verifyOnce { mapController.queueEvent(event) }
  }

  @Test
  fun testSnapshot() {
    every { mapController.snapshot() } returns null
    val snapshot = mapSurface.snapshot()
    verifyOnce { mapController.snapshot() }
    assertNull(snapshot)
  }

  @Test
  fun testSnapshotAsync() {
    every { mapController.snapshot(any()) } just runs
    val listener = MapView.OnSnapshotReady { }
    mapSurface.snapshot(listener)
    verifyOnce { mapController.snapshot(listener) }
  }

  @Test
  fun setMaximumFpsTest() {
    val fps = 160
    mapSurface.setMaximumFps(fps)
    verifyOnce { mapboxSurfaceRenderer.setMaximumFps(fps) }
  }

  @Test
  fun setOnFpsChangedListenerTest() {
    val listener = OnFpsChangedListener { }
    mapSurface.setOnFpsChangedListener(listener)
    verifyOnce { mapboxSurfaceRenderer.setOnFpsChangedListener(listener) }
  }

  @Test
  fun onStartTest() {
    mapSurface.onStart()
    verifyOnce { mapController.onStart() }
  }

  @Test
  fun onStopTest() {
    mapSurface.onStop()
    verifyOnce { mapController.onStop() }
  }

  @Test
  fun onDestroyTest() {
    mapSurface.onDestroy()
    verifyOnce { mapController.onDestroy() }
  }

  @Test
  fun onLowMemoryTest() {
    mapSurface.onLowMemory()
    verifyOnce { mapController.onLowMemory() }
  }

  @MapboxExperimental
  @Test
  fun addWidgetTest() {
    val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    val widget = BitmapWidget(bitmap)
    mapSurface.addWidget(widget)
    verifyOnce { mapController.addWidget(widget) }
    bitmap.recycle()
  }

  @MapboxExperimental
  @Test
  fun removeWidgetTest() {
    val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    val widget = BitmapWidget(bitmap)
    every { mapController.removeWidget(any()) } returns true
    mapSurface.removeWidget(widget)
    verifyOnce { mapController.removeWidget(widget) }
    bitmap.recycle()
  }

  @Test
  fun addRendererSetupErrorListenerTest() {
    val listener = RendererSetupErrorListener { }
    mapSurface.addRendererSetupErrorListener(listener)
    verifyOnce { mapController.addRendererSetupErrorListener(listener) }
  }

  @Test
  fun removeRendererSetupErrorListenerTest() {
    val listener = RendererSetupErrorListener { }
    mapSurface.removeRendererSetupErrorListener(listener)
    verifyOnce { mapController.removeRendererSetupErrorListener(listener) }
  }

  @Test
  fun getPluginTest() {
    val pluginId = "no_such_plugin"
    every { mapSurface.getPlugin<MapPlugin>(any()) } returns null
    val plugin: MapPlugin? = mapSurface.getPlugin(pluginId)
    verifyOnce { mapController.getPlugin<MapPlugin>(pluginId) }
    assertNull(plugin)
  }
}