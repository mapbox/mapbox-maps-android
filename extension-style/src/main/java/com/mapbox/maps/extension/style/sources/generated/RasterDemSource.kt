// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.maps.StyleManager
import com.mapbox.maps.TileCacheBudget
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.Source
import com.mapbox.maps.extension.style.sources.TileSet
import com.mapbox.maps.extension.style.types.SourceDsl
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * A RGB-encoded raster DEM source
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#raster_dem)
 *
 */
class RasterDemSource(builder: Builder) : Source(builder.sourceId) {

  init {
    sourceProperties.putAll(builder.properties)
    volatileSourceProperties.putAll(builder.volatileProperties)
  }

  /**
   * Get the type of the current source as a String.
   */
  override fun getType(): String {
    return "raster-dem"
  }

  /**
   * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`.
   */
  fun url(value: String): RasterDemSource = apply {
    setProperty(PropertyValue("url", TypeUtils.wrapToValue(value)))
  }

  /**
   * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`.
   */
  val url: String?
    /**
     * Get the Url property
     *
     * @return String
     */
    get() = getPropertyValue("url")

  /**
   * An array of one or more tile source URLs, as in the TileJSON spec.
   */
  fun tiles(value: List<String>): RasterDemSource = apply {
    setProperty(PropertyValue("tiles", TypeUtils.wrapToValue(value)))
  }

  /**
   * An array of one or more tile source URLs, as in the TileJSON spec.
   */
  val tiles: List<String>?
    /**
     * Get the Tiles property
     *
     * @return List<String>
     */
    get() = getPropertyValue("tiles")

  /**
   * An array containing the longitude and latitude of the southwest and northeast corners of the source's
   * bounding box in the following order: `[sw.lng, sw.lat, ne.lng, ne.lat]`. When this property is included in
   * a source, no tiles outside of the given bounds are requested by Mapbox GL.
   */
  val bounds: List<Double>?
    /**
     * Get the Bounds property
     *
     * @return List<Double>
     */
    get() = getPropertyValue("bounds")

  /**
   * Minimum zoom level for which tiles are available, as in the TileJSON spec.
   */
  fun minzoom(value: Long = 0L): RasterDemSource = apply {
    setProperty(PropertyValue("minzoom", TypeUtils.wrapToValue(value)))
  }

  /**
   * Minimum zoom level for which tiles are available, as in the TileJSON spec.
   */
  val minzoom: Long?
    /**
     * Get the Minzoom property
     *
     * Use static method [RasterDemSource.defaultMinzoom] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("minzoom")

  /**
   * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
   * at the maxzoom are used when displaying the map at higher zoom levels.
   */
  fun maxzoom(value: Long = 22L): RasterDemSource = apply {
    setProperty(PropertyValue("maxzoom", TypeUtils.wrapToValue(value)))
  }

  /**
   * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
   * at the maxzoom are used when displaying the map at higher zoom levels.
   */
  val maxzoom: Long?
    /**
     * Get the Maxzoom property
     *
     * Use static method [RasterDemSource.defaultMaxzoom] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("maxzoom")

  /**
   * The minimum visual size to display tiles for this layer. Only configurable for raster layers.
   */
  val tileSize: Long?
    /**
     * Get the TileSize property
     *
     * @return Long
     */
    get() = getPropertyValue("tileSize")

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
   * The encoding used by this source. Mapbox Terrain RGB is used by default
   */
  val encoding: Encoding?
    /**
     * Get the Encoding property
     *
     * Use static method [RasterDemSource.defaultEncoding] to get the default property.
     *
     * @return Encoding
     */
    get() {
      getPropertyValue<String?>("encoding")?.let {
        return Encoding.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * A setting to determine whether a source's tiles are cached locally.
   */
  fun volatile(value: Boolean = false): RasterDemSource = apply {
    setProperty(PropertyValue("volatile", TypeUtils.wrapToValue(value)))
  }

  /**
   * A setting to determine whether a source's tiles are cached locally.
   */
  val volatile: Boolean?
    /**
     * Get the Volatile property
     *
     * Use static method [RasterDemSource.defaultVolatile] to get the default property.
     *
     * @return Boolean
     */
    get() = getPropertyValue("volatile")

  /**
   * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
   * will first request a tile at zoom level lower than zoom - delta, but so that
   * the zoom level is multiple of delta, in an attempt to display a full map at
   * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
   * The default delta is 4.
   */
  fun prefetchZoomDelta(value: Long = 4L): RasterDemSource = apply {
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
     * Use static method [RasterDemSource.defaultPrefetchZoomDelta] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("prefetch-zoom-delta")

  /**
   * This property defines a source-specific resource budget, either in tile units or in megabytes. Whenever the
   * tile cache goes over the defined limit, the least recently used tile will be evicted from
   * the in-memory cache. Note that the current implementation does not take into account resources allocated by
   * the visible tiles.
   */
  fun tileCacheBudget(value: TileCacheBudget): RasterDemSource = apply {
    setVolatileProperty(PropertyValue("tile-cache-budget", TypeUtils.wrapToValue(value)))
  }

  /**
   * This property defines a source-specific resource budget, either in tile units or in megabytes. Whenever the
   * tile cache goes over the defined limit, the least recently used tile will be evicted from
   * the in-memory cache. Note that the current implementation does not take into account resources allocated by
   * the visible tiles.
   */
  val tileCacheBudget: TileCacheBudget?
    /**
     * Get the TileCacheBudget property
     *
     * @return TileCacheBudget
     */
    get() = getPropertyValue("tile-cache-budget")

  /**
   * Minimum tile update interval in seconds, which is used to throttle the tile update network requests.
   * If the given source supports loading tiles from a server, sets the minimum tile update interval.
   * Update network requests that are more frequent than the minimum tile update interval are suppressed.
   */
  fun minimumTileUpdateInterval(value: Double = 0.0): RasterDemSource = apply {
    setVolatileProperty(PropertyValue("minimum-tile-update-interval", TypeUtils.wrapToValue(value)))
  }

  /**
   * Minimum tile update interval in seconds, which is used to throttle the tile update network requests.
   * If the given source supports loading tiles from a server, sets the minimum tile update interval.
   * Update network requests that are more frequent than the minimum tile update interval are suppressed.
   */
  val minimumTileUpdateInterval: Double?
    /**
     * Get the MinimumTileUpdateInterval property
     *
     * Use static method [RasterDemSource.defaultMinimumTileUpdateInterval] to get the default property.
     *
     * @return Double
     */
    get() = getPropertyValue("minimum-tile-update-interval")

  /**
   * When a set of tiles for a current zoom level is being rendered and some of
   * the ideal tiles that cover the screen are not yet loaded, parent tile could be used
   * instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times.
   * This property sets the maximum limit for how much a parent tile can be overscaled.
   */
  fun maxOverscaleFactorForParentTiles(value: Long): RasterDemSource = apply {
    setVolatileProperty(PropertyValue("max-overscale-factor-for-parent-tiles", TypeUtils.wrapToValue(value)))
  }

  /**
   * When a set of tiles for a current zoom level is being rendered and some of
   * the ideal tiles that cover the screen are not yet loaded, parent tile could be used
   * instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times.
   * This property sets the maximum limit for how much a parent tile can be overscaled.
   */
  val maxOverscaleFactorForParentTiles: Long?
    /**
     * Get the MaxOverscaleFactorForParentTiles property
     *
     * @return Long
     */
    get() = getPropertyValue("max-overscale-factor-for-parent-tiles")

  /**
   * For the tiled sources, this property sets the tile requests delay. The given delay comes in
   * action only during an ongoing animation or gestures. It helps to avoid loading, parsing and rendering
   * of the transient tiles and thus to improve the rendering performance, especially on low-end devices.
   */
  fun tileRequestsDelay(value: Double = 0.0): RasterDemSource = apply {
    setVolatileProperty(PropertyValue("tile-requests-delay", TypeUtils.wrapToValue(value)))
  }

  /**
   * For the tiled sources, this property sets the tile requests delay. The given delay comes in
   * action only during an ongoing animation or gestures. It helps to avoid loading, parsing and rendering
   * of the transient tiles and thus to improve the rendering performance, especially on low-end devices.
   */
  val tileRequestsDelay: Double?
    /**
     * Get the TileRequestsDelay property
     *
     * Use static method [RasterDemSource.defaultTileRequestsDelay] to get the default property.
     *
     * @return Double
     */
    get() = getPropertyValue("tile-requests-delay")

  /**
   * For the tiled sources, this property sets the tile network requests delay. The given delay comes
   * in action only during an ongoing animation or gestures. It helps to avoid loading the transient
   * tiles from the network and thus to avoid redundant network requests. Note that tile-network-requests-delay value is
   * superseded with tile-requests-delay property value, if both are provided.
   */
  fun tileNetworkRequestsDelay(value: Double = 0.0): RasterDemSource = apply {
    setVolatileProperty(PropertyValue("tile-network-requests-delay", TypeUtils.wrapToValue(value)))
  }

  /**
   * For the tiled sources, this property sets the tile network requests delay. The given delay comes
   * in action only during an ongoing animation or gestures. It helps to avoid loading the transient
   * tiles from the network and thus to avoid redundant network requests. Note that tile-network-requests-delay value is
   * superseded with tile-requests-delay property value, if both are provided.
   */
  val tileNetworkRequestsDelay: Double?
    /**
     * Get the TileNetworkRequestsDelay property
     *
     * Use static method [RasterDemSource.defaultTileNetworkRequestsDelay] to get the default property.
     *
     * @return Double
     */
    get() = getPropertyValue("tile-network-requests-delay")

  /**
   * Builder for RasterDemSource.
   *
   * @param sourceId the ID of the source
   */
  @SourceDsl
  class Builder(val sourceId: String) {
    internal val properties = HashMap<String, PropertyValue<*>>()
    // Properties that only settable after the source is added to the style.
    internal val volatileProperties = HashMap<String, PropertyValue<*>>()

    /**
     * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`.
     */
    fun url(value: String): Builder = apply {
      val propertyValue = PropertyValue("url", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * An array of one or more tile source URLs, as in the TileJSON spec.
     */
    fun tiles(value: List<String>): Builder = apply {
      val propertyValue = PropertyValue("tiles", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * An array containing the longitude and latitude of the southwest and northeast corners of the source's
     * bounding box in the following order: `[sw.lng, sw.lat, ne.lng, ne.lat]`. When this property is included in
     * a source, no tiles outside of the given bounds are requested by Mapbox GL.
     */
    fun bounds(value: List<Double> = listOf(-180.0, -85.051129, 180.0, 85.051129)): Builder = apply {
      val propertyValue = PropertyValue("bounds", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Minimum zoom level for which tiles are available, as in the TileJSON spec.
     */
    fun minzoom(value: Long = 0L): Builder = apply {
      val propertyValue = PropertyValue("minzoom", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
     * at the maxzoom are used when displaying the map at higher zoom levels.
     */
    fun maxzoom(value: Long = 22L): Builder = apply {
      val propertyValue = PropertyValue("maxzoom", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * The minimum visual size to display tiles for this layer. Only configurable for raster layers.
     */
    fun tileSize(value: Long = 512L): Builder = apply {
      val propertyValue = PropertyValue("tileSize", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Contains an attribution to be displayed when the map is shown to a user.
     */
    fun attribution(value: String): Builder = apply {
      val propertyValue = PropertyValue("attribution", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * The encoding used by this source. Mapbox Terrain RGB is used by default
     */
    fun encoding(value: Encoding = Encoding.MAPBOX): Builder = apply {
      val propertyValue = PropertyValue("encoding", TypeUtils.wrapToValue(value.value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A setting to determine whether a source's tiles are cached locally.
     */
    fun volatile(value: Boolean = false): Builder = apply {
      val propertyValue = PropertyValue("volatile", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
     * will first request a tile at zoom level lower than zoom - delta, but so that
     * the zoom level is multiple of delta, in an attempt to display a full map at
     * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
     * The default delta is 4.
     */
    fun prefetchZoomDelta(value: Long = 4L): Builder = apply {
      val propertyValue = PropertyValue("prefetch-zoom-delta", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * This property defines a source-specific resource budget, either in tile units or in megabytes. Whenever the
     * tile cache goes over the defined limit, the least recently used tile will be evicted from
     * the in-memory cache. Note that the current implementation does not take into account resources allocated by
     * the visible tiles.
     */
    fun tileCacheBudget(value: TileCacheBudget): Builder = apply {
      val propertyValue = PropertyValue("tile-cache-budget", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Minimum tile update interval in seconds, which is used to throttle the tile update network requests.
     * If the given source supports loading tiles from a server, sets the minimum tile update interval.
     * Update network requests that are more frequent than the minimum tile update interval are suppressed.
     */
    fun minimumTileUpdateInterval(value: Double = 0.0): Builder = apply {
      val propertyValue = PropertyValue("minimum-tile-update-interval", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When a set of tiles for a current zoom level is being rendered and some of
     * the ideal tiles that cover the screen are not yet loaded, parent tile could be used
     * instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times.
     * This property sets the maximum limit for how much a parent tile can be overscaled.
     */
    fun maxOverscaleFactorForParentTiles(value: Long): Builder = apply {
      val propertyValue = PropertyValue("max-overscale-factor-for-parent-tiles", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * For the tiled sources, this property sets the tile requests delay. The given delay comes in
     * action only during an ongoing animation or gestures. It helps to avoid loading, parsing and rendering
     * of the transient tiles and thus to improve the rendering performance, especially on low-end devices.
     */
    fun tileRequestsDelay(value: Double = 0.0): Builder = apply {
      val propertyValue = PropertyValue("tile-requests-delay", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * For the tiled sources, this property sets the tile network requests delay. The given delay comes
     * in action only during an ongoing animation or gestures. It helps to avoid loading the transient
     * tiles from the network and thus to avoid redundant network requests. Note that tile-network-requests-delay value is
     * superseded with tile-requests-delay property value, if both are provided.
     */
    fun tileNetworkRequestsDelay(value: Double = 0.0): Builder = apply {
      val propertyValue = PropertyValue("tile-network-requests-delay", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Add a TileSet to the Source.
     *
     * @param tileSet
     */
    fun tileSet(tileSet: TileSet): Builder = apply {
      for ((name, value) in tileSet) {
        val propertyValue = PropertyValue(name, value)
        properties[propertyValue.propertyName] = propertyValue
      }
    }

    /**
     * Add a TileSet to the Source, using DSL.
     *
     * @param tilejson
     * @param tiles
     * @param block
     */
    fun tileSet(tilejson: String, tiles: List<String>, block: TileSet.RasterDemBuilder.() -> Unit): Builder = apply {
      val tileSet = TileSet.RasterDemBuilder(tilejson, tiles).apply(block).build()
      for ((name, value) in tileSet) {
        val propertyValue = PropertyValue(name, value)
        properties[propertyValue.propertyName] = propertyValue
      }
    }
    /**
     * Build the RasterDemSource.
     *
     * @return the RasterDemSource
     */
    fun build(): RasterDemSource = RasterDemSource(this)
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /**
     * Minimum zoom level for which tiles are available, as in the TileJSON spec.
     */
    val defaultMinzoom: Long?
      /**
       * Get the Minzoom property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "minzoom").silentUnwrap()

    /**
     * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
     * at the maxzoom are used when displaying the map at higher zoom levels.
     */
    val defaultMaxzoom: Long?
      /**
       * Get the Maxzoom property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "maxzoom").silentUnwrap()

    /**
     * The encoding used by this source. Mapbox Terrain RGB is used by default
     */
    val defaultEncoding: Encoding?
      /**
       * Get the Encoding property
       *
       * @return Encoding
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "encoding").silentUnwrap<String>()?.let {
          return Encoding.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * A setting to determine whether a source's tiles are cached locally.
     */
    val defaultVolatile: Boolean?
      /**
       * Get the Volatile property
       *
       * @return Boolean
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "volatile").silentUnwrap()

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
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "prefetch-zoom-delta").silentUnwrap()

    /**
     * Minimum tile update interval in seconds, which is used to throttle the tile update network requests.
     * If the given source supports loading tiles from a server, sets the minimum tile update interval.
     * Update network requests that are more frequent than the minimum tile update interval are suppressed.
     */
    val defaultMinimumTileUpdateInterval: Double?
      /**
       * Get the MinimumTileUpdateInterval property
       *
       * @return Double
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "minimum-tile-update-interval").silentUnwrap()

    /**
     * For the tiled sources, this property sets the tile requests delay. The given delay comes in
     * action only during an ongoing animation or gestures. It helps to avoid loading, parsing and rendering
     * of the transient tiles and thus to improve the rendering performance, especially on low-end devices.
     */
    val defaultTileRequestsDelay: Double?
      /**
       * Get the TileRequestsDelay property
       *
       * @return Double
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "tile-requests-delay").silentUnwrap()

    /**
     * For the tiled sources, this property sets the tile network requests delay. The given delay comes
     * in action only during an ongoing animation or gestures. It helps to avoid loading the transient
     * tiles from the network and thus to avoid redundant network requests. Note that tile-network-requests-delay value is
     * superseded with tile-requests-delay property value, if both are provided.
     */
    val defaultTileNetworkRequestsDelay: Double?
      /**
       * Get the TileNetworkRequestsDelay property
       *
       * @return Double
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "tile-network-requests-delay").silentUnwrap()
  }
}

/**
 * DSL function for [RasterDemSource].
 */
fun rasterDemSource(id: String, block: RasterDemSource.Builder.() -> Unit): RasterDemSource =
  RasterDemSource.Builder(id).apply(block).build()

// End of generated file.