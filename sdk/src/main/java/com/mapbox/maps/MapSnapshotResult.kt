package com.mapbox.maps

import android.graphics.Bitmap
import com.mapbox.geojson.Point

/**
 * An image snapshot of a map rendered by the map snapshotter.
 */
abstract class MapSnapshotResult internal constructor() {
  /**
   * Calculate screen coordinate on the snapshot from geographical `coordinate`.
   *
   * @param coordinate A geographical `coordinate`.
   * @return A `screen coordinate` measured in `platform pixels` on the snapshot for geographical `coordinate`.
   */
  abstract fun screenCoordinate(coordinate: Point): ScreenCoordinate

  /**
   * Calculate geographical coordinates from a point on the snapshot.
   *
   * @param screenCoordinate A `screen coordinate` on the snapshot in `platform pixels`.
   * @return A geographical `coordinate` for a `screen coordinate` on the snapshot.
   */
  abstract fun coordinate(screenCoordinate: ScreenCoordinate): Point

  /**
   * Get list of attributions for the sources in this snapshot.
   *
   * @return A list of attributions for the sources in this snapshot.
   */
  abstract fun attributions(): List<String>

  /**
   * Get the rendered snapshot image as bitmap.
   *
   * @return A rendered snapshot image as bitmap.
   */
  abstract fun bitmap(): Bitmap
}