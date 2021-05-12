// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Geometry
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.Source
import com.mapbox.maps.extension.style.types.SourceDsl
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.extension.style.utils.toValue

/**
 * A GeoJSON data source.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#geojson)
 *
 */
class GeoJsonSource(
  builder: Builder
) : Source(builder.sourceId) {

  var geoJsonParsed = false
  var geoJsonParsedListener: (() -> Unit)? = null

  private constructor(
    builder: Builder,
    // will always be instance of Geometry, Feature, FeatureCollection
    data: Any?,
    onGeoJsonParsed: (GeoJsonSource) -> Unit
  ) : this(builder) {
    if (data != null && (data is Geometry || data is FeatureCollection || data is Feature)) {
      workerHandler.post {
        val property = PropertyValue(
          "data",
          when(data) {
            is Geometry -> data.toValue()
            is FeatureCollection -> data.toValue()
            is Feature -> data.toValue()
            else -> null
          }
        )
        mainHandler.post {
          setProperty(property)
          geoJsonParsed = true
          onGeoJsonParsed.invoke(this)
          geoJsonParsedListener?.invoke()
        }
      }
    }
  }

  init {
    sourceProperties.putAll(builder.properties)
    volatileSourceProperties.putAll(builder.volatileProperties)
  }

  /**
   * Get the type of the current source as a String.
   */
  override fun getType(): String {
    return "geojson"
  }

  /**
   * A URL to a GeoJSON file, or inline GeoJSON.
   */
  fun data(value: String) = apply {
    setProperty(PropertyValue("data", TypeUtils.wrapToValue(value)))
  }

  /**
   * A URL to a GeoJSON file, or inline GeoJSON.
   */
  fun data(value: Expression) = apply {
    setProperty(PropertyValue("data", value))
  }

  /**
   * A URL to a GeoJSON file, or inline GeoJSON.
   *
   * Note: Getter for plain Geojson string data is not supported due to performance consideration.
   */
  val data: String?
    /**
     * Get the Data property
     *
     * @return String
     */
    get() = getPropertyValue("data")

  /**
   * A URL to a GeoJSON file, or inline GeoJSON.
   *
   * Note: Getter for plain Geojson string data is not supported due to performance consideration.
   */
  val dataAsExpression: Expression?
    /**
     * Get the Data property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("data")?.let {
        return it
      }
      data?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * A URL to a GeoJSON file, or inline GeoJSON.
   */
  fun url(value: String) = apply {
    data(value)
  }

  /**
   * A URL to a GeoJSON file, or inline GeoJSON.
   */
  fun url(value: Expression) = apply {
    data(value)
  }

  /**
   * Maximum zoom level at which to create vector tiles (higher means greater detail at high zoom
   * levels).
   */
  val maxzoom: Long?
    /**
     * Get the Maxzoom property
     *
     * @return Long
     */
    get() = getPropertyValue("maxzoom")

  /**
   * Maximum zoom level at which to create vector tiles (higher means greater detail at high zoom
   * levels).
   */
  val maxzoomAsExpression: Expression?
    /**
     * Get the Maxzoom property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("maxzoom")?.let {
        return it
      }
      maxzoom?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Contains an attribution to be displayed when the map is shown to a user.
   */
  val attribution: String?
    /**
     * Get the Attribution property
     *
     * @return String
     */
    get() = getPropertyValue("attribution")

  /**
   * Contains an attribution to be displayed when the map is shown to a user.
   */
  val attributionAsExpression: Expression?
    /**
     * Get the Attribution property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("attribution")?.let {
        return it
      }
      attribution?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Size of the tile buffer on each side. A value of 0 produces no buffer. A
   * value of 512 produces a buffer as wide as the tile itself. Larger values produce fewer
   * rendering artifacts near tile edges and slower performance.
   */
  val buffer: Long?
    /**
     * Get the Buffer property
     *
     * @return Long
     */
    get() = getPropertyValue("buffer")

  /**
   * Size of the tile buffer on each side. A value of 0 produces no buffer. A
   * value of 512 produces a buffer as wide as the tile itself. Larger values produce fewer
   * rendering artifacts near tile edges and slower performance.
   */
  val bufferAsExpression: Expression?
    /**
     * Get the Buffer property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("buffer")?.let {
        return it
      }
      buffer?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
   */
  val tolerance: Double?
    /**
     * Get the Tolerance property
     *
     * @return Double
     */
    get() = getPropertyValue("tolerance")

  /**
   * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
   */
  val toleranceAsExpression: Expression?
    /**
     * Get the Tolerance property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("tolerance")?.let {
        return it
      }
      tolerance?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If the data is a collection of point features, setting this to true clusters the points
   * by radius into groups. Cluster groups become new `Point` features in the source with additional properties:
   * - `cluster` Is `true` if the point is a cluster
   * - `cluster_id` A unqiue id for the cluster to be used in conjunction with the
   * [cluster inspection methods](https://www.mapbox.com/mapbox-gl-js/api/#geojsonsource#getclusterexpansionzoom)
   * - `point_count` Number of original points grouped into this cluster
   * - `point_count_abbreviated` An abbreviated point count
   */
  val cluster: Boolean?
    /**
     * Get the Cluster property
     *
     * @return Boolean
     */
    get() = getPropertyValue("cluster")

  /**
   * If the data is a collection of point features, setting this to true clusters the points
   * by radius into groups. Cluster groups become new `Point` features in the source with additional properties:
   * - `cluster` Is `true` if the point is a cluster
   * - `cluster_id` A unqiue id for the cluster to be used in conjunction with the
   * [cluster inspection methods](https://www.mapbox.com/mapbox-gl-js/api/#geojsonsource#getclusterexpansionzoom)
   * - `point_count` Number of original points grouped into this cluster
   * - `point_count_abbreviated` An abbreviated point count
   */
  val clusterAsExpression: Expression?
    /**
     * Get the Cluster property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("cluster")?.let {
        return it
      }
      cluster?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
   * to the width of a tile.
   */
  val clusterRadius: Long?
    /**
     * Get the ClusterRadius property
     *
     * @return Long
     */
    get() = getPropertyValue("clusterRadius")

  /**
   * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
   * to the width of a tile.
   */
  val clusterRadiusAsExpression: Expression?
    /**
     * Get the ClusterRadius property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("clusterRadius")?.let {
        return it
      }
      clusterRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less
   * than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom
   * levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
   */
  val clusterMaxZoom: Long?
    /**
     * Get the ClusterMaxZoom property
     *
     * @return Long
     */
    get() = getPropertyValue("clusterMaxZoom")

  /**
   * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less
   * than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom
   * levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
   */
  val clusterMaxZoomAsExpression: Expression?
    /**
     * Get the ClusterMaxZoom property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("clusterMaxZoom")?.let {
        return it
      }
      clusterMaxZoom?.let {
        return Expression.literal(it)
      }
      return null
    }

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
  val clusterProperties: HashMap<String, Any>?
    /**
     * Get the ClusterProperties property
     *
     * @return HashMap<String, Any>
     */
    get() = getPropertyValue("clusterProperties")

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
  val clusterPropertiesAsExpression: Expression?
    /**
     * Get the ClusterProperties property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("clusterProperties")?.let {
        return it
      }
      clusterProperties?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Whether to calculate line distance metrics. This is required for line layers that specify `line-gradient` values.
   */
  val lineMetrics: Boolean?
    /**
     * Get the LineMetrics property
     *
     * @return Boolean
     */
    get() = getPropertyValue("lineMetrics")

  /**
   * Whether to calculate line distance metrics. This is required for line layers that specify `line-gradient` values.
   */
  val lineMetricsAsExpression: Expression?
    /**
     * Get the LineMetrics property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("lineMetrics")?.let {
        return it
      }
      lineMetrics?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
   * will first request a tile for `zoom - delta` in a attempt to display a full
   * map at lower resolution as quick as possible. It will get clamped at the tile source
   * minimum zoom. The default `delta` is 4.
   */
  fun prefetchZoomDelta(value: Long = 4L) = apply {
    setVolatileProperty(PropertyValue("prefetch-zoom-delta", TypeUtils.wrapToValue(value)))
  }

  /**
   * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
   * will first request a tile for `zoom - delta` in a attempt to display a full
   * map at lower resolution as quick as possible. It will get clamped at the tile source
   * minimum zoom. The default `delta` is 4.
   */
  fun prefetchZoomDelta(value: Expression) = apply {
    setVolatileProperty(PropertyValue("prefetch-zoom-delta", value))
  }

  /**
   * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
   * will first request a tile for `zoom - delta` in a attempt to display a full
   * map at lower resolution as quick as possible. It will get clamped at the tile source
   * minimum zoom. The default `delta` is 4.
   */
  val prefetchZoomDelta: Long?
    /**
     * Get the PrefetchZoomDelta property
     *
     * @return Long
     */
    get() = getPropertyValue("prefetch-zoom-delta")

  /**
   * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
   * will first request a tile for `zoom - delta` in a attempt to display a full
   * map at lower resolution as quick as possible. It will get clamped at the tile source
   * minimum zoom. The default `delta` is 4.
   */
  val prefetchZoomDeltaAsExpression: Expression?
    /**
     * Get the PrefetchZoomDelta property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("prefetch-zoom-delta")?.let {
        return it
      }
      prefetchZoomDelta?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Add a Feature to the GeojsonSource.
   *
   * @param value the feature
   */
  fun feature(value: Feature) = apply {
    workerHandler.post {
      val property = PropertyValue("data", value.toValue())
      mainHandler.post {
        setProperty(property)
      }
    }
  }

  /**
   * Add a FeatureCollection to the GeojsonSource.
   *
   * @param value the feature collection
   */
  fun featureCollection(value: FeatureCollection) = apply {
    workerHandler.post {
      val property = PropertyValue("data", value.toValue())
      mainHandler.post {
        setProperty(property)
      }
    }
  }

  /**
   * Add a Geometry to the GeojsonSource.
   *
   * @param value the geometry
   */
  fun geometry(value: Geometry) = apply {
    workerHandler.post {
      val property = PropertyValue("data", value.toValue())
      mainHandler.post {
        setProperty(property)
      }
    }
  }

  /**
   * Builder for GeoJsonSource.
   *
   * @param sourceId the ID of the source
   */
  @SourceDsl
  class Builder(
    val sourceId: String,
    private val onGeoJsonParsed: (GeoJsonSource) -> Unit = {}
  ) {

    private var rawData: Any? = null
    internal val properties = HashMap<String, PropertyValue<*>>()
    // Properties that only settable after the source is added to the style.
    internal val volatileProperties = HashMap<String, PropertyValue<*>>()

    /**
     * A URL to a GeoJSON file, or inline GeoJSON.
     */
    fun data(value: String) = apply {
      val propertyValue = PropertyValue("data", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A URL to a GeoJSON file, or inline GeoJSON.
     */
    fun data(value: Expression) = apply {
      val propertyValue = PropertyValue("data", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A URL to a GeoJSON file, or inline GeoJSON.
     */
    fun url(value: String) = apply {
      data(value)
    }

    /**
     * A URL to a GeoJSON file, or inline GeoJSON.
     */
    fun url(value: Expression) = apply {
      data(value)
    }

    /**
     * Maximum zoom level at which to create vector tiles (higher means greater detail at high zoom
     * levels).
     */
    fun maxzoom(value: Long = 18L) = apply {
      val propertyValue = PropertyValue("maxzoom", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Maximum zoom level at which to create vector tiles (higher means greater detail at high zoom
     * levels).
     */
    fun maxzoom(value: Expression) = apply {
      val propertyValue = PropertyValue("maxzoom", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Contains an attribution to be displayed when the map is shown to a user.
     */
    fun attribution(value: String) = apply {
      val propertyValue = PropertyValue("attribution", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Contains an attribution to be displayed when the map is shown to a user.
     */
    fun attribution(value: Expression) = apply {
      val propertyValue = PropertyValue("attribution", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Size of the tile buffer on each side. A value of 0 produces no buffer. A
     * value of 512 produces a buffer as wide as the tile itself. Larger values produce fewer
     * rendering artifacts near tile edges and slower performance.
     */
    fun buffer(value: Long = 128L) = apply {
      val propertyValue = PropertyValue("buffer", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Size of the tile buffer on each side. A value of 0 produces no buffer. A
     * value of 512 produces a buffer as wide as the tile itself. Larger values produce fewer
     * rendering artifacts near tile edges and slower performance.
     */
    fun buffer(value: Expression) = apply {
      val propertyValue = PropertyValue("buffer", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
     */
    fun tolerance(value: Double = 0.375) = apply {
      val propertyValue = PropertyValue("tolerance", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
     */
    fun tolerance(value: Expression) = apply {
      val propertyValue = PropertyValue("tolerance", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * If the data is a collection of point features, setting this to true clusters the points
     * by radius into groups. Cluster groups become new `Point` features in the source with additional properties:
     * - `cluster` Is `true` if the point is a cluster
     * - `cluster_id` A unqiue id for the cluster to be used in conjunction with the
     * [cluster inspection methods](https://www.mapbox.com/mapbox-gl-js/api/#geojsonsource#getclusterexpansionzoom)
     * - `point_count` Number of original points grouped into this cluster
     * - `point_count_abbreviated` An abbreviated point count
     */
    fun cluster(value: Boolean = false) = apply {
      val propertyValue = PropertyValue("cluster", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * If the data is a collection of point features, setting this to true clusters the points
     * by radius into groups. Cluster groups become new `Point` features in the source with additional properties:
     * - `cluster` Is `true` if the point is a cluster
     * - `cluster_id` A unqiue id for the cluster to be used in conjunction with the
     * [cluster inspection methods](https://www.mapbox.com/mapbox-gl-js/api/#geojsonsource#getclusterexpansionzoom)
     * - `point_count` Number of original points grouped into this cluster
     * - `point_count_abbreviated` An abbreviated point count
     */
    fun cluster(value: Expression) = apply {
      val propertyValue = PropertyValue("cluster", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
     * to the width of a tile.
     */
    fun clusterRadius(value: Long = 50L) = apply {
      val propertyValue = PropertyValue("clusterRadius", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
     * to the width of a tile.
     */
    fun clusterRadius(value: Expression) = apply {
      val propertyValue = PropertyValue("clusterRadius", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less
     * than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom
     * levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
     */
    fun clusterMaxZoom(value: Long) = apply {
      val propertyValue = PropertyValue("clusterMaxZoom", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less
     * than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom
     * levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
     */
    fun clusterMaxZoom(value: Expression) = apply {
      val propertyValue = PropertyValue("clusterMaxZoom", value)
      properties[propertyValue.propertyName] = propertyValue
    }

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
    fun clusterProperties(value: HashMap<String, Any>) = apply {
      val propertyValue = PropertyValue("clusterProperties", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

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
    fun clusterProperties(value: Expression) = apply {
      val propertyValue = PropertyValue("clusterProperties", value)
      properties[propertyValue.propertyName] = propertyValue
    }

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
     *
     * @param propertyName name of the property
     * @param operatorExpr operatorExpr is any expression function that accepts at least 2 operands (e.g. "+" or
     * "max").
     * It accumulates the property value from clusters/points the cluster contains. It can either be a literal
     * with single operator, or be a valid expression
     * @param mapExpr map expression produces the value of a single point, it shall be a valid
     * expression
     *
     */
    fun clusterProperty(propertyName: String, operatorExpr: Expression, mapExpr: Expression) =
      apply {
        @Suppress("UNCHECKED_CAST")
        val options: HashMap<String, Value> =
          properties["clusterProperties"]?.value?.contents as? HashMap<String, Value>
            ?: HashMap()
        options[propertyName] = Value(listOf(operatorExpr, mapExpr))
        val propertyValue = PropertyValue("clusterProperties", TypeUtils.wrapToValue(options))
        properties[propertyValue.propertyName] = propertyValue
      }

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
     *
     * @param propertyName name of the property
     * @param mapExpr map expression produces the value of a single point, it shall be a valid
     * expression
     *
     */
    fun clusterProperty(propertyName: String, mapExpr: Expression) =
      apply {
        @Suppress("UNCHECKED_CAST")
        val options: HashMap<String, Value> =
          properties["clusterProperties"]?.value?.contents as? HashMap<String, Value>
            ?: HashMap()
        options[propertyName] = mapExpr
        val propertyValue = PropertyValue("clusterProperties", TypeUtils.wrapToValue(options))
        properties[propertyValue.propertyName] = propertyValue
      }

    /**
     * Whether to calculate line distance metrics. This is required for line layers that specify `line-gradient` values.
     */
    fun lineMetrics(value: Boolean = false) = apply {
      val propertyValue = PropertyValue("lineMetrics", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Whether to calculate line distance metrics. This is required for line layers that specify `line-gradient` values.
     */
    fun lineMetrics(value: Expression) = apply {
      val propertyValue = PropertyValue("lineMetrics", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
     * will first request a tile for `zoom - delta` in a attempt to display a full
     * map at lower resolution as quick as possible. It will get clamped at the tile source
     * minimum zoom. The default `delta` is 4.
     */
    fun prefetchZoomDelta(value: Long = 4L) = apply {
      val propertyValue = PropertyValue("prefetch-zoom-delta", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
     * will first request a tile for `zoom - delta` in a attempt to display a full
     * map at lower resolution as quick as possible. It will get clamped at the tile source
     * minimum zoom. The default `delta` is 4.
     */
    fun prefetchZoomDelta(value: Expression) = apply {
      val propertyValue = PropertyValue("prefetch-zoom-delta", value)
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Add a Feature to the GeojsonSource.
     *
     * @param value the feature
     */
    fun feature(value: Feature) = apply {
      rawData = value
      val propertyValue = PropertyValue("data", "")
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Add a FeatureCollection to the GeojsonSource.
     *
     * @param value the feature collection
     */
    fun featureCollection(value: FeatureCollection) = apply {
      rawData = value
      val propertyValue = PropertyValue("data", "")
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Add a Geometry to the GeojsonSource.
     *
     * @param value the geometry
     */
    fun geometry(value: Geometry) = apply {
      rawData = value
      val propertyValue = PropertyValue("data", "")
      properties[propertyValue.propertyName] = propertyValue
    }
    /**
     * Build the GeoJsonSource.
     *
     * @return the GeoJsonSource
     */
    fun build() = GeoJsonSource(this, rawData, onGeoJsonParsed)
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /**
     * Maximum zoom level at which to create vector tiles (higher means greater detail at high zoom
     * levels).
     */
    val defaultMaxzoom: Long?
      /**
       * Get the Maxzoom property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "maxzoom").silentUnwrap()

    /**
     * Maximum zoom level at which to create vector tiles (higher means greater detail at high zoom
     * levels).
     */
    val defaultMaxzoomAsExpression: Expression?
      /**
       * Get the Maxzoom property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("geojson", "maxzoom").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultMaxzoom?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Size of the tile buffer on each side. A value of 0 produces no buffer. A
     * value of 512 produces a buffer as wide as the tile itself. Larger values produce fewer
     * rendering artifacts near tile edges and slower performance.
     */
    val defaultBuffer: Long?
      /**
       * Get the Buffer property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "buffer").silentUnwrap()

    /**
     * Size of the tile buffer on each side. A value of 0 produces no buffer. A
     * value of 512 produces a buffer as wide as the tile itself. Larger values produce fewer
     * rendering artifacts near tile edges and slower performance.
     */
    val defaultBufferAsExpression: Expression?
      /**
       * Get the Buffer property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("geojson", "buffer").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultBuffer?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
     */
    val defaultTolerance: Double?
      /**
       * Get the Tolerance property
       *
       * @return Double
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "tolerance").silentUnwrap()

    /**
     * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
     */
    val defaultToleranceAsExpression: Expression?
      /**
       * Get the Tolerance property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("geojson", "tolerance").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTolerance?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * If the data is a collection of point features, setting this to true clusters the points
     * by radius into groups. Cluster groups become new `Point` features in the source with additional properties:
     * - `cluster` Is `true` if the point is a cluster
     * - `cluster_id` A unqiue id for the cluster to be used in conjunction with the
     * [cluster inspection methods](https://www.mapbox.com/mapbox-gl-js/api/#geojsonsource#getclusterexpansionzoom)
     * - `point_count` Number of original points grouped into this cluster
     * - `point_count_abbreviated` An abbreviated point count
     */
    val defaultCluster: Boolean?
      /**
       * Get the Cluster property
       *
       * @return Boolean
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "cluster").silentUnwrap()

    /**
     * If the data is a collection of point features, setting this to true clusters the points
     * by radius into groups. Cluster groups become new `Point` features in the source with additional properties:
     * - `cluster` Is `true` if the point is a cluster
     * - `cluster_id` A unqiue id for the cluster to be used in conjunction with the
     * [cluster inspection methods](https://www.mapbox.com/mapbox-gl-js/api/#geojsonsource#getclusterexpansionzoom)
     * - `point_count` Number of original points grouped into this cluster
     * - `point_count_abbreviated` An abbreviated point count
     */
    val defaultClusterAsExpression: Expression?
      /**
       * Get the Cluster property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("geojson", "cluster").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCluster?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
     * to the width of a tile.
     */
    val defaultClusterRadius: Long?
      /**
       * Get the ClusterRadius property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterRadius").silentUnwrap()

    /**
     * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
     * to the width of a tile.
     */
    val defaultClusterRadiusAsExpression: Expression?
      /**
       * Get the ClusterRadius property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterRadius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultClusterRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less
     * than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom
     * levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
     */
    val defaultClusterMaxZoom: Long?
      /**
       * Get the ClusterMaxZoom property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterMaxZoom").silentUnwrap()

    /**
     * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less
     * than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom
     * levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
     */
    val defaultClusterMaxZoomAsExpression: Expression?
      /**
       * Get the ClusterMaxZoom property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterMaxZoom").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultClusterMaxZoom?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Whether to calculate line distance metrics. This is required for line layers that specify `line-gradient` values.
     */
    val defaultLineMetrics: Boolean?
      /**
       * Get the LineMetrics property
       *
       * @return Boolean
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "lineMetrics").silentUnwrap()

    /**
     * Whether to calculate line distance metrics. This is required for line layers that specify `line-gradient` values.
     */
    val defaultLineMetricsAsExpression: Expression?
      /**
       * Get the LineMetrics property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("geojson", "lineMetrics").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineMetrics?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
     * will first request a tile for `zoom - delta` in a attempt to display a full
     * map at lower resolution as quick as possible. It will get clamped at the tile source
     * minimum zoom. The default `delta` is 4.
     */
    val defaultPrefetchZoomDelta: Long?
      /**
       * Get the PrefetchZoomDelta property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "prefetch-zoom-delta").silentUnwrap()

    /**
     * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
     * will first request a tile for `zoom - delta` in a attempt to display a full
     * map at lower resolution as quick as possible. It will get clamped at the tile source
     * minimum zoom. The default `delta` is 4.
     */
    val defaultPrefetchZoomDeltaAsExpression: Expression?
      /**
       * Get the PrefetchZoomDelta property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("geojson", "prefetch-zoom-delta").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultPrefetchZoomDelta?.let {
          return Expression.literal(it)
        }
        return null
      }
  }
}

/**
 * DSL function for [GeoJsonSource] performing parsing using background thread.
 * Immediately returns [GeoJsonSource] with no data set and starts preparing actual data
 * using a worker thread.
 *
 * If using runtime styling:
 *
 * loadStyle(style(Style.DARK) {
 *   +geoJsonSource(id) {
 *    featureCollection(collection)
 *   }
 *   ...
 * }
 *
 * compositing style will be performed correctly under the hood.
 */
fun geoJsonSource(
  id: String,
  block: GeoJsonSource.Builder.() -> Unit
): GeoJsonSource = GeoJsonSource.Builder(id, {}).apply(block).build()

/**
 * DSL function for [GeoJsonSource] performing parsing using background thread.
 * Immediately returns [GeoJsonSource] with no data set,
 * fully parsed [GeoJsonSource] is returned in [onGeoJsonParsed] callback.
 *
 * Using this method means that it is user's responsibility to proceed with adding layers or
 * other style objects in [onGeoJsonParsed] callback.
 */
fun geoJsonSource(
  id: String,
  block: GeoJsonSource.Builder.() -> Unit,
  onGeoJsonParsed: (GeoJsonSource) -> Unit
): GeoJsonSource = GeoJsonSource.Builder(id, onGeoJsonParsed).apply(block).build()

// End of generated file.