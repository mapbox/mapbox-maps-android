package com.mapbox.maps.renderer

import android.view.Surface
import androidx.annotation.VisibleForTesting

internal open class MapboxSurfaceRenderer : MapboxRenderer {

  private var createSurface = false

  override val widgetRenderer: MapboxWidgetRenderer

  constructor(antialiasingSampleCount: Int) {
    widgetRenderer = MapboxWidgetRenderer(
      antialiasingSampleCount = antialiasingSampleCount,
    )
    renderThread = MapboxRenderThread(
      mapboxRenderer = this,
      mapboxWidgetRenderer = widgetRenderer,
      translucentSurface = false,
      antialiasingSampleCount = antialiasingSampleCount,
    )
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(renderThread: MapboxRenderThread) {
    widgetRenderer = MapboxWidgetRenderer(
      antialiasingSampleCount = 1,
    )
    this.renderThread = renderThread
  }

  fun surfaceChanged(surface: Surface, width: Int, height: Int) {
    if (createSurface) {
      renderThread.onSurfaceCreated(
        surface = surface,
        width = width,
        height = height
      )
      createSurface = false
    }
    renderThread.onSurfaceSizeChanged(
      width = width,
      height = height
    )
  }

  fun surfaceDestroyed() {
    renderThread.onSurfaceDestroyed()
  }

  fun surfaceCreated() {
    createSurface = true
  }
}