package com.mapbox.maps.testapp.auto.custom

import android.graphics.Rect
import androidx.car.app.SurfaceCallback
import androidx.car.app.SurfaceContainer

abstract class SurfaceCallbackInterceptor constructor(
  private val surfaceCallback: SurfaceCallback
) : SurfaceCallback {

  override fun onSurfaceAvailable(surfaceContainer: SurfaceContainer) =
    surfaceCallback.onSurfaceAvailable(surfaceContainer)

  override fun onSurfaceDestroyed(surfaceContainer: SurfaceContainer) =
    surfaceCallback.onSurfaceDestroyed(surfaceContainer)

  override fun onVisibleAreaChanged(visibleArea: Rect) =
    surfaceCallback.onVisibleAreaChanged(visibleArea)

  override fun onStableAreaChanged(stableArea: Rect) =
    surfaceCallback.onStableAreaChanged(stableArea)

  override fun onScale(focusX: Float, focusY: Float, scaleFactor: Float) =
    surfaceCallback.onScale(focusX, focusY, scaleFactor)

  override fun onScroll(distanceX: Float, distanceY: Float) =
    surfaceCallback.onScroll(distanceX, distanceY)

  override fun onFling(velocityX: Float, velocityY: Float) =
    surfaceCallback.onFling(velocityX, velocityY)

  override fun onClick(x: Float, y: Float) =
    surfaceCallback.onClick(x, y)
}