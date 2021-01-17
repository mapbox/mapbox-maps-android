package com.mapbox.maps.renderer

import android.view.SurfaceHolder
import androidx.annotation.VisibleForTesting
import java.lang.ref.WeakReference

internal class MapboxSurfaceHolderRenderer : MapboxSurfaceRenderer, SurfaceHolder.Callback {

  private var surfaceHolder: WeakReference<SurfaceHolder>

  constructor(surfaceHolder: SurfaceHolder?) : super() {
    this.surfaceHolder = WeakReference(surfaceHolder)
    surfaceHolder?.addCallback(this)
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(surfaceHolder: SurfaceHolder?, renderThread: MapboxRenderThread) : super() {
    this.surfaceHolder = WeakReference(surfaceHolder)
  }

  override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    holder?.let {
      super.surfaceChanged(holder.surface, width, height)
    }
  }

  override fun surfaceDestroyed(holder: SurfaceHolder?) {
    super.surfaceDestroyed()
  }

  override fun surfaceCreated(holder: SurfaceHolder?) {
    super.surfaceCreated()
  }

  override fun onDestroy() {
    super.onDestroy()
    surfaceHolder.get()?.removeCallback(this)
  }
}