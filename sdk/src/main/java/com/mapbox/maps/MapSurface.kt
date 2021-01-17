package com.mapbox.maps

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.Surface
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.renderer.MapboxSurfaceRenderer

/**
 * A [MapSurface] provides an embeddable map interface.
 * You use this class to display map information and to manipulate the map contents from your application.
 * You can center the map on a given coordinate, specify the size of the area you want to display,
 * and style the features of the map to fit your application's use case.
 *
 * Use of [MapSurface] requires a Mapbox API access token.
 * Obtain an access token on the [Mapbox account page](https://www.mapbox.com/studio/account/tokens/).
 *
 * <strong>Warning:</strong> Please note that you are responsible for getting permission to use the map data,
 * and for ensuring your use adheres to the relevant terms of use.
 */
class MapSurface : MapPluginProviderDelegate, MapControllable {

  private val mapboxMapOptions: MapboxMapOptions
  private val surface: Surface
  private val mapObserver: MapObserver
  private val mapController: MapController
  private val renderer: MapboxSurfaceRenderer

  constructor(
    context: Context,
    surface: Surface,
    mapObserver: MapObserver
  ) : this(MapboxMapOptions(context), surface, mapObserver)

  constructor(
    mapboxMapOptions: MapboxMapOptions,
    surface: Surface,
    mapObserver: MapObserver
  ) {
    this.mapboxMapOptions = mapboxMapOptions
    this.surface = surface
    this.mapObserver = mapObserver
    this.renderer = MapboxSurfaceRenderer()
    this.mapController = MapController(
      renderer,
      NativeMapObserver(Handler(Looper.getMainLooper())),
      mapboxMapOptions
    )
    mapController.initializePlugins(
      null,
      mapboxMapOptions.context,
      mapboxMapOptions.attrs,
      mapboxMapOptions.pixelRatio
    )
  }

  /**
   * Called when the surface has been created.
   */
  fun surfaceCreated() {
    renderer.surfaceCreated()
  }

  /**
   * Called when the surface dimensions have changed.
   *
   * @param width The width of the surface viewport
   * @param height The height of the surface viewport
   */
  fun surfaceChanged(width: Int, height: Int) {
    renderer.surfaceChanged(surface, width, height)
  }

  /**
   * Called when the surface
   */
  fun surfaceDestroyed() {
    renderer.surfaceDestroyed()
  }

  /**
   * Returns a [MapboxMap] object that can be used to interact with the map.
   *
   * @return [MapboxMap] object to interact with the map.
   */
  override fun getMapboxMap(): MapboxMap = mapController.getMapboxMap()

  /**
   * Called when a touch event has occurred.
   *
   * @param event the motion event that has occurred
   * @return True if event was handled, false otherwise
   */
  override fun onTouchEvent(event: MotionEvent): Boolean {
    return mapController.onTouchEvent(event)
  }

  /**
   * Called when a motion event has occurred.
   *
   * @param event the motion event that has occurred
   * @return True if event was handled, false otherwise
   */
  override fun onGenericMotionEvent(event: MotionEvent): Boolean {
    return mapController.onGenericMotionEvent(event)
  }

  /**
   * Called when the size has changed.
   *
   * @param w width of the viewport
   * @param h height of the viewport
   */
  override fun onSizeChanged(w: Int, h: Int) {
    mapController.onSizeChanged(w, h)
  }

  /**
   * Called to queue an event on the render thread.
   *
   * @param runnable the runnable to be queued
   */
  override fun queueEvent(runnable: Runnable) {
    mapController.queueEvent(runnable)
  }

  /**
   * Called to capture a snapshot synchronously.
   *
   * @return the snapshot
   */
  override fun snapshot(): Bitmap? = mapController.snapshot()

  /**
   * Called to capture a snapshot asynchronously.
   *
   * @param listener The listener to be invoked when snapshot finishes
   */
  override fun snapshot(listener: MapView.OnSnapshotReady) {
    mapController.snapshot(listener)
  }

  /**
   * Called to limit the maximum fps
   *
   * @param fps The maximum fps
   */
  override fun setMaximumFps(fps: Int) {
    if (fps <= 0) {
      return
    }
    renderer.setMaximumFps(fps)
  }

  /**
   * Called to resume rendering
   */
  override fun onStart() {
    mapController.onStart()
  }

  /**
   * Called to stop rendering
   */
  override fun onStop() {
    mapController.onStop()
  }

  /**
   * Called to dispose the renderer
   */
  override fun onDestroy() {
    mapController.onDestroy()
  }

  /**
   * Get the plugin instance.
   *
   * @param clazz the same class type that was used when instantiating the plugin
   * @return created plugin instance
   */
  override fun <T> getPlugin(clazz: Class<T>): T? {
    return mapController.getPlugin(clazz)
  }

  /**
   * Get the plugin instance
   *
   * @param className the name of the class that was used when instantiating the plugin
   * @return created plugin instance
   */
  override fun <T> getPlugin(className: String): T? {
    return mapController.getPlugin(className)
  }
}