package com.mapbox.maps.renderer

import android.graphics.Bitmap
import com.mapbox.maps.MapClient
import com.mapbox.maps.MapInterface
import com.mapbox.maps.MapView

internal interface MapboxRendererInterface {

  /**
   * Triggered when Activity.onStart or Fragment.onStart is called by Android.
   */
  fun onStart()

  /**
   * Triggered when Activity.onStop or Fragment.onStop is called by Android.
   */
  fun onStop()

  /**
   * Triggered when Activity.onDestroy or Fragment.onDestroy is called by Android.
   */
  fun onDestroy()

  /**
   * Queue user's event on the render thread. Runnable will be executed and buffers will be swapped afterwards.
   */
  fun queueRenderEvent(runnable: Runnable)

  /**
   * Queue user's event on the render thread. Runnable will be executed but buffers will not be swapped afterwards.
   */
  fun queueEvent(runnable: Runnable)

  /**
   * Take current map snapshot by calling glReadPixels. This method is synchronous.
   */
  fun snapshot(): Bitmap?

  /**
   * Take current map snapshot by calling glReadPixels. This method is asynchronous.
   */
  fun snapshot(listener: MapView.OnSnapshotReady)

  /**
   * Set maximum FPS limit we want to render with.
   */
  fun setMaximumFps(fps: Int)

  /**
   * Set FPS change callback listener.
   */
  fun setOnFpsChangedListener(listener: OnFpsChangedListener)
}