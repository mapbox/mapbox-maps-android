package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.QueryFeatureExtensionCallback
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource

/**
 * Returns all the leaves (original points) of a cluster (given its cluster_id), with pagination support: limit is the number of leaves
 * to return (set to Infinity for all points), and offset is the amount of points to skip (for pagination).
 *
 * Requires configuring the source as a cluster by calling [GeoJsonSource.Builder#cluster(boolean)].
 *
 * @param cluster Cluster from which to retrieve leaves from
 * @param limit   The number of points to return from the query (must use type 'uint64_t', set to maximum for all points). Defaults to 10.
 * @param offset  The amount of points to skip (for pagination, must use type 'uint64_t'). Defaults to 0.
 * @param callback The result will be returned through the [QueryFeatureExtensionCallback].
 *         The result is a feature collection or a string describing an error if the operation was not successful.
 */
@JvmOverloads
fun GeoJsonSource.getClusterLeaves(
  cluster: Feature,
  limit: Long = 10,
  offset: Long = 0,
  callback: QueryFeatureExtensionCallback
) =
  featureDelegate?.queryFeatureExtensions(
    sourceId, cluster, SUPER_CLUSTER, LEAVES,
    hashMapOf(Pair(LIMIT, Value(limit)), Pair(OFFSET, Value(offset))),
    callback
  )

/**
 * Returns the children (original points or clusters) of a cluster (on the next zoom level)
 * given its id (cluster_id value from feature properties).
 *
 * Requires configuring the source as a cluster by calling [GeoJsonSource.Builder#cluster(boolean)].
 *
 * @param sourceIdentifier Style source identifier.
 * @param cluster cluster from which to retrieve children from
 * @param callback The result will be returned through the [QueryFeatureExtensionCallback].
 *         The result is a feature collection or a string describing an error if the operation was not successful.
 */
fun GeoJsonSource.getClusterChildren(
  sourceIdentifier: String,
  cluster: Feature,
  callback: QueryFeatureExtensionCallback
) = featureDelegate?.queryFeatureExtensions(
  sourceIdentifier, cluster, SUPER_CLUSTER, CHILDREN, null, callback
)

/**
 * Returns the zoom on which the cluster expands into several children (useful for "click to zoom" feature)
 * given the cluster's cluster_id (cluster_id value from feature properties).
 *
 * Requires configuring the source as a cluster by calling [GeoJsonSource.Builder#cluster(boolean)].
 *
 * @param sourceIdentifier Style source identifier.
 * @param cluster cluster from which to retrieve the expansion zoom from
 * @param callback The result will be returned through the [QueryFeatureExtensionCallback].
 *         The result is a feature extension value containing a value or a string describing an error if the operation was not successful.
 */
fun GeoJsonSource.getClusterExpansionZoom(
  sourceIdentifier: String,
  cluster: Feature,
  callback: QueryFeatureExtensionCallback
) = featureDelegate?.queryFeatureExtensions(
  sourceIdentifier, cluster, SUPER_CLUSTER, EXPANDSION_ZOOM, null, callback
)

private const val SUPER_CLUSTER = "supercluster"
private const val LEAVES = "leaves"
private const val LIMIT = "limit"
private const val OFFSET = "offset"
private const val CHILDREN = "children"
private const val EXPANDSION_ZOOM = "expansion-zoom"
