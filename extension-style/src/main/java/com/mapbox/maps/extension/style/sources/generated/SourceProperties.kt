// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

/**
 * Influences the y direction of the tile coordinates. The global-mercator (aka Spherical Mercator) profile is assumed.
 *
 * @param value
 */
enum class Scheme(val value: String) {

  /**
   * Slippy map tilenames scheme.
   */
  XYZ("xyz"),

  /**
   * OSGeo spec scheme.
   */
  TMS("tms"),
}

/**
 * The encoding used by this source. Mapbox Terrain RGB is used by default
 *
 * @param value
 */
enum class Encoding(val value: String) {

  /**
   * Terrarium format PNG tiles. See https://aws.amazon.com/es/public-datasets/terrain/ for more info.
   */
  TERRARIUM("terrarium"),

  /**
   * Mapbox Terrain RGB tiles. See https://www.mapbox.com/help/access-elevation-data/#mapbox-terrain-rgb for more info.
   */
  MAPBOX("mapbox"),
}

// End of generated file.