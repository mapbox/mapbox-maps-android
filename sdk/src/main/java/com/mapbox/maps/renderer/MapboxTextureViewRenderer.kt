package com.mapbox.maps.renderer

import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.renderer.egl.AntialiasingConfig

internal class MapboxTextureViewRenderer : MapboxRenderer, TextureView.SurfaceTextureListener {

  constructor(textureView: TextureView, antialiasingConfig: AntialiasingConfig) {
    renderThread = MapboxRenderThread(
      mapboxRenderer = this,
      translucentSurface = true,
      antialiasingConfig = antialiasingConfig,
    )
    textureView.let {
      it.isOpaque = false
      it.surfaceTextureListener = this
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(renderThread: MapboxRenderThread) {
    this.renderThread = renderThread
  }

  override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
    renderThread.onSurfaceSizeChanged(width, height)
  }

  override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
    // do nothing
  }

  override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
    renderThread.onSurfaceDestroyed()
    surface.release()
    return true
  }

  override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
    renderThread.onSurfaceCreated(
      surface = Surface(surfaceTexture),
      width = width,
      height = height
    )
  }
}