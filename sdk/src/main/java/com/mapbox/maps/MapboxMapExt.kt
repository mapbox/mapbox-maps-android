@file:JvmName("MapboxMapUtils")

package com.mapbox.maps

import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate

/**
 * Calculates geographical coordinates(i.e., longitude-latitude pair) that corresponds
 * to screen box.
 *
 * @param screenBox A box on the screen in \link MapOptions#size platform pixels \endlink.
 *
 * @return Returns a batch of geographical coordinates corresponding to the screen coordinates
 * on the screen.
 */
fun MapCameraManagerDelegate.coordinatesForPixels(screenBox: ScreenBox): List<Point> {
  return this.coordinatesForPixels(listOf(screenBox.min, screenBox.max))
}