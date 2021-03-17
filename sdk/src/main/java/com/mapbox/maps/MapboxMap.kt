package com.mapbox.maps

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.plugin.PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
import com.mapbox.maps.plugin.PLUGIN_GESTURE_CLASS_NAME
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.delegates.listeners.*
import com.mapbox.maps.plugin.delegates.listeners.eventdata.StyleDataType
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import java.lang.ref.WeakReference
import java.util.*

/**
 * The general class to interact with in the Mapbox Maps SDK for Android.
 * It exposes the entry point for all methods related to the Map object.
 * You cannot instantiate [MapboxMap] object directly, rather, you must obtain one
 * from the getMapboxMap() method MapView that you have
 * added to your application.
 * <p>
 * Note: Similar to a View object, a MapboxMap should only be read and modified
 * from the main thread.
 * </p>
 *
 * @property style the map style.
 */
class MapboxMap internal constructor(
  nativeMap: MapInterface,
  private val nativeObserver: NativeObserver,
  private val pixelRatio: Float
) : MapTransformDelegate,
  MapProjectionDelegate,
  MapFeatureQueryDelegate,
  ObservableInterface,
  MapListenerDelegate,
  MapPluginExtensionsDelegate,
  MapStyleStateDelegate {

  private val nativeMapWeakRef = WeakReference(nativeMap)
  internal lateinit var style: Style

  private var terrainEnabled = false

  @VisibleForTesting(otherwise = PRIVATE)
  internal var cameraAnimationsPlugin: WeakReference<CameraAnimationsPlugin>? = null
  @VisibleForTesting(otherwise = PRIVATE)
  internal var gesturesPlugin: WeakReference<GesturesPlugin>? = null

  /**
   * Will load a new map style asynchronous from the specified URI.
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
   * @param styleUri The style URI
   * @param onStyleLoaded The OnStyleLoaded callback
   * @param onMapLoadErrorListener The OnMapLoadErrorListener callback
   */
  fun loadStyleUri(
    styleUri: String,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null
  ) {
    initializeStyleLoad(onStyleLoaded, onMapLoadErrorListener)
    nativeMapWeakRef.call { (this as StyleManagerInterface).styleURI = styleUri }
  }

  /**
   * Will load a new map style asynchronous from the specified URI.
   *
   * @param styleUri The style URI
   * @param onStyleLoaded The OnStyleLoaded callback
   */
  fun loadStyleUri(
    styleUri: String,
    onStyleLoaded: Style.OnStyleLoaded
  ) = loadStyleUri(styleUri, onStyleLoaded, null)

  /**
   * Load style JSON
   */
  fun loadStyleJSON(
    json: String,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null
  ) {
    initializeStyleLoad(onStyleLoaded, onMapLoadErrorListener)
    nativeMapWeakRef.call {
      (this as StyleManagerInterface).styleJSON = json
    }
  }

  /**
   * Load style JSON.
   */
  fun loadStyleJSON(
    json: String,
    onStyleLoaded: Style.OnStyleLoaded
  ) = loadStyleJSON(json, onStyleLoaded, null)

  /**
   * Load the style from Style Extension.
   */
  fun loadStyle(
    styleExtension: StyleContract.StyleExtension,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null
  ) {
    terrainEnabled = false
    this.loadStyleUri(
      styleExtension.styleUri,
      { style -> onFinishLoadingStyleExtension(style, styleExtension, onStyleLoaded) },
      onMapLoadErrorListener
    )
  }

  /**
   * Load the style from Style Extension.
   */
  fun loadStyle(
    styleExtension: StyleContract.StyleExtension,
    onStyleLoaded: Style.OnStyleLoaded
  ) = loadStyle(styleExtension, onStyleLoaded, null)

  /**
   * Handle the style loading from Style Extension.
   */
  internal fun onFinishLoadingStyleExtension(
    style: Style,
    styleExtension: StyleContract.StyleExtension,
    onStyleLoaded: Style.OnStyleLoaded? = null
  ) {
    this.style = style
    styleExtension.images.forEach {
      it.bindTo(style)
    }
    styleExtension.sources.forEach {
      it.bindTo(style)
    }
    styleExtension.layers.forEach {
      it.first.bindTo(style, it.second)
    }
    styleExtension.light?.bindTo(style)
    styleExtension.terrain?.let {
      terrainEnabled = true
      it.bindTo(style)
    }
    onStyleLoaded?.onStyleLoaded(style)
  }

  private fun initializeStyleLoad(
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null
  ) {
    if (::style.isInitialized) {
      style.fullyLoaded = false
    }
    onMapLoadErrorListener?.let {
      addOnMapLoadErrorListener(it)
    }
    addOnStyleDataLoadedListener(
      object : OnStyleDataLoadedListener {
        override fun onStyleDataLoaded(type: StyleDataType) {
          if (type == StyleDataType.STYLE) {
            onFinishLoadingStyle(onStyleLoaded, onMapLoadErrorListener)
            removeOnStyleDataLoadedListener(this)
          }
        }
      }
    )
  }

  /**
   * Handle the style loading through native map
   */
  internal fun onFinishLoadingStyle(
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null
  ) {
    nativeMapWeakRef.get()?.let {
      style = Style(it as StyleManagerInterface, pixelRatio)
      // notify the listener provided with the style setter
      onStyleLoaded?.onStyleLoaded(style)

      // notify style getters
      for (styleGetter in nativeObserver.awaitingStyleGetters) {
        styleGetter.onStyleLoaded(style)
      }
      nativeObserver.awaitingStyleGetters.clear()
    }
    onMapLoadErrorListener?.let {
      addOnMapLoadErrorListener(it)
    }
  }

  /**
   * Get the Style of the map asynchronously.
   *
   * @param onStyleLoaded the callback to be invoked when the style is fully loaded
   */
  fun getStyle(onStyleLoaded: Style.OnStyleLoaded) {
    if (::style.isInitialized) {
      if (style.fullyLoaded) {
        // style has loaded, notify callback immediately
        onStyleLoaded.onStyleLoaded(style)
      } else {
        // style load is occurring now, add callback
        nativeObserver.awaitingStyleGetters.add(onStyleLoaded)
      }
    } else {
      // no style has loaded yet, add callback
      nativeObserver.awaitingStyleGetters.add(onStyleLoaded)
    }
  }

  /**
   * Get the Style of the map synchronously, will return null is style is not loaded yet.
   */
  fun getStyle(): Style? {
    if (::style.isInitialized && style.fullyLoaded) {
      // style has loaded, return it immediately
      return style
    }
    return null
  }

  /**
   * Get the ResourceOptions the map was initialized with.
   *
   * @return resourceOptions The resource options of the map
   */
  fun getResourceOptions(): ResourceOptions {
    return nativeMapWeakRef.call { this.resourceOptions }
  }

  /**
   * Changes the map view by any combination of center, zoom, bearing, and pitch, without an animated transition.
   * The map will retain its current values for any details not passed via the camera options argument.
   *
   * @param cameraOptions New camera options
   */
  override fun setCamera(cameraOptions: CameraOptions) =
    nativeMapWeakRef.call { this.setCamera(cameraOptions) }

  /**
   * Get the current camera options given an optional padding.
   *
   * @param edgeInsets The optional padding
   */
  override fun getCameraOptions(edgeInsets: EdgeInsets?): CameraOptions =
    nativeMapWeakRef.call { this.getCameraOptions(edgeInsets) }

  /**
   * Notify map about gesture being in progress.
   *
   * @param inProgress True if gesture is in progress
   */
  override fun setGestureInProgress(inProgress: Boolean) =
    nativeMapWeakRef.call { this.isGestureInProgress = inProgress }

  /**
   * Returns if a gesture is in progress.
   *
   * @return Returns if a gesture is in progress
   */
  override fun isGestureInProgress(): Boolean = nativeMapWeakRef.call { this.isGestureInProgress }

  /**
   * Set the map north orientation
   *
   * @param northOrientation The map north orientation to set
   */
  override fun setNorthOrientation(northOrientation: NorthOrientation) =
    nativeMapWeakRef.call { this.setNorthOrientation(northOrientation) }

  /**
   * Set the map constrain mode
   *
   * @param constrainMode The map constraint mode to set
   */
  override fun setConstrainMode(constrainMode: ConstrainMode) =
    nativeMapWeakRef.call { this.setConstrainMode(constrainMode) }

  /**
   * Set the map viewport mode
   *
   * @param viewportMode The map viewport mode to set
   */
  override fun setViewportMode(viewportMode: ViewportMode) =
    nativeMapWeakRef.call { this.setViewportMode(viewportMode) }

  /**
   * Set the map bounds.
   *
   * @param boundOptions the map bound options
   */
  override fun setBounds(boundOptions: BoundOptions) =
    nativeMapWeakRef.call { this.bounds = boundOptions }

  /**
   * Get the map bounds options.
   *
   * @return Returns the map bounds options
   */
  override fun getBounds(): BoundOptions = nativeMapWeakRef.call { this.bounds }

  /**
   * Tells the map rendering engine that the animation is currently performed by the
   * user (e.g. with a `setCamera()` calls series). It adjusts the engine for the animation use case.
   * In particular, it brings more stability to symbol placement and rendering.
   *
   * @param inProgress Bool representing if user animation is in progress
   */
  override fun setUserAnimationInProgress(inProgress: Boolean) {
    nativeMapWeakRef.call { this.isUserAnimationInProgress = inProgress }
  }

  /**
   * Returns if user animation is currently in progress.
   *
   * @return Return true if a user animation is in progress.
   */
  override fun isUserAnimationInProgress(): Boolean {
    return nativeMapWeakRef.call { this.isUserAnimationInProgress }
  }

  /**
   * Set the prefetch zoom delta
   *
   * @param delta The prefetch zoom delta
   */
  fun setPrefetchZoomDelta(delta: Byte) =
    nativeMapWeakRef.call { this.prefetchZoomDelta = delta }

  /**
   * Get the prefetch zoom delta
   *
   * @return Returns the prefetch zoom delta
   */
  fun getPrefetchZoomDelta(): Byte = nativeMapWeakRef.call { this.prefetchZoomDelta }

  /**
   * Get map options.
   *
   * @return Returns map options
   */
  override fun getMapOptions(): MapOptions = nativeMapWeakRef.call { this.mapOptions }

  /**
   * Get minimum zoom.
   *
   * @return Zoom value.
   */
  override fun getMinZoom() = nativeMapWeakRef.call { this.minZoom }

  /**
   * Get maximum zoom.
   *
   * @return Zoom value.
   */
  override fun getMaxZoom() = nativeMapWeakRef.call { this.maxZoom }

  /**
   * The current zoom factor applied on the map meaning 2 ^ Current zoom
   *
   * @return Scale value.
   */
  override fun getScale() = nativeMapWeakRef.call { this.scale }

  /**
   * Gets the size of the map.
   *
   * @return size The size of the map in MapOptions#size platform pixels
   */
  override fun getSize() = nativeMapWeakRef.call { this.size }

  /**
   * Get debug options
   */
  fun getDebug(): List<MapDebugOptions> = nativeMapWeakRef.call { this.debug }

  /**
   * Set debug options
   */
  fun setDebug(debugOptions: List<MapDebugOptions>, enabled: Boolean) =
    nativeMapWeakRef.call { this.setDebug(debugOptions, enabled) }

  /**
   * Dump debug logs
   */
  fun dumpDebugLogs() = nativeMapWeakRef.call { this.dumpDebugLogs() }

  /**
   * Convert to a camera options from a given LatLngBounds, padding, bearing and pitch values.
   *
   * @param coordinateBounds The LatLngBounds to take in account when converting
   * @param padding The additional padding to take in account when converting
   * @param bearing The optional bearing to take in account when converting
   * @param pitch The optional pitch to take in account when converting
   *
   * @return Returns the converted camera options
   */
  override fun cameraForCoordinateBounds(
    coordinateBounds: CoordinateBounds,
    padding: EdgeInsets,
    bearing: Double?,
    pitch: Double?
  ): CameraOptions =
    nativeMapWeakRef.call {
      this.cameraForCoordinateBounds(
        coordinateBounds,
        padding,
        bearing,
        pitch
      )
    }

  /**
   * Convert to a camera options from a given list of points, padding, bearing and pitch values.
   *
   * @param coordinates The List of coordinates to take in account when converting
   * @param padding The additional padding to take in account when converting
   * @param bearing The optional bearing to take in account when converting
   * @param pitch The optional pitch to take in account when converting
   *
   * @return Returns the converted camera options
   */
  override fun cameraForCoordinates(
    coordinates: List<Point>,
    padding: EdgeInsets,
    bearing: Double?,
    pitch: Double?
  ): CameraOptions =
    nativeMapWeakRef.call { this.cameraForCoordinates(coordinates, padding, bearing, pitch) }

  /**
   * Convert to a camera options from a given geometry, padding, bearing and pitch values.
   *
   * @param geometry The geometry to take in account when converting
   * @param padding The additional padding to take in account when converting
   * @param bearing The optional bearing to take in account when converting
   * @param pitch The optional pitch to take in account when converting
   *
   * @return Returns the converted camera options
   */
  override fun cameraForGeometry(
    geometry: Geometry,
    padding: EdgeInsets,
    bearing: Double?,
    pitch: Double?
  ): CameraOptions =
    nativeMapWeakRef.call { this.cameraForGeometry(geometry, padding, bearing, pitch) }

  /**
   * Convert to a LatLngBounds from a given camera options.
   *
   * @param cameraOptions The camera options to take in account when converting
   *
   * @return Returns the converted LatLngBounds
   */
  override fun coordinateBoundsForCamera(cameraOptions: CameraOptions): CoordinateBounds =
    nativeMapWeakRef.call { this.coordinateBoundsForCamera(cameraOptions) }

  /**
   *  Returns the coordinate bounds and zoom for a given camera.
   *
   * Note that if the given camera shows the antimeridian, the returned wrapped bounds
   * might not represent the minimum bounding box.
   *
   * See also {@link #coordinateBoundsZoomForCameraUnwrapped}
   *
   *  @return Returns the coordinate bounds and zoom for a given camera.
   */
  override fun coordinateBoundsZoomForCamera(cameraOptions: CameraOptions): CoordinateBoundsZoom =
    nativeMapWeakRef.call { this.coordinateBoundsZoomForCamera(cameraOptions) }

  /**
   * Returns the unwrapped coordinate bounds and zoom for a given camera.
   *
   * This method is particularly useful, if the camera shows the antimeridian.
   *
   *  @return Returns the unwrapped coordinate bounds and zoom for a given camera.
   */
  override fun coordinateBoundsZoomForCameraUnwrapped(cameraOptions: CameraOptions): CoordinateBoundsZoom =
    nativeMapWeakRef.call { this.coordinateBoundsZoomForCameraUnwrapped(cameraOptions) }

  /**
   * Calculate a screen coordinate that corresponds to a geographical coordinate
   * (i.e., longitude-latitude pair).
   *
   * The screen coordinate is in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * @param point A geographical coordinate on the map to convert to a screen coordinate.
   *
   * @return Returns a screen coordinate on the screen in [MapOptions.size] platform pixels.
   */
  override fun pixelForCoordinate(point: Point): ScreenCoordinate =
    nativeMapWeakRef.call { this.pixelForCoordinate(point) }

  /**
   * Calculate screen coordinates that corresponds to geographical coordinates
   * (i.e., longitude-latitude pair).
   *
   * The screen coordinates are in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * @param coordinates A batch of geographical coordinates on the map to convert to screen coordinates.
   *
   * @return Returns a batch of screen coordinates on the screen in [MapOptions.size] platform pixels.
   */
  override fun pixelsForCoordinates(coordinates: List<Point>): List<ScreenCoordinate> =
    nativeMapWeakRef.call { this.pixelsForCoordinates(coordinates) }

  /**
   * Calculate a geographical coordinate(i.e., longitude-latitude pair) that corresponds
   * to a screen coordinate.
   *
   * The screen coordinate is in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * @param screenCoordinate A screen coordinate represented by x y coordinates.
   *
   * @return Returns a geographical coordinate corresponding to the x y coordinates
   * on the screen.
   */
  override fun coordinateForPixel(screenCoordinate: ScreenCoordinate): Point =
    nativeMapWeakRef.call { this.coordinateForPixel(screenCoordinate) }

  /**
   * Calculate geographical coordinates(i.e., longitude-latitude pair) that corresponds
   * to screen coordinates.
   *
   * The screen coordinates are in [MapOptions.size] platform pixels relative to the top left
   * of the map (not of the whole screen).
   *
   * @param pixels A batch of screen coordinates on the screen in [MapOptions.size] platform pixels.
   *
   * @return Returns a batch of geographical coordinates corresponding to the screen coordinates
   * on the screen.
   */
  override fun coordinatesForPixels(pixels: List<ScreenCoordinate>): List<Point> =
    nativeMapWeakRef.call { this.coordinatesForPixels(pixels) }

  /**
   * Calculate distance spanned by one pixel at the specified latitude
   * and zoom level.
   *
   * @param latitude The latitude for which to return the value
   * @param zoom The zoom level
   *
   * @return Returns the distance measured in meters.
   */
  override fun getMetersPerPixelAtLatitude(latitude: Double, zoom: Double): Double =
    Projection.getMetersPerPixelAtLatitude(latitude, zoom)

  /**
   * Calculate distance spanned by one pixel at the specified latitude
   * at current zoom level.
   *
   * @param latitude The latitude for which to return the value
   *
   * @return Returns the distance measured in meters.
   */
  override fun getMetersPerPixelAtLatitude(latitude: Double): Double =
    Projection.getMetersPerPixelAtLatitude(latitude, getCameraOptions(null).zoom ?: 0.0)

  /**
   * Calculate Spherical Mercator ProjectedMeters coordinates.
   *
   * @param point A longitude-latitude pair for which to calculate
   * ProjectedMeters coordinates
   *
   * @return Returns Spherical Mercator ProjectedMeters coordinates
   */
  override fun projectedMetersForCoordinate(point: Point): ProjectedMeters =
    Projection.projectedMetersForCoordinate(point)

  /**
   * Calculate a longitude-latitude pair for a Spherical Mercator projected
   * meters.
   *
   * @param projectedMeters Spherical Mercator ProjectedMeters coordinates for
   * which to calculate a longitude-latitude pair.
   *
   * @return Returns a longitude-latitude pair.
   */
  override fun coordinateForProjectedMeters(projectedMeters: ProjectedMeters): Point =
    Projection.coordinateForProjectedMeters(projectedMeters)

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
  override fun project(point: Point, zoomScale: Double): MercatorCoordinate =
    Projection.project(point, zoomScale)

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
  override fun unproject(coordinate: MercatorCoordinate, zoomScale: Double): Point =
    Projection.unproject(coordinate, zoomScale)

  /**
   * Queries the map for rendered features.
   *
   * @param shape Screen pixel coordinates (point, line string or box) to query for rendered features.
   * @param options Options for querying rendered features.
   * @param callback Callback called when the query completes
   *
   * @return An array of rendered features.
   */
  override fun queryRenderedFeatures(
    shape: List<ScreenCoordinate?>,
    options: RenderedQueryOptions,
    callback: QueryFeaturesCallback
  ) {
    nativeMapWeakRef.call {
      this.queryRenderedFeatures(shape, options, callback)
    }
  }

  /**
   * Queries the map for rendered features.
   *
   * @param box Screen box to query for rendered features.
   * @param options Options for querying rendered features.
   * @param callback Callback called when the query completes
   *
   * @return An array of rendered features.
   */
  override fun queryRenderedFeatures(
    box: ScreenBox,
    options: RenderedQueryOptions,
    callback: QueryFeaturesCallback
  ) {
    nativeMapWeakRef.call {
      this.queryRenderedFeatures(box, options, callback)
    }
  }

  /**
   * Queries the map for rendered features.
   *
   * @param pixel Screen pixel coordinate to query for rendered features.
   * @param options Options for querying rendered features.
   * @param callback Callback called when the query completes
   *
   * @return An array of rendered features.
   */
  override fun queryRenderedFeatures(
    pixel: ScreenCoordinate,
    options: RenderedQueryOptions,
    callback: QueryFeaturesCallback
  ) {
    nativeMapWeakRef.call {
      this.queryRenderedFeatures(pixel, options, callback)
    }
  }

  /**
   * Queries the map for source features.
   *
   * @param sourceId Style source identifier used to query for source features.
   * @param options Options for querying source features.
   *
   * @return An array of source features.
   */
  override fun querySourceFeatures(
    sourceId: String,
    options: SourceQueryOptions,
    callback: QueryFeaturesCallback
  ) {
    nativeMapWeakRef.call { this.querySourceFeatures(sourceId, options, callback) }
  }

  /**
   * Queries for feature extension values in a GeoJSON source.
   *
   * @param sourceIdentifier The identifier of the source to query.
   * @param feature to look for in the query.
   * @param extension e.g. supercluster.
   * @param extensionField children, leaves, or expansion-zoom.
   * @param args
   *
   * @return A feature extension value containing either a value or a feature collection.
   */
  fun queryFeatureExtensions(
    sourceIdentifier: String,
    feature: Feature,
    extension: String,
    extensionField: String,
    args: HashMap<String, Value>?,
    callback: QueryFeatureExtensionCallback
  ) {
    nativeMapWeakRef.call {
      this.queryFeatureExtensions(
        sourceIdentifier,
        feature,
        extension,
        extensionField,
        args,
        callback
      )
    }
  }

  /**
   * Update the state map of a feature within a style source.
   *
   * Update entries in the state map of a given feature within a style source. Only entries listed in the
   * \p state map will be updated. An entry in the feature state map that is not listed in \p state will
   * retain its previous value.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method migth not be
   * immediately visible using getStateFeature().
   *
   * @param sourceId Style source identifier.
   * @param sourceLayerId Style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId Identifier of the feature whose state should be updated.
   * @param state Map of entries to update with their respective new values.
   */
  fun setFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    state: Value
  ) =
    nativeMapWeakRef.call { this.setFeatureState(sourceId, sourceLayerId, featureId, state) }

  /**
   * Get the state map of a feature within a style source.
   *
   * Note that updates to feature state are asynchronous, so changes made by other methods might not be
   * immediately visible.
   *
   * @param sourceId Style source identifier.
   * @param sourceLayerId Style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId Identifier of the feature whose state should be queried.
   * @return Feature's state map or an empty map if the feature could not be found.
   */
  fun getFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    callback: QueryFeatureStateCallback
  ) {
    nativeMapWeakRef.call { this.getFeatureState(sourceId, sourceLayerId, featureId, callback) }
  }

  /**
   * Remove entries from a feature state map.
   *
   * Remove a specified entry or all entries from a feature's state map, depending on the value of stateKey.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method migth not be
   * immediately visible using getStateFeature().
   *
   * @param sourceId Style source identifier.
   * @param sourceLayerId Style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId Identifier of the feature whose state should be removed.
   * @param stateKey Key of the entry to remove. If empty, the entire state is removed.
   */
  fun removeFeatureState(
    sourceId: String,
    sourceLayerId: String?,
    featureId: String,
    stateKey: String?
  ) {
    nativeMapWeakRef.call {
      this.removeFeatureState(
        sourceId,
        sourceLayerId,
        featureId,
        stateKey
      )
    }
  }

  /**
   * Reduce memory use. Useful to call when the application gets paused or sent to background.
   */
  fun reduceMemoryUse() {
    nativeMapWeakRef.call { this.reduceMemoryUse() }
  }

  /**
   * @brief Rebinds the default frame buffer object
   *
   * @param framebufferId ID of the new frame buffer object
   */
  fun setDefaultFramebufferObject(framebufferId: Int) {
    nativeMapWeakRef.call { this.setDefaultFramebufferObject(framebufferId) }
  }

  /**
   * Subscribes an Observer to a provided list of event types.
   * Observable will hold a strong reference to an \sa Observer instance, therefore,
   * in order to stop receiving notifications, caller must call unsubscribe with an
   * \sa Observer instance used for an initial subscription.
   *
   * @param observer an \sa Observer
   * @param events an array of event types to be subscribed to.
   */
  override fun subscribe(observer: Observer, events: List<String>) {
    nativeMapWeakRef.call {
      (this as ObservableInterface).subscribe(observer, events)
    }
  }

  /**
   * Unsubscribes an Observer from a provided list of event types.
   *
   * @param observer an Observer
   * @param events an array of event types to be unsubscribed from.
   */
  override fun unsubscribe(observer: Observer, events: List<String>) {
    nativeMapWeakRef.call {
      (this as ObservableInterface).unsubscribe(observer, events)
    }
  }

  /**
   * Unsubscribes an Observer from all events.
   *
   * @param observer an Observer
   */
  override fun unsubscribe(observer: Observer) {
    nativeMapWeakRef.call {
      (this as ObservableInterface).unsubscribe(observer)
    }
  }

  /**
   * Add a listener that's going to be invoked whenever map camera changes.
   */
  override fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    nativeObserver.addOnCameraChangeListener(onCameraChangeListener)
  }

  /**
   * Remove the camera change listener.
   */
  override fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    nativeObserver.removeOnCameraChangeListener(onCameraChangeListener)
  }

  // Map events
  /**
   * Add a listener that's going to be invoked whenever map has entered the idle state.
   *
   * The Map is in the idle state when there are no ongoing transitions and the Map has rendered all
   * available tiles.
   */
  override fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    nativeObserver.addOnMapIdleListener(onMapIdleListener)
  }

  /**
   * Remove the map idle listener.
   */
  override fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    nativeObserver.removeOnMapIdleListener(onMapIdleListener)
  }

  /**
   * Add a listener that's going to be invoked whenever there's a map load error.
   */
  override fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    nativeObserver.addOnMapLoadErrorListener(onMapLoadErrorListener)
  }

  /**
   * Remove the map error listener.
   */
  override fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    nativeObserver.removeOnMapLoadErrorListener(onMapLoadErrorListener)
  }

  /**
   * Add a listener that's going to be invoked whenever the Map's style has been fully loaded, and
   * the Map has rendered all visible tiles.
   */
  override fun addOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    nativeObserver.addOnMapLoadedListener(onMapLoadedListener)
  }

  /**
   * Remove the map loaded listener.
   */
  override fun removeOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    nativeObserver.removeOnMapLoadedListener(onMapLoadedListener)
  }

  // Render frame events
  /**
   * Add a listener that's going to be invoked whenever the Map started rendering a frame.
   */
  override fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    nativeObserver.addOnRenderFrameStartedListener(onRenderFrameStartedListener)
  }

  /**
   * Remove the render frame started listener.
   */
  override fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    nativeObserver.removeOnRenderFrameStartedListener(onRenderFrameStartedListener)
  }

  /**
   * Add a listener that's going to be invoked whenever the Map finished rendering a frame.
   *
   * The render-mode value tells whether the Map has all data ("full") required to render the visible viewport.
   * The needs-repaint value provides information about ongoing transitions that trigger Map repaint.
   * The placement-changed value tells if the symbol placement has been changed in the visible viewport.
   */
  override fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    nativeObserver.addOnRenderFrameFinishedListener(onRenderFrameFinishedListener)
  }

  /**
   * Remove the render frame finished listener.
   */
  override fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    nativeObserver.removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener)
  }

  // Source events
  /**
   * Add a listener that's going to be invoked whenever a source has been added with StyleManager#addStyleSource
   * runtime API.
   */
  override fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    nativeObserver.addOnSourceAddedListener(onSourceAddedListener)
  }

  /**
   * Remove the source added listener.
   */
  override fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    nativeObserver.removeOnSourceAddedListener(onSourceAddedListener)
  }
  /**
   * Add a listener that's going to be invoked whenever the source data has been loaded.
   */
  override fun addOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    nativeObserver.addOnSourceDataLoadedListener(onSourceDataLoadedListener)
  }

  /**
   * Remove the source data loaded listener.
   */
  override fun removeOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    nativeObserver.removeOnSourceDataLoadedListener(onSourceDataLoadedListener)
  }

  /**
   * Add a listener that's going to be invoked whenever a source has been removed with StyleManager#removeStyleSource
   * runtime API.
   */
  override fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    nativeObserver.addOnSourceRemovedListener(onSourceRemovedListener)
  }

  /**
   * Remove the source removed listener.
   */
  override fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    nativeObserver.removeOnSourceRemovedListener(onSourceRemovedListener)
  }

  // Style events
  /**
   * Add a listener that's going to be invoked whenever the requested style has been fully loaded,
   * including the style specified sprite and sources.
   */
  override fun addOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    nativeObserver.addOnStyleLoadedListener(onStyleLoadedListener)
  }

  /**
   * Remove the style loaded listener.
   */
  override fun removeOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    nativeObserver.removeOnStyleLoadedListener(onStyleLoadedListener)
  }

  /**
   * Add a listener that's going to be invoked whenever the requested style data been loaded.
   * The 'type' property defines what kind of style data has been loaded.
   *
   * This event may be useful when application needs to modify style layers or sources and add or remove sources
   * before style is fully loaded.
   */
  override fun addOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    nativeObserver.addOnStyleDataLoadedListener(onStyleDataLoadedListener)
  }

  /**
   * Remove the style data loaded listener
   */
  override fun removeOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    nativeObserver.removeOnStyleDataLoadedListener(onStyleDataLoadedListener)
  }

  /**
   * Add a listener that's going to be invoked whenever a style has a missing image.
   *
   * This event is emitted when the Map renders visible tiles and one of the required images is
   * missing in the sprite sheet.
   */
  override fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    nativeObserver.addOnStyleImageMissingListener(onStyleImageMissingListener)
  }

  /**
   * Remove the style image missing listener.
   */
  override fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    nativeObserver.removeOnStyleImageMissingListener(onStyleImageMissingListener)
  }

  /**
   * Add a listener that's going to be invoked whenever an image added to the Style is no longer
   * needed and can be removed using StyleManager#removeStyleImage method.
   */
  override fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    nativeObserver.addOnStyleImageUnusedListener(onStyleImageUnusedListener)
  }

  /**
   * Remove the style image unused listener.
   */
  override fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    nativeObserver.removeOnStyleImageUnusedListener(onStyleImageUnusedListener)
  }

  /**
   * Triggers a repaint of the map.
   */
  fun triggerRepaint() {
    nativeMapWeakRef.call { this.triggerRepaint() }
  }

  /**
   * Get the map's current free camera options. After mutation, it should be set back to the map.
   * @return The current free camera options.
   */
  fun getFreeCameraOptions() = nativeMapWeakRef.call { this.freeCameraOptions }

  /**
   * Sets the map view with the free camera options.
   *
   * FreeCameraOptions provides more direct access to the underlying camera entity.
   * For backwards compatibility the state set using this API must be representable with
   * `CameraOptions` as well. Parameters are clamped to a valid range or discarded as invalid
   * if the conversion to the pitch and bearing presentation is ambiguous. For example orientation
   * can be invalid if it leads to the camera being upside down or the quaternion has zero length.
   *
   * @param options The free camera options to set.
   */
  fun setCamera(options: FreeCameraOptions) {
    nativeMapWeakRef.call { this.setCamera(options) }
  }

  /**
   * Get elevation for given coordinate. Value is available only for the visible region on the screen, if terrain (DEM) tile is available.
   *
   * @param coordinate defined as longitude-latitude pair.
   *
   * @return Elevation (in meters) multiplied by current terrain exaggeration, or empty if elevation for the coordinate is not available.
   */
  fun getElevation(coordinate: Point) = nativeMapWeakRef.call { this.getElevation(coordinate) }

  /**
   * Returns if the style has been fully loaded.
   */
  override fun isFullyLoaded(): Boolean {
    return style.isFullyLoaded()
  }

  /**
   * Prepares the drag gesture to use the provided screen coordinate as a pivot point.
   * This function should be called each time when user starts a dragging action (e.g. by clicking on the map).
   * The following dragging will be relative to the pivot.
   *
   * @param point The pivot coordinate, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   */
  override fun dragStart(point: ScreenCoordinate) {
    nativeMapWeakRef.call { this.dragStart(point) }
  }

  /**
   * Ends the ongoing drag gesture.
   * This function should be called always after the user has ended a drag gesture initiated by `dragStart`.
   */
  override fun dragEnd() {
    nativeMapWeakRef.call { this.dragEnd() }
  }

  /**
   * Drags the map from one screen point to another. The method should be called after `dragStart` and before `dragEnd`.
   *
   * @param fromPoint The point to drag the map from, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   * @param toPoint The point to drag the map to, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   * @param animation Optional animation
   */
  override fun drag(
    fromPoint: ScreenCoordinate,
    toPoint: ScreenCoordinate,
    animation: AnimationOptions?
  ) {
    nativeMapWeakRef.call { this.drag(fromPoint, toPoint, animation) }
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
    return nativeMapWeakRef.call { this.getDragCameraOptions(fromPoint, toPoint) }
  }

  /**
   * Is terrain enabled for loaded style of the map.
   * @return True if terrain is enabled for given style and false otherwise.
   */
  override fun terrainEnabled() = terrainEnabled

  internal fun setCameraAnimationPlugin(cameraAnimationsPlugin: CameraAnimationsPlugin?) {
    cameraAnimationsPlugin?.let {
      if (it.javaClass.canonicalName == PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME)
        this.cameraAnimationsPlugin = WeakReference(it)
    }
  }

  /**
   * Call extension function on [CameraAnimationsPlugin].
   * In most cases should not be called directly.
   */
  override fun cameraAnimationsPlugin(function: (CameraAnimationsPlugin.() -> Any?)): Any? {
    cameraAnimationsPlugin?.get()?.let {
      return function.invoke(it)
    }
    return null
  }

  internal fun setGesturesAnimationPlugin(gesturesPlugin: GesturesPlugin?) {
    gesturesPlugin?.let {
      if (it.javaClass.canonicalName == PLUGIN_GESTURE_CLASS_NAME)
        this.gesturesPlugin = WeakReference(it)
    }
  }

  /**
   * Call extension function on [GesturesPlugin].
   * In most cases should not be called directly.
   */
  override fun gesturesPlugin(function: (GesturesPlugin.() -> Any?)): Any? {
    gesturesPlugin?.get()?.let {
      return function.invoke(it)
    }
    return null
  }
}