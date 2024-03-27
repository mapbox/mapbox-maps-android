package com.mapbox.maps.renderer

import android.view.SurfaceHolder
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.ContextMode

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class MapboxSurfaceHolderRenderer : MapboxSurfaceRenderer, SurfaceHolder.Callback {

  constructor(
    surfaceHolder: SurfaceHolder,
    antialiasingSampleCount: Int,
    contextMode: ContextMode,
    mapName: String,
  ) : super(antialiasingSampleCount, contextMode, mapName) {
    surfaceHolder.addCallback(this)
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(renderThread: MapboxRenderThread) : super(renderThread)

  override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    super.surfaceChanged(holder.surface, width, height)
  }

  override fun surfaceDestroyed(holder: SurfaceHolder) {
    super.surfaceDestroyed()
  }

  override fun surfaceCreated(holder: SurfaceHolder) {
    super.surfaceCreated()
  }
}