package com.mapbox.maps

import android.graphics.Canvas
import com.mapbox.geojson.Point

/**
 * An instance of overlay allowing to draw custom content directly over the snapshot image on the [Canvas].
 */
abstract class SnapshotOverlay internal constructor() {

  /**
   * Canvas that should be used for drawing on top of the snapshot image.
   */
  abstract val canvas: Canvas

  /**
   * List of attributions for the sources in this snapshot.
   */
  abstract val attributions: List<String>

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
}