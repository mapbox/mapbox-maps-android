package com.mapbox.maps

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.TextureView
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.renderer.MapboxSurfaceHolderRenderer
import com.mapbox.maps.renderer.MapboxTextureViewRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.egl.EGLCore
import com.mapbox.maps.renderer.widget.Widget
import com.mapbox.maps.viewannotation.ViewAnnotationManager

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
  internal var mapController: MapController
    private set

  private val viewAnnotationManagerDelegate = lazy { ViewAnnotationManagerImpl(this) }
  /**
   * Get view annotation manager instance to add / update / remove view annotations
   * represented as Android views.
   */
  val viewAnnotationManager: ViewAnnotationManager by viewAnnotationManagerDelegate

  /**
   * Build a [MapView] with [Context] and [MapInitOptions] objects.
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
   */
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  /**
   * Build a [MapView] with a [Context] object, a [AttributeSet] object, and
   * an [Int] which represents a style resource file.
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
  @Suppress("UNCHECKED_CAST")
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  internal constructor(
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
    val view = if (resolvedMapInitOptions.textureView) {
      TextureView(context, attrs)
    } else {
      SurfaceView(context, attrs)
    }
    mapController = MapController(
      when (view) {
        is SurfaceView -> MapboxSurfaceHolderRenderer(view.holder, resolvedMapInitOptions.antialiasingSampleCount)
        is TextureView -> MapboxTextureViewRenderer(view, resolvedMapInitOptions.antialiasingSampleCount)
        else -> throw IllegalArgumentException("Provided view has to be a texture or a surface.")
      },
      resolvedMapInitOptions
    )
    addView(view, 0)
    mapController.initializePlugins(resolvedMapInitOptions, this)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    mapController.onAttachedToWindow(this)
  }

  @SuppressLint("CustomViewStyleable")
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun parseTypedArray(context: Context, attrs: AttributeSet?): MapInitOptions {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      val resourceOptions =
        ResourcesAttributeParser.parseResourcesOptions(
          context,
          typedArray
        )
      val mapOptions =
        MapAttributeParser.parseMapOptions(typedArray, context.resources.displayMetrics.density)
      val cameraOptions = CameraAttributeParser.parseCameraOptions(typedArray)
      val textureView = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapSurface, 0) != 0
      val styleUri =
        typedArray.getString(R.styleable.mapbox_MapView_mapbox_styleUri) ?: Style.MAPBOX_STREETS
      val antialiasingSampleCount = typedArray.getInteger(R.styleable.mapbox_MapView_mapbox_mapAntialiasingSampleCount, DEFAULT_ANTIALIASING_SAMPLE_COUNT)

      return MapInitOptions(
        context,
        resourceOptions,
        mapOptions,
        attrs = attrs,
        styleUri = if (styleUri.isEmpty()) {
          null
        } else {
          styleUri
        },
        antialiasingSampleCount = antialiasingSampleCount
      ).also {
        it.cameraOptions = cameraOptions
        it.textureView = textureView
      }
    } finally {
      typedArray.recycle()
    }
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  @VisibleForTesting
  internal constructor(
    context: Context,
    attrs: AttributeSet?,
    mapController: MapController
  ) : super(context, attrs, 0, 0) {
    this.mapController = mapController
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
    mapController.onSizeChanged(w, h)
  }

  /**
   * You must call this method from the parent's Activity#onStart() or Fragment#onStart()
   * @see android.app.Activity.onStart
   * @see android.app.Fragment.onStart
   */
  override fun onStart() {
    mapController.onStart()
  }

  /**
   * You must call this method from the parent's Activity#onStop() or Fragment#onStop()
   * @see android.app.Activity.onStop
   * @see android.app.Fragment.onStop
   */
  override fun onStop() {
    mapController.onStop()
  }

  /**
   * You must call this method from the parent's Activity#onLowMemory() or Fragment#onLowMemory()
   * @see android.app.Activity.onLowMemory
   * @see android.app.Fragment.onLowMemory
   */
  override fun onLowMemory() {
    mapController.onLowMemory()
  }

  /**
   * You must call this method from the parent's Activity#onDestroy() or Fragment#onDestroy()
   */
  override fun onDestroy() {
    if (viewAnnotationManagerDelegate.isInitialized()) {
      (viewAnnotationManager as ViewAnnotationManagerImpl).destroy()
    }
    mapController.onDestroy()
  }

  /**
   * Returns a [MapboxMap] object that can be used to interact with the map.
   *
   * Note: keeping the reference to an invalid [MapboxMap] instance introduces significant native memory leak,
   * see [MapboxMap.isValid] for more details.
   *
   * @return [MapboxMap] object to interact with the map.
   */
  override fun getMapboxMap(): MapboxMap = mapController.getMapboxMap()

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
   * Set new maximum FPS for map rendering.
   *
   * @param maximumFps new maximum FPS value that must be > 0.
   */
  override fun setMaximumFps(@IntRange(from = 1L, to = Int.MAX_VALUE.toLong()) maximumFps: Int) {
    mapController.setMaximumFps(maximumFps)
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
    return mapController.onTouchEvent(event)
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

  internal fun viewAnnotationDraw() {
    mapController.viewAnnotationDraw()
  }

  internal fun viewAnnotationPositionArrived() {
    mapController.viewAnnotationPositionArrived()
  }

  /**
   * Static variables and methods.
   */
  companion object {
    internal const val DEFAULT_ANTIALIASING_SAMPLE_COUNT = 1
    /**
     * Static method to check if [MapView] could properly render on this device.
     * This method may take some time on slow devices.
     *
     * @return true if [MapView] could be rendered on this device and false otherwise
     */
    @JvmStatic
    fun isRenderingSupported(): Boolean {
      EGLCore(false, DEFAULT_ANTIALIASING_SAMPLE_COUNT).apply {
        val eglConfigOk = prepareEgl()
        release()
        return eglConfigOk
      }
    }
  }
}