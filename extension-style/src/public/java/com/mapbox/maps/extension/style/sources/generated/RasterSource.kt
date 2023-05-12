// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.Source
import com.mapbox.maps.extension.style.sources.TileSet
import com.mapbox.maps.extension.style.types.SourceDsl
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * A raster tile source.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#raster)
 *
 */
class RasterSource(builder: Builder) : Source(builder.sourceId) {

  init {
    sourceProperties.putAll(builder.properties)
    volatileSourceProperties.putAll(builder.volatileProperties)
  }

  /**
   * Get the type of the current source as a String.
   */
  override fun getType(): String {
    return "raster"
  }

  /**
   * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`.
   */
  fun url(value: String): RasterSource = apply {
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
  fun tiles(value: List<String>): RasterSource = apply {
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
  fun minzoom(value: Long = 0L): RasterSource = apply {
    setProperty(PropertyValue("minzoom", TypeUtils.wrapToValue(value)))
  }

  /**
   * Minimum zoom level for which tiles are available, as in the TileJSON spec.
   */
  val minzoom: Long?
    /**
     * Get the Minzoom property
     *
     * Use static method [RasterSource.defaultMinzoom] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("minzoom")

  /**
   * Maximum zoom level for which tiles are available, as in the TileJSON spec. Data from tiles
   * at the maxzoom are used when displaying the map at higher zoom levels.
   */
  fun maxzoom(value: Long = 22L): RasterSource = apply {
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
     * Use static method [RasterSource.defaultMaxzoom] to get the default property.
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
   * Influences the y direction of the tile coordinates. The global-mercator (aka Spherical Mercator) profile is assumed.
   */
  val scheme: Scheme?
    /**
     * Get the Scheme property
     *
     * Use static method [RasterSource.defaultScheme] to get the default property.
     *
     * @return Scheme
     */
    get() {
      getPropertyValue<String?>("scheme")?.let {
        return Scheme.valueOf(it.uppercase(Locale.US).replace('-', '_'))
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
   * A setting to determine whether a source's tiles are cached locally.
   */
  fun volatile(value: Boolean = false): RasterSource = apply {
    setProperty(PropertyValue("volatile", TypeUtils.wrapToValue(value)))
  }

  /**
   * A setting to determine whether a source's tiles are cached locally.
   */
  val volatile: Boolean?
    /**
     * Get the Volatile property
     *
     * Use static method [RasterSource.defaultVolatile] to get the default property.
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
  fun prefetchZoomDelta(value: Long = 4L): RasterSource = apply {
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
     * Use static method [RasterSource.defaultPrefetchZoomDelta] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("prefetch-zoom-delta")

  /**
   * Minimum tile update interval in seconds, which is used to throttle the tile update network requests.
   * If the given source supports loading tiles from a server, sets the minimum tile update interval.
   * Update network requests that are more frequent than the minimum tile update interval are suppressed.
   */
  fun minimumTileUpdateInterval(value: Double = 0.0): RasterSource = apply {
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
     * Use static method [RasterSource.defaultMinimumTileUpdateInterval] to get the default property.
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
  fun maxOverscaleFactorForParentTiles(value: Long): RasterSource = apply {
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
  fun tileRequestsDelay(value: Double = 0.0): RasterSource = apply {
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
     * Use static method [RasterSource.defaultTileRequestsDelay] to get the default property.
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
  fun tileNetworkRequestsDelay(value: Double = 0.0): RasterSource = apply {
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
     * Use static method [RasterSource.defaultTileNetworkRequestsDelay] to get the default property.
     *
     * @return Double
     */
    get() = getPropertyValue("tile-network-requests-delay")

  /**
   * Builder for RasterSource.
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
     * Influences the y direction of the tile coordinates. The global-mercator (aka Spherical Mercator) profile is assumed.
     */
    fun scheme(value: Scheme = Scheme.XYZ): Builder = apply {
      val propertyValue = PropertyValue("scheme", TypeUtils.wrapToValue(value.value))
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
    fun tileSet(tilejson: String, tiles: List<String>, block: TileSet.Builder.() -> Unit): Builder = apply {
      val tileSet = TileSet.Builder(tilejson, tiles).apply(block).build()
      for ((name, value) in tileSet) {
        val propertyValue = PropertyValue(name, value)
        properties[propertyValue.propertyName] = propertyValue
      }
    }
    /**
     * Build the RasterSource.
     *
     * @return the RasterSource
     */
    fun build(): RasterSource = RasterSource(this)
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
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster", "maxzoom").silentUnwrap()

    /**
     * Influences the y direction of the tile coordinates. The global-mercator (aka Spherical Mercator) profile is assumed.
     */
    val defaultScheme: Scheme?
      /**
       * Get the Scheme property
       *
       * @return Scheme
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("raster", "scheme").silentUnwrap<String>()?.let {
          return Scheme.valueOf(it.uppercase(Locale.US).replace('-', '_'))
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
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster", "volatile").silentUnwrap()

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
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster", "prefetch-zoom-delta").silentUnwrap()

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
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster", "minimum-tile-update-interval").silentUnwrap()

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
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster", "tile-requests-delay").silentUnwrap()

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
      get() = StyleManager.getStyleSourcePropertyDefaultValue("raster", "tile-network-requests-delay").silentUnwrap()
  }
}

/**
 * DSL function for [RasterSource].
 */
fun rasterSource(id: String, block: RasterSource.Builder.() -> Unit): RasterSource =
  RasterSource.Builder(id).apply(block).build()

// End of generated file.