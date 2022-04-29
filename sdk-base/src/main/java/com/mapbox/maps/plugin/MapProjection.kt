package com.mapbox.maps.plugin

import com.mapbox.maps.MapboxExperimental

/**
 * Describes the projection used to render the map.
 *
 * Mapbox map supports Mercator and Globe projections.
 */
@MapboxExperimental
sealed class MapProjection {

  /**
   * Mercator projection.
   *
   * @see [Mercator projection description](https://en.wikipedia.org/wiki/Mercator_projection)
   */
  object Mercator : MapProjection()

  /**
   * Globe projection is a custom map projection mode for rendering the map wrapped around a full 3D globe.
   * Conceptually it is the undistorted and unskewed “ground truth” view of the map
   * that preserves true proportions between different areas of the map.
   *
   * Some layers are not supported when map is in Globe projection:
   *  - custom
   *  - location indicator
   */
  object Globe : MapProjection()

  /**
   * Custom toString method.
   */
  override fun toString() = when (this) {
    Globe -> "globe"
    Mercator -> "mercator"
  }
}