package com.mapbox.maps.plugin.delegates

import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MercatorCoordinate
import com.mapbox.maps.ProjectedMeters
import com.mapbox.maps.plugin.MapProjection

/**
 * Definition of a map projection delegate.
 */
interface MapProjectionDelegate {
  /**
   * Calculate distance spanned by one pixel at the specified latitude
   * and zoom level.
   *
   * @param latitude The latitude for which to return the value
   * @param zoom The zoom level
   *
   * @return Returns the distance measured in meters.
   */
  fun getMetersPerPixelAtLatitude(latitude: Double, zoom: Double): Double

  /**
   * Calculate distance spanned by one pixel at the specified latitude
   * at current zoom level.
   *
   * @param latitude The latitude for which to return the value
   *
   * @return Returns the distance measured in meters.
   */
  fun getMetersPerPixelAtLatitude(latitude: Double): Double

  /**
   * Calculate Spherical Mercator ProjectedMeters coordinates.
   *
   * @param point A longitude-latitude pair for which to calculate
   * ProjectedMeters coordinates
   *
   * @return Returns Spherical Mercator ProjectedMeters coordinates
   */
  fun projectedMetersForCoordinate(point: Point): ProjectedMeters

  /**
   * Calculate a longitude-latitude pair for a Spherical Mercator projected
   * meters.
   *
   * @param projectedMeters Spherical Mercator ProjectedMeters coordinates for
   * which to calculate a longitude-latitude pair.
   *
   * @return Returns a longitude-latitude pair.
   */
  fun coordinateForProjectedMeters(projectedMeters: ProjectedMeters): Point

  /**
   * Calculate a point on the map in Mercator Projection for a given
   * coordinate at the specified zoom scale.
   *
   * @param point The longitude-latitude pair for which to return the value.
   * @param zoomScale The current zoom factor applied on the map, is used to
   * calculate the world size as tileSize * zoomScale (i.e., 512 * 2 ^ Zoom level)
   * where tileSize is the width of a tile in pixels.
   *
   * @return Returns a point on the map in Mercator projection.
   */
  fun project(point: Point, zoomScale: Double): MercatorCoordinate

  /**
   * Calculate a coordinate for a given point on the map in Mercator Projection.
   *
   * @param coordinate Point on the map in Mercator projection.
   * @param zoomScale The current zoom factor applied on the map, is used to
   * calculate the world size as tileSize * zoomScale (i.e., 512 * 2 ^ Zoom level)
   * where tileSize is the width of a tile in pixels.
   *
   * @return Returns a coordinate.
   */
  fun unproject(coordinate: MercatorCoordinate, zoomScale: Double): Point

  /**
   * Set current map projection for the map.
   * Should only be used to set projection before initial style was loaded to avoid visual blinking.
   * When working with projections added during runtime styling please use Style's setProjection method.
   *
   * @param mapProjection [MapProjection] to be applied to the map
   */
  @MapboxExperimental
  fun setMapProjection(mapProjection: MapProjection)

  /**
   * Get current map projection for the map. Should be used before initial style was loaded.
   * When working with projections added during runtime styling please use Style's getProjection method.
   *
   * @return [MapProjection] map is using.
   */
  @MapboxExperimental
  fun getMapProjection(): MapProjection
}