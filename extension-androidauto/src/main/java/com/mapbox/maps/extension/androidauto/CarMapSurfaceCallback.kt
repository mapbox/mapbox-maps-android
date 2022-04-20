package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import androidx.car.app.AppManager
import androidx.car.app.CarContext
import androidx.car.app.SurfaceCallback
import androidx.car.app.SurfaceContainer
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logI

/**
 * @see MapboxCarMap to create new map experiences.
 *
 * This is a Mapbox implementation for the [SurfaceCallback]. It is used to simplify the lower
 * level calls that manage the map surface. This class handles the surface callbacks and forwards
 * them to the [CarMapSurfaceOwner] where [MapboxCarMapObserver] instances are notified.
 */
@MapboxExperimental
internal class CarMapSurfaceCallback internal constructor(
  private val carContext: CarContext,
  private val carMapSurfaceOwner: CarMapSurfaceOwner,
  private val mapInitOptions: MapInitOptions
) : SurfaceCallback {

  fun onBind() {
    carContext.getCarService(AppManager::class.java).setSurfaceCallback(this)
  }

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
      carMapSurfaceOwner.surfaceAvailable(carMapSurface)
    }
  }

  override fun onVisibleAreaChanged(visibleArea: Rect) {
    logI(TAG, "onVisibleAreaChanged visibleArea:$visibleArea")
    carMapSurfaceOwner.surfaceVisibleAreaChanged(visibleArea)
  }

  override fun onStableAreaChanged(stableArea: Rect) {
    // Have not found a need for this.
  }

  override fun onScroll(distanceX: Float, distanceY: Float) {
    logI(TAG, "onScroll $distanceX, $distanceY")
    carMapSurfaceOwner.scroll(distanceX, distanceY)
  }

  override fun onFling(velocityX: Float, velocityY: Float) {
    logI(TAG, "onFling $velocityX, $velocityY")
    carMapSurfaceOwner.fling(velocityX, velocityY)
  }

  override fun onScale(focusX: Float, focusY: Float, scaleFactor: Float) {
    logI(TAG, "onScroll $focusX, $focusY, $scaleFactor")
    carMapSurfaceOwner.scale(focusX, focusY, scaleFactor)
  }

  override fun onSurfaceDestroyed(surfaceContainer: SurfaceContainer) {
    logI(TAG, "onSurfaceDestroyed")
    carMapSurfaceOwner.surfaceDestroyed()
  }

  private companion object {
    private const val TAG = "CarMapSurfaceCallback"
  }
}