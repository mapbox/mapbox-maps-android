package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import androidx.car.app.AppManager
import androidx.car.app.CarContext
import androidx.car.app.SurfaceCallback
import com.mapbox.common.Logger
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(MapboxExperimental::class)
class CarMapSurfaceCallbackTest {

  private val carContext: CarContext = mockk(relaxed = true)
  private val carMapSurfaceOwner: CarMapSurfaceOwner = mockk()
  private val mapboxCarOptions: MapInitOptions = mockk(relaxed = true)
  private val testMapSurface: MapSurface = mockk(relaxed = true)

  private val carMapSurfaceCallback = CarMapSurfaceCallback(
    carContext,
    carMapSurfaceOwner,
    mapboxCarOptions
  )

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

  @Test
  fun `onCreate should request the map surface with the SurfaceCallback`() {
    val surfaceCallback = slot<SurfaceCallback>()
    every { carContext.getCarService(AppManager::class.java) } returns mockk {
      every { setSurfaceCallback(capture(surfaceCallback)) } just Runs
    }

    carMapSurfaceCallback.onBind()

    assertTrue(surfaceCallback.isCaptured)
    assertEquals(surfaceCallback.captured, carMapSurfaceCallback)
  }

  @Test
  fun `onSurfaceAvailable should notify surfaceAvailable when style is loaded`() {
    every { testMapSurface.getMapboxMap() } returns mockk(relaxed = true) {
      every { loadStyleUri(any(), onStyleLoaded = any(), any()) } answers {
        secondArg<Style.OnStyleLoaded>().onStyleLoaded(mockk())
      }
    }
    val carMapSurfaceSlot = slot<MapboxCarMapSurface>()
    every { carMapSurfaceOwner.surfaceAvailable(capture(carMapSurfaceSlot)) } just Runs

    carMapSurfaceCallback.onSurfaceAvailable(
      mockk {
        every { surface } returns mockk()
        every { width } returns 800
        every { height } returns 400
      }
    )

    verifyOrder {
      testMapSurface.surfaceChanged(800, 400)
      carMapSurfaceOwner.surfaceAvailable(any())
    }
  }

  @Test
  fun `onVisibleAreaChanged should notify carMapSurfaceOwner surfaceVisibleAreaChanged`() {
    val visibleRect = mockk<Rect>()
    every { carMapSurfaceOwner.surfaceVisibleAreaChanged(any()) } just Runs

    carMapSurfaceCallback.onVisibleAreaChanged(visibleRect)

    verify(exactly = 1) { carMapSurfaceOwner.surfaceVisibleAreaChanged(visibleRect) }
  }

  @Test
  fun `onStableAreaChanged should not do anything`() {
    carMapSurfaceCallback.onStableAreaChanged(mockk())

    verify(exactly = 0) { carMapSurfaceOwner.surfaceVisibleAreaChanged(any()) }
    verify(exactly = 0) { carMapSurfaceOwner.surfaceDestroyed() }
  }

  @Test
  fun `onSurfaceDestroyed should notify carMapSurfaceOwner surfaceDestroyed`() {
    every { carMapSurfaceOwner.surfaceDestroyed() } just Runs

    carMapSurfaceCallback.onSurfaceDestroyed(mockk())

    verify(exactly = 1) { carMapSurfaceOwner.surfaceDestroyed() }
  }
}