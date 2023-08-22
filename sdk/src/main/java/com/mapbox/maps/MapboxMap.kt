package com.mapbox.maps

import android.app.Activity
import android.graphics.RectF
import android.os.Handler
import android.webkit.URLUtil
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.utils.transition
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.delegates.listeners.*
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import kotlin.math.roundToInt

/**
 * The general class to interact with in the Mapbox Maps SDK for Android.
 * It exposes the entry point for all methods related to the Map object.
 * You cannot instantiate [MapboxMap] object directly, rather, you must obtain one
 * from the getMapboxMap() method MapView that you have
 * added to your application.
 *
 * Note: Similar to a View object, a MapboxMap should only be read and modified
 * from the main thread.
 *
 * @property style the map style.
 */
class MapboxMap :
  MapTransformDelegate,
  MapProjectionDelegate,
  MapFeatureQueryDelegate,
  MapListenerDelegate,
  MapPluginExtensionsDelegate,
  MapCameraManagerDelegate {

  private val nativeMap: NativeMapImpl
  private var isMapValid = true

  /**
   * Whether the [MapboxMap] instance is valid.
   *
   * [MapboxMap] becomes invalid after [MapView.onDestroy] is invoked,
   * calling any method then could result in undefined behaviour and will print an error log.
   *
   * Keeping the reference to an invalid [MapboxMap] instance introduces significant native memory leak.
   *
   * @return True if [MapboxMap] is valid and false otherwise.
   */
  fun isValid(): Boolean {
    return isMapValid
  }

  internal val nativeObserver: NativeObserver
  internal var style: Style? = null
  internal var isStyleLoadInitiated = false
  private val styleObserver: StyleObserver
  internal var renderHandler: Handler? = null

  /**
   * Represents current camera state.
   */
  override val cameraState: CameraState
    get() {
      checkNativeMap("cameraState")
      return nativeMap.getCameraState()
    }

  internal var cameraAnimationsPlugin: CameraAnimationsPlugin? = null
  internal var gesturesPlugin: GesturesPlugin? = null

  @VisibleForTesting(otherwise = PRIVATE)
  internal constructor(
    nativeMap: NativeMapImpl,
    nativeObserver: NativeObserver,
    styleObserver: StyleObserver
  ) {
    this.nativeMap = nativeMap
    this.nativeObserver = nativeObserver
    this.styleObserver = styleObserver
  }

  internal constructor(
    nativeMap: NativeMapImpl,
    nativeObserver: NativeObserver,
    pixelRatio: Float
  ) {
    this.nativeMap = nativeMap
    this.nativeObserver = nativeObserver
    // we register our internal native observers here and
    // this is critical to always have our observers registered first
    this.styleObserver = StyleObserver(
      this.nativeMap.map,
      { style -> this.style = style },
      nativeObserver,
      pixelRatio
    )
  }

  /**
   * Create a new instance of [MapboxMapRecorder] for this map.
   */
  @MapboxExperimental
  fun createRecorder() = MapboxMapRecorder(MapRecorder(nativeMap.map))

  private fun String.isValidUri(): Boolean {
    val isMapboxStyleUri = startsWith("mapbox://", ignoreCase = true)
    val isMapboxAssetUri = startsWith("asset://", ignoreCase = true)
    val isMapboxFileUri = startsWith("file://", ignoreCase = true)
    return isMapboxStyleUri || isMapboxAssetUri || isMapboxFileUri || URLUtil.isValidUrl(this)
  }

  private fun applyStyle(style: String) {
    if (style.isValidUri()) {
      nativeMap.setStyleURI(style)
    } else {
      nativeMap.setStyleJSON(style.ifBlank { "{}" })
    }
  }

  /**
   * Loads the new map style either from a specified URI or from a JSON both represented as [String].
   *
   * URI can take the following forms:
   *
   * - **Constants**: load one of the bundled styles in [Style].
   *
   * - **`mapbox://styles/<user>/<style>`**:
   * loads the style from a [Mapbox account](https://www.mapbox.com/account/).
   * *user* is your username. *style* is the ID of your custom
   * style created in [Mapbox Studio](https://www.mapbox.com/studio).
   *
   * - **`http://...` or `https://...`**:
   * loads the style over the Internet from any web server.
   *
   * - **`asset://...`**:
   * loads the style from the APK *assets* directory.
   * This is used to load a style bundled with your app.
   *
   * - **`file://...`**:
   * loads the style from a file path. This is used to load a style from disk.
   *
   * If [style] is not the valid URI - it will be treated as style JSON string.
   *
   * ** Important notes comparing to Maps v10 **:
   * 1. Parameter `onMapLoadErrorListener` was removed as it was not covering all the map / style loading errors.
   *  Now if you need to listen to those errors you have to register specific listener in advance, e.g.
   *  [OnMapLoadErrorListener] should now be registered with [subscribeMapLoadingError];
   *  you could also subscribe to other events like [subscribeStyleImageMissing] or [subscribeStyleImageRemoveUnused].
   *
   * 2. Parameter `styleTransitionOptions` was removed from this overloaded method.
   *  In order to apply it you should use more granular overloaded [loadStyle] taking [StyleContract.StyleExtension]
   *  and add transition options in DSL block:
   *
   *  mapboxMap.loadStyle(style(Style.DARK) {
   *   +transition {
   *     duration(100L)
   *     enablePlacementTransitions(false)
   *    }
   *   // other runtime styling
   * }
   *
   * @param style specified URI or from JSON both represented as [String].
   * @param onStyleLoaded callback triggered when the style is successfully loaded.
   */
  @JvmOverloads
  fun loadStyle(
    style: String,
    onStyleLoaded: Style.OnStyleLoaded? = null,
  ) {
    checkNativeMap("loadStyle")
    initializeStyleLoad(
      onStyleLoaded,
      styleDataStyleLoadedListener = {},
    )
    applyStyle(style)
  }

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated." +
      " IMPORTANT: onMapLoadErrorListener will not be triggered anymore," +
      " please refer to documentation for new method to understand how to handle errors.",
    replaceWith = ReplaceWith("loadStyle(style, onStyleLoaded)")
  )
  fun loadStyleUri(
    styleUri: String,
    styleTransitionOptions: TransitionOptions? = null,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null,
  ) {
    loadStyle(
      style(styleUri) {
        styleTransitionOptions?.let {
          +transition {
            it.toBuilder()
          }
        }
      },
      onStyleLoaded
    )
  }

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated." +
      " IMPORTANT: onMapLoadErrorListener will not be triggered anymore," +
      " please refer to documentation for new method to understand how to handle errors.",
    replaceWith = ReplaceWith("loadStyle(style, onStyleLoaded)")
  )
  fun loadStyleUri(
    styleUri: String,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null
  ) {
    loadStyleUri(styleUri, null, onStyleLoaded, onMapLoadErrorListener)
  }

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated." +
      " IMPORTANT: onMapLoadErrorListener will not be triggered anymore," +
      " please refer to documentation for new method to understand how to handle errors.",
    replaceWith = ReplaceWith("loadStyle(style, onStyleLoaded)")
  )
  fun loadStyleUri(
    styleUri: String,
    onStyleLoaded: Style.OnStyleLoaded
  ) = loadStyleUri(styleUri, null, onStyleLoaded, null)

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated." +
      " IMPORTANT: onMapLoadErrorListener will not be triggered anymore," +
      " please refer to documentation for new method to understand how to handle errors.",
    replaceWith = ReplaceWith("loadStyle(style)")
  )
  fun loadStyleUri(
    styleUri: String,
  ) = loadStyleUri(styleUri, null, null, null)

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated." +
      " IMPORTANT: onMapLoadErrorListener will not be triggered anymore," +
      " please refer to documentation for new method to understand how to handle errors.",
    replaceWith = ReplaceWith("loadStyle(style, onStyleLoaded)")
  )
  fun loadStyleJson(
    styleJson: String,
    styleTransitionOptions: TransitionOptions? = null,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null,
  ) {
    loadStyleUri(styleJson, styleTransitionOptions, onStyleLoaded, onMapLoadErrorListener)
  }

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated." +
      " IMPORTANT: onMapLoadErrorListener will not be triggered anymore," +
      " please refer to documentation for new method to understand how to handle errors.",
    replaceWith = ReplaceWith("loadStyle(style, onStyleLoaded)")
  )
  fun loadStyleJson(
    styleJson: String,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null
  ) {
    loadStyleUri(styleJson, null, onStyleLoaded, onMapLoadErrorListener)
  }

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated.",
    replaceWith = ReplaceWith("loadStyle(style, onStyleLoaded)")
  )
  fun loadStyleJson(
    styleJson: String,
    onStyleLoaded: Style.OnStyleLoaded
  ) = loadStyleUri(styleJson, null, onStyleLoaded, null)

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated.",
    replaceWith = ReplaceWith("loadStyle(style)")
  )
  fun loadStyleJson(
    styleJson: String,
  ) = loadStyleUri(styleJson, null, null, null)

  /**
   * Loads the new map style built from the specified style DSL block. For example:
   *
   * mapboxMap.loadStyle(style(Style.DARK) {
   *   +geoJsonSource(SOURCE_ID) {
   *     featureCollection(collection)
   *   }
   *   +symbolLayer(LAYER_ID, SOURCE_ID) {
   *     iconImage(IMAGE_ID)
   *   }
   * }
   *
   * ** Important notes comparing to Maps v10 **:
   * 1. Parameter `onMapLoadErrorListener` was removed as it was not covering all the map / style loading errors.
   *  Now if you need to listen to those errors you have to register specific listener in advance, e.g.
   *  [OnMapLoadErrorListener] should now be registered with [subscribeMapLoadingError];
   *  you could also subscribe to other events like [subscribeStyleImageMissing] or [subscribeStyleImageRemoveUnused].
   *
   * 2. Parameter `styleTransitionOptions` was removed from this overloaded method. Instead you have to add transition options in the DSL block:
   *
   *  mapboxMap.loadStyle(style(Style.DARK) {
   *   +transition {
   *     duration(100L)
   *     enablePlacementTransitions(false)
   *    }
   *   // other runtime styling
   * }
   *
   * @param styleExtension The style DSL block used to describe the style with runtime styling on top of it.
   * @param onStyleLoaded callback triggered when the style is successfully loaded.
   */
  @OptIn(MapboxExperimental::class)
  @JvmOverloads
  fun loadStyle(
    styleExtension: StyleContract.StyleExtension,
    onStyleLoaded: Style.OnStyleLoaded? = null,
  ) {
    checkNativeMap("loadStyle")
    initializeStyleLoad(
      onStyleLoaded = onStyleLoaded,
      styleDataStyleLoadedListener = { style ->
        styleExtension.flatLight?.bindTo(style)
        styleExtension.dynamicLight?.bindTo(style)
        styleExtension.terrain?.bindTo(style)
        styleExtension.atmosphere?.bindTo(style)
        styleExtension.projection?.bindTo(style)
        styleExtension.transition?.let(style::setStyleTransition)
      },
      styleDataSourcesLoadedListener = { style ->
        styleExtension.sources.forEach {
          it.bindTo(style)
        }
        styleExtension.layers.forEach { (layer, layerPosition) ->
          layer.bindTo(style, layerPosition)
        }
      },
      styleDataSpritesLoadedListener = { style ->
        styleExtension.images.forEach {
          it.bindTo(style)
        }
        // note - it is not strictly required to load models here, models can be loaded anytime during style load flow
        styleExtension.models.forEach {
          it.bindTo(style)
        }
      },
    )
    applyStyle(styleExtension.style)
  }

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated." +
      " IMPORTANT: onMapLoadErrorListener and styleTransitionOptions will not be applied anymore," +
      " please refer to documentation for new method to understand how to apply them properly.",
    replaceWith = ReplaceWith("loadStyle(styleExtension, onStyleLoaded)")
  )
  fun loadStyle(
    styleExtension: StyleContract.StyleExtension,
    styleTransitionOptions: TransitionOptions? = null,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null,
  ) {
    loadStyle(styleExtension, onStyleLoaded)
  }

  /**
   * Legacy method to load style, please refer to deprecation message for more details.
   */
  @Deprecated(
    message = "Loading style was revisited in v11, this method is deprecated." +
      " IMPORTANT: onMapLoadErrorListener will not be triggered anymore," +
      " please refer to documentation for new method to understand how to handle errors.",
    replaceWith = ReplaceWith("loadStyle(styleExtension, onStyleLoaded)")
  )
  fun loadStyle(
    styleExtension: StyleContract.StyleExtension,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null
  ) {
    loadStyle(styleExtension, onStyleLoaded)
  }

  private fun initializeStyleLoad(
    onStyleLoaded: Style.OnStyleLoaded? = null,
    styleDataStyleLoadedListener: Style.OnStyleLoaded,
    styleDataSpritesLoadedListener: Style.OnStyleLoaded? = null,
    styleDataSourcesLoadedListener: Style.OnStyleLoaded? = null,
  ) {
    style = null
    styleObserver.setLoadStyleListener(
      onStyleLoaded,
      styleDataStyleLoadedListener = styleDataStyleLoadedListener,
      styleDataSpritesLoadedListener = styleDataSpritesLoadedListener,
      styleDataSourcesLoadedListener = styleDataSourcesLoadedListener,
    )
    isStyleLoadInitiated = true
  }

  /**
   * Get the [Style] of the map asynchronously.
   *
   * Note: keeping the reference to an invalid [Style] instance introduces significant native memory leak,
   * see [Style.isValid] for more details.
   *
   * @param onStyleLoaded the callback to be invoked when the style is fully loaded
   */
  fun getStyle(onStyleLoaded: Style.OnStyleLoaded) {
    checkNativeMap("getStyle")
    style?.let(onStyleLoaded::onStyleLoaded)
      ?: styleObserver.addGetStyleListener(onStyleLoaded)
  }

  /**
   * Get the Style of the map synchronously, will return null is style is not loaded yet.
   *
   * Note: keeping the reference to an invalid [Style] instance introduces significant native memory leak,
   * see [Style.isValid] for more details.
   *
   * @return currently loaded [Style] object or NULL if it is not loaded.
   */
  fun getStyle(): Style? {
    checkNativeMap("getStyle")
    return style
  }

  /**
   * Changes the map view by any combination of center, zoom, bearing, and pitch, without an animated transition.
   * The map will retain its current values for any details not passed via the camera options argument.
   * It is not guaranteed that the provided CameraOptions will be set, the map may apply constraints resulting in a
   * different CameraState.
   *
   * @param cameraOptions New camera options
   */
  override fun setCamera(cameraOptions: CameraOptions) {
    checkNativeMap("setCamera")
    nativeMap.setCamera(cameraOptions)
  }

  /**
   * Notify map about gesture being in progress.
   *
   * @param inProgress True if gesture is in progress
   */
  override fun setGestureInProgress(inProgress: Boolean) {
    checkNativeMap("setGestureInProgress")
    nativeMap.setGestureInProgress(inProgress)
  }

  /**
   * Returns if a gesture is in progress.
   *
   * @return Returns if a gesture is in progress
   */
  override fun isGestureInProgress(): Boolean {
    checkNativeMap("isGestureInProgress")
    return nativeMap.isGestureInProgress()
  }

  /**
   * Set the map north orientation
   *
   * @param northOrientation The map north orientation to set
   */
  override fun setNorthOrientation(northOrientation: NorthOrientation) {
    checkNativeMap("setNorthOrientation")
    nativeMap.setNorthOrientation(northOrientation)
  }

  /**
   * Set the map constrain mode
   *
   * @param constrainMode The map constraint mode to set
   */
  override fun setConstrainMode(constrainMode: ConstrainMode) {
    checkNativeMap("setConstrainMode")
    nativeMap.setConstrainMode(constrainMode)
  }

  /**
   * Set the map viewport mode
   *
   * @param viewportMode The map viewport mode to set
   */
  override fun setViewportMode(viewportMode: ViewportMode) {
    checkNativeMap("setViewportMode")
    nativeMap.setViewportMode(viewportMode)
  }

  /**
   * Set the map bounds.
   *
   * @param options the map bound options
   */
  override fun setBounds(options: CameraBoundsOptions): Expected<String, None> {
    checkNativeMap("setBounds")
    return nativeMap.setBounds(options)
  }

  /**
   * Get the map bounds options.
   *
   * @return Returns the map bounds options
   */
  override fun getBounds(): CameraBounds {
    checkNativeMap("getBounds")
    return nativeMap.getBounds()
  }

  /**
   * Tells the map rendering engine that the animation is currently performed by the
   * user (e.g. with a `setCamera()` calls series). It adjusts the engine for the animation use case.
   * In particular, it brings more stability to symbol placement and rendering.
   *
   * @param inProgress Bool representing if user animation is in progress
   */
  override fun setUserAnimationInProgress(inProgress: Boolean) {
    checkNativeMap("setUserAnimationInProgress")
    nativeMap.setUserAnimationInProgress(inProgress)
  }

  /**
   * Returns if user animation is currently in progress.
   *
   * @return Return true if a user animation is in progress.
   */
  override fun isUserAnimationInProgress(): Boolean {
    checkNativeMap("isUserAnimationInProgress")
    return nativeMap.isUserAnimationInProgress()
  }

  /**
   * Set the prefetch zoom delta
   *
   * @param delta The prefetch zoom delta
   */
  fun setPrefetchZoomDelta(delta: Byte) {
    checkNativeMap("setPrefetchZoomDelta")
    nativeMap.setPrefetchZoomDelta(delta)
  }

  /**
   * Get the prefetch zoom delta
   *
   * @return Returns the prefetch zoom delta
   */
  fun getPrefetchZoomDelta(): Byte {
    checkNativeMap("getPrefetchZoomDelta")
    return nativeMap.getPrefetchZoomDelta()
  }

  /**
   * Get map options.
   *
   * @return Returns map options
   */
  override fun getMapOptions(): MapOptions {
    checkNativeMap("getMapOptions")
    return nativeMap.getMapOptions()
  }

  /**
   * Gets the size of the map.
   *
   * @return size The size of the map in MapOptions#size platform pixels
   */
  override fun getSize(): Size {
    checkNativeMap("getSize")
    return nativeMap.getSize()
  }

  /**
   * Get debug options
   */
  fun getDebug(): List<MapDebugOptions> {
    checkNativeMap("getDebug")
    return nativeMap.getDebug()
  }

  /**
   * Set debug options
   */
  fun setDebug(debugOptions: List<MapDebugOptions>, enabled: Boolean) {
    checkNativeMap("setDebug")
    nativeMap.setDebug(debugOptions, enabled)
  }

  /**
   * Convenience method that returns the [CameraOptions] object for given parameters.
   *
   * In order for this method to produce correct results [MapView] must be already
   * measured and inflated to have correct width and height values.
   * Calling this method in [Activity.onCreate] will lead to incorrect results.
   *
   * @param bounds The [CoordinateBounds] of the camera.
   * @param padding The amount of padding in [EdgeInsets] to add to the given bounds.
   * @param bearing The bearing of the camera.
   * @param pitch The pitch of the camera.
   * @param maxZoom The maximum zoom level allowed in the returned camera options.
   * @param offset The center of the given bounds relative to map center in pixels.
   *
   * @return The [CameraOptions] object representing the provided parameters.
   */
  override fun cameraForCoordinateBounds(
    bounds: CoordinateBounds,
    padding: EdgeInsets?,
    bearing: Double?,
    pitch: Double?,
    maxZoom: Double?,
    offset: ScreenCoordinate?
  ): CameraOptions {
    checkNativeMap("cameraForCoordinateBounds")
    return nativeMap.cameraForCoordinateBounds(
      bounds,
      padding,
      bearing,
      pitch,
      maxZoom,
      offset,
    )
  }

  /**
   * Convert to a camera options from a given list of points, padding, bearing and pitch values.
   *
   * In order for this method to produce correct results [MapView] must be already
   * measured and inflated to have correct width and height values.
   * Calling this method in [Activity.onCreate] will lead to incorrect results.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching
   * the current camera center.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * @param coordinates The List of coordinates to take in account when converting
   * @param padding The optional padding to take in account when converting
   * @param bearing The optional bearing to take in account when converting
   * @param pitch The optional pitch to take in account when converting
   *
   * @return Returns the converted camera options
   */
  override fun cameraForCoordinates(
    coordinates: List<Point>,
    padding: EdgeInsets?,
    bearing: Double?,
    pitch: Double?
  ): CameraOptions {
    checkNativeMap("cameraForCoordinates")
    return nativeMap.cameraForCoordinates(coordinates, padding, bearing, pitch)
  }

  /**
   * Convenience method that returns the camera options object for given arguments
   *
   * In order for this method to produce correct results [MapView] must be already
   * measured and inflated to have correct width and height values.
   * Calling this method in [Activity.onCreate] will lead to incorrect results.
   *
   * Returns the camera options object for given arguments with zoom adjusted to fit \p coordinates into \p box, so that
   * coordinates on the left, top and right of \p camera.center fit into \p box.
   * Returns the provided camera options object unchanged upon error.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching
   * the current camera center.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * @param coordinates The coordinates representing the bounds of the map
   * @param box The box into which \p coordinates should fit
   * @param camera The camera for which zoom should be adjusted. Note that \p camera.center is required.
   *
   * @return Returns the camera options object with the zoom level adjusted to fit \p coordinates into \p box.
   */
  override fun cameraForCoordinates(
    coordinates: List<Point>,
    camera: CameraOptions,
    box: ScreenBox
  ): CameraOptions {
    checkNativeMap("cameraForCoordinates")
    return nativeMap.cameraForCoordinates(coordinates, camera, box)
  }

  /**
   * Convert to a camera options from a given geometry, padding, bearing and pitch values.
   *
   * In order for this method to produce correct results [MapView] must be already
   * measured and inflated to have correct width and height values.
   * Calling this method in [Activity.onCreate] will lead to incorrect results.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching
   * the current camera center.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * @param geometry The geometry to take in account when converting
   * @param padding The optional padding to take in account when converting
   * @param bearing The optional bearing to take in account when converting
   * @param pitch The optional pitch to take in account when converting
   *
   * @return Returns the converted camera options
   */
  override fun cameraForGeometry(
    geometry: Geometry,
    padding: EdgeInsets?,
    bearing: Double?,
    pitch: Double?
  ): CameraOptions {
    checkNativeMap("cameraForGeometry")
    return nativeMap.cameraForGeometry(geometry, padding, bearing, pitch)
  }

  /**
   * Returns the [CoordinateBounds] for a given camera.
   *
   * Note that if the given `camera` shows the antimeridian, the returned wrapped [CoordinateBounds]
   * might not represent the minimum bounding box.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the world
   * bounds.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * @param camera The [CameraOptions] to use for calculating [CoordinateBounds].
   *
   * @return The [CoordinateBounds] object representing a given `camera`.
   */
  override fun coordinateBoundsForCamera(camera: CameraOptions): CoordinateBounds {
    checkNativeMap("coordinateBoundsForCamera")
    return nativeMap.coordinateBoundsForCamera(camera)
  }

  /**
   * Returns the [CoordinateBounds] for a given camera.
   *
   * This method is useful if the `camera` shows the antimeridian.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the world
   * bounds.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * @param camera The [CameraOptions] to use for calculating [CoordinateBounds].
   *
   * @return The [CoordinateBounds] object representing a given `camera`.
   */
  override fun coordinateBoundsForCameraUnwrapped(camera: CameraOptions): CoordinateBounds {
    checkNativeMap("coordinateBoundsForCameraUnwrapped")
    return nativeMap.coordinateBoundsForCameraUnwrapped(camera)
  }

  /**
   *  Returns the coordinate bounds and zoom for a given camera.
   *
   * In order for this method to produce correct results [MapView] must be already
   * measured and inflated to have correct width and height values.
   * Calling this method in [Activity.onCreate] will lead to incorrect results.
   *
   * Note that if the given camera shows the antimeridian, the returned wrapped bounds
   * might not represent the minimum bounding box.
   *
   * See also {@link #coordinateBoundsZoomForCameraUnwrapped}
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the world
   * bounds
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   *  @return Returns the coordinate bounds and zoom for a given camera.
   */
  override fun coordinateBoundsZoomForCamera(camera: CameraOptions): CoordinateBoundsZoom {
    checkNativeMap("coordinateBoundsZoomForCamera")
    return nativeMap.coordinateBoundsZoomForCamera(camera)
  }

  /**
   * Returns the unwrapped coordinate bounds and zoom for a given camera.
   *
   * In order for this method to produce correct results [MapView] must be already
   * measured and inflated to have correct width and height values.
   * Calling this method in [Activity.onCreate] will lead to incorrect results.
   *
   * This method is particularly useful, if the camera shows the antimeridian.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the
   * world bounds.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   *  @return Returns the unwrapped coordinate bounds and zoom for a given camera.
   */
  override fun coordinateBoundsZoomForCameraUnwrapped(camera: CameraOptions): CoordinateBoundsZoom {
    checkNativeMap("coordinateBoundsZoomForCameraUnwrapped")
    return nativeMap.coordinateBoundsZoomForCameraUnwrapped(camera)
  }

  /**
   * Returns the [CoordinateBounds] for given [RectF] defined in screen points.
   *
   * The screen points are in `platform pixels` relative to the top left corner
   * of the map (not of the whole screen).
   *
   * This API isn't supported by Globe projection.
   *
   * @param rectF rectangle area defined in screen points.
   * @return [CoordinateBounds] representing given [RectF].
   * @throws [IllegalArgumentException] if [RectF] is empty
   */
  override fun coordinateBoundsForRect(rectF: RectF): CoordinateBounds {
    checkNativeMap("coordinateBoundsForRect")
    if (rectF.isEmpty) throw IllegalArgumentException("RectF must not be empty")
    val coordinates = nativeMap.coordinatesForPixels(
      mutableListOf(
        // bottom-left corresponds to southwest
        ScreenCoordinate(rectF.bottom.toDouble(), rectF.left.toDouble()),
        // top-right corresponds to northeast
        ScreenCoordinate(rectF.top.toDouble(), rectF.right.toDouble()),
      )
    )
    return CoordinateBounds(
      /* southwest */ coordinates[0],
      /* northeast */ coordinates[1]
    )
  }

  /**
   * Calculate a screen coordinate that corresponds to a geographical coordinate
   * (i.e., longitude-latitude pair).
   *
   * The screen coordinate is in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * Map must be fully loaded for getting an altitude-compliant result if using 3D terrain.
   *
   * If the screen coordinate is outside of the bounds of [MapView] the returned screen coordinate
   * contains -1 for both coordinates.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching center of
   * the screen.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * @param coordinate A geographical coordinate on the map to convert to a screen coordinate.
   *
   * @return Returns a screen coordinate on the screen in [MapOptions.size] platform pixels. If the screen coordinate is outside of the bounds of [MapView] the returned screen coordinate contains -1 for both coordinates.
   */
  override fun pixelForCoordinate(coordinate: Point): ScreenCoordinate {
    checkNativeMap("pixelForCoordinate")
    return nativeMap.pixelForCoordinate(coordinate).clampScreenCoordinate()
  }

  /**
   * Calculate screen coordinates that corresponds to geographical coordinates
   * (i.e., longitude-latitude pair).
   *
   * The screen coordinates are in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * Map must be fully loaded for getting an altitude-compliant result if using 3D terrain.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the center
   * of the screen.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * @param coordinates A batch of geographical coordinates on the map to convert to screen coordinates.
   *
   * @return Returns a batch of screen coordinates on the screen in [MapOptions.size] platform pixels.
   */
  override fun pixelsForCoordinates(coordinates: List<Point>): List<ScreenCoordinate> {
    checkNativeMap("pixelsForCoordinates")
    return nativeMap.pixelsForCoordinates(coordinates.toMutableList()).map { it.clampScreenCoordinate() }
  }

  /**
   * Clamp screen coordinate to the bound of [MapView].
   * If the screen coordinate is outside of the bounds of [MapView] the returned screen coordinate
   * contains -1 for both coordinates.
   */
  private fun ScreenCoordinate.clampScreenCoordinate(): ScreenCoordinate {
    val screenSize = nativeMap.getSize()

    var x = this.x
    var y = this.y

    if (this.x < 0.0 || this.x > screenSize.width) {
      x = this.x.roundToInt().toDouble()
    }

    if (this.y < 0.0 || this.y > screenSize.height) {
      y = this.y.roundToInt().toDouble()
    }
    return if (x in 0.0..screenSize.width.toDouble() && y in 0.0..screenSize.height.toDouble()) {
      ScreenCoordinate(x, y)
    } else {
      ScreenCoordinate(-1.0, -1.0)
    }
  }

  /**
   * Calculate a geographical coordinate(i.e., longitude-latitude pair) that corresponds
   * to a screen coordinate.
   *
   * The screen coordinate is in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * Map must be fully loaded for getting an altitude-compliant result if using 3D terrain.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the center
   * of the screen.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * @param pixel A screen coordinate represented by x y coordinates.
   *
   * @return Returns a geographical coordinate corresponding to the x y coordinates
   * on the screen.
   */
  override fun coordinateForPixel(pixel: ScreenCoordinate): Point {
    checkNativeMap("coordinateForPixel")
    return nativeMap.coordinateForPixel(pixel)
  }

  /**
   * Calculate geographical coordinates(i.e., longitude-latitude pair) that corresponds
   * to screen coordinates.
   *
   * The screen coordinates are in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * Map must be fully loaded for getting an altitude-compliant result if using 3D terrain.
   *
   * This API isn't supported by Globe projection and will return a no-op result matching the center
   * of the screen.
   * See [com.mapbox.maps.extension.style.projection.generated.setProjection]
   * and [com.mapbox.maps.extension.style.projection.generated.getProjection]
   *
   * @param pixels A batch of screen coordinates on the screen in [MapOptions.size] platform pixels.
   *
   * @return Returns a batch of geographical coordinates corresponding to the screen coordinates
   * on the screen.
   */
  override fun coordinatesForPixels(pixels: List<ScreenCoordinate>): List<Point> {
    checkNativeMap("coordinatesForPixels")
    return nativeMap.coordinatesForPixels(pixels.toMutableList())
  }

  /**
   * Calculates the geographical coordinate information that corresponds to a given screen coordinate.
   *
   * The screen coordinate is in platform pixels, relative to the top left corner of the map (not the whole screen).
   *
   * The returned coordinate will be the closest position projected onto the map surface,
   * in case the screen coordinate does not intersect with the map surface.
   *
   * @param pixel The screen coordinate on the map, in platform pixels.
   *
   * @return A CoordinateInfo record containing information about the geographical coordinate corresponding to the given screen coordinate, including whether it is on the map surface.
   *
   */
  override fun coordinateInfoForPixel(pixel: ScreenCoordinate): CoordinateInfo {
    checkNativeMap("coordinateInfoForPixel")
    return nativeMap.coordinateInfoForPixel(pixel)
  }

  /**
   * Calculates the geographical coordinates information that corresponds to the given screen coordinates.
   *
   * The screen coordinates are in platform pixels, relative to the top left corner of the map (not the whole screen).
   *
   * The returned coordinate will be the closest position projected onto the map surface,
   * in case the screen coordinate does not intersect with the map surface.
   *
   * @param pixels The list of screen coordinates on the map, in platform pixels.
   *
   * @return The CoordinateInfo records containing information about the geographical coordinates corresponding to the given screen coordinates, including whether they are on the map surface.
   *
   */
  override fun coordinatesInfoForPixels(pixels: List<ScreenCoordinate>): List<CoordinateInfo> {
    checkNativeMap("coordinatesInfoForPixels")
    return nativeMap.coordinatesInfoForPixels(pixels)
  }

  /**
   * Calculate distance spanned by one pixel at the specified latitude
   * and zoom level.
   *
   * @param latitude The latitude for which to return the value
   * @param zoom The zoom level
   *
   * @return Returns the distance measured in meters.
   */
  override fun getMetersPerPixelAtLatitude(latitude: Double, zoom: Double): Double {
    return Projection.getMetersPerPixelAtLatitude(latitude, zoom)
  }

  /**
   * Calculate distance spanned by one pixel at the specified latitude
   * at current zoom level.
   *
   * @param latitude The latitude for which to return the value
   *
   * @return Returns the distance measured in meters.
   */
  override fun getMetersPerPixelAtLatitude(latitude: Double): Double {
    return Projection.getMetersPerPixelAtLatitude(latitude, cameraState.zoom)
  }

  /**
   * Calculate Spherical Mercator ProjectedMeters coordinates.
   *
   * @param point A longitude-latitude pair for which to calculate
   * ProjectedMeters coordinates
   *
   * @return Returns Spherical Mercator ProjectedMeters coordinates
   */
  override fun projectedMetersForCoordinate(point: Point): ProjectedMeters {
    return Projection.projectedMetersForCoordinate(point)
  }

  /**
   * Calculate a longitude-latitude pair for a Spherical Mercator projected
   * meters.
   *
   * @param projectedMeters Spherical Mercator ProjectedMeters coordinates for
   * which to calculate a longitude-latitude pair.
   *
   * @return Returns a longitude-latitude pair.
   */
  override fun coordinateForProjectedMeters(projectedMeters: ProjectedMeters): Point {
    return Projection.coordinateForProjectedMeters(projectedMeters)
  }

  /**
   * Calculate a point on the map in Mercator Projection for a given
   * coordinate at the specified zoom scale.
   *
   * @param point The longitude-latitude pair for which to return the value.
   * @param zoomScale The current zoom factor applied on the map, is used to
   * calculate the world size as tileSize * zoomScale (i.e., 512 * 2 ^ Zoom level)
   * where tileSize is the width of a tile in pixels.
   *
   * @return Returns a point on the map in Mercator projection.
   */
  override fun project(point: Point, zoomScale: Double): MercatorCoordinate {
    return Projection.project(point, zoomScale)
  }

  /**
   * Calculate a coordinate for a given point on the map in Mercator Projection.
   *
   * @param coordinate Point on the map in Mercator projection.
   * @param zoomScale The current zoom factor applied on the map, is used to
   * calculate the world size as tileSize * zoomScale (i.e., 512 * 2 ^ Zoom level)
   * where tileSize is the width of a tile in pixels.
   *
   * @return Returns a coordinate.
   */
  override fun unproject(coordinate: MercatorCoordinate, zoomScale: Double): Point {
    return Projection.unproject(coordinate, zoomScale)
  }

  /**
   * Queries the map for rendered features.
   *
   * @param geometry The `screen pixel coordinates` (point, line string or box) to query for rendered features.
   * @param options The `render query options` for querying rendered features.
   * @param callback The `query features callback` called when the query completes.
   * @return A `cancelable` object that could be used to cancel the pending query.
   */
  override fun queryRenderedFeatures(
    geometry: RenderedQueryGeometry,
    options: RenderedQueryOptions,
    callback: QueryRenderedFeaturesCallback,
  ): Cancelable {
    checkNativeMap("queryRenderedFeatures", false)
    return nativeMap.queryRenderedFeatures(geometry, options, callback)
  }

  /**
   * Queries the map for source features.
   *
   * @param sourceId The style source identifier used to query for source features.
   * @param options The `source query options` for querying source features.
   * @param callback The `query features callback` called when the query completes.
   * @return A `cancelable` object that could be used to cancel the pending query.
   *
   * Note: In order to get expected results, the corresponding source needs to be in use and
   * the query shall be made after the corresponding source data is loaded.
   */
  override fun querySourceFeatures(
    sourceId: String,
    options: SourceQueryOptions,
    callback: QuerySourceFeaturesCallback
  ): Cancelable {
    checkNativeMap("querySourceFeatures", false)
    return nativeMap.querySourceFeatures(sourceId, options, callback)
  }

  /**
   * In some cases querying source / render features is expected to be a blocking operation
   * e.g. performing this action on map click. In this case in order to avoid deadlock on main
   * thread querying could be performed on render thread and in that case querying result will be also
   * delivered on render thread not leading to the main thread deadlock. Example:
   *
   * fun onMapClick() {
   *  executeOnRenderThread {
   *    queryRenderedFeatures(pixel, options) {
   *      // result callback called, do needed actions
   *      lock.notify()
   *    }
   *  }
   *  lock.wait()
   *  return false
   * }
   */
  override fun executeOnRenderThread(runnable: Runnable) {
    checkNativeMap("executeOnRenderThread")
    renderHandler?.post(runnable)
  }

  /**
   * Returns all the leaves (original points) of a cluster (given its cluster_id) from a GeoJsonSource, with pagination support: limit is the number of leaves
   * to return (set to Infinity for all points), and offset is the amount of points to skip (for pagination).
   *
   * Requires configuring the source as a cluster by calling [GeoJsonSource.Builder#cluster(boolean)].
   *
   * @param sourceIdentifier GeoJsonSource identifier.
   * @param cluster Cluster from which to retrieve leaves from
   * @param limit The number of points to return from the query (must use type [Long], set to maximum for all points). Defaults to 10.
   * @param offset The amount of points to skip (for pagination, must use type [Long]). Defaults to 0.
   * @param callback The result will be returned through the [QueryFeatureExtensionCallback].
   *         The result is a feature collection or a string describing an error if the operation was not successful.
   *
   * @return A `cancelable` object that could be used to cancel the pending query.
   */
  @JvmOverloads
  fun getGeoJsonClusterLeaves(
    sourceIdentifier: String,
    cluster: Feature,
    limit: Long = QFE_DEFAULT_LIMIT,
    offset: Long = QFE_DEFAULT_OFFSET,
    callback: QueryFeatureExtensionCallback,
  ): Cancelable = nativeMap.queryFeatureExtensions(
    /* sourceIdentifier = */ sourceIdentifier,
    /* feature = */ cluster,
    /* extension = */ QFE_SUPER_CLUSTER,
    /* extensionField = */ QFE_LEAVES,
    /* args = */ hashMapOf(QFE_LIMIT to Value(limit), QFE_OFFSET to Value(offset)),
    /* callback = */ callback
  )

  /**
   * Returns the children (original points or clusters) of a cluster (on the next zoom level)
   * given its id (cluster_id value from feature properties) from a GeoJsonSource.
   *
   * Requires configuring the source as a cluster by calling [GeoJsonSource.Builder#cluster(boolean)].
   *
   * @param sourceIdentifier GeoJsonSource identifier.
   * @param cluster cluster from which to retrieve children from
   * @param callback The result will be returned through the [QueryFeatureExtensionCallback].
   *         The result is a feature collection or a string describing an error if the operation was not successful.
   *
   * @return A `cancelable` object that could be used to cancel the pending query.
   */
  fun getGeoJsonClusterChildren(
    sourceIdentifier: String,
    cluster: Feature,
    callback: QueryFeatureExtensionCallback,
  ): Cancelable = nativeMap.queryFeatureExtensions(
    /* sourceIdentifier = */ sourceIdentifier,
    /* feature = */ cluster,
    /* extension = */ QFE_SUPER_CLUSTER,
    /* extensionField = */ QFE_CHILDREN,
    /* args = */ null,
    /* callback = */ callback
  )

  /**
   * Returns the zoom on which the cluster expands into several children (useful for "click to zoom" feature)
   * given the cluster's cluster_id (cluster_id value from feature properties) from a GeoJsonSource.
   *
   * Requires configuring the source as a cluster by calling [GeoJsonSource.Builder#cluster(boolean)].
   *
   * @param sourceIdentifier GeoJsonSource identifier.
   * @param cluster cluster from which to retrieve the expansion zoom from
   * @param callback The result will be returned through the [QueryFeatureExtensionCallback].
   *         The result is a feature extension value containing a value or a string describing an error if the operation was not successful.
   *
   * @return A `cancelable` object that could be used to cancel the pending query.
   */
  fun getGeoJsonClusterExpansionZoom(
    sourceIdentifier: String,
    cluster: Feature,
    callback: QueryFeatureExtensionCallback,
  ): Cancelable = nativeMap.queryFeatureExtensions(
    /* sourceIdentifier = */ sourceIdentifier,
    /* feature = */ cluster,
    /* extension = */ QFE_SUPER_CLUSTER,
    /* extensionField = */ QFE_EXPANSION_ZOOM,
    /* args = */ null,
    /* callback = */ callback
  )

  /**
   * Updates the state object of a feature within a style source.
   *
   * Update entries in the `state` object of a given feature within a style source. Only properties of the
   * `state` object will be updated. A property in the feature `state` object that is not listed in `state` will
   * retain its previous value. The properties must be paint properties, layout properties are not supported.
   *
   * Note that updates to feature `state` are asynchronous, so changes made by this method might not be
   * immediately visible using `getStateFeature`. And the corresponding source needs to be in use to ensure the
   * feature data it contains can be successfully updated.
   *
   * @param sourceId The style source identifier.
   * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId The feature identifier of the feature whose state should be updated.
   * @param state The `state` object with properties to update with their respective new values.
   * @param callback The `feature state operation callback` called when the operation completes or ends.
   * @return A `cancelable` object that could be used to cancel the pending operation.
   *
   */
  @JvmOverloads
  fun setFeatureState(
    sourceId: String,
    sourceLayerId: String? = null,
    featureId: String,
    state: Value,
    callback: FeatureStateOperationCallback,
  ): Cancelable {
    checkNativeMap("setFeatureState")
    return nativeMap.setFeatureState(
      /* sourceId = */ sourceId,
      /* sourceLayerId = */ sourceLayerId,
      /* featureId = */ featureId,
      /* state = */ state,
      /* callback = */ callback
    )
  }

  /**
   * Get the state map of a feature within a style source.
   *
   * Note that updates to feature state are asynchronous, so changes made by other methods might not be
   * immediately visible.
   *
   * @param sourceId The style source identifier.
   * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId The feature identifier of the feature whose state should be queried.
   * @param callback The `query feature state callback` called when the query completes.
   * @return A `cancelable` object that could be used to cancel the pending query.
   */
  @JvmOverloads
  fun getFeatureState(
    sourceId: String,
    sourceLayerId: String? = null,
    featureId: String,
    callback: QueryFeatureStateCallback,
  ): Cancelable {
    checkNativeMap("getFeatureState")
    return nativeMap.getFeatureState(
      /* sourceId = */ sourceId,
      /* sourceLayerId = */ sourceLayerId,
      /* featureId = */ featureId,
      /* callback = */callback
    )
  }

  /**
   * Removes entries from a feature state object.
   *
   * Remove a specified property or all property from a feature's state object, depending on the value of
   * `stateKey`.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method might not be
   * immediately visible using `getStateFeature`.
   *
   * @param sourceId The style source identifier.
   * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId The feature identifier of the feature whose state should be removed.
   * @param stateKey The key of the property to remove. If `null`, all feature's state object properties are removed.
   * @param callback The `feature state operation callback` called when the operation completes or ends.
   * @return A `cancelable` object that could be used to cancel the pending operation.
   */
  @JvmOverloads
  fun removeFeatureState(
    sourceId: String,
    sourceLayerId: String? = null,
    featureId: String,
    stateKey: String? = null,
    callback: FeatureStateOperationCallback,
  ): Cancelable {
    checkNativeMap("removeFeatureState")
    return nativeMap.removeFeatureState(
      /* sourceId = */
      sourceId,
      /* sourceLayerId = */
      sourceLayerId,
      /* featureId = */
      featureId,
      /* stateKey = */
      stateKey,
      /* callback = */
      callback,
    )
  }

  /**
   * Reset all the feature states within a style source.
   *
   * Remove all feature state entries from the specified style source or source layer.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method might not be
   * immediately visible using `getStateFeature`.
   *
   * @param sourceId The style source identifier.
   * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
   * @param callback The `feature state operation callback` called when the operation completes or ends.
   * @return A `cancelable` object that could be used to cancel the pending operation.
   */
  @JvmOverloads
  fun resetFeatureStates(
    sourceId: String,
    sourceLayerId: String? = null,
    callback: FeatureStateOperationCallback
  ): Cancelable {
    checkNativeMap("resetFeatureState")
    return nativeMap.resetFeatureStates(sourceId, sourceLayerId, callback)
  }

  /**
   * Reduce memory use. Useful to call when the application gets paused or sent to background.
   */
  fun reduceMemoryUse() {
    checkNativeMap("reduceMemoryUse")
    nativeMap.reduceMemoryUse()
  }

  /**
   * Subscribes to `MapLoaded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param mapLoadedCallback
   */
  override fun subscribeMapLoaded(mapLoadedCallback: MapLoadedCallback): Cancelable {
    checkNativeMap("subscribeMapLoaded")
    return nativeObserver.subscribeMapLoaded(mapLoadedCallback)
  }

  /**
   * Subscribes to `MapIdle` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param mapIdleCallback
   */
  override fun subscribeMapIdle(mapIdleCallback: MapIdleCallback): Cancelable {
    checkNativeMap("subscribeMapIdle")
    return nativeObserver.subscribeMapIdle(mapIdleCallback)
  }

  /**
   * Subscribes to `MapLoadingError` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param mapLoadingErrorCallback
   */
  override fun subscribeMapLoadingError(mapLoadingErrorCallback: MapLoadingErrorCallback): Cancelable {
    checkNativeMap("subscribeMapLoadingError")
    return nativeObserver.subscribeMapLoadingError(mapLoadingErrorCallback)
  }

  /**
   * Subscribes to `StyleLoaded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param styleLoadedCallback
   */
  override fun subscribeStyleLoaded(styleLoadedCallback: StyleLoadedCallback): Cancelable {
    checkNativeMap("subscribeStyleLoaded")
    return nativeObserver.subscribeStyleLoaded(styleLoadedCallback)
  }

  /**
   * Subscribes to `StyleDataLoaded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param styleDataLoadedCallback
   */
  override fun subscribeStyleDataLoaded(styleDataLoadedCallback: StyleDataLoadedCallback): Cancelable {
    checkNativeMap("subscribeStyleDataLoaded")
    return nativeObserver.subscribeStyleDataLoaded(styleDataLoadedCallback)
  }

  /**
   * Subscribes to `SourceDataLoaded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param sourceDataLoadedCallback
   */
  override fun subscribeSourceDataLoaded(sourceDataLoadedCallback: SourceDataLoadedCallback): Cancelable {
    checkNativeMap("subscribeSourceDataLoaded")
    return nativeObserver.subscribeSourceDataLoaded(sourceDataLoadedCallback)
  }

  /**
   * Subscribes to `SourceAdded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param sourceAddedCallback
   */
  override fun subscribeSourceAdded(sourceAddedCallback: SourceAddedCallback): Cancelable {
    checkNativeMap("subscribeSourceAdded")
    return nativeObserver.subscribeSourceAdded(sourceAddedCallback)
  }

  /**
   * Subscribes to `SourceRemoved` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param sourceRemovedCallback
   */
  override fun subscribeSourceRemoved(sourceRemovedCallback: SourceRemovedCallback): Cancelable {
    checkNativeMap("subscribeSourceRemoved")
    return nativeObserver.subscribeSourceRemoved(sourceRemovedCallback)
  }

  /**
   * Subscribes to `StyleImageMissing` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param styleImageMissingCallback
   */
  override fun subscribeStyleImageMissing(styleImageMissingCallback: StyleImageMissingCallback): Cancelable {
    checkNativeMap("subscribeStyleImageMissing")
    return nativeObserver.subscribeStyleImageMissing(styleImageMissingCallback)
  }

  /**
   * Subscribes to `StyleImageRemoveUnused` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param styleImageRemoveUnusedCallback
   */
  override fun subscribeStyleImageRemoveUnused(styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback): Cancelable {
    checkNativeMap("subscribeStyleImageRemoveUnused")
    return nativeObserver.subscribeStyleImageRemoveUnused(styleImageRemoveUnusedCallback)
  }

  /**
   * Subscribes to `CameraChanged` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param cameraChangedCallback
   */
  override fun subscribeCameraChanged(cameraChangedCallback: CameraChangedCallback): Cancelable {
    checkNativeMap("subscribeCameraChanged")
    return nativeObserver.subscribeCameraChanged(cameraChangedCallback)
  }

  /**
   * Subscribes to `RenderFrameStarted` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param renderFrameStartedCallback
   */
  override fun subscribeRenderFrameStarted(renderFrameStartedCallback: RenderFrameStartedCallback): Cancelable {
    checkNativeMap("subscribeRenderFrameStarted")
    return nativeObserver.subscribeRenderFrameStarted(renderFrameStartedCallback)
  }

  /**
   * Subscribes to `RenderFrameFinished` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param renderFrameFinishedCallback
   */
  override fun subscribeRenderFrameFinished(renderFrameFinishedCallback: RenderFrameFinishedCallback): Cancelable {
    checkNativeMap("subscribeRenderFrameFinished")
    return nativeObserver.subscribeRenderFrameFinished(renderFrameFinishedCallback)
  }

  /**
   * Subscribes to `ResourceRequest` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param resourceRequestCallback
   */
  override fun subscribeResourceRequest(resourceRequestCallback: ResourceRequestCallback): Cancelable {
    checkNativeMap("subscribeResourceRequest")
    return nativeObserver.subscribeResourceRequest(resourceRequestCallback)
  }

  /**
   * Subscribes to an experimental `GenericEvent` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param eventName
   * @param genericEventCallback
   */
  @MapboxExperimental
  override fun subscribeGenericEvent(
    eventName: String,
    genericEventCallback: GenericEventCallback
  ): Cancelable {
    checkNativeMap("subscribeGenericEvent")
    return nativeObserver.subscribeGenericEvent(eventName, genericEventCallback)
  }

  /**
   * Add a listener that's going to be invoked whenever map camera changes.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeCameraChange] instead.",
    replaceWith = ReplaceWith("subscribeCameraChanged(cameraChangedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    checkNativeMap("addOnCameraChangeListener")
    nativeObserver.addOnCameraChangeListener(onCameraChangeListener)
  }

  /**
   * Remove the camera change listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeCameraChange] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    checkNativeMap("removeOnCameraChangeListener")
    nativeObserver.removeOnCameraChangeListener(onCameraChangeListener)
  }

  // Map events
  /**
   * Add a listener that's going to be invoked whenever map has entered the idle state.
   *
   * The Map is in the idle state when there are no ongoing transitions and the Map has rendered all
   * available tiles.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeMapIdle] instead.",
    replaceWith = ReplaceWith("subscribeMapIdle(mapIdleCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    checkNativeMap("addOnMapIdleListener")
    nativeObserver.addOnMapIdleListener(onMapIdleListener)
  }

  /**
   * Remove the map idle listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeMapIdle] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    checkNativeMap("removeOnMapIdleListener")
    nativeObserver.removeOnMapIdleListener(onMapIdleListener)
  }

  /**
   * Add a listener that's going to be invoked whenever there's a map load error.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeMapLoadingError] instead.",
    replaceWith = ReplaceWith("subscribeMapLoadingError(mapLoadingErrorCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    checkNativeMap("addOnMapLoadErrorListener")
    nativeObserver.addOnMapLoadErrorListener(onMapLoadErrorListener)
  }

  /**
   * Remove the map error listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeMapLoadingError] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    checkNativeMap("removeOnMapLoadErrorListener")
    nativeObserver.removeOnMapLoadErrorListener(onMapLoadErrorListener)
  }

  /**
   * Add a listener that's going to be invoked whenever the Map's style has been fully loaded, and
   * the Map has rendered all visible tiles.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeMapLoaded] instead.",
    replaceWith = ReplaceWith("subscribeMapLoaded(mapLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    checkNativeMap("addOnMapLoadedListener")
    nativeObserver.addOnMapLoadedListener(onMapLoadedListener)
  }

  /**
   * Remove the map loaded listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeMapLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    checkNativeMap("removeOnMapLoadedListener")
    nativeObserver.removeOnMapLoadedListener(onMapLoadedListener)
  }

  // Render frame events
  /**
   * Add a listener that's going to be invoked whenever the Map started rendering a frame.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeRenderFrameStarted] instead.",
    replaceWith = ReplaceWith("subscribeRenderFrameStarted(renderFrameStartedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    checkNativeMap("addOnRenderFrameStartedListener")
    nativeObserver.addOnRenderFrameStartedListener(onRenderFrameStartedListener)
  }

  /**
   * Remove the render frame started listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeRenderFrameStarted] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    checkNativeMap("removeOnRenderFrameStartedListener")
    nativeObserver.removeOnRenderFrameStartedListener(onRenderFrameStartedListener)
  }

  /**
   * Add a listener that's going to be invoked whenever the Map finished rendering a frame.
   *
   * The render-mode value tells whether the Map has all data ("full") required to render the visible viewport.
   * The needs-repaint value provides information about ongoing transitions that trigger Map repaint.
   * The placement-changed value tells if the symbol placement has been changed in the visible viewport.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeRenderFrameFinished] instead.",
    replaceWith = ReplaceWith("subscribeRenderFrameFinished(renderFrameFinishedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    checkNativeMap("addOnRenderFrameFinishedListener")
    nativeObserver.addOnRenderFrameFinishedListener(onRenderFrameFinishedListener)
  }

  /**
   * Remove the render frame finished listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeRenderFrameFinished] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    checkNativeMap("removeOnRenderFrameFinishedListener")
    nativeObserver.removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener)
  }

  // Source events
  /**
   * Add a listener that's going to be invoked whenever a source has been added with StyleManager#addStyleSource
   * runtime API.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeSourceAdded] instead.",
    replaceWith = ReplaceWith("subscribeSourceAdded(sourceAddedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    checkNativeMap("addOnSourceAddedListener")
    nativeObserver.addOnSourceAddedListener(onSourceAddedListener)
  }

  /**
   * Remove the source added listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeSourceAdded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    checkNativeMap("removeOnSourceAddedListener")
    nativeObserver.removeOnSourceAddedListener(onSourceAddedListener)
  }

  /**
   * Add a listener that's going to be invoked whenever the source data has been loaded.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeSourceDataLoaded] instead.",
    replaceWith = ReplaceWith("subscribeSourceDataLoaded(sourceDataLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    checkNativeMap("addOnSourceDataLoadedListener")
    nativeObserver.addOnSourceDataLoadedListener(onSourceDataLoadedListener)
  }

  /**
   * Remove the source data loaded listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeSourceDataLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    checkNativeMap("removeOnSourceDataLoadedListener")
    nativeObserver.removeOnSourceDataLoadedListener(onSourceDataLoadedListener)
  }

  /**
   * Add a listener that's going to be invoked whenever a source has been removed with StyleManager#removeStyleSource
   * runtime API.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeSourceRemoved] instead.",
    replaceWith = ReplaceWith("subscribeSourceRemoved(sourceRemovedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    checkNativeMap("addOnSourceRemovedListener")
    nativeObserver.addOnSourceRemovedListener(onSourceRemovedListener)
  }

  /**
   * Remove the source removed listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeSourceRemoved] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    checkNativeMap("removeOnSourceRemovedListener")
    nativeObserver.removeOnSourceRemovedListener(onSourceRemovedListener)
  }

  // Style events
  /**
   * Add a listener that's going to be invoked whenever the requested style has been fully loaded,
   * including the style specified sprite and sources.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleLoaded] instead.",
    replaceWith = ReplaceWith("subscribeStyleLoaded(styleLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    checkNativeMap("addOnStyleLoadedListener")
    nativeObserver.addOnStyleLoadedListener(onStyleLoadedListener)
  }

  /**
   * Remove the style loaded listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    checkNativeMap("removeOnStyleLoadedListener")
    nativeObserver.removeOnStyleLoadedListener(onStyleLoadedListener)
  }

  /**
   * Add a listener that's going to be invoked whenever the requested style data been loaded.
   * The 'type' property defines what kind of style data has been loaded.
   *
   * This event may be useful when application needs to modify style layers or sources and add or remove sources
   * before style is fully loaded.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleDataLoaded] instead.",
    replaceWith = ReplaceWith("subscribeStyleDataLoaded(styleDataLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    checkNativeMap("addOnStyleDataLoadedListener")
    nativeObserver.addOnStyleDataLoadedListener(onStyleDataLoadedListener)
  }

  /**
   * Remove the style data loaded listener
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleDataLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    checkNativeMap("removeOnStyleDataLoadedListener")
    nativeObserver.removeOnStyleDataLoadedListener(onStyleDataLoadedListener)
  }

  /**
   * Add a listener that's going to be invoked whenever a style has a missing image.
   *
   * This event is emitted when the Map renders visible tiles and one of the required images is
   * missing in the sprite sheet.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleImageMissing] instead.",
    replaceWith = ReplaceWith("subscribeStyleImageMissing(styleImageMissingCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    checkNativeMap("addOnStyleImageMissingListener")
    nativeObserver.addOnStyleImageMissingListener(onStyleImageMissingListener)
  }

  /**
   * Remove the style image missing listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleImageMissing] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    checkNativeMap("removeOnStyleImageMissingListener")
    nativeObserver.removeOnStyleImageMissingListener(onStyleImageMissingListener)
  }

  /**
   * Add a listener that's going to be invoked whenever an image added to the Style is no longer
   * needed and can be removed using StyleManager#removeStyleImage method.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleImageRemoveUnused] instead.",
    replaceWith = ReplaceWith("subscribeStyleImageRemoveUnused(styleImageRemoveUnusedCallback)"),
    level = DeprecationLevel.WARNING
  )
  override fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    checkNativeMap("addOnStyleImageUnusedListener")
    nativeObserver.addOnStyleImageUnusedListener(onStyleImageUnusedListener)
  }

  /**
   * Remove the style image unused listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleImageRemoveUnused] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  override fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    checkNativeMap("removeOnStyleImageUnusedListener")
    nativeObserver.removeOnStyleImageUnusedListener(onStyleImageUnusedListener)
  }

  /**
   * Triggers a repaint of the map.
   */
  fun triggerRepaint() {
    checkNativeMap("triggerRepaint")
    nativeMap.triggerRepaint()
  }

  /**
   * Get the map's current free camera options. After mutation, it should be set back to the map.
   * @return The current free camera options.
   */
  override fun getFreeCameraOptions(): FreeCameraOptions {
    checkNativeMap("getFreeCameraOptions")
    return nativeMap.getFreeCameraOptions()
  }

  /**
   * Sets the map view with the free camera options.
   *
   * FreeCameraOptions provides more direct access to the underlying camera entity.
   * For backwards compatibility the state set using this API must be representable with
   * `CameraOptions` as well. Parameters are clamped to a valid range or discarded as invalid
   * if the conversion to the pitch and bearing presentation is ambiguous. For example orientation
   * can be invalid if it leads to the camera being upside down or the quaternion has zero length.
   *
   * @param freeCameraOptions The free camera options to set.
   */
  override fun setCamera(freeCameraOptions: FreeCameraOptions) {
    checkNativeMap("setCamera")
    nativeMap.setCamera(freeCameraOptions)
  }

  /**
   * Get elevation for given coordinate. Value is available only for the visible region on the screen, if terrain (DEM) tile is available.
   *
   * @param coordinate defined as longitude-latitude pair.
   *
   * @return Elevation (in meters) multiplied by current terrain exaggeration, or empty if elevation for the coordinate is not available.
   */
  fun getElevation(coordinate: Point): Double? {
    checkNativeMap("getElevation")
    return nativeMap.getElevation(coordinate)
  }

  /**
   * The tile cache budget hint to be used by the map. The budget can be given in
   * tile units or in megabytes. A Map will do the best effort to keep memory
   * allocations for a non essential resources within the budget.
   *
   * If tile cache budget in megabytes is set, the engine will try to use ETC1 texture compression
   * for raster layers, therefore, raster images with alpha channel will be rendered incorrectly.
   *
   * If null is set, the tile cache budget in tile units will be dynamically calculated based on
   * the current viewport size.
   *
   * @param tileCacheBudget The tile cache budget hint to be used by the Map.
   */
  fun setTileCacheBudget(tileCacheBudget: TileCacheBudget?) {
    checkNativeMap("setTileCacheBudget")
    nativeMap.setTileCacheBudget(tileCacheBudget)
  }

  /**
   * Sets whether multiple copies of the world will be rendered side by side beyond -180 and 180 degrees longitude.
   * If disabled, when the map is zoomed out far enough that a single representation of the world
   * does not fill the map's entire container, there will be blank space beyond 180 and -180 degrees longitude.
   * In this case, features that cross 180 and -180 degrees longitude will be cut in two
   * (with one portion on the right edge of the map and the other on the left edge of the map) at every zoom level.
   *
   * By default, renderWorldCopies is set to `true`.
   *
   * @param renderWorldCopies The `boolean` value defining whether rendering world copies is going to be enabled or not.
   */
  fun setRenderWorldCopies(renderWorldCopies: Boolean) {
    checkNativeMap("setRenderWorldCopies")
    nativeMap.setRenderWorldCopies(renderWorldCopies)
  }

  /**
   * Returns whether multiple copies of the world are being rendered side by side beyond -180 and 180 degrees longitude.
   *
   * @return `true` if rendering world copies is enabled, `false` otherwise.
   */
  fun getRenderWorldCopies(): Boolean {
    checkNativeMap("getRenderWorldCopies")
    return nativeMap.getRenderWorldCopies()
  }

  /**
   * Prepares the drag gesture to use the provided screen coordinate as a pivot point.
   * This function should be called each time when user starts a dragging action (e.g. by clicking on the map).
   * The following dragging will be relative to the pivot.
   *
   * @param point The pivot coordinate, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   */
  override fun dragStart(point: ScreenCoordinate) {
    checkNativeMap("dragStart")
    nativeMap.dragStart(point)
  }

  /**
   * Ends the ongoing drag gesture.
   * This function should be called always after the user has ended a drag gesture initiated by `dragStart`.
   */
  override fun dragEnd() {
    checkNativeMap("dragEnd")
    nativeMap.dragEnd()
  }

  /**
   * Calculates target point where camera should move after drag. The method should be called after `dragStart` and before `dragEnd`.
   *
   * @param fromPoint The point to drag the map from, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   * @param toPoint The point to drag the map to, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   *
   * @return Returns the camera options object showing end point
   */
  override fun getDragCameraOptions(
    fromPoint: ScreenCoordinate,
    toPoint: ScreenCoordinate
  ): CameraOptions {
    checkNativeMap("getDragCameraOptions")
    return nativeMap.getDragCameraOptions(fromPoint, toPoint)
  }

  /**
   * Call extension function on [CameraAnimationsPlugin].
   * In most cases should not be called directly.
   */
  override fun cameraAnimationsPlugin(function: (CameraAnimationsPlugin.() -> Any?)): Any? {
    cameraAnimationsPlugin?.let {
      return function.invoke(it)
    }
    logW(
      TAG,
      "Either camera plugin is not added to the MapView or MapView has already been destroyed;" +
        " MapboxMap camera extension functions are no-op."
    )
    return null
  }

  /**
   * Call extension function on [GesturesPlugin].
   * In most cases should not be called directly.
   */
  override fun gesturesPlugin(function: (GesturesPlugin.() -> Any?)): Any? {
    gesturesPlugin?.let {
      return function.invoke(it)
    }
    logW(
      TAG,
      "Either gestures plugin is not added to the MapView or MapView has already been destroyed;" +
        " MapboxMap gestures extension functions are no-op."
    )
    return null
  }

  internal fun onDestroy() {
    cameraAnimationsPlugin = null
    gesturesPlugin = null
    styleObserver.onDestroy()
    isMapValid = false
  }

  /**
   * Adds a model to be used in the style. This API can also be used for updating
   * a model. If the model for a given `modelId` was already added, it gets replaced by the new model.
   *
   * The model can be used in `model-id` property in model layer.
   *
   * @param modelId An identifier of the model.
   * @param modelUri A URI for the model.
   */
  @MapboxExperimental
  fun addStyleModel(modelId: String, modelUri: String) {
    checkNativeMap("addStyleModel")
    val expected = nativeMap.addStyleModel(modelId, modelUri)
    if (expected.isError) {
      logE(TAG, expected.error ?: "Error occurred in MapboxMap.addStyleModel!")
    }
  }

  /**
   * Removes a model from the style.
   *
   * @param modelId The identifier of the model to remove.
   */
  @MapboxExperimental
  fun removeStyleModel(modelId: String) {
    checkNativeMap("removeStyleModel")
    val expected = nativeMap.removeStyleModel(modelId)
    if (expected.isError) {
      logE(TAG, expected.error ?: "Error occurred in MapboxMap.removeStyleModel!")
    }
  }

  /**
   * Checks whether a model exists.
   *
   * @param modelId The identifier of the model.
   *
   * @return True if model exists, false otherwise.
   */
  @MapboxExperimental
  fun hasStyleModel(modelId: String): Boolean {
    checkNativeMap("hasStyleModel")
    return nativeMap.hasStyleModel(modelId)
  }

  /**
   * Returns tileIDs that cover current map camera
   *
   * Note! This is an experimental API and behavior might change in future.
   *
   * @param tileCoverOptions Options for the tile cover method
   * @param cameraOptions This is an extra parameter for future use. Has no effect for now.
   */
  @MapboxExperimental
  fun tileCover(
    tileCoverOptions: TileCoverOptions,
    cameraOptions: CameraOptions?
  ): List<CanonicalTileID> {
    checkNativeMap("tileCover")
    return nativeMap.tileCover(tileCoverOptions, cameraOptions)
  }

  /**
   * Returns attributions for the data used by the Map's style.
   *
   * @return An array of attributions for the data sources used by the Map's style.
   */
  fun getAttributions(): List<String> {
    checkNativeMap("getAttributions")
    return nativeMap.getAttributions()
  }

  internal fun addViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions
  ): Expected<String, None> {
    checkNativeMap("addViewAnnotation")
    return nativeMap.addViewAnnotation(viewId, options)
  }

  internal fun updateViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions
  ): Expected<String, None> {
    checkNativeMap("updateViewAnnotation")
    return nativeMap.updateViewAnnotation(viewId, options)
  }

  internal fun removeViewAnnotation(viewId: String): Expected<String, None> {
    checkNativeMap("removeViewAnnotation")
    return nativeMap.removeViewAnnotation(viewId)
  }

  internal fun getViewAnnotationOptions(identifier: String): Expected<String, ViewAnnotationOptions> {
    checkNativeMap("getViewAnnotationOptions")
    return nativeMap.getViewAnnotationOptions(identifier)
  }

  internal fun setViewAnnotationPositionsUpdateListener(listener: DelegatingViewAnnotationPositionsUpdateListener?) {
    checkNativeMap("setViewAnnotationPositionsUpdateListener")
    return nativeMap.setViewAnnotationPositionsUpdateListener(listener)
  }

  private fun checkNativeMap(methodName: String, checkMainThread: Boolean = true) {
    if (checkMainThread) {
      ThreadChecker.throwIfNotMainThread()
    }
    if (!isMapValid) {
      logW(
        TAG,
        "MapboxMap object (accessing $methodName) should not be stored and used after MapView is destroyed."
      )
    }
  }

  /**
   * A convenience object to access MapboxMap's static utilities.
   */
  companion object {
    /**
     * Clears temporary map data.
     *
     * Clears temporary map data from the data path defined in the current options.
     * Useful to reduce the disk usage or in case the disk cache contains invalid data.
     *
     * Note that calling this API will affect all maps that use the same data path and does not
     * affect persistent map data like offline style packages.
     *
     * @param callback Called once the request is complete or an error occurred.
     */
    @JvmStatic
    fun clearData(callback: AsyncOperationResultCallback) {
      MapsResourceOptions.clearData(callback)
    }

    private const val TAG = "Mbgl-MapboxMap"
    internal const val QFE_SUPER_CLUSTER = "supercluster"
    internal const val QFE_LEAVES = "leaves"
    internal const val QFE_LIMIT = "limit"
    internal const val QFE_OFFSET = "offset"
    internal const val QFE_DEFAULT_LIMIT = 10L
    internal const val QFE_DEFAULT_OFFSET = 0L
    internal const val QFE_CHILDREN = "children"
    internal const val QFE_EXPANSION_ZOOM = "expansion-zoom"
  }
}