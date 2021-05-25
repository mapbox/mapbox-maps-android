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
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.renderer.MapboxSurfaceHolderRenderer
import com.mapbox.maps.renderer.MapboxTextureViewRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.egl.EGLCore
import java.lang.ref.WeakReference

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
  private var mapController: MapController

  /**
   * Build a [MapView] with [Context] and [MapInitOptions] objects.
   */
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
        is SurfaceView -> MapboxSurfaceHolderRenderer(view.holder)
        is TextureView -> MapboxTextureViewRenderer(WeakReference(view))
        else -> throw IllegalArgumentException("Provided view has to be a texture or a surface.")
      },
      resolvedMapInitOptions
    )
    // render cache disabled explicitly by default
    mapController.setRenderCache(RenderCache.Disabled)
    addView(view, 0)
    mapController.initializePlugins(resolvedMapInitOptions, this)
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

      return MapInitOptions(
        context,
        resourceOptions,
        mapOptions,
        attrs = attrs,
        styleUri = if (styleUri.isEmpty()) {
          null
        } else {
          styleUri
        }
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
  override fun setRenderCache(cache: RenderCache) {
    mapController.setRenderCache(cache)
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
  fun onLowMemory() {
    mapController.reduceMemoryUse()
  }

  /**
   * You must call this method from the parent's Activity#onDestroy() or Fragment#onDestroy()
   */
  override fun onDestroy() {
    mapController.onDestroy()
  }

  /**
   * Returns a [MapboxMap] object that can be used to interact with the map.
   * @return [MapboxMap] object to interact with the map.
   */
  override fun getMapboxMap(): MapboxMap = mapController.getMapboxMap()

  /**
   * Queue a runnable to be executed on the map renderer thread.
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
   * Only one instance of the same class type can be used.
   * If there is already a plugin created using a certain class,
   * that same instance will continue to be returned by this method.
   *
   * @param clazz plugin implementation class, has to extend [MapPlugin]
   * @param constructorArguments pairs of argument classes and instances.
   * If you'd like your plugin to be instantiated using different than no-arg constructor,
   * you need to specify the precise argument classes and instances that are needed to call that constructor.
   *
   * @return the created plugin instance
   */
  fun <T : MapPlugin> createPlugin(
    clazz: Class<T>,
    vararg constructorArguments: Pair<Class<*>, Any>
  ): T? = mapController.createPlugin(this, clazz, *constructorArguments)

  /**
   * Get the plugin instance.
   *
   * @param clazz the same class type that was used when instantiating the plugin
   * @return created plugin instance
   */
  override fun <T> getPlugin(clazz: Class<T>): T? = mapController.getPlugin(clazz)

  /**
   * Get the plugin instance
   *
   * @param className the name of the class that was used when instantiating the plugin
   * @return created plugin instance
   */
  override fun <T> getPlugin(className: String): T? {
    @Suppress("UNCHECKED_CAST")
    return getPlugin(
      Class.forName(
        className
      )
    ) as T
  }

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
    /**
     * Static method to check if [MapView] could properly render on this device.
     * This method may take some time on slow devices.
     *
     * @return true if [MapView] could be rendered on this device and false otherwise
     */
    @JvmStatic
    fun isRenderingSupported(): Boolean {
      EGLCore(false).apply {
        prepareEgl()
        release()
        return eglStatusSuccess
      }
    }
  }
}