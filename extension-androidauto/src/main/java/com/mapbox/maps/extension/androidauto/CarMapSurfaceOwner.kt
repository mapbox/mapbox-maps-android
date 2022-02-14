package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import com.mapbox.common.Logger
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.camera
import java.util.concurrent.CopyOnWriteArraySet

/**
 * @see MapboxCarMap to create new map experiences.
 *
 * Maintains the surface state for [MapboxCarMap].
 */
@OptIn(MapboxExperimental::class)
internal class CarMapSurfaceOwner {

  internal var mapboxCarMapSurface: MapboxCarMapSurface? = null
    private set
  internal var visibleArea: Rect? = null
    private set
  internal var edgeInsets: EdgeInsets? = null
    private set
  internal var visibleCenter: ScreenCoordinate = visibleCenter()
    private set

  private val carMapObservers = CopyOnWriteArraySet<MapboxCarMapObserver>()

  fun registerObserver(mapboxCarMapObserver: MapboxCarMapObserver) {
    carMapObservers.add(mapboxCarMapObserver)
    Logger.i(TAG, "registerObserver + 1 = ${carMapObservers.size}")

    mapboxCarMapSurface?.let { carMapSurface ->
      mapboxCarMapObserver.onAttached(carMapSurface)
    }
    ifNonNull(mapboxCarMapSurface, visibleArea, edgeInsets) { _, area, edge ->
      Logger.i(TAG, "registerObserver visibleAreaChanged")
      mapboxCarMapObserver.onVisibleAreaChanged(area, edge)
    }
  }

  fun unregisterObserver(mapboxCarMapObserver: MapboxCarMapObserver) {
    carMapObservers.remove(mapboxCarMapObserver)
    mapboxCarMapSurface?.let { mapboxCarMapObserver.onDetached(it) }
    Logger.i(TAG, "unregisterObserver - 1 = ${carMapObservers.size}")
  }

  fun clearObservers() {
    this.mapboxCarMapSurface?.let { surface -> carMapObservers.forEach { it.onDetached(surface) } }
    carMapObservers.clear()
  }

  fun surfaceAvailable(mapboxCarMapSurface: MapboxCarMapSurface) {
    Logger.i(TAG, "surfaceAvailable")
    val oldCarMapSurface = this.mapboxCarMapSurface
    this.mapboxCarMapSurface = mapboxCarMapSurface
    oldCarMapSurface?.let { carMapObservers.forEach { it.onDetached(oldCarMapSurface) } }
    carMapObservers.forEach { it.onAttached(mapboxCarMapSurface) }

    notifyVisibleAreaChanged()
  }

  fun surfaceDestroyed() {
    Logger.i(TAG, "surfaceDestroyed")
    val detachSurface = this.mapboxCarMapSurface
    detachSurface?.mapSurface?.onStop()
    detachSurface?.mapSurface?.surfaceDestroyed()
    detachSurface?.mapSurface?.onDestroy()
    this.mapboxCarMapSurface = null
    detachSurface?.let { carMapObservers.forEach { it.onDetached(detachSurface) } }
  }

  fun surfaceVisibleAreaChanged(visibleArea: Rect) {
    Logger.i(TAG, "surfaceVisibleAreaChanged")
    this.visibleArea = visibleArea
    notifyVisibleAreaChanged()
  }

  private fun notifyVisibleAreaChanged() {
    this.edgeInsets = visibleArea?.edgeInsets()
    this.visibleCenter = visibleCenter()
    ifNonNull(mapboxCarMapSurface, visibleArea, edgeInsets) { _, area, edge ->
      Logger.i(TAG, "notifyVisibleAreaChanged $area $edge")
      carMapObservers.forEach {
        it.onVisibleAreaChanged(area, edge)
      }
    }
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

  fun scroll(distanceX: Float, distanceY: Float) {
    val carMapSurface = mapboxCarMapSurface ?: return
    val handled = carMapObservers.any { it.onScroll(carMapSurface, distanceX, distanceY) }
    if (handled) return

    with(carMapSurface.mapSurface.getMapboxMap()) {
      val fromCoordinate = visibleCenter
      dragStart(fromCoordinate)
      val toCoordinate = ScreenCoordinate(
        fromCoordinate.x - distanceX,
        fromCoordinate.y - distanceY
      )
      Logger.i(TAG, "scroll from $fromCoordinate to $toCoordinate")
      setCamera(getDragCameraOptions(fromCoordinate, toCoordinate))
      dragEnd()
    }
  }

  fun fling(velocityX: Float, velocityY: Float) {
    val carMapSurface = mapboxCarMapSurface ?: return
    val handled = carMapObservers.any { it.onFling(carMapSurface, velocityX, velocityY) }
    if (handled) return

    Logger.i(TAG, "fling $velocityX, $velocityY")
    // TODO implement fling
    // https://github.com/mapbox/mapbox-navigation-android-examples/issues/67
  }

  fun scale(focusX: Float, focusY: Float, scaleFactor: Float) {
    val carMapSurface = mapboxCarMapSurface ?: return
    with(carMapSurface.mapSurface.getMapboxMap()) {
      val fromZoom = cameraState.zoom
      val toZoom = fromZoom - (1.0 - scaleFactor.toDouble())
      val anchor = ScreenCoordinate(
        focusX.toDouble(),
        focusY.toDouble()
      )
      val handled = carMapObservers.any { it.onScale(carMapSurface, anchor, fromZoom, toZoom) }
      if (handled) return

      val cameraOptions = CameraOptions.Builder()
        .zoom(toZoom)
        .anchor(anchor)
        .build()

      Logger.i(TAG, "scale with $focusX, $focusY $scaleFactor -> $fromZoom $toZoom")
      if (scaleFactor == DOUBLE_TAP_SCALE_FACTOR) {
        carMapSurface.mapSurface.camera.easeTo(cameraOptions)
      } else {
        setCamera(cameraOptions)
      }
    }
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

    /**
     * This appears to be undocumented from android auto. But when running from the emulator,
     * you can double tap the screen and zoom in to reproduce this value.
     * It is a jarring experience if you do not easeTo the zoom.
     */
    private const val DOUBLE_TAP_SCALE_FACTOR = 2.0f
  }
}