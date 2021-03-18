package com.mapbox.maps

import android.graphics.Bitmap
import com.mapbox.bindgen.Value
import com.mapbox.common.ValueConverter
import com.mapbox.geojson.Point
import com.mapbox.geojson.constants.GeoJsonConstants.MAX_LATITUDE
import com.mapbox.geojson.constants.GeoJsonConstants.MIN_LATITUDE
import java.nio.ByteBuffer
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Extension function to compare two double with delta.
 */
fun Double.equalsWithDelta(other: Double, delta: Double): Boolean {
  return abs(this - other) < delta
}

/**
 * Extension function to compare two double with default delta of 1E-5.
 */
fun Double.roughlyEquals(other: Double): Boolean {
  return this.equalsWithDelta(other, 1E-5)
}

/**
 * Extension function to compare two double with default delta of 1E-5.
 */
fun Value.toJson(): String {
  return ValueConverter.toJson(this)
}

/**
 * Extension function to obtain [Bitmap] from snapshotter converted from [Image].
 */
fun MapSnapshotInterface.bitmap(): Bitmap {
  val image = image()
  val configBmp: Bitmap.Config = Bitmap.Config.ARGB_8888
  val bitmap: Bitmap = Bitmap.createBitmap(image.width, image.height, configBmp)
  val buffer: ByteBuffer = ByteBuffer.wrap(image.data)
  bitmap.copyPixelsFromBuffer(buffer)
  return bitmap
}

/**
 * Calculates the center point of this [CoordinateBounds] by simple interpolation and returns
 * it as a point. This is a non-geodesic calculation which is not the geographic center.
 *
 * @return [Point] center of this [CoordinateBounds]
 */
fun CoordinateBounds.getCenter(): Point {
  checkBounds()
  val latCenter = (northeast.latitude() + southwest.latitude()) / 2.0
  val lngCenter = (northeast.longitude() + southwest.longitude()) / 2.0
  return Point.fromLngLat(lngCenter, latCenter)
}

/**
 * Determines whether this [CoordinateBounds] contains a [Point].
 *
 * @param point the point which may be contained
 * @return true, if the point is contained within the bounds
 */
fun CoordinateBounds.contains(point: Point): Boolean {
  checkBounds()
  return point.latitude() <= northeast.latitude() &&
    point.latitude() >= southwest.latitude() &&
    point.longitude() >= southwest.longitude() &&
    point.longitude() <= northeast.longitude()
}

/**
 * Determines whether this [CoordinateBounds] contains another bounds.
 *
 * @param other the bounds which may be contained
 * @return true, if the bounds is contained within the bounds
 */
fun CoordinateBounds.contains(other: CoordinateBounds): Boolean {
  checkBounds()
  other.checkBounds()
  return other.contains(northeast) && other.contains(southwest)
}

/**
 * Returns a new [CoordinateBounds] that stretches to contain both this and another [CoordinateBounds].
 *
 * @param bounds [CoordinateBounds] to add
 * @return [CoordinateBounds]
 */
fun CoordinateBounds.union(bounds: CoordinateBounds): CoordinateBounds {
  checkBounds()
  bounds.checkBounds()
  return CoordinateBounds(
    // southwest
    Point.fromLngLat(
      min(this.southwest.longitude(), bounds.southwest.longitude()),
      min(this.southwest.latitude(), bounds.southwest.latitude())
    ),
    // northeast
    Point.fromLngLat(
      max(this.northeast.longitude(), bounds.northeast.longitude()),
      max(this.northeast.latitude(), bounds.northeast.latitude())
    )
  )
}

private fun CoordinateBounds.checkBounds() {
  val latNorth = northeast.latitude()
  val latSouth = southwest.latitude()

  val lonEast = northeast.longitude()
  val lonWest = southwest.longitude()

  if (latNorth.isNaN() || latSouth.isNaN()) {
    throw IllegalArgumentException("latitude must not be NaN")
  }

  if (lonEast.isNaN() || lonWest.isNaN()) {
    throw IllegalArgumentException("longitude must not be NaN")
  }

  if (lonEast.isInfinite() || lonWest.isInfinite()) {
    throw IllegalArgumentException("longitude must not be infinite")
  }

  if (latNorth > MAX_LATITUDE || latNorth < MIN_LATITUDE ||
    latSouth > MAX_LATITUDE || latSouth < MIN_LATITUDE
  ) {
    throw IllegalArgumentException("latitude must be between -90 and 90")
  }

  if (latNorth < latSouth) {
    throw IllegalArgumentException("latNorth cannot be less than latSouth")
  }

  if (lonEast < lonWest) {
    throw IllegalArgumentException("lonEast cannot be less than lonWest")
  }
}