package com.mapbox.maps.extension.style.sources

import com.mapbox.geojson.Feature
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.utils.check

/**
 * Custom Vector Source, allows using FeatureCollections.
 *
 * CustomGeometrySource uses a coalescing model for frequent data updates targeting the same tile id,
 * which means, that the in-progress request as well as the last scheduled request are guaranteed to finish.
 * Any requests scheduled meanwhile can be canceled.
 */
class CustomGeometrySource(
  /**
   * Style source identifier.
   */
  val id: String,
  /**
   * Settings for the custom geometry.
   */
  val options: CustomGeometrySourceOptions
) : StyleContract.StyleSourceExtension {
  private var delegate: Style? = null

  /**
   * Set tile data of a custom geometry.
   *
   * @param tileID Identifier of the tile
   * @param tileData JSON string representing the data in GeoJSON format. See RFC7946
   */
  fun setTileData(tileID: CanonicalTileID, featureCollection: MutableList<Feature>) {
    delegate?.setStyleCustomGeometrySourceTileData(id, tileID, featureCollection).check()
  }

  /**
   * Invalidate region for provided custom geometry source.
   *
   * @param coordinateBounds Coordinate bounds.
   */
  fun invalidRegion(coordinateBounds: CoordinateBounds) {
    delegate?.invalidateStyleCustomGeometrySourceRegion(id, coordinateBounds).check()
  }

  /**
   * Invalidate tile for provided custom geometry source.
   *
   * @param tileID Identifier of the tile
   */
  fun invalidTile(tileID: CanonicalTileID) {
    delegate?.invalidateStyleCustomGeometrySourceTile(id, tileID).check()
  }

  /**
   * Add the source to the Style.
   *
   * @param delegate The style delegate
   */
  override fun bindTo(delegate: Style) {
    this.delegate = delegate
    delegate.addStyleCustomGeometrySource(id, options).check()
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