package com.mapbox.maps

import android.graphics.Bitmap
import android.view.MotionEvent
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.widget.Widget

/**
 * MapControllable interface is the gateway for public API to talk to the internal map controller.
 */
interface MapControllable : MapboxLifecycleObserver {

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
   * Add [Widget] to the map.
   */
  @MapboxExperimental
  fun addWidget(widget: Widget)

  /**
   * Remove [Widget] from the map.
   *
   * @return true if widget was present and removed, false otherwise
   */
  @MapboxExperimental
  fun removeWidget(widget: Widget): Boolean
}