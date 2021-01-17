package com.mapbox.maps.renderer

import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import androidx.annotation.VisibleForTesting
import java.lang.ref.WeakReference

internal class MapboxTextureViewRenderer : MapboxRenderer, TextureView.SurfaceTextureListener {

  private val textureView: WeakReference<TextureView>

  constructor(textureView: WeakReference<TextureView>) {
    this.textureView = textureView
    renderThread = MapboxRenderThread(
      mapboxRenderer = this,
      translucentSurface = true
    )
    textureView.get()?.let {
      it.isOpaque = false
      it.surfaceTextureListener = this
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(textureView: WeakReference<TextureView>, renderThread: MapboxRenderThread) {
    this.textureView = textureView
    this.renderThread = renderThread
  }

  override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
    renderThread.onSurfaceSizeChanged(width, height)
  }

  override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    // do nothing
  }

  override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
    renderThread.onSurfaceDestroyed()
    return true
  }

  override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture?, width: Int, height: Int) {
    renderThread.onSurfaceCreated(
      surface = Surface(surfaceTexture),
      width = width,
      height = height
    )
  }

  override fun onDestroy() {
    super.onDestroy()
    textureView.get()?.surfaceTextureListener = null
    renderThread.destroy()
  }
}