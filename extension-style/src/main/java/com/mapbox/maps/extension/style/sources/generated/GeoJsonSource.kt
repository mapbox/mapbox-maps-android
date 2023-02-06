// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import android.os.Handler
import android.os.HandlerThread
import android.os.Process.THREAD_PRIORITY_DEFAULT
import androidx.annotation.VisibleForTesting
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.GeoJson
import com.mapbox.geojson.Geometry
import com.mapbox.maps.GeoJSONSourceData
import com.mapbox.maps.MapEvents
import com.mapbox.maps.MapboxConcurrentGeometryModificationException
import com.mapbox.maps.Observer
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.Source
import com.mapbox.maps.extension.style.types.PromoteId
import com.mapbox.maps.extension.style.types.SourceDsl
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.extension.style.utils.toValue
import com.mapbox.maps.logW

/**
 * A GeoJSON data source.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#geojson)
 *
 */
class GeoJsonSource(builder: Builder) : Source(builder.sourceId) {
  private val workerHandler by lazy {
    Handler(workerThread.looper)
  }

  private var initGeoJson: GeoJson? = null
  private var initData: String? = null
  private var initDataId: String? = null

  private constructor(
    builder: Builder,
    geoJson: GeoJson?,
    data: String?,
    dataId: String?,
  ) : this(builder) {
    this.initGeoJson = geoJson
    this.initData = data
    this.initDataId = dataId
  }

  private fun setGeoJson(geoJson: GeoJson, dataId: String? = null) {
    workerHandler.removeCallbacksAndMessages(null)
    workerHandler.post {
      if (dataId != null) {
        delegate?.setStyleGeoJSONSourceData(
          /* sourceId = */ sourceId,
          /* dataId = */ dataId,
          /* data = */ toGeoJsonData(geoJson)
        ) ?: logW(
          TAG,
          "GeoJsonSource (sourceId=$sourceId) was not able to set data" +
            " with `feature()`, `featureCollection()` or `geometry()` as there is no Style object."
        )
      } else {
        delegate?.setStyleGeoJSONSourceData(
          /* sourceId = */ sourceId,
          /* data = */ toGeoJsonData(geoJson)
        ) ?: logW(
          TAG,
          "GeoJsonSource (sourceId=$sourceId) was not able to set data (dataId=$dataId)" +
            " with `feature()`, `featureCollection()` or `geometry()` as there is no Style object."
        )
      }
    }
  }

  private fun setData(data: String, dataId: String? = null) {
    workerHandler.removeCallbacksAndMessages(null)
    workerHandler.post {
      if (dataId != null) {
        delegate?.setStyleGeoJSONSourceData(
          /* sourceId = */ sourceId,
          /* dataId = */ dataId,
          /* data = */ GeoJSONSourceData.valueOf(data)
        ) ?: logW(
          TAG,
          "GeoJsonSource (id=$sourceId) was not able to set data with `data()` or `url()` as there is no Style object."
        )
      } else {
        delegate?.setStyleGeoJSONSourceData(
          /* sourceId = */ sourceId,
          /* data = */ GeoJSONSourceData.valueOf(data)
        ) ?: logW(
          TAG,
          "GeoJsonSource (sourceId=$sourceId) was not able to set data (dataId=$dataId) `data()` or `url()`" +
            " as there is no Style object."
        )
      }
    }
  }

  override fun bindTo(delegate: StyleInterface) {
    super.bindTo(delegate)
    initGeoJson?.let {
      setGeoJson(it, initDataId)
      initGeoJson = null
    }
    initData?.let {
      setData(it, initDataId)
      initData = null
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
   * @param value an URL to a GeoJSON file, or an inline GeoJSON.
   * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
   */
  @JvmOverloads
  fun data(value: String, dataId: String? = null) = apply {
    setData(value, dataId)
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
   * @param value an URL to a GeoJSON file, or an inline GeoJSON.
   * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
   */
  @JvmOverloads
  fun url(value: String, dataId: String? = null) = apply {
    data(value, dataId)
  }

  /**
   * Maximum zoom level at which to create vector tiles (higher means greater detail at high zoom
   * levels).
   */
  val maxzoom: Long?
    /**
     * Get the Maxzoom property
     *
     * Use static method [GeoJsonSource.defaultMaxzoom] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("maxzoom")

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
   * Size of the tile buffer on each side. A value of 0 produces no buffer. A
   * value of 512 produces a buffer as wide as the tile itself. Larger values produce fewer
   * rendering artifacts near tile edges and slower performance.
   */
  val buffer: Long?
    /**
     * Get the Buffer property
     *
     * Use static method [GeoJsonSource.defaultBuffer] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("buffer")

  /**
   * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
   */
  val tolerance: Double?
    /**
     * Get the Tolerance property
     *
     * Use static method [GeoJsonSource.defaultTolerance] to get the default property.
     *
     * @return Double
     */
    get() = getPropertyValue("tolerance")

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
     * Use static method [GeoJsonSource.defaultCluster] to get the default property.
     *
     * @return Boolean
     */
    get() = getPropertyValue("cluster")

  /**
   * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
   * to the width of a tile.
   */
  val clusterRadius: Long?
    /**
     * Get the ClusterRadius property
     *
     * Use static method [GeoJsonSource.defaultClusterRadius] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("clusterRadius")

  /**
   * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less
   * than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom
   * levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
   */
  val clusterMaxZoom: Long?
    /**
     * Get the ClusterMaxZoom property
     *
     * Use static method [GeoJsonSource.defaultClusterMaxZoom] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("clusterMaxZoom")

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
   * Whether to calculate line distance metrics. This is required for line layers that specify `line-gradient` values.
   */
  val lineMetrics: Boolean?
    /**
     * Get the LineMetrics property
     *
     * Use static method [GeoJsonSource.defaultLineMetrics] to get the default property.
     *
     * @return Boolean
     */
    get() = getPropertyValue("lineMetrics")

  /**
   * Whether to generate ids for the geojson features. When enabled, the `feature.id` property will be auto
   * assigned based on its index in the `features` array, over-writing any previous values.
   */
  val generateId: Boolean?
    /**
     * Get the GenerateId property
     *
     * Use static method [GeoJsonSource.defaultGenerateId] to get the default property.
     *
     * @return Boolean
     */
    get() = getPropertyValue("generateId")

  /**
   * A property to use as a feature id (for feature state). Either a property name, or
   * an object of the form `{<sourceLayer>: <propertyName>}`.
   */
  val promoteId: PromoteId?
    /**
     * Get the PromoteId property
     *
     * @return PromoteId
     */
    get() {
      val propertyValue = getPropertyValue<Any>("promoteId")
      propertyValue?.let {
        return PromoteId.fromProperty(it)
      }
      return null
    }

  /**
   * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
   * will first request a tile at zoom level lower than zoom - delta, but so that
   * the zoom level is multiple of delta, in an attempt to display a full map at
   * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
   * The default delta is 4.
   */
  fun prefetchZoomDelta(value: Long = 4L) = apply {
    setVolatileProperty(PropertyValue("prefetch-zoom-delta", TypeUtils.wrapToValue(value)))
  }

  /**
   * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
   * will first request a tile at zoom level lower than zoom - delta, but so that
   * the zoom level is multiple of delta, in an attempt to display a full map at
   * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
   * The default delta is 4.
   */
  val prefetchZoomDelta: Long?
    /**
     * Get the PrefetchZoomDelta property
     *
     * Use static method [GeoJsonSource.defaultPrefetchZoomDelta] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("prefetch-zoom-delta")

  /**
   * Add a Feature to the GeojsonSource.
   * Data will be parsed from collection to [String] in a worker thread and
   * use main thread to pass this data to gl-native.
   *
   * In order to capture events when actual data is drawn on the map please refer to [Observer] API
   * and listen to [MapEvents.SOURCE_DATA_LOADED] (optionally pass `data-id` to filter the events)
   * or [MapEvents.MAP_LOADING_ERROR] with `type = metadata` if data parsing error has occurred.
   *
   * Note: This method is not thread-safe. The Feature is parsed on a worker thread, please make sure
   * the Feature is immutable as well as all collections that are used to build it.
   *
   * @param value the feature
   * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
   * @throws [MapboxConcurrentGeometryModificationException]
   */
  @JvmOverloads
  fun feature(value: Feature, dataId: String? = null): GeoJsonSource = applyGeoJsonData(value, dataId)

  /**
   * Add a Feature Collection to the GeojsonSource.
   * Data will be parsed from collection to [String] in a worker thread and
   * use main thread to pass this data to gl-native.
   *
   * In order to capture events when actual data is drawn on the map please refer to [Observer] API
   * and listen to [MapEvents.SOURCE_DATA_LOADED] (optionally pass `data-id` to filter the events)
   * or [MapEvents.MAP_LOADING_ERROR] with `type = metadata` if data parsing error has occurred.
   *
   * Note: This method is not thread-safe. The FeatureCollection is parsed on a worker thread, please make sure
   * the FeatureCollection is immutable as well as all collections that are used to build it.
   *
   * @param value the feature collection
   * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
   * @throws [MapboxConcurrentGeometryModificationException]
   */
  @JvmOverloads
  fun featureCollection(value: FeatureCollection, dataId: String? = null): GeoJsonSource = applyGeoJsonData(value, dataId)

  /**
   * Add a Geometry to the GeojsonSource.
   * Data will be parsed from collection to [String] in a worker thread and
   * use main thread to pass this data to gl-native.
   *
   * In order to capture events when actual data is drawn on the map please refer to [Observer] API
   * and listen to [MapEvents.SOURCE_DATA_LOADED] (optionally pass `data-id` to filter the events)
   * or [MapEvents.MAP_LOADING_ERROR] with `type = metadata` if data parsing error has occurred.
   *
   * Note: This method is not thread-safe. The Geometry is parsed on a worker thread, please make sure
   * the Geometry is immutable as well as all collections that are used to build it.
   *
   * @param value the geometry
   * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
   * @throws [MapboxConcurrentGeometryModificationException]
   */
  @JvmOverloads
  fun geometry(value: Geometry, dataId: String? = null): GeoJsonSource = applyGeoJsonData(value, dataId)

  private fun GeoJson.toPropertyValue(): PropertyValue<*> {
    try {
      return PropertyValue(
        "data",
        when (this) {
          is Geometry -> toValue()
          is FeatureCollection -> toValue()
          is Feature -> toValue()
          else -> RuntimeException("GeoJson data must be Geometry, FeatureCollection or Feature!")
        }
      )
    } catch (e: ConcurrentModificationException) {
      throw MapboxConcurrentGeometryModificationException(
        """While applying ${javaClass.simpleName} to geojson source with sourceId="$sourceId" some collection was mutated which is not allowed as data parsing happens on another thread.
        Please make sure all collections passed via `geometry`, `feature`, `featureCollection` methods are immutable.
        Easiest way to achieve this is either always pass the fresh copy or use https://developer.android.com/reference/java/util/concurrent/CopyOnWriteArrayList.
        """.trimIndent(),
        sourceId
      )
    }
  }

  private fun applyGeoJsonData(
    data: GeoJson,
    dataId: String?,
  ): GeoJsonSource = apply {
    setGeoJson(data, dataId)
  }

  /**
   * Builder for GeoJsonSource.
   *
   * @param sourceId the ID of the source
   */
  @SourceDsl
  class Builder(val sourceId: String) {
    internal val properties = HashMap<String, PropertyValue<*>>()
    // Properties that only settable after the source is added to the style.
    internal val volatileProperties = HashMap<String, PropertyValue<*>>()

    private var geoJson: GeoJson? = null
    private var data: String? = null
    private var dataId: String? = null

    /**
     * @param value an URL to a GeoJSON file, or an inline GeoJSON.
     * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
     */
    @JvmOverloads
    fun data(value: String, dataId: String? = null) = apply {
      geoJson = null
      data = value
      this.dataId = dataId
    }

    /**
     * @param value an URL to a GeoJSON file, or an inline GeoJSON.
     * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
     */
    @JvmOverloads
    fun url(value: String, dataId: String? = null) = apply {
      data(value, dataId)
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
     * Contains an attribution to be displayed when the map is shown to a user.
     */
    fun attribution(value: String) = apply {
      val propertyValue = PropertyValue("attribution", TypeUtils.wrapToValue(value))
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
     * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
     */
    fun tolerance(value: Double = 0.375) = apply {
      val propertyValue = PropertyValue("tolerance", TypeUtils.wrapToValue(value))
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
     * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal
     * to the width of a tile.
     */
    fun clusterRadius(value: Long = 50L) = apply {
      val propertyValue = PropertyValue("clusterRadius", TypeUtils.wrapToValue(value))
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
     * Whether to generate ids for the geojson features. When enabled, the `feature.id` property will be auto
     * assigned based on its index in the `features` array, over-writing any previous values.
     */
    fun generateId(value: Boolean = false) = apply {
      val propertyValue = PropertyValue("generateId", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A property to use as a feature id (for feature state). Either a property name, or
     * an object of the form `{<sourceLayer>: <propertyName>}`.
     */
    fun promoteId(value: PromoteId) = apply {
      val propertyValue = PropertyValue("promoteId", value.toValue())
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
     * will first request a tile at zoom level lower than zoom - delta, but so that
     * the zoom level is multiple of delta, in an attempt to display a full map at
     * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
     * The default delta is 4.
     */
    fun prefetchZoomDelta(value: Long = 4L) = apply {
      val propertyValue = PropertyValue("prefetch-zoom-delta", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Add a Feature to the GeojsonSource.
     *
     * @param value the feature
     * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
     */
    @JvmOverloads
    fun feature(value: Feature, dataId: String? = null) = apply {
      geoJson(value, dataId)
    }

    /**
     * Add a FeatureCollection to the GeojsonSource.
     *
     * @param value the feature collection
     * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
     */
    @JvmOverloads
    fun featureCollection(value: FeatureCollection, dataId: String? = null) = apply {
      geoJson(value, dataId)
    }

    /**
     * Add a Geometry to the GeojsonSource.
     *
     * @param value the geometry
     * @param dataId optional metadata to filter the SOURCE_DATA_LOADED events later
     */
    @JvmOverloads
    fun geometry(value: Geometry, dataId: String? = null) = apply {
      geoJson(value, dataId)
    }

    private fun geoJson(geoJson: GeoJson, dataId: String? = null) {
      this.geoJson = geoJson
      this.dataId = dataId
      data = null
    }

    /**
     * Build the GeoJsonSource.
     *
     * @return the GeoJsonSource
     */
    fun build(): GeoJsonSource {
      // set default data to allow empty data source.
      val propertyValue = PropertyValue("data", TypeUtils.wrapToValue(""))
      properties[propertyValue.propertyName] = propertyValue
      return GeoJsonSource(this, geoJson, data, dataId)
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "GeoJsonSource"

    /** A worker thread to parse large geojson data. */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val workerThread = HandlerThread("GEOJSON_PARSER", THREAD_PRIORITY_DEFAULT).apply {
      start()
    }

    internal fun toGeoJsonData(geoJson: GeoJson): GeoJSONSourceData {
      return when (geoJson) {
        is Feature -> GeoJSONSourceData.valueOf(geoJson)
        is Geometry -> GeoJSONSourceData.valueOf(geoJson)
        is FeatureCollection -> GeoJSONSourceData.valueOf(geoJson.features()!!)
        else -> throw RuntimeException("Incorrect GeoJson data format")
      }
    }

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
     * Whether to generate ids for the geojson features. When enabled, the `feature.id` property will be auto
     * assigned based on its index in the `features` array, over-writing any previous values.
     */
    val defaultGenerateId: Boolean?
      /**
       * Get the GenerateId property
       *
       * @return Boolean
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "generateId").silentUnwrap()

    /**
     * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
     * will first request a tile at zoom level lower than zoom - delta, but so that
     * the zoom level is multiple of delta, in an attempt to display a full map at
     * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
     * The default delta is 4.
     */
    val defaultPrefetchZoomDelta: Long?
      /**
       * Get the PrefetchZoomDelta property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("geojson", "prefetch-zoom-delta").silentUnwrap()
  }
}

/**
 * DSL function for [GeoJsonSource] accepting empty data source.
 * Immediately returns [GeoJsonSource] with no data set.
 */
fun geoJsonSource(
  id: String
) = GeoJsonSource.Builder(id).build()

/**
 * DSL function for [GeoJsonSource].
 *
 * Immediately returns [GeoJsonSource] with no data set and starts preparing actual data
 * using a worker thread if [GeoJsonSource.Builder.feature],
 * [GeoJsonSource.Builder.featureCollection] or [GeoJsonSource.Builder.geometry] were applied.
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
 * [Style.OnStyleLoaded] will be emitted without waiting to draw [GeoJsonSource.feature],
 * [GeoJsonSource.featureCollection] or [GeoJsonSource.geometry] on the map.
 *
 * In order to capture events when actual data is drawn on the map please refer to [Observer] API
 * and listen to [MapEvents.SOURCE_DATA_LOADED] (optionally pass `data-id` with the data update call
 * to filter the events) or [MapEvents.MAP_LOADING_ERROR] with `type = metadata`
 * if data parsing error has occurred.
 */
fun geoJsonSource(
  id: String,
  block: GeoJsonSource.Builder.() -> Unit
): GeoJsonSource = GeoJsonSource.Builder(id).apply(block).build()

// End of generated file.