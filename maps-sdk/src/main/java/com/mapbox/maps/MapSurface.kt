package com.mapbox.maps

import android.content.Context
import android.graphics.Bitmap
import android.view.MotionEvent
import android.view.Surface
import android.view.WindowManager
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.renderer.*
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
 */
class MapSurface : MapPluginProviderDelegate, MapControllable {

  private val context: Context
  private val mapController: MapController
  private val mapInitOptions: MapInitOptions
  private val renderer: MapboxSurfaceRenderer

  /**
   * The surface to be used, set from the constructor.
   */
  val surface: Surface

  /**
   * @param context the application context to init the default [MapInitOptions]
   * @param surface the surface that will display map
   * @param mapInitOptions the init options for map
   */
  @JvmOverloads constructor(
    context: Context,
    surface: Surface,
    mapInitOptions: MapInitOptions = MapInitOptions(context) // could use strong ref here as MapInitOptions have strong ref in any case
  ) {
    this.context = context
    this.surface = surface
    this.mapInitOptions = mapInitOptions
    this.renderer = MapboxSurfaceRenderer(
      mapInitOptions.antialiasingSampleCount,
      mapInitOptions.mapOptions.contextMode ?: ContextMode.UNIQUE,
      mapInitOptions.mapName
    )
    this.mapController = MapController(renderer, mapInitOptions).apply {
      initializePlugins(mapInitOptions)
    }
  }

  /**
   * @param context the application context to init the default [MapInitOptions]
   * @param surface the surface that will display map
   * @param mapInitOptions the init options for map
   * @param renderer the MapboxSurfaceRenderer instance to be used
   * @param mapController the MapController instance to be used
   */
  @VisibleForTesting(otherwise = PRIVATE)
  internal constructor(
    context: Context,
    surface: Surface,
    mapInitOptions: MapInitOptions,
    renderer: MapboxSurfaceRenderer,
    mapController: MapController
  ) {
    this.context = context
    this.surface = surface
    this.mapInitOptions = mapInitOptions
    this.renderer = renderer
    this.mapController = mapController
  }

  /**
   * Must be called when the surface has been created.
   */
  fun surfaceCreated() {
    renderer.surfaceCreated()
    // display should not be null at this point but to be sure we will fallback to DEFAULT_FPS
    @Suppress("DEPRECATION")
    val screenRefreshRate = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?)
      ?.defaultDisplay?.refreshRate?.toInt() ?: MapView.DEFAULT_FPS
    mapController.setScreenRefreshRate(screenRefreshRate)
  }

  /**
   * Must be called when the surface dimensions have changed.
   *
   * @param width The width of the surface viewport
   * @param height The height of the surface viewport
   */
  fun surfaceChanged(width: Int, height: Int) {
    renderer.surfaceChanged(surface, width, height)
    onSizeChanged(width, height)
  }

  /**
   * Must be called when the surface is destroyed.
   */
  fun surfaceDestroyed() {
    renderer.surfaceDestroyed()
  }

  /**
   * Returns a [MapboxMap] object that can be used to interact with the map.
   *
   * @return [MapboxMap] object to interact with the map.
   */
  override val mapboxMap: MapboxMap
    get() = mapController.mapboxMap

  /**
   * Returns a [MapboxMap] object that can be used to interact with the map.
   *
   * @return [MapboxMap] object to interact with the map.
   */
  @Deprecated(
    "This method is deprecated, and will be removed in next major release. Use [mapboxMap] property instead.",
    replaceWith = ReplaceWith("mapboxMap")
  )
  // Hide it from Java. They will use [mapboxMap] property getter above. Moreover, mangle the name
  // in Java to avoid "platform declaration clash".
  @JvmSynthetic @JvmName("getMapboxMapDeprecated")
  fun getMapboxMap(): MapboxMap = mapboxMap

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
   *
   * @throws RuntimeException when trying to add the Widget to the [MapSurface] that does not
   * have [MapOptions.contextMode] = [ContextMode.SHARED] as part of [MapInitOptions].
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