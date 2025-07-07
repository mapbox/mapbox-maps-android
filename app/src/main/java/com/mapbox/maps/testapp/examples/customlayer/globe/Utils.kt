package com.mapbox.maps.testapp.examples.customlayer.globe

import com.mapbox.geojson.Point
import com.mapbox.maps.Projection
import com.mapbox.maps.Vec2
import com.mapbox.maps.Vec3
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin

private const val DEG_TO_RAD = Math.PI / 180.0
private const val RAD_TO_DEG = 180.0 / Math.PI
const val M2_PI = Math.PI * 2.0
private const val PI_HALF = Math.PI / 2.0
private const val EXTENT = 8192.0

fun metersToMercator(latitude: Double): Double {
  val pixelsPerMeters = 1.0 / Projection.getMetersPerPixelAtLatitude(latitude, 0.0)
  val pixelsToMercator = 1.0 / Projection.worldSize(1.0)
  return pixelsPerMeters * pixelsToMercator
}

fun lngFromMercatorX(x: Double) = x * 360.0 - 180.0

fun latFromMercatorY(y: Double) =
  RAD_TO_DEG * (2.0 * atan(exp(Math.PI - y * M2_PI)) - PI_HALF)

fun Point.toEcef(altitude: Double = 0.0) = toEcef(latitude(), longitude(), altitude)

/**
 * Transforms a point from its latitude/longitude/altitude representation to an (x, y, z) position
 * in a 3D space.
 * Custom "Earth-Centered, Earth-Fixed", the Y-axis aligns with the Earth's polar axis.
 * */
fun toEcef(latitude: Double, longitude: Double, altitude: Double = 0.0): Vec3 {
  // The circumference of the world in custom "map units" at the equator.  8192 (2¹³)
  val radius = EXTENT / M2_PI
  // How many Mercator units correspond to one meter at the equator (where distortion is zero) and
  // then scales it by the world extent.
  val ecefPerMeter = metersToMercator(latitude = 0.0) * EXTENT
  val z = radius + altitude * ecefPerMeter
  val lat = latitude * DEG_TO_RAD
  val lng = longitude * DEG_TO_RAD
  val sx = cos(lat) * sin(lng) * z
  // Reversed polar axis. Positive Y-axis points South.
  val sy = -sin(lat) * z
  val sz = cos(lat) * cos(lng) * z
  return Vec3(sx, sy, sz)
}

fun interpolate(a: Double, b: Double, t: Double) = a * (1.0 - t) + b * t

operator fun Vec2.times(other: Double) = Vec2(x * other, y * other)

operator fun Vec3.plus(other: Vec3) = Vec3(x + other.x, y + other.y, z + other.z)
operator fun Vec3.times(other: Double) = Vec3(x * other, y * other, z * other)