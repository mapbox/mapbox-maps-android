package com.mapbox.maps.renderer

import android.view.Surface
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.ContextMode

internal open class MapboxSurfaceRenderer : MapboxRenderer {

  private var createSurface = false

  override val widgetRenderer: MapboxWidgetRenderer

  constructor(
    antialiasingSampleCount: Int,
    contextMode: ContextMode,
    mapName: String,
  ) : super(mapName) {
    widgetRenderer = MapboxWidgetRenderer(
      antialiasingSampleCount = antialiasingSampleCount,
      mapName = mapName,
    )
    renderThread = MapboxRenderThread(
      mapboxRenderer = this,
      mapboxWidgetRenderer = widgetRenderer,
      translucentSurface = false,
      antialiasingSampleCount = antialiasingSampleCount,
      contextMode = contextMode,
      mapName = mapName,
    )
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(renderThread: MapboxRenderThread) : super(mapName = "") {
    widgetRenderer = MapboxWidgetRenderer(
      antialiasingSampleCount = 1,
      mapName = "",
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