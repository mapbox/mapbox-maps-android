package com.mapbox.maps.renderer

import android.view.TextureView
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.lang.ref.WeakReference

internal class MapboxTextureViewRendererTest : MapboxRendererTest() {

  private lateinit var textureViewRenderer: MapboxTextureViewRenderer

  @Before
  override fun setUp() {
    super.setUp()
    val weakView = WeakReference(mockk<TextureView>(relaxed = true))
    mapboxRenderer = MapboxTextureViewRenderer(weakView, renderThread)
    textureViewRenderer = MapboxTextureViewRenderer(weakView, renderThread)
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
    textureViewRenderer.onSurfaceTextureDestroyed(mockk())
    verify { renderThread.onSurfaceDestroyed() }
  }

  @Test
  fun onDestroyTest() {
    textureViewRenderer.onDestroy()
    verify { renderThread.destroy() }
  }
}