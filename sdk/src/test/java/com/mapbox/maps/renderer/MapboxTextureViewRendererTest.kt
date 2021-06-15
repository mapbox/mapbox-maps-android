package com.mapbox.maps.renderer

import android.graphics.SurfaceTexture
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class MapboxTextureViewRendererTest : MapboxRendererTest() {

  private lateinit var textureViewRenderer: MapboxTextureViewRenderer

  @Before
  override fun setUp() {
    super.setUp()
    mapboxRenderer = MapboxTextureViewRenderer(renderThread)
    textureViewRenderer = MapboxTextureViewRenderer(renderThread)
  }

  @Test
  fun onSurfaceTextureSizeChangedTest() {
    textureViewRenderer.onSurfaceTextureAvailable(mockk(relaxed = true), 1, 1)
    verify { renderThread.onSurfaceCreated(any(), 1, 1) }
  }

  @Test
  fun onSurfaceTextureUpdatedTest() {
    assert(true)
  }

  @Test
  fun onSurfaceTextureDestroyedTest() {
    val surfaceTexture = mockk<SurfaceTexture>(relaxUnitFun = true)
    textureViewRenderer.onSurfaceTextureDestroyed(surfaceTexture)
    verify { renderThread.onSurfaceDestroyed() }
    verify { surfaceTexture.release() }
  }
}