package com.mapbox.maps.plugin

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate

/**
 * Describes projection map is using.
 *
 * MapboxMap supports Mercator and Globe projections.
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
   * Globe projection is a custom map projection model for rendering the map wrapped around a full 3D globe.
   * Conceptually it is the undistorted and unskewed “ground truth” view of the map
   * that preserves true proportions between different areas of the map.
   *
   * Some layers are not supported when map is in Globe projection:
   *  - circle
   *  - custom
   *  - fill extrusion
   *  - heatmap
   *  - model
   *  - location indicator
   *
   * If Globe projection is set it will be switched automatically to Mercator projection
   * when passing [TRANSITION_ZOOM_LEVEL] during zooming in.
   *
   * @see [TRANSITION_ZOOM_LEVEL] for more details what projection will be used depending on current zoom level.
   */
  object Globe : MapProjection()

  /**
   * Custom toString method.
   */
  override fun toString() = when (this) {
    Globe -> "Globe"
    Mercator -> "Mercator"
  }

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Zoom level threshold where MapboxMap will automatically switch projection
     * from [MapProjection.Mercator] to [MapProjection.Globe] or vice-versa
     * if [MapProjectionDelegate.setMapProjection] was configured to use [MapProjection.Globe].
     *
     * If MapboxMap is using [MapProjection.Globe] and current map zoom level is >= [TRANSITION_ZOOM_LEVEL] -
     * map will use [MapProjection.Mercator] and [MapProjectionDelegate.getMapProjection] will return [MapProjection.Mercator].
     *
     * If MapboxMap is using [MapProjection.Globe] and current map zoom level is < [TRANSITION_ZOOM_LEVEL] -
     * map will use [MapProjection.Globe] and [MapProjectionDelegate.getMapProjection] will return [MapProjection.Globe].
     *
     * If MapboxMap is using [MapProjection.Mercator] - map will use [MapProjection.Mercator] for any zoom level and
     * [MapProjectionDelegate.getMapProjection] will return [MapProjection.Mercator].
     */
    const val TRANSITION_ZOOM_LEVEL = 5.0
  }
}