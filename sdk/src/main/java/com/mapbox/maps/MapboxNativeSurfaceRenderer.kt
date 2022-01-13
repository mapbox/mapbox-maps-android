package com.mapbox.maps

import android.app.Activity
import android.graphics.Bitmap
import android.view.Surface
import android.view.SurfaceHolder
import androidx.annotation.Keep
import com.mapbox.maps.renderer.MapboxRendererInterface
import com.mapbox.maps.renderer.OnFpsChangedListener

@Keep
internal class MapboxNativeSurfaceRenderer(
  activity: Activity,
  surfaceHolder: SurfaceHolder
): SurfaceHolder.Callback, MapboxRendererInterface {

  init {
    initialize(activity)
    surfaceHolder.addCallback(this)
  }

  @Suppress("unused")
  @Keep
  @Volatile
  var peer: Long = 0

  @Keep
  private external fun initialize(activity: Activity)

  @Throws(Throwable::class)
  private external fun finalize()

  @Keep
  private external fun nativeSetSurface(surface: Surface?, width: Int, height: Int)

  @Keep
  private external fun nativeCreateMap(
    mapOptions: MapOptions,
    resourceOptions: ResourceOptions
  ): Map

  fun createMap(mapInitOptions: MapInitOptions): Map {
    return nativeCreateMap(
      mapInitOptions.mapOptions,
      mapInitOptions.resourceOptions
    )
  }

  override fun surfaceCreated(holder: SurfaceHolder) {
    // do nothing
  }

  override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    nativeSetSurface(holder.surface, width, height)
  }

  override fun surfaceDestroyed(holder: SurfaceHolder) {
    nativeSetSurface(null, 0, 0)
  }

  override fun onStart() {
    
  }

  override fun onStop() {
    
  }

  override fun onDestroy() {
    
  }

  override fun queueRenderEvent(runnable: Runnable) {
    
  }

  override fun queueEvent(runnable: Runnable) {
    
  }

  override fun snapshot(): Bitmap? {
    return null
  }

  override fun snapshot(listener: MapView.OnSnapshotReady) {
    
  }

  override fun setMaximumFps(fps: Int) {
    
  }

  override fun setOnFpsChangedListener(listener: OnFpsChangedListener) {
    
  }
}