package com.mapbox.maps.plugin.annotation

import android.graphics.Color

data class ClusterOptions(

  /**
   * If the data is a collection of point features, setting this to true clusters the points
   * by radius into groups. Cluster groups become new `Point` features in the source with additional properties:
   * - `cluster` Is `true` if the point is a cluster
   * - `cluster_id` A unqiue id for the cluster to be used in conjunction with the
   * [cluster inspection methods](https://www.mapbox.com/mapbox-gl-js/api/#geojsonsource#getclusterexpansionzoom)
   * - `point_count` Number of original points grouped into this cluster
   * - `point_count_abbreviated` An abbreviated point count
   */
  val cluster: Boolean? = null,

  /**
   * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
   * to the width of a tile.
   */
  val clusterRadius: Long = 50,
  val circleRadius: Double = 18.0,
  val textColor: Int = Color.WHITE,
  val textSize: Double = 12.0,
  val textField:String ="get(\"point_count\")",
  /**
   * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less
   * than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom
   * levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
   */
  val clusterMaxZoom: Long = 14,

  val colorLevels: List<Pair<Int, Int>> = listOf(Pair(0, Color.BLUE)),
  /**
   * An object defining custom properties on the generated clusters if clustering is enabled, aggregating values from
   * clustered points. Has the form `{"property_name": [operator, map_expression]}`. `operator` is any expression function that accepts at
   * least 2 operands (e.g. `"+"` or `"max"`) — it accumulates the property value from clusters/points the
   * cluster contains; `map_expression` produces the value of a single point.
   *
   * Example: `{"sum": ["+", ["get", "scalerank"]]}`.
   *
   * For more advanced use cases, in place of `operator`, you can use a custom reduce expression
   * that references a special `["accumulated"]` value, e.g.:
   * `{"sum": [["+", ["accumulated"], ["get", "sum"]], ["get", "scalerank"]]}`
   */
  val clusterProperties: HashMap<String, Any>? = null,
)
