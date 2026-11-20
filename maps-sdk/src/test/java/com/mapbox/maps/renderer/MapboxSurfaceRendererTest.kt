package com.mapbox.maps.renderer

import android.view.Surface
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class MapboxSurfaceRendererTest : MapboxRendererTest() {

  private lateinit var surfaceRenderer: MapboxSurfaceRenderer
  private lateinit var surface: Surface

  @Before
  override fun setUp() {
    super.setUp()
    surface = mockk(relaxed = true)
    mapboxRenderer = MapboxSurfaceRenderer(renderThread)
    surfaceRenderer = MapboxSurfaceRenderer(renderThread)
  }

  @Test
  fun surfaceChangedTest() {
    surfaceRenderer.surfaceChanged(surface, 1, 1)
    verify { renderThread.onSurfaceSizeChanged(1, 1) }
  }

  @Test
  fun surfaceCreatedTest() {
    surfaceRenderer.surfaceCreated()
    surfaceRenderer.surfaceChanged(surface, 1, 1)
    verify {
      renderThread.onSurfaceCreated(surface, 1, 1)
      renderThread.onSurfaceSizeChanged(1, 1)
    }
  }

  @Test
  fun surfaceDestroyedTest() {
    surfaceRenderer.surfaceDestroyed()
    verify { renderThread.onSurfaceDestroyed() }
  }

  @Test
  fun surfaceAvailabilityReportedOnceWhenSurfaceIsCreated() {
    val reported = mutableListOf<Boolean>()
    surfaceRenderer.setSurfaceAvailabilityListener { reported += it }

    surfaceRenderer.surfaceCreated()
    surfaceRenderer.surfaceChanged(surface, 1, 1)
    surfaceRenderer.surfaceChanged(surface, 2, 2)

    assertEquals(listOf(true), reported)
  }

  @Test
  fun surfaceAvailabilityNotReportedWithoutSurfaceCreated() {
    val reported = mutableListOf<Boolean>()
    surfaceRenderer.setSurfaceAvailabilityListener { reported += it }

    surfaceRenderer.surfaceChanged(surface, 1, 1)

    assertEquals(emptyList<Boolean>(), reported)
  }

  @Test
  fun surfaceAvailabilityReportedWhenSurfaceIsDestroyed() {
    val reported = mutableListOf<Boolean>()
    surfaceRenderer.setSurfaceAvailabilityListener { reported += it }
    surfaceRenderer.surfaceCreated()
    surfaceRenderer.surfaceChanged(surface, 1, 1)

    surfaceRenderer.surfaceDestroyed()

    assertEquals(listOf(true, false), reported)
  }
}