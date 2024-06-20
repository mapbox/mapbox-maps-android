package com.mapbox.maps.compose.testapp.examples.utils

import com.mapbox.geojson.Point

internal object CityLocations {
  val HELSINKI: Point = Point.fromLngLat(24.941526986277893, 60.17099952463323)
  val BERLIN = Point.fromLngLat(13.403, 52.562)
  val KYIV = Point.fromLngLat(30.498, 50.541)
  val WASHINGTON = Point.fromLngLat(-77.00897, 38.87031)
  val NULLISLAND = Point.fromLngLat(0.0, 0.0)
}