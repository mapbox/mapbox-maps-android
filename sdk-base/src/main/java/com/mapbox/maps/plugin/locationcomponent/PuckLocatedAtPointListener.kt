package com.mapbox.maps.plugin.locationcomponent

/**
 * Listener that gets invoked when the is location puck rendered on point validation finished.
 */
fun interface PuckLocatedAtPointListener {
  /**
   * This method is called when the is location puck rendered on point validation finished.
   *
   * @param isPuckLocatedAtPoint true if the given point is on the rendered location puck, false otherwise.
   */
  fun onResult(isPuckLocatedAtPoint: Boolean)
}