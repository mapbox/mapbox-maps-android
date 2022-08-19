@file:Suppress("NoMockkVerifyImport")

package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import android.view.Surface
import androidx.car.app.AppManager
import androidx.car.app.CarContext
import androidx.car.app.SurfaceContainer
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logI
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
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

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
  fun `surfaceDestroyed should stop and destroy map before notifying observers`() {
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
      testMapSurface.onStop()
      testMapSurface.surfaceDestroyed()
      testMapSurface.onDestroy()
      observer.onDetached(any())
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
}