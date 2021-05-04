package com.mapbox.maps.plugin.annotation

import android.graphics.Color
import com.mapbox.bindgen.Value

/**
 * Options to show and configure symbol clustering with using SymbolManager.
 *
 * It exposes a minimal of configuration options, a more advanced setup can be created manually with
 * using CircleLayer and SymbolLayers directly.
 *
 */
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
  val cluster: Boolean = true,

  /**
   * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
   * to the width of a tile, 50 by default.
   */
  val clusterRadius: Long = 50,

  /**
   * The circle radius of the cluster items in expression.
   * Have higher priority than [circleRadius], but will apply [circleRadius] if not set any value.
   */
  val circleRadiusExpression: Value? = null,

  /**
   * The circle radius of the cluster items in expression, literal(18) by default
   */
  val circleRadius: Double = 18.0,

  /**
   * The text color of cluster item in expression.
   * Have higher priority than [textColor], but will apply [textColor] if not set any value.
   */
  val textColorExpression: Value? = null,

  /**
   * The text color of cluster item in expression. color(Color.WHITE) by default
   */
  val textColor: Int = Color.WHITE,

  /**
   * The text size of cluster item in expression.
   * Have higher priority than[textSize], but will apply [textSize] if not set any value.
   */
  val textSizeExpression: Value? = null,

  /**
   * The text size of cluster item in expression. literal(12) by default.
   */
  val textSize: Double = 12.0,

  /**
   *  The text field of a cluster item in expression. get("point_count") by default.
   */
  val textField: Value? = null,

  /**
   * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less
   * than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom
   * levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
   */
  val clusterMaxZoom: Long = 14,

  /**
   * The cluster color levels, which a pair constructed with amount of point and a int color value.
   */
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