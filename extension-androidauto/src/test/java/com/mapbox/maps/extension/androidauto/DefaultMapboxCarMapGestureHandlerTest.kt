@file:Suppress("NoMockkVerifyImport")

package com.mapbox.maps.extension.androidauto

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapCenterAltitudeMode
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.logI
import com.mapbox.maps.plugin.animation.camera
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class DefaultMapboxCarMapGestureHandlerTest {

  private val surface = mockk<MapboxCarMapSurface>(relaxed = true) {
    every { mapSurface } returns mockk {
      every { mapboxMap } returns mockk {
        every { getCenterAltitudeMode() } returns MapCenterAltitudeMode.TERRAIN
        every { setCenterAltitudeMode(any()) } just runs
        every { setGestureInProgress(any()) } just runs
        every { cameraForDrag(any(), any()) } returns CameraOptions.Builder().build()
        every { setCamera(any<CameraOptions>()) } just runs
      }
      every { camera } returns mockk(relaxed = true)
    }
  }
  private val carMapGestures = DefaultMapboxCarMapGestureHandler()

  @Before
  fun `set up mocks`() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
  }

  @After
  fun cleanup() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun `onScroll will start and stop dragging`() {
    val center = ScreenCoordinate(450.0, 225.0)
    val mapboxMap = surface.mapSurface.mapboxMap

    carMapGestures.onScroll(surface, center, 3.0f, -3.0f)

    verifyOrder {
      mapboxMap.getCenterAltitudeMode()
      mapboxMap.getCenterAltitudeMode()
      mapboxMap.setGestureInProgress(true)
      mapboxMap.setCenterAltitudeMode(MapCenterAltitudeMode.SEA)
      mapboxMap.cameraForDrag(any(), any())
      mapboxMap.setCamera(any<CameraOptions>())
      mapboxMap.setCenterAltitudeMode(MapCenterAltitudeMode.TERRAIN)
      mapboxMap.setGestureInProgress(false)
    }
  }

  @Test
  fun `onScroll with center altitude mode set to SEA`() {
    val center = ScreenCoordinate(450.0, 225.0)
    val mapboxMap = surface.mapSurface.mapboxMap

    every { mapboxMap.getCenterAltitudeMode() } returns MapCenterAltitudeMode.SEA

    carMapGestures.onScroll(surface, center, 3.0f, -3.0f)

    verifyOrder {
      mapboxMap.getCenterAltitudeMode()
      mapboxMap.getCenterAltitudeMode()
      mapboxMap.setGestureInProgress(true)
      mapboxMap.cameraForDrag(any(), any())
      mapboxMap.setCamera(any<CameraOptions>())
      mapboxMap.setGestureInProgress(false)
    }
  }

  @Test
  fun `onScroll will move camera from visibleCenter to the delta distance`() {
    val fromCoordinateSlot = slot<ScreenCoordinate>()
    val toCoordinateSlot = slot<ScreenCoordinate>()
    val mapboxMap = surface.mapSurface.mapboxMap
    every {
      mapboxMap.cameraForDrag(capture(fromCoordinateSlot), capture(toCoordinateSlot))
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
    every { surface.mapSurface.mapboxMap.cameraState } returns mockk {
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
    val mapboxMap = surface.mapSurface.mapboxMap
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
    val mapboxMap = surface.mapSurface.mapboxMap
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
    val mapboxMap = surface.mapSurface.mapboxMap
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