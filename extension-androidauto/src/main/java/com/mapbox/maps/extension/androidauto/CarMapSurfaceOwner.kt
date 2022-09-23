package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import androidx.car.app.CarContext
import androidx.car.app.SurfaceCallback
import androidx.car.app.SurfaceContainer
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.logI
import java.util.concurrent.CopyOnWriteArraySet

/**
 * @see MapboxCarMap to create new map experiences.
 *
 * Maintains the surface state for [MapboxCarMap].
 */
@MapboxExperimental
internal class CarMapSurfaceOwner @JvmOverloads constructor(
  internal var gestureHandler: MapboxCarMapGestureHandler? = DefaultMapboxCarMapGestureHandler()
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

  internal lateinit var carContext: CarContext
  internal lateinit var mapInitOptions: MapInitOptions

  private val carMapObservers = CopyOnWriteArraySet<MapboxCarMapObserver>()

  internal fun setup(carContext: CarContext, mapInitOptions: MapInitOptions) = apply {
    this.carContext = carContext
    this.mapInitOptions = mapInitOptions
  }

  internal fun isSetup(): Boolean = this::carContext.isInitialized && this::mapInitOptions.isInitialized

  internal fun registerObserver(mapboxCarMapObserver: MapboxCarMapObserver) {
    carMapObservers.add(mapboxCarMapObserver)
    logI(TAG, "registerObserver + 1 = ${carMapObservers.size}")

    mapboxCarMapSurface?.let { carMapSurface ->
      mapboxCarMapObserver.onAttached(carMapSurface)
    }
    ifNonNull(mapboxCarMapSurface, visibleArea, visibleEdgeInsets) { _, area, edge ->
      logI(TAG, "registerObserver visibleAreaChanged")
      mapboxCarMapObserver.onVisibleAreaChanged(area, edge)
    }
    ifNonNull(mapboxCarMapSurface, stableArea, stableEdgeInsets) { _, area, edge ->
      logI(TAG, "registerObserver stableAreaChanged")
      mapboxCarMapObserver.onStableAreaChanged(area, edge)
    }
  }

  internal fun unregisterObserver(mapboxCarMapObserver: MapboxCarMapObserver) {
    carMapObservers.remove(mapboxCarMapObserver)
    mapboxCarMapSurface?.let { mapboxCarMapObserver.onDetached(it) }
    logI(TAG, "unregisterObserver - 1 = ${carMapObservers.size}")
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
    surfaceContainer.surface?.let { surface ->
      val mapSurface = MapSurfaceProvider.create(
        carContext,
        surface,
        mapInitOptions
      )
      mapSurface.onStart()
      mapSurface.surfaceCreated()
      mapSurface.surfaceChanged(surfaceContainer.width, surfaceContainer.height)
      val carMapSurface = MapboxCarMapSurface(carContext, mapSurface, surfaceContainer)
      val oldCarMapSurface = this.mapboxCarMapSurface
      this.mapboxCarMapSurface = carMapSurface
      oldCarMapSurface?.mapSurface?.let { mapSurface ->
        carMapObservers.forEach { it.onDetached(oldCarMapSurface) }
        // The surface can become available without a call to surfaceDestroyed
        // https://issuetracker.google.com/issues/235121269
        mapSurface.onStop()
        mapSurface.surfaceDestroyed()
        mapSurface.onDestroy()
      }
      carMapObservers.forEach { it.onAttached(carMapSurface) }

      notifyVisibleAreaChanged()
    }
  }

  /**
   * Destroys the resources used by [MapSurface] and notifies any registered observers that the map
   * has been detached with [MapboxCarMapObserver.onDetached].
   *
   * @see SurfaceCallback.onSurfaceDestroyed
   */
  override fun onSurfaceDestroyed(surfaceContainer: SurfaceContainer) {
    logI(TAG, "onSurfaceDestroyed")
    val detachSurface = this.mapboxCarMapSurface
    detachSurface?.mapSurface?.onStop()
    detachSurface?.mapSurface?.surfaceDestroyed()
    detachSurface?.mapSurface?.onDestroy()
    this.mapboxCarMapSurface = null
    detachSurface?.let { carMapObservers.forEach { it.onDetached(detachSurface) } }
  }

  /**
   * Notifies any registered observers that the visible area has changed with
   * [MapboxCarMapObserver.onVisibleAreaChanged].
   *
   * @see SurfaceCallback.onVisibleAreaChanged
   */
  override fun onVisibleAreaChanged(visibleArea: Rect) {
    logI(TAG, "onVisibleAreaChanged visibleArea:$visibleArea")
    this.visibleArea = visibleArea
    notifyVisibleAreaChanged()
  }

  private fun notifyVisibleAreaChanged() {
    this.visibleEdgeInsets = visibleArea?.edgeInsets()
    this.visibleCenter = visibleCenter()
    ifNonNull(mapboxCarMapSurface, visibleArea, visibleEdgeInsets) { _, area, edge ->
      logI(TAG, "notifyVisibleAreaChanged $area $edge")
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
    logI(TAG, "onStableAreaChanged stableArea:$stableArea")
    this.stableEdgeInsets = stableArea.edgeInsets()
    this.stableArea = stableArea
    ifNonNull(mapboxCarMapSurface, stableArea, stableEdgeInsets) { _, area, edge ->
      logI(TAG, "notifyStableAreaChanged $area $edge")
      carMapObservers.forEach {
        it.onStableAreaChanged(area, edge)
      }
    }
  }

  /**
   * Forwards the gesture to the [MapboxCarMapGestureHandler.onScroll].
   *
   * @see SurfaceCallback.onScroll
   */
  override fun onScroll(distanceX: Float, distanceY: Float) {
    logI(TAG, "onScroll $distanceX, $distanceY")
    val carMapSurface = mapboxCarMapSurface ?: return
    gestureHandler?.onScroll(carMapSurface, visibleCenter, distanceX, distanceY)
  }

  /**
   * Forwards the gesture to the [MapboxCarMapGestureHandler.onFling].
   *
   * @see SurfaceCallback.onFling
   */
  override fun onFling(velocityX: Float, velocityY: Float) {
    logI(TAG, "onFling $velocityX, $velocityY")
    val carMapSurface = mapboxCarMapSurface ?: return
    gestureHandler?.onFling(carMapSurface, velocityX, velocityY)
  }

  /**
   * Forwards the gesture to the [MapboxCarMapGestureHandler.onScale].
   *
   * @see SurfaceCallback.onScale
   */
  override fun onScale(focusX: Float, focusY: Float, scaleFactor: Float) {
    logI(TAG, "onScroll $focusX, $focusY, $scaleFactor")
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

    private val rectCenterMapper = { rect: Rect ->
      ScreenCoordinate(rect.exactCenterX().toDouble(), rect.exactCenterY().toDouble())
    }

    private val surfaceContainerCenterMapper = { carMapSurface: MapboxCarMapSurface ->
      val container = carMapSurface.surfaceContainer
      ScreenCoordinate(container.width / 2.0, container.height / 2.0)
    }
  }
}