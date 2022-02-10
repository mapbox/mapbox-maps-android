package com.mapbox.maps.extension.androidauto

import androidx.car.app.AppManager
import androidx.car.app.CarContext
import androidx.car.app.SurfaceCallback
import com.mapbox.common.Logger
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenCoordinate
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(MapboxExperimental::class)
class MapboxCarMapTest {

  private val testMapSurface: MapSurface = mockk(relaxed = true)

  @Before
  fun setup() {
    mockkStatic(Logger::class)
    every { Logger.i(any(), any()) } just Runs
    mockkObject(MapSurfaceProvider)
    every { MapSurfaceProvider.create(any(), any(), any()) } returns testMapSurface
  }

  @After
  fun teardown() {
    unmockkAll()
  }

  @Test(expected = RuntimeException::class)
  fun `MapboxCarMap constructor crashes when context is not a CarContext`() {
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk()
    }

    MapboxCarMap(mapInitOptions)
  }

  @Test
  fun `MapboxCarMap constructor requests the surface callback`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    MapboxCarMap(mapInitOptions)

    assertTrue(surfaceCallbackSlot.isCaptured)
  }

  @Test
  fun `carMapSurface is valid after onSurfaceAvailable`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    val mapboxCarMap = MapboxCarMap(mapInitOptions)
    assertNull(mapboxCarMap.carMapSurface)
    surfaceCallbackSlot.captured.onSurfaceAvailable(mockk(relaxed = true))

    assertNotNull(mapboxCarMap.carMapSurface)
  }

  @Test
  fun `visibleArea is valid after onVisibleAreaChanged`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    val mapboxCarMap = MapboxCarMap(mapInitOptions)
    assertNull(mapboxCarMap.visibleArea)
    surfaceCallbackSlot.captured.onSurfaceAvailable(mockk(relaxed = true))
    surfaceCallbackSlot.captured.onVisibleAreaChanged(mockk(relaxed = true))

    assertNotNull(mapboxCarMap.visibleArea)
  }

  @Test
  fun `edgeInsets is valid after onVisibleAreaChanged`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    val mapboxCarMap = MapboxCarMap(mapInitOptions)
    assertNull(mapboxCarMap.edgeInsets)
    surfaceCallbackSlot.captured.onSurfaceAvailable(mockk(relaxed = true))
    surfaceCallbackSlot.captured.onVisibleAreaChanged(mockk(relaxed = true))

    assertNotNull(mapboxCarMap.edgeInsets)
  }

  @Test
  fun `registerObserver includes onAttached and onVisibleAreaChanged`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    val mapboxCarMap = MapboxCarMap(mapInitOptions)
    val observer = mockk<MapboxCarMapObserver>(relaxed = true)
    mapboxCarMap.registerObserver(observer)
    surfaceCallbackSlot.captured.onSurfaceAvailable(mockk(relaxed = true))
    surfaceCallbackSlot.captured.onVisibleAreaChanged(mockk(relaxed = true))

    verifyOrder {
      observer.onAttached(any())
      observer.onVisibleAreaChanged(any(), any())
    }
  }

  @Test
  fun `registerObserver receives callbacks if registered after onVisibleAreaChanged`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    val mapboxCarMap = MapboxCarMap(mapInitOptions)
    surfaceCallbackSlot.captured.onSurfaceAvailable(mockk(relaxed = true))
    surfaceCallbackSlot.captured.onVisibleAreaChanged(mockk(relaxed = true))

    val observer = mockk<MapboxCarMapObserver>(relaxed = true)
    mapboxCarMap.registerObserver(observer)

    verifyOrder {
      observer.onAttached(any())
      observer.onVisibleAreaChanged(any(), any())
    }
  }

  @Test
  fun `unregisterObserver will prevent callbacks`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    val mapboxCarMap = MapboxCarMap(mapInitOptions)
    val observer = mockk<MapboxCarMapObserver>(relaxed = true)
    mapboxCarMap.registerObserver(observer)
    mapboxCarMap.unregisterObserver(observer)

    surfaceCallbackSlot.captured.onSurfaceAvailable(mockk(relaxed = true))
    surfaceCallbackSlot.captured.onVisibleAreaChanged(mockk(relaxed = true))

    verify(exactly = 0) { observer.onAttached(any()) }
    verify(exactly = 0) { observer.onVisibleAreaChanged(any(), any()) }
    verify(exactly = 0) { observer.onDetached(any()) }
  }

  @Test
  fun `clearObservers will prevent callbacks`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    val mapboxCarMap = MapboxCarMap(mapInitOptions)
    val observer = mockk<MapboxCarMapObserver>(relaxed = true)
    mapboxCarMap.registerObserver(observer)
    mapboxCarMap.clearObservers()

    surfaceCallbackSlot.captured.onSurfaceAvailable(mockk(relaxed = true))
    surfaceCallbackSlot.captured.onVisibleAreaChanged(mockk(relaxed = true))

    verify(exactly = 0) { observer.onAttached(any()) }
    verify(exactly = 0) { observer.onVisibleAreaChanged(any(), any()) }
    verify(exactly = 0) { observer.onDetached(any()) }
  }

  @Test
  fun `setGestures allows you to provide custom gestures`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    val mapboxCarMap = MapboxCarMap(mapInitOptions)
    val testGestures = TestMapboxCarMapGestures()
    mapboxCarMap.setGestureHandler(testGestures)

    surfaceCallbackSlot.captured.onSurfaceAvailable(mockk(relaxed = true))
    surfaceCallbackSlot.captured.onVisibleAreaChanged(mockk(relaxed = true))
    surfaceCallbackSlot.captured.onScroll(0.0f, 0.0f)
    surfaceCallbackSlot.captured.onFling(0.0f, 0.0f)
    surfaceCallbackSlot.captured.onScale(0.0f, 0.0f, 0.0f)

    assertTrue(testGestures.capturedOnScroll)
    assertTrue(testGestures.capturedOnFling)
    assertTrue(testGestures.capturedOnScale)
  }

  @Test
  fun `setGestures is not called before surface is available`() {
    val surfaceCallbackSlot = slot<SurfaceCallback>()
    val mapInitOptions = mockk<MapInitOptions> {
      every { context } returns mockk<CarContext> {
        every { getCarService(AppManager::class.java) } returns mockk {
          every { setSurfaceCallback(capture(surfaceCallbackSlot)) } just Runs
        }
      }
    }

    val mapboxCarMap = MapboxCarMap(mapInitOptions)
    val testGestures = TestMapboxCarMapGestures()
    mapboxCarMap.setGestureHandler(testGestures)

    surfaceCallbackSlot.captured.onScroll(0.0f, 0.0f)
    surfaceCallbackSlot.captured.onFling(0.0f, 0.0f)
    surfaceCallbackSlot.captured.onScale(0.0f, 0.0f, 0.0f)
    surfaceCallbackSlot.captured.onSurfaceAvailable(mockk(relaxed = true))
    surfaceCallbackSlot.captured.onVisibleAreaChanged(mockk(relaxed = true))

    assertFalse(testGestures.capturedOnScroll)
    assertFalse(testGestures.capturedOnFling)
    assertFalse(testGestures.capturedOnScale)
  }

  class TestMapboxCarMapGestures : MapboxCarMapGestureHandler {
    var capturedOnScroll = false
    var capturedOnScale = false
    var capturedOnFling = false

    override fun onScroll(
      mapboxCarMapSurface: MapboxCarMapSurface,
      visibleCenter: ScreenCoordinate,
      distanceX: Float,
      distanceY: Float
    ) {
      capturedOnScroll = true
    }

    override fun onFling(
      mapboxCarMapSurface: MapboxCarMapSurface,
      velocityX: Float,
      velocityY: Float
    ) {
      capturedOnFling = true
    }

    override fun onScale(
      mapboxCarMapSurface: MapboxCarMapSurface,
      focusX: Float,
      focusY: Float,
      scaleFactor: Float
    ) {
      capturedOnScale = true
    }
  }
}