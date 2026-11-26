package com.mapbox.maps

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.view.MotionEvent
import android.view.Surface
import android.view.SurfaceView
import android.view.WindowManager
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import com.mapbox.common.MapboxThread
import com.mapbox.common.mapboxThreadProvider
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.renderer.*
import com.mapbox.maps.renderer.widget.Widget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

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

  private val mapboxThread: MapboxThread
  val context: Context

  @get:JvmSynthetic
  internal lateinit var mapController: MapController
    private set
  private lateinit var renderer: MapboxSurfaceRenderer
  private val mapView: MapView?
  private var displayRefreshRateMonitor: DisplayRefreshRateMonitor? = null

  /**
   * Cancelled in [onDestroy], tying [mapScope] and [mapImmediateScope] to this [MapSurface]'s
   * lifecycle instead of [mapboxThread]'s (which outlives any single [MapSurface]).
   */
  private val mapSurfaceJob = SupervisorJob()

  /**
   * Coroutine scope that dispatches onto whichever thread Mapbox is configured to run on: the
   * main thread by default, but a dedicated background thread if the app configures a
   * [MapboxThreadProvider][com.mapbox.common.MapboxThreadProvider] such as
   * `OneMapboxThreadProvider`. Cancelled in [onDestroy].
   *
   * Wrap only the calls that actually touch Mapbox map/style APIs (e.g. [mapboxMap], plugin
   * calls) in this scope. Do not wrap unrelated main-thread-only Android framework calls in it
   * (e.g. `Lifecycle.addObserver`, other work that requires the real main thread regardless of
   * how Mapbox is configured) — those need the main thread specifically, not "whatever thread
   * Mapbox happens to be using," and belong outside this scope, called directly or via
   * `Dispatchers.Main`.
   */
  @MapboxExperimental
  @MapboxDelicateApi
  val mapScope: CoroutineScope

  /**
   * Like [mapScope], but using [MapboxThread.dispatcher][com.mapbox.common.MapboxThread.dispatcher]'s `immediate` variant:
   * runs inline when already on the right thread, instead of always re-dispatching. See
   * [mapScope] for what belongs (and doesn't belong) in this scope.
   */
  @MapboxExperimental
  @MapboxDelicateApi
  val mapImmediateScope: CoroutineScope

  /**
   * The [MainCoroutineDispatcher] backing [mapScope]: always posts to whichever thread Mapbox is
   * configured to run on, even when already called from it. Exposed for code that wants to
   * `withContext` onto that thread rather than launch a separate coroutine via [mapScope]. Use
   * [immediate][MainCoroutineDispatcher.immediate] to run inline when already on that thread,
   * matching [mapImmediateScope].
   */
  @MapboxExperimental
  @MapboxDelicateApi
  val dispatcher: MainCoroutineDispatcher

  @Suppress("PrivatePropertyName")
  private var TAG: String = "MapSurface"

  /**
   * The surface to be used, set from the constructor.
   */
  val surface: Surface

  /**
   * @param context a visual context (Activity, WindowContext, or DisplayContext) used to initialise the default [MapInitOptions]. On API 30+, a non-visual context (e.g. applicationContext) disables VRR/refresh-rate monitoring and locks the map to 60 fps on high-refresh-rate displays.
   * @param surface the surface that will display map
   * @param mapInitOptions the init options for map
   */
  @JvmOverloads constructor(
    context: Context,
    surface: Surface,
    mapInitOptions: MapInitOptions = MapInitOptions(context), // could use strong ref here as MapInitOptions have strong ref in any case
  ) {
    this.mapboxThread = mapboxThreadProvider.getOrCreate()
    this.mapScope = CoroutineScope(mapSurfaceJob + mapboxThread.dispatcher)
    this.mapImmediateScope = CoroutineScope(mapSurfaceJob + mapboxThread.dispatcher.immediate)
    this.dispatcher = mapboxThread.dispatcher

    this.context = context
    this.surface = surface
    this.mapView = null
    val mapName = mapInitOptions.mapName.ifBlank { "${System.identityHashCode(this)}" }
    TAG = "MapSurface\\$mapName"
    // The renderer, map controller and plugins are created on the Mapbox thread, where the map
    // runs. For now the constructor waits for them so that the MapSurface is complete when it
    // returns: mapboxMap, getPlugin() and the surface callbacks all go through mapController.
    mapboxThread.runAndWait {
      renderer = MapboxSurfaceRenderer(
        antialiasingSampleCount = mapInitOptions.antialiasingSampleCount,
        contextMode = mapInitOptions.mapOptions.contextMode ?: ContextMode.UNIQUE,
        mapName = mapName,
        mapboxThread = mapboxThread,
      )
      val threadChecker = MapThreadChecker(mapboxThread.handler.looper)
      mapController =
        MapController(renderer, mapInitOptions, mapName, mapboxThread, threadChecker).apply {
          initializePlugins(mapInitOptions)
        }
    }
  }

  /**
   * Provides access to an existing [MapView] without creating another map or renderer.
   *
   * The view must use a [SurfaceView] whose surface has already been created. The [MapView]
   * remains the sole owner of surface callbacks and lifecycle events; calling [surfaceCreated],
   * [surfaceChanged], [surfaceDestroyed], [onStart], [onStop], [onResume], or [onDestroy] on this
   * facade throws [IllegalStateException].
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  @OptIn(MapboxExperimental::class, MapboxDelicateApi::class)
  constructor(mapView: MapView) {
    // Share the MapView's thread and scopes: the MapView owns the lifecycle, so this facade never
    // cancels anything in onDestroy.
    this.mapboxThread = mapView.mapboxThread
    this.mapScope = mapView.mapScope
    this.mapImmediateScope = mapView.mapImmediateScope
    this.dispatcher = mapView.dispatcher
    val surfaceView = mapView.rendererSurfaceView
      ?: error("MapView must use SurfaceView mode to provide a MapSurface facade")
    check(surfaceView.holder.surface.isValid) {
      "MapView's surface must be created before providing a MapSurface facade"
    }
    this.context = mapView.context
    this.surface = surfaceView.holder.surface
    this.mapController = mapView.mapController
    this.renderer = mapController.renderer as? MapboxSurfaceRenderer
      ?: error("MapView must use a surface renderer to provide a MapSurface facade")
    this.mapView = mapView
  }

  /**
   * @param context a visual context (Activity, WindowContext, or DisplayContext) used to initialise the default [MapInitOptions]. On API 30+, a non-visual context (e.g. applicationContext) disables VRR/refresh-rate monitoring and locks the map to 60 fps on high-refresh-rate displays.
   * @param surface the surface that will display map
   * @param renderer the MapboxSurfaceRenderer instance to be used
   * @param mapController the MapController instance to be used
   */
  @VisibleForTesting(otherwise = PRIVATE)
  internal constructor(
    context: Context,
    surface: Surface,
    renderer: MapboxSurfaceRenderer,
    mapController: MapController
  ) {
    this.mapboxThread = mapboxThreadProvider.getOrCreate()
    this.mapScope = CoroutineScope(mapSurfaceJob + mapboxThread.dispatcher)
    this.mapImmediateScope = CoroutineScope(mapSurfaceJob + mapboxThread.dispatcher.immediate)
    this.dispatcher = mapboxThread.dispatcher
    this.context = context
    this.surface = surface
    this.mapView = null
    this.renderer = renderer
    this.mapController = mapController
  }

  /**
   * Must be called when the surface has been created.
   */
  @OptIn(MapboxDelicateApi::class, MapboxExperimental::class)
  fun surfaceCreated() {
    checkOwnsLifecycle()
    logI(TAG, "surfaceCreated() called")
    renderer.surfaceCreated()
    // Set default refresh rate immediately to ensure map controller has a valid value
    mapController.setScreenRefreshRate(MapView.DEFAULT_FPS)
    // Retrieve screen refresh rate off the main thread to prevent ANR
    mapImmediateScope.launch {
      val rate = safeBinderCall(fallback = MapView.DEFAULT_FPS, logTag = TAG) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
          context.display.refreshRate.toInt()
        } else {
          @Suppress("DEPRECATION")
          (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?)
            ?.defaultDisplay?.refreshRate?.toInt() ?: MapView.DEFAULT_FPS
        }
      }
      mapController.setScreenRefreshRate(rate)
    }

    // Stop any previous display refresh rate monitor in case `surfaceCreated` is called twice
    stopDisplayRefreshRateMonitor()
    // Subscribe to live refresh-rate changes (VRR mode switches, per-UID frameRateOverride).
    displayRefreshRateMonitor = DisplayRefreshRateMonitor(
      context = context,
      onRefreshRateChanged = mapController::setScreenRefreshRate,
    ).also { it.start() }
  }

  /**
   * Must be called when the surface dimensions have changed.
   *
   * @param width The width of the surface viewport
   * @param height The height of the surface viewport
   */
  fun surfaceChanged(width: Int, height: Int) {
    checkOwnsLifecycle()
    renderer.surfaceChanged(surface, width, height)
    onSizeChanged(width, height)
  }

  /**
   * Must be called when the surface is destroyed.
   */
  fun surfaceDestroyed() {
    checkOwnsLifecycle()
    logI(TAG, "surfaceDestroyed() called")
    stopDisplayRefreshRateMonitor()
    renderer.surfaceDestroyed()
  }

  private fun stopDisplayRefreshRateMonitor() {
    displayRefreshRateMonitor?.stop()
    displayRefreshRateMonitor = null
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
   * Call it on the Mapbox thread, for example from [mapImmediateScope]; with the default
   * `MapboxThreadProvider` that is the main thread.
   *
   * @param fps The maximum fps
   */
  override fun setMaximumFps(fps: Int) {
    mapController.setMaximumFps(fps)
  }

  /**
   * The currently configured maximum FPS, or null if [setMaximumFps] was never called
   * (or was followed by [clearMaximumFps]).
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  val maximumFps: Int?
    get() = mapController.maximumFps

  /**
   * Reset to "no cap" — equivalent to never having called [setMaximumFps].
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  fun clearMaximumFps() {
    mapController.clearMaximumFps()
  }

  /**
   * Register a callback invoked when [setMaximumFps] or [clearMaximumFps] is called.
   * Fires synchronously on the Mapbox thread.
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  fun addOnMaximumFpsChangedListener(listener: OnMaximumFpsChangedListener) {
    mapController.addOnMaximumFpsChangedListener(listener)
  }

  /**
   * Unregister a callback previously registered via [addOnMaximumFpsChangedListener].
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  fun removeOnMaximumFpsChangedListener(listener: OnMaximumFpsChangedListener) {
    mapController.removeOnMaximumFpsChangedListener(listener)
  }

  /**
   * Set [OnFpsChangedListener] to get map rendering FPS.
   */
  override fun setOnFpsChangedListener(listener: OnFpsChangedListener) {
    renderer.setOnFpsChangedListener(listener)
  }

  /**
   * The [RenderThreadStatsRecorder] of this map. Call [RenderThreadStatsRecorder.start] and
   * [RenderThreadStatsRecorder.end] on it to record frame stats.
   */
  @MapboxExperimental
  override val renderThreadStatsRecorder: RenderThreadStatsRecorder
    get() = mapController.renderThreadStatsRecorder

  /**
   * Called to resume rendering
   */
  override fun onStart() {
    checkOwnsLifecycle()
    mapController.onStart()
  }

  /**
   * Called to stop rendering
   */
  override fun onStop() {
    checkOwnsLifecycle()
    mapController.onStop()
  }

  /**
   * Called when the activity is resumed
   */
  override fun onResume() {
    checkOwnsLifecycle()
    mapController.onResume()
  }

  /**
   * Called to dispose the renderer
   */
  override fun onDestroy() {
    checkOwnsLifecycle()
    mapController.onDestroy()
    mapSurfaceJob.cancel()
  }

  private fun checkOwnsLifecycle() {
    check(mapView == null) { "The backing MapView owns surface callbacks and lifecycle events" }
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
   * Please note that errors could be already reported from the renderer during [MapSurface] creation
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
   * Create a new plugin instance that will be added to the map.
   * Only one instance of [Plugin.instance] with given [Plugin.id] can exist for given [MapSurface].
   *
   * @param plugin instance of [Plugin] that will be added to the [MapSurface].
   */
  fun createPlugin(
    plugin: Plugin
  ) = mapController.createPlugin(mapView, plugin)

  /**
   * Remove the plugin instance that will be removed from the map.
   *
   * Removing the plugin from [MapSurface] will result in [MapPlugin.cleanup] being called and existing
   * events subscription cancelled.
   *
   * @param id the id of the plugin to be removed
   */
  fun removePlugin(id: String) = mapController.removePlugin(id)
  /**
   * Get the plugin instance.
   *
   * @param id plugin id
   * @return created plugin instance or null if no plugin is found for given id.
   */
  override fun <T : MapPlugin> getPlugin(id: String): T? = mapController.getPlugin(id)
}