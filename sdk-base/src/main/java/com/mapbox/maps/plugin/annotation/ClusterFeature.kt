package com.mapbox.maps.plugin.annotation

import androidx.annotation.RestrictTo
import com.mapbox.geojson.Feature
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature
import java.util.Objects

/**
 * [ClusterFeature] is a strongly typed class with cluster related properties in addition to the underlying [Feature] built from [Point].
 *
 * @param featuresetFeature [FeaturesetFeature<FeatureState>] that holds original feature representing the cluster.
 */
class ClusterFeature @RestrictTo(RestrictTo.Scope.LIBRARY) constructor(private val featuresetFeature: FeaturesetFeature<FeatureState>) {

  /**
   * Original feature representing the cluster built from [Point].
   */
  val originalFeature = featuresetFeature.originalFeature

  /**
   * A unique id for the cluster to be used in conjunction with the cluster inspection methods:
   * [com.mapbox.maps.MapboxMap.getGeoJsonClusterExpansionZoom], [com.mapbox.maps.MapboxMap.getGeoJsonClusterChildren], [com.mapbox.maps.MapboxMap.getGeoJsonClusterLeaves]
   * Returns the empty string if no such property exists.
   */
  val clusterId: String = featuresetFeature.properties.optString("cluster_id", "")

  /**
   * Number of original points grouped into this cluster.
   * Returns 0L if no such property exists.
   */
  val pointCount: Long = featuresetFeature.properties.optLong("point_count", 0L)

  /**
   * An abbreviated point count.
   * It's a human-readable representation of the amount of items in a cluster.
   * For example, if the cluster has 1823 items [pointCountAbbreviated] would be "1.8K"
   * Returns the empty string if no such property exists.
   */
  val pointCountAbbreviated: String = featuresetFeature.properties.optString("point_count_abbreviated", "")

  /**
   * See [Any.equals]
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ClusterFeature

    if (featuresetFeature != other.featuresetFeature) return false
    if (originalFeature != other.originalFeature) return false
    if (clusterId != other.clusterId) return false
    if (pointCount != other.pointCount) return false
    if (pointCountAbbreviated != other.pointCountAbbreviated) return false

    return true
  }

  /**
   * See [Any.hashCode]
   */
  override fun hashCode(): Int {
    return Objects.hash(featuresetFeature, clusterId, pointCount, pointCountAbbreviated, originalFeature)
  }
}