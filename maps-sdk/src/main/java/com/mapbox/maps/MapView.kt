package com.mapbox.maps

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.TextureView
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import androidx.core.view.children
import com.mapbox.common.MainMapboxThreadProvider
import com.mapbox.common.MapboxThread
import com.mapbox.common.MapboxTracing
import com.mapbox.common.mapboxThreadProvider
import com.mapbox.common.runOrDispatch
import com.mapbox.maps.debugoptions.DebugOptionsController
import com.mapbox.maps.debugoptions.MapViewDebugOptions
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.renderer.DisplayRefreshRateMonitor
import com.mapbox.maps.renderer.MapboxSurfaceHolderRenderer
import com.mapbox.maps.renderer.MapboxTextureViewRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.OnMaximumFpsChangedListener
import com.mapbox.maps.renderer.RenderThreadStatsRecorder
import com.mapbox.maps.renderer.RendererSetupErrorListener
import com.mapbox.maps.renderer.egl.EGLCore
import com.mapbox.maps.renderer.widget.Widget
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationManagerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.nio.IntBuffer
import kotlin.math.abs
import kotlin.math.max

/**
 * A [MapView] provides an embeddable map interface.
 * You use this class to display map information and to manipulate the map contents from your application.
 * You can center the map on a given coordinate, specify the size of the area you want to display,
 * and style the features of the map to fit your application's use case.
 *
 * Use of [MapView] requires a Mapbox API access token.
 * Obtain an access token on the [Mapbox account page](https://www.mapbox.com/studio/account/tokens/).
 *
 * <strong>Warning:</strong> Please note that you are responsible for getting permission to use the map data,
 * and for ensuring your use adheres to the relevant terms of use.
 */
open class MapView : FrameLayout, MapPluginProviderDelegate, MapControllable {
  @get:JvmSynthetic
  internal lateinit var mapController: MapController
    private set
  private val debugOptionsControllerDelegate = lazy {
    DebugOptionsController(
      this
    )
  }
  private val debugOptionsController: DebugOptionsController by debugOptionsControllerDelegate
  private var interceptedViewAnnotationEvents: MutableList<MotionEvent> = mutableListOf()
  private val touchSlop: Int by lazy { ViewConfiguration.get(context).scaledTouchSlop }
  private val gestureRouter = ViewAnnotationGestureRouter(this)
  private var displayRefreshRateMonitor: DisplayRefreshRateMonitor? = null
  private val viewAnnotationManagerDelegate = lazy {
    ViewAnnotationManagerImpl(this).also {
      it.subviewDebugFrames = MapViewDebugOptions.COLLISION in debugOptions
    }
  }

  @Suppress("PrivatePropertyName")
  private var TAG: String = "MapView"
  /**
   * Get view annotation manager instance to add / update / remove view annotations
   * represented as Android views.
   */
  val viewAnnotationManager: ViewAnnotationManager by viewAnnotationManagerDelegate

  internal val isViewAnnotationManagerInitialized: Boolean
    get() = viewAnnotationManagerDelegate.isInitialized()

  /**
   * [Set] of debug options for the current [MapView] and its native [MapboxMap].
   */
  var debugOptions: Set<MapViewDebugOptions>
    get() = debugOptionsController.options
    set(value) {
      debugOptionsController.options = value
      if (viewAnnotationManagerDelegate.isInitialized()) {
        (viewAnnotationManager as ViewAnnotationManagerImpl).subviewDebugFrames =
          MapViewDebugOptions.COLLISION in value
      }
    }

  @MapboxExperimental
  @MapboxDelicateApi
  internal val mapboxThread: MapboxThread

  /**
   * Cancelled in [onDestroy], tying [mapScope] and [mapImmediateScope] to this [MapView]'s
   * lifecycle instead of [mapboxThread]'s (which outlives any single [MapView]).
   */
  private val mapViewJob = SupervisorJob()

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

  /**
   * Build a [MapView] with [Context] and [MapInitOptions] objects.
   *
   * @param context a visual context (Activity, WindowContext, or DisplayContext) used to
   * initialise the default [MapInitOptions]. On API 30+, a non-visual context
   * (e.g. applicationContext) disables VRR/refresh-rate monitoring and locks
   * the map to 60 fps on high-refresh-rate displays.
   * @param mapInitOptions the init options for map
   */
  @JvmOverloads
  constructor(context: Context, mapInitOptions: MapInitOptions = MapInitOptions(context)) : this(
    context,
    null,
    0,
    0,
    mapInitOptions
  )

  /**
   * Build a [MapView] with [Context] and [AttributeSet] objects.
   *
   * @param context a visual context (Activity, WindowContext, or DisplayContext). On API 30+,
   * a non-visual context (e.g. applicationContext) disables VRR/refresh-rate monitoring and
   * locks the map to 60 fps on high-refresh-rate displays.
   * @param attrs the attribute set to initialise the [MapView] with
   */
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  /**
   * Build a [MapView] with a [Context] object, a [AttributeSet] object, and
   * an [Int] which represents a style resource file.
   *
   * @param context a visual context (Activity, WindowContext, or DisplayContext). On API 30+,
   * a non-visual context (e.g. applicationContext) disables VRR/refresh-rate monitoring and
   * locks the map to 60 fps on high-refresh-rate displays.
   * @param attrs the attribute set to initialise the [MapView] with
   * @param defStyleAttr the default style attribute resource to apply to this [MapView]
   */
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
    context,
    attrs,
    defStyleAttr,
    0,
    null
  )

  /**
   * Build a [MapView] with a [Context] object, a [MapInitOptions] object,
   * a [AttributeSet] object, an [Int] which represents a style attribute file,
   * and an [Int] which represents a style resource file.
   */
  @OptIn(
    MapboxDelicateApi::class, MapboxExperimental::class,
    com.mapbox.annotation.MapboxExperimental::class
  )
  private constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int,
    initOptions: MapInitOptions?,
  ) : super(context, attrs, defStyleAttr, defStyleRes) {
    val resolvedMapInitOptions = if (attrs != null) {
      parseTypedArray(context, attrs)
    } else {
      initOptions ?: MapInitOptions(context)
    }
    // Layout Preview doesn't run the Mapbox startup initializers, so mapboxThreadProvider isn't
    // set there. The preview map is never created, so the main thread serves as a placeholder.
    mapboxThread = if (isInEditMode) {
      MainMapboxThreadProvider().getOrCreate()
    } else {
      mapboxThreadProvider.getOrCreate()
    }

    mapScope = CoroutineScope(mapViewJob + mapboxThread.dispatcher)
    mapImmediateScope = CoroutineScope(mapViewJob + mapboxThread.dispatcher.immediate)
    dispatcher = mapboxThread.dispatcher
    MapboxTracing.traceSync("$MAPS_SDK_TRACE_PREFIX MapView") {
      val mapName = resolvedMapInitOptions.mapName.ifBlank { "${System.identityHashCode(this)}" }
      TAG = "MapView\\$mapName"
      if (isInEditMode) {
        return
      }
      val view = if (resolvedMapInitOptions.textureView) {
        TextureView(context, attrs)
      } else {
        SurfaceView(context, attrs)
      }
      val contextMode = resolvedMapInitOptions.mapOptions.contextMode ?: ContextMode.UNIQUE
      // The renderer, map controller and plugins are created on the Mapbox thread, where the map
      // runs. For now the constructor waits for them so that the MapView is complete when it
      // returns: mapboxMap, getPlugin() and the view callbacks all go through mapController.
      mapboxThread.runAndWait {
        val renderer = MapboxTracing.traceSync({ "$MAPS_SDK_TRACE_PREFIX renderer($mapName)" }) {
          when (view) {
            is SurfaceView -> MapboxSurfaceHolderRenderer(
              view.holder,
              resolvedMapInitOptions.antialiasingSampleCount,
              contextMode,
              mapName,
              mapboxThread,
            )

            is TextureView -> MapboxTextureViewRenderer(
              view,
              resolvedMapInitOptions.antialiasingSampleCount,
              contextMode,
              mapName,
              mapboxThread,
            )

            else -> throw IllegalArgumentException("Provided view has to be a texture or a surface.")
          }
        }
        mapController = MapController(
          renderer,
          resolvedMapInitOptions,
          mapName,
          mapboxThread = mapboxThread,
          threadChecker = MapThreadChecker(mapboxThread.handler.looper),
        )
        mapController.initializePlugins(resolvedMapInitOptions, this@MapView)
      }
      addView(view, 0)
    }
  }

  /**
   * Adds [view] on the main thread: right away when called there, which is always the case with
   * the default Mapbox thread, and dispatched to the main thread otherwise.
   */
  internal fun addViewOnMainThread(view: View, index: Int = -1) {
    Dispatchers.Main.runOrDispatch { addView(view, index) }
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    if (!isInEditMode) {
      mapController.onAttachedToWindow(this@MapView)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        // The platform reports the visibility of a view that is attached as hidden only when it
        // changes, so read the initial value here.
        mapController.setHostVisible(isShown && windowVisibility == VISIBLE)
      }
    }
  }

  @SuppressLint("CustomViewStyleable")
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  @JvmSynthetic
  internal fun parseTypedArray(context: Context, attrs: AttributeSet?): MapInitOptions {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      val mapOptions =
        MapAttributeParser.parseMapOptions(typedArray, context.resources.displayMetrics.density)
      val cameraOptions = CameraAttributeParser.parseCameraOptions(typedArray)
      val textureView = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapSurface, 0) != 0
      val styleUri =
        typedArray.getString(R.styleable.mapbox_MapView_mapbox_styleUri) ?: Style.STANDARD
      val antialiasingSampleCount = typedArray.getInteger(
        R.styleable.mapbox_MapView_mapbox_mapAntialiasingSampleCount,
        DEFAULT_ANTIALIASING_SAMPLE_COUNT
      )
      val mapName = typedArray.getString(R.styleable.mapbox_MapView_mapbox_mapName) ?: ""

      return MapInitOptions(
        context,
        mapOptions,
        styleUri = if (styleUri.isEmpty()) {
          null
        } else {
          styleUri
        },
        attrs = attrs,
        antialiasingSampleCount = antialiasingSampleCount,
        mapName = mapName
      ).also {
        it.cameraOptions = cameraOptions
        it.textureView = textureView
      }
    } finally {
      typedArray.recycle()
    }
  }

  @OptIn(MapboxDelicateApi::class, MapboxExperimental::class)
  private constructor(
    context: Context,
    attrs: AttributeSet?,
    mapController: MapController,
  ) : super(context, attrs, 0, 0) {
    this.mapController = mapController
    mapboxThread = mapboxThreadProvider.getOrCreate()
    mapScope = CoroutineScope(mapViewJob + mapboxThread.dispatcher)
    mapImmediateScope = CoroutineScope(mapViewJob + mapboxThread.dispatcher.immediate)
    dispatcher = mapboxThread.dispatcher
  }

  /**
   * Called when the size of the MapView has changed.
   */
  public override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    if (w != oldw || h != oldh) {
      onSizeChanged(w, h)
    }
  }

  /**
   * Called when the size of the MapView has changed
   */
  override fun onSizeChanged(w: Int, h: Int) {
    if (!isInEditMode) {
      mapController.onSizeChanged(w, h)
    }
  }

  /**
   * You must call this method from the parent's Activity#onStart() or Fragment#onStart()
   * @see android.app.Activity.onStart
   * @see android.app.Fragment.onStart
   */
  @OptIn(MapboxDelicateApi::class, MapboxExperimental::class)
  override fun onStart() {
    logI(TAG, "onStart() called")
    // Set default refresh rate immediately to ensure map controller has a valid value
    mapController.setScreenRefreshRate(DEFAULT_FPS)
    // Retrieve screen refresh rate off the main thread to prevent ANR
    mapImmediateScope.launch {
      val rate = safeBinderCall(fallback = DEFAULT_FPS, logTag = TAG) {
        display?.refreshRate?.toInt() ?: DEFAULT_FPS
      }
      mapController.setScreenRefreshRate(rate)
    }

    // Stop any previous display refresh rate monitor in case `onStart` is called twice
    stopDisplayRefreshRateMonitor()
    // Subscribe to live refresh-rate changes (VRR mode switches, per-UID frameRateOverride).
    displayRefreshRateMonitor = DisplayRefreshRateMonitor(
      context = context,
      onRefreshRateChanged = mapController::setScreenRefreshRate,
    ).also { it.start() }

    mapController.onStart()
    mapImmediateScope.launch {
      if (debugOptionsControllerDelegate.isInitialized()) {
        debugOptionsController.started = true
      }
    }
  }

  /**
   * You must call this method from the parent's Activity#onStop() or Fragment#onStop()
   * @see android.app.Activity.onStop
   * @see android.app.Fragment.onStop
   */
  override fun onStop() {
    logI(TAG, "onStop() called")
    stopDisplayRefreshRateMonitor()
    mapController.onStop()
    mapImmediateScope.launch {
      if (debugOptionsControllerDelegate.isInitialized()) {
        debugOptionsController.started = false
      }
    }
  }

  private fun stopDisplayRefreshRateMonitor() {
    displayRefreshRateMonitor?.stop()
    displayRefreshRateMonitor = null
  }

  /**
   * You must call this method from the parent's Activity#onResume() or Fragment#onResume()
   * @see android.app.Activity.onResume
   * @see android.app.Fragment.onResume
   */
  override fun onResume() {
    logI(TAG, "onResume() called")
    mapController.onResume()
  }

  /**
   * You must call this method from the parent's Activity#onLowMemory() or Fragment#onLowMemory()
   * @see android.app.Activity.onLowMemory
   * @see android.app.Fragment.onLowMemory
   */
  override fun onLowMemory() {
    logI(TAG, "onLowMemory() called")
    mapController.onLowMemory()
  }

  /**
   * You must call this method from the parent's Activity#onDestroy() or Fragment#onDestroy()
   */
  @OptIn(MapboxDelicateApi::class, MapboxExperimental::class)
  override fun onDestroy() {
    logI(TAG, "onDestroy() called")
    if (viewAnnotationManagerDelegate.isInitialized()) {
      (viewAnnotationManager as ViewAnnotationManagerImpl).destroy()
    }
    mapController.onDestroy()
    mapViewJob.cancel()
  }

  /**
   * Returns a [MapboxMap] object that can be used to interact with the map.
   *
   * Note: keeping the reference to an invalid [MapboxMap] instance introduces significant native memory leak,
   * see [MapboxMap.isValid] for more details.
   *
   * @return [MapboxMap] object to interact with the map.
   */
  override val mapboxMap
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
  @JvmSynthetic
  @JvmName("getMapboxMapDeprecated")
  fun getMapboxMap(): MapboxMap = mapboxMap

  /**
   * Queue a runnable to be executed on the map renderer thread.
   * Consecutive events will be called in the order they were queued.
   *
   * @param event the runnable to queue
   * @param needRender if we should force redraw after running event (e.g. execute some GL commands)
   */
  override fun queueEvent(event: Runnable, needRender: Boolean) {
    mapController.queueEvent(event, needRender)
  }

  /**
   * Perform snapshot of current [MapView] state.
   * This method is synchronous so it blocks calling thread until [Bitmap] is ready.
   *
   * @return [Bitmap] representing current map state or null if map is not ready yet.
   */
  override fun snapshot() = mapController.snapshot()

  /**
   * Perform snapshot of current [MapView] state with notifying listener about it.
   * This method is asynchronous so it does not block calling thread.
   * Consecutive requests will return snapshots in the order they were added.
   *
   * [OnSnapshotReady.onSnapshotReady] is called from non UI thread.
   *
   * @param listener instance of [OnSnapshotReady] callback
   */
  override fun snapshot(listener: OnSnapshotReady) {
    mapController.snapshot(listener)
  }

  /**
   * Set whether legacy mode should be used for [snapshot].
   *
   * Legacy mode is not that efficient (as it blocks map rendering when making the snapshot)
   * but may help with vendor specific issues like described in
   * https://github.com/mapbox/mapbox-maps-android/issues/2280.
   *
   * @param enabled enables legacy mode when True and disables otherwise.
   */
  @MapboxExperimental
  fun setSnapshotLegacyMode(enabled: Boolean) {
    mapController.renderer.snapshotLegacyModeEnabled = enabled
  }

  /**
   * Set new maximum FPS for map rendering.
   *
   * Call it on the Mapbox thread, for example from [mapImmediateScope]; with the default
   * `MapboxThreadProvider` that is the main thread.
   *
   * @param fps new maximum FPS value that must be greater than 0 and less than max integer.
   * When setting this value higher than screen physically supports - max possible screen FPS rate will be used.
   */
  override fun setMaximumFps(@IntRange(from = 1, to = Int.MAX_VALUE.toLong()) fps: Int) {
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
   * The [SurfaceView] used by the map renderer, or null when this map uses a [TextureView].
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  val rendererSurfaceView: SurfaceView?
    get() = children.filterIsInstance<SurfaceView>().firstOrNull()

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

  @RequiresApi(Build.VERSION_CODES.N)
  override fun onVisibilityAggregated(isVisible: Boolean) {
    super.onVisibilityAggregated(isVisible)
    if (!isInEditMode) {
      mapController.setHostVisible(isVisible)
    }
  }

  /**
   * Create a new plugin instance that will be added to the map.
   * Only one instance of [Plugin.instance] with given [Plugin.id] can exist for given [MapView].
   *
   * @param plugin instance of [Plugin] that will be added to the [MapView].
   */
  fun createPlugin(
    plugin: Plugin
  ) = mapController.createPlugin(this, plugin)

  /**
   * Remove the plugin instance that will be removed from the map.
   *
   * Removing the plugin from [MapView] will result in [MapPlugin.cleanup] being called and existing
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

  /**
   * Called when a touch event has occurred.
   *
   * @return true to indicate the touch event has been handled
   */
  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    var interceptedTouchRes = false
    if (interceptedViewAnnotationEvents.isNotEmpty() && interceptedViewAnnotationEvents.none { it.eventTime == event.eventTime }) {
      interceptedViewAnnotationEvents.forEach {
        interceptedTouchRes = mapController.onTouchEvent(it) || interceptedTouchRes
        it.recycle()
      }
    }
    interceptedViewAnnotationEvents.clear()
    return mapController.onTouchEvent(event) || interceptedTouchRes
  }

  /**
   * Called whenever a touch event is detected on the surface of a ViewGroup,
   * including on the surface of its children
   *
   * @param event the motion event that has occurred
   * @return True if event was intercepted, false otherwise
   */
  override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
    return when (event.actionMasked) {
      MotionEvent.ACTION_DOWN -> {
        recycleAndClearInterceptedViewAnnotationEvents()
        gestureRouter.onDown(event)
        interceptedViewAnnotationEvents.add(MotionEvent.obtain(event))
        false
      }

      MotionEvent.ACTION_POINTER_DOWN -> {
        interceptedViewAnnotationEvents.add(MotionEvent.obtain(event))
        false
      }

      MotionEvent.ACTION_MOVE -> {
        val crossedSlop = isCrossedSlop(event)
        gestureRouter.shouldInterceptMove(crossedSlop)
      }

      MotionEvent.ACTION_UP,
      MotionEvent.ACTION_CANCEL -> {
        recycleAndClearInterceptedViewAnnotationEvents()
        return false
      }

      MotionEvent.ACTION_POINTER_UP -> {
        val upPointerId = event.getPointerId(event.actionIndex)
        interceptedViewAnnotationEvents.removeAll {
          upPointerId == it.getPointerId(it.actionIndex)
        }

        return false
      }

      else -> {
        // In general, we don't want to intercept touch events. They should be
        // handled by the child view.
        false
      }
    }
  }

  private fun recycleAndClearInterceptedViewAnnotationEvents() {
    interceptedViewAnnotationEvents.forEach { it.recycle() }
    interceptedViewAnnotationEvents.clear()
  }

  private fun isCrossedSlop(moveEvent: MotionEvent): Boolean {
    val downEvent = interceptedViewAnnotationEvents.firstOrNull() ?: return false
    val primaryPointerId = downEvent.getPointerId(0)
    val moveCoordinateIndex = moveEvent.findPointerIndex(primaryPointerId)
    if (moveCoordinateIndex == -1) {
      return false
    }
    val dx = abs(downEvent.getX(0) - moveEvent.getX(moveCoordinateIndex))
    val dy = abs(downEvent.getY(0) - moveEvent.getY(moveCoordinateIndex))
    return max(dx, dy) > touchSlop
  }

  /**
   * Called by a descendant view (e.g. a scrollable Compose annotation) to claim the current gesture.
   * While a descendant disallows interception, this [MapView] won't intercept the ongoing touch
   * sequence for panning, letting the child consume it instead.
   */
  override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    gestureRouter.requestDisallowInterceptTouchEvent(disallowIntercept)
    super.requestDisallowInterceptTouchEvent(disallowIntercept)
  }

  /**
   * Called when a generic motion event has occurred.
   *
   * @return true to indicate the touch event has been handled
   */
  override fun onGenericMotionEvent(event: MotionEvent): Boolean {
    return mapController.onGenericMotionEvent(event) ||
      super.onGenericMotionEvent(event)
  }

  /**
   * Set [OnFpsChangedListener] to get map rendering FPS.
   */
  override fun setOnFpsChangedListener(listener: OnFpsChangedListener) {
    mapController.setOnFpsChangedListener(listener)
  }

  /**
   * The [RenderThreadStatsRecorder] of this map. Call [RenderThreadStatsRecorder.start] and
   * [RenderThreadStatsRecorder.end] on it to record frame stats.
   */
  @MapboxExperimental
  override val renderThreadStatsRecorder: RenderThreadStatsRecorder
    get() = mapController.renderThreadStatsRecorder

  /**
   * Add [Widget] to the map.
   *
   * @throws RuntimeException when trying to add the Widget to the [MapView] that does not
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
   * Schedules a thread service type reset on the render thread after a specified delay.
   * This method is called to ensure CPU affinity is properly set
   * when coming back from background, addressing timing issues where CPU affinity
   * might not be immediately available.
   * **Note:** This is a delicate API that should only be used when you experience
   * performance issues after returning from background. Improper usage may not
   * provide any benefits or could potentially affect rendering performance.
   */
  @MapboxExperimental
  @MapboxDelicateApi
  fun scheduleThreadServiceTypeReset() {
    mapController.renderer.renderThread.scheduleThreadServiceTypeReset()
  }

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
   * Interface for getting snapshot result [Bitmap].
   */
  fun interface OnSnapshotReady {
    /**
     * Will be called when snapshot [Bitmap] is ready.
     *
     * @param bitmap Returns valid [Bitmap] if snapshot was successful and null otherwise.
     */
    fun onSnapshotReady(bitmap: Bitmap?)
  }

  /**
   * Static variables and methods.
   */
  companion object {
    @JvmSynthetic
    internal const val DEFAULT_ANTIALIASING_SAMPLE_COUNT = 1

    /**
     * Fallback to this value if, for some reason, Android display is NULL.
     * This will not affect rendering on displays with higher frame rate if [MapView.setMaximumFps] was not called.
     */
    @JvmSynthetic
    internal const val DEFAULT_FPS = 60

    /**
     * Static method to check if [MapView] could properly render on this device.
     * This method may take some time on slow devices.
     *
     * @return true if [MapView] could be rendered on this device and false otherwise
     */
    @JvmStatic
    fun isRenderingSupported(): Boolean {
      EGLCore(false, DEFAULT_ANTIALIASING_SAMPLE_COUNT, mapName = "").apply {
        val eglConfigOk = prepareEgl()
        release()
        return eglConfigOk
      }
    }

    /**
     * Static method to check if [MapView] could properly render 3D terrain on this device.
     * This method may take some time on slow devices.
     *
     * @return true if 3D terrain could be rendered on this device and false otherwise
     */
    @JvmStatic
    fun isTerrainRenderingSupported(): Boolean {
      EGLCore(false, DEFAULT_ANTIALIASING_SAMPLE_COUNT, mapName = "").apply {
        val eglConfigOk = prepareEgl()
        val eglSurface = createOffscreenSurface(1, 1)
        makeCurrent(eglSurface)
        val resultBuffer = IntBuffer.allocate(1)
        GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS, resultBuffer)
        resultBuffer.rewind()
        val result = resultBuffer.get()
        val terrainSupported = result > 0
        releaseSurface(eglSurface)
        release()
        return eglConfigOk && terrainSupported
      }
    }

    @VisibleForTesting
    @JvmSynthetic
    internal operator fun invoke(
      context: Context,
      attrs: AttributeSet?,
      mapController: MapController,
    ) = MapView(context, attrs, mapController)
  }
}