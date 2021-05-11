package com.mapbox.maps

import android.graphics.Bitmap
import android.view.MotionEvent
import com.mapbox.maps.renderer.OnFpsChangedListener

/**
 * MapControllable interface is the gateway for public API to talk to the internal map controller.
 */
interface MapControllable {

  /**
   * Returns a [MapboxMap] object that can be used to interact with the map.
   *
   * @return [MapboxMap] object to interact with the map.
   */
  fun getMapboxMap(): MapboxMap

  /**
   * Called when a touch event has occurred.
   *
   * @param event the motion event that has occurred
   * @return True if event was handled, false otherwise
   */
  fun onTouchEvent(event: MotionEvent): Boolean

  /**
   * Called when a motion event has occurred.
   *
   * @param event the motion event that has occurred
   * @return True if event was handled, false otherwise
   */
  fun onGenericMotionEvent(event: MotionEvent): Boolean

  /**
   * Called when the size has changed.
   *
   * @param w width of the viewport
   * @param h height of the viewport
   */
  fun onSizeChanged(w: Int, h: Int)

  /**
   * Render cache is introduced to store intermediate rendering results of tiles into cache textures
   * to be reused in future frames.
   * It's considerably smaller effort from the GPU to render quads with single texture lookups
   * rather than rendering geometries of individual layers and tiles separately.
   *
   * Using render cache may bring improvement to rendering performance
   * and reduce communication between CPU and GPU.
   *
   * @param cache [RenderCache] to apply to given [MapView].
   */
  @MapboxExperimental
  fun setRenderCache(cache: RenderCache)

  /**
   * Queue a runnable to be executed on the map renderer thread.
   *
   * @param event the runnable to queue
   * @param needRender if we should force redraw after running event (e.g. execute some GL commands)
   */
  fun queueEvent(event: Runnable, needRender: Boolean = true)

  /**
   * Called to capture a snapshot synchronously.
   *
   * @return the snapshot
   */
  fun snapshot(): Bitmap?

  /**
   * Called to capture a snapshot asynchronously.
   *
   * @param listener The listener to be invoked when snapshot finishes
   */
  fun snapshot(listener: MapView.OnSnapshotReady)

  /**
   * Called to limit the maximum fps
   *
   * @param fps The maximum fps
   */
  fun setMaximumFps(fps: Int)

  /**
   * Set [OnFpsChangedListener] to get map rendering FPS.
   */
  fun setOnFpsChangedListener(listener: OnFpsChangedListener)

  /**
   * Called to start rendering
   */
  fun onStart()

  /**
   * Called to stop rendering
   */
  fun onStop()

  /**
   * Called to dispose the renderer
   */
  fun onDestroy()
}