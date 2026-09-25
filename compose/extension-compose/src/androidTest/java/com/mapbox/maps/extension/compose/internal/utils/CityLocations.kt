package com.mapbox.maps.extension.compose.internal.utils

import com.mapbox.geojson.Point

internal object CityLocations {
  val HELSINKI: Point = Point.fromLngLat(24.9384, 60.1699)
  val MINSK: Point = Point.fromLngLat(27.561481, 53.902496)
  val BERLIN = Point.fromLngLat(13.403, 52.562)
  val KYIV = Point.fromLngLat(30.498, 50.541)
  val WASHINGTON = Point.fromLngLat(-77.00897, 38.87031)
}

/**
 * Produce a new [Point] that offsets [offset] in both latitude and longitude.
 */
internal fun Point.offset(offset: Double = 0.01) =
  Point.fromLngLat(longitude() + offset, latitude() + offset)