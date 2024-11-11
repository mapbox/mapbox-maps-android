package com.mapbox.maps.renderer

import android.view.SurfaceHolder
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class MapboxSurfaceHolderRendererTest : MapboxRendererTest() {

  private lateinit var surfaceHolderRenderer: MapboxSurfaceHolderRenderer
  private lateinit var surfaceHolder: SurfaceHolder

  @Before
  override fun setUp() {
    super.setUp()
    surfaceHolder = mockk(relaxed = true)
    mapboxRenderer = MapboxSurfaceHolderRenderer(renderThread)
    surfaceHolderRenderer = MapboxSurfaceHolderRenderer(renderThread)
  }

  @Test
  fun surfaceChangedTest() {
    surfaceHolderRenderer.surfaceChanged(surfaceHolder, 0, 1, 1)
    verify { renderThread.onSurfaceSizeChanged(1, 1) }
  }

  @Test
  fun surfaceCreatedTest() {
    surfaceHolderRenderer.surfaceCreated(surfaceHolder)
    surfaceHolderRenderer.surfaceChanged(surfaceHolder, 0, 1, 1)
    verify {
      renderThread.onSurfaceCreated(surfaceHolder.surface, 1, 1)
      renderThread.onSurfaceSizeChanged(1, 1)
    }
  }

  @Test
  fun surfaceDestroyedTest() {
    surfaceHolderRenderer.surfaceDestroyed(surfaceHolder)
    verify { renderThread.onSurfaceDestroyed() }
  }
}