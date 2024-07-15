package com.mapbox.maps.extension.compose.style.sources

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.maps.GeoJSONSourceData
import com.mapbox.maps.TileCacheBudgetInMegabytes
import com.mapbox.maps.TileCacheBudgetInTiles
import com.mapbox.maps.extension.compose.style.internal.ComposeTypeUtils
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.sources.generated.RasterArraySource
import java.util.Objects

/**
 * An object defining custom properties on the generated clusters if clustering is enabled, aggregating values from clustered points. Has the form `{"property_name": [operator, map_expression]}`. `operator` is any expression function that accepts at least 2 operands (e.g. `"+"` or `"max"`) — it accumulates the property value from clusters/points the cluster contains; `map_expression` produces the value of a single point.
Example: `{"sum": ["+", ["get", "scalerank"]]}`.
For more advanced use cases, in place of `operator`, you can use a custom reduce expression that references a special `["accumulated"]` value, e.g.:
`{"sum": [["+", ["accumulated"], ["get", "sum"]], ["get", "scalerank"]]}`
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class ClusterProperties(public val value: Value) {
  /**
   * Construct the ClusterProperties with [HashMap<String, Any>].
   */
  public constructor(value: HashMap<String, Any>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ClusterProperties with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: ClusterProperties = ClusterProperties(Value("ClusterProperties.INITIAL"))

    /**
     * Default value for [ClusterProperties], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: ClusterProperties = ClusterProperties(Value.nullValue())
  }
}

/**
 * This property defines a source-specific resource budget, either in tile units or in megabytes. Whenever the tile cache goes over the defined limit, the least recently used tile will be evicted from the in-memory cache. Note that the current implementation does not take into account resources allocated by the visible tiles.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class TileCacheBudget(public val value: Value) {
  /**
   * Construct the TileCacheBudget with [TileCacheBudgetInMegabytes].
   */
  public constructor(value: TileCacheBudgetInMegabytes) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the TileCacheBudget with [TileCacheBudgetInTiles].
   */
  public constructor(value: TileCacheBudgetInTiles) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the TileCacheBudget with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {

    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: TileCacheBudget = TileCacheBudget(Value("TileCacheBudget.INITIAL"))

    /**
     * Default value for [TileCacheBudget], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: TileCacheBudget = TileCacheBudget(Value.nullValue())
  }
}

/**
 * Contains the description of the raster data layers and the bands contained within the tiles.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class RasterLayers(public val value: Value) {
  /**
   * Construct the RasterLayers with [List<RasterDataLayer>].
   */
  public constructor(value: List<RasterArraySource.RasterDataLayer>) : this(
      ComposeTypeUtils.wrapToValue(
          value
      )
  )
  /**
   * Construct the RasterLayers with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: RasterLayers = RasterLayers(Value("RasterLayers.INITIAL"))

    /**
     * Default value for [RasterLayers], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: RasterLayers = RasterLayers(Value.nullValue())
  }
}

/**
 * A property to use as a feature id (for feature state). Either a property name, or an object of
 * the form `{<sourceLayer>: <propertyName>}`:
 * - If specified as a string for a vector tile source
 *   ([com.mapbox.maps.extension.compose.style.sources.generated.VectorSourceState]), the same property is used
 *   across all its source layers.
 * - If specified as an object only specified source layers will have id overridden, others will
 *   fallback to original feature id.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class PromoteIdValue(public val value: Value) {
  /**
   * Construct the [PromoteIdValue] with [propertyName] and optional [sourceId].
   * - If only [propertyName] is specified then the same property is used across all its source
   *   layers.
   * - If both [propertyName] and [sourceId] are given, only specified source layer will have id
   *   overridden, others will fallback to original feature id.
   */
  public constructor(propertyName: String, sourceId: String? = null) : this(
    ComposeTypeUtils.wrapToValue(
      com.mapbox.maps.extension.style.types.PromoteId(propertyName, sourceId)
    )
  )
  /**
   * Construct the PromoteId with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: PromoteIdValue = PromoteIdValue(Value("PromoteIdValue.INITIAL"))

    /**
     * Default value for [PromoteIdValue], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: PromoteIdValue = PromoteIdValue(Value.nullValue())
  }
}

/**
 * The [GeoJSONData] that drives the [com.mapbox.maps.extension.compose.style.sources.generated.GeoJsonSourceState].
 *
 * @param data the [GeoJSONSourceData] to be used with native renderer.
 */
@Immutable
public data class GeoJSONData(internal val data: GeoJSONSourceData) {
  /**
   * Construct the [GeoJSONData] with [String].
   */
  public constructor(value: String) : this(GeoJSONSourceData.valueOf(value))

  /**
   * Construct the [GeoJSONData] with [Feature].
   */
  public constructor(value: Feature) : this(GeoJSONSourceData.valueOf(value))

  /**
   * Construct the [GeoJSONData] with a list of [Feature].
   */
  public constructor(value: List<Feature>) : this(GeoJSONSourceData.valueOf(value))

  /**
   * Construct the [GeoJSONData] with [Geometry].
   */
  public constructor(value: Geometry) : this(GeoJSONSourceData.valueOf(value))

  private val dataContents: Any?
    get() = when (data.typeInfo) {
      GeoJSONSourceData.Type.FEATURE -> data.feature
      GeoJSONSourceData.Type.GEOMETRY -> data.geometry
      GeoJSONSourceData.Type.LIST -> data.list
      GeoJSONSourceData.Type.STRING -> data.string
      else -> null
    }

  /**
   * Override hashCode method.
   */
  override fun hashCode(): Int {
    return Objects.hash(data.typeInfo, dataContents)
  }

  /**
   * Override equals method.
   */
  override fun equals(other: Any?): Boolean {
    return other is GeoJSONData && data.typeInfo == other.data.typeInfo && dataContents == other.dataContents
  }

  /**
   * Override toString method.
   */
  override fun toString(): String {
    return when (data.typeInfo) {
      GeoJSONSourceData.Type.GEOMETRY -> data.geometry.toJson()
      GeoJSONSourceData.Type.FEATURE -> data.feature.toJson()
      GeoJSONSourceData.Type.LIST -> data.list.toString()
      GeoJSONSourceData.Type.STRING -> data.string
      else -> "unknown"
    }
  }

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Default value for [GeoJSONData].
     */
    @JvmField
    public val DEFAULT: GeoJSONData = GeoJSONData("")
  }
}