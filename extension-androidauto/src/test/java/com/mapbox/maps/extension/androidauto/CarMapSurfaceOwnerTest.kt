@file:Suppress("NoMockkVerifyImport")

package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.testing.ShadowLogger
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class CarMapSurfaceOwnerTest {

  private val carMapGestures = mockk<DefaultMapboxCarMapGestureHandler>(relaxed = true)
  private val carMapSurfaceOwner = CarMapSurfaceOwner(carMapGestures)

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
  fun `surfaceAvailable should notify observers that map is loaded`() {
    val firstObserver: MapboxCarMapObserver = mockk(relaxed = true)
    val secondObserver: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(firstObserver)
    carMapSurfaceOwner.registerObserver(secondObserver)

    val mapboxCarMapSurface: MapboxCarMapSurface = mockk(relaxed = true)
    carMapSurfaceOwner.surfaceAvailable(mapboxCarMapSurface)

    verify(exactly = 1) { firstObserver.onAttached(mapboxCarMapSurface) }
    verify(exactly = 1) { secondObserver.onAttached(mapboxCarMapSurface) }
  }

  @Test
  fun `surfaceAvailable should not notify visibleAreaChanged when visible area is null`() {
    val firstObserver: MapboxCarMapObserver = mockk(relaxed = true)
    val secondObserver: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(firstObserver)
    carMapSurfaceOwner.registerObserver(secondObserver)

    val mapboxCarMapSurface: MapboxCarMapSurface = mockk(relaxed = true)
    carMapSurfaceOwner.surfaceAvailable(mapboxCarMapSurface)

    verify(exactly = 0) { firstObserver.onVisibleAreaChanged(any(), any()) }
    verify(exactly = 0) { secondObserver.onVisibleAreaChanged(any(), any()) }
  }

  @Test
  fun `surfaceVisibleAreaChanged should notify visibleAreaChanged when surface is available`() {
    val firstObserver: MapboxCarMapObserver = mockk(relaxed = true)
    val secondObserver: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(firstObserver)
    carMapSurfaceOwner.registerObserver(secondObserver)

    val mapboxCarMapSurface: MapboxCarMapSurface = mockk {
      every { surfaceContainer } returns mockk {
        every { width } returns 800
        every { height } returns 400
      }
    }
    carMapSurfaceOwner.surfaceAvailable(mapboxCarMapSurface)
    val visibleRect: Rect = mockk(relaxed = true)
    carMapSurfaceOwner.surfaceVisibleAreaChanged(visibleRect)

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
    carMapSurfaceOwner.surfaceVisibleAreaChanged(visibleRect)

    verify(exactly = 0) { firstObserver.onVisibleAreaChanged(any(), any()) }
    verify(exactly = 0) { secondObserver.onVisibleAreaChanged(any(), any()) }
  }

  @Test
  fun `surfaceDestroyed should stop and destroy map before notifying observers`() {
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(observer)
    val testMapSurface = mockk<MapSurface>(relaxed = true)
    val mapboxCarMapSurface: MapboxCarMapSurface = mockk {
      every { surfaceContainer } returns mockk {
        every { width } returns 800
        every { height } returns 400
      }
      every { mapSurface } returns testMapSurface
    }

    carMapSurfaceOwner.surfaceAvailable(mapboxCarMapSurface)
    carMapSurfaceOwner.surfaceDestroyed()

    verifyOrder {
      testMapSurface.onStop()
      testMapSurface.surfaceDestroyed()
      testMapSurface.onDestroy()
      observer.onDetached(mapboxCarMapSurface)
    }
  }

  @Test
  fun `onDetached is called after mapboxCarMapSurface becomes null when surfaceDestroyed`() {
    carMapSurfaceOwner.registerObserver(object : MapboxCarMapObserver {
      override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
        assertNull(carMapSurfaceOwner.mapboxCarMapSurface)
      }
    })

    val testMapSurface = mockk<MapSurface>(relaxed = true)
    val mapboxCarMapSurface: MapboxCarMapSurface = mockk {
      every { surfaceContainer } returns mockk {
        every { width } returns 800
        every { height } returns 400
      }
      every { mapSurface } returns testMapSurface
    }

    carMapSurfaceOwner.surfaceAvailable(mapboxCarMapSurface)
    carMapSurfaceOwner.surfaceDestroyed()
  }

  @Test
  fun `onDetached is called after mapboxCarMapSurface becomes null when new surfaceAvailable`() {
    carMapSurfaceOwner.registerObserver(object : MapboxCarMapObserver {
      override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
        assertNotEquals(carMapSurfaceOwner.mapboxCarMapSurface, mapboxCarMapSurface)
      }
    })

    val firstMapSurface = mockk<MapSurface>(relaxed = true)
    val firstSurface = mockk<MapboxCarMapSurface> {
      every { surfaceContainer } returns mockk {
        every { width } returns 800
        every { height } returns 400
      }
      every { mapSurface } returns firstMapSurface
    }
    val secondSurface = mockk<MapboxCarMapSurface> {
      every { surfaceContainer } returns mockk {
        every { width } returns 800
        every { height } returns 400
      }
    }

    carMapSurfaceOwner.surfaceAvailable(firstSurface)
    carMapSurfaceOwner.surfaceVisibleAreaChanged(mockk(relaxed = true))
    carMapSurfaceOwner.surfaceAvailable(secondSurface)
  }

  @Test
  fun `should notify destroy and detached old surface when new surface is available`() {
    val observer: MapboxCarMapObserver = mockk(relaxed = true)
    carMapSurfaceOwner.registerObserver(observer)
    val firstMapSurface = mockk<MapSurface>(relaxed = true)
    val firstSurface = mockk<MapboxCarMapSurface> {
      every { surfaceContainer } returns mockk {
        every { width } returns 800
        every { height } returns 400
      }
      every { mapSurface } returns firstMapSurface
    }
    val secondSurface = mockk<MapboxCarMapSurface> {
      every { surfaceContainer } returns mockk {
        every { width } returns 800
        every { height } returns 400
      }
    }

    carMapSurfaceOwner.surfaceAvailable(firstSurface)
    carMapSurfaceOwner.surfaceVisibleAreaChanged(mockk(relaxed = true))
    carMapSurfaceOwner.surfaceAvailable(secondSurface)

    verifyOrder {
      observer.onAttached(firstSurface)
      observer.onVisibleAreaChanged(any(), any())
      observer.onDetached(firstSurface)
      observer.onAttached(secondSurface)
      observer.onVisibleAreaChanged(any(), any())
    }
    // Map style changes should not destroy the map.
    verify(exactly = 0) { firstMapSurface.onStop() }
    verify(exactly = 0) { firstMapSurface.surfaceDestroyed() }
    verify(exactly = 0) { firstMapSurface.onDestroy() }
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
    val mapboxCarMapSurface: MapboxCarMapSurface = mockk {
      every { surfaceContainer } returns mockk {
        every { width } returns 800
        every { height } returns 400
      }
    }

    carMapSurfaceOwner.surfaceAvailable(mapboxCarMapSurface)
    val visibleRect = Rect(30, 112, 779, 381)
    carMapSurfaceOwner.surfaceVisibleAreaChanged(visibleRect)

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
    val mapboxCarMapSurface: MapboxCarMapSurface = mockk {
      every { surfaceContainer } returns mockk {
        every { width } returns 805
        every { height } returns 405
      }
    }

    carMapSurfaceOwner.surfaceAvailable(mapboxCarMapSurface)
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
    val mapboxCarMapSurface: MapboxCarMapSurface = mockk {
      every { surfaceContainer } returns mockk {
        every { width } returns 805
        every { height } returns 405
      }
    }

    carMapSurfaceOwner.surfaceAvailable(mapboxCarMapSurface)
    val visibleRect = Rect(133, 112, 779, 381)
    carMapSurfaceOwner.surfaceVisibleAreaChanged(visibleRect)

    val visibleCenter = carMapSurfaceOwner.visibleCenter
    assertEquals(456.0, visibleCenter.x, 0.0001)
    assertEquals(246.5, visibleCenter.y, 0.0001)
  }

  @Test
  fun `gesture detector is not called before surface is ready`() {
    carMapSurfaceOwner.scroll(1f, 1f)
    carMapSurfaceOwner.fling(1f, 1f)
    carMapSurfaceOwner.scale(50f, 100f, 1f)

    verify(exactly = 0) { carMapGestures.onScale(any(), any(), any(), any()) }
    verify(exactly = 0) { carMapGestures.onFling(any(), any(), any()) }
    verify(exactly = 0) { carMapGestures.onScale(any(), any(), any(), any()) }
  }

  @Test
  fun `default gesture detector is called after surface is ready`() {
    carMapSurfaceOwner.surfaceAvailable(
      mockk {
        every { surfaceContainer } returns mockk {
          every { width } returns 805
          every { height } returns 405
        }
      }
    )

    carMapSurfaceOwner.scroll(1f, 1f)
    carMapSurfaceOwner.fling(1f, 1f)
    carMapSurfaceOwner.scale(50f, 100f, 1f)

    verify(exactly = 1) { carMapGestures.onScale(any(), any(), any(), any()) }
    verify(exactly = 1) { carMapGestures.onFling(any(), any(), any()) }
    verify(exactly = 1) { carMapGestures.onScale(any(), any(), any(), any()) }
  }
}