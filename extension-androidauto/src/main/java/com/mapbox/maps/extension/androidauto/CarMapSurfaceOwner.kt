package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.SurfaceHolder
import androidx.annotation.OptIn
import androidx.annotation.RestrictTo
import androidx.car.app.CarContext
import androidx.car.app.SurfaceCallback
import androidx.car.app.SurfaceContainer
import androidx.car.app.annotations.ExperimentalCarApi
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.logD
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.Plugin
import java.util.concurrent.CopyOnWriteArraySet

/**
 * @see MapboxCarMap to create new map experiences.
 *
 * Maintains the surface state for [MapboxCarMap].
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
@kotlin.OptIn(MapboxExperimental::class)
internal class CarMapSurfaceOwner(
  internal var gestureHandler: MapboxCarMapGestureHandler? = DefaultMapboxCarMapGestureHandler(),
  private val mapType: MapboxCarMapType = MapboxCarMapType.MAP_SURFACE,
) : SurfaceCallback {

  internal var mapboxCarMapSurface: MapboxCarMapSurface? = null
    private set
  internal var visibleArea: Rect? = null
    private set
  internal var visibleEdgeInsets: EdgeInsets? = null
    private set
  internal var visibleCenter: ScreenCoordinate = visibleCenter()
    private set
  internal var stableArea: Rect? = null
    private set
  internal var stableEdgeInsets: EdgeInsets? = null
  private var mapViewDisplay: CarMapViewDisplay? = null
  private var pendingMapViewDisplay: CarMapViewDisplay? = null
  private var pendingMapViewWarning: Runnable? = null
  private val mainHandler by lazy { Handler(Looper.getMainLooper()) }

  internal lateinit var carContext: CarContext
  internal lateinit var mapInitOptions: MapInitOptions

  private val carMapObservers = CopyOnWriteArraySet<MapboxCarMapObserver>()

  internal fun setup(carContext: CarContext, mapInitOptions: MapInitOptions) = apply {
    validateMapInitOptions(mapInitOptions)
    this.carContext = carContext
    this.mapInitOptions = mapInitOptions
  }

  private fun validateMapInitOptions(mapInitOptions: MapInitOptions) {
    require(mapType != MapboxCarMapType.MAP_VIEW || !mapInitOptions.textureView) {
      "MAP_VIEW requires MapInitOptions.textureView = false"
    }
    require(
      mapType != MapboxCarMapType.MAP_VIEW ||
        mapInitOptions.plugins.any { it.id == Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID }
    ) {
      "MAP_VIEW requires the MAPBOX_LIFECYCLE_PLUGIN_ID plugin in MapInitOptions.plugins"
    }
  }

  internal fun isSetup(): Boolean = this::carContext.isInitialized && this::mapInitOptions.isInitialized

  internal fun registerObserver(mapboxCarMapObserver: MapboxCarMapObserver) {
    carMapObservers.add(mapboxCarMapObserver)
    logD(TAG) { "registerObserver + 1 = ${carMapObservers.size}" }

    mapboxCarMapSurface?.let { carMapSurface ->
      mapboxCarMapObserver.onAttached(carMapSurface)
    }
    ifNonNull(mapboxCarMapSurface, visibleArea, visibleEdgeInsets) { _, area, edge ->
      logD(TAG, "registerObserver visibleAreaChanged")
      mapboxCarMapObserver.onVisibleAreaChanged(area, edge)
    }
    ifNonNull(mapboxCarMapSurface, stableArea, stableEdgeInsets) { _, area, edge ->
      logD(TAG, "registerObserver stableAreaChanged")
      mapboxCarMapObserver.onStableAreaChanged(area, edge)
    }
  }

  internal fun unregisterObserver(mapboxCarMapObserver: MapboxCarMapObserver) {
    carMapObservers.remove(mapboxCarMapObserver)
    mapboxCarMapSurface?.let { mapboxCarMapObserver.onDetached(it) }
    logD(TAG) { "unregisterObserver - 1 = ${carMapObservers.size}" }
  }

  internal fun clearObservers() {
    this.mapboxCarMapSurface?.let { surface -> carMapObservers.forEach { it.onDetached(surface) } }
    carMapObservers.clear()
  }

  /**
   * Prepares the [MapSurface] and notifies any registered observers that the map has been attached
   * with [MapboxCarMapObserver.onAttached].
   *
   * @see SurfaceCallback.onSurfaceAvailable
   */
  override fun onSurfaceAvailable(surfaceContainer: SurfaceContainer) {
    logI(TAG, "onSurfaceAvailable $surfaceContainer")
    when (mapType) {
      MapboxCarMapType.MAP_SURFACE -> surfaceContainer.surface?.let { surface ->
        val mapSurface = MapSurfaceProvider.create(carContext, surface, mapInitOptions)
        attach(mapSurface, mapView = null, mapViewDisplay = null, surfaceContainer)
      }
      MapboxCarMapType.MAP_VIEW -> showMapView(surfaceContainer)
    }
  }

  // Wait for the view's surface before exposing a facade over its existing map and renderer.
  private fun showMapView(surfaceContainer: SurfaceContainer) {
    validateMapInitOptions(mapInitOptions)
    val mapViewInitOptions = mapInitOptions.copy(
      plugins = mapInitOptions.plugins.filterNot { it.id in MAP_VIEW_ORNAMENT_PLUGIN_IDS }
    )
    releasePendingMapViewDisplay()
    val oldCarMapSurface = mapboxCarMapSurface
    val oldMapViewDisplay = mapViewDisplay
    mapboxCarMapSurface = null
    mapViewDisplay = null
    notifyVisibleAreaChanged()
    notifyStableAreaChanged()
    oldCarMapSurface?.let { surface ->
      carMapObservers.forEach { it.onDetached(surface) }
    }
    oldMapViewDisplay?.destroy()
    val display = MapSurfaceProvider.createMapViewDisplay(carContext, surfaceContainer, mapViewInitOptions)
      ?: return
    val mapView = display.mapView
    val surfaceView = mapView.rendererSurfaceView
      ?: run {
        display.destroy()
        error("MapView must use SurfaceView mode to build a MapSurface")
      }
    pendingMapViewDisplay = display
    val pendingSince = SystemClock.elapsedRealtime()
    schedulePendingMapViewWarning(display, surfaceContainer)
    val callback = object : SurfaceHolder.Callback {
      override fun surfaceCreated(holder: SurfaceHolder) {
        holder.removeCallback(this)
        if (pendingMapViewDisplay !== display) return
        pendingMapViewDisplay = null
        cancelPendingMapViewWarning()
        logI(
          TAG,
          "Map view surface created after ${SystemClock.elapsedRealtime() - pendingSince} ms " +
            "for car surface $surfaceContainer"
        )
        val mapSurface = MapSurfaceProvider.createFromMapView(mapView)
        attach(mapSurface, mapView, display, surfaceContainer)
      }

      override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) = Unit

      override fun surfaceDestroyed(holder: SurfaceHolder) = Unit
    }
    val holder = surfaceView.holder
    holder.addCallback(callback)
    // surfaceCreated is not replayed for callbacks added after the surface already exists.
    if (holder.surface?.isValid == true) {
      callback.surfaceCreated(holder)
    }
  }

  private fun schedulePendingMapViewWarning(display: CarMapViewDisplay, surfaceContainer: SurfaceContainer) {
    cancelPendingMapViewWarning()
    val warning = Runnable {
      pendingMapViewWarning = null
      if (pendingMapViewDisplay === display) {
        logW(
          TAG,
          "Map view surface is still not created $PENDING_MAP_VIEW_WARNING_DELAY_MS ms after " +
            "car surface $surfaceContainer became available; the car screen stays blank until it is"
        )
      }
    }
    pendingMapViewWarning = warning
    mainHandler.postDelayed(warning, PENDING_MAP_VIEW_WARNING_DELAY_MS)
  }

  private fun cancelPendingMapViewWarning() {
    pendingMapViewWarning?.let { mainHandler.removeCallbacks(it) }
    pendingMapViewWarning = null
  }

  private fun releasePendingMapViewDisplay() {
    cancelPendingMapViewWarning()
    val display = pendingMapViewDisplay
    pendingMapViewDisplay = null
    display?.destroy()
  }

  private fun attach(
    mapSurface: MapSurface,
    mapView: MapView?,
    mapViewDisplay: CarMapViewDisplay?,
    surfaceContainer: SurfaceContainer,
  ) {
    if (mapView == null) {
      mapSurface.onStart()
      mapSurface.surfaceCreated()
      mapSurface.surfaceChanged(surfaceContainer.width, surfaceContainer.height)
    }
    val carMapSurface = MapboxCarMapSurface(carContext, mapView, mapSurface, surfaceContainer)
    val oldCarMapSurface = this.mapboxCarMapSurface
    val oldMapViewDisplay = this.mapViewDisplay
    this.mapboxCarMapSurface = carMapSurface
    this.mapViewDisplay = mapViewDisplay
    if (oldCarMapSurface != null) {
      carMapObservers.forEach { it.onDetached(oldCarMapSurface) }
      // The surface can become available without a call to surfaceDestroyed
      // https://issuetracker.google.com/issues/235121269
      destroy(oldCarMapSurface.mapSurface, oldMapViewDisplay)
    }
    carMapObservers.forEach { it.onAttached(carMapSurface) }

    notifyVisibleAreaChanged()
    notifyStableAreaChanged()
  }

  private fun destroy(mapSurface: MapSurface, mapViewDisplay: CarMapViewDisplay?) {
    if (mapViewDisplay != null) {
      mapViewDisplay.destroy()
    } else {
      mapSurface.onStop()
      mapSurface.surfaceDestroyed()
      mapSurface.onDestroy()
    }
  }

  /**
   * Notifies registered observers with [MapboxCarMapObserver.onDetached] before destroying
   * the resources used by [MapSurface].
   *
   * @see SurfaceCallback.onSurfaceDestroyed
   */
  override fun onSurfaceDestroyed(surfaceContainer: SurfaceContainer) {
    logI(TAG, "onSurfaceDestroyed $surfaceContainer")
    releasePendingMapViewDisplay()
    val detachSurface = this.mapboxCarMapSurface
    val detachDisplay = this.mapViewDisplay
    this.mapboxCarMapSurface = null
    this.mapViewDisplay = null
    detachSurface?.let { surface ->
      try {
        carMapObservers.forEach { it.onDetached(surface) }
      } finally {
        destroy(surface.mapSurface, detachDisplay)
      }
    }
  }

  /**
   * Notifies any registered observers that the visible area has changed with
   * [MapboxCarMapObserver.onVisibleAreaChanged].
   *
   * @see SurfaceCallback.onVisibleAreaChanged
   */
  override fun onVisibleAreaChanged(visibleArea: Rect) {
    logI(TAG, "onVisibleAreaChanged visibleArea:$visibleArea $visibleEdgeInsets")
    this.visibleArea = visibleArea
    notifyVisibleAreaChanged()
  }

  private fun notifyVisibleAreaChanged() {
    this.visibleEdgeInsets = visibleArea?.edgeInsets()
    this.visibleCenter = visibleCenter()
    ifNonNull(mapboxCarMapSurface, visibleArea, visibleEdgeInsets) { _, area, edge ->
      logD(TAG) { "notifyVisibleAreaChanged $area $edge" }
      carMapObservers.forEach {
        it.onVisibleAreaChanged(area, edge)
      }
    }
  }

  /**
   * Notifies any registered observers that the visible area has changed with
   * [MapboxCarMapObserver.onStableAreaChanged].
   *
   * @see SurfaceCallback.onStableAreaChanged
   */
  override fun onStableAreaChanged(stableArea: Rect) {
    logI(TAG, "onStableAreaChanged stableArea:$stableArea $stableEdgeInsets")
    this.stableArea = stableArea
    notifyStableAreaChanged()
  }

  private fun notifyStableAreaChanged() {
    this.stableEdgeInsets = stableArea?.edgeInsets()
    ifNonNull(mapboxCarMapSurface, stableArea, stableEdgeInsets) { _, area, edge ->
      logD(TAG) { "notifyStableAreaChanged $area $edge" }
      carMapObservers.forEach {
        it.onStableAreaChanged(area, edge)
      }
    }
  }

  /**
   * Forwards the click to the [MapboxCarMapGestureHandler.onClick].
   *
   * @see SurfaceCallback.onClick
   */
  @OptIn(markerClass = [ExperimentalCarApi::class])
  override fun onClick(x: Float, y: Float) {
    logD(TAG) { "onClick $x, $y" }
    val carMapSurface = mapboxCarMapSurface ?: return
    gestureHandler?.onClick(carMapSurface, x, y)
  }

  /**
   * Forwards the gesture to the [MapboxCarMapGestureHandler.onScroll].
   *
   * @see SurfaceCallback.onScroll
   */
  override fun onScroll(distanceX: Float, distanceY: Float) {
    logD(TAG) { "onScroll $distanceX, $distanceY" }
    val carMapSurface = mapboxCarMapSurface ?: return
    gestureHandler?.onScroll(carMapSurface, visibleCenter, distanceX, distanceY)
  }

  /**
   * Forwards the gesture to the [MapboxCarMapGestureHandler.onFling].
   *
   * @see SurfaceCallback.onFling
   */
  override fun onFling(velocityX: Float, velocityY: Float) {
    logD(TAG) { "onFling $velocityX, $velocityY" }
    val carMapSurface = mapboxCarMapSurface ?: return
    gestureHandler?.onFling(carMapSurface, velocityX, velocityY)
  }

  /**
   * Forwards the gesture to the [MapboxCarMapGestureHandler.onScale].
   *
   * @see SurfaceCallback.onScale
   */
  override fun onScale(focusX: Float, focusY: Float, scaleFactor: Float) {
    logD(TAG) { "onScroll $focusX, $focusY, $scaleFactor" }
    val carMapSurface = mapboxCarMapSurface ?: return
    gestureHandler?.onScale(carMapSurface, focusX, focusY, scaleFactor)
  }

  private inline fun <R1, R2, R3, T> ifNonNull(
    r1: R1?,
    r2: R2?,
    r3: R3?,
    func: (R1, R2, R3) -> T
  ): T? = if (r1 != null && r2 != null && r3 != null) {
    func(r1, r2, r3)
  } else {
    null
  }

  private fun Rect.edgeInsets(): EdgeInsets? {
    val surfaceContainer = mapboxCarMapSurface?.surfaceContainer ?: return null
    return EdgeInsets(
      top.toDouble(),
      left.toDouble(),
      (surfaceContainer.height - bottom).toDouble(),
      (surfaceContainer.width - right).toDouble()
    )
  }

  private fun visibleCenter(): ScreenCoordinate {
    return visibleArea?.run(rectCenterMapper)
      ?: mapboxCarMapSurface?.run(surfaceContainerCenterMapper)
      ?: ScreenCoordinate(0.0, 0.0)
  }

  private companion object {
    private const val TAG = "CarMapSurfaceOwner"
    private const val PENDING_MAP_VIEW_WARNING_DELAY_MS = 3_000L

    // The car UI owns ornaments; view plugins ignore the stable area and can duplicate car widgets.
    private val MAP_VIEW_ORNAMENT_PLUGIN_IDS = setOf(
      Plugin.MAPBOX_COMPASS_PLUGIN_ID,
      Plugin.MAPBOX_LOGO_PLUGIN_ID,
      Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID,
      Plugin.MAPBOX_CONSOLIDATED_ATTRIBUTION_PLUGIN_ID,
      Plugin.MAPBOX_SCALEBAR_PLUGIN_ID,
    )

    private val rectCenterMapper = { rect: Rect ->
      ScreenCoordinate(rect.exactCenterX().toDouble(), rect.exactCenterY().toDouble())
    }

    private val surfaceContainerCenterMapper = { carMapSurface: MapboxCarMapSurface ->
      val container = carMapSurface.surfaceContainer
      ScreenCoordinate(container.width / 2.0, container.height / 2.0)
    }
  }
}