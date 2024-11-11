package com.mapbox.maps

import com.mapbox.common.MapboxOptions
import com.mapbox.common.TileStore

/**
 * Manages configuration options that are used by the Maps API objects, such as maps data directory and base URL.
 *
 * The Maps API objects include instances of [MapView], [Snapshotter], [OfflineManager] and [OfflineRegionManager].
 *
 * The options changes are taken into consideration by the Maps API objects during their construction phase.
 * Any changes made to the options during runtime will not impact objects that have already been created.
 *
 * If the options need to be overridden, it is recommended to do it once at the application start and before
 * any of the Maps API objects are constructed. Although it is technically possible to run Maps API objects that use different
 * resource options, such a setup might cause performance implications.
 */
object MapboxMapsOptions {
  /**
   * Base URL that would be used by the Maps engine to make HTTP requests.
   *
   * The value of the key must be a string that is a valid URL.
   *
   * By default the engine uses the base URL `https://api.mapbox.com`
   */
  @JvmStatic
  var baseUrl: String
    get() = MapsResourceOptions.getBaseURL()
    set(value) = MapsResourceOptions.setBaseURL(value)

  /**
   * The path to the Mapbox Maps data folder.
   *
   * The engine will use this folder for storing offline style packages and temporary data.
   *
   * The application must have sufficient permissions to create files within the provided directory.
   */
  @JvmStatic
  var dataPath: String
    get() = MapsResourceOptions.getDataPath()
    set(value) = MapsResourceOptions.setDataPath(value)

  /**
   * the tile store usage mode for the Maps API objects.
   *
   * The [TileStoreUsageMode.READ_ONLY] mode is used by default.
   */
  @JvmStatic
  var tileStoreUsageMode: TileStoreUsageMode
    get() = MapsResourceOptions.getTileStoreUsageMode()
    set(value) = MapsResourceOptions.setTileStoreUsageMode(value)

  /**
   * The [TileStore] instance for the Maps API objects.
   *
   * This resource option is taken into consideration by the Maps API objects only if tile store usage is enabled.
   *
   * If null is set, but tile store usage is enabled, a tile store at the default location will be created and used.
   */
  @JvmStatic
  var tileStore: TileStore?
    get() = MapsResourceOptions.getTileStore()
    set(value) = MapsResourceOptions.setTileStore(value)
}

/**
 * Access Mapbox Maps SDK specific options.
 */
@Suppress("UnusedReceiverParameter")
@get:JvmSynthetic
val MapboxOptions.mapsOptions
  get() = MapboxMapsOptions