@file:Suppress("NoMockkVerifyImport")

package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import android.os.Looper
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.car.app.AppManager
import androidx.car.app.CarContext
import androidx.car.app.SurfaceContainer
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.Plugin
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import java.time.Duration

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class CarMapSurfaceOwnerTest {

  private val carMapGestures = mockk<DefaultMapboxCarMapGestureHandler>(relaxed = true)
  private val carContext: CarContext = mockk(relaxed = true)
  private val mapInitOptions: MapInitOptions = mockk(relaxed = true)
  private val testSurface: Surface = mockk(relaxed = true)
  private val testMapSurface: MapSurface = mockk(relaxed = true)

  private val carMapSurfaceOwner = CarMapSurfaceOwner(carMapGestures)

  @Before
  fun `set up mocks`() {
    every {
      carContext.getCarService(AppManager::class.java).setSurfaceCallback(carMapSurfaceOwner)
    } just Runs
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    every { logW(any(), any()) } just Runs
    mockkObject(MapSurfaceProvider)
    every { MapSurfaceProvider.create(any(), any(), any()) } returns testMapSurface
  }

  @After
  fun teardown() {
    unmockkAll()
  }

  @Test
  fun `should not notify observer loaded when there is no surface`() {
    val firstObserver: MapboxCarMapObserver = mockk(relaxed = true)
    val secondObserver: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(firstObserver)
    carMapSurfaceOwner.registerObserver(secondObserver)
    carMapSurfaceOwner.unregisterObserver(firstObserver)
    carMapSurfaceOwner.clearObservers()

    verify(exactly = 0) { firstObserver.onAttached(any()) }
    verify(exactly = 0) { secondObserver.onAttached(any()) }
  }

  @Test
  fun `should not notify observer detached when there is no surface`() {
    val firstObserver: MapboxCarMapObserver = mockk(relaxed = true)
    val secondObserver: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(firstObserver)
    carMapSurfaceOwner.registerObserver(secondObserver)
    carMapSurfaceOwner.unregisterObserver(firstObserver)
    carMapSurfaceOwner.clearObservers()

    verify(exactly = 0) { firstObserver.onDetached(any()) }
    verify(exactly = 0) { secondObserver.onDetached(any()) }
  }

  @Test
  fun `onSurfaceAvailable should notify observers that map is loaded`() {
    val firstObserver: MapboxCarMapObserver = mockk(relaxed = true)
    val secondObserver: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(firstObserver)
    carMapSurfaceOwner.registerObserver(secondObserver)

    val surfaceContainer: SurfaceContainer = mockk(relaxed = true)
    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(surfaceContainer)

    verify(exactly = 1) { firstObserver.onAttached(any()) }
    verify(exactly = 1) { secondObserver.onAttached(any()) }
  }

  @Test
  fun `surfaceAvailable should not notify visibleAreaChanged when visible area is null`() {
    val firstObserver: MapboxCarMapObserver = mockk(relaxed = true)
    val secondObserver: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(firstObserver)
    carMapSurfaceOwner.registerObserver(secondObserver)

    val surfaceContainer: SurfaceContainer = mockk(relaxed = true)
    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(surfaceContainer)

    verify(exactly = 0) { firstObserver.onVisibleAreaChanged(any(), any()) }
    verify(exactly = 0) { secondObserver.onVisibleAreaChanged(any(), any()) }
  }

  @Test
  fun `surfaceVisibleAreaChanged should notify visibleAreaChanged when surface is available`() {
    val firstObserver: MapboxCarMapObserver = mockk(relaxed = true)
    val secondObserver: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(firstObserver)
    carMapSurfaceOwner.registerObserver(secondObserver)

    val surfaceContainer: SurfaceContainer = mockk {
      every { width } returns 800
      every { height } returns 400
      every { surface } returns testSurface
    }
    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(surfaceContainer)
    val visibleRect: Rect = mockk(relaxed = true)
    carMapSurfaceOwner.onVisibleAreaChanged(visibleRect)

    verify(exactly = 1) { firstObserver.onVisibleAreaChanged(any(), any()) }
    verify(exactly = 1) { secondObserver.onVisibleAreaChanged(any(), any()) }
  }

  @Test
  fun `surfaceVisibleAreaChanged should not notify visibleAreaChanged when surface is not available`() {
    val firstObserver: MapboxCarMapObserver = mockk(relaxed = true)
    val secondObserver: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(firstObserver)
    carMapSurfaceOwner.registerObserver(secondObserver)

    val visibleRect: Rect = mockk(relaxed = true)
    carMapSurfaceOwner.onVisibleAreaChanged(visibleRect)

    verify(exactly = 0) { firstObserver.onVisibleAreaChanged(any(), any()) }
    verify(exactly = 0) { secondObserver.onVisibleAreaChanged(any(), any()) }
  }

  @Test
  fun `surfaceDestroyed should notify observers before stopping and destroying map`() {
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(observer)
    val surfaceContainer: SurfaceContainer = mockk {
      every { width } returns 800
      every { height } returns 400
      every { surface } returns testSurface
    }

    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(surfaceContainer)
    carMapSurfaceOwner.onSurfaceDestroyed(surfaceContainer)

    verifyOrder {
      observer.onDetached(any())
      testMapSurface.onStop()
      testMapSurface.surfaceDestroyed()
      testMapSurface.onDestroy()
    }
  }

  @Test
  fun `onDetached is called after mapboxCarMapSurface becomes null when surfaceDestroyed`() {
    carMapSurfaceOwner.registerObserver(object : MapboxCarMapObserver {
      override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
        assertNull(carMapSurfaceOwner.mapboxCarMapSurface)
      }
    })

    val surfaceContainer: SurfaceContainer = mockk {
      every { width } returns 800
      every { height } returns 400
      every { surface } returns testSurface
    }

    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(surfaceContainer)
    carMapSurfaceOwner.onSurfaceDestroyed(surfaceContainer)
  }

  @Test
  fun `onDetached is called after mapboxCarMapSurface becomes null when new surfaceAvailable`() {
    carMapSurfaceOwner.registerObserver(object : MapboxCarMapObserver {
      override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
        assertNotEquals(carMapSurfaceOwner.mapboxCarMapSurface, mapboxCarMapSurface)
      }
    })

    val firstSurface: SurfaceContainer = mockk {
      every { width } returns 800
      every { height } returns 400
      every { surface } returns mockk(relaxed = true)
    }
    val secondSurface: SurfaceContainer = mockk {
      every { width } returns 400
      every { height } returns 800
      every { surface } returns mockk(relaxed = true)
    }

    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(firstSurface)
    carMapSurfaceOwner.onVisibleAreaChanged(mockk(relaxed = true))
    carMapSurfaceOwner.onSurfaceAvailable(secondSurface)
  }

  @Test
  fun `should notify destroy and detached old surface when new surface is available`() {
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(observer)
    val firstSurface: Surface = mockk(relaxed = true)
    val firstMapSurface: MapSurface = mockk(relaxed = true)
    every { MapSurfaceProvider.create(any(), firstSurface, mapInitOptions) } returns firstMapSurface
    val firstContainer: SurfaceContainer = mockk {
      every { width } returns 800
      every { height } returns 400
      every { surface } returns firstSurface
    }
    val secondSurface: Surface = mockk(relaxed = true)
    val secondMapSurface: MapSurface = mockk(relaxed = true)
    every { MapSurfaceProvider.create(any(), secondSurface, mapInitOptions) } returns secondMapSurface
    val secondContainer: SurfaceContainer = mockk {
      every { width } returns 400
      every { height } returns 800
      every { surface } returns secondSurface
    }

    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(firstContainer)
    carMapSurfaceOwner.onVisibleAreaChanged(mockk(relaxed = true))
    carMapSurfaceOwner.onSurfaceAvailable(secondContainer)

    verifyOrder {
      // Verify the initial setup
      firstMapSurface.onStart()
      firstMapSurface.surfaceCreated()
      firstMapSurface.surfaceChanged(800, 400)
      observer.onAttached(match { it.mapSurface == firstMapSurface })
      observer.onVisibleAreaChanged(any(), any())

      // Start attaching new surface and detaching the old one
      secondMapSurface.onStart()
      secondMapSurface.surfaceCreated()
      secondMapSurface.surfaceChanged(400, 800)
      observer.onDetached(match { it.mapSurface == firstMapSurface })
      firstMapSurface.onStop()
      firstMapSurface.surfaceDestroyed()
      firstMapSurface.onDestroy()
      observer.onAttached(match { it.mapSurface == secondMapSurface })
      observer.onVisibleAreaChanged(any(), any())
    }
  }

  @Test
  fun `surfaceVisibleAreaChanged should notify visibleAreaChanged with edgeInsets`() {
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(observer)
    val visibleAreaSlot = slot<Rect>()
    val edgeInsets = slot<EdgeInsets>()
    every {
      observer.onVisibleAreaChanged(capture(visibleAreaSlot), capture(edgeInsets))
    } just Runs
    val surfaceContainer: SurfaceContainer = mockk {
      every { width } returns 800
      every { height } returns 400
      every { surface } returns testSurface
    }

    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(surfaceContainer)
    val visibleRect = Rect(30, 112, 779, 381)
    carMapSurfaceOwner.onVisibleAreaChanged(visibleRect)

    assertEquals(visibleRect, visibleAreaSlot.captured)
    // edgeInset.left = visibleRect.left = 30
    assertEquals(30.0, edgeInsets.captured.left, 0.0001)
    // edgeInset.top = visibleRect.top = 112
    assertEquals(112.0, edgeInsets.captured.top, 0.0001)
    // edgeInset.right = surfaceContainer.width - visibleRect.right = 800 - 779 = 21
    assertEquals(21.0, edgeInsets.captured.right, 0.0001)
    // edgeInsets.bottom = surfaceContainer.height - visibleRect.bottom = 400 - 381 = 19
    assertEquals(19.0, edgeInsets.captured.bottom, 0.0001)
  }

  @Test
  fun `visibleCenter is zero by default`() {
    val visibleCenter = carMapSurfaceOwner.visibleCenter
    assertEquals(0.0, visibleCenter.x, 0.0001)
    assertEquals(0.0, visibleCenter.y, 0.0001)
  }

  @Test
  fun `surfaceAvailable finds the visibleCenter`() {
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(observer)
    val visibleAreaSlot = slot<Rect>()
    val edgeInsets = slot<EdgeInsets>()
    every {
      observer.onVisibleAreaChanged(capture(visibleAreaSlot), capture(edgeInsets))
    } just Runs
    val surfaceContainer: SurfaceContainer = mockk {
      every { width } returns 805
      every { height } returns 405
      every { surface } returns testSurface
    }

    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(surfaceContainer)
    val visibleCenter = carMapSurfaceOwner.visibleCenter

    assertEquals(402.5, visibleCenter.x, 0.0001)
    assertEquals(202.5, visibleCenter.y, 0.0001)
  }

  @Test
  fun `surfaceVisibleAreaChanged finds the visibleCenter`() {
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(observer)
    val visibleAreaSlot = slot<Rect>()
    val edgeInsets = slot<EdgeInsets>()
    every {
      observer.onVisibleAreaChanged(capture(visibleAreaSlot), capture(edgeInsets))
    } just Runs
    val surfaceContainer: SurfaceContainer = mockk {
      every { width } returns 805
      every { height } returns 405
      every { surface } returns testSurface
    }

    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(surfaceContainer)
    val visibleRect = Rect(133, 112, 779, 381)
    carMapSurfaceOwner.onVisibleAreaChanged(visibleRect)

    val visibleCenter = carMapSurfaceOwner.visibleCenter
    assertEquals(456.0, visibleCenter.x, 0.0001)
    assertEquals(246.5, visibleCenter.y, 0.0001)
  }

  @Test
  fun `onClick is forwarded to the gesture handler`() {
    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(mockk(relaxed = true))

    carMapSurfaceOwner.onClick(10f, 20f)

    verify(exactly = 1) {
      carMapGestures.onClick(carMapSurfaceOwner.mapboxCarMapSurface!!, 10f, 20f)
    }
  }

  @Test
  fun `onClick is ignored before the surface is ready`() {
    carMapSurfaceOwner.onClick(10f, 20f)

    verify(exactly = 0) { carMapGestures.onClick(any(), any(), any()) }
  }

  @Test
  fun `gesture detector is not called before surface is ready`() {
    carMapSurfaceOwner.onScroll(1f, 1f)
    carMapSurfaceOwner.onFling(1f, 1f)
    carMapSurfaceOwner.onScale(50f, 100f, 1f)

    verify(exactly = 0) { carMapGestures.onScale(any(), any(), any(), any()) }
    verify(exactly = 0) { carMapGestures.onFling(any(), any(), any()) }
    verify(exactly = 0) { carMapGestures.onScale(any(), any(), any(), any()) }
  }

  @Test
  fun `default gesture detector is called after surface is ready`() {
    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    carMapSurfaceOwner.onSurfaceAvailable(
      mockk {
        every { width } returns 805
        every { height } returns 405
        every { surface } returns testSurface
      }
    )

    carMapSurfaceOwner.onScroll(1f, 1f)
    carMapSurfaceOwner.onFling(1f, 1f)
    carMapSurfaceOwner.onScale(50f, 100f, 1f)

    verify(exactly = 1) { carMapGestures.onScale(any(), any(), any(), any()) }
    verify(exactly = 1) { carMapGestures.onFling(any(), any(), any()) }
    verify(exactly = 1) { carMapGestures.onScale(any(), any(), any(), any()) }
  }

  @Test
  fun `MAP_VIEW attaches observers once the map view surface is created`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)

    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    verify(exactly = 0) { observer.onAttached(any()) }
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)

    verify { observer.onAttached(match { it.mapView == testMapView && it.mapSurface == testMapSurface }) }
    verify(exactly = 0) { MapSurfaceProvider.create(any(), any(), any()) }
    verify(exactly = 0) { testMapSurface.onStart() }
    verify(exactly = 0) { testMapSurface.surfaceCreated() }
    verify(exactly = 0) { testMapSurface.surfaceChanged(any(), any()) }
  }

  @Test
  fun `MAP_VIEW warns when the map view surface is not created in time`() {
    val owner = mapViewOwner()
    owner.onSurfaceAvailable(mapViewSurfaceContainer())

    shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(2_999))
    verify(exactly = 0) { logW(any(), any()) }

    shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(1))
    verify(exactly = 1) { logW("CarMapSurfaceOwner", match { it.contains("still not created") }) }
  }

  @Test
  fun `MAP_VIEW does not warn when the map view surface is created in time`() {
    val owner = mapViewOwner()
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)

    shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(10))

    verify(exactly = 0) { logW(any(), any()) }
    verify { logI("CarMapSurfaceOwner", match { it.startsWith("Map view surface created after") }) }
  }

  @Test
  fun `MAP_VIEW does not warn when the car surface is destroyed while pending`() {
    val owner = mapViewOwner()
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    owner.onSurfaceDestroyed(mapViewSurfaceContainer())

    shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(10))

    verify(exactly = 0) { logW(any(), any()) }
  }

  @Test
  fun `MAP_VIEW warns once for the replacement display only`() {
    val owner = mapViewOwner()
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(2))
    owner.onSurfaceAvailable(mapViewSurfaceContainer())

    shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(2))
    verify(exactly = 0) { logW(any(), any()) }

    shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(10))
    verify(exactly = 1) { logW(any(), any()) }
  }

  @Test
  fun `MAP_VIEW attaches immediately when the map view surface already exists`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    every { mapViewSurfaceHolder.surface } returns mockk { every { isValid } returns true }

    owner.onSurfaceAvailable(mapViewSurfaceContainer())

    verify(exactly = 1) { observer.onAttached(match { it.mapView == testMapView }) }
    verify { mapViewSurfaceHolder.removeCallback(mapViewSurfaceCallback.captured) }
    shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(10))
    verify(exactly = 0) { logW(any(), any()) }
  }

  @Test
  fun `MAP_VIEW excludes default ornaments without changing the original options`() {
    val options = MapInitOptions(carContext, mapOptions = mockk())
    val originalPlugins = options.plugins
    val owner = mapViewOwner(options)
    val displayOptions = slot<MapInitOptions>()

    owner.onSurfaceAvailable(mapViewSurfaceContainer())

    verify { MapSurfaceProvider.createMapViewDisplay(carContext, any(), capture(displayOptions)) }
    assertEquals(
      listOf(
        Plugin.MAPBOX_CAMERA_PLUGIN_ID,
        Plugin.MAPBOX_GESTURES_PLUGIN_ID,
        Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID,
        Plugin.MAPBOX_ANNOTATION_PLUGIN_ID,
        Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID,
        Plugin.MAPBOX_MAP_OVERLAY_PLUGIN_ID,
        Plugin.MAPBOX_VIEWPORT_PLUGIN_ID,
      ),
      displayOptions.captured.plugins.map { it.id }
    )
    assertSame(originalPlugins, options.plugins)
    assertSame(options, owner.mapInitOptions)
    assertEquals(options.copy(plugins = displayOptions.captured.plugins), displayOptions.captured)
  }

  @Test
  fun `MAP_VIEW excludes explicitly supplied ornaments while retaining custom and lifecycle plugins`() {
    val customPlugin = Plugin.Custom("custom", mockk())
    val lifecyclePlugin = Plugin.Mapbox(Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID)
    val plugins = listOf(
      Plugin.Mapbox(Plugin.MAPBOX_COMPASS_PLUGIN_ID),
      customPlugin,
      Plugin.Mapbox(Plugin.MAPBOX_LOGO_PLUGIN_ID),
      lifecyclePlugin,
      Plugin.Mapbox(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_CONSOLIDATED_ATTRIBUTION_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_SCALEBAR_PLUGIN_ID),
    )
    val options = MapInitOptions(carContext, mapOptions = mockk(), plugins = plugins)
    val owner = mapViewOwner(options)
    val displayOptions = slot<MapInitOptions>()

    owner.onSurfaceAvailable(mapViewSurfaceContainer())

    verify { MapSurfaceProvider.createMapViewDisplay(carContext, any(), capture(displayOptions)) }
    assertEquals(listOf(customPlugin, lifecyclePlugin), displayOptions.captured.plugins)
    assertSame(plugins, options.plugins)
  }

  @Test
  fun `MAP_SURFACE passes default plugins through without filtering`() {
    val options = MapInitOptions(carContext, mapOptions = mockk())
    carMapSurfaceOwner.setup(carContext, options)
    val surfaceOptions = slot<MapInitOptions>()

    carMapSurfaceOwner.onSurfaceAvailable(mapViewSurfaceContainer())

    verify { MapSurfaceProvider.create(carContext, testSurface, capture(surfaceOptions)) }
    assertSame(options, surfaceOptions.captured)
    assertSame(MapInitOptions.defaultPluginList, options.plugins)
  }

  @Test
  fun `MAP_VIEW surfaceDestroyed notifies observers while the map is valid before destroying the display`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)
    var mapValid = true
    every { testMapSurface.mapboxMap.isValid() } answers { mapValid }
    every { testMapViewDisplay.destroy() } answers { mapValid = false }
    every { observer.onDetached(any()) } answers {
      assertNull(owner.mapboxCarMapSurface)
      assertTrue(testMapSurface.mapboxMap.isValid())
    }

    owner.onSurfaceDestroyed(mapViewSurfaceContainer())
    owner.onSurfaceDestroyed(mapViewSurfaceContainer())

    assertFalse(mapValid)
    verifyOrder {
      observer.onDetached(any())
      testMapViewDisplay.destroy()
    }
    verify(exactly = 1) { observer.onDetached(any()) }
    verify(exactly = 1) { testMapViewDisplay.destroy() }
    verify(exactly = 0) { testMapSurface.onStop() }
    verify(exactly = 0) { testMapSurface.surfaceDestroyed() }
    verify(exactly = 0) { testMapSurface.onDestroy() }
  }

  @Test
  fun `MAP_VIEW surfaceDestroyed still releases the display if an observer throws`() {
    val owner = mapViewOwner()
    val observer = mockk<MapboxCarMapObserver>(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)
    val failure = IllegalStateException("observer failed to detach")
    every { observer.onDetached(any()) } throws failure

    assertSame(
      failure,
      assertThrows(IllegalStateException::class.java) {
        owner.onSurfaceDestroyed(mapViewSurfaceContainer())
      }
    )
    owner.onSurfaceDestroyed(mapViewSurfaceContainer())

    assertNull(owner.mapboxCarMapSurface)
    verifyOrder {
      observer.onDetached(any())
      testMapViewDisplay.destroy()
    }
    verify(exactly = 1) { observer.onDetached(any()) }
    verify(exactly = 1) { testMapViewDisplay.destroy() }
  }

  @Test
  fun `MAP_VIEW replacement releases the previous display without destroying its facade`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)
    val nextDisplay: CarMapViewDisplay = mockk(relaxed = true) {
      every { mapView } returns testMapView
    }
    every { MapSurfaceProvider.createMapViewDisplay(any(), any(), any()) } returns nextDisplay

    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)

    verifyOrder {
      observer.onDetached(any())
      testMapViewDisplay.destroy()
      MapSurfaceProvider.createMapViewDisplay(any(), any(), any())
      observer.onAttached(any())
    }
    verify(exactly = 0) { testMapSurface.onDestroy() }
    verify(exactly = 0) { nextDisplay.destroy() }
  }

  @Test
  fun `MAP_VIEW cleans up shared plugins before initializing the replacement`() {
    val plugin = mockk<MapPlugin>(relaxed = true)
    var initialized = false
    every { plugin.initialize() } answers { initialized = true }
    every { plugin.cleanup() } answers { initialized = false }
    val options = MapInitOptions(
      carContext,
      mapOptions = mockk(),
      plugins = listOf(
        Plugin.Custom("custom", plugin),
        Plugin.Mapbox(Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID),
      ),
    )
    val owner = mapViewOwner(options)
    every { MapSurfaceProvider.createMapViewDisplay(any(), any(), any()) } answers {
      plugin.initialize()
      testMapViewDisplay
    }
    every { testMapViewDisplay.destroy() } answers { plugin.cleanup() }
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)
    val replacement = mapViewSurfaceContainer(width = 1000, height = 600)
    val nextDisplay = mockk<CarMapViewDisplay>(relaxed = true) {
      every { mapView } returns testMapView
    }
    every { MapSurfaceProvider.createMapViewDisplay(carContext, replacement, any()) } answers {
      assertNull(owner.mapboxCarMapSurface)
      plugin.initialize()
      nextDisplay
    }

    owner.onSurfaceAvailable(replacement)
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)

    assertTrue(initialized)
    verifyOrder {
      plugin.cleanup()
      MapSurfaceProvider.createMapViewDisplay(carContext, replacement, any())
      plugin.initialize()
    }
    verify(exactly = 1) { testMapViewDisplay.destroy() }
    verify(exactly = 0) { nextDisplay.destroy() }
  }

  @Test
  fun `MAP_VIEW failed active replacement leaves no destroyed surface for observers`() {
    val owner = mapViewOwner()
    val observer = mockk<MapboxCarMapObserver>(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)
    owner.onVisibleAreaChanged(Rect(20, 10, 750, 350))
    owner.onStableAreaChanged(Rect(30, 20, 700, 300))
    val replacement = mapViewSurfaceContainer(width = 1000, height = 600)
    every { MapSurfaceProvider.createMapViewDisplay(carContext, replacement, any()) } returns null

    owner.onSurfaceAvailable(replacement)
    val lateObserver = mockk<MapboxCarMapObserver>(relaxed = true)
    owner.registerObserver(lateObserver)
    owner.onSurfaceDestroyed(replacement)

    assertNull(owner.mapboxCarMapSurface)
    assertNull(owner.visibleEdgeInsets)
    assertNull(owner.stableEdgeInsets)
    verifyOrder {
      observer.onDetached(any())
      testMapViewDisplay.destroy()
      MapSurfaceProvider.createMapViewDisplay(carContext, replacement, any())
    }
    verify(exactly = 1) { observer.onDetached(any()) }
    verify(exactly = 1) { testMapViewDisplay.destroy() }
    verify(exactly = 0) { lateObserver.onAttached(any()) }
    verify(exactly = 0) { lateObserver.onVisibleAreaChanged(any(), any()) }
    verify(exactly = 0) { lateObserver.onStableAreaChanged(any(), any()) }
  }

  @Test
  fun `MAP_VIEW surfaceDestroyed before the map view surface exists releases the display`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())

    owner.onSurfaceDestroyed(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)

    verify { testMapViewDisplay.destroy() }
    verify(exactly = 0) { observer.onAttached(any()) }
  }

  @Test
  fun `MAP_VIEW failed replacement releases pending display and ignores its stale callback`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    val oldCallback = mapViewSurfaceCallback.captured
    val replacement = mapViewSurfaceContainer(width = 1000, height = 600)
    every { MapSurfaceProvider.createMapViewDisplay(carContext, replacement, any()) } returns null
    every { testMapViewDisplay.destroy() } answers {
      oldCallback.surfaceCreated(mapViewSurfaceHolder)
    }

    owner.onSurfaceAvailable(replacement)
    oldCallback.surfaceCreated(mapViewSurfaceHolder)
    owner.onSurfaceDestroyed(replacement)

    assertNull(owner.mapboxCarMapSurface)
    verifyOrder {
      testMapViewDisplay.destroy()
      MapSurfaceProvider.createMapViewDisplay(carContext, replacement, any())
    }
    verify(exactly = 1) { testMapViewDisplay.destroy() }
    verify(exactly = 0) { observer.onAttached(any()) }
    verify(exactly = 0) { MapSurfaceProvider.createFromMapView(any()) }
  }

  @Test
  fun `MAP_VIEW replacement exception releases pending display and ignores its stale callback`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    val oldCallback = mapViewSurfaceCallback.captured
    val replacement = mapViewSurfaceContainer(width = 1000, height = 600)
    val failure = IllegalStateException("display creation failed")
    every { MapSurfaceProvider.createMapViewDisplay(carContext, replacement, any()) } throws failure

    assertSame(
      failure,
      assertThrows(IllegalStateException::class.java) {
        owner.onSurfaceAvailable(replacement)
      }
    )
    oldCallback.surfaceCreated(mapViewSurfaceHolder)
    owner.onSurfaceDestroyed(replacement)

    assertNull(owner.mapboxCarMapSurface)
    verify(exactly = 1) { testMapViewDisplay.destroy() }
    verify(exactly = 0) { observer.onAttached(any()) }
    verify(exactly = 0) { MapSurfaceProvider.createFromMapView(any()) }
  }

  @Test
  fun `MAP_VIEW replays stable area received before the view surface exists`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    val area = Rect(30, 20, 750, 350)

    owner.onStableAreaChanged(area)

    assertNull(owner.stableEdgeInsets)
    verify(exactly = 0) { observer.onStableAreaChanged(any(), any()) }

    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)

    val expectedInsets = EdgeInsets(20.0, 30.0, 50.0, 50.0)
    assertEquals(expectedInsets, owner.stableEdgeInsets)
    verifyOrder {
      observer.onAttached(any())
      observer.onStableAreaChanged(area, expectedInsets)
    }
    val lateObserver: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(lateObserver)
    verify(exactly = 1) { lateObserver.onStableAreaChanged(area, expectedInsets) }
  }

  @Test
  fun `MAP_VIEW recalculates stored stable area when replacement dimensions change`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)
    val area = Rect(30, 20, 750, 350)
    owner.onStableAreaChanged(area)
    val replacement = mapViewSurfaceContainer(width = 1000, height = 600)

    owner.onSurfaceAvailable(replacement)
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)

    val expectedInsets = EdgeInsets(20.0, 30.0, 250.0, 250.0)
    assertEquals(expectedInsets, owner.stableEdgeInsets)
    verify(exactly = 1) { observer.onStableAreaChanged(area, expectedInsets) }
  }

  @Test
  fun `MAP_VIEW recalculates stable area updated while replacement is pending`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)
    owner.onStableAreaChanged(Rect(30, 20, 750, 350))
    owner.onSurfaceAvailable(mapViewSurfaceContainer(width = 1000, height = 600))
    val area = Rect(40, 25, 700, 300)
    owner.onStableAreaChanged(area)

    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)

    val expectedInsets = EdgeInsets(25.0, 40.0, 300.0, 300.0)
    assertEquals(expectedInsets, owner.stableEdgeInsets)
    verify(exactly = 1) { observer.onStableAreaChanged(area, expectedInsets) }
  }

  @Test
  fun `MAP_SURFACE replays stable area received before attachment`() {
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(observer)
    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    val area = Rect(30, 20, 750, 350)
    carMapSurfaceOwner.onStableAreaChanged(area)

    carMapSurfaceOwner.onSurfaceAvailable(mapViewSurfaceContainer())

    val expectedInsets = EdgeInsets(20.0, 30.0, 50.0, 50.0)
    assertEquals(expectedInsets, carMapSurfaceOwner.stableEdgeInsets)
    verifyOrder {
      observer.onAttached(any())
      observer.onStableAreaChanged(area, expectedInsets)
    }
  }

  @Test
  fun `MAP_VIEW does not notify stable area when none was received`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)

    owner.onSurfaceAvailable(mapViewSurfaceContainer())
    mapViewSurfaceCallback.captured.surfaceCreated(mapViewSurfaceHolder)

    assertNull(owner.stableEdgeInsets)
    verify(exactly = 0) { observer.onStableAreaChanged(any(), any()) }
  }

  @Test
  fun `MAP_VIEW rejects texture mode during setup before allocating a display`() {
    val owner = CarMapSurfaceOwner(carMapGestures, MapboxCarMapType.MAP_VIEW)
    every { mapInitOptions.textureView } returns true

    val exception = assertThrows(IllegalArgumentException::class.java) {
      owner.setup(carContext, mapInitOptions)
    }

    assertEquals("MAP_VIEW requires MapInitOptions.textureView = false", exception.message)
    assertFalse(owner.isSetup())
    verify(exactly = 0) { MapSurfaceProvider.createMapViewDisplay(any(), any(), any()) }
  }

  @Test
  fun `MAP_VIEW rejects texture mode enabled after setup before allocating a display`() {
    val owner = mapViewOwner()
    every { mapInitOptions.textureView } returns true

    assertThrows(IllegalArgumentException::class.java) {
      owner.onSurfaceAvailable(mapViewSurfaceContainer())
    }

    verify(exactly = 0) { MapSurfaceProvider.createMapViewDisplay(any(), any(), any()) }
  }

  @Test
  fun `MAP_VIEW rejects missing lifecycle plugin during setup before allocating a display`() {
    val owner = CarMapSurfaceOwner(carMapGestures, MapboxCarMapType.MAP_VIEW)
    every { mapInitOptions.plugins } returns listOf(Plugin.Mapbox(Plugin.MAPBOX_CAMERA_PLUGIN_ID))

    val exception = assertThrows(IllegalArgumentException::class.java) {
      owner.setup(carContext, mapInitOptions)
    }

    assertEquals(
      "MAP_VIEW requires the MAPBOX_LIFECYCLE_PLUGIN_ID plugin in MapInitOptions.plugins",
      exception.message
    )
    assertFalse(owner.isSetup())
    verify(exactly = 0) { MapSurfaceProvider.createMapViewDisplay(any(), any(), any()) }
  }

  @Test
  fun `MAP_VIEW rejects lifecycle plugin removed after setup before allocating a display`() {
    val owner = mapViewOwner()
    every { mapInitOptions.plugins } returns emptyList()

    assertThrows(IllegalArgumentException::class.java) {
      owner.onSurfaceAvailable(mapViewSurfaceContainer())
    }

    verify(exactly = 0) { MapSurfaceProvider.createMapViewDisplay(any(), any(), any()) }
  }

  @Test
  fun `MAP_SURFACE does not require the lifecycle plugin`() {
    every { mapInitOptions.plugins } returns emptyList()
    carMapSurfaceOwner.setup(carContext, mapInitOptions)

    carMapSurfaceOwner.onSurfaceAvailable(mapViewSurfaceContainer())

    verify(exactly = 1) { MapSurfaceProvider.create(carContext, testSurface, mapInitOptions) }
    verify(exactly = 0) { MapSurfaceProvider.createMapViewDisplay(any(), any(), any()) }
  }

  @Test
  fun `MAP_SURFACE continues to ignore texture mode`() {
    every { mapInitOptions.textureView } returns true
    carMapSurfaceOwner.setup(carContext, mapInitOptions)

    carMapSurfaceOwner.onSurfaceAvailable(mapViewSurfaceContainer())

    verify(exactly = 1) { MapSurfaceProvider.create(carContext, testSurface, mapInitOptions) }
    verify(exactly = 0) { MapSurfaceProvider.createMapViewDisplay(any(), any(), any()) }
  }

  @Test
  fun `MAP_VIEW releases unexpected view display before throwing`() {
    val owner = mapViewOwner()
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    owner.registerObserver(observer)
    every { testMapView.rendererSurfaceView } returns null

    assertThrows(IllegalStateException::class.java) {
      owner.onSurfaceAvailable(mapViewSurfaceContainer())
    }
    owner.onSurfaceDestroyed(mapViewSurfaceContainer())

    verify(exactly = 1) { testMapViewDisplay.destroy() }
    verify(exactly = 0) { observer.onAttached(any()) }
    verify(exactly = 0) { MapSurfaceProvider.createFromMapView(any()) }
  }

  private val mapViewSurfaceCallback = slot<SurfaceHolder.Callback>()
  private val mapViewSurfaceHolder: SurfaceHolder = mockk(relaxed = true) {
    every { addCallback(capture(mapViewSurfaceCallback)) } just Runs
  }
  private val testMapView: MapView = mockk(relaxed = true) {
    every { rendererSurfaceView } returns mockk<SurfaceView>(relaxed = true) {
      every { holder } returns mapViewSurfaceHolder
    }
  }
  private val testMapViewDisplay: CarMapViewDisplay = mockk(relaxed = true) {
    every { mapView } returns testMapView
  }

  private fun mapViewOwner(options: MapInitOptions = mapInitOptions): CarMapSurfaceOwner {
    every { mapInitOptions.plugins } returns listOf(Plugin.Mapbox(Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID))
    every { MapSurfaceProvider.createMapViewDisplay(any(), any(), any()) } returns testMapViewDisplay
    every { MapSurfaceProvider.createFromMapView(testMapView) } returns testMapSurface
    val owner = CarMapSurfaceOwner(carMapGestures, MapboxCarMapType.MAP_VIEW)
    owner.setup(carContext, options)
    return owner
  }

  private fun mapViewSurfaceContainer(
    width: Int = 800,
    height: Int = 400,
  ): SurfaceContainer = mockk {
    every { this@mockk.width } returns width
    every { this@mockk.height } returns height
    every { surface } returns testSurface
  }
}