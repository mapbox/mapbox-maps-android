package com.mapbox.maps

import android.content.Context
import android.graphics.Bitmap
import android.view.MotionEvent
import android.view.Surface
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.renderer.MapboxSurfaceRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.RendererSetupErrorListener
import com.mapbox.maps.renderer.widget.Widget

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
 *
 * @param context the application context to init the default [MapInitOptions]
 * @param surface the surface that will display map
 * @param mapInitOptions the init options to for map
 */
class MapSurface @JvmOverloads constructor(
  context: Context,
  val surface: Surface,
  mapInitOptions: MapInitOptions = MapInitOptions(context)
) : MapPluginProviderDelegate, MapControllable {

  private val mapController: MapController
  private val renderer: MapboxSurfaceRenderer = MapboxSurfaceRenderer(mapInitOptions.antialiasingSampleCount)

  init {
    this.mapController = MapController(
      renderer,
      mapInitOptions
    )
    mapController.initializePlugins(mapInitOptions)
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
   * Queue a runnable to be executed on the map renderer thread.
   * Consecutive events will be called in the order they were queued.
   *
   * @param event the runnable to queue
   * @param needRender if we should force redraw after running event (e.g. execute some GL commands)
   */
  override fun queueEvent(event: Runnable, needRender: Boolean) {
    mapController.queueEvent(event)
  }

  /**
   * Called to capture a snapshot synchronously.
   *
   * @return the snapshot
   */
  override fun snapshot(): Bitmap? = mapController.snapshot()

  /**
   * Called to capture a snapshot asynchronously.
   * Consecutive requests will return snapshots in the order they were added.
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
   * Set [OnFpsChangedListener] to get map rendering FPS.
   */
  override fun setOnFpsChangedListener(listener: OnFpsChangedListener) {
    renderer.setOnFpsChangedListener(listener)
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
   * Called to reduce memory use
   */
  override fun onLowMemory() {
    mapController.onLowMemory()
  }

  /**
   * Add [Widget] to the map.
   */
  @MapboxExperimental
  override fun addWidget(widget: Widget) {
    mapController.addWidget(widget)
  }

  /**
   * Remove [Widget] from the map.
   *
   * @return true if widget was present and removed, false otherwise
   */
  @MapboxExperimental
  override fun removeWidget(widget: Widget) = mapController.removeWidget(widget)

  /**
   * Add an instance of [RendererSetupErrorListener].
   *
   * Please note that errors could be already reported from the renderer during [MapView] creation
   * before this method will be called - all accumulated renderer errors will be delivered.
   */
  override fun addRendererSetupErrorListener(rendererSetupErrorListener: RendererSetupErrorListener) {
    mapController.addRendererSetupErrorListener(rendererSetupErrorListener)
  }

  /**
   * Remove an instance of [RendererSetupErrorListener].
   */
  override fun removeRendererSetupErrorListener(rendererSetupErrorListener: RendererSetupErrorListener) {
    mapController.removeRendererSetupErrorListener(rendererSetupErrorListener)
  }

  /**
   * Get the plugin instance.
   *
   * @param id plugin id
   * @return created plugin instance or null if no plugin is found for given id.
   */
  override fun <T : MapPlugin> getPlugin(id: String): T? = mapController.getPlugin(id)
}