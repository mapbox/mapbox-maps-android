package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.geojson.Feature
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.utils.check

/**
 * Custom Geometry Source, allows using FeatureCollections.
 *
 * CustomGeometrySource uses a coalescing model for frequent data updates targeting the same tile id,
 * which means, that the in-progress request as well as the last scheduled request are guaranteed to finish.
 * Any requests scheduled meanwhile can be canceled.
 */
class CustomGeometrySource(
  /**
   * Style source identifier.
   */
  id: String,
  /**
   * Settings for the custom geometry.
   */
  private val options: CustomGeometrySourceOptions,
) : Source(id) {
  /**
   * Get the type of the current source as a String.
   */
  override fun getType(): String {
    return "custom-geometry"
  }

  override fun addSource(style: Style): Expected<String, None> {
    return style.addStyleCustomGeometrySource(sourceId, options)
  }

  /**
   * Set tile data of a custom geometry.
   *
   * @param tileID Identifier of the tile
   * @param featureCollection An array with the features to add.
   */
  fun setTileData(tileID: CanonicalTileID, featureCollection: MutableList<Feature>) {
    delegate?.setStyleCustomGeometrySourceTileData(sourceId, tileID, featureCollection).check()
  }

  /**
   * Invalidate region for provided custom geometry source.
   *
   * @param coordinateBounds Coordinate bounds.
   */
  @Deprecated(
    message = "This method is deprecated because of confusing naming.",
    replaceWith = ReplaceWith("invalidateRegion(coordinateBounds)"),
    level = DeprecationLevel.WARNING
  )
  fun invalidRegion(coordinateBounds: CoordinateBounds) {
    invalidateRegion(coordinateBounds)
  }

  /**
   * Invalidate region for provided custom geometry source.
   *
   * @param coordinateBounds Coordinate bounds.
   */
  fun invalidateRegion(coordinateBounds: CoordinateBounds) {
    delegate?.invalidateStyleCustomGeometrySourceRegion(sourceId, coordinateBounds).check()
  }

  /**
   * Invalidate tile for provided custom geometry source.
   *
   * @param tileID Identifier of the tile
   */
  @Deprecated(
    message = "This method is deprecated because of confusing naming.",
    replaceWith = ReplaceWith("invalidateTile(tileID)"),
    level = DeprecationLevel.WARNING
  )
  fun invalidTile(tileID: CanonicalTileID) {
    invalidateTile(tileID)
  }

  /**
   * Invalidate tile for provided custom geometry source.
   *
   * @param tileID Identifier of the tile
   */
  fun invalidateTile(tileID: CanonicalTileID) {
    delegate?.invalidateStyleCustomGeometrySourceTile(sourceId, tileID).check()
  }
}

/**
 * DSL function for [CustomGeometrySource].
 */
fun customGeometrySource(
  id: String,
  block: CustomGeometrySourceOptions.Builder.() -> Unit
): CustomGeometrySource =
  CustomGeometrySource(id, CustomGeometrySourceOptions.Builder().apply(block).build())