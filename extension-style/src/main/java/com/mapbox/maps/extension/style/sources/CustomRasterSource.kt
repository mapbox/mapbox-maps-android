package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.CustomRasterSourceOptions
import com.mapbox.maps.CustomRasterSourceTileData
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
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