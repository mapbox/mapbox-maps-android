package com.mapbox.maps.extension.style.sources

import android.graphics.Bitmap
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.check

/**
 * Describes the Custom Raster Source to be used in the style.
 *
 * To add the data, implement the [CustomRasterSourceOptions.fetchTileFunction] callback and call [setTileData] when needed.
 */
@MapboxExperimental
class CustomRasterSource(
  /**
   * Style source identifier.
   */
  id: String,
  /**
   * Settings for the custom raster source.
   */
  private val options: CustomRasterSourceOptions,
) : Source(id) {

  /**
   * Get the type of the current source as a String.
   */
  override fun getType(): String {
    return "custom-raster"
  }

  override fun addSource(style: MapboxStyleManager): Expected<String, None> {
    return style.addStyleCustomRasterSource(sourceId, options)
  }

  /**
   * Set tile data for a raster tile.
   *
   * By default, the provided data is not cached, and the implementation will call the fetch callback each time the tile reappears.
   *
   * @param tileID A [CanonicalTileID] of the tile.
   * @param image [Image] content of the tile. If NULL is provided then the tile gets removed from the map.
   */
  fun setTileData(tileID: CanonicalTileID, image: Image?) {
    delegate?.setStyleCustomRasterSourceTileData(sourceId, tileID, image).check()
  }

  /**
   * Set tile data for a raster tile.
   *
   * By default, the provided data is not cached, and the implementation will call the fetch callback each time the tile reappears.
   *
   * @param tileID A [CanonicalTileID] of the tile.
   * @param bitmap [Bitmap] content of the tile. If NULL is provided then the tile gets removed from the map.
   */
  fun setTileData(tileID: CanonicalTileID, bitmap: Bitmap?) {
    delegate?.setStyleCustomRasterSourceTileData(sourceId, tileID, bitmap?.toMapboxImage()).check()
  }

  /**
   * Invalidate region for provided custom raster source.
   *
   * @param coordinateBounds Coordinate bounds.
   */
  fun invalidateRegion(coordinateBounds: CoordinateBounds) {
    delegate?.invalidateStyleCustomRasterSourceRegion(sourceId, coordinateBounds).check()
  }

  /**
   * Invalidate tile for provided custom raster source.
   *
   * @param tileID Identifier of the tile
   */
  fun invalidateTile(tileID: CanonicalTileID) {
    delegate?.invalidateStyleCustomRasterSourceTile(sourceId, tileID).check()
  }

  /**
   * The property allows to define source specific resource budget, either in tile units or in megabytes.
   * Whenever tile cache goes over the defined limit, least recently used tile will be evicted from
   * the in-memory cache. Note that the current implementation does not take into account resources allocated by
   * the visible tiles.
   */
  fun setTileCacheBudget(value: TileCacheBudget) {
    setVolatileProperty(PropertyValue("tile-cache-budget", TypeUtils.wrapToValue(value)))
  }

  /**
   * The property allows to define source specific resource budget, either in tile units or in megabytes.
   * Whenever tile cache goes over the defined limit, least recently used tile will be evicted from
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
}

/**
 * DSL function for [CustomRasterSource].
 */
@MapboxExperimental
fun customRasterSource(
  id: String,
  block: CustomRasterSourceOptions.Builder.() -> Unit
): CustomRasterSource =
  CustomRasterSource(id, CustomRasterSourceOptions.Builder().apply(block).build())