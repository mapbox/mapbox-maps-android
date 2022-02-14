package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import androidx.car.app.SurfaceCallback
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenCoordinate

/**
 * Many downstream services will not work until the surface has been created and the map has
 * loaded. This interface allows you to create custom Mapbox experiences for the car.
 */
@MapboxExperimental
interface MapboxCarMapObserver {

  /**
   * Called when a [MapboxCarMapSurface] has been loaded.
   * You can assume there will only be a single surface at a time.
   *
   * @see [MapboxCarMap.registerObserver]
   *
   * @param mapboxCarMapSurface loaded and ready to use car map surface
   */
  fun onAttached(mapboxCarMapSurface: MapboxCarMapSurface) {
    // No op by default
  }

  /**
   * Called when a [MapboxCarMapSurface] has been detached from this observer. Some examples that
   * can cause this to detach are:
   * - [MapboxCarMap] lifecycle is destroyed
   * - This observer has been unregistered with [MapboxCarMap.unregisterObserver]
   *
   * You can assume that there was a corresponding call to [onAttached] with the same
   * [MapboxCarMapObserver] instance.
   *
   * @param mapboxCarMapSurface loaded and ready car map surface
   */
  fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
    // No op by default
  }

  /**
   * Called when the car library updates the visible regions for the surface. For example, this
   * is triggered when the action buttons come in and out of visibility.
   * You can assume this will be called after [onAttached].
   *
   * @param visibleArea the visible area provided by the host
   * @param edgeInsets distance from each side of the screen that creates the [visibleArea]
   */
  fun onVisibleAreaChanged(visibleArea: Rect, edgeInsets: EdgeInsets) {
    // No op by default
  }

  /**
   * Allows you to implement or observe the map scroll gesture handler. The surface is [onAttached]
   * before this can be triggered.
   *
   * @see [SurfaceCallback.onScroll] for instructions to enable.
   *
   * @param mapboxCarMapSurface loaded and ready car map surface
   * @param distanceX the distance in pixels along the X axis
   * @param distanceY the distance in pixels along the Y axis
   *
   * @return true when the fling scroll was handled, false will trigger the default handler
   */
  fun onScroll(
    mapboxCarMapSurface: MapboxCarMapSurface,
    distanceX: Float,
    distanceY: Float
  ): Boolean {
    // By default, scroll is handled internally
    return false
  }

  /**
   * Allows you to implement or observe the map fling gesture handler. The surface is [onAttached]
   * before this can be triggered.
   *
   * @see [SurfaceCallback.onFling] for instructions to enable.
   *
   * @param mapboxCarMapSurface loaded and ready car map surface
   * @param velocityX the velocity of this fling measured in pixels per second along the x axis
   * @param velocityY the velocity of this fling measured in pixels per second along the y axis
   *
   * @return true when the fling call was handled, false will trigger the default handler
   */
  fun onFling(
    mapboxCarMapSurface: MapboxCarMapSurface,
    velocityX: Float,
    velocityY: Float
  ): Boolean {
    // By default, fling is handled internally
    return false
  }

  /**
   * Allows you to implement or observe the map scale gesture handler. The surface is [onAttached]
   * before this can be triggered.
   *
   * @see [SurfaceCallback.onScroll] for instructions to enable.
   *
   * @param mapboxCarMapSurface loaded and ready car map surface
   * @param anchor the focus point in pixels for the zooming gesture
   * @param fromZoom the current zoom of the Mapbox camera
   * @param toZoom the new zoom that will be set if the function returns false
   *
   * @return true when the scale call was handled, false will trigger the default handler
   */
  @Suppress("LongParameterList")
  fun onScale(
    mapboxCarMapSurface: MapboxCarMapSurface,
    anchor: ScreenCoordinate,
    fromZoom: Double,
    toZoom: Double
  ): Boolean {
    // By default, scale is handled internally
    return false
  }
}