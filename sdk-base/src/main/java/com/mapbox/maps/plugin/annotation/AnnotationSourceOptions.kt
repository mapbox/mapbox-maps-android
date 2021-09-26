package com.mapbox.maps.plugin.annotation

/**
 * Configure class for composing GeoJsonSource objects that included in Annotation
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#geojson)
 */
data class AnnotationSourceOptions @JvmOverloads constructor(
  /**
   * Maximum zoom level at which to create vector tiles (higher means greater detail at high zoom
   * levels).
   */
  val maxZoom: Long? = null,

  /**
   * Size of the tile buffer on each side. A value of 0 produces no buffer. A
   * value of 512 produces a buffer as wide as the tile itself. Larger values produce fewer
   * rendering artifacts near tile edges and slower performance.
   */
  val buffer: Long? = null,

  /**
   * Whether to calculate line distance metrics. This is required for line layers that specify `line-gradient` values.
   */
  val lineMetrics: Boolean? = null,

  /**
   * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
   */
  val tolerance: Double? = null,

  /**
   * Options to show and configure symbol clustering with using SymbolManager.
   */
  val clusterOptions: ClusterOptions? = null
)