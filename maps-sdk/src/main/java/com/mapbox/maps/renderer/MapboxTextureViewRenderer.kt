package com.mapbox.maps.renderer

import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.ContextMode
import com.mapbox.maps.RenderBackendType

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class MapboxTextureViewRenderer : MapboxRenderer, TextureView.SurfaceTextureListener {

  override val widgetRenderer: MapboxWidgetRenderer

  constructor(
    textureView: TextureView,
    antialiasingSampleCount: Int,
    contextMode: ContextMode,
    mapName: String,
  ) : super(mapName) {
    val widgetRenderer = MapboxWidgetRenderer(
      antialiasingSampleCount = antialiasingSampleCount,
      mapName = mapName,
    )
    this.widgetRenderer = widgetRenderer
    renderThread = when (supportedRenderBackend) {
      RenderBackendType.VULKAN -> VulkanMapboxRenderThread(
        mapboxRenderer = this,
        antialiasingSampleCount = antialiasingSampleCount,
        mapName = mapName,
      )
      RenderBackendType.OPEN_GL -> GLMapboxRenderThread(
        mapboxRenderer = this,
        mapboxWidgetRenderer = widgetRenderer,
        translucentSurface = true,
        antialiasingSampleCount = antialiasingSampleCount,
        contextMode = contextMode,
        mapName = mapName,
      )
    }
    textureView.let {
      it.isOpaque = false
      it.surfaceTextureListener = this
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(renderThread: MapboxRenderThread) : super(mapName = "") {
    val widgetRenderer = MapboxWidgetRenderer(
      antialiasingSampleCount = 1,
      mapName = "",
    )
    this.widgetRenderer = widgetRenderer
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