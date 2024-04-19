// This file is generated.

package com.mapbox.maps.extension.compose.style.sources.generated

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.maps.GeoJSONSourceData
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.TileCacheBudgetInMegabytes
import com.mapbox.maps.TileCacheBudgetInTiles
import com.mapbox.maps.extension.compose.style.internal.ComposeTypeUtils
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.sources.generated.RasterArraySource.RasterDataLayer

/**
 * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Url(public val value: Value) {
  /**
   * Construct the Url with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Url with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "url"

    /**
     * Default value for [Url], setting default will result in restoring the property value defined in the style.
     */
    public val default: Url = Url(Value.nullValue())
  }
}

/**
 * An array of one or more tile source URLs, as in the TileJSON spec.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Tiles(public val value: Value) {
  /**
   * Construct the Tiles with [List<String>].
   */
  public constructor(value: List<String>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Tiles with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "tiles"

    /**
     * Default value for [Tiles], setting default will result in restoring the property value defined in the style.
     */
    public val default: Tiles = Tiles(Value.nullValue())
  }
}

/**
 * An array containing the longitude and latitude of the southwest and northeast corners of the source's bounding box in the following order: `[sw.lng, sw.lat, ne.lng, ne.lat]`. When this property is included in a source, no tiles outside of the given bounds are requested by Mapbox GL.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Bounds(public val value: Value) {
  /**
   * Construct the Bounds with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Bounds with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "bounds"

    /**
     * Default value for [Bounds], setting default will result in restoring the property value defined in the style.
     */
    public val default: Bounds = Bounds(Value.nullValue())
  }
}

/**
 * Influences the y direction of the tile coordinates. The global-mercator (aka Spherical Mercator) profile is assumed.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Scheme(public val value: Value) {
  /**
   * Construct the Scheme with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "scheme"

    /**
     * Default value for [Scheme], setting default will result in restoring the property value defined in the style.
     */
    public val default: Scheme = Scheme(Value.nullValue())

    /**
     * Slippy map tilenames scheme.
     */
    @JvmField
    public val XYZ: Scheme = Scheme(Value("xyz"))

    /**
     * OSGeo spec scheme.
     */
    @JvmField
    public val TMS: Scheme = Scheme(Value("tms"))
  }
}

/**
 * Minimum zoom level for which tiles are available, as in the TileJSON spec.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class MinZoom(public val value: Value) {
  /**
   * Construct the MinZoom with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the MinZoom with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "minzoom"

    /**
     * Default value for [MinZoom], setting default will result in restoring the property value defined in the style.
     */
    public val default: MinZoom = MinZoom(Value.nullValue())
  }
}

/**
 * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles at the maxzoom are used when displaying the map at higher zoom levels.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class MaxZoom(public val value: Value) {
  /**
   * Construct the MaxZoom with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the MaxZoom with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "maxzoom"

    /**
     * Default value for [MaxZoom], setting default will result in restoring the property value defined in the style.
     */
    public val default: MaxZoom = MaxZoom(Value.nullValue())
  }
}

/**
 * Contains an attribution to be displayed when the map is shown to a user.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Attribution(public val value: Value) {
  /**
   * Construct the Attribution with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Attribution with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "attribution"

    /**
     * Default value for [Attribution], setting default will result in restoring the property value defined in the style.
     */
    public val default: Attribution = Attribution(Value.nullValue())
  }
}

/**
 * A property to use as a feature id (for feature state). Either a property name, or an object of the form `{<sourceLayer>: <propertyName>}`. If specified as a string for a vector tile source, the same property is used across all its source layers. If specified as an object only specified source layers will have id overriden, others will fallback to original feature id
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class PromoteId(public val value: Value) {
  /**
   * Construct the PromoteId with [PromoteId].
   */
  public constructor(value: com.mapbox.maps.extension.style.types.PromoteId) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the PromoteId with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "promoteId"

    /**
     * Default value for [PromoteId], setting default will result in restoring the property value defined in the style.
     */
    public val default: PromoteId = PromoteId(Value.nullValue())
  }
}

/**
 * A setting to determine whether a source's tiles are cached locally.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Volatile(public val value: Value) {
  /**
   * Construct the Volatile with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Volatile with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "volatile"

    /**
     * Default value for [Volatile], setting default will result in restoring the property value defined in the style.
     */
    public val default: Volatile = Volatile(Value.nullValue())
  }
}

/**
 * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map will first request a tile at zoom level lower than zoom - delta, but so that the zoom level is multiple of delta, in an attempt to display a full map at lower resolution as quick as possible. It will get clamped at the tile source minimum zoom. The default delta is 4.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class PrefetchZoomDelta(public val value: Value) {
  /**
   * Construct the PrefetchZoomDelta with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the PrefetchZoomDelta with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "prefetch-zoom-delta"

    /**
     * Default value for [PrefetchZoomDelta], setting default will result in restoring the property value defined in the style.
     */
    public val default: PrefetchZoomDelta = PrefetchZoomDelta(Value.nullValue())
  }
}

/**
 * Minimum tile update interval in seconds, which is used to throttle the tile update network requests. If the given source supports loading tiles from a server, sets the minimum tile update interval. Update network requests that are more frequent than the minimum tile update interval are suppressed.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class MinimumTileUpdateInterval(public val value: Value) {
  /**
   * Construct the MinimumTileUpdateInterval with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the MinimumTileUpdateInterval with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "minimum-tile-update-interval"

    /**
     * Default value for [MinimumTileUpdateInterval], setting default will result in restoring the property value defined in the style.
     */
    public val default: MinimumTileUpdateInterval = MinimumTileUpdateInterval(Value.nullValue())
  }
}

/**
 * When a set of tiles for a current zoom level is being rendered and some of the ideal tiles that cover the screen are not yet loaded, parent tile could be used instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times. This property sets the maximum limit for how much a parent tile can be overscaled.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class MaxOverscaleFactorForParentTiles(public val value: Value) {
  /**
   * Construct the MaxOverscaleFactorForParentTiles with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the MaxOverscaleFactorForParentTiles with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "max-overscale-factor-for-parent-tiles"

    /**
     * Default value for [MaxOverscaleFactorForParentTiles], setting default will result in restoring the property value defined in the style.
     */
    public val default: MaxOverscaleFactorForParentTiles = MaxOverscaleFactorForParentTiles(Value.nullValue())
  }
}

/**
 * For the tiled sources, this property sets the tile requests delay. The given delay comes in action only during an ongoing animation or gestures. It helps to avoid loading, parsing and rendering of the transient tiles and thus to improve the rendering performance, especially on low-end devices.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TileRequestsDelay(public val value: Value) {
  /**
   * Construct the TileRequestsDelay with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TileRequestsDelay with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "tile-requests-delay"

    /**
     * Default value for [TileRequestsDelay], setting default will result in restoring the property value defined in the style.
     */
    public val default: TileRequestsDelay = TileRequestsDelay(Value.nullValue())
  }
}

/**
 * For the tiled sources, this property sets the tile network requests delay. The given delay comes in action only during an ongoing animation or gestures. It helps to avoid loading the transient tiles from the network and thus to avoid redundant network requests. Note that tile-network-requests-delay value is superseded with tile-requests-delay property value, if both are provided.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TileNetworkRequestsDelay(public val value: Value) {
  /**
   * Construct the TileNetworkRequestsDelay with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TileNetworkRequestsDelay with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "tile-network-requests-delay"

    /**
     * Default value for [TileNetworkRequestsDelay], setting default will result in restoring the property value defined in the style.
     */
    public val default: TileNetworkRequestsDelay = TileNetworkRequestsDelay(Value.nullValue())
  }
}

/**
 * The minimum visual size to display tiles for this layer. Only configurable for raster layers.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TileSize(public val value: Value) {
  /**
   * Construct the TileSize with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TileSize with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "tileSize"

    /**
     * Default value for [TileSize], setting default will result in restoring the property value defined in the style.
     */
    public val default: TileSize = TileSize(Value.nullValue())
  }
}

/**
 * The encoding used by this source. Mapbox Terrain RGB is used by default
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Encoding(public val value: Value) {
  /**
   * Construct the Encoding with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "encoding"

    /**
     * Default value for [Encoding], setting default will result in restoring the property value defined in the style.
     */
    public val default: Encoding = Encoding(Value.nullValue())

    /**
     * Terrarium format PNG tiles. See https://aws.amazon.com/es/public-datasets/terrain/ for more info.
     */
    @JvmField
    public val TERRARIUM: Encoding = Encoding(Value("terrarium"))

    /**
     * Mapbox Terrain RGB tiles. See https://www.mapbox.com/help/access-elevation-data/#mapbox-terrain-rgb for more info.
     */
    @JvmField
    public val MAPBOX: Encoding = Encoding(Value("mapbox"))
  }
}

/**
 * Contains the description of the raster data layers and the bands contained within the tiles.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterLayers(public val value: Value) {
  /**
   * Construct the RasterLayers with [List<RasterDataLayer>].
   */
  public constructor(value: List<RasterDataLayer>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterLayers with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "rasterLayers"

    /**
     * Default value for [RasterLayers], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterLayers = RasterLayers(Value.nullValue())
  }
}

/**
 * Corners of image specified in longitude, latitude pairs. Note: When using globe projection, the image will be centered at the North or South Pole in the respective hemisphere if the average latitude value exceeds 85 degrees or falls below -85 degrees.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Coordinates(public val value: Value) {
  /**
   * Construct the Coordinates with [List<List<Double>>].
   */
  public constructor(value: List<List<Double>>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Coordinates with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "coordinates"

    /**
     * Default value for [Coordinates], setting default will result in restoring the property value defined in the style.
     */
    public val default: Coordinates = Coordinates(Value.nullValue())
  }
}

/**
 * Size of the tile buffer on each side. A value of 0 produces no buffer. A value of 512 produces a buffer as wide as the tile itself. Larger values produce fewer rendering artifacts near tile edges and slower performance.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Buffer(public val value: Value) {
  /**
   * Construct the Buffer with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Buffer with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "buffer"

    /**
     * Default value for [Buffer], setting default will result in restoring the property value defined in the style.
     */
    public val default: Buffer = Buffer(Value.nullValue())
  }
}

/**
 * Douglas-Peucker simplification tolerance (higher means simpler geometries and faster performance).
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Tolerance(public val value: Value) {
  /**
   * Construct the Tolerance with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Tolerance with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "tolerance"

    /**
     * Default value for [Tolerance], setting default will result in restoring the property value defined in the style.
     */
    public val default: Tolerance = Tolerance(Value.nullValue())
  }
}

/**
 * If the data is a collection of point features, setting this to true clusters the points by radius into groups. Cluster groups become new `Point` features in the source with additional properties:
 * `cluster` Is `true` if the point is a cluster
 * `cluster_id` A unqiue id for the cluster to be used in conjunction with the [cluster inspection methods](https://www.mapbox.com/mapbox-gl-js/api/#geojsonsource#getclusterexpansionzoom)
 * `point_count` Number of original points grouped into this cluster
 * `point_count_abbreviated` An abbreviated point count
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Cluster(public val value: Value) {
  /**
   * Construct the Cluster with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Cluster with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "cluster"

    /**
     * Default value for [Cluster], setting default will result in restoring the property value defined in the style.
     */
    public val default: Cluster = Cluster(Value.nullValue())
  }
}

/**
 * Radius of each cluster if clustering is enabled. A value of 512 indicates a radius equal to the width of a tile.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ClusterRadius(public val value: Value) {
  /**
   * Construct the ClusterRadius with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ClusterRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "clusterRadius"

    /**
     * Default value for [ClusterRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: ClusterRadius = ClusterRadius(Value.nullValue())
  }
}

/**
 * Max zoom on which to cluster points if clustering is enabled. Defaults to one zoom less than maxzoom (so that last zoom features are not clustered). Clusters are re-evaluated at integer zoom levels so setting clusterMaxZoom to 14 means the clusters will be displayed until z15.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ClusterMaxZoom(public val value: Value) {
  /**
   * Construct the ClusterMaxZoom with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ClusterMaxZoom with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "clusterMaxZoom"

    /**
     * Default value for [ClusterMaxZoom], setting default will result in restoring the property value defined in the style.
     */
    public val default: ClusterMaxZoom = ClusterMaxZoom(Value.nullValue())
  }
}

/**
 * An object defining custom properties on the generated clusters if clustering is enabled, aggregating values from clustered points. Has the form `{"property_name": [operator, map_expression]}`. `operator` is any expression function that accepts at least 2 operands (e.g. `"+"` or `"max"`) — it accumulates the property value from clusters/points the cluster contains; `map_expression` produces the value of a single point.
Example: `{"sum": ["+", ["get", "scalerank"]]}`.
For more advanced use cases, in place of `operator`, you can use a custom reduce expression that references a special `["accumulated"]` value, e.g.:
`{"sum": [["+", ["accumulated"], ["get", "sum"]], ["get", "scalerank"]]}`
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
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
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "clusterProperties"

    /**
     * Default value for [ClusterProperties], setting default will result in restoring the property value defined in the style.
     */
    public val default: ClusterProperties = ClusterProperties(Value.nullValue())
  }
}

/**
 * Whether to calculate line distance metrics. This is required for line layers that specify `line-gradient` values.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineMetrics(public val value: Value) {
  /**
   * Construct the LineMetrics with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineMetrics with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "lineMetrics"

    /**
     * Default value for [LineMetrics], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineMetrics = LineMetrics(Value.nullValue())
  }
}

/**
 * Whether to generate ids for the geojson features. When enabled, the `feature.id` property will be auto assigned based on its index in the `features` array, over-writing any previous values.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class GenerateId(public val value: Value) {
  /**
   * Construct the GenerateId with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the GenerateId with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "generateId"

    /**
     * Default value for [GenerateId], setting default will result in restoring the property value defined in the style.
     */
    public val default: GenerateId = GenerateId(Value.nullValue())
  }
}

/**
 * This property defines a source-specific resource budget, either in tile units or in megabytes. Whenever the tile cache goes over the defined limit, the least recently used tile will be evicted from the in-memory cache. Note that the current implementation does not take into account resources allocated by the visible tiles.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
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
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "tile-cache-budget"

    /**
     * Default value for [TileCacheBudget], setting default will result in restoring the property value defined in the style.
     */
    public val default: TileCacheBudget = TileCacheBudget(Value.nullValue())
  }
}

/**
 * The [GeoJSONData] that drives the [GeoJsonSource].
 *
 * @param data the [GeoJSONSourceData] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
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

  /**
   * Override hashCode method.
   */
  override fun hashCode(): Int {
    return 33 + data.typeInfo.hashCode() + when (data.typeInfo) {
      GeoJSONSourceData.Type.FEATURE -> data.feature.hashCode()
      GeoJSONSourceData.Type.GEOMETRY -> data.geometry.hashCode()
      GeoJSONSourceData.Type.LIST -> data.list.hashCode()
      GeoJSONSourceData.Type.STRING -> data.string.hashCode()
      else -> 0
    }
  }

  /**
   * Override equals method.
   */
  override fun equals(other: Any?): Boolean {
    return other is GeoJSONData && data.typeInfo == other.data.typeInfo && when (data.typeInfo) {
      GeoJSONSourceData.Type.FEATURE -> data.feature == other.data.feature
      GeoJSONSourceData.Type.GEOMETRY -> data.geometry == other.data.geometry
      GeoJSONSourceData.Type.LIST -> data.list == other.data.list
      GeoJSONSourceData.Type.STRING -> data.string == other.data.string
      else -> false
    }
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
    public val default: GeoJSONData = GeoJSONData("")
  }
}
// End of generated file.