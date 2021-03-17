package com.mapbox.maps.renderer

import android.view.Surface
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.Task

internal open class MapboxSurfaceRenderer : MapboxRenderer {

  private var createSurface = false

  constructor() {
    renderThread = MapboxRenderThread(
      mapboxRenderer = this,
      translucentSurface = false
    )
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(renderThread: MapboxRenderThread) {
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

  override fun onDestroy() {
    super.onDestroy()
    renderThread.destroy()
  }

  override fun scheduleTask(task: Task) {
    renderThread.queueEvent {
      task.run()
    }
  }
}