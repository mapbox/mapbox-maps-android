package com.mapbox.maps.plugin.animation

import androidx.annotation.VisibleForTesting
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.animation.CameraTransform.ge
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import kotlin.math.*

internal object CameraTransform {

  private const val LONGITUDE_MAX = 180
  private const val DEGREES_MAX = 360

  fun angleBetween(p1: ScreenCoordinate, p2: ScreenCoordinate) =
    atan2((p1.x * p2.y - p1.y * p2.x), p1.x * p2.x + p1.y * p2.y)

  fun Double.scaleZoom() = log2(this)

  fun Double.zoomScale() = 2.0.pow(this)

  fun Double.rad2deg() = this * 180.0 / Math.PI

  fun Double.deg2rad() = this * Math.PI / 180.0

  fun MercatorCoordinate.offset(arg: MercatorCoordinate) = ScreenCoordinate(x - arg.x, y - arg.y)

  fun ScreenCoordinate.offset(arg: ScreenCoordinate) = ScreenCoordinate(x - arg.x, y - arg.y)

  fun getMapCenter(edgeInsets: EdgeInsets, mapSize: Size): ScreenCoordinate {
    val centerX = (mapSize.width - edgeInsets.left - edgeInsets.right) / 2.0 + edgeInsets.left
    val centerY = (mapSize.height - edgeInsets.top - edgeInsets.bottom) / 2.0 + edgeInsets.top
    return ScreenCoordinate(centerX, centerY)
  }

  fun unwrapForShortestPath(start: Point, end: Point): Point {
    val delta = abs(end.longitude() - start.longitude())
    if (delta <= LONGITUDE_MAX || delta >= DEGREES_MAX) return start
    var lon: Double = start.longitude()
    if (start.longitude() > 0 && end.longitude() < 0) {
      lon -= DEGREES_MAX
    } else if (start.longitude() < 0 && end.longitude() > 0) {
      lon += DEGREES_MAX
    }
    return Point.fromLngLat(lon, start.latitude())
  }

  fun calculateLatLngMoveBy(
    offset: ScreenCoordinate,
    cameraState: CameraState,
    mapTransformDelegate: MapTransformDelegate,
    mapCameraManagerDelegate: MapCameraManagerDelegate
  ): Point {
    val mapOptions = mapTransformDelegate.getMapOptions()
    val mapCenter = getMapCenter(
      cameraState.padding,
      mapOptions.size!!
    )
    val pointOnScreenX = mapCenter.x - offset.x
    val pointOnScreenY = mapCenter.y - offset.y
    val pointOnScreen = ScreenCoordinate(pointOnScreenX, pointOnScreenY)
    return mapCameraManagerDelegate.coordinateForPixel(pointOnScreen)
  }

  fun calculateScaleBy(amount: Double, currentZoom: Double) = log2(amount) + currentZoom

  /**
   * Extension function to wrap coordinate to one world copy.
   */
  fun Point.wrapCoordinate(): Point {
    val lng = wrap(
      value = this.longitude(),
      min = -LONGITUDE_MAX.toDouble(),
      max = LONGITUDE_MAX.toDouble()
    )
    return if (lng.isNaN()) Point.fromLngLat(this.longitude(), this.latitude())
    else Point.fromLngLat(lng, this.latitude())
  }

  // Wrap value to the given range (including min, excluding max).
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun wrap(value: Double, min: Double, max: Double): Double {
    if (value.eq(max)) {
      return min
    } else if (value.ge(min) && value.less(max)) {
      return value
    }
    val delta = max - min
    val wrapped = min + ((value - min) % delta)
    return if (value < min) wrapped + delta else wrapped
  }

  private const val PRECISION = 1e-6
  private fun Double.eq(other: Double) = abs(this - other) < PRECISION
  private fun Double.ge(other: Double) = (this - other) > PRECISION || this.eq(other)
  private fun Double.less(other: Double) = (this - other) < -PRECISION
}