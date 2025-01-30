package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.CustomRasterSourceOptions
import com.mapbox.maps.CustomRasterSourceTileData
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.check

/**
 * Describes the Custom Raster Source to be used in the style.
 *
 * Note: the provided data is not cached, and the implementation will call [com.mapbox.maps.CustomRasterSourceTileStatusChangedCallback] each time the tile reappears.
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
   * Set tile data for raster tiles.
   *
   * The provided data is not cached, and the implementation will call [com.mapbox.maps.CustomRasterSourceTileStatusChangedCallback] each time the tile reappears.
   *
   * @param tileData List with new tile data.
   */
  fun setTileData(tileData: List<CustomRasterSourceTileData>) {
    delegate?.setStyleCustomRasterSourceTileData(sourceId, tileData).check()
  }

  /**
   * When a set of tiles for a current zoom level is being rendered and some of
   * the ideal tiles that cover the screen are not yet loaded, parent tile could be used
   * instead. This might introduce unwanted rendering side-effects, especially for raster tiles that are overscaled multiple times.
   * This property sets the maximum limit for how much a parent tile can be overscaled.
   */
  fun setMaxOverscaleFactorForParentTiles(value: Long) {
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