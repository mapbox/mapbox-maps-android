package com.mapbox.maps.extension.style.sources

import com.mapbox.geojson.Feature
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.utils.take

/**
 * Returns all the leaves of a cluster (given its cluster_id), with pagination support: limit is
 * the number of leaves to return (set to Infinity for all points), and offset is the amount
 * of points to skip (for pagination).
 *
 * @param clusterId from which to retrieve leaves from
 * @param limit is the number of points to return
 * @param offset is the amount of points to skip (for pagination)
 *
 * @return An array of features for the underlying leaves.
 */
fun GeoJsonSource.getClusterLeaves(
  clusterId: Int,
  limit: Int,
  offset: Int
): MutableList<Feature> {
  return this.delegate?.getStyleGeoJSONSourceClusterLeaves(this.sourceId, clusterId, limit, offset).take()
}

/**
 * Returns the zoom on which the cluster expands into several children
 * (useful for "click to zoom" feature).
 *
 * @param clusterId Cluster from which to retrieve the expansion zoom from
 *
 * @return The zoom on which the cluster expands into several children.
 */
fun GeoJsonSource.getExpansionZoom(
  clusterId: Int
): Byte {
  return this.delegate?.getStyleGeoJSONSourceClusterExpansionZoom(this.sourceId, clusterId).take()
}

/**
 * Returns the children of a cluster (on the next zoom level).
 *
 * @param clusterId from which to retrieve children from
 *
 * @return An array of features for the underlying children.
 */
fun GeoJsonSource.getClusterChildren(
  clusterId: Int
): MutableList<Feature> {
  return this.delegate?.getStyleGeoJSONSourceClusterChildren(this.sourceId, clusterId).take()
}