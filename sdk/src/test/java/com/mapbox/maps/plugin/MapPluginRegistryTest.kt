package com.mapbox.maps.plugin

import android.content.Context
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.mapbox.common.ShadowLogger
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import io.mockk.*
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class MapPluginRegistryTest {

  private val delegateProvider = mockk<MapDelegateProvider>()
  private lateinit var mapPluginRegistry: MapPluginRegistry
  private val mapInitOptions = mockk<MapInitOptions>()
  private val mapOptions = mockk<MapOptions>()
  private val resourceOptions = mockk<ResourceOptions>()

  @Before
  fun setUp() {
    mapPluginRegistry = MapPluginRegistry(delegateProvider)
    every { mapInitOptions.mapOptions } returns mapOptions
    every { mapInitOptions.resourceOptions } returns resourceOptions
    every { mapInitOptions.attrs } returns null
  }

  @Test
  fun createPlugin() {
    val mapView = mockk<MapView>(relaxUnitFun = true)
    val mapPlugin = mockk<MapPlugin>(relaxUnitFun = true)
    val plugin = Plugin.Custom("id", mapPlugin)

    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    verify { mapPlugin.onDelegateProvider(delegateProvider) }
    verify { mapPlugin.initialize() }
  }

  @Test
  fun createPlugin_duplicate() {
    val mapView = mockk<MapView>(relaxUnitFun = true)
    val mapPluginOne = mockk<MapPlugin>(relaxUnitFun = true)
    val pluginOne = Plugin.Custom("id", mapPluginOne)
    mapPluginRegistry.createPlugin(mapView, mapInitOptions, pluginOne)

    val mapPluginTwo = mockk<MapPlugin>(relaxUnitFun = true)
    val pluginTwo = Plugin.Custom("id", mapPluginTwo)
    mapPluginRegistry.createPlugin(mapView, mapInitOptions, pluginTwo)

    verify(exactly = 1) { mapPluginOne.onDelegateProvider(delegateProvider) }
    verify(exactly = 2) { mapPluginOne.initialize() }
    verify(exactly = 0) { mapPluginTwo.onDelegateProvider(delegateProvider) }
    verify(exactly = 0) { mapPluginTwo.initialize() }
  }

  @Test
  fun createPlugin_startedBefore() {
    val mapView = mockk<MapView>()
    val lifecyclePlugin = mockk<TestLifecyclePlugin>(relaxUnitFun = true)
    val plugin = Plugin.Custom("id", lifecyclePlugin)

    mapPluginRegistry.onStart()
    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    verify { lifecyclePlugin.onStart() }
  }

  @Test
  fun createPlugin_viewPlugin() {
    val mapView = mockk<MapView>(relaxed = true)
    val viewPlugin = mockk<ViewPlugin>(relaxUnitFun = true)
    val view = mockk<View>()
    val plugin = Plugin.Custom("id", viewPlugin)
    every { mapInitOptions.context } returns mockk()
    every { mapOptions.pixelRatio } returns 1f
    every { viewPlugin.bind(mapView, any(), any()) } returns view
    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    verify { viewPlugin.bind(mapView, any(), any()) }
    verify { mapView.addView(view) }
    verify { viewPlugin.onPluginView(view) }
  }

  @Test
  fun createPlugin_gesturePlugin() {
    val mapView = mockk<MapView>(relaxed = true)
    val gesturesPlugin = mockk<GesturesPlugin>(relaxUnitFun = true)
    val plugin = Plugin.Custom("id", gesturesPlugin)
    every { gesturesPlugin.bind(any(), any(), any()) } returns mockk()
    every { mapInitOptions.context } returns mockk()
    every { mapOptions.pixelRatio } returns 1f
    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    verify { gesturesPlugin.bind(any(), any(), any()) }

    verify { gesturesPlugin.onDelegateProvider(any()) }
    verify { gesturesPlugin.initialize() }
  }

  @Test
  fun getPlugin() {
    val mapView = mockk<MapView>()
    val mapPlugin = mockk<MapPlugin>(relaxUnitFun = true)
    val plugin = Plugin.Custom("id", mapPlugin)

    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    Assert.assertEquals(mapPlugin, mapPluginRegistry.getPlugin("id"))
  }

  @Test
  fun getPlugin_null() {
    Assert.assertNull(mapPluginRegistry.getPlugin("id"))
  }

  @Test
  fun onStart() {
    val mapView = mockk<MapView>()
    val lifecyclePlugin = mockk<TestLifecyclePlugin>(relaxUnitFun = true)
    val plugin = Plugin.Custom("id", lifecyclePlugin)

    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    mapPluginRegistry.onStart()
    verify { lifecyclePlugin.onStart() }
  }

  @Test
  fun onStop() {
    val mapView = mockk<MapView>()
    val lifecyclePlugin = mockk<TestLifecyclePlugin>(relaxUnitFun = true)
    val plugin = Plugin.Custom("id", lifecyclePlugin)

    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    mapPluginRegistry.onStart()
    mapPluginRegistry.onStop()
    verify { lifecyclePlugin.onStop() }
  }

  @Test
  fun onCameraMove() {
    val mapView = mockk<MapView>(relaxUnitFun = true)
    val cameraPlugin = mockk<MapCameraPlugin>(relaxUnitFun = true)
    val plugin = Plugin.Custom("id", cameraPlugin)
    val cameraState = mockk<CameraState>()

    val center = Point.fromLngLat(1.0, 2.0)
    every { cameraState.center } returns center
    val zoom = 5.0
    every { cameraState.zoom } returns zoom
    val bearing = 15.0
    every { cameraState.bearing } returns bearing
    val pitch = 30.0
    every { cameraState.pitch } returns pitch
    val insets = EdgeInsets(1.0, 2.0, 3.0, 4.0)
    every { cameraState.padding } returns insets

    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    mapPluginRegistry.onCameraMove(cameraState)
    verify {
      cameraPlugin.onCameraMove(
        center.latitude(),
        center.longitude(),
        zoom,
        pitch,
        bearing,
        arrayOf(insets.left, insets.top, insets.right, insets.bottom)
      )
    }
  }

  @Test
  fun onTouch() {
    val mapView = mockk<MapView>(relaxUnitFun = true)
    val context = mockk<Context>(relaxUnitFun = true)
    val displayMetrics = DisplayMetrics().also { it.density = 1f }
    val motionEvent = mockk<MotionEvent>(relaxUnitFun = true)
    val gesturesPlugin = mockk<GesturesPlugin>(relaxed = true)
    val plugin = Plugin.Custom("id", gesturesPlugin)

    every { mapView.context.applicationContext } returns context
    every { mapInitOptions.context } returns context
    every { mapOptions.pixelRatio } returns 1f
    every { mapView.resources.displayMetrics } returns displayMetrics
    every { gesturesPlugin.onTouchEvent(motionEvent) } returns true

    assertFalse(mapPluginRegistry.onTouch(motionEvent))
    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    assertTrue(mapPluginRegistry.onTouch(motionEvent))

    verify {
      gesturesPlugin.onTouchEvent(motionEvent)
    }
  }

  @Test
  fun onGenericMotionEvent() {
    val mapView = mockk<MapView>(relaxUnitFun = true)
    val context = mockk<Context>(relaxUnitFun = true)
    val displayMetrics = DisplayMetrics().also { it.density = 1f }
    val motionEvent = mockk<MotionEvent>(relaxUnitFun = true)
    val gesturesPlugin = mockk<GesturesPlugin>(relaxed = true)
    val plugin = Plugin.Custom("id", gesturesPlugin)

    every { mapView.context.applicationContext } returns context
    every { mapInitOptions.context } returns context
    every { mapOptions.pixelRatio } returns 1f
    every { mapView.resources.displayMetrics } returns displayMetrics
    every { gesturesPlugin.onGenericMotionEvent(motionEvent) } returns true

    assertFalse(mapPluginRegistry.onGenericMotionEvent(motionEvent))
    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    assertTrue(mapPluginRegistry.onGenericMotionEvent(motionEvent))

    verify {
      gesturesPlugin.onGenericMotionEvent(motionEvent)
    }
  }

  @Test
  fun onSizeChanged() {
    val mapView = mockk<MapView>(relaxUnitFun = true)
    val context = mockk<Context>(relaxUnitFun = true)
    val displayMetrics = DisplayMetrics().also { it.density = 1f }
    val gesturesPlugin = mockk<GesturesPlugin>(relaxed = true)
    val plugin = Plugin.Custom("id", gesturesPlugin)

    every { mapView.context.applicationContext } returns context
    every { mapInitOptions.context } returns context
    every { mapOptions.pixelRatio } returns 1f
    every { mapView.resources.displayMetrics } returns displayMetrics

    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)
    mapPluginRegistry.onSizeChanged(1, 2)

    verify {
      gesturesPlugin.onSizeChanged(1, 2)
    }
  }

  @Test
  fun onStyleChanged() {
    val mapView = mockk<MapView>(relaxed = true)
    val stylePlugin = mockk<TestStyleObserverPlugin>(relaxUnitFun = true)
    val style = mockk<Style>()
    val plugin = Plugin.Custom("id", stylePlugin)

    mapPluginRegistry.createPlugin(mapView, mapInitOptions, plugin)

    mapPluginRegistry.onStyleChanged(style)

    verify {
      stylePlugin.onStyleChanged(style)
    }
  }

  class TestLifecyclePlugin : MapPlugin, LifecyclePlugin {
    override fun cleanup() {
    }

    override fun initialize() {
    }

    override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }
  }

  class TestStyleObserverPlugin : MapPlugin, MapStyleObserverPlugin {
    override fun initialize() {
    }

    override fun cleanup() {
    }

    override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    }

    override fun onStyleChanged(styleDelegate: StyleInterface) {
    }
  }
}