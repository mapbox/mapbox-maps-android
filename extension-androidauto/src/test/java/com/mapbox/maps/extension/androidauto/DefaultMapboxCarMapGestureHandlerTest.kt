@file:Suppress("NoMockkVerifyImport")

package com.mapbox.maps.extension.androidauto

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.androidauto.testing.ShadowLogger
import com.mapbox.maps.plugin.animation.camera
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verifyOrder
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class DefaultMapboxCarMapGestureHandlerTest {

  private val surface = mockk<MapboxCarMapSurface>(relaxed = true) {
    every { mapSurface } returns mockk {
      every { getMapboxMap() } returns mockk(relaxed = true)
      every { camera } returns mockk(relaxed = true)
    }
  }
  private val carMapGestures = DefaultMapboxCarMapGestureHandler()

  @Test
  fun `onScroll will start and stop dragging`() {
    val center = ScreenCoordinate(450.0, 225.0)
    val mapboxMap = surface.mapSurface.getMapboxMap()

    carMapGestures.onScroll(surface, center, 3.0f, -3.0f)

    verifyOrder {
      mapboxMap.dragStart(ScreenCoordinate(450.0, 225.0))
      mapboxMap.setCamera(any<CameraOptions>())
      mapboxMap.dragEnd()
    }
  }

  @Test
  fun `onScroll will move camera from visibleCenter to the delta distance`() {
    val fromCoordinateSlot = slot<ScreenCoordinate>()
    val toCoordinateSlot = slot<ScreenCoordinate>()
    val mapboxMap = surface.mapSurface.getMapboxMap()
    every {
      mapboxMap.getDragCameraOptions(capture(fromCoordinateSlot), capture(toCoordinateSlot))
    } returns mockk(relaxed = true)

    val center = ScreenCoordinate(450.0, 225.0)
    carMapGestures.onScroll(surface, center, 3.3f, -3.3f)

    assertEquals(450.0, fromCoordinateSlot.captured.x, 0.0001)
    assertEquals(225.0, fromCoordinateSlot.captured.y, 0.0001)
    assertEquals(446.7, toCoordinateSlot.captured.x, 0.0001)
    assertEquals(228.3, toCoordinateSlot.captured.y, 0.0001)
  }

  @Test
  fun `onScale double-tap-gesture will easeTo new zoom`() {
    every { surface.mapSurface.getMapboxMap().cameraState } returns mockk {
      every { zoom } returns 10.0
    }
    val cameraOptionsSlot = slot<CameraOptions>()
    every {
      surface.mapSurface.camera.easeTo(capture(cameraOptionsSlot))
    } returns mockk(relaxed = true)

    carMapGestures.onScale(surface, 526.0f, 260.0f, 2.0f)

    with(cameraOptionsSlot.captured) {
      assertEquals(526.0, anchor!!.x, 0.0001)
      assertEquals(260.0, anchor!!.y, 0.0001)
      assertEquals(11.0, zoom!!, 0.0001)
    }
  }

  @Test
  fun `onScale will use the focus as the anchor point`() {
    val mapboxMap = surface.mapSurface.getMapboxMap()
    every { mapboxMap.cameraState } returns mockk {
      every { zoom } returns 16.50
    }
    val cameraOptionsSlot = slot<CameraOptions>()
    every {
      mapboxMap.setCamera(capture(cameraOptionsSlot))
    } returns mockk(relaxed = true)

    carMapGestures.onScale(surface, 224.4f, 117.1f, 0.98f)

    with(cameraOptionsSlot.captured.anchor!!) {
      assertEquals(224.4, x, 0.0001)
      assertEquals(117.1, y, 0.0001)
    }
  }

  @Test
  fun `onScale with factor less than one will zoom out`() {
    val expectedFromZoom = 16.50
    val expectedToZoom = 16.48
    val mapboxMap = surface.mapSurface.getMapboxMap()
    every { mapboxMap.cameraState } returns mockk {
      every { zoom } returns expectedFromZoom
    }
    val cameraOptionsSlot = slot<CameraOptions>()
    every {
      mapboxMap.setCamera(capture(cameraOptionsSlot))
    } returns mockk(relaxed = true)

    carMapGestures.onScale(surface, 400.0f, 200.0f, 0.98f)

    assertEquals(expectedToZoom, cameraOptionsSlot.captured.zoom!!, 0.0001)
  }

  @Test
  fun `onScale with factor greater than one will zoom in`() {
    val expectedFromZoom = 16.50
    val expectedToZoom = 16.52
    val mapboxMap = surface.mapSurface.getMapboxMap()
    every { mapboxMap.cameraState } returns mockk {
      every { zoom } returns expectedFromZoom
    }
    val cameraOptionsSlot = slot<CameraOptions>()
    every {
      mapboxMap.setCamera(capture(cameraOptionsSlot))
    } returns mockk(relaxed = true)

    carMapGestures.onScale(surface, 400.0f, 200.0f, 1.02f)

    assertEquals(expectedToZoom, cameraOptionsSlot.captured.zoom!!, 0.0001)
  }
}