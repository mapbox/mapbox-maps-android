package com.mapbox.maps.renderer

import android.os.Build
import android.view.Surface
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O])
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
  fun onDestroyTest() {
    surfaceRenderer.onDestroy()
    verify { renderThread.destroy() }
  }
}