// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
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
 * @see <a href="https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#raster_dem">The online documentation</a>
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
  fun url(value: String) = apply {
    setProperty(PropertyValue("url", TypeUtils.wrapToValue(value)))
  }

  /**
   * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`.
   */
  fun url(value: Expression) = apply {
    setProperty(PropertyValue("url", value))
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
   * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`.
   */
  val urlAsExpression: Expression?
    /**
     * Get the Url property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("url")?.let {
        return it
      }
      url?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * An array of one or more tile source URLs, as in the TileJSON spec.
   */
  fun tiles(value: List<String>) = apply {
    setProperty(PropertyValue("tiles", TypeUtils.wrapToValue(value)))
  }

  /**
   * An array of one or more tile source URLs, as in the TileJSON spec.
   */
  fun tiles(value: Expression) = apply {
    setProperty(PropertyValue("tiles", value))
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
   * An array of one or more tile source URLs, as in the TileJSON spec.
   */
  val tilesAsExpression: Expression?
    /**
     * Get the Tiles property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("tiles")?.let {
        return it
      }
      tiles?.let {
        return Expression.literal(it)
      }
      return null
    }

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
   * An array containing the longitude and latitude of the southwest and northeast corners of the source's
   * bounding box in the following order: `[sw.lng, sw.lat, ne.lng, ne.lat]`. When this property is included in
   * a source, no tiles outside of the given bounds are requested by Mapbox GL.
   */
  val boundsAsExpression: Expression?
    /**
     * Get the Bounds property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("bounds")?.let {
        return it
      }
      bounds?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Minimum zoom level for which tiles are available, as in the TileJSON spec.
   */
  fun minzoom(value: Long = 0L) = apply {
    setProperty(PropertyValue("minzoom", TypeUtils.wrapToValue(value)))
  }

  /**
   * Minimum zoom level for which tiles are available, as in the TileJSON spec.
   */
  fun minzoom(value: Expression) = apply {
    setProperty(PropertyValue("minzoom", value))
  }

  /**
   * Minimum zoom level for which tiles are available, as in the TileJSON spec.
   */
  val minzoom: Long?
    /**
     * Get the Minzoom property
     *
     * @return Long
     */
    get() = getPropertyValue("minzoom")

  /**
   * Minimum zoom level for which tiles are available, as in the TileJSON spec.
   */
  val minzoomAsExpression: Expression?
    /**
     * Get the Minzoom property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("minzoom")?.let {
        return it
      }
      minzoom?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
   * at the maxzoom are used when displaying the map at higher zoom levels.
   */
  fun maxzoom(value: Long = 22L) = apply {
    setProperty(PropertyValue("maxzoom", TypeUtils.wrapToValue(value)))
  }

  /**
   * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
   * at the maxzoom are used when displaying the map at higher zoom levels.
   */
  fun maxzoom(value: Expression) = apply {
    setProperty(PropertyValue("maxzoom", value))
  }

  /**
   * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
   * at the maxzoom are used when displaying the map at higher zoom levels.
   */
  val maxzoom: Long?
    /**
     * Get the Maxzoom property
     *
     * @return Long
     */
    get() = getPropertyValue("maxzoom")

  /**
   * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
   * at the maxzoom are used when displaying the map at higher zoom levels.
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
   * The minimum visual size to display tiles for this layer. Only configurable for raster layers.
   */
  val tileSizeAsExpression: Expression?
    /**
     * Get the TileSize property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("tileSize")?.let {
        return it
      }
      tileSize?.let {
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
   * The encoding used by this source. Mapbox Terrain RGB is used by default
   */
  val encoding: Encoding?
    /**
     * Get the Encoding property
     *
     * @return Encoding
     */
    get() {
      getPropertyValue<String?>("encoding")?.let {
        return Encoding.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * The encoding used by this source. Mapbox Terrain RGB is used by default
   * This is an Expression representation of this Property.
   *
   */
  val encodingAsExpression: Expression?
    /**
     * Get the Encoding property as an Expression
     *
     * @return expression
     */
    get() {
      getPropertyValue<Expression>("encoding")?.let {
        return it
      }
      encoding?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * A setting to determine whether a source's tiles are cached locally.
   */
  fun volatile(value: Boolean = false) = apply {
    setProperty(PropertyValue("volatile", TypeUtils.wrapToValue(value)))
  }

  /**
   * A setting to determine whether a source's tiles are cached locally.
   */
  fun volatile(value: Expression) = apply {
    setProperty(PropertyValue("volatile", value))
  }

  /**
   * A setting to determine whether a source's tiles are cached locally.
   */
  val volatile: Boolean?
    /**
     * Get the Volatile property
     *
     * @return Boolean
     */
    get() = getPropertyValue("volatile")

  /**
   * A setting to determine whether a source's tiles are cached locally.
   */
  val volatileAsExpression: Expression?
    /**
     * Get the Volatile property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("volatile")?.let {
        return it
      }
      volatile?.let {
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
   * Minimum tile update interval in milliseconds, which is used to throttle the tile update network requests.
   */
  fun minimumTileUpdateInterval(value: Double = 0.0) = apply {
    setVolatileProperty(PropertyValue("minimum-tile-update-interval", TypeUtils.wrapToValue(value)))
  }

  /**
   * Minimum tile update interval in milliseconds, which is used to throttle the tile update network requests.
   */
  fun minimumTileUpdateInterval(value: Expression) = apply {
    setVolatileProperty(PropertyValue("minimum-tile-update-interval", value))
  }

  /**
   * Minimum tile update interval in milliseconds, which is used to throttle the tile update network requests.
   */
  val minimumTileUpdateInterval: Double?
    /**
     * Get the MinimumTileUpdateInterval property
     *
     * @return Double
     */
    get() = getPropertyValue("minimum-tile-update-interval")

  /**
   * Minimum tile update interval in milliseconds, which is used to throttle the tile update network requests.
   */
  val minimumTileUpdateIntervalAsExpression: Expression?
    /**
     * Get the MinimumTileUpdateInterval property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("minimum-tile-update-interval")?.let {
        return it
      }
      minimumTileUpdateInterval?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * When a set of tiles for a current zoom level is being rendered and some of
   * the ideal tiles that cover the screen are not yet loaded, parent tile could be used
   * instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times.
   * This property sets the maximum limit for how much a parent tile can be overscaled.
   */
  fun maxOverscaleFactorForParentTiles(value: Long) = apply {
    setVolatileProperty(PropertyValue("max-overscale-factor-for-parent-tiles", TypeUtils.wrapToValue(value)))
  }

  /**
   * When a set of tiles for a current zoom level is being rendered and some of
   * the ideal tiles that cover the screen are not yet loaded, parent tile could be used
   * instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times.
   * This property sets the maximum limit for how much a parent tile can be overscaled.
   */
  fun maxOverscaleFactorForParentTiles(value: Expression) = apply {
    setVolatileProperty(PropertyValue("max-overscale-factor-for-parent-tiles", value))
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
   * When a set of tiles for a current zoom level is being rendered and some of
   * the ideal tiles that cover the screen are not yet loaded, parent tile could be used
   * instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times.
   * This property sets the maximum limit for how much a parent tile can be overscaled.
   */
  val maxOverscaleFactorForParentTilesAsExpression: Expression?
    /**
     * Get the MaxOverscaleFactorForParentTiles property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("max-overscale-factor-for-parent-tiles")?.let {
        return it
      }
      maxOverscaleFactorForParentTiles?.let {
        return Expression.literal(it)
      }
      return null
    }

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
    fun url(value: String) = apply {
      val propertyValue = PropertyValue("url", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`.
     */
    fun url(value: Expression) = apply {
      val propertyValue = PropertyValue("url", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * An array of one or more tile source URLs, as in the TileJSON spec.
     */
    fun tiles(value: List<String>) = apply {
      val propertyValue = PropertyValue("tiles", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * An array of one or more tile source URLs, as in the TileJSON spec.
     */
    fun tiles(value: Expression) = apply {
      val propertyValue = PropertyValue("tiles", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * An array containing the longitude and latitude of the southwest and northeast corners of the source's
     * bounding box in the following order: `[sw.lng, sw.lat, ne.lng, ne.lat]`. When this property is included in
     * a source, no tiles outside of the given bounds are requested by Mapbox GL.
     */
    fun bounds(value: List<Double> = listOf(-180.0, -85.051129, 180.0, 85.051129)) = apply {
      val propertyValue = PropertyValue("bounds", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * An array containing the longitude and latitude of the southwest and northeast corners of the source's
     * bounding box in the following order: `[sw.lng, sw.lat, ne.lng, ne.lat]`. When this property is included in
     * a source, no tiles outside of the given bounds are requested by Mapbox GL.
     */
    fun bounds(value: Expression) = apply {
      val propertyValue = PropertyValue("bounds", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Minimum zoom level for which tiles are available, as in the TileJSON spec.
     */
    fun minzoom(value: Long = 0L) = apply {
      val propertyValue = PropertyValue("minzoom", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Minimum zoom level for which tiles are available, as in the TileJSON spec.
     */
    fun minzoom(value: Expression) = apply {
      val propertyValue = PropertyValue("minzoom", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
     * at the maxzoom are used when displaying the map at higher zoom levels.
     */
    fun maxzoom(value: Long = 22L) = apply {
      val propertyValue = PropertyValue("maxzoom", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
     * at the maxzoom are used when displaying the map at higher zoom levels.
     */
    fun maxzoom(value: Expression) = apply {
      val propertyValue = PropertyValue("maxzoom", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * The minimum visual size to display tiles for this layer. Only configurable for raster layers.
     */
    fun tileSize(value: Long = 512L) = apply {
      val propertyValue = PropertyValue("tileSize", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * The minimum visual size to display tiles for this layer. Only configurable for raster layers.
     */
    fun tileSize(value: Expression) = apply {
      val propertyValue = PropertyValue("tileSize", value)
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
     * The encoding used by this source. Mapbox Terrain RGB is used by default
     */
    fun encoding(value: Encoding = Encoding.MAPBOX) = apply {
      val propertyValue = PropertyValue("encoding", TypeUtils.wrapToValue(value.value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * The encoding used by this source. Mapbox Terrain RGB is used by default
     */
    fun encoding(value: Expression) = apply {
      val propertyValue = PropertyValue("encoding", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A setting to determine whether a source's tiles are cached locally.
     */
    fun volatile(value: Boolean = false) = apply {
      val propertyValue = PropertyValue("volatile", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A setting to determine whether a source's tiles are cached locally.
     */
    fun volatile(value: Expression) = apply {
      val propertyValue = PropertyValue("volatile", value)
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
     * Minimum tile update interval in milliseconds, which is used to throttle the tile update network requests.
     */
    fun minimumTileUpdateInterval(value: Double = 0.0) = apply {
      val propertyValue = PropertyValue("minimum-tile-update-interval", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Minimum tile update interval in milliseconds, which is used to throttle the tile update network requests.
     */
    fun minimumTileUpdateInterval(value: Expression) = apply {
      val propertyValue = PropertyValue("minimum-tile-update-interval", value)
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When a set of tiles for a current zoom level is being rendered and some of
     * the ideal tiles that cover the screen are not yet loaded, parent tile could be used
     * instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times.
     * This property sets the maximum limit for how much a parent tile can be overscaled.
     */
    fun maxOverscaleFactorForParentTiles(value: Long) = apply {
      val propertyValue = PropertyValue("max-overscale-factor-for-parent-tiles", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When a set of tiles for a current zoom level is being rendered and some of
     * the ideal tiles that cover the screen are not yet loaded, parent tile could be used
     * instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times.
     * This property sets the maximum limit for how much a parent tile can be overscaled.
     */
    fun maxOverscaleFactorForParentTiles(value: Expression) = apply {
      val propertyValue = PropertyValue("max-overscale-factor-for-parent-tiles", value)
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Add a TileSet to the Source.
     *
     * @param tileSet
     */
    fun tileSet(tileSet: TileSet) = apply {
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
    fun tileSet(tilejson: String, tiles: List<String>, block: TileSet.RasterDemBuilder.() -> Unit) = apply {
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
    fun build() = RasterDemSource(this)
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
     * Minimum zoom level for which tiles are available, as in the TileJSON spec.
     */
    val defaultMinzoomAsExpression: Expression?
      /**
       * Get the Minzoom property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "minzoom").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultMinzoom?.let {
          return Expression.literal(it)
        }
        return null
      }

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
     * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
     * at the maxzoom are used when displaying the map at higher zoom levels.
     */
    val defaultMaxzoomAsExpression: Expression?
      /**
       * Get the Maxzoom property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "maxzoom").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultMaxzoom?.let {
          return Expression.literal(it)
        }
        return null
      }

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
          return Encoding.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * The encoding used by this source. Mapbox Terrain RGB is used by default
     * This is an Expression representation of this Property.
     *
     */
    val defaultEncodingAsExpression: Expression?
      /**
       * Get the Encoding property as an Expression
       *
       * @return expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "encoding").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultEncoding?.let {
          return Expression.literal(it.value)
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
     * A setting to determine whether a source's tiles are cached locally.
     */
    val defaultVolatileAsExpression: Expression?
      /**
       * Get the Volatile property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "volatile").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultVolatile?.let {
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
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "prefetch-zoom-delta").silentUnwrap()

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
        StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "prefetch-zoom-delta").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultPrefetchZoomDelta?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Minimum tile update interval in milliseconds, which is used to throttle the tile update network requests.
     */
    val defaultMinimumTileUpdateInterval: Double?
      /**
       * Get the MinimumTileUpdateInterval property
       *
       * @return Double
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "minimum-tile-update-interval").silentUnwrap()

    /**
     * Minimum tile update interval in milliseconds, which is used to throttle the tile update network requests.
     */
    val defaultMinimumTileUpdateIntervalAsExpression: Expression?
      /**
       * Get the MinimumTileUpdateInterval property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("raster-dem", "minimum-tile-update-interval").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultMinimumTileUpdateInterval?.let {
          return Expression.literal(it)
        }
        return null
      }
  }
}

/**
 * DSL function for [RasterDemSource].
 */
fun rasterDemSource(id: String, block: RasterDemSource.Builder.() -> Unit): RasterDemSource =
  RasterDemSource.Builder(id).apply(block).build()

// End of generated file.